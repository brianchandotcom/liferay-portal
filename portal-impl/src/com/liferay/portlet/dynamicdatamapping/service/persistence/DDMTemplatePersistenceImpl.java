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
import com.liferay.portal.service.persistence.ImagePersistence;
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
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			DDMTemplateModelImpl.UUID_COLUMN_BITMASK |
			DDMTemplateModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
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
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_CLASSPK = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByClassPK",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CLASSPK =
		new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByClassPK",
			new String[] { Long.class.getName() },
			DDMTemplateModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CLASSPK = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByClassPK",
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
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			DDMTemplateModelImpl.GROUPID_COLUMN_BITMASK |
			DDMTemplateModelImpl.CLASSNAMEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_T = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_T",
			new String[] { Long.class.getName(), String.class.getName() },
			DDMTemplateModelImpl.GROUPID_COLUMN_BITMASK |
			DDMTemplateModelImpl.TEMPLATEKEY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_T = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_T",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_C = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			DDMTemplateModelImpl.GROUPID_COLUMN_BITMASK |
			DDMTemplateModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			DDMTemplateModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_C = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C_T = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_T = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			DDMTemplateModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			DDMTemplateModelImpl.CLASSPK_COLUMN_BITMASK |
			DDMTemplateModelImpl.TYPE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C_T = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C_T_M = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C_T_M",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_T_M =
		new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C_T_M",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName()
			},
			DDMTemplateModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			DDMTemplateModelImpl.CLASSPK_COLUMN_BITMASK |
			DDMTemplateModelImpl.TYPE_COLUMN_BITMASK |
			DDMTemplateModelImpl.MODE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C_T_M = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_T_M",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
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

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_T,
			new Object[] {
				Long.valueOf(ddmTemplate.getGroupId()),
				
			ddmTemplate.getTemplateKey()
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

		clearUniqueFindersCache(ddmTemplate);
	}

	@Override
	public void clearCache(List<DDMTemplate> ddmTemplates) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DDMTemplate ddmTemplate : ddmTemplates) {
			EntityCacheUtil.removeResult(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
				DDMTemplateImpl.class, ddmTemplate.getPrimaryKey());

			clearUniqueFindersCache(ddmTemplate);
		}
	}

	protected void clearUniqueFindersCache(DDMTemplate ddmTemplate) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				ddmTemplate.getUuid(), Long.valueOf(ddmTemplate.getGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_T,
			new Object[] {
				Long.valueOf(ddmTemplate.getGroupId()),
				
			ddmTemplate.getTemplateKey()
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
	 * @param templateId the primary key of the d d m template
	 * @return the d d m template that was removed
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate remove(long templateId)
		throws NoSuchTemplateException, SystemException {
		return remove(Long.valueOf(templateId));
	}

	/**
	 * Removes the d d m template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the d d m template
	 * @return the d d m template that was removed
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DDMTemplate remove(Serializable primaryKey)
		throws NoSuchTemplateException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DDMTemplate ddmTemplate = (DDMTemplate)session.get(DDMTemplateImpl.class,
					primaryKey);

			if (ddmTemplate == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTemplateException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(ddmTemplate);
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

	@Override
	protected DDMTemplate removeImpl(DDMTemplate ddmTemplate)
		throws SystemException {
		ddmTemplate = toUnwrappedModel(ddmTemplate);

		Session session = null;

		try {
			session = openSession();

			if (ddmTemplate.isCachedModel()) {
				ddmTemplate = (DDMTemplate)session.get(DDMTemplateImpl.class,
						ddmTemplate.getPrimaryKeyObj());
			}

			if (ddmTemplate != null) {
				session.delete(ddmTemplate);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (ddmTemplate != null) {
			clearCache(ddmTemplate);
		}

		return ddmTemplate;
	}

	@Override
	public DDMTemplate updateImpl(
		com.liferay.portlet.dynamicdatamapping.model.DDMTemplate ddmTemplate)
		throws SystemException {
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

			if (ddmTemplate.isNew()) {
				session.save(ddmTemplate);

				ddmTemplate.setNew(false);
			}
			else {
				session.merge(ddmTemplate);
			}
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
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmTemplateModelImpl.getOriginalUuid(),
						Long.valueOf(ddmTemplateModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						ddmTemplateModelImpl.getUuid(),
						Long.valueOf(ddmTemplateModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
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
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CLASSPK.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(ddmTemplateModelImpl.getOriginalClassPK())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_CLASSPK, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CLASSPK,
					args);

				args = new Object[] {
						Long.valueOf(ddmTemplateModelImpl.getClassPK())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_CLASSPK, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CLASSPK,
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
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(ddmTemplateModelImpl.getOriginalGroupId()),
						Long.valueOf(ddmTemplateModelImpl.getOriginalClassNameId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C,
					args);

				args = new Object[] {
						Long.valueOf(ddmTemplateModelImpl.getGroupId()),
						Long.valueOf(ddmTemplateModelImpl.getClassNameId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C,
					args);
			}

			if ((ddmTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(ddmTemplateModelImpl.getOriginalGroupId()),
						Long.valueOf(ddmTemplateModelImpl.getOriginalClassNameId()),
						Long.valueOf(ddmTemplateModelImpl.getOriginalClassPK())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C,
					args);

				args = new Object[] {
						Long.valueOf(ddmTemplateModelImpl.getGroupId()),
						Long.valueOf(ddmTemplateModelImpl.getClassNameId()),
						Long.valueOf(ddmTemplateModelImpl.getClassPK())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C,
					args);
			}

			if ((ddmTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(ddmTemplateModelImpl.getOriginalClassNameId()),
						Long.valueOf(ddmTemplateModelImpl.getOriginalClassPK()),
						
						ddmTemplateModelImpl.getOriginalType()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C_T, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_T,
					args);

				args = new Object[] {
						Long.valueOf(ddmTemplateModelImpl.getClassNameId()),
						Long.valueOf(ddmTemplateModelImpl.getClassPK()),
						
						ddmTemplateModelImpl.getType()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C_T, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_T,
					args);
			}

			if ((ddmTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_T_M.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(ddmTemplateModelImpl.getOriginalClassNameId()),
						Long.valueOf(ddmTemplateModelImpl.getOriginalClassPK()),
						
						ddmTemplateModelImpl.getOriginalType(),
						
						ddmTemplateModelImpl.getOriginalMode()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C_T_M, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_T_M,
					args);

				args = new Object[] {
						Long.valueOf(ddmTemplateModelImpl.getClassNameId()),
						Long.valueOf(ddmTemplateModelImpl.getClassPK()),
						
						ddmTemplateModelImpl.getType(),
						
						ddmTemplateModelImpl.getMode()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C_T_M, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_T_M,
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

			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_T,
				new Object[] {
					Long.valueOf(ddmTemplate.getGroupId()),
					
				ddmTemplate.getTemplateKey()
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

			if ((ddmTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_G_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(ddmTemplateModelImpl.getOriginalGroupId()),
						
						ddmTemplateModelImpl.getOriginalTemplateKey()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_T, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_T, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_T,
					new Object[] {
						Long.valueOf(ddmTemplate.getGroupId()),
						
					ddmTemplate.getTemplateKey()
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
		ddmTemplateImpl.setClassNameId(ddmTemplate.getClassNameId());
		ddmTemplateImpl.setClassPK(ddmTemplate.getClassPK());
		ddmTemplateImpl.setTemplateKey(ddmTemplate.getTemplateKey());
		ddmTemplateImpl.setName(ddmTemplate.getName());
		ddmTemplateImpl.setDescription(ddmTemplate.getDescription());
		ddmTemplateImpl.setType(ddmTemplate.getType());
		ddmTemplateImpl.setMode(ddmTemplate.getMode());
		ddmTemplateImpl.setLanguage(ddmTemplate.getLanguage());
		ddmTemplateImpl.setScript(ddmTemplate.getScript());
		ddmTemplateImpl.setCacheable(ddmTemplate.isCacheable());
		ddmTemplateImpl.setSmallImage(ddmTemplate.isSmallImage());
		ddmTemplateImpl.setSmallImageId(ddmTemplate.getSmallImageId());
		ddmTemplateImpl.setSmallImageURL(ddmTemplate.getSmallImageURL());

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

		if ((list != null) && !list.isEmpty()) {
			for (DDMTemplate ddmTemplate : list) {
				if (!Validator.equals(uuid, ddmTemplate.getUuid())) {
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
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		DDMTemplate ddmTemplate = fetchByUuid_First(uuid, orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the first d d m template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<DDMTemplate> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m template in the ordered set where uuid = &#63;.
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
		DDMTemplate ddmTemplate = fetchByUuid_Last(uuid, orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the last d d m template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<DDMTemplate> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m templates before and after the current d d m template in the ordered set where uuid = &#63;.
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

		if (result instanceof DDMTemplate) {
			DDMTemplate ddmTemplate = (DDMTemplate)result;

			if (!Validator.equals(uuid, ddmTemplate.getUuid()) ||
					(groupId != ddmTemplate.getGroupId())) {
				result = null;
			}
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
	 * Returns all the d d m templates where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findByUuid_C(String uuid, long companyId)
		throws SystemException {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m templates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findByUuid_C(String uuid, long companyId,
		int start, int end) throws SystemException {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m templates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findByUuid_C(String uuid, long companyId,
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

		List<DDMTemplate> list = (List<DDMTemplate>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DDMTemplate ddmTemplate : list) {
				if (!Validator.equals(uuid, ddmTemplate.getUuid()) ||
						(companyId != ddmTemplate.getCompanyId())) {
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

			query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

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
	 * Returns the first d d m template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate findByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		DDMTemplate ddmTemplate = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the first d d m template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<DDMTemplate> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		DDMTemplate ddmTemplate = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the last d d m template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid_C(uuid, companyId);

		List<DDMTemplate> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m templates before and after the current d d m template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param templateId the primary key of the current d d m template
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate[] findByUuid_C_PrevAndNext(long templateId, String uuid,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		DDMTemplate ddmTemplate = findByPrimaryKey(templateId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplate[] array = new DDMTemplateImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, ddmTemplate, uuid,
					companyId, orderByComparator, true);

			array[1] = ddmTemplate;

			array[2] = getByUuid_C_PrevAndNext(session, ddmTemplate, uuid,
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

	protected DDMTemplate getByUuid_C_PrevAndNext(Session session,
		DDMTemplate ddmTemplate, String uuid, long companyId,
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

		if ((list != null) && !list.isEmpty()) {
			for (DDMTemplate ddmTemplate : list) {
				if ((groupId != ddmTemplate.getGroupId())) {
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
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		DDMTemplate ddmTemplate = fetchByGroupId_First(groupId,
				orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the first d d m template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate fetchByGroupId_First(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		List<DDMTemplate> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m template in the ordered set where groupId = &#63;.
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
		DDMTemplate ddmTemplate = fetchByGroupId_Last(groupId, orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the last d d m template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate fetchByGroupId_Last(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByGroupId(groupId);

		List<DDMTemplate> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m templates before and after the current d d m template in the ordered set where groupId = &#63;.
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
	 * Returns all the d d m templates where classPK = &#63;.
	 *
	 * @param classPK the class p k
	 * @return the matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findByClassPK(long classPK)
		throws SystemException {
		return findByClassPK(classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m templates where classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classPK the class p k
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findByClassPK(long classPK, int start, int end)
		throws SystemException {
		return findByClassPK(classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m templates where classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classPK the class p k
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findByClassPK(long classPK, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CLASSPK;
			finderArgs = new Object[] { classPK };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_CLASSPK;
			finderArgs = new Object[] { classPK, start, end, orderByComparator };
		}

		List<DDMTemplate> list = (List<DDMTemplate>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DDMTemplate ddmTemplate : list) {
				if ((classPK != ddmTemplate.getClassPK())) {
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

			query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_CLASSPK_CLASSPK_2);

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

				qPos.add(classPK);

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
	 * Returns the first d d m template in the ordered set where classPK = &#63;.
	 *
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate findByClassPK_First(long classPK,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		DDMTemplate ddmTemplate = fetchByClassPK_First(classPK,
				orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the first d d m template in the ordered set where classPK = &#63;.
	 *
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate fetchByClassPK_First(long classPK,
		OrderByComparator orderByComparator) throws SystemException {
		List<DDMTemplate> list = findByClassPK(classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m template in the ordered set where classPK = &#63;.
	 *
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate findByClassPK_Last(long classPK,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		DDMTemplate ddmTemplate = fetchByClassPK_Last(classPK, orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the last d d m template in the ordered set where classPK = &#63;.
	 *
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate fetchByClassPK_Last(long classPK,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByClassPK(classPK);

		List<DDMTemplate> list = findByClassPK(classPK, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m templates before and after the current d d m template in the ordered set where classPK = &#63;.
	 *
	 * @param templateId the primary key of the current d d m template
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate[] findByClassPK_PrevAndNext(long templateId,
		long classPK, OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		DDMTemplate ddmTemplate = findByPrimaryKey(templateId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplate[] array = new DDMTemplateImpl[3];

			array[0] = getByClassPK_PrevAndNext(session, ddmTemplate, classPK,
					orderByComparator, true);

			array[1] = ddmTemplate;

			array[2] = getByClassPK_PrevAndNext(session, ddmTemplate, classPK,
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

	protected DDMTemplate getByClassPK_PrevAndNext(Session session,
		DDMTemplate ddmTemplate, long classPK,
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

		query.append(_FINDER_COLUMN_CLASSPK_CLASSPK_2);

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

		qPos.add(classPK);

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

		if ((list != null) && !list.isEmpty()) {
			for (DDMTemplate ddmTemplate : list) {
				if (!Validator.equals(type, ddmTemplate.getType())) {
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
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate findByType_First(String type,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		DDMTemplate ddmTemplate = fetchByType_First(type, orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the first d d m template in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate fetchByType_First(String type,
		OrderByComparator orderByComparator) throws SystemException {
		List<DDMTemplate> list = findByType(type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m template in the ordered set where type = &#63;.
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
		DDMTemplate ddmTemplate = fetchByType_Last(type, orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the last d d m template in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate fetchByType_Last(String type,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByType(type);

		List<DDMTemplate> list = findByType(type, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m templates before and after the current d d m template in the ordered set where type = &#63;.
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

		if ((list != null) && !list.isEmpty()) {
			for (DDMTemplate ddmTemplate : list) {
				if (!Validator.equals(language, ddmTemplate.getLanguage())) {
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
	 * @param language the language
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate findByLanguage_First(String language,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		DDMTemplate ddmTemplate = fetchByLanguage_First(language,
				orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("language=");
		msg.append(language);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the first d d m template in the ordered set where language = &#63;.
	 *
	 * @param language the language
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate fetchByLanguage_First(String language,
		OrderByComparator orderByComparator) throws SystemException {
		List<DDMTemplate> list = findByLanguage(language, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m template in the ordered set where language = &#63;.
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
		DDMTemplate ddmTemplate = fetchByLanguage_Last(language,
				orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("language=");
		msg.append(language);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the last d d m template in the ordered set where language = &#63;.
	 *
	 * @param language the language
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate fetchByLanguage_Last(String language,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByLanguage(language);

		List<DDMTemplate> list = findByLanguage(language, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m templates before and after the current d d m template in the ordered set where language = &#63;.
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
	 * Returns all the d d m templates where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findByG_C(long groupId, long classNameId)
		throws SystemException {
		return findByG_C(groupId, classNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m templates where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findByG_C(long groupId, long classNameId,
		int start, int end) throws SystemException {
		return findByG_C(groupId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m templates where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findByG_C(long groupId, long classNameId,
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

		List<DDMTemplate> list = (List<DDMTemplate>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DDMTemplate ddmTemplate : list) {
				if ((groupId != ddmTemplate.getGroupId()) ||
						(classNameId != ddmTemplate.getClassNameId())) {
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

			query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

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
	 * Returns the first d d m template in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate findByG_C_First(long groupId, long classNameId,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		DDMTemplate ddmTemplate = fetchByG_C_First(groupId, classNameId,
				orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the first d d m template in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate fetchByG_C_First(long groupId, long classNameId,
		OrderByComparator orderByComparator) throws SystemException {
		List<DDMTemplate> list = findByG_C(groupId, classNameId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m template in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate findByG_C_Last(long groupId, long classNameId,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		DDMTemplate ddmTemplate = fetchByG_C_Last(groupId, classNameId,
				orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the last d d m template in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate fetchByG_C_Last(long groupId, long classNameId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByG_C(groupId, classNameId);

		List<DDMTemplate> list = findByG_C(groupId, classNameId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m templates before and after the current d d m template in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param templateId the primary key of the current d d m template
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate[] findByG_C_PrevAndNext(long templateId, long groupId,
		long classNameId, OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		DDMTemplate ddmTemplate = findByPrimaryKey(templateId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplate[] array = new DDMTemplateImpl[3];

			array[0] = getByG_C_PrevAndNext(session, ddmTemplate, groupId,
					classNameId, orderByComparator, true);

			array[1] = ddmTemplate;

			array[2] = getByG_C_PrevAndNext(session, ddmTemplate, groupId,
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

	protected DDMTemplate getByG_C_PrevAndNext(Session session,
		DDMTemplate ddmTemplate, long groupId, long classNameId,
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
	 * Returns all the d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the matching d d m templates that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> filterFindByG_C(long groupId, long classNameId)
		throws SystemException {
		return filterFindByG_C(groupId, classNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of matching d d m templates that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> filterFindByG_C(long groupId, long classNameId,
		int start, int end) throws SystemException {
		return filterFindByG_C(groupId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m templates that the user has permissions to view where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m templates that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> filterFindByG_C(long groupId, long classNameId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C(groupId, classNameId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
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

		query.append(_FINDER_COLUMN_G_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

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

			qPos.add(classNameId);

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
	 * Returns the d d m templates before and after the current d d m template in the ordered set of d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param templateId the primary key of the current d d m template
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate[] filterFindByG_C_PrevAndNext(long templateId,
		long groupId, long classNameId, OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_PrevAndNext(templateId, groupId, classNameId,
				orderByComparator);
		}

		DDMTemplate ddmTemplate = findByPrimaryKey(templateId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplate[] array = new DDMTemplateImpl[3];

			array[0] = filterGetByG_C_PrevAndNext(session, ddmTemplate,
					groupId, classNameId, orderByComparator, true);

			array[1] = ddmTemplate;

			array[2] = filterGetByG_C_PrevAndNext(session, ddmTemplate,
					groupId, classNameId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMTemplate filterGetByG_C_PrevAndNext(Session session,
		DDMTemplate ddmTemplate, long groupId, long classNameId,
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

		query.append(_FINDER_COLUMN_G_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

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

		qPos.add(classNameId);

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
	 * Returns the d d m template where groupId = &#63; and templateKey = &#63; or throws a {@link com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param templateKey the template key
	 * @return the matching d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate findByG_T(long groupId, String templateKey)
		throws NoSuchTemplateException, SystemException {
		DDMTemplate ddmTemplate = fetchByG_T(groupId, templateKey);

		if (ddmTemplate == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", templateKey=");
			msg.append(templateKey);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchTemplateException(msg.toString());
		}

		return ddmTemplate;
	}

	/**
	 * Returns the d d m template where groupId = &#63; and templateKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param templateKey the template key
	 * @return the matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate fetchByG_T(long groupId, String templateKey)
		throws SystemException {
		return fetchByG_T(groupId, templateKey, true);
	}

	/**
	 * Returns the d d m template where groupId = &#63; and templateKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param templateKey the template key
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate fetchByG_T(long groupId, String templateKey,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, templateKey };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_T,
					finderArgs, this);
		}

		if (result instanceof DDMTemplate) {
			DDMTemplate ddmTemplate = (DDMTemplate)result;

			if ((groupId != ddmTemplate.getGroupId()) ||
					!Validator.equals(templateKey, ddmTemplate.getTemplateKey())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_G_T_GROUPID_2);

			if (templateKey == null) {
				query.append(_FINDER_COLUMN_G_T_TEMPLATEKEY_1);
			}
			else {
				if (templateKey.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_T_TEMPLATEKEY_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_T_TEMPLATEKEY_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (templateKey != null) {
					qPos.add(templateKey);
				}

				List<DDMTemplate> list = q.list();

				result = list;

				DDMTemplate ddmTemplate = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_T,
						finderArgs, list);
				}
				else {
					ddmTemplate = list.get(0);

					cacheResult(ddmTemplate);

					if ((ddmTemplate.getGroupId() != groupId) ||
							(ddmTemplate.getTemplateKey() == null) ||
							!ddmTemplate.getTemplateKey().equals(templateKey)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_T,
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
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_T,
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
	 * Returns all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findByG_C_C(long groupId, long classNameId,
		long classPK) throws SystemException {
		return findByG_C_C(groupId, classNameId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findByG_C_C(long groupId, long classNameId,
		long classPK, int start, int end) throws SystemException {
		return findByG_C_C(groupId, classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findByG_C_C(long groupId, long classNameId,
		long classPK, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C;
			finderArgs = new Object[] { groupId, classNameId, classPK };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_C;
			finderArgs = new Object[] {
					groupId, classNameId, classPK,
					
					start, end, orderByComparator
				};
		}

		List<DDMTemplate> list = (List<DDMTemplate>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DDMTemplate ddmTemplate : list) {
				if ((groupId != ddmTemplate.getGroupId()) ||
						(classNameId != ddmTemplate.getClassNameId()) ||
						(classPK != ddmTemplate.getClassPK())) {
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

			query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

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

				qPos.add(classPK);

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
	 * Returns the first d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate findByG_C_C_First(long groupId, long classNameId,
		long classPK, OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		DDMTemplate ddmTemplate = fetchByG_C_C_First(groupId, classNameId,
				classPK, orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the first d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate fetchByG_C_C_First(long groupId, long classNameId,
		long classPK, OrderByComparator orderByComparator)
		throws SystemException {
		List<DDMTemplate> list = findByG_C_C(groupId, classNameId, classPK, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate findByG_C_C_Last(long groupId, long classNameId,
		long classPK, OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		DDMTemplate ddmTemplate = fetchByG_C_C_Last(groupId, classNameId,
				classPK, orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the last d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate fetchByG_C_C_Last(long groupId, long classNameId,
		long classPK, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByG_C_C(groupId, classNameId, classPK);

		List<DDMTemplate> list = findByG_C_C(groupId, classNameId, classPK,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m templates before and after the current d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param templateId the primary key of the current d d m template
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate[] findByG_C_C_PrevAndNext(long templateId, long groupId,
		long classNameId, long classPK, OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		DDMTemplate ddmTemplate = findByPrimaryKey(templateId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplate[] array = new DDMTemplateImpl[3];

			array[0] = getByG_C_C_PrevAndNext(session, ddmTemplate, groupId,
					classNameId, classPK, orderByComparator, true);

			array[1] = ddmTemplate;

			array[2] = getByG_C_C_PrevAndNext(session, ddmTemplate, groupId,
					classNameId, classPK, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMTemplate getByG_C_C_PrevAndNext(Session session,
		DDMTemplate ddmTemplate, long groupId, long classNameId, long classPK,
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

		query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

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

		qPos.add(classPK);

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
	 * Returns all the d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching d d m templates that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> filterFindByG_C_C(long groupId, long classNameId,
		long classPK) throws SystemException {
		return filterFindByG_C_C(groupId, classNameId, classPK,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of matching d d m templates that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> filterFindByG_C_C(long groupId, long classNameId,
		long classPK, int start, int end) throws SystemException {
		return filterFindByG_C_C(groupId, classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m templates that the user has permissions to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m templates that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> filterFindByG_C_C(long groupId, long classNameId,
		long classPK, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_C(groupId, classNameId, classPK, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

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

			qPos.add(classNameId);

			qPos.add(classPK);

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
	 * Returns the d d m templates before and after the current d d m template in the ordered set of d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param templateId the primary key of the current d d m template
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate[] filterFindByG_C_C_PrevAndNext(long templateId,
		long groupId, long classNameId, long classPK,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_C_PrevAndNext(templateId, groupId, classNameId,
				classPK, orderByComparator);
		}

		DDMTemplate ddmTemplate = findByPrimaryKey(templateId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplate[] array = new DDMTemplateImpl[3];

			array[0] = filterGetByG_C_C_PrevAndNext(session, ddmTemplate,
					groupId, classNameId, classPK, orderByComparator, true);

			array[1] = ddmTemplate;

			array[2] = filterGetByG_C_C_PrevAndNext(session, ddmTemplate,
					groupId, classNameId, classPK, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMTemplate filterGetByG_C_C_PrevAndNext(Session session,
		DDMTemplate ddmTemplate, long groupId, long classNameId, long classPK,
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

		query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

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

		qPos.add(classNameId);

		qPos.add(classPK);

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
	 * Returns all the d d m templates where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @return the matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findByC_C_T(long classNameId, long classPK,
		String type) throws SystemException {
		return findByC_C_T(classNameId, classPK, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m templates where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findByC_C_T(long classNameId, long classPK,
		String type, int start, int end) throws SystemException {
		return findByC_C_T(classNameId, classPK, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m templates where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findByC_C_T(long classNameId, long classPK,
		String type, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_T;
			finderArgs = new Object[] { classNameId, classPK, type };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C_T;
			finderArgs = new Object[] {
					classNameId, classPK, type,
					
					start, end, orderByComparator
				};
		}

		List<DDMTemplate> list = (List<DDMTemplate>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DDMTemplate ddmTemplate : list) {
				if ((classNameId != ddmTemplate.getClassNameId()) ||
						(classPK != ddmTemplate.getClassPK()) ||
						!Validator.equals(type, ddmTemplate.getType())) {
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

			query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_C_C_T_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_T_CLASSPK_2);

			if (type == null) {
				query.append(_FINDER_COLUMN_C_C_T_TYPE_1);
			}
			else {
				if (type.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_C_T_TYPE_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_C_T_TYPE_2);
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

				qPos.add(classNameId);

				qPos.add(classPK);

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
	 * Returns the first d d m template in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate findByC_C_T_First(long classNameId, long classPK,
		String type, OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		DDMTemplate ddmTemplate = fetchByC_C_T_First(classNameId, classPK,
				type, orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the first d d m template in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate fetchByC_C_T_First(long classNameId, long classPK,
		String type, OrderByComparator orderByComparator)
		throws SystemException {
		List<DDMTemplate> list = findByC_C_T(classNameId, classPK, type, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m template in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate findByC_C_T_Last(long classNameId, long classPK,
		String type, OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		DDMTemplate ddmTemplate = fetchByC_C_T_Last(classNameId, classPK, type,
				orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the last d d m template in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate fetchByC_C_T_Last(long classNameId, long classPK,
		String type, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByC_C_T(classNameId, classPK, type);

		List<DDMTemplate> list = findByC_C_T(classNameId, classPK, type,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m templates before and after the current d d m template in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param templateId the primary key of the current d d m template
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate[] findByC_C_T_PrevAndNext(long templateId,
		long classNameId, long classPK, String type,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		DDMTemplate ddmTemplate = findByPrimaryKey(templateId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplate[] array = new DDMTemplateImpl[3];

			array[0] = getByC_C_T_PrevAndNext(session, ddmTemplate,
					classNameId, classPK, type, orderByComparator, true);

			array[1] = ddmTemplate;

			array[2] = getByC_C_T_PrevAndNext(session, ddmTemplate,
					classNameId, classPK, type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMTemplate getByC_C_T_PrevAndNext(Session session,
		DDMTemplate ddmTemplate, long classNameId, long classPK, String type,
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

		query.append(_FINDER_COLUMN_C_C_T_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_T_CLASSPK_2);

		if (type == null) {
			query.append(_FINDER_COLUMN_C_C_T_TYPE_1);
		}
		else {
			if (type.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_C_T_TYPE_3);
			}
			else {
				query.append(_FINDER_COLUMN_C_C_T_TYPE_2);
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

		qPos.add(classNameId);

		qPos.add(classPK);

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
	 * Returns all the d d m templates where classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param mode the mode
	 * @return the matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findByC_C_T_M(long classNameId, long classPK,
		String type, String mode) throws SystemException {
		return findByC_C_T_M(classNameId, classPK, type, mode,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m templates where classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param mode the mode
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findByC_C_T_M(long classNameId, long classPK,
		String type, String mode, int start, int end) throws SystemException {
		return findByC_C_T_M(classNameId, classPK, type, mode, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m templates where classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param mode the mode
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> findByC_C_T_M(long classNameId, long classPK,
		String type, String mode, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_T_M;
			finderArgs = new Object[] { classNameId, classPK, type, mode };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C_T_M;
			finderArgs = new Object[] {
					classNameId, classPK, type, mode,
					
					start, end, orderByComparator
				};
		}

		List<DDMTemplate> list = (List<DDMTemplate>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DDMTemplate ddmTemplate : list) {
				if ((classNameId != ddmTemplate.getClassNameId()) ||
						(classPK != ddmTemplate.getClassPK()) ||
						!Validator.equals(type, ddmTemplate.getType()) ||
						!Validator.equals(mode, ddmTemplate.getMode())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(6 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_C_C_T_M_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_T_M_CLASSPK_2);

			if (type == null) {
				query.append(_FINDER_COLUMN_C_C_T_M_TYPE_1);
			}
			else {
				if (type.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_C_T_M_TYPE_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_C_T_M_TYPE_2);
				}
			}

			if (mode == null) {
				query.append(_FINDER_COLUMN_C_C_T_M_MODE_1);
			}
			else {
				if (mode.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_C_T_M_MODE_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_C_T_M_MODE_2);
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

				qPos.add(classNameId);

				qPos.add(classPK);

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
	 * Returns the first d d m template in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param mode the mode
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate findByC_C_T_M_First(long classNameId, long classPK,
		String type, String mode, OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		DDMTemplate ddmTemplate = fetchByC_C_T_M_First(classNameId, classPK,
				type, mode, orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", type=");
		msg.append(type);

		msg.append(", mode=");
		msg.append(mode);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the first d d m template in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param mode the mode
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate fetchByC_C_T_M_First(long classNameId, long classPK,
		String type, String mode, OrderByComparator orderByComparator)
		throws SystemException {
		List<DDMTemplate> list = findByC_C_T_M(classNameId, classPK, type,
				mode, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m template in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param mode the mode
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate findByC_C_T_M_Last(long classNameId, long classPK,
		String type, String mode, OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		DDMTemplate ddmTemplate = fetchByC_C_T_M_Last(classNameId, classPK,
				type, mode, orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", type=");
		msg.append(type);

		msg.append(", mode=");
		msg.append(mode);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the last d d m template in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param mode the mode
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate fetchByC_C_T_M_Last(long classNameId, long classPK,
		String type, String mode, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByC_C_T_M(classNameId, classPK, type, mode);

		List<DDMTemplate> list = findByC_C_T_M(classNameId, classPK, type,
				mode, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m templates before and after the current d d m template in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	 *
	 * @param templateId the primary key of the current d d m template
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param mode the mode
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m template
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate[] findByC_C_T_M_PrevAndNext(long templateId,
		long classNameId, long classPK, String type, String mode,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		DDMTemplate ddmTemplate = findByPrimaryKey(templateId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplate[] array = new DDMTemplateImpl[3];

			array[0] = getByC_C_T_M_PrevAndNext(session, ddmTemplate,
					classNameId, classPK, type, mode, orderByComparator, true);

			array[1] = ddmTemplate;

			array[2] = getByC_C_T_M_PrevAndNext(session, ddmTemplate,
					classNameId, classPK, type, mode, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMTemplate getByC_C_T_M_PrevAndNext(Session session,
		DDMTemplate ddmTemplate, long classNameId, long classPK, String type,
		String mode, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

		query.append(_FINDER_COLUMN_C_C_T_M_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_T_M_CLASSPK_2);

		if (type == null) {
			query.append(_FINDER_COLUMN_C_C_T_M_TYPE_1);
		}
		else {
			if (type.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_C_T_M_TYPE_3);
			}
			else {
				query.append(_FINDER_COLUMN_C_C_T_M_TYPE_2);
			}
		}

		if (mode == null) {
			query.append(_FINDER_COLUMN_C_C_T_M_MODE_1);
		}
		else {
			if (mode.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_C_T_M_MODE_3);
			}
			else {
				query.append(_FINDER_COLUMN_C_C_T_M_MODE_2);
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

		qPos.add(classNameId);

		qPos.add(classPK);

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
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
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
			remove(ddmTemplate);
		}
	}

	/**
	 * Removes the d d m template where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the d d m template that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate removeByUUID_G(String uuid, long groupId)
		throws NoSuchTemplateException, SystemException {
		DDMTemplate ddmTemplate = findByUUID_G(uuid, groupId);

		return remove(ddmTemplate);
	}

	/**
	 * Removes all the d d m templates where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid_C(String uuid, long companyId)
		throws SystemException {
		for (DDMTemplate ddmTemplate : findByUuid_C(uuid, companyId)) {
			remove(ddmTemplate);
		}
	}

	/**
	 * Removes all the d d m templates where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (DDMTemplate ddmTemplate : findByGroupId(groupId)) {
			remove(ddmTemplate);
		}
	}

	/**
	 * Removes all the d d m templates where classPK = &#63; from the database.
	 *
	 * @param classPK the class p k
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByClassPK(long classPK) throws SystemException {
		for (DDMTemplate ddmTemplate : findByClassPK(classPK)) {
			remove(ddmTemplate);
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
			remove(ddmTemplate);
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
			remove(ddmTemplate);
		}
	}

	/**
	 * Removes all the d d m templates where groupId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_C(long groupId, long classNameId)
		throws SystemException {
		for (DDMTemplate ddmTemplate : findByG_C(groupId, classNameId)) {
			remove(ddmTemplate);
		}
	}

	/**
	 * Removes the d d m template where groupId = &#63; and templateKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param templateKey the template key
	 * @return the d d m template that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate removeByG_T(long groupId, String templateKey)
		throws NoSuchTemplateException, SystemException {
		DDMTemplate ddmTemplate = findByG_T(groupId, templateKey);

		return remove(ddmTemplate);
	}

	/**
	 * Removes all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_C_C(long groupId, long classNameId, long classPK)
		throws SystemException {
		for (DDMTemplate ddmTemplate : findByG_C_C(groupId, classNameId, classPK)) {
			remove(ddmTemplate);
		}
	}

	/**
	 * Removes all the d d m templates where classNameId = &#63; and classPK = &#63; and type = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_C_T(long classNameId, long classPK, String type)
		throws SystemException {
		for (DDMTemplate ddmTemplate : findByC_C_T(classNameId, classPK, type)) {
			remove(ddmTemplate);
		}
	}

	/**
	 * Removes all the d d m templates where classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param mode the mode
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_C_T_M(long classNameId, long classPK, String type,
		String mode) throws SystemException {
		for (DDMTemplate ddmTemplate : findByC_C_T_M(classNameId, classPK,
				type, mode)) {
			remove(ddmTemplate);
		}
	}

	/**
	 * Removes all the d d m templates from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (DDMTemplate ddmTemplate : findAll()) {
			remove(ddmTemplate);
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
	 * Returns the number of d d m templates where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid_C(String uuid, long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMTEMPLATE_WHERE);

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
	 * Returns the number of d d m templates where classPK = &#63;.
	 *
	 * @param classPK the class p k
	 * @return the number of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public int countByClassPK(long classPK) throws SystemException {
		Object[] finderArgs = new Object[] { classPK };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_CLASSPK,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_CLASSPK_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classPK);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CLASSPK,
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
	 * Returns the number of d d m templates where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the number of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_C(long groupId, long classNameId)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, classNameId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMTEMPLATE_WHERE);

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
	 * Returns the number of d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the number of matching d d m templates that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_C(long groupId, long classNameId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C(groupId, classNameId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_DDMTEMPLATE_WHERE);

		query.append(_FINDER_COLUMN_G_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

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

			qPos.add(classNameId);

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
	 * Returns the number of d d m templates where groupId = &#63; and templateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param templateKey the template key
	 * @return the number of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_T(long groupId, String templateKey)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, templateKey };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_T,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_G_T_GROUPID_2);

			if (templateKey == null) {
				query.append(_FINDER_COLUMN_G_T_TEMPLATEKEY_1);
			}
			else {
				if (templateKey.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_T_TEMPLATEKEY_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_T_TEMPLATEKEY_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (templateKey != null) {
					qPos.add(templateKey);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_T, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the number of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_C_C(long groupId, long classNameId, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, classNameId, classPK };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_C_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DDMTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_C_C,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the number of matching d d m templates that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_C_C(long groupId, long classNameId, long classPK)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_C(groupId, classNameId, classPK);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_DDMTEMPLATE_WHERE);

		query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

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

			qPos.add(classNameId);

			qPos.add(classPK);

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
	 * Returns the number of d d m templates where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @return the number of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_C_T(long classNameId, long classPK, String type)
		throws SystemException {
		Object[] finderArgs = new Object[] { classNameId, classPK, type };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C_T,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DDMTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_C_C_T_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_T_CLASSPK_2);

			if (type == null) {
				query.append(_FINDER_COLUMN_C_C_T_TYPE_1);
			}
			else {
				if (type.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_C_T_TYPE_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_C_T_TYPE_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C_T,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of d d m templates where classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param mode the mode
	 * @return the number of matching d d m templates
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_C_T_M(long classNameId, long classPK, String type,
		String mode) throws SystemException {
		Object[] finderArgs = new Object[] { classNameId, classPK, type, mode };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C_T_M,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_DDMTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_C_C_T_M_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_T_M_CLASSPK_2);

			if (type == null) {
				query.append(_FINDER_COLUMN_C_C_T_M_TYPE_1);
			}
			else {
				if (type.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_C_T_M_TYPE_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_C_T_M_TYPE_2);
				}
			}

			if (mode == null) {
				query.append(_FINDER_COLUMN_C_C_T_M_MODE_1);
			}
			else {
				if (mode.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_C_T_M_MODE_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_C_T_M_MODE_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C_T_M,
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
	@BeanReference(type = ImagePersistence.class)
	protected ImagePersistence imagePersistence;
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
	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "ddmTemplate.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "ddmTemplate.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(ddmTemplate.uuid IS NULL OR ddmTemplate.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "ddmTemplate.companyId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "ddmTemplate.groupId = ?";
	private static final String _FINDER_COLUMN_CLASSPK_CLASSPK_2 = "ddmTemplate.classPK = ?";
	private static final String _FINDER_COLUMN_TYPE_TYPE_1 = "ddmTemplate.type IS NULL";
	private static final String _FINDER_COLUMN_TYPE_TYPE_2 = "ddmTemplate.type = ?";
	private static final String _FINDER_COLUMN_TYPE_TYPE_3 = "(ddmTemplate.type IS NULL OR ddmTemplate.type = ?)";
	private static final String _FINDER_COLUMN_LANGUAGE_LANGUAGE_1 = "ddmTemplate.language IS NULL";
	private static final String _FINDER_COLUMN_LANGUAGE_LANGUAGE_2 = "ddmTemplate.language = ?";
	private static final String _FINDER_COLUMN_LANGUAGE_LANGUAGE_3 = "(ddmTemplate.language IS NULL OR ddmTemplate.language = ?)";
	private static final String _FINDER_COLUMN_G_C_GROUPID_2 = "ddmTemplate.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_CLASSNAMEID_2 = "ddmTemplate.classNameId = ?";
	private static final String _FINDER_COLUMN_G_T_GROUPID_2 = "ddmTemplate.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_T_TEMPLATEKEY_1 = "ddmTemplate.templateKey IS NULL";
	private static final String _FINDER_COLUMN_G_T_TEMPLATEKEY_2 = "ddmTemplate.templateKey = ?";
	private static final String _FINDER_COLUMN_G_T_TEMPLATEKEY_3 = "(ddmTemplate.templateKey IS NULL OR ddmTemplate.templateKey = ?)";
	private static final String _FINDER_COLUMN_G_C_C_GROUPID_2 = "ddmTemplate.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_CLASSNAMEID_2 = "ddmTemplate.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_CLASSPK_2 = "ddmTemplate.classPK = ?";
	private static final String _FINDER_COLUMN_C_C_T_CLASSNAMEID_2 = "ddmTemplate.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_T_CLASSPK_2 = "ddmTemplate.classPK = ? AND ";
	private static final String _FINDER_COLUMN_C_C_T_TYPE_1 = "ddmTemplate.type IS NULL";
	private static final String _FINDER_COLUMN_C_C_T_TYPE_2 = "ddmTemplate.type = ?";
	private static final String _FINDER_COLUMN_C_C_T_TYPE_3 = "(ddmTemplate.type IS NULL OR ddmTemplate.type = ?)";
	private static final String _FINDER_COLUMN_C_C_T_M_CLASSNAMEID_2 = "ddmTemplate.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_T_M_CLASSPK_2 = "ddmTemplate.classPK = ? AND ";
	private static final String _FINDER_COLUMN_C_C_T_M_TYPE_1 = "ddmTemplate.type IS NULL AND ";
	private static final String _FINDER_COLUMN_C_C_T_M_TYPE_2 = "ddmTemplate.type = ? AND ";
	private static final String _FINDER_COLUMN_C_C_T_M_TYPE_3 = "(ddmTemplate.type IS NULL OR ddmTemplate.type = ?) AND ";
	private static final String _FINDER_COLUMN_C_C_T_M_MODE_1 = "ddmTemplate.mode IS NULL";
	private static final String _FINDER_COLUMN_C_C_T_M_MODE_2 = "ddmTemplate.mode = ?";
	private static final String _FINDER_COLUMN_C_C_T_M_MODE_3 = "(ddmTemplate.mode IS NULL OR ddmTemplate.mode = ?)";
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