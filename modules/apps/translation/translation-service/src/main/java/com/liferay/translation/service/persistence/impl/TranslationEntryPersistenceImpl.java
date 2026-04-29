/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.service.persistence.impl;

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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
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
import com.liferay.translation.exception.NoSuchEntryException;
import com.liferay.translation.model.TranslationEntry;
import com.liferay.translation.model.TranslationEntryTable;
import com.liferay.translation.model.impl.TranslationEntryImpl;
import com.liferay.translation.model.impl.TranslationEntryModelImpl;
import com.liferay.translation.service.persistence.TranslationEntryPersistence;
import com.liferay.translation.service.persistence.TranslationEntryUtil;
import com.liferay.translation.service.persistence.impl.constants.TranslationPersistenceConstants;

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
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the translation entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = TranslationEntryPersistence.class)
public class TranslationEntryPersistenceImpl
	extends BasePersistenceImpl<TranslationEntry, NoSuchEntryException>
	implements TranslationEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>TranslationEntryUtil</code> to access the translation entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		TranslationEntryImpl.class.getName();

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
	private CollectionPersistenceFinder<TranslationEntry>
		_collectionPersistenceFinderByUuid;

	/**
	 * Returns all the translation entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching translation entries
	 */
	@Override
	public List<TranslationEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the translation entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TranslationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of translation entries
	 * @param end the upper bound of the range of translation entries (not inclusive)
	 * @return the range of matching translation entries
	 */
	@Override
	public List<TranslationEntry> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the translation entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TranslationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of translation entries
	 * @param end the upper bound of the range of translation entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching translation entries
	 */
	@Override
	public List<TranslationEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<TranslationEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the translation entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TranslationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of translation entries
	 * @param end the upper bound of the range of translation entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching translation entries
	 */
	@Override
	public List<TranslationEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<TranslationEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					TranslationEntry.class)) {

			return _collectionPersistenceFinderByUuid.find(
				finderCache, new Object[] {uuid}, start, end, orderByComparator,
				useFinderCache);
		}
	}

	/**
	 * Returns the first translation entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching translation entry
	 * @throws NoSuchEntryException if a matching translation entry could not be found
	 */
	@Override
	public TranslationEntry findByUuid_First(
			String uuid, OrderByComparator<TranslationEntry> orderByComparator)
		throws NoSuchEntryException {

		TranslationEntry translationEntry = fetchByUuid_First(
			uuid, orderByComparator);

		if (translationEntry != null) {
			return translationEntry;
		}

		throw new NoSuchEntryException(
			_collectionPersistenceFinderByUuid.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid}));
	}

	/**
	 * Returns the first translation entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching translation entry, or <code>null</code> if a matching translation entry could not be found
	 */
	@Override
	public TranslationEntry fetchByUuid_First(
		String uuid, OrderByComparator<TranslationEntry> orderByComparator) {

		return _collectionPersistenceFinderByUuid.fetchFirst(
			finderCache, new Object[] {uuid}, orderByComparator);
	}

	/**
	 * Removes all the translation entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		_collectionPersistenceFinderByUuid.remove(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of translation entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching translation entries
	 */
	@Override
	public int countByUuid(String uuid) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					TranslationEntry.class)) {

			return _collectionPersistenceFinderByUuid.count(
				finderCache, new Object[] {uuid});
		}
	}

	private FinderPath _finderPathFetchByUUID_G;
	private UniquePersistenceFinder<TranslationEntry>
		_uniquePersistenceFinderByUUID_G;

	/**
	 * Returns the translation entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching translation entry
	 * @throws NoSuchEntryException if a matching translation entry could not be found
	 */
	@Override
	public TranslationEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException {

		TranslationEntry translationEntry = fetchByUUID_G(uuid, groupId);

		if (translationEntry == null) {
			String message =
				_uniquePersistenceFinderByUUID_G.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, groupId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchEntryException(message);
		}

		return translationEntry;
	}

	/**
	 * Returns the translation entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching translation entry, or <code>null</code> if a matching translation entry could not be found
	 */
	@Override
	public TranslationEntry fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the translation entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching translation entry, or <code>null</code> if a matching translation entry could not be found
	 */
	@Override
	public TranslationEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					TranslationEntry.class)) {

			return _uniquePersistenceFinderByUUID_G.fetch(
				finderCache, new Object[] {uuid, groupId}, useFinderCache);
		}
	}

	/**
	 * Removes the translation entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the translation entry that was removed
	 */
	@Override
	public TranslationEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException {

		TranslationEntry translationEntry = findByUUID_G(uuid, groupId);

		return remove(translationEntry);
	}

	/**
	 * Returns the number of translation entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching translation entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		return _uniquePersistenceFinderByUUID_G.count(
			finderCache, new Object[] {uuid, groupId});
	}

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;
	private CollectionPersistenceFinder<TranslationEntry>
		_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns all the translation entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching translation entries
	 */
	@Override
	public List<TranslationEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the translation entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TranslationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of translation entries
	 * @param end the upper bound of the range of translation entries (not inclusive)
	 * @return the range of matching translation entries
	 */
	@Override
	public List<TranslationEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the translation entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TranslationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of translation entries
	 * @param end the upper bound of the range of translation entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching translation entries
	 */
	@Override
	public List<TranslationEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<TranslationEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the translation entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TranslationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of translation entries
	 * @param end the upper bound of the range of translation entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching translation entries
	 */
	@Override
	public List<TranslationEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<TranslationEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					TranslationEntry.class)) {

			return _collectionPersistenceFinderByUuid_C.find(
				finderCache, new Object[] {uuid, companyId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first translation entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching translation entry
	 * @throws NoSuchEntryException if a matching translation entry could not be found
	 */
	@Override
	public TranslationEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<TranslationEntry> orderByComparator)
		throws NoSuchEntryException {

		TranslationEntry translationEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (translationEntry != null) {
			return translationEntry;
		}

		throw new NoSuchEntryException(
			_collectionPersistenceFinderByUuid_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, companyId}));
	}

	/**
	 * Returns the first translation entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching translation entry, or <code>null</code> if a matching translation entry could not be found
	 */
	@Override
	public TranslationEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<TranslationEntry> orderByComparator) {

		return _collectionPersistenceFinderByUuid_C.fetchFirst(
			finderCache, new Object[] {uuid, companyId}, orderByComparator);
	}

	/**
	 * Removes all the translation entries where uuid = &#63; and companyId = &#63; from the database.
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
	 * Returns the number of translation entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching translation entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					TranslationEntry.class)) {

			return _collectionPersistenceFinderByUuid_C.count(
				finderCache, new Object[] {uuid, companyId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathCountByC_C;
	private CollectionPersistenceFinder<TranslationEntry>
		_collectionPersistenceFinderByC_C;

	/**
	 * Returns all the translation entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching translation entries
	 */
	@Override
	public List<TranslationEntry> findByC_C(long classNameId, long classPK) {
		return findByC_C(
			classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the translation entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TranslationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of translation entries
	 * @param end the upper bound of the range of translation entries (not inclusive)
	 * @return the range of matching translation entries
	 */
	@Override
	public List<TranslationEntry> findByC_C(
		long classNameId, long classPK, int start, int end) {

		return findByC_C(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the translation entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TranslationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of translation entries
	 * @param end the upper bound of the range of translation entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching translation entries
	 */
	@Override
	public List<TranslationEntry> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<TranslationEntry> orderByComparator) {

		return findByC_C(
			classNameId, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the translation entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TranslationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of translation entries
	 * @param end the upper bound of the range of translation entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching translation entries
	 */
	@Override
	public List<TranslationEntry> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<TranslationEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					TranslationEntry.class)) {

			return _collectionPersistenceFinderByC_C.find(
				finderCache, new Object[] {classNameId, classPK}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first translation entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching translation entry
	 * @throws NoSuchEntryException if a matching translation entry could not be found
	 */
	@Override
	public TranslationEntry findByC_C_First(
			long classNameId, long classPK,
			OrderByComparator<TranslationEntry> orderByComparator)
		throws NoSuchEntryException {

		TranslationEntry translationEntry = fetchByC_C_First(
			classNameId, classPK, orderByComparator);

		if (translationEntry != null) {
			return translationEntry;
		}

		throw new NoSuchEntryException(
			_collectionPersistenceFinderByC_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {classNameId, classPK}));
	}

	/**
	 * Returns the first translation entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching translation entry, or <code>null</code> if a matching translation entry could not be found
	 */
	@Override
	public TranslationEntry fetchByC_C_First(
		long classNameId, long classPK,
		OrderByComparator<TranslationEntry> orderByComparator) {

		return _collectionPersistenceFinderByC_C.fetchFirst(
			finderCache, new Object[] {classNameId, classPK},
			orderByComparator);
	}

	/**
	 * Removes all the translation entries where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByC_C(long classNameId, long classPK) {
		_collectionPersistenceFinderByC_C.remove(
			finderCache, new Object[] {classNameId, classPK});
	}

	/**
	 * Returns the number of translation entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching translation entries
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					TranslationEntry.class)) {

			return _collectionPersistenceFinderByC_C.count(
				finderCache, new Object[] {classNameId, classPK});
		}
	}

	private FinderPath _finderPathFetchByC_C_L;
	private UniquePersistenceFinder<TranslationEntry>
		_uniquePersistenceFinderByC_C_L;

	/**
	 * Returns the translation entry where classNameId = &#63; and classPK = &#63; and languageId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @return the matching translation entry
	 * @throws NoSuchEntryException if a matching translation entry could not be found
	 */
	@Override
	public TranslationEntry findByC_C_L(
			long classNameId, long classPK, String languageId)
		throws NoSuchEntryException {

		TranslationEntry translationEntry = fetchByC_C_L(
			classNameId, classPK, languageId);

		if (translationEntry == null) {
			String message =
				_uniquePersistenceFinderByC_C_L.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {classNameId, classPK, languageId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchEntryException(message);
		}

		return translationEntry;
	}

	/**
	 * Returns the translation entry where classNameId = &#63; and classPK = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @return the matching translation entry, or <code>null</code> if a matching translation entry could not be found
	 */
	@Override
	public TranslationEntry fetchByC_C_L(
		long classNameId, long classPK, String languageId) {

		return fetchByC_C_L(classNameId, classPK, languageId, true);
	}

	/**
	 * Returns the translation entry where classNameId = &#63; and classPK = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching translation entry, or <code>null</code> if a matching translation entry could not be found
	 */
	@Override
	public TranslationEntry fetchByC_C_L(
		long classNameId, long classPK, String languageId,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					TranslationEntry.class)) {

			return _uniquePersistenceFinderByC_C_L.fetch(
				finderCache, new Object[] {classNameId, classPK, languageId},
				useFinderCache);
		}
	}

	/**
	 * Removes the translation entry where classNameId = &#63; and classPK = &#63; and languageId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @return the translation entry that was removed
	 */
	@Override
	public TranslationEntry removeByC_C_L(
			long classNameId, long classPK, String languageId)
		throws NoSuchEntryException {

		TranslationEntry translationEntry = findByC_C_L(
			classNameId, classPK, languageId);

		return remove(translationEntry);
	}

	/**
	 * Returns the number of translation entries where classNameId = &#63; and classPK = &#63; and languageId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @return the number of matching translation entries
	 */
	@Override
	public int countByC_C_L(long classNameId, long classPK, String languageId) {
		return _uniquePersistenceFinderByC_C_L.count(
			finderCache, new Object[] {classNameId, classPK, languageId});
	}

	public TranslationEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(TranslationEntry.class);

		setModelImplClass(TranslationEntryImpl.class);
		setModelPKClass(long.class);

		setTable(TranslationEntryTable.INSTANCE);
	}

	/**
	 * Caches the translation entry in the entity cache if it is enabled.
	 *
	 * @param translationEntry the translation entry
	 */
	@Override
	public void cacheResult(TranslationEntry translationEntry) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					translationEntry.getCtCollectionId())) {

			entityCache.putResult(
				TranslationEntryImpl.class, translationEntry.getPrimaryKey(),
				translationEntry);

			finderCache.putResult(
				_finderPathFetchByUUID_G,
				new Object[] {
					translationEntry.getUuid(), translationEntry.getGroupId()
				},
				translationEntry);

			finderCache.putResult(
				_finderPathFetchByC_C_L,
				new Object[] {
					translationEntry.getClassNameId(),
					translationEntry.getClassPK(),
					translationEntry.getLanguageId()
				},
				translationEntry);
		}
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the translation entries in the entity cache if it is enabled.
	 *
	 * @param translationEntries the translation entries
	 */
	@Override
	public void cacheResult(List<TranslationEntry> translationEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (translationEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (TranslationEntry translationEntry : translationEntries) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						translationEntry.getCtCollectionId())) {

				if (entityCache.getResult(
						TranslationEntryImpl.class,
						translationEntry.getPrimaryKey()) == null) {

					cacheResult(translationEntry);
				}
			}
		}
	}

	protected void cacheUniqueFindersCache(
		TranslationEntryModelImpl translationEntryModelImpl) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					translationEntryModelImpl.getCtCollectionId())) {

			Object[] args = new Object[] {
				translationEntryModelImpl.getUuid(),
				translationEntryModelImpl.getGroupId()
			};

			finderCache.putResult(
				_finderPathFetchByUUID_G, args, translationEntryModelImpl);

			args = new Object[] {
				translationEntryModelImpl.getClassNameId(),
				translationEntryModelImpl.getClassPK(),
				translationEntryModelImpl.getLanguageId()
			};

			finderCache.putResult(
				_finderPathFetchByC_C_L, args, translationEntryModelImpl);
		}
	}

	/**
	 * Creates a new translation entry with the primary key. Does not add the translation entry to the database.
	 *
	 * @param translationEntryId the primary key for the new translation entry
	 * @return the new translation entry
	 */
	@Override
	public TranslationEntry create(long translationEntryId) {
		TranslationEntry translationEntry = new TranslationEntryImpl();

		translationEntry.setNew(true);
		translationEntry.setPrimaryKey(translationEntryId);

		String uuid = PortalUUIDUtil.generate();

		translationEntry.setUuid(uuid);

		translationEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return translationEntry;
	}

	/**
	 * Removes the translation entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param translationEntryId the primary key of the translation entry
	 * @return the translation entry that was removed
	 * @throws NoSuchEntryException if a translation entry with the primary key could not be found
	 */
	@Override
	public TranslationEntry remove(long translationEntryId)
		throws NoSuchEntryException {

		return remove((Serializable)translationEntryId);
	}

	@Override
	protected TranslationEntry removeImpl(TranslationEntry translationEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(translationEntry)) {
				translationEntry = (TranslationEntry)session.get(
					TranslationEntryImpl.class,
					translationEntry.getPrimaryKeyObj());
			}

			if ((translationEntry != null) &&
				ctPersistenceHelper.isRemove(translationEntry)) {

				session.delete(translationEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (translationEntry != null) {
			clearCache(translationEntry);
		}

		return translationEntry;
	}

	@Override
	public TranslationEntry updateImpl(TranslationEntry translationEntry) {
		boolean isNew = translationEntry.isNew();

		if (!(translationEntry instanceof TranslationEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(translationEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					translationEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in translationEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom TranslationEntry implementation " +
					translationEntry.getClass());
		}

		TranslationEntryModelImpl translationEntryModelImpl =
			(TranslationEntryModelImpl)translationEntry;

		if (Validator.isNull(translationEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			translationEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (translationEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				translationEntry.setCreateDate(date);
			}
			else {
				translationEntry.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!translationEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				translationEntry.setModifiedDate(date);
			}
			else {
				translationEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(translationEntry)) {
				if (!isNew) {
					session.evict(
						TranslationEntryImpl.class,
						translationEntry.getPrimaryKeyObj());
				}

				session.save(translationEntry);
			}
			else {
				translationEntry = (TranslationEntry)session.merge(
					translationEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			TranslationEntryImpl.class, translationEntryModelImpl, false, true);

		cacheUniqueFindersCache(translationEntryModelImpl);

		if (isNew) {
			translationEntry.setNew(false);
		}

		translationEntry.resetOriginalValues();

		return translationEntry;
	}

	/**
	 * Returns the translation entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param translationEntryId the primary key of the translation entry
	 * @return the translation entry
	 * @throws NoSuchEntryException if a translation entry with the primary key could not be found
	 */
	@Override
	public TranslationEntry findByPrimaryKey(long translationEntryId)
		throws NoSuchEntryException {

		return findByPrimaryKey((Serializable)translationEntryId);
	}

	/**
	 * Returns the translation entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the translation entry
	 * @return the translation entry, or <code>null</code> if a translation entry with the primary key could not be found
	 */
	@Override
	public TranslationEntry fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(
				TranslationEntry.class, primaryKey)) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKey(primaryKey);
			}
		}

		TranslationEntry translationEntry =
			(TranslationEntry)entityCache.getResult(
				TranslationEntryImpl.class, primaryKey);

		if (translationEntry != null) {
			return translationEntry;
		}

		Session session = null;

		try {
			session = openSession();

			translationEntry = (TranslationEntry)session.get(
				TranslationEntryImpl.class, primaryKey);

			if (translationEntry != null) {
				cacheResult(translationEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return translationEntry;
	}

	/**
	 * Returns the translation entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param translationEntryId the primary key of the translation entry
	 * @return the translation entry, or <code>null</code> if a translation entry with the primary key could not be found
	 */
	@Override
	public TranslationEntry fetchByPrimaryKey(long translationEntryId) {
		return fetchByPrimaryKey((Serializable)translationEntryId);
	}

	@Override
	public Map<Serializable, TranslationEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(TranslationEntry.class)) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKeys(primaryKeys);
			}
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, TranslationEntry> map =
			new HashMap<Serializable, TranslationEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			TranslationEntry translationEntry = fetchByPrimaryKey(primaryKey);

			if (translationEntry != null) {
				map.put(primaryKey, translationEntry);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			try (SafeCloseable safeCloseable =
					ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
						TranslationEntry.class, primaryKey)) {

				TranslationEntry translationEntry =
					(TranslationEntry)entityCache.getResult(
						TranslationEntryImpl.class, primaryKey);

				if (translationEntry == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, translationEntry);
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

			for (TranslationEntry translationEntry :
					(List<TranslationEntry>)query.list()) {

				map.put(translationEntry.getPrimaryKeyObj(), translationEntry);

				cacheResult(translationEntry);
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
	 * Returns all the translation entries.
	 *
	 * @return the translation entries
	 */
	@Override
	public List<TranslationEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the translation entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TranslationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of translation entries
	 * @param end the upper bound of the range of translation entries (not inclusive)
	 * @return the range of translation entries
	 */
	@Override
	public List<TranslationEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the translation entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TranslationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of translation entries
	 * @param end the upper bound of the range of translation entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of translation entries
	 */
	@Override
	public List<TranslationEntry> findAll(
		int start, int end,
		OrderByComparator<TranslationEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the translation entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TranslationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of translation entries
	 * @param end the upper bound of the range of translation entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of translation entries
	 */
	@Override
	public List<TranslationEntry> findAll(
		int start, int end,
		OrderByComparator<TranslationEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					TranslationEntry.class)) {

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

			List<TranslationEntry> list = null;

			if (useFinderCache) {
				list = (List<TranslationEntry>)finderCache.getResult(
					finderPath, finderArgs, this);
			}

			if (list == null) {
				StringBundler sb = null;
				String sql = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						2 + (orderByComparator.getOrderByFields().length * 2));

					sb.append(_SQL_SELECT_TRANSLATIONENTRY);

					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

					sql = sb.toString();
				}
				else {
					sql = _SQL_SELECT_TRANSLATIONENTRY;

					sql = sql.concat(TranslationEntryModelImpl.ORDER_BY_JPQL);
				}

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					list = (List<TranslationEntry>)QueryUtil.list(
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
	 * Removes all the translation entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (TranslationEntry translationEntry : findAll()) {
			remove(translationEntry);
		}
	}

	/**
	 * Returns the number of translation entries.
	 *
	 * @return the number of translation entries
	 */
	@Override
	public int countAll() {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					TranslationEntry.class)) {

			Long count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);

			if (count == null) {
				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(
						_SQL_COUNT_TRANSLATIONENTRY);

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
		return "translationEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_TRANSLATIONENTRY;
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
		return TranslationEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "TranslationEntry";
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
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("classNameId");
		ctStrictColumnNames.add("classPK");
		ctMergeColumnNames.add("content");
		ctMergeColumnNames.add("contentType");
		ctMergeColumnNames.add("languageId");
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
			Collections.singleton("translationEntryId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(
			new String[] {"classNameId", "classPK", "languageId"});
	}

	/**
	 * Initializes the translation entry persistence.
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
			_SQL_SELECT_TRANSLATIONENTRY_WHERE,
			_SQL_COUNT_TRANSLATIONENTRY_WHERE,
			TranslationEntryModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"translationEntry.", "uuid", FinderColumn.Type.STRING, "=",
				true, true, TranslationEntry::getUuid));

		_finderPathFetchByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, true);

		_uniquePersistenceFinderByUUID_G = new UniquePersistenceFinder<>(
			this, _finderPathFetchByUUID_G, _SQL_SELECT_TRANSLATIONENTRY_WHERE,
			new FinderColumn<>(
				"translationEntry.", "uuid", FinderColumn.Type.STRING, "=",
				true, false, TranslationEntry::getUuid),
			new FinderColumn<>(
				"translationEntry.", "groupId", FinderColumn.Type.LONG, "=",
				true, true, TranslationEntry::getGroupId));

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
				_finderPathCountByUuid_C, _SQL_SELECT_TRANSLATIONENTRY_WHERE,
				_SQL_COUNT_TRANSLATIONENTRY_WHERE,
				TranslationEntryModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"translationEntry.", "uuid", FinderColumn.Type.STRING, "=",
					true, false, TranslationEntry::getUuid),
				new FinderColumn<>(
					"translationEntry.", "companyId", FinderColumn.Type.LONG,
					"=", true, true, TranslationEntry::getCompanyId));

		_finderPathWithPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"classNameId", "classPK"}, true);

		_finderPathWithoutPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "classPK"}, true);

		_finderPathCountByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "classPK"}, false);

		_collectionPersistenceFinderByC_C = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByC_C,
			_finderPathWithoutPaginationFindByC_C, _finderPathCountByC_C,
			_SQL_SELECT_TRANSLATIONENTRY_WHERE,
			_SQL_COUNT_TRANSLATIONENTRY_WHERE,
			TranslationEntryModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"translationEntry.", "classNameId", FinderColumn.Type.LONG, "=",
				true, false, TranslationEntry::getClassNameId),
			new FinderColumn<>(
				"translationEntry.", "classPK", FinderColumn.Type.LONG, "=",
				true, true, TranslationEntry::getClassPK));

		_finderPathFetchByC_C_L = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C_L",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			new String[] {"classNameId", "classPK", "languageId"}, true);

		_uniquePersistenceFinderByC_C_L = new UniquePersistenceFinder<>(
			this, _finderPathFetchByC_C_L, _SQL_SELECT_TRANSLATIONENTRY_WHERE,
			new FinderColumn<>(
				"translationEntry.", "classNameId", FinderColumn.Type.LONG, "=",
				true, false, TranslationEntry::getClassNameId),
			new FinderColumn<>(
				"translationEntry.", "classPK", FinderColumn.Type.LONG, "=",
				true, false, TranslationEntry::getClassPK),
			new FinderColumn<>(
				"translationEntry.", "languageId", FinderColumn.Type.STRING,
				"=", true, true, TranslationEntry::getLanguageId));

		TranslationEntryUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		TranslationEntryUtil.setPersistence(null);

		entityCache.removeCache(TranslationEntryImpl.class.getName());
	}

	@Override
	@Reference(
		target = TranslationPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = TranslationPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = TranslationPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_TRANSLATIONENTRY =
		"SELECT translationEntry FROM TranslationEntry translationEntry";

	private static final String _SQL_SELECT_TRANSLATIONENTRY_WHERE =
		"SELECT translationEntry FROM TranslationEntry translationEntry WHERE ";

	private static final String _SQL_COUNT_TRANSLATIONENTRY =
		"SELECT COUNT(translationEntry) FROM TranslationEntry translationEntry";

	private static final String _SQL_COUNT_TRANSLATIONENTRY_WHERE =
		"SELECT COUNT(translationEntry) FROM TranslationEntry translationEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "translationEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No TranslationEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		TranslationEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-1406755202