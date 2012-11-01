/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.NoSuchPortletItemException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.PortletItem;
import com.liferay.portal.model.impl.PortletItemImpl;
import com.liferay.portal.model.impl.PortletItemModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the portlet item service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PortletItemPersistence
 * @see PortletItemUtil
 * @generated
 */
public class PortletItemPersistenceImpl extends BasePersistenceImpl<PortletItem>
	implements PortletItemPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link PortletItemUtil} to access the portlet item persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = PortletItemImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C = new FinderPath(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemModelImpl.FINDER_CACHE_ENABLED, PortletItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C = new FinderPath(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemModelImpl.FINDER_CACHE_ENABLED, PortletItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			PortletItemModelImpl.GROUPID_COLUMN_BITMASK |
			PortletItemModelImpl.CLASSNAMEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C = new FinderPath(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_P_C = new FinderPath(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemModelImpl.FINDER_CACHE_ENABLED, PortletItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_P_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_C = new FinderPath(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemModelImpl.FINDER_CACHE_ENABLED, PortletItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_P_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName()
			},
			PortletItemModelImpl.GROUPID_COLUMN_BITMASK |
			PortletItemModelImpl.PORTLETID_COLUMN_BITMASK |
			PortletItemModelImpl.CLASSNAMEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P_C = new FinderPath(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FETCH_BY_G_N_P_C = new FinderPath(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemModelImpl.FINDER_CACHE_ENABLED, PortletItemImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_N_P_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Long.class.getName()
			},
			PortletItemModelImpl.GROUPID_COLUMN_BITMASK |
			PortletItemModelImpl.NAME_COLUMN_BITMASK |
			PortletItemModelImpl.PORTLETID_COLUMN_BITMASK |
			PortletItemModelImpl.CLASSNAMEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_N_P_C = new FinderPath(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_N_P_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemModelImpl.FINDER_CACHE_ENABLED, PortletItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemModelImpl.FINDER_CACHE_ENABLED, PortletItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the portlet item in the entity cache if it is enabled.
	 *
	 * @param portletItem the portlet item
	 */
	public void cacheResult(PortletItem portletItem) {
		EntityCacheUtil.putResult(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemImpl.class, portletItem.getPrimaryKey(), portletItem);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N_P_C,
			new Object[] {
				Long.valueOf(portletItem.getGroupId()),
				
			portletItem.getName(),
				
			portletItem.getPortletId(),
				Long.valueOf(portletItem.getClassNameId())
			}, portletItem);

		portletItem.resetOriginalValues();
	}

	/**
	 * Caches the portlet items in the entity cache if it is enabled.
	 *
	 * @param portletItems the portlet items
	 */
	public void cacheResult(List<PortletItem> portletItems) {
		for (PortletItem portletItem : portletItems) {
			if (EntityCacheUtil.getResult(
						PortletItemModelImpl.ENTITY_CACHE_ENABLED,
						PortletItemImpl.class, portletItem.getPrimaryKey()) == null) {
				cacheResult(portletItem);
			}
			else {
				portletItem.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all portlet items.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(PortletItemImpl.class.getName());
		}

		EntityCacheUtil.clearCache(PortletItemImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the portlet item.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(PortletItem portletItem) {
		EntityCacheUtil.removeResult(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemImpl.class, portletItem.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(portletItem);
	}

	@Override
	public void clearCache(List<PortletItem> portletItems) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (PortletItem portletItem : portletItems) {
			EntityCacheUtil.removeResult(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
				PortletItemImpl.class, portletItem.getPrimaryKey());

			clearUniqueFindersCache(portletItem);
		}
	}

	protected void clearUniqueFindersCache(PortletItem portletItem) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_N_P_C,
			new Object[] {
				Long.valueOf(portletItem.getGroupId()),
				
			portletItem.getName(),
				
			portletItem.getPortletId(),
				Long.valueOf(portletItem.getClassNameId())
			});
	}

	/**
	 * Creates a new portlet item with the primary key. Does not add the portlet item to the database.
	 *
	 * @param portletItemId the primary key for the new portlet item
	 * @return the new portlet item
	 */
	public PortletItem create(long portletItemId) {
		PortletItem portletItem = new PortletItemImpl();

		portletItem.setNew(true);
		portletItem.setPrimaryKey(portletItemId);

		return portletItem;
	}

	/**
	 * Removes the portlet item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param portletItemId the primary key of the portlet item
	 * @return the portlet item that was removed
	 * @throws com.liferay.portal.NoSuchPortletItemException if a portlet item with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PortletItem remove(long portletItemId)
		throws NoSuchPortletItemException, SystemException {
		return remove(Long.valueOf(portletItemId));
	}

	/**
	 * Removes the portlet item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the portlet item
	 * @return the portlet item that was removed
	 * @throws com.liferay.portal.NoSuchPortletItemException if a portlet item with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortletItem remove(Serializable primaryKey)
		throws NoSuchPortletItemException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PortletItem portletItem = (PortletItem)session.get(PortletItemImpl.class,
					primaryKey);

			if (portletItem == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPortletItemException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(portletItem);
		}
		catch (NoSuchPortletItemException nsee) {
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
	protected PortletItem removeImpl(PortletItem portletItem)
		throws SystemException {
		portletItem = toUnwrappedModel(portletItem);

		Session session = null;

		try {
			session = openSession();

			if (portletItem.isCachedModel()) {
				portletItem = (PortletItem)session.get(PortletItemImpl.class,
						portletItem.getPrimaryKeyObj());
			}

			if (portletItem != null) {
				session.delete(portletItem);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (portletItem != null) {
			clearCache(portletItem);
		}

		return portletItem;
	}

	@Override
	public PortletItem updateImpl(
		com.liferay.portal.model.PortletItem portletItem)
		throws SystemException {
		portletItem = toUnwrappedModel(portletItem);

		boolean isNew = portletItem.isNew();

		PortletItemModelImpl portletItemModelImpl = (PortletItemModelImpl)portletItem;

		Session session = null;

		try {
			session = openSession();

			if (portletItem.isNew()) {
				session.save(portletItem);

				portletItem.setNew(false);
			}
			else {
				session.merge(portletItem);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !PortletItemModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((portletItemModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(portletItemModelImpl.getOriginalGroupId()),
						Long.valueOf(portletItemModelImpl.getOriginalClassNameId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C,
					args);

				args = new Object[] {
						Long.valueOf(portletItemModelImpl.getGroupId()),
						Long.valueOf(portletItemModelImpl.getClassNameId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C,
					args);
			}

			if ((portletItemModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(portletItemModelImpl.getOriginalGroupId()),
						
						portletItemModelImpl.getOriginalPortletId(),
						Long.valueOf(portletItemModelImpl.getOriginalClassNameId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_C,
					args);

				args = new Object[] {
						Long.valueOf(portletItemModelImpl.getGroupId()),
						
						portletItemModelImpl.getPortletId(),
						Long.valueOf(portletItemModelImpl.getClassNameId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_C,
					args);
			}
		}

		EntityCacheUtil.putResult(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemImpl.class, portletItem.getPrimaryKey(), portletItem);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N_P_C,
				new Object[] {
					Long.valueOf(portletItem.getGroupId()),
					
				portletItem.getName(),
					
				portletItem.getPortletId(),
					Long.valueOf(portletItem.getClassNameId())
				}, portletItem);
		}
		else {
			if ((portletItemModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_G_N_P_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(portletItemModelImpl.getOriginalGroupId()),
						
						portletItemModelImpl.getOriginalName(),
						
						portletItemModelImpl.getOriginalPortletId(),
						Long.valueOf(portletItemModelImpl.getOriginalClassNameId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_N_P_C, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_N_P_C, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N_P_C,
					new Object[] {
						Long.valueOf(portletItem.getGroupId()),
						
					portletItem.getName(),
						
					portletItem.getPortletId(),
						Long.valueOf(portletItem.getClassNameId())
					}, portletItem);
			}
		}

		return portletItem;
	}

	protected PortletItem toUnwrappedModel(PortletItem portletItem) {
		if (portletItem instanceof PortletItemImpl) {
			return portletItem;
		}

		PortletItemImpl portletItemImpl = new PortletItemImpl();

		portletItemImpl.setNew(portletItem.isNew());
		portletItemImpl.setPrimaryKey(portletItem.getPrimaryKey());

		portletItemImpl.setPortletItemId(portletItem.getPortletItemId());
		portletItemImpl.setGroupId(portletItem.getGroupId());
		portletItemImpl.setCompanyId(portletItem.getCompanyId());
		portletItemImpl.setUserId(portletItem.getUserId());
		portletItemImpl.setUserName(portletItem.getUserName());
		portletItemImpl.setCreateDate(portletItem.getCreateDate());
		portletItemImpl.setModifiedDate(portletItem.getModifiedDate());
		portletItemImpl.setName(portletItem.getName());
		portletItemImpl.setPortletId(portletItem.getPortletId());
		portletItemImpl.setClassNameId(portletItem.getClassNameId());

		return portletItemImpl;
	}

	/**
	 * Returns the portlet item with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the portlet item
	 * @return the portlet item
	 * @throws com.liferay.portal.NoSuchModelException if a portlet item with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortletItem findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the portlet item with the primary key or throws a {@link com.liferay.portal.NoSuchPortletItemException} if it could not be found.
	 *
	 * @param portletItemId the primary key of the portlet item
	 * @return the portlet item
	 * @throws com.liferay.portal.NoSuchPortletItemException if a portlet item with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PortletItem findByPrimaryKey(long portletItemId)
		throws NoSuchPortletItemException, SystemException {
		PortletItem portletItem = fetchByPrimaryKey(portletItemId);

		if (portletItem == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + portletItemId);
			}

			throw new NoSuchPortletItemException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				portletItemId);
		}

		return portletItem;
	}

	/**
	 * Returns the portlet item with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the portlet item
	 * @return the portlet item, or <code>null</code> if a portlet item with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortletItem fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the portlet item with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param portletItemId the primary key of the portlet item
	 * @return the portlet item, or <code>null</code> if a portlet item with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PortletItem fetchByPrimaryKey(long portletItemId)
		throws SystemException {
		PortletItem portletItem = (PortletItem)EntityCacheUtil.getResult(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
				PortletItemImpl.class, portletItemId);

		if (portletItem == _nullPortletItem) {
			return null;
		}

		if (portletItem == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				portletItem = (PortletItem)session.get(PortletItemImpl.class,
						Long.valueOf(portletItemId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (portletItem != null) {
					cacheResult(portletItem);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
						PortletItemImpl.class, portletItemId, _nullPortletItem);
				}

				closeSession(session);
			}
		}

		return portletItem;
	}

	/**
	 * Returns all the portlet items where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the matching portlet items
	 * @throws SystemException if a system exception occurred
	 */
	public List<PortletItem> findByG_C(long groupId, long classNameId)
		throws SystemException {
		return findByG_C(groupId, classNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the portlet items where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of portlet items
	 * @param end the upper bound of the range of portlet items (not inclusive)
	 * @return the range of matching portlet items
	 * @throws SystemException if a system exception occurred
	 */
	public List<PortletItem> findByG_C(long groupId, long classNameId,
		int start, int end) throws SystemException {
		return findByG_C(groupId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the portlet items where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of portlet items
	 * @param end the upper bound of the range of portlet items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching portlet items
	 * @throws SystemException if a system exception occurred
	 */
	public List<PortletItem> findByG_C(long groupId, long classNameId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C;
			finderArgs = new Object[] { groupId, classNameId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C;
			finderArgs = new Object[] {
					groupId, classNameId,
					
					start, end, orderByComparator
				};
		}

		List<PortletItem> list = (List<PortletItem>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (PortletItem portletItem : list) {
				if ((groupId != portletItem.getGroupId()) ||
						(classNameId != portletItem.getClassNameId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_PORTLETITEM_WHERE);

			query.append(_FINDER_COLUMN_G_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				list = (List<PortletItem>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first portlet item in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet item
	 * @throws com.liferay.portal.NoSuchPortletItemException if a matching portlet item could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PortletItem findByG_C_First(long groupId, long classNameId,
		OrderByComparator orderByComparator)
		throws NoSuchPortletItemException, SystemException {
		PortletItem portletItem = fetchByG_C_First(groupId, classNameId,
				orderByComparator);

		if (portletItem != null) {
			return portletItem;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPortletItemException(msg.toString());
	}

	/**
	 * Returns the first portlet item in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet item, or <code>null</code> if a matching portlet item could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PortletItem fetchByG_C_First(long groupId, long classNameId,
		OrderByComparator orderByComparator) throws SystemException {
		List<PortletItem> list = findByG_C(groupId, classNameId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last portlet item in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet item
	 * @throws com.liferay.portal.NoSuchPortletItemException if a matching portlet item could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PortletItem findByG_C_Last(long groupId, long classNameId,
		OrderByComparator orderByComparator)
		throws NoSuchPortletItemException, SystemException {
		PortletItem portletItem = fetchByG_C_Last(groupId, classNameId,
				orderByComparator);

		if (portletItem != null) {
			return portletItem;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPortletItemException(msg.toString());
	}

	/**
	 * Returns the last portlet item in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet item, or <code>null</code> if a matching portlet item could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PortletItem fetchByG_C_Last(long groupId, long classNameId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByG_C(groupId, classNameId);

		List<PortletItem> list = findByG_C(groupId, classNameId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the portlet items before and after the current portlet item in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param portletItemId the primary key of the current portlet item
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next portlet item
	 * @throws com.liferay.portal.NoSuchPortletItemException if a portlet item with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PortletItem[] findByG_C_PrevAndNext(long portletItemId,
		long groupId, long classNameId, OrderByComparator orderByComparator)
		throws NoSuchPortletItemException, SystemException {
		PortletItem portletItem = findByPrimaryKey(portletItemId);

		Session session = null;

		try {
			session = openSession();

			PortletItem[] array = new PortletItemImpl[3];

			array[0] = getByG_C_PrevAndNext(session, portletItem, groupId,
					classNameId, orderByComparator, true);

			array[1] = portletItem;

			array[2] = getByG_C_PrevAndNext(session, portletItem, groupId,
					classNameId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected PortletItem getByG_C_PrevAndNext(Session session,
		PortletItem portletItem, long groupId, long classNameId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_PORTLETITEM_WHERE);

		query.append(_FINDER_COLUMN_G_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classNameId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(portletItem);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PortletItem> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the portlet items where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param portletId the portlet ID
	 * @param classNameId the class name ID
	 * @return the matching portlet items
	 * @throws SystemException if a system exception occurred
	 */
	public List<PortletItem> findByG_P_C(long groupId, String portletId,
		long classNameId) throws SystemException {
		return findByG_P_C(groupId, portletId, classNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the portlet items where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param portletId the portlet ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of portlet items
	 * @param end the upper bound of the range of portlet items (not inclusive)
	 * @return the range of matching portlet items
	 * @throws SystemException if a system exception occurred
	 */
	public List<PortletItem> findByG_P_C(long groupId, String portletId,
		long classNameId, int start, int end) throws SystemException {
		return findByG_P_C(groupId, portletId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the portlet items where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param portletId the portlet ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of portlet items
	 * @param end the upper bound of the range of portlet items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching portlet items
	 * @throws SystemException if a system exception occurred
	 */
	public List<PortletItem> findByG_P_C(long groupId, String portletId,
		long classNameId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_C;
			finderArgs = new Object[] { groupId, portletId, classNameId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_P_C;
			finderArgs = new Object[] {
					groupId, portletId, classNameId,
					
					start, end, orderByComparator
				};
		}

		List<PortletItem> list = (List<PortletItem>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (PortletItem portletItem : list) {
				if ((groupId != portletItem.getGroupId()) ||
						!Validator.equals(portletId, portletItem.getPortletId()) ||
						(classNameId != portletItem.getClassNameId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_PORTLETITEM_WHERE);

			query.append(_FINDER_COLUMN_G_P_C_GROUPID_2);

			if (portletId == null) {
				query.append(_FINDER_COLUMN_G_P_C_PORTLETID_1);
			}
			else {
				if (portletId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_P_C_PORTLETID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_P_C_PORTLETID_2);
				}
			}

			query.append(_FINDER_COLUMN_G_P_C_CLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (portletId != null) {
					qPos.add(portletId);
				}

				qPos.add(classNameId);

				list = (List<PortletItem>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first portlet item in the ordered set where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param portletId the portlet ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet item
	 * @throws com.liferay.portal.NoSuchPortletItemException if a matching portlet item could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PortletItem findByG_P_C_First(long groupId, String portletId,
		long classNameId, OrderByComparator orderByComparator)
		throws NoSuchPortletItemException, SystemException {
		PortletItem portletItem = fetchByG_P_C_First(groupId, portletId,
				classNameId, orderByComparator);

		if (portletItem != null) {
			return portletItem;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", portletId=");
		msg.append(portletId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPortletItemException(msg.toString());
	}

	/**
	 * Returns the first portlet item in the ordered set where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param portletId the portlet ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet item, or <code>null</code> if a matching portlet item could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PortletItem fetchByG_P_C_First(long groupId, String portletId,
		long classNameId, OrderByComparator orderByComparator)
		throws SystemException {
		List<PortletItem> list = findByG_P_C(groupId, portletId, classNameId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last portlet item in the ordered set where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param portletId the portlet ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet item
	 * @throws com.liferay.portal.NoSuchPortletItemException if a matching portlet item could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PortletItem findByG_P_C_Last(long groupId, String portletId,
		long classNameId, OrderByComparator orderByComparator)
		throws NoSuchPortletItemException, SystemException {
		PortletItem portletItem = fetchByG_P_C_Last(groupId, portletId,
				classNameId, orderByComparator);

		if (portletItem != null) {
			return portletItem;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", portletId=");
		msg.append(portletId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPortletItemException(msg.toString());
	}

	/**
	 * Returns the last portlet item in the ordered set where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param portletId the portlet ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet item, or <code>null</code> if a matching portlet item could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PortletItem fetchByG_P_C_Last(long groupId, String portletId,
		long classNameId, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByG_P_C(groupId, portletId, classNameId);

		List<PortletItem> list = findByG_P_C(groupId, portletId, classNameId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the portlet items before and after the current portlet item in the ordered set where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	 *
	 * @param portletItemId the primary key of the current portlet item
	 * @param groupId the group ID
	 * @param portletId the portlet ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next portlet item
	 * @throws com.liferay.portal.NoSuchPortletItemException if a portlet item with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PortletItem[] findByG_P_C_PrevAndNext(long portletItemId,
		long groupId, String portletId, long classNameId,
		OrderByComparator orderByComparator)
		throws NoSuchPortletItemException, SystemException {
		PortletItem portletItem = findByPrimaryKey(portletItemId);

		Session session = null;

		try {
			session = openSession();

			PortletItem[] array = new PortletItemImpl[3];

			array[0] = getByG_P_C_PrevAndNext(session, portletItem, groupId,
					portletId, classNameId, orderByComparator, true);

			array[1] = portletItem;

			array[2] = getByG_P_C_PrevAndNext(session, portletItem, groupId,
					portletId, classNameId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected PortletItem getByG_P_C_PrevAndNext(Session session,
		PortletItem portletItem, long groupId, String portletId,
		long classNameId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_PORTLETITEM_WHERE);

		query.append(_FINDER_COLUMN_G_P_C_GROUPID_2);

		if (portletId == null) {
			query.append(_FINDER_COLUMN_G_P_C_PORTLETID_1);
		}
		else {
			if (portletId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_P_C_PORTLETID_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_P_C_PORTLETID_2);
			}
		}

		query.append(_FINDER_COLUMN_G_P_C_CLASSNAMEID_2);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (portletId != null) {
			qPos.add(portletId);
		}

		qPos.add(classNameId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(portletItem);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PortletItem> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the portlet item where groupId = &#63; and name = &#63; and portletId = &#63; and classNameId = &#63; or throws a {@link com.liferay.portal.NoSuchPortletItemException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param portletId the portlet ID
	 * @param classNameId the class name ID
	 * @return the matching portlet item
	 * @throws com.liferay.portal.NoSuchPortletItemException if a matching portlet item could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PortletItem findByG_N_P_C(long groupId, String name,
		String portletId, long classNameId)
		throws NoSuchPortletItemException, SystemException {
		PortletItem portletItem = fetchByG_N_P_C(groupId, name, portletId,
				classNameId);

		if (portletItem == null) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", name=");
			msg.append(name);

			msg.append(", portletId=");
			msg.append(portletId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchPortletItemException(msg.toString());
		}

		return portletItem;
	}

	/**
	 * Returns the portlet item where groupId = &#63; and name = &#63; and portletId = &#63; and classNameId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param portletId the portlet ID
	 * @param classNameId the class name ID
	 * @return the matching portlet item, or <code>null</code> if a matching portlet item could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PortletItem fetchByG_N_P_C(long groupId, String name,
		String portletId, long classNameId) throws SystemException {
		return fetchByG_N_P_C(groupId, name, portletId, classNameId, true);
	}

	/**
	 * Returns the portlet item where groupId = &#63; and name = &#63; and portletId = &#63; and classNameId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param portletId the portlet ID
	 * @param classNameId the class name ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching portlet item, or <code>null</code> if a matching portlet item could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PortletItem fetchByG_N_P_C(long groupId, String name,
		String portletId, long classNameId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, name, portletId, classNameId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_N_P_C,
					finderArgs, this);
		}

		if (result instanceof PortletItem) {
			PortletItem portletItem = (PortletItem)result;

			if ((groupId != portletItem.getGroupId()) ||
					!Validator.equals(name, portletItem.getName()) ||
					!Validator.equals(portletId, portletItem.getPortletId()) ||
					(classNameId != portletItem.getClassNameId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_PORTLETITEM_WHERE);

			query.append(_FINDER_COLUMN_G_N_P_C_GROUPID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_G_N_P_C_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_N_P_C_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_N_P_C_NAME_2);
				}
			}

			if (portletId == null) {
				query.append(_FINDER_COLUMN_G_N_P_C_PORTLETID_1);
			}
			else {
				if (portletId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_N_P_C_PORTLETID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_N_P_C_PORTLETID_2);
				}
			}

			query.append(_FINDER_COLUMN_G_N_P_C_CLASSNAMEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (name != null) {
					qPos.add(name);
				}

				if (portletId != null) {
					qPos.add(portletId);
				}

				qPos.add(classNameId);

				List<PortletItem> list = q.list();

				result = list;

				PortletItem portletItem = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N_P_C,
						finderArgs, list);
				}
				else {
					portletItem = list.get(0);

					cacheResult(portletItem);

					if ((portletItem.getGroupId() != groupId) ||
							(portletItem.getName() == null) ||
							!portletItem.getName().equals(name) ||
							(portletItem.getPortletId() == null) ||
							!portletItem.getPortletId().equals(portletId) ||
							(portletItem.getClassNameId() != classNameId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N_P_C,
							finderArgs, portletItem);
					}
				}

				return portletItem;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_N_P_C,
						finderArgs);
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (PortletItem)result;
			}
		}
	}

	/**
	 * Returns all the portlet items.
	 *
	 * @return the portlet items
	 * @throws SystemException if a system exception occurred
	 */
	public List<PortletItem> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the portlet items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of portlet items
	 * @param end the upper bound of the range of portlet items (not inclusive)
	 * @return the range of portlet items
	 * @throws SystemException if a system exception occurred
	 */
	public List<PortletItem> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the portlet items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of portlet items
	 * @param end the upper bound of the range of portlet items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of portlet items
	 * @throws SystemException if a system exception occurred
	 */
	public List<PortletItem> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<PortletItem> list = (List<PortletItem>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_PORTLETITEM);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_PORTLETITEM;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<PortletItem>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<PortletItem>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the portlet items where groupId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_C(long groupId, long classNameId)
		throws SystemException {
		for (PortletItem portletItem : findByG_C(groupId, classNameId)) {
			remove(portletItem);
		}
	}

	/**
	 * Removes all the portlet items where groupId = &#63; and portletId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param portletId the portlet ID
	 * @param classNameId the class name ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_P_C(long groupId, String portletId, long classNameId)
		throws SystemException {
		for (PortletItem portletItem : findByG_P_C(groupId, portletId,
				classNameId)) {
			remove(portletItem);
		}
	}

	/**
	 * Removes the portlet item where groupId = &#63; and name = &#63; and portletId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param portletId the portlet ID
	 * @param classNameId the class name ID
	 * @return the portlet item that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public PortletItem removeByG_N_P_C(long groupId, String name,
		String portletId, long classNameId)
		throws NoSuchPortletItemException, SystemException {
		PortletItem portletItem = findByG_N_P_C(groupId, name, portletId,
				classNameId);

		return remove(portletItem);
	}

	/**
	 * Removes all the portlet items from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (PortletItem portletItem : findAll()) {
			remove(portletItem);
		}
	}

	/**
	 * Returns the number of portlet items where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the number of matching portlet items
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_C(long groupId, long classNameId)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, classNameId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_PORTLETITEM_WHERE);

			query.append(_FINDER_COLUMN_G_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_C, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of portlet items where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param portletId the portlet ID
	 * @param classNameId the class name ID
	 * @return the number of matching portlet items
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_P_C(long groupId, String portletId, long classNameId)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, portletId, classNameId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_P_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_PORTLETITEM_WHERE);

			query.append(_FINDER_COLUMN_G_P_C_GROUPID_2);

			if (portletId == null) {
				query.append(_FINDER_COLUMN_G_P_C_PORTLETID_1);
			}
			else {
				if (portletId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_P_C_PORTLETID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_P_C_PORTLETID_2);
				}
			}

			query.append(_FINDER_COLUMN_G_P_C_CLASSNAMEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (portletId != null) {
					qPos.add(portletId);
				}

				qPos.add(classNameId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_P_C,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of portlet items where groupId = &#63; and name = &#63; and portletId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param portletId the portlet ID
	 * @param classNameId the class name ID
	 * @return the number of matching portlet items
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_N_P_C(long groupId, String name, String portletId,
		long classNameId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, name, portletId, classNameId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_N_P_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_PORTLETITEM_WHERE);

			query.append(_FINDER_COLUMN_G_N_P_C_GROUPID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_G_N_P_C_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_N_P_C_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_N_P_C_NAME_2);
				}
			}

			if (portletId == null) {
				query.append(_FINDER_COLUMN_G_N_P_C_PORTLETID_1);
			}
			else {
				if (portletId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_N_P_C_PORTLETID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_N_P_C_PORTLETID_2);
				}
			}

			query.append(_FINDER_COLUMN_G_N_P_C_CLASSNAMEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (name != null) {
					qPos.add(name);
				}

				if (portletId != null) {
					qPos.add(portletId);
				}

				qPos.add(classNameId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_N_P_C,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of portlet items.
	 *
	 * @return the number of portlet items
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_PORTLETITEM);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Initializes the portlet item persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.PortletItem")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<PortletItem>> listenersList = new ArrayList<ModelListener<PortletItem>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<PortletItem>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(PortletItemImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = AccountPersistence.class)
	protected AccountPersistence accountPersistence;
	@BeanReference(type = AddressPersistence.class)
	protected AddressPersistence addressPersistence;
	@BeanReference(type = BrowserTrackerPersistence.class)
	protected BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@BeanReference(type = ClusterGroupPersistence.class)
	protected ClusterGroupPersistence clusterGroupPersistence;
	@BeanReference(type = CompanyPersistence.class)
	protected CompanyPersistence companyPersistence;
	@BeanReference(type = ContactPersistence.class)
	protected ContactPersistence contactPersistence;
	@BeanReference(type = CountryPersistence.class)
	protected CountryPersistence countryPersistence;
	@BeanReference(type = EmailAddressPersistence.class)
	protected EmailAddressPersistence emailAddressPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = ImagePersistence.class)
	protected ImagePersistence imagePersistence;
	@BeanReference(type = LayoutPersistence.class)
	protected LayoutPersistence layoutPersistence;
	@BeanReference(type = LayoutBranchPersistence.class)
	protected LayoutBranchPersistence layoutBranchPersistence;
	@BeanReference(type = LayoutPrototypePersistence.class)
	protected LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(type = LayoutRevisionPersistence.class)
	protected LayoutRevisionPersistence layoutRevisionPersistence;
	@BeanReference(type = LayoutSetPersistence.class)
	protected LayoutSetPersistence layoutSetPersistence;
	@BeanReference(type = LayoutSetBranchPersistence.class)
	protected LayoutSetBranchPersistence layoutSetBranchPersistence;
	@BeanReference(type = LayoutSetPrototypePersistence.class)
	protected LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(type = ListTypePersistence.class)
	protected ListTypePersistence listTypePersistence;
	@BeanReference(type = LockPersistence.class)
	protected LockPersistence lockPersistence;
	@BeanReference(type = MembershipRequestPersistence.class)
	protected MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(type = OrganizationPersistence.class)
	protected OrganizationPersistence organizationPersistence;
	@BeanReference(type = OrgGroupRolePersistence.class)
	protected OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(type = OrgLaborPersistence.class)
	protected OrgLaborPersistence orgLaborPersistence;
	@BeanReference(type = PasswordPolicyPersistence.class)
	protected PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(type = PasswordPolicyRelPersistence.class)
	protected PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(type = PasswordTrackerPersistence.class)
	protected PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(type = PhonePersistence.class)
	protected PhonePersistence phonePersistence;
	@BeanReference(type = PluginSettingPersistence.class)
	protected PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(type = PortalPreferencesPersistence.class)
	protected PortalPreferencesPersistence portalPreferencesPersistence;
	@BeanReference(type = PortletPersistence.class)
	protected PortletPersistence portletPersistence;
	@BeanReference(type = PortletItemPersistence.class)
	protected PortletItemPersistence portletItemPersistence;
	@BeanReference(type = PortletPreferencesPersistence.class)
	protected PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(type = RegionPersistence.class)
	protected RegionPersistence regionPersistence;
	@BeanReference(type = ReleasePersistence.class)
	protected ReleasePersistence releasePersistence;
	@BeanReference(type = RepositoryPersistence.class)
	protected RepositoryPersistence repositoryPersistence;
	@BeanReference(type = RepositoryEntryPersistence.class)
	protected RepositoryEntryPersistence repositoryEntryPersistence;
	@BeanReference(type = ResourceActionPersistence.class)
	protected ResourceActionPersistence resourceActionPersistence;
	@BeanReference(type = ResourceBlockPersistence.class)
	protected ResourceBlockPersistence resourceBlockPersistence;
	@BeanReference(type = ResourceBlockPermissionPersistence.class)
	protected ResourceBlockPermissionPersistence resourceBlockPermissionPersistence;
	@BeanReference(type = ResourcePermissionPersistence.class)
	protected ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(type = ResourceTypePermissionPersistence.class)
	protected ResourceTypePermissionPersistence resourceTypePermissionPersistence;
	@BeanReference(type = RolePersistence.class)
	protected RolePersistence rolePersistence;
	@BeanReference(type = ServiceComponentPersistence.class)
	protected ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(type = ShardPersistence.class)
	protected ShardPersistence shardPersistence;
	@BeanReference(type = SubscriptionPersistence.class)
	protected SubscriptionPersistence subscriptionPersistence;
	@BeanReference(type = TeamPersistence.class)
	protected TeamPersistence teamPersistence;
	@BeanReference(type = TicketPersistence.class)
	protected TicketPersistence ticketPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserGroupPersistence.class)
	protected UserGroupPersistence userGroupPersistence;
	@BeanReference(type = UserGroupGroupRolePersistence.class)
	protected UserGroupGroupRolePersistence userGroupGroupRolePersistence;
	@BeanReference(type = UserGroupRolePersistence.class)
	protected UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(type = UserIdMapperPersistence.class)
	protected UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(type = UserNotificationEventPersistence.class)
	protected UserNotificationEventPersistence userNotificationEventPersistence;
	@BeanReference(type = UserTrackerPersistence.class)
	protected UserTrackerPersistence userTrackerPersistence;
	@BeanReference(type = UserTrackerPathPersistence.class)
	protected UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(type = VirtualHostPersistence.class)
	protected VirtualHostPersistence virtualHostPersistence;
	@BeanReference(type = WebDAVPropsPersistence.class)
	protected WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(type = WebsitePersistence.class)
	protected WebsitePersistence websitePersistence;
	@BeanReference(type = WorkflowDefinitionLinkPersistence.class)
	protected WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
	@BeanReference(type = WorkflowInstanceLinkPersistence.class)
	protected WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	private static final String _SQL_SELECT_PORTLETITEM = "SELECT portletItem FROM PortletItem portletItem";
	private static final String _SQL_SELECT_PORTLETITEM_WHERE = "SELECT portletItem FROM PortletItem portletItem WHERE ";
	private static final String _SQL_COUNT_PORTLETITEM = "SELECT COUNT(portletItem) FROM PortletItem portletItem";
	private static final String _SQL_COUNT_PORTLETITEM_WHERE = "SELECT COUNT(portletItem) FROM PortletItem portletItem WHERE ";
	private static final String _FINDER_COLUMN_G_C_GROUPID_2 = "portletItem.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_CLASSNAMEID_2 = "portletItem.classNameId = ?";
	private static final String _FINDER_COLUMN_G_P_C_GROUPID_2 = "portletItem.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_C_PORTLETID_1 = "portletItem.portletId IS NULL AND ";
	private static final String _FINDER_COLUMN_G_P_C_PORTLETID_2 = "portletItem.portletId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_C_PORTLETID_3 = "(portletItem.portletId IS NULL OR portletItem.portletId = ?) AND ";
	private static final String _FINDER_COLUMN_G_P_C_CLASSNAMEID_2 = "portletItem.classNameId = ?";
	private static final String _FINDER_COLUMN_G_N_P_C_GROUPID_2 = "portletItem.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_N_P_C_NAME_1 = "portletItem.name IS NULL AND ";
	private static final String _FINDER_COLUMN_G_N_P_C_NAME_2 = "lower(portletItem.name) = lower(CAST_TEXT(?)) AND ";
	private static final String _FINDER_COLUMN_G_N_P_C_NAME_3 = "(portletItem.name IS NULL OR lower(portletItem.name) = lower(CAST_TEXT(?))) AND ";
	private static final String _FINDER_COLUMN_G_N_P_C_PORTLETID_1 = "portletItem.portletId IS NULL AND ";
	private static final String _FINDER_COLUMN_G_N_P_C_PORTLETID_2 = "portletItem.portletId = ? AND ";
	private static final String _FINDER_COLUMN_G_N_P_C_PORTLETID_3 = "(portletItem.portletId IS NULL OR portletItem.portletId = ?) AND ";
	private static final String _FINDER_COLUMN_G_N_P_C_CLASSNAMEID_2 = "portletItem.classNameId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "portletItem.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No PortletItem exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No PortletItem exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(PortletItemPersistenceImpl.class);
	private static PortletItem _nullPortletItem = new PortletItemImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<PortletItem> toCacheModel() {
				return _nullPortletItemCacheModel;
			}
		};

	private static CacheModel<PortletItem> _nullPortletItemCacheModel = new CacheModel<PortletItem>() {
			public PortletItem toEntityModel() {
				return _nullPortletItem;
			}
		};
}