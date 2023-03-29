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

import com.liferay.osb.faro.contacts.exception.NoSuchContactsCardTemplateException;
import com.liferay.osb.faro.contacts.model.ContactsCardTemplate;
import com.liferay.osb.faro.contacts.model.impl.ContactsCardTemplateImpl;
import com.liferay.osb.faro.contacts.model.impl.ContactsCardTemplateModelImpl;
import com.liferay.osb.faro.contacts.service.persistence.ContactsCardTemplatePersistence;
import com.liferay.osb.faro.contacts.service.persistence.ContactsCardTemplateUtil;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the contacts card template service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Shinn Lok
 * @generated
 */
public class ContactsCardTemplatePersistenceImpl
	extends BasePersistenceImpl<ContactsCardTemplate>
	implements ContactsCardTemplatePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ContactsCardTemplateUtil</code> to access the contacts card template persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ContactsCardTemplateImpl.class.getName();

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
	 * Returns all the contacts card templates where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching contacts card templates
	 */
	@Override
	public List<ContactsCardTemplate> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the contacts card templates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ContactsCardTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of contacts card templates
	 * @param end the upper bound of the range of contacts card templates (not inclusive)
	 * @return the range of matching contacts card templates
	 */
	@Override
	public List<ContactsCardTemplate> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the contacts card templates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ContactsCardTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of contacts card templates
	 * @param end the upper bound of the range of contacts card templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching contacts card templates
	 */
	@Override
	public List<ContactsCardTemplate> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<ContactsCardTemplate> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the contacts card templates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ContactsCardTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of contacts card templates
	 * @param end the upper bound of the range of contacts card templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching contacts card templates
	 */
	@Override
	public List<ContactsCardTemplate> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<ContactsCardTemplate> orderByComparator,
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

		List<ContactsCardTemplate> list = null;

		if (useFinderCache) {
			list = (List<ContactsCardTemplate>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ContactsCardTemplate contactsCardTemplate : list) {
					if (groupId != contactsCardTemplate.getGroupId()) {
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

			sb.append(_SQL_SELECT_CONTACTSCARDTEMPLATE_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ContactsCardTemplateModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<ContactsCardTemplate>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first contacts card template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching contacts card template
	 * @throws NoSuchContactsCardTemplateException if a matching contacts card template could not be found
	 */
	@Override
	public ContactsCardTemplate findByGroupId_First(
			long groupId,
			OrderByComparator<ContactsCardTemplate> orderByComparator)
		throws NoSuchContactsCardTemplateException {

		ContactsCardTemplate contactsCardTemplate = fetchByGroupId_First(
			groupId, orderByComparator);

		if (contactsCardTemplate != null) {
			return contactsCardTemplate;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchContactsCardTemplateException(sb.toString());
	}

	/**
	 * Returns the first contacts card template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching contacts card template, or <code>null</code> if a matching contacts card template could not be found
	 */
	@Override
	public ContactsCardTemplate fetchByGroupId_First(
		long groupId,
		OrderByComparator<ContactsCardTemplate> orderByComparator) {

		List<ContactsCardTemplate> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last contacts card template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching contacts card template
	 * @throws NoSuchContactsCardTemplateException if a matching contacts card template could not be found
	 */
	@Override
	public ContactsCardTemplate findByGroupId_Last(
			long groupId,
			OrderByComparator<ContactsCardTemplate> orderByComparator)
		throws NoSuchContactsCardTemplateException {

		ContactsCardTemplate contactsCardTemplate = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (contactsCardTemplate != null) {
			return contactsCardTemplate;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchContactsCardTemplateException(sb.toString());
	}

	/**
	 * Returns the last contacts card template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching contacts card template, or <code>null</code> if a matching contacts card template could not be found
	 */
	@Override
	public ContactsCardTemplate fetchByGroupId_Last(
		long groupId,
		OrderByComparator<ContactsCardTemplate> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<ContactsCardTemplate> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the contacts card templates before and after the current contacts card template in the ordered set where groupId = &#63;.
	 *
	 * @param contactsCardTemplateId the primary key of the current contacts card template
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next contacts card template
	 * @throws NoSuchContactsCardTemplateException if a contacts card template with the primary key could not be found
	 */
	@Override
	public ContactsCardTemplate[] findByGroupId_PrevAndNext(
			long contactsCardTemplateId, long groupId,
			OrderByComparator<ContactsCardTemplate> orderByComparator)
		throws NoSuchContactsCardTemplateException {

		ContactsCardTemplate contactsCardTemplate = findByPrimaryKey(
			contactsCardTemplateId);

		Session session = null;

		try {
			session = openSession();

			ContactsCardTemplate[] array = new ContactsCardTemplateImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, contactsCardTemplate, groupId, orderByComparator,
				true);

			array[1] = contactsCardTemplate;

			array[2] = getByGroupId_PrevAndNext(
				session, contactsCardTemplate, groupId, orderByComparator,
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

	protected ContactsCardTemplate getByGroupId_PrevAndNext(
		Session session, ContactsCardTemplate contactsCardTemplate,
		long groupId, OrderByComparator<ContactsCardTemplate> orderByComparator,
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

		sb.append(_SQL_SELECT_CONTACTSCARDTEMPLATE_WHERE);

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
			sb.append(ContactsCardTemplateModelImpl.ORDER_BY_JPQL);
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
						contactsCardTemplate)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ContactsCardTemplate> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the contacts card templates where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (ContactsCardTemplate contactsCardTemplate :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(contactsCardTemplate);
		}
	}

	/**
	 * Returns the number of contacts card templates where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching contacts card templates
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CONTACTSCARDTEMPLATE_WHERE);

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
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"contactsCardTemplate.groupId = ?";

	public ContactsCardTemplatePersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("settings", "settings_");
		dbColumnNames.put("type", "type_");

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
				"_dbColumnNames");

			field.setAccessible(true);

			field.set(this, dbColumnNames);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		setModelClass(ContactsCardTemplate.class);
	}

	/**
	 * Caches the contacts card template in the entity cache if it is enabled.
	 *
	 * @param contactsCardTemplate the contacts card template
	 */
	@Override
	public void cacheResult(ContactsCardTemplate contactsCardTemplate) {
		entityCache.putResult(
			ContactsCardTemplateModelImpl.ENTITY_CACHE_ENABLED,
			ContactsCardTemplateImpl.class,
			contactsCardTemplate.getPrimaryKey(), contactsCardTemplate);

		contactsCardTemplate.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the contacts card templates in the entity cache if it is enabled.
	 *
	 * @param contactsCardTemplates the contacts card templates
	 */
	@Override
	public void cacheResult(List<ContactsCardTemplate> contactsCardTemplates) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (contactsCardTemplates.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ContactsCardTemplate contactsCardTemplate :
				contactsCardTemplates) {

			if (entityCache.getResult(
					ContactsCardTemplateModelImpl.ENTITY_CACHE_ENABLED,
					ContactsCardTemplateImpl.class,
					contactsCardTemplate.getPrimaryKey()) == null) {

				cacheResult(contactsCardTemplate);
			}
			else {
				contactsCardTemplate.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all contacts card templates.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ContactsCardTemplateImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the contacts card template.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ContactsCardTemplate contactsCardTemplate) {
		entityCache.removeResult(
			ContactsCardTemplateModelImpl.ENTITY_CACHE_ENABLED,
			ContactsCardTemplateImpl.class,
			contactsCardTemplate.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<ContactsCardTemplate> contactsCardTemplates) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ContactsCardTemplate contactsCardTemplate :
				contactsCardTemplates) {

			entityCache.removeResult(
				ContactsCardTemplateModelImpl.ENTITY_CACHE_ENABLED,
				ContactsCardTemplateImpl.class,
				contactsCardTemplate.getPrimaryKey());
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				ContactsCardTemplateModelImpl.ENTITY_CACHE_ENABLED,
				ContactsCardTemplateImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new contacts card template with the primary key. Does not add the contacts card template to the database.
	 *
	 * @param contactsCardTemplateId the primary key for the new contacts card template
	 * @return the new contacts card template
	 */
	@Override
	public ContactsCardTemplate create(long contactsCardTemplateId) {
		ContactsCardTemplate contactsCardTemplate =
			new ContactsCardTemplateImpl();

		contactsCardTemplate.setNew(true);
		contactsCardTemplate.setPrimaryKey(contactsCardTemplateId);

		return contactsCardTemplate;
	}

	/**
	 * Removes the contacts card template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param contactsCardTemplateId the primary key of the contacts card template
	 * @return the contacts card template that was removed
	 * @throws NoSuchContactsCardTemplateException if a contacts card template with the primary key could not be found
	 */
	@Override
	public ContactsCardTemplate remove(long contactsCardTemplateId)
		throws NoSuchContactsCardTemplateException {

		return remove((Serializable)contactsCardTemplateId);
	}

	/**
	 * Removes the contacts card template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the contacts card template
	 * @return the contacts card template that was removed
	 * @throws NoSuchContactsCardTemplateException if a contacts card template with the primary key could not be found
	 */
	@Override
	public ContactsCardTemplate remove(Serializable primaryKey)
		throws NoSuchContactsCardTemplateException {

		Session session = null;

		try {
			session = openSession();

			ContactsCardTemplate contactsCardTemplate =
				(ContactsCardTemplate)session.get(
					ContactsCardTemplateImpl.class, primaryKey);

			if (contactsCardTemplate == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchContactsCardTemplateException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(contactsCardTemplate);
		}
		catch (NoSuchContactsCardTemplateException noSuchEntityException) {
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
	protected ContactsCardTemplate removeImpl(
		ContactsCardTemplate contactsCardTemplate) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(contactsCardTemplate)) {
				contactsCardTemplate = (ContactsCardTemplate)session.get(
					ContactsCardTemplateImpl.class,
					contactsCardTemplate.getPrimaryKeyObj());
			}

			if (contactsCardTemplate != null) {
				session.delete(contactsCardTemplate);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (contactsCardTemplate != null) {
			clearCache(contactsCardTemplate);
		}

		return contactsCardTemplate;
	}

	@Override
	public ContactsCardTemplate updateImpl(
		ContactsCardTemplate contactsCardTemplate) {

		boolean isNew = contactsCardTemplate.isNew();

		if (!(contactsCardTemplate instanceof ContactsCardTemplateModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(contactsCardTemplate.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					contactsCardTemplate);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in contactsCardTemplate proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ContactsCardTemplate implementation " +
					contactsCardTemplate.getClass());
		}

		ContactsCardTemplateModelImpl contactsCardTemplateModelImpl =
			(ContactsCardTemplateModelImpl)contactsCardTemplate;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(contactsCardTemplate);

				contactsCardTemplate.setNew(false);
			}
			else {
				contactsCardTemplate = (ContactsCardTemplate)session.merge(
					contactsCardTemplate);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!ContactsCardTemplateModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				contactsCardTemplateModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByGroupId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByGroupId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((contactsCardTemplateModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGroupId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					contactsCardTemplateModelImpl.getOriginalGroupId()
				};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);

				args = new Object[] {
					contactsCardTemplateModelImpl.getGroupId()
				};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);
			}
		}

		entityCache.putResult(
			ContactsCardTemplateModelImpl.ENTITY_CACHE_ENABLED,
			ContactsCardTemplateImpl.class,
			contactsCardTemplate.getPrimaryKey(), contactsCardTemplate, false);

		contactsCardTemplate.resetOriginalValues();

		return contactsCardTemplate;
	}

	/**
	 * Returns the contacts card template with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the contacts card template
	 * @return the contacts card template
	 * @throws NoSuchContactsCardTemplateException if a contacts card template with the primary key could not be found
	 */
	@Override
	public ContactsCardTemplate findByPrimaryKey(Serializable primaryKey)
		throws NoSuchContactsCardTemplateException {

		ContactsCardTemplate contactsCardTemplate = fetchByPrimaryKey(
			primaryKey);

		if (contactsCardTemplate == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchContactsCardTemplateException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return contactsCardTemplate;
	}

	/**
	 * Returns the contacts card template with the primary key or throws a <code>NoSuchContactsCardTemplateException</code> if it could not be found.
	 *
	 * @param contactsCardTemplateId the primary key of the contacts card template
	 * @return the contacts card template
	 * @throws NoSuchContactsCardTemplateException if a contacts card template with the primary key could not be found
	 */
	@Override
	public ContactsCardTemplate findByPrimaryKey(long contactsCardTemplateId)
		throws NoSuchContactsCardTemplateException {

		return findByPrimaryKey((Serializable)contactsCardTemplateId);
	}

	/**
	 * Returns the contacts card template with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the contacts card template
	 * @return the contacts card template, or <code>null</code> if a contacts card template with the primary key could not be found
	 */
	@Override
	public ContactsCardTemplate fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			ContactsCardTemplateModelImpl.ENTITY_CACHE_ENABLED,
			ContactsCardTemplateImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		ContactsCardTemplate contactsCardTemplate =
			(ContactsCardTemplate)serializable;

		if (contactsCardTemplate == null) {
			Session session = null;

			try {
				session = openSession();

				contactsCardTemplate = (ContactsCardTemplate)session.get(
					ContactsCardTemplateImpl.class, primaryKey);

				if (contactsCardTemplate != null) {
					cacheResult(contactsCardTemplate);
				}
				else {
					entityCache.putResult(
						ContactsCardTemplateModelImpl.ENTITY_CACHE_ENABLED,
						ContactsCardTemplateImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					ContactsCardTemplateModelImpl.ENTITY_CACHE_ENABLED,
					ContactsCardTemplateImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return contactsCardTemplate;
	}

	/**
	 * Returns the contacts card template with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param contactsCardTemplateId the primary key of the contacts card template
	 * @return the contacts card template, or <code>null</code> if a contacts card template with the primary key could not be found
	 */
	@Override
	public ContactsCardTemplate fetchByPrimaryKey(long contactsCardTemplateId) {
		return fetchByPrimaryKey((Serializable)contactsCardTemplateId);
	}

	@Override
	public Map<Serializable, ContactsCardTemplate> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, ContactsCardTemplate> map =
			new HashMap<Serializable, ContactsCardTemplate>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			ContactsCardTemplate contactsCardTemplate = fetchByPrimaryKey(
				primaryKey);

			if (contactsCardTemplate != null) {
				map.put(primaryKey, contactsCardTemplate);
			}

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

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(
				ContactsCardTemplateModelImpl.ENTITY_CACHE_ENABLED,
				ContactsCardTemplateImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (ContactsCardTemplate)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_CONTACTSCARDTEMPLATE_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
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

			for (ContactsCardTemplate contactsCardTemplate :
					(List<ContactsCardTemplate>)query.list()) {

				map.put(
					contactsCardTemplate.getPrimaryKeyObj(),
					contactsCardTemplate);

				cacheResult(contactsCardTemplate);

				uncachedPrimaryKeys.remove(
					contactsCardTemplate.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					ContactsCardTemplateModelImpl.ENTITY_CACHE_ENABLED,
					ContactsCardTemplateImpl.class, primaryKey, nullModel);
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
	 * Returns all the contacts card templates.
	 *
	 * @return the contacts card templates
	 */
	@Override
	public List<ContactsCardTemplate> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the contacts card templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ContactsCardTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of contacts card templates
	 * @param end the upper bound of the range of contacts card templates (not inclusive)
	 * @return the range of contacts card templates
	 */
	@Override
	public List<ContactsCardTemplate> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the contacts card templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ContactsCardTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of contacts card templates
	 * @param end the upper bound of the range of contacts card templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of contacts card templates
	 */
	@Override
	public List<ContactsCardTemplate> findAll(
		int start, int end,
		OrderByComparator<ContactsCardTemplate> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the contacts card templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ContactsCardTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of contacts card templates
	 * @param end the upper bound of the range of contacts card templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of contacts card templates
	 */
	@Override
	public List<ContactsCardTemplate> findAll(
		int start, int end,
		OrderByComparator<ContactsCardTemplate> orderByComparator,
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

		List<ContactsCardTemplate> list = null;

		if (useFinderCache) {
			list = (List<ContactsCardTemplate>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CONTACTSCARDTEMPLATE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CONTACTSCARDTEMPLATE;

				sql = sql.concat(ContactsCardTemplateModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ContactsCardTemplate>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the contacts card templates from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ContactsCardTemplate contactsCardTemplate : findAll()) {
			remove(contactsCardTemplate);
		}
	}

	/**
	 * Returns the number of contacts card templates.
	 *
	 * @return the number of contacts card templates
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
					_SQL_COUNT_CONTACTSCARDTEMPLATE);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

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
	protected Map<String, Integer> getTableColumnsMap() {
		return ContactsCardTemplateModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the contacts card template persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		_finderPathWithPaginationFindAll = new FinderPath(
			ContactsCardTemplateModelImpl.ENTITY_CACHE_ENABLED,
			ContactsCardTemplateModelImpl.FINDER_CACHE_ENABLED,
			ContactsCardTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			ContactsCardTemplateModelImpl.ENTITY_CACHE_ENABLED,
			ContactsCardTemplateModelImpl.FINDER_CACHE_ENABLED,
			ContactsCardTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			ContactsCardTemplateModelImpl.ENTITY_CACHE_ENABLED,
			ContactsCardTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			ContactsCardTemplateModelImpl.ENTITY_CACHE_ENABLED,
			ContactsCardTemplateModelImpl.FINDER_CACHE_ENABLED,
			ContactsCardTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			ContactsCardTemplateModelImpl.ENTITY_CACHE_ENABLED,
			ContactsCardTemplateModelImpl.FINDER_CACHE_ENABLED,
			ContactsCardTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] {Long.class.getName()},
			ContactsCardTemplateModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByGroupId = new FinderPath(
			ContactsCardTemplateModelImpl.ENTITY_CACHE_ENABLED,
			ContactsCardTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] {Long.class.getName()});

		_setContactsCardTemplateUtilPersistence(this);
	}

	public void destroy() {
		_setContactsCardTemplateUtilPersistence(null);

		entityCache.removeCache(ContactsCardTemplateImpl.class.getName());

		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private void _setContactsCardTemplateUtilPersistence(
		ContactsCardTemplatePersistence contactsCardTemplatePersistence) {

		try {
			Field field = ContactsCardTemplateUtil.class.getDeclaredField(
				"_persistence");

			field.setAccessible(true);

			field.set(null, contactsCardTemplatePersistence);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_CONTACTSCARDTEMPLATE =
		"SELECT contactsCardTemplate FROM ContactsCardTemplate contactsCardTemplate";

	private static final String _SQL_SELECT_CONTACTSCARDTEMPLATE_WHERE_PKS_IN =
		"SELECT contactsCardTemplate FROM ContactsCardTemplate contactsCardTemplate WHERE contactsCardTemplateId IN (";

	private static final String _SQL_SELECT_CONTACTSCARDTEMPLATE_WHERE =
		"SELECT contactsCardTemplate FROM ContactsCardTemplate contactsCardTemplate WHERE ";

	private static final String _SQL_COUNT_CONTACTSCARDTEMPLATE =
		"SELECT COUNT(contactsCardTemplate) FROM ContactsCardTemplate contactsCardTemplate";

	private static final String _SQL_COUNT_CONTACTSCARDTEMPLATE_WHERE =
		"SELECT COUNT(contactsCardTemplate) FROM ContactsCardTemplate contactsCardTemplate WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"contactsCardTemplate.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ContactsCardTemplate exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ContactsCardTemplate exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ContactsCardTemplatePersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"settings", "type"});

}