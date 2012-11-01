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

import com.liferay.portlet.social.NoSuchRelationException;
import com.liferay.portlet.social.model.SocialRelation;
import com.liferay.portlet.social.model.impl.SocialRelationImpl;
import com.liferay.portlet.social.model.impl.SocialRelationModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the social relation service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialRelationPersistence
 * @see SocialRelationUtil
 * @generated
 */
public class SocialRelationPersistenceImpl extends BasePersistenceImpl<SocialRelation>
	implements SocialRelationPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SocialRelationUtil} to access the social relation persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SocialRelationImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			SocialRelationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			SocialRelationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			SocialRelationModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			SocialRelationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			SocialRelationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			SocialRelationModelImpl.UUID_COLUMN_BITMASK |
			SocialRelationModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			SocialRelationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			SocialRelationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			SocialRelationModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID1 = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			SocialRelationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUserId1",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID1 =
		new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			SocialRelationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId1",
			new String[] { Long.class.getName() },
			SocialRelationModelImpl.USERID1_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID1 = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId1",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID2 = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			SocialRelationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUserId2",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID2 =
		new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			SocialRelationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId2",
			new String[] { Long.class.getName() },
			SocialRelationModelImpl.USERID2_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID2 = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId2",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_TYPE = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			SocialRelationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByType",
			new String[] {
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TYPE = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			SocialRelationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByType",
			new String[] { Integer.class.getName() },
			SocialRelationModelImpl.TYPE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_TYPE = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByType",
			new String[] { Integer.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_T = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			SocialRelationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_T = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			SocialRelationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_T",
			new String[] { Long.class.getName(), Integer.class.getName() },
			SocialRelationModelImpl.COMPANYID_COLUMN_BITMASK |
			SocialRelationModelImpl.TYPE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_T = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_T",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_U1_U2 = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			SocialRelationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByU1_U2",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U1_U2 = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			SocialRelationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU1_U2",
			new String[] { Long.class.getName(), Long.class.getName() },
			SocialRelationModelImpl.USERID1_COLUMN_BITMASK |
			SocialRelationModelImpl.USERID2_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U1_U2 = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU1_U2",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_U1_T = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			SocialRelationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByU1_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U1_T = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			SocialRelationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU1_T",
			new String[] { Long.class.getName(), Integer.class.getName() },
			SocialRelationModelImpl.USERID1_COLUMN_BITMASK |
			SocialRelationModelImpl.TYPE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U1_T = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU1_T",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_U2_T = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			SocialRelationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByU2_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U2_T = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			SocialRelationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU2_T",
			new String[] { Long.class.getName(), Integer.class.getName() },
			SocialRelationModelImpl.USERID2_COLUMN_BITMASK |
			SocialRelationModelImpl.TYPE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U2_T = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU2_T",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_U1_U2_T = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			SocialRelationImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByU1_U2_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			SocialRelationModelImpl.USERID1_COLUMN_BITMASK |
			SocialRelationModelImpl.USERID2_COLUMN_BITMASK |
			SocialRelationModelImpl.TYPE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U1_U2_T = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU1_U2_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			SocialRelationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			SocialRelationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the social relation in the entity cache if it is enabled.
	 *
	 * @param socialRelation the social relation
	 */
	public void cacheResult(SocialRelation socialRelation) {
		EntityCacheUtil.putResult(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationImpl.class, socialRelation.getPrimaryKey(),
			socialRelation);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U1_U2_T,
			new Object[] {
				Long.valueOf(socialRelation.getUserId1()),
				Long.valueOf(socialRelation.getUserId2()),
				Integer.valueOf(socialRelation.getType())
			}, socialRelation);

		socialRelation.resetOriginalValues();
	}

	/**
	 * Caches the social relations in the entity cache if it is enabled.
	 *
	 * @param socialRelations the social relations
	 */
	public void cacheResult(List<SocialRelation> socialRelations) {
		for (SocialRelation socialRelation : socialRelations) {
			if (EntityCacheUtil.getResult(
						SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
						SocialRelationImpl.class, socialRelation.getPrimaryKey()) == null) {
				cacheResult(socialRelation);
			}
			else {
				socialRelation.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all social relations.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(SocialRelationImpl.class.getName());
		}

		EntityCacheUtil.clearCache(SocialRelationImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the social relation.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SocialRelation socialRelation) {
		EntityCacheUtil.removeResult(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationImpl.class, socialRelation.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(socialRelation);
	}

	@Override
	public void clearCache(List<SocialRelation> socialRelations) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SocialRelation socialRelation : socialRelations) {
			EntityCacheUtil.removeResult(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
				SocialRelationImpl.class, socialRelation.getPrimaryKey());

			clearUniqueFindersCache(socialRelation);
		}
	}

	protected void clearUniqueFindersCache(SocialRelation socialRelation) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U1_U2_T,
			new Object[] {
				Long.valueOf(socialRelation.getUserId1()),
				Long.valueOf(socialRelation.getUserId2()),
				Integer.valueOf(socialRelation.getType())
			});
	}

	/**
	 * Creates a new social relation with the primary key. Does not add the social relation to the database.
	 *
	 * @param relationId the primary key for the new social relation
	 * @return the new social relation
	 */
	public SocialRelation create(long relationId) {
		SocialRelation socialRelation = new SocialRelationImpl();

		socialRelation.setNew(true);
		socialRelation.setPrimaryKey(relationId);

		String uuid = PortalUUIDUtil.generate();

		socialRelation.setUuid(uuid);

		return socialRelation;
	}

	/**
	 * Removes the social relation with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param relationId the primary key of the social relation
	 * @return the social relation that was removed
	 * @throws com.liferay.portlet.social.NoSuchRelationException if a social relation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation remove(long relationId)
		throws NoSuchRelationException, SystemException {
		return remove(Long.valueOf(relationId));
	}

	/**
	 * Removes the social relation with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the social relation
	 * @return the social relation that was removed
	 * @throws com.liferay.portlet.social.NoSuchRelationException if a social relation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialRelation remove(Serializable primaryKey)
		throws NoSuchRelationException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SocialRelation socialRelation = (SocialRelation)session.get(SocialRelationImpl.class,
					primaryKey);

			if (socialRelation == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRelationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(socialRelation);
		}
		catch (NoSuchRelationException nsee) {
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
	protected SocialRelation removeImpl(SocialRelation socialRelation)
		throws SystemException {
		socialRelation = toUnwrappedModel(socialRelation);

		Session session = null;

		try {
			session = openSession();

			if (socialRelation.isCachedModel()) {
				socialRelation = (SocialRelation)session.get(SocialRelationImpl.class,
						socialRelation.getPrimaryKeyObj());
			}

			if (socialRelation != null) {
				session.delete(socialRelation);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (socialRelation != null) {
			clearCache(socialRelation);
		}

		return socialRelation;
	}

	@Override
	public SocialRelation updateImpl(
		com.liferay.portlet.social.model.SocialRelation socialRelation)
		throws SystemException {
		socialRelation = toUnwrappedModel(socialRelation);

		boolean isNew = socialRelation.isNew();

		SocialRelationModelImpl socialRelationModelImpl = (SocialRelationModelImpl)socialRelation;

		if (Validator.isNull(socialRelation.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			socialRelation.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (socialRelation.isNew()) {
				session.save(socialRelation);

				socialRelation.setNew(false);
			}
			else {
				session.merge(socialRelation);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !SocialRelationModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((socialRelationModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						socialRelationModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { socialRelationModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((socialRelationModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						socialRelationModelImpl.getOriginalUuid(),
						Long.valueOf(socialRelationModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						socialRelationModelImpl.getUuid(),
						Long.valueOf(socialRelationModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((socialRelationModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(socialRelationModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						Long.valueOf(socialRelationModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((socialRelationModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID1.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(socialRelationModelImpl.getOriginalUserId1())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID1, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID1,
					args);

				args = new Object[] {
						Long.valueOf(socialRelationModelImpl.getUserId1())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID1, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID1,
					args);
			}

			if ((socialRelationModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID2.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(socialRelationModelImpl.getOriginalUserId2())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID2, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID2,
					args);

				args = new Object[] {
						Long.valueOf(socialRelationModelImpl.getUserId2())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID2, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID2,
					args);
			}

			if ((socialRelationModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TYPE.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Integer.valueOf(socialRelationModelImpl.getOriginalType())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_TYPE, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TYPE,
					args);

				args = new Object[] {
						Integer.valueOf(socialRelationModelImpl.getType())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_TYPE, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TYPE,
					args);
			}

			if ((socialRelationModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(socialRelationModelImpl.getOriginalCompanyId()),
						Integer.valueOf(socialRelationModelImpl.getOriginalType())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_T, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_T,
					args);

				args = new Object[] {
						Long.valueOf(socialRelationModelImpl.getCompanyId()),
						Integer.valueOf(socialRelationModelImpl.getType())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_T, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_T,
					args);
			}

			if ((socialRelationModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U1_U2.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(socialRelationModelImpl.getOriginalUserId1()),
						Long.valueOf(socialRelationModelImpl.getOriginalUserId2())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U1_U2, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U1_U2,
					args);

				args = new Object[] {
						Long.valueOf(socialRelationModelImpl.getUserId1()),
						Long.valueOf(socialRelationModelImpl.getUserId2())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U1_U2, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U1_U2,
					args);
			}

			if ((socialRelationModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U1_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(socialRelationModelImpl.getOriginalUserId1()),
						Integer.valueOf(socialRelationModelImpl.getOriginalType())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U1_T, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U1_T,
					args);

				args = new Object[] {
						Long.valueOf(socialRelationModelImpl.getUserId1()),
						Integer.valueOf(socialRelationModelImpl.getType())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U1_T, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U1_T,
					args);
			}

			if ((socialRelationModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U2_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(socialRelationModelImpl.getOriginalUserId2()),
						Integer.valueOf(socialRelationModelImpl.getOriginalType())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U2_T, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U2_T,
					args);

				args = new Object[] {
						Long.valueOf(socialRelationModelImpl.getUserId2()),
						Integer.valueOf(socialRelationModelImpl.getType())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U2_T, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U2_T,
					args);
			}
		}

		EntityCacheUtil.putResult(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationImpl.class, socialRelation.getPrimaryKey(),
			socialRelation);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U1_U2_T,
				new Object[] {
					Long.valueOf(socialRelation.getUserId1()),
					Long.valueOf(socialRelation.getUserId2()),
					Integer.valueOf(socialRelation.getType())
				}, socialRelation);
		}
		else {
			if ((socialRelationModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_U1_U2_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(socialRelationModelImpl.getOriginalUserId1()),
						Long.valueOf(socialRelationModelImpl.getOriginalUserId2()),
						Integer.valueOf(socialRelationModelImpl.getOriginalType())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U1_U2_T, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U1_U2_T, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U1_U2_T,
					new Object[] {
						Long.valueOf(socialRelation.getUserId1()),
						Long.valueOf(socialRelation.getUserId2()),
						Integer.valueOf(socialRelation.getType())
					}, socialRelation);
			}
		}

		return socialRelation;
	}

	protected SocialRelation toUnwrappedModel(SocialRelation socialRelation) {
		if (socialRelation instanceof SocialRelationImpl) {
			return socialRelation;
		}

		SocialRelationImpl socialRelationImpl = new SocialRelationImpl();

		socialRelationImpl.setNew(socialRelation.isNew());
		socialRelationImpl.setPrimaryKey(socialRelation.getPrimaryKey());

		socialRelationImpl.setUuid(socialRelation.getUuid());
		socialRelationImpl.setRelationId(socialRelation.getRelationId());
		socialRelationImpl.setCompanyId(socialRelation.getCompanyId());
		socialRelationImpl.setCreateDate(socialRelation.getCreateDate());
		socialRelationImpl.setUserId1(socialRelation.getUserId1());
		socialRelationImpl.setUserId2(socialRelation.getUserId2());
		socialRelationImpl.setType(socialRelation.getType());

		return socialRelationImpl;
	}

	/**
	 * Returns the social relation with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the social relation
	 * @return the social relation
	 * @throws com.liferay.portal.NoSuchModelException if a social relation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialRelation findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the social relation with the primary key or throws a {@link com.liferay.portlet.social.NoSuchRelationException} if it could not be found.
	 *
	 * @param relationId the primary key of the social relation
	 * @return the social relation
	 * @throws com.liferay.portlet.social.NoSuchRelationException if a social relation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation findByPrimaryKey(long relationId)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = fetchByPrimaryKey(relationId);

		if (socialRelation == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + relationId);
			}

			throw new NoSuchRelationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				relationId);
		}

		return socialRelation;
	}

	/**
	 * Returns the social relation with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the social relation
	 * @return the social relation, or <code>null</code> if a social relation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialRelation fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the social relation with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param relationId the primary key of the social relation
	 * @return the social relation, or <code>null</code> if a social relation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation fetchByPrimaryKey(long relationId)
		throws SystemException {
		SocialRelation socialRelation = (SocialRelation)EntityCacheUtil.getResult(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
				SocialRelationImpl.class, relationId);

		if (socialRelation == _nullSocialRelation) {
			return null;
		}

		if (socialRelation == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				socialRelation = (SocialRelation)session.get(SocialRelationImpl.class,
						Long.valueOf(relationId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (socialRelation != null) {
					cacheResult(socialRelation);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
						SocialRelationImpl.class, relationId,
						_nullSocialRelation);
				}

				closeSession(session);
			}
		}

		return socialRelation;
	}

	/**
	 * Returns all the social relations where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRelation> findByUuid(String uuid)
		throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social relations where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @return the range of matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRelation> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social relations where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRelation> findByUuid(String uuid, int start, int end,
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

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SocialRelation socialRelation : list) {
				if (!Validator.equals(uuid, socialRelation.getUuid())) {
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
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

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

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<SocialRelation>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first social relation in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social relation
	 * @throws com.liferay.portlet.social.NoSuchRelationException if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = fetchByUuid_First(uuid,
				orderByComparator);

		if (socialRelation != null) {
			return socialRelation;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRelationException(msg.toString());
	}

	/**
	 * Returns the first social relation in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social relation, or <code>null</code> if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<SocialRelation> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social relation in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social relation
	 * @throws com.liferay.portlet.social.NoSuchRelationException if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = fetchByUuid_Last(uuid, orderByComparator);

		if (socialRelation != null) {
			return socialRelation;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRelationException(msg.toString());
	}

	/**
	 * Returns the last social relation in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social relation, or <code>null</code> if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<SocialRelation> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social relations before and after the current social relation in the ordered set where uuid = &#63;.
	 *
	 * @param relationId the primary key of the current social relation
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social relation
	 * @throws com.liferay.portlet.social.NoSuchRelationException if a social relation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation[] findByUuid_PrevAndNext(long relationId,
		String uuid, OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = findByPrimaryKey(relationId);

		Session session = null;

		try {
			session = openSession();

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = getByUuid_PrevAndNext(session, socialRelation, uuid,
					orderByComparator, true);

			array[1] = socialRelation;

			array[2] = getByUuid_PrevAndNext(session, socialRelation, uuid,
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

	protected SocialRelation getByUuid_PrevAndNext(Session session,
		SocialRelation socialRelation, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (uuid != null) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialRelation);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialRelation> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the social relations where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRelation> findByUuid_C(String uuid, long companyId)
		throws SystemException {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social relations where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @return the range of matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRelation> findByUuid_C(String uuid, long companyId,
		int start, int end) throws SystemException {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social relations where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRelation> findByUuid_C(String uuid, long companyId,
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

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SocialRelation socialRelation : list) {
				if (!Validator.equals(uuid, socialRelation.getUuid()) ||
						(companyId != socialRelation.getCompanyId())) {
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

			query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

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

				list = (List<SocialRelation>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first social relation in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social relation
	 * @throws com.liferay.portlet.social.NoSuchRelationException if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation findByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (socialRelation != null) {
			return socialRelation;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRelationException(msg.toString());
	}

	/**
	 * Returns the first social relation in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social relation, or <code>null</code> if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<SocialRelation> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social relation in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social relation
	 * @throws com.liferay.portlet.social.NoSuchRelationException if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (socialRelation != null) {
			return socialRelation;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRelationException(msg.toString());
	}

	/**
	 * Returns the last social relation in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social relation, or <code>null</code> if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid_C(uuid, companyId);

		List<SocialRelation> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social relations before and after the current social relation in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param relationId the primary key of the current social relation
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social relation
	 * @throws com.liferay.portlet.social.NoSuchRelationException if a social relation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation[] findByUuid_C_PrevAndNext(long relationId,
		String uuid, long companyId, OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = findByPrimaryKey(relationId);

		Session session = null;

		try {
			session = openSession();

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, socialRelation, uuid,
					companyId, orderByComparator, true);

			array[1] = socialRelation;

			array[2] = getByUuid_C_PrevAndNext(session, socialRelation, uuid,
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

	protected SocialRelation getByUuid_C_PrevAndNext(Session session,
		SocialRelation socialRelation, String uuid, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(socialRelation);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialRelation> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the social relations where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRelation> findByCompanyId(long companyId)
		throws SystemException {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the social relations where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @return the range of matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRelation> findByCompanyId(long companyId, int start,
		int end) throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social relations where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRelation> findByCompanyId(long companyId, int start,
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

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SocialRelation socialRelation : list) {
				if ((companyId != socialRelation.getCompanyId())) {
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
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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

				qPos.add(companyId);

				list = (List<SocialRelation>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first social relation in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social relation
	 * @throws com.liferay.portlet.social.NoSuchRelationException if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (socialRelation != null) {
			return socialRelation;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRelationException(msg.toString());
	}

	/**
	 * Returns the first social relation in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social relation, or <code>null</code> if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation fetchByCompanyId_First(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<SocialRelation> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social relation in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social relation
	 * @throws com.liferay.portlet.social.NoSuchRelationException if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (socialRelation != null) {
			return socialRelation;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRelationException(msg.toString());
	}

	/**
	 * Returns the last social relation in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social relation, or <code>null</code> if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation fetchByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByCompanyId(companyId);

		List<SocialRelation> list = findByCompanyId(companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social relations before and after the current social relation in the ordered set where companyId = &#63;.
	 *
	 * @param relationId the primary key of the current social relation
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social relation
	 * @throws com.liferay.portlet.social.NoSuchRelationException if a social relation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation[] findByCompanyId_PrevAndNext(long relationId,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = findByPrimaryKey(relationId);

		Session session = null;

		try {
			session = openSession();

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, socialRelation,
					companyId, orderByComparator, true);

			array[1] = socialRelation;

			array[2] = getByCompanyId_PrevAndNext(session, socialRelation,
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

	protected SocialRelation getByCompanyId_PrevAndNext(Session session,
		SocialRelation socialRelation, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialRelation);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialRelation> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the social relations where userId1 = &#63;.
	 *
	 * @param userId1 the user id1
	 * @return the matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRelation> findByUserId1(long userId1)
		throws SystemException {
		return findByUserId1(userId1, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social relations where userId1 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId1 the user id1
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @return the range of matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRelation> findByUserId1(long userId1, int start, int end)
		throws SystemException {
		return findByUserId1(userId1, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social relations where userId1 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId1 the user id1
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRelation> findByUserId1(long userId1, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID1;
			finderArgs = new Object[] { userId1 };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID1;
			finderArgs = new Object[] { userId1, start, end, orderByComparator };
		}

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SocialRelation socialRelation : list) {
				if ((userId1 != socialRelation.getUserId1())) {
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
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

			query.append(_FINDER_COLUMN_USERID1_USERID1_2);

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

				qPos.add(userId1);

				list = (List<SocialRelation>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first social relation in the ordered set where userId1 = &#63;.
	 *
	 * @param userId1 the user id1
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social relation
	 * @throws com.liferay.portlet.social.NoSuchRelationException if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation findByUserId1_First(long userId1,
		OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = fetchByUserId1_First(userId1,
				orderByComparator);

		if (socialRelation != null) {
			return socialRelation;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId1=");
		msg.append(userId1);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRelationException(msg.toString());
	}

	/**
	 * Returns the first social relation in the ordered set where userId1 = &#63;.
	 *
	 * @param userId1 the user id1
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social relation, or <code>null</code> if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation fetchByUserId1_First(long userId1,
		OrderByComparator orderByComparator) throws SystemException {
		List<SocialRelation> list = findByUserId1(userId1, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social relation in the ordered set where userId1 = &#63;.
	 *
	 * @param userId1 the user id1
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social relation
	 * @throws com.liferay.portlet.social.NoSuchRelationException if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation findByUserId1_Last(long userId1,
		OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = fetchByUserId1_Last(userId1,
				orderByComparator);

		if (socialRelation != null) {
			return socialRelation;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId1=");
		msg.append(userId1);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRelationException(msg.toString());
	}

	/**
	 * Returns the last social relation in the ordered set where userId1 = &#63;.
	 *
	 * @param userId1 the user id1
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social relation, or <code>null</code> if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation fetchByUserId1_Last(long userId1,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUserId1(userId1);

		List<SocialRelation> list = findByUserId1(userId1, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social relations before and after the current social relation in the ordered set where userId1 = &#63;.
	 *
	 * @param relationId the primary key of the current social relation
	 * @param userId1 the user id1
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social relation
	 * @throws com.liferay.portlet.social.NoSuchRelationException if a social relation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation[] findByUserId1_PrevAndNext(long relationId,
		long userId1, OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = findByPrimaryKey(relationId);

		Session session = null;

		try {
			session = openSession();

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = getByUserId1_PrevAndNext(session, socialRelation,
					userId1, orderByComparator, true);

			array[1] = socialRelation;

			array[2] = getByUserId1_PrevAndNext(session, socialRelation,
					userId1, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialRelation getByUserId1_PrevAndNext(Session session,
		SocialRelation socialRelation, long userId1,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

		query.append(_FINDER_COLUMN_USERID1_USERID1_2);

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

		qPos.add(userId1);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialRelation);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialRelation> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the social relations where userId2 = &#63;.
	 *
	 * @param userId2 the user id2
	 * @return the matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRelation> findByUserId2(long userId2)
		throws SystemException {
		return findByUserId2(userId2, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social relations where userId2 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId2 the user id2
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @return the range of matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRelation> findByUserId2(long userId2, int start, int end)
		throws SystemException {
		return findByUserId2(userId2, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social relations where userId2 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId2 the user id2
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRelation> findByUserId2(long userId2, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID2;
			finderArgs = new Object[] { userId2 };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID2;
			finderArgs = new Object[] { userId2, start, end, orderByComparator };
		}

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SocialRelation socialRelation : list) {
				if ((userId2 != socialRelation.getUserId2())) {
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
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

			query.append(_FINDER_COLUMN_USERID2_USERID2_2);

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

				qPos.add(userId2);

				list = (List<SocialRelation>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first social relation in the ordered set where userId2 = &#63;.
	 *
	 * @param userId2 the user id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social relation
	 * @throws com.liferay.portlet.social.NoSuchRelationException if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation findByUserId2_First(long userId2,
		OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = fetchByUserId2_First(userId2,
				orderByComparator);

		if (socialRelation != null) {
			return socialRelation;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId2=");
		msg.append(userId2);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRelationException(msg.toString());
	}

	/**
	 * Returns the first social relation in the ordered set where userId2 = &#63;.
	 *
	 * @param userId2 the user id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social relation, or <code>null</code> if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation fetchByUserId2_First(long userId2,
		OrderByComparator orderByComparator) throws SystemException {
		List<SocialRelation> list = findByUserId2(userId2, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social relation in the ordered set where userId2 = &#63;.
	 *
	 * @param userId2 the user id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social relation
	 * @throws com.liferay.portlet.social.NoSuchRelationException if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation findByUserId2_Last(long userId2,
		OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = fetchByUserId2_Last(userId2,
				orderByComparator);

		if (socialRelation != null) {
			return socialRelation;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId2=");
		msg.append(userId2);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRelationException(msg.toString());
	}

	/**
	 * Returns the last social relation in the ordered set where userId2 = &#63;.
	 *
	 * @param userId2 the user id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social relation, or <code>null</code> if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation fetchByUserId2_Last(long userId2,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUserId2(userId2);

		List<SocialRelation> list = findByUserId2(userId2, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social relations before and after the current social relation in the ordered set where userId2 = &#63;.
	 *
	 * @param relationId the primary key of the current social relation
	 * @param userId2 the user id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social relation
	 * @throws com.liferay.portlet.social.NoSuchRelationException if a social relation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation[] findByUserId2_PrevAndNext(long relationId,
		long userId2, OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = findByPrimaryKey(relationId);

		Session session = null;

		try {
			session = openSession();

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = getByUserId2_PrevAndNext(session, socialRelation,
					userId2, orderByComparator, true);

			array[1] = socialRelation;

			array[2] = getByUserId2_PrevAndNext(session, socialRelation,
					userId2, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialRelation getByUserId2_PrevAndNext(Session session,
		SocialRelation socialRelation, long userId2,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

		query.append(_FINDER_COLUMN_USERID2_USERID2_2);

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

		qPos.add(userId2);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialRelation);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialRelation> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the social relations where type = &#63;.
	 *
	 * @param type the type
	 * @return the matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRelation> findByType(int type) throws SystemException {
		return findByType(type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social relations where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param type the type
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @return the range of matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRelation> findByType(int type, int start, int end)
		throws SystemException {
		return findByType(type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social relations where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param type the type
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRelation> findByType(int type, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TYPE;
			finderArgs = new Object[] { type };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_TYPE;
			finderArgs = new Object[] { type, start, end, orderByComparator };
		}

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SocialRelation socialRelation : list) {
				if ((type != socialRelation.getType())) {
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
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

			query.append(_FINDER_COLUMN_TYPE_TYPE_2);

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

				qPos.add(type);

				list = (List<SocialRelation>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first social relation in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social relation
	 * @throws com.liferay.portlet.social.NoSuchRelationException if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation findByType_First(int type,
		OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = fetchByType_First(type,
				orderByComparator);

		if (socialRelation != null) {
			return socialRelation;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRelationException(msg.toString());
	}

	/**
	 * Returns the first social relation in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social relation, or <code>null</code> if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation fetchByType_First(int type,
		OrderByComparator orderByComparator) throws SystemException {
		List<SocialRelation> list = findByType(type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social relation in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social relation
	 * @throws com.liferay.portlet.social.NoSuchRelationException if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation findByType_Last(int type,
		OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = fetchByType_Last(type, orderByComparator);

		if (socialRelation != null) {
			return socialRelation;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRelationException(msg.toString());
	}

	/**
	 * Returns the last social relation in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social relation, or <code>null</code> if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation fetchByType_Last(int type,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByType(type);

		List<SocialRelation> list = findByType(type, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social relations before and after the current social relation in the ordered set where type = &#63;.
	 *
	 * @param relationId the primary key of the current social relation
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social relation
	 * @throws com.liferay.portlet.social.NoSuchRelationException if a social relation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation[] findByType_PrevAndNext(long relationId, int type,
		OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = findByPrimaryKey(relationId);

		Session session = null;

		try {
			session = openSession();

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = getByType_PrevAndNext(session, socialRelation, type,
					orderByComparator, true);

			array[1] = socialRelation;

			array[2] = getByType_PrevAndNext(session, socialRelation, type,
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

	protected SocialRelation getByType_PrevAndNext(Session session,
		SocialRelation socialRelation, int type,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

		query.append(_FINDER_COLUMN_TYPE_TYPE_2);

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

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialRelation);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialRelation> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the social relations where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRelation> findByC_T(long companyId, int type)
		throws SystemException {
		return findByC_T(companyId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the social relations where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @return the range of matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRelation> findByC_T(long companyId, int type, int start,
		int end) throws SystemException {
		return findByC_T(companyId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social relations where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRelation> findByC_T(long companyId, int type, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_T;
			finderArgs = new Object[] { companyId, type };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_T;
			finderArgs = new Object[] {
					companyId, type,
					
					start, end, orderByComparator
				};
		}

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SocialRelation socialRelation : list) {
				if ((companyId != socialRelation.getCompanyId()) ||
						(type != socialRelation.getType())) {
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

			query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

			query.append(_FINDER_COLUMN_C_T_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_T_TYPE_2);

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

				qPos.add(companyId);

				qPos.add(type);

				list = (List<SocialRelation>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first social relation in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social relation
	 * @throws com.liferay.portlet.social.NoSuchRelationException if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation findByC_T_First(long companyId, int type,
		OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = fetchByC_T_First(companyId, type,
				orderByComparator);

		if (socialRelation != null) {
			return socialRelation;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRelationException(msg.toString());
	}

	/**
	 * Returns the first social relation in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social relation, or <code>null</code> if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation fetchByC_T_First(long companyId, int type,
		OrderByComparator orderByComparator) throws SystemException {
		List<SocialRelation> list = findByC_T(companyId, type, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social relation in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social relation
	 * @throws com.liferay.portlet.social.NoSuchRelationException if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation findByC_T_Last(long companyId, int type,
		OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = fetchByC_T_Last(companyId, type,
				orderByComparator);

		if (socialRelation != null) {
			return socialRelation;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRelationException(msg.toString());
	}

	/**
	 * Returns the last social relation in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social relation, or <code>null</code> if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation fetchByC_T_Last(long companyId, int type,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByC_T(companyId, type);

		List<SocialRelation> list = findByC_T(companyId, type, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social relations before and after the current social relation in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param relationId the primary key of the current social relation
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social relation
	 * @throws com.liferay.portlet.social.NoSuchRelationException if a social relation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation[] findByC_T_PrevAndNext(long relationId,
		long companyId, int type, OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = findByPrimaryKey(relationId);

		Session session = null;

		try {
			session = openSession();

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = getByC_T_PrevAndNext(session, socialRelation, companyId,
					type, orderByComparator, true);

			array[1] = socialRelation;

			array[2] = getByC_T_PrevAndNext(session, socialRelation, companyId,
					type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialRelation getByC_T_PrevAndNext(Session session,
		SocialRelation socialRelation, long companyId, int type,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

		query.append(_FINDER_COLUMN_C_T_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_T_TYPE_2);

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

		qPos.add(companyId);

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialRelation);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialRelation> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the social relations where userId1 = &#63; and userId2 = &#63;.
	 *
	 * @param userId1 the user id1
	 * @param userId2 the user id2
	 * @return the matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRelation> findByU1_U2(long userId1, long userId2)
		throws SystemException {
		return findByU1_U2(userId1, userId2, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social relations where userId1 = &#63; and userId2 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId1 the user id1
	 * @param userId2 the user id2
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @return the range of matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRelation> findByU1_U2(long userId1, long userId2,
		int start, int end) throws SystemException {
		return findByU1_U2(userId1, userId2, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social relations where userId1 = &#63; and userId2 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId1 the user id1
	 * @param userId2 the user id2
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRelation> findByU1_U2(long userId1, long userId2,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U1_U2;
			finderArgs = new Object[] { userId1, userId2 };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_U1_U2;
			finderArgs = new Object[] {
					userId1, userId2,
					
					start, end, orderByComparator
				};
		}

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SocialRelation socialRelation : list) {
				if ((userId1 != socialRelation.getUserId1()) ||
						(userId2 != socialRelation.getUserId2())) {
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

			query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

			query.append(_FINDER_COLUMN_U1_U2_USERID1_2);

			query.append(_FINDER_COLUMN_U1_U2_USERID2_2);

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

				qPos.add(userId1);

				qPos.add(userId2);

				list = (List<SocialRelation>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first social relation in the ordered set where userId1 = &#63; and userId2 = &#63;.
	 *
	 * @param userId1 the user id1
	 * @param userId2 the user id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social relation
	 * @throws com.liferay.portlet.social.NoSuchRelationException if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation findByU1_U2_First(long userId1, long userId2,
		OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = fetchByU1_U2_First(userId1, userId2,
				orderByComparator);

		if (socialRelation != null) {
			return socialRelation;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId1=");
		msg.append(userId1);

		msg.append(", userId2=");
		msg.append(userId2);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRelationException(msg.toString());
	}

	/**
	 * Returns the first social relation in the ordered set where userId1 = &#63; and userId2 = &#63;.
	 *
	 * @param userId1 the user id1
	 * @param userId2 the user id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social relation, or <code>null</code> if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation fetchByU1_U2_First(long userId1, long userId2,
		OrderByComparator orderByComparator) throws SystemException {
		List<SocialRelation> list = findByU1_U2(userId1, userId2, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social relation in the ordered set where userId1 = &#63; and userId2 = &#63;.
	 *
	 * @param userId1 the user id1
	 * @param userId2 the user id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social relation
	 * @throws com.liferay.portlet.social.NoSuchRelationException if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation findByU1_U2_Last(long userId1, long userId2,
		OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = fetchByU1_U2_Last(userId1, userId2,
				orderByComparator);

		if (socialRelation != null) {
			return socialRelation;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId1=");
		msg.append(userId1);

		msg.append(", userId2=");
		msg.append(userId2);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRelationException(msg.toString());
	}

	/**
	 * Returns the last social relation in the ordered set where userId1 = &#63; and userId2 = &#63;.
	 *
	 * @param userId1 the user id1
	 * @param userId2 the user id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social relation, or <code>null</code> if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation fetchByU1_U2_Last(long userId1, long userId2,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByU1_U2(userId1, userId2);

		List<SocialRelation> list = findByU1_U2(userId1, userId2, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social relations before and after the current social relation in the ordered set where userId1 = &#63; and userId2 = &#63;.
	 *
	 * @param relationId the primary key of the current social relation
	 * @param userId1 the user id1
	 * @param userId2 the user id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social relation
	 * @throws com.liferay.portlet.social.NoSuchRelationException if a social relation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation[] findByU1_U2_PrevAndNext(long relationId,
		long userId1, long userId2, OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = findByPrimaryKey(relationId);

		Session session = null;

		try {
			session = openSession();

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = getByU1_U2_PrevAndNext(session, socialRelation, userId1,
					userId2, orderByComparator, true);

			array[1] = socialRelation;

			array[2] = getByU1_U2_PrevAndNext(session, socialRelation, userId1,
					userId2, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialRelation getByU1_U2_PrevAndNext(Session session,
		SocialRelation socialRelation, long userId1, long userId2,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

		query.append(_FINDER_COLUMN_U1_U2_USERID1_2);

		query.append(_FINDER_COLUMN_U1_U2_USERID2_2);

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

		qPos.add(userId1);

		qPos.add(userId2);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialRelation);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialRelation> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the social relations where userId1 = &#63; and type = &#63;.
	 *
	 * @param userId1 the user id1
	 * @param type the type
	 * @return the matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRelation> findByU1_T(long userId1, int type)
		throws SystemException {
		return findByU1_T(userId1, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the social relations where userId1 = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId1 the user id1
	 * @param type the type
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @return the range of matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRelation> findByU1_T(long userId1, int type, int start,
		int end) throws SystemException {
		return findByU1_T(userId1, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social relations where userId1 = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId1 the user id1
	 * @param type the type
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRelation> findByU1_T(long userId1, int type, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U1_T;
			finderArgs = new Object[] { userId1, type };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_U1_T;
			finderArgs = new Object[] {
					userId1, type,
					
					start, end, orderByComparator
				};
		}

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SocialRelation socialRelation : list) {
				if ((userId1 != socialRelation.getUserId1()) ||
						(type != socialRelation.getType())) {
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

			query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

			query.append(_FINDER_COLUMN_U1_T_USERID1_2);

			query.append(_FINDER_COLUMN_U1_T_TYPE_2);

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

				qPos.add(userId1);

				qPos.add(type);

				list = (List<SocialRelation>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first social relation in the ordered set where userId1 = &#63; and type = &#63;.
	 *
	 * @param userId1 the user id1
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social relation
	 * @throws com.liferay.portlet.social.NoSuchRelationException if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation findByU1_T_First(long userId1, int type,
		OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = fetchByU1_T_First(userId1, type,
				orderByComparator);

		if (socialRelation != null) {
			return socialRelation;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId1=");
		msg.append(userId1);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRelationException(msg.toString());
	}

	/**
	 * Returns the first social relation in the ordered set where userId1 = &#63; and type = &#63;.
	 *
	 * @param userId1 the user id1
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social relation, or <code>null</code> if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation fetchByU1_T_First(long userId1, int type,
		OrderByComparator orderByComparator) throws SystemException {
		List<SocialRelation> list = findByU1_T(userId1, type, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social relation in the ordered set where userId1 = &#63; and type = &#63;.
	 *
	 * @param userId1 the user id1
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social relation
	 * @throws com.liferay.portlet.social.NoSuchRelationException if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation findByU1_T_Last(long userId1, int type,
		OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = fetchByU1_T_Last(userId1, type,
				orderByComparator);

		if (socialRelation != null) {
			return socialRelation;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId1=");
		msg.append(userId1);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRelationException(msg.toString());
	}

	/**
	 * Returns the last social relation in the ordered set where userId1 = &#63; and type = &#63;.
	 *
	 * @param userId1 the user id1
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social relation, or <code>null</code> if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation fetchByU1_T_Last(long userId1, int type,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByU1_T(userId1, type);

		List<SocialRelation> list = findByU1_T(userId1, type, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social relations before and after the current social relation in the ordered set where userId1 = &#63; and type = &#63;.
	 *
	 * @param relationId the primary key of the current social relation
	 * @param userId1 the user id1
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social relation
	 * @throws com.liferay.portlet.social.NoSuchRelationException if a social relation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation[] findByU1_T_PrevAndNext(long relationId,
		long userId1, int type, OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = findByPrimaryKey(relationId);

		Session session = null;

		try {
			session = openSession();

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = getByU1_T_PrevAndNext(session, socialRelation, userId1,
					type, orderByComparator, true);

			array[1] = socialRelation;

			array[2] = getByU1_T_PrevAndNext(session, socialRelation, userId1,
					type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialRelation getByU1_T_PrevAndNext(Session session,
		SocialRelation socialRelation, long userId1, int type,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

		query.append(_FINDER_COLUMN_U1_T_USERID1_2);

		query.append(_FINDER_COLUMN_U1_T_TYPE_2);

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

		qPos.add(userId1);

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialRelation);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialRelation> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the social relations where userId2 = &#63; and type = &#63;.
	 *
	 * @param userId2 the user id2
	 * @param type the type
	 * @return the matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRelation> findByU2_T(long userId2, int type)
		throws SystemException {
		return findByU2_T(userId2, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the social relations where userId2 = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId2 the user id2
	 * @param type the type
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @return the range of matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRelation> findByU2_T(long userId2, int type, int start,
		int end) throws SystemException {
		return findByU2_T(userId2, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social relations where userId2 = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId2 the user id2
	 * @param type the type
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRelation> findByU2_T(long userId2, int type, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U2_T;
			finderArgs = new Object[] { userId2, type };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_U2_T;
			finderArgs = new Object[] {
					userId2, type,
					
					start, end, orderByComparator
				};
		}

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SocialRelation socialRelation : list) {
				if ((userId2 != socialRelation.getUserId2()) ||
						(type != socialRelation.getType())) {
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

			query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

			query.append(_FINDER_COLUMN_U2_T_USERID2_2);

			query.append(_FINDER_COLUMN_U2_T_TYPE_2);

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

				qPos.add(userId2);

				qPos.add(type);

				list = (List<SocialRelation>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first social relation in the ordered set where userId2 = &#63; and type = &#63;.
	 *
	 * @param userId2 the user id2
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social relation
	 * @throws com.liferay.portlet.social.NoSuchRelationException if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation findByU2_T_First(long userId2, int type,
		OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = fetchByU2_T_First(userId2, type,
				orderByComparator);

		if (socialRelation != null) {
			return socialRelation;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId2=");
		msg.append(userId2);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRelationException(msg.toString());
	}

	/**
	 * Returns the first social relation in the ordered set where userId2 = &#63; and type = &#63;.
	 *
	 * @param userId2 the user id2
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social relation, or <code>null</code> if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation fetchByU2_T_First(long userId2, int type,
		OrderByComparator orderByComparator) throws SystemException {
		List<SocialRelation> list = findByU2_T(userId2, type, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social relation in the ordered set where userId2 = &#63; and type = &#63;.
	 *
	 * @param userId2 the user id2
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social relation
	 * @throws com.liferay.portlet.social.NoSuchRelationException if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation findByU2_T_Last(long userId2, int type,
		OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = fetchByU2_T_Last(userId2, type,
				orderByComparator);

		if (socialRelation != null) {
			return socialRelation;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId2=");
		msg.append(userId2);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRelationException(msg.toString());
	}

	/**
	 * Returns the last social relation in the ordered set where userId2 = &#63; and type = &#63;.
	 *
	 * @param userId2 the user id2
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social relation, or <code>null</code> if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation fetchByU2_T_Last(long userId2, int type,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByU2_T(userId2, type);

		List<SocialRelation> list = findByU2_T(userId2, type, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social relations before and after the current social relation in the ordered set where userId2 = &#63; and type = &#63;.
	 *
	 * @param relationId the primary key of the current social relation
	 * @param userId2 the user id2
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social relation
	 * @throws com.liferay.portlet.social.NoSuchRelationException if a social relation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation[] findByU2_T_PrevAndNext(long relationId,
		long userId2, int type, OrderByComparator orderByComparator)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = findByPrimaryKey(relationId);

		Session session = null;

		try {
			session = openSession();

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = getByU2_T_PrevAndNext(session, socialRelation, userId2,
					type, orderByComparator, true);

			array[1] = socialRelation;

			array[2] = getByU2_T_PrevAndNext(session, socialRelation, userId2,
					type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialRelation getByU2_T_PrevAndNext(Session session,
		SocialRelation socialRelation, long userId2, int type,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

		query.append(_FINDER_COLUMN_U2_T_USERID2_2);

		query.append(_FINDER_COLUMN_U2_T_TYPE_2);

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

		qPos.add(userId2);

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialRelation);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialRelation> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the social relation where userId1 = &#63; and userId2 = &#63; and type = &#63; or throws a {@link com.liferay.portlet.social.NoSuchRelationException} if it could not be found.
	 *
	 * @param userId1 the user id1
	 * @param userId2 the user id2
	 * @param type the type
	 * @return the matching social relation
	 * @throws com.liferay.portlet.social.NoSuchRelationException if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation findByU1_U2_T(long userId1, long userId2, int type)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = fetchByU1_U2_T(userId1, userId2, type);

		if (socialRelation == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId1=");
			msg.append(userId1);

			msg.append(", userId2=");
			msg.append(userId2);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchRelationException(msg.toString());
		}

		return socialRelation;
	}

	/**
	 * Returns the social relation where userId1 = &#63; and userId2 = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId1 the user id1
	 * @param userId2 the user id2
	 * @param type the type
	 * @return the matching social relation, or <code>null</code> if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation fetchByU1_U2_T(long userId1, long userId2, int type)
		throws SystemException {
		return fetchByU1_U2_T(userId1, userId2, type, true);
	}

	/**
	 * Returns the social relation where userId1 = &#63; and userId2 = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId1 the user id1
	 * @param userId2 the user id2
	 * @param type the type
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching social relation, or <code>null</code> if a matching social relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation fetchByU1_U2_T(long userId1, long userId2, int type,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { userId1, userId2, type };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_U1_U2_T,
					finderArgs, this);
		}

		if (result instanceof SocialRelation) {
			SocialRelation socialRelation = (SocialRelation)result;

			if ((userId1 != socialRelation.getUserId1()) ||
					(userId2 != socialRelation.getUserId2()) ||
					(type != socialRelation.getType())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_SOCIALRELATION_WHERE);

			query.append(_FINDER_COLUMN_U1_U2_T_USERID1_2);

			query.append(_FINDER_COLUMN_U1_U2_T_USERID2_2);

			query.append(_FINDER_COLUMN_U1_U2_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId1);

				qPos.add(userId2);

				qPos.add(type);

				List<SocialRelation> list = q.list();

				result = list;

				SocialRelation socialRelation = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U1_U2_T,
						finderArgs, list);
				}
				else {
					socialRelation = list.get(0);

					cacheResult(socialRelation);

					if ((socialRelation.getUserId1() != userId1) ||
							(socialRelation.getUserId2() != userId2) ||
							(socialRelation.getType() != type)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U1_U2_T,
							finderArgs, socialRelation);
					}
				}

				return socialRelation;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U1_U2_T,
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
				return (SocialRelation)result;
			}
		}
	}

	/**
	 * Returns all the social relations.
	 *
	 * @return the social relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRelation> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social relations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @return the range of social relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRelation> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the social relations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of social relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRelation> findAll(int start, int end,
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

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SOCIALRELATION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SOCIALRELATION;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<SocialRelation>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<SocialRelation>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Removes all the social relations where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (SocialRelation socialRelation : findByUuid(uuid)) {
			remove(socialRelation);
		}
	}

	/**
	 * Removes all the social relations where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid_C(String uuid, long companyId)
		throws SystemException {
		for (SocialRelation socialRelation : findByUuid_C(uuid, companyId)) {
			remove(socialRelation);
		}
	}

	/**
	 * Removes all the social relations where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCompanyId(long companyId) throws SystemException {
		for (SocialRelation socialRelation : findByCompanyId(companyId)) {
			remove(socialRelation);
		}
	}

	/**
	 * Removes all the social relations where userId1 = &#63; from the database.
	 *
	 * @param userId1 the user id1
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUserId1(long userId1) throws SystemException {
		for (SocialRelation socialRelation : findByUserId1(userId1)) {
			remove(socialRelation);
		}
	}

	/**
	 * Removes all the social relations where userId2 = &#63; from the database.
	 *
	 * @param userId2 the user id2
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUserId2(long userId2) throws SystemException {
		for (SocialRelation socialRelation : findByUserId2(userId2)) {
			remove(socialRelation);
		}
	}

	/**
	 * Removes all the social relations where type = &#63; from the database.
	 *
	 * @param type the type
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByType(int type) throws SystemException {
		for (SocialRelation socialRelation : findByType(type)) {
			remove(socialRelation);
		}
	}

	/**
	 * Removes all the social relations where companyId = &#63; and type = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_T(long companyId, int type) throws SystemException {
		for (SocialRelation socialRelation : findByC_T(companyId, type)) {
			remove(socialRelation);
		}
	}

	/**
	 * Removes all the social relations where userId1 = &#63; and userId2 = &#63; from the database.
	 *
	 * @param userId1 the user id1
	 * @param userId2 the user id2
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByU1_U2(long userId1, long userId2)
		throws SystemException {
		for (SocialRelation socialRelation : findByU1_U2(userId1, userId2)) {
			remove(socialRelation);
		}
	}

	/**
	 * Removes all the social relations where userId1 = &#63; and type = &#63; from the database.
	 *
	 * @param userId1 the user id1
	 * @param type the type
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByU1_T(long userId1, int type) throws SystemException {
		for (SocialRelation socialRelation : findByU1_T(userId1, type)) {
			remove(socialRelation);
		}
	}

	/**
	 * Removes all the social relations where userId2 = &#63; and type = &#63; from the database.
	 *
	 * @param userId2 the user id2
	 * @param type the type
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByU2_T(long userId2, int type) throws SystemException {
		for (SocialRelation socialRelation : findByU2_T(userId2, type)) {
			remove(socialRelation);
		}
	}

	/**
	 * Removes the social relation where userId1 = &#63; and userId2 = &#63; and type = &#63; from the database.
	 *
	 * @param userId1 the user id1
	 * @param userId2 the user id2
	 * @param type the type
	 * @return the social relation that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation removeByU1_U2_T(long userId1, long userId2, int type)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = findByU1_U2_T(userId1, userId2, type);

		return remove(socialRelation);
	}

	/**
	 * Removes all the social relations from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (SocialRelation socialRelation : findAll()) {
			remove(socialRelation);
		}
	}

	/**
	 * Returns the number of social relations where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SOCIALRELATION_WHERE);

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
	 * Returns the number of social relations where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid_C(String uuid, long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SOCIALRELATION_WHERE);

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
	 * Returns the number of social relations where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SOCIALRELATION_WHERE);

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
	 * Returns the number of social relations where userId1 = &#63;.
	 *
	 * @param userId1 the user id1
	 * @return the number of matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUserId1(long userId1) throws SystemException {
		Object[] finderArgs = new Object[] { userId1 };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID1,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SOCIALRELATION_WHERE);

			query.append(_FINDER_COLUMN_USERID1_USERID1_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId1);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID1,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social relations where userId2 = &#63;.
	 *
	 * @param userId2 the user id2
	 * @return the number of matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUserId2(long userId2) throws SystemException {
		Object[] finderArgs = new Object[] { userId2 };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID2,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SOCIALRELATION_WHERE);

			query.append(_FINDER_COLUMN_USERID2_USERID2_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId2);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID2,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social relations where type = &#63;.
	 *
	 * @param type the type
	 * @return the number of matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public int countByType(int type) throws SystemException {
		Object[] finderArgs = new Object[] { type };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_TYPE,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SOCIALRELATION_WHERE);

			query.append(_FINDER_COLUMN_TYPE_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(type);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_TYPE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social relations where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_T(long companyId, int type) throws SystemException {
		Object[] finderArgs = new Object[] { companyId, type };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_T,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SOCIALRELATION_WHERE);

			query.append(_FINDER_COLUMN_C_T_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(type);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_T, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social relations where userId1 = &#63; and userId2 = &#63;.
	 *
	 * @param userId1 the user id1
	 * @param userId2 the user id2
	 * @return the number of matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public int countByU1_U2(long userId1, long userId2)
		throws SystemException {
		Object[] finderArgs = new Object[] { userId1, userId2 };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U1_U2,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SOCIALRELATION_WHERE);

			query.append(_FINDER_COLUMN_U1_U2_USERID1_2);

			query.append(_FINDER_COLUMN_U1_U2_USERID2_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId1);

				qPos.add(userId2);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U1_U2,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social relations where userId1 = &#63; and type = &#63;.
	 *
	 * @param userId1 the user id1
	 * @param type the type
	 * @return the number of matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public int countByU1_T(long userId1, int type) throws SystemException {
		Object[] finderArgs = new Object[] { userId1, type };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U1_T,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SOCIALRELATION_WHERE);

			query.append(_FINDER_COLUMN_U1_T_USERID1_2);

			query.append(_FINDER_COLUMN_U1_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId1);

				qPos.add(type);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U1_T,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social relations where userId2 = &#63; and type = &#63;.
	 *
	 * @param userId2 the user id2
	 * @param type the type
	 * @return the number of matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public int countByU2_T(long userId2, int type) throws SystemException {
		Object[] finderArgs = new Object[] { userId2, type };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U2_T,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SOCIALRELATION_WHERE);

			query.append(_FINDER_COLUMN_U2_T_USERID2_2);

			query.append(_FINDER_COLUMN_U2_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId2);

				qPos.add(type);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U2_T,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social relations where userId1 = &#63; and userId2 = &#63; and type = &#63;.
	 *
	 * @param userId1 the user id1
	 * @param userId2 the user id2
	 * @param type the type
	 * @return the number of matching social relations
	 * @throws SystemException if a system exception occurred
	 */
	public int countByU1_U2_T(long userId1, long userId2, int type)
		throws SystemException {
		Object[] finderArgs = new Object[] { userId1, userId2, type };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U1_U2_T,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_SOCIALRELATION_WHERE);

			query.append(_FINDER_COLUMN_U1_U2_T_USERID1_2);

			query.append(_FINDER_COLUMN_U1_U2_T_USERID2_2);

			query.append(_FINDER_COLUMN_U1_U2_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId1);

				qPos.add(userId2);

				qPos.add(type);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U1_U2_T,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social relations.
	 *
	 * @return the number of social relations
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SOCIALRELATION);

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
	 * Initializes the social relation persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.social.model.SocialRelation")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SocialRelation>> listenersList = new ArrayList<ModelListener<SocialRelation>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SocialRelation>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(SocialRelationImpl.class.getName());
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
	private static final String _SQL_SELECT_SOCIALRELATION = "SELECT socialRelation FROM SocialRelation socialRelation";
	private static final String _SQL_SELECT_SOCIALRELATION_WHERE = "SELECT socialRelation FROM SocialRelation socialRelation WHERE ";
	private static final String _SQL_COUNT_SOCIALRELATION = "SELECT COUNT(socialRelation) FROM SocialRelation socialRelation";
	private static final String _SQL_COUNT_SOCIALRELATION_WHERE = "SELECT COUNT(socialRelation) FROM SocialRelation socialRelation WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "socialRelation.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "socialRelation.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(socialRelation.uuid IS NULL OR socialRelation.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "socialRelation.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "socialRelation.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(socialRelation.uuid IS NULL OR socialRelation.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "socialRelation.companyId = ?";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "socialRelation.companyId = ?";
	private static final String _FINDER_COLUMN_USERID1_USERID1_2 = "socialRelation.userId1 = ?";
	private static final String _FINDER_COLUMN_USERID2_USERID2_2 = "socialRelation.userId2 = ?";
	private static final String _FINDER_COLUMN_TYPE_TYPE_2 = "socialRelation.type = ?";
	private static final String _FINDER_COLUMN_C_T_COMPANYID_2 = "socialRelation.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_T_TYPE_2 = "socialRelation.type = ?";
	private static final String _FINDER_COLUMN_U1_U2_USERID1_2 = "socialRelation.userId1 = ? AND ";
	private static final String _FINDER_COLUMN_U1_U2_USERID2_2 = "socialRelation.userId2 = ?";
	private static final String _FINDER_COLUMN_U1_T_USERID1_2 = "socialRelation.userId1 = ? AND ";
	private static final String _FINDER_COLUMN_U1_T_TYPE_2 = "socialRelation.type = ?";
	private static final String _FINDER_COLUMN_U2_T_USERID2_2 = "socialRelation.userId2 = ? AND ";
	private static final String _FINDER_COLUMN_U2_T_TYPE_2 = "socialRelation.type = ?";
	private static final String _FINDER_COLUMN_U1_U2_T_USERID1_2 = "socialRelation.userId1 = ? AND ";
	private static final String _FINDER_COLUMN_U1_U2_T_USERID2_2 = "socialRelation.userId2 = ? AND ";
	private static final String _FINDER_COLUMN_U1_U2_T_TYPE_2 = "socialRelation.type = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "socialRelation.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SocialRelation exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SocialRelation exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(SocialRelationPersistenceImpl.class);
	private static SocialRelation _nullSocialRelation = new SocialRelationImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<SocialRelation> toCacheModel() {
				return _nullSocialRelationCacheModel;
			}
		};

	private static CacheModel<SocialRelation> _nullSocialRelationCacheModel = new CacheModel<SocialRelation>() {
			public SocialRelation toEntityModel() {
				return _nullSocialRelation;
			}
		};
}