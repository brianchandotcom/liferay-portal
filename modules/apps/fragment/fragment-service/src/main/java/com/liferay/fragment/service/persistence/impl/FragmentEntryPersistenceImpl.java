/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.service.persistence.impl;

import com.liferay.fragment.exception.DuplicateFragmentEntryExternalReferenceCodeException;
import com.liferay.fragment.exception.NoSuchEntryException;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryTable;
import com.liferay.fragment.model.impl.FragmentEntryImpl;
import com.liferay.fragment.model.impl.FragmentEntryModelImpl;
import com.liferay.fragment.service.persistence.FragmentEntryPersistence;
import com.liferay.fragment.service.persistence.FragmentEntryUtil;
import com.liferay.fragment.service.persistence.impl.constants.FragmentPersistenceConstants;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
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
 * The persistence implementation for the fragment entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = FragmentEntryPersistence.class)
public class FragmentEntryPersistenceImpl
	extends BasePersistenceImpl<FragmentEntry, NoSuchEntryException>
	implements FragmentEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>FragmentEntryUtil</code> to access the fragment entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		FragmentEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;
	private CollectionPersistenceFinder<FragmentEntry>
		_collectionPersistenceFinderByUuid;

	/**
	 * Returns all the fragment entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByUuid.find(
				finderCache, new Object[] {uuid}, start, end, orderByComparator,
				useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByUuid_First(
			String uuid, OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByUuid_First(
			uuid, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		throw new NoSuchEntryException(
			_collectionPersistenceFinderByUuid.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid}));
	}

	/**
	 * Returns the first fragment entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByUuid_First(
		String uuid, OrderByComparator<FragmentEntry> orderByComparator) {

		return _collectionPersistenceFinderByUuid.fetchFirst(
			finderCache, new Object[] {uuid}, orderByComparator);
	}

	/**
	 * Removes all the fragment entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		_collectionPersistenceFinderByUuid.remove(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of fragment entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByUuid(String uuid) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByUuid.count(
				finderCache, new Object[] {uuid});
		}
	}

	private FinderPath _finderPathWithPaginationFindByUuid_Head;
	private FinderPath _finderPathWithoutPaginationFindByUuid_Head;
	private FinderPath _finderPathCountByUuid_Head;
	private CollectionPersistenceFinder<FragmentEntry>
		_collectionPersistenceFinderByUuid_Head;

	/**
	 * Returns all the fragment entries where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByUuid_Head(String uuid, boolean head) {
		return findByUuid_Head(
			uuid, head, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where uuid = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByUuid_Head(
		String uuid, boolean head, int start, int end) {

		return findByUuid_Head(uuid, head, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where uuid = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByUuid_Head(
		String uuid, boolean head, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByUuid_Head(uuid, head, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where uuid = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByUuid_Head(
		String uuid, boolean head, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByUuid_Head.find(
				finderCache, new Object[] {uuid, head}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByUuid_Head_First(
			String uuid, boolean head,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByUuid_Head_First(
			uuid, head, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		throw new NoSuchEntryException(
			_collectionPersistenceFinderByUuid_Head.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, head}));
	}

	/**
	 * Returns the first fragment entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByUuid_Head_First(
		String uuid, boolean head,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return _collectionPersistenceFinderByUuid_Head.fetchFirst(
			finderCache, new Object[] {uuid, head}, orderByComparator);
	}

	/**
	 * Removes all the fragment entries where uuid = &#63; and head = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 */
	@Override
	public void removeByUuid_Head(String uuid, boolean head) {
		_collectionPersistenceFinderByUuid_Head.remove(
			finderCache, new Object[] {uuid, head});
	}

	/**
	 * Returns the number of fragment entries where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByUuid_Head(String uuid, boolean head) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByUuid_Head.count(
				finderCache, new Object[] {uuid, head});
		}
	}

	private FinderPath _finderPathWithPaginationFindByUUID_G;
	private FinderPath _finderPathWithoutPaginationFindByUUID_G;
	private FinderPath _finderPathCountByUUID_G;
	private CollectionPersistenceFinder<FragmentEntry>
		_collectionPersistenceFinderByUUID_G;

	/**
	 * Returns all the fragment entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByUUID_G(String uuid, long groupId) {
		return findByUUID_G(
			uuid, groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByUUID_G(
		String uuid, long groupId, int start, int end) {

		return findByUUID_G(uuid, groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByUUID_G(
		String uuid, long groupId, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByUUID_G(uuid, groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByUUID_G(
		String uuid, long groupId, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByUUID_G.find(
				finderCache, new Object[] {uuid, groupId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByUUID_G_First(
			String uuid, long groupId,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByUUID_G_First(
			uuid, groupId, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		throw new NoSuchEntryException(
			_collectionPersistenceFinderByUUID_G.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, groupId}));
	}

	/**
	 * Returns the first fragment entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByUUID_G_First(
		String uuid, long groupId,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return _collectionPersistenceFinderByUUID_G.fetchFirst(
			finderCache, new Object[] {uuid, groupId}, orderByComparator);
	}

	/**
	 * Removes all the fragment entries where uuid = &#63; and groupId = &#63; from the database.
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
	 * Returns the number of fragment entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByUUID_G.count(
				finderCache, new Object[] {uuid, groupId});
		}
	}

	private FinderPath _finderPathFetchByUUID_G_Head;
	private UniquePersistenceFinder<FragmentEntry>
		_uniquePersistenceFinderByUUID_G_Head;

	/**
	 * Returns the fragment entry where uuid = &#63; and groupId = &#63; and head = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @return the matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByUUID_G_Head(
			String uuid, long groupId, boolean head)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByUUID_G_Head(uuid, groupId, head);

		if (fragmentEntry == null) {
			String message =
				_uniquePersistenceFinderByUUID_G_Head.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {uuid, groupId, head});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchEntryException(message);
		}

		return fragmentEntry;
	}

	/**
	 * Returns the fragment entry where uuid = &#63; and groupId = &#63; and head = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @return the matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByUUID_G_Head(
		String uuid, long groupId, boolean head) {

		return fetchByUUID_G_Head(uuid, groupId, head, true);
	}

	/**
	 * Returns the fragment entry where uuid = &#63; and groupId = &#63; and head = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByUUID_G_Head(
		String uuid, long groupId, boolean head, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _uniquePersistenceFinderByUUID_G_Head.fetch(
				finderCache, new Object[] {uuid, groupId, head},
				useFinderCache);
		}
	}

	/**
	 * Removes the fragment entry where uuid = &#63; and groupId = &#63; and head = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @return the fragment entry that was removed
	 */
	@Override
	public FragmentEntry removeByUUID_G_Head(
			String uuid, long groupId, boolean head)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = findByUUID_G_Head(uuid, groupId, head);

		return remove(fragmentEntry);
	}

	/**
	 * Returns the number of fragment entries where uuid = &#63; and groupId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByUUID_G_Head(String uuid, long groupId, boolean head) {
		return _uniquePersistenceFinderByUUID_G_Head.count(
			finderCache, new Object[] {uuid, groupId, head});
	}

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;
	private CollectionPersistenceFinder<FragmentEntry>
		_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns all the fragment entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByUuid_C.find(
				finderCache, new Object[] {uuid, companyId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		throw new NoSuchEntryException(
			_collectionPersistenceFinderByUuid_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, companyId}));
	}

	/**
	 * Returns the first fragment entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return _collectionPersistenceFinderByUuid_C.fetchFirst(
			finderCache, new Object[] {uuid, companyId}, orderByComparator);
	}

	/**
	 * Removes all the fragment entries where uuid = &#63; and companyId = &#63; from the database.
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
	 * Returns the number of fragment entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByUuid_C.count(
				finderCache, new Object[] {uuid, companyId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByUuid_C_Head;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C_Head;
	private FinderPath _finderPathCountByUuid_C_Head;
	private CollectionPersistenceFinder<FragmentEntry>
		_collectionPersistenceFinderByUuid_C_Head;

	/**
	 * Returns all the fragment entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByUuid_C_Head(
		String uuid, long companyId, boolean head) {

		return findByUuid_C_Head(
			uuid, companyId, head, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByUuid_C_Head(
		String uuid, long companyId, boolean head, int start, int end) {

		return findByUuid_C_Head(uuid, companyId, head, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByUuid_C_Head(
		String uuid, long companyId, boolean head, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByUuid_C_Head(
			uuid, companyId, head, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByUuid_C_Head(
		String uuid, long companyId, boolean head, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByUuid_C_Head.find(
				finderCache, new Object[] {uuid, companyId, head}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByUuid_C_Head_First(
			String uuid, long companyId, boolean head,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByUuid_C_Head_First(
			uuid, companyId, head, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		throw new NoSuchEntryException(
			_collectionPersistenceFinderByUuid_C_Head.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {uuid, companyId, head}));
	}

	/**
	 * Returns the first fragment entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByUuid_C_Head_First(
		String uuid, long companyId, boolean head,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return _collectionPersistenceFinderByUuid_C_Head.fetchFirst(
			finderCache, new Object[] {uuid, companyId, head},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entries where uuid = &#63; and companyId = &#63; and head = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 */
	@Override
	public void removeByUuid_C_Head(String uuid, long companyId, boolean head) {
		_collectionPersistenceFinderByUuid_C_Head.remove(
			finderCache, new Object[] {uuid, companyId, head});
	}

	/**
	 * Returns the number of fragment entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByUuid_C_Head(String uuid, long companyId, boolean head) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByUuid_C_Head.count(
				finderCache, new Object[] {uuid, companyId, head});
		}
	}

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;
	private CollectionPersistenceFinder<FragmentEntry>
		_collectionPersistenceFinderByGroupId;

	/**
	 * Returns all the fragment entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByGroupId.find(
				finderCache, new Object[] {groupId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByGroupId_First(
			long groupId, OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByGroupId_First(
			groupId, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		throw new NoSuchEntryException(
			_collectionPersistenceFinderByGroupId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {groupId}));
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByGroupId_First(
		long groupId, OrderByComparator<FragmentEntry> orderByComparator) {

		return _collectionPersistenceFinderByGroupId.fetchFirst(
			finderCache, new Object[] {groupId}, orderByComparator);
	}

	/**
	 * Removes all the fragment entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		_collectionPersistenceFinderByGroupId.remove(
			finderCache, new Object[] {groupId});
	}

	/**
	 * Returns the number of fragment entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByGroupId(long groupId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByGroupId.count(
				finderCache, new Object[] {groupId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByGroupId_Head;
	private FinderPath _finderPathWithoutPaginationFindByGroupId_Head;
	private FinderPath _finderPathCountByGroupId_Head;
	private CollectionPersistenceFinder<FragmentEntry>
		_collectionPersistenceFinderByGroupId_Head;

	/**
	 * Returns all the fragment entries where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByGroupId_Head(long groupId, boolean head) {
		return findByGroupId_Head(
			groupId, head, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByGroupId_Head(
		long groupId, boolean head, int start, int end) {

		return findByGroupId_Head(groupId, head, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByGroupId_Head(
		long groupId, boolean head, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByGroupId_Head(
			groupId, head, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByGroupId_Head(
		long groupId, boolean head, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByGroupId_Head.find(
				finderCache, new Object[] {groupId, head}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByGroupId_Head_First(
			long groupId, boolean head,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByGroupId_Head_First(
			groupId, head, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		throw new NoSuchEntryException(
			_collectionPersistenceFinderByGroupId_Head.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {groupId, head}));
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByGroupId_Head_First(
		long groupId, boolean head,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return _collectionPersistenceFinderByGroupId_Head.fetchFirst(
			finderCache, new Object[] {groupId, head}, orderByComparator);
	}

	/**
	 * Removes all the fragment entries where groupId = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 */
	@Override
	public void removeByGroupId_Head(long groupId, boolean head) {
		_collectionPersistenceFinderByGroupId_Head.remove(
			finderCache, new Object[] {groupId, head});
	}

	/**
	 * Returns the number of fragment entries where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByGroupId_Head(long groupId, boolean head) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByGroupId_Head.count(
				finderCache, new Object[] {groupId, head});
		}
	}

	private FinderPath _finderPathWithPaginationFindByFragmentCollectionId;
	private FinderPath _finderPathWithoutPaginationFindByFragmentCollectionId;
	private FinderPath _finderPathCountByFragmentCollectionId;
	private CollectionPersistenceFinder<FragmentEntry>
		_collectionPersistenceFinderByFragmentCollectionId;

	/**
	 * Returns all the fragment entries where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByFragmentCollectionId(
		long fragmentCollectionId) {

		return findByFragmentCollectionId(
			fragmentCollectionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByFragmentCollectionId(
		long fragmentCollectionId, int start, int end) {

		return findByFragmentCollectionId(
			fragmentCollectionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByFragmentCollectionId(
		long fragmentCollectionId, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByFragmentCollectionId(
			fragmentCollectionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByFragmentCollectionId(
		long fragmentCollectionId, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByFragmentCollectionId.find(
				finderCache, new Object[] {fragmentCollectionId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry in the ordered set where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByFragmentCollectionId_First(
			long fragmentCollectionId,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByFragmentCollectionId_First(
			fragmentCollectionId, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		throw new NoSuchEntryException(
			_collectionPersistenceFinderByFragmentCollectionId.
				buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {fragmentCollectionId}));
	}

	/**
	 * Returns the first fragment entry in the ordered set where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByFragmentCollectionId_First(
		long fragmentCollectionId,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return _collectionPersistenceFinderByFragmentCollectionId.fetchFirst(
			finderCache, new Object[] {fragmentCollectionId},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entries where fragmentCollectionId = &#63; from the database.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 */
	@Override
	public void removeByFragmentCollectionId(long fragmentCollectionId) {
		_collectionPersistenceFinderByFragmentCollectionId.remove(
			finderCache, new Object[] {fragmentCollectionId});
	}

	/**
	 * Returns the number of fragment entries where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByFragmentCollectionId(long fragmentCollectionId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByFragmentCollectionId.count(
				finderCache, new Object[] {fragmentCollectionId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByFragmentCollectionId_Head;
	private FinderPath
		_finderPathWithoutPaginationFindByFragmentCollectionId_Head;
	private FinderPath _finderPathCountByFragmentCollectionId_Head;
	private CollectionPersistenceFinder<FragmentEntry>
		_collectionPersistenceFinderByFragmentCollectionId_Head;

	/**
	 * Returns all the fragment entries where fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByFragmentCollectionId_Head(
		long fragmentCollectionId, boolean head) {

		return findByFragmentCollectionId_Head(
			fragmentCollectionId, head, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the fragment entries where fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByFragmentCollectionId_Head(
		long fragmentCollectionId, boolean head, int start, int end) {

		return findByFragmentCollectionId_Head(
			fragmentCollectionId, head, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByFragmentCollectionId_Head(
		long fragmentCollectionId, boolean head, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByFragmentCollectionId_Head(
			fragmentCollectionId, head, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByFragmentCollectionId_Head(
		long fragmentCollectionId, boolean head, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByFragmentCollectionId_Head.find(
				finderCache, new Object[] {fragmentCollectionId, head}, start,
				end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry in the ordered set where fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByFragmentCollectionId_Head_First(
			long fragmentCollectionId, boolean head,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByFragmentCollectionId_Head_First(
			fragmentCollectionId, head, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		throw new NoSuchEntryException(
			_collectionPersistenceFinderByFragmentCollectionId_Head.
				buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {fragmentCollectionId, head}));
	}

	/**
	 * Returns the first fragment entry in the ordered set where fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByFragmentCollectionId_Head_First(
		long fragmentCollectionId, boolean head,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return _collectionPersistenceFinderByFragmentCollectionId_Head.
			fetchFirst(
				finderCache, new Object[] {fragmentCollectionId, head},
				orderByComparator);
	}

	/**
	 * Removes all the fragment entries where fragmentCollectionId = &#63; and head = &#63; from the database.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 */
	@Override
	public void removeByFragmentCollectionId_Head(
		long fragmentCollectionId, boolean head) {

		_collectionPersistenceFinderByFragmentCollectionId_Head.remove(
			finderCache, new Object[] {fragmentCollectionId, head});
	}

	/**
	 * Returns the number of fragment entries where fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByFragmentCollectionId_Head(
		long fragmentCollectionId, boolean head) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByFragmentCollectionId_Head.
				count(finderCache, new Object[] {fragmentCollectionId, head});
		}
	}

	private FinderPath _finderPathWithPaginationFindByType;
	private FinderPath _finderPathWithoutPaginationFindByType;
	private FinderPath _finderPathCountByType;
	private CollectionPersistenceFinder<FragmentEntry>
		_collectionPersistenceFinderByType;

	/**
	 * Returns all the fragment entries where type = &#63;.
	 *
	 * @param type the type
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByType(int type) {
		return findByType(type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByType(int type, int start, int end) {
		return findByType(type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByType(
		int type, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByType(type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByType(
		int type, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByType.find(
				finderCache, new Object[] {type}, start, end, orderByComparator,
				useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByType_First(
			int type, OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByType_First(
			type, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		throw new NoSuchEntryException(
			_collectionPersistenceFinderByType.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {type}));
	}

	/**
	 * Returns the first fragment entry in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByType_First(
		int type, OrderByComparator<FragmentEntry> orderByComparator) {

		return _collectionPersistenceFinderByType.fetchFirst(
			finderCache, new Object[] {type}, orderByComparator);
	}

	/**
	 * Removes all the fragment entries where type = &#63; from the database.
	 *
	 * @param type the type
	 */
	@Override
	public void removeByType(int type) {
		_collectionPersistenceFinderByType.remove(
			finderCache, new Object[] {type});
	}

	/**
	 * Returns the number of fragment entries where type = &#63;.
	 *
	 * @param type the type
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByType(int type) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByType.count(
				finderCache, new Object[] {type});
		}
	}

	private FinderPath _finderPathWithPaginationFindByType_Head;
	private FinderPath _finderPathWithoutPaginationFindByType_Head;
	private FinderPath _finderPathCountByType_Head;
	private CollectionPersistenceFinder<FragmentEntry>
		_collectionPersistenceFinderByType_Head;

	/**
	 * Returns all the fragment entries where type = &#63; and head = &#63;.
	 *
	 * @param type the type
	 * @param head the head
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByType_Head(int type, boolean head) {
		return findByType_Head(
			type, head, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where type = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByType_Head(
		int type, boolean head, int start, int end) {

		return findByType_Head(type, head, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where type = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByType_Head(
		int type, boolean head, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByType_Head(type, head, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where type = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByType_Head(
		int type, boolean head, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByType_Head.find(
				finderCache, new Object[] {type, head}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry in the ordered set where type = &#63; and head = &#63;.
	 *
	 * @param type the type
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByType_Head_First(
			int type, boolean head,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByType_Head_First(
			type, head, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		throw new NoSuchEntryException(
			_collectionPersistenceFinderByType_Head.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {type, head}));
	}

	/**
	 * Returns the first fragment entry in the ordered set where type = &#63; and head = &#63;.
	 *
	 * @param type the type
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByType_Head_First(
		int type, boolean head,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return _collectionPersistenceFinderByType_Head.fetchFirst(
			finderCache, new Object[] {type, head}, orderByComparator);
	}

	/**
	 * Removes all the fragment entries where type = &#63; and head = &#63; from the database.
	 *
	 * @param type the type
	 * @param head the head
	 */
	@Override
	public void removeByType_Head(int type, boolean head) {
		_collectionPersistenceFinderByType_Head.remove(
			finderCache, new Object[] {type, head});
	}

	/**
	 * Returns the number of fragment entries where type = &#63; and head = &#63;.
	 *
	 * @param type the type
	 * @param head the head
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByType_Head(int type, boolean head) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByType_Head.count(
				finderCache, new Object[] {type, head});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_FCI;
	private FinderPath _finderPathWithoutPaginationFindByG_FCI;
	private FinderPath _finderPathCountByG_FCI;
	private CollectionPersistenceFinder<FragmentEntry>
		_collectionPersistenceFinderByG_FCI;

	/**
	 * Returns all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI(
		long groupId, long fragmentCollectionId) {

		return findByG_FCI(
			groupId, fragmentCollectionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI(
		long groupId, long fragmentCollectionId, int start, int end) {

		return findByG_FCI(groupId, fragmentCollectionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI(
		long groupId, long fragmentCollectionId, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByG_FCI(
			groupId, fragmentCollectionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI(
		long groupId, long fragmentCollectionId, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByG_FCI.find(
				finderCache, new Object[] {groupId, fragmentCollectionId},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FCI_First(
			long groupId, long fragmentCollectionId,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FCI_First(
			groupId, fragmentCollectionId, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		throw new NoSuchEntryException(
			_collectionPersistenceFinderByG_FCI.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, fragmentCollectionId}));
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FCI_First(
		long groupId, long fragmentCollectionId,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return _collectionPersistenceFinderByG_FCI.fetchFirst(
			finderCache, new Object[] {groupId, fragmentCollectionId},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; from the database.
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
	 * Returns the number of fragment entries where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByG_FCI(long groupId, long fragmentCollectionId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByG_FCI.count(
				finderCache, new Object[] {groupId, fragmentCollectionId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_FCI_Head;
	private FinderPath _finderPathWithoutPaginationFindByG_FCI_Head;
	private FinderPath _finderPathCountByG_FCI_Head;
	private CollectionPersistenceFinder<FragmentEntry>
		_collectionPersistenceFinderByG_FCI_Head;

	/**
	 * Returns all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_Head(
		long groupId, long fragmentCollectionId, boolean head) {

		return findByG_FCI_Head(
			groupId, fragmentCollectionId, head, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_Head(
		long groupId, long fragmentCollectionId, boolean head, int start,
		int end) {

		return findByG_FCI_Head(
			groupId, fragmentCollectionId, head, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_Head(
		long groupId, long fragmentCollectionId, boolean head, int start,
		int end, OrderByComparator<FragmentEntry> orderByComparator) {

		return findByG_FCI_Head(
			groupId, fragmentCollectionId, head, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_Head(
		long groupId, long fragmentCollectionId, boolean head, int start,
		int end, OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByG_FCI_Head.find(
				finderCache, new Object[] {groupId, fragmentCollectionId, head},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FCI_Head_First(
			long groupId, long fragmentCollectionId, boolean head,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FCI_Head_First(
			groupId, fragmentCollectionId, head, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		throw new NoSuchEntryException(
			_collectionPersistenceFinderByG_FCI_Head.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, fragmentCollectionId, head}));
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FCI_Head_First(
		long groupId, long fragmentCollectionId, boolean head,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return _collectionPersistenceFinderByG_FCI_Head.fetchFirst(
			finderCache, new Object[] {groupId, fragmentCollectionId, head},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 */
	@Override
	public void removeByG_FCI_Head(
		long groupId, long fragmentCollectionId, boolean head) {

		_collectionPersistenceFinderByG_FCI_Head.remove(
			finderCache, new Object[] {groupId, fragmentCollectionId, head});
	}

	/**
	 * Returns the number of fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByG_FCI_Head(
		long groupId, long fragmentCollectionId, boolean head) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByG_FCI_Head.count(
				finderCache,
				new Object[] {groupId, fragmentCollectionId, head});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_FEK;
	private FinderPath _finderPathWithoutPaginationFindByG_FEK;
	private FinderPath _finderPathCountByG_FEK;
	private CollectionPersistenceFinder<FragmentEntry>
		_collectionPersistenceFinderByG_FEK;

	/**
	 * Returns all the fragment entries where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FEK(
		long groupId, String fragmentEntryKey) {

		return findByG_FEK(
			groupId, fragmentEntryKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FEK(
		long groupId, String fragmentEntryKey, int start, int end) {

		return findByG_FEK(groupId, fragmentEntryKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FEK(
		long groupId, String fragmentEntryKey, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByG_FEK(
			groupId, fragmentEntryKey, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FEK(
		long groupId, String fragmentEntryKey, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByG_FEK.find(
				finderCache, new Object[] {groupId, fragmentEntryKey}, start,
				end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FEK_First(
			long groupId, String fragmentEntryKey,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FEK_First(
			groupId, fragmentEntryKey, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		throw new NoSuchEntryException(
			_collectionPersistenceFinderByG_FEK.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, fragmentEntryKey}));
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FEK_First(
		long groupId, String fragmentEntryKey,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return _collectionPersistenceFinderByG_FEK.fetchFirst(
			finderCache, new Object[] {groupId, fragmentEntryKey},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entries where groupId = &#63; and fragmentEntryKey = &#63; from the database.
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
	 * Returns the number of fragment entries where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByG_FEK(long groupId, String fragmentEntryKey) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByG_FEK.count(
				finderCache, new Object[] {groupId, fragmentEntryKey});
		}
	}

	private FinderPath _finderPathFetchByG_FEK_Head;
	private UniquePersistenceFinder<FragmentEntry>
		_uniquePersistenceFinderByG_FEK_Head;

	/**
	 * Returns the fragment entry where groupId = &#63; and fragmentEntryKey = &#63; and head = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param head the head
	 * @return the matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FEK_Head(
			long groupId, String fragmentEntryKey, boolean head)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FEK_Head(
			groupId, fragmentEntryKey, head);

		if (fragmentEntry == null) {
			String message =
				_uniquePersistenceFinderByG_FEK_Head.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {groupId, fragmentEntryKey, head});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchEntryException(message);
		}

		return fragmentEntry;
	}

	/**
	 * Returns the fragment entry where groupId = &#63; and fragmentEntryKey = &#63; and head = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param head the head
	 * @return the matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FEK_Head(
		long groupId, String fragmentEntryKey, boolean head) {

		return fetchByG_FEK_Head(groupId, fragmentEntryKey, head, true);
	}

	/**
	 * Returns the fragment entry where groupId = &#63; and fragmentEntryKey = &#63; and head = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param head the head
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FEK_Head(
		long groupId, String fragmentEntryKey, boolean head,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _uniquePersistenceFinderByG_FEK_Head.fetch(
				finderCache, new Object[] {groupId, fragmentEntryKey, head},
				useFinderCache);
		}
	}

	/**
	 * Removes the fragment entry where groupId = &#63; and fragmentEntryKey = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param head the head
	 * @return the fragment entry that was removed
	 */
	@Override
	public FragmentEntry removeByG_FEK_Head(
			long groupId, String fragmentEntryKey, boolean head)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = findByG_FEK_Head(
			groupId, fragmentEntryKey, head);

		return remove(fragmentEntry);
	}

	/**
	 * Returns the number of fragment entries where groupId = &#63; and fragmentEntryKey = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param head the head
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByG_FEK_Head(
		long groupId, String fragmentEntryKey, boolean head) {

		return _uniquePersistenceFinderByG_FEK_Head.count(
			finderCache, new Object[] {groupId, fragmentEntryKey, head});
	}

	private FinderPath _finderPathWithPaginationFindByG_FCI_LikeN;
	private FinderPath _finderPathWithPaginationCountByG_FCI_LikeN;
	private CollectionPersistenceFinder<FragmentEntry>
		_collectionPersistenceFinderByG_FCI_LikeN;

	/**
	 * Returns all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name) {

		return findByG_FCI_LikeN(
			groupId, fragmentCollectionId, name, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name, int start,
		int end) {

		return findByG_FCI_LikeN(
			groupId, fragmentCollectionId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name, int start,
		int end, OrderByComparator<FragmentEntry> orderByComparator) {

		return findByG_FCI_LikeN(
			groupId, fragmentCollectionId, name, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name, int start,
		int end, OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByG_FCI_LikeN.find(
				finderCache, new Object[] {groupId, fragmentCollectionId, name},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FCI_LikeN_First(
			long groupId, long fragmentCollectionId, String name,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FCI_LikeN_First(
			groupId, fragmentCollectionId, name, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		throw new NoSuchEntryException(
			_collectionPersistenceFinderByG_FCI_LikeN.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, fragmentCollectionId, name}));
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FCI_LikeN_First(
		long groupId, long fragmentCollectionId, String name,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return _collectionPersistenceFinderByG_FCI_LikeN.fetchFirst(
			finderCache, new Object[] {groupId, fragmentCollectionId, name},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; from the database.
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
	 * Returns the number of fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByG_FCI_LikeN.count(
				finderCache,
				new Object[] {groupId, fragmentCollectionId, name});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_FCI_LikeN_Head;
	private FinderPath _finderPathWithPaginationCountByG_FCI_LikeN_Head;
	private CollectionPersistenceFinder<FragmentEntry>
		_collectionPersistenceFinderByG_FCI_LikeN_Head;

	/**
	 * Returns all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param head the head
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_LikeN_Head(
		long groupId, long fragmentCollectionId, String name, boolean head) {

		return findByG_FCI_LikeN_Head(
			groupId, fragmentCollectionId, name, head, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_LikeN_Head(
		long groupId, long fragmentCollectionId, String name, boolean head,
		int start, int end) {

		return findByG_FCI_LikeN_Head(
			groupId, fragmentCollectionId, name, head, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_LikeN_Head(
		long groupId, long fragmentCollectionId, String name, boolean head,
		int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByG_FCI_LikeN_Head(
			groupId, fragmentCollectionId, name, head, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_LikeN_Head(
		long groupId, long fragmentCollectionId, String name, boolean head,
		int start, int end, OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByG_FCI_LikeN_Head.find(
				finderCache,
				new Object[] {groupId, fragmentCollectionId, name, head}, start,
				end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FCI_LikeN_Head_First(
			long groupId, long fragmentCollectionId, String name, boolean head,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FCI_LikeN_Head_First(
			groupId, fragmentCollectionId, name, head, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		throw new NoSuchEntryException(
			_collectionPersistenceFinderByG_FCI_LikeN_Head.
				buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {groupId, fragmentCollectionId, name, head}));
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FCI_LikeN_Head_First(
		long groupId, long fragmentCollectionId, String name, boolean head,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return _collectionPersistenceFinderByG_FCI_LikeN_Head.fetchFirst(
			finderCache,
			new Object[] {groupId, fragmentCollectionId, name, head},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param head the head
	 */
	@Override
	public void removeByG_FCI_LikeN_Head(
		long groupId, long fragmentCollectionId, String name, boolean head) {

		_collectionPersistenceFinderByG_FCI_LikeN_Head.remove(
			finderCache,
			new Object[] {groupId, fragmentCollectionId, name, head});
	}

	/**
	 * Returns the number of fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param head the head
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByG_FCI_LikeN_Head(
		long groupId, long fragmentCollectionId, String name, boolean head) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByG_FCI_LikeN_Head.count(
				finderCache,
				new Object[] {groupId, fragmentCollectionId, name, head});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_FCI_T;
	private FinderPath _finderPathWithoutPaginationFindByG_FCI_T;
	private FinderPath _finderPathCountByG_FCI_T;
	private CollectionPersistenceFinder<FragmentEntry>
		_collectionPersistenceFinderByG_FCI_T;

	/**
	 * Returns all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_T(
		long groupId, long fragmentCollectionId, int type) {

		return findByG_FCI_T(
			groupId, fragmentCollectionId, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_T(
		long groupId, long fragmentCollectionId, int type, int start, int end) {

		return findByG_FCI_T(
			groupId, fragmentCollectionId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_T(
		long groupId, long fragmentCollectionId, int type, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByG_FCI_T(
			groupId, fragmentCollectionId, type, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_T(
		long groupId, long fragmentCollectionId, int type, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByG_FCI_T.find(
				finderCache, new Object[] {groupId, fragmentCollectionId, type},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FCI_T_First(
			long groupId, long fragmentCollectionId, int type,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FCI_T_First(
			groupId, fragmentCollectionId, type, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		throw new NoSuchEntryException(
			_collectionPersistenceFinderByG_FCI_T.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, fragmentCollectionId, type}));
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FCI_T_First(
		long groupId, long fragmentCollectionId, int type,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return _collectionPersistenceFinderByG_FCI_T.fetchFirst(
			finderCache, new Object[] {groupId, fragmentCollectionId, type},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; from the database.
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
	 * Returns the number of fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByG_FCI_T(
		long groupId, long fragmentCollectionId, int type) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByG_FCI_T.count(
				finderCache,
				new Object[] {groupId, fragmentCollectionId, type});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_FCI_T_Head;
	private FinderPath _finderPathWithoutPaginationFindByG_FCI_T_Head;
	private FinderPath _finderPathCountByG_FCI_T_Head;
	private CollectionPersistenceFinder<FragmentEntry>
		_collectionPersistenceFinderByG_FCI_T_Head;

	/**
	 * Returns all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param head the head
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_T_Head(
		long groupId, long fragmentCollectionId, int type, boolean head) {

		return findByG_FCI_T_Head(
			groupId, fragmentCollectionId, type, head, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_T_Head(
		long groupId, long fragmentCollectionId, int type, boolean head,
		int start, int end) {

		return findByG_FCI_T_Head(
			groupId, fragmentCollectionId, type, head, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_T_Head(
		long groupId, long fragmentCollectionId, int type, boolean head,
		int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByG_FCI_T_Head(
			groupId, fragmentCollectionId, type, head, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_T_Head(
		long groupId, long fragmentCollectionId, int type, boolean head,
		int start, int end, OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByG_FCI_T_Head.find(
				finderCache,
				new Object[] {groupId, fragmentCollectionId, type, head}, start,
				end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FCI_T_Head_First(
			long groupId, long fragmentCollectionId, int type, boolean head,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FCI_T_Head_First(
			groupId, fragmentCollectionId, type, head, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		throw new NoSuchEntryException(
			_collectionPersistenceFinderByG_FCI_T_Head.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, fragmentCollectionId, type, head}));
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FCI_T_Head_First(
		long groupId, long fragmentCollectionId, int type, boolean head,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return _collectionPersistenceFinderByG_FCI_T_Head.fetchFirst(
			finderCache,
			new Object[] {groupId, fragmentCollectionId, type, head},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param head the head
	 */
	@Override
	public void removeByG_FCI_T_Head(
		long groupId, long fragmentCollectionId, int type, boolean head) {

		_collectionPersistenceFinderByG_FCI_T_Head.remove(
			finderCache,
			new Object[] {groupId, fragmentCollectionId, type, head});
	}

	/**
	 * Returns the number of fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param head the head
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByG_FCI_T_Head(
		long groupId, long fragmentCollectionId, int type, boolean head) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByG_FCI_T_Head.count(
				finderCache,
				new Object[] {groupId, fragmentCollectionId, type, head});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_FCI_S;
	private FinderPath _finderPathWithoutPaginationFindByG_FCI_S;
	private FinderPath _finderPathCountByG_FCI_S;
	private CollectionPersistenceFinder<FragmentEntry>
		_collectionPersistenceFinderByG_FCI_S;

	/**
	 * Returns all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_S(
		long groupId, long fragmentCollectionId, int status) {

		return findByG_FCI_S(
			groupId, fragmentCollectionId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_S(
		long groupId, long fragmentCollectionId, int status, int start,
		int end) {

		return findByG_FCI_S(
			groupId, fragmentCollectionId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_S(
		long groupId, long fragmentCollectionId, int status, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByG_FCI_S(
			groupId, fragmentCollectionId, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_S(
		long groupId, long fragmentCollectionId, int status, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByG_FCI_S.find(
				finderCache,
				new Object[] {groupId, fragmentCollectionId, status}, start,
				end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FCI_S_First(
			long groupId, long fragmentCollectionId, int status,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FCI_S_First(
			groupId, fragmentCollectionId, status, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		throw new NoSuchEntryException(
			_collectionPersistenceFinderByG_FCI_S.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, fragmentCollectionId, status}));
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FCI_S_First(
		long groupId, long fragmentCollectionId, int status,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return _collectionPersistenceFinderByG_FCI_S.fetchFirst(
			finderCache, new Object[] {groupId, fragmentCollectionId, status},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; from the database.
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
	 * Returns the number of fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByG_FCI_S(
		long groupId, long fragmentCollectionId, int status) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByG_FCI_S.count(
				finderCache,
				new Object[] {groupId, fragmentCollectionId, status});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_FCI_S_Head;
	private FinderPath _finderPathWithoutPaginationFindByG_FCI_S_Head;
	private FinderPath _finderPathCountByG_FCI_S_Head;
	private CollectionPersistenceFinder<FragmentEntry>
		_collectionPersistenceFinderByG_FCI_S_Head;

	/**
	 * Returns all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param head the head
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_S_Head(
		long groupId, long fragmentCollectionId, int status, boolean head) {

		return findByG_FCI_S_Head(
			groupId, fragmentCollectionId, status, head, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_S_Head(
		long groupId, long fragmentCollectionId, int status, boolean head,
		int start, int end) {

		return findByG_FCI_S_Head(
			groupId, fragmentCollectionId, status, head, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_S_Head(
		long groupId, long fragmentCollectionId, int status, boolean head,
		int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByG_FCI_S_Head(
			groupId, fragmentCollectionId, status, head, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_S_Head(
		long groupId, long fragmentCollectionId, int status, boolean head,
		int start, int end, OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByG_FCI_S_Head.find(
				finderCache,
				new Object[] {groupId, fragmentCollectionId, status, head},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FCI_S_Head_First(
			long groupId, long fragmentCollectionId, int status, boolean head,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FCI_S_Head_First(
			groupId, fragmentCollectionId, status, head, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		throw new NoSuchEntryException(
			_collectionPersistenceFinderByG_FCI_S_Head.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, fragmentCollectionId, status, head}));
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FCI_S_Head_First(
		long groupId, long fragmentCollectionId, int status, boolean head,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return _collectionPersistenceFinderByG_FCI_S_Head.fetchFirst(
			finderCache,
			new Object[] {groupId, fragmentCollectionId, status, head},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param head the head
	 */
	@Override
	public void removeByG_FCI_S_Head(
		long groupId, long fragmentCollectionId, int status, boolean head) {

		_collectionPersistenceFinderByG_FCI_S_Head.remove(
			finderCache,
			new Object[] {groupId, fragmentCollectionId, status, head});
	}

	/**
	 * Returns the number of fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param head the head
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByG_FCI_S_Head(
		long groupId, long fragmentCollectionId, int status, boolean head) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByG_FCI_S_Head.count(
				finderCache,
				new Object[] {groupId, fragmentCollectionId, status, head});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_FCI_LikeN_S;
	private FinderPath _finderPathWithPaginationCountByG_FCI_LikeN_S;
	private CollectionPersistenceFinder<FragmentEntry>
		_collectionPersistenceFinderByG_FCI_LikeN_S;

	/**
	 * Returns all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status) {

		return findByG_FCI_LikeN_S(
			groupId, fragmentCollectionId, name, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status,
		int start, int end) {

		return findByG_FCI_LikeN_S(
			groupId, fragmentCollectionId, name, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status,
		int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByG_FCI_LikeN_S(
			groupId, fragmentCollectionId, name, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status,
		int start, int end, OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByG_FCI_LikeN_S.find(
				finderCache,
				new Object[] {groupId, fragmentCollectionId, name, status},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FCI_LikeN_S_First(
			long groupId, long fragmentCollectionId, String name, int status,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FCI_LikeN_S_First(
			groupId, fragmentCollectionId, name, status, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		throw new NoSuchEntryException(
			_collectionPersistenceFinderByG_FCI_LikeN_S.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, fragmentCollectionId, name, status}));
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FCI_LikeN_S_First(
		long groupId, long fragmentCollectionId, String name, int status,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return _collectionPersistenceFinderByG_FCI_LikeN_S.fetchFirst(
			finderCache,
			new Object[] {groupId, fragmentCollectionId, name, status},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63; from the database.
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
	 * Returns the number of fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByG_FCI_LikeN_S.count(
				finderCache,
				new Object[] {groupId, fragmentCollectionId, name, status});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_FCI_LikeN_S_Head;
	private FinderPath _finderPathWithPaginationCountByG_FCI_LikeN_S_Head;
	private CollectionPersistenceFinder<FragmentEntry>
		_collectionPersistenceFinderByG_FCI_LikeN_S_Head;

	/**
	 * Returns all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param head the head
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_LikeN_S_Head(
		long groupId, long fragmentCollectionId, String name, int status,
		boolean head) {

		return findByG_FCI_LikeN_S_Head(
			groupId, fragmentCollectionId, name, status, head,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_LikeN_S_Head(
		long groupId, long fragmentCollectionId, String name, int status,
		boolean head, int start, int end) {

		return findByG_FCI_LikeN_S_Head(
			groupId, fragmentCollectionId, name, status, head, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_LikeN_S_Head(
		long groupId, long fragmentCollectionId, String name, int status,
		boolean head, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByG_FCI_LikeN_S_Head(
			groupId, fragmentCollectionId, name, status, head, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_LikeN_S_Head(
		long groupId, long fragmentCollectionId, String name, int status,
		boolean head, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByG_FCI_LikeN_S_Head.find(
				finderCache,
				new Object[] {
					groupId, fragmentCollectionId, name, status, head
				},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FCI_LikeN_S_Head_First(
			long groupId, long fragmentCollectionId, String name, int status,
			boolean head, OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FCI_LikeN_S_Head_First(
			groupId, fragmentCollectionId, name, status, head,
			orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		throw new NoSuchEntryException(
			_collectionPersistenceFinderByG_FCI_LikeN_S_Head.
				buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {
						groupId, fragmentCollectionId, name, status, head
					}));
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FCI_LikeN_S_Head_First(
		long groupId, long fragmentCollectionId, String name, int status,
		boolean head, OrderByComparator<FragmentEntry> orderByComparator) {

		return _collectionPersistenceFinderByG_FCI_LikeN_S_Head.fetchFirst(
			finderCache,
			new Object[] {groupId, fragmentCollectionId, name, status, head},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param head the head
	 */
	@Override
	public void removeByG_FCI_LikeN_S_Head(
		long groupId, long fragmentCollectionId, String name, int status,
		boolean head) {

		_collectionPersistenceFinderByG_FCI_LikeN_S_Head.remove(
			finderCache,
			new Object[] {groupId, fragmentCollectionId, name, status, head});
	}

	/**
	 * Returns the number of fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param head the head
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByG_FCI_LikeN_S_Head(
		long groupId, long fragmentCollectionId, String name, int status,
		boolean head) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByG_FCI_LikeN_S_Head.count(
				finderCache,
				new Object[] {
					groupId, fragmentCollectionId, name, status, head
				});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_FCI_T_S;
	private FinderPath _finderPathWithoutPaginationFindByG_FCI_T_S;
	private FinderPath _finderPathCountByG_FCI_T_S;
	private CollectionPersistenceFinder<FragmentEntry>
		_collectionPersistenceFinderByG_FCI_T_S;

	/**
	 * Returns all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_T_S(
		long groupId, long fragmentCollectionId, int type, int status) {

		return findByG_FCI_T_S(
			groupId, fragmentCollectionId, type, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_T_S(
		long groupId, long fragmentCollectionId, int type, int status,
		int start, int end) {

		return findByG_FCI_T_S(
			groupId, fragmentCollectionId, type, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_T_S(
		long groupId, long fragmentCollectionId, int type, int status,
		int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByG_FCI_T_S(
			groupId, fragmentCollectionId, type, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_T_S(
		long groupId, long fragmentCollectionId, int type, int status,
		int start, int end, OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByG_FCI_T_S.find(
				finderCache,
				new Object[] {groupId, fragmentCollectionId, type, status},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FCI_T_S_First(
			long groupId, long fragmentCollectionId, int type, int status,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FCI_T_S_First(
			groupId, fragmentCollectionId, type, status, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		throw new NoSuchEntryException(
			_collectionPersistenceFinderByG_FCI_T_S.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, fragmentCollectionId, type, status}));
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FCI_T_S_First(
		long groupId, long fragmentCollectionId, int type, int status,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return _collectionPersistenceFinderByG_FCI_T_S.fetchFirst(
			finderCache,
			new Object[] {groupId, fragmentCollectionId, type, status},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; from the database.
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
	 * Returns the number of fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByG_FCI_T_S(
		long groupId, long fragmentCollectionId, int type, int status) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByG_FCI_T_S.count(
				finderCache,
				new Object[] {groupId, fragmentCollectionId, type, status});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_FCI_T_S_Head;
	private FinderPath _finderPathWithoutPaginationFindByG_FCI_T_S_Head;
	private FinderPath _finderPathCountByG_FCI_T_S_Head;
	private CollectionPersistenceFinder<FragmentEntry>
		_collectionPersistenceFinderByG_FCI_T_S_Head;

	/**
	 * Returns all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param head the head
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_T_S_Head(
		long groupId, long fragmentCollectionId, int type, int status,
		boolean head) {

		return findByG_FCI_T_S_Head(
			groupId, fragmentCollectionId, type, status, head,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_T_S_Head(
		long groupId, long fragmentCollectionId, int type, int status,
		boolean head, int start, int end) {

		return findByG_FCI_T_S_Head(
			groupId, fragmentCollectionId, type, status, head, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_T_S_Head(
		long groupId, long fragmentCollectionId, int type, int status,
		boolean head, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByG_FCI_T_S_Head(
			groupId, fragmentCollectionId, type, status, head, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_T_S_Head(
		long groupId, long fragmentCollectionId, int type, int status,
		boolean head, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByG_FCI_T_S_Head.find(
				finderCache,
				new Object[] {
					groupId, fragmentCollectionId, type, status, head
				},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FCI_T_S_Head_First(
			long groupId, long fragmentCollectionId, int type, int status,
			boolean head, OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FCI_T_S_Head_First(
			groupId, fragmentCollectionId, type, status, head,
			orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		throw new NoSuchEntryException(
			_collectionPersistenceFinderByG_FCI_T_S_Head.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {
					groupId, fragmentCollectionId, type, status, head
				}));
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FCI_T_S_Head_First(
		long groupId, long fragmentCollectionId, int type, int status,
		boolean head, OrderByComparator<FragmentEntry> orderByComparator) {

		return _collectionPersistenceFinderByG_FCI_T_S_Head.fetchFirst(
			finderCache,
			new Object[] {groupId, fragmentCollectionId, type, status, head},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param head the head
	 */
	@Override
	public void removeByG_FCI_T_S_Head(
		long groupId, long fragmentCollectionId, int type, int status,
		boolean head) {

		_collectionPersistenceFinderByG_FCI_T_S_Head.remove(
			finderCache,
			new Object[] {groupId, fragmentCollectionId, type, status, head});
	}

	/**
	 * Returns the number of fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param head the head
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByG_FCI_T_S_Head(
		long groupId, long fragmentCollectionId, int type, int status,
		boolean head) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByG_FCI_T_S_Head.count(
				finderCache,
				new Object[] {
					groupId, fragmentCollectionId, type, status, head
				});
		}
	}

	private FinderPath _finderPathWithPaginationFindByERC_G;
	private FinderPath _finderPathWithoutPaginationFindByERC_G;
	private FinderPath _finderPathCountByERC_G;
	private CollectionPersistenceFinder<FragmentEntry>
		_collectionPersistenceFinderByERC_G;

	/**
	 * Returns all the fragment entries where externalReferenceCode = &#63; and groupId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByERC_G(
		String externalReferenceCode, long groupId) {

		return findByERC_G(
			externalReferenceCode, groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where externalReferenceCode = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByERC_G(
		String externalReferenceCode, long groupId, int start, int end) {

		return findByERC_G(externalReferenceCode, groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where externalReferenceCode = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByERC_G(
		String externalReferenceCode, long groupId, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByERC_G(
			externalReferenceCode, groupId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where externalReferenceCode = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByERC_G(
		String externalReferenceCode, long groupId, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByERC_G.find(
				finderCache, new Object[] {externalReferenceCode, groupId},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry in the ordered set where externalReferenceCode = &#63; and groupId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByERC_G_First(
			String externalReferenceCode, long groupId,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByERC_G_First(
			externalReferenceCode, groupId, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		throw new NoSuchEntryException(
			_collectionPersistenceFinderByERC_G.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {externalReferenceCode, groupId}));
	}

	/**
	 * Returns the first fragment entry in the ordered set where externalReferenceCode = &#63; and groupId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByERC_G_First(
		String externalReferenceCode, long groupId,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return _collectionPersistenceFinderByERC_G.fetchFirst(
			finderCache, new Object[] {externalReferenceCode, groupId},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entries where externalReferenceCode = &#63; and groupId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 */
	@Override
	public void removeByERC_G(String externalReferenceCode, long groupId) {
		_collectionPersistenceFinderByERC_G.remove(
			finderCache, new Object[] {externalReferenceCode, groupId});
	}

	/**
	 * Returns the number of fragment entries where externalReferenceCode = &#63; and groupId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByERC_G(String externalReferenceCode, long groupId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _collectionPersistenceFinderByERC_G.count(
				finderCache, new Object[] {externalReferenceCode, groupId});
		}
	}

	private FinderPath _finderPathFetchByERC_G_Head;
	private UniquePersistenceFinder<FragmentEntry>
		_uniquePersistenceFinderByERC_G_Head;

	/**
	 * Returns the fragment entry where externalReferenceCode = &#63; and groupId = &#63; and head = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param head the head
	 * @return the matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByERC_G_Head(
			String externalReferenceCode, long groupId, boolean head)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByERC_G_Head(
			externalReferenceCode, groupId, head);

		if (fragmentEntry == null) {
			String message =
				_uniquePersistenceFinderByERC_G_Head.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {externalReferenceCode, groupId, head});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchEntryException(message);
		}

		return fragmentEntry;
	}

	/**
	 * Returns the fragment entry where externalReferenceCode = &#63; and groupId = &#63; and head = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param head the head
	 * @return the matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByERC_G_Head(
		String externalReferenceCode, long groupId, boolean head) {

		return fetchByERC_G_Head(externalReferenceCode, groupId, head, true);
	}

	/**
	 * Returns the fragment entry where externalReferenceCode = &#63; and groupId = &#63; and head = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param head the head
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByERC_G_Head(
		String externalReferenceCode, long groupId, boolean head,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _uniquePersistenceFinderByERC_G_Head.fetch(
				finderCache,
				new Object[] {externalReferenceCode, groupId, head},
				useFinderCache);
		}
	}

	/**
	 * Removes the fragment entry where externalReferenceCode = &#63; and groupId = &#63; and head = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param head the head
	 * @return the fragment entry that was removed
	 */
	@Override
	public FragmentEntry removeByERC_G_Head(
			String externalReferenceCode, long groupId, boolean head)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = findByERC_G_Head(
			externalReferenceCode, groupId, head);

		return remove(fragmentEntry);
	}

	/**
	 * Returns the number of fragment entries where externalReferenceCode = &#63; and groupId = &#63; and head = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param head the head
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByERC_G_Head(
		String externalReferenceCode, long groupId, boolean head) {

		return _uniquePersistenceFinderByERC_G_Head.count(
			finderCache, new Object[] {externalReferenceCode, groupId, head});
	}

	private FinderPath _finderPathFetchByHeadId;
	private UniquePersistenceFinder<FragmentEntry>
		_uniquePersistenceFinderByHeadId;

	/**
	 * Returns the fragment entry where headId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param headId the head ID
	 * @return the matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByHeadId(long headId) throws NoSuchEntryException {
		FragmentEntry fragmentEntry = fetchByHeadId(headId);

		if (fragmentEntry == null) {
			String message =
				_uniquePersistenceFinderByHeadId.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY, new Object[] {headId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchEntryException(message);
		}

		return fragmentEntry;
	}

	/**
	 * Returns the fragment entry where headId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param headId the head ID
	 * @return the matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByHeadId(long headId) {
		return fetchByHeadId(headId, true);
	}

	/**
	 * Returns the fragment entry where headId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param headId the head ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByHeadId(long headId, boolean useFinderCache) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntry.class)) {

			return _uniquePersistenceFinderByHeadId.fetch(
				finderCache, new Object[] {headId}, useFinderCache);
		}
	}

	/**
	 * Removes the fragment entry where headId = &#63; from the database.
	 *
	 * @param headId the head ID
	 * @return the fragment entry that was removed
	 */
	@Override
	public FragmentEntry removeByHeadId(long headId)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = findByHeadId(headId);

		return remove(fragmentEntry);
	}

	/**
	 * Returns the number of fragment entries where headId = &#63;.
	 *
	 * @param headId the head ID
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByHeadId(long headId) {
		return _uniquePersistenceFinderByHeadId.count(
			finderCache, new Object[] {headId});
	}

	public FragmentEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(FragmentEntry.class);

		setModelImplClass(FragmentEntryImpl.class);
		setModelPKClass(long.class);

		setTable(FragmentEntryTable.INSTANCE);
	}

	/**
	 * Caches the fragment entry in the entity cache if it is enabled.
	 *
	 * @param fragmentEntry the fragment entry
	 */
	@Override
	public void cacheResult(FragmentEntry fragmentEntry) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					fragmentEntry.getCtCollectionId())) {

			entityCache.putResult(
				FragmentEntryImpl.class, fragmentEntry.getPrimaryKey(),
				fragmentEntry);

			finderCache.putResult(
				_finderPathFetchByUUID_G_Head,
				new Object[] {
					fragmentEntry.getUuid(), fragmentEntry.getGroupId(),
					fragmentEntry.isHead()
				},
				fragmentEntry);

			finderCache.putResult(
				_finderPathFetchByG_FEK_Head,
				new Object[] {
					fragmentEntry.getGroupId(),
					fragmentEntry.getFragmentEntryKey(), fragmentEntry.isHead()
				},
				fragmentEntry);

			finderCache.putResult(
				_finderPathFetchByERC_G_Head,
				new Object[] {
					fragmentEntry.getExternalReferenceCode(),
					fragmentEntry.getGroupId(), fragmentEntry.isHead()
				},
				fragmentEntry);

			finderCache.putResult(
				_finderPathFetchByHeadId,
				new Object[] {fragmentEntry.getHeadId()}, fragmentEntry);
		}
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the fragment entries in the entity cache if it is enabled.
	 *
	 * @param fragmentEntries the fragment entries
	 */
	@Override
	public void cacheResult(List<FragmentEntry> fragmentEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (fragmentEntries.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (FragmentEntry fragmentEntry : fragmentEntries) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						fragmentEntry.getCtCollectionId())) {

				if (entityCache.getResult(
						FragmentEntryImpl.class,
						fragmentEntry.getPrimaryKey()) == null) {

					cacheResult(fragmentEntry);
				}
			}
		}
	}

	protected void cacheUniqueFindersCache(
		FragmentEntryModelImpl fragmentEntryModelImpl) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					fragmentEntryModelImpl.getCtCollectionId())) {

			Object[] args = new Object[] {
				fragmentEntryModelImpl.getUuid(),
				fragmentEntryModelImpl.getGroupId(),
				fragmentEntryModelImpl.isHead()
			};

			finderCache.putResult(
				_finderPathFetchByUUID_G_Head, args, fragmentEntryModelImpl);

			args = new Object[] {
				fragmentEntryModelImpl.getGroupId(),
				fragmentEntryModelImpl.getFragmentEntryKey(),
				fragmentEntryModelImpl.isHead()
			};

			finderCache.putResult(
				_finderPathFetchByG_FEK_Head, args, fragmentEntryModelImpl);

			args = new Object[] {
				fragmentEntryModelImpl.getExternalReferenceCode(),
				fragmentEntryModelImpl.getGroupId(),
				fragmentEntryModelImpl.isHead()
			};

			finderCache.putResult(
				_finderPathFetchByERC_G_Head, args, fragmentEntryModelImpl);

			args = new Object[] {fragmentEntryModelImpl.getHeadId()};

			finderCache.putResult(
				_finderPathFetchByHeadId, args, fragmentEntryModelImpl);
		}
	}

	/**
	 * Creates a new fragment entry with the primary key. Does not add the fragment entry to the database.
	 *
	 * @param fragmentEntryId the primary key for the new fragment entry
	 * @return the new fragment entry
	 */
	@Override
	public FragmentEntry create(long fragmentEntryId) {
		FragmentEntry fragmentEntry = new FragmentEntryImpl();

		fragmentEntry.setNew(true);
		fragmentEntry.setPrimaryKey(fragmentEntryId);

		String uuid = PortalUUIDUtil.generate();

		fragmentEntry.setUuid(uuid);

		fragmentEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return fragmentEntry;
	}

	/**
	 * Removes the fragment entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fragmentEntryId the primary key of the fragment entry
	 * @return the fragment entry that was removed
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	@Override
	public FragmentEntry remove(long fragmentEntryId)
		throws NoSuchEntryException {

		return remove((Serializable)fragmentEntryId);
	}

	@Override
	protected FragmentEntry removeImpl(FragmentEntry fragmentEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(fragmentEntry)) {
				fragmentEntry = (FragmentEntry)session.get(
					FragmentEntryImpl.class, fragmentEntry.getPrimaryKeyObj());
			}

			if ((fragmentEntry != null) &&
				ctPersistenceHelper.isRemove(fragmentEntry)) {

				session.delete(fragmentEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (fragmentEntry != null) {
			clearCache(fragmentEntry);
		}

		return fragmentEntry;
	}

	@Override
	public FragmentEntry updateImpl(FragmentEntry fragmentEntry) {
		boolean isNew = fragmentEntry.isNew();

		if (!(fragmentEntry instanceof FragmentEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(fragmentEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					fragmentEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in fragmentEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom FragmentEntry implementation " +
					fragmentEntry.getClass());
		}

		FragmentEntryModelImpl fragmentEntryModelImpl =
			(FragmentEntryModelImpl)fragmentEntry;

		if (Validator.isNull(fragmentEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			fragmentEntry.setUuid(uuid);
		}

		if (Validator.isNull(fragmentEntry.getExternalReferenceCode())) {
			fragmentEntry.setExternalReferenceCode(fragmentEntry.getUuid());
		}
		else {
			if (!Objects.equals(
					fragmentEntryModelImpl.getColumnOriginalValue(
						"externalReferenceCode"),
					fragmentEntry.getExternalReferenceCode())) {

				long userId = GetterUtil.getLong(
					PrincipalThreadLocal.getName());

				if (userId > 0) {
					long companyId = fragmentEntry.getCompanyId();

					long groupId = fragmentEntry.getGroupId();

					long classPK = 0;

					if (!isNew) {
						classPK = fragmentEntry.getPrimaryKey();
					}

					try {
						fragmentEntry.setExternalReferenceCode(
							SanitizerUtil.sanitize(
								companyId, groupId, userId,
								FragmentEntry.class.getName(), classPK,
								ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL,
								fragmentEntry.getExternalReferenceCode(),
								null));
					}
					catch (SanitizerException sanitizerException) {
						throw new SystemException(sanitizerException);
					}
				}
			}

			FragmentEntry ercFragmentEntry = fetchByERC_G_Head(
				fragmentEntry.getExternalReferenceCode(),
				fragmentEntry.getGroupId(), fragmentEntry.isHead());

			if (isNew) {
				if (ercFragmentEntry != null) {
					throw new DuplicateFragmentEntryExternalReferenceCodeException(
						"Duplicate fragment entry with external reference code " +
							fragmentEntry.getExternalReferenceCode() +
								" and group " + fragmentEntry.getGroupId());
				}
			}
			else {
				if ((ercFragmentEntry != null) &&
					(fragmentEntry.getFragmentEntryId() !=
						ercFragmentEntry.getFragmentEntryId())) {

					throw new DuplicateFragmentEntryExternalReferenceCodeException(
						"Duplicate fragment entry with external reference code " +
							fragmentEntry.getExternalReferenceCode() +
								" and group " + fragmentEntry.getGroupId());
				}
			}
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (fragmentEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				fragmentEntry.setCreateDate(date);
			}
			else {
				fragmentEntry.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!fragmentEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				fragmentEntry.setModifiedDate(date);
			}
			else {
				fragmentEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(fragmentEntry)) {
				if (!isNew) {
					session.evict(
						FragmentEntryImpl.class,
						fragmentEntry.getPrimaryKeyObj());
				}

				session.save(fragmentEntry);
			}
			else {
				fragmentEntry = (FragmentEntry)session.merge(fragmentEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			FragmentEntryImpl.class, fragmentEntryModelImpl, false, true);

		cacheUniqueFindersCache(fragmentEntryModelImpl);

		if (isNew) {
			fragmentEntry.setNew(false);
		}

		fragmentEntry.resetOriginalValues();

		return fragmentEntry;
	}

	/**
	 * Returns the fragment entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param fragmentEntryId the primary key of the fragment entry
	 * @return the fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	@Override
	public FragmentEntry findByPrimaryKey(long fragmentEntryId)
		throws NoSuchEntryException {

		return findByPrimaryKey((Serializable)fragmentEntryId);
	}

	@Override
	protected CTPersistenceHelper getCTPersistenceHelper() {
		return ctPersistenceHelper;
	}

	/**
	 * Returns the fragment entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fragmentEntryId the primary key of the fragment entry
	 * @return the fragment entry, or <code>null</code> if a fragment entry with the primary key could not be found
	 */
	@Override
	public FragmentEntry fetchByPrimaryKey(long fragmentEntryId) {
		return fetchByPrimaryKey((Serializable)fragmentEntryId);
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
		return "fragmentEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_FRAGMENTENTRY;
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
		return FragmentEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "FragmentEntry";
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
		ctStrictColumnNames.add("headId");
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
			Collections.singleton("fragmentEntryId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId", "head"});

		_uniqueIndexColumnNames.add(
			new String[] {"groupId", "fragmentEntryKey", "head"});

		_uniqueIndexColumnNames.add(
			new String[] {"externalReferenceCode", "groupId", "head"});

		_uniqueIndexColumnNames.add(new String[] {"headId"});
	}

	/**
	 * Initializes the fragment entry persistence.
	 */
	@Activate
	public void activate() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

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
			_SQL_SELECT_FRAGMENTENTRY_WHERE, _SQL_COUNT_FRAGMENTENTRY_WHERE,
			FragmentEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
			new FinderColumn<>(
				"fragmentEntry.", "uuid", FinderColumn.Type.STRING, "=", true,
				true, FragmentEntry::getUuid));

		_finderPathWithPaginationFindByUuid_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_Head",
			new String[] {
				String.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "head"}, true);

		_finderPathWithoutPaginationFindByUuid_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_Head",
			new String[] {String.class.getName(), Boolean.class.getName()},
			new String[] {"uuid_", "head"}, true);

		_finderPathCountByUuid_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_Head",
			new String[] {String.class.getName(), Boolean.class.getName()},
			new String[] {"uuid_", "head"}, false);

		_collectionPersistenceFinderByUuid_Head =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByUuid_Head,
				_finderPathWithoutPaginationFindByUuid_Head,
				_finderPathCountByUuid_Head, _SQL_SELECT_FRAGMENTENTRY_WHERE,
				_SQL_COUNT_FRAGMENTENTRY_WHERE,
				FragmentEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"fragmentEntry.", "uuid", FinderColumn.Type.STRING, "=",
					true, false, FragmentEntry::getUuid),
				new FinderColumn<>(
					"fragmentEntry.", "head", FinderColumn.Type.BOOLEAN, "=",
					true, true, FragmentEntry::isHead));

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
				_finderPathCountByUUID_G, _SQL_SELECT_FRAGMENTENTRY_WHERE,
				_SQL_COUNT_FRAGMENTENTRY_WHERE,
				FragmentEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"fragmentEntry.", "uuid", FinderColumn.Type.STRING, "=",
					true, false, FragmentEntry::getUuid),
				new FinderColumn<>(
					"fragmentEntry.", "groupId", FinderColumn.Type.LONG, "=",
					true, true, FragmentEntry::getGroupId));

		_finderPathFetchByUUID_G_Head = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G_Head",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"uuid_", "groupId", "head"}, true);

		_uniquePersistenceFinderByUUID_G_Head = new UniquePersistenceFinder<>(
			this, _finderPathFetchByUUID_G_Head,
			_SQL_SELECT_FRAGMENTENTRY_WHERE,
			new FinderColumn<>(
				"fragmentEntry.", "uuid", FinderColumn.Type.STRING, "=", true,
				false, FragmentEntry::getUuid),
			new FinderColumn<>(
				"fragmentEntry.", "groupId", FinderColumn.Type.LONG, "=", true,
				false, FragmentEntry::getGroupId),
			new FinderColumn<>(
				"fragmentEntry.", "head", FinderColumn.Type.BOOLEAN, "=", true,
				true, FragmentEntry::isHead));

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
				_finderPathCountByUuid_C, _SQL_SELECT_FRAGMENTENTRY_WHERE,
				_SQL_COUNT_FRAGMENTENTRY_WHERE,
				FragmentEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"fragmentEntry.", "uuid", FinderColumn.Type.STRING, "=",
					true, false, FragmentEntry::getUuid),
				new FinderColumn<>(
					"fragmentEntry.", "companyId", FinderColumn.Type.LONG, "=",
					true, true, FragmentEntry::getCompanyId));

		_finderPathWithPaginationFindByUuid_C_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C_Head",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "companyId", "head"}, true);

		_finderPathWithoutPaginationFindByUuid_C_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C_Head",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"uuid_", "companyId", "head"}, true);

		_finderPathCountByUuid_C_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C_Head",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"uuid_", "companyId", "head"}, false);

		_collectionPersistenceFinderByUuid_C_Head =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByUuid_C_Head,
				_finderPathWithoutPaginationFindByUuid_C_Head,
				_finderPathCountByUuid_C_Head, _SQL_SELECT_FRAGMENTENTRY_WHERE,
				_SQL_COUNT_FRAGMENTENTRY_WHERE,
				FragmentEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"fragmentEntry.", "uuid", FinderColumn.Type.STRING, "=",
					true, false, FragmentEntry::getUuid),
				new FinderColumn<>(
					"fragmentEntry.", "companyId", FinderColumn.Type.LONG, "=",
					true, false, FragmentEntry::getCompanyId),
				new FinderColumn<>(
					"fragmentEntry.", "head", FinderColumn.Type.BOOLEAN, "=",
					true, true, FragmentEntry::isHead));

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
				_finderPathCountByGroupId, _SQL_SELECT_FRAGMENTENTRY_WHERE,
				_SQL_COUNT_FRAGMENTENTRY_WHERE,
				FragmentEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"fragmentEntry.", "groupId", FinderColumn.Type.LONG, "=",
					true, true, FragmentEntry::getGroupId));

		_finderPathWithPaginationFindByGroupId_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId_Head",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "head"}, true);

		_finderPathWithoutPaginationFindByGroupId_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId_Head",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"groupId", "head"}, true);

		_finderPathCountByGroupId_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId_Head",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"groupId", "head"}, false);

		_collectionPersistenceFinderByGroupId_Head =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByGroupId_Head,
				_finderPathWithoutPaginationFindByGroupId_Head,
				_finderPathCountByGroupId_Head, _SQL_SELECT_FRAGMENTENTRY_WHERE,
				_SQL_COUNT_FRAGMENTENTRY_WHERE,
				FragmentEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"fragmentEntry.", "groupId", FinderColumn.Type.LONG, "=",
					true, false, FragmentEntry::getGroupId),
				new FinderColumn<>(
					"fragmentEntry.", "head", FinderColumn.Type.BOOLEAN, "=",
					true, true, FragmentEntry::isHead));

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
				_SQL_SELECT_FRAGMENTENTRY_WHERE, _SQL_COUNT_FRAGMENTENTRY_WHERE,
				FragmentEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"fragmentEntry.", "fragmentCollectionId",
					FinderColumn.Type.LONG, "=", true, true,
					FragmentEntry::getFragmentCollectionId));

		_finderPathWithPaginationFindByFragmentCollectionId_Head =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByFragmentCollectionId_Head",
				new String[] {
					Long.class.getName(), Boolean.class.getName(),
					Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				},
				new String[] {"fragmentCollectionId", "head"}, true);

		_finderPathWithoutPaginationFindByFragmentCollectionId_Head =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByFragmentCollectionId_Head",
				new String[] {Long.class.getName(), Boolean.class.getName()},
				new String[] {"fragmentCollectionId", "head"}, true);

		_finderPathCountByFragmentCollectionId_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByFragmentCollectionId_Head",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"fragmentCollectionId", "head"}, false);

		_collectionPersistenceFinderByFragmentCollectionId_Head =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByFragmentCollectionId_Head,
				_finderPathWithoutPaginationFindByFragmentCollectionId_Head,
				_finderPathCountByFragmentCollectionId_Head,
				_SQL_SELECT_FRAGMENTENTRY_WHERE, _SQL_COUNT_FRAGMENTENTRY_WHERE,
				FragmentEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"fragmentEntry.", "fragmentCollectionId",
					FinderColumn.Type.LONG, "=", true, false,
					FragmentEntry::getFragmentCollectionId),
				new FinderColumn<>(
					"fragmentEntry.", "head", FinderColumn.Type.BOOLEAN, "=",
					true, true, FragmentEntry::isHead));

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
			_SQL_SELECT_FRAGMENTENTRY_WHERE, _SQL_COUNT_FRAGMENTENTRY_WHERE,
			FragmentEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
			new FinderColumn<>(
				"fragmentEntry.", "type", FinderColumn.Type.INTEGER, "=", true,
				true, FragmentEntry::getType));

		_finderPathWithPaginationFindByType_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByType_Head",
			new String[] {
				Integer.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"type_", "head"}, true);

		_finderPathWithoutPaginationFindByType_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByType_Head",
			new String[] {Integer.class.getName(), Boolean.class.getName()},
			new String[] {"type_", "head"}, true);

		_finderPathCountByType_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByType_Head",
			new String[] {Integer.class.getName(), Boolean.class.getName()},
			new String[] {"type_", "head"}, false);

		_collectionPersistenceFinderByType_Head =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByType_Head,
				_finderPathWithoutPaginationFindByType_Head,
				_finderPathCountByType_Head, _SQL_SELECT_FRAGMENTENTRY_WHERE,
				_SQL_COUNT_FRAGMENTENTRY_WHERE,
				FragmentEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"fragmentEntry.", "type", FinderColumn.Type.INTEGER, "=",
					true, false, FragmentEntry::getType),
				new FinderColumn<>(
					"fragmentEntry.", "head", FinderColumn.Type.BOOLEAN, "=",
					true, true, FragmentEntry::isHead));

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
			_SQL_SELECT_FRAGMENTENTRY_WHERE, _SQL_COUNT_FRAGMENTENTRY_WHERE,
			FragmentEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
			new FinderColumn<>(
				"fragmentEntry.", "groupId", FinderColumn.Type.LONG, "=", true,
				false, FragmentEntry::getGroupId),
			new FinderColumn<>(
				"fragmentEntry.", "fragmentCollectionId",
				FinderColumn.Type.LONG, "=", true, true,
				FragmentEntry::getFragmentCollectionId));

		_finderPathWithPaginationFindByG_FCI_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_FCI_Head",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "fragmentCollectionId", "head"}, true);

		_finderPathWithoutPaginationFindByG_FCI_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_FCI_Head",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"groupId", "fragmentCollectionId", "head"}, true);

		_finderPathCountByG_FCI_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_FCI_Head",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"groupId", "fragmentCollectionId", "head"}, false);

		_collectionPersistenceFinderByG_FCI_Head =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_FCI_Head,
				_finderPathWithoutPaginationFindByG_FCI_Head,
				_finderPathCountByG_FCI_Head, _SQL_SELECT_FRAGMENTENTRY_WHERE,
				_SQL_COUNT_FRAGMENTENTRY_WHERE,
				FragmentEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"fragmentEntry.", "groupId", FinderColumn.Type.LONG, "=",
					true, false, FragmentEntry::getGroupId),
				new FinderColumn<>(
					"fragmentEntry.", "fragmentCollectionId",
					FinderColumn.Type.LONG, "=", true, false,
					FragmentEntry::getFragmentCollectionId),
				new FinderColumn<>(
					"fragmentEntry.", "head", FinderColumn.Type.BOOLEAN, "=",
					true, true, FragmentEntry::isHead));

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
			_SQL_SELECT_FRAGMENTENTRY_WHERE, _SQL_COUNT_FRAGMENTENTRY_WHERE,
			FragmentEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
			new FinderColumn<>(
				"fragmentEntry.", "groupId", FinderColumn.Type.LONG, "=", true,
				false, FragmentEntry::getGroupId),
			new FinderColumn<>(
				"fragmentEntry.", "fragmentEntryKey", FinderColumn.Type.STRING,
				"=", true, true, FragmentEntry::getFragmentEntryKey));

		_finderPathFetchByG_FEK_Head = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByG_FEK_Head",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"groupId", "fragmentEntryKey", "head"}, true);

		_uniquePersistenceFinderByG_FEK_Head = new UniquePersistenceFinder<>(
			this, _finderPathFetchByG_FEK_Head, _SQL_SELECT_FRAGMENTENTRY_WHERE,
			new FinderColumn<>(
				"fragmentEntry.", "groupId", FinderColumn.Type.LONG, "=", true,
				false, FragmentEntry::getGroupId),
			new FinderColumn<>(
				"fragmentEntry.", "fragmentEntryKey", FinderColumn.Type.STRING,
				"=", true, false, FragmentEntry::getFragmentEntryKey),
			new FinderColumn<>(
				"fragmentEntry.", "head", FinderColumn.Type.BOOLEAN, "=", true,
				true, FragmentEntry::isHead));

		_finderPathWithPaginationFindByG_FCI_LikeN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_FCI_LikeN",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "fragmentCollectionId", "name"}, true);

		_finderPathWithPaginationCountByG_FCI_LikeN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_FCI_LikeN",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			new String[] {"groupId", "fragmentCollectionId", "name"}, false);

		_collectionPersistenceFinderByG_FCI_LikeN =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_FCI_LikeN, null,
				_finderPathWithPaginationCountByG_FCI_LikeN,
				_SQL_SELECT_FRAGMENTENTRY_WHERE, _SQL_COUNT_FRAGMENTENTRY_WHERE,
				FragmentEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"fragmentEntry.", "groupId", FinderColumn.Type.LONG, "=",
					true, false, FragmentEntry::getGroupId),
				new FinderColumn<>(
					"fragmentEntry.", "fragmentCollectionId",
					FinderColumn.Type.LONG, "=", true, false,
					FragmentEntry::getFragmentCollectionId),
				new FinderColumn<>(
					"fragmentEntry.", "name", FinderColumn.Type.STRING, "LIKE",
					true, true, FragmentEntry::getName));

		_finderPathWithPaginationFindByG_FCI_LikeN_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_FCI_LikeN_Head",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "fragmentCollectionId", "name", "head"},
			true);

		_finderPathWithPaginationCountByG_FCI_LikeN_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_FCI_LikeN_Head",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Boolean.class.getName()
			},
			new String[] {"groupId", "fragmentCollectionId", "name", "head"},
			false);

		_collectionPersistenceFinderByG_FCI_LikeN_Head =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_FCI_LikeN_Head, null,
				_finderPathWithPaginationCountByG_FCI_LikeN_Head,
				_SQL_SELECT_FRAGMENTENTRY_WHERE, _SQL_COUNT_FRAGMENTENTRY_WHERE,
				FragmentEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"fragmentEntry.", "groupId", FinderColumn.Type.LONG, "=",
					true, false, FragmentEntry::getGroupId),
				new FinderColumn<>(
					"fragmentEntry.", "fragmentCollectionId",
					FinderColumn.Type.LONG, "=", true, false,
					FragmentEntry::getFragmentCollectionId),
				new FinderColumn<>(
					"fragmentEntry.", "name", FinderColumn.Type.STRING, "LIKE",
					true, false, FragmentEntry::getName),
				new FinderColumn<>(
					"fragmentEntry.", "head", FinderColumn.Type.BOOLEAN, "=",
					true, true, FragmentEntry::isHead));

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
				_finderPathCountByG_FCI_T, _SQL_SELECT_FRAGMENTENTRY_WHERE,
				_SQL_COUNT_FRAGMENTENTRY_WHERE,
				FragmentEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"fragmentEntry.", "groupId", FinderColumn.Type.LONG, "=",
					true, false, FragmentEntry::getGroupId),
				new FinderColumn<>(
					"fragmentEntry.", "fragmentCollectionId",
					FinderColumn.Type.LONG, "=", true, false,
					FragmentEntry::getFragmentCollectionId),
				new FinderColumn<>(
					"fragmentEntry.", "type", FinderColumn.Type.INTEGER, "=",
					true, true, FragmentEntry::getType));

		_finderPathWithPaginationFindByG_FCI_T_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_FCI_T_Head",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "fragmentCollectionId", "type_", "head"},
			true);

		_finderPathWithoutPaginationFindByG_FCI_T_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_FCI_T_Head",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Boolean.class.getName()
			},
			new String[] {"groupId", "fragmentCollectionId", "type_", "head"},
			true);

		_finderPathCountByG_FCI_T_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_FCI_T_Head",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Boolean.class.getName()
			},
			new String[] {"groupId", "fragmentCollectionId", "type_", "head"},
			false);

		_collectionPersistenceFinderByG_FCI_T_Head =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_FCI_T_Head,
				_finderPathWithoutPaginationFindByG_FCI_T_Head,
				_finderPathCountByG_FCI_T_Head, _SQL_SELECT_FRAGMENTENTRY_WHERE,
				_SQL_COUNT_FRAGMENTENTRY_WHERE,
				FragmentEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"fragmentEntry.", "groupId", FinderColumn.Type.LONG, "=",
					true, false, FragmentEntry::getGroupId),
				new FinderColumn<>(
					"fragmentEntry.", "fragmentCollectionId",
					FinderColumn.Type.LONG, "=", true, false,
					FragmentEntry::getFragmentCollectionId),
				new FinderColumn<>(
					"fragmentEntry.", "type", FinderColumn.Type.INTEGER, "=",
					true, false, FragmentEntry::getType),
				new FinderColumn<>(
					"fragmentEntry.", "head", FinderColumn.Type.BOOLEAN, "=",
					true, true, FragmentEntry::isHead));

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
				_finderPathCountByG_FCI_S, _SQL_SELECT_FRAGMENTENTRY_WHERE,
				_SQL_COUNT_FRAGMENTENTRY_WHERE,
				FragmentEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"fragmentEntry.", "groupId", FinderColumn.Type.LONG, "=",
					true, false, FragmentEntry::getGroupId),
				new FinderColumn<>(
					"fragmentEntry.", "fragmentCollectionId",
					FinderColumn.Type.LONG, "=", true, false,
					FragmentEntry::getFragmentCollectionId),
				new FinderColumn<>(
					"fragmentEntry.", "status", FinderColumn.Type.INTEGER, "=",
					true, true, FragmentEntry::getStatus));

		_finderPathWithPaginationFindByG_FCI_S_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_FCI_S_Head",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "fragmentCollectionId", "status", "head"},
			true);

		_finderPathWithoutPaginationFindByG_FCI_S_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_FCI_S_Head",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Boolean.class.getName()
			},
			new String[] {"groupId", "fragmentCollectionId", "status", "head"},
			true);

		_finderPathCountByG_FCI_S_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_FCI_S_Head",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Boolean.class.getName()
			},
			new String[] {"groupId", "fragmentCollectionId", "status", "head"},
			false);

		_collectionPersistenceFinderByG_FCI_S_Head =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_FCI_S_Head,
				_finderPathWithoutPaginationFindByG_FCI_S_Head,
				_finderPathCountByG_FCI_S_Head, _SQL_SELECT_FRAGMENTENTRY_WHERE,
				_SQL_COUNT_FRAGMENTENTRY_WHERE,
				FragmentEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"fragmentEntry.", "groupId", FinderColumn.Type.LONG, "=",
					true, false, FragmentEntry::getGroupId),
				new FinderColumn<>(
					"fragmentEntry.", "fragmentCollectionId",
					FinderColumn.Type.LONG, "=", true, false,
					FragmentEntry::getFragmentCollectionId),
				new FinderColumn<>(
					"fragmentEntry.", "status", FinderColumn.Type.INTEGER, "=",
					true, false, FragmentEntry::getStatus),
				new FinderColumn<>(
					"fragmentEntry.", "head", FinderColumn.Type.BOOLEAN, "=",
					true, true, FragmentEntry::isHead));

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

		_finderPathWithPaginationCountByG_FCI_LikeN_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_FCI_LikeN_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName()
			},
			new String[] {"groupId", "fragmentCollectionId", "name", "status"},
			false);

		_collectionPersistenceFinderByG_FCI_LikeN_S =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_FCI_LikeN_S, null,
				_finderPathWithPaginationCountByG_FCI_LikeN_S,
				_SQL_SELECT_FRAGMENTENTRY_WHERE, _SQL_COUNT_FRAGMENTENTRY_WHERE,
				FragmentEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"fragmentEntry.", "groupId", FinderColumn.Type.LONG, "=",
					true, false, FragmentEntry::getGroupId),
				new FinderColumn<>(
					"fragmentEntry.", "fragmentCollectionId",
					FinderColumn.Type.LONG, "=", true, false,
					FragmentEntry::getFragmentCollectionId),
				new FinderColumn<>(
					"fragmentEntry.", "name", FinderColumn.Type.STRING, "LIKE",
					true, false, FragmentEntry::getName),
				new FinderColumn<>(
					"fragmentEntry.", "status", FinderColumn.Type.INTEGER, "=",
					true, true, FragmentEntry::getStatus));

		_finderPathWithPaginationFindByG_FCI_LikeN_S_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_FCI_LikeN_S_Head",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {
				"groupId", "fragmentCollectionId", "name", "status", "head"
			},
			true);

		_finderPathWithPaginationCountByG_FCI_LikeN_S_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_FCI_LikeN_S_Head",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Boolean.class.getName()
			},
			new String[] {
				"groupId", "fragmentCollectionId", "name", "status", "head"
			},
			false);

		_collectionPersistenceFinderByG_FCI_LikeN_S_Head =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_FCI_LikeN_S_Head, null,
				_finderPathWithPaginationCountByG_FCI_LikeN_S_Head,
				_SQL_SELECT_FRAGMENTENTRY_WHERE, _SQL_COUNT_FRAGMENTENTRY_WHERE,
				FragmentEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"fragmentEntry.", "groupId", FinderColumn.Type.LONG, "=",
					true, false, FragmentEntry::getGroupId),
				new FinderColumn<>(
					"fragmentEntry.", "fragmentCollectionId",
					FinderColumn.Type.LONG, "=", true, false,
					FragmentEntry::getFragmentCollectionId),
				new FinderColumn<>(
					"fragmentEntry.", "name", FinderColumn.Type.STRING, "LIKE",
					true, false, FragmentEntry::getName),
				new FinderColumn<>(
					"fragmentEntry.", "status", FinderColumn.Type.INTEGER, "=",
					true, false, FragmentEntry::getStatus),
				new FinderColumn<>(
					"fragmentEntry.", "head", FinderColumn.Type.BOOLEAN, "=",
					true, true, FragmentEntry::isHead));

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
				_finderPathCountByG_FCI_T_S, _SQL_SELECT_FRAGMENTENTRY_WHERE,
				_SQL_COUNT_FRAGMENTENTRY_WHERE,
				FragmentEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"fragmentEntry.", "groupId", FinderColumn.Type.LONG, "=",
					true, false, FragmentEntry::getGroupId),
				new FinderColumn<>(
					"fragmentEntry.", "fragmentCollectionId",
					FinderColumn.Type.LONG, "=", true, false,
					FragmentEntry::getFragmentCollectionId),
				new FinderColumn<>(
					"fragmentEntry.", "type", FinderColumn.Type.INTEGER, "=",
					true, false, FragmentEntry::getType),
				new FinderColumn<>(
					"fragmentEntry.", "status", FinderColumn.Type.INTEGER, "=",
					true, true, FragmentEntry::getStatus));

		_finderPathWithPaginationFindByG_FCI_T_S_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_FCI_T_S_Head",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {
				"groupId", "fragmentCollectionId", "type_", "status", "head"
			},
			true);

		_finderPathWithoutPaginationFindByG_FCI_T_S_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_FCI_T_S_Head",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Boolean.class.getName()
			},
			new String[] {
				"groupId", "fragmentCollectionId", "type_", "status", "head"
			},
			true);

		_finderPathCountByG_FCI_T_S_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_FCI_T_S_Head",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Boolean.class.getName()
			},
			new String[] {
				"groupId", "fragmentCollectionId", "type_", "status", "head"
			},
			false);

		_collectionPersistenceFinderByG_FCI_T_S_Head =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_FCI_T_S_Head,
				_finderPathWithoutPaginationFindByG_FCI_T_S_Head,
				_finderPathCountByG_FCI_T_S_Head,
				_SQL_SELECT_FRAGMENTENTRY_WHERE, _SQL_COUNT_FRAGMENTENTRY_WHERE,
				FragmentEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"fragmentEntry.", "groupId", FinderColumn.Type.LONG, "=",
					true, false, FragmentEntry::getGroupId),
				new FinderColumn<>(
					"fragmentEntry.", "fragmentCollectionId",
					FinderColumn.Type.LONG, "=", true, false,
					FragmentEntry::getFragmentCollectionId),
				new FinderColumn<>(
					"fragmentEntry.", "type", FinderColumn.Type.INTEGER, "=",
					true, false, FragmentEntry::getType),
				new FinderColumn<>(
					"fragmentEntry.", "status", FinderColumn.Type.INTEGER, "=",
					true, false, FragmentEntry::getStatus),
				new FinderColumn<>(
					"fragmentEntry.", "head", FinderColumn.Type.BOOLEAN, "=",
					true, true, FragmentEntry::isHead));

		_finderPathWithPaginationFindByERC_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByERC_G",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"externalReferenceCode", "groupId"}, true);

		_finderPathWithoutPaginationFindByERC_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByERC_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"externalReferenceCode", "groupId"}, true);

		_finderPathCountByERC_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByERC_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"externalReferenceCode", "groupId"}, false);

		_collectionPersistenceFinderByERC_G = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByERC_G,
			_finderPathWithoutPaginationFindByERC_G, _finderPathCountByERC_G,
			_SQL_SELECT_FRAGMENTENTRY_WHERE, _SQL_COUNT_FRAGMENTENTRY_WHERE,
			FragmentEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
			new FinderColumn<>(
				"fragmentEntry.", "externalReferenceCode",
				FinderColumn.Type.STRING, "=", true, false,
				FragmentEntry::getExternalReferenceCode),
			new FinderColumn<>(
				"fragmentEntry.", "groupId", FinderColumn.Type.LONG, "=", true,
				true, FragmentEntry::getGroupId));

		_finderPathFetchByERC_G_Head = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByERC_G_Head",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"externalReferenceCode", "groupId", "head"}, true);

		_uniquePersistenceFinderByERC_G_Head = new UniquePersistenceFinder<>(
			this, _finderPathFetchByERC_G_Head, _SQL_SELECT_FRAGMENTENTRY_WHERE,
			new FinderColumn<>(
				"fragmentEntry.", "externalReferenceCode",
				FinderColumn.Type.STRING, "=", true, false,
				FragmentEntry::getExternalReferenceCode),
			new FinderColumn<>(
				"fragmentEntry.", "groupId", FinderColumn.Type.LONG, "=", true,
				false, FragmentEntry::getGroupId),
			new FinderColumn<>(
				"fragmentEntry.", "head", FinderColumn.Type.BOOLEAN, "=", true,
				true, FragmentEntry::isHead));

		_finderPathFetchByHeadId = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByHeadId",
			new String[] {Long.class.getName()}, new String[] {"headId"}, true);

		_uniquePersistenceFinderByHeadId = new UniquePersistenceFinder<>(
			this, _finderPathFetchByHeadId, _SQL_SELECT_FRAGMENTENTRY_WHERE,
			new FinderColumn<>(
				"fragmentEntry.", "headId", FinderColumn.Type.LONG, "=", true,
				true, FragmentEntry::getHeadId));

		FragmentEntryUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		FragmentEntryUtil.setPersistence(null);

		entityCache.removeCache(FragmentEntryImpl.class.getName());
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

	private static final String _ENTITY_ALIAS_PREFIX =
		FragmentEntryModelImpl.ENTITY_ALIAS + ".";

	private static final String _SQL_SELECT_FRAGMENTENTRY =
		"SELECT fragmentEntry FROM FragmentEntry fragmentEntry";

	private static final String _SQL_SELECT_FRAGMENTENTRY_WHERE =
		"SELECT fragmentEntry FROM FragmentEntry fragmentEntry WHERE ";

	private static final String _SQL_COUNT_FRAGMENTENTRY_WHERE =
		"SELECT COUNT(fragmentEntry) FROM FragmentEntry fragmentEntry WHERE ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No FragmentEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "type"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1012972025