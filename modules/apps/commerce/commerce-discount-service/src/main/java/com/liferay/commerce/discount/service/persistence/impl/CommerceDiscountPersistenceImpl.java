/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.service.persistence.impl;

import com.liferay.commerce.discount.exception.DuplicateCommerceDiscountExternalReferenceCodeException;
import com.liferay.commerce.discount.exception.NoSuchDiscountException;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountTable;
import com.liferay.commerce.discount.model.impl.CommerceDiscountImpl;
import com.liferay.commerce.discount.model.impl.CommerceDiscountModelImpl;
import com.liferay.commerce.discount.service.persistence.CommerceDiscountPersistence;
import com.liferay.commerce.discount.service.persistence.CommerceDiscountUtil;
import com.liferay.commerce.discount.service.persistence.impl.constants.CommercePersistenceConstants;
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

import java.sql.Timestamp;

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
 * The persistence implementation for the commerce discount service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
@Component(service = CommerceDiscountPersistence.class)
public class CommerceDiscountPersistenceImpl
	extends BasePersistenceImpl<CommerceDiscount, NoSuchDiscountException>
	implements CommerceDiscountPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceDiscountUtil</code> to access the commerce discount persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceDiscountImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;
	private CollectionPersistenceFinder<CommerceDiscount>
		_collectionPersistenceFinderByUuid;

	/**
	 * Returns all the commerce discounts where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce discounts
	 */
	@Override
	public List<CommerceDiscount> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discounts where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @return the range of matching commerce discounts
	 */
	@Override
	public List<CommerceDiscount> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce discounts where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discounts
	 */
	@Override
	public List<CommerceDiscount> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceDiscount> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce discounts where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discounts
	 */
	@Override
	public List<CommerceDiscount> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceDiscount> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid.find(
			finderCache, new Object[] {uuid}, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce discount in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount
	 * @throws NoSuchDiscountException if a matching commerce discount could not be found
	 */
	@Override
	public CommerceDiscount findByUuid_First(
			String uuid, OrderByComparator<CommerceDiscount> orderByComparator)
		throws NoSuchDiscountException {

		CommerceDiscount commerceDiscount = fetchByUuid_First(
			uuid, orderByComparator);

		if (commerceDiscount != null) {
			return commerceDiscount;
		}

		throw new NoSuchDiscountException(
			_collectionPersistenceFinderByUuid.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid}));
	}

	/**
	 * Returns the first commerce discount in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	 */
	@Override
	public CommerceDiscount fetchByUuid_First(
		String uuid, OrderByComparator<CommerceDiscount> orderByComparator) {

		return _collectionPersistenceFinderByUuid.fetchFirst(
			finderCache, new Object[] {uuid}, orderByComparator);
	}

	/**
	 * Returns all the commerce discounts that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce discounts that the user has permission to view
	 */
	@Override
	public List<CommerceDiscount> filterFindByUuid(String uuid) {
		return filterFindByUuid(
			uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discounts that the user has permission to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @return the range of matching commerce discounts that the user has permission to view
	 */
	@Override
	public List<CommerceDiscount> filterFindByUuid(
		String uuid, int start, int end) {

		return filterFindByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce discounts that the user has permissions to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discounts that the user has permission to view
	 */
	@Override
	public List<CommerceDiscount> filterFindByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceDiscount> orderByComparator) {

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
			sb.append(_FILTER_SQL_SELECT_COMMERCEDISCOUNT_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEDISCOUNT_NO_INLINE_DISTINCT_WHERE_1);
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
				_FILTER_SQL_SELECT_COMMERCEDISCOUNT_NO_INLINE_DISTINCT_WHERE_2);
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
					CommerceDiscountModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(CommerceDiscountModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceDiscount.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, CommerceDiscountImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, CommerceDiscountImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindUuid) {
				queryPos.add(uuid);
			}

			return (List<CommerceDiscount>)QueryUtil.list(
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
	 * Removes all the commerce discounts where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		_collectionPersistenceFinderByUuid.remove(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of commerce discounts where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce discounts
	 */
	@Override
	public int countByUuid(String uuid) {
		return _collectionPersistenceFinderByUuid.count(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of commerce discounts that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce discounts that the user has permission to view
	 */
	@Override
	public int filterCountByUuid(String uuid) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByUuid(uuid);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<CommerceDiscount> commerceDiscounts = findByUuid(uuid);

			commerceDiscounts = InlineSQLHelperUtil.filter(commerceDiscounts);

			return commerceDiscounts.size();
		}

		uuid = Objects.toString(uuid, "");

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_COMMERCEDISCOUNT_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2_SQL);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceDiscount.class.getName(),
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
		"commerceDiscount.uuid_ = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3_SQL =
		"(commerceDiscount.uuid_ IS NULL OR commerceDiscount.uuid_ = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;
	private CollectionPersistenceFinder<CommerceDiscount>
		_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns all the commerce discounts where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce discounts
	 */
	@Override
	public List<CommerceDiscount> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discounts where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @return the range of matching commerce discounts
	 */
	@Override
	public List<CommerceDiscount> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce discounts where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discounts
	 */
	@Override
	public List<CommerceDiscount> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceDiscount> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce discounts where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discounts
	 */
	@Override
	public List<CommerceDiscount> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceDiscount> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid_C.find(
			finderCache, new Object[] {uuid, companyId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce discount in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount
	 * @throws NoSuchDiscountException if a matching commerce discount could not be found
	 */
	@Override
	public CommerceDiscount findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommerceDiscount> orderByComparator)
		throws NoSuchDiscountException {

		CommerceDiscount commerceDiscount = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (commerceDiscount != null) {
			return commerceDiscount;
		}

		throw new NoSuchDiscountException(
			_collectionPersistenceFinderByUuid_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, companyId}));
	}

	/**
	 * Returns the first commerce discount in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	 */
	@Override
	public CommerceDiscount fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommerceDiscount> orderByComparator) {

		return _collectionPersistenceFinderByUuid_C.fetchFirst(
			finderCache, new Object[] {uuid, companyId}, orderByComparator);
	}

	/**
	 * Returns all the commerce discounts that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce discounts that the user has permission to view
	 */
	@Override
	public List<CommerceDiscount> filterFindByUuid_C(
		String uuid, long companyId) {

		return filterFindByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discounts that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @return the range of matching commerce discounts that the user has permission to view
	 */
	@Override
	public List<CommerceDiscount> filterFindByUuid_C(
		String uuid, long companyId, int start, int end) {

		return filterFindByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce discounts that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discounts that the user has permission to view
	 */
	@Override
	public List<CommerceDiscount> filterFindByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceDiscount> orderByComparator) {

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
			sb.append(_FILTER_SQL_SELECT_COMMERCEDISCOUNT_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEDISCOUNT_NO_INLINE_DISTINCT_WHERE_1);
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
				_FILTER_SQL_SELECT_COMMERCEDISCOUNT_NO_INLINE_DISTINCT_WHERE_2);
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
					CommerceDiscountModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(CommerceDiscountModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceDiscount.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, CommerceDiscountImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, CommerceDiscountImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindUuid) {
				queryPos.add(uuid);
			}

			queryPos.add(companyId);

			return (List<CommerceDiscount>)QueryUtil.list(
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
	 * Removes all the commerce discounts where uuid = &#63; and companyId = &#63; from the database.
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
	 * Returns the number of commerce discounts where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce discounts
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		return _collectionPersistenceFinderByUuid_C.count(
			finderCache, new Object[] {uuid, companyId});
	}

	/**
	 * Returns the number of commerce discounts that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce discounts that the user has permission to view
	 */
	@Override
	public int filterCountByUuid_C(String uuid, long companyId) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByUuid_C(uuid, companyId);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<CommerceDiscount> commerceDiscounts = findByUuid_C(
				uuid, companyId);

			commerceDiscounts = InlineSQLHelperUtil.filter(commerceDiscounts);

			return commerceDiscounts.size();
		}

		uuid = Objects.toString(uuid, "");

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_COMMERCEDISCOUNT_WHERE);

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
			sb.toString(), CommerceDiscount.class.getName(),
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
		"commerceDiscount.uuid_ = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3_SQL =
		"(commerceDiscount.uuid_ IS NULL OR commerceDiscount.uuid_ = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"commerceDiscount.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;
	private CollectionPersistenceFinder<CommerceDiscount>
		_collectionPersistenceFinderByCompanyId;

	/**
	 * Returns all the commerce discounts where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce discounts
	 */
	@Override
	public List<CommerceDiscount> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discounts where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @return the range of matching commerce discounts
	 */
	@Override
	public List<CommerceDiscount> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce discounts where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discounts
	 */
	@Override
	public List<CommerceDiscount> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceDiscount> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce discounts where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discounts
	 */
	@Override
	public List<CommerceDiscount> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceDiscount> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByCompanyId.find(
			finderCache, new Object[] {companyId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce discount in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount
	 * @throws NoSuchDiscountException if a matching commerce discount could not be found
	 */
	@Override
	public CommerceDiscount findByCompanyId_First(
			long companyId,
			OrderByComparator<CommerceDiscount> orderByComparator)
		throws NoSuchDiscountException {

		CommerceDiscount commerceDiscount = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (commerceDiscount != null) {
			return commerceDiscount;
		}

		throw new NoSuchDiscountException(
			_collectionPersistenceFinderByCompanyId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId}));
	}

	/**
	 * Returns the first commerce discount in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	 */
	@Override
	public CommerceDiscount fetchByCompanyId_First(
		long companyId, OrderByComparator<CommerceDiscount> orderByComparator) {

		return _collectionPersistenceFinderByCompanyId.fetchFirst(
			finderCache, new Object[] {companyId}, orderByComparator);
	}

	/**
	 * Returns all the commerce discounts that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce discounts that the user has permission to view
	 */
	@Override
	public List<CommerceDiscount> filterFindByCompanyId(long companyId) {
		return filterFindByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discounts that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @return the range of matching commerce discounts that the user has permission to view
	 */
	@Override
	public List<CommerceDiscount> filterFindByCompanyId(
		long companyId, int start, int end) {

		return filterFindByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce discounts that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discounts that the user has permission to view
	 */
	@Override
	public List<CommerceDiscount> filterFindByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceDiscount> orderByComparator) {

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
			sb.append(_FILTER_SQL_SELECT_COMMERCEDISCOUNT_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEDISCOUNT_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEDISCOUNT_NO_INLINE_DISTINCT_WHERE_2);
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
					CommerceDiscountModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(CommerceDiscountModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceDiscount.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, CommerceDiscountImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, CommerceDiscountImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			return (List<CommerceDiscount>)QueryUtil.list(
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
	 * Removes all the commerce discounts where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		_collectionPersistenceFinderByCompanyId.remove(
			finderCache, new Object[] {companyId});
	}

	/**
	 * Returns the number of commerce discounts where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce discounts
	 */
	@Override
	public int countByCompanyId(long companyId) {
		return _collectionPersistenceFinderByCompanyId.count(
			finderCache, new Object[] {companyId});
	}

	/**
	 * Returns the number of commerce discounts that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce discounts that the user has permission to view
	 */
	@Override
	public int filterCountByCompanyId(long companyId) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByCompanyId(companyId);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<CommerceDiscount> commerceDiscounts = findByCompanyId(
				companyId);

			commerceDiscounts = InlineSQLHelperUtil.filter(commerceDiscounts);

			return commerceDiscounts.size();
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_COMMERCEDISCOUNT_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceDiscount.class.getName(),
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
		"commerceDiscount.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns all the commerce discounts where companyId = &#63; and couponCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param couponCode the coupon code
	 * @return the matching commerce discounts
	 */
	@Override
	public List<CommerceDiscount> findByC_C(long companyId, String couponCode) {
		return findByC_C(
			companyId, couponCode, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discounts where companyId = &#63; and couponCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param couponCode the coupon code
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @return the range of matching commerce discounts
	 */
	@Override
	public List<CommerceDiscount> findByC_C(
		long companyId, String couponCode, int start, int end) {

		return findByC_C(companyId, couponCode, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce discounts where companyId = &#63; and couponCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param couponCode the coupon code
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discounts
	 */
	@Override
	public List<CommerceDiscount> findByC_C(
		long companyId, String couponCode, int start, int end,
		OrderByComparator<CommerceDiscount> orderByComparator) {

		return findByC_C(
			companyId, couponCode, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce discounts where companyId = &#63; and couponCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param couponCode the coupon code
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discounts
	 */
	@Override
	public List<CommerceDiscount> findByC_C(
		long companyId, String couponCode, int start, int end,
		OrderByComparator<CommerceDiscount> orderByComparator,
		boolean useFinderCache) {

		couponCode = Objects.toString(couponCode, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_C;
				finderArgs = new Object[] {companyId, couponCode};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_C;
			finderArgs = new Object[] {
				companyId, couponCode, start, end, orderByComparator
			};
		}

		List<CommerceDiscount> list = null;

		if (useFinderCache) {
			list = (List<CommerceDiscount>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceDiscount commerceDiscount : list) {
					if ((companyId != commerceDiscount.getCompanyId()) ||
						!couponCode.equals(commerceDiscount.getCouponCode())) {

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

			sb.append(_SQL_SELECT_COMMERCEDISCOUNT_WHERE);

			sb.append(_FINDER_COLUMN_C_C_COMPANYID_2);

			boolean bindCouponCode = false;

			if (couponCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_C_COUPONCODE_3);
			}
			else {
				bindCouponCode = true;

				sb.append(_FINDER_COLUMN_C_C_COUPONCODE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
			}
			else {
				sb.append(CommerceDiscountModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindCouponCode) {
					queryPos.add(StringUtil.toLowerCase(couponCode));
				}

				list = (List<CommerceDiscount>)QueryUtil.list(
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
	 * Returns the first commerce discount in the ordered set where companyId = &#63; and couponCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param couponCode the coupon code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount
	 * @throws NoSuchDiscountException if a matching commerce discount could not be found
	 */
	@Override
	public CommerceDiscount findByC_C_First(
			long companyId, String couponCode,
			OrderByComparator<CommerceDiscount> orderByComparator)
		throws NoSuchDiscountException {

		CommerceDiscount commerceDiscount = fetchByC_C_First(
			companyId, couponCode, orderByComparator);

		if (commerceDiscount != null) {
			return commerceDiscount;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", couponCode=");
		sb.append(couponCode);

		sb.append("}");

		throw new NoSuchDiscountException(sb.toString());
	}

	/**
	 * Returns the first commerce discount in the ordered set where companyId = &#63; and couponCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param couponCode the coupon code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	 */
	@Override
	public CommerceDiscount fetchByC_C_First(
		long companyId, String couponCode,
		OrderByComparator<CommerceDiscount> orderByComparator) {

		List<CommerceDiscount> list = findByC_C(
			companyId, couponCode, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the commerce discounts that the user has permission to view where companyId = &#63; and couponCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param couponCode the coupon code
	 * @return the matching commerce discounts that the user has permission to view
	 */
	@Override
	public List<CommerceDiscount> filterFindByC_C(
		long companyId, String couponCode) {

		return filterFindByC_C(
			companyId, couponCode, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discounts that the user has permission to view where companyId = &#63; and couponCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param couponCode the coupon code
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @return the range of matching commerce discounts that the user has permission to view
	 */
	@Override
	public List<CommerceDiscount> filterFindByC_C(
		long companyId, String couponCode, int start, int end) {

		return filterFindByC_C(companyId, couponCode, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce discounts that the user has permissions to view where companyId = &#63; and couponCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param couponCode the coupon code
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discounts that the user has permission to view
	 */
	@Override
	public List<CommerceDiscount> filterFindByC_C(
		long companyId, String couponCode, int start, int end,
		OrderByComparator<CommerceDiscount> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_C(
				companyId, couponCode, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByC_C(
					companyId, couponCode, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					orderByComparator));
		}

		couponCode = Objects.toString(couponCode, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COMMERCEDISCOUNT_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEDISCOUNT_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_C_COMPANYID_2);

		boolean bindCouponCode = false;

		if (couponCode.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_C_COUPONCODE_3);
		}
		else {
			bindCouponCode = true;

			sb.append(_FINDER_COLUMN_C_C_COUPONCODE_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEDISCOUNT_NO_INLINE_DISTINCT_WHERE_2);
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
					CommerceDiscountModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(CommerceDiscountModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceDiscount.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, CommerceDiscountImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, CommerceDiscountImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			if (bindCouponCode) {
				queryPos.add(StringUtil.toLowerCase(couponCode));
			}

			return (List<CommerceDiscount>)QueryUtil.list(
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
	 * Removes all the commerce discounts where companyId = &#63; and couponCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param couponCode the coupon code
	 */
	@Override
	public void removeByC_C(long companyId, String couponCode) {
		for (CommerceDiscount commerceDiscount :
				findByC_C(
					companyId, couponCode, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceDiscount);
		}
	}

	/**
	 * Returns the number of commerce discounts where companyId = &#63; and couponCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param couponCode the coupon code
	 * @return the number of matching commerce discounts
	 */
	@Override
	public int countByC_C(long companyId, String couponCode) {
		couponCode = Objects.toString(couponCode, "");

		FinderPath finderPath = _finderPathCountByC_C;

		Object[] finderArgs = new Object[] {companyId, couponCode};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEDISCOUNT_WHERE);

			sb.append(_FINDER_COLUMN_C_C_COMPANYID_2);

			boolean bindCouponCode = false;

			if (couponCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_C_COUPONCODE_3);
			}
			else {
				bindCouponCode = true;

				sb.append(_FINDER_COLUMN_C_C_COUPONCODE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindCouponCode) {
					queryPos.add(StringUtil.toLowerCase(couponCode));
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

	/**
	 * Returns the number of commerce discounts that the user has permission to view where companyId = &#63; and couponCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param couponCode the coupon code
	 * @return the number of matching commerce discounts that the user has permission to view
	 */
	@Override
	public int filterCountByC_C(long companyId, String couponCode) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByC_C(companyId, couponCode);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<CommerceDiscount> commerceDiscounts = findByC_C(
				companyId, couponCode);

			commerceDiscounts = InlineSQLHelperUtil.filter(commerceDiscounts);

			return commerceDiscounts.size();
		}

		couponCode = Objects.toString(couponCode, "");

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_COMMERCEDISCOUNT_WHERE);

		sb.append(_FINDER_COLUMN_C_C_COMPANYID_2);

		boolean bindCouponCode = false;

		if (couponCode.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_C_COUPONCODE_3);
		}
		else {
			bindCouponCode = true;

			sb.append(_FINDER_COLUMN_C_C_COUPONCODE_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceDiscount.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			if (bindCouponCode) {
				queryPos.add(StringUtil.toLowerCase(couponCode));
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

	private static final String _FINDER_COLUMN_C_C_COMPANYID_2 =
		"commerceDiscount.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_COUPONCODE_2 =
		"lower(commerceDiscount.couponCode) = ?";

	private static final String _FINDER_COLUMN_C_C_COUPONCODE_3 =
		"(commerceDiscount.couponCode IS NULL OR commerceDiscount.couponCode = '')";

	private FinderPath _finderPathWithPaginationFindByLtD_S;
	private FinderPath _finderPathWithPaginationCountByLtD_S;
	private CollectionPersistenceFinder<CommerceDiscount>
		_collectionPersistenceFinderByLtD_S;

	/**
	 * Returns all the commerce discounts where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching commerce discounts
	 */
	@Override
	public List<CommerceDiscount> findByLtD_S(Date displayDate, int status) {
		return findByLtD_S(
			displayDate, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discounts where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @return the range of matching commerce discounts
	 */
	@Override
	public List<CommerceDiscount> findByLtD_S(
		Date displayDate, int status, int start, int end) {

		return findByLtD_S(displayDate, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce discounts where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discounts
	 */
	@Override
	public List<CommerceDiscount> findByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<CommerceDiscount> orderByComparator) {

		return findByLtD_S(
			displayDate, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce discounts where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discounts
	 */
	@Override
	public List<CommerceDiscount> findByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<CommerceDiscount> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByLtD_S.find(
			finderCache, new Object[] {displayDate, status}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce discount in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount
	 * @throws NoSuchDiscountException if a matching commerce discount could not be found
	 */
	@Override
	public CommerceDiscount findByLtD_S_First(
			Date displayDate, int status,
			OrderByComparator<CommerceDiscount> orderByComparator)
		throws NoSuchDiscountException {

		CommerceDiscount commerceDiscount = fetchByLtD_S_First(
			displayDate, status, orderByComparator);

		if (commerceDiscount != null) {
			return commerceDiscount;
		}

		throw new NoSuchDiscountException(
			_collectionPersistenceFinderByLtD_S.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {displayDate, status}));
	}

	/**
	 * Returns the first commerce discount in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	 */
	@Override
	public CommerceDiscount fetchByLtD_S_First(
		Date displayDate, int status,
		OrderByComparator<CommerceDiscount> orderByComparator) {

		return _collectionPersistenceFinderByLtD_S.fetchFirst(
			finderCache, new Object[] {displayDate, status}, orderByComparator);
	}

	/**
	 * Returns all the commerce discounts that the user has permission to view where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching commerce discounts that the user has permission to view
	 */
	@Override
	public List<CommerceDiscount> filterFindByLtD_S(
		Date displayDate, int status) {

		return filterFindByLtD_S(
			displayDate, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discounts that the user has permission to view where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @return the range of matching commerce discounts that the user has permission to view
	 */
	@Override
	public List<CommerceDiscount> filterFindByLtD_S(
		Date displayDate, int status, int start, int end) {

		return filterFindByLtD_S(displayDate, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce discounts that the user has permissions to view where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discounts that the user has permission to view
	 */
	@Override
	public List<CommerceDiscount> filterFindByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<CommerceDiscount> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByLtD_S(
				displayDate, status, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByLtD_S(
					displayDate, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
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
			sb.append(_FILTER_SQL_SELECT_COMMERCEDISCOUNT_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEDISCOUNT_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean bindDisplayDate = false;

		if (displayDate == null) {
			sb.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_1);
		}
		else {
			bindDisplayDate = true;

			sb.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_2);
		}

		sb.append(_FINDER_COLUMN_LTD_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEDISCOUNT_NO_INLINE_DISTINCT_WHERE_2);
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
					CommerceDiscountModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(CommerceDiscountModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceDiscount.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, CommerceDiscountImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, CommerceDiscountImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindDisplayDate) {
				queryPos.add(new Timestamp(displayDate.getTime()));
			}

			queryPos.add(status);

			return (List<CommerceDiscount>)QueryUtil.list(
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
	 * Removes all the commerce discounts where displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 */
	@Override
	public void removeByLtD_S(Date displayDate, int status) {
		_collectionPersistenceFinderByLtD_S.remove(
			finderCache, new Object[] {displayDate, status});
	}

	/**
	 * Returns the number of commerce discounts where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching commerce discounts
	 */
	@Override
	public int countByLtD_S(Date displayDate, int status) {
		return _collectionPersistenceFinderByLtD_S.count(
			finderCache, new Object[] {displayDate, status});
	}

	/**
	 * Returns the number of commerce discounts that the user has permission to view where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching commerce discounts that the user has permission to view
	 */
	@Override
	public int filterCountByLtD_S(Date displayDate, int status) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByLtD_S(displayDate, status);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<CommerceDiscount> commerceDiscounts = findByLtD_S(
				displayDate, status);

			commerceDiscounts = InlineSQLHelperUtil.filter(commerceDiscounts);

			return commerceDiscounts.size();
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_COMMERCEDISCOUNT_WHERE);

		boolean bindDisplayDate = false;

		if (displayDate == null) {
			sb.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_1);
		}
		else {
			bindDisplayDate = true;

			sb.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_2);
		}

		sb.append(_FINDER_COLUMN_LTD_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceDiscount.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindDisplayDate) {
				queryPos.add(new Timestamp(displayDate.getTime()));
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

	private static final String _FINDER_COLUMN_LTD_S_DISPLAYDATE_1 =
		"commerceDiscount.displayDate IS NULL AND ";

	private static final String _FINDER_COLUMN_LTD_S_DISPLAYDATE_2 =
		"commerceDiscount.displayDate < ? AND ";

	private static final String _FINDER_COLUMN_LTD_S_STATUS_2 =
		"commerceDiscount.status = ?";

	private FinderPath _finderPathWithPaginationFindByLtE_S;
	private FinderPath _finderPathWithPaginationCountByLtE_S;
	private CollectionPersistenceFinder<CommerceDiscount>
		_collectionPersistenceFinderByLtE_S;

	/**
	 * Returns all the commerce discounts where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the matching commerce discounts
	 */
	@Override
	public List<CommerceDiscount> findByLtE_S(Date expirationDate, int status) {
		return findByLtE_S(
			expirationDate, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discounts where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @return the range of matching commerce discounts
	 */
	@Override
	public List<CommerceDiscount> findByLtE_S(
		Date expirationDate, int status, int start, int end) {

		return findByLtE_S(expirationDate, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce discounts where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discounts
	 */
	@Override
	public List<CommerceDiscount> findByLtE_S(
		Date expirationDate, int status, int start, int end,
		OrderByComparator<CommerceDiscount> orderByComparator) {

		return findByLtE_S(
			expirationDate, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce discounts where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discounts
	 */
	@Override
	public List<CommerceDiscount> findByLtE_S(
		Date expirationDate, int status, int start, int end,
		OrderByComparator<CommerceDiscount> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByLtE_S.find(
			finderCache, new Object[] {expirationDate, status}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce discount in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount
	 * @throws NoSuchDiscountException if a matching commerce discount could not be found
	 */
	@Override
	public CommerceDiscount findByLtE_S_First(
			Date expirationDate, int status,
			OrderByComparator<CommerceDiscount> orderByComparator)
		throws NoSuchDiscountException {

		CommerceDiscount commerceDiscount = fetchByLtE_S_First(
			expirationDate, status, orderByComparator);

		if (commerceDiscount != null) {
			return commerceDiscount;
		}

		throw new NoSuchDiscountException(
			_collectionPersistenceFinderByLtE_S.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {expirationDate, status}));
	}

	/**
	 * Returns the first commerce discount in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	 */
	@Override
	public CommerceDiscount fetchByLtE_S_First(
		Date expirationDate, int status,
		OrderByComparator<CommerceDiscount> orderByComparator) {

		return _collectionPersistenceFinderByLtE_S.fetchFirst(
			finderCache, new Object[] {expirationDate, status},
			orderByComparator);
	}

	/**
	 * Returns all the commerce discounts that the user has permission to view where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the matching commerce discounts that the user has permission to view
	 */
	@Override
	public List<CommerceDiscount> filterFindByLtE_S(
		Date expirationDate, int status) {

		return filterFindByLtE_S(
			expirationDate, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discounts that the user has permission to view where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @return the range of matching commerce discounts that the user has permission to view
	 */
	@Override
	public List<CommerceDiscount> filterFindByLtE_S(
		Date expirationDate, int status, int start, int end) {

		return filterFindByLtE_S(expirationDate, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce discounts that the user has permissions to view where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discounts that the user has permission to view
	 */
	@Override
	public List<CommerceDiscount> filterFindByLtE_S(
		Date expirationDate, int status, int start, int end,
		OrderByComparator<CommerceDiscount> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByLtE_S(
				expirationDate, status, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByLtE_S(
					expirationDate, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator));
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
			sb.append(_FILTER_SQL_SELECT_COMMERCEDISCOUNT_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEDISCOUNT_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean bindExpirationDate = false;

		if (expirationDate == null) {
			sb.append(_FINDER_COLUMN_LTE_S_EXPIRATIONDATE_1);
		}
		else {
			bindExpirationDate = true;

			sb.append(_FINDER_COLUMN_LTE_S_EXPIRATIONDATE_2);
		}

		sb.append(_FINDER_COLUMN_LTE_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEDISCOUNT_NO_INLINE_DISTINCT_WHERE_2);
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
					CommerceDiscountModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(CommerceDiscountModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceDiscount.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, CommerceDiscountImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, CommerceDiscountImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindExpirationDate) {
				queryPos.add(new Timestamp(expirationDate.getTime()));
			}

			queryPos.add(status);

			return (List<CommerceDiscount>)QueryUtil.list(
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
	 * Removes all the commerce discounts where expirationDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 */
	@Override
	public void removeByLtE_S(Date expirationDate, int status) {
		_collectionPersistenceFinderByLtE_S.remove(
			finderCache, new Object[] {expirationDate, status});
	}

	/**
	 * Returns the number of commerce discounts where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the number of matching commerce discounts
	 */
	@Override
	public int countByLtE_S(Date expirationDate, int status) {
		return _collectionPersistenceFinderByLtE_S.count(
			finderCache, new Object[] {expirationDate, status});
	}

	/**
	 * Returns the number of commerce discounts that the user has permission to view where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the number of matching commerce discounts that the user has permission to view
	 */
	@Override
	public int filterCountByLtE_S(Date expirationDate, int status) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByLtE_S(expirationDate, status);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<CommerceDiscount> commerceDiscounts = findByLtE_S(
				expirationDate, status);

			commerceDiscounts = InlineSQLHelperUtil.filter(commerceDiscounts);

			return commerceDiscounts.size();
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_COMMERCEDISCOUNT_WHERE);

		boolean bindExpirationDate = false;

		if (expirationDate == null) {
			sb.append(_FINDER_COLUMN_LTE_S_EXPIRATIONDATE_1);
		}
		else {
			bindExpirationDate = true;

			sb.append(_FINDER_COLUMN_LTE_S_EXPIRATIONDATE_2);
		}

		sb.append(_FINDER_COLUMN_LTE_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceDiscount.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindExpirationDate) {
				queryPos.add(new Timestamp(expirationDate.getTime()));
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

	private static final String _FINDER_COLUMN_LTE_S_EXPIRATIONDATE_1 =
		"commerceDiscount.expirationDate IS NULL AND ";

	private static final String _FINDER_COLUMN_LTE_S_EXPIRATIONDATE_2 =
		"commerceDiscount.expirationDate < ? AND ";

	private static final String _FINDER_COLUMN_LTE_S_STATUS_2 =
		"commerceDiscount.status = ?";

	private FinderPath _finderPathFetchByC_C_A;

	/**
	 * Returns the commerce discount where companyId = &#63; and couponCode = &#63; and active = &#63; or throws a <code>NoSuchDiscountException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param couponCode the coupon code
	 * @param active the active
	 * @return the matching commerce discount
	 * @throws NoSuchDiscountException if a matching commerce discount could not be found
	 */
	@Override
	public CommerceDiscount findByC_C_A(
			long companyId, String couponCode, boolean active)
		throws NoSuchDiscountException {

		CommerceDiscount commerceDiscount = fetchByC_C_A(
			companyId, couponCode, active);

		if (commerceDiscount == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", couponCode=");
			sb.append(couponCode);

			sb.append(", active=");
			sb.append(active);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchDiscountException(sb.toString());
		}

		return commerceDiscount;
	}

	/**
	 * Returns the commerce discount where companyId = &#63; and couponCode = &#63; and active = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param couponCode the coupon code
	 * @param active the active
	 * @return the matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	 */
	@Override
	public CommerceDiscount fetchByC_C_A(
		long companyId, String couponCode, boolean active) {

		return fetchByC_C_A(companyId, couponCode, active, true);
	}

	/**
	 * Returns the commerce discount where companyId = &#63; and couponCode = &#63; and active = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param couponCode the coupon code
	 * @param active the active
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	 */
	@Override
	public CommerceDiscount fetchByC_C_A(
		long companyId, String couponCode, boolean active,
		boolean useFinderCache) {

		couponCode = Objects.toString(couponCode, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, couponCode, active};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_C_A, finderArgs, this);
		}

		if (result instanceof CommerceDiscount) {
			CommerceDiscount commerceDiscount = (CommerceDiscount)result;

			if ((companyId != commerceDiscount.getCompanyId()) ||
				!Objects.equals(couponCode, commerceDiscount.getCouponCode()) ||
				(active != commerceDiscount.isActive())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_COMMERCEDISCOUNT_WHERE);

			sb.append(_FINDER_COLUMN_C_C_A_COMPANYID_2);

			boolean bindCouponCode = false;

			if (couponCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_C_A_COUPONCODE_3);
			}
			else {
				bindCouponCode = true;

				sb.append(_FINDER_COLUMN_C_C_A_COUPONCODE_2);
			}

			sb.append(_FINDER_COLUMN_C_C_A_ACTIVE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindCouponCode) {
					queryPos.add(StringUtil.toLowerCase(couponCode));
				}

				queryPos.add(active);

				List<CommerceDiscount> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_C_A, finderArgs, list);
					}
				}
				else {
					CommerceDiscount commerceDiscount = list.get(0);

					result = commerceDiscount;

					cacheResult(commerceDiscount);
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
			return (CommerceDiscount)result;
		}
	}

	/**
	 * Removes the commerce discount where companyId = &#63; and couponCode = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param couponCode the coupon code
	 * @param active the active
	 * @return the commerce discount that was removed
	 */
	@Override
	public CommerceDiscount removeByC_C_A(
			long companyId, String couponCode, boolean active)
		throws NoSuchDiscountException {

		CommerceDiscount commerceDiscount = findByC_C_A(
			companyId, couponCode, active);

		return remove(commerceDiscount);
	}

	/**
	 * Returns the number of commerce discounts where companyId = &#63; and couponCode = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param couponCode the coupon code
	 * @param active the active
	 * @return the number of matching commerce discounts
	 */
	@Override
	public int countByC_C_A(long companyId, String couponCode, boolean active) {
		CommerceDiscount commerceDiscount = fetchByC_C_A(
			companyId, couponCode, active);

		if (commerceDiscount == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_C_C_A_COMPANYID_2 =
		"commerceDiscount.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_A_COUPONCODE_2 =
		"lower(commerceDiscount.couponCode) = ? AND ";

	private static final String _FINDER_COLUMN_C_C_A_COUPONCODE_3 =
		"(commerceDiscount.couponCode IS NULL OR commerceDiscount.couponCode = '') AND ";

	private static final String _FINDER_COLUMN_C_C_A_ACTIVE_2 =
		"commerceDiscount.active = ?";

	private FinderPath _finderPathWithPaginationFindByC_L_A_S;
	private FinderPath _finderPathWithoutPaginationFindByC_L_A_S;
	private FinderPath _finderPathCountByC_L_A_S;
	private CollectionPersistenceFinder<CommerceDiscount>
		_collectionPersistenceFinderByC_L_A_S;

	/**
	 * Returns all the commerce discounts where companyId = &#63; and level = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param level the level
	 * @param active the active
	 * @param status the status
	 * @return the matching commerce discounts
	 */
	@Override
	public List<CommerceDiscount> findByC_L_A_S(
		long companyId, String level, boolean active, int status) {

		return findByC_L_A_S(
			companyId, level, active, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discounts where companyId = &#63; and level = &#63; and active = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param level the level
	 * @param active the active
	 * @param status the status
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @return the range of matching commerce discounts
	 */
	@Override
	public List<CommerceDiscount> findByC_L_A_S(
		long companyId, String level, boolean active, int status, int start,
		int end) {

		return findByC_L_A_S(
			companyId, level, active, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce discounts where companyId = &#63; and level = &#63; and active = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param level the level
	 * @param active the active
	 * @param status the status
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discounts
	 */
	@Override
	public List<CommerceDiscount> findByC_L_A_S(
		long companyId, String level, boolean active, int status, int start,
		int end, OrderByComparator<CommerceDiscount> orderByComparator) {

		return findByC_L_A_S(
			companyId, level, active, status, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the commerce discounts where companyId = &#63; and level = &#63; and active = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param level the level
	 * @param active the active
	 * @param status the status
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discounts
	 */
	@Override
	public List<CommerceDiscount> findByC_L_A_S(
		long companyId, String level, boolean active, int status, int start,
		int end, OrderByComparator<CommerceDiscount> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByC_L_A_S.find(
			finderCache, new Object[] {companyId, level, active, status}, start,
			end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce discount in the ordered set where companyId = &#63; and level = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param level the level
	 * @param active the active
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount
	 * @throws NoSuchDiscountException if a matching commerce discount could not be found
	 */
	@Override
	public CommerceDiscount findByC_L_A_S_First(
			long companyId, String level, boolean active, int status,
			OrderByComparator<CommerceDiscount> orderByComparator)
		throws NoSuchDiscountException {

		CommerceDiscount commerceDiscount = fetchByC_L_A_S_First(
			companyId, level, active, status, orderByComparator);

		if (commerceDiscount != null) {
			return commerceDiscount;
		}

		throw new NoSuchDiscountException(
			_collectionPersistenceFinderByC_L_A_S.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {companyId, level, active, status}));
	}

	/**
	 * Returns the first commerce discount in the ordered set where companyId = &#63; and level = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param level the level
	 * @param active the active
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	 */
	@Override
	public CommerceDiscount fetchByC_L_A_S_First(
		long companyId, String level, boolean active, int status,
		OrderByComparator<CommerceDiscount> orderByComparator) {

		return _collectionPersistenceFinderByC_L_A_S.fetchFirst(
			finderCache, new Object[] {companyId, level, active, status},
			orderByComparator);
	}

	/**
	 * Returns all the commerce discounts that the user has permission to view where companyId = &#63; and level = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param level the level
	 * @param active the active
	 * @param status the status
	 * @return the matching commerce discounts that the user has permission to view
	 */
	@Override
	public List<CommerceDiscount> filterFindByC_L_A_S(
		long companyId, String level, boolean active, int status) {

		return filterFindByC_L_A_S(
			companyId, level, active, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discounts that the user has permission to view where companyId = &#63; and level = &#63; and active = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param level the level
	 * @param active the active
	 * @param status the status
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @return the range of matching commerce discounts that the user has permission to view
	 */
	@Override
	public List<CommerceDiscount> filterFindByC_L_A_S(
		long companyId, String level, boolean active, int status, int start,
		int end) {

		return filterFindByC_L_A_S(
			companyId, level, active, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce discounts that the user has permissions to view where companyId = &#63; and level = &#63; and active = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param level the level
	 * @param active the active
	 * @param status the status
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discounts that the user has permission to view
	 */
	@Override
	public List<CommerceDiscount> filterFindByC_L_A_S(
		long companyId, String level, boolean active, int status, int start,
		int end, OrderByComparator<CommerceDiscount> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_L_A_S(
				companyId, level, active, status, start, end,
				orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByC_L_A_S(
					companyId, level, active, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator));
		}

		level = Objects.toString(level, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(7);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COMMERCEDISCOUNT_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEDISCOUNT_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_L_A_S_COMPANYID_2);

		boolean bindLevel = false;

		if (level.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_L_A_S_LEVEL_3_SQL);
		}
		else {
			bindLevel = true;

			sb.append(_FINDER_COLUMN_C_L_A_S_LEVEL_2_SQL);
		}

		sb.append(_FINDER_COLUMN_C_L_A_S_ACTIVE_2_SQL);

		sb.append(_FINDER_COLUMN_C_L_A_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEDISCOUNT_NO_INLINE_DISTINCT_WHERE_2);
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
					CommerceDiscountModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(CommerceDiscountModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceDiscount.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, CommerceDiscountImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, CommerceDiscountImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			if (bindLevel) {
				queryPos.add(level);
			}

			queryPos.add(active);

			queryPos.add(status);

			return (List<CommerceDiscount>)QueryUtil.list(
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
	 * Removes all the commerce discounts where companyId = &#63; and level = &#63; and active = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param level the level
	 * @param active the active
	 * @param status the status
	 */
	@Override
	public void removeByC_L_A_S(
		long companyId, String level, boolean active, int status) {

		_collectionPersistenceFinderByC_L_A_S.remove(
			finderCache, new Object[] {companyId, level, active, status});
	}

	/**
	 * Returns the number of commerce discounts where companyId = &#63; and level = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param level the level
	 * @param active the active
	 * @param status the status
	 * @return the number of matching commerce discounts
	 */
	@Override
	public int countByC_L_A_S(
		long companyId, String level, boolean active, int status) {

		return _collectionPersistenceFinderByC_L_A_S.count(
			finderCache, new Object[] {companyId, level, active, status});
	}

	/**
	 * Returns the number of commerce discounts that the user has permission to view where companyId = &#63; and level = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param level the level
	 * @param active the active
	 * @param status the status
	 * @return the number of matching commerce discounts that the user has permission to view
	 */
	@Override
	public int filterCountByC_L_A_S(
		long companyId, String level, boolean active, int status) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByC_L_A_S(companyId, level, active, status);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<CommerceDiscount> commerceDiscounts = findByC_L_A_S(
				companyId, level, active, status);

			commerceDiscounts = InlineSQLHelperUtil.filter(commerceDiscounts);

			return commerceDiscounts.size();
		}

		level = Objects.toString(level, "");

		StringBundler sb = new StringBundler(5);

		sb.append(_FILTER_SQL_COUNT_COMMERCEDISCOUNT_WHERE);

		sb.append(_FINDER_COLUMN_C_L_A_S_COMPANYID_2);

		boolean bindLevel = false;

		if (level.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_L_A_S_LEVEL_3_SQL);
		}
		else {
			bindLevel = true;

			sb.append(_FINDER_COLUMN_C_L_A_S_LEVEL_2_SQL);
		}

		sb.append(_FINDER_COLUMN_C_L_A_S_ACTIVE_2_SQL);

		sb.append(_FINDER_COLUMN_C_L_A_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceDiscount.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			if (bindLevel) {
				queryPos.add(level);
			}

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

	private static final String _FINDER_COLUMN_C_L_A_S_COMPANYID_2 =
		"commerceDiscount.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_L_A_S_LEVEL_2_SQL =
		"commerceDiscount.levelType = ? AND ";

	private static final String _FINDER_COLUMN_C_L_A_S_LEVEL_3_SQL =
		"(commerceDiscount.levelType IS NULL OR commerceDiscount.levelType = '') AND ";

	private static final String _FINDER_COLUMN_C_L_A_S_ACTIVE_2_SQL =
		"commerceDiscount.active_ = ? AND ";

	private static final String _FINDER_COLUMN_C_L_A_S_STATUS_2 =
		"commerceDiscount.status = ?";

	private FinderPath _finderPathFetchByERC_C;
	private UniquePersistenceFinder<CommerceDiscount>
		_uniquePersistenceFinderByERC_C;

	/**
	 * Returns the commerce discount where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchDiscountException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching commerce discount
	 * @throws NoSuchDiscountException if a matching commerce discount could not be found
	 */
	@Override
	public CommerceDiscount findByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchDiscountException {

		CommerceDiscount commerceDiscount = fetchByERC_C(
			externalReferenceCode, companyId);

		if (commerceDiscount == null) {
			String message =
				_uniquePersistenceFinderByERC_C.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {externalReferenceCode, companyId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchDiscountException(message);
		}

		return commerceDiscount;
	}

	/**
	 * Returns the commerce discount where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	 */
	@Override
	public CommerceDiscount fetchByERC_C(
		String externalReferenceCode, long companyId) {

		return fetchByERC_C(externalReferenceCode, companyId, true);
	}

	/**
	 * Returns the commerce discount where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	 */
	@Override
	public CommerceDiscount fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache) {

		return _uniquePersistenceFinderByERC_C.fetch(
			finderCache, new Object[] {externalReferenceCode, companyId},
			useFinderCache);
	}

	/**
	 * Removes the commerce discount where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the commerce discount that was removed
	 */
	@Override
	public CommerceDiscount removeByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchDiscountException {

		CommerceDiscount commerceDiscount = findByERC_C(
			externalReferenceCode, companyId);

		return remove(commerceDiscount);
	}

	/**
	 * Returns the number of commerce discounts where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching commerce discounts
	 */
	@Override
	public int countByERC_C(String externalReferenceCode, long companyId) {
		return _uniquePersistenceFinderByERC_C.count(
			finderCache, new Object[] {externalReferenceCode, companyId});
	}

	public CommerceDiscountPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("level", "levelType");
		dbColumnNames.put("active", "active_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceDiscount.class);

		setModelImplClass(CommerceDiscountImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceDiscountTable.INSTANCE);
	}

	/**
	 * Caches the commerce discount in the entity cache if it is enabled.
	 *
	 * @param commerceDiscount the commerce discount
	 */
	@Override
	public void cacheResult(CommerceDiscount commerceDiscount) {
		entityCache.putResult(
			CommerceDiscountImpl.class, commerceDiscount.getPrimaryKey(),
			commerceDiscount);

		finderCache.putResult(
			_finderPathFetchByC_C_A,
			new Object[] {
				commerceDiscount.getCompanyId(),
				commerceDiscount.getCouponCode(), commerceDiscount.isActive()
			},
			commerceDiscount);

		finderCache.putResult(
			_finderPathFetchByERC_C,
			new Object[] {
				commerceDiscount.getExternalReferenceCode(),
				commerceDiscount.getCompanyId()
			},
			commerceDiscount);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce discounts in the entity cache if it is enabled.
	 *
	 * @param commerceDiscounts the commerce discounts
	 */
	@Override
	public void cacheResult(List<CommerceDiscount> commerceDiscounts) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commerceDiscounts.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommerceDiscount commerceDiscount : commerceDiscounts) {
			if (entityCache.getResult(
					CommerceDiscountImpl.class,
					commerceDiscount.getPrimaryKey()) == null) {

				cacheResult(commerceDiscount);
			}
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceDiscountModelImpl commerceDiscountModelImpl) {

		Object[] args = new Object[] {
			commerceDiscountModelImpl.getCompanyId(),
			commerceDiscountModelImpl.getCouponCode(),
			commerceDiscountModelImpl.isActive()
		};

		finderCache.putResult(
			_finderPathFetchByC_C_A, args, commerceDiscountModelImpl);

		args = new Object[] {
			commerceDiscountModelImpl.getExternalReferenceCode(),
			commerceDiscountModelImpl.getCompanyId()
		};

		finderCache.putResult(
			_finderPathFetchByERC_C, args, commerceDiscountModelImpl);
	}

	/**
	 * Creates a new commerce discount with the primary key. Does not add the commerce discount to the database.
	 *
	 * @param commerceDiscountId the primary key for the new commerce discount
	 * @return the new commerce discount
	 */
	@Override
	public CommerceDiscount create(long commerceDiscountId) {
		CommerceDiscount commerceDiscount = new CommerceDiscountImpl();

		commerceDiscount.setNew(true);
		commerceDiscount.setPrimaryKey(commerceDiscountId);

		String uuid = PortalUUIDUtil.generate();

		commerceDiscount.setUuid(uuid);

		commerceDiscount.setCompanyId(CompanyThreadLocal.getCompanyId());

		return commerceDiscount;
	}

	/**
	 * Removes the commerce discount with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceDiscountId the primary key of the commerce discount
	 * @return the commerce discount that was removed
	 * @throws NoSuchDiscountException if a commerce discount with the primary key could not be found
	 */
	@Override
	public CommerceDiscount remove(long commerceDiscountId)
		throws NoSuchDiscountException {

		return remove((Serializable)commerceDiscountId);
	}

	@Override
	protected CommerceDiscount removeImpl(CommerceDiscount commerceDiscount) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceDiscount)) {
				commerceDiscount = (CommerceDiscount)session.get(
					CommerceDiscountImpl.class,
					commerceDiscount.getPrimaryKeyObj());
			}

			if (commerceDiscount != null) {
				session.delete(commerceDiscount);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceDiscount != null) {
			clearCache(commerceDiscount);
		}

		return commerceDiscount;
	}

	@Override
	public CommerceDiscount updateImpl(CommerceDiscount commerceDiscount) {
		boolean isNew = commerceDiscount.isNew();

		if (!(commerceDiscount instanceof CommerceDiscountModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceDiscount.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceDiscount);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceDiscount proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceDiscount implementation " +
					commerceDiscount.getClass());
		}

		CommerceDiscountModelImpl commerceDiscountModelImpl =
			(CommerceDiscountModelImpl)commerceDiscount;

		if (Validator.isNull(commerceDiscount.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			commerceDiscount.setUuid(uuid);
		}

		if (Validator.isNull(commerceDiscount.getExternalReferenceCode())) {
			commerceDiscount.setExternalReferenceCode(
				commerceDiscount.getUuid());
		}
		else {
			if (!Objects.equals(
					commerceDiscountModelImpl.getColumnOriginalValue(
						"externalReferenceCode"),
					commerceDiscount.getExternalReferenceCode())) {

				long userId = GetterUtil.getLong(
					PrincipalThreadLocal.getName());

				if (userId > 0) {
					long companyId = commerceDiscount.getCompanyId();

					long groupId = 0;

					long classPK = 0;

					if (!isNew) {
						classPK = commerceDiscount.getPrimaryKey();
					}

					try {
						commerceDiscount.setExternalReferenceCode(
							SanitizerUtil.sanitize(
								companyId, groupId, userId,
								CommerceDiscount.class.getName(), classPK,
								ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL,
								commerceDiscount.getExternalReferenceCode(),
								null));
					}
					catch (SanitizerException sanitizerException) {
						throw new SystemException(sanitizerException);
					}
				}
			}

			CommerceDiscount ercCommerceDiscount = fetchByERC_C(
				commerceDiscount.getExternalReferenceCode(),
				commerceDiscount.getCompanyId());

			if (isNew) {
				if (ercCommerceDiscount != null) {
					throw new DuplicateCommerceDiscountExternalReferenceCodeException(
						"Duplicate commerce discount with external reference code " +
							commerceDiscount.getExternalReferenceCode() +
								" and company " +
									commerceDiscount.getCompanyId());
				}
			}
			else {
				if ((ercCommerceDiscount != null) &&
					(commerceDiscount.getCommerceDiscountId() !=
						ercCommerceDiscount.getCommerceDiscountId())) {

					throw new DuplicateCommerceDiscountExternalReferenceCodeException(
						"Duplicate commerce discount with external reference code " +
							commerceDiscount.getExternalReferenceCode() +
								" and company " +
									commerceDiscount.getCompanyId());
				}
			}
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (commerceDiscount.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceDiscount.setCreateDate(date);
			}
			else {
				commerceDiscount.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commerceDiscountModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceDiscount.setModifiedDate(date);
			}
			else {
				commerceDiscount.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceDiscount);
			}
			else {
				commerceDiscount = (CommerceDiscount)session.merge(
					commerceDiscount);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceDiscountImpl.class, commerceDiscountModelImpl, false, true);

		cacheUniqueFindersCache(commerceDiscountModelImpl);

		if (isNew) {
			commerceDiscount.setNew(false);
		}

		commerceDiscount.resetOriginalValues();

		return commerceDiscount;
	}

	/**
	 * Returns the commerce discount with the primary key or throws a <code>NoSuchDiscountException</code> if it could not be found.
	 *
	 * @param commerceDiscountId the primary key of the commerce discount
	 * @return the commerce discount
	 * @throws NoSuchDiscountException if a commerce discount with the primary key could not be found
	 */
	@Override
	public CommerceDiscount findByPrimaryKey(long commerceDiscountId)
		throws NoSuchDiscountException {

		return findByPrimaryKey((Serializable)commerceDiscountId);
	}

	/**
	 * Returns the commerce discount with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceDiscountId the primary key of the commerce discount
	 * @return the commerce discount, or <code>null</code> if a commerce discount with the primary key could not be found
	 */
	@Override
	public CommerceDiscount fetchByPrimaryKey(long commerceDiscountId) {
		return fetchByPrimaryKey((Serializable)commerceDiscountId);
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
		return "commerceDiscountId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEDISCOUNT;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceDiscountModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce discount persistence.
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
			_SQL_SELECT_COMMERCEDISCOUNT_WHERE,
			_SQL_COUNT_COMMERCEDISCOUNT_WHERE,
			CommerceDiscountModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
			new FinderColumn<>(
				"commerceDiscount.", "uuid", FinderColumn.Type.STRING, "=",
				true, true, CommerceDiscount::getUuid));

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
				_finderPathCountByUuid_C, _SQL_SELECT_COMMERCEDISCOUNT_WHERE,
				_SQL_COUNT_COMMERCEDISCOUNT_WHERE,
				CommerceDiscountModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"commerceDiscount.", "uuid", FinderColumn.Type.STRING, "=",
					true, false, CommerceDiscount::getUuid),
				new FinderColumn<>(
					"commerceDiscount.", "companyId", FinderColumn.Type.LONG,
					"=", true, true, CommerceDiscount::getCompanyId));

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
				_finderPathCountByCompanyId, _SQL_SELECT_COMMERCEDISCOUNT_WHERE,
				_SQL_COUNT_COMMERCEDISCOUNT_WHERE,
				CommerceDiscountModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"commerceDiscount.", "companyId", FinderColumn.Type.LONG,
					"=", true, true, CommerceDiscount::getCompanyId));

		_finderPathWithPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "couponCode"}, true);

		_finderPathWithoutPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "couponCode"}, true);

		_finderPathCountByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "couponCode"}, false);

		_finderPathWithPaginationFindByLtD_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLtD_S",
			new String[] {
				Date.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"displayDate", "status"}, true);

		_finderPathWithPaginationCountByLtD_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByLtD_S",
			new String[] {Date.class.getName(), Integer.class.getName()},
			new String[] {"displayDate", "status"}, false);

		_collectionPersistenceFinderByLtD_S = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByLtD_S, null,
			_finderPathWithPaginationCountByLtD_S,
			_SQL_SELECT_COMMERCEDISCOUNT_WHERE,
			_SQL_COUNT_COMMERCEDISCOUNT_WHERE,
			CommerceDiscountModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
			new FinderColumn<>(
				"commerceDiscount.", "displayDate", FinderColumn.Type.DATE, "<",
				true, false, CommerceDiscount::getDisplayDate),
			new FinderColumn<>(
				"commerceDiscount.", "status", FinderColumn.Type.INTEGER, "=",
				true, true, CommerceDiscount::getStatus));

		_finderPathWithPaginationFindByLtE_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLtE_S",
			new String[] {
				Date.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"expirationDate", "status"}, true);

		_finderPathWithPaginationCountByLtE_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByLtE_S",
			new String[] {Date.class.getName(), Integer.class.getName()},
			new String[] {"expirationDate", "status"}, false);

		_collectionPersistenceFinderByLtE_S = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByLtE_S, null,
			_finderPathWithPaginationCountByLtE_S,
			_SQL_SELECT_COMMERCEDISCOUNT_WHERE,
			_SQL_COUNT_COMMERCEDISCOUNT_WHERE,
			CommerceDiscountModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
			new FinderColumn<>(
				"commerceDiscount.", "expirationDate", FinderColumn.Type.DATE,
				"<", true, false, CommerceDiscount::getExpirationDate),
			new FinderColumn<>(
				"commerceDiscount.", "status", FinderColumn.Type.INTEGER, "=",
				true, true, CommerceDiscount::getStatus));

		_finderPathFetchByC_C_A = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C_A",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"companyId", "couponCode", "active_"}, true);

		_finderPathWithPaginationFindByC_L_A_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_L_A_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "levelType", "active_", "status"}, true);

		_finderPathWithoutPaginationFindByC_L_A_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_L_A_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName(), Integer.class.getName()
			},
			new String[] {"companyId", "levelType", "active_", "status"}, true);

		_finderPathCountByC_L_A_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_L_A_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName(), Integer.class.getName()
			},
			new String[] {"companyId", "levelType", "active_", "status"},
			false);

		_collectionPersistenceFinderByC_L_A_S =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByC_L_A_S,
				_finderPathWithoutPaginationFindByC_L_A_S,
				_finderPathCountByC_L_A_S, _SQL_SELECT_COMMERCEDISCOUNT_WHERE,
				_SQL_COUNT_COMMERCEDISCOUNT_WHERE,
				CommerceDiscountModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"commerceDiscount.", "companyId", FinderColumn.Type.LONG,
					"=", true, false, CommerceDiscount::getCompanyId),
				new FinderColumn<>(
					"commerceDiscount.", "level", FinderColumn.Type.STRING, "=",
					true, false, CommerceDiscount::getLevel),
				new FinderColumn<>(
					"commerceDiscount.", "active", FinderColumn.Type.BOOLEAN,
					"=", true, false, CommerceDiscount::isActive),
				new FinderColumn<>(
					"commerceDiscount.", "status", FinderColumn.Type.INTEGER,
					"=", true, true, CommerceDiscount::getStatus));

		_finderPathFetchByERC_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByERC_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"externalReferenceCode", "companyId"}, true);

		_uniquePersistenceFinderByERC_C = new UniquePersistenceFinder<>(
			this, _finderPathFetchByERC_C, _SQL_SELECT_COMMERCEDISCOUNT_WHERE,
			new FinderColumn<>(
				"commerceDiscount.", "externalReferenceCode",
				FinderColumn.Type.STRING, "=", true, false,
				CommerceDiscount::getExternalReferenceCode),
			new FinderColumn<>(
				"commerceDiscount.", "companyId", FinderColumn.Type.LONG, "=",
				true, true, CommerceDiscount::getCompanyId));

		CommerceDiscountUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		CommerceDiscountUtil.setPersistence(null);

		entityCache.removeCache(CommerceDiscountImpl.class.getName());
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

	private static final String _ENTITY_ALIAS_PREFIX =
		CommerceDiscountModelImpl.ENTITY_ALIAS + ".";

	private static final String _SQL_SELECT_COMMERCEDISCOUNT =
		"SELECT commerceDiscount FROM CommerceDiscount commerceDiscount";

	private static final String _SQL_SELECT_COMMERCEDISCOUNT_WHERE =
		"SELECT commerceDiscount FROM CommerceDiscount commerceDiscount WHERE ";

	private static final String _SQL_COUNT_COMMERCEDISCOUNT_WHERE =
		"SELECT COUNT(commerceDiscount) FROM CommerceDiscount commerceDiscount WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"commerceDiscount.commerceDiscountId";

	private static final String _FILTER_SQL_SELECT_COMMERCEDISCOUNT_WHERE =
		"SELECT DISTINCT {commerceDiscount.*} FROM CommerceDiscount commerceDiscount WHERE ";

	private static final String
		_FILTER_SQL_SELECT_COMMERCEDISCOUNT_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {CommerceDiscount.*} FROM (SELECT DISTINCT commerceDiscount.commerceDiscountId FROM CommerceDiscount commerceDiscount WHERE ";

	private static final String
		_FILTER_SQL_SELECT_COMMERCEDISCOUNT_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN CommerceDiscount ON TEMP_TABLE.commerceDiscountId = CommerceDiscount.commerceDiscountId";

	private static final String _FILTER_SQL_COUNT_COMMERCEDISCOUNT_WHERE =
		"SELECT COUNT(DISTINCT commerceDiscount.commerceDiscountId) AS COUNT_VALUE FROM CommerceDiscount commerceDiscount WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "commerceDiscount";

	private static final String _FILTER_ENTITY_TABLE = "CommerceDiscount";

	private static final String _ORDER_BY_ENTITY_TABLE = "CommerceDiscount.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceDiscount exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDiscountPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "level", "active"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:448433884