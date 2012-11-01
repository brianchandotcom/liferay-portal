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

package com.liferay.portlet.social.service.persistence;

import com.liferay.portal.NoSuchModelException;
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
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.social.NoSuchRequestException;
import com.liferay.portlet.social.model.SocialRequest;
import com.liferay.portlet.social.model.impl.SocialRequestImpl;
import com.liferay.portlet.social.model.impl.SocialRequestModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the social request service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialRequestPersistence
 * @see SocialRequestUtil
 * @generated
 */
public class SocialRequestPersistenceImpl extends BasePersistenceImpl<SocialRequest>
	implements SocialRequestPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SocialRequestUtil} to access the social request persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SocialRequestImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			SocialRequestImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			SocialRequestImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid", new String[] { String.class.getName() },
			SocialRequestModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			SocialRequestImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			SocialRequestModelImpl.UUID_COLUMN_BITMASK |
			SocialRequestModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			SocialRequestImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			SocialRequestImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			SocialRequestModelImpl.UUID_COLUMN_BITMASK |
			SocialRequestModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			SocialRequestImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			SocialRequestImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCompanyId", new String[] { Long.class.getName() },
			SocialRequestModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			SocialRequestImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID =
		new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			SocialRequestImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUserId", new String[] { Long.class.getName() },
			SocialRequestModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_RECEIVERUSERID =
		new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			SocialRequestImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByReceiverUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RECEIVERUSERID =
		new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			SocialRequestImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByReceiverUserId", new String[] { Long.class.getName() },
			SocialRequestModelImpl.RECEIVERUSERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_RECEIVERUSERID = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByReceiverUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_U_S = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			SocialRequestImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByU_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_S = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			SocialRequestImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByU_S",
			new String[] { Long.class.getName(), Integer.class.getName() },
			SocialRequestModelImpl.USERID_COLUMN_BITMASK |
			SocialRequestModelImpl.STATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_S = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_S",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_R_S = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			SocialRequestImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByR_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_S = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			SocialRequestImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByR_S",
			new String[] { Long.class.getName(), Integer.class.getName() },
			SocialRequestModelImpl.RECEIVERUSERID_COLUMN_BITMASK |
			SocialRequestModelImpl.STATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_R_S = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByR_S",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_U_C_C_T_R = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			SocialRequestImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByU_C_C_T_R",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Long.class.getName()
			},
			SocialRequestModelImpl.USERID_COLUMN_BITMASK |
			SocialRequestModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SocialRequestModelImpl.CLASSPK_COLUMN_BITMASK |
			SocialRequestModelImpl.TYPE_COLUMN_BITMASK |
			SocialRequestModelImpl.RECEIVERUSERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_C_C_T_R = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_C_C_T_R",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_U_C_C_T_S =
		new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			SocialRequestImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByU_C_C_T_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_C_C_T_S =
		new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			SocialRequestImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByU_C_C_T_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName()
			},
			SocialRequestModelImpl.USERID_COLUMN_BITMASK |
			SocialRequestModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SocialRequestModelImpl.CLASSPK_COLUMN_BITMASK |
			SocialRequestModelImpl.TYPE_COLUMN_BITMASK |
			SocialRequestModelImpl.STATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_C_C_T_S = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_C_C_T_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C_T_R_S =
		new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			SocialRequestImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_C_T_R_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_T_R_S =
		new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			SocialRequestImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByC_C_T_R_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			SocialRequestModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SocialRequestModelImpl.CLASSPK_COLUMN_BITMASK |
			SocialRequestModelImpl.TYPE_COLUMN_BITMASK |
			SocialRequestModelImpl.RECEIVERUSERID_COLUMN_BITMASK |
			SocialRequestModelImpl.STATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C_T_R_S = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_T_R_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			SocialRequestImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED,
			SocialRequestImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the social request in the entity cache if it is enabled.
	 *
	 * @param socialRequest the social request
	 */
	public void cacheResult(SocialRequest socialRequest) {
		EntityCacheUtil.putResult(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestImpl.class, socialRequest.getPrimaryKey(),
			socialRequest);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				socialRequest.getUuid(),
				Long.valueOf(socialRequest.getGroupId())
			}, socialRequest);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_C_C_T_R,
			new Object[] {
				Long.valueOf(socialRequest.getUserId()),
				Long.valueOf(socialRequest.getClassNameId()),
				Long.valueOf(socialRequest.getClassPK()),
				Integer.valueOf(socialRequest.getType()),
				Long.valueOf(socialRequest.getReceiverUserId())
			}, socialRequest);

		socialRequest.resetOriginalValues();
	}

	/**
	 * Caches the social requests in the entity cache if it is enabled.
	 *
	 * @param socialRequests the social requests
	 */
	public void cacheResult(List<SocialRequest> socialRequests) {
		for (SocialRequest socialRequest : socialRequests) {
			if (EntityCacheUtil.getResult(
						SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
						SocialRequestImpl.class, socialRequest.getPrimaryKey()) == null) {
				cacheResult(socialRequest);
			}
			else {
				socialRequest.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all social requests.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(SocialRequestImpl.class.getName());
		}

		EntityCacheUtil.clearCache(SocialRequestImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the social request.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SocialRequest socialRequest) {
		EntityCacheUtil.removeResult(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestImpl.class, socialRequest.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(socialRequest);
	}

	@Override
	public void clearCache(List<SocialRequest> socialRequests) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SocialRequest socialRequest : socialRequests) {
			EntityCacheUtil.removeResult(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
				SocialRequestImpl.class, socialRequest.getPrimaryKey());

			clearUniqueFindersCache(socialRequest);
		}
	}

	protected void clearUniqueFindersCache(SocialRequest socialRequest) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				socialRequest.getUuid(),
				Long.valueOf(socialRequest.getGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_C_C_T_R,
			new Object[] {
				Long.valueOf(socialRequest.getUserId()),
				Long.valueOf(socialRequest.getClassNameId()),
				Long.valueOf(socialRequest.getClassPK()),
				Integer.valueOf(socialRequest.getType()),
				Long.valueOf(socialRequest.getReceiverUserId())
			});
	}

	/**
	 * Creates a new social request with the primary key. Does not add the social request to the database.
	 *
	 * @param requestId the primary key for the new social request
	 * @return the new social request
	 */
	public SocialRequest create(long requestId) {
		SocialRequest socialRequest = new SocialRequestImpl();

		socialRequest.setNew(true);
		socialRequest.setPrimaryKey(requestId);

		String uuid = PortalUUIDUtil.generate();

		socialRequest.setUuid(uuid);

		return socialRequest;
	}

	/**
	 * Removes the social request with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param requestId the primary key of the social request
	 * @return the social request that was removed
	 * @throws com.liferay.portlet.social.NoSuchRequestException if a social request with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest remove(long requestId)
		throws NoSuchRequestException, SystemException {
		return remove(Long.valueOf(requestId));
	}

	/**
	 * Removes the social request with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the social request
	 * @return the social request that was removed
	 * @throws com.liferay.portlet.social.NoSuchRequestException if a social request with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialRequest remove(Serializable primaryKey)
		throws NoSuchRequestException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SocialRequest socialRequest = (SocialRequest)session.get(SocialRequestImpl.class,
					primaryKey);

			if (socialRequest == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRequestException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(socialRequest);
		}
		catch (NoSuchRequestException nsee) {
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
	protected SocialRequest removeImpl(SocialRequest socialRequest)
		throws SystemException {
		socialRequest = toUnwrappedModel(socialRequest);

		Session session = null;

		try {
			session = openSession();

			if (socialRequest.isCachedModel()) {
				socialRequest = (SocialRequest)session.get(SocialRequestImpl.class,
						socialRequest.getPrimaryKeyObj());
			}

			if (socialRequest != null) {
				session.delete(socialRequest);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (socialRequest != null) {
			clearCache(socialRequest);
		}

		return socialRequest;
	}

	@Override
	public SocialRequest updateImpl(
		com.liferay.portlet.social.model.SocialRequest socialRequest)
		throws SystemException {
		socialRequest = toUnwrappedModel(socialRequest);

		boolean isNew = socialRequest.isNew();

		SocialRequestModelImpl socialRequestModelImpl = (SocialRequestModelImpl)socialRequest;

		if (Validator.isNull(socialRequest.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			socialRequest.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (socialRequest.isNew()) {
				session.save(socialRequest);

				socialRequest.setNew(false);
			}
			else {
				session.merge(socialRequest);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !SocialRequestModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((socialRequestModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						socialRequestModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { socialRequestModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((socialRequestModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						socialRequestModelImpl.getOriginalUuid(),
						Long.valueOf(socialRequestModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						socialRequestModelImpl.getUuid(),
						Long.valueOf(socialRequestModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((socialRequestModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(socialRequestModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						Long.valueOf(socialRequestModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((socialRequestModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(socialRequestModelImpl.getOriginalUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);

				args = new Object[] {
						Long.valueOf(socialRequestModelImpl.getUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);
			}

			if ((socialRequestModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RECEIVERUSERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(socialRequestModelImpl.getOriginalReceiverUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_RECEIVERUSERID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RECEIVERUSERID,
					args);

				args = new Object[] {
						Long.valueOf(socialRequestModelImpl.getReceiverUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_RECEIVERUSERID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RECEIVERUSERID,
					args);
			}

			if ((socialRequestModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(socialRequestModelImpl.getOriginalUserId()),
						Integer.valueOf(socialRequestModelImpl.getOriginalStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_S,
					args);

				args = new Object[] {
						Long.valueOf(socialRequestModelImpl.getUserId()),
						Integer.valueOf(socialRequestModelImpl.getStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_S,
					args);
			}

			if ((socialRequestModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(socialRequestModelImpl.getOriginalReceiverUserId()),
						Integer.valueOf(socialRequestModelImpl.getOriginalStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_R_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_S,
					args);

				args = new Object[] {
						Long.valueOf(socialRequestModelImpl.getReceiverUserId()),
						Integer.valueOf(socialRequestModelImpl.getStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_R_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_S,
					args);
			}

			if ((socialRequestModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_C_C_T_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(socialRequestModelImpl.getOriginalUserId()),
						Long.valueOf(socialRequestModelImpl.getOriginalClassNameId()),
						Long.valueOf(socialRequestModelImpl.getOriginalClassPK()),
						Integer.valueOf(socialRequestModelImpl.getOriginalType()),
						Integer.valueOf(socialRequestModelImpl.getOriginalStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_C_C_T_S,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_C_C_T_S,
					args);

				args = new Object[] {
						Long.valueOf(socialRequestModelImpl.getUserId()),
						Long.valueOf(socialRequestModelImpl.getClassNameId()),
						Long.valueOf(socialRequestModelImpl.getClassPK()),
						Integer.valueOf(socialRequestModelImpl.getType()),
						Integer.valueOf(socialRequestModelImpl.getStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_C_C_T_S,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_C_C_T_S,
					args);
			}

			if ((socialRequestModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_T_R_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(socialRequestModelImpl.getOriginalClassNameId()),
						Long.valueOf(socialRequestModelImpl.getOriginalClassPK()),
						Integer.valueOf(socialRequestModelImpl.getOriginalType()),
						Long.valueOf(socialRequestModelImpl.getOriginalReceiverUserId()),
						Integer.valueOf(socialRequestModelImpl.getOriginalStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C_T_R_S,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_T_R_S,
					args);

				args = new Object[] {
						Long.valueOf(socialRequestModelImpl.getClassNameId()),
						Long.valueOf(socialRequestModelImpl.getClassPK()),
						Integer.valueOf(socialRequestModelImpl.getType()),
						Long.valueOf(socialRequestModelImpl.getReceiverUserId()),
						Integer.valueOf(socialRequestModelImpl.getStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C_T_R_S,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_T_R_S,
					args);
			}
		}

		EntityCacheUtil.putResult(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
			SocialRequestImpl.class, socialRequest.getPrimaryKey(),
			socialRequest);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					socialRequest.getUuid(),
					Long.valueOf(socialRequest.getGroupId())
				}, socialRequest);

			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_C_C_T_R,
				new Object[] {
					Long.valueOf(socialRequest.getUserId()),
					Long.valueOf(socialRequest.getClassNameId()),
					Long.valueOf(socialRequest.getClassPK()),
					Integer.valueOf(socialRequest.getType()),
					Long.valueOf(socialRequest.getReceiverUserId())
				}, socialRequest);
		}
		else {
			if ((socialRequestModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						socialRequestModelImpl.getOriginalUuid(),
						Long.valueOf(socialRequestModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
					new Object[] {
						socialRequest.getUuid(),
						Long.valueOf(socialRequest.getGroupId())
					}, socialRequest);
			}

			if ((socialRequestModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_U_C_C_T_R.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(socialRequestModelImpl.getOriginalUserId()),
						Long.valueOf(socialRequestModelImpl.getOriginalClassNameId()),
						Long.valueOf(socialRequestModelImpl.getOriginalClassPK()),
						Integer.valueOf(socialRequestModelImpl.getOriginalType()),
						Long.valueOf(socialRequestModelImpl.getOriginalReceiverUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_C_C_T_R,
					args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_C_C_T_R,
					args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_C_C_T_R,
					new Object[] {
						Long.valueOf(socialRequest.getUserId()),
						Long.valueOf(socialRequest.getClassNameId()),
						Long.valueOf(socialRequest.getClassPK()),
						Integer.valueOf(socialRequest.getType()),
						Long.valueOf(socialRequest.getReceiverUserId())
					}, socialRequest);
			}
		}

		return socialRequest;
	}

	protected SocialRequest toUnwrappedModel(SocialRequest socialRequest) {
		if (socialRequest instanceof SocialRequestImpl) {
			return socialRequest;
		}

		SocialRequestImpl socialRequestImpl = new SocialRequestImpl();

		socialRequestImpl.setNew(socialRequest.isNew());
		socialRequestImpl.setPrimaryKey(socialRequest.getPrimaryKey());

		socialRequestImpl.setUuid(socialRequest.getUuid());
		socialRequestImpl.setRequestId(socialRequest.getRequestId());
		socialRequestImpl.setGroupId(socialRequest.getGroupId());
		socialRequestImpl.setCompanyId(socialRequest.getCompanyId());
		socialRequestImpl.setUserId(socialRequest.getUserId());
		socialRequestImpl.setCreateDate(socialRequest.getCreateDate());
		socialRequestImpl.setModifiedDate(socialRequest.getModifiedDate());
		socialRequestImpl.setClassNameId(socialRequest.getClassNameId());
		socialRequestImpl.setClassPK(socialRequest.getClassPK());
		socialRequestImpl.setType(socialRequest.getType());
		socialRequestImpl.setExtraData(socialRequest.getExtraData());
		socialRequestImpl.setReceiverUserId(socialRequest.getReceiverUserId());
		socialRequestImpl.setStatus(socialRequest.getStatus());

		return socialRequestImpl;
	}

	/**
	 * Returns the social request with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the social request
	 * @return the social request
	 * @throws com.liferay.portal.NoSuchModelException if a social request with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialRequest findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the social request with the primary key or throws a {@link com.liferay.portlet.social.NoSuchRequestException} if it could not be found.
	 *
	 * @param requestId the primary key of the social request
	 * @return the social request
	 * @throws com.liferay.portlet.social.NoSuchRequestException if a social request with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest findByPrimaryKey(long requestId)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = fetchByPrimaryKey(requestId);

		if (socialRequest == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + requestId);
			}

			throw new NoSuchRequestException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				requestId);
		}

		return socialRequest;
	}

	/**
	 * Returns the social request with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the social request
	 * @return the social request, or <code>null</code> if a social request with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialRequest fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the social request with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param requestId the primary key of the social request
	 * @return the social request, or <code>null</code> if a social request with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest fetchByPrimaryKey(long requestId)
		throws SystemException {
		SocialRequest socialRequest = (SocialRequest)EntityCacheUtil.getResult(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
				SocialRequestImpl.class, requestId);

		if (socialRequest == _nullSocialRequest) {
			return null;
		}

		if (socialRequest == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				socialRequest = (SocialRequest)session.get(SocialRequestImpl.class,
						Long.valueOf(requestId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (socialRequest != null) {
					cacheResult(socialRequest);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(SocialRequestModelImpl.ENTITY_CACHE_ENABLED,
						SocialRequestImpl.class, requestId, _nullSocialRequest);
				}

				closeSession(session);
			}
		}

		return socialRequest;
	}

	/**
	 * Returns all the social requests where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching social requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRequest> findByUuid(String uuid)
		throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social requests where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of social requests
	 * @param end the upper bound of the range of social requests (not inclusive)
	 * @return the range of matching social requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRequest> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social requests where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of social requests
	 * @param end the upper bound of the range of social requests (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRequest> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid, start, end, orderByComparator };
		}

		List<SocialRequest> list = (List<SocialRequest>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SocialRequest socialRequest : list) {
				if (!Validator.equals(uuid, socialRequest.getUuid())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_UUID_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(SocialRequestModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<SocialRequest>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first social request in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social request
	 * @throws com.liferay.portlet.social.NoSuchRequestException if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = fetchByUuid_First(uuid, orderByComparator);

		if (socialRequest != null) {
			return socialRequest;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRequestException(msg.toString());
	}

	/**
	 * Returns the first social request in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social request, or <code>null</code> if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<SocialRequest> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social request in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social request
	 * @throws com.liferay.portlet.social.NoSuchRequestException if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = fetchByUuid_Last(uuid, orderByComparator);

		if (socialRequest != null) {
			return socialRequest;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRequestException(msg.toString());
	}

	/**
	 * Returns the last social request in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social request, or <code>null</code> if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<SocialRequest> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social requests before and after the current social request in the ordered set where uuid = &#63;.
	 *
	 * @param requestId the primary key of the current social request
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social request
	 * @throws com.liferay.portlet.social.NoSuchRequestException if a social request with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest[] findByUuid_PrevAndNext(long requestId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = findByPrimaryKey(requestId);

		Session session = null;

		try {
			session = openSession();

			SocialRequest[] array = new SocialRequestImpl[3];

			array[0] = getByUuid_PrevAndNext(session, socialRequest, uuid,
					orderByComparator, true);

			array[1] = socialRequest;

			array[2] = getByUuid_PrevAndNext(session, socialRequest, uuid,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialRequest getByUuid_PrevAndNext(Session session,
		SocialRequest socialRequest, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else {
			if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}
		}

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
			query.append(SocialRequestModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (uuid != null) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialRequest);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialRequest> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the social request where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.social.NoSuchRequestException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching social request
	 * @throws com.liferay.portlet.social.NoSuchRequestException if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest findByUUID_G(String uuid, long groupId)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = fetchByUUID_G(uuid, groupId);

		if (socialRequest == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchRequestException(msg.toString());
		}

		return socialRequest;
	}

	/**
	 * Returns the social request where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching social request, or <code>null</code> if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the social request where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching social request, or <code>null</code> if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof SocialRequest) {
			SocialRequest socialRequest = (SocialRequest)result;

			if (!Validator.equals(uuid, socialRequest.getUuid()) ||
					(groupId != socialRequest.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_G_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_G_UUID_2);
				}
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			query.append(SocialRequestModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<SocialRequest> list = q.list();

				result = list;

				SocialRequest socialRequest = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					socialRequest = list.get(0);

					cacheResult(socialRequest);

					if ((socialRequest.getUuid() == null) ||
							!socialRequest.getUuid().equals(uuid) ||
							(socialRequest.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, socialRequest);
					}
				}

				return socialRequest;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
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
				return (SocialRequest)result;
			}
		}
	}

	/**
	 * Returns all the social requests where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching social requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRequest> findByUuid_C(String uuid, long companyId)
		throws SystemException {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social requests where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of social requests
	 * @param end the upper bound of the range of social requests (not inclusive)
	 * @return the range of matching social requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRequest> findByUuid_C(String uuid, long companyId,
		int start, int end) throws SystemException {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social requests where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of social requests
	 * @param end the upper bound of the range of social requests (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRequest> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] { uuid, companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] {
					uuid, companyId,
					
					start, end, orderByComparator
				};
		}

		List<SocialRequest> list = (List<SocialRequest>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SocialRequest socialRequest : list) {
				if (!Validator.equals(uuid, socialRequest.getUuid()) ||
						(companyId != socialRequest.getCompanyId())) {
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
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_C_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_C_UUID_2);
				}
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(SocialRequestModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				list = (List<SocialRequest>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first social request in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social request
	 * @throws com.liferay.portlet.social.NoSuchRequestException if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest findByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (socialRequest != null) {
			return socialRequest;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRequestException(msg.toString());
	}

	/**
	 * Returns the first social request in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social request, or <code>null</code> if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<SocialRequest> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social request in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social request
	 * @throws com.liferay.portlet.social.NoSuchRequestException if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (socialRequest != null) {
			return socialRequest;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRequestException(msg.toString());
	}

	/**
	 * Returns the last social request in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social request, or <code>null</code> if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid_C(uuid, companyId);

		List<SocialRequest> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social requests before and after the current social request in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param requestId the primary key of the current social request
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social request
	 * @throws com.liferay.portlet.social.NoSuchRequestException if a social request with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest[] findByUuid_C_PrevAndNext(long requestId,
		String uuid, long companyId, OrderByComparator orderByComparator)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = findByPrimaryKey(requestId);

		Session session = null;

		try {
			session = openSession();

			SocialRequest[] array = new SocialRequestImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, socialRequest, uuid,
					companyId, orderByComparator, true);

			array[1] = socialRequest;

			array[2] = getByUuid_C_PrevAndNext(session, socialRequest, uuid,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialRequest getByUuid_C_PrevAndNext(Session session,
		SocialRequest socialRequest, String uuid, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1);
		}
		else {
			if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

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
			query.append(SocialRequestModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (uuid != null) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialRequest);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialRequest> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the social requests where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching social requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRequest> findByCompanyId(long companyId)
		throws SystemException {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the social requests where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of social requests
	 * @param end the upper bound of the range of social requests (not inclusive)
	 * @return the range of matching social requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRequest> findByCompanyId(long companyId, int start,
		int end) throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social requests where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of social requests
	 * @param end the upper bound of the range of social requests (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRequest> findByCompanyId(long companyId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId, start, end, orderByComparator };
		}

		List<SocialRequest> list = (List<SocialRequest>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SocialRequest socialRequest : list) {
				if ((companyId != socialRequest.getCompanyId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(SocialRequestModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<SocialRequest>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first social request in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social request
	 * @throws com.liferay.portlet.social.NoSuchRequestException if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (socialRequest != null) {
			return socialRequest;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRequestException(msg.toString());
	}

	/**
	 * Returns the first social request in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social request, or <code>null</code> if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest fetchByCompanyId_First(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<SocialRequest> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social request in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social request
	 * @throws com.liferay.portlet.social.NoSuchRequestException if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (socialRequest != null) {
			return socialRequest;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRequestException(msg.toString());
	}

	/**
	 * Returns the last social request in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social request, or <code>null</code> if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest fetchByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByCompanyId(companyId);

		List<SocialRequest> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social requests before and after the current social request in the ordered set where companyId = &#63;.
	 *
	 * @param requestId the primary key of the current social request
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social request
	 * @throws com.liferay.portlet.social.NoSuchRequestException if a social request with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest[] findByCompanyId_PrevAndNext(long requestId,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = findByPrimaryKey(requestId);

		Session session = null;

		try {
			session = openSession();

			SocialRequest[] array = new SocialRequestImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, socialRequest,
					companyId, orderByComparator, true);

			array[1] = socialRequest;

			array[2] = getByCompanyId_PrevAndNext(session, socialRequest,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialRequest getByCompanyId_PrevAndNext(Session session,
		SocialRequest socialRequest, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
			query.append(SocialRequestModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialRequest);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialRequest> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the social requests where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching social requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRequest> findByUserId(long userId)
		throws SystemException {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social requests where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of social requests
	 * @param end the upper bound of the range of social requests (not inclusive)
	 * @return the range of matching social requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRequest> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social requests where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of social requests
	 * @param end the upper bound of the range of social requests (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRequest> findByUserId(long userId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID;
			finderArgs = new Object[] { userId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID;
			finderArgs = new Object[] { userId, start, end, orderByComparator };
		}

		List<SocialRequest> list = (List<SocialRequest>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SocialRequest socialRequest : list) {
				if ((userId != socialRequest.getUserId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(SocialRequestModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = (List<SocialRequest>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first social request in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social request
	 * @throws com.liferay.portlet.social.NoSuchRequestException if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest findByUserId_First(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = fetchByUserId_First(userId,
				orderByComparator);

		if (socialRequest != null) {
			return socialRequest;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRequestException(msg.toString());
	}

	/**
	 * Returns the first social request in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social request, or <code>null</code> if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest fetchByUserId_First(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		List<SocialRequest> list = findByUserId(userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social request in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social request
	 * @throws com.liferay.portlet.social.NoSuchRequestException if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest findByUserId_Last(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = fetchByUserId_Last(userId,
				orderByComparator);

		if (socialRequest != null) {
			return socialRequest;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRequestException(msg.toString());
	}

	/**
	 * Returns the last social request in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social request, or <code>null</code> if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest fetchByUserId_Last(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUserId(userId);

		List<SocialRequest> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social requests before and after the current social request in the ordered set where userId = &#63;.
	 *
	 * @param requestId the primary key of the current social request
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social request
	 * @throws com.liferay.portlet.social.NoSuchRequestException if a social request with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest[] findByUserId_PrevAndNext(long requestId,
		long userId, OrderByComparator orderByComparator)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = findByPrimaryKey(requestId);

		Session session = null;

		try {
			session = openSession();

			SocialRequest[] array = new SocialRequestImpl[3];

			array[0] = getByUserId_PrevAndNext(session, socialRequest, userId,
					orderByComparator, true);

			array[1] = socialRequest;

			array[2] = getByUserId_PrevAndNext(session, socialRequest, userId,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialRequest getByUserId_PrevAndNext(Session session,
		SocialRequest socialRequest, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

		query.append(_FINDER_COLUMN_USERID_USERID_2);

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
			query.append(SocialRequestModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialRequest);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialRequest> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the social requests where receiverUserId = &#63;.
	 *
	 * @param receiverUserId the receiver user ID
	 * @return the matching social requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRequest> findByReceiverUserId(long receiverUserId)
		throws SystemException {
		return findByReceiverUserId(receiverUserId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social requests where receiverUserId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param receiverUserId the receiver user ID
	 * @param start the lower bound of the range of social requests
	 * @param end the upper bound of the range of social requests (not inclusive)
	 * @return the range of matching social requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRequest> findByReceiverUserId(long receiverUserId,
		int start, int end) throws SystemException {
		return findByReceiverUserId(receiverUserId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social requests where receiverUserId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param receiverUserId the receiver user ID
	 * @param start the lower bound of the range of social requests
	 * @param end the upper bound of the range of social requests (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRequest> findByReceiverUserId(long receiverUserId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RECEIVERUSERID;
			finderArgs = new Object[] { receiverUserId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_RECEIVERUSERID;
			finderArgs = new Object[] {
					receiverUserId,
					
					start, end, orderByComparator
				};
		}

		List<SocialRequest> list = (List<SocialRequest>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SocialRequest socialRequest : list) {
				if ((receiverUserId != socialRequest.getReceiverUserId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

			query.append(_FINDER_COLUMN_RECEIVERUSERID_RECEIVERUSERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(SocialRequestModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(receiverUserId);

				list = (List<SocialRequest>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first social request in the ordered set where receiverUserId = &#63;.
	 *
	 * @param receiverUserId the receiver user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social request
	 * @throws com.liferay.portlet.social.NoSuchRequestException if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest findByReceiverUserId_First(long receiverUserId,
		OrderByComparator orderByComparator)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = fetchByReceiverUserId_First(receiverUserId,
				orderByComparator);

		if (socialRequest != null) {
			return socialRequest;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("receiverUserId=");
		msg.append(receiverUserId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRequestException(msg.toString());
	}

	/**
	 * Returns the first social request in the ordered set where receiverUserId = &#63;.
	 *
	 * @param receiverUserId the receiver user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social request, or <code>null</code> if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest fetchByReceiverUserId_First(long receiverUserId,
		OrderByComparator orderByComparator) throws SystemException {
		List<SocialRequest> list = findByReceiverUserId(receiverUserId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social request in the ordered set where receiverUserId = &#63;.
	 *
	 * @param receiverUserId the receiver user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social request
	 * @throws com.liferay.portlet.social.NoSuchRequestException if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest findByReceiverUserId_Last(long receiverUserId,
		OrderByComparator orderByComparator)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = fetchByReceiverUserId_Last(receiverUserId,
				orderByComparator);

		if (socialRequest != null) {
			return socialRequest;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("receiverUserId=");
		msg.append(receiverUserId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRequestException(msg.toString());
	}

	/**
	 * Returns the last social request in the ordered set where receiverUserId = &#63;.
	 *
	 * @param receiverUserId the receiver user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social request, or <code>null</code> if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest fetchByReceiverUserId_Last(long receiverUserId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByReceiverUserId(receiverUserId);

		List<SocialRequest> list = findByReceiverUserId(receiverUserId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social requests before and after the current social request in the ordered set where receiverUserId = &#63;.
	 *
	 * @param requestId the primary key of the current social request
	 * @param receiverUserId the receiver user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social request
	 * @throws com.liferay.portlet.social.NoSuchRequestException if a social request with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest[] findByReceiverUserId_PrevAndNext(long requestId,
		long receiverUserId, OrderByComparator orderByComparator)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = findByPrimaryKey(requestId);

		Session session = null;

		try {
			session = openSession();

			SocialRequest[] array = new SocialRequestImpl[3];

			array[0] = getByReceiverUserId_PrevAndNext(session, socialRequest,
					receiverUserId, orderByComparator, true);

			array[1] = socialRequest;

			array[2] = getByReceiverUserId_PrevAndNext(session, socialRequest,
					receiverUserId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialRequest getByReceiverUserId_PrevAndNext(Session session,
		SocialRequest socialRequest, long receiverUserId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

		query.append(_FINDER_COLUMN_RECEIVERUSERID_RECEIVERUSERID_2);

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
			query.append(SocialRequestModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(receiverUserId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialRequest);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialRequest> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the social requests where userId = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param status the status
	 * @return the matching social requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRequest> findByU_S(long userId, int status)
		throws SystemException {
		return findByU_S(userId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the social requests where userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of social requests
	 * @param end the upper bound of the range of social requests (not inclusive)
	 * @return the range of matching social requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRequest> findByU_S(long userId, int status, int start,
		int end) throws SystemException {
		return findByU_S(userId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social requests where userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of social requests
	 * @param end the upper bound of the range of social requests (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRequest> findByU_S(long userId, int status, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_S;
			finderArgs = new Object[] { userId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_U_S;
			finderArgs = new Object[] {
					userId, status,
					
					start, end, orderByComparator
				};
		}

		List<SocialRequest> list = (List<SocialRequest>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SocialRequest socialRequest : list) {
				if ((userId != socialRequest.getUserId()) ||
						(status != socialRequest.getStatus())) {
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
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

			query.append(_FINDER_COLUMN_U_S_USERID_2);

			query.append(_FINDER_COLUMN_U_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(SocialRequestModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(status);

				list = (List<SocialRequest>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first social request in the ordered set where userId = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social request
	 * @throws com.liferay.portlet.social.NoSuchRequestException if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest findByU_S_First(long userId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = fetchByU_S_First(userId, status,
				orderByComparator);

		if (socialRequest != null) {
			return socialRequest;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRequestException(msg.toString());
	}

	/**
	 * Returns the first social request in the ordered set where userId = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social request, or <code>null</code> if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest fetchByU_S_First(long userId, int status,
		OrderByComparator orderByComparator) throws SystemException {
		List<SocialRequest> list = findByU_S(userId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social request in the ordered set where userId = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social request
	 * @throws com.liferay.portlet.social.NoSuchRequestException if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest findByU_S_Last(long userId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = fetchByU_S_Last(userId, status,
				orderByComparator);

		if (socialRequest != null) {
			return socialRequest;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRequestException(msg.toString());
	}

	/**
	 * Returns the last social request in the ordered set where userId = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social request, or <code>null</code> if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest fetchByU_S_Last(long userId, int status,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByU_S(userId, status);

		List<SocialRequest> list = findByU_S(userId, status, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social requests before and after the current social request in the ordered set where userId = &#63; and status = &#63;.
	 *
	 * @param requestId the primary key of the current social request
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social request
	 * @throws com.liferay.portlet.social.NoSuchRequestException if a social request with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest[] findByU_S_PrevAndNext(long requestId, long userId,
		int status, OrderByComparator orderByComparator)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = findByPrimaryKey(requestId);

		Session session = null;

		try {
			session = openSession();

			SocialRequest[] array = new SocialRequestImpl[3];

			array[0] = getByU_S_PrevAndNext(session, socialRequest, userId,
					status, orderByComparator, true);

			array[1] = socialRequest;

			array[2] = getByU_S_PrevAndNext(session, socialRequest, userId,
					status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialRequest getByU_S_PrevAndNext(Session session,
		SocialRequest socialRequest, long userId, int status,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

		query.append(_FINDER_COLUMN_U_S_USERID_2);

		query.append(_FINDER_COLUMN_U_S_STATUS_2);

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
			query.append(SocialRequestModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialRequest);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialRequest> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the social requests where receiverUserId = &#63; and status = &#63;.
	 *
	 * @param receiverUserId the receiver user ID
	 * @param status the status
	 * @return the matching social requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRequest> findByR_S(long receiverUserId, int status)
		throws SystemException {
		return findByR_S(receiverUserId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social requests where receiverUserId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param receiverUserId the receiver user ID
	 * @param status the status
	 * @param start the lower bound of the range of social requests
	 * @param end the upper bound of the range of social requests (not inclusive)
	 * @return the range of matching social requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRequest> findByR_S(long receiverUserId, int status,
		int start, int end) throws SystemException {
		return findByR_S(receiverUserId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social requests where receiverUserId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param receiverUserId the receiver user ID
	 * @param status the status
	 * @param start the lower bound of the range of social requests
	 * @param end the upper bound of the range of social requests (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRequest> findByR_S(long receiverUserId, int status,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_S;
			finderArgs = new Object[] { receiverUserId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_R_S;
			finderArgs = new Object[] {
					receiverUserId, status,
					
					start, end, orderByComparator
				};
		}

		List<SocialRequest> list = (List<SocialRequest>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SocialRequest socialRequest : list) {
				if ((receiverUserId != socialRequest.getReceiverUserId()) ||
						(status != socialRequest.getStatus())) {
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
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

			query.append(_FINDER_COLUMN_R_S_RECEIVERUSERID_2);

			query.append(_FINDER_COLUMN_R_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(SocialRequestModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(receiverUserId);

				qPos.add(status);

				list = (List<SocialRequest>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first social request in the ordered set where receiverUserId = &#63; and status = &#63;.
	 *
	 * @param receiverUserId the receiver user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social request
	 * @throws com.liferay.portlet.social.NoSuchRequestException if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest findByR_S_First(long receiverUserId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = fetchByR_S_First(receiverUserId, status,
				orderByComparator);

		if (socialRequest != null) {
			return socialRequest;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("receiverUserId=");
		msg.append(receiverUserId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRequestException(msg.toString());
	}

	/**
	 * Returns the first social request in the ordered set where receiverUserId = &#63; and status = &#63;.
	 *
	 * @param receiverUserId the receiver user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social request, or <code>null</code> if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest fetchByR_S_First(long receiverUserId, int status,
		OrderByComparator orderByComparator) throws SystemException {
		List<SocialRequest> list = findByR_S(receiverUserId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social request in the ordered set where receiverUserId = &#63; and status = &#63;.
	 *
	 * @param receiverUserId the receiver user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social request
	 * @throws com.liferay.portlet.social.NoSuchRequestException if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest findByR_S_Last(long receiverUserId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = fetchByR_S_Last(receiverUserId, status,
				orderByComparator);

		if (socialRequest != null) {
			return socialRequest;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("receiverUserId=");
		msg.append(receiverUserId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRequestException(msg.toString());
	}

	/**
	 * Returns the last social request in the ordered set where receiverUserId = &#63; and status = &#63;.
	 *
	 * @param receiverUserId the receiver user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social request, or <code>null</code> if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest fetchByR_S_Last(long receiverUserId, int status,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByR_S(receiverUserId, status);

		List<SocialRequest> list = findByR_S(receiverUserId, status, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social requests before and after the current social request in the ordered set where receiverUserId = &#63; and status = &#63;.
	 *
	 * @param requestId the primary key of the current social request
	 * @param receiverUserId the receiver user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social request
	 * @throws com.liferay.portlet.social.NoSuchRequestException if a social request with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest[] findByR_S_PrevAndNext(long requestId,
		long receiverUserId, int status, OrderByComparator orderByComparator)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = findByPrimaryKey(requestId);

		Session session = null;

		try {
			session = openSession();

			SocialRequest[] array = new SocialRequestImpl[3];

			array[0] = getByR_S_PrevAndNext(session, socialRequest,
					receiverUserId, status, orderByComparator, true);

			array[1] = socialRequest;

			array[2] = getByR_S_PrevAndNext(session, socialRequest,
					receiverUserId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialRequest getByR_S_PrevAndNext(Session session,
		SocialRequest socialRequest, long receiverUserId, int status,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

		query.append(_FINDER_COLUMN_R_S_RECEIVERUSERID_2);

		query.append(_FINDER_COLUMN_R_S_STATUS_2);

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
			query.append(SocialRequestModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(receiverUserId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialRequest);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialRequest> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the social request where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; or throws a {@link com.liferay.portlet.social.NoSuchRequestException} if it could not be found.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param receiverUserId the receiver user ID
	 * @return the matching social request
	 * @throws com.liferay.portlet.social.NoSuchRequestException if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest findByU_C_C_T_R(long userId, long classNameId,
		long classPK, int type, long receiverUserId)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = fetchByU_C_C_T_R(userId, classNameId,
				classPK, type, receiverUserId);

		if (socialRequest == null) {
			StringBundler msg = new StringBundler(12);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(", type=");
			msg.append(type);

			msg.append(", receiverUserId=");
			msg.append(receiverUserId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchRequestException(msg.toString());
		}

		return socialRequest;
	}

	/**
	 * Returns the social request where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param receiverUserId the receiver user ID
	 * @return the matching social request, or <code>null</code> if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest fetchByU_C_C_T_R(long userId, long classNameId,
		long classPK, int type, long receiverUserId) throws SystemException {
		return fetchByU_C_C_T_R(userId, classNameId, classPK, type,
			receiverUserId, true);
	}

	/**
	 * Returns the social request where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param receiverUserId the receiver user ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching social request, or <code>null</code> if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest fetchByU_C_C_T_R(long userId, long classNameId,
		long classPK, int type, long receiverUserId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				userId, classNameId, classPK, type, receiverUserId
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_U_C_C_T_R,
					finderArgs, this);
		}

		if (result instanceof SocialRequest) {
			SocialRequest socialRequest = (SocialRequest)result;

			if ((userId != socialRequest.getUserId()) ||
					(classNameId != socialRequest.getClassNameId()) ||
					(classPK != socialRequest.getClassPK()) ||
					(type != socialRequest.getType()) ||
					(receiverUserId != socialRequest.getReceiverUserId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(7);

			query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

			query.append(_FINDER_COLUMN_U_C_C_T_R_USERID_2);

			query.append(_FINDER_COLUMN_U_C_C_T_R_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_U_C_C_T_R_CLASSPK_2);

			query.append(_FINDER_COLUMN_U_C_C_T_R_TYPE_2);

			query.append(_FINDER_COLUMN_U_C_C_T_R_RECEIVERUSERID_2);

			query.append(SocialRequestModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(type);

				qPos.add(receiverUserId);

				List<SocialRequest> list = q.list();

				result = list;

				SocialRequest socialRequest = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_C_C_T_R,
						finderArgs, list);
				}
				else {
					socialRequest = list.get(0);

					cacheResult(socialRequest);

					if ((socialRequest.getUserId() != userId) ||
							(socialRequest.getClassNameId() != classNameId) ||
							(socialRequest.getClassPK() != classPK) ||
							(socialRequest.getType() != type) ||
							(socialRequest.getReceiverUserId() != receiverUserId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_C_C_T_R,
							finderArgs, socialRequest);
					}
				}

				return socialRequest;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_C_C_T_R,
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
				return (SocialRequest)result;
			}
		}
	}

	/**
	 * Returns all the social requests where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param status the status
	 * @return the matching social requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRequest> findByU_C_C_T_S(long userId, long classNameId,
		long classPK, int type, int status) throws SystemException {
		return findByU_C_C_T_S(userId, classNameId, classPK, type, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social requests where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of social requests
	 * @param end the upper bound of the range of social requests (not inclusive)
	 * @return the range of matching social requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRequest> findByU_C_C_T_S(long userId, long classNameId,
		long classPK, int type, int status, int start, int end)
		throws SystemException {
		return findByU_C_C_T_S(userId, classNameId, classPK, type, status,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the social requests where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of social requests
	 * @param end the upper bound of the range of social requests (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRequest> findByU_C_C_T_S(long userId, long classNameId,
		long classPK, int type, int status, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_C_C_T_S;
			finderArgs = new Object[] { userId, classNameId, classPK, type, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_U_C_C_T_S;
			finderArgs = new Object[] {
					userId, classNameId, classPK, type, status,
					
					start, end, orderByComparator
				};
		}

		List<SocialRequest> list = (List<SocialRequest>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SocialRequest socialRequest : list) {
				if ((userId != socialRequest.getUserId()) ||
						(classNameId != socialRequest.getClassNameId()) ||
						(classPK != socialRequest.getClassPK()) ||
						(type != socialRequest.getType()) ||
						(status != socialRequest.getStatus())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(7 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(7);
			}

			query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

			query.append(_FINDER_COLUMN_U_C_C_T_S_USERID_2);

			query.append(_FINDER_COLUMN_U_C_C_T_S_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_U_C_C_T_S_CLASSPK_2);

			query.append(_FINDER_COLUMN_U_C_C_T_S_TYPE_2);

			query.append(_FINDER_COLUMN_U_C_C_T_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(SocialRequestModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(type);

				qPos.add(status);

				list = (List<SocialRequest>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first social request in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social request
	 * @throws com.liferay.portlet.social.NoSuchRequestException if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest findByU_C_C_T_S_First(long userId, long classNameId,
		long classPK, int type, int status, OrderByComparator orderByComparator)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = fetchByU_C_C_T_S_First(userId,
				classNameId, classPK, type, status, orderByComparator);

		if (socialRequest != null) {
			return socialRequest;
		}

		StringBundler msg = new StringBundler(12);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", type=");
		msg.append(type);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRequestException(msg.toString());
	}

	/**
	 * Returns the first social request in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social request, or <code>null</code> if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest fetchByU_C_C_T_S_First(long userId, long classNameId,
		long classPK, int type, int status, OrderByComparator orderByComparator)
		throws SystemException {
		List<SocialRequest> list = findByU_C_C_T_S(userId, classNameId,
				classPK, type, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social request in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social request
	 * @throws com.liferay.portlet.social.NoSuchRequestException if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest findByU_C_C_T_S_Last(long userId, long classNameId,
		long classPK, int type, int status, OrderByComparator orderByComparator)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = fetchByU_C_C_T_S_Last(userId,
				classNameId, classPK, type, status, orderByComparator);

		if (socialRequest != null) {
			return socialRequest;
		}

		StringBundler msg = new StringBundler(12);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", type=");
		msg.append(type);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRequestException(msg.toString());
	}

	/**
	 * Returns the last social request in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social request, or <code>null</code> if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest fetchByU_C_C_T_S_Last(long userId, long classNameId,
		long classPK, int type, int status, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByU_C_C_T_S(userId, classNameId, classPK, type, status);

		List<SocialRequest> list = findByU_C_C_T_S(userId, classNameId,
				classPK, type, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social requests before and after the current social request in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param requestId the primary key of the current social request
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social request
	 * @throws com.liferay.portlet.social.NoSuchRequestException if a social request with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest[] findByU_C_C_T_S_PrevAndNext(long requestId,
		long userId, long classNameId, long classPK, int type, int status,
		OrderByComparator orderByComparator)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = findByPrimaryKey(requestId);

		Session session = null;

		try {
			session = openSession();

			SocialRequest[] array = new SocialRequestImpl[3];

			array[0] = getByU_C_C_T_S_PrevAndNext(session, socialRequest,
					userId, classNameId, classPK, type, status,
					orderByComparator, true);

			array[1] = socialRequest;

			array[2] = getByU_C_C_T_S_PrevAndNext(session, socialRequest,
					userId, classNameId, classPK, type, status,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialRequest getByU_C_C_T_S_PrevAndNext(Session session,
		SocialRequest socialRequest, long userId, long classNameId,
		long classPK, int type, int status,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

		query.append(_FINDER_COLUMN_U_C_C_T_S_USERID_2);

		query.append(_FINDER_COLUMN_U_C_C_T_S_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_U_C_C_T_S_CLASSPK_2);

		query.append(_FINDER_COLUMN_U_C_C_T_S_TYPE_2);

		query.append(_FINDER_COLUMN_U_C_C_T_S_STATUS_2);

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
			query.append(SocialRequestModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		qPos.add(classNameId);

		qPos.add(classPK);

		qPos.add(type);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialRequest);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialRequest> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the social requests where classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param receiverUserId the receiver user ID
	 * @param status the status
	 * @return the matching social requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRequest> findByC_C_T_R_S(long classNameId, long classPK,
		int type, long receiverUserId, int status) throws SystemException {
		return findByC_C_T_R_S(classNameId, classPK, type, receiverUserId,
			status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social requests where classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param receiverUserId the receiver user ID
	 * @param status the status
	 * @param start the lower bound of the range of social requests
	 * @param end the upper bound of the range of social requests (not inclusive)
	 * @return the range of matching social requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRequest> findByC_C_T_R_S(long classNameId, long classPK,
		int type, long receiverUserId, int status, int start, int end)
		throws SystemException {
		return findByC_C_T_R_S(classNameId, classPK, type, receiverUserId,
			status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social requests where classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param receiverUserId the receiver user ID
	 * @param status the status
	 * @param start the lower bound of the range of social requests
	 * @param end the upper bound of the range of social requests (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRequest> findByC_C_T_R_S(long classNameId, long classPK,
		int type, long receiverUserId, int status, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_T_R_S;
			finderArgs = new Object[] {
					classNameId, classPK, type, receiverUserId, status
				};
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C_T_R_S;
			finderArgs = new Object[] {
					classNameId, classPK, type, receiverUserId, status,
					
					start, end, orderByComparator
				};
		}

		List<SocialRequest> list = (List<SocialRequest>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SocialRequest socialRequest : list) {
				if ((classNameId != socialRequest.getClassNameId()) ||
						(classPK != socialRequest.getClassPK()) ||
						(type != socialRequest.getType()) ||
						(receiverUserId != socialRequest.getReceiverUserId()) ||
						(status != socialRequest.getStatus())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(7 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(7);
			}

			query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

			query.append(_FINDER_COLUMN_C_C_T_R_S_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_T_R_S_CLASSPK_2);

			query.append(_FINDER_COLUMN_C_C_T_R_S_TYPE_2);

			query.append(_FINDER_COLUMN_C_C_T_R_S_RECEIVERUSERID_2);

			query.append(_FINDER_COLUMN_C_C_T_R_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(SocialRequestModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(type);

				qPos.add(receiverUserId);

				qPos.add(status);

				list = (List<SocialRequest>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first social request in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param receiverUserId the receiver user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social request
	 * @throws com.liferay.portlet.social.NoSuchRequestException if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest findByC_C_T_R_S_First(long classNameId, long classPK,
		int type, long receiverUserId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = fetchByC_C_T_R_S_First(classNameId,
				classPK, type, receiverUserId, status, orderByComparator);

		if (socialRequest != null) {
			return socialRequest;
		}

		StringBundler msg = new StringBundler(12);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", type=");
		msg.append(type);

		msg.append(", receiverUserId=");
		msg.append(receiverUserId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRequestException(msg.toString());
	}

	/**
	 * Returns the first social request in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param receiverUserId the receiver user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social request, or <code>null</code> if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest fetchByC_C_T_R_S_First(long classNameId, long classPK,
		int type, long receiverUserId, int status,
		OrderByComparator orderByComparator) throws SystemException {
		List<SocialRequest> list = findByC_C_T_R_S(classNameId, classPK, type,
				receiverUserId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social request in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param receiverUserId the receiver user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social request
	 * @throws com.liferay.portlet.social.NoSuchRequestException if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest findByC_C_T_R_S_Last(long classNameId, long classPK,
		int type, long receiverUserId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = fetchByC_C_T_R_S_Last(classNameId,
				classPK, type, receiverUserId, status, orderByComparator);

		if (socialRequest != null) {
			return socialRequest;
		}

		StringBundler msg = new StringBundler(12);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", type=");
		msg.append(type);

		msg.append(", receiverUserId=");
		msg.append(receiverUserId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRequestException(msg.toString());
	}

	/**
	 * Returns the last social request in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param receiverUserId the receiver user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social request, or <code>null</code> if a matching social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest fetchByC_C_T_R_S_Last(long classNameId, long classPK,
		int type, long receiverUserId, int status,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByC_C_T_R_S(classNameId, classPK, type,
				receiverUserId, status);

		List<SocialRequest> list = findByC_C_T_R_S(classNameId, classPK, type,
				receiverUserId, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social requests before and after the current social request in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; and status = &#63;.
	 *
	 * @param requestId the primary key of the current social request
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param receiverUserId the receiver user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social request
	 * @throws com.liferay.portlet.social.NoSuchRequestException if a social request with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest[] findByC_C_T_R_S_PrevAndNext(long requestId,
		long classNameId, long classPK, int type, long receiverUserId,
		int status, OrderByComparator orderByComparator)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = findByPrimaryKey(requestId);

		Session session = null;

		try {
			session = openSession();

			SocialRequest[] array = new SocialRequestImpl[3];

			array[0] = getByC_C_T_R_S_PrevAndNext(session, socialRequest,
					classNameId, classPK, type, receiverUserId, status,
					orderByComparator, true);

			array[1] = socialRequest;

			array[2] = getByC_C_T_R_S_PrevAndNext(session, socialRequest,
					classNameId, classPK, type, receiverUserId, status,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialRequest getByC_C_T_R_S_PrevAndNext(Session session,
		SocialRequest socialRequest, long classNameId, long classPK, int type,
		long receiverUserId, int status, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALREQUEST_WHERE);

		query.append(_FINDER_COLUMN_C_C_T_R_S_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_T_R_S_CLASSPK_2);

		query.append(_FINDER_COLUMN_C_C_T_R_S_TYPE_2);

		query.append(_FINDER_COLUMN_C_C_T_R_S_RECEIVERUSERID_2);

		query.append(_FINDER_COLUMN_C_C_T_R_S_STATUS_2);

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
			query.append(SocialRequestModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		qPos.add(classPK);

		qPos.add(type);

		qPos.add(receiverUserId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialRequest);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialRequest> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the social requests.
	 *
	 * @return the social requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRequest> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social requests.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of social requests
	 * @param end the upper bound of the range of social requests (not inclusive)
	 * @return the range of social requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRequest> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the social requests.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of social requests
	 * @param end the upper bound of the range of social requests (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of social requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRequest> findAll(int start, int end,
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

		List<SocialRequest> list = (List<SocialRequest>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SOCIALREQUEST);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SOCIALREQUEST.concat(SocialRequestModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<SocialRequest>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<SocialRequest>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the social requests where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (SocialRequest socialRequest : findByUuid(uuid)) {
			remove(socialRequest);
		}
	}

	/**
	 * Removes the social request where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the social request that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest removeByUUID_G(String uuid, long groupId)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = findByUUID_G(uuid, groupId);

		return remove(socialRequest);
	}

	/**
	 * Removes all the social requests where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid_C(String uuid, long companyId)
		throws SystemException {
		for (SocialRequest socialRequest : findByUuid_C(uuid, companyId)) {
			remove(socialRequest);
		}
	}

	/**
	 * Removes all the social requests where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCompanyId(long companyId) throws SystemException {
		for (SocialRequest socialRequest : findByCompanyId(companyId)) {
			remove(socialRequest);
		}
	}

	/**
	 * Removes all the social requests where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUserId(long userId) throws SystemException {
		for (SocialRequest socialRequest : findByUserId(userId)) {
			remove(socialRequest);
		}
	}

	/**
	 * Removes all the social requests where receiverUserId = &#63; from the database.
	 *
	 * @param receiverUserId the receiver user ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByReceiverUserId(long receiverUserId)
		throws SystemException {
		for (SocialRequest socialRequest : findByReceiverUserId(receiverUserId)) {
			remove(socialRequest);
		}
	}

	/**
	 * Removes all the social requests where userId = &#63; and status = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param status the status
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByU_S(long userId, int status) throws SystemException {
		for (SocialRequest socialRequest : findByU_S(userId, status)) {
			remove(socialRequest);
		}
	}

	/**
	 * Removes all the social requests where receiverUserId = &#63; and status = &#63; from the database.
	 *
	 * @param receiverUserId the receiver user ID
	 * @param status the status
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByR_S(long receiverUserId, int status)
		throws SystemException {
		for (SocialRequest socialRequest : findByR_S(receiverUserId, status)) {
			remove(socialRequest);
		}
	}

	/**
	 * Removes the social request where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param receiverUserId the receiver user ID
	 * @return the social request that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest removeByU_C_C_T_R(long userId, long classNameId,
		long classPK, int type, long receiverUserId)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = findByU_C_C_T_R(userId, classNameId,
				classPK, type, receiverUserId);

		return remove(socialRequest);
	}

	/**
	 * Removes all the social requests where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param status the status
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByU_C_C_T_S(long userId, long classNameId, long classPK,
		int type, int status) throws SystemException {
		for (SocialRequest socialRequest : findByU_C_C_T_S(userId, classNameId,
				classPK, type, status)) {
			remove(socialRequest);
		}
	}

	/**
	 * Removes all the social requests where classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; and status = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param receiverUserId the receiver user ID
	 * @param status the status
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_C_T_R_S(long classNameId, long classPK, int type,
		long receiverUserId, int status) throws SystemException {
		for (SocialRequest socialRequest : findByC_C_T_R_S(classNameId,
				classPK, type, receiverUserId, status)) {
			remove(socialRequest);
		}
	}

	/**
	 * Removes all the social requests from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (SocialRequest socialRequest : findAll()) {
			remove(socialRequest);
		}
	}

	/**
	 * Returns the number of social requests where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching social requests
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SOCIALREQUEST_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_UUID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social requests where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching social requests
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SOCIALREQUEST_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_G_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_G_UUID_2);
				}
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID_G,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social requests where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching social requests
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid_C(String uuid, long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SOCIALREQUEST_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_C_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_C_UUID_2);
				}
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID_C,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social requests where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching social requests
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SOCIALREQUEST_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_COMPANYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social requests where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching social requests
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SOCIALREQUEST_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social requests where receiverUserId = &#63;.
	 *
	 * @param receiverUserId the receiver user ID
	 * @return the number of matching social requests
	 * @throws SystemException if a system exception occurred
	 */
	public int countByReceiverUserId(long receiverUserId)
		throws SystemException {
		Object[] finderArgs = new Object[] { receiverUserId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_RECEIVERUSERID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SOCIALREQUEST_WHERE);

			query.append(_FINDER_COLUMN_RECEIVERUSERID_RECEIVERUSERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(receiverUserId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_RECEIVERUSERID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social requests where userId = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param status the status
	 * @return the number of matching social requests
	 * @throws SystemException if a system exception occurred
	 */
	public int countByU_S(long userId, int status) throws SystemException {
		Object[] finderArgs = new Object[] { userId, status };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SOCIALREQUEST_WHERE);

			query.append(_FINDER_COLUMN_U_S_USERID_2);

			query.append(_FINDER_COLUMN_U_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(status);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_S, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social requests where receiverUserId = &#63; and status = &#63;.
	 *
	 * @param receiverUserId the receiver user ID
	 * @param status the status
	 * @return the number of matching social requests
	 * @throws SystemException if a system exception occurred
	 */
	public int countByR_S(long receiverUserId, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] { receiverUserId, status };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_R_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SOCIALREQUEST_WHERE);

			query.append(_FINDER_COLUMN_R_S_RECEIVERUSERID_2);

			query.append(_FINDER_COLUMN_R_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(receiverUserId);

				qPos.add(status);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_R_S, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social requests where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param receiverUserId the receiver user ID
	 * @return the number of matching social requests
	 * @throws SystemException if a system exception occurred
	 */
	public int countByU_C_C_T_R(long userId, long classNameId, long classPK,
		int type, long receiverUserId) throws SystemException {
		Object[] finderArgs = new Object[] {
				userId, classNameId, classPK, type, receiverUserId
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_C_C_T_R,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_COUNT_SOCIALREQUEST_WHERE);

			query.append(_FINDER_COLUMN_U_C_C_T_R_USERID_2);

			query.append(_FINDER_COLUMN_U_C_C_T_R_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_U_C_C_T_R_CLASSPK_2);

			query.append(_FINDER_COLUMN_U_C_C_T_R_TYPE_2);

			query.append(_FINDER_COLUMN_U_C_C_T_R_RECEIVERUSERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(type);

				qPos.add(receiverUserId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_C_C_T_R,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social requests where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param status the status
	 * @return the number of matching social requests
	 * @throws SystemException if a system exception occurred
	 */
	public int countByU_C_C_T_S(long userId, long classNameId, long classPK,
		int type, int status) throws SystemException {
		Object[] finderArgs = new Object[] {
				userId, classNameId, classPK, type, status
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_C_C_T_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_COUNT_SOCIALREQUEST_WHERE);

			query.append(_FINDER_COLUMN_U_C_C_T_S_USERID_2);

			query.append(_FINDER_COLUMN_U_C_C_T_S_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_U_C_C_T_S_CLASSPK_2);

			query.append(_FINDER_COLUMN_U_C_C_T_S_TYPE_2);

			query.append(_FINDER_COLUMN_U_C_C_T_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(type);

				qPos.add(status);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_C_C_T_S,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social requests where classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param receiverUserId the receiver user ID
	 * @param status the status
	 * @return the number of matching social requests
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_C_T_R_S(long classNameId, long classPK, int type,
		long receiverUserId, int status) throws SystemException {
		Object[] finderArgs = new Object[] {
				classNameId, classPK, type, receiverUserId, status
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C_T_R_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_COUNT_SOCIALREQUEST_WHERE);

			query.append(_FINDER_COLUMN_C_C_T_R_S_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_T_R_S_CLASSPK_2);

			query.append(_FINDER_COLUMN_C_C_T_R_S_TYPE_2);

			query.append(_FINDER_COLUMN_C_C_T_R_S_RECEIVERUSERID_2);

			query.append(_FINDER_COLUMN_C_C_T_R_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(type);

				qPos.add(receiverUserId);

				qPos.add(status);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C_T_R_S,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social requests.
	 *
	 * @return the number of social requests
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SOCIALREQUEST);

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
	 * Initializes the social request persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.social.model.SocialRequest")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SocialRequest>> listenersList = new ArrayList<ModelListener<SocialRequest>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SocialRequest>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(SocialRequestImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = SocialActivityPersistence.class)
	protected SocialActivityPersistence socialActivityPersistence;
	@BeanReference(type = SocialActivityAchievementPersistence.class)
	protected SocialActivityAchievementPersistence socialActivityAchievementPersistence;
	@BeanReference(type = SocialActivityCounterPersistence.class)
	protected SocialActivityCounterPersistence socialActivityCounterPersistence;
	@BeanReference(type = SocialActivityLimitPersistence.class)
	protected SocialActivityLimitPersistence socialActivityLimitPersistence;
	@BeanReference(type = SocialActivitySettingPersistence.class)
	protected SocialActivitySettingPersistence socialActivitySettingPersistence;
	@BeanReference(type = SocialRelationPersistence.class)
	protected SocialRelationPersistence socialRelationPersistence;
	@BeanReference(type = SocialRequestPersistence.class)
	protected SocialRequestPersistence socialRequestPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_SOCIALREQUEST = "SELECT socialRequest FROM SocialRequest socialRequest";
	private static final String _SQL_SELECT_SOCIALREQUEST_WHERE = "SELECT socialRequest FROM SocialRequest socialRequest WHERE ";
	private static final String _SQL_COUNT_SOCIALREQUEST = "SELECT COUNT(socialRequest) FROM SocialRequest socialRequest";
	private static final String _SQL_COUNT_SOCIALREQUEST_WHERE = "SELECT COUNT(socialRequest) FROM SocialRequest socialRequest WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "socialRequest.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "socialRequest.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(socialRequest.uuid IS NULL OR socialRequest.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "socialRequest.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "socialRequest.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(socialRequest.uuid IS NULL OR socialRequest.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "socialRequest.groupId = ?";
	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "socialRequest.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "socialRequest.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(socialRequest.uuid IS NULL OR socialRequest.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "socialRequest.companyId = ?";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "socialRequest.companyId = ?";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "socialRequest.userId = ?";
	private static final String _FINDER_COLUMN_RECEIVERUSERID_RECEIVERUSERID_2 = "socialRequest.receiverUserId = ?";
	private static final String _FINDER_COLUMN_U_S_USERID_2 = "socialRequest.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_S_STATUS_2 = "socialRequest.status = ?";
	private static final String _FINDER_COLUMN_R_S_RECEIVERUSERID_2 = "socialRequest.receiverUserId = ? AND ";
	private static final String _FINDER_COLUMN_R_S_STATUS_2 = "socialRequest.status = ?";
	private static final String _FINDER_COLUMN_U_C_C_T_R_USERID_2 = "socialRequest.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_C_C_T_R_CLASSNAMEID_2 = "socialRequest.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_U_C_C_T_R_CLASSPK_2 = "socialRequest.classPK = ? AND ";
	private static final String _FINDER_COLUMN_U_C_C_T_R_TYPE_2 = "socialRequest.type = ? AND ";
	private static final String _FINDER_COLUMN_U_C_C_T_R_RECEIVERUSERID_2 = "socialRequest.receiverUserId = ?";
	private static final String _FINDER_COLUMN_U_C_C_T_S_USERID_2 = "socialRequest.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_C_C_T_S_CLASSNAMEID_2 = "socialRequest.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_U_C_C_T_S_CLASSPK_2 = "socialRequest.classPK = ? AND ";
	private static final String _FINDER_COLUMN_U_C_C_T_S_TYPE_2 = "socialRequest.type = ? AND ";
	private static final String _FINDER_COLUMN_U_C_C_T_S_STATUS_2 = "socialRequest.status = ?";
	private static final String _FINDER_COLUMN_C_C_T_R_S_CLASSNAMEID_2 = "socialRequest.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_T_R_S_CLASSPK_2 = "socialRequest.classPK = ? AND ";
	private static final String _FINDER_COLUMN_C_C_T_R_S_TYPE_2 = "socialRequest.type = ? AND ";
	private static final String _FINDER_COLUMN_C_C_T_R_S_RECEIVERUSERID_2 = "socialRequest.receiverUserId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_T_R_S_STATUS_2 = "socialRequest.status = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "socialRequest.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SocialRequest exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SocialRequest exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(SocialRequestPersistenceImpl.class);
	private static SocialRequest _nullSocialRequest = new SocialRequestImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<SocialRequest> toCacheModel() {
				return _nullSocialRequestCacheModel;
			}
		};

	private static CacheModel<SocialRequest> _nullSocialRequestCacheModel = new CacheModel<SocialRequest>() {
			public SocialRequest toEntityModel() {
				return _nullSocialRequest;
			}
		};
}