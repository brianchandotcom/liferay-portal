/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.persistence.impl;

import com.liferay.object.exception.NoSuchObjectValidationRuleException;
import com.liferay.object.model.ObjectValidationRule;
import com.liferay.object.model.ObjectValidationRuleTable;
import com.liferay.object.model.impl.ObjectValidationRuleImpl;
import com.liferay.object.model.impl.ObjectValidationRuleModelImpl;
import com.liferay.object.service.persistence.ObjectValidationRulePersistence;
import com.liferay.object.service.persistence.ObjectValidationRuleUtil;
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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
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
 * The persistence implementation for the object validation rule service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
@Component(service = ObjectValidationRulePersistence.class)
public class ObjectValidationRulePersistenceImpl
	extends BasePersistenceImpl<ObjectValidationRule>
	implements ObjectValidationRulePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ObjectValidationRuleUtil</code> to access the object validation rule persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ObjectValidationRuleImpl.class.getName();

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
	 * Returns all the object validation rules where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object validation rules
	 */
	@Override
	public List<ObjectValidationRule> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object validation rules where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @return the range of matching object validation rules
	 */
	@Override
	public List<ObjectValidationRule> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object validation rules where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object validation rules
	 */
	@Override
	public List<ObjectValidationRule> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectValidationRule> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object validation rules where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object validation rules
	 */
	@Override
	public List<ObjectValidationRule> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectValidationRule> orderByComparator,
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

		List<ObjectValidationRule> list = null;

		if (useFinderCache) {
			list = (List<ObjectValidationRule>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectValidationRule objectValidationRule : list) {
					if (!uuid.equals(objectValidationRule.getUuid())) {
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

			sb.append(_SQL_SELECT_OBJECTVALIDATIONRULE_WHERE);

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
				sb.append(ObjectValidationRuleModelImpl.ORDER_BY_JPQL);
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

				list = (List<ObjectValidationRule>)QueryUtil.list(
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
	 * Returns the first object validation rule in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule
	 * @throws NoSuchObjectValidationRuleException if a matching object validation rule could not be found
	 */
	@Override
	public ObjectValidationRule findByUuid_First(
			String uuid,
			OrderByComparator<ObjectValidationRule> orderByComparator)
		throws NoSuchObjectValidationRuleException {

		ObjectValidationRule objectValidationRule = fetchByUuid_First(
			uuid, orderByComparator);

		if (objectValidationRule != null) {
			return objectValidationRule;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchObjectValidationRuleException(sb.toString());
	}

	/**
	 * Returns the first object validation rule in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule, or <code>null</code> if a matching object validation rule could not be found
	 */
	@Override
	public ObjectValidationRule fetchByUuid_First(
		String uuid,
		OrderByComparator<ObjectValidationRule> orderByComparator) {

		List<ObjectValidationRule> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object validation rule in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule
	 * @throws NoSuchObjectValidationRuleException if a matching object validation rule could not be found
	 */
	@Override
	public ObjectValidationRule findByUuid_Last(
			String uuid,
			OrderByComparator<ObjectValidationRule> orderByComparator)
		throws NoSuchObjectValidationRuleException {

		ObjectValidationRule objectValidationRule = fetchByUuid_Last(
			uuid, orderByComparator);

		if (objectValidationRule != null) {
			return objectValidationRule;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchObjectValidationRuleException(sb.toString());
	}

	/**
	 * Returns the last object validation rule in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule, or <code>null</code> if a matching object validation rule could not be found
	 */
	@Override
	public ObjectValidationRule fetchByUuid_Last(
		String uuid,
		OrderByComparator<ObjectValidationRule> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<ObjectValidationRule> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object validation rules before and after the current object validation rule in the ordered set where uuid = &#63;.
	 *
	 * @param objectValidationRuleId the primary key of the current object validation rule
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object validation rule
	 * @throws NoSuchObjectValidationRuleException if a object validation rule with the primary key could not be found
	 */
	@Override
	public ObjectValidationRule[] findByUuid_PrevAndNext(
			long objectValidationRuleId, String uuid,
			OrderByComparator<ObjectValidationRule> orderByComparator)
		throws NoSuchObjectValidationRuleException {

		uuid = Objects.toString(uuid, "");

		ObjectValidationRule objectValidationRule = findByPrimaryKey(
			objectValidationRuleId);

		Session session = null;

		try {
			session = openSession();

			ObjectValidationRule[] array = new ObjectValidationRuleImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, objectValidationRule, uuid, orderByComparator, true);

			array[1] = objectValidationRule;

			array[2] = getByUuid_PrevAndNext(
				session, objectValidationRule, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ObjectValidationRule getByUuid_PrevAndNext(
		Session session, ObjectValidationRule objectValidationRule, String uuid,
		OrderByComparator<ObjectValidationRule> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTVALIDATIONRULE_WHERE);

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
			sb.append(ObjectValidationRuleModelImpl.ORDER_BY_JPQL);
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
						objectValidationRule)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectValidationRule> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object validation rules where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (ObjectValidationRule objectValidationRule :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(objectValidationRule);
		}
	}

	/**
	 * Returns the number of object validation rules where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object validation rules
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTVALIDATIONRULE_WHERE);

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
		"objectValidationRule.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(objectValidationRule.uuid IS NULL OR objectValidationRule.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the object validation rules where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object validation rules
	 */
	@Override
	public List<ObjectValidationRule> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object validation rules where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @return the range of matching object validation rules
	 */
	@Override
	public List<ObjectValidationRule> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object validation rules where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object validation rules
	 */
	@Override
	public List<ObjectValidationRule> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectValidationRule> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object validation rules where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object validation rules
	 */
	@Override
	public List<ObjectValidationRule> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectValidationRule> orderByComparator,
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

		List<ObjectValidationRule> list = null;

		if (useFinderCache) {
			list = (List<ObjectValidationRule>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectValidationRule objectValidationRule : list) {
					if (!uuid.equals(objectValidationRule.getUuid()) ||
						(companyId != objectValidationRule.getCompanyId())) {

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

			sb.append(_SQL_SELECT_OBJECTVALIDATIONRULE_WHERE);

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
				sb.append(ObjectValidationRuleModelImpl.ORDER_BY_JPQL);
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

				list = (List<ObjectValidationRule>)QueryUtil.list(
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
	 * Returns the first object validation rule in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule
	 * @throws NoSuchObjectValidationRuleException if a matching object validation rule could not be found
	 */
	@Override
	public ObjectValidationRule findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ObjectValidationRule> orderByComparator)
		throws NoSuchObjectValidationRuleException {

		ObjectValidationRule objectValidationRule = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (objectValidationRule != null) {
			return objectValidationRule;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchObjectValidationRuleException(sb.toString());
	}

	/**
	 * Returns the first object validation rule in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule, or <code>null</code> if a matching object validation rule could not be found
	 */
	@Override
	public ObjectValidationRule fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ObjectValidationRule> orderByComparator) {

		List<ObjectValidationRule> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object validation rule in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule
	 * @throws NoSuchObjectValidationRuleException if a matching object validation rule could not be found
	 */
	@Override
	public ObjectValidationRule findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ObjectValidationRule> orderByComparator)
		throws NoSuchObjectValidationRuleException {

		ObjectValidationRule objectValidationRule = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (objectValidationRule != null) {
			return objectValidationRule;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchObjectValidationRuleException(sb.toString());
	}

	/**
	 * Returns the last object validation rule in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule, or <code>null</code> if a matching object validation rule could not be found
	 */
	@Override
	public ObjectValidationRule fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ObjectValidationRule> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<ObjectValidationRule> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object validation rules before and after the current object validation rule in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectValidationRuleId the primary key of the current object validation rule
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object validation rule
	 * @throws NoSuchObjectValidationRuleException if a object validation rule with the primary key could not be found
	 */
	@Override
	public ObjectValidationRule[] findByUuid_C_PrevAndNext(
			long objectValidationRuleId, String uuid, long companyId,
			OrderByComparator<ObjectValidationRule> orderByComparator)
		throws NoSuchObjectValidationRuleException {

		uuid = Objects.toString(uuid, "");

		ObjectValidationRule objectValidationRule = findByPrimaryKey(
			objectValidationRuleId);

		Session session = null;

		try {
			session = openSession();

			ObjectValidationRule[] array = new ObjectValidationRuleImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, objectValidationRule, uuid, companyId,
				orderByComparator, true);

			array[1] = objectValidationRule;

			array[2] = getByUuid_C_PrevAndNext(
				session, objectValidationRule, uuid, companyId,
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

	protected ObjectValidationRule getByUuid_C_PrevAndNext(
		Session session, ObjectValidationRule objectValidationRule, String uuid,
		long companyId,
		OrderByComparator<ObjectValidationRule> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTVALIDATIONRULE_WHERE);

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
			sb.append(ObjectValidationRuleModelImpl.ORDER_BY_JPQL);
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
						objectValidationRule)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectValidationRule> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object validation rules where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (ObjectValidationRule objectValidationRule :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(objectValidationRule);
		}
	}

	/**
	 * Returns the number of object validation rules where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object validation rules
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_OBJECTVALIDATIONRULE_WHERE);

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
		"objectValidationRule.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(objectValidationRule.uuid IS NULL OR objectValidationRule.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"objectValidationRule.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByObjectDefinitionId;
	private FinderPath _finderPathWithoutPaginationFindByObjectDefinitionId;
	private FinderPath _finderPathCountByObjectDefinitionId;

	/**
	 * Returns all the object validation rules where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @return the matching object validation rules
	 */
	@Override
	public List<ObjectValidationRule> findByObjectDefinitionId(
		long objectDefinitionId) {

		return findByObjectDefinitionId(
			objectDefinitionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object validation rules where objectDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @return the range of matching object validation rules
	 */
	@Override
	public List<ObjectValidationRule> findByObjectDefinitionId(
		long objectDefinitionId, int start, int end) {

		return findByObjectDefinitionId(objectDefinitionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object validation rules where objectDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object validation rules
	 */
	@Override
	public List<ObjectValidationRule> findByObjectDefinitionId(
		long objectDefinitionId, int start, int end,
		OrderByComparator<ObjectValidationRule> orderByComparator) {

		return findByObjectDefinitionId(
			objectDefinitionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object validation rules where objectDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object validation rules
	 */
	@Override
	public List<ObjectValidationRule> findByObjectDefinitionId(
		long objectDefinitionId, int start, int end,
		OrderByComparator<ObjectValidationRule> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByObjectDefinitionId;
				finderArgs = new Object[] {objectDefinitionId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByObjectDefinitionId;
			finderArgs = new Object[] {
				objectDefinitionId, start, end, orderByComparator
			};
		}

		List<ObjectValidationRule> list = null;

		if (useFinderCache) {
			list = (List<ObjectValidationRule>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectValidationRule objectValidationRule : list) {
					if (objectDefinitionId !=
							objectValidationRule.getObjectDefinitionId()) {

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

			sb.append(_SQL_SELECT_OBJECTVALIDATIONRULE_WHERE);

			sb.append(_FINDER_COLUMN_OBJECTDEFINITIONID_OBJECTDEFINITIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ObjectValidationRuleModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectDefinitionId);

				list = (List<ObjectValidationRule>)QueryUtil.list(
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
	 * Returns the first object validation rule in the ordered set where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule
	 * @throws NoSuchObjectValidationRuleException if a matching object validation rule could not be found
	 */
	@Override
	public ObjectValidationRule findByObjectDefinitionId_First(
			long objectDefinitionId,
			OrderByComparator<ObjectValidationRule> orderByComparator)
		throws NoSuchObjectValidationRuleException {

		ObjectValidationRule objectValidationRule =
			fetchByObjectDefinitionId_First(
				objectDefinitionId, orderByComparator);

		if (objectValidationRule != null) {
			return objectValidationRule;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectDefinitionId=");
		sb.append(objectDefinitionId);

		sb.append("}");

		throw new NoSuchObjectValidationRuleException(sb.toString());
	}

	/**
	 * Returns the first object validation rule in the ordered set where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule, or <code>null</code> if a matching object validation rule could not be found
	 */
	@Override
	public ObjectValidationRule fetchByObjectDefinitionId_First(
		long objectDefinitionId,
		OrderByComparator<ObjectValidationRule> orderByComparator) {

		List<ObjectValidationRule> list = findByObjectDefinitionId(
			objectDefinitionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object validation rule in the ordered set where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule
	 * @throws NoSuchObjectValidationRuleException if a matching object validation rule could not be found
	 */
	@Override
	public ObjectValidationRule findByObjectDefinitionId_Last(
			long objectDefinitionId,
			OrderByComparator<ObjectValidationRule> orderByComparator)
		throws NoSuchObjectValidationRuleException {

		ObjectValidationRule objectValidationRule =
			fetchByObjectDefinitionId_Last(
				objectDefinitionId, orderByComparator);

		if (objectValidationRule != null) {
			return objectValidationRule;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectDefinitionId=");
		sb.append(objectDefinitionId);

		sb.append("}");

		throw new NoSuchObjectValidationRuleException(sb.toString());
	}

	/**
	 * Returns the last object validation rule in the ordered set where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule, or <code>null</code> if a matching object validation rule could not be found
	 */
	@Override
	public ObjectValidationRule fetchByObjectDefinitionId_Last(
		long objectDefinitionId,
		OrderByComparator<ObjectValidationRule> orderByComparator) {

		int count = countByObjectDefinitionId(objectDefinitionId);

		if (count == 0) {
			return null;
		}

		List<ObjectValidationRule> list = findByObjectDefinitionId(
			objectDefinitionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object validation rules before and after the current object validation rule in the ordered set where objectDefinitionId = &#63;.
	 *
	 * @param objectValidationRuleId the primary key of the current object validation rule
	 * @param objectDefinitionId the object definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object validation rule
	 * @throws NoSuchObjectValidationRuleException if a object validation rule with the primary key could not be found
	 */
	@Override
	public ObjectValidationRule[] findByObjectDefinitionId_PrevAndNext(
			long objectValidationRuleId, long objectDefinitionId,
			OrderByComparator<ObjectValidationRule> orderByComparator)
		throws NoSuchObjectValidationRuleException {

		ObjectValidationRule objectValidationRule = findByPrimaryKey(
			objectValidationRuleId);

		Session session = null;

		try {
			session = openSession();

			ObjectValidationRule[] array = new ObjectValidationRuleImpl[3];

			array[0] = getByObjectDefinitionId_PrevAndNext(
				session, objectValidationRule, objectDefinitionId,
				orderByComparator, true);

			array[1] = objectValidationRule;

			array[2] = getByObjectDefinitionId_PrevAndNext(
				session, objectValidationRule, objectDefinitionId,
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

	protected ObjectValidationRule getByObjectDefinitionId_PrevAndNext(
		Session session, ObjectValidationRule objectValidationRule,
		long objectDefinitionId,
		OrderByComparator<ObjectValidationRule> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTVALIDATIONRULE_WHERE);

		sb.append(_FINDER_COLUMN_OBJECTDEFINITIONID_OBJECTDEFINITIONID_2);

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
			sb.append(ObjectValidationRuleModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(objectDefinitionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						objectValidationRule)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectValidationRule> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object validation rules where objectDefinitionId = &#63; from the database.
	 *
	 * @param objectDefinitionId the object definition ID
	 */
	@Override
	public void removeByObjectDefinitionId(long objectDefinitionId) {
		for (ObjectValidationRule objectValidationRule :
				findByObjectDefinitionId(
					objectDefinitionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(objectValidationRule);
		}
	}

	/**
	 * Returns the number of object validation rules where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @return the number of matching object validation rules
	 */
	@Override
	public int countByObjectDefinitionId(long objectDefinitionId) {
		FinderPath finderPath = _finderPathCountByObjectDefinitionId;

		Object[] finderArgs = new Object[] {objectDefinitionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTVALIDATIONRULE_WHERE);

			sb.append(_FINDER_COLUMN_OBJECTDEFINITIONID_OBJECTDEFINITIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectDefinitionId);

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
		_FINDER_COLUMN_OBJECTDEFINITIONID_OBJECTDEFINITIONID_2 =
			"objectValidationRule.objectDefinitionId = ?";

	private FinderPath _finderPathWithPaginationFindByODI_A;
	private FinderPath _finderPathWithoutPaginationFindByODI_A;
	private FinderPath _finderPathCountByODI_A;

	/**
	 * Returns all the object validation rules where objectDefinitionId = &#63; and active = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @return the matching object validation rules
	 */
	@Override
	public List<ObjectValidationRule> findByODI_A(
		long objectDefinitionId, boolean active) {

		return findByODI_A(
			objectDefinitionId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the object validation rules where objectDefinitionId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @return the range of matching object validation rules
	 */
	@Override
	public List<ObjectValidationRule> findByODI_A(
		long objectDefinitionId, boolean active, int start, int end) {

		return findByODI_A(objectDefinitionId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object validation rules where objectDefinitionId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object validation rules
	 */
	@Override
	public List<ObjectValidationRule> findByODI_A(
		long objectDefinitionId, boolean active, int start, int end,
		OrderByComparator<ObjectValidationRule> orderByComparator) {

		return findByODI_A(
			objectDefinitionId, active, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object validation rules where objectDefinitionId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object validation rules
	 */
	@Override
	public List<ObjectValidationRule> findByODI_A(
		long objectDefinitionId, boolean active, int start, int end,
		OrderByComparator<ObjectValidationRule> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByODI_A;
				finderArgs = new Object[] {objectDefinitionId, active};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByODI_A;
			finderArgs = new Object[] {
				objectDefinitionId, active, start, end, orderByComparator
			};
		}

		List<ObjectValidationRule> list = null;

		if (useFinderCache) {
			list = (List<ObjectValidationRule>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectValidationRule objectValidationRule : list) {
					if ((objectDefinitionId !=
							objectValidationRule.getObjectDefinitionId()) ||
						(active != objectValidationRule.isActive())) {

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

			sb.append(_SQL_SELECT_OBJECTVALIDATIONRULE_WHERE);

			sb.append(_FINDER_COLUMN_ODI_A_OBJECTDEFINITIONID_2);

			sb.append(_FINDER_COLUMN_ODI_A_ACTIVE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ObjectValidationRuleModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectDefinitionId);

				queryPos.add(active);

				list = (List<ObjectValidationRule>)QueryUtil.list(
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
	 * Returns the first object validation rule in the ordered set where objectDefinitionId = &#63; and active = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule
	 * @throws NoSuchObjectValidationRuleException if a matching object validation rule could not be found
	 */
	@Override
	public ObjectValidationRule findByODI_A_First(
			long objectDefinitionId, boolean active,
			OrderByComparator<ObjectValidationRule> orderByComparator)
		throws NoSuchObjectValidationRuleException {

		ObjectValidationRule objectValidationRule = fetchByODI_A_First(
			objectDefinitionId, active, orderByComparator);

		if (objectValidationRule != null) {
			return objectValidationRule;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectDefinitionId=");
		sb.append(objectDefinitionId);

		sb.append(", active=");
		sb.append(active);

		sb.append("}");

		throw new NoSuchObjectValidationRuleException(sb.toString());
	}

	/**
	 * Returns the first object validation rule in the ordered set where objectDefinitionId = &#63; and active = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule, or <code>null</code> if a matching object validation rule could not be found
	 */
	@Override
	public ObjectValidationRule fetchByODI_A_First(
		long objectDefinitionId, boolean active,
		OrderByComparator<ObjectValidationRule> orderByComparator) {

		List<ObjectValidationRule> list = findByODI_A(
			objectDefinitionId, active, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object validation rule in the ordered set where objectDefinitionId = &#63; and active = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule
	 * @throws NoSuchObjectValidationRuleException if a matching object validation rule could not be found
	 */
	@Override
	public ObjectValidationRule findByODI_A_Last(
			long objectDefinitionId, boolean active,
			OrderByComparator<ObjectValidationRule> orderByComparator)
		throws NoSuchObjectValidationRuleException {

		ObjectValidationRule objectValidationRule = fetchByODI_A_Last(
			objectDefinitionId, active, orderByComparator);

		if (objectValidationRule != null) {
			return objectValidationRule;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectDefinitionId=");
		sb.append(objectDefinitionId);

		sb.append(", active=");
		sb.append(active);

		sb.append("}");

		throw new NoSuchObjectValidationRuleException(sb.toString());
	}

	/**
	 * Returns the last object validation rule in the ordered set where objectDefinitionId = &#63; and active = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule, or <code>null</code> if a matching object validation rule could not be found
	 */
	@Override
	public ObjectValidationRule fetchByODI_A_Last(
		long objectDefinitionId, boolean active,
		OrderByComparator<ObjectValidationRule> orderByComparator) {

		int count = countByODI_A(objectDefinitionId, active);

		if (count == 0) {
			return null;
		}

		List<ObjectValidationRule> list = findByODI_A(
			objectDefinitionId, active, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object validation rules before and after the current object validation rule in the ordered set where objectDefinitionId = &#63; and active = &#63;.
	 *
	 * @param objectValidationRuleId the primary key of the current object validation rule
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object validation rule
	 * @throws NoSuchObjectValidationRuleException if a object validation rule with the primary key could not be found
	 */
	@Override
	public ObjectValidationRule[] findByODI_A_PrevAndNext(
			long objectValidationRuleId, long objectDefinitionId,
			boolean active,
			OrderByComparator<ObjectValidationRule> orderByComparator)
		throws NoSuchObjectValidationRuleException {

		ObjectValidationRule objectValidationRule = findByPrimaryKey(
			objectValidationRuleId);

		Session session = null;

		try {
			session = openSession();

			ObjectValidationRule[] array = new ObjectValidationRuleImpl[3];

			array[0] = getByODI_A_PrevAndNext(
				session, objectValidationRule, objectDefinitionId, active,
				orderByComparator, true);

			array[1] = objectValidationRule;

			array[2] = getByODI_A_PrevAndNext(
				session, objectValidationRule, objectDefinitionId, active,
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

	protected ObjectValidationRule getByODI_A_PrevAndNext(
		Session session, ObjectValidationRule objectValidationRule,
		long objectDefinitionId, boolean active,
		OrderByComparator<ObjectValidationRule> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTVALIDATIONRULE_WHERE);

		sb.append(_FINDER_COLUMN_ODI_A_OBJECTDEFINITIONID_2);

		sb.append(_FINDER_COLUMN_ODI_A_ACTIVE_2);

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
			sb.append(ObjectValidationRuleModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(objectDefinitionId);

		queryPos.add(active);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						objectValidationRule)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectValidationRule> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object validation rules where objectDefinitionId = &#63; and active = &#63; from the database.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 */
	@Override
	public void removeByODI_A(long objectDefinitionId, boolean active) {
		for (ObjectValidationRule objectValidationRule :
				findByODI_A(
					objectDefinitionId, active, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(objectValidationRule);
		}
	}

	/**
	 * Returns the number of object validation rules where objectDefinitionId = &#63; and active = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @return the number of matching object validation rules
	 */
	@Override
	public int countByODI_A(long objectDefinitionId, boolean active) {
		FinderPath finderPath = _finderPathCountByODI_A;

		Object[] finderArgs = new Object[] {objectDefinitionId, active};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_OBJECTVALIDATIONRULE_WHERE);

			sb.append(_FINDER_COLUMN_ODI_A_OBJECTDEFINITIONID_2);

			sb.append(_FINDER_COLUMN_ODI_A_ACTIVE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectDefinitionId);

				queryPos.add(active);

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

	private static final String _FINDER_COLUMN_ODI_A_OBJECTDEFINITIONID_2 =
		"objectValidationRule.objectDefinitionId = ? AND ";

	private static final String _FINDER_COLUMN_ODI_A_ACTIVE_2 =
		"objectValidationRule.active = ?";

	private FinderPath _finderPathWithPaginationFindByODI_E;
	private FinderPath _finderPathWithoutPaginationFindByODI_E;
	private FinderPath _finderPathCountByODI_E;

	/**
	 * Returns all the object validation rules where objectDefinitionId = &#63; and engine = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param engine the engine
	 * @return the matching object validation rules
	 */
	@Override
	public List<ObjectValidationRule> findByODI_E(
		long objectDefinitionId, String engine) {

		return findByODI_E(
			objectDefinitionId, engine, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the object validation rules where objectDefinitionId = &#63; and engine = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param engine the engine
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @return the range of matching object validation rules
	 */
	@Override
	public List<ObjectValidationRule> findByODI_E(
		long objectDefinitionId, String engine, int start, int end) {

		return findByODI_E(objectDefinitionId, engine, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object validation rules where objectDefinitionId = &#63; and engine = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param engine the engine
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object validation rules
	 */
	@Override
	public List<ObjectValidationRule> findByODI_E(
		long objectDefinitionId, String engine, int start, int end,
		OrderByComparator<ObjectValidationRule> orderByComparator) {

		return findByODI_E(
			objectDefinitionId, engine, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object validation rules where objectDefinitionId = &#63; and engine = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param engine the engine
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object validation rules
	 */
	@Override
	public List<ObjectValidationRule> findByODI_E(
		long objectDefinitionId, String engine, int start, int end,
		OrderByComparator<ObjectValidationRule> orderByComparator,
		boolean useFinderCache) {

		engine = Objects.toString(engine, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByODI_E;
				finderArgs = new Object[] {objectDefinitionId, engine};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByODI_E;
			finderArgs = new Object[] {
				objectDefinitionId, engine, start, end, orderByComparator
			};
		}

		List<ObjectValidationRule> list = null;

		if (useFinderCache) {
			list = (List<ObjectValidationRule>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectValidationRule objectValidationRule : list) {
					if ((objectDefinitionId !=
							objectValidationRule.getObjectDefinitionId()) ||
						!engine.equals(objectValidationRule.getEngine())) {

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

			sb.append(_SQL_SELECT_OBJECTVALIDATIONRULE_WHERE);

			sb.append(_FINDER_COLUMN_ODI_E_OBJECTDEFINITIONID_2);

			boolean bindEngine = false;

			if (engine.isEmpty()) {
				sb.append(_FINDER_COLUMN_ODI_E_ENGINE_3);
			}
			else {
				bindEngine = true;

				sb.append(_FINDER_COLUMN_ODI_E_ENGINE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ObjectValidationRuleModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectDefinitionId);

				if (bindEngine) {
					queryPos.add(engine);
				}

				list = (List<ObjectValidationRule>)QueryUtil.list(
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
	 * Returns the first object validation rule in the ordered set where objectDefinitionId = &#63; and engine = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param engine the engine
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule
	 * @throws NoSuchObjectValidationRuleException if a matching object validation rule could not be found
	 */
	@Override
	public ObjectValidationRule findByODI_E_First(
			long objectDefinitionId, String engine,
			OrderByComparator<ObjectValidationRule> orderByComparator)
		throws NoSuchObjectValidationRuleException {

		ObjectValidationRule objectValidationRule = fetchByODI_E_First(
			objectDefinitionId, engine, orderByComparator);

		if (objectValidationRule != null) {
			return objectValidationRule;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectDefinitionId=");
		sb.append(objectDefinitionId);

		sb.append(", engine=");
		sb.append(engine);

		sb.append("}");

		throw new NoSuchObjectValidationRuleException(sb.toString());
	}

	/**
	 * Returns the first object validation rule in the ordered set where objectDefinitionId = &#63; and engine = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param engine the engine
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule, or <code>null</code> if a matching object validation rule could not be found
	 */
	@Override
	public ObjectValidationRule fetchByODI_E_First(
		long objectDefinitionId, String engine,
		OrderByComparator<ObjectValidationRule> orderByComparator) {

		List<ObjectValidationRule> list = findByODI_E(
			objectDefinitionId, engine, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object validation rule in the ordered set where objectDefinitionId = &#63; and engine = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param engine the engine
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule
	 * @throws NoSuchObjectValidationRuleException if a matching object validation rule could not be found
	 */
	@Override
	public ObjectValidationRule findByODI_E_Last(
			long objectDefinitionId, String engine,
			OrderByComparator<ObjectValidationRule> orderByComparator)
		throws NoSuchObjectValidationRuleException {

		ObjectValidationRule objectValidationRule = fetchByODI_E_Last(
			objectDefinitionId, engine, orderByComparator);

		if (objectValidationRule != null) {
			return objectValidationRule;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectDefinitionId=");
		sb.append(objectDefinitionId);

		sb.append(", engine=");
		sb.append(engine);

		sb.append("}");

		throw new NoSuchObjectValidationRuleException(sb.toString());
	}

	/**
	 * Returns the last object validation rule in the ordered set where objectDefinitionId = &#63; and engine = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param engine the engine
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule, or <code>null</code> if a matching object validation rule could not be found
	 */
	@Override
	public ObjectValidationRule fetchByODI_E_Last(
		long objectDefinitionId, String engine,
		OrderByComparator<ObjectValidationRule> orderByComparator) {

		int count = countByODI_E(objectDefinitionId, engine);

		if (count == 0) {
			return null;
		}

		List<ObjectValidationRule> list = findByODI_E(
			objectDefinitionId, engine, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object validation rules before and after the current object validation rule in the ordered set where objectDefinitionId = &#63; and engine = &#63;.
	 *
	 * @param objectValidationRuleId the primary key of the current object validation rule
	 * @param objectDefinitionId the object definition ID
	 * @param engine the engine
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object validation rule
	 * @throws NoSuchObjectValidationRuleException if a object validation rule with the primary key could not be found
	 */
	@Override
	public ObjectValidationRule[] findByODI_E_PrevAndNext(
			long objectValidationRuleId, long objectDefinitionId, String engine,
			OrderByComparator<ObjectValidationRule> orderByComparator)
		throws NoSuchObjectValidationRuleException {

		engine = Objects.toString(engine, "");

		ObjectValidationRule objectValidationRule = findByPrimaryKey(
			objectValidationRuleId);

		Session session = null;

		try {
			session = openSession();

			ObjectValidationRule[] array = new ObjectValidationRuleImpl[3];

			array[0] = getByODI_E_PrevAndNext(
				session, objectValidationRule, objectDefinitionId, engine,
				orderByComparator, true);

			array[1] = objectValidationRule;

			array[2] = getByODI_E_PrevAndNext(
				session, objectValidationRule, objectDefinitionId, engine,
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

	protected ObjectValidationRule getByODI_E_PrevAndNext(
		Session session, ObjectValidationRule objectValidationRule,
		long objectDefinitionId, String engine,
		OrderByComparator<ObjectValidationRule> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTVALIDATIONRULE_WHERE);

		sb.append(_FINDER_COLUMN_ODI_E_OBJECTDEFINITIONID_2);

		boolean bindEngine = false;

		if (engine.isEmpty()) {
			sb.append(_FINDER_COLUMN_ODI_E_ENGINE_3);
		}
		else {
			bindEngine = true;

			sb.append(_FINDER_COLUMN_ODI_E_ENGINE_2);
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
			sb.append(ObjectValidationRuleModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(objectDefinitionId);

		if (bindEngine) {
			queryPos.add(engine);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						objectValidationRule)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectValidationRule> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object validation rules where objectDefinitionId = &#63; and engine = &#63; from the database.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param engine the engine
	 */
	@Override
	public void removeByODI_E(long objectDefinitionId, String engine) {
		for (ObjectValidationRule objectValidationRule :
				findByODI_E(
					objectDefinitionId, engine, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(objectValidationRule);
		}
	}

	/**
	 * Returns the number of object validation rules where objectDefinitionId = &#63; and engine = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param engine the engine
	 * @return the number of matching object validation rules
	 */
	@Override
	public int countByODI_E(long objectDefinitionId, String engine) {
		engine = Objects.toString(engine, "");

		FinderPath finderPath = _finderPathCountByODI_E;

		Object[] finderArgs = new Object[] {objectDefinitionId, engine};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_OBJECTVALIDATIONRULE_WHERE);

			sb.append(_FINDER_COLUMN_ODI_E_OBJECTDEFINITIONID_2);

			boolean bindEngine = false;

			if (engine.isEmpty()) {
				sb.append(_FINDER_COLUMN_ODI_E_ENGINE_3);
			}
			else {
				bindEngine = true;

				sb.append(_FINDER_COLUMN_ODI_E_ENGINE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectDefinitionId);

				if (bindEngine) {
					queryPos.add(engine);
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

	private static final String _FINDER_COLUMN_ODI_E_OBJECTDEFINITIONID_2 =
		"objectValidationRule.objectDefinitionId = ? AND ";

	private static final String _FINDER_COLUMN_ODI_E_ENGINE_2 =
		"objectValidationRule.engine = ?";

	private static final String _FINDER_COLUMN_ODI_E_ENGINE_3 =
		"(objectValidationRule.engine IS NULL OR objectValidationRule.engine = '')";

	private FinderPath _finderPathWithPaginationFindByODI_O;
	private FinderPath _finderPathWithoutPaginationFindByODI_O;
	private FinderPath _finderPathCountByODI_O;

	/**
	 * Returns all the object validation rules where objectDefinitionId = &#63; and outputType = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param outputType the output type
	 * @return the matching object validation rules
	 */
	@Override
	public List<ObjectValidationRule> findByODI_O(
		long objectDefinitionId, String outputType) {

		return findByODI_O(
			objectDefinitionId, outputType, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object validation rules where objectDefinitionId = &#63; and outputType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param outputType the output type
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @return the range of matching object validation rules
	 */
	@Override
	public List<ObjectValidationRule> findByODI_O(
		long objectDefinitionId, String outputType, int start, int end) {

		return findByODI_O(objectDefinitionId, outputType, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object validation rules where objectDefinitionId = &#63; and outputType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param outputType the output type
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object validation rules
	 */
	@Override
	public List<ObjectValidationRule> findByODI_O(
		long objectDefinitionId, String outputType, int start, int end,
		OrderByComparator<ObjectValidationRule> orderByComparator) {

		return findByODI_O(
			objectDefinitionId, outputType, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the object validation rules where objectDefinitionId = &#63; and outputType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param outputType the output type
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object validation rules
	 */
	@Override
	public List<ObjectValidationRule> findByODI_O(
		long objectDefinitionId, String outputType, int start, int end,
		OrderByComparator<ObjectValidationRule> orderByComparator,
		boolean useFinderCache) {

		outputType = Objects.toString(outputType, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByODI_O;
				finderArgs = new Object[] {objectDefinitionId, outputType};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByODI_O;
			finderArgs = new Object[] {
				objectDefinitionId, outputType, start, end, orderByComparator
			};
		}

		List<ObjectValidationRule> list = null;

		if (useFinderCache) {
			list = (List<ObjectValidationRule>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectValidationRule objectValidationRule : list) {
					if ((objectDefinitionId !=
							objectValidationRule.getObjectDefinitionId()) ||
						!outputType.equals(
							objectValidationRule.getOutputType())) {

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

			sb.append(_SQL_SELECT_OBJECTVALIDATIONRULE_WHERE);

			sb.append(_FINDER_COLUMN_ODI_O_OBJECTDEFINITIONID_2);

			boolean bindOutputType = false;

			if (outputType.isEmpty()) {
				sb.append(_FINDER_COLUMN_ODI_O_OUTPUTTYPE_3);
			}
			else {
				bindOutputType = true;

				sb.append(_FINDER_COLUMN_ODI_O_OUTPUTTYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ObjectValidationRuleModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectDefinitionId);

				if (bindOutputType) {
					queryPos.add(outputType);
				}

				list = (List<ObjectValidationRule>)QueryUtil.list(
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
	 * Returns the first object validation rule in the ordered set where objectDefinitionId = &#63; and outputType = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param outputType the output type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule
	 * @throws NoSuchObjectValidationRuleException if a matching object validation rule could not be found
	 */
	@Override
	public ObjectValidationRule findByODI_O_First(
			long objectDefinitionId, String outputType,
			OrderByComparator<ObjectValidationRule> orderByComparator)
		throws NoSuchObjectValidationRuleException {

		ObjectValidationRule objectValidationRule = fetchByODI_O_First(
			objectDefinitionId, outputType, orderByComparator);

		if (objectValidationRule != null) {
			return objectValidationRule;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectDefinitionId=");
		sb.append(objectDefinitionId);

		sb.append(", outputType=");
		sb.append(outputType);

		sb.append("}");

		throw new NoSuchObjectValidationRuleException(sb.toString());
	}

	/**
	 * Returns the first object validation rule in the ordered set where objectDefinitionId = &#63; and outputType = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param outputType the output type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule, or <code>null</code> if a matching object validation rule could not be found
	 */
	@Override
	public ObjectValidationRule fetchByODI_O_First(
		long objectDefinitionId, String outputType,
		OrderByComparator<ObjectValidationRule> orderByComparator) {

		List<ObjectValidationRule> list = findByODI_O(
			objectDefinitionId, outputType, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object validation rule in the ordered set where objectDefinitionId = &#63; and outputType = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param outputType the output type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule
	 * @throws NoSuchObjectValidationRuleException if a matching object validation rule could not be found
	 */
	@Override
	public ObjectValidationRule findByODI_O_Last(
			long objectDefinitionId, String outputType,
			OrderByComparator<ObjectValidationRule> orderByComparator)
		throws NoSuchObjectValidationRuleException {

		ObjectValidationRule objectValidationRule = fetchByODI_O_Last(
			objectDefinitionId, outputType, orderByComparator);

		if (objectValidationRule != null) {
			return objectValidationRule;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectDefinitionId=");
		sb.append(objectDefinitionId);

		sb.append(", outputType=");
		sb.append(outputType);

		sb.append("}");

		throw new NoSuchObjectValidationRuleException(sb.toString());
	}

	/**
	 * Returns the last object validation rule in the ordered set where objectDefinitionId = &#63; and outputType = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param outputType the output type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule, or <code>null</code> if a matching object validation rule could not be found
	 */
	@Override
	public ObjectValidationRule fetchByODI_O_Last(
		long objectDefinitionId, String outputType,
		OrderByComparator<ObjectValidationRule> orderByComparator) {

		int count = countByODI_O(objectDefinitionId, outputType);

		if (count == 0) {
			return null;
		}

		List<ObjectValidationRule> list = findByODI_O(
			objectDefinitionId, outputType, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object validation rules before and after the current object validation rule in the ordered set where objectDefinitionId = &#63; and outputType = &#63;.
	 *
	 * @param objectValidationRuleId the primary key of the current object validation rule
	 * @param objectDefinitionId the object definition ID
	 * @param outputType the output type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object validation rule
	 * @throws NoSuchObjectValidationRuleException if a object validation rule with the primary key could not be found
	 */
	@Override
	public ObjectValidationRule[] findByODI_O_PrevAndNext(
			long objectValidationRuleId, long objectDefinitionId,
			String outputType,
			OrderByComparator<ObjectValidationRule> orderByComparator)
		throws NoSuchObjectValidationRuleException {

		outputType = Objects.toString(outputType, "");

		ObjectValidationRule objectValidationRule = findByPrimaryKey(
			objectValidationRuleId);

		Session session = null;

		try {
			session = openSession();

			ObjectValidationRule[] array = new ObjectValidationRuleImpl[3];

			array[0] = getByODI_O_PrevAndNext(
				session, objectValidationRule, objectDefinitionId, outputType,
				orderByComparator, true);

			array[1] = objectValidationRule;

			array[2] = getByODI_O_PrevAndNext(
				session, objectValidationRule, objectDefinitionId, outputType,
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

	protected ObjectValidationRule getByODI_O_PrevAndNext(
		Session session, ObjectValidationRule objectValidationRule,
		long objectDefinitionId, String outputType,
		OrderByComparator<ObjectValidationRule> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTVALIDATIONRULE_WHERE);

		sb.append(_FINDER_COLUMN_ODI_O_OBJECTDEFINITIONID_2);

		boolean bindOutputType = false;

		if (outputType.isEmpty()) {
			sb.append(_FINDER_COLUMN_ODI_O_OUTPUTTYPE_3);
		}
		else {
			bindOutputType = true;

			sb.append(_FINDER_COLUMN_ODI_O_OUTPUTTYPE_2);
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
			sb.append(ObjectValidationRuleModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(objectDefinitionId);

		if (bindOutputType) {
			queryPos.add(outputType);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						objectValidationRule)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectValidationRule> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object validation rules where objectDefinitionId = &#63; and outputType = &#63; from the database.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param outputType the output type
	 */
	@Override
	public void removeByODI_O(long objectDefinitionId, String outputType) {
		for (ObjectValidationRule objectValidationRule :
				findByODI_O(
					objectDefinitionId, outputType, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(objectValidationRule);
		}
	}

	/**
	 * Returns the number of object validation rules where objectDefinitionId = &#63; and outputType = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param outputType the output type
	 * @return the number of matching object validation rules
	 */
	@Override
	public int countByODI_O(long objectDefinitionId, String outputType) {
		outputType = Objects.toString(outputType, "");

		FinderPath finderPath = _finderPathCountByODI_O;

		Object[] finderArgs = new Object[] {objectDefinitionId, outputType};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_OBJECTVALIDATIONRULE_WHERE);

			sb.append(_FINDER_COLUMN_ODI_O_OBJECTDEFINITIONID_2);

			boolean bindOutputType = false;

			if (outputType.isEmpty()) {
				sb.append(_FINDER_COLUMN_ODI_O_OUTPUTTYPE_3);
			}
			else {
				bindOutputType = true;

				sb.append(_FINDER_COLUMN_ODI_O_OUTPUTTYPE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectDefinitionId);

				if (bindOutputType) {
					queryPos.add(outputType);
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

	private static final String _FINDER_COLUMN_ODI_O_OBJECTDEFINITIONID_2 =
		"objectValidationRule.objectDefinitionId = ? AND ";

	private static final String _FINDER_COLUMN_ODI_O_OUTPUTTYPE_2 =
		"objectValidationRule.outputType = ?";

	private static final String _FINDER_COLUMN_ODI_O_OUTPUTTYPE_3 =
		"(objectValidationRule.outputType IS NULL OR objectValidationRule.outputType = '')";

	private FinderPath _finderPathWithPaginationFindByA_E;
	private FinderPath _finderPathWithoutPaginationFindByA_E;
	private FinderPath _finderPathCountByA_E;

	/**
	 * Returns all the object validation rules where active = &#63; and engine = &#63;.
	 *
	 * @param active the active
	 * @param engine the engine
	 * @return the matching object validation rules
	 */
	@Override
	public List<ObjectValidationRule> findByA_E(boolean active, String engine) {
		return findByA_E(
			active, engine, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object validation rules where active = &#63; and engine = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param active the active
	 * @param engine the engine
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @return the range of matching object validation rules
	 */
	@Override
	public List<ObjectValidationRule> findByA_E(
		boolean active, String engine, int start, int end) {

		return findByA_E(active, engine, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object validation rules where active = &#63; and engine = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param active the active
	 * @param engine the engine
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object validation rules
	 */
	@Override
	public List<ObjectValidationRule> findByA_E(
		boolean active, String engine, int start, int end,
		OrderByComparator<ObjectValidationRule> orderByComparator) {

		return findByA_E(active, engine, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object validation rules where active = &#63; and engine = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param active the active
	 * @param engine the engine
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object validation rules
	 */
	@Override
	public List<ObjectValidationRule> findByA_E(
		boolean active, String engine, int start, int end,
		OrderByComparator<ObjectValidationRule> orderByComparator,
		boolean useFinderCache) {

		engine = Objects.toString(engine, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByA_E;
				finderArgs = new Object[] {active, engine};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByA_E;
			finderArgs = new Object[] {
				active, engine, start, end, orderByComparator
			};
		}

		List<ObjectValidationRule> list = null;

		if (useFinderCache) {
			list = (List<ObjectValidationRule>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectValidationRule objectValidationRule : list) {
					if ((active != objectValidationRule.isActive()) ||
						!engine.equals(objectValidationRule.getEngine())) {

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

			sb.append(_SQL_SELECT_OBJECTVALIDATIONRULE_WHERE);

			sb.append(_FINDER_COLUMN_A_E_ACTIVE_2);

			boolean bindEngine = false;

			if (engine.isEmpty()) {
				sb.append(_FINDER_COLUMN_A_E_ENGINE_3);
			}
			else {
				bindEngine = true;

				sb.append(_FINDER_COLUMN_A_E_ENGINE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ObjectValidationRuleModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(active);

				if (bindEngine) {
					queryPos.add(engine);
				}

				list = (List<ObjectValidationRule>)QueryUtil.list(
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
	 * Returns the first object validation rule in the ordered set where active = &#63; and engine = &#63;.
	 *
	 * @param active the active
	 * @param engine the engine
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule
	 * @throws NoSuchObjectValidationRuleException if a matching object validation rule could not be found
	 */
	@Override
	public ObjectValidationRule findByA_E_First(
			boolean active, String engine,
			OrderByComparator<ObjectValidationRule> orderByComparator)
		throws NoSuchObjectValidationRuleException {

		ObjectValidationRule objectValidationRule = fetchByA_E_First(
			active, engine, orderByComparator);

		if (objectValidationRule != null) {
			return objectValidationRule;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("active=");
		sb.append(active);

		sb.append(", engine=");
		sb.append(engine);

		sb.append("}");

		throw new NoSuchObjectValidationRuleException(sb.toString());
	}

	/**
	 * Returns the first object validation rule in the ordered set where active = &#63; and engine = &#63;.
	 *
	 * @param active the active
	 * @param engine the engine
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule, or <code>null</code> if a matching object validation rule could not be found
	 */
	@Override
	public ObjectValidationRule fetchByA_E_First(
		boolean active, String engine,
		OrderByComparator<ObjectValidationRule> orderByComparator) {

		List<ObjectValidationRule> list = findByA_E(
			active, engine, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object validation rule in the ordered set where active = &#63; and engine = &#63;.
	 *
	 * @param active the active
	 * @param engine the engine
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule
	 * @throws NoSuchObjectValidationRuleException if a matching object validation rule could not be found
	 */
	@Override
	public ObjectValidationRule findByA_E_Last(
			boolean active, String engine,
			OrderByComparator<ObjectValidationRule> orderByComparator)
		throws NoSuchObjectValidationRuleException {

		ObjectValidationRule objectValidationRule = fetchByA_E_Last(
			active, engine, orderByComparator);

		if (objectValidationRule != null) {
			return objectValidationRule;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("active=");
		sb.append(active);

		sb.append(", engine=");
		sb.append(engine);

		sb.append("}");

		throw new NoSuchObjectValidationRuleException(sb.toString());
	}

	/**
	 * Returns the last object validation rule in the ordered set where active = &#63; and engine = &#63;.
	 *
	 * @param active the active
	 * @param engine the engine
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule, or <code>null</code> if a matching object validation rule could not be found
	 */
	@Override
	public ObjectValidationRule fetchByA_E_Last(
		boolean active, String engine,
		OrderByComparator<ObjectValidationRule> orderByComparator) {

		int count = countByA_E(active, engine);

		if (count == 0) {
			return null;
		}

		List<ObjectValidationRule> list = findByA_E(
			active, engine, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object validation rules before and after the current object validation rule in the ordered set where active = &#63; and engine = &#63;.
	 *
	 * @param objectValidationRuleId the primary key of the current object validation rule
	 * @param active the active
	 * @param engine the engine
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object validation rule
	 * @throws NoSuchObjectValidationRuleException if a object validation rule with the primary key could not be found
	 */
	@Override
	public ObjectValidationRule[] findByA_E_PrevAndNext(
			long objectValidationRuleId, boolean active, String engine,
			OrderByComparator<ObjectValidationRule> orderByComparator)
		throws NoSuchObjectValidationRuleException {

		engine = Objects.toString(engine, "");

		ObjectValidationRule objectValidationRule = findByPrimaryKey(
			objectValidationRuleId);

		Session session = null;

		try {
			session = openSession();

			ObjectValidationRule[] array = new ObjectValidationRuleImpl[3];

			array[0] = getByA_E_PrevAndNext(
				session, objectValidationRule, active, engine,
				orderByComparator, true);

			array[1] = objectValidationRule;

			array[2] = getByA_E_PrevAndNext(
				session, objectValidationRule, active, engine,
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

	protected ObjectValidationRule getByA_E_PrevAndNext(
		Session session, ObjectValidationRule objectValidationRule,
		boolean active, String engine,
		OrderByComparator<ObjectValidationRule> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTVALIDATIONRULE_WHERE);

		sb.append(_FINDER_COLUMN_A_E_ACTIVE_2);

		boolean bindEngine = false;

		if (engine.isEmpty()) {
			sb.append(_FINDER_COLUMN_A_E_ENGINE_3);
		}
		else {
			bindEngine = true;

			sb.append(_FINDER_COLUMN_A_E_ENGINE_2);
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
			sb.append(ObjectValidationRuleModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(active);

		if (bindEngine) {
			queryPos.add(engine);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						objectValidationRule)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectValidationRule> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object validation rules where active = &#63; and engine = &#63; from the database.
	 *
	 * @param active the active
	 * @param engine the engine
	 */
	@Override
	public void removeByA_E(boolean active, String engine) {
		for (ObjectValidationRule objectValidationRule :
				findByA_E(
					active, engine, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(objectValidationRule);
		}
	}

	/**
	 * Returns the number of object validation rules where active = &#63; and engine = &#63;.
	 *
	 * @param active the active
	 * @param engine the engine
	 * @return the number of matching object validation rules
	 */
	@Override
	public int countByA_E(boolean active, String engine) {
		engine = Objects.toString(engine, "");

		FinderPath finderPath = _finderPathCountByA_E;

		Object[] finderArgs = new Object[] {active, engine};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_OBJECTVALIDATIONRULE_WHERE);

			sb.append(_FINDER_COLUMN_A_E_ACTIVE_2);

			boolean bindEngine = false;

			if (engine.isEmpty()) {
				sb.append(_FINDER_COLUMN_A_E_ENGINE_3);
			}
			else {
				bindEngine = true;

				sb.append(_FINDER_COLUMN_A_E_ENGINE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(active);

				if (bindEngine) {
					queryPos.add(engine);
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

	private static final String _FINDER_COLUMN_A_E_ACTIVE_2 =
		"objectValidationRule.active = ? AND ";

	private static final String _FINDER_COLUMN_A_E_ENGINE_2 =
		"objectValidationRule.engine = ?";

	private static final String _FINDER_COLUMN_A_E_ENGINE_3 =
		"(objectValidationRule.engine IS NULL OR objectValidationRule.engine = '')";

	private FinderPath _finderPathFetchByERC_C_ODI;

	/**
	 * Returns the object validation rule where externalReferenceCode = &#63; and companyId = &#63; and objectDefinitionId = &#63; or throws a <code>NoSuchObjectValidationRuleException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param objectDefinitionId the object definition ID
	 * @return the matching object validation rule
	 * @throws NoSuchObjectValidationRuleException if a matching object validation rule could not be found
	 */
	@Override
	public ObjectValidationRule findByERC_C_ODI(
			String externalReferenceCode, long companyId,
			long objectDefinitionId)
		throws NoSuchObjectValidationRuleException {

		ObjectValidationRule objectValidationRule = fetchByERC_C_ODI(
			externalReferenceCode, companyId, objectDefinitionId);

		if (objectValidationRule == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("externalReferenceCode=");
			sb.append(externalReferenceCode);

			sb.append(", companyId=");
			sb.append(companyId);

			sb.append(", objectDefinitionId=");
			sb.append(objectDefinitionId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchObjectValidationRuleException(sb.toString());
		}

		return objectValidationRule;
	}

	/**
	 * Returns the object validation rule where externalReferenceCode = &#63; and companyId = &#63; and objectDefinitionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param objectDefinitionId the object definition ID
	 * @return the matching object validation rule, or <code>null</code> if a matching object validation rule could not be found
	 */
	@Override
	public ObjectValidationRule fetchByERC_C_ODI(
		String externalReferenceCode, long companyId, long objectDefinitionId) {

		return fetchByERC_C_ODI(
			externalReferenceCode, companyId, objectDefinitionId, true);
	}

	/**
	 * Returns the object validation rule where externalReferenceCode = &#63; and companyId = &#63; and objectDefinitionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param objectDefinitionId the object definition ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching object validation rule, or <code>null</code> if a matching object validation rule could not be found
	 */
	@Override
	public ObjectValidationRule fetchByERC_C_ODI(
		String externalReferenceCode, long companyId, long objectDefinitionId,
		boolean useFinderCache) {

		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				externalReferenceCode, companyId, objectDefinitionId
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByERC_C_ODI, finderArgs, this);
		}

		if (result instanceof ObjectValidationRule) {
			ObjectValidationRule objectValidationRule =
				(ObjectValidationRule)result;

			if (!Objects.equals(
					externalReferenceCode,
					objectValidationRule.getExternalReferenceCode()) ||
				(companyId != objectValidationRule.getCompanyId()) ||
				(objectDefinitionId !=
					objectValidationRule.getObjectDefinitionId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_OBJECTVALIDATIONRULE_WHERE);

			boolean bindExternalReferenceCode = false;

			if (externalReferenceCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_ERC_C_ODI_EXTERNALREFERENCECODE_3);
			}
			else {
				bindExternalReferenceCode = true;

				sb.append(_FINDER_COLUMN_ERC_C_ODI_EXTERNALREFERENCECODE_2);
			}

			sb.append(_FINDER_COLUMN_ERC_C_ODI_COMPANYID_2);

			sb.append(_FINDER_COLUMN_ERC_C_ODI_OBJECTDEFINITIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindExternalReferenceCode) {
					queryPos.add(externalReferenceCode);
				}

				queryPos.add(companyId);

				queryPos.add(objectDefinitionId);

				List<ObjectValidationRule> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByERC_C_ODI, finderArgs, list);
					}
				}
				else {
					ObjectValidationRule objectValidationRule = list.get(0);

					result = objectValidationRule;

					cacheResult(objectValidationRule);
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
			return (ObjectValidationRule)result;
		}
	}

	/**
	 * Removes the object validation rule where externalReferenceCode = &#63; and companyId = &#63; and objectDefinitionId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param objectDefinitionId the object definition ID
	 * @return the object validation rule that was removed
	 */
	@Override
	public ObjectValidationRule removeByERC_C_ODI(
			String externalReferenceCode, long companyId,
			long objectDefinitionId)
		throws NoSuchObjectValidationRuleException {

		ObjectValidationRule objectValidationRule = findByERC_C_ODI(
			externalReferenceCode, companyId, objectDefinitionId);

		return remove(objectValidationRule);
	}

	/**
	 * Returns the number of object validation rules where externalReferenceCode = &#63; and companyId = &#63; and objectDefinitionId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param objectDefinitionId the object definition ID
	 * @return the number of matching object validation rules
	 */
	@Override
	public int countByERC_C_ODI(
		String externalReferenceCode, long companyId, long objectDefinitionId) {

		ObjectValidationRule objectValidationRule = fetchByERC_C_ODI(
			externalReferenceCode, companyId, objectDefinitionId);

		if (objectValidationRule == null) {
			return 0;
		}

		return 1;
	}

	private static final String
		_FINDER_COLUMN_ERC_C_ODI_EXTERNALREFERENCECODE_2 =
			"objectValidationRule.externalReferenceCode = ? AND ";

	private static final String
		_FINDER_COLUMN_ERC_C_ODI_EXTERNALREFERENCECODE_3 =
			"(objectValidationRule.externalReferenceCode IS NULL OR objectValidationRule.externalReferenceCode = '') AND ";

	private static final String _FINDER_COLUMN_ERC_C_ODI_COMPANYID_2 =
		"objectValidationRule.companyId = ? AND ";

	private static final String _FINDER_COLUMN_ERC_C_ODI_OBJECTDEFINITIONID_2 =
		"objectValidationRule.objectDefinitionId = ?";

	public ObjectValidationRulePersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("active", "active_");
		dbColumnNames.put("system", "system_");

		setDBColumnNames(dbColumnNames);

		setModelClass(ObjectValidationRule.class);

		setModelImplClass(ObjectValidationRuleImpl.class);
		setModelPKClass(long.class);

		setTable(ObjectValidationRuleTable.INSTANCE);
	}

	/**
	 * Caches the object validation rule in the entity cache if it is enabled.
	 *
	 * @param objectValidationRule the object validation rule
	 */
	@Override
	public void cacheResult(ObjectValidationRule objectValidationRule) {
		entityCache.putResult(
			ObjectValidationRuleImpl.class,
			objectValidationRule.getPrimaryKey(), objectValidationRule);

		finderCache.putResult(
			_finderPathFetchByERC_C_ODI,
			new Object[] {
				objectValidationRule.getExternalReferenceCode(),
				objectValidationRule.getCompanyId(),
				objectValidationRule.getObjectDefinitionId()
			},
			objectValidationRule);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the object validation rules in the entity cache if it is enabled.
	 *
	 * @param objectValidationRules the object validation rules
	 */
	@Override
	public void cacheResult(List<ObjectValidationRule> objectValidationRules) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (objectValidationRules.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ObjectValidationRule objectValidationRule :
				objectValidationRules) {

			if (entityCache.getResult(
					ObjectValidationRuleImpl.class,
					objectValidationRule.getPrimaryKey()) == null) {

				cacheResult(objectValidationRule);
			}
		}
	}

	/**
	 * Clears the cache for all object validation rules.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ObjectValidationRuleImpl.class);

		finderCache.clearCache(ObjectValidationRuleImpl.class);
	}

	/**
	 * Clears the cache for the object validation rule.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ObjectValidationRule objectValidationRule) {
		entityCache.removeResult(
			ObjectValidationRuleImpl.class, objectValidationRule);
	}

	@Override
	public void clearCache(List<ObjectValidationRule> objectValidationRules) {
		for (ObjectValidationRule objectValidationRule :
				objectValidationRules) {

			entityCache.removeResult(
				ObjectValidationRuleImpl.class, objectValidationRule);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(ObjectValidationRuleImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				ObjectValidationRuleImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		ObjectValidationRuleModelImpl objectValidationRuleModelImpl) {

		Object[] args = new Object[] {
			objectValidationRuleModelImpl.getExternalReferenceCode(),
			objectValidationRuleModelImpl.getCompanyId(),
			objectValidationRuleModelImpl.getObjectDefinitionId()
		};

		finderCache.putResult(
			_finderPathFetchByERC_C_ODI, args, objectValidationRuleModelImpl);
	}

	/**
	 * Creates a new object validation rule with the primary key. Does not add the object validation rule to the database.
	 *
	 * @param objectValidationRuleId the primary key for the new object validation rule
	 * @return the new object validation rule
	 */
	@Override
	public ObjectValidationRule create(long objectValidationRuleId) {
		ObjectValidationRule objectValidationRule =
			new ObjectValidationRuleImpl();

		objectValidationRule.setNew(true);
		objectValidationRule.setPrimaryKey(objectValidationRuleId);

		String uuid = PortalUUIDUtil.generate();

		objectValidationRule.setUuid(uuid);

		objectValidationRule.setCompanyId(CompanyThreadLocal.getCompanyId());

		return objectValidationRule;
	}

	/**
	 * Removes the object validation rule with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectValidationRuleId the primary key of the object validation rule
	 * @return the object validation rule that was removed
	 * @throws NoSuchObjectValidationRuleException if a object validation rule with the primary key could not be found
	 */
	@Override
	public ObjectValidationRule remove(long objectValidationRuleId)
		throws NoSuchObjectValidationRuleException {

		return remove((Serializable)objectValidationRuleId);
	}

	/**
	 * Removes the object validation rule with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the object validation rule
	 * @return the object validation rule that was removed
	 * @throws NoSuchObjectValidationRuleException if a object validation rule with the primary key could not be found
	 */
	@Override
	public ObjectValidationRule remove(Serializable primaryKey)
		throws NoSuchObjectValidationRuleException {

		Session session = null;

		try {
			session = openSession();

			ObjectValidationRule objectValidationRule =
				(ObjectValidationRule)session.get(
					ObjectValidationRuleImpl.class, primaryKey);

			if (objectValidationRule == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchObjectValidationRuleException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(objectValidationRule);
		}
		catch (NoSuchObjectValidationRuleException noSuchEntityException) {
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
	protected ObjectValidationRule removeImpl(
		ObjectValidationRule objectValidationRule) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(objectValidationRule)) {
				objectValidationRule = (ObjectValidationRule)session.get(
					ObjectValidationRuleImpl.class,
					objectValidationRule.getPrimaryKeyObj());
			}

			if (objectValidationRule != null) {
				session.delete(objectValidationRule);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (objectValidationRule != null) {
			clearCache(objectValidationRule);
		}

		return objectValidationRule;
	}

	@Override
	public ObjectValidationRule updateImpl(
		ObjectValidationRule objectValidationRule) {

		boolean isNew = objectValidationRule.isNew();

		if (!(objectValidationRule instanceof ObjectValidationRuleModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(objectValidationRule.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					objectValidationRule);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in objectValidationRule proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ObjectValidationRule implementation " +
					objectValidationRule.getClass());
		}

		ObjectValidationRuleModelImpl objectValidationRuleModelImpl =
			(ObjectValidationRuleModelImpl)objectValidationRule;

		if (Validator.isNull(objectValidationRule.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			objectValidationRule.setUuid(uuid);
		}

		if (Validator.isNull(objectValidationRule.getExternalReferenceCode())) {
			objectValidationRule.setExternalReferenceCode(
				objectValidationRule.getUuid());
		}
		else {
			if (!Objects.equals(
					objectValidationRuleModelImpl.getColumnOriginalValue(
						"externalReferenceCode"),
					objectValidationRule.getExternalReferenceCode())) {

				long userId = GetterUtil.getLong(
					PrincipalThreadLocal.getName());

				if (userId > 0) {
					long companyId = objectValidationRule.getCompanyId();

					long groupId = 0;

					long classPK = 0;

					if (!isNew) {
						classPK = objectValidationRule.getPrimaryKey();
					}

					try {
						objectValidationRule.setExternalReferenceCode(
							SanitizerUtil.sanitize(
								companyId, groupId, userId,
								ObjectValidationRule.class.getName(), classPK,
								ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL,
								objectValidationRule.getExternalReferenceCode(),
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

		if (isNew && (objectValidationRule.getCreateDate() == null)) {
			if (serviceContext == null) {
				objectValidationRule.setCreateDate(date);
			}
			else {
				objectValidationRule.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!objectValidationRuleModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				objectValidationRule.setModifiedDate(date);
			}
			else {
				objectValidationRule.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(objectValidationRule);
			}
			else {
				objectValidationRule = (ObjectValidationRule)session.merge(
					objectValidationRule);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			ObjectValidationRuleImpl.class, objectValidationRuleModelImpl,
			false, true);

		cacheUniqueFindersCache(objectValidationRuleModelImpl);

		if (isNew) {
			objectValidationRule.setNew(false);
		}

		objectValidationRule.resetOriginalValues();

		return objectValidationRule;
	}

	/**
	 * Returns the object validation rule with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the object validation rule
	 * @return the object validation rule
	 * @throws NoSuchObjectValidationRuleException if a object validation rule with the primary key could not be found
	 */
	@Override
	public ObjectValidationRule findByPrimaryKey(Serializable primaryKey)
		throws NoSuchObjectValidationRuleException {

		ObjectValidationRule objectValidationRule = fetchByPrimaryKey(
			primaryKey);

		if (objectValidationRule == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchObjectValidationRuleException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return objectValidationRule;
	}

	/**
	 * Returns the object validation rule with the primary key or throws a <code>NoSuchObjectValidationRuleException</code> if it could not be found.
	 *
	 * @param objectValidationRuleId the primary key of the object validation rule
	 * @return the object validation rule
	 * @throws NoSuchObjectValidationRuleException if a object validation rule with the primary key could not be found
	 */
	@Override
	public ObjectValidationRule findByPrimaryKey(long objectValidationRuleId)
		throws NoSuchObjectValidationRuleException {

		return findByPrimaryKey((Serializable)objectValidationRuleId);
	}

	/**
	 * Returns the object validation rule with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectValidationRuleId the primary key of the object validation rule
	 * @return the object validation rule, or <code>null</code> if a object validation rule with the primary key could not be found
	 */
	@Override
	public ObjectValidationRule fetchByPrimaryKey(long objectValidationRuleId) {
		return fetchByPrimaryKey((Serializable)objectValidationRuleId);
	}

	/**
	 * Returns all the object validation rules.
	 *
	 * @return the object validation rules
	 */
	@Override
	public List<ObjectValidationRule> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object validation rules.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @return the range of object validation rules
	 */
	@Override
	public List<ObjectValidationRule> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the object validation rules.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object validation rules
	 */
	@Override
	public List<ObjectValidationRule> findAll(
		int start, int end,
		OrderByComparator<ObjectValidationRule> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object validation rules.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object validation rules
	 */
	@Override
	public List<ObjectValidationRule> findAll(
		int start, int end,
		OrderByComparator<ObjectValidationRule> orderByComparator,
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

		List<ObjectValidationRule> list = null;

		if (useFinderCache) {
			list = (List<ObjectValidationRule>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_OBJECTVALIDATIONRULE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_OBJECTVALIDATIONRULE;

				sql = sql.concat(ObjectValidationRuleModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ObjectValidationRule>)QueryUtil.list(
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
	 * Removes all the object validation rules from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ObjectValidationRule objectValidationRule : findAll()) {
			remove(objectValidationRule);
		}
	}

	/**
	 * Returns the number of object validation rules.
	 *
	 * @return the number of object validation rules
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
					_SQL_COUNT_OBJECTVALIDATIONRULE);

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
		return "objectValidationRuleId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_OBJECTVALIDATIONRULE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ObjectValidationRuleModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the object validation rule persistence.
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

		_finderPathWithPaginationFindByObjectDefinitionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByObjectDefinitionId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"objectDefinitionId"}, true);

		_finderPathWithoutPaginationFindByObjectDefinitionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByObjectDefinitionId", new String[] {Long.class.getName()},
			new String[] {"objectDefinitionId"}, true);

		_finderPathCountByObjectDefinitionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByObjectDefinitionId", new String[] {Long.class.getName()},
			new String[] {"objectDefinitionId"}, false);

		_finderPathWithPaginationFindByODI_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByODI_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"objectDefinitionId", "active_"}, true);

		_finderPathWithoutPaginationFindByODI_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByODI_A",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"objectDefinitionId", "active_"}, true);

		_finderPathCountByODI_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByODI_A",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"objectDefinitionId", "active_"}, false);

		_finderPathWithPaginationFindByODI_E = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByODI_E",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"objectDefinitionId", "engine"}, true);

		_finderPathWithoutPaginationFindByODI_E = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByODI_E",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"objectDefinitionId", "engine"}, true);

		_finderPathCountByODI_E = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByODI_E",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"objectDefinitionId", "engine"}, false);

		_finderPathWithPaginationFindByODI_O = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByODI_O",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"objectDefinitionId", "outputType"}, true);

		_finderPathWithoutPaginationFindByODI_O = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByODI_O",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"objectDefinitionId", "outputType"}, true);

		_finderPathCountByODI_O = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByODI_O",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"objectDefinitionId", "outputType"}, false);

		_finderPathWithPaginationFindByA_E = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByA_E",
			new String[] {
				Boolean.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"active_", "engine"}, true);

		_finderPathWithoutPaginationFindByA_E = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByA_E",
			new String[] {Boolean.class.getName(), String.class.getName()},
			new String[] {"active_", "engine"}, true);

		_finderPathCountByA_E = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByA_E",
			new String[] {Boolean.class.getName(), String.class.getName()},
			new String[] {"active_", "engine"}, false);

		_finderPathFetchByERC_C_ODI = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByERC_C_ODI",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Long.class.getName()
			},
			new String[] {
				"externalReferenceCode", "companyId", "objectDefinitionId"
			},
			true);

		ObjectValidationRuleUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		ObjectValidationRuleUtil.setPersistence(null);

		entityCache.removeCache(ObjectValidationRuleImpl.class.getName());
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

	private static final String _SQL_SELECT_OBJECTVALIDATIONRULE =
		"SELECT objectValidationRule FROM ObjectValidationRule objectValidationRule";

	private static final String _SQL_SELECT_OBJECTVALIDATIONRULE_WHERE =
		"SELECT objectValidationRule FROM ObjectValidationRule objectValidationRule WHERE ";

	private static final String _SQL_COUNT_OBJECTVALIDATIONRULE =
		"SELECT COUNT(objectValidationRule) FROM ObjectValidationRule objectValidationRule";

	private static final String _SQL_COUNT_OBJECTVALIDATIONRULE_WHERE =
		"SELECT COUNT(objectValidationRule) FROM ObjectValidationRule objectValidationRule WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"objectValidationRule.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ObjectValidationRule exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ObjectValidationRule exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectValidationRulePersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "active", "system"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}