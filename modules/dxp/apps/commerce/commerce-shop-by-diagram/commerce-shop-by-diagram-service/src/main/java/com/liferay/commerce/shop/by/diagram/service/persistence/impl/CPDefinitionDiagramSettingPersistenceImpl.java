/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.shop.by.diagram.service.persistence.impl;

import com.liferay.commerce.shop.by.diagram.exception.NoSuchCPDefinitionDiagramSettingException;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettingTable;
import com.liferay.commerce.shop.by.diagram.model.impl.CPDefinitionDiagramSettingImpl;
import com.liferay.commerce.shop.by.diagram.model.impl.CPDefinitionDiagramSettingModelImpl;
import com.liferay.commerce.shop.by.diagram.service.persistence.CPDefinitionDiagramSettingPersistence;
import com.liferay.commerce.shop.by.diagram.service.persistence.impl.constants.CommercePersistenceConstants;
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
 * The persistence implementation for the cp definition diagram setting service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Andrea Sbarra
 * @generated
 */
@Component(
	service = {
		CPDefinitionDiagramSettingPersistence.class, BasePersistence.class
	}
)
public class CPDefinitionDiagramSettingPersistenceImpl
	extends BasePersistenceImpl<CPDefinitionDiagramSetting>
	implements CPDefinitionDiagramSettingPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CPDefinitionDiagramSettingUtil</code> to access the cp definition diagram setting persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CPDefinitionDiagramSettingImpl.class.getName();

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
	 * Returns all the cp definition diagram settings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp definition diagram settings
	 */
	@Override
	public List<CPDefinitionDiagramSetting> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition diagram settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition diagram settings
	 * @param end the upper bound of the range of cp definition diagram settings (not inclusive)
	 * @return the range of matching cp definition diagram settings
	 */
	@Override
	public List<CPDefinitionDiagramSetting> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition diagram settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition diagram settings
	 * @param end the upper bound of the range of cp definition diagram settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition diagram settings
	 */
	@Override
	public List<CPDefinitionDiagramSetting> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPDefinitionDiagramSetting> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition diagram settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition diagram settings
	 * @param end the upper bound of the range of cp definition diagram settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition diagram settings
	 */
	@Override
	public List<CPDefinitionDiagramSetting> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPDefinitionDiagramSetting> orderByComparator,
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

		List<CPDefinitionDiagramSetting> list = null;

		if (useFinderCache) {
			list = (List<CPDefinitionDiagramSetting>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionDiagramSetting cpDefinitionDiagramSetting :
						list) {

					if (!uuid.equals(cpDefinitionDiagramSetting.getUuid())) {
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

			sb.append(_SQL_SELECT_CPDEFINITIONDIAGRAMSETTING_WHERE);

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
				sb.append(CPDefinitionDiagramSettingModelImpl.ORDER_BY_JPQL);
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

				list = (List<CPDefinitionDiagramSetting>)QueryUtil.list(
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
	 * Returns the first cp definition diagram setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition diagram setting
	 * @throws NoSuchCPDefinitionDiagramSettingException if a matching cp definition diagram setting could not be found
	 */
	@Override
	public CPDefinitionDiagramSetting findByUuid_First(
			String uuid,
			OrderByComparator<CPDefinitionDiagramSetting> orderByComparator)
		throws NoSuchCPDefinitionDiagramSettingException {

		CPDefinitionDiagramSetting cpDefinitionDiagramSetting =
			fetchByUuid_First(uuid, orderByComparator);

		if (cpDefinitionDiagramSetting != null) {
			return cpDefinitionDiagramSetting;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchCPDefinitionDiagramSettingException(sb.toString());
	}

	/**
	 * Returns the first cp definition diagram setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition diagram setting, or <code>null</code> if a matching cp definition diagram setting could not be found
	 */
	@Override
	public CPDefinitionDiagramSetting fetchByUuid_First(
		String uuid,
		OrderByComparator<CPDefinitionDiagramSetting> orderByComparator) {

		List<CPDefinitionDiagramSetting> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition diagram setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition diagram setting
	 * @throws NoSuchCPDefinitionDiagramSettingException if a matching cp definition diagram setting could not be found
	 */
	@Override
	public CPDefinitionDiagramSetting findByUuid_Last(
			String uuid,
			OrderByComparator<CPDefinitionDiagramSetting> orderByComparator)
		throws NoSuchCPDefinitionDiagramSettingException {

		CPDefinitionDiagramSetting cpDefinitionDiagramSetting =
			fetchByUuid_Last(uuid, orderByComparator);

		if (cpDefinitionDiagramSetting != null) {
			return cpDefinitionDiagramSetting;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchCPDefinitionDiagramSettingException(sb.toString());
	}

	/**
	 * Returns the last cp definition diagram setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition diagram setting, or <code>null</code> if a matching cp definition diagram setting could not be found
	 */
	@Override
	public CPDefinitionDiagramSetting fetchByUuid_Last(
		String uuid,
		OrderByComparator<CPDefinitionDiagramSetting> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionDiagramSetting> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition diagram settings before and after the current cp definition diagram setting in the ordered set where uuid = &#63;.
	 *
	 * @param CPDefinitionDiagramSettingId the primary key of the current cp definition diagram setting
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition diagram setting
	 * @throws NoSuchCPDefinitionDiagramSettingException if a cp definition diagram setting with the primary key could not be found
	 */
	@Override
	public CPDefinitionDiagramSetting[] findByUuid_PrevAndNext(
			long CPDefinitionDiagramSettingId, String uuid,
			OrderByComparator<CPDefinitionDiagramSetting> orderByComparator)
		throws NoSuchCPDefinitionDiagramSettingException {

		uuid = Objects.toString(uuid, "");

		CPDefinitionDiagramSetting cpDefinitionDiagramSetting =
			findByPrimaryKey(CPDefinitionDiagramSettingId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionDiagramSetting[] array =
				new CPDefinitionDiagramSettingImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, cpDefinitionDiagramSetting, uuid, orderByComparator,
				true);

			array[1] = cpDefinitionDiagramSetting;

			array[2] = getByUuid_PrevAndNext(
				session, cpDefinitionDiagramSetting, uuid, orderByComparator,
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

	protected CPDefinitionDiagramSetting getByUuid_PrevAndNext(
		Session session, CPDefinitionDiagramSetting cpDefinitionDiagramSetting,
		String uuid,
		OrderByComparator<CPDefinitionDiagramSetting> orderByComparator,
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

		sb.append(_SQL_SELECT_CPDEFINITIONDIAGRAMSETTING_WHERE);

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
			sb.append(CPDefinitionDiagramSettingModelImpl.ORDER_BY_JPQL);
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
						cpDefinitionDiagramSetting)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPDefinitionDiagramSetting> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition diagram settings where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CPDefinitionDiagramSetting cpDefinitionDiagramSetting :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(cpDefinitionDiagramSetting);
		}
	}

	/**
	 * Returns the number of cp definition diagram settings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp definition diagram settings
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CPDEFINITIONDIAGRAMSETTING_WHERE);

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
		"cpDefinitionDiagramSetting.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(cpDefinitionDiagramSetting.uuid IS NULL OR cpDefinitionDiagramSetting.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the cp definition diagram settings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp definition diagram settings
	 */
	@Override
	public List<CPDefinitionDiagramSetting> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition diagram settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition diagram settings
	 * @param end the upper bound of the range of cp definition diagram settings (not inclusive)
	 * @return the range of matching cp definition diagram settings
	 */
	@Override
	public List<CPDefinitionDiagramSetting> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition diagram settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition diagram settings
	 * @param end the upper bound of the range of cp definition diagram settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition diagram settings
	 */
	@Override
	public List<CPDefinitionDiagramSetting> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPDefinitionDiagramSetting> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition diagram settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition diagram settings
	 * @param end the upper bound of the range of cp definition diagram settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition diagram settings
	 */
	@Override
	public List<CPDefinitionDiagramSetting> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPDefinitionDiagramSetting> orderByComparator,
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

		List<CPDefinitionDiagramSetting> list = null;

		if (useFinderCache) {
			list = (List<CPDefinitionDiagramSetting>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionDiagramSetting cpDefinitionDiagramSetting :
						list) {

					if (!uuid.equals(cpDefinitionDiagramSetting.getUuid()) ||
						(companyId !=
							cpDefinitionDiagramSetting.getCompanyId())) {

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

			sb.append(_SQL_SELECT_CPDEFINITIONDIAGRAMSETTING_WHERE);

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
				sb.append(CPDefinitionDiagramSettingModelImpl.ORDER_BY_JPQL);
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

				list = (List<CPDefinitionDiagramSetting>)QueryUtil.list(
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
	 * Returns the first cp definition diagram setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition diagram setting
	 * @throws NoSuchCPDefinitionDiagramSettingException if a matching cp definition diagram setting could not be found
	 */
	@Override
	public CPDefinitionDiagramSetting findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CPDefinitionDiagramSetting> orderByComparator)
		throws NoSuchCPDefinitionDiagramSettingException {

		CPDefinitionDiagramSetting cpDefinitionDiagramSetting =
			fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (cpDefinitionDiagramSetting != null) {
			return cpDefinitionDiagramSetting;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCPDefinitionDiagramSettingException(sb.toString());
	}

	/**
	 * Returns the first cp definition diagram setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition diagram setting, or <code>null</code> if a matching cp definition diagram setting could not be found
	 */
	@Override
	public CPDefinitionDiagramSetting fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CPDefinitionDiagramSetting> orderByComparator) {

		List<CPDefinitionDiagramSetting> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition diagram setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition diagram setting
	 * @throws NoSuchCPDefinitionDiagramSettingException if a matching cp definition diagram setting could not be found
	 */
	@Override
	public CPDefinitionDiagramSetting findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CPDefinitionDiagramSetting> orderByComparator)
		throws NoSuchCPDefinitionDiagramSettingException {

		CPDefinitionDiagramSetting cpDefinitionDiagramSetting =
			fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (cpDefinitionDiagramSetting != null) {
			return cpDefinitionDiagramSetting;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCPDefinitionDiagramSettingException(sb.toString());
	}

	/**
	 * Returns the last cp definition diagram setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition diagram setting, or <code>null</code> if a matching cp definition diagram setting could not be found
	 */
	@Override
	public CPDefinitionDiagramSetting fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CPDefinitionDiagramSetting> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionDiagramSetting> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition diagram settings before and after the current cp definition diagram setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CPDefinitionDiagramSettingId the primary key of the current cp definition diagram setting
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition diagram setting
	 * @throws NoSuchCPDefinitionDiagramSettingException if a cp definition diagram setting with the primary key could not be found
	 */
	@Override
	public CPDefinitionDiagramSetting[] findByUuid_C_PrevAndNext(
			long CPDefinitionDiagramSettingId, String uuid, long companyId,
			OrderByComparator<CPDefinitionDiagramSetting> orderByComparator)
		throws NoSuchCPDefinitionDiagramSettingException {

		uuid = Objects.toString(uuid, "");

		CPDefinitionDiagramSetting cpDefinitionDiagramSetting =
			findByPrimaryKey(CPDefinitionDiagramSettingId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionDiagramSetting[] array =
				new CPDefinitionDiagramSettingImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, cpDefinitionDiagramSetting, uuid, companyId,
				orderByComparator, true);

			array[1] = cpDefinitionDiagramSetting;

			array[2] = getByUuid_C_PrevAndNext(
				session, cpDefinitionDiagramSetting, uuid, companyId,
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

	protected CPDefinitionDiagramSetting getByUuid_C_PrevAndNext(
		Session session, CPDefinitionDiagramSetting cpDefinitionDiagramSetting,
		String uuid, long companyId,
		OrderByComparator<CPDefinitionDiagramSetting> orderByComparator,
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

		sb.append(_SQL_SELECT_CPDEFINITIONDIAGRAMSETTING_WHERE);

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
			sb.append(CPDefinitionDiagramSettingModelImpl.ORDER_BY_JPQL);
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
						cpDefinitionDiagramSetting)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPDefinitionDiagramSetting> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition diagram settings where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CPDefinitionDiagramSetting cpDefinitionDiagramSetting :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(cpDefinitionDiagramSetting);
		}
	}

	/**
	 * Returns the number of cp definition diagram settings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp definition diagram settings
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CPDEFINITIONDIAGRAMSETTING_WHERE);

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
		"cpDefinitionDiagramSetting.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(cpDefinitionDiagramSetting.uuid IS NULL OR cpDefinitionDiagramSetting.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"cpDefinitionDiagramSetting.companyId = ?";

	private FinderPath _finderPathFetchByCPDefinitionId;
	private FinderPath _finderPathCountByCPDefinitionId;

	/**
	 * Returns the cp definition diagram setting where CPDefinitionId = &#63; or throws a <code>NoSuchCPDefinitionDiagramSettingException</code> if it could not be found.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching cp definition diagram setting
	 * @throws NoSuchCPDefinitionDiagramSettingException if a matching cp definition diagram setting could not be found
	 */
	@Override
	public CPDefinitionDiagramSetting findByCPDefinitionId(long CPDefinitionId)
		throws NoSuchCPDefinitionDiagramSettingException {

		CPDefinitionDiagramSetting cpDefinitionDiagramSetting =
			fetchByCPDefinitionId(CPDefinitionId);

		if (cpDefinitionDiagramSetting == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("CPDefinitionId=");
			sb.append(CPDefinitionId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCPDefinitionDiagramSettingException(sb.toString());
		}

		return cpDefinitionDiagramSetting;
	}

	/**
	 * Returns the cp definition diagram setting where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching cp definition diagram setting, or <code>null</code> if a matching cp definition diagram setting could not be found
	 */
	@Override
	public CPDefinitionDiagramSetting fetchByCPDefinitionId(
		long CPDefinitionId) {

		return fetchByCPDefinitionId(CPDefinitionId, true);
	}

	/**
	 * Returns the cp definition diagram setting where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp definition diagram setting, or <code>null</code> if a matching cp definition diagram setting could not be found
	 */
	@Override
	public CPDefinitionDiagramSetting fetchByCPDefinitionId(
		long CPDefinitionId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {CPDefinitionId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByCPDefinitionId, finderArgs);
		}

		if (result instanceof CPDefinitionDiagramSetting) {
			CPDefinitionDiagramSetting cpDefinitionDiagramSetting =
				(CPDefinitionDiagramSetting)result;

			if (CPDefinitionId !=
					cpDefinitionDiagramSetting.getCPDefinitionId()) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_CPDEFINITIONDIAGRAMSETTING_WHERE);

			sb.append(_FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPDefinitionId);

				List<CPDefinitionDiagramSetting> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByCPDefinitionId, finderArgs, list);
					}
				}
				else {
					CPDefinitionDiagramSetting cpDefinitionDiagramSetting =
						list.get(0);

					result = cpDefinitionDiagramSetting;

					cacheResult(cpDefinitionDiagramSetting);
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
			return (CPDefinitionDiagramSetting)result;
		}
	}

	/**
	 * Removes the cp definition diagram setting where CPDefinitionId = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the cp definition diagram setting that was removed
	 */
	@Override
	public CPDefinitionDiagramSetting removeByCPDefinitionId(
			long CPDefinitionId)
		throws NoSuchCPDefinitionDiagramSettingException {

		CPDefinitionDiagramSetting cpDefinitionDiagramSetting =
			findByCPDefinitionId(CPDefinitionId);

		return remove(cpDefinitionDiagramSetting);
	}

	/**
	 * Returns the number of cp definition diagram settings where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the number of matching cp definition diagram settings
	 */
	@Override
	public int countByCPDefinitionId(long CPDefinitionId) {
		FinderPath finderPath = _finderPathCountByCPDefinitionId;

		Object[] finderArgs = new Object[] {CPDefinitionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CPDEFINITIONDIAGRAMSETTING_WHERE);

			sb.append(_FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPDefinitionId);

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

	private static final String _FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2 =
		"cpDefinitionDiagramSetting.CPDefinitionId = ?";

	public CPDefinitionDiagramSettingPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CPDefinitionDiagramSetting.class);

		setModelImplClass(CPDefinitionDiagramSettingImpl.class);
		setModelPKClass(long.class);

		setTable(CPDefinitionDiagramSettingTable.INSTANCE);
	}

	/**
	 * Caches the cp definition diagram setting in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionDiagramSetting the cp definition diagram setting
	 */
	@Override
	public void cacheResult(
		CPDefinitionDiagramSetting cpDefinitionDiagramSetting) {

		entityCache.putResult(
			CPDefinitionDiagramSettingImpl.class,
			cpDefinitionDiagramSetting.getPrimaryKey(),
			cpDefinitionDiagramSetting);

		finderCache.putResult(
			_finderPathFetchByCPDefinitionId,
			new Object[] {cpDefinitionDiagramSetting.getCPDefinitionId()},
			cpDefinitionDiagramSetting);
	}

	/**
	 * Caches the cp definition diagram settings in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionDiagramSettings the cp definition diagram settings
	 */
	@Override
	public void cacheResult(
		List<CPDefinitionDiagramSetting> cpDefinitionDiagramSettings) {

		for (CPDefinitionDiagramSetting cpDefinitionDiagramSetting :
				cpDefinitionDiagramSettings) {

			if (entityCache.getResult(
					CPDefinitionDiagramSettingImpl.class,
					cpDefinitionDiagramSetting.getPrimaryKey()) == null) {

				cacheResult(cpDefinitionDiagramSetting);
			}
		}
	}

	/**
	 * Clears the cache for all cp definition diagram settings.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CPDefinitionDiagramSettingImpl.class);

		finderCache.clearCache(CPDefinitionDiagramSettingImpl.class);
	}

	/**
	 * Clears the cache for the cp definition diagram setting.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CPDefinitionDiagramSetting cpDefinitionDiagramSetting) {

		entityCache.removeResult(
			CPDefinitionDiagramSettingImpl.class, cpDefinitionDiagramSetting);
	}

	@Override
	public void clearCache(
		List<CPDefinitionDiagramSetting> cpDefinitionDiagramSettings) {

		for (CPDefinitionDiagramSetting cpDefinitionDiagramSetting :
				cpDefinitionDiagramSettings) {

			entityCache.removeResult(
				CPDefinitionDiagramSettingImpl.class,
				cpDefinitionDiagramSetting);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CPDefinitionDiagramSettingImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CPDefinitionDiagramSettingImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CPDefinitionDiagramSettingModelImpl
			cpDefinitionDiagramSettingModelImpl) {

		Object[] args = new Object[] {
			cpDefinitionDiagramSettingModelImpl.getCPDefinitionId()
		};

		finderCache.putResult(
			_finderPathCountByCPDefinitionId, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByCPDefinitionId, args,
			cpDefinitionDiagramSettingModelImpl);
	}

	/**
	 * Creates a new cp definition diagram setting with the primary key. Does not add the cp definition diagram setting to the database.
	 *
	 * @param CPDefinitionDiagramSettingId the primary key for the new cp definition diagram setting
	 * @return the new cp definition diagram setting
	 */
	@Override
	public CPDefinitionDiagramSetting create(
		long CPDefinitionDiagramSettingId) {

		CPDefinitionDiagramSetting cpDefinitionDiagramSetting =
			new CPDefinitionDiagramSettingImpl();

		cpDefinitionDiagramSetting.setNew(true);
		cpDefinitionDiagramSetting.setPrimaryKey(CPDefinitionDiagramSettingId);

		String uuid = PortalUUIDUtil.generate();

		cpDefinitionDiagramSetting.setUuid(uuid);

		cpDefinitionDiagramSetting.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return cpDefinitionDiagramSetting;
	}

	/**
	 * Removes the cp definition diagram setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPDefinitionDiagramSettingId the primary key of the cp definition diagram setting
	 * @return the cp definition diagram setting that was removed
	 * @throws NoSuchCPDefinitionDiagramSettingException if a cp definition diagram setting with the primary key could not be found
	 */
	@Override
	public CPDefinitionDiagramSetting remove(long CPDefinitionDiagramSettingId)
		throws NoSuchCPDefinitionDiagramSettingException {

		return remove((Serializable)CPDefinitionDiagramSettingId);
	}

	/**
	 * Removes the cp definition diagram setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cp definition diagram setting
	 * @return the cp definition diagram setting that was removed
	 * @throws NoSuchCPDefinitionDiagramSettingException if a cp definition diagram setting with the primary key could not be found
	 */
	@Override
	public CPDefinitionDiagramSetting remove(Serializable primaryKey)
		throws NoSuchCPDefinitionDiagramSettingException {

		Session session = null;

		try {
			session = openSession();

			CPDefinitionDiagramSetting cpDefinitionDiagramSetting =
				(CPDefinitionDiagramSetting)session.get(
					CPDefinitionDiagramSettingImpl.class, primaryKey);

			if (cpDefinitionDiagramSetting == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCPDefinitionDiagramSettingException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(cpDefinitionDiagramSetting);
		}
		catch (NoSuchCPDefinitionDiagramSettingException
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
	protected CPDefinitionDiagramSetting removeImpl(
		CPDefinitionDiagramSetting cpDefinitionDiagramSetting) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpDefinitionDiagramSetting)) {
				cpDefinitionDiagramSetting =
					(CPDefinitionDiagramSetting)session.get(
						CPDefinitionDiagramSettingImpl.class,
						cpDefinitionDiagramSetting.getPrimaryKeyObj());
			}

			if (cpDefinitionDiagramSetting != null) {
				session.delete(cpDefinitionDiagramSetting);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (cpDefinitionDiagramSetting != null) {
			clearCache(cpDefinitionDiagramSetting);
		}

		return cpDefinitionDiagramSetting;
	}

	@Override
	public CPDefinitionDiagramSetting updateImpl(
		CPDefinitionDiagramSetting cpDefinitionDiagramSetting) {

		boolean isNew = cpDefinitionDiagramSetting.isNew();

		if (!(cpDefinitionDiagramSetting instanceof
				CPDefinitionDiagramSettingModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(cpDefinitionDiagramSetting.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					cpDefinitionDiagramSetting);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cpDefinitionDiagramSetting proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CPDefinitionDiagramSetting implementation " +
					cpDefinitionDiagramSetting.getClass());
		}

		CPDefinitionDiagramSettingModelImpl
			cpDefinitionDiagramSettingModelImpl =
				(CPDefinitionDiagramSettingModelImpl)cpDefinitionDiagramSetting;

		if (Validator.isNull(cpDefinitionDiagramSetting.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			cpDefinitionDiagramSetting.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (cpDefinitionDiagramSetting.getCreateDate() == null)) {
			if (serviceContext == null) {
				cpDefinitionDiagramSetting.setCreateDate(date);
			}
			else {
				cpDefinitionDiagramSetting.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!cpDefinitionDiagramSettingModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cpDefinitionDiagramSetting.setModifiedDate(date);
			}
			else {
				cpDefinitionDiagramSetting.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(cpDefinitionDiagramSetting);
			}
			else {
				cpDefinitionDiagramSetting =
					(CPDefinitionDiagramSetting)session.merge(
						cpDefinitionDiagramSetting);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CPDefinitionDiagramSettingImpl.class,
			cpDefinitionDiagramSettingModelImpl, false, true);

		cacheUniqueFindersCache(cpDefinitionDiagramSettingModelImpl);

		if (isNew) {
			cpDefinitionDiagramSetting.setNew(false);
		}

		cpDefinitionDiagramSetting.resetOriginalValues();

		return cpDefinitionDiagramSetting;
	}

	/**
	 * Returns the cp definition diagram setting with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp definition diagram setting
	 * @return the cp definition diagram setting
	 * @throws NoSuchCPDefinitionDiagramSettingException if a cp definition diagram setting with the primary key could not be found
	 */
	@Override
	public CPDefinitionDiagramSetting findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCPDefinitionDiagramSettingException {

		CPDefinitionDiagramSetting cpDefinitionDiagramSetting =
			fetchByPrimaryKey(primaryKey);

		if (cpDefinitionDiagramSetting == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCPDefinitionDiagramSettingException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return cpDefinitionDiagramSetting;
	}

	/**
	 * Returns the cp definition diagram setting with the primary key or throws a <code>NoSuchCPDefinitionDiagramSettingException</code> if it could not be found.
	 *
	 * @param CPDefinitionDiagramSettingId the primary key of the cp definition diagram setting
	 * @return the cp definition diagram setting
	 * @throws NoSuchCPDefinitionDiagramSettingException if a cp definition diagram setting with the primary key could not be found
	 */
	@Override
	public CPDefinitionDiagramSetting findByPrimaryKey(
			long CPDefinitionDiagramSettingId)
		throws NoSuchCPDefinitionDiagramSettingException {

		return findByPrimaryKey((Serializable)CPDefinitionDiagramSettingId);
	}

	/**
	 * Returns the cp definition diagram setting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPDefinitionDiagramSettingId the primary key of the cp definition diagram setting
	 * @return the cp definition diagram setting, or <code>null</code> if a cp definition diagram setting with the primary key could not be found
	 */
	@Override
	public CPDefinitionDiagramSetting fetchByPrimaryKey(
		long CPDefinitionDiagramSettingId) {

		return fetchByPrimaryKey((Serializable)CPDefinitionDiagramSettingId);
	}

	/**
	 * Returns all the cp definition diagram settings.
	 *
	 * @return the cp definition diagram settings
	 */
	@Override
	public List<CPDefinitionDiagramSetting> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition diagram settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition diagram settings
	 * @param end the upper bound of the range of cp definition diagram settings (not inclusive)
	 * @return the range of cp definition diagram settings
	 */
	@Override
	public List<CPDefinitionDiagramSetting> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition diagram settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition diagram settings
	 * @param end the upper bound of the range of cp definition diagram settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp definition diagram settings
	 */
	@Override
	public List<CPDefinitionDiagramSetting> findAll(
		int start, int end,
		OrderByComparator<CPDefinitionDiagramSetting> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition diagram settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition diagram settings
	 * @param end the upper bound of the range of cp definition diagram settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp definition diagram settings
	 */
	@Override
	public List<CPDefinitionDiagramSetting> findAll(
		int start, int end,
		OrderByComparator<CPDefinitionDiagramSetting> orderByComparator,
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

		List<CPDefinitionDiagramSetting> list = null;

		if (useFinderCache) {
			list = (List<CPDefinitionDiagramSetting>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CPDEFINITIONDIAGRAMSETTING);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CPDEFINITIONDIAGRAMSETTING;

				sql = sql.concat(
					CPDefinitionDiagramSettingModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CPDefinitionDiagramSetting>)QueryUtil.list(
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
	 * Removes all the cp definition diagram settings from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPDefinitionDiagramSetting cpDefinitionDiagramSetting :
				findAll()) {

			remove(cpDefinitionDiagramSetting);
		}
	}

	/**
	 * Returns the number of cp definition diagram settings.
	 *
	 * @return the number of cp definition diagram settings
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
					_SQL_COUNT_CPDEFINITIONDIAGRAMSETTING);

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
		return "CPDefinitionDiagramSettingId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CPDEFINITIONDIAGRAMSETTING;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CPDefinitionDiagramSettingModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the cp definition diagram setting persistence.
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

		_finderPathFetchByCPDefinitionId = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByCPDefinitionId",
			new String[] {Long.class.getName()},
			new String[] {"CPDefinitionId"}, true);

		_finderPathCountByCPDefinitionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCPDefinitionId",
			new String[] {Long.class.getName()},
			new String[] {"CPDefinitionId"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(CPDefinitionDiagramSettingImpl.class.getName());
	}

	@Override
	@Reference(
		target = CommercePersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = CommercePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = CommercePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_CPDEFINITIONDIAGRAMSETTING =
		"SELECT cpDefinitionDiagramSetting FROM CPDefinitionDiagramSetting cpDefinitionDiagramSetting";

	private static final String _SQL_SELECT_CPDEFINITIONDIAGRAMSETTING_WHERE =
		"SELECT cpDefinitionDiagramSetting FROM CPDefinitionDiagramSetting cpDefinitionDiagramSetting WHERE ";

	private static final String _SQL_COUNT_CPDEFINITIONDIAGRAMSETTING =
		"SELECT COUNT(cpDefinitionDiagramSetting) FROM CPDefinitionDiagramSetting cpDefinitionDiagramSetting";

	private static final String _SQL_COUNT_CPDEFINITIONDIAGRAMSETTING_WHERE =
		"SELECT COUNT(cpDefinitionDiagramSetting) FROM CPDefinitionDiagramSetting cpDefinitionDiagramSetting WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"cpDefinitionDiagramSetting.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CPDefinitionDiagramSetting exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CPDefinitionDiagramSetting exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionDiagramSettingPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "type"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private CPDefinitionDiagramSettingModelArgumentsResolver
		_cpDefinitionDiagramSettingModelArgumentsResolver;

}