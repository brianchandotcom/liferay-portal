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

package com.liferay.dynamic.data.mapping.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.exception.NoSuchTemplateVersionException;
import com.liferay.dynamic.data.mapping.model.DDMTemplateVersion;
import com.liferay.dynamic.data.mapping.model.impl.DDMTemplateVersionImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMTemplateVersionModelImpl;
import com.liferay.dynamic.data.mapping.service.persistence.DDMTemplateVersionPersistence;

import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The persistence implementation for the ddm template version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplateVersionPersistence
 * @see com.liferay.dynamic.data.mapping.service.persistence.DDMTemplateVersionUtil
 * @generated
 */
@ProviderType
public class DDMTemplateVersionPersistenceImpl extends BasePersistenceImpl<DDMTemplateVersion>
	implements DDMTemplateVersionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DDMTemplateVersionUtil} to access the ddm template version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DDMTemplateVersionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByTemplateId;
	private FinderPath _finderPathWithoutPaginationFindByTemplateId;
	private FinderPath _finderPathCountByTemplateId;

	/**
	 * Returns all the ddm template versions where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @return the matching ddm template versions
	 */
	@Override
	public List<DDMTemplateVersion> findByTemplateId(long templateId) {
		return findByTemplateId(templateId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm template versions where templateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param templateId the template ID
	 * @param start the lower bound of the range of ddm template versions
	 * @param end the upper bound of the range of ddm template versions (not inclusive)
	 * @return the range of matching ddm template versions
	 */
	@Override
	public List<DDMTemplateVersion> findByTemplateId(long templateId,
		int start, int end) {
		return findByTemplateId(templateId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm template versions where templateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param templateId the template ID
	 * @param start the lower bound of the range of ddm template versions
	 * @param end the upper bound of the range of ddm template versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm template versions
	 */
	@Override
	public List<DDMTemplateVersion> findByTemplateId(long templateId,
		int start, int end,
		OrderByComparator<DDMTemplateVersion> orderByComparator) {
		return findByTemplateId(templateId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm template versions where templateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param templateId the template ID
	 * @param start the lower bound of the range of ddm template versions
	 * @param end the upper bound of the range of ddm template versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ddm template versions
	 */
	@Override
	public List<DDMTemplateVersion> findByTemplateId(long templateId,
		int start, int end,
		OrderByComparator<DDMTemplateVersion> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByTemplateId;
			finderArgs = new Object[] { templateId };
		}
		else {
			finderPath = _finderPathWithPaginationFindByTemplateId;
			finderArgs = new Object[] { templateId, start, end, orderByComparator };
		}

		List<DDMTemplateVersion> list = null;

		if (retrieveFromCache) {
			list = (List<DDMTemplateVersion>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMTemplateVersion ddmTemplateVersion : list) {
					if ((templateId != ddmTemplateVersion.getTemplateId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_DDMTEMPLATEVERSION_WHERE);

			query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMTemplateVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(templateId);

				if (!pagination) {
					list = (List<DDMTemplateVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMTemplateVersion>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first ddm template version in the ordered set where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm template version
	 * @throws NoSuchTemplateVersionException if a matching ddm template version could not be found
	 */
	@Override
	public DDMTemplateVersion findByTemplateId_First(long templateId,
		OrderByComparator<DDMTemplateVersion> orderByComparator)
		throws NoSuchTemplateVersionException {
		DDMTemplateVersion ddmTemplateVersion = fetchByTemplateId_First(templateId,
				orderByComparator);

		if (ddmTemplateVersion != null) {
			return ddmTemplateVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("templateId=");
		msg.append(templateId);

		msg.append("}");

		throw new NoSuchTemplateVersionException(msg.toString());
	}

	/**
	 * Returns the first ddm template version in the ordered set where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm template version, or <code>null</code> if a matching ddm template version could not be found
	 */
	@Override
	public DDMTemplateVersion fetchByTemplateId_First(long templateId,
		OrderByComparator<DDMTemplateVersion> orderByComparator) {
		List<DDMTemplateVersion> list = findByTemplateId(templateId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddm template version in the ordered set where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm template version
	 * @throws NoSuchTemplateVersionException if a matching ddm template version could not be found
	 */
	@Override
	public DDMTemplateVersion findByTemplateId_Last(long templateId,
		OrderByComparator<DDMTemplateVersion> orderByComparator)
		throws NoSuchTemplateVersionException {
		DDMTemplateVersion ddmTemplateVersion = fetchByTemplateId_Last(templateId,
				orderByComparator);

		if (ddmTemplateVersion != null) {
			return ddmTemplateVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("templateId=");
		msg.append(templateId);

		msg.append("}");

		throw new NoSuchTemplateVersionException(msg.toString());
	}

	/**
	 * Returns the last ddm template version in the ordered set where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm template version, or <code>null</code> if a matching ddm template version could not be found
	 */
	@Override
	public DDMTemplateVersion fetchByTemplateId_Last(long templateId,
		OrderByComparator<DDMTemplateVersion> orderByComparator) {
		int count = countByTemplateId(templateId);

		if (count == 0) {
			return null;
		}

		List<DDMTemplateVersion> list = findByTemplateId(templateId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddm template versions before and after the current ddm template version in the ordered set where templateId = &#63;.
	 *
	 * @param templateVersionId the primary key of the current ddm template version
	 * @param templateId the template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm template version
	 * @throws NoSuchTemplateVersionException if a ddm template version with the primary key could not be found
	 */
	@Override
	public DDMTemplateVersion[] findByTemplateId_PrevAndNext(
		long templateVersionId, long templateId,
		OrderByComparator<DDMTemplateVersion> orderByComparator)
		throws NoSuchTemplateVersionException {
		DDMTemplateVersion ddmTemplateVersion = findByPrimaryKey(templateVersionId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplateVersion[] array = new DDMTemplateVersionImpl[3];

			array[0] = getByTemplateId_PrevAndNext(session, ddmTemplateVersion,
					templateId, orderByComparator, true);

			array[1] = ddmTemplateVersion;

			array[2] = getByTemplateId_PrevAndNext(session, ddmTemplateVersion,
					templateId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMTemplateVersion getByTemplateId_PrevAndNext(Session session,
		DDMTemplateVersion ddmTemplateVersion, long templateId,
		OrderByComparator<DDMTemplateVersion> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMTEMPLATEVERSION_WHERE);

		query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(DDMTemplateVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(templateId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					ddmTemplateVersion)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<DDMTemplateVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddm template versions where templateId = &#63; from the database.
	 *
	 * @param templateId the template ID
	 */
	@Override
	public void removeByTemplateId(long templateId) {
		for (DDMTemplateVersion ddmTemplateVersion : findByTemplateId(
				templateId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmTemplateVersion);
		}
	}

	/**
	 * Returns the number of ddm template versions where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @return the number of matching ddm template versions
	 */
	@Override
	public int countByTemplateId(long templateId) {
		FinderPath finderPath = _finderPathCountByTemplateId;

		Object[] finderArgs = new Object[] { templateId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMTEMPLATEVERSION_WHERE);

			query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(templateId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_TEMPLATEID_TEMPLATEID_2 = "ddmTemplateVersion.templateId = ?";
	private FinderPath _finderPathFetchByT_V;
	private FinderPath _finderPathCountByT_V;

	/**
	 * Returns the ddm template version where templateId = &#63; and version = &#63; or throws a {@link NoSuchTemplateVersionException} if it could not be found.
	 *
	 * @param templateId the template ID
	 * @param version the version
	 * @return the matching ddm template version
	 * @throws NoSuchTemplateVersionException if a matching ddm template version could not be found
	 */
	@Override
	public DDMTemplateVersion findByT_V(long templateId, String version)
		throws NoSuchTemplateVersionException {
		DDMTemplateVersion ddmTemplateVersion = fetchByT_V(templateId, version);

		if (ddmTemplateVersion == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("templateId=");
			msg.append(templateId);

			msg.append(", version=");
			msg.append(version);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchTemplateVersionException(msg.toString());
		}

		return ddmTemplateVersion;
	}

	/**
	 * Returns the ddm template version where templateId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param templateId the template ID
	 * @param version the version
	 * @return the matching ddm template version, or <code>null</code> if a matching ddm template version could not be found
	 */
	@Override
	public DDMTemplateVersion fetchByT_V(long templateId, String version) {
		return fetchByT_V(templateId, version, true);
	}

	/**
	 * Returns the ddm template version where templateId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param templateId the template ID
	 * @param version the version
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching ddm template version, or <code>null</code> if a matching ddm template version could not be found
	 */
	@Override
	public DDMTemplateVersion fetchByT_V(long templateId, String version,
		boolean retrieveFromCache) {
		version = Objects.toString(version, "");

		Object[] finderArgs = new Object[] { templateId, version };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(_finderPathFetchByT_V, finderArgs,
					this);
		}

		if (result instanceof DDMTemplateVersion) {
			DDMTemplateVersion ddmTemplateVersion = (DDMTemplateVersion)result;

			if ((templateId != ddmTemplateVersion.getTemplateId()) ||
					!Objects.equals(version, ddmTemplateVersion.getVersion())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_DDMTEMPLATEVERSION_WHERE);

			query.append(_FINDER_COLUMN_T_V_TEMPLATEID_2);

			boolean bindVersion = false;

			if (version.isEmpty()) {
				query.append(_FINDER_COLUMN_T_V_VERSION_3);
			}
			else {
				bindVersion = true;

				query.append(_FINDER_COLUMN_T_V_VERSION_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(templateId);

				if (bindVersion) {
					qPos.add(version);
				}

				List<DDMTemplateVersion> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(_finderPathFetchByT_V, finderArgs,
						list);
				}
				else {
					DDMTemplateVersion ddmTemplateVersion = list.get(0);

					result = ddmTemplateVersion;

					cacheResult(ddmTemplateVersion);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(_finderPathFetchByT_V, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (DDMTemplateVersion)result;
		}
	}

	/**
	 * Removes the ddm template version where templateId = &#63; and version = &#63; from the database.
	 *
	 * @param templateId the template ID
	 * @param version the version
	 * @return the ddm template version that was removed
	 */
	@Override
	public DDMTemplateVersion removeByT_V(long templateId, String version)
		throws NoSuchTemplateVersionException {
		DDMTemplateVersion ddmTemplateVersion = findByT_V(templateId, version);

		return remove(ddmTemplateVersion);
	}

	/**
	 * Returns the number of ddm template versions where templateId = &#63; and version = &#63;.
	 *
	 * @param templateId the template ID
	 * @param version the version
	 * @return the number of matching ddm template versions
	 */
	@Override
	public int countByT_V(long templateId, String version) {
		version = Objects.toString(version, "");

		FinderPath finderPath = _finderPathCountByT_V;

		Object[] finderArgs = new Object[] { templateId, version };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMTEMPLATEVERSION_WHERE);

			query.append(_FINDER_COLUMN_T_V_TEMPLATEID_2);

			boolean bindVersion = false;

			if (version.isEmpty()) {
				query.append(_FINDER_COLUMN_T_V_VERSION_3);
			}
			else {
				bindVersion = true;

				query.append(_FINDER_COLUMN_T_V_VERSION_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(templateId);

				if (bindVersion) {
					qPos.add(version);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_T_V_TEMPLATEID_2 = "ddmTemplateVersion.templateId = ? AND ";
	private static final String _FINDER_COLUMN_T_V_VERSION_2 = "ddmTemplateVersion.version = ?";
	private static final String _FINDER_COLUMN_T_V_VERSION_3 = "(ddmTemplateVersion.version IS NULL OR ddmTemplateVersion.version = '')";
	private FinderPath _finderPathWithPaginationFindByT_S;
	private FinderPath _finderPathWithoutPaginationFindByT_S;
	private FinderPath _finderPathCountByT_S;

	/**
	 * Returns all the ddm template versions where templateId = &#63; and status = &#63;.
	 *
	 * @param templateId the template ID
	 * @param status the status
	 * @return the matching ddm template versions
	 */
	@Override
	public List<DDMTemplateVersion> findByT_S(long templateId, int status) {
		return findByT_S(templateId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm template versions where templateId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param templateId the template ID
	 * @param status the status
	 * @param start the lower bound of the range of ddm template versions
	 * @param end the upper bound of the range of ddm template versions (not inclusive)
	 * @return the range of matching ddm template versions
	 */
	@Override
	public List<DDMTemplateVersion> findByT_S(long templateId, int status,
		int start, int end) {
		return findByT_S(templateId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm template versions where templateId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param templateId the template ID
	 * @param status the status
	 * @param start the lower bound of the range of ddm template versions
	 * @param end the upper bound of the range of ddm template versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm template versions
	 */
	@Override
	public List<DDMTemplateVersion> findByT_S(long templateId, int status,
		int start, int end,
		OrderByComparator<DDMTemplateVersion> orderByComparator) {
		return findByT_S(templateId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm template versions where templateId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param templateId the template ID
	 * @param status the status
	 * @param start the lower bound of the range of ddm template versions
	 * @param end the upper bound of the range of ddm template versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ddm template versions
	 */
	@Override
	public List<DDMTemplateVersion> findByT_S(long templateId, int status,
		int start, int end,
		OrderByComparator<DDMTemplateVersion> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByT_S;
			finderArgs = new Object[] { templateId, status };
		}
		else {
			finderPath = _finderPathWithPaginationFindByT_S;
			finderArgs = new Object[] {
					templateId, status,
					
					start, end, orderByComparator
				};
		}

		List<DDMTemplateVersion> list = null;

		if (retrieveFromCache) {
			list = (List<DDMTemplateVersion>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMTemplateVersion ddmTemplateVersion : list) {
					if ((templateId != ddmTemplateVersion.getTemplateId()) ||
							(status != ddmTemplateVersion.getStatus())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_DDMTEMPLATEVERSION_WHERE);

			query.append(_FINDER_COLUMN_T_S_TEMPLATEID_2);

			query.append(_FINDER_COLUMN_T_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMTemplateVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(templateId);

				qPos.add(status);

				if (!pagination) {
					list = (List<DDMTemplateVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMTemplateVersion>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first ddm template version in the ordered set where templateId = &#63; and status = &#63;.
	 *
	 * @param templateId the template ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm template version
	 * @throws NoSuchTemplateVersionException if a matching ddm template version could not be found
	 */
	@Override
	public DDMTemplateVersion findByT_S_First(long templateId, int status,
		OrderByComparator<DDMTemplateVersion> orderByComparator)
		throws NoSuchTemplateVersionException {
		DDMTemplateVersion ddmTemplateVersion = fetchByT_S_First(templateId,
				status, orderByComparator);

		if (ddmTemplateVersion != null) {
			return ddmTemplateVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("templateId=");
		msg.append(templateId);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchTemplateVersionException(msg.toString());
	}

	/**
	 * Returns the first ddm template version in the ordered set where templateId = &#63; and status = &#63;.
	 *
	 * @param templateId the template ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm template version, or <code>null</code> if a matching ddm template version could not be found
	 */
	@Override
	public DDMTemplateVersion fetchByT_S_First(long templateId, int status,
		OrderByComparator<DDMTemplateVersion> orderByComparator) {
		List<DDMTemplateVersion> list = findByT_S(templateId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddm template version in the ordered set where templateId = &#63; and status = &#63;.
	 *
	 * @param templateId the template ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm template version
	 * @throws NoSuchTemplateVersionException if a matching ddm template version could not be found
	 */
	@Override
	public DDMTemplateVersion findByT_S_Last(long templateId, int status,
		OrderByComparator<DDMTemplateVersion> orderByComparator)
		throws NoSuchTemplateVersionException {
		DDMTemplateVersion ddmTemplateVersion = fetchByT_S_Last(templateId,
				status, orderByComparator);

		if (ddmTemplateVersion != null) {
			return ddmTemplateVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("templateId=");
		msg.append(templateId);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchTemplateVersionException(msg.toString());
	}

	/**
	 * Returns the last ddm template version in the ordered set where templateId = &#63; and status = &#63;.
	 *
	 * @param templateId the template ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm template version, or <code>null</code> if a matching ddm template version could not be found
	 */
	@Override
	public DDMTemplateVersion fetchByT_S_Last(long templateId, int status,
		OrderByComparator<DDMTemplateVersion> orderByComparator) {
		int count = countByT_S(templateId, status);

		if (count == 0) {
			return null;
		}

		List<DDMTemplateVersion> list = findByT_S(templateId, status,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddm template versions before and after the current ddm template version in the ordered set where templateId = &#63; and status = &#63;.
	 *
	 * @param templateVersionId the primary key of the current ddm template version
	 * @param templateId the template ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm template version
	 * @throws NoSuchTemplateVersionException if a ddm template version with the primary key could not be found
	 */
	@Override
	public DDMTemplateVersion[] findByT_S_PrevAndNext(long templateVersionId,
		long templateId, int status,
		OrderByComparator<DDMTemplateVersion> orderByComparator)
		throws NoSuchTemplateVersionException {
		DDMTemplateVersion ddmTemplateVersion = findByPrimaryKey(templateVersionId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplateVersion[] array = new DDMTemplateVersionImpl[3];

			array[0] = getByT_S_PrevAndNext(session, ddmTemplateVersion,
					templateId, status, orderByComparator, true);

			array[1] = ddmTemplateVersion;

			array[2] = getByT_S_PrevAndNext(session, ddmTemplateVersion,
					templateId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMTemplateVersion getByT_S_PrevAndNext(Session session,
		DDMTemplateVersion ddmTemplateVersion, long templateId, int status,
		OrderByComparator<DDMTemplateVersion> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_DDMTEMPLATEVERSION_WHERE);

		query.append(_FINDER_COLUMN_T_S_TEMPLATEID_2);

		query.append(_FINDER_COLUMN_T_S_STATUS_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(DDMTemplateVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(templateId);

		qPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					ddmTemplateVersion)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<DDMTemplateVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddm template versions where templateId = &#63; and status = &#63; from the database.
	 *
	 * @param templateId the template ID
	 * @param status the status
	 */
	@Override
	public void removeByT_S(long templateId, int status) {
		for (DDMTemplateVersion ddmTemplateVersion : findByT_S(templateId,
				status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmTemplateVersion);
		}
	}

	/**
	 * Returns the number of ddm template versions where templateId = &#63; and status = &#63;.
	 *
	 * @param templateId the template ID
	 * @param status the status
	 * @return the number of matching ddm template versions
	 */
	@Override
	public int countByT_S(long templateId, int status) {
		FinderPath finderPath = _finderPathCountByT_S;

		Object[] finderArgs = new Object[] { templateId, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMTEMPLATEVERSION_WHERE);

			query.append(_FINDER_COLUMN_T_S_TEMPLATEID_2);

			query.append(_FINDER_COLUMN_T_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(templateId);

				qPos.add(status);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_T_S_TEMPLATEID_2 = "ddmTemplateVersion.templateId = ? AND ";
	private static final String _FINDER_COLUMN_T_S_STATUS_2 = "ddmTemplateVersion.status = ?";

	public DDMTemplateVersionPersistenceImpl() {
		setModelClass(DDMTemplateVersion.class);

		setModelImplClass(DDMTemplateVersionImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(DDMTemplateVersionModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the ddm template version in the entity cache if it is enabled.
	 *
	 * @param ddmTemplateVersion the ddm template version
	 */
	@Override
	public void cacheResult(DDMTemplateVersion ddmTemplateVersion) {
		entityCache.putResult(DDMTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateVersionImpl.class, ddmTemplateVersion.getPrimaryKey(),
			ddmTemplateVersion);

		finderCache.putResult(_finderPathFetchByT_V,
			new Object[] {
				ddmTemplateVersion.getTemplateId(),
				ddmTemplateVersion.getVersion()
			}, ddmTemplateVersion);

		ddmTemplateVersion.resetOriginalValues();
	}

	/**
	 * Caches the ddm template versions in the entity cache if it is enabled.
	 *
	 * @param ddmTemplateVersions the ddm template versions
	 */
	@Override
	public void cacheResult(List<DDMTemplateVersion> ddmTemplateVersions) {
		for (DDMTemplateVersion ddmTemplateVersion : ddmTemplateVersions) {
			if (entityCache.getResult(
						DDMTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
						DDMTemplateVersionImpl.class,
						ddmTemplateVersion.getPrimaryKey()) == null) {
				cacheResult(ddmTemplateVersion);
			}
			else {
				ddmTemplateVersion.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all ddm template versions.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DDMTemplateVersionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the ddm template version.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DDMTemplateVersion ddmTemplateVersion) {
		entityCache.removeResult(DDMTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateVersionImpl.class, ddmTemplateVersion.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((DDMTemplateVersionModelImpl)ddmTemplateVersion,
			true);
	}

	@Override
	public void clearCache(List<DDMTemplateVersion> ddmTemplateVersions) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DDMTemplateVersion ddmTemplateVersion : ddmTemplateVersions) {
			entityCache.removeResult(DDMTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
				DDMTemplateVersionImpl.class, ddmTemplateVersion.getPrimaryKey());

			clearUniqueFindersCache((DDMTemplateVersionModelImpl)ddmTemplateVersion,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		DDMTemplateVersionModelImpl ddmTemplateVersionModelImpl) {
		Object[] args = new Object[] {
				ddmTemplateVersionModelImpl.getTemplateId(),
				ddmTemplateVersionModelImpl.getVersion()
			};

		finderCache.putResult(_finderPathCountByT_V, args, Long.valueOf(1),
			false);
		finderCache.putResult(_finderPathFetchByT_V, args,
			ddmTemplateVersionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		DDMTemplateVersionModelImpl ddmTemplateVersionModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					ddmTemplateVersionModelImpl.getTemplateId(),
					ddmTemplateVersionModelImpl.getVersion()
				};

			finderCache.removeResult(_finderPathCountByT_V, args);
			finderCache.removeResult(_finderPathFetchByT_V, args);
		}

		if ((ddmTemplateVersionModelImpl.getColumnBitmask() &
				_finderPathFetchByT_V.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					ddmTemplateVersionModelImpl.getOriginalTemplateId(),
					ddmTemplateVersionModelImpl.getOriginalVersion()
				};

			finderCache.removeResult(_finderPathCountByT_V, args);
			finderCache.removeResult(_finderPathFetchByT_V, args);
		}
	}

	/**
	 * Creates a new ddm template version with the primary key. Does not add the ddm template version to the database.
	 *
	 * @param templateVersionId the primary key for the new ddm template version
	 * @return the new ddm template version
	 */
	@Override
	public DDMTemplateVersion create(long templateVersionId) {
		DDMTemplateVersion ddmTemplateVersion = new DDMTemplateVersionImpl();

		ddmTemplateVersion.setNew(true);
		ddmTemplateVersion.setPrimaryKey(templateVersionId);

		ddmTemplateVersion.setCompanyId(companyProvider.getCompanyId());

		return ddmTemplateVersion;
	}

	/**
	 * Removes the ddm template version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param templateVersionId the primary key of the ddm template version
	 * @return the ddm template version that was removed
	 * @throws NoSuchTemplateVersionException if a ddm template version with the primary key could not be found
	 */
	@Override
	public DDMTemplateVersion remove(long templateVersionId)
		throws NoSuchTemplateVersionException {
		return remove((Serializable)templateVersionId);
	}

	/**
	 * Removes the ddm template version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the ddm template version
	 * @return the ddm template version that was removed
	 * @throws NoSuchTemplateVersionException if a ddm template version with the primary key could not be found
	 */
	@Override
	public DDMTemplateVersion remove(Serializable primaryKey)
		throws NoSuchTemplateVersionException {
		Session session = null;

		try {
			session = openSession();

			DDMTemplateVersion ddmTemplateVersion = (DDMTemplateVersion)session.get(DDMTemplateVersionImpl.class,
					primaryKey);

			if (ddmTemplateVersion == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTemplateVersionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(ddmTemplateVersion);
		}
		catch (NoSuchTemplateVersionException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected DDMTemplateVersion removeImpl(
		DDMTemplateVersion ddmTemplateVersion) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ddmTemplateVersion)) {
				ddmTemplateVersion = (DDMTemplateVersion)session.get(DDMTemplateVersionImpl.class,
						ddmTemplateVersion.getPrimaryKeyObj());
			}

			if (ddmTemplateVersion != null) {
				session.delete(ddmTemplateVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (ddmTemplateVersion != null) {
			clearCache(ddmTemplateVersion);
		}

		return ddmTemplateVersion;
	}

	@Override
	public DDMTemplateVersion updateImpl(DDMTemplateVersion ddmTemplateVersion) {
		boolean isNew = ddmTemplateVersion.isNew();

		if (!(ddmTemplateVersion instanceof DDMTemplateVersionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(ddmTemplateVersion.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(ddmTemplateVersion);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ddmTemplateVersion proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom DDMTemplateVersion implementation " +
				ddmTemplateVersion.getClass());
		}

		DDMTemplateVersionModelImpl ddmTemplateVersionModelImpl = (DDMTemplateVersionModelImpl)ddmTemplateVersion;

		Session session = null;

		try {
			session = openSession();

			if (ddmTemplateVersion.isNew()) {
				session.save(ddmTemplateVersion);

				ddmTemplateVersion.setNew(false);
			}
			else {
				ddmTemplateVersion = (DDMTemplateVersion)session.merge(ddmTemplateVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!DDMTemplateVersionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					ddmTemplateVersionModelImpl.getTemplateId()
				};

			finderCache.removeResult(_finderPathCountByTemplateId, args);
			finderCache.removeResult(_finderPathWithoutPaginationFindByTemplateId,
				args);

			args = new Object[] {
					ddmTemplateVersionModelImpl.getTemplateId(),
					ddmTemplateVersionModelImpl.getStatus()
				};

			finderCache.removeResult(_finderPathCountByT_S, args);
			finderCache.removeResult(_finderPathWithoutPaginationFindByT_S, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(_finderPathWithoutPaginationFindAll,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((ddmTemplateVersionModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByTemplateId.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmTemplateVersionModelImpl.getOriginalTemplateId()
					};

				finderCache.removeResult(_finderPathCountByTemplateId, args);
				finderCache.removeResult(_finderPathWithoutPaginationFindByTemplateId,
					args);

				args = new Object[] { ddmTemplateVersionModelImpl.getTemplateId() };

				finderCache.removeResult(_finderPathCountByTemplateId, args);
				finderCache.removeResult(_finderPathWithoutPaginationFindByTemplateId,
					args);
			}

			if ((ddmTemplateVersionModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByT_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmTemplateVersionModelImpl.getOriginalTemplateId(),
						ddmTemplateVersionModelImpl.getOriginalStatus()
					};

				finderCache.removeResult(_finderPathCountByT_S, args);
				finderCache.removeResult(_finderPathWithoutPaginationFindByT_S,
					args);

				args = new Object[] {
						ddmTemplateVersionModelImpl.getTemplateId(),
						ddmTemplateVersionModelImpl.getStatus()
					};

				finderCache.removeResult(_finderPathCountByT_S, args);
				finderCache.removeResult(_finderPathWithoutPaginationFindByT_S,
					args);
			}
		}

		entityCache.putResult(DDMTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateVersionImpl.class, ddmTemplateVersion.getPrimaryKey(),
			ddmTemplateVersion, false);

		clearUniqueFindersCache(ddmTemplateVersionModelImpl, false);
		cacheUniqueFindersCache(ddmTemplateVersionModelImpl);

		ddmTemplateVersion.resetOriginalValues();

		return ddmTemplateVersion;
	}

	/**
	 * Returns the ddm template version with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the ddm template version
	 * @return the ddm template version
	 * @throws NoSuchTemplateVersionException if a ddm template version with the primary key could not be found
	 */
	@Override
	public DDMTemplateVersion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTemplateVersionException {
		DDMTemplateVersion ddmTemplateVersion = fetchByPrimaryKey(primaryKey);

		if (ddmTemplateVersion == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTemplateVersionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return ddmTemplateVersion;
	}

	/**
	 * Returns the ddm template version with the primary key or throws a {@link NoSuchTemplateVersionException} if it could not be found.
	 *
	 * @param templateVersionId the primary key of the ddm template version
	 * @return the ddm template version
	 * @throws NoSuchTemplateVersionException if a ddm template version with the primary key could not be found
	 */
	@Override
	public DDMTemplateVersion findByPrimaryKey(long templateVersionId)
		throws NoSuchTemplateVersionException {
		return findByPrimaryKey((Serializable)templateVersionId);
	}

	/**
	 * Returns the ddm template version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param templateVersionId the primary key of the ddm template version
	 * @return the ddm template version, or <code>null</code> if a ddm template version with the primary key could not be found
	 */
	@Override
	public DDMTemplateVersion fetchByPrimaryKey(long templateVersionId) {
		return fetchByPrimaryKey((Serializable)templateVersionId);
	}

	/**
	 * Returns all the ddm template versions.
	 *
	 * @return the ddm template versions
	 */
	@Override
	public List<DDMTemplateVersion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm template versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm template versions
	 * @param end the upper bound of the range of ddm template versions (not inclusive)
	 * @return the range of ddm template versions
	 */
	@Override
	public List<DDMTemplateVersion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm template versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm template versions
	 * @param end the upper bound of the range of ddm template versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ddm template versions
	 */
	@Override
	public List<DDMTemplateVersion> findAll(int start, int end,
		OrderByComparator<DDMTemplateVersion> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm template versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm template versions
	 * @param end the upper bound of the range of ddm template versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of ddm template versions
	 */
	@Override
	public List<DDMTemplateVersion> findAll(int start, int end,
		OrderByComparator<DDMTemplateVersion> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindAll;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<DDMTemplateVersion> list = null;

		if (retrieveFromCache) {
			list = (List<DDMTemplateVersion>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_DDMTEMPLATEVERSION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DDMTEMPLATEVERSION;

				if (pagination) {
					sql = sql.concat(DDMTemplateVersionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<DDMTemplateVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMTemplateVersion>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the ddm template versions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DDMTemplateVersion ddmTemplateVersion : findAll()) {
			remove(ddmTemplateVersion);
		}
	}

	/**
	 * Returns the number of ddm template versions.
	 *
	 * @return the number of ddm template versions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(_finderPathCountAll,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DDMTEMPLATEVERSION);

				count = (Long)q.uniqueResult();

				finderCache.putResult(_finderPathCountAll, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "templateVersionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DDMTEMPLATEVERSION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return DDMTemplateVersionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the ddm template version persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(DDMTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
				DDMTemplateVersionModelImpl.FINDER_CACHE_ENABLED,
				DDMTemplateVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(DDMTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
				DDMTemplateVersionModelImpl.FINDER_CACHE_ENABLED,
				DDMTemplateVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
				new String[0]);

		_finderPathCountAll = new FinderPath(DDMTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
				DDMTemplateVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
				new String[0]);

		_finderPathWithPaginationFindByTemplateId = new FinderPath(DDMTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
				DDMTemplateVersionModelImpl.FINDER_CACHE_ENABLED,
				DDMTemplateVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByTemplateId",
				new String[] {
					Long.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByTemplateId = new FinderPath(DDMTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
				DDMTemplateVersionModelImpl.FINDER_CACHE_ENABLED,
				DDMTemplateVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByTemplateId",
				new String[] { Long.class.getName() },
				DDMTemplateVersionModelImpl.TEMPLATEID_COLUMN_BITMASK);

		_finderPathCountByTemplateId = new FinderPath(DDMTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
				DDMTemplateVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByTemplateId",
				new String[] { Long.class.getName() });

		_finderPathFetchByT_V = new FinderPath(DDMTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
				DDMTemplateVersionModelImpl.FINDER_CACHE_ENABLED,
				DDMTemplateVersionImpl.class, FINDER_CLASS_NAME_ENTITY,
				"fetchByT_V",
				new String[] { Long.class.getName(), String.class.getName() },
				DDMTemplateVersionModelImpl.TEMPLATEID_COLUMN_BITMASK |
				DDMTemplateVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByT_V = new FinderPath(DDMTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
				DDMTemplateVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByT_V",
				new String[] { Long.class.getName(), String.class.getName() });

		_finderPathWithPaginationFindByT_S = new FinderPath(DDMTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
				DDMTemplateVersionModelImpl.FINDER_CACHE_ENABLED,
				DDMTemplateVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByT_S",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByT_S = new FinderPath(DDMTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
				DDMTemplateVersionModelImpl.FINDER_CACHE_ENABLED,
				DDMTemplateVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByT_S",
				new String[] { Long.class.getName(), Integer.class.getName() },
				DDMTemplateVersionModelImpl.TEMPLATEID_COLUMN_BITMASK |
				DDMTemplateVersionModelImpl.STATUS_COLUMN_BITMASK);

		_finderPathCountByT_S = new FinderPath(DDMTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
				DDMTemplateVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByT_S",
				new String[] { Long.class.getName(), Integer.class.getName() });
	}

	public void destroy() {
		entityCache.removeCache(DDMTemplateVersionImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_DDMTEMPLATEVERSION = "SELECT ddmTemplateVersion FROM DDMTemplateVersion ddmTemplateVersion";
	private static final String _SQL_SELECT_DDMTEMPLATEVERSION_WHERE = "SELECT ddmTemplateVersion FROM DDMTemplateVersion ddmTemplateVersion WHERE ";
	private static final String _SQL_COUNT_DDMTEMPLATEVERSION = "SELECT COUNT(ddmTemplateVersion) FROM DDMTemplateVersion ddmTemplateVersion";
	private static final String _SQL_COUNT_DDMTEMPLATEVERSION_WHERE = "SELECT COUNT(ddmTemplateVersion) FROM DDMTemplateVersion ddmTemplateVersion WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "ddmTemplateVersion.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DDMTemplateVersion exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DDMTemplateVersion exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(DDMTemplateVersionPersistenceImpl.class);
}