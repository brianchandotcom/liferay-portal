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

package com.liferay.portlet.journal.service.persistence;

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
import com.liferay.portal.service.persistence.ImagePersistence;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.WebDAVPropsPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence;
import com.liferay.portlet.journal.NoSuchTemplateException;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.model.impl.JournalTemplateImpl;
import com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the journal template service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalTemplatePersistence
 * @see JournalTemplateUtil
 * @generated
 */
public class JournalTemplatePersistenceImpl extends BasePersistenceImpl<JournalTemplate>
	implements JournalTemplatePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link JournalTemplateUtil} to access the journal template persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = JournalTemplateImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			JournalTemplateImpl.class, FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			JournalTemplateImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			JournalTemplateImpl.class, FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_TEMPLATEID = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			JournalTemplateImpl.class, FINDER_CLASS_NAME_LIST,
			"findByTemplateId",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_TEMPLATEID = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByTemplateId",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_SMALLIMAGEID = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			JournalTemplateImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchBySmallImageId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_SMALLIMAGEID = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countBySmallImageId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_T = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			JournalTemplateImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByG_T",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_G_T = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByG_T",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_S = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			JournalTemplateImpl.class, FINDER_CLASS_NAME_LIST, "findByG_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_S = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByG_S",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			JournalTemplateImpl.class, FINDER_CLASS_NAME_LIST, "findAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the journal template in the entity cache if it is enabled.
	 *
	 * @param journalTemplate the journal template
	 */
	public void cacheResult(JournalTemplate journalTemplate) {
		EntityCacheUtil.putResult(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateImpl.class, journalTemplate.getPrimaryKey(),
			journalTemplate);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				journalTemplate.getUuid(),
				Long.valueOf(journalTemplate.getGroupId())
			}, journalTemplate);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
			new Object[] { Long.valueOf(journalTemplate.getSmallImageId()) },
			journalTemplate);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_T,
			new Object[] {
				Long.valueOf(journalTemplate.getGroupId()),
				
			journalTemplate.getTemplateId()
			}, journalTemplate);

		journalTemplate.resetOriginalValues();
	}

	/**
	 * Caches the journal templates in the entity cache if it is enabled.
	 *
	 * @param journalTemplates the journal templates
	 */
	public void cacheResult(List<JournalTemplate> journalTemplates) {
		for (JournalTemplate journalTemplate : journalTemplates) {
			if (EntityCacheUtil.getResult(
						JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
						JournalTemplateImpl.class,
						journalTemplate.getPrimaryKey()) == null) {
				cacheResult(journalTemplate);
			}
		}
	}

	/**
	 * Clears the cache for all journal templates.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(JournalTemplateImpl.class.getName());
		}

		EntityCacheUtil.clearCache(JournalTemplateImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the journal template.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(JournalTemplate journalTemplate) {
		EntityCacheUtil.removeResult(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateImpl.class, journalTemplate.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FIND_ALL, FINDER_ARGS_EMPTY);

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				journalTemplate.getUuid(),
				Long.valueOf(journalTemplate.getGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
			new Object[] { Long.valueOf(journalTemplate.getSmallImageId()) });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_T,
			new Object[] {
				Long.valueOf(journalTemplate.getGroupId()),
				
			journalTemplate.getTemplateId()
			});
	}

	/**
	 * Creates a new journal template with the primary key. Does not add the journal template to the database.
	 *
	 * @param id the primary key for the new journal template
	 * @return the new journal template
	 */
	public JournalTemplate create(long id) {
		JournalTemplate journalTemplate = new JournalTemplateImpl();

		journalTemplate.setNew(true);
		journalTemplate.setPrimaryKey(id);

		String uuid = PortalUUIDUtil.generate();

		journalTemplate.setUuid(uuid);

		return journalTemplate;
	}

	/**
	 * Removes the journal template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the journal template
	 * @return the journal template that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a journal template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public JournalTemplate remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the journal template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param id the primary key of the journal template
	 * @return the journal template that was removed
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a journal template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate remove(long id)
		throws NoSuchTemplateException, SystemException {
		Session session = null;

		try {
			session = openSession();

			JournalTemplate journalTemplate = (JournalTemplate)session.get(JournalTemplateImpl.class,
					Long.valueOf(id));

			if (journalTemplate == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
				}

				throw new NoSuchTemplateException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					id);
			}

			return journalTemplatePersistence.remove(journalTemplate);
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
	 * Removes the journal template from the database. Also notifies the appropriate model listeners.
	 *
	 * @param journalTemplate the journal template
	 * @return the journal template that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public JournalTemplate remove(JournalTemplate journalTemplate)
		throws SystemException {
		return super.remove(journalTemplate);
	}

	@Override
	protected JournalTemplate removeImpl(JournalTemplate journalTemplate)
		throws SystemException {
		journalTemplate = toUnwrappedModel(journalTemplate);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, journalTemplate);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		JournalTemplateModelImpl journalTemplateModelImpl = (JournalTemplateModelImpl)journalTemplate;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				journalTemplateModelImpl.getUuid(),
				Long.valueOf(journalTemplateModelImpl.getGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
			new Object[] {
				Long.valueOf(journalTemplateModelImpl.getSmallImageId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_T,
			new Object[] {
				Long.valueOf(journalTemplateModelImpl.getGroupId()),
				
			journalTemplateModelImpl.getTemplateId()
			});

		EntityCacheUtil.removeResult(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateImpl.class, journalTemplate.getPrimaryKey());

		return journalTemplate;
	}

	@Override
	public JournalTemplate updateImpl(
		com.liferay.portlet.journal.model.JournalTemplate journalTemplate,
		boolean merge) throws SystemException {
		journalTemplate = toUnwrappedModel(journalTemplate);

		boolean isNew = journalTemplate.isNew();

		JournalTemplateModelImpl journalTemplateModelImpl = (JournalTemplateModelImpl)journalTemplate;

		if (Validator.isNull(journalTemplate.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			journalTemplate.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, journalTemplate, merge);

			journalTemplate.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateImpl.class, journalTemplate.getPrimaryKey(),
			journalTemplate);

		if (!isNew &&
				(!Validator.equals(journalTemplate.getUuid(),
					journalTemplateModelImpl.getOriginalUuid()) ||
				(journalTemplate.getGroupId() != journalTemplateModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					journalTemplateModelImpl.getOriginalUuid(),
					Long.valueOf(journalTemplateModelImpl.getOriginalGroupId())
				});
		}

		if (isNew ||
				(!Validator.equals(journalTemplate.getUuid(),
					journalTemplateModelImpl.getOriginalUuid()) ||
				(journalTemplate.getGroupId() != journalTemplateModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					journalTemplate.getUuid(),
					Long.valueOf(journalTemplate.getGroupId())
				}, journalTemplate);
		}

		if (!isNew &&
				(journalTemplate.getSmallImageId() != journalTemplateModelImpl.getOriginalSmallImageId())) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
				new Object[] {
					Long.valueOf(
						journalTemplateModelImpl.getOriginalSmallImageId())
				});
		}

		if (isNew ||
				(journalTemplate.getSmallImageId() != journalTemplateModelImpl.getOriginalSmallImageId())) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
				new Object[] { Long.valueOf(journalTemplate.getSmallImageId()) },
				journalTemplate);
		}

		if (!isNew &&
				((journalTemplate.getGroupId() != journalTemplateModelImpl.getOriginalGroupId()) ||
				!Validator.equals(journalTemplate.getTemplateId(),
					journalTemplateModelImpl.getOriginalTemplateId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_T,
				new Object[] {
					Long.valueOf(journalTemplateModelImpl.getOriginalGroupId()),
					
				journalTemplateModelImpl.getOriginalTemplateId()
				});
		}

		if (isNew ||
				((journalTemplate.getGroupId() != journalTemplateModelImpl.getOriginalGroupId()) ||
				!Validator.equals(journalTemplate.getTemplateId(),
					journalTemplateModelImpl.getOriginalTemplateId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_T,
				new Object[] {
					Long.valueOf(journalTemplate.getGroupId()),
					
				journalTemplate.getTemplateId()
				}, journalTemplate);
		}

		return journalTemplate;
	}

	protected JournalTemplate toUnwrappedModel(JournalTemplate journalTemplate) {
		if (journalTemplate instanceof JournalTemplateImpl) {
			return journalTemplate;
		}

		JournalTemplateImpl journalTemplateImpl = new JournalTemplateImpl();

		journalTemplateImpl.setNew(journalTemplate.isNew());
		journalTemplateImpl.setPrimaryKey(journalTemplate.getPrimaryKey());

		journalTemplateImpl.setUuid(journalTemplate.getUuid());
		journalTemplateImpl.setId(journalTemplate.getId());
		journalTemplateImpl.setGroupId(journalTemplate.getGroupId());
		journalTemplateImpl.setCompanyId(journalTemplate.getCompanyId());
		journalTemplateImpl.setUserId(journalTemplate.getUserId());
		journalTemplateImpl.setUserName(journalTemplate.getUserName());
		journalTemplateImpl.setCreateDate(journalTemplate.getCreateDate());
		journalTemplateImpl.setModifiedDate(journalTemplate.getModifiedDate());
		journalTemplateImpl.setTemplateId(journalTemplate.getTemplateId());
		journalTemplateImpl.setStructureId(journalTemplate.getStructureId());
		journalTemplateImpl.setName(journalTemplate.getName());
		journalTemplateImpl.setDescription(journalTemplate.getDescription());
		journalTemplateImpl.setXsl(journalTemplate.getXsl());
		journalTemplateImpl.setLangType(journalTemplate.getLangType());
		journalTemplateImpl.setCacheable(journalTemplate.isCacheable());
		journalTemplateImpl.setSmallImage(journalTemplate.isSmallImage());
		journalTemplateImpl.setSmallImageId(journalTemplate.getSmallImageId());
		journalTemplateImpl.setSmallImageURL(journalTemplate.getSmallImageURL());

		return journalTemplateImpl;
	}

	/**
	 * Returns the journal template with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the journal template
	 * @return the journal template
	 * @throws com.liferay.portal.NoSuchModelException if a journal template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public JournalTemplate findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the journal template with the primary key or throws a {@link com.liferay.portlet.journal.NoSuchTemplateException} if it could not be found.
	 *
	 * @param id the primary key of the journal template
	 * @return the journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a journal template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate findByPrimaryKey(long id)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = fetchByPrimaryKey(id);

		if (journalTemplate == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
			}

			throw new NoSuchTemplateException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				id);
		}

		return journalTemplate;
	}

	/**
	 * Returns the journal template with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the journal template
	 * @return the journal template, or <code>null</code> if a journal template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public JournalTemplate fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the journal template with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param id the primary key of the journal template
	 * @return the journal template, or <code>null</code> if a journal template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate fetchByPrimaryKey(long id) throws SystemException {
		JournalTemplate journalTemplate = (JournalTemplate)EntityCacheUtil.getResult(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
				JournalTemplateImpl.class, id);

		if (journalTemplate == _nullJournalTemplate) {
			return null;
		}

		if (journalTemplate == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				journalTemplate = (JournalTemplate)session.get(JournalTemplateImpl.class,
						Long.valueOf(id));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (journalTemplate != null) {
					cacheResult(journalTemplate);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
						JournalTemplateImpl.class, id, _nullJournalTemplate);
				}

				closeSession(session);
			}
		}

		return journalTemplate;
	}

	/**
	 * Returns all the journal templates where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> findByUuid(String uuid)
		throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal templates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of journal templates
	 * @param end the upper bound of the range of journal templates (not inclusive)
	 * @return the range of matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal templates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of journal templates
	 * @param end the upper bound of the range of journal templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, start, end, orderByComparator };

		List<JournalTemplate> list = (List<JournalTemplate>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

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
				query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
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

				list = (List<JournalTemplate>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_UUID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first journal template in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		List<JournalTemplate> list = findByUuid(uuid, 0, 1, orderByComparator);

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
	 * Returns the last journal template in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		int count = countByUuid(uuid);

		List<JournalTemplate> list = findByUuid(uuid, count - 1, count,
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
	 * Returns the journal templates before and after the current journal template in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param id the primary key of the current journal template
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a journal template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate[] findByUuid_PrevAndNext(long id, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalTemplate[] array = new JournalTemplateImpl[3];

			array[0] = getByUuid_PrevAndNext(session, journalTemplate, uuid,
					orderByComparator, true);

			array[1] = journalTemplate;

			array[2] = getByUuid_PrevAndNext(session, journalTemplate, uuid,
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

	protected JournalTemplate getByUuid_PrevAndNext(Session session,
		JournalTemplate journalTemplate, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

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
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
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
			query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByValues(journalTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the journal template where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchTemplateException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate findByUUID_G(String uuid, long groupId)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = fetchByUUID_G(uuid, groupId);

		if (journalTemplate == null) {
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

		return journalTemplate;
	}

	/**
	 * Returns the journal template where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching journal template, or <code>null</code> if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the journal template where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching journal template, or <code>null</code> if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

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

			query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);

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

				List<JournalTemplate> list = q.list();

				result = list;

				JournalTemplate journalTemplate = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					journalTemplate = list.get(0);

					cacheResult(journalTemplate);

					if ((journalTemplate.getUuid() == null) ||
							!journalTemplate.getUuid().equals(uuid) ||
							(journalTemplate.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, journalTemplate);
					}
				}

				return journalTemplate;
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
				return (JournalTemplate)result;
			}
		}
	}

	/**
	 * Returns all the journal templates where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal templates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal templates
	 * @param end the upper bound of the range of journal templates (not inclusive)
	 * @return the range of matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal templates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal templates
	 * @param end the upper bound of the range of journal templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> findByGroupId(long groupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId,
				
				start, end, orderByComparator
			};

		List<JournalTemplate> list = (List<JournalTemplate>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<JournalTemplate>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_GROUPID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first journal template in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		List<JournalTemplate> list = findByGroupId(groupId, 0, 1,
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
	 * Returns the last journal template in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		int count = countByGroupId(groupId);

		List<JournalTemplate> list = findByGroupId(groupId, count - 1, count,
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
	 * Returns the journal templates before and after the current journal template in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param id the primary key of the current journal template
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a journal template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate[] findByGroupId_PrevAndNext(long id, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalTemplate[] array = new JournalTemplateImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, journalTemplate,
					groupId, orderByComparator, true);

			array[1] = journalTemplate;

			array[2] = getByGroupId_PrevAndNext(session, journalTemplate,
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

	protected JournalTemplate getByGroupId_PrevAndNext(Session session,
		JournalTemplate journalTemplate, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
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
			query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(journalTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal templates that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching journal templates that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> filterFindByGroupId(long groupId)
		throws SystemException {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal templates that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal templates
	 * @param end the upper bound of the range of journal templates (not inclusive)
	 * @return the range of matching journal templates that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> filterFindByGroupId(long groupId, int start,
		int end) throws SystemException {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal templates that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal templates
	 * @param end the upper bound of the range of journal templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal templates that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> filterFindByGroupId(long groupId, int start,
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
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALTEMPLATE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALTEMPLATE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALTEMPLATE_NO_INLINE_DISTINCT_WHERE_2);
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

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalTemplateModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, JournalTemplateImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, JournalTemplateImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<JournalTemplate>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the journal templates before and after the current journal template in the ordered set of journal templates that the user has permission to view where groupId = &#63;.
	 *
	 * @param id the primary key of the current journal template
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a journal template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate[] filterFindByGroupId_PrevAndNext(long id,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(id, groupId, orderByComparator);
		}

		JournalTemplate journalTemplate = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalTemplate[] array = new JournalTemplateImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session, journalTemplate,
					groupId, orderByComparator, true);

			array[1] = journalTemplate;

			array[2] = filterGetByGroupId_PrevAndNext(session, journalTemplate,
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

	protected JournalTemplate filterGetByGroupId_PrevAndNext(Session session,
		JournalTemplate journalTemplate, long groupId,
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
			query.append(_FILTER_SQL_SELECT_JOURNALTEMPLATE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALTEMPLATE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALTEMPLATE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

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

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalTemplateModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, JournalTemplateImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, JournalTemplateImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(journalTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal templates where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @return the matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> findByTemplateId(String templateId)
		throws SystemException {
		return findByTemplateId(templateId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal templates where templateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param templateId the template ID
	 * @param start the lower bound of the range of journal templates
	 * @param end the upper bound of the range of journal templates (not inclusive)
	 * @return the range of matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> findByTemplateId(String templateId, int start,
		int end) throws SystemException {
		return findByTemplateId(templateId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal templates where templateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param templateId the template ID
	 * @param start the lower bound of the range of journal templates
	 * @param end the upper bound of the range of journal templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> findByTemplateId(String templateId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				templateId,
				
				start, end, orderByComparator
			};

		List<JournalTemplate> list = (List<JournalTemplate>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_TEMPLATEID,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

			if (templateId == null) {
				query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_1);
			}
			else {
				if (templateId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_3);
				}
				else {
					query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (templateId != null) {
					qPos.add(templateId);
				}

				list = (List<JournalTemplate>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_TEMPLATEID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_TEMPLATEID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first journal template in the ordered set where templateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param templateId the template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate findByTemplateId_First(String templateId,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		List<JournalTemplate> list = findByTemplateId(templateId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("templateId=");
			msg.append(templateId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last journal template in the ordered set where templateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param templateId the template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate findByTemplateId_Last(String templateId,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		int count = countByTemplateId(templateId);

		List<JournalTemplate> list = findByTemplateId(templateId, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("templateId=");
			msg.append(templateId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the journal templates before and after the current journal template in the ordered set where templateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param id the primary key of the current journal template
	 * @param templateId the template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a journal template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate[] findByTemplateId_PrevAndNext(long id,
		String templateId, OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalTemplate[] array = new JournalTemplateImpl[3];

			array[0] = getByTemplateId_PrevAndNext(session, journalTemplate,
					templateId, orderByComparator, true);

			array[1] = journalTemplate;

			array[2] = getByTemplateId_PrevAndNext(session, journalTemplate,
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

	protected JournalTemplate getByTemplateId_PrevAndNext(Session session,
		JournalTemplate journalTemplate, String templateId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

		if (templateId == null) {
			query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_1);
		}
		else {
			if (templateId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_3);
			}
			else {
				query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_2);
			}
		}

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
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
			query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (templateId != null) {
			qPos.add(templateId);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(journalTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the journal template where smallImageId = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchTemplateException} if it could not be found.
	 *
	 * @param smallImageId the small image ID
	 * @return the matching journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate findBySmallImageId(long smallImageId)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = fetchBySmallImageId(smallImageId);

		if (journalTemplate == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("smallImageId=");
			msg.append(smallImageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchTemplateException(msg.toString());
		}

		return journalTemplate;
	}

	/**
	 * Returns the journal template where smallImageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param smallImageId the small image ID
	 * @return the matching journal template, or <code>null</code> if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate fetchBySmallImageId(long smallImageId)
		throws SystemException {
		return fetchBySmallImageId(smallImageId, true);
	}

	/**
	 * Returns the journal template where smallImageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param smallImageId the small image ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching journal template, or <code>null</code> if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate fetchBySmallImageId(long smallImageId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { smallImageId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_SMALLIMAGEID_SMALLIMAGEID_2);

			query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(smallImageId);

				List<JournalTemplate> list = q.list();

				result = list;

				JournalTemplate journalTemplate = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
						finderArgs, list);
				}
				else {
					journalTemplate = list.get(0);

					cacheResult(journalTemplate);

					if ((journalTemplate.getSmallImageId() != smallImageId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
							finderArgs, journalTemplate);
					}
				}

				return journalTemplate;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
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
				return (JournalTemplate)result;
			}
		}
	}

	/**
	 * Returns the journal template where groupId = &#63; and templateId = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchTemplateException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param templateId the template ID
	 * @return the matching journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate findByG_T(long groupId, String templateId)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = fetchByG_T(groupId, templateId);

		if (journalTemplate == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", templateId=");
			msg.append(templateId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchTemplateException(msg.toString());
		}

		return journalTemplate;
	}

	/**
	 * Returns the journal template where groupId = &#63; and templateId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param templateId the template ID
	 * @return the matching journal template, or <code>null</code> if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate fetchByG_T(long groupId, String templateId)
		throws SystemException {
		return fetchByG_T(groupId, templateId, true);
	}

	/**
	 * Returns the journal template where groupId = &#63; and templateId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param templateId the template ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching journal template, or <code>null</code> if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate fetchByG_T(long groupId, String templateId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, templateId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_T,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_G_T_GROUPID_2);

			if (templateId == null) {
				query.append(_FINDER_COLUMN_G_T_TEMPLATEID_1);
			}
			else {
				if (templateId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_T_TEMPLATEID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_T_TEMPLATEID_2);
				}
			}

			query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (templateId != null) {
					qPos.add(templateId);
				}

				List<JournalTemplate> list = q.list();

				result = list;

				JournalTemplate journalTemplate = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_T,
						finderArgs, list);
				}
				else {
					journalTemplate = list.get(0);

					cacheResult(journalTemplate);

					if ((journalTemplate.getGroupId() != groupId) ||
							(journalTemplate.getTemplateId() == null) ||
							!journalTemplate.getTemplateId().equals(templateId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_T,
							finderArgs, journalTemplate);
					}
				}

				return journalTemplate;
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
				return (JournalTemplate)result;
			}
		}
	}

	/**
	 * Returns all the journal templates where groupId = &#63; and structureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param structureId the structure ID
	 * @return the matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> findByG_S(long groupId, String structureId)
		throws SystemException {
		return findByG_S(groupId, structureId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal templates where groupId = &#63; and structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of journal templates
	 * @param end the upper bound of the range of journal templates (not inclusive)
	 * @return the range of matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> findByG_S(long groupId, String structureId,
		int start, int end) throws SystemException {
		return findByG_S(groupId, structureId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal templates where groupId = &#63; and structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of journal templates
	 * @param end the upper bound of the range of journal templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> findByG_S(long groupId, String structureId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, structureId,
				
				start, end, orderByComparator
			};

		List<JournalTemplate> list = (List<JournalTemplate>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_S,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_G_S_GROUPID_2);

			if (structureId == null) {
				query.append(_FINDER_COLUMN_G_S_STRUCTUREID_1);
			}
			else {
				if (structureId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_S_STRUCTUREID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_S_STRUCTUREID_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (structureId != null) {
					qPos.add(structureId);
				}

				list = (List<JournalTemplate>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_S,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_S,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first journal template in the ordered set where groupId = &#63; and structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate findByG_S_First(long groupId, String structureId,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		List<JournalTemplate> list = findByG_S(groupId, structureId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", structureId=");
			msg.append(structureId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last journal template in the ordered set where groupId = &#63; and structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate findByG_S_Last(long groupId, String structureId,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		int count = countByG_S(groupId, structureId);

		List<JournalTemplate> list = findByG_S(groupId, structureId, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", structureId=");
			msg.append(structureId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the journal templates before and after the current journal template in the ordered set where groupId = &#63; and structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param id the primary key of the current journal template
	 * @param groupId the group ID
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a journal template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate[] findByG_S_PrevAndNext(long id, long groupId,
		String structureId, OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalTemplate[] array = new JournalTemplateImpl[3];

			array[0] = getByG_S_PrevAndNext(session, journalTemplate, groupId,
					structureId, orderByComparator, true);

			array[1] = journalTemplate;

			array[2] = getByG_S_PrevAndNext(session, journalTemplate, groupId,
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

	protected JournalTemplate getByG_S_PrevAndNext(Session session,
		JournalTemplate journalTemplate, long groupId, String structureId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

		query.append(_FINDER_COLUMN_G_S_GROUPID_2);

		if (structureId == null) {
			query.append(_FINDER_COLUMN_G_S_STRUCTUREID_1);
		}
		else {
			if (structureId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_S_STRUCTUREID_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_S_STRUCTUREID_2);
			}
		}

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
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
			query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (structureId != null) {
			qPos.add(structureId);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(journalTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal templates that the user has permission to view where groupId = &#63; and structureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param structureId the structure ID
	 * @return the matching journal templates that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> filterFindByG_S(long groupId,
		String structureId) throws SystemException {
		return filterFindByG_S(groupId, structureId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal templates that the user has permission to view where groupId = &#63; and structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of journal templates
	 * @param end the upper bound of the range of journal templates (not inclusive)
	 * @return the range of matching journal templates that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> filterFindByG_S(long groupId,
		String structureId, int start, int end) throws SystemException {
		return filterFindByG_S(groupId, structureId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal templates that the user has permissions to view where groupId = &#63; and structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of journal templates
	 * @param end the upper bound of the range of journal templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal templates that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> filterFindByG_S(long groupId,
		String structureId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_S(groupId, structureId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALTEMPLATE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALTEMPLATE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_S_GROUPID_2);

		if (structureId == null) {
			query.append(_FINDER_COLUMN_G_S_STRUCTUREID_1);
		}
		else {
			if (structureId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_S_STRUCTUREID_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_S_STRUCTUREID_2);
			}
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALTEMPLATE_NO_INLINE_DISTINCT_WHERE_2);
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

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalTemplateModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, JournalTemplateImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, JournalTemplateImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (structureId != null) {
				qPos.add(structureId);
			}

			return (List<JournalTemplate>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the journal templates before and after the current journal template in the ordered set of journal templates that the user has permission to view where groupId = &#63; and structureId = &#63;.
	 *
	 * @param id the primary key of the current journal template
	 * @param groupId the group ID
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a journal template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate[] filterFindByG_S_PrevAndNext(long id, long groupId,
		String structureId, OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_S_PrevAndNext(id, groupId, structureId,
				orderByComparator);
		}

		JournalTemplate journalTemplate = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalTemplate[] array = new JournalTemplateImpl[3];

			array[0] = filterGetByG_S_PrevAndNext(session, journalTemplate,
					groupId, structureId, orderByComparator, true);

			array[1] = journalTemplate;

			array[2] = filterGetByG_S_PrevAndNext(session, journalTemplate,
					groupId, structureId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalTemplate filterGetByG_S_PrevAndNext(Session session,
		JournalTemplate journalTemplate, long groupId, String structureId,
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
			query.append(_FILTER_SQL_SELECT_JOURNALTEMPLATE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALTEMPLATE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_S_GROUPID_2);

		if (structureId == null) {
			query.append(_FINDER_COLUMN_G_S_STRUCTUREID_1);
		}
		else {
			if (structureId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_S_STRUCTUREID_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_S_STRUCTUREID_2);
			}
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALTEMPLATE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

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

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalTemplateModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, JournalTemplateImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, JournalTemplateImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (structureId != null) {
			qPos.add(structureId);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(journalTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal templates.
	 *
	 * @return the journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal templates
	 * @param end the upper bound of the range of journal templates (not inclusive)
	 * @return the range of journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal templates
	 * @param end the upper bound of the range of journal templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		List<JournalTemplate> list = (List<JournalTemplate>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_JOURNALTEMPLATE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_JOURNALTEMPLATE.concat(JournalTemplateModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<JournalTemplate>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<JournalTemplate>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_ALL,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs,
						list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the journal templates where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (JournalTemplate journalTemplate : findByUuid(uuid)) {
			journalTemplatePersistence.remove(journalTemplate);
		}
	}

	/**
	 * Removes the journal template where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = findByUUID_G(uuid, groupId);

		journalTemplatePersistence.remove(journalTemplate);
	}

	/**
	 * Removes all the journal templates where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (JournalTemplate journalTemplate : findByGroupId(groupId)) {
			journalTemplatePersistence.remove(journalTemplate);
		}
	}

	/**
	 * Removes all the journal templates where templateId = &#63; from the database.
	 *
	 * @param templateId the template ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByTemplateId(String templateId) throws SystemException {
		for (JournalTemplate journalTemplate : findByTemplateId(templateId)) {
			journalTemplatePersistence.remove(journalTemplate);
		}
	}

	/**
	 * Removes the journal template where smallImageId = &#63; from the database.
	 *
	 * @param smallImageId the small image ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeBySmallImageId(long smallImageId)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = findBySmallImageId(smallImageId);

		journalTemplatePersistence.remove(journalTemplate);
	}

	/**
	 * Removes the journal template where groupId = &#63; and templateId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param templateId the template ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_T(long groupId, String templateId)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = findByG_T(groupId, templateId);

		journalTemplatePersistence.remove(journalTemplate);
	}

	/**
	 * Removes all the journal templates where groupId = &#63; and structureId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param structureId the structure ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_S(long groupId, String structureId)
		throws SystemException {
		for (JournalTemplate journalTemplate : findByG_S(groupId, structureId)) {
			journalTemplatePersistence.remove(journalTemplate);
		}
	}

	/**
	 * Removes all the journal templates from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (JournalTemplate journalTemplate : findAll()) {
			journalTemplatePersistence.remove(journalTemplate);
		}
	}

	/**
	 * Returns the number of journal templates where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_JOURNALTEMPLATE_WHERE);

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
	 * Returns the number of journal templates where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALTEMPLATE_WHERE);

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
	 * Returns the number of journal templates where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_JOURNALTEMPLATE_WHERE);

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
	 * Returns the number of journal templates that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching journal templates that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByGroupId(long groupId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_JOURNALTEMPLATE_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalTemplate.class.getName(),
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
	 * Returns the number of journal templates where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @return the number of matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public int countByTemplateId(String templateId) throws SystemException {
		Object[] finderArgs = new Object[] { templateId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_TEMPLATEID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_JOURNALTEMPLATE_WHERE);

			if (templateId == null) {
				query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_1);
			}
			else {
				if (templateId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_3);
				}
				else {
					query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (templateId != null) {
					qPos.add(templateId);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_TEMPLATEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of journal templates where smallImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @return the number of matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public int countBySmallImageId(long smallImageId) throws SystemException {
		Object[] finderArgs = new Object[] { smallImageId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_SMALLIMAGEID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_JOURNALTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_SMALLIMAGEID_SMALLIMAGEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(smallImageId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_SMALLIMAGEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of journal templates where groupId = &#63; and templateId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param templateId the template ID
	 * @return the number of matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_T(long groupId, String templateId)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, templateId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_T,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_G_T_GROUPID_2);

			if (templateId == null) {
				query.append(_FINDER_COLUMN_G_T_TEMPLATEID_1);
			}
			else {
				if (templateId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_T_TEMPLATEID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_T_TEMPLATEID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (templateId != null) {
					qPos.add(templateId);
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
	 * Returns the number of journal templates where groupId = &#63; and structureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param structureId the structure ID
	 * @return the number of matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_S(long groupId, String structureId)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, structureId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_G_S_GROUPID_2);

			if (structureId == null) {
				query.append(_FINDER_COLUMN_G_S_STRUCTUREID_1);
			}
			else {
				if (structureId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_S_STRUCTUREID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_S_STRUCTUREID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (structureId != null) {
					qPos.add(structureId);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_S, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of journal templates that the user has permission to view where groupId = &#63; and structureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param structureId the structure ID
	 * @return the number of matching journal templates that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_S(long groupId, String structureId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_S(groupId, structureId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_JOURNALTEMPLATE_WHERE);

		query.append(_FINDER_COLUMN_G_S_GROUPID_2);

		if (structureId == null) {
			query.append(_FINDER_COLUMN_G_S_STRUCTUREID_1);
		}
		else {
			if (structureId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_S_STRUCTUREID_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_S_STRUCTUREID_2);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (structureId != null) {
				qPos.add(structureId);
			}

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
	 * Returns the number of journal templates.
	 *
	 * @return the number of journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_JOURNALTEMPLATE);

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
	 * Initializes the journal template persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.journal.model.JournalTemplate")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<JournalTemplate>> listenersList = new ArrayList<ModelListener<JournalTemplate>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<JournalTemplate>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(JournalTemplateImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
	}

	@BeanReference(type = JournalArticlePersistence.class)
	protected JournalArticlePersistence journalArticlePersistence;
	@BeanReference(type = JournalArticleImagePersistence.class)
	protected JournalArticleImagePersistence journalArticleImagePersistence;
	@BeanReference(type = JournalArticleResourcePersistence.class)
	protected JournalArticleResourcePersistence journalArticleResourcePersistence;
	@BeanReference(type = JournalContentSearchPersistence.class)
	protected JournalContentSearchPersistence journalContentSearchPersistence;
	@BeanReference(type = JournalFeedPersistence.class)
	protected JournalFeedPersistence journalFeedPersistence;
	@BeanReference(type = JournalStructurePersistence.class)
	protected JournalStructurePersistence journalStructurePersistence;
	@BeanReference(type = JournalTemplatePersistence.class)
	protected JournalTemplatePersistence journalTemplatePersistence;
	@BeanReference(type = ImagePersistence.class)
	protected ImagePersistence imagePersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = WebDAVPropsPersistence.class)
	protected WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(type = ExpandoValuePersistence.class)
	protected ExpandoValuePersistence expandoValuePersistence;
	private static final String _SQL_SELECT_JOURNALTEMPLATE = "SELECT journalTemplate FROM JournalTemplate journalTemplate";
	private static final String _SQL_SELECT_JOURNALTEMPLATE_WHERE = "SELECT journalTemplate FROM JournalTemplate journalTemplate WHERE ";
	private static final String _SQL_COUNT_JOURNALTEMPLATE = "SELECT COUNT(journalTemplate) FROM JournalTemplate journalTemplate";
	private static final String _SQL_COUNT_JOURNALTEMPLATE_WHERE = "SELECT COUNT(journalTemplate) FROM JournalTemplate journalTemplate WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "journalTemplate.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "journalTemplate.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(journalTemplate.uuid IS NULL OR journalTemplate.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "journalTemplate.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "journalTemplate.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(journalTemplate.uuid IS NULL OR journalTemplate.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "journalTemplate.groupId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "journalTemplate.groupId = ?";
	private static final String _FINDER_COLUMN_TEMPLATEID_TEMPLATEID_1 = "journalTemplate.templateId IS NULL";
	private static final String _FINDER_COLUMN_TEMPLATEID_TEMPLATEID_2 = "journalTemplate.templateId = ?";
	private static final String _FINDER_COLUMN_TEMPLATEID_TEMPLATEID_3 = "(journalTemplate.templateId IS NULL OR journalTemplate.templateId = ?)";
	private static final String _FINDER_COLUMN_SMALLIMAGEID_SMALLIMAGEID_2 = "journalTemplate.smallImageId = ?";
	private static final String _FINDER_COLUMN_G_T_GROUPID_2 = "journalTemplate.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_T_TEMPLATEID_1 = "journalTemplate.templateId IS NULL";
	private static final String _FINDER_COLUMN_G_T_TEMPLATEID_2 = "journalTemplate.templateId = ?";
	private static final String _FINDER_COLUMN_G_T_TEMPLATEID_3 = "(journalTemplate.templateId IS NULL OR journalTemplate.templateId = ?)";
	private static final String _FINDER_COLUMN_G_S_GROUPID_2 = "journalTemplate.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_S_STRUCTUREID_1 = "journalTemplate.structureId IS NULL";
	private static final String _FINDER_COLUMN_G_S_STRUCTUREID_2 = "journalTemplate.structureId = ?";
	private static final String _FINDER_COLUMN_G_S_STRUCTUREID_3 = "(journalTemplate.structureId IS NULL OR journalTemplate.structureId = ?)";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "journalTemplate.id_";
	private static final String _FILTER_SQL_SELECT_JOURNALTEMPLATE_WHERE = "SELECT DISTINCT {journalTemplate.*} FROM JournalTemplate journalTemplate WHERE ";
	private static final String _FILTER_SQL_SELECT_JOURNALTEMPLATE_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {JournalTemplate.*} FROM (SELECT DISTINCT journalTemplate.id_ FROM JournalTemplate journalTemplate WHERE ";
	private static final String _FILTER_SQL_SELECT_JOURNALTEMPLATE_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN JournalTemplate ON TEMP_TABLE.id_ = JournalTemplate.id_";
	private static final String _FILTER_SQL_COUNT_JOURNALTEMPLATE_WHERE = "SELECT COUNT(DISTINCT journalTemplate.id_) AS COUNT_VALUE FROM JournalTemplate journalTemplate WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "journalTemplate";
	private static final String _FILTER_ENTITY_TABLE = "JournalTemplate";
	private static final String _ORDER_BY_ENTITY_ALIAS = "journalTemplate.";
	private static final String _ORDER_BY_ENTITY_TABLE = "JournalTemplate.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No JournalTemplate exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No JournalTemplate exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(JournalTemplatePersistenceImpl.class);
	private static JournalTemplate _nullJournalTemplate = new JournalTemplateImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<JournalTemplate> toCacheModel() {
				return _nullJournalTemplateCacheModel;
			}
		};

	private static CacheModel<JournalTemplate> _nullJournalTemplateCacheModel = new CacheModel<JournalTemplate>() {
			public JournalTemplate toEntityModel() {
				return _nullJournalTemplate;
			}
		};
}