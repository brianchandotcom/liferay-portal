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

package com.liferay.osb.faro.contacts.service.persistence.impl;

import com.liferay.osb.faro.contacts.exception.NoSuchContactsLayoutTemplateException;
import com.liferay.osb.faro.contacts.model.ContactsLayoutTemplate;
import com.liferay.osb.faro.contacts.model.ContactsLayoutTemplateTable;
import com.liferay.osb.faro.contacts.model.impl.ContactsLayoutTemplateImpl;
import com.liferay.osb.faro.contacts.model.impl.ContactsLayoutTemplateModelImpl;
import com.liferay.osb.faro.contacts.service.persistence.ContactsLayoutTemplatePersistence;
import com.liferay.osb.faro.contacts.service.persistence.ContactsLayoutTemplateUtil;
import com.liferay.osb.faro.contacts.service.persistence.impl.constants.OSBFaroPersistenceConstants;
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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

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
 * The persistence implementation for the contacts layout template service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Shinn Lok
 * @generated
 */
@Component(service = ContactsLayoutTemplatePersistence.class)
public class ContactsLayoutTemplatePersistenceImpl
	extends BasePersistenceImpl<ContactsLayoutTemplate>
	implements ContactsLayoutTemplatePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ContactsLayoutTemplateUtil</code> to access the contacts layout template persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ContactsLayoutTemplateImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the contacts layout templates where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching contacts layout templates
	 */
	@Override
	public List<ContactsLayoutTemplate> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the contacts layout templates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ContactsLayoutTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of contacts layout templates
	 * @param end the upper bound of the range of contacts layout templates (not inclusive)
	 * @return the range of matching contacts layout templates
	 */
	@Override
	public List<ContactsLayoutTemplate> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the contacts layout templates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ContactsLayoutTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of contacts layout templates
	 * @param end the upper bound of the range of contacts layout templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching contacts layout templates
	 */
	@Override
	public List<ContactsLayoutTemplate> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<ContactsLayoutTemplate> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the contacts layout templates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ContactsLayoutTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of contacts layout templates
	 * @param end the upper bound of the range of contacts layout templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching contacts layout templates
	 */
	@Override
	public List<ContactsLayoutTemplate> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<ContactsLayoutTemplate> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByGroupId;
				finderArgs = new Object[] {groupId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] {groupId, start, end, orderByComparator};
		}

		List<ContactsLayoutTemplate> list = null;

		if (useFinderCache) {
			list = (List<ContactsLayoutTemplate>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ContactsLayoutTemplate contactsLayoutTemplate : list) {
					if (groupId != contactsLayoutTemplate.getGroupId()) {
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

			sb.append(_SQL_SELECT_CONTACTSLAYOUTTEMPLATE_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ContactsLayoutTemplateModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<ContactsLayoutTemplate>)QueryUtil.list(
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
	 * Returns the first contacts layout template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching contacts layout template
	 * @throws NoSuchContactsLayoutTemplateException if a matching contacts layout template could not be found
	 */
	@Override
	public ContactsLayoutTemplate findByGroupId_First(
			long groupId,
			OrderByComparator<ContactsLayoutTemplate> orderByComparator)
		throws NoSuchContactsLayoutTemplateException {

		ContactsLayoutTemplate contactsLayoutTemplate = fetchByGroupId_First(
			groupId, orderByComparator);

		if (contactsLayoutTemplate != null) {
			return contactsLayoutTemplate;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchContactsLayoutTemplateException(sb.toString());
	}

	/**
	 * Returns the first contacts layout template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching contacts layout template, or <code>null</code> if a matching contacts layout template could not be found
	 */
	@Override
	public ContactsLayoutTemplate fetchByGroupId_First(
		long groupId,
		OrderByComparator<ContactsLayoutTemplate> orderByComparator) {

		List<ContactsLayoutTemplate> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last contacts layout template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching contacts layout template
	 * @throws NoSuchContactsLayoutTemplateException if a matching contacts layout template could not be found
	 */
	@Override
	public ContactsLayoutTemplate findByGroupId_Last(
			long groupId,
			OrderByComparator<ContactsLayoutTemplate> orderByComparator)
		throws NoSuchContactsLayoutTemplateException {

		ContactsLayoutTemplate contactsLayoutTemplate = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (contactsLayoutTemplate != null) {
			return contactsLayoutTemplate;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchContactsLayoutTemplateException(sb.toString());
	}

	/**
	 * Returns the last contacts layout template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching contacts layout template, or <code>null</code> if a matching contacts layout template could not be found
	 */
	@Override
	public ContactsLayoutTemplate fetchByGroupId_Last(
		long groupId,
		OrderByComparator<ContactsLayoutTemplate> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<ContactsLayoutTemplate> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the contacts layout templates before and after the current contacts layout template in the ordered set where groupId = &#63;.
	 *
	 * @param contactsLayoutTemplateId the primary key of the current contacts layout template
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next contacts layout template
	 * @throws NoSuchContactsLayoutTemplateException if a contacts layout template with the primary key could not be found
	 */
	@Override
	public ContactsLayoutTemplate[] findByGroupId_PrevAndNext(
			long contactsLayoutTemplateId, long groupId,
			OrderByComparator<ContactsLayoutTemplate> orderByComparator)
		throws NoSuchContactsLayoutTemplateException {

		ContactsLayoutTemplate contactsLayoutTemplate = findByPrimaryKey(
			contactsLayoutTemplateId);

		Session session = null;

		try {
			session = openSession();

			ContactsLayoutTemplate[] array = new ContactsLayoutTemplateImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, contactsLayoutTemplate, groupId, orderByComparator,
				true);

			array[1] = contactsLayoutTemplate;

			array[2] = getByGroupId_PrevAndNext(
				session, contactsLayoutTemplate, groupId, orderByComparator,
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

	protected ContactsLayoutTemplate getByGroupId_PrevAndNext(
		Session session, ContactsLayoutTemplate contactsLayoutTemplate,
		long groupId,
		OrderByComparator<ContactsLayoutTemplate> orderByComparator,
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

		sb.append(_SQL_SELECT_CONTACTSLAYOUTTEMPLATE_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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
			sb.append(ContactsLayoutTemplateModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						contactsLayoutTemplate)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ContactsLayoutTemplate> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the contacts layout templates where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (ContactsLayoutTemplate contactsLayoutTemplate :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(contactsLayoutTemplate);
		}
	}

	/**
	 * Returns the number of contacts layout templates where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching contacts layout templates
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CONTACTSLAYOUTTEMPLATE_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"contactsLayoutTemplate.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByG_T;
	private FinderPath _finderPathWithoutPaginationFindByG_T;
	private FinderPath _finderPathCountByG_T;

	/**
	 * Returns all the contacts layout templates where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching contacts layout templates
	 */
	@Override
	public List<ContactsLayoutTemplate> findByG_T(long groupId, int type) {
		return findByG_T(
			groupId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the contacts layout templates where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ContactsLayoutTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of contacts layout templates
	 * @param end the upper bound of the range of contacts layout templates (not inclusive)
	 * @return the range of matching contacts layout templates
	 */
	@Override
	public List<ContactsLayoutTemplate> findByG_T(
		long groupId, int type, int start, int end) {

		return findByG_T(groupId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the contacts layout templates where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ContactsLayoutTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of contacts layout templates
	 * @param end the upper bound of the range of contacts layout templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching contacts layout templates
	 */
	@Override
	public List<ContactsLayoutTemplate> findByG_T(
		long groupId, int type, int start, int end,
		OrderByComparator<ContactsLayoutTemplate> orderByComparator) {

		return findByG_T(groupId, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the contacts layout templates where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ContactsLayoutTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of contacts layout templates
	 * @param end the upper bound of the range of contacts layout templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching contacts layout templates
	 */
	@Override
	public List<ContactsLayoutTemplate> findByG_T(
		long groupId, int type, int start, int end,
		OrderByComparator<ContactsLayoutTemplate> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_T;
				finderArgs = new Object[] {groupId, type};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_T;
			finderArgs = new Object[] {
				groupId, type, start, end, orderByComparator
			};
		}

		List<ContactsLayoutTemplate> list = null;

		if (useFinderCache) {
			list = (List<ContactsLayoutTemplate>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ContactsLayoutTemplate contactsLayoutTemplate : list) {
					if ((groupId != contactsLayoutTemplate.getGroupId()) ||
						(type != contactsLayoutTemplate.getType())) {

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

			sb.append(_SQL_SELECT_CONTACTSLAYOUTTEMPLATE_WHERE);

			sb.append(_FINDER_COLUMN_G_T_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ContactsLayoutTemplateModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(type);

				list = (List<ContactsLayoutTemplate>)QueryUtil.list(
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
	 * Returns the first contacts layout template in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching contacts layout template
	 * @throws NoSuchContactsLayoutTemplateException if a matching contacts layout template could not be found
	 */
	@Override
	public ContactsLayoutTemplate findByG_T_First(
			long groupId, int type,
			OrderByComparator<ContactsLayoutTemplate> orderByComparator)
		throws NoSuchContactsLayoutTemplateException {

		ContactsLayoutTemplate contactsLayoutTemplate = fetchByG_T_First(
			groupId, type, orderByComparator);

		if (contactsLayoutTemplate != null) {
			return contactsLayoutTemplate;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchContactsLayoutTemplateException(sb.toString());
	}

	/**
	 * Returns the first contacts layout template in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching contacts layout template, or <code>null</code> if a matching contacts layout template could not be found
	 */
	@Override
	public ContactsLayoutTemplate fetchByG_T_First(
		long groupId, int type,
		OrderByComparator<ContactsLayoutTemplate> orderByComparator) {

		List<ContactsLayoutTemplate> list = findByG_T(
			groupId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last contacts layout template in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching contacts layout template
	 * @throws NoSuchContactsLayoutTemplateException if a matching contacts layout template could not be found
	 */
	@Override
	public ContactsLayoutTemplate findByG_T_Last(
			long groupId, int type,
			OrderByComparator<ContactsLayoutTemplate> orderByComparator)
		throws NoSuchContactsLayoutTemplateException {

		ContactsLayoutTemplate contactsLayoutTemplate = fetchByG_T_Last(
			groupId, type, orderByComparator);

		if (contactsLayoutTemplate != null) {
			return contactsLayoutTemplate;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchContactsLayoutTemplateException(sb.toString());
	}

	/**
	 * Returns the last contacts layout template in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching contacts layout template, or <code>null</code> if a matching contacts layout template could not be found
	 */
	@Override
	public ContactsLayoutTemplate fetchByG_T_Last(
		long groupId, int type,
		OrderByComparator<ContactsLayoutTemplate> orderByComparator) {

		int count = countByG_T(groupId, type);

		if (count == 0) {
			return null;
		}

		List<ContactsLayoutTemplate> list = findByG_T(
			groupId, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the contacts layout templates before and after the current contacts layout template in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param contactsLayoutTemplateId the primary key of the current contacts layout template
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next contacts layout template
	 * @throws NoSuchContactsLayoutTemplateException if a contacts layout template with the primary key could not be found
	 */
	@Override
	public ContactsLayoutTemplate[] findByG_T_PrevAndNext(
			long contactsLayoutTemplateId, long groupId, int type,
			OrderByComparator<ContactsLayoutTemplate> orderByComparator)
		throws NoSuchContactsLayoutTemplateException {

		ContactsLayoutTemplate contactsLayoutTemplate = findByPrimaryKey(
			contactsLayoutTemplateId);

		Session session = null;

		try {
			session = openSession();

			ContactsLayoutTemplate[] array = new ContactsLayoutTemplateImpl[3];

			array[0] = getByG_T_PrevAndNext(
				session, contactsLayoutTemplate, groupId, type,
				orderByComparator, true);

			array[1] = contactsLayoutTemplate;

			array[2] = getByG_T_PrevAndNext(
				session, contactsLayoutTemplate, groupId, type,
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

	protected ContactsLayoutTemplate getByG_T_PrevAndNext(
		Session session, ContactsLayoutTemplate contactsLayoutTemplate,
		long groupId, int type,
		OrderByComparator<ContactsLayoutTemplate> orderByComparator,
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

		sb.append(_SQL_SELECT_CONTACTSLAYOUTTEMPLATE_WHERE);

		sb.append(_FINDER_COLUMN_G_T_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_T_TYPE_2);

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
			sb.append(ContactsLayoutTemplateModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						contactsLayoutTemplate)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ContactsLayoutTemplate> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the contacts layout templates where groupId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 */
	@Override
	public void removeByG_T(long groupId, int type) {
		for (ContactsLayoutTemplate contactsLayoutTemplate :
				findByG_T(
					groupId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(contactsLayoutTemplate);
		}
	}

	/**
	 * Returns the number of contacts layout templates where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching contacts layout templates
	 */
	@Override
	public int countByG_T(long groupId, int type) {
		FinderPath finderPath = _finderPathCountByG_T;

		Object[] finderArgs = new Object[] {groupId, type};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CONTACTSLAYOUTTEMPLATE_WHERE);

			sb.append(_FINDER_COLUMN_G_T_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_T_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(type);

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

	private static final String _FINDER_COLUMN_G_T_GROUPID_2 =
		"contactsLayoutTemplate.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_T_TYPE_2 =
		"contactsLayoutTemplate.type = ?";

	public ContactsLayoutTemplatePersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("settings", "settings_");
		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(ContactsLayoutTemplate.class);

		setModelImplClass(ContactsLayoutTemplateImpl.class);
		setModelPKClass(long.class);

		setTable(ContactsLayoutTemplateTable.INSTANCE);
	}

	/**
	 * Caches the contacts layout template in the entity cache if it is enabled.
	 *
	 * @param contactsLayoutTemplate the contacts layout template
	 */
	@Override
	public void cacheResult(ContactsLayoutTemplate contactsLayoutTemplate) {
		entityCache.putResult(
			ContactsLayoutTemplateImpl.class,
			contactsLayoutTemplate.getPrimaryKey(), contactsLayoutTemplate);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the contacts layout templates in the entity cache if it is enabled.
	 *
	 * @param contactsLayoutTemplates the contacts layout templates
	 */
	@Override
	public void cacheResult(
		List<ContactsLayoutTemplate> contactsLayoutTemplates) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (contactsLayoutTemplates.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ContactsLayoutTemplate contactsLayoutTemplate :
				contactsLayoutTemplates) {

			if (entityCache.getResult(
					ContactsLayoutTemplateImpl.class,
					contactsLayoutTemplate.getPrimaryKey()) == null) {

				cacheResult(contactsLayoutTemplate);
			}
		}
	}

	/**
	 * Clears the cache for all contacts layout templates.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ContactsLayoutTemplateImpl.class);

		finderCache.clearCache(ContactsLayoutTemplateImpl.class);
	}

	/**
	 * Clears the cache for the contacts layout template.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ContactsLayoutTemplate contactsLayoutTemplate) {
		entityCache.removeResult(
			ContactsLayoutTemplateImpl.class, contactsLayoutTemplate);
	}

	@Override
	public void clearCache(
		List<ContactsLayoutTemplate> contactsLayoutTemplates) {

		for (ContactsLayoutTemplate contactsLayoutTemplate :
				contactsLayoutTemplates) {

			entityCache.removeResult(
				ContactsLayoutTemplateImpl.class, contactsLayoutTemplate);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(ContactsLayoutTemplateImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				ContactsLayoutTemplateImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new contacts layout template with the primary key. Does not add the contacts layout template to the database.
	 *
	 * @param contactsLayoutTemplateId the primary key for the new contacts layout template
	 * @return the new contacts layout template
	 */
	@Override
	public ContactsLayoutTemplate create(long contactsLayoutTemplateId) {
		ContactsLayoutTemplate contactsLayoutTemplate =
			new ContactsLayoutTemplateImpl();

		contactsLayoutTemplate.setNew(true);
		contactsLayoutTemplate.setPrimaryKey(contactsLayoutTemplateId);

		contactsLayoutTemplate.setCompanyId(CompanyThreadLocal.getCompanyId());

		return contactsLayoutTemplate;
	}

	/**
	 * Removes the contacts layout template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param contactsLayoutTemplateId the primary key of the contacts layout template
	 * @return the contacts layout template that was removed
	 * @throws NoSuchContactsLayoutTemplateException if a contacts layout template with the primary key could not be found
	 */
	@Override
	public ContactsLayoutTemplate remove(long contactsLayoutTemplateId)
		throws NoSuchContactsLayoutTemplateException {

		return remove((Serializable)contactsLayoutTemplateId);
	}

	/**
	 * Removes the contacts layout template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the contacts layout template
	 * @return the contacts layout template that was removed
	 * @throws NoSuchContactsLayoutTemplateException if a contacts layout template with the primary key could not be found
	 */
	@Override
	public ContactsLayoutTemplate remove(Serializable primaryKey)
		throws NoSuchContactsLayoutTemplateException {

		Session session = null;

		try {
			session = openSession();

			ContactsLayoutTemplate contactsLayoutTemplate =
				(ContactsLayoutTemplate)session.get(
					ContactsLayoutTemplateImpl.class, primaryKey);

			if (contactsLayoutTemplate == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchContactsLayoutTemplateException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(contactsLayoutTemplate);
		}
		catch (NoSuchContactsLayoutTemplateException noSuchEntityException) {
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
	protected ContactsLayoutTemplate removeImpl(
		ContactsLayoutTemplate contactsLayoutTemplate) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(contactsLayoutTemplate)) {
				contactsLayoutTemplate = (ContactsLayoutTemplate)session.get(
					ContactsLayoutTemplateImpl.class,
					contactsLayoutTemplate.getPrimaryKeyObj());
			}

			if (contactsLayoutTemplate != null) {
				session.delete(contactsLayoutTemplate);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (contactsLayoutTemplate != null) {
			clearCache(contactsLayoutTemplate);
		}

		return contactsLayoutTemplate;
	}

	@Override
	public ContactsLayoutTemplate updateImpl(
		ContactsLayoutTemplate contactsLayoutTemplate) {

		boolean isNew = contactsLayoutTemplate.isNew();

		if (!(contactsLayoutTemplate instanceof
				ContactsLayoutTemplateModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(contactsLayoutTemplate.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					contactsLayoutTemplate);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in contactsLayoutTemplate proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ContactsLayoutTemplate implementation " +
					contactsLayoutTemplate.getClass());
		}

		ContactsLayoutTemplateModelImpl contactsLayoutTemplateModelImpl =
			(ContactsLayoutTemplateModelImpl)contactsLayoutTemplate;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(contactsLayoutTemplate);
			}
			else {
				contactsLayoutTemplate = (ContactsLayoutTemplate)session.merge(
					contactsLayoutTemplate);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			ContactsLayoutTemplateImpl.class, contactsLayoutTemplateModelImpl,
			false, true);

		if (isNew) {
			contactsLayoutTemplate.setNew(false);
		}

		contactsLayoutTemplate.resetOriginalValues();

		return contactsLayoutTemplate;
	}

	/**
	 * Returns the contacts layout template with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the contacts layout template
	 * @return the contacts layout template
	 * @throws NoSuchContactsLayoutTemplateException if a contacts layout template with the primary key could not be found
	 */
	@Override
	public ContactsLayoutTemplate findByPrimaryKey(Serializable primaryKey)
		throws NoSuchContactsLayoutTemplateException {

		ContactsLayoutTemplate contactsLayoutTemplate = fetchByPrimaryKey(
			primaryKey);

		if (contactsLayoutTemplate == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchContactsLayoutTemplateException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return contactsLayoutTemplate;
	}

	/**
	 * Returns the contacts layout template with the primary key or throws a <code>NoSuchContactsLayoutTemplateException</code> if it could not be found.
	 *
	 * @param contactsLayoutTemplateId the primary key of the contacts layout template
	 * @return the contacts layout template
	 * @throws NoSuchContactsLayoutTemplateException if a contacts layout template with the primary key could not be found
	 */
	@Override
	public ContactsLayoutTemplate findByPrimaryKey(
			long contactsLayoutTemplateId)
		throws NoSuchContactsLayoutTemplateException {

		return findByPrimaryKey((Serializable)contactsLayoutTemplateId);
	}

	/**
	 * Returns the contacts layout template with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param contactsLayoutTemplateId the primary key of the contacts layout template
	 * @return the contacts layout template, or <code>null</code> if a contacts layout template with the primary key could not be found
	 */
	@Override
	public ContactsLayoutTemplate fetchByPrimaryKey(
		long contactsLayoutTemplateId) {

		return fetchByPrimaryKey((Serializable)contactsLayoutTemplateId);
	}

	/**
	 * Returns all the contacts layout templates.
	 *
	 * @return the contacts layout templates
	 */
	@Override
	public List<ContactsLayoutTemplate> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the contacts layout templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ContactsLayoutTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of contacts layout templates
	 * @param end the upper bound of the range of contacts layout templates (not inclusive)
	 * @return the range of contacts layout templates
	 */
	@Override
	public List<ContactsLayoutTemplate> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the contacts layout templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ContactsLayoutTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of contacts layout templates
	 * @param end the upper bound of the range of contacts layout templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of contacts layout templates
	 */
	@Override
	public List<ContactsLayoutTemplate> findAll(
		int start, int end,
		OrderByComparator<ContactsLayoutTemplate> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the contacts layout templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ContactsLayoutTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of contacts layout templates
	 * @param end the upper bound of the range of contacts layout templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of contacts layout templates
	 */
	@Override
	public List<ContactsLayoutTemplate> findAll(
		int start, int end,
		OrderByComparator<ContactsLayoutTemplate> orderByComparator,
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

		List<ContactsLayoutTemplate> list = null;

		if (useFinderCache) {
			list = (List<ContactsLayoutTemplate>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CONTACTSLAYOUTTEMPLATE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CONTACTSLAYOUTTEMPLATE;

				sql = sql.concat(ContactsLayoutTemplateModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ContactsLayoutTemplate>)QueryUtil.list(
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
	 * Removes all the contacts layout templates from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ContactsLayoutTemplate contactsLayoutTemplate : findAll()) {
			remove(contactsLayoutTemplate);
		}
	}

	/**
	 * Returns the number of contacts layout templates.
	 *
	 * @return the number of contacts layout templates
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
					_SQL_COUNT_CONTACTSLAYOUTTEMPLATE);

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
		return "contactsLayoutTemplateId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CONTACTSLAYOUTTEMPLATE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ContactsLayoutTemplateModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the contacts layout template persistence.
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

		_finderPathWithPaginationFindByG_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "type_"}, true);

		_finderPathWithoutPaginationFindByG_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_T",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"groupId", "type_"}, true);

		_finderPathCountByG_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_T",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"groupId", "type_"}, false);

		ContactsLayoutTemplateUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		ContactsLayoutTemplateUtil.setPersistence(null);

		entityCache.removeCache(ContactsLayoutTemplateImpl.class.getName());
	}

	@Override
	@Reference(
		target = OSBFaroPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = OSBFaroPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = OSBFaroPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_CONTACTSLAYOUTTEMPLATE =
		"SELECT contactsLayoutTemplate FROM ContactsLayoutTemplate contactsLayoutTemplate";

	private static final String _SQL_SELECT_CONTACTSLAYOUTTEMPLATE_WHERE =
		"SELECT contactsLayoutTemplate FROM ContactsLayoutTemplate contactsLayoutTemplate WHERE ";

	private static final String _SQL_COUNT_CONTACTSLAYOUTTEMPLATE =
		"SELECT COUNT(contactsLayoutTemplate) FROM ContactsLayoutTemplate contactsLayoutTemplate";

	private static final String _SQL_COUNT_CONTACTSLAYOUTTEMPLATE_WHERE =
		"SELECT COUNT(contactsLayoutTemplate) FROM ContactsLayoutTemplate contactsLayoutTemplate WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"contactsLayoutTemplate.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ContactsLayoutTemplate exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ContactsLayoutTemplate exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ContactsLayoutTemplatePersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"settings", "type"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}