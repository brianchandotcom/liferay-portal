/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
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
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMTemplateImpl;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMTemplateModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the d d m template service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplatePersistence
 * @see DDMTemplateUtil
 * @generated
 */
public class DDMTemplatePersistenceImpl extends BasePersistenceImpl<DDMTemplate>
	implements DDMTemplatePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DDMTemplateUtil} to access the d d m template persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DDMTemplateImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			DDMTemplateModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			DDMTemplateModelImpl.UUID_COLUMN_BITMASK |
			DDMTemplateModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			DDMTemplateModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_STRUCTUREID =
		new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByStructureId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STRUCTUREID =
		new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByStructureId",
			new String[] { Long.class.getName() },
			DDMTemplateModelImpl.STRUCTUREID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_STRUCTUREID = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByStructureId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_TYPE = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByType",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TYPE = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByType",
			new String[] { String.class.getName() },
			DDMTemplateModelImpl.TYPE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_TYPE = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByType",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_LANGUAGE = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLanguage",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LANGUAGE =
		new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByLanguage",
			new String[] { String.class.getName() },
			DDMTemplateModelImpl.LANGUAGE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_LANGUAGE = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByLanguage",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_S_T = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByS_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_S_T = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByS_T",
			new String[] { Long.class.getName(), String.class.getName() },
			DDMTemplateModelImpl.STRUCTUREID_COLUMN_BITMASK |
			DDMTemplateModelImpl.TYPE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_S_T = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByS_T",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_S_T_M = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByS_T_M",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_S_T_M = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByS_T_M",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			DDMTemplateModelImpl.STRUCTUREID_COLUMN_BITMASK |
			DDMTemplateModelImpl.TYPE_COLUMN_BITMASK |
			DDMTemplateModelImpl.MODE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_S_T_M = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByS_T_M",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the d d m template in the entity cache if it is enabled.
	 *
	 * @param ddmTemplate the d d m template
	 */
	public void cacheResult(DDMTemplate ddmTemplate) {
		EntityCacheUtil.putResult(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateImpl.class, ddmTemplate.getPrimaryKey(), ddmTemplate);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				ddmTemplate.getUuid(), Long.valueOf(ddmTemplate.getGroupId())
			}, ddmTemplate);

		ddmTemplate.resetOriginalValues();
	}

	/**
	 * Caches the d d m templates in the entity cache if it is enabled.
	 *
	 * @param ddmTemplates the d d m templates
	 */
	public void cacheResult(List<DDMTemplate> ddmTemplates) {
		for (DDMTemplate ddmTemplate : ddmTemplates) {
			if (EntityCacheUtil.getResult(
						DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
						DDMTemplateImpl.class, ddmTemplate.getPrimaryKey()) == null) {
				cacheResult(ddmTemplate);
			}
			else {
				ddmTemplate.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all d d m templates.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(DDMTemplateImpl.class.getName());
		}

		EntityCacheUtil.clearCache(DDMTemplateImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the d d m template.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DDMTemplate ddmTemplate) {
		EntityCacheUtil.removeResult(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateImpl.class, ddmTemplate.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				ddmTemplate.getUuid(), Long.valueOf(ddmTemplate.getGroupId())
			});
	}

	/**
	 * Creates a new d d m template with the primary key. Does not add the d d m template to the database.
	 *
	 * @param templateId the primary key for the new d d m template
	 * @return the new d d m template
	 */
	public DDMTemplate create(long templateId) {
		DDMTemplate ddmTemplate = new DDMTemplateImpl();

		ddmTemplate.setNew(true);
		ddmTemplate.setPrimaryKey(templateId);

		String uuid = PortalUUIDUtil.generate();

		ddmTemplate.setUuid(uuid);

		return ddmTemplate;
	}

	/**
	 * Removes the d d m template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the d d m template
	 * @return the d d m template that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a d d m template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DDMTemplate remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the d d m template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param templateId the primary key of the d d m template
	 * @return the d d m template that was removed
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate remove(long templateId)
		throws NoSuchTemplateException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DDMTemplate ddmTemplate = (DDMTemplate)session.get(DDMTemplateImpl.class,
					Long.valueOf(templateId));

			if (ddmTemplate == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + templateId);
				}

				throw new NoSuchTemplateException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					templateId);
			}

			return ddmTemplatePersistence.remove(ddmTemplate);
		}
		catch (NoSuchTemplateException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Removes the d d m template from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddmTemplate the d d m template
	 * @return the d d m template that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DDMTemplate remove(DDMTemplate ddmTemplate)
		throws SystemException {
		return super.remove(ddmTemplate);
	}

	@Override
	protected DDMTemplate removeImpl(DDMTemplate ddmTemplate)
		throws SystemException {
		ddmTemplate = toUnwrappedModel(ddmTemplate);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, ddmTemplate);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		DDMTemplateModelImpl ddmTemplateModelImpl = (DDMTemplateModelImpl)ddmTemplate;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				ddmTemplateModelImpl.getUuid(),
				Long.valueOf(ddmTemplateModelImpl.getGroupId())
			});

		EntityCacheUtil.removeResult(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateImpl.class, ddmTemplate.getPrimaryKey());

		return ddmTemplate;
	}

	@Override
	public DDMTemplate updateImpl(
		com.liferay.portlet.dynamicdatamapping.model.DDMTemplate ddmTemplate,
		boolean merge) throws SystemException {
		ddmTemplate = toUnwrappedModel(ddmTemplate);

		boolean isNew = ddmTemplate.isNew();

		DDMTemplateModelImpl ddmTemplateModelImpl = (DDMTemplateModelImpl)ddmTemplate;

		if (Validator.isNull(ddmTemplate.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			ddmTemplate.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, ddmTemplate, merge);

			ddmTemplate.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !DDMTemplateModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((ddmTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmTemplateModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { ddmTemplateModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((ddmTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(ddmTemplateModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						Long.valueOf(ddmTemplateModelImpl.getGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((ddmTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STRUCTUREID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(ddmTemplateModelImpl.getOriginalStructureId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_STRUCTUREID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STRUCTUREID,
					args);

				args = new Object[] {
						Long.valueOf(ddmTemplateModelImpl.getStructureId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_STRUCTUREID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STRUCTUREID,
					args);
			}

			if ((ddmTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TYPE.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmTemplateModelImpl.getOriginalType()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_TYPE, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TYPE,
					args);

				args = new Object[] { ddmTemplateModelImpl.getType() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_TYPE, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TYPE,
					args);
			}

			if ((ddmTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LANGUAGE.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmTemplateModelImpl.getOriginalLanguage()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_LANGUAGE, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LANGUAGE,
					args);

				args = new Object[] { ddmTemplateModelImpl.getLanguage() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_LANGUAGE, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LANGUAGE,
					args);
			}

			if ((ddmTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_S_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(ddmTemplateModelImpl.getOriginalStructureId()),
						
						ddmTemplateModelImpl.getOriginalType()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_S_T, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_S_T,
					args);

				args = new Object[] {
						Long.valueOf(ddmTemplateModelImpl.getStructureId()),
						
						ddmTemplateModelImpl.getType()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_S_T, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_S_T,
					args);
			}

			if ((ddmTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_S_T_M.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(ddmTemplateModelImpl.getOriginalStructureId()),
						
						ddmTemplateModelImpl.getOriginalType(),
						
						ddmTemplateModelImpl.getOriginalMode()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_S_T_M, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_S_T_M,
					args);

				args = new Object[] {
						Long.valueOf(ddmTemplateModelImpl.getStructureId()),
						
						ddmTemplateModelImpl.getType(),
						
						ddmTemplateModelImpl.getMode()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_S_T_M, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_S_T_M,
					args);
			}
		}

		EntityCacheUtil.putResult(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateImpl.class, ddmTemplate.getPrimaryKey(), ddmTemplate);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					ddmTemplate.getUuid(),
					Long.valueOf(ddmTemplate.getGroupId())
				}, ddmTemplate);
		}
		else {
			if ((ddmTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmTemplateModelImpl.getOriginalUuid(),
						Long.valueOf(ddmTemplateModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
					new Object[] {
						ddmTemplate.getUuid(),
						Long.valueOf(ddmTemplate.getGroupId())
					}, ddmTemplate);
			}
		}

		return ddmTemplate;
	}

	protected DDMTemplate toUnwrappedModel(DDMTemplate ddmTemplate) {
		if (ddmTemplate instanceof DDMTemplateImpl) {
			return ddmTemplate;
		}

		DDMTemplateImpl ddmTemplateImpl = new DDMTemplateImpl();

		ddmTemplateImpl.setNew(ddmTemplate.isNew());
		ddmTemplateImpl.setPrimaryKey(ddmTemplate.getPrimaryKey());

		ddmTemplateImpl.setUuid(ddmTemplate.getUuid());
		ddmTemplateImpl.setTemplateId(ddmTemplate.getTemplateId());
		ddmTemplateImpl.setGroupId(ddmTemplate.getGroupId());
		ddmTemplateImpl.setCompanyId(ddmTemplate.getCompanyId());
		ddmTemplateImpl.setUserId(ddmTemplate.getUserId());
		ddmTemplateImpl.setUserName(ddmTemplate.getUserName());
		ddmTemplateImpl.setCreateDate(ddmTemplate.getCreateDate());
		ddmTemplateImpl.setModifiedDate(ddmTemplate.getModifiedDate());
		ddmTemplateImpl.setStructureId(ddmTemplate.getStructureId());
		ddmTemplateImpl.setName(ddmTemplate.getName());
		ddmTemplateImpl.setDescription(ddmTemplate.getDescription());
		ddmTemplateImpl.setType(ddmTemplate.getType());
		ddmTemplateImpl.setMode(ddmTemplate.getMode());
		ddmTemplateImpl.setLanguage(ddmTemplate.getLanguage());
		ddmTemplateImpl.setScript(ddmTemplate.getScript());

		return ddmTemplateImpl;
	}

	/**
	 * Returns the d d m template with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the d d m template
	 * @return the d d m template
	 * @throws com.liferay.portal.NoSuchModelException if a d d m template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DDMTemplate findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the d d m template with the primary key or throws a {@link com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException} if it could not be found.
	 *
	 * @param templateId the primary key of the d d m template
	 * @return the d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate findByPrimaryKey(long templateId)
		throws NoSuchTemplateException, SystemException {
		DDMTemplate ddmTemplate = fetchByPrimaryKey(templateId);

		if (ddmTemplate == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + templateId);
			}

			throw new NoSuchTemplateException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				templateId);
		}

		return ddmTemplate;
	}

	/**
	 * Returns the d d m template with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the d d m template
	 * @return the d d m template, or <code>null</code> if a d d m template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DDMTemplate fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the d d m template with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param templateId the primary key of the d d m template
	 * @return the d d m template, or <code>null</code> if a d d m template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate fetchByPrimaryKey(long templateId)
		throws SystemException {
		DDMTemplate ddmTemplate = (DDMTemplate)EntityCacheUtil.getResult(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
				DDMTemplateImpl.class, templateId);

		if (ddmTemplate == _nullDDMTemplate) {
			return null;
		}

		if (ddmTemplate == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				ddmTemplate = (DDMTemplate)session.get(DDMTemplateImpl.class,
						Long.valueOf(templateId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (ddmTemplate != null) {
					cacheResult(ddmTemplate);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
						DDMTemplateImpl.class, templateId, _nullDDMTemplate);
				}

				closeSession(session);
			}
		}

		return ddmTemplate;
	}

	/**
	 * Returns all the d d m templates where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findByUuid(String uuid) throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m templates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m templates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findByUuid(String uuid, int start, int end,
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

		List<DDMTemplate> list = (List<DDMTemplate>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

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

				list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first d d m template in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		List<DDMTemplate> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last d d m template in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		int count = countByUuid(uuid);

		List<DDMTemplate> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the d d m templates before and after the current d d m template in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param templateId the primary key of the current d d m template
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate[] findByUuid_PrevAndNext(long templateId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		DDMTemplate ddmTemplate = findByPrimaryKey(templateId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplate[] array = new DDMTemplateImpl[3];

			array[0] = getByUuid_PrevAndNext(session, ddmTemplate, uuid,
					orderByComparator, true);

			array[1] = ddmTemplate;

			array[2] = getByUuid_PrevAndNext(session, ddmTemplate, uuid,
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

	protected DDMTemplate getByUuid_PrevAndNext(Session session,
		DDMTemplate ddmTemplate, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(ddmTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the d d m template where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate findByUUID_G(String uuid, long groupId)
		throws NoSuchTemplateException, SystemException {
		DDMTemplate ddmTemplate = fetchByUUID_G(uuid, groupId);

		if (ddmTemplate == null) {
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

			throw new NoSuchTemplateException(msg.toString());
		}

		return ddmTemplate;
	}

	/**
	 * Returns the d d m template where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the d d m template where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

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

				List<DDMTemplate> list = q.list();

				result = list;

				DDMTemplate ddmTemplate = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					ddmTemplate = list.get(0);

					cacheResult(ddmTemplate);

					if ((ddmTemplate.getUuid() == null) ||
							!ddmTemplate.getUuid().equals(uuid) ||
							(ddmTemplate.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, ddmTemplate);
					}
				}

				return ddmTemplate;
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
				return (DDMTemplate)result;
			}
		}
	}

	/**
	 * Returns all the d d m templates where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m templates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m templates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findByGroupId(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<DDMTemplate> list = (List<DDMTemplate>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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

				list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first d d m template in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		List<DDMTemplate> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last d d m template in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		int count = countByGroupId(groupId);

		List<DDMTemplate> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the d d m templates before and after the current d d m template in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param templateId the primary key of the current d d m template
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate[] findByGroupId_PrevAndNext(long templateId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		DDMTemplate ddmTemplate = findByPrimaryKey(templateId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplate[] array = new DDMTemplateImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, ddmTemplate, groupId,
					orderByComparator, true);

			array[1] = ddmTemplate;

			array[2] = getByGroupId_PrevAndNext(session, ddmTemplate, groupId,
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

	protected DDMTemplate getByGroupId_PrevAndNext(Session session,
		DDMTemplate ddmTemplate, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d d m templates that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching d d m templates that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> filterFindByGroupId(long groupId)
		throws SystemException {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m templates that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of matching d d m templates that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> filterFindByGroupId(long groupId, int start,
		int end) throws SystemException {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m templates that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m templates that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> filterFindByGroupId(long groupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(2);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DDMTemplateImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DDMTemplateImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<DDMTemplate>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the d d m templates before and after the current d d m template in the ordered set of d d m templates that the user has permission to view where groupId = &#63;.
	 *
	 * @param templateId the primary key of the current d d m template
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate[] filterFindByGroupId_PrevAndNext(long templateId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(templateId, groupId,
				orderByComparator);
		}

		DDMTemplate ddmTemplate = findByPrimaryKey(templateId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplate[] array = new DDMTemplateImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session, ddmTemplate,
					groupId, orderByComparator, true);

			array[1] = ddmTemplate;

			array[2] = filterGetByGroupId_PrevAndNext(session, ddmTemplate,
					groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMTemplate filterGetByGroupId_PrevAndNext(Session session,
		DDMTemplate ddmTemplate, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DDMTemplateImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DDMTemplateImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d d m templates where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @return the matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findByStructureId(long structureId)
		throws SystemException {
		return findByStructureId(structureId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m templates where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findByStructureId(long structureId, int start,
		int end) throws SystemException {
		return findByStructureId(structureId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m templates where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findByStructureId(long structureId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STRUCTUREID;
			finderArgs = new Object[] { structureId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_STRUCTUREID;
			finderArgs = new Object[] { structureId, start, end, orderByComparator };
		}

		List<DDMTemplate> list = (List<DDMTemplate>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2);

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

				qPos.add(structureId);

				list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first d d m template in the ordered set where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate findByStructureId_First(long structureId,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		List<DDMTemplate> list = findByStructureId(structureId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("structureId=");
			msg.append(structureId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last d d m template in the ordered set where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate findByStructureId_Last(long structureId,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		int count = countByStructureId(structureId);

		List<DDMTemplate> list = findByStructureId(structureId, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("structureId=");
			msg.append(structureId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the d d m templates before and after the current d d m template in the ordered set where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param templateId the primary key of the current d d m template
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate[] findByStructureId_PrevAndNext(long templateId,
		long structureId, OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		DDMTemplate ddmTemplate = findByPrimaryKey(templateId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplate[] array = new DDMTemplateImpl[3];

			array[0] = getByStructureId_PrevAndNext(session, ddmTemplate,
					structureId, orderByComparator, true);

			array[1] = ddmTemplate;

			array[2] = getByStructureId_PrevAndNext(session, ddmTemplate,
					structureId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMTemplate getByStructureId_PrevAndNext(Session session,
		DDMTemplate ddmTemplate, long structureId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

		query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2);

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

		qPos.add(structureId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d d m templates where type = &#63;.
	 *
	 * @param type the type
	 * @return the matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findByType(String type) throws SystemException {
		return findByType(type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m templates where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param type the type
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findByType(String type, int start, int end)
		throws SystemException {
		return findByType(type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m templates where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param type the type
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findByType(String type, int start, int end,
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

		List<DDMTemplate> list = (List<DDMTemplate>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

			if (type == null) {
				query.append(_FINDER_COLUMN_TYPE_TYPE_1);
			}
			else {
				if (type.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_TYPE_TYPE_3);
				}
				else {
					query.append(_FINDER_COLUMN_TYPE_TYPE_2);
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

				if (type != null) {
					qPos.add(type);
				}

				list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first d d m template in the ordered set where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate findByType_First(String type,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		List<DDMTemplate> list = findByType(type, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last d d m template in the ordered set where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate findByType_Last(String type,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		int count = countByType(type);

		List<DDMTemplate> list = findByType(type, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the d d m templates before and after the current d d m template in the ordered set where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param templateId the primary key of the current d d m template
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate[] findByType_PrevAndNext(long templateId, String type,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		DDMTemplate ddmTemplate = findByPrimaryKey(templateId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplate[] array = new DDMTemplateImpl[3];

			array[0] = getByType_PrevAndNext(session, ddmTemplate, type,
					orderByComparator, true);

			array[1] = ddmTemplate;

			array[2] = getByType_PrevAndNext(session, ddmTemplate, type,
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

	protected DDMTemplate getByType_PrevAndNext(Session session,
		DDMTemplate ddmTemplate, String type,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

		if (type == null) {
			query.append(_FINDER_COLUMN_TYPE_TYPE_1);
		}
		else {
			if (type.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_TYPE_TYPE_3);
			}
			else {
				query.append(_FINDER_COLUMN_TYPE_TYPE_2);
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

		if (type != null) {
			qPos.add(type);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d d m templates where language = &#63;.
	 *
	 * @param language the language
	 * @return the matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findByLanguage(String language)
		throws SystemException {
		return findByLanguage(language, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the d d m templates where language = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param language the language
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findByLanguage(String language, int start, int end)
		throws SystemException {
		return findByLanguage(language, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m templates where language = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param language the language
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findByLanguage(String language, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LANGUAGE;
			finderArgs = new Object[] { language };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_LANGUAGE;
			finderArgs = new Object[] { language, start, end, orderByComparator };
		}

		List<DDMTemplate> list = (List<DDMTemplate>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

			if (language == null) {
				query.append(_FINDER_COLUMN_LANGUAGE_LANGUAGE_1);
			}
			else {
				if (language.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_LANGUAGE_LANGUAGE_3);
				}
				else {
					query.append(_FINDER_COLUMN_LANGUAGE_LANGUAGE_2);
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

				if (language != null) {
					qPos.add(language);
				}

				list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first d d m template in the ordered set where language = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param language the language
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate findByLanguage_First(String language,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		List<DDMTemplate> list = findByLanguage(language, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("language=");
			msg.append(language);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last d d m template in the ordered set where language = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param language the language
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate findByLanguage_Last(String language,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		int count = countByLanguage(language);

		List<DDMTemplate> list = findByLanguage(language, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("language=");
			msg.append(language);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the d d m templates before and after the current d d m template in the ordered set where language = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param templateId the primary key of the current d d m template
	 * @param language the language
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate[] findByLanguage_PrevAndNext(long templateId,
		String language, OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		DDMTemplate ddmTemplate = findByPrimaryKey(templateId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplate[] array = new DDMTemplateImpl[3];

			array[0] = getByLanguage_PrevAndNext(session, ddmTemplate,
					language, orderByComparator, true);

			array[1] = ddmTemplate;

			array[2] = getByLanguage_PrevAndNext(session, ddmTemplate,
					language, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMTemplate getByLanguage_PrevAndNext(Session session,
		DDMTemplate ddmTemplate, String language,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

		if (language == null) {
			query.append(_FINDER_COLUMN_LANGUAGE_LANGUAGE_1);
		}
		else {
			if (language.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_LANGUAGE_LANGUAGE_3);
			}
			else {
				query.append(_FINDER_COLUMN_LANGUAGE_LANGUAGE_2);
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

		if (language != null) {
			qPos.add(language);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d d m templates where structureId = &#63; and type = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param type the type
	 * @return the matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findByS_T(long structureId, String type)
		throws SystemException {
		return findByS_T(structureId, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m templates where structureId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param type the type
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findByS_T(long structureId, String type,
		int start, int end) throws SystemException {
		return findByS_T(structureId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m templates where structureId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param type the type
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findByS_T(long structureId, String type,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_S_T;
			finderArgs = new Object[] { structureId, type };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_S_T;
			finderArgs = new Object[] {
					structureId, type,
					
					start, end, orderByComparator
				};
		}

		List<DDMTemplate> list = (List<DDMTemplate>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_S_T_STRUCTUREID_2);

			if (type == null) {
				query.append(_FINDER_COLUMN_S_T_TYPE_1);
			}
			else {
				if (type.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_S_T_TYPE_3);
				}
				else {
					query.append(_FINDER_COLUMN_S_T_TYPE_2);
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

				qPos.add(structureId);

				if (type != null) {
					qPos.add(type);
				}

				list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first d d m template in the ordered set where structureId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate findByS_T_First(long structureId, String type,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		List<DDMTemplate> list = findByS_T(structureId, type, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("structureId=");
			msg.append(structureId);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last d d m template in the ordered set where structureId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate findByS_T_Last(long structureId, String type,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		int count = countByS_T(structureId, type);

		List<DDMTemplate> list = findByS_T(structureId, type, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("structureId=");
			msg.append(structureId);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the d d m templates before and after the current d d m template in the ordered set where structureId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param templateId the primary key of the current d d m template
	 * @param structureId the structure ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate[] findByS_T_PrevAndNext(long templateId,
		long structureId, String type, OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		DDMTemplate ddmTemplate = findByPrimaryKey(templateId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplate[] array = new DDMTemplateImpl[3];

			array[0] = getByS_T_PrevAndNext(session, ddmTemplate, structureId,
					type, orderByComparator, true);

			array[1] = ddmTemplate;

			array[2] = getByS_T_PrevAndNext(session, ddmTemplate, structureId,
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

	protected DDMTemplate getByS_T_PrevAndNext(Session session,
		DDMTemplate ddmTemplate, long structureId, String type,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

		query.append(_FINDER_COLUMN_S_T_STRUCTUREID_2);

		if (type == null) {
			query.append(_FINDER_COLUMN_S_T_TYPE_1);
		}
		else {
			if (type.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_S_T_TYPE_3);
			}
			else {
				query.append(_FINDER_COLUMN_S_T_TYPE_2);
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

		qPos.add(structureId);

		if (type != null) {
			qPos.add(type);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d d m templates where structureId = &#63; and type = &#63; and mode = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param type the type
	 * @param mode the mode
	 * @return the matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findByS_T_M(long structureId, String type,
		String mode) throws SystemException {
		return findByS_T_M(structureId, type, mode, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m templates where structureId = &#63; and type = &#63; and mode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param type the type
	 * @param mode the mode
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findByS_T_M(long structureId, String type,
		String mode, int start, int end) throws SystemException {
		return findByS_T_M(structureId, type, mode, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m templates where structureId = &#63; and type = &#63; and mode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param type the type
	 * @param mode the mode
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findByS_T_M(long structureId, String type,
		String mode, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_S_T_M;
			finderArgs = new Object[] { structureId, type, mode };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_S_T_M;
			finderArgs = new Object[] {
					structureId, type, mode,
					
					start, end, orderByComparator
				};
		}

		List<DDMTemplate> list = (List<DDMTemplate>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_S_T_M_STRUCTUREID_2);

			if (type == null) {
				query.append(_FINDER_COLUMN_S_T_M_TYPE_1);
			}
			else {
				if (type.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_S_T_M_TYPE_3);
				}
				else {
					query.append(_FINDER_COLUMN_S_T_M_TYPE_2);
				}
			}

			if (mode == null) {
				query.append(_FINDER_COLUMN_S_T_M_MODE_1);
			}
			else {
				if (mode.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_S_T_M_MODE_3);
				}
				else {
					query.append(_FINDER_COLUMN_S_T_M_MODE_2);
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

				qPos.add(structureId);

				if (type != null) {
					qPos.add(type);
				}

				if (mode != null) {
					qPos.add(mode);
				}

				list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first d d m template in the ordered set where structureId = &#63; and type = &#63; and mode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param type the type
	 * @param mode the mode
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate findByS_T_M_First(long structureId, String type,
		String mode, OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		List<DDMTemplate> list = findByS_T_M(structureId, type, mode, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("structureId=");
			msg.append(structureId);

			msg.append(", type=");
			msg.append(type);

			msg.append(", mode=");
			msg.append(mode);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last d d m template in the ordered set where structureId = &#63; and type = &#63; and mode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param type the type
	 * @param mode the mode
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate findByS_T_M_Last(long structureId, String type,
		String mode, OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		int count = countByS_T_M(structureId, type, mode);

		List<DDMTemplate> list = findByS_T_M(structureId, type, mode,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("structureId=");
			msg.append(structureId);

			msg.append(", type=");
			msg.append(type);

			msg.append(", mode=");
			msg.append(mode);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the d d m templates before and after the current d d m template in the ordered set where structureId = &#63; and type = &#63; and mode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param templateId the primary key of the current d d m template
	 * @param structureId the structure ID
	 * @param type the type
	 * @param mode the mode
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate[] findByS_T_M_PrevAndNext(long templateId,
		long structureId, String type, String mode,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		DDMTemplate ddmTemplate = findByPrimaryKey(templateId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplate[] array = new DDMTemplateImpl[3];

			array[0] = getByS_T_M_PrevAndNext(session, ddmTemplate,
					structureId, type, mode, orderByComparator, true);

			array[1] = ddmTemplate;

			array[2] = getByS_T_M_PrevAndNext(session, ddmTemplate,
					structureId, type, mode, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMTemplate getByS_T_M_PrevAndNext(Session session,
		DDMTemplate ddmTemplate, long structureId, String type, String mode,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

		query.append(_FINDER_COLUMN_S_T_M_STRUCTUREID_2);

		if (type == null) {
			query.append(_FINDER_COLUMN_S_T_M_TYPE_1);
		}
		else {
			if (type.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_S_T_M_TYPE_3);
			}
			else {
				query.append(_FINDER_COLUMN_S_T_M_TYPE_2);
			}
		}

		if (mode == null) {
			query.append(_FINDER_COLUMN_S_T_M_MODE_1);
		}
		else {
			if (mode.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_S_T_M_MODE_3);
			}
			else {
				query.append(_FINDER_COLUMN_S_T_M_MODE_2);
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

		qPos.add(structureId);

		if (type != null) {
			qPos.add(type);
		}

		if (mode != null) {
			qPos.add(mode);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d d m templates.
	 *
	 * @return the d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<DDMTemplate> list = (List<DDMTemplate>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DDMTEMPLATE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DDMTEMPLATE;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the d d m templates where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (DDMTemplate ddmTemplate : findByUuid(uuid)) {
			ddmTemplatePersistence.remove(ddmTemplate);
		}
	}

	/**
	 * Removes the d d m template where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchTemplateException, SystemException {
		DDMTemplate ddmTemplate = findByUUID_G(uuid, groupId);

		ddmTemplatePersistence.remove(ddmTemplate);
	}

	/**
	 * Removes all the d d m templates where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (DDMTemplate ddmTemplate : findByGroupId(groupId)) {
			ddmTemplatePersistence.remove(ddmTemplate);
		}
	}

	/**
	 * Removes all the d d m templates where structureId = &#63; from the database.
	 *
	 * @param structureId the structure ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByStructureId(long structureId) throws SystemException {
		for (DDMTemplate ddmTemplate : findByStructureId(structureId)) {
			ddmTemplatePersistence.remove(ddmTemplate);
		}
	}

	/**
	 * Removes all the d d m templates where type = &#63; from the database.
	 *
	 * @param type the type
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByType(String type) throws SystemException {
		for (DDMTemplate ddmTemplate : findByType(type)) {
			ddmTemplatePersistence.remove(ddmTemplate);
		}
	}

	/**
	 * Removes all the d d m templates where language = &#63; from the database.
	 *
	 * @param language the language
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByLanguage(String language) throws SystemException {
		for (DDMTemplate ddmTemplate : findByLanguage(language)) {
			ddmTemplatePersistence.remove(ddmTemplate);
		}
	}

	/**
	 * Removes all the d d m templates where structureId = &#63; and type = &#63; from the database.
	 *
	 * @param structureId the structure ID
	 * @param type the type
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByS_T(long structureId, String type)
		throws SystemException {
		for (DDMTemplate ddmTemplate : findByS_T(structureId, type)) {
			ddmTemplatePersistence.remove(ddmTemplate);
		}
	}

	/**
	 * Removes all the d d m templates where structureId = &#63; and type = &#63; and mode = &#63; from the database.
	 *
	 * @param structureId the structure ID
	 * @param type the type
	 * @param mode the mode
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByS_T_M(long structureId, String type, String mode)
		throws SystemException {
		for (DDMTemplate ddmTemplate : findByS_T_M(structureId, type, mode)) {
			ddmTemplatePersistence.remove(ddmTemplate);
		}
	}

	/**
	 * Removes all the d d m templates from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (DDMTemplate ddmTemplate : findAll()) {
			ddmTemplatePersistence.remove(ddmTemplate);
		}
	}

	/**
	 * Returns the number of d d m templates where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMTEMPLATE_WHERE);

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
	 * Returns the number of d d m templates where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMTEMPLATE_WHERE);

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
	 * Returns the number of d d m templates where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_GROUPID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of d d m templates that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching d d m templates that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByGroupId(long groupId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_DDMTEMPLATE_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the number of d d m templates where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @return the number of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public int countByStructureId(long structureId) throws SystemException {
		Object[] finderArgs = new Object[] { structureId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_STRUCTUREID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(structureId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_STRUCTUREID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of d d m templates where type = &#63;.
	 *
	 * @param type the type
	 * @return the number of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public int countByType(String type) throws SystemException {
		Object[] finderArgs = new Object[] { type };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_TYPE,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMTEMPLATE_WHERE);

			if (type == null) {
				query.append(_FINDER_COLUMN_TYPE_TYPE_1);
			}
			else {
				if (type.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_TYPE_TYPE_3);
				}
				else {
					query.append(_FINDER_COLUMN_TYPE_TYPE_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (type != null) {
					qPos.add(type);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_TYPE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of d d m templates where language = &#63;.
	 *
	 * @param language the language
	 * @return the number of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public int countByLanguage(String language) throws SystemException {
		Object[] finderArgs = new Object[] { language };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_LANGUAGE,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMTEMPLATE_WHERE);

			if (language == null) {
				query.append(_FINDER_COLUMN_LANGUAGE_LANGUAGE_1);
			}
			else {
				if (language.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_LANGUAGE_LANGUAGE_3);
				}
				else {
					query.append(_FINDER_COLUMN_LANGUAGE_LANGUAGE_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (language != null) {
					qPos.add(language);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_LANGUAGE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of d d m templates where structureId = &#63; and type = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param type the type
	 * @return the number of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public int countByS_T(long structureId, String type)
		throws SystemException {
		Object[] finderArgs = new Object[] { structureId, type };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_S_T,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_S_T_STRUCTUREID_2);

			if (type == null) {
				query.append(_FINDER_COLUMN_S_T_TYPE_1);
			}
			else {
				if (type.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_S_T_TYPE_3);
				}
				else {
					query.append(_FINDER_COLUMN_S_T_TYPE_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(structureId);

				if (type != null) {
					qPos.add(type);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_S_T, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of d d m templates where structureId = &#63; and type = &#63; and mode = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param type the type
	 * @param mode the mode
	 * @return the number of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public int countByS_T_M(long structureId, String type, String mode)
		throws SystemException {
		Object[] finderArgs = new Object[] { structureId, type, mode };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_S_T_M,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DDMTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_S_T_M_STRUCTUREID_2);

			if (type == null) {
				query.append(_FINDER_COLUMN_S_T_M_TYPE_1);
			}
			else {
				if (type.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_S_T_M_TYPE_3);
				}
				else {
					query.append(_FINDER_COLUMN_S_T_M_TYPE_2);
				}
			}

			if (mode == null) {
				query.append(_FINDER_COLUMN_S_T_M_MODE_1);
			}
			else {
				if (mode.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_S_T_M_MODE_3);
				}
				else {
					query.append(_FINDER_COLUMN_S_T_M_MODE_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(structureId);

				if (type != null) {
					qPos.add(type);
				}

				if (mode != null) {
					qPos.add(mode);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_S_T_M,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of d d m templates.
	 *
	 * @return the number of d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DDMTEMPLATE);

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
	 * Initializes the d d m template persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.dynamicdatamapping.model.DDMTemplate")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<DDMTemplate>> listenersList = new ArrayList<ModelListener<DDMTemplate>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<DDMTemplate>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(DDMTemplateImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = DDMContentPersistence.class)
	protected DDMContentPersistence ddmContentPersistence;
	@BeanReference(type = DDMStorageLinkPersistence.class)
	protected DDMStorageLinkPersistence ddmStorageLinkPersistence;
	@BeanReference(type = DDMStructurePersistence.class)
	protected DDMStructurePersistence ddmStructurePersistence;
	@BeanReference(type = DDMStructureLinkPersistence.class)
	protected DDMStructureLinkPersistence ddmStructureLinkPersistence;
	@BeanReference(type = DDMTemplatePersistence.class)
	protected DDMTemplatePersistence ddmTemplatePersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_DDMTEMPLATE = "SELECT ddmTemplate FROM DDMTemplate ddmTemplate";
	private static final String _SQL_SELECT_DDMTEMPLATE_WHERE = "SELECT ddmTemplate FROM DDMTemplate ddmTemplate WHERE ";
	private static final String _SQL_COUNT_DDMTEMPLATE = "SELECT COUNT(ddmTemplate) FROM DDMTemplate ddmTemplate";
	private static final String _SQL_COUNT_DDMTEMPLATE_WHERE = "SELECT COUNT(ddmTemplate) FROM DDMTemplate ddmTemplate WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "ddmTemplate.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "ddmTemplate.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(ddmTemplate.uuid IS NULL OR ddmTemplate.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "ddmTemplate.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "ddmTemplate.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(ddmTemplate.uuid IS NULL OR ddmTemplate.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "ddmTemplate.groupId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "ddmTemplate.groupId = ?";
	private static final String _FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2 = "ddmTemplate.structureId = ?";
	private static final String _FINDER_COLUMN_TYPE_TYPE_1 = "ddmTemplate.type IS NULL";
	private static final String _FINDER_COLUMN_TYPE_TYPE_2 = "ddmTemplate.type = ?";
	private static final String _FINDER_COLUMN_TYPE_TYPE_3 = "(ddmTemplate.type IS NULL OR ddmTemplate.type = ?)";
	private static final String _FINDER_COLUMN_LANGUAGE_LANGUAGE_1 = "ddmTemplate.language IS NULL";
	private static final String _FINDER_COLUMN_LANGUAGE_LANGUAGE_2 = "ddmTemplate.language = ?";
	private static final String _FINDER_COLUMN_LANGUAGE_LANGUAGE_3 = "(ddmTemplate.language IS NULL OR ddmTemplate.language = ?)";
	private static final String _FINDER_COLUMN_S_T_STRUCTUREID_2 = "ddmTemplate.structureId = ? AND ";
	private static final String _FINDER_COLUMN_S_T_TYPE_1 = "ddmTemplate.type IS NULL";
	private static final String _FINDER_COLUMN_S_T_TYPE_2 = "ddmTemplate.type = ?";
	private static final String _FINDER_COLUMN_S_T_TYPE_3 = "(ddmTemplate.type IS NULL OR ddmTemplate.type = ?)";
	private static final String _FINDER_COLUMN_S_T_M_STRUCTUREID_2 = "ddmTemplate.structureId = ? AND ";
	private static final String _FINDER_COLUMN_S_T_M_TYPE_1 = "ddmTemplate.type IS NULL AND ";
	private static final String _FINDER_COLUMN_S_T_M_TYPE_2 = "ddmTemplate.type = ? AND ";
	private static final String _FINDER_COLUMN_S_T_M_TYPE_3 = "(ddmTemplate.type IS NULL OR ddmTemplate.type = ?) AND ";
	private static final String _FINDER_COLUMN_S_T_M_MODE_1 = "ddmTemplate.mode IS NULL";
	private static final String _FINDER_COLUMN_S_T_M_MODE_2 = "ddmTemplate.mode = ?";
	private static final String _FINDER_COLUMN_S_T_M_MODE_3 = "(ddmTemplate.mode IS NULL OR ddmTemplate.mode = ?)";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "ddmTemplate.templateId";
	private static final String _FILTER_SQL_SELECT_DDMTEMPLATE_WHERE = "SELECT DISTINCT {ddmTemplate.*} FROM DDMTemplate ddmTemplate WHERE ";
	private static final String _FILTER_SQL_SELECT_DDMTEMPLATE_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {DDMTemplate.*} FROM (SELECT DISTINCT ddmTemplate.templateId FROM DDMTemplate ddmTemplate WHERE ";
	private static final String _FILTER_SQL_SELECT_DDMTEMPLATE_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN DDMTemplate ON TEMP_TABLE.templateId = DDMTemplate.templateId";
	private static final String _FILTER_SQL_COUNT_DDMTEMPLATE_WHERE = "SELECT COUNT(DISTINCT ddmTemplate.templateId) AS COUNT_VALUE FROM DDMTemplate ddmTemplate WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "ddmTemplate";
	private static final String _FILTER_ENTITY_TABLE = "DDMTemplate";
	private static final String _ORDER_BY_ENTITY_ALIAS = "ddmTemplate.";
	private static final String _ORDER_BY_ENTITY_TABLE = "DDMTemplate.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DDMTemplate exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DDMTemplate exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(DDMTemplatePersistenceImpl.class);
	private static DDMTemplate _nullDDMTemplate = new DDMTemplateImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<DDMTemplate> toCacheModel() {
				return _nullDDMTemplateCacheModel;
			}
		};

	private static CacheModel<DDMTemplate> _nullDDMTemplateCacheModel = new CacheModel<DDMTemplate>() {
			public DDMTemplate toEntityModel() {
				return _nullDDMTemplate;
			}
		};
}