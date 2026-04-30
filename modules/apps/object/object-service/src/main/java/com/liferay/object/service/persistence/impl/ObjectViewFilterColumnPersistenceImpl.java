/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.persistence.impl;

import com.liferay.object.exception.NoSuchObjectViewFilterColumnException;
import com.liferay.object.model.ObjectViewFilterColumn;
import com.liferay.object.model.ObjectViewFilterColumnTable;
import com.liferay.object.model.impl.ObjectViewFilterColumnImpl;
import com.liferay.object.model.impl.ObjectViewFilterColumnModelImpl;
import com.liferay.object.service.persistence.ObjectViewFilterColumnPersistence;
import com.liferay.object.service.persistence.ObjectViewFilterColumnUtil;
import com.liferay.object.service.persistence.impl.constants.ObjectPersistenceConstants;
import com.liferay.petra.string.StringBundler;
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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.CollectionPersistenceFinder;
import com.liferay.portal.kernel.service.persistence.impl.FinderColumn;
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the object view filter column service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
@Component(service = ObjectViewFilterColumnPersistence.class)
public class ObjectViewFilterColumnPersistenceImpl
	extends BasePersistenceImpl
		<ObjectViewFilterColumn, NoSuchObjectViewFilterColumnException>
	implements ObjectViewFilterColumnPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ObjectViewFilterColumnUtil</code> to access the object view filter column persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ObjectViewFilterColumnImpl.class.getName();

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
	private CollectionPersistenceFinder<ObjectViewFilterColumn>
		_collectionPersistenceFinderByUuid;

	/**
	 * Returns all the object view filter columns where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object view filter columns
	 */
	@Override
	public List<ObjectViewFilterColumn> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object view filter columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @return the range of matching object view filter columns
	 */
	@Override
	public List<ObjectViewFilterColumn> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object view filter columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view filter columns
	 */
	@Override
	public List<ObjectViewFilterColumn> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object view filter columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view filter columns
	 */
	@Override
	public List<ObjectViewFilterColumn> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid.find(
			finderCache, new Object[] {uuid}, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first object view filter column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a matching object view filter column could not be found
	 */
	@Override
	public ObjectViewFilterColumn findByUuid_First(
			String uuid,
			OrderByComparator<ObjectViewFilterColumn> orderByComparator)
		throws NoSuchObjectViewFilterColumnException {

		ObjectViewFilterColumn objectViewFilterColumn = fetchByUuid_First(
			uuid, orderByComparator);

		if (objectViewFilterColumn != null) {
			return objectViewFilterColumn;
		}

		throw new NoSuchObjectViewFilterColumnException(
			_collectionPersistenceFinderByUuid.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid}));
	}

	/**
	 * Returns the first object view filter column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view filter column, or <code>null</code> if a matching object view filter column could not be found
	 */
	@Override
	public ObjectViewFilterColumn fetchByUuid_First(
		String uuid,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator) {

		return _collectionPersistenceFinderByUuid.fetchFirst(
			finderCache, new Object[] {uuid}, orderByComparator);
	}

	/**
	 * Removes all the object view filter columns where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		_collectionPersistenceFinderByUuid.remove(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of object view filter columns where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object view filter columns
	 */
	@Override
	public int countByUuid(String uuid) {
		return _collectionPersistenceFinderByUuid.count(
			finderCache, new Object[] {uuid});
	}

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;
	private CollectionPersistenceFinder<ObjectViewFilterColumn>
		_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns all the object view filter columns where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object view filter columns
	 */
	@Override
	public List<ObjectViewFilterColumn> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object view filter columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @return the range of matching object view filter columns
	 */
	@Override
	public List<ObjectViewFilterColumn> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object view filter columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view filter columns
	 */
	@Override
	public List<ObjectViewFilterColumn> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object view filter columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view filter columns
	 */
	@Override
	public List<ObjectViewFilterColumn> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid_C.find(
			finderCache, new Object[] {uuid, companyId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object view filter column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a matching object view filter column could not be found
	 */
	@Override
	public ObjectViewFilterColumn findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ObjectViewFilterColumn> orderByComparator)
		throws NoSuchObjectViewFilterColumnException {

		ObjectViewFilterColumn objectViewFilterColumn = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (objectViewFilterColumn != null) {
			return objectViewFilterColumn;
		}

		throw new NoSuchObjectViewFilterColumnException(
			_collectionPersistenceFinderByUuid_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, companyId}));
	}

	/**
	 * Returns the first object view filter column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view filter column, or <code>null</code> if a matching object view filter column could not be found
	 */
	@Override
	public ObjectViewFilterColumn fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator) {

		return _collectionPersistenceFinderByUuid_C.fetchFirst(
			finderCache, new Object[] {uuid, companyId}, orderByComparator);
	}

	/**
	 * Removes all the object view filter columns where uuid = &#63; and companyId = &#63; from the database.
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
	 * Returns the number of object view filter columns where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object view filter columns
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		return _collectionPersistenceFinderByUuid_C.count(
			finderCache, new Object[] {uuid, companyId});
	}

	private FinderPath _finderPathWithPaginationFindByObjectViewId;
	private FinderPath _finderPathWithoutPaginationFindByObjectViewId;
	private FinderPath _finderPathCountByObjectViewId;
	private CollectionPersistenceFinder<ObjectViewFilterColumn>
		_collectionPersistenceFinderByObjectViewId;

	/**
	 * Returns all the object view filter columns where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @return the matching object view filter columns
	 */
	@Override
	public List<ObjectViewFilterColumn> findByObjectViewId(long objectViewId) {
		return findByObjectViewId(
			objectViewId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object view filter columns where objectViewId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @return the range of matching object view filter columns
	 */
	@Override
	public List<ObjectViewFilterColumn> findByObjectViewId(
		long objectViewId, int start, int end) {

		return findByObjectViewId(objectViewId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object view filter columns where objectViewId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view filter columns
	 */
	@Override
	public List<ObjectViewFilterColumn> findByObjectViewId(
		long objectViewId, int start, int end,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator) {

		return findByObjectViewId(
			objectViewId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object view filter columns where objectViewId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view filter columns
	 */
	@Override
	public List<ObjectViewFilterColumn> findByObjectViewId(
		long objectViewId, int start, int end,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByObjectViewId.find(
			finderCache, new Object[] {objectViewId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object view filter column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a matching object view filter column could not be found
	 */
	@Override
	public ObjectViewFilterColumn findByObjectViewId_First(
			long objectViewId,
			OrderByComparator<ObjectViewFilterColumn> orderByComparator)
		throws NoSuchObjectViewFilterColumnException {

		ObjectViewFilterColumn objectViewFilterColumn =
			fetchByObjectViewId_First(objectViewId, orderByComparator);

		if (objectViewFilterColumn != null) {
			return objectViewFilterColumn;
		}

		throw new NoSuchObjectViewFilterColumnException(
			_collectionPersistenceFinderByObjectViewId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {objectViewId}));
	}

	/**
	 * Returns the first object view filter column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view filter column, or <code>null</code> if a matching object view filter column could not be found
	 */
	@Override
	public ObjectViewFilterColumn fetchByObjectViewId_First(
		long objectViewId,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator) {

		return _collectionPersistenceFinderByObjectViewId.fetchFirst(
			finderCache, new Object[] {objectViewId}, orderByComparator);
	}

	/**
	 * Removes all the object view filter columns where objectViewId = &#63; from the database.
	 *
	 * @param objectViewId the object view ID
	 */
	@Override
	public void removeByObjectViewId(long objectViewId) {
		_collectionPersistenceFinderByObjectViewId.remove(
			finderCache, new Object[] {objectViewId});
	}

	/**
	 * Returns the number of object view filter columns where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @return the number of matching object view filter columns
	 */
	@Override
	public int countByObjectViewId(long objectViewId) {
		return _collectionPersistenceFinderByObjectViewId.count(
			finderCache, new Object[] {objectViewId});
	}

	private FinderPath _finderPathWithPaginationFindByOVI_OFN;
	private FinderPath _finderPathWithoutPaginationFindByOVI_OFN;
	private FinderPath _finderPathCountByOVI_OFN;
	private CollectionPersistenceFinder<ObjectViewFilterColumn>
		_collectionPersistenceFinderByOVI_OFN;

	/**
	 * Returns all the object view filter columns where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @return the matching object view filter columns
	 */
	@Override
	public List<ObjectViewFilterColumn> findByOVI_OFN(
		long objectViewId, String objectFieldName) {

		return findByOVI_OFN(
			objectViewId, objectFieldName, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the object view filter columns where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @return the range of matching object view filter columns
	 */
	@Override
	public List<ObjectViewFilterColumn> findByOVI_OFN(
		long objectViewId, String objectFieldName, int start, int end) {

		return findByOVI_OFN(objectViewId, objectFieldName, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object view filter columns where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view filter columns
	 */
	@Override
	public List<ObjectViewFilterColumn> findByOVI_OFN(
		long objectViewId, String objectFieldName, int start, int end,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator) {

		return findByOVI_OFN(
			objectViewId, objectFieldName, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object view filter columns where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view filter columns
	 */
	@Override
	public List<ObjectViewFilterColumn> findByOVI_OFN(
		long objectViewId, String objectFieldName, int start, int end,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByOVI_OFN.find(
			finderCache, new Object[] {objectViewId, objectFieldName}, start,
			end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object view filter column in the ordered set where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a matching object view filter column could not be found
	 */
	@Override
	public ObjectViewFilterColumn findByOVI_OFN_First(
			long objectViewId, String objectFieldName,
			OrderByComparator<ObjectViewFilterColumn> orderByComparator)
		throws NoSuchObjectViewFilterColumnException {

		ObjectViewFilterColumn objectViewFilterColumn = fetchByOVI_OFN_First(
			objectViewId, objectFieldName, orderByComparator);

		if (objectViewFilterColumn != null) {
			return objectViewFilterColumn;
		}

		throw new NoSuchObjectViewFilterColumnException(
			_collectionPersistenceFinderByOVI_OFN.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {objectViewId, objectFieldName}));
	}

	/**
	 * Returns the first object view filter column in the ordered set where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view filter column, or <code>null</code> if a matching object view filter column could not be found
	 */
	@Override
	public ObjectViewFilterColumn fetchByOVI_OFN_First(
		long objectViewId, String objectFieldName,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator) {

		return _collectionPersistenceFinderByOVI_OFN.fetchFirst(
			finderCache, new Object[] {objectViewId, objectFieldName},
			orderByComparator);
	}

	/**
	 * Removes all the object view filter columns where objectViewId = &#63; and objectFieldName = &#63; from the database.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 */
	@Override
	public void removeByOVI_OFN(long objectViewId, String objectFieldName) {
		_collectionPersistenceFinderByOVI_OFN.remove(
			finderCache, new Object[] {objectViewId, objectFieldName});
	}

	/**
	 * Returns the number of object view filter columns where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @return the number of matching object view filter columns
	 */
	@Override
	public int countByOVI_OFN(long objectViewId, String objectFieldName) {
		return _collectionPersistenceFinderByOVI_OFN.count(
			finderCache, new Object[] {objectViewId, objectFieldName});
	}

	public ObjectViewFilterColumnPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(ObjectViewFilterColumn.class);

		setModelImplClass(ObjectViewFilterColumnImpl.class);
		setModelPKClass(long.class);

		setTable(ObjectViewFilterColumnTable.INSTANCE);
	}

	/**
	 * Caches the object view filter column in the entity cache if it is enabled.
	 *
	 * @param objectViewFilterColumn the object view filter column
	 */
	@Override
	public void cacheResult(ObjectViewFilterColumn objectViewFilterColumn) {
		entityCache.putResult(
			ObjectViewFilterColumnImpl.class,
			objectViewFilterColumn.getPrimaryKey(), objectViewFilterColumn);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the object view filter columns in the entity cache if it is enabled.
	 *
	 * @param objectViewFilterColumns the object view filter columns
	 */
	@Override
	public void cacheResult(
		List<ObjectViewFilterColumn> objectViewFilterColumns) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (objectViewFilterColumns.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ObjectViewFilterColumn objectViewFilterColumn :
				objectViewFilterColumns) {

			if (entityCache.getResult(
					ObjectViewFilterColumnImpl.class,
					objectViewFilterColumn.getPrimaryKey()) == null) {

				cacheResult(objectViewFilterColumn);
			}
		}
	}

	/**
	 * Creates a new object view filter column with the primary key. Does not add the object view filter column to the database.
	 *
	 * @param objectViewFilterColumnId the primary key for the new object view filter column
	 * @return the new object view filter column
	 */
	@Override
	public ObjectViewFilterColumn create(long objectViewFilterColumnId) {
		ObjectViewFilterColumn objectViewFilterColumn =
			new ObjectViewFilterColumnImpl();

		objectViewFilterColumn.setNew(true);
		objectViewFilterColumn.setPrimaryKey(objectViewFilterColumnId);

		String uuid = PortalUUIDUtil.generate();

		objectViewFilterColumn.setUuid(uuid);

		objectViewFilterColumn.setCompanyId(CompanyThreadLocal.getCompanyId());

		return objectViewFilterColumn;
	}

	/**
	 * Removes the object view filter column with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectViewFilterColumnId the primary key of the object view filter column
	 * @return the object view filter column that was removed
	 * @throws NoSuchObjectViewFilterColumnException if a object view filter column with the primary key could not be found
	 */
	@Override
	public ObjectViewFilterColumn remove(long objectViewFilterColumnId)
		throws NoSuchObjectViewFilterColumnException {

		return remove((Serializable)objectViewFilterColumnId);
	}

	@Override
	protected ObjectViewFilterColumn removeImpl(
		ObjectViewFilterColumn objectViewFilterColumn) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(objectViewFilterColumn)) {
				objectViewFilterColumn = (ObjectViewFilterColumn)session.get(
					ObjectViewFilterColumnImpl.class,
					objectViewFilterColumn.getPrimaryKeyObj());
			}

			if (objectViewFilterColumn != null) {
				session.delete(objectViewFilterColumn);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (objectViewFilterColumn != null) {
			clearCache(objectViewFilterColumn);
		}

		return objectViewFilterColumn;
	}

	@Override
	public ObjectViewFilterColumn updateImpl(
		ObjectViewFilterColumn objectViewFilterColumn) {

		boolean isNew = objectViewFilterColumn.isNew();

		if (!(objectViewFilterColumn instanceof
				ObjectViewFilterColumnModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(objectViewFilterColumn.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					objectViewFilterColumn);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in objectViewFilterColumn proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ObjectViewFilterColumn implementation " +
					objectViewFilterColumn.getClass());
		}

		ObjectViewFilterColumnModelImpl objectViewFilterColumnModelImpl =
			(ObjectViewFilterColumnModelImpl)objectViewFilterColumn;

		if (Validator.isNull(objectViewFilterColumn.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			objectViewFilterColumn.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (objectViewFilterColumn.getCreateDate() == null)) {
			if (serviceContext == null) {
				objectViewFilterColumn.setCreateDate(date);
			}
			else {
				objectViewFilterColumn.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!objectViewFilterColumnModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				objectViewFilterColumn.setModifiedDate(date);
			}
			else {
				objectViewFilterColumn.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(objectViewFilterColumn);
			}
			else {
				objectViewFilterColumn = (ObjectViewFilterColumn)session.merge(
					objectViewFilterColumn);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			ObjectViewFilterColumnImpl.class, objectViewFilterColumnModelImpl,
			false, true);

		if (isNew) {
			objectViewFilterColumn.setNew(false);
		}

		objectViewFilterColumn.resetOriginalValues();

		return objectViewFilterColumn;
	}

	/**
	 * Returns the object view filter column with the primary key or throws a <code>NoSuchObjectViewFilterColumnException</code> if it could not be found.
	 *
	 * @param objectViewFilterColumnId the primary key of the object view filter column
	 * @return the object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a object view filter column with the primary key could not be found
	 */
	@Override
	public ObjectViewFilterColumn findByPrimaryKey(
			long objectViewFilterColumnId)
		throws NoSuchObjectViewFilterColumnException {

		return findByPrimaryKey((Serializable)objectViewFilterColumnId);
	}

	/**
	 * Returns the object view filter column with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectViewFilterColumnId the primary key of the object view filter column
	 * @return the object view filter column, or <code>null</code> if a object view filter column with the primary key could not be found
	 */
	@Override
	public ObjectViewFilterColumn fetchByPrimaryKey(
		long objectViewFilterColumnId) {

		return fetchByPrimaryKey((Serializable)objectViewFilterColumnId);
	}

	/**
	 * Returns all the object view filter columns.
	 *
	 * @return the object view filter columns
	 */
	@Override
	public List<ObjectViewFilterColumn> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object view filter columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @return the range of object view filter columns
	 */
	@Override
	public List<ObjectViewFilterColumn> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the object view filter columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object view filter columns
	 */
	@Override
	public List<ObjectViewFilterColumn> findAll(
		int start, int end,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object view filter columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object view filter columns
	 */
	@Override
	public List<ObjectViewFilterColumn> findAll(
		int start, int end,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator,
		boolean useFinderCache) {

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

		List<ObjectViewFilterColumn> list = null;

		if (useFinderCache) {
			list = (List<ObjectViewFilterColumn>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_OBJECTVIEWFILTERCOLUMN);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_OBJECTVIEWFILTERCOLUMN;

				sql = sql.concat(ObjectViewFilterColumnModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ObjectViewFilterColumn>)QueryUtil.list(
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

	/**
	 * Removes all the object view filter columns from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ObjectViewFilterColumn objectViewFilterColumn : findAll()) {
			remove(objectViewFilterColumn);
		}
	}

	/**
	 * Returns the number of object view filter columns.
	 *
	 * @return the number of object view filter columns
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_OBJECTVIEWFILTERCOLUMN);

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
		return "objectViewFilterColumnId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_OBJECTVIEWFILTERCOLUMN;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ObjectViewFilterColumnModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the object view filter column persistence.
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
			_SQL_SELECT_OBJECTVIEWFILTERCOLUMN_WHERE,
			_SQL_COUNT_OBJECTVIEWFILTERCOLUMN_WHERE,
			ObjectViewFilterColumnModelImpl.ORDER_BY_JPQL,
			_ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"objectViewFilterColumn.", "uuid", FinderColumn.Type.STRING,
				"=", true, true, ObjectViewFilterColumn::getUuid));

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
				_SQL_SELECT_OBJECTVIEWFILTERCOLUMN_WHERE,
				_SQL_COUNT_OBJECTVIEWFILTERCOLUMN_WHERE,
				ObjectViewFilterColumnModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"objectViewFilterColumn.", "uuid", FinderColumn.Type.STRING,
					"=", true, false, ObjectViewFilterColumn::getUuid),
				new FinderColumn<>(
					"objectViewFilterColumn.", "companyId",
					FinderColumn.Type.LONG, "=", true, true,
					ObjectViewFilterColumn::getCompanyId));

		_finderPathWithPaginationFindByObjectViewId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByObjectViewId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"objectViewId"}, true);

		_finderPathWithoutPaginationFindByObjectViewId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByObjectViewId",
			new String[] {Long.class.getName()}, new String[] {"objectViewId"},
			true);

		_finderPathCountByObjectViewId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByObjectViewId",
			new String[] {Long.class.getName()}, new String[] {"objectViewId"},
			false);

		_collectionPersistenceFinderByObjectViewId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByObjectViewId,
				_finderPathWithoutPaginationFindByObjectViewId,
				_finderPathCountByObjectViewId,
				_SQL_SELECT_OBJECTVIEWFILTERCOLUMN_WHERE,
				_SQL_COUNT_OBJECTVIEWFILTERCOLUMN_WHERE,
				ObjectViewFilterColumnModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"objectViewFilterColumn.", "objectViewId",
					FinderColumn.Type.LONG, "=", true, true,
					ObjectViewFilterColumn::getObjectViewId));

		_finderPathWithPaginationFindByOVI_OFN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByOVI_OFN",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"objectViewId", "objectFieldName"}, true);

		_finderPathWithoutPaginationFindByOVI_OFN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByOVI_OFN",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"objectViewId", "objectFieldName"}, true);

		_finderPathCountByOVI_OFN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByOVI_OFN",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"objectViewId", "objectFieldName"}, false);

		_collectionPersistenceFinderByOVI_OFN =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByOVI_OFN,
				_finderPathWithoutPaginationFindByOVI_OFN,
				_finderPathCountByOVI_OFN,
				_SQL_SELECT_OBJECTVIEWFILTERCOLUMN_WHERE,
				_SQL_COUNT_OBJECTVIEWFILTERCOLUMN_WHERE,
				ObjectViewFilterColumnModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"objectViewFilterColumn.", "objectViewId",
					FinderColumn.Type.LONG, "=", true, false,
					ObjectViewFilterColumn::getObjectViewId),
				new FinderColumn<>(
					"objectViewFilterColumn.", "objectFieldName",
					FinderColumn.Type.STRING, "=", true, true,
					ObjectViewFilterColumn::getObjectFieldName));

		ObjectViewFilterColumnUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		ObjectViewFilterColumnUtil.setPersistence(null);

		entityCache.removeCache(ObjectViewFilterColumnImpl.class.getName());
	}

	@Override
	@Reference(
		target = ObjectPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = ObjectPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = ObjectPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_OBJECTVIEWFILTERCOLUMN =
		"SELECT objectViewFilterColumn FROM ObjectViewFilterColumn objectViewFilterColumn";

	private static final String _SQL_SELECT_OBJECTVIEWFILTERCOLUMN_WHERE =
		"SELECT objectViewFilterColumn FROM ObjectViewFilterColumn objectViewFilterColumn WHERE ";

	private static final String _SQL_COUNT_OBJECTVIEWFILTERCOLUMN =
		"SELECT COUNT(objectViewFilterColumn) FROM ObjectViewFilterColumn objectViewFilterColumn";

	private static final String _SQL_COUNT_OBJECTVIEWFILTERCOLUMN_WHERE =
		"SELECT COUNT(objectViewFilterColumn) FROM ObjectViewFilterColumn objectViewFilterColumn WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"objectViewFilterColumn.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ObjectViewFilterColumn exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectViewFilterColumnPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:274378888