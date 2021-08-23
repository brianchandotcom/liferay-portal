/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.custom.elements.service.persistence.impl;

import com.liferay.custom.elements.exception.NoSuchPortletDescriptorException;
import com.liferay.custom.elements.model.CustomElementsPortletDescriptor;
import com.liferay.custom.elements.model.CustomElementsPortletDescriptorTable;
import com.liferay.custom.elements.model.impl.CustomElementsPortletDescriptorImpl;
import com.liferay.custom.elements.model.impl.CustomElementsPortletDescriptorModelImpl;
import com.liferay.custom.elements.service.persistence.CustomElementsPortletDescriptorPersistence;
import com.liferay.custom.elements.service.persistence.impl.constants.CustomElementsPersistenceConstants;
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
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
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
 * The persistence implementation for the custom elements portlet descriptor service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(
	service = {
		CustomElementsPortletDescriptorPersistence.class, BasePersistence.class
	}
)
public class CustomElementsPortletDescriptorPersistenceImpl
	extends BasePersistenceImpl<CustomElementsPortletDescriptor>
	implements CustomElementsPortletDescriptorPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CustomElementsPortletDescriptorUtil</code> to access the custom elements portlet descriptor persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CustomElementsPortletDescriptorImpl.class.getName();

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
	 * Returns all the custom elements portlet descriptors where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching custom elements portlet descriptors
	 */
	@Override
	public List<CustomElementsPortletDescriptor> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the custom elements portlet descriptors where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementsPortletDescriptorModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of custom elements portlet descriptors
	 * @param end the upper bound of the range of custom elements portlet descriptors (not inclusive)
	 * @return the range of matching custom elements portlet descriptors
	 */
	@Override
	public List<CustomElementsPortletDescriptor> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the custom elements portlet descriptors where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementsPortletDescriptorModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of custom elements portlet descriptors
	 * @param end the upper bound of the range of custom elements portlet descriptors (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching custom elements portlet descriptors
	 */
	@Override
	public List<CustomElementsPortletDescriptor> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CustomElementsPortletDescriptor> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the custom elements portlet descriptors where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementsPortletDescriptorModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of custom elements portlet descriptors
	 * @param end the upper bound of the range of custom elements portlet descriptors (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching custom elements portlet descriptors
	 */
	@Override
	public List<CustomElementsPortletDescriptor> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CustomElementsPortletDescriptor> orderByComparator,
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

		List<CustomElementsPortletDescriptor> list = null;

		if (useFinderCache) {
			list = (List<CustomElementsPortletDescriptor>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CustomElementsPortletDescriptor
						customElementsPortletDescriptor : list) {

					if (!uuid.equals(
							customElementsPortletDescriptor.getUuid())) {

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

			sb.append(_SQL_SELECT_CUSTOMELEMENTSPORTLETDESCRIPTOR_WHERE);

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
				sb.append(
					CustomElementsPortletDescriptorModelImpl.ORDER_BY_JPQL);
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

				list = (List<CustomElementsPortletDescriptor>)QueryUtil.list(
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
	 * Returns the first custom elements portlet descriptor in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching custom elements portlet descriptor
	 * @throws NoSuchPortletDescriptorException if a matching custom elements portlet descriptor could not be found
	 */
	@Override
	public CustomElementsPortletDescriptor findByUuid_First(
			String uuid,
			OrderByComparator<CustomElementsPortletDescriptor>
				orderByComparator)
		throws NoSuchPortletDescriptorException {

		CustomElementsPortletDescriptor customElementsPortletDescriptor =
			fetchByUuid_First(uuid, orderByComparator);

		if (customElementsPortletDescriptor != null) {
			return customElementsPortletDescriptor;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchPortletDescriptorException(sb.toString());
	}

	/**
	 * Returns the first custom elements portlet descriptor in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching custom elements portlet descriptor, or <code>null</code> if a matching custom elements portlet descriptor could not be found
	 */
	@Override
	public CustomElementsPortletDescriptor fetchByUuid_First(
		String uuid,
		OrderByComparator<CustomElementsPortletDescriptor> orderByComparator) {

		List<CustomElementsPortletDescriptor> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last custom elements portlet descriptor in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching custom elements portlet descriptor
	 * @throws NoSuchPortletDescriptorException if a matching custom elements portlet descriptor could not be found
	 */
	@Override
	public CustomElementsPortletDescriptor findByUuid_Last(
			String uuid,
			OrderByComparator<CustomElementsPortletDescriptor>
				orderByComparator)
		throws NoSuchPortletDescriptorException {

		CustomElementsPortletDescriptor customElementsPortletDescriptor =
			fetchByUuid_Last(uuid, orderByComparator);

		if (customElementsPortletDescriptor != null) {
			return customElementsPortletDescriptor;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchPortletDescriptorException(sb.toString());
	}

	/**
	 * Returns the last custom elements portlet descriptor in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching custom elements portlet descriptor, or <code>null</code> if a matching custom elements portlet descriptor could not be found
	 */
	@Override
	public CustomElementsPortletDescriptor fetchByUuid_Last(
		String uuid,
		OrderByComparator<CustomElementsPortletDescriptor> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CustomElementsPortletDescriptor> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the custom elements portlet descriptors before and after the current custom elements portlet descriptor in the ordered set where uuid = &#63;.
	 *
	 * @param customElementsPortletDescriptorId the primary key of the current custom elements portlet descriptor
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next custom elements portlet descriptor
	 * @throws NoSuchPortletDescriptorException if a custom elements portlet descriptor with the primary key could not be found
	 */
	@Override
	public CustomElementsPortletDescriptor[] findByUuid_PrevAndNext(
			long customElementsPortletDescriptorId, String uuid,
			OrderByComparator<CustomElementsPortletDescriptor>
				orderByComparator)
		throws NoSuchPortletDescriptorException {

		uuid = Objects.toString(uuid, "");

		CustomElementsPortletDescriptor customElementsPortletDescriptor =
			findByPrimaryKey(customElementsPortletDescriptorId);

		Session session = null;

		try {
			session = openSession();

			CustomElementsPortletDescriptor[] array =
				new CustomElementsPortletDescriptorImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, customElementsPortletDescriptor, uuid,
				orderByComparator, true);

			array[1] = customElementsPortletDescriptor;

			array[2] = getByUuid_PrevAndNext(
				session, customElementsPortletDescriptor, uuid,
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

	protected CustomElementsPortletDescriptor getByUuid_PrevAndNext(
		Session session,
		CustomElementsPortletDescriptor customElementsPortletDescriptor,
		String uuid,
		OrderByComparator<CustomElementsPortletDescriptor> orderByComparator,
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

		sb.append(_SQL_SELECT_CUSTOMELEMENTSPORTLETDESCRIPTOR_WHERE);

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
			sb.append(CustomElementsPortletDescriptorModelImpl.ORDER_BY_JPQL);
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
						customElementsPortletDescriptor)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CustomElementsPortletDescriptor> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the custom elements portlet descriptors where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CustomElementsPortletDescriptor customElementsPortletDescriptor :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(customElementsPortletDescriptor);
		}
	}

	/**
	 * Returns the number of custom elements portlet descriptors where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching custom elements portlet descriptors
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CUSTOMELEMENTSPORTLETDESCRIPTOR_WHERE);

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
		"customElementsPortletDescriptor.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(customElementsPortletDescriptor.uuid IS NULL OR customElementsPortletDescriptor.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the custom elements portlet descriptors where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching custom elements portlet descriptors
	 */
	@Override
	public List<CustomElementsPortletDescriptor> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the custom elements portlet descriptors where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementsPortletDescriptorModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of custom elements portlet descriptors
	 * @param end the upper bound of the range of custom elements portlet descriptors (not inclusive)
	 * @return the range of matching custom elements portlet descriptors
	 */
	@Override
	public List<CustomElementsPortletDescriptor> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the custom elements portlet descriptors where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementsPortletDescriptorModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of custom elements portlet descriptors
	 * @param end the upper bound of the range of custom elements portlet descriptors (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching custom elements portlet descriptors
	 */
	@Override
	public List<CustomElementsPortletDescriptor> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CustomElementsPortletDescriptor> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the custom elements portlet descriptors where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementsPortletDescriptorModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of custom elements portlet descriptors
	 * @param end the upper bound of the range of custom elements portlet descriptors (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching custom elements portlet descriptors
	 */
	@Override
	public List<CustomElementsPortletDescriptor> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CustomElementsPortletDescriptor> orderByComparator,
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

		List<CustomElementsPortletDescriptor> list = null;

		if (useFinderCache) {
			list = (List<CustomElementsPortletDescriptor>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CustomElementsPortletDescriptor
						customElementsPortletDescriptor : list) {

					if (!uuid.equals(
							customElementsPortletDescriptor.getUuid()) ||
						(companyId !=
							customElementsPortletDescriptor.getCompanyId())) {

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

			sb.append(_SQL_SELECT_CUSTOMELEMENTSPORTLETDESCRIPTOR_WHERE);

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
				sb.append(
					CustomElementsPortletDescriptorModelImpl.ORDER_BY_JPQL);
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

				list = (List<CustomElementsPortletDescriptor>)QueryUtil.list(
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
	 * Returns the first custom elements portlet descriptor in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching custom elements portlet descriptor
	 * @throws NoSuchPortletDescriptorException if a matching custom elements portlet descriptor could not be found
	 */
	@Override
	public CustomElementsPortletDescriptor findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CustomElementsPortletDescriptor>
				orderByComparator)
		throws NoSuchPortletDescriptorException {

		CustomElementsPortletDescriptor customElementsPortletDescriptor =
			fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (customElementsPortletDescriptor != null) {
			return customElementsPortletDescriptor;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchPortletDescriptorException(sb.toString());
	}

	/**
	 * Returns the first custom elements portlet descriptor in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching custom elements portlet descriptor, or <code>null</code> if a matching custom elements portlet descriptor could not be found
	 */
	@Override
	public CustomElementsPortletDescriptor fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CustomElementsPortletDescriptor> orderByComparator) {

		List<CustomElementsPortletDescriptor> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last custom elements portlet descriptor in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching custom elements portlet descriptor
	 * @throws NoSuchPortletDescriptorException if a matching custom elements portlet descriptor could not be found
	 */
	@Override
	public CustomElementsPortletDescriptor findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CustomElementsPortletDescriptor>
				orderByComparator)
		throws NoSuchPortletDescriptorException {

		CustomElementsPortletDescriptor customElementsPortletDescriptor =
			fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (customElementsPortletDescriptor != null) {
			return customElementsPortletDescriptor;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchPortletDescriptorException(sb.toString());
	}

	/**
	 * Returns the last custom elements portlet descriptor in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching custom elements portlet descriptor, or <code>null</code> if a matching custom elements portlet descriptor could not be found
	 */
	@Override
	public CustomElementsPortletDescriptor fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CustomElementsPortletDescriptor> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CustomElementsPortletDescriptor> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the custom elements portlet descriptors before and after the current custom elements portlet descriptor in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param customElementsPortletDescriptorId the primary key of the current custom elements portlet descriptor
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next custom elements portlet descriptor
	 * @throws NoSuchPortletDescriptorException if a custom elements portlet descriptor with the primary key could not be found
	 */
	@Override
	public CustomElementsPortletDescriptor[] findByUuid_C_PrevAndNext(
			long customElementsPortletDescriptorId, String uuid, long companyId,
			OrderByComparator<CustomElementsPortletDescriptor>
				orderByComparator)
		throws NoSuchPortletDescriptorException {

		uuid = Objects.toString(uuid, "");

		CustomElementsPortletDescriptor customElementsPortletDescriptor =
			findByPrimaryKey(customElementsPortletDescriptorId);

		Session session = null;

		try {
			session = openSession();

			CustomElementsPortletDescriptor[] array =
				new CustomElementsPortletDescriptorImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, customElementsPortletDescriptor, uuid, companyId,
				orderByComparator, true);

			array[1] = customElementsPortletDescriptor;

			array[2] = getByUuid_C_PrevAndNext(
				session, customElementsPortletDescriptor, uuid, companyId,
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

	protected CustomElementsPortletDescriptor getByUuid_C_PrevAndNext(
		Session session,
		CustomElementsPortletDescriptor customElementsPortletDescriptor,
		String uuid, long companyId,
		OrderByComparator<CustomElementsPortletDescriptor> orderByComparator,
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

		sb.append(_SQL_SELECT_CUSTOMELEMENTSPORTLETDESCRIPTOR_WHERE);

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
			sb.append(CustomElementsPortletDescriptorModelImpl.ORDER_BY_JPQL);
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
						customElementsPortletDescriptor)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CustomElementsPortletDescriptor> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the custom elements portlet descriptors where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CustomElementsPortletDescriptor customElementsPortletDescriptor :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(customElementsPortletDescriptor);
		}
	}

	/**
	 * Returns the number of custom elements portlet descriptors where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching custom elements portlet descriptors
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CUSTOMELEMENTSPORTLETDESCRIPTOR_WHERE);

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
		"customElementsPortletDescriptor.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(customElementsPortletDescriptor.uuid IS NULL OR customElementsPortletDescriptor.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"customElementsPortletDescriptor.companyId = ?";

	public CustomElementsPortletDescriptorPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put(
			"customElementsPortletDescriptorId", "customElementsPortletDescId");

		setDBColumnNames(dbColumnNames);

		setModelClass(CustomElementsPortletDescriptor.class);

		setModelImplClass(CustomElementsPortletDescriptorImpl.class);
		setModelPKClass(long.class);

		setTable(CustomElementsPortletDescriptorTable.INSTANCE);
	}

	/**
	 * Caches the custom elements portlet descriptor in the entity cache if it is enabled.
	 *
	 * @param customElementsPortletDescriptor the custom elements portlet descriptor
	 */
	@Override
	public void cacheResult(
		CustomElementsPortletDescriptor customElementsPortletDescriptor) {

		entityCache.putResult(
			CustomElementsPortletDescriptorImpl.class,
			customElementsPortletDescriptor.getPrimaryKey(),
			customElementsPortletDescriptor);
	}

	/**
	 * Caches the custom elements portlet descriptors in the entity cache if it is enabled.
	 *
	 * @param customElementsPortletDescriptors the custom elements portlet descriptors
	 */
	@Override
	public void cacheResult(
		List<CustomElementsPortletDescriptor>
			customElementsPortletDescriptors) {

		for (CustomElementsPortletDescriptor customElementsPortletDescriptor :
				customElementsPortletDescriptors) {

			if (entityCache.getResult(
					CustomElementsPortletDescriptorImpl.class,
					customElementsPortletDescriptor.getPrimaryKey()) == null) {

				cacheResult(customElementsPortletDescriptor);
			}
		}
	}

	/**
	 * Clears the cache for all custom elements portlet descriptors.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CustomElementsPortletDescriptorImpl.class);

		finderCache.clearCache(CustomElementsPortletDescriptorImpl.class);
	}

	/**
	 * Clears the cache for the custom elements portlet descriptor.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CustomElementsPortletDescriptor customElementsPortletDescriptor) {

		entityCache.removeResult(
			CustomElementsPortletDescriptorImpl.class,
			customElementsPortletDescriptor);
	}

	@Override
	public void clearCache(
		List<CustomElementsPortletDescriptor>
			customElementsPortletDescriptors) {

		for (CustomElementsPortletDescriptor customElementsPortletDescriptor :
				customElementsPortletDescriptors) {

			entityCache.removeResult(
				CustomElementsPortletDescriptorImpl.class,
				customElementsPortletDescriptor);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CustomElementsPortletDescriptorImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CustomElementsPortletDescriptorImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new custom elements portlet descriptor with the primary key. Does not add the custom elements portlet descriptor to the database.
	 *
	 * @param customElementsPortletDescriptorId the primary key for the new custom elements portlet descriptor
	 * @return the new custom elements portlet descriptor
	 */
	@Override
	public CustomElementsPortletDescriptor create(
		long customElementsPortletDescriptorId) {

		CustomElementsPortletDescriptor customElementsPortletDescriptor =
			new CustomElementsPortletDescriptorImpl();

		customElementsPortletDescriptor.setNew(true);
		customElementsPortletDescriptor.setPrimaryKey(
			customElementsPortletDescriptorId);

		String uuid = PortalUUIDUtil.generate();

		customElementsPortletDescriptor.setUuid(uuid);

		customElementsPortletDescriptor.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return customElementsPortletDescriptor;
	}

	/**
	 * Removes the custom elements portlet descriptor with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param customElementsPortletDescriptorId the primary key of the custom elements portlet descriptor
	 * @return the custom elements portlet descriptor that was removed
	 * @throws NoSuchPortletDescriptorException if a custom elements portlet descriptor with the primary key could not be found
	 */
	@Override
	public CustomElementsPortletDescriptor remove(
			long customElementsPortletDescriptorId)
		throws NoSuchPortletDescriptorException {

		return remove((Serializable)customElementsPortletDescriptorId);
	}

	/**
	 * Removes the custom elements portlet descriptor with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the custom elements portlet descriptor
	 * @return the custom elements portlet descriptor that was removed
	 * @throws NoSuchPortletDescriptorException if a custom elements portlet descriptor with the primary key could not be found
	 */
	@Override
	public CustomElementsPortletDescriptor remove(Serializable primaryKey)
		throws NoSuchPortletDescriptorException {

		Session session = null;

		try {
			session = openSession();

			CustomElementsPortletDescriptor customElementsPortletDescriptor =
				(CustomElementsPortletDescriptor)session.get(
					CustomElementsPortletDescriptorImpl.class, primaryKey);

			if (customElementsPortletDescriptor == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPortletDescriptorException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(customElementsPortletDescriptor);
		}
		catch (NoSuchPortletDescriptorException noSuchEntityException) {
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
	protected CustomElementsPortletDescriptor removeImpl(
		CustomElementsPortletDescriptor customElementsPortletDescriptor) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(customElementsPortletDescriptor)) {
				customElementsPortletDescriptor =
					(CustomElementsPortletDescriptor)session.get(
						CustomElementsPortletDescriptorImpl.class,
						customElementsPortletDescriptor.getPrimaryKeyObj());
			}

			if (customElementsPortletDescriptor != null) {
				session.delete(customElementsPortletDescriptor);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (customElementsPortletDescriptor != null) {
			clearCache(customElementsPortletDescriptor);
		}

		return customElementsPortletDescriptor;
	}

	@Override
	public CustomElementsPortletDescriptor updateImpl(
		CustomElementsPortletDescriptor customElementsPortletDescriptor) {

		boolean isNew = customElementsPortletDescriptor.isNew();

		if (!(customElementsPortletDescriptor instanceof
				CustomElementsPortletDescriptorModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					customElementsPortletDescriptor.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					customElementsPortletDescriptor);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in customElementsPortletDescriptor proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CustomElementsPortletDescriptor implementation " +
					customElementsPortletDescriptor.getClass());
		}

		CustomElementsPortletDescriptorModelImpl
			customElementsPortletDescriptorModelImpl =
				(CustomElementsPortletDescriptorModelImpl)
					customElementsPortletDescriptor;

		if (Validator.isNull(customElementsPortletDescriptor.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			customElementsPortletDescriptor.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew &&
			(customElementsPortletDescriptor.getCreateDate() == null)) {

			if (serviceContext == null) {
				customElementsPortletDescriptor.setCreateDate(date);
			}
			else {
				customElementsPortletDescriptor.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!customElementsPortletDescriptorModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				customElementsPortletDescriptor.setModifiedDate(date);
			}
			else {
				customElementsPortletDescriptor.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(customElementsPortletDescriptor);
			}
			else {
				customElementsPortletDescriptor =
					(CustomElementsPortletDescriptor)session.merge(
						customElementsPortletDescriptor);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CustomElementsPortletDescriptorImpl.class,
			customElementsPortletDescriptorModelImpl, false, true);

		if (isNew) {
			customElementsPortletDescriptor.setNew(false);
		}

		customElementsPortletDescriptor.resetOriginalValues();

		return customElementsPortletDescriptor;
	}

	/**
	 * Returns the custom elements portlet descriptor with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the custom elements portlet descriptor
	 * @return the custom elements portlet descriptor
	 * @throws NoSuchPortletDescriptorException if a custom elements portlet descriptor with the primary key could not be found
	 */
	@Override
	public CustomElementsPortletDescriptor findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchPortletDescriptorException {

		CustomElementsPortletDescriptor customElementsPortletDescriptor =
			fetchByPrimaryKey(primaryKey);

		if (customElementsPortletDescriptor == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPortletDescriptorException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return customElementsPortletDescriptor;
	}

	/**
	 * Returns the custom elements portlet descriptor with the primary key or throws a <code>NoSuchPortletDescriptorException</code> if it could not be found.
	 *
	 * @param customElementsPortletDescriptorId the primary key of the custom elements portlet descriptor
	 * @return the custom elements portlet descriptor
	 * @throws NoSuchPortletDescriptorException if a custom elements portlet descriptor with the primary key could not be found
	 */
	@Override
	public CustomElementsPortletDescriptor findByPrimaryKey(
			long customElementsPortletDescriptorId)
		throws NoSuchPortletDescriptorException {

		return findByPrimaryKey(
			(Serializable)customElementsPortletDescriptorId);
	}

	/**
	 * Returns the custom elements portlet descriptor with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param customElementsPortletDescriptorId the primary key of the custom elements portlet descriptor
	 * @return the custom elements portlet descriptor, or <code>null</code> if a custom elements portlet descriptor with the primary key could not be found
	 */
	@Override
	public CustomElementsPortletDescriptor fetchByPrimaryKey(
		long customElementsPortletDescriptorId) {

		return fetchByPrimaryKey(
			(Serializable)customElementsPortletDescriptorId);
	}

	/**
	 * Returns all the custom elements portlet descriptors.
	 *
	 * @return the custom elements portlet descriptors
	 */
	@Override
	public List<CustomElementsPortletDescriptor> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the custom elements portlet descriptors.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementsPortletDescriptorModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of custom elements portlet descriptors
	 * @param end the upper bound of the range of custom elements portlet descriptors (not inclusive)
	 * @return the range of custom elements portlet descriptors
	 */
	@Override
	public List<CustomElementsPortletDescriptor> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the custom elements portlet descriptors.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementsPortletDescriptorModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of custom elements portlet descriptors
	 * @param end the upper bound of the range of custom elements portlet descriptors (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of custom elements portlet descriptors
	 */
	@Override
	public List<CustomElementsPortletDescriptor> findAll(
		int start, int end,
		OrderByComparator<CustomElementsPortletDescriptor> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the custom elements portlet descriptors.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementsPortletDescriptorModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of custom elements portlet descriptors
	 * @param end the upper bound of the range of custom elements portlet descriptors (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of custom elements portlet descriptors
	 */
	@Override
	public List<CustomElementsPortletDescriptor> findAll(
		int start, int end,
		OrderByComparator<CustomElementsPortletDescriptor> orderByComparator,
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

		List<CustomElementsPortletDescriptor> list = null;

		if (useFinderCache) {
			list = (List<CustomElementsPortletDescriptor>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CUSTOMELEMENTSPORTLETDESCRIPTOR);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CUSTOMELEMENTSPORTLETDESCRIPTOR;

				sql = sql.concat(
					CustomElementsPortletDescriptorModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CustomElementsPortletDescriptor>)QueryUtil.list(
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
	 * Removes all the custom elements portlet descriptors from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CustomElementsPortletDescriptor customElementsPortletDescriptor :
				findAll()) {

			remove(customElementsPortletDescriptor);
		}
	}

	/**
	 * Returns the number of custom elements portlet descriptors.
	 *
	 * @return the number of custom elements portlet descriptors
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_CUSTOMELEMENTSPORTLETDESCRIPTOR);

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
		return "customElementsPortletDescId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CUSTOMELEMENTSPORTLETDESCRIPTOR;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CustomElementsPortletDescriptorModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the custom elements portlet descriptor persistence.
	 */
	@Activate
	public void activate() {
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
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(
			CustomElementsPortletDescriptorImpl.class.getName());
	}

	@Override
	@Reference(
		target = CustomElementsPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = CustomElementsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = CustomElementsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_CUSTOMELEMENTSPORTLETDESCRIPTOR =
		"SELECT customElementsPortletDescriptor FROM CustomElementsPortletDescriptor customElementsPortletDescriptor";

	private static final String
		_SQL_SELECT_CUSTOMELEMENTSPORTLETDESCRIPTOR_WHERE =
			"SELECT customElementsPortletDescriptor FROM CustomElementsPortletDescriptor customElementsPortletDescriptor WHERE ";

	private static final String _SQL_COUNT_CUSTOMELEMENTSPORTLETDESCRIPTOR =
		"SELECT COUNT(customElementsPortletDescriptor) FROM CustomElementsPortletDescriptor customElementsPortletDescriptor";

	private static final String
		_SQL_COUNT_CUSTOMELEMENTSPORTLETDESCRIPTOR_WHERE =
			"SELECT COUNT(customElementsPortletDescriptor) FROM CustomElementsPortletDescriptor customElementsPortletDescriptor WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"customElementsPortletDescriptor.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CustomElementsPortletDescriptor exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CustomElementsPortletDescriptor exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CustomElementsPortletDescriptorPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "customElementsPortletDescriptorId"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private CustomElementsPortletDescriptorModelArgumentsResolver
		_customElementsPortletDescriptorModelArgumentsResolver;

}