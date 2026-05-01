/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.persistence.impl;

import com.liferay.object.exception.DuplicateObjectDefinitionExternalReferenceCodeException;
import com.liferay.object.exception.NoSuchObjectDefinitionException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectDefinitionTable;
import com.liferay.object.model.impl.ObjectDefinitionImpl;
import com.liferay.object.model.impl.ObjectDefinitionModelImpl;
import com.liferay.object.service.persistence.ObjectDefinitionPersistence;
import com.liferay.object.service.persistence.ObjectDefinitionUtil;
import com.liferay.object.service.persistence.impl.constants.ObjectPersistenceConstants;
import com.liferay.petra.string.StringBundler;
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

import java.util.Date;
import java.util.HashMap;
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
 * The persistence implementation for the object definition service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
@Component(service = ObjectDefinitionPersistence.class)
public class ObjectDefinitionPersistenceImpl
	extends BasePersistenceImpl
		<ObjectDefinition, NoSuchObjectDefinitionException>
	implements ObjectDefinitionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ObjectDefinitionUtil</code> to access the object definition persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ObjectDefinitionImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;
	private CollectionPersistenceFinder<ObjectDefinition>
		_collectionPersistenceFinderByUuid;

	/**
	 * Returns all the object definitions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object definitions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object definitions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid.find(
			finderCache, new Object[] {uuid}, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first object definition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object definition
	 * @throws NoSuchObjectDefinitionException if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition findByUuid_First(
			String uuid, OrderByComparator<ObjectDefinition> orderByComparator)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = fetchByUuid_First(
			uuid, orderByComparator);

		if (objectDefinition != null) {
			return objectDefinition;
		}

		throw new NoSuchObjectDefinitionException(
			_collectionPersistenceFinderByUuid.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid}));
	}

	/**
	 * Returns the first object definition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object definition, or <code>null</code> if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition fetchByUuid_First(
		String uuid, OrderByComparator<ObjectDefinition> orderByComparator) {

		return _collectionPersistenceFinderByUuid.fetchFirst(
			finderCache, new Object[] {uuid}, orderByComparator);
	}

	/**
	 * Returns all the object definitions that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByUuid(String uuid) {
		return filterFindByUuid(
			uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object definitions that the user has permission to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByUuid(
		String uuid, int start, int end) {

		return filterFindByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions that the user has permissions to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByUuid(uuid, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByUuid(
					uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					orderByComparator));
		}

		uuid = Objects.toString(uuid, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				3 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_OBJECTDEFINITION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(
					ObjectDefinitionModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, ObjectDefinitionImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, ObjectDefinitionImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindUuid) {
				queryPos.add(uuid);
			}

			return (List<ObjectDefinition>)QueryUtil.list(
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
	 * Removes all the object definitions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		_collectionPersistenceFinderByUuid.remove(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of object definitions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object definitions
	 */
	@Override
	public int countByUuid(String uuid) {
		return _collectionPersistenceFinderByUuid.count(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of object definitions that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object definitions that the user has permission to view
	 */
	@Override
	public int filterCountByUuid(String uuid) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByUuid(uuid);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<ObjectDefinition> objectDefinitions = findByUuid(uuid);

			objectDefinitions = InlineSQLHelperUtil.filter(objectDefinitions);

			return objectDefinitions.size();
		}

		uuid = Objects.toString(uuid, "");

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_OBJECTDEFINITION_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2_SQL);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindUuid) {
				queryPos.add(uuid);
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

	private static final String _FINDER_COLUMN_UUID_UUID_2_SQL =
		"objectDefinition.uuid_ = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3_SQL =
		"(objectDefinition.uuid_ IS NULL OR objectDefinition.uuid_ = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;
	private CollectionPersistenceFinder<ObjectDefinition>
		_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns all the object definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid_C.find(
			finderCache, new Object[] {uuid, companyId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object definition
	 * @throws NoSuchObjectDefinitionException if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ObjectDefinition> orderByComparator)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (objectDefinition != null) {
			return objectDefinition;
		}

		throw new NoSuchObjectDefinitionException(
			_collectionPersistenceFinderByUuid_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, companyId}));
	}

	/**
	 * Returns the first object definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object definition, or <code>null</code> if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		return _collectionPersistenceFinderByUuid_C.fetchFirst(
			finderCache, new Object[] {uuid, companyId}, orderByComparator);
	}

	/**
	 * Returns all the object definitions that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByUuid_C(
		String uuid, long companyId) {

		return filterFindByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object definitions that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByUuid_C(
		String uuid, long companyId, int start, int end) {

		return filterFindByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByUuid_C(uuid, companyId, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					orderByComparator));
		}

		uuid = Objects.toString(uuid, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_OBJECTDEFINITION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2_SQL);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(
					ObjectDefinitionModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, ObjectDefinitionImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, ObjectDefinitionImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindUuid) {
				queryPos.add(uuid);
			}

			queryPos.add(companyId);

			return (List<ObjectDefinition>)QueryUtil.list(
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
	 * Removes all the object definitions where uuid = &#63; and companyId = &#63; from the database.
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
	 * Returns the number of object definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object definitions
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		return _collectionPersistenceFinderByUuid_C.count(
			finderCache, new Object[] {uuid, companyId});
	}

	/**
	 * Returns the number of object definitions that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object definitions that the user has permission to view
	 */
	@Override
	public int filterCountByUuid_C(String uuid, long companyId) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByUuid_C(uuid, companyId);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<ObjectDefinition> objectDefinitions = findByUuid_C(
				uuid, companyId);

			objectDefinitions = InlineSQLHelperUtil.filter(objectDefinitions);

			return objectDefinitions.size();
		}

		uuid = Objects.toString(uuid, "");

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_OBJECTDEFINITION_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2_SQL);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindUuid) {
				queryPos.add(uuid);
			}

			queryPos.add(companyId);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_2_SQL =
		"objectDefinition.uuid_ = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3_SQL =
		"(objectDefinition.uuid_ IS NULL OR objectDefinition.uuid_ = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"objectDefinition.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;
	private CollectionPersistenceFinder<ObjectDefinition>
		_collectionPersistenceFinderByCompanyId;

	/**
	 * Returns all the object definitions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object definitions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object definitions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByCompanyId.find(
			finderCache, new Object[] {companyId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object definition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object definition
	 * @throws NoSuchObjectDefinitionException if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition findByCompanyId_First(
			long companyId,
			OrderByComparator<ObjectDefinition> orderByComparator)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (objectDefinition != null) {
			return objectDefinition;
		}

		throw new NoSuchObjectDefinitionException(
			_collectionPersistenceFinderByCompanyId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId}));
	}

	/**
	 * Returns the first object definition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object definition, or <code>null</code> if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition fetchByCompanyId_First(
		long companyId, OrderByComparator<ObjectDefinition> orderByComparator) {

		return _collectionPersistenceFinderByCompanyId.fetchFirst(
			finderCache, new Object[] {companyId}, orderByComparator);
	}

	/**
	 * Returns all the object definitions that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByCompanyId(long companyId) {
		return filterFindByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object definitions that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByCompanyId(
		long companyId, int start, int end) {

		return filterFindByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByCompanyId(companyId, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					orderByComparator));
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
			sb.append(_FILTER_SQL_SELECT_OBJECTDEFINITION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(
					ObjectDefinitionModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, ObjectDefinitionImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, ObjectDefinitionImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			return (List<ObjectDefinition>)QueryUtil.list(
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
	 * Removes all the object definitions where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		_collectionPersistenceFinderByCompanyId.remove(
			finderCache, new Object[] {companyId});
	}

	/**
	 * Returns the number of object definitions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching object definitions
	 */
	@Override
	public int countByCompanyId(long companyId) {
		return _collectionPersistenceFinderByCompanyId.count(
			finderCache, new Object[] {companyId});
	}

	/**
	 * Returns the number of object definitions that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching object definitions that the user has permission to view
	 */
	@Override
	public int filterCountByCompanyId(long companyId) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByCompanyId(companyId);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<ObjectDefinition> objectDefinitions = findByCompanyId(
				companyId);

			objectDefinitions = InlineSQLHelperUtil.filter(objectDefinitions);

			return objectDefinitions.size();
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_OBJECTDEFINITION_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"objectDefinition.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByObjectFolderId;
	private FinderPath _finderPathWithoutPaginationFindByObjectFolderId;
	private FinderPath _finderPathCountByObjectFolderId;
	private CollectionPersistenceFinder<ObjectDefinition>
		_collectionPersistenceFinderByObjectFolderId;

	/**
	 * Returns all the object definitions where objectFolderId = &#63;.
	 *
	 * @param objectFolderId the object folder ID
	 * @return the matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByObjectFolderId(long objectFolderId) {
		return findByObjectFolderId(
			objectFolderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object definitions where objectFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param objectFolderId the object folder ID
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByObjectFolderId(
		long objectFolderId, int start, int end) {

		return findByObjectFolderId(objectFolderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions where objectFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param objectFolderId the object folder ID
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByObjectFolderId(
		long objectFolderId, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		return findByObjectFolderId(
			objectFolderId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object definitions where objectFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param objectFolderId the object folder ID
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByObjectFolderId(
		long objectFolderId, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByObjectFolderId.find(
			finderCache, new Object[] {objectFolderId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object definition in the ordered set where objectFolderId = &#63;.
	 *
	 * @param objectFolderId the object folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object definition
	 * @throws NoSuchObjectDefinitionException if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition findByObjectFolderId_First(
			long objectFolderId,
			OrderByComparator<ObjectDefinition> orderByComparator)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = fetchByObjectFolderId_First(
			objectFolderId, orderByComparator);

		if (objectDefinition != null) {
			return objectDefinition;
		}

		throw new NoSuchObjectDefinitionException(
			_collectionPersistenceFinderByObjectFolderId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {objectFolderId}));
	}

	/**
	 * Returns the first object definition in the ordered set where objectFolderId = &#63;.
	 *
	 * @param objectFolderId the object folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object definition, or <code>null</code> if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition fetchByObjectFolderId_First(
		long objectFolderId,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		return _collectionPersistenceFinderByObjectFolderId.fetchFirst(
			finderCache, new Object[] {objectFolderId}, orderByComparator);
	}

	/**
	 * Returns all the object definitions that the user has permission to view where objectFolderId = &#63;.
	 *
	 * @param objectFolderId the object folder ID
	 * @return the matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByObjectFolderId(
		long objectFolderId) {

		return filterFindByObjectFolderId(
			objectFolderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object definitions that the user has permission to view where objectFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param objectFolderId the object folder ID
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByObjectFolderId(
		long objectFolderId, int start, int end) {

		return filterFindByObjectFolderId(objectFolderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions that the user has permissions to view where objectFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param objectFolderId the object folder ID
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByObjectFolderId(
		long objectFolderId, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByObjectFolderId(
				objectFolderId, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByObjectFolderId(
					objectFolderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					orderByComparator));
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
			sb.append(_FILTER_SQL_SELECT_OBJECTDEFINITION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_OBJECTFOLDERID_OBJECTFOLDERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(
					ObjectDefinitionModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, ObjectDefinitionImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, ObjectDefinitionImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(objectFolderId);

			return (List<ObjectDefinition>)QueryUtil.list(
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
	 * Removes all the object definitions where objectFolderId = &#63; from the database.
	 *
	 * @param objectFolderId the object folder ID
	 */
	@Override
	public void removeByObjectFolderId(long objectFolderId) {
		_collectionPersistenceFinderByObjectFolderId.remove(
			finderCache, new Object[] {objectFolderId});
	}

	/**
	 * Returns the number of object definitions where objectFolderId = &#63;.
	 *
	 * @param objectFolderId the object folder ID
	 * @return the number of matching object definitions
	 */
	@Override
	public int countByObjectFolderId(long objectFolderId) {
		return _collectionPersistenceFinderByObjectFolderId.count(
			finderCache, new Object[] {objectFolderId});
	}

	/**
	 * Returns the number of object definitions that the user has permission to view where objectFolderId = &#63;.
	 *
	 * @param objectFolderId the object folder ID
	 * @return the number of matching object definitions that the user has permission to view
	 */
	@Override
	public int filterCountByObjectFolderId(long objectFolderId) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByObjectFolderId(objectFolderId);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<ObjectDefinition> objectDefinitions = findByObjectFolderId(
				objectFolderId);

			objectDefinitions = InlineSQLHelperUtil.filter(objectDefinitions);

			return objectDefinitions.size();
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_OBJECTDEFINITION_WHERE);

		sb.append(_FINDER_COLUMN_OBJECTFOLDERID_OBJECTFOLDERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(objectFolderId);

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

	private static final String _FINDER_COLUMN_OBJECTFOLDERID_OBJECTFOLDERID_2 =
		"objectDefinition.objectFolderId = ?";

	private FinderPath _finderPathWithPaginationFindByAccountEntryRestricted;
	private FinderPath _finderPathWithoutPaginationFindByAccountEntryRestricted;
	private FinderPath _finderPathCountByAccountEntryRestricted;
	private CollectionPersistenceFinder<ObjectDefinition>
		_collectionPersistenceFinderByAccountEntryRestricted;

	/**
	 * Returns all the object definitions where accountEntryRestricted = &#63;.
	 *
	 * @param accountEntryRestricted the account entry restricted
	 * @return the matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByAccountEntryRestricted(
		boolean accountEntryRestricted) {

		return findByAccountEntryRestricted(
			accountEntryRestricted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object definitions where accountEntryRestricted = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryRestricted the account entry restricted
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByAccountEntryRestricted(
		boolean accountEntryRestricted, int start, int end) {

		return findByAccountEntryRestricted(
			accountEntryRestricted, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions where accountEntryRestricted = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryRestricted the account entry restricted
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByAccountEntryRestricted(
		boolean accountEntryRestricted, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		return findByAccountEntryRestricted(
			accountEntryRestricted, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object definitions where accountEntryRestricted = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryRestricted the account entry restricted
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByAccountEntryRestricted(
		boolean accountEntryRestricted, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByAccountEntryRestricted.find(
			finderCache, new Object[] {accountEntryRestricted}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object definition in the ordered set where accountEntryRestricted = &#63;.
	 *
	 * @param accountEntryRestricted the account entry restricted
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object definition
	 * @throws NoSuchObjectDefinitionException if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition findByAccountEntryRestricted_First(
			boolean accountEntryRestricted,
			OrderByComparator<ObjectDefinition> orderByComparator)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = fetchByAccountEntryRestricted_First(
			accountEntryRestricted, orderByComparator);

		if (objectDefinition != null) {
			return objectDefinition;
		}

		throw new NoSuchObjectDefinitionException(
			_collectionPersistenceFinderByAccountEntryRestricted.
				buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {accountEntryRestricted}));
	}

	/**
	 * Returns the first object definition in the ordered set where accountEntryRestricted = &#63;.
	 *
	 * @param accountEntryRestricted the account entry restricted
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object definition, or <code>null</code> if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition fetchByAccountEntryRestricted_First(
		boolean accountEntryRestricted,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		return _collectionPersistenceFinderByAccountEntryRestricted.fetchFirst(
			finderCache, new Object[] {accountEntryRestricted},
			orderByComparator);
	}

	/**
	 * Returns all the object definitions that the user has permission to view where accountEntryRestricted = &#63;.
	 *
	 * @param accountEntryRestricted the account entry restricted
	 * @return the matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByAccountEntryRestricted(
		boolean accountEntryRestricted) {

		return filterFindByAccountEntryRestricted(
			accountEntryRestricted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object definitions that the user has permission to view where accountEntryRestricted = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryRestricted the account entry restricted
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByAccountEntryRestricted(
		boolean accountEntryRestricted, int start, int end) {

		return filterFindByAccountEntryRestricted(
			accountEntryRestricted, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions that the user has permissions to view where accountEntryRestricted = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryRestricted the account entry restricted
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByAccountEntryRestricted(
		boolean accountEntryRestricted, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByAccountEntryRestricted(
				accountEntryRestricted, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByAccountEntryRestricted(
					accountEntryRestricted, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator));
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
			sb.append(_FILTER_SQL_SELECT_OBJECTDEFINITION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(
			_FINDER_COLUMN_ACCOUNTENTRYRESTRICTED_ACCOUNTENTRYRESTRICTED_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(
					ObjectDefinitionModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, ObjectDefinitionImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, ObjectDefinitionImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(accountEntryRestricted);

			return (List<ObjectDefinition>)QueryUtil.list(
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
	 * Removes all the object definitions where accountEntryRestricted = &#63; from the database.
	 *
	 * @param accountEntryRestricted the account entry restricted
	 */
	@Override
	public void removeByAccountEntryRestricted(boolean accountEntryRestricted) {
		_collectionPersistenceFinderByAccountEntryRestricted.remove(
			finderCache, new Object[] {accountEntryRestricted});
	}

	/**
	 * Returns the number of object definitions where accountEntryRestricted = &#63;.
	 *
	 * @param accountEntryRestricted the account entry restricted
	 * @return the number of matching object definitions
	 */
	@Override
	public int countByAccountEntryRestricted(boolean accountEntryRestricted) {
		return _collectionPersistenceFinderByAccountEntryRestricted.count(
			finderCache, new Object[] {accountEntryRestricted});
	}

	/**
	 * Returns the number of object definitions that the user has permission to view where accountEntryRestricted = &#63;.
	 *
	 * @param accountEntryRestricted the account entry restricted
	 * @return the number of matching object definitions that the user has permission to view
	 */
	@Override
	public int filterCountByAccountEntryRestricted(
		boolean accountEntryRestricted) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByAccountEntryRestricted(accountEntryRestricted);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<ObjectDefinition> objectDefinitions =
				findByAccountEntryRestricted(accountEntryRestricted);

			objectDefinitions = InlineSQLHelperUtil.filter(objectDefinitions);

			return objectDefinitions.size();
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_OBJECTDEFINITION_WHERE);

		sb.append(
			_FINDER_COLUMN_ACCOUNTENTRYRESTRICTED_ACCOUNTENTRYRESTRICTED_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(accountEntryRestricted);

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

	private static final String
		_FINDER_COLUMN_ACCOUNTENTRYRESTRICTED_ACCOUNTENTRYRESTRICTED_2 =
			"objectDefinition.accountEntryRestricted = ?";

	private FinderPath _finderPathFetchByClassName;
	private UniquePersistenceFinder<ObjectDefinition>
		_uniquePersistenceFinderByClassName;

	/**
	 * Returns the object definition where className = &#63; or throws a <code>NoSuchObjectDefinitionException</code> if it could not be found.
	 *
	 * @param className the class name
	 * @return the matching object definition
	 * @throws NoSuchObjectDefinitionException if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition findByClassName(String className)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = fetchByClassName(className);

		if (objectDefinition == null) {
			String message =
				_uniquePersistenceFinderByClassName.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY, new Object[] {className});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchObjectDefinitionException(message);
		}

		return objectDefinition;
	}

	/**
	 * Returns the object definition where className = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param className the class name
	 * @return the matching object definition, or <code>null</code> if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition fetchByClassName(String className) {
		return fetchByClassName(className, true);
	}

	/**
	 * Returns the object definition where className = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param className the class name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching object definition, or <code>null</code> if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition fetchByClassName(
		String className, boolean useFinderCache) {

		return _uniquePersistenceFinderByClassName.fetch(
			finderCache, new Object[] {className}, useFinderCache);
	}

	/**
	 * Removes the object definition where className = &#63; from the database.
	 *
	 * @param className the class name
	 * @return the object definition that was removed
	 */
	@Override
	public ObjectDefinition removeByClassName(String className)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = findByClassName(className);

		return remove(objectDefinition);
	}

	/**
	 * Returns the number of object definitions where className = &#63;.
	 *
	 * @param className the class name
	 * @return the number of matching object definitions
	 */
	@Override
	public int countByClassName(String className) {
		return _uniquePersistenceFinderByClassName.count(
			finderCache, new Object[] {className});
	}

	private FinderPath _finderPathWithPaginationFindBySystem;
	private FinderPath _finderPathWithoutPaginationFindBySystem;
	private FinderPath _finderPathCountBySystem;
	private CollectionPersistenceFinder<ObjectDefinition>
		_collectionPersistenceFinderBySystem;

	/**
	 * Returns all the object definitions where system = &#63;.
	 *
	 * @param system the system
	 * @return the matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findBySystem(boolean system) {
		return findBySystem(system, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object definitions where system = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param system the system
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findBySystem(
		boolean system, int start, int end) {

		return findBySystem(system, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions where system = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param system the system
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findBySystem(
		boolean system, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		return findBySystem(system, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object definitions where system = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param system the system
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findBySystem(
		boolean system, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderBySystem.find(
			finderCache, new Object[] {system}, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first object definition in the ordered set where system = &#63;.
	 *
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object definition
	 * @throws NoSuchObjectDefinitionException if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition findBySystem_First(
			boolean system,
			OrderByComparator<ObjectDefinition> orderByComparator)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = fetchBySystem_First(
			system, orderByComparator);

		if (objectDefinition != null) {
			return objectDefinition;
		}

		throw new NoSuchObjectDefinitionException(
			_collectionPersistenceFinderBySystem.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {system}));
	}

	/**
	 * Returns the first object definition in the ordered set where system = &#63;.
	 *
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object definition, or <code>null</code> if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition fetchBySystem_First(
		boolean system, OrderByComparator<ObjectDefinition> orderByComparator) {

		return _collectionPersistenceFinderBySystem.fetchFirst(
			finderCache, new Object[] {system}, orderByComparator);
	}

	/**
	 * Returns all the object definitions that the user has permission to view where system = &#63;.
	 *
	 * @param system the system
	 * @return the matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindBySystem(boolean system) {
		return filterFindBySystem(
			system, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object definitions that the user has permission to view where system = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param system the system
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindBySystem(
		boolean system, int start, int end) {

		return filterFindBySystem(system, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions that the user has permissions to view where system = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param system the system
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindBySystem(
		boolean system, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findBySystem(system, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findBySystem(
					system, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					orderByComparator));
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
			sb.append(_FILTER_SQL_SELECT_OBJECTDEFINITION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_SYSTEM_SYSTEM_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(
					ObjectDefinitionModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, ObjectDefinitionImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, ObjectDefinitionImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(system);

			return (List<ObjectDefinition>)QueryUtil.list(
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
	 * Removes all the object definitions where system = &#63; from the database.
	 *
	 * @param system the system
	 */
	@Override
	public void removeBySystem(boolean system) {
		_collectionPersistenceFinderBySystem.remove(
			finderCache, new Object[] {system});
	}

	/**
	 * Returns the number of object definitions where system = &#63;.
	 *
	 * @param system the system
	 * @return the number of matching object definitions
	 */
	@Override
	public int countBySystem(boolean system) {
		return _collectionPersistenceFinderBySystem.count(
			finderCache, new Object[] {system});
	}

	/**
	 * Returns the number of object definitions that the user has permission to view where system = &#63;.
	 *
	 * @param system the system
	 * @return the number of matching object definitions that the user has permission to view
	 */
	@Override
	public int filterCountBySystem(boolean system) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countBySystem(system);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<ObjectDefinition> objectDefinitions = findBySystem(system);

			objectDefinitions = InlineSQLHelperUtil.filter(objectDefinitions);

			return objectDefinitions.size();
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_OBJECTDEFINITION_WHERE);

		sb.append(_FINDER_COLUMN_SYSTEM_SYSTEM_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(system);

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

	private static final String _FINDER_COLUMN_SYSTEM_SYSTEM_2_SQL =
		"objectDefinition.system_ = ?";

	private FinderPath _finderPathWithPaginationFindByC_U;
	private FinderPath _finderPathWithoutPaginationFindByC_U;
	private FinderPath _finderPathCountByC_U;
	private CollectionPersistenceFinder<ObjectDefinition>
		_collectionPersistenceFinderByC_U;

	/**
	 * Returns all the object definitions where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByC_U(long companyId, long userId) {
		return findByC_U(
			companyId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object definitions where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByC_U(
		long companyId, long userId, int start, int end) {

		return findByC_U(companyId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByC_U(
		long companyId, long userId, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		return findByC_U(
			companyId, userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object definitions where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByC_U(
		long companyId, long userId, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByC_U.find(
			finderCache, new Object[] {companyId, userId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object definition in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object definition
	 * @throws NoSuchObjectDefinitionException if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition findByC_U_First(
			long companyId, long userId,
			OrderByComparator<ObjectDefinition> orderByComparator)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = fetchByC_U_First(
			companyId, userId, orderByComparator);

		if (objectDefinition != null) {
			return objectDefinition;
		}

		throw new NoSuchObjectDefinitionException(
			_collectionPersistenceFinderByC_U.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId, userId}));
	}

	/**
	 * Returns the first object definition in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object definition, or <code>null</code> if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition fetchByC_U_First(
		long companyId, long userId,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		return _collectionPersistenceFinderByC_U.fetchFirst(
			finderCache, new Object[] {companyId, userId}, orderByComparator);
	}

	/**
	 * Returns all the object definitions that the user has permission to view where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByC_U(long companyId, long userId) {
		return filterFindByC_U(
			companyId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object definitions that the user has permission to view where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByC_U(
		long companyId, long userId, int start, int end) {

		return filterFindByC_U(companyId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions that the user has permissions to view where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByC_U(
		long companyId, long userId, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_U(companyId, userId, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByC_U(
					companyId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					orderByComparator));
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
			sb.append(_FILTER_SQL_SELECT_OBJECTDEFINITION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_U_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_U_USERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(
					ObjectDefinitionModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, ObjectDefinitionImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, ObjectDefinitionImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			queryPos.add(userId);

			return (List<ObjectDefinition>)QueryUtil.list(
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
	 * Removes all the object definitions where companyId = &#63; and userId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 */
	@Override
	public void removeByC_U(long companyId, long userId) {
		_collectionPersistenceFinderByC_U.remove(
			finderCache, new Object[] {companyId, userId});
	}

	/**
	 * Returns the number of object definitions where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the number of matching object definitions
	 */
	@Override
	public int countByC_U(long companyId, long userId) {
		return _collectionPersistenceFinderByC_U.count(
			finderCache, new Object[] {companyId, userId});
	}

	/**
	 * Returns the number of object definitions that the user has permission to view where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the number of matching object definitions that the user has permission to view
	 */
	@Override
	public int filterCountByC_U(long companyId, long userId) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByC_U(companyId, userId);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<ObjectDefinition> objectDefinitions = findByC_U(
				companyId, userId);

			objectDefinitions = InlineSQLHelperUtil.filter(objectDefinitions);

			return objectDefinitions.size();
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_OBJECTDEFINITION_WHERE);

		sb.append(_FINDER_COLUMN_C_U_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_U_USERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

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

	private static final String _FINDER_COLUMN_C_U_COMPANYID_2 =
		"objectDefinition.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_U_USERID_2 =
		"objectDefinition.userId = ?";

	private FinderPath _finderPathFetchByC_C;
	private UniquePersistenceFinder<ObjectDefinition>
		_uniquePersistenceFinderByC_C;

	/**
	 * Returns the object definition where companyId = &#63; and className = &#63; or throws a <code>NoSuchObjectDefinitionException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param className the class name
	 * @return the matching object definition
	 * @throws NoSuchObjectDefinitionException if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition findByC_C(long companyId, String className)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = fetchByC_C(companyId, className);

		if (objectDefinition == null) {
			String message =
				_uniquePersistenceFinderByC_C.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {companyId, className});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchObjectDefinitionException(message);
		}

		return objectDefinition;
	}

	/**
	 * Returns the object definition where companyId = &#63; and className = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param className the class name
	 * @return the matching object definition, or <code>null</code> if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition fetchByC_C(long companyId, String className) {
		return fetchByC_C(companyId, className, true);
	}

	/**
	 * Returns the object definition where companyId = &#63; and className = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param className the class name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching object definition, or <code>null</code> if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition fetchByC_C(
		long companyId, String className, boolean useFinderCache) {

		return _uniquePersistenceFinderByC_C.fetch(
			finderCache, new Object[] {companyId, className}, useFinderCache);
	}

	/**
	 * Removes the object definition where companyId = &#63; and className = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param className the class name
	 * @return the object definition that was removed
	 */
	@Override
	public ObjectDefinition removeByC_C(long companyId, String className)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = findByC_C(companyId, className);

		return remove(objectDefinition);
	}

	/**
	 * Returns the number of object definitions where companyId = &#63; and className = &#63;.
	 *
	 * @param companyId the company ID
	 * @param className the class name
	 * @return the number of matching object definitions
	 */
	@Override
	public int countByC_C(long companyId, String className) {
		return _uniquePersistenceFinderByC_C.count(
			finderCache, new Object[] {companyId, className});
	}

	private FinderPath _finderPathFetchByC_N;
	private UniquePersistenceFinder<ObjectDefinition>
		_uniquePersistenceFinderByC_N;

	/**
	 * Returns the object definition where companyId = &#63; and name = &#63; or throws a <code>NoSuchObjectDefinitionException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching object definition
	 * @throws NoSuchObjectDefinitionException if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition findByC_N(long companyId, String name)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = fetchByC_N(companyId, name);

		if (objectDefinition == null) {
			String message =
				_uniquePersistenceFinderByC_N.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId, name});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchObjectDefinitionException(message);
		}

		return objectDefinition;
	}

	/**
	 * Returns the object definition where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching object definition, or <code>null</code> if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition fetchByC_N(long companyId, String name) {
		return fetchByC_N(companyId, name, true);
	}

	/**
	 * Returns the object definition where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching object definition, or <code>null</code> if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition fetchByC_N(
		long companyId, String name, boolean useFinderCache) {

		return _uniquePersistenceFinderByC_N.fetch(
			finderCache, new Object[] {companyId, name}, useFinderCache);
	}

	/**
	 * Removes the object definition where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the object definition that was removed
	 */
	@Override
	public ObjectDefinition removeByC_N(long companyId, String name)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = findByC_N(companyId, name);

		return remove(objectDefinition);
	}

	/**
	 * Returns the number of object definitions where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching object definitions
	 */
	@Override
	public int countByC_N(long companyId, String name) {
		return _uniquePersistenceFinderByC_N.count(
			finderCache, new Object[] {companyId, name});
	}

	private FinderPath _finderPathWithPaginationFindByC_S;
	private FinderPath _finderPathWithoutPaginationFindByC_S;
	private FinderPath _finderPathCountByC_S;
	private CollectionPersistenceFinder<ObjectDefinition>
		_collectionPersistenceFinderByC_S;

	/**
	 * Returns all the object definitions where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByC_S(long companyId, int status) {
		return findByC_S(
			companyId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object definitions where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByC_S(
		long companyId, int status, int start, int end) {

		return findByC_S(companyId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByC_S(
		long companyId, int status, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		return findByC_S(
			companyId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object definitions where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByC_S(
		long companyId, int status, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByC_S.find(
			finderCache, new Object[] {companyId, status}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object definition in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object definition
	 * @throws NoSuchObjectDefinitionException if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition findByC_S_First(
			long companyId, int status,
			OrderByComparator<ObjectDefinition> orderByComparator)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = fetchByC_S_First(
			companyId, status, orderByComparator);

		if (objectDefinition != null) {
			return objectDefinition;
		}

		throw new NoSuchObjectDefinitionException(
			_collectionPersistenceFinderByC_S.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId, status}));
	}

	/**
	 * Returns the first object definition in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object definition, or <code>null</code> if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition fetchByC_S_First(
		long companyId, int status,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		return _collectionPersistenceFinderByC_S.fetchFirst(
			finderCache, new Object[] {companyId, status}, orderByComparator);
	}

	/**
	 * Returns all the object definitions that the user has permission to view where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByC_S(long companyId, int status) {
		return filterFindByC_S(
			companyId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object definitions that the user has permission to view where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByC_S(
		long companyId, int status, int start, int end) {

		return filterFindByC_S(companyId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions that the user has permissions to view where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByC_S(
		long companyId, int status, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_S(companyId, status, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByC_S(
					companyId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					orderByComparator));
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
			sb.append(_FILTER_SQL_SELECT_OBJECTDEFINITION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_S_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(
					ObjectDefinitionModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, ObjectDefinitionImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, ObjectDefinitionImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			queryPos.add(status);

			return (List<ObjectDefinition>)QueryUtil.list(
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
	 * Removes all the object definitions where companyId = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 */
	@Override
	public void removeByC_S(long companyId, int status) {
		_collectionPersistenceFinderByC_S.remove(
			finderCache, new Object[] {companyId, status});
	}

	/**
	 * Returns the number of object definitions where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching object definitions
	 */
	@Override
	public int countByC_S(long companyId, int status) {
		return _collectionPersistenceFinderByC_S.count(
			finderCache, new Object[] {companyId, status});
	}

	/**
	 * Returns the number of object definitions that the user has permission to view where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching object definitions that the user has permission to view
	 */
	@Override
	public int filterCountByC_S(long companyId, int status) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByC_S(companyId, status);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<ObjectDefinition> objectDefinitions = findByC_S(
				companyId, status);

			objectDefinitions = InlineSQLHelperUtil.filter(objectDefinitions);

			return objectDefinitions.size();
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_OBJECTDEFINITION_WHERE);

		sb.append(_FINDER_COLUMN_C_S_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

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

	private static final String _FINDER_COLUMN_C_S_COMPANYID_2 =
		"objectDefinition.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_S_STATUS_2 =
		"objectDefinition.status = ?";

	private FinderPath _finderPathWithPaginationFindByS_S;
	private FinderPath _finderPathWithoutPaginationFindByS_S;
	private FinderPath _finderPathCountByS_S;
	private CollectionPersistenceFinder<ObjectDefinition>
		_collectionPersistenceFinderByS_S;

	/**
	 * Returns all the object definitions where system = &#63; and status = &#63;.
	 *
	 * @param system the system
	 * @param status the status
	 * @return the matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByS_S(boolean system, int status) {
		return findByS_S(
			system, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object definitions where system = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param system the system
	 * @param status the status
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByS_S(
		boolean system, int status, int start, int end) {

		return findByS_S(system, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions where system = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param system the system
	 * @param status the status
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByS_S(
		boolean system, int status, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		return findByS_S(system, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object definitions where system = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param system the system
	 * @param status the status
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByS_S(
		boolean system, int status, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByS_S.find(
			finderCache, new Object[] {system, status}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object definition in the ordered set where system = &#63; and status = &#63;.
	 *
	 * @param system the system
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object definition
	 * @throws NoSuchObjectDefinitionException if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition findByS_S_First(
			boolean system, int status,
			OrderByComparator<ObjectDefinition> orderByComparator)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = fetchByS_S_First(
			system, status, orderByComparator);

		if (objectDefinition != null) {
			return objectDefinition;
		}

		throw new NoSuchObjectDefinitionException(
			_collectionPersistenceFinderByS_S.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {system, status}));
	}

	/**
	 * Returns the first object definition in the ordered set where system = &#63; and status = &#63;.
	 *
	 * @param system the system
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object definition, or <code>null</code> if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition fetchByS_S_First(
		boolean system, int status,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		return _collectionPersistenceFinderByS_S.fetchFirst(
			finderCache, new Object[] {system, status}, orderByComparator);
	}

	/**
	 * Returns all the object definitions that the user has permission to view where system = &#63; and status = &#63;.
	 *
	 * @param system the system
	 * @param status the status
	 * @return the matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByS_S(boolean system, int status) {
		return filterFindByS_S(
			system, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object definitions that the user has permission to view where system = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param system the system
	 * @param status the status
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByS_S(
		boolean system, int status, int start, int end) {

		return filterFindByS_S(system, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions that the user has permissions to view where system = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param system the system
	 * @param status the status
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByS_S(
		boolean system, int status, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByS_S(system, status, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByS_S(
					system, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					orderByComparator));
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
			sb.append(_FILTER_SQL_SELECT_OBJECTDEFINITION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_S_S_SYSTEM_2_SQL);

		sb.append(_FINDER_COLUMN_S_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(
					ObjectDefinitionModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, ObjectDefinitionImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, ObjectDefinitionImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(system);

			queryPos.add(status);

			return (List<ObjectDefinition>)QueryUtil.list(
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
	 * Removes all the object definitions where system = &#63; and status = &#63; from the database.
	 *
	 * @param system the system
	 * @param status the status
	 */
	@Override
	public void removeByS_S(boolean system, int status) {
		_collectionPersistenceFinderByS_S.remove(
			finderCache, new Object[] {system, status});
	}

	/**
	 * Returns the number of object definitions where system = &#63; and status = &#63;.
	 *
	 * @param system the system
	 * @param status the status
	 * @return the number of matching object definitions
	 */
	@Override
	public int countByS_S(boolean system, int status) {
		return _collectionPersistenceFinderByS_S.count(
			finderCache, new Object[] {system, status});
	}

	/**
	 * Returns the number of object definitions that the user has permission to view where system = &#63; and status = &#63;.
	 *
	 * @param system the system
	 * @param status the status
	 * @return the number of matching object definitions that the user has permission to view
	 */
	@Override
	public int filterCountByS_S(boolean system, int status) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByS_S(system, status);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<ObjectDefinition> objectDefinitions = findByS_S(
				system, status);

			objectDefinitions = InlineSQLHelperUtil.filter(objectDefinitions);

			return objectDefinitions.size();
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_OBJECTDEFINITION_WHERE);

		sb.append(_FINDER_COLUMN_S_S_SYSTEM_2_SQL);

		sb.append(_FINDER_COLUMN_S_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(system);

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

	private static final String _FINDER_COLUMN_S_S_SYSTEM_2_SQL =
		"objectDefinition.system_ = ? AND ";

	private static final String _FINDER_COLUMN_S_S_STATUS_2 =
		"objectDefinition.status = ?";

	private FinderPath _finderPathWithPaginationFindByC_A_S;
	private FinderPath _finderPathWithoutPaginationFindByC_A_S;
	private FinderPath _finderPathCountByC_A_S;
	private CollectionPersistenceFinder<ObjectDefinition>
		_collectionPersistenceFinderByC_A_S;

	/**
	 * Returns all the object definitions where companyId = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param status the status
	 * @return the matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByC_A_S(
		long companyId, boolean active, int status) {

		return findByC_A_S(
			companyId, active, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the object definitions where companyId = &#63; and active = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param status the status
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByC_A_S(
		long companyId, boolean active, int status, int start, int end) {

		return findByC_A_S(companyId, active, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions where companyId = &#63; and active = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param status the status
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByC_A_S(
		long companyId, boolean active, int status, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		return findByC_A_S(
			companyId, active, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object definitions where companyId = &#63; and active = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param status the status
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByC_A_S(
		long companyId, boolean active, int status, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByC_A_S.find(
			finderCache, new Object[] {companyId, active, status}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object definition in the ordered set where companyId = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object definition
	 * @throws NoSuchObjectDefinitionException if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition findByC_A_S_First(
			long companyId, boolean active, int status,
			OrderByComparator<ObjectDefinition> orderByComparator)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = fetchByC_A_S_First(
			companyId, active, status, orderByComparator);

		if (objectDefinition != null) {
			return objectDefinition;
		}

		throw new NoSuchObjectDefinitionException(
			_collectionPersistenceFinderByC_A_S.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {companyId, active, status}));
	}

	/**
	 * Returns the first object definition in the ordered set where companyId = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object definition, or <code>null</code> if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition fetchByC_A_S_First(
		long companyId, boolean active, int status,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		return _collectionPersistenceFinderByC_A_S.fetchFirst(
			finderCache, new Object[] {companyId, active, status},
			orderByComparator);
	}

	/**
	 * Returns all the object definitions that the user has permission to view where companyId = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param status the status
	 * @return the matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByC_A_S(
		long companyId, boolean active, int status) {

		return filterFindByC_A_S(
			companyId, active, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the object definitions that the user has permission to view where companyId = &#63; and active = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param status the status
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByC_A_S(
		long companyId, boolean active, int status, int start, int end) {

		return filterFindByC_A_S(companyId, active, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions that the user has permissions to view where companyId = &#63; and active = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param status the status
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByC_A_S(
		long companyId, boolean active, int status, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_A_S(
				companyId, active, status, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByC_A_S(
					companyId, active, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator));
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
			sb.append(_FILTER_SQL_SELECT_OBJECTDEFINITION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_A_S_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_A_S_ACTIVE_2_SQL);

		sb.append(_FINDER_COLUMN_C_A_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(
					ObjectDefinitionModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, ObjectDefinitionImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, ObjectDefinitionImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			queryPos.add(active);

			queryPos.add(status);

			return (List<ObjectDefinition>)QueryUtil.list(
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
	 * Removes all the object definitions where companyId = &#63; and active = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param status the status
	 */
	@Override
	public void removeByC_A_S(long companyId, boolean active, int status) {
		_collectionPersistenceFinderByC_A_S.remove(
			finderCache, new Object[] {companyId, active, status});
	}

	/**
	 * Returns the number of object definitions where companyId = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param status the status
	 * @return the number of matching object definitions
	 */
	@Override
	public int countByC_A_S(long companyId, boolean active, int status) {
		return _collectionPersistenceFinderByC_A_S.count(
			finderCache, new Object[] {companyId, active, status});
	}

	/**
	 * Returns the number of object definitions that the user has permission to view where companyId = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param status the status
	 * @return the number of matching object definitions that the user has permission to view
	 */
	@Override
	public int filterCountByC_A_S(long companyId, boolean active, int status) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByC_A_S(companyId, active, status);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<ObjectDefinition> objectDefinitions = findByC_A_S(
				companyId, active, status);

			objectDefinitions = InlineSQLHelperUtil.filter(objectDefinitions);

			return objectDefinitions.size();
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_OBJECTDEFINITION_WHERE);

		sb.append(_FINDER_COLUMN_C_A_S_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_A_S_ACTIVE_2_SQL);

		sb.append(_FINDER_COLUMN_C_A_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			queryPos.add(active);

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

	private static final String _FINDER_COLUMN_C_A_S_COMPANYID_2 =
		"objectDefinition.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_A_S_ACTIVE_2_SQL =
		"objectDefinition.active_ = ? AND ";

	private static final String _FINDER_COLUMN_C_A_S_STATUS_2 =
		"objectDefinition.status = ?";

	private FinderPath _finderPathWithPaginationFindByC_M_S;
	private FinderPath _finderPathWithoutPaginationFindByC_M_S;
	private FinderPath _finderPathCountByC_M_S;
	private CollectionPersistenceFinder<ObjectDefinition>
		_collectionPersistenceFinderByC_M_S;

	/**
	 * Returns all the object definitions where companyId = &#63; and modifiable = &#63; and system = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiable the modifiable
	 * @param system the system
	 * @return the matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByC_M_S(
		long companyId, boolean modifiable, boolean system) {

		return findByC_M_S(
			companyId, modifiable, system, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the object definitions where companyId = &#63; and modifiable = &#63; and system = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param modifiable the modifiable
	 * @param system the system
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByC_M_S(
		long companyId, boolean modifiable, boolean system, int start,
		int end) {

		return findByC_M_S(companyId, modifiable, system, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions where companyId = &#63; and modifiable = &#63; and system = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param modifiable the modifiable
	 * @param system the system
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByC_M_S(
		long companyId, boolean modifiable, boolean system, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		return findByC_M_S(
			companyId, modifiable, system, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object definitions where companyId = &#63; and modifiable = &#63; and system = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param modifiable the modifiable
	 * @param system the system
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByC_M_S(
		long companyId, boolean modifiable, boolean system, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByC_M_S.find(
			finderCache, new Object[] {companyId, modifiable, system}, start,
			end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object definition in the ordered set where companyId = &#63; and modifiable = &#63; and system = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiable the modifiable
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object definition
	 * @throws NoSuchObjectDefinitionException if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition findByC_M_S_First(
			long companyId, boolean modifiable, boolean system,
			OrderByComparator<ObjectDefinition> orderByComparator)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = fetchByC_M_S_First(
			companyId, modifiable, system, orderByComparator);

		if (objectDefinition != null) {
			return objectDefinition;
		}

		throw new NoSuchObjectDefinitionException(
			_collectionPersistenceFinderByC_M_S.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {companyId, modifiable, system}));
	}

	/**
	 * Returns the first object definition in the ordered set where companyId = &#63; and modifiable = &#63; and system = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiable the modifiable
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object definition, or <code>null</code> if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition fetchByC_M_S_First(
		long companyId, boolean modifiable, boolean system,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		return _collectionPersistenceFinderByC_M_S.fetchFirst(
			finderCache, new Object[] {companyId, modifiable, system},
			orderByComparator);
	}

	/**
	 * Returns all the object definitions that the user has permission to view where companyId = &#63; and modifiable = &#63; and system = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiable the modifiable
	 * @param system the system
	 * @return the matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByC_M_S(
		long companyId, boolean modifiable, boolean system) {

		return filterFindByC_M_S(
			companyId, modifiable, system, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the object definitions that the user has permission to view where companyId = &#63; and modifiable = &#63; and system = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param modifiable the modifiable
	 * @param system the system
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByC_M_S(
		long companyId, boolean modifiable, boolean system, int start,
		int end) {

		return filterFindByC_M_S(
			companyId, modifiable, system, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions that the user has permissions to view where companyId = &#63; and modifiable = &#63; and system = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param modifiable the modifiable
	 * @param system the system
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByC_M_S(
		long companyId, boolean modifiable, boolean system, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_M_S(
				companyId, modifiable, system, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByC_M_S(
					companyId, modifiable, system, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator));
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
			sb.append(_FILTER_SQL_SELECT_OBJECTDEFINITION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_M_S_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_M_S_MODIFIABLE_2);

		sb.append(_FINDER_COLUMN_C_M_S_SYSTEM_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(
					ObjectDefinitionModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, ObjectDefinitionImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, ObjectDefinitionImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			queryPos.add(modifiable);

			queryPos.add(system);

			return (List<ObjectDefinition>)QueryUtil.list(
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
	 * Removes all the object definitions where companyId = &#63; and modifiable = &#63; and system = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param modifiable the modifiable
	 * @param system the system
	 */
	@Override
	public void removeByC_M_S(
		long companyId, boolean modifiable, boolean system) {

		_collectionPersistenceFinderByC_M_S.remove(
			finderCache, new Object[] {companyId, modifiable, system});
	}

	/**
	 * Returns the number of object definitions where companyId = &#63; and modifiable = &#63; and system = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiable the modifiable
	 * @param system the system
	 * @return the number of matching object definitions
	 */
	@Override
	public int countByC_M_S(
		long companyId, boolean modifiable, boolean system) {

		return _collectionPersistenceFinderByC_M_S.count(
			finderCache, new Object[] {companyId, modifiable, system});
	}

	/**
	 * Returns the number of object definitions that the user has permission to view where companyId = &#63; and modifiable = &#63; and system = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiable the modifiable
	 * @param system the system
	 * @return the number of matching object definitions that the user has permission to view
	 */
	@Override
	public int filterCountByC_M_S(
		long companyId, boolean modifiable, boolean system) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByC_M_S(companyId, modifiable, system);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<ObjectDefinition> objectDefinitions = findByC_M_S(
				companyId, modifiable, system);

			objectDefinitions = InlineSQLHelperUtil.filter(objectDefinitions);

			return objectDefinitions.size();
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_OBJECTDEFINITION_WHERE);

		sb.append(_FINDER_COLUMN_C_M_S_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_M_S_MODIFIABLE_2);

		sb.append(_FINDER_COLUMN_C_M_S_SYSTEM_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			queryPos.add(modifiable);

			queryPos.add(system);

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

	private static final String _FINDER_COLUMN_C_M_S_COMPANYID_2 =
		"objectDefinition.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_M_S_MODIFIABLE_2 =
		"objectDefinition.modifiable = ? AND ";

	private static final String _FINDER_COLUMN_C_M_S_SYSTEM_2_SQL =
		"objectDefinition.system_ = ?";

	private FinderPath _finderPathWithPaginationFindByC_A_S_S;
	private FinderPath _finderPathWithoutPaginationFindByC_A_S_S;
	private FinderPath _finderPathCountByC_A_S_S;
	private CollectionPersistenceFinder<ObjectDefinition>
		_collectionPersistenceFinderByC_A_S_S;

	/**
	 * Returns all the object definitions where companyId = &#63; and active = &#63; and system = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param system the system
	 * @param status the status
	 * @return the matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByC_A_S_S(
		long companyId, boolean active, boolean system, int status) {

		return findByC_A_S_S(
			companyId, active, system, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object definitions where companyId = &#63; and active = &#63; and system = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param system the system
	 * @param status the status
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByC_A_S_S(
		long companyId, boolean active, boolean system, int status, int start,
		int end) {

		return findByC_A_S_S(
			companyId, active, system, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions where companyId = &#63; and active = &#63; and system = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param system the system
	 * @param status the status
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByC_A_S_S(
		long companyId, boolean active, boolean system, int status, int start,
		int end, OrderByComparator<ObjectDefinition> orderByComparator) {

		return findByC_A_S_S(
			companyId, active, system, status, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the object definitions where companyId = &#63; and active = &#63; and system = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param system the system
	 * @param status the status
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByC_A_S_S(
		long companyId, boolean active, boolean system, int status, int start,
		int end, OrderByComparator<ObjectDefinition> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByC_A_S_S.find(
			finderCache, new Object[] {companyId, active, system, status},
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object definition in the ordered set where companyId = &#63; and active = &#63; and system = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param system the system
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object definition
	 * @throws NoSuchObjectDefinitionException if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition findByC_A_S_S_First(
			long companyId, boolean active, boolean system, int status,
			OrderByComparator<ObjectDefinition> orderByComparator)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = fetchByC_A_S_S_First(
			companyId, active, system, status, orderByComparator);

		if (objectDefinition != null) {
			return objectDefinition;
		}

		throw new NoSuchObjectDefinitionException(
			_collectionPersistenceFinderByC_A_S_S.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {companyId, active, system, status}));
	}

	/**
	 * Returns the first object definition in the ordered set where companyId = &#63; and active = &#63; and system = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param system the system
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object definition, or <code>null</code> if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition fetchByC_A_S_S_First(
		long companyId, boolean active, boolean system, int status,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		return _collectionPersistenceFinderByC_A_S_S.fetchFirst(
			finderCache, new Object[] {companyId, active, system, status},
			orderByComparator);
	}

	/**
	 * Returns all the object definitions that the user has permission to view where companyId = &#63; and active = &#63; and system = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param system the system
	 * @param status the status
	 * @return the matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByC_A_S_S(
		long companyId, boolean active, boolean system, int status) {

		return filterFindByC_A_S_S(
			companyId, active, system, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object definitions that the user has permission to view where companyId = &#63; and active = &#63; and system = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param system the system
	 * @param status the status
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByC_A_S_S(
		long companyId, boolean active, boolean system, int status, int start,
		int end) {

		return filterFindByC_A_S_S(
			companyId, active, system, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions that the user has permissions to view where companyId = &#63; and active = &#63; and system = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param system the system
	 * @param status the status
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByC_A_S_S(
		long companyId, boolean active, boolean system, int status, int start,
		int end, OrderByComparator<ObjectDefinition> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_A_S_S(
				companyId, active, system, status, start, end,
				orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByC_A_S_S(
					companyId, active, system, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator));
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
			sb.append(_FILTER_SQL_SELECT_OBJECTDEFINITION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_A_S_S_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_A_S_S_ACTIVE_2_SQL);

		sb.append(_FINDER_COLUMN_C_A_S_S_SYSTEM_2_SQL);

		sb.append(_FINDER_COLUMN_C_A_S_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(
					ObjectDefinitionModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, ObjectDefinitionImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, ObjectDefinitionImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			queryPos.add(active);

			queryPos.add(system);

			queryPos.add(status);

			return (List<ObjectDefinition>)QueryUtil.list(
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
	 * Removes all the object definitions where companyId = &#63; and active = &#63; and system = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param system the system
	 * @param status the status
	 */
	@Override
	public void removeByC_A_S_S(
		long companyId, boolean active, boolean system, int status) {

		_collectionPersistenceFinderByC_A_S_S.remove(
			finderCache, new Object[] {companyId, active, system, status});
	}

	/**
	 * Returns the number of object definitions where companyId = &#63; and active = &#63; and system = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param system the system
	 * @param status the status
	 * @return the number of matching object definitions
	 */
	@Override
	public int countByC_A_S_S(
		long companyId, boolean active, boolean system, int status) {

		return _collectionPersistenceFinderByC_A_S_S.count(
			finderCache, new Object[] {companyId, active, system, status});
	}

	/**
	 * Returns the number of object definitions that the user has permission to view where companyId = &#63; and active = &#63; and system = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param system the system
	 * @param status the status
	 * @return the number of matching object definitions that the user has permission to view
	 */
	@Override
	public int filterCountByC_A_S_S(
		long companyId, boolean active, boolean system, int status) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByC_A_S_S(companyId, active, system, status);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<ObjectDefinition> objectDefinitions = findByC_A_S_S(
				companyId, active, system, status);

			objectDefinitions = InlineSQLHelperUtil.filter(objectDefinitions);

			return objectDefinitions.size();
		}

		StringBundler sb = new StringBundler(5);

		sb.append(_FILTER_SQL_COUNT_OBJECTDEFINITION_WHERE);

		sb.append(_FINDER_COLUMN_C_A_S_S_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_A_S_S_ACTIVE_2_SQL);

		sb.append(_FINDER_COLUMN_C_A_S_S_SYSTEM_2_SQL);

		sb.append(_FINDER_COLUMN_C_A_S_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			queryPos.add(active);

			queryPos.add(system);

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

	private static final String _FINDER_COLUMN_C_A_S_S_COMPANYID_2 =
		"objectDefinition.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_A_S_S_ACTIVE_2_SQL =
		"objectDefinition.active_ = ? AND ";

	private static final String _FINDER_COLUMN_C_A_S_S_SYSTEM_2_SQL =
		"objectDefinition.system_ = ? AND ";

	private static final String _FINDER_COLUMN_C_A_S_S_STATUS_2 =
		"objectDefinition.status = ?";

	private FinderPath _finderPathWithPaginationFindByC_OFI_A_E_S_S;
	private FinderPath _finderPathWithoutPaginationFindByC_OFI_A_E_S_S;
	private FinderPath _finderPathCountByC_OFI_A_E_S_S;
	private FinderPath _finderPathWithPaginationCountByC_OFI_A_E_S_S;

	/**
	 * Returns all the object definitions where companyId = &#63; and objectFolderId = &#63; and active = &#63; and enableObjectEntryDraft = &#63; and scope = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param objectFolderId the object folder ID
	 * @param active the active
	 * @param enableObjectEntryDraft the enable object entry draft
	 * @param scope the scope
	 * @param status the status
	 * @return the matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByC_OFI_A_E_S_S(
		long companyId, long objectFolderId, boolean active,
		boolean enableObjectEntryDraft, String scope, int status) {

		return findByC_OFI_A_E_S_S(
			companyId, objectFolderId, active, enableObjectEntryDraft, scope,
			status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object definitions where companyId = &#63; and objectFolderId = &#63; and active = &#63; and enableObjectEntryDraft = &#63; and scope = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param objectFolderId the object folder ID
	 * @param active the active
	 * @param enableObjectEntryDraft the enable object entry draft
	 * @param scope the scope
	 * @param status the status
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByC_OFI_A_E_S_S(
		long companyId, long objectFolderId, boolean active,
		boolean enableObjectEntryDraft, String scope, int status, int start,
		int end) {

		return findByC_OFI_A_E_S_S(
			companyId, objectFolderId, active, enableObjectEntryDraft, scope,
			status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions where companyId = &#63; and objectFolderId = &#63; and active = &#63; and enableObjectEntryDraft = &#63; and scope = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param objectFolderId the object folder ID
	 * @param active the active
	 * @param enableObjectEntryDraft the enable object entry draft
	 * @param scope the scope
	 * @param status the status
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByC_OFI_A_E_S_S(
		long companyId, long objectFolderId, boolean active,
		boolean enableObjectEntryDraft, String scope, int status, int start,
		int end, OrderByComparator<ObjectDefinition> orderByComparator) {

		return findByC_OFI_A_E_S_S(
			companyId, objectFolderId, active, enableObjectEntryDraft, scope,
			status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object definitions where companyId = &#63; and objectFolderId = &#63; and active = &#63; and enableObjectEntryDraft = &#63; and scope = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param objectFolderId the object folder ID
	 * @param active the active
	 * @param enableObjectEntryDraft the enable object entry draft
	 * @param scope the scope
	 * @param status the status
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByC_OFI_A_E_S_S(
		long companyId, long objectFolderId, boolean active,
		boolean enableObjectEntryDraft, String scope, int status, int start,
		int end, OrderByComparator<ObjectDefinition> orderByComparator,
		boolean useFinderCache) {

		scope = Objects.toString(scope, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_OFI_A_E_S_S;
				finderArgs = new Object[] {
					companyId, objectFolderId, active, enableObjectEntryDraft,
					scope, status
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_OFI_A_E_S_S;
			finderArgs = new Object[] {
				companyId, objectFolderId, active, enableObjectEntryDraft,
				scope, status, start, end, orderByComparator
			};
		}

		List<ObjectDefinition> list = null;

		if (useFinderCache) {
			list = (List<ObjectDefinition>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectDefinition objectDefinition : list) {
					if ((companyId != objectDefinition.getCompanyId()) ||
						(objectFolderId !=
							objectDefinition.getObjectFolderId()) ||
						(active != objectDefinition.isActive()) ||
						(enableObjectEntryDraft !=
							objectDefinition.isEnableObjectEntryDraft()) ||
						!scope.equals(objectDefinition.getScope()) ||
						(status != objectDefinition.getStatus())) {

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
					8 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(8);
			}

			sb.append(_SQL_SELECT_OBJECTDEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_OBJECTFOLDERID_2);

			sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_ACTIVE_2);

			sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_ENABLEOBJECTENTRYDRAFT_2);

			boolean bindScope = false;

			if (scope.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_SCOPE_3);
			}
			else {
				bindScope = true;

				sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_SCOPE_2);
			}

			sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
			}
			else {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(objectFolderId);

				queryPos.add(active);

				queryPos.add(enableObjectEntryDraft);

				if (bindScope) {
					queryPos.add(scope);
				}

				queryPos.add(status);

				list = (List<ObjectDefinition>)QueryUtil.list(
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
	 * Returns the first object definition in the ordered set where companyId = &#63; and objectFolderId = &#63; and active = &#63; and enableObjectEntryDraft = &#63; and scope = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param objectFolderId the object folder ID
	 * @param active the active
	 * @param enableObjectEntryDraft the enable object entry draft
	 * @param scope the scope
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object definition
	 * @throws NoSuchObjectDefinitionException if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition findByC_OFI_A_E_S_S_First(
			long companyId, long objectFolderId, boolean active,
			boolean enableObjectEntryDraft, String scope, int status,
			OrderByComparator<ObjectDefinition> orderByComparator)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = fetchByC_OFI_A_E_S_S_First(
			companyId, objectFolderId, active, enableObjectEntryDraft, scope,
			status, orderByComparator);

		if (objectDefinition != null) {
			return objectDefinition;
		}

		StringBundler sb = new StringBundler(14);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", objectFolderId=");
		sb.append(objectFolderId);

		sb.append(", active=");
		sb.append(active);

		sb.append(", enableObjectEntryDraft=");
		sb.append(enableObjectEntryDraft);

		sb.append(", scope=");
		sb.append(scope);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchObjectDefinitionException(sb.toString());
	}

	/**
	 * Returns the first object definition in the ordered set where companyId = &#63; and objectFolderId = &#63; and active = &#63; and enableObjectEntryDraft = &#63; and scope = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param objectFolderId the object folder ID
	 * @param active the active
	 * @param enableObjectEntryDraft the enable object entry draft
	 * @param scope the scope
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object definition, or <code>null</code> if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition fetchByC_OFI_A_E_S_S_First(
		long companyId, long objectFolderId, boolean active,
		boolean enableObjectEntryDraft, String scope, int status,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		List<ObjectDefinition> list = findByC_OFI_A_E_S_S(
			companyId, objectFolderId, active, enableObjectEntryDraft, scope,
			status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the object definitions that the user has permission to view where companyId = &#63; and objectFolderId = &#63; and active = &#63; and enableObjectEntryDraft = &#63; and scope = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param objectFolderId the object folder ID
	 * @param active the active
	 * @param enableObjectEntryDraft the enable object entry draft
	 * @param scope the scope
	 * @param status the status
	 * @return the matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByC_OFI_A_E_S_S(
		long companyId, long objectFolderId, boolean active,
		boolean enableObjectEntryDraft, String scope, int status) {

		return filterFindByC_OFI_A_E_S_S(
			companyId, objectFolderId, active, enableObjectEntryDraft, scope,
			status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object definitions that the user has permission to view where companyId = &#63; and objectFolderId = &#63; and active = &#63; and enableObjectEntryDraft = &#63; and scope = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param objectFolderId the object folder ID
	 * @param active the active
	 * @param enableObjectEntryDraft the enable object entry draft
	 * @param scope the scope
	 * @param status the status
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByC_OFI_A_E_S_S(
		long companyId, long objectFolderId, boolean active,
		boolean enableObjectEntryDraft, String scope, int status, int start,
		int end) {

		return filterFindByC_OFI_A_E_S_S(
			companyId, objectFolderId, active, enableObjectEntryDraft, scope,
			status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions that the user has permissions to view where companyId = &#63; and objectFolderId = &#63; and active = &#63; and enableObjectEntryDraft = &#63; and scope = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param objectFolderId the object folder ID
	 * @param active the active
	 * @param enableObjectEntryDraft the enable object entry draft
	 * @param scope the scope
	 * @param status the status
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByC_OFI_A_E_S_S(
		long companyId, long objectFolderId, boolean active,
		boolean enableObjectEntryDraft, String scope, int status, int start,
		int end, OrderByComparator<ObjectDefinition> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_OFI_A_E_S_S(
				companyId, objectFolderId, active, enableObjectEntryDraft,
				scope, status, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByC_OFI_A_E_S_S(
					companyId, objectFolderId, active, enableObjectEntryDraft,
					scope, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					orderByComparator));
		}

		scope = Objects.toString(scope, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				8 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(9);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_OBJECTDEFINITION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_OBJECTFOLDERID_2);

		sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_ACTIVE_2_SQL);

		sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_ENABLEOBJECTENTRYDRAFT_2);

		boolean bindScope = false;

		if (scope.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_SCOPE_3);
		}
		else {
			bindScope = true;

			sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_SCOPE_2);
		}

		sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(
					ObjectDefinitionModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, ObjectDefinitionImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, ObjectDefinitionImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			queryPos.add(objectFolderId);

			queryPos.add(active);

			queryPos.add(enableObjectEntryDraft);

			if (bindScope) {
				queryPos.add(scope);
			}

			queryPos.add(status);

			return (List<ObjectDefinition>)QueryUtil.list(
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
	 * Returns all the object definitions that the user has permission to view where companyId = &#63; and objectFolderId = any &#63; and active = &#63; and enableObjectEntryDraft = &#63; and scope = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param objectFolderIds the object folder IDs
	 * @param active the active
	 * @param enableObjectEntryDraft the enable object entry draft
	 * @param scope the scope
	 * @param status the status
	 * @return the matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByC_OFI_A_E_S_S(
		long companyId, long[] objectFolderIds, boolean active,
		boolean enableObjectEntryDraft, String scope, int status) {

		return filterFindByC_OFI_A_E_S_S(
			companyId, objectFolderIds, active, enableObjectEntryDraft, scope,
			status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object definitions that the user has permission to view where companyId = &#63; and objectFolderId = any &#63; and active = &#63; and enableObjectEntryDraft = &#63; and scope = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param objectFolderIds the object folder IDs
	 * @param active the active
	 * @param enableObjectEntryDraft the enable object entry draft
	 * @param scope the scope
	 * @param status the status
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByC_OFI_A_E_S_S(
		long companyId, long[] objectFolderIds, boolean active,
		boolean enableObjectEntryDraft, String scope, int status, int start,
		int end) {

		return filterFindByC_OFI_A_E_S_S(
			companyId, objectFolderIds, active, enableObjectEntryDraft, scope,
			status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions that the user has permission to view where companyId = &#63; and objectFolderId = any &#63; and active = &#63; and enableObjectEntryDraft = &#63; and scope = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param objectFolderIds the object folder IDs
	 * @param active the active
	 * @param enableObjectEntryDraft the enable object entry draft
	 * @param scope the scope
	 * @param status the status
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByC_OFI_A_E_S_S(
		long companyId, long[] objectFolderIds, boolean active,
		boolean enableObjectEntryDraft, String scope, int status, int start,
		int end, OrderByComparator<ObjectDefinition> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_OFI_A_E_S_S(
				companyId, objectFolderIds, active, enableObjectEntryDraft,
				scope, status, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByC_OFI_A_E_S_S(
					companyId, objectFolderIds, active, enableObjectEntryDraft,
					scope, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					orderByComparator));
		}

		if (objectFolderIds == null) {
			objectFolderIds = new long[0];
		}
		else if (objectFolderIds.length > 1) {
			objectFolderIds = ArrayUtil.sortedUnique(objectFolderIds);
		}

		scope = Objects.toString(scope, "");

		StringBundler sb = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_OBJECTDEFINITION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_COMPANYID_2);

		if (objectFolderIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_OBJECTFOLDERID_7);

			sb.append(StringUtil.merge(objectFolderIds));

			sb.append(")");

			sb.append(")");

			sb.append(WHERE_AND);
		}

		sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_ACTIVE_2_SQL);

		sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_ENABLEOBJECTENTRYDRAFT_2);

		boolean bindScope = false;

		if (scope.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_SCOPE_3);
		}
		else {
			bindScope = true;

			sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_SCOPE_2);
		}

		sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_STATUS_2);

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(
					ObjectDefinitionModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, ObjectDefinitionImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, ObjectDefinitionImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			queryPos.add(active);

			queryPos.add(enableObjectEntryDraft);

			if (bindScope) {
				queryPos.add(scope);
			}

			queryPos.add(status);

			return (List<ObjectDefinition>)QueryUtil.list(
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
	 * Returns all the object definitions where companyId = &#63; and objectFolderId = any &#63; and active = &#63; and enableObjectEntryDraft = &#63; and scope = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param objectFolderIds the object folder IDs
	 * @param active the active
	 * @param enableObjectEntryDraft the enable object entry draft
	 * @param scope the scope
	 * @param status the status
	 * @return the matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByC_OFI_A_E_S_S(
		long companyId, long[] objectFolderIds, boolean active,
		boolean enableObjectEntryDraft, String scope, int status) {

		return findByC_OFI_A_E_S_S(
			companyId, objectFolderIds, active, enableObjectEntryDraft, scope,
			status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object definitions where companyId = &#63; and objectFolderId = any &#63; and active = &#63; and enableObjectEntryDraft = &#63; and scope = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param objectFolderIds the object folder IDs
	 * @param active the active
	 * @param enableObjectEntryDraft the enable object entry draft
	 * @param scope the scope
	 * @param status the status
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByC_OFI_A_E_S_S(
		long companyId, long[] objectFolderIds, boolean active,
		boolean enableObjectEntryDraft, String scope, int status, int start,
		int end) {

		return findByC_OFI_A_E_S_S(
			companyId, objectFolderIds, active, enableObjectEntryDraft, scope,
			status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions where companyId = &#63; and objectFolderId = any &#63; and active = &#63; and enableObjectEntryDraft = &#63; and scope = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param objectFolderIds the object folder IDs
	 * @param active the active
	 * @param enableObjectEntryDraft the enable object entry draft
	 * @param scope the scope
	 * @param status the status
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByC_OFI_A_E_S_S(
		long companyId, long[] objectFolderIds, boolean active,
		boolean enableObjectEntryDraft, String scope, int status, int start,
		int end, OrderByComparator<ObjectDefinition> orderByComparator) {

		return findByC_OFI_A_E_S_S(
			companyId, objectFolderIds, active, enableObjectEntryDraft, scope,
			status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object definitions where companyId = &#63; and objectFolderId = &#63; and active = &#63; and enableObjectEntryDraft = &#63; and scope = &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param objectFolderIds the object folder IDs
	 * @param active the active
	 * @param enableObjectEntryDraft the enable object entry draft
	 * @param scope the scope
	 * @param status the status
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByC_OFI_A_E_S_S(
		long companyId, long[] objectFolderIds, boolean active,
		boolean enableObjectEntryDraft, String scope, int status, int start,
		int end, OrderByComparator<ObjectDefinition> orderByComparator,
		boolean useFinderCache) {

		if (objectFolderIds == null) {
			objectFolderIds = new long[0];
		}
		else if (objectFolderIds.length > 1) {
			objectFolderIds = ArrayUtil.sortedUnique(objectFolderIds);
		}

		scope = Objects.toString(scope, "");

		if (objectFolderIds.length == 1) {
			return findByC_OFI_A_E_S_S(
				companyId, objectFolderIds[0], active, enableObjectEntryDraft,
				scope, status, start, end, orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {
					companyId, StringUtil.merge(objectFolderIds), active,
					enableObjectEntryDraft, scope, status
				};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				companyId, StringUtil.merge(objectFolderIds), active,
				enableObjectEntryDraft, scope, status, start, end,
				orderByComparator
			};
		}

		List<ObjectDefinition> list = null;

		if (useFinderCache) {
			list = (List<ObjectDefinition>)finderCache.getResult(
				_finderPathWithPaginationFindByC_OFI_A_E_S_S, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectDefinition objectDefinition : list) {
					if ((companyId != objectDefinition.getCompanyId()) ||
						!ArrayUtil.contains(
							objectFolderIds,
							objectDefinition.getObjectFolderId()) ||
						(active != objectDefinition.isActive()) ||
						(enableObjectEntryDraft !=
							objectDefinition.isEnableObjectEntryDraft()) ||
						!scope.equals(objectDefinition.getScope()) ||
						(status != objectDefinition.getStatus())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_OBJECTDEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_COMPANYID_2);

			if (objectFolderIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_OBJECTFOLDERID_7);

				sb.append(StringUtil.merge(objectFolderIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_ACTIVE_2);

			sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_ENABLEOBJECTENTRYDRAFT_2);

			boolean bindScope = false;

			if (scope.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_SCOPE_3);
			}
			else {
				bindScope = true;

				sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_SCOPE_2);
			}

			sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_STATUS_2);

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
			}
			else {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(active);

				queryPos.add(enableObjectEntryDraft);

				if (bindScope) {
					queryPos.add(scope);
				}

				queryPos.add(status);

				list = (List<ObjectDefinition>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByC_OFI_A_E_S_S,
						finderArgs, list);
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
	 * Removes all the object definitions where companyId = &#63; and objectFolderId = &#63; and active = &#63; and enableObjectEntryDraft = &#63; and scope = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param objectFolderId the object folder ID
	 * @param active the active
	 * @param enableObjectEntryDraft the enable object entry draft
	 * @param scope the scope
	 * @param status the status
	 */
	@Override
	public void removeByC_OFI_A_E_S_S(
		long companyId, long objectFolderId, boolean active,
		boolean enableObjectEntryDraft, String scope, int status) {

		for (ObjectDefinition objectDefinition :
				findByC_OFI_A_E_S_S(
					companyId, objectFolderId, active, enableObjectEntryDraft,
					scope, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(objectDefinition);
		}
	}

	/**
	 * Returns the number of object definitions where companyId = &#63; and objectFolderId = &#63; and active = &#63; and enableObjectEntryDraft = &#63; and scope = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param objectFolderId the object folder ID
	 * @param active the active
	 * @param enableObjectEntryDraft the enable object entry draft
	 * @param scope the scope
	 * @param status the status
	 * @return the number of matching object definitions
	 */
	@Override
	public int countByC_OFI_A_E_S_S(
		long companyId, long objectFolderId, boolean active,
		boolean enableObjectEntryDraft, String scope, int status) {

		scope = Objects.toString(scope, "");

		FinderPath finderPath = _finderPathCountByC_OFI_A_E_S_S;

		Object[] finderArgs = new Object[] {
			companyId, objectFolderId, active, enableObjectEntryDraft, scope,
			status
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(7);

			sb.append(_SQL_COUNT_OBJECTDEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_OBJECTFOLDERID_2);

			sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_ACTIVE_2);

			sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_ENABLEOBJECTENTRYDRAFT_2);

			boolean bindScope = false;

			if (scope.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_SCOPE_3);
			}
			else {
				bindScope = true;

				sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_SCOPE_2);
			}

			sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(objectFolderId);

				queryPos.add(active);

				queryPos.add(enableObjectEntryDraft);

				if (bindScope) {
					queryPos.add(scope);
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

	/**
	 * Returns the number of object definitions where companyId = &#63; and objectFolderId = any &#63; and active = &#63; and enableObjectEntryDraft = &#63; and scope = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param objectFolderIds the object folder IDs
	 * @param active the active
	 * @param enableObjectEntryDraft the enable object entry draft
	 * @param scope the scope
	 * @param status the status
	 * @return the number of matching object definitions
	 */
	@Override
	public int countByC_OFI_A_E_S_S(
		long companyId, long[] objectFolderIds, boolean active,
		boolean enableObjectEntryDraft, String scope, int status) {

		if (objectFolderIds == null) {
			objectFolderIds = new long[0];
		}
		else if (objectFolderIds.length > 1) {
			objectFolderIds = ArrayUtil.sortedUnique(objectFolderIds);
		}

		scope = Objects.toString(scope, "");

		Object[] finderArgs = new Object[] {
			companyId, StringUtil.merge(objectFolderIds), active,
			enableObjectEntryDraft, scope, status
		};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByC_OFI_A_E_S_S, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_OBJECTDEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_COMPANYID_2);

			if (objectFolderIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_OBJECTFOLDERID_7);

				sb.append(StringUtil.merge(objectFolderIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_ACTIVE_2);

			sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_ENABLEOBJECTENTRYDRAFT_2);

			boolean bindScope = false;

			if (scope.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_SCOPE_3);
			}
			else {
				bindScope = true;

				sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_SCOPE_2);
			}

			sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_STATUS_2);

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(active);

				queryPos.add(enableObjectEntryDraft);

				if (bindScope) {
					queryPos.add(scope);
				}

				queryPos.add(status);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathWithPaginationCountByC_OFI_A_E_S_S, finderArgs,
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

	/**
	 * Returns the number of object definitions that the user has permission to view where companyId = &#63; and objectFolderId = &#63; and active = &#63; and enableObjectEntryDraft = &#63; and scope = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param objectFolderId the object folder ID
	 * @param active the active
	 * @param enableObjectEntryDraft the enable object entry draft
	 * @param scope the scope
	 * @param status the status
	 * @return the number of matching object definitions that the user has permission to view
	 */
	@Override
	public int filterCountByC_OFI_A_E_S_S(
		long companyId, long objectFolderId, boolean active,
		boolean enableObjectEntryDraft, String scope, int status) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByC_OFI_A_E_S_S(
				companyId, objectFolderId, active, enableObjectEntryDraft,
				scope, status);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<ObjectDefinition> objectDefinitions = findByC_OFI_A_E_S_S(
				companyId, objectFolderId, active, enableObjectEntryDraft,
				scope, status);

			objectDefinitions = InlineSQLHelperUtil.filter(objectDefinitions);

			return objectDefinitions.size();
		}

		scope = Objects.toString(scope, "");

		StringBundler sb = new StringBundler(7);

		sb.append(_FILTER_SQL_COUNT_OBJECTDEFINITION_WHERE);

		sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_OBJECTFOLDERID_2);

		sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_ACTIVE_2_SQL);

		sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_ENABLEOBJECTENTRYDRAFT_2);

		boolean bindScope = false;

		if (scope.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_SCOPE_3);
		}
		else {
			bindScope = true;

			sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_SCOPE_2);
		}

		sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			queryPos.add(objectFolderId);

			queryPos.add(active);

			queryPos.add(enableObjectEntryDraft);

			if (bindScope) {
				queryPos.add(scope);
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
	 * Returns the number of object definitions that the user has permission to view where companyId = &#63; and objectFolderId = any &#63; and active = &#63; and enableObjectEntryDraft = &#63; and scope = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param objectFolderIds the object folder IDs
	 * @param active the active
	 * @param enableObjectEntryDraft the enable object entry draft
	 * @param scope the scope
	 * @param status the status
	 * @return the number of matching object definitions that the user has permission to view
	 */
	@Override
	public int filterCountByC_OFI_A_E_S_S(
		long companyId, long[] objectFolderIds, boolean active,
		boolean enableObjectEntryDraft, String scope, int status) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByC_OFI_A_E_S_S(
				companyId, objectFolderIds, active, enableObjectEntryDraft,
				scope, status);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<ObjectDefinition> objectDefinitions =
				InlineSQLHelperUtil.filter(
					findByC_OFI_A_E_S_S(
						companyId, objectFolderIds, active,
						enableObjectEntryDraft, scope, status));

			return objectDefinitions.size();
		}

		if (objectFolderIds == null) {
			objectFolderIds = new long[0];
		}
		else if (objectFolderIds.length > 1) {
			objectFolderIds = ArrayUtil.sortedUnique(objectFolderIds);
		}

		scope = Objects.toString(scope, "");

		StringBundler sb = new StringBundler();

		sb.append(_FILTER_SQL_COUNT_OBJECTDEFINITION_WHERE);

		sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_COMPANYID_2);

		if (objectFolderIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_OBJECTFOLDERID_7);

			sb.append(StringUtil.merge(objectFolderIds));

			sb.append(")");

			sb.append(")");

			sb.append(WHERE_AND);
		}

		sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_ACTIVE_2_SQL);

		sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_ENABLEOBJECTENTRYDRAFT_2);

		boolean bindScope = false;

		if (scope.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_SCOPE_3);
		}
		else {
			bindScope = true;

			sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_SCOPE_2);
		}

		sb.append(_FINDER_COLUMN_C_OFI_A_E_S_S_STATUS_2);

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			queryPos.add(active);

			queryPos.add(enableObjectEntryDraft);

			if (bindScope) {
				queryPos.add(scope);
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

	private static final String _FINDER_COLUMN_C_OFI_A_E_S_S_COMPANYID_2 =
		"objectDefinition.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_OFI_A_E_S_S_OBJECTFOLDERID_2 =
		"objectDefinition.objectFolderId = ? AND ";

	private static final String _FINDER_COLUMN_C_OFI_A_E_S_S_OBJECTFOLDERID_7 =
		"objectDefinition.objectFolderId IN (";

	private static final String _FINDER_COLUMN_C_OFI_A_E_S_S_ACTIVE_2 =
		"objectDefinition.active = ? AND ";

	private static final String _FINDER_COLUMN_C_OFI_A_E_S_S_ACTIVE_2_SQL =
		"objectDefinition.active_ = ? AND ";

	private static final String
		_FINDER_COLUMN_C_OFI_A_E_S_S_ENABLEOBJECTENTRYDRAFT_2 =
			"objectDefinition.enableObjectEntryDraft = ? AND ";

	private static final String _FINDER_COLUMN_C_OFI_A_E_S_S_SCOPE_2 =
		"objectDefinition.scope = ? AND ";

	private static final String _FINDER_COLUMN_C_OFI_A_E_S_S_SCOPE_3 =
		"(objectDefinition.scope IS NULL OR objectDefinition.scope = '') AND ";

	private static final String _FINDER_COLUMN_C_OFI_A_E_S_S_STATUS_2 =
		"objectDefinition.status = ?";

	private FinderPath _finderPathFetchByERC_C;
	private UniquePersistenceFinder<ObjectDefinition>
		_uniquePersistenceFinderByERC_C;

	/**
	 * Returns the object definition where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchObjectDefinitionException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching object definition
	 * @throws NoSuchObjectDefinitionException if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition findByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = fetchByERC_C(
			externalReferenceCode, companyId);

		if (objectDefinition == null) {
			String message =
				_uniquePersistenceFinderByERC_C.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {externalReferenceCode, companyId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchObjectDefinitionException(message);
		}

		return objectDefinition;
	}

	/**
	 * Returns the object definition where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching object definition, or <code>null</code> if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition fetchByERC_C(
		String externalReferenceCode, long companyId) {

		return fetchByERC_C(externalReferenceCode, companyId, true);
	}

	/**
	 * Returns the object definition where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching object definition, or <code>null</code> if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache) {

		return _uniquePersistenceFinderByERC_C.fetch(
			finderCache, new Object[] {externalReferenceCode, companyId},
			useFinderCache);
	}

	/**
	 * Removes the object definition where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the object definition that was removed
	 */
	@Override
	public ObjectDefinition removeByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = findByERC_C(
			externalReferenceCode, companyId);

		return remove(objectDefinition);
	}

	/**
	 * Returns the number of object definitions where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching object definitions
	 */
	@Override
	public int countByERC_C(String externalReferenceCode, long companyId) {
		return _uniquePersistenceFinderByERC_C.count(
			finderCache, new Object[] {externalReferenceCode, companyId});
	}

	public ObjectDefinitionPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put(
			"accountEntryRestrictedObjectFieldId", "accountERObjectFieldId");
		dbColumnNames.put("active", "active_");
		dbColumnNames.put("system", "system_");

		setDBColumnNames(dbColumnNames);

		setModelClass(ObjectDefinition.class);

		setModelImplClass(ObjectDefinitionImpl.class);
		setModelPKClass(long.class);

		setTable(ObjectDefinitionTable.INSTANCE);
	}

	/**
	 * Caches the object definition in the entity cache if it is enabled.
	 *
	 * @param objectDefinition the object definition
	 */
	@Override
	public void cacheResult(ObjectDefinition objectDefinition) {
		entityCache.putResult(
			ObjectDefinitionImpl.class, objectDefinition.getPrimaryKey(),
			objectDefinition);

		finderCache.putResult(
			_finderPathFetchByClassName,
			new Object[] {objectDefinition.getClassName()}, objectDefinition);

		finderCache.putResult(
			_finderPathFetchByC_C,
			new Object[] {
				objectDefinition.getCompanyId(), objectDefinition.getClassName()
			},
			objectDefinition);

		finderCache.putResult(
			_finderPathFetchByC_N,
			new Object[] {
				objectDefinition.getCompanyId(), objectDefinition.getName()
			},
			objectDefinition);

		finderCache.putResult(
			_finderPathFetchByERC_C,
			new Object[] {
				objectDefinition.getExternalReferenceCode(),
				objectDefinition.getCompanyId()
			},
			objectDefinition);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the object definitions in the entity cache if it is enabled.
	 *
	 * @param objectDefinitions the object definitions
	 */
	@Override
	public void cacheResult(List<ObjectDefinition> objectDefinitions) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (objectDefinitions.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ObjectDefinition objectDefinition : objectDefinitions) {
			if (entityCache.getResult(
					ObjectDefinitionImpl.class,
					objectDefinition.getPrimaryKey()) == null) {

				cacheResult(objectDefinition);
			}
		}
	}

	protected void cacheUniqueFindersCache(
		ObjectDefinitionModelImpl objectDefinitionModelImpl) {

		Object[] args = new Object[] {objectDefinitionModelImpl.getClassName()};

		finderCache.putResult(
			_finderPathFetchByClassName, args, objectDefinitionModelImpl);

		args = new Object[] {
			objectDefinitionModelImpl.getCompanyId(),
			objectDefinitionModelImpl.getClassName()
		};

		finderCache.putResult(
			_finderPathFetchByC_C, args, objectDefinitionModelImpl);

		args = new Object[] {
			objectDefinitionModelImpl.getCompanyId(),
			objectDefinitionModelImpl.getName()
		};

		finderCache.putResult(
			_finderPathFetchByC_N, args, objectDefinitionModelImpl);

		args = new Object[] {
			objectDefinitionModelImpl.getExternalReferenceCode(),
			objectDefinitionModelImpl.getCompanyId()
		};

		finderCache.putResult(
			_finderPathFetchByERC_C, args, objectDefinitionModelImpl);
	}

	/**
	 * Creates a new object definition with the primary key. Does not add the object definition to the database.
	 *
	 * @param objectDefinitionId the primary key for the new object definition
	 * @return the new object definition
	 */
	@Override
	public ObjectDefinition create(long objectDefinitionId) {
		ObjectDefinition objectDefinition = new ObjectDefinitionImpl();

		objectDefinition.setNew(true);
		objectDefinition.setPrimaryKey(objectDefinitionId);

		String uuid = PortalUUIDUtil.generate();

		objectDefinition.setUuid(uuid);

		objectDefinition.setCompanyId(CompanyThreadLocal.getCompanyId());

		return objectDefinition;
	}

	/**
	 * Removes the object definition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectDefinitionId the primary key of the object definition
	 * @return the object definition that was removed
	 * @throws NoSuchObjectDefinitionException if a object definition with the primary key could not be found
	 */
	@Override
	public ObjectDefinition remove(long objectDefinitionId)
		throws NoSuchObjectDefinitionException {

		return remove((Serializable)objectDefinitionId);
	}

	@Override
	protected ObjectDefinition removeImpl(ObjectDefinition objectDefinition) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(objectDefinition)) {
				objectDefinition = (ObjectDefinition)session.get(
					ObjectDefinitionImpl.class,
					objectDefinition.getPrimaryKeyObj());
			}

			if (objectDefinition != null) {
				session.delete(objectDefinition);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (objectDefinition != null) {
			clearCache(objectDefinition);
		}

		return objectDefinition;
	}

	@Override
	public ObjectDefinition updateImpl(ObjectDefinition objectDefinition) {
		boolean isNew = objectDefinition.isNew();

		if (!(objectDefinition instanceof ObjectDefinitionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(objectDefinition.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					objectDefinition);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in objectDefinition proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ObjectDefinition implementation " +
					objectDefinition.getClass());
		}

		ObjectDefinitionModelImpl objectDefinitionModelImpl =
			(ObjectDefinitionModelImpl)objectDefinition;

		if (Validator.isNull(objectDefinition.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			objectDefinition.setUuid(uuid);
		}

		if (Validator.isNull(objectDefinition.getExternalReferenceCode())) {
			objectDefinition.setExternalReferenceCode(
				objectDefinition.getUuid());
		}
		else {
			if (!Objects.equals(
					objectDefinitionModelImpl.getColumnOriginalValue(
						"externalReferenceCode"),
					objectDefinition.getExternalReferenceCode())) {

				long userId = GetterUtil.getLong(
					PrincipalThreadLocal.getName());

				if (userId > 0) {
					long companyId = objectDefinition.getCompanyId();

					long groupId = 0;

					long classPK = 0;

					if (!isNew) {
						classPK = objectDefinition.getPrimaryKey();
					}

					try {
						objectDefinition.setExternalReferenceCode(
							SanitizerUtil.sanitize(
								companyId, groupId, userId,
								ObjectDefinition.class.getName(), classPK,
								ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL,
								objectDefinition.getExternalReferenceCode(),
								null));
					}
					catch (SanitizerException sanitizerException) {
						throw new SystemException(sanitizerException);
					}
				}
			}

			ObjectDefinition ercObjectDefinition = fetchByERC_C(
				objectDefinition.getExternalReferenceCode(),
				objectDefinition.getCompanyId());

			if (isNew) {
				if (ercObjectDefinition != null) {
					throw new DuplicateObjectDefinitionExternalReferenceCodeException(
						"Duplicate object definition with external reference code " +
							objectDefinition.getExternalReferenceCode() +
								" and company " +
									objectDefinition.getCompanyId());
				}
			}
			else {
				if ((ercObjectDefinition != null) &&
					(objectDefinition.getObjectDefinitionId() !=
						ercObjectDefinition.getObjectDefinitionId())) {

					throw new DuplicateObjectDefinitionExternalReferenceCodeException(
						"Duplicate object definition with external reference code " +
							objectDefinition.getExternalReferenceCode() +
								" and company " +
									objectDefinition.getCompanyId());
				}
			}
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (objectDefinition.getCreateDate() == null)) {
			if (serviceContext == null) {
				objectDefinition.setCreateDate(date);
			}
			else {
				objectDefinition.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!objectDefinitionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				objectDefinition.setModifiedDate(date);
			}
			else {
				objectDefinition.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(objectDefinition);
			}
			else {
				objectDefinition = (ObjectDefinition)session.merge(
					objectDefinition);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			ObjectDefinitionImpl.class, objectDefinitionModelImpl, false, true);

		cacheUniqueFindersCache(objectDefinitionModelImpl);

		if (isNew) {
			objectDefinition.setNew(false);
		}

		objectDefinition.resetOriginalValues();

		return objectDefinition;
	}

	/**
	 * Returns the object definition with the primary key or throws a <code>NoSuchObjectDefinitionException</code> if it could not be found.
	 *
	 * @param objectDefinitionId the primary key of the object definition
	 * @return the object definition
	 * @throws NoSuchObjectDefinitionException if a object definition with the primary key could not be found
	 */
	@Override
	public ObjectDefinition findByPrimaryKey(long objectDefinitionId)
		throws NoSuchObjectDefinitionException {

		return findByPrimaryKey((Serializable)objectDefinitionId);
	}

	/**
	 * Returns the object definition with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectDefinitionId the primary key of the object definition
	 * @return the object definition, or <code>null</code> if a object definition with the primary key could not be found
	 */
	@Override
	public ObjectDefinition fetchByPrimaryKey(long objectDefinitionId) {
		return fetchByPrimaryKey((Serializable)objectDefinitionId);
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
		return "objectDefinitionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_OBJECTDEFINITION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ObjectDefinitionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the object definition persistence.
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
			_SQL_SELECT_OBJECTDEFINITION_WHERE,
			_SQL_COUNT_OBJECTDEFINITION_WHERE,
			ObjectDefinitionModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
			new FinderColumn<>(
				"objectDefinition.", "uuid", FinderColumn.Type.STRING, "=",
				true, true, ObjectDefinition::getUuid));

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
				_finderPathCountByUuid_C, _SQL_SELECT_OBJECTDEFINITION_WHERE,
				_SQL_COUNT_OBJECTDEFINITION_WHERE,
				ObjectDefinitionModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"objectDefinition.", "uuid", FinderColumn.Type.STRING, "=",
					true, false, ObjectDefinition::getUuid),
				new FinderColumn<>(
					"objectDefinition.", "companyId", FinderColumn.Type.LONG,
					"=", true, true, ObjectDefinition::getCompanyId));

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
				_finderPathCountByCompanyId, _SQL_SELECT_OBJECTDEFINITION_WHERE,
				_SQL_COUNT_OBJECTDEFINITION_WHERE,
				ObjectDefinitionModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"objectDefinition.", "companyId", FinderColumn.Type.LONG,
					"=", true, true, ObjectDefinition::getCompanyId));

		_finderPathWithPaginationFindByObjectFolderId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByObjectFolderId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"objectFolderId"}, true);

		_finderPathWithoutPaginationFindByObjectFolderId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByObjectFolderId",
			new String[] {Long.class.getName()},
			new String[] {"objectFolderId"}, true);

		_finderPathCountByObjectFolderId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByObjectFolderId",
			new String[] {Long.class.getName()},
			new String[] {"objectFolderId"}, false);

		_collectionPersistenceFinderByObjectFolderId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByObjectFolderId,
				_finderPathWithoutPaginationFindByObjectFolderId,
				_finderPathCountByObjectFolderId,
				_SQL_SELECT_OBJECTDEFINITION_WHERE,
				_SQL_COUNT_OBJECTDEFINITION_WHERE,
				ObjectDefinitionModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"objectDefinition.", "objectFolderId",
					FinderColumn.Type.LONG, "=", true, true,
					ObjectDefinition::getObjectFolderId));

		_finderPathWithPaginationFindByAccountEntryRestricted = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByAccountEntryRestricted",
			new String[] {
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"accountEntryRestricted"}, true);

		_finderPathWithoutPaginationFindByAccountEntryRestricted =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByAccountEntryRestricted",
				new String[] {Boolean.class.getName()},
				new String[] {"accountEntryRestricted"}, true);

		_finderPathCountByAccountEntryRestricted = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByAccountEntryRestricted",
			new String[] {Boolean.class.getName()},
			new String[] {"accountEntryRestricted"}, false);

		_collectionPersistenceFinderByAccountEntryRestricted =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByAccountEntryRestricted,
				_finderPathWithoutPaginationFindByAccountEntryRestricted,
				_finderPathCountByAccountEntryRestricted,
				_SQL_SELECT_OBJECTDEFINITION_WHERE,
				_SQL_COUNT_OBJECTDEFINITION_WHERE,
				ObjectDefinitionModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"objectDefinition.", "accountEntryRestricted",
					FinderColumn.Type.BOOLEAN, "=", true, true,
					ObjectDefinition::isAccountEntryRestricted));

		_finderPathFetchByClassName = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByClassName",
			new String[] {String.class.getName()}, new String[] {"className"},
			true);

		_uniquePersistenceFinderByClassName = new UniquePersistenceFinder<>(
			this, _finderPathFetchByClassName,
			_SQL_SELECT_OBJECTDEFINITION_WHERE,
			new FinderColumn<>(
				"objectDefinition.", "className", FinderColumn.Type.STRING, "=",
				true, true, ObjectDefinition::getClassName));

		_finderPathWithPaginationFindBySystem = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBySystem",
			new String[] {
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"system_"}, true);

		_finderPathWithoutPaginationFindBySystem = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBySystem",
			new String[] {Boolean.class.getName()}, new String[] {"system_"},
			true);

		_finderPathCountBySystem = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBySystem",
			new String[] {Boolean.class.getName()}, new String[] {"system_"},
			false);

		_collectionPersistenceFinderBySystem =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindBySystem,
				_finderPathWithoutPaginationFindBySystem,
				_finderPathCountBySystem, _SQL_SELECT_OBJECTDEFINITION_WHERE,
				_SQL_COUNT_OBJECTDEFINITION_WHERE,
				ObjectDefinitionModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"objectDefinition.", "system", FinderColumn.Type.BOOLEAN,
					"=", true, true, ObjectDefinition::isSystem));

		_finderPathWithPaginationFindByC_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "userId"}, true);

		_finderPathWithoutPaginationFindByC_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_U",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"companyId", "userId"}, true);

		_finderPathCountByC_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_U",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"companyId", "userId"}, false);

		_collectionPersistenceFinderByC_U = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByC_U,
			_finderPathWithoutPaginationFindByC_U, _finderPathCountByC_U,
			_SQL_SELECT_OBJECTDEFINITION_WHERE,
			_SQL_COUNT_OBJECTDEFINITION_WHERE,
			ObjectDefinitionModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
			new FinderColumn<>(
				"objectDefinition.", "companyId", FinderColumn.Type.LONG, "=",
				true, false, ObjectDefinition::getCompanyId),
			new FinderColumn<>(
				"objectDefinition.", "userId", FinderColumn.Type.LONG, "=",
				true, true, ObjectDefinition::getUserId));

		_finderPathFetchByC_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "className"}, true);

		_uniquePersistenceFinderByC_C = new UniquePersistenceFinder<>(
			this, _finderPathFetchByC_C, _SQL_SELECT_OBJECTDEFINITION_WHERE,
			new FinderColumn<>(
				"objectDefinition.", "companyId", FinderColumn.Type.LONG, "=",
				true, false, ObjectDefinition::getCompanyId),
			new FinderColumn<>(
				"objectDefinition.", "className", FinderColumn.Type.STRING, "=",
				true, true, ObjectDefinition::getClassName));

		_finderPathFetchByC_N = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "name"}, true);

		_finderPathFetchByC_N.touch();

		_uniquePersistenceFinderByC_N = new UniquePersistenceFinder<>(
			this, _finderPathFetchByC_N, _SQL_SELECT_OBJECTDEFINITION_WHERE,
			new FinderColumn<>(
				"objectDefinition.", "companyId", FinderColumn.Type.LONG, "=",
				true, false, ObjectDefinition::getCompanyId),
			new FinderColumn<>(
				"objectDefinition.", "name", FinderColumn.Type.STRING, "=",
				true, true, ObjectDefinition::getName));

		_finderPathWithPaginationFindByC_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "status"}, true);

		_finderPathWithoutPaginationFindByC_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"companyId", "status"}, true);

		_finderPathCountByC_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"companyId", "status"}, false);

		_collectionPersistenceFinderByC_S = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByC_S,
			_finderPathWithoutPaginationFindByC_S, _finderPathCountByC_S,
			_SQL_SELECT_OBJECTDEFINITION_WHERE,
			_SQL_COUNT_OBJECTDEFINITION_WHERE,
			ObjectDefinitionModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
			new FinderColumn<>(
				"objectDefinition.", "companyId", FinderColumn.Type.LONG, "=",
				true, false, ObjectDefinition::getCompanyId),
			new FinderColumn<>(
				"objectDefinition.", "status", FinderColumn.Type.INTEGER, "=",
				true, true, ObjectDefinition::getStatus));

		_finderPathWithPaginationFindByS_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByS_S",
			new String[] {
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"system_", "status"}, true);

		_finderPathWithoutPaginationFindByS_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByS_S",
			new String[] {Boolean.class.getName(), Integer.class.getName()},
			new String[] {"system_", "status"}, true);

		_finderPathCountByS_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByS_S",
			new String[] {Boolean.class.getName(), Integer.class.getName()},
			new String[] {"system_", "status"}, false);

		_collectionPersistenceFinderByS_S = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByS_S,
			_finderPathWithoutPaginationFindByS_S, _finderPathCountByS_S,
			_SQL_SELECT_OBJECTDEFINITION_WHERE,
			_SQL_COUNT_OBJECTDEFINITION_WHERE,
			ObjectDefinitionModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
			new FinderColumn<>(
				"objectDefinition.", "system", FinderColumn.Type.BOOLEAN, "=",
				true, false, ObjectDefinition::isSystem),
			new FinderColumn<>(
				"objectDefinition.", "status", FinderColumn.Type.INTEGER, "=",
				true, true, ObjectDefinition::getStatus));

		_finderPathWithPaginationFindByC_A_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_A_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId", "active_", "status"}, true);

		_finderPathWithoutPaginationFindByC_A_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_A_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName()
			},
			new String[] {"companyId", "active_", "status"}, true);

		_finderPathCountByC_A_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_A_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName()
			},
			new String[] {"companyId", "active_", "status"}, false);

		_collectionPersistenceFinderByC_A_S = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByC_A_S,
			_finderPathWithoutPaginationFindByC_A_S, _finderPathCountByC_A_S,
			_SQL_SELECT_OBJECTDEFINITION_WHERE,
			_SQL_COUNT_OBJECTDEFINITION_WHERE,
			ObjectDefinitionModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
			new FinderColumn<>(
				"objectDefinition.", "companyId", FinderColumn.Type.LONG, "=",
				true, false, ObjectDefinition::getCompanyId),
			new FinderColumn<>(
				"objectDefinition.", "active", FinderColumn.Type.BOOLEAN, "=",
				true, false, ObjectDefinition::isActive),
			new FinderColumn<>(
				"objectDefinition.", "status", FinderColumn.Type.INTEGER, "=",
				true, true, ObjectDefinition::getStatus));

		_finderPathWithPaginationFindByC_M_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_M_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId", "modifiable", "system_"}, true);

		_finderPathWithoutPaginationFindByC_M_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_M_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"companyId", "modifiable", "system_"}, true);

		_finderPathCountByC_M_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_M_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"companyId", "modifiable", "system_"}, false);

		_collectionPersistenceFinderByC_M_S = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByC_M_S,
			_finderPathWithoutPaginationFindByC_M_S, _finderPathCountByC_M_S,
			_SQL_SELECT_OBJECTDEFINITION_WHERE,
			_SQL_COUNT_OBJECTDEFINITION_WHERE,
			ObjectDefinitionModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
			new FinderColumn<>(
				"objectDefinition.", "companyId", FinderColumn.Type.LONG, "=",
				true, false, ObjectDefinition::getCompanyId),
			new FinderColumn<>(
				"objectDefinition.", "modifiable", FinderColumn.Type.BOOLEAN,
				"=", true, false, ObjectDefinition::isModifiable),
			new FinderColumn<>(
				"objectDefinition.", "system", FinderColumn.Type.BOOLEAN, "=",
				true, true, ObjectDefinition::isSystem));

		_finderPathWithPaginationFindByC_A_S_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_A_S_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "active_", "system_", "status"}, true);

		_finderPathWithoutPaginationFindByC_A_S_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_A_S_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName(), Integer.class.getName()
			},
			new String[] {"companyId", "active_", "system_", "status"}, true);

		_finderPathCountByC_A_S_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_A_S_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName(), Integer.class.getName()
			},
			new String[] {"companyId", "active_", "system_", "status"}, false);

		_collectionPersistenceFinderByC_A_S_S =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByC_A_S_S,
				_finderPathWithoutPaginationFindByC_A_S_S,
				_finderPathCountByC_A_S_S, _SQL_SELECT_OBJECTDEFINITION_WHERE,
				_SQL_COUNT_OBJECTDEFINITION_WHERE,
				ObjectDefinitionModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"objectDefinition.", "companyId", FinderColumn.Type.LONG,
					"=", true, false, ObjectDefinition::getCompanyId),
				new FinderColumn<>(
					"objectDefinition.", "active", FinderColumn.Type.BOOLEAN,
					"=", true, false, ObjectDefinition::isActive),
				new FinderColumn<>(
					"objectDefinition.", "system", FinderColumn.Type.BOOLEAN,
					"=", true, false, ObjectDefinition::isSystem),
				new FinderColumn<>(
					"objectDefinition.", "status", FinderColumn.Type.INTEGER,
					"=", true, true, ObjectDefinition::getStatus));

		_finderPathWithPaginationFindByC_OFI_A_E_S_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_OFI_A_E_S_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Boolean.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {
				"companyId", "objectFolderId", "active_",
				"enableObjectEntryDraft", "scope", "status"
			},
			true);

		_finderPathWithoutPaginationFindByC_OFI_A_E_S_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_OFI_A_E_S_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Boolean.class.getName(),
				String.class.getName(), Integer.class.getName()
			},
			new String[] {
				"companyId", "objectFolderId", "active_",
				"enableObjectEntryDraft", "scope", "status"
			},
			true);

		_finderPathCountByC_OFI_A_E_S_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_OFI_A_E_S_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Boolean.class.getName(),
				String.class.getName(), Integer.class.getName()
			},
			new String[] {
				"companyId", "objectFolderId", "active_",
				"enableObjectEntryDraft", "scope", "status"
			},
			false);

		_finderPathWithPaginationCountByC_OFI_A_E_S_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_OFI_A_E_S_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Boolean.class.getName(),
				String.class.getName(), Integer.class.getName()
			},
			new String[] {
				"companyId", "objectFolderId", "active_",
				"enableObjectEntryDraft", "scope", "status"
			},
			false);

		_finderPathFetchByERC_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByERC_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"externalReferenceCode", "companyId"}, true);

		_uniquePersistenceFinderByERC_C = new UniquePersistenceFinder<>(
			this, _finderPathFetchByERC_C, _SQL_SELECT_OBJECTDEFINITION_WHERE,
			new FinderColumn<>(
				"objectDefinition.", "externalReferenceCode",
				FinderColumn.Type.STRING, "=", true, false,
				ObjectDefinition::getExternalReferenceCode),
			new FinderColumn<>(
				"objectDefinition.", "companyId", FinderColumn.Type.LONG, "=",
				true, true, ObjectDefinition::getCompanyId));

		ObjectDefinitionUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		ObjectDefinitionUtil.setPersistence(null);

		entityCache.removeCache(ObjectDefinitionImpl.class.getName());
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

	private static final String _ENTITY_ALIAS_PREFIX =
		ObjectDefinitionModelImpl.ENTITY_ALIAS + ".";

	private static final String _SQL_SELECT_OBJECTDEFINITION =
		"SELECT objectDefinition FROM ObjectDefinition objectDefinition";

	private static final String _SQL_SELECT_OBJECTDEFINITION_WHERE =
		"SELECT objectDefinition FROM ObjectDefinition objectDefinition WHERE ";

	private static final String _SQL_COUNT_OBJECTDEFINITION_WHERE =
		"SELECT COUNT(objectDefinition) FROM ObjectDefinition objectDefinition WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"objectDefinition.objectDefinitionId";

	private static final String _FILTER_SQL_SELECT_OBJECTDEFINITION_WHERE =
		"SELECT DISTINCT {objectDefinition.*} FROM ObjectDefinition objectDefinition WHERE ";

	private static final String
		_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {ObjectDefinition.*} FROM (SELECT DISTINCT objectDefinition.objectDefinitionId FROM ObjectDefinition objectDefinition WHERE ";

	private static final String
		_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN ObjectDefinition ON TEMP_TABLE.objectDefinitionId = ObjectDefinition.objectDefinitionId";

	private static final String _FILTER_SQL_COUNT_OBJECTDEFINITION_WHERE =
		"SELECT COUNT(DISTINCT objectDefinition.objectDefinitionId) AS COUNT_VALUE FROM ObjectDefinition objectDefinition WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "objectDefinition";

	private static final String _FILTER_ENTITY_TABLE = "ObjectDefinition";

	private static final String _ORDER_BY_ENTITY_TABLE = "ObjectDefinition.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ObjectDefinition exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectDefinitionPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {
			"uuid", "accountEntryRestrictedObjectFieldId", "active", "system"
		});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-1083426987