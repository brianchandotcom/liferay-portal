/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.persistence.impl;

import com.liferay.object.exception.NoSuchObjectValidationRuleSettingException;
import com.liferay.object.model.ObjectValidationRuleSetting;
import com.liferay.object.model.ObjectValidationRuleSettingTable;
import com.liferay.object.model.impl.ObjectValidationRuleSettingImpl;
import com.liferay.object.model.impl.ObjectValidationRuleSettingModelImpl;
import com.liferay.object.service.persistence.ObjectValidationRuleSettingPersistence;
import com.liferay.object.service.persistence.ObjectValidationRuleSettingUtil;
import com.liferay.object.service.persistence.impl.constants.ObjectPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUID;

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
 * The persistence implementation for the object validation rule setting service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
@Component(service = ObjectValidationRuleSettingPersistence.class)
public class ObjectValidationRuleSettingPersistenceImpl
	extends BasePersistenceImpl<ObjectValidationRuleSetting>
	implements ObjectValidationRuleSettingPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ObjectValidationRuleSettingUtil</code> to access the object validation rule setting persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ObjectValidationRuleSettingImpl.class.getName();

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

	/**
	 * Returns all the object validation rule settings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object validation rule settings
	 */
	@Override
	public List<ObjectValidationRuleSetting> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object validation rule settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @return the range of matching object validation rule settings
	 */
	@Override
	public List<ObjectValidationRuleSetting> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object validation rule settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object validation rule settings
	 */
	@Override
	public List<ObjectValidationRuleSetting> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectValidationRuleSetting> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object validation rule settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object validation rule settings
	 */
	@Override
	public List<ObjectValidationRuleSetting> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectValidationRuleSetting> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<ObjectValidationRuleSetting> list = null;

		if (useFinderCache) {
			list = (List<ObjectValidationRuleSetting>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectValidationRuleSetting objectValidationRuleSetting :
						list) {

					if (!uuid.equals(objectValidationRuleSetting.getUuid())) {
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
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_OBJECTVALIDATIONRULESETTING_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ObjectValidationRuleSettingModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				list = (List<ObjectValidationRuleSetting>)QueryUtil.list(
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
	 * Returns the first object validation rule setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a matching object validation rule setting could not be found
	 */
	@Override
	public ObjectValidationRuleSetting findByUuid_First(
			String uuid,
			OrderByComparator<ObjectValidationRuleSetting> orderByComparator)
		throws NoSuchObjectValidationRuleSettingException {

		ObjectValidationRuleSetting objectValidationRuleSetting =
			fetchByUuid_First(uuid, orderByComparator);

		if (objectValidationRuleSetting != null) {
			return objectValidationRuleSetting;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchObjectValidationRuleSettingException(sb.toString());
	}

	/**
	 * Returns the first object validation rule setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule setting, or <code>null</code> if a matching object validation rule setting could not be found
	 */
	@Override
	public ObjectValidationRuleSetting fetchByUuid_First(
		String uuid,
		OrderByComparator<ObjectValidationRuleSetting> orderByComparator) {

		List<ObjectValidationRuleSetting> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object validation rule setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a matching object validation rule setting could not be found
	 */
	@Override
	public ObjectValidationRuleSetting findByUuid_Last(
			String uuid,
			OrderByComparator<ObjectValidationRuleSetting> orderByComparator)
		throws NoSuchObjectValidationRuleSettingException {

		ObjectValidationRuleSetting objectValidationRuleSetting =
			fetchByUuid_Last(uuid, orderByComparator);

		if (objectValidationRuleSetting != null) {
			return objectValidationRuleSetting;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchObjectValidationRuleSettingException(sb.toString());
	}

	/**
	 * Returns the last object validation rule setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule setting, or <code>null</code> if a matching object validation rule setting could not be found
	 */
	@Override
	public ObjectValidationRuleSetting fetchByUuid_Last(
		String uuid,
		OrderByComparator<ObjectValidationRuleSetting> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<ObjectValidationRuleSetting> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object validation rule settings before and after the current object validation rule setting in the ordered set where uuid = &#63;.
	 *
	 * @param objectValidationRuleSettingId the primary key of the current object validation rule setting
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a object validation rule setting with the primary key could not be found
	 */
	@Override
	public ObjectValidationRuleSetting[] findByUuid_PrevAndNext(
			long objectValidationRuleSettingId, String uuid,
			OrderByComparator<ObjectValidationRuleSetting> orderByComparator)
		throws NoSuchObjectValidationRuleSettingException {

		uuid = Objects.toString(uuid, "");

		ObjectValidationRuleSetting objectValidationRuleSetting =
			findByPrimaryKey(objectValidationRuleSettingId);

		Session session = null;

		try {
			session = openSession();

			ObjectValidationRuleSetting[] array =
				new ObjectValidationRuleSettingImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, objectValidationRuleSetting, uuid, orderByComparator,
				true);

			array[1] = objectValidationRuleSetting;

			array[2] = getByUuid_PrevAndNext(
				session, objectValidationRuleSetting, uuid, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ObjectValidationRuleSetting getByUuid_PrevAndNext(
		Session session,
		ObjectValidationRuleSetting objectValidationRuleSetting, String uuid,
		OrderByComparator<ObjectValidationRuleSetting> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_OBJECTVALIDATIONRULESETTING_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(ObjectValidationRuleSettingModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						objectValidationRuleSetting)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectValidationRuleSetting> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object validation rule settings where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (ObjectValidationRuleSetting objectValidationRuleSetting :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(objectValidationRuleSetting);
		}
	}

	/**
	 * Returns the number of object validation rule settings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object validation rule settings
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTVALIDATIONRULESETTING_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
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

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"objectValidationRuleSetting.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(objectValidationRuleSetting.uuid IS NULL OR objectValidationRuleSetting.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the object validation rule settings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object validation rule settings
	 */
	@Override
	public List<ObjectValidationRuleSetting> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object validation rule settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @return the range of matching object validation rule settings
	 */
	@Override
	public List<ObjectValidationRuleSetting> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object validation rule settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object validation rule settings
	 */
	@Override
	public List<ObjectValidationRuleSetting> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectValidationRuleSetting> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object validation rule settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object validation rule settings
	 */
	@Override
	public List<ObjectValidationRuleSetting> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectValidationRuleSetting> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<ObjectValidationRuleSetting> list = null;

		if (useFinderCache) {
			list = (List<ObjectValidationRuleSetting>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectValidationRuleSetting objectValidationRuleSetting :
						list) {

					if (!uuid.equals(objectValidationRuleSetting.getUuid()) ||
						(companyId !=
							objectValidationRuleSetting.getCompanyId())) {

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

			sb.append(_SQL_SELECT_OBJECTVALIDATIONRULESETTING_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ObjectValidationRuleSettingModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				list = (List<ObjectValidationRuleSetting>)QueryUtil.list(
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
	 * Returns the first object validation rule setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a matching object validation rule setting could not be found
	 */
	@Override
	public ObjectValidationRuleSetting findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ObjectValidationRuleSetting> orderByComparator)
		throws NoSuchObjectValidationRuleSettingException {

		ObjectValidationRuleSetting objectValidationRuleSetting =
			fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (objectValidationRuleSetting != null) {
			return objectValidationRuleSetting;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchObjectValidationRuleSettingException(sb.toString());
	}

	/**
	 * Returns the first object validation rule setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule setting, or <code>null</code> if a matching object validation rule setting could not be found
	 */
	@Override
	public ObjectValidationRuleSetting fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ObjectValidationRuleSetting> orderByComparator) {

		List<ObjectValidationRuleSetting> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object validation rule setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a matching object validation rule setting could not be found
	 */
	@Override
	public ObjectValidationRuleSetting findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ObjectValidationRuleSetting> orderByComparator)
		throws NoSuchObjectValidationRuleSettingException {

		ObjectValidationRuleSetting objectValidationRuleSetting =
			fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (objectValidationRuleSetting != null) {
			return objectValidationRuleSetting;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchObjectValidationRuleSettingException(sb.toString());
	}

	/**
	 * Returns the last object validation rule setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule setting, or <code>null</code> if a matching object validation rule setting could not be found
	 */
	@Override
	public ObjectValidationRuleSetting fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ObjectValidationRuleSetting> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<ObjectValidationRuleSetting> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object validation rule settings before and after the current object validation rule setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectValidationRuleSettingId the primary key of the current object validation rule setting
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a object validation rule setting with the primary key could not be found
	 */
	@Override
	public ObjectValidationRuleSetting[] findByUuid_C_PrevAndNext(
			long objectValidationRuleSettingId, String uuid, long companyId,
			OrderByComparator<ObjectValidationRuleSetting> orderByComparator)
		throws NoSuchObjectValidationRuleSettingException {

		uuid = Objects.toString(uuid, "");

		ObjectValidationRuleSetting objectValidationRuleSetting =
			findByPrimaryKey(objectValidationRuleSettingId);

		Session session = null;

		try {
			session = openSession();

			ObjectValidationRuleSetting[] array =
				new ObjectValidationRuleSettingImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, objectValidationRuleSetting, uuid, companyId,
				orderByComparator, true);

			array[1] = objectValidationRuleSetting;

			array[2] = getByUuid_C_PrevAndNext(
				session, objectValidationRuleSetting, uuid, companyId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ObjectValidationRuleSetting getByUuid_C_PrevAndNext(
		Session session,
		ObjectValidationRuleSetting objectValidationRuleSetting, String uuid,
		long companyId,
		OrderByComparator<ObjectValidationRuleSetting> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_OBJECTVALIDATIONRULESETTING_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(ObjectValidationRuleSettingModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						objectValidationRuleSetting)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectValidationRuleSetting> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object validation rule settings where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (ObjectValidationRuleSetting objectValidationRuleSetting :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(objectValidationRuleSetting);
		}
	}

	/**
	 * Returns the number of object validation rule settings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object validation rule settings
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_OBJECTVALIDATIONRULESETTING_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"objectValidationRuleSetting.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(objectValidationRuleSetting.uuid IS NULL OR objectValidationRuleSetting.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"objectValidationRuleSetting.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByObjectValidationRuleId;
	private FinderPath _finderPathWithoutPaginationFindByObjectValidationRuleId;
	private FinderPath _finderPathCountByObjectValidationRuleId;

	/**
	 * Returns all the object validation rule settings where objectValidationRuleId = &#63;.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @return the matching object validation rule settings
	 */
	@Override
	public List<ObjectValidationRuleSetting> findByObjectValidationRuleId(
		long objectValidationRuleId) {

		return findByObjectValidationRuleId(
			objectValidationRuleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object validation rule settings where objectValidationRuleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @return the range of matching object validation rule settings
	 */
	@Override
	public List<ObjectValidationRuleSetting> findByObjectValidationRuleId(
		long objectValidationRuleId, int start, int end) {

		return findByObjectValidationRuleId(
			objectValidationRuleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object validation rule settings where objectValidationRuleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object validation rule settings
	 */
	@Override
	public List<ObjectValidationRuleSetting> findByObjectValidationRuleId(
		long objectValidationRuleId, int start, int end,
		OrderByComparator<ObjectValidationRuleSetting> orderByComparator) {

		return findByObjectValidationRuleId(
			objectValidationRuleId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object validation rule settings where objectValidationRuleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object validation rule settings
	 */
	@Override
	public List<ObjectValidationRuleSetting> findByObjectValidationRuleId(
		long objectValidationRuleId, int start, int end,
		OrderByComparator<ObjectValidationRuleSetting> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByObjectValidationRuleId;
				finderArgs = new Object[] {objectValidationRuleId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByObjectValidationRuleId;
			finderArgs = new Object[] {
				objectValidationRuleId, start, end, orderByComparator
			};
		}

		List<ObjectValidationRuleSetting> list = null;

		if (useFinderCache) {
			list = (List<ObjectValidationRuleSetting>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectValidationRuleSetting objectValidationRuleSetting :
						list) {

					if (objectValidationRuleId !=
							objectValidationRuleSetting.
								getObjectValidationRuleId()) {

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
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_OBJECTVALIDATIONRULESETTING_WHERE);

			sb.append(
				_FINDER_COLUMN_OBJECTVALIDATIONRULEID_OBJECTVALIDATIONRULEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ObjectValidationRuleSettingModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectValidationRuleId);

				list = (List<ObjectValidationRuleSetting>)QueryUtil.list(
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
	 * Returns the first object validation rule setting in the ordered set where objectValidationRuleId = &#63;.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a matching object validation rule setting could not be found
	 */
	@Override
	public ObjectValidationRuleSetting findByObjectValidationRuleId_First(
			long objectValidationRuleId,
			OrderByComparator<ObjectValidationRuleSetting> orderByComparator)
		throws NoSuchObjectValidationRuleSettingException {

		ObjectValidationRuleSetting objectValidationRuleSetting =
			fetchByObjectValidationRuleId_First(
				objectValidationRuleId, orderByComparator);

		if (objectValidationRuleSetting != null) {
			return objectValidationRuleSetting;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectValidationRuleId=");
		sb.append(objectValidationRuleId);

		sb.append("}");

		throw new NoSuchObjectValidationRuleSettingException(sb.toString());
	}

	/**
	 * Returns the first object validation rule setting in the ordered set where objectValidationRuleId = &#63;.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule setting, or <code>null</code> if a matching object validation rule setting could not be found
	 */
	@Override
	public ObjectValidationRuleSetting fetchByObjectValidationRuleId_First(
		long objectValidationRuleId,
		OrderByComparator<ObjectValidationRuleSetting> orderByComparator) {

		List<ObjectValidationRuleSetting> list = findByObjectValidationRuleId(
			objectValidationRuleId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object validation rule setting in the ordered set where objectValidationRuleId = &#63;.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a matching object validation rule setting could not be found
	 */
	@Override
	public ObjectValidationRuleSetting findByObjectValidationRuleId_Last(
			long objectValidationRuleId,
			OrderByComparator<ObjectValidationRuleSetting> orderByComparator)
		throws NoSuchObjectValidationRuleSettingException {

		ObjectValidationRuleSetting objectValidationRuleSetting =
			fetchByObjectValidationRuleId_Last(
				objectValidationRuleId, orderByComparator);

		if (objectValidationRuleSetting != null) {
			return objectValidationRuleSetting;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectValidationRuleId=");
		sb.append(objectValidationRuleId);

		sb.append("}");

		throw new NoSuchObjectValidationRuleSettingException(sb.toString());
	}

	/**
	 * Returns the last object validation rule setting in the ordered set where objectValidationRuleId = &#63;.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule setting, or <code>null</code> if a matching object validation rule setting could not be found
	 */
	@Override
	public ObjectValidationRuleSetting fetchByObjectValidationRuleId_Last(
		long objectValidationRuleId,
		OrderByComparator<ObjectValidationRuleSetting> orderByComparator) {

		int count = countByObjectValidationRuleId(objectValidationRuleId);

		if (count == 0) {
			return null;
		}

		List<ObjectValidationRuleSetting> list = findByObjectValidationRuleId(
			objectValidationRuleId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object validation rule settings before and after the current object validation rule setting in the ordered set where objectValidationRuleId = &#63;.
	 *
	 * @param objectValidationRuleSettingId the primary key of the current object validation rule setting
	 * @param objectValidationRuleId the object validation rule ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a object validation rule setting with the primary key could not be found
	 */
	@Override
	public ObjectValidationRuleSetting[]
			findByObjectValidationRuleId_PrevAndNext(
				long objectValidationRuleSettingId, long objectValidationRuleId,
				OrderByComparator<ObjectValidationRuleSetting>
					orderByComparator)
		throws NoSuchObjectValidationRuleSettingException {

		ObjectValidationRuleSetting objectValidationRuleSetting =
			findByPrimaryKey(objectValidationRuleSettingId);

		Session session = null;

		try {
			session = openSession();

			ObjectValidationRuleSetting[] array =
				new ObjectValidationRuleSettingImpl[3];

			array[0] = getByObjectValidationRuleId_PrevAndNext(
				session, objectValidationRuleSetting, objectValidationRuleId,
				orderByComparator, true);

			array[1] = objectValidationRuleSetting;

			array[2] = getByObjectValidationRuleId_PrevAndNext(
				session, objectValidationRuleSetting, objectValidationRuleId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ObjectValidationRuleSetting
		getByObjectValidationRuleId_PrevAndNext(
			Session session,
			ObjectValidationRuleSetting objectValidationRuleSetting,
			long objectValidationRuleId,
			OrderByComparator<ObjectValidationRuleSetting> orderByComparator,
			boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_OBJECTVALIDATIONRULESETTING_WHERE);

		sb.append(
			_FINDER_COLUMN_OBJECTVALIDATIONRULEID_OBJECTVALIDATIONRULEID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(ObjectValidationRuleSettingModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(objectValidationRuleId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						objectValidationRuleSetting)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectValidationRuleSetting> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object validation rule settings where objectValidationRuleId = &#63; from the database.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 */
	@Override
	public void removeByObjectValidationRuleId(long objectValidationRuleId) {
		for (ObjectValidationRuleSetting objectValidationRuleSetting :
				findByObjectValidationRuleId(
					objectValidationRuleId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(objectValidationRuleSetting);
		}
	}

	/**
	 * Returns the number of object validation rule settings where objectValidationRuleId = &#63;.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @return the number of matching object validation rule settings
	 */
	@Override
	public int countByObjectValidationRuleId(long objectValidationRuleId) {
		FinderPath finderPath = _finderPathCountByObjectValidationRuleId;

		Object[] finderArgs = new Object[] {objectValidationRuleId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTVALIDATIONRULESETTING_WHERE);

			sb.append(
				_FINDER_COLUMN_OBJECTVALIDATIONRULEID_OBJECTVALIDATIONRULEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectValidationRuleId);

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

	private static final String
		_FINDER_COLUMN_OBJECTVALIDATIONRULEID_OBJECTVALIDATIONRULEID_2 =
			"objectValidationRuleSetting.objectValidationRuleId = ?";

	private FinderPath _finderPathWithPaginationFindByOVRI_N;
	private FinderPath _finderPathWithoutPaginationFindByOVRI_N;
	private FinderPath _finderPathCountByOVRI_N;

	/**
	 * Returns all the object validation rule settings where objectValidationRuleId = &#63; and name = &#63;.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @return the matching object validation rule settings
	 */
	@Override
	public List<ObjectValidationRuleSetting> findByOVRI_N(
		long objectValidationRuleId, String name) {

		return findByOVRI_N(
			objectValidationRuleId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the object validation rule settings where objectValidationRuleId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @return the range of matching object validation rule settings
	 */
	@Override
	public List<ObjectValidationRuleSetting> findByOVRI_N(
		long objectValidationRuleId, String name, int start, int end) {

		return findByOVRI_N(objectValidationRuleId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object validation rule settings where objectValidationRuleId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object validation rule settings
	 */
	@Override
	public List<ObjectValidationRuleSetting> findByOVRI_N(
		long objectValidationRuleId, String name, int start, int end,
		OrderByComparator<ObjectValidationRuleSetting> orderByComparator) {

		return findByOVRI_N(
			objectValidationRuleId, name, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object validation rule settings where objectValidationRuleId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object validation rule settings
	 */
	@Override
	public List<ObjectValidationRuleSetting> findByOVRI_N(
		long objectValidationRuleId, String name, int start, int end,
		OrderByComparator<ObjectValidationRuleSetting> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByOVRI_N;
				finderArgs = new Object[] {objectValidationRuleId, name};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByOVRI_N;
			finderArgs = new Object[] {
				objectValidationRuleId, name, start, end, orderByComparator
			};
		}

		List<ObjectValidationRuleSetting> list = null;

		if (useFinderCache) {
			list = (List<ObjectValidationRuleSetting>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectValidationRuleSetting objectValidationRuleSetting :
						list) {

					if ((objectValidationRuleId !=
							objectValidationRuleSetting.
								getObjectValidationRuleId()) ||
						!name.equals(objectValidationRuleSetting.getName())) {

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

			sb.append(_SQL_SELECT_OBJECTVALIDATIONRULESETTING_WHERE);

			sb.append(_FINDER_COLUMN_OVRI_N_OBJECTVALIDATIONRULEID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_OVRI_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_OVRI_N_NAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ObjectValidationRuleSettingModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectValidationRuleId);

				if (bindName) {
					queryPos.add(name);
				}

				list = (List<ObjectValidationRuleSetting>)QueryUtil.list(
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
	 * Returns the first object validation rule setting in the ordered set where objectValidationRuleId = &#63; and name = &#63;.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a matching object validation rule setting could not be found
	 */
	@Override
	public ObjectValidationRuleSetting findByOVRI_N_First(
			long objectValidationRuleId, String name,
			OrderByComparator<ObjectValidationRuleSetting> orderByComparator)
		throws NoSuchObjectValidationRuleSettingException {

		ObjectValidationRuleSetting objectValidationRuleSetting =
			fetchByOVRI_N_First(
				objectValidationRuleId, name, orderByComparator);

		if (objectValidationRuleSetting != null) {
			return objectValidationRuleSetting;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectValidationRuleId=");
		sb.append(objectValidationRuleId);

		sb.append(", name=");
		sb.append(name);

		sb.append("}");

		throw new NoSuchObjectValidationRuleSettingException(sb.toString());
	}

	/**
	 * Returns the first object validation rule setting in the ordered set where objectValidationRuleId = &#63; and name = &#63;.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule setting, or <code>null</code> if a matching object validation rule setting could not be found
	 */
	@Override
	public ObjectValidationRuleSetting fetchByOVRI_N_First(
		long objectValidationRuleId, String name,
		OrderByComparator<ObjectValidationRuleSetting> orderByComparator) {

		List<ObjectValidationRuleSetting> list = findByOVRI_N(
			objectValidationRuleId, name, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object validation rule setting in the ordered set where objectValidationRuleId = &#63; and name = &#63;.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a matching object validation rule setting could not be found
	 */
	@Override
	public ObjectValidationRuleSetting findByOVRI_N_Last(
			long objectValidationRuleId, String name,
			OrderByComparator<ObjectValidationRuleSetting> orderByComparator)
		throws NoSuchObjectValidationRuleSettingException {

		ObjectValidationRuleSetting objectValidationRuleSetting =
			fetchByOVRI_N_Last(objectValidationRuleId, name, orderByComparator);

		if (objectValidationRuleSetting != null) {
			return objectValidationRuleSetting;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectValidationRuleId=");
		sb.append(objectValidationRuleId);

		sb.append(", name=");
		sb.append(name);

		sb.append("}");

		throw new NoSuchObjectValidationRuleSettingException(sb.toString());
	}

	/**
	 * Returns the last object validation rule setting in the ordered set where objectValidationRuleId = &#63; and name = &#63;.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule setting, or <code>null</code> if a matching object validation rule setting could not be found
	 */
	@Override
	public ObjectValidationRuleSetting fetchByOVRI_N_Last(
		long objectValidationRuleId, String name,
		OrderByComparator<ObjectValidationRuleSetting> orderByComparator) {

		int count = countByOVRI_N(objectValidationRuleId, name);

		if (count == 0) {
			return null;
		}

		List<ObjectValidationRuleSetting> list = findByOVRI_N(
			objectValidationRuleId, name, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object validation rule settings before and after the current object validation rule setting in the ordered set where objectValidationRuleId = &#63; and name = &#63;.
	 *
	 * @param objectValidationRuleSettingId the primary key of the current object validation rule setting
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a object validation rule setting with the primary key could not be found
	 */
	@Override
	public ObjectValidationRuleSetting[] findByOVRI_N_PrevAndNext(
			long objectValidationRuleSettingId, long objectValidationRuleId,
			String name,
			OrderByComparator<ObjectValidationRuleSetting> orderByComparator)
		throws NoSuchObjectValidationRuleSettingException {

		name = Objects.toString(name, "");

		ObjectValidationRuleSetting objectValidationRuleSetting =
			findByPrimaryKey(objectValidationRuleSettingId);

		Session session = null;

		try {
			session = openSession();

			ObjectValidationRuleSetting[] array =
				new ObjectValidationRuleSettingImpl[3];

			array[0] = getByOVRI_N_PrevAndNext(
				session, objectValidationRuleSetting, objectValidationRuleId,
				name, orderByComparator, true);

			array[1] = objectValidationRuleSetting;

			array[2] = getByOVRI_N_PrevAndNext(
				session, objectValidationRuleSetting, objectValidationRuleId,
				name, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ObjectValidationRuleSetting getByOVRI_N_PrevAndNext(
		Session session,
		ObjectValidationRuleSetting objectValidationRuleSetting,
		long objectValidationRuleId, String name,
		OrderByComparator<ObjectValidationRuleSetting> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_OBJECTVALIDATIONRULESETTING_WHERE);

		sb.append(_FINDER_COLUMN_OVRI_N_OBJECTVALIDATIONRULEID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_OVRI_N_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_OVRI_N_NAME_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(ObjectValidationRuleSettingModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(objectValidationRuleId);

		if (bindName) {
			queryPos.add(name);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						objectValidationRuleSetting)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectValidationRuleSetting> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object validation rule settings where objectValidationRuleId = &#63; and name = &#63; from the database.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 */
	@Override
	public void removeByOVRI_N(long objectValidationRuleId, String name) {
		for (ObjectValidationRuleSetting objectValidationRuleSetting :
				findByOVRI_N(
					objectValidationRuleId, name, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(objectValidationRuleSetting);
		}
	}

	/**
	 * Returns the number of object validation rule settings where objectValidationRuleId = &#63; and name = &#63;.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @return the number of matching object validation rule settings
	 */
	@Override
	public int countByOVRI_N(long objectValidationRuleId, String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByOVRI_N;

		Object[] finderArgs = new Object[] {objectValidationRuleId, name};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_OBJECTVALIDATIONRULESETTING_WHERE);

			sb.append(_FINDER_COLUMN_OVRI_N_OBJECTVALIDATIONRULEID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_OVRI_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_OVRI_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectValidationRuleId);

				if (bindName) {
					queryPos.add(name);
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

	private static final String _FINDER_COLUMN_OVRI_N_OBJECTVALIDATIONRULEID_2 =
		"objectValidationRuleSetting.objectValidationRuleId = ? AND ";

	private static final String _FINDER_COLUMN_OVRI_N_NAME_2 =
		"objectValidationRuleSetting.name = ?";

	private static final String _FINDER_COLUMN_OVRI_N_NAME_3 =
		"(objectValidationRuleSetting.name IS NULL OR objectValidationRuleSetting.name = '')";

	private FinderPath _finderPathFetchByOVRI_N_V;
	private FinderPath _finderPathCountByOVRI_N_V;

	/**
	 * Returns the object validation rule setting where objectValidationRuleId = &#63; and name = &#63; and value = &#63; or throws a <code>NoSuchObjectValidationRuleSettingException</code> if it could not be found.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @param value the value
	 * @return the matching object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a matching object validation rule setting could not be found
	 */
	@Override
	public ObjectValidationRuleSetting findByOVRI_N_V(
			long objectValidationRuleId, String name, String value)
		throws NoSuchObjectValidationRuleSettingException {

		ObjectValidationRuleSetting objectValidationRuleSetting =
			fetchByOVRI_N_V(objectValidationRuleId, name, value);

		if (objectValidationRuleSetting == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("objectValidationRuleId=");
			sb.append(objectValidationRuleId);

			sb.append(", name=");
			sb.append(name);

			sb.append(", value=");
			sb.append(value);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchObjectValidationRuleSettingException(sb.toString());
		}

		return objectValidationRuleSetting;
	}

	/**
	 * Returns the object validation rule setting where objectValidationRuleId = &#63; and name = &#63; and value = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @param value the value
	 * @return the matching object validation rule setting, or <code>null</code> if a matching object validation rule setting could not be found
	 */
	@Override
	public ObjectValidationRuleSetting fetchByOVRI_N_V(
		long objectValidationRuleId, String name, String value) {

		return fetchByOVRI_N_V(objectValidationRuleId, name, value, true);
	}

	/**
	 * Returns the object validation rule setting where objectValidationRuleId = &#63; and name = &#63; and value = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @param value the value
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching object validation rule setting, or <code>null</code> if a matching object validation rule setting could not be found
	 */
	@Override
	public ObjectValidationRuleSetting fetchByOVRI_N_V(
		long objectValidationRuleId, String name, String value,
		boolean useFinderCache) {

		name = Objects.toString(name, "");
		value = Objects.toString(value, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {objectValidationRuleId, name, value};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByOVRI_N_V, finderArgs, this);
		}

		if (result instanceof ObjectValidationRuleSetting) {
			ObjectValidationRuleSetting objectValidationRuleSetting =
				(ObjectValidationRuleSetting)result;

			if ((objectValidationRuleId !=
					objectValidationRuleSetting.getObjectValidationRuleId()) ||
				!Objects.equals(name, objectValidationRuleSetting.getName()) ||
				!Objects.equals(
					value, objectValidationRuleSetting.getValue())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_OBJECTVALIDATIONRULESETTING_WHERE);

			sb.append(_FINDER_COLUMN_OVRI_N_V_OBJECTVALIDATIONRULEID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_OVRI_N_V_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_OVRI_N_V_NAME_2);
			}

			boolean bindValue = false;

			if (value.isEmpty()) {
				sb.append(_FINDER_COLUMN_OVRI_N_V_VALUE_3);
			}
			else {
				bindValue = true;

				sb.append(_FINDER_COLUMN_OVRI_N_V_VALUE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectValidationRuleId);

				if (bindName) {
					queryPos.add(name);
				}

				if (bindValue) {
					queryPos.add(value);
				}

				List<ObjectValidationRuleSetting> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByOVRI_N_V, finderArgs, list);
					}
				}
				else {
					ObjectValidationRuleSetting objectValidationRuleSetting =
						list.get(0);

					result = objectValidationRuleSetting;

					cacheResult(objectValidationRuleSetting);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (ObjectValidationRuleSetting)result;
		}
	}

	/**
	 * Removes the object validation rule setting where objectValidationRuleId = &#63; and name = &#63; and value = &#63; from the database.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @param value the value
	 * @return the object validation rule setting that was removed
	 */
	@Override
	public ObjectValidationRuleSetting removeByOVRI_N_V(
			long objectValidationRuleId, String name, String value)
		throws NoSuchObjectValidationRuleSettingException {

		ObjectValidationRuleSetting objectValidationRuleSetting =
			findByOVRI_N_V(objectValidationRuleId, name, value);

		return remove(objectValidationRuleSetting);
	}

	/**
	 * Returns the number of object validation rule settings where objectValidationRuleId = &#63; and name = &#63; and value = &#63;.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @param value the value
	 * @return the number of matching object validation rule settings
	 */
	@Override
	public int countByOVRI_N_V(
		long objectValidationRuleId, String name, String value) {

		name = Objects.toString(name, "");
		value = Objects.toString(value, "");

		FinderPath finderPath = _finderPathCountByOVRI_N_V;

		Object[] finderArgs = new Object[] {
			objectValidationRuleId, name, value
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_OBJECTVALIDATIONRULESETTING_WHERE);

			sb.append(_FINDER_COLUMN_OVRI_N_V_OBJECTVALIDATIONRULEID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_OVRI_N_V_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_OVRI_N_V_NAME_2);
			}

			boolean bindValue = false;

			if (value.isEmpty()) {
				sb.append(_FINDER_COLUMN_OVRI_N_V_VALUE_3);
			}
			else {
				bindValue = true;

				sb.append(_FINDER_COLUMN_OVRI_N_V_VALUE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectValidationRuleId);

				if (bindName) {
					queryPos.add(name);
				}

				if (bindValue) {
					queryPos.add(value);
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

	private static final String
		_FINDER_COLUMN_OVRI_N_V_OBJECTVALIDATIONRULEID_2 =
			"objectValidationRuleSetting.objectValidationRuleId = ? AND ";

	private static final String _FINDER_COLUMN_OVRI_N_V_NAME_2 =
		"objectValidationRuleSetting.name = ? AND ";

	private static final String _FINDER_COLUMN_OVRI_N_V_NAME_3 =
		"(objectValidationRuleSetting.name IS NULL OR objectValidationRuleSetting.name = '') AND ";

	private static final String _FINDER_COLUMN_OVRI_N_V_VALUE_2 =
		"objectValidationRuleSetting.value = ?";

	private static final String _FINDER_COLUMN_OVRI_N_V_VALUE_3 =
		"(objectValidationRuleSetting.value IS NULL OR objectValidationRuleSetting.value = '')";

	public ObjectValidationRuleSettingPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(ObjectValidationRuleSetting.class);

		setModelImplClass(ObjectValidationRuleSettingImpl.class);
		setModelPKClass(long.class);

		setTable(ObjectValidationRuleSettingTable.INSTANCE);
	}

	/**
	 * Caches the object validation rule setting in the entity cache if it is enabled.
	 *
	 * @param objectValidationRuleSetting the object validation rule setting
	 */
	@Override
	public void cacheResult(
		ObjectValidationRuleSetting objectValidationRuleSetting) {

		entityCache.putResult(
			ObjectValidationRuleSettingImpl.class,
			objectValidationRuleSetting.getPrimaryKey(),
			objectValidationRuleSetting);

		finderCache.putResult(
			_finderPathFetchByOVRI_N_V,
			new Object[] {
				objectValidationRuleSetting.getObjectValidationRuleId(),
				objectValidationRuleSetting.getName(),
				objectValidationRuleSetting.getValue()
			},
			objectValidationRuleSetting);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the object validation rule settings in the entity cache if it is enabled.
	 *
	 * @param objectValidationRuleSettings the object validation rule settings
	 */
	@Override
	public void cacheResult(
		List<ObjectValidationRuleSetting> objectValidationRuleSettings) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (objectValidationRuleSettings.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ObjectValidationRuleSetting objectValidationRuleSetting :
				objectValidationRuleSettings) {

			if (entityCache.getResult(
					ObjectValidationRuleSettingImpl.class,
					objectValidationRuleSetting.getPrimaryKey()) == null) {

				cacheResult(objectValidationRuleSetting);
			}
		}
	}

	/**
	 * Clears the cache for all object validation rule settings.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ObjectValidationRuleSettingImpl.class);

		finderCache.clearCache(ObjectValidationRuleSettingImpl.class);
	}

	/**
	 * Clears the cache for the object validation rule setting.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		ObjectValidationRuleSetting objectValidationRuleSetting) {

		entityCache.removeResult(
			ObjectValidationRuleSettingImpl.class, objectValidationRuleSetting);
	}

	@Override
	public void clearCache(
		List<ObjectValidationRuleSetting> objectValidationRuleSettings) {

		for (ObjectValidationRuleSetting objectValidationRuleSetting :
				objectValidationRuleSettings) {

			entityCache.removeResult(
				ObjectValidationRuleSettingImpl.class,
				objectValidationRuleSetting);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(ObjectValidationRuleSettingImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				ObjectValidationRuleSettingImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		ObjectValidationRuleSettingModelImpl
			objectValidationRuleSettingModelImpl) {

		Object[] args = new Object[] {
			objectValidationRuleSettingModelImpl.getObjectValidationRuleId(),
			objectValidationRuleSettingModelImpl.getName(),
			objectValidationRuleSettingModelImpl.getValue()
		};

		finderCache.putResult(
			_finderPathCountByOVRI_N_V, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByOVRI_N_V, args,
			objectValidationRuleSettingModelImpl);
	}

	/**
	 * Creates a new object validation rule setting with the primary key. Does not add the object validation rule setting to the database.
	 *
	 * @param objectValidationRuleSettingId the primary key for the new object validation rule setting
	 * @return the new object validation rule setting
	 */
	@Override
	public ObjectValidationRuleSetting create(
		long objectValidationRuleSettingId) {

		ObjectValidationRuleSetting objectValidationRuleSetting =
			new ObjectValidationRuleSettingImpl();

		objectValidationRuleSetting.setNew(true);
		objectValidationRuleSetting.setPrimaryKey(
			objectValidationRuleSettingId);

		String uuid = _portalUUID.generate();

		objectValidationRuleSetting.setUuid(uuid);

		objectValidationRuleSetting.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return objectValidationRuleSetting;
	}

	/**
	 * Removes the object validation rule setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectValidationRuleSettingId the primary key of the object validation rule setting
	 * @return the object validation rule setting that was removed
	 * @throws NoSuchObjectValidationRuleSettingException if a object validation rule setting with the primary key could not be found
	 */
	@Override
	public ObjectValidationRuleSetting remove(
			long objectValidationRuleSettingId)
		throws NoSuchObjectValidationRuleSettingException {

		return remove((Serializable)objectValidationRuleSettingId);
	}

	/**
	 * Removes the object validation rule setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the object validation rule setting
	 * @return the object validation rule setting that was removed
	 * @throws NoSuchObjectValidationRuleSettingException if a object validation rule setting with the primary key could not be found
	 */
	@Override
	public ObjectValidationRuleSetting remove(Serializable primaryKey)
		throws NoSuchObjectValidationRuleSettingException {

		Session session = null;

		try {
			session = openSession();

			ObjectValidationRuleSetting objectValidationRuleSetting =
				(ObjectValidationRuleSetting)session.get(
					ObjectValidationRuleSettingImpl.class, primaryKey);

			if (objectValidationRuleSetting == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchObjectValidationRuleSettingException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(objectValidationRuleSetting);
		}
		catch (NoSuchObjectValidationRuleSettingException
					noSuchEntityException) {

			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected ObjectValidationRuleSetting removeImpl(
		ObjectValidationRuleSetting objectValidationRuleSetting) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(objectValidationRuleSetting)) {
				objectValidationRuleSetting =
					(ObjectValidationRuleSetting)session.get(
						ObjectValidationRuleSettingImpl.class,
						objectValidationRuleSetting.getPrimaryKeyObj());
			}

			if (objectValidationRuleSetting != null) {
				session.delete(objectValidationRuleSetting);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (objectValidationRuleSetting != null) {
			clearCache(objectValidationRuleSetting);
		}

		return objectValidationRuleSetting;
	}

	@Override
	public ObjectValidationRuleSetting updateImpl(
		ObjectValidationRuleSetting objectValidationRuleSetting) {

		boolean isNew = objectValidationRuleSetting.isNew();

		if (!(objectValidationRuleSetting instanceof
				ObjectValidationRuleSettingModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					objectValidationRuleSetting.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					objectValidationRuleSetting);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in objectValidationRuleSetting proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ObjectValidationRuleSetting implementation " +
					objectValidationRuleSetting.getClass());
		}

		ObjectValidationRuleSettingModelImpl
			objectValidationRuleSettingModelImpl =
				(ObjectValidationRuleSettingModelImpl)
					objectValidationRuleSetting;

		if (Validator.isNull(objectValidationRuleSetting.getUuid())) {
			String uuid = _portalUUID.generate();

			objectValidationRuleSetting.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (objectValidationRuleSetting.getCreateDate() == null)) {
			if (serviceContext == null) {
				objectValidationRuleSetting.setCreateDate(date);
			}
			else {
				objectValidationRuleSetting.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!objectValidationRuleSettingModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				objectValidationRuleSetting.setModifiedDate(date);
			}
			else {
				objectValidationRuleSetting.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(objectValidationRuleSetting);
			}
			else {
				objectValidationRuleSetting =
					(ObjectValidationRuleSetting)session.merge(
						objectValidationRuleSetting);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			ObjectValidationRuleSettingImpl.class,
			objectValidationRuleSettingModelImpl, false, true);

		cacheUniqueFindersCache(objectValidationRuleSettingModelImpl);

		if (isNew) {
			objectValidationRuleSetting.setNew(false);
		}

		objectValidationRuleSetting.resetOriginalValues();

		return objectValidationRuleSetting;
	}

	/**
	 * Returns the object validation rule setting with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the object validation rule setting
	 * @return the object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a object validation rule setting with the primary key could not be found
	 */
	@Override
	public ObjectValidationRuleSetting findByPrimaryKey(Serializable primaryKey)
		throws NoSuchObjectValidationRuleSettingException {

		ObjectValidationRuleSetting objectValidationRuleSetting =
			fetchByPrimaryKey(primaryKey);

		if (objectValidationRuleSetting == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchObjectValidationRuleSettingException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return objectValidationRuleSetting;
	}

	/**
	 * Returns the object validation rule setting with the primary key or throws a <code>NoSuchObjectValidationRuleSettingException</code> if it could not be found.
	 *
	 * @param objectValidationRuleSettingId the primary key of the object validation rule setting
	 * @return the object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a object validation rule setting with the primary key could not be found
	 */
	@Override
	public ObjectValidationRuleSetting findByPrimaryKey(
			long objectValidationRuleSettingId)
		throws NoSuchObjectValidationRuleSettingException {

		return findByPrimaryKey((Serializable)objectValidationRuleSettingId);
	}

	/**
	 * Returns the object validation rule setting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectValidationRuleSettingId the primary key of the object validation rule setting
	 * @return the object validation rule setting, or <code>null</code> if a object validation rule setting with the primary key could not be found
	 */
	@Override
	public ObjectValidationRuleSetting fetchByPrimaryKey(
		long objectValidationRuleSettingId) {

		return fetchByPrimaryKey((Serializable)objectValidationRuleSettingId);
	}

	/**
	 * Returns all the object validation rule settings.
	 *
	 * @return the object validation rule settings
	 */
	@Override
	public List<ObjectValidationRuleSetting> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object validation rule settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @return the range of object validation rule settings
	 */
	@Override
	public List<ObjectValidationRuleSetting> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the object validation rule settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object validation rule settings
	 */
	@Override
	public List<ObjectValidationRuleSetting> findAll(
		int start, int end,
		OrderByComparator<ObjectValidationRuleSetting> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object validation rule settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object validation rule settings
	 */
	@Override
	public List<ObjectValidationRuleSetting> findAll(
		int start, int end,
		OrderByComparator<ObjectValidationRuleSetting> orderByComparator,
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

		List<ObjectValidationRuleSetting> list = null;

		if (useFinderCache) {
			list = (List<ObjectValidationRuleSetting>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_OBJECTVALIDATIONRULESETTING);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_OBJECTVALIDATIONRULESETTING;

				sql = sql.concat(
					ObjectValidationRuleSettingModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ObjectValidationRuleSetting>)QueryUtil.list(
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
	 * Removes all the object validation rule settings from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ObjectValidationRuleSetting objectValidationRuleSetting :
				findAll()) {

			remove(objectValidationRuleSetting);
		}
	}

	/**
	 * Returns the number of object validation rule settings.
	 *
	 * @return the number of object validation rule settings
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
					_SQL_COUNT_OBJECTVALIDATIONRULESETTING);

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
		return "objectValidationRuleSettingId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_OBJECTVALIDATIONRULESETTING;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ObjectValidationRuleSettingModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the object validation rule setting persistence.
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

		_finderPathWithPaginationFindByObjectValidationRuleId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByObjectValidationRuleId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"objectValidationRuleId"}, true);

		_finderPathWithoutPaginationFindByObjectValidationRuleId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByObjectValidationRuleId",
				new String[] {Long.class.getName()},
				new String[] {"objectValidationRuleId"}, true);

		_finderPathCountByObjectValidationRuleId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByObjectValidationRuleId",
			new String[] {Long.class.getName()},
			new String[] {"objectValidationRuleId"}, false);

		_finderPathWithPaginationFindByOVRI_N = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByOVRI_N",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"objectValidationRuleId", "name"}, true);

		_finderPathWithoutPaginationFindByOVRI_N = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByOVRI_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"objectValidationRuleId", "name"}, true);

		_finderPathCountByOVRI_N = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByOVRI_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"objectValidationRuleId", "name"}, false);

		_finderPathFetchByOVRI_N_V = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByOVRI_N_V",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {"objectValidationRuleId", "name", "value"}, true);

		_finderPathCountByOVRI_N_V = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByOVRI_N_V",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {"objectValidationRuleId", "name", "value"}, false);

		ObjectValidationRuleSettingUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		ObjectValidationRuleSettingUtil.setPersistence(null);

		entityCache.removeCache(
			ObjectValidationRuleSettingImpl.class.getName());
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

	private static final String _SQL_SELECT_OBJECTVALIDATIONRULESETTING =
		"SELECT objectValidationRuleSetting FROM ObjectValidationRuleSetting objectValidationRuleSetting";

	private static final String _SQL_SELECT_OBJECTVALIDATIONRULESETTING_WHERE =
		"SELECT objectValidationRuleSetting FROM ObjectValidationRuleSetting objectValidationRuleSetting WHERE ";

	private static final String _SQL_COUNT_OBJECTVALIDATIONRULESETTING =
		"SELECT COUNT(objectValidationRuleSetting) FROM ObjectValidationRuleSetting objectValidationRuleSetting";

	private static final String _SQL_COUNT_OBJECTVALIDATIONRULESETTING_WHERE =
		"SELECT COUNT(objectValidationRuleSetting) FROM ObjectValidationRuleSetting objectValidationRuleSetting WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"objectValidationRuleSetting.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ObjectValidationRuleSetting exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ObjectValidationRuleSetting exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectValidationRuleSettingPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private PortalUUID _portalUUID;

}