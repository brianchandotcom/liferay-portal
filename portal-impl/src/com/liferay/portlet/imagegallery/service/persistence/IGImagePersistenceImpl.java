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

package com.liferay.portlet.imagegallery.service.persistence;

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
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.asset.service.persistence.AssetCategoryPersistence;
import com.liferay.portlet.asset.service.persistence.AssetEntryPersistence;
import com.liferay.portlet.asset.service.persistence.AssetLinkPersistence;
import com.liferay.portlet.asset.service.persistence.AssetTagPersistence;
import com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence;
import com.liferay.portlet.imagegallery.NoSuchImageException;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.model.impl.IGImageImpl;
import com.liferay.portlet.imagegallery.model.impl.IGImageModelImpl;
import com.liferay.portlet.social.service.persistence.SocialActivityPersistence;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the i g image service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see IGImagePersistence
 * @see IGImageUtil
 * @generated
 */
public class IGImagePersistenceImpl extends BasePersistenceImpl<IGImage>
	implements IGImagePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link IGImageUtil} to access the i g image persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = IGImageImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, IGImageImpl.class,
			FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, IGImageImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, IGImageImpl.class,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_SMALLIMAGEID = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, IGImageImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchBySmallImageId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_SMALLIMAGEID = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countBySmallImageId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_LARGEIMAGEID = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, IGImageImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByLargeImageId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_LARGEIMAGEID = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByLargeImageId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_CUSTOM1IMAGEID = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, IGImageImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByCustom1ImageId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_CUSTOM1IMAGEID = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByCustom1ImageId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_CUSTOM2IMAGEID = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, IGImageImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByCustom2ImageId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_CUSTOM2IMAGEID = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByCustom2ImageId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_U = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, IGImageImpl.class,
			FINDER_CLASS_NAME_LIST, "findByG_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_F = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, IGImageImpl.class,
			FINDER_CLASS_NAME_LIST, "findByG_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByG_F",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_F_N = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, IGImageImpl.class,
			FINDER_CLASS_NAME_LIST, "findByG_F_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F_N = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByG_F_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, IGImageImpl.class,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the i g image in the entity cache if it is enabled.
	 *
	 * @param igImage the i g image
	 */
	public void cacheResult(IGImage igImage) {
		EntityCacheUtil.putResult(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageImpl.class, igImage.getPrimaryKey(), igImage);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { igImage.getUuid(), Long.valueOf(igImage.getGroupId()) },
			igImage);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
			new Object[] { Long.valueOf(igImage.getSmallImageId()) }, igImage);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_LARGEIMAGEID,
			new Object[] { Long.valueOf(igImage.getLargeImageId()) }, igImage);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CUSTOM1IMAGEID,
			new Object[] { Long.valueOf(igImage.getCustom1ImageId()) }, igImage);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CUSTOM2IMAGEID,
			new Object[] { Long.valueOf(igImage.getCustom2ImageId()) }, igImage);

		igImage.resetOriginalValues();
	}

	/**
	 * Caches the i g images in the entity cache if it is enabled.
	 *
	 * @param igImages the i g images
	 */
	public void cacheResult(List<IGImage> igImages) {
		for (IGImage igImage : igImages) {
			if (EntityCacheUtil.getResult(
						IGImageModelImpl.ENTITY_CACHE_ENABLED,
						IGImageImpl.class, igImage.getPrimaryKey(), this) == null) {
				cacheResult(igImage);
			}
		}
	}

	/**
	 * Clears the cache for all i g images.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(IGImageImpl.class.getName());
		}

		EntityCacheUtil.clearCache(IGImageImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the i g image.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(IGImage igImage) {
		EntityCacheUtil.removeResult(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageImpl.class, igImage.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { igImage.getUuid(), Long.valueOf(igImage.getGroupId()) });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
			new Object[] { Long.valueOf(igImage.getSmallImageId()) });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_LARGEIMAGEID,
			new Object[] { Long.valueOf(igImage.getLargeImageId()) });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CUSTOM1IMAGEID,
			new Object[] { Long.valueOf(igImage.getCustom1ImageId()) });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CUSTOM2IMAGEID,
			new Object[] { Long.valueOf(igImage.getCustom2ImageId()) });
	}

	/**
	 * Creates a new i g image with the primary key. Does not add the i g image to the database.
	 *
	 * @param imageId the primary key for the new i g image
	 * @return the new i g image
	 */
	public IGImage create(long imageId) {
		IGImage igImage = new IGImageImpl();

		igImage.setNew(true);
		igImage.setPrimaryKey(imageId);

		String uuid = PortalUUIDUtil.generate();

		igImage.setUuid(uuid);

		return igImage;
	}

	/**
	 * Removes the i g image with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the i g image
	 * @return the i g image that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a i g image with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public IGImage remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the i g image with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param imageId the primary key of the i g image
	 * @return the i g image that was removed
	 * @throws com.liferay.portlet.imagegallery.NoSuchImageException if a i g image with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public IGImage remove(long imageId)
		throws NoSuchImageException, SystemException {
		Session session = null;

		try {
			session = openSession();

			IGImage igImage = (IGImage)session.get(IGImageImpl.class,
					Long.valueOf(imageId));

			if (igImage == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + imageId);
				}

				throw new NoSuchImageException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					imageId);
			}

			return igImagePersistence.remove(igImage);
		}
		catch (NoSuchImageException nsee) {
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
	 * Removes the i g image from the database. Also notifies the appropriate model listeners.
	 *
	 * @param igImage the i g image
	 * @return the i g image that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public IGImage remove(IGImage igImage) throws SystemException {
		return super.remove(igImage);
	}

	@Override
	protected IGImage removeImpl(IGImage igImage) throws SystemException {
		igImage = toUnwrappedModel(igImage);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, igImage);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		IGImageModelImpl igImageModelImpl = (IGImageModelImpl)igImage;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				igImageModelImpl.getUuid(),
				Long.valueOf(igImageModelImpl.getGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
			new Object[] { Long.valueOf(igImageModelImpl.getSmallImageId()) });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_LARGEIMAGEID,
			new Object[] { Long.valueOf(igImageModelImpl.getLargeImageId()) });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CUSTOM1IMAGEID,
			new Object[] { Long.valueOf(igImageModelImpl.getCustom1ImageId()) });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CUSTOM2IMAGEID,
			new Object[] { Long.valueOf(igImageModelImpl.getCustom2ImageId()) });

		EntityCacheUtil.removeResult(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageImpl.class, igImage.getPrimaryKey());

		return igImage;
	}

	@Override
	public IGImage updateImpl(
		com.liferay.portlet.imagegallery.model.IGImage igImage, boolean merge)
		throws SystemException {
		igImage = toUnwrappedModel(igImage);

		boolean isNew = igImage.isNew();

		IGImageModelImpl igImageModelImpl = (IGImageModelImpl)igImage;

		if (Validator.isNull(igImage.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			igImage.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, igImage, merge);

			igImage.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageImpl.class, igImage.getPrimaryKey(), igImage);

		if (!isNew &&
				(!Validator.equals(igImage.getUuid(),
					igImageModelImpl.getOriginalUuid()) ||
				(igImage.getGroupId() != igImageModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					igImageModelImpl.getOriginalUuid(),
					Long.valueOf(igImageModelImpl.getOriginalGroupId())
				});
		}

		if (isNew ||
				(!Validator.equals(igImage.getUuid(),
					igImageModelImpl.getOriginalUuid()) ||
				(igImage.getGroupId() != igImageModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					igImage.getUuid(), Long.valueOf(igImage.getGroupId())
				}, igImage);
		}

		if (!isNew &&
				(igImage.getSmallImageId() != igImageModelImpl.getOriginalSmallImageId())) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
				new Object[] {
					Long.valueOf(igImageModelImpl.getOriginalSmallImageId())
				});
		}

		if (isNew ||
				(igImage.getSmallImageId() != igImageModelImpl.getOriginalSmallImageId())) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
				new Object[] { Long.valueOf(igImage.getSmallImageId()) },
				igImage);
		}

		if (!isNew &&
				(igImage.getLargeImageId() != igImageModelImpl.getOriginalLargeImageId())) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_LARGEIMAGEID,
				new Object[] {
					Long.valueOf(igImageModelImpl.getOriginalLargeImageId())
				});
		}

		if (isNew ||
				(igImage.getLargeImageId() != igImageModelImpl.getOriginalLargeImageId())) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_LARGEIMAGEID,
				new Object[] { Long.valueOf(igImage.getLargeImageId()) },
				igImage);
		}

		if (!isNew &&
				(igImage.getCustom1ImageId() != igImageModelImpl.getOriginalCustom1ImageId())) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CUSTOM1IMAGEID,
				new Object[] {
					Long.valueOf(igImageModelImpl.getOriginalCustom1ImageId())
				});
		}

		if (isNew ||
				(igImage.getCustom1ImageId() != igImageModelImpl.getOriginalCustom1ImageId())) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CUSTOM1IMAGEID,
				new Object[] { Long.valueOf(igImage.getCustom1ImageId()) },
				igImage);
		}

		if (!isNew &&
				(igImage.getCustom2ImageId() != igImageModelImpl.getOriginalCustom2ImageId())) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CUSTOM2IMAGEID,
				new Object[] {
					Long.valueOf(igImageModelImpl.getOriginalCustom2ImageId())
				});
		}

		if (isNew ||
				(igImage.getCustom2ImageId() != igImageModelImpl.getOriginalCustom2ImageId())) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CUSTOM2IMAGEID,
				new Object[] { Long.valueOf(igImage.getCustom2ImageId()) },
				igImage);
		}

		return igImage;
	}

	protected IGImage toUnwrappedModel(IGImage igImage) {
		if (igImage instanceof IGImageImpl) {
			return igImage;
		}

		IGImageImpl igImageImpl = new IGImageImpl();

		igImageImpl.setNew(igImage.isNew());
		igImageImpl.setPrimaryKey(igImage.getPrimaryKey());

		igImageImpl.setUuid(igImage.getUuid());
		igImageImpl.setImageId(igImage.getImageId());
		igImageImpl.setGroupId(igImage.getGroupId());
		igImageImpl.setCompanyId(igImage.getCompanyId());
		igImageImpl.setUserId(igImage.getUserId());
		igImageImpl.setUserName(igImage.getUserName());
		igImageImpl.setCreateDate(igImage.getCreateDate());
		igImageImpl.setModifiedDate(igImage.getModifiedDate());
		igImageImpl.setFolderId(igImage.getFolderId());
		igImageImpl.setName(igImage.getName());
		igImageImpl.setDescription(igImage.getDescription());
		igImageImpl.setSmallImageId(igImage.getSmallImageId());
		igImageImpl.setLargeImageId(igImage.getLargeImageId());
		igImageImpl.setCustom1ImageId(igImage.getCustom1ImageId());
		igImageImpl.setCustom2ImageId(igImage.getCustom2ImageId());

		return igImageImpl;
	}

	/**
	 * Returns the i g image with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the i g image
	 * @return the i g image
	 * @throws com.liferay.portal.NoSuchModelException if a i g image with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public IGImage findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the i g image with the primary key or throws a {@link com.liferay.portlet.imagegallery.NoSuchImageException} if it could not be found.
	 *
	 * @param imageId the primary key of the i g image
	 * @return the i g image
	 * @throws com.liferay.portlet.imagegallery.NoSuchImageException if a i g image with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public IGImage findByPrimaryKey(long imageId)
		throws NoSuchImageException, SystemException {
		IGImage igImage = fetchByPrimaryKey(imageId);

		if (igImage == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + imageId);
			}

			throw new NoSuchImageException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				imageId);
		}

		return igImage;
	}

	/**
	 * Returns the i g image with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the i g image
	 * @return the i g image, or <code>null</code> if a i g image with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public IGImage fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the i g image with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param imageId the primary key of the i g image
	 * @return the i g image, or <code>null</code> if a i g image with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public IGImage fetchByPrimaryKey(long imageId) throws SystemException {
		IGImage igImage = (IGImage)EntityCacheUtil.getResult(IGImageModelImpl.ENTITY_CACHE_ENABLED,
				IGImageImpl.class, imageId, this);

		if (igImage == _nullIGImage) {
			return null;
		}

		if (igImage == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				igImage = (IGImage)session.get(IGImageImpl.class,
						Long.valueOf(imageId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (igImage != null) {
					cacheResult(igImage);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(IGImageModelImpl.ENTITY_CACHE_ENABLED,
						IGImageImpl.class, imageId, _nullIGImage);
				}

				closeSession(session);
			}
		}

		return igImage;
	}

	/**
	 * Returns all the i g images where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching i g images
	 * @throws SystemException if a system exception occurred
	 */
	public List<IGImage> findByUuid(String uuid) throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the i g images where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of i g images
	 * @param end the upper bound of the range of i g images (not inclusive)
	 * @return the range of matching i g images
	 * @throws SystemException if a system exception occurred
	 */
	public List<IGImage> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the i g images where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of i g images
	 * @param end the upper bound of the range of i g images (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching i g images
	 * @throws SystemException if a system exception occurred
	 */
	public List<IGImage> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<IGImage> list = (List<IGImage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
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

			query.append(_SQL_SELECT_IGIMAGE_WHERE);

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
				query.append(IGImageModelImpl.ORDER_BY_JPQL);
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

				list = (List<IGImage>)QueryUtil.list(q, getDialect(), start, end);
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
	 * Returns the first i g image in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching i g image
	 * @throws com.liferay.portlet.imagegallery.NoSuchImageException if a matching i g image could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public IGImage findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchImageException, SystemException {
		List<IGImage> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchImageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last i g image in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching i g image
	 * @throws com.liferay.portlet.imagegallery.NoSuchImageException if a matching i g image could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public IGImage findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchImageException, SystemException {
		int count = countByUuid(uuid);

		List<IGImage> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchImageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the i g images before and after the current i g image in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param imageId the primary key of the current i g image
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next i g image
	 * @throws com.liferay.portlet.imagegallery.NoSuchImageException if a i g image with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public IGImage[] findByUuid_PrevAndNext(long imageId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchImageException, SystemException {
		IGImage igImage = findByPrimaryKey(imageId);

		Session session = null;

		try {
			session = openSession();

			IGImage[] array = new IGImageImpl[3];

			array[0] = getByUuid_PrevAndNext(session, igImage, uuid,
					orderByComparator, true);

			array[1] = igImage;

			array[2] = getByUuid_PrevAndNext(session, igImage, uuid,
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

	protected IGImage getByUuid_PrevAndNext(Session session, IGImage igImage,
		String uuid, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_IGIMAGE_WHERE);

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
			query.append(IGImageModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByValues(igImage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<IGImage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the i g image where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.imagegallery.NoSuchImageException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching i g image
	 * @throws com.liferay.portlet.imagegallery.NoSuchImageException if a matching i g image could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public IGImage findByUUID_G(String uuid, long groupId)
		throws NoSuchImageException, SystemException {
		IGImage igImage = fetchByUUID_G(uuid, groupId);

		if (igImage == null) {
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

			throw new NoSuchImageException(msg.toString());
		}

		return igImage;
	}

	/**
	 * Returns the i g image where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching i g image, or <code>null</code> if a matching i g image could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public IGImage fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the i g image where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching i g image, or <code>null</code> if a matching i g image could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public IGImage fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_IGIMAGE_WHERE);

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

			query.append(IGImageModelImpl.ORDER_BY_JPQL);

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

				List<IGImage> list = q.list();

				result = list;

				IGImage igImage = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					igImage = list.get(0);

					cacheResult(igImage);

					if ((igImage.getUuid() == null) ||
							!igImage.getUuid().equals(uuid) ||
							(igImage.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, igImage);
					}
				}

				return igImage;
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
				return (IGImage)result;
			}
		}
	}

	/**
	 * Returns all the i g images where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching i g images
	 * @throws SystemException if a system exception occurred
	 */
	public List<IGImage> findByGroupId(long groupId) throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the i g images where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of i g images
	 * @param end the upper bound of the range of i g images (not inclusive)
	 * @return the range of matching i g images
	 * @throws SystemException if a system exception occurred
	 */
	public List<IGImage> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the i g images where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of i g images
	 * @param end the upper bound of the range of i g images (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching i g images
	 * @throws SystemException if a system exception occurred
	 */
	public List<IGImage> findByGroupId(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<IGImage> list = (List<IGImage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
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

			query.append(_SQL_SELECT_IGIMAGE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(IGImageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<IGImage>)QueryUtil.list(q, getDialect(), start, end);
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
	 * Returns the first i g image in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching i g image
	 * @throws com.liferay.portlet.imagegallery.NoSuchImageException if a matching i g image could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public IGImage findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchImageException, SystemException {
		List<IGImage> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchImageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last i g image in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching i g image
	 * @throws com.liferay.portlet.imagegallery.NoSuchImageException if a matching i g image could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public IGImage findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchImageException, SystemException {
		int count = countByGroupId(groupId);

		List<IGImage> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchImageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the i g images before and after the current i g image in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param imageId the primary key of the current i g image
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next i g image
	 * @throws com.liferay.portlet.imagegallery.NoSuchImageException if a i g image with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public IGImage[] findByGroupId_PrevAndNext(long imageId, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchImageException, SystemException {
		IGImage igImage = findByPrimaryKey(imageId);

		Session session = null;

		try {
			session = openSession();

			IGImage[] array = new IGImageImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, igImage, groupId,
					orderByComparator, true);

			array[1] = igImage;

			array[2] = getByGroupId_PrevAndNext(session, igImage, groupId,
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

	protected IGImage getByGroupId_PrevAndNext(Session session,
		IGImage igImage, long groupId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_IGIMAGE_WHERE);

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
			query.append(IGImageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(igImage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<IGImage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the i g images that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching i g images that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<IGImage> filterFindByGroupId(long groupId)
		throws SystemException {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the i g images that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of i g images
	 * @param end the upper bound of the range of i g images (not inclusive)
	 * @return the range of matching i g images that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<IGImage> filterFindByGroupId(long groupId, int start, int end)
		throws SystemException {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the i g images that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of i g images
	 * @param end the upper bound of the range of i g images (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching i g images that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<IGImage> filterFindByGroupId(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
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
			query.append(_FILTER_SQL_SELECT_IGIMAGE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_IGIMAGE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_IGIMAGE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(IGImageModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(IGImageModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				IGImage.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN,
				groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, IGImageImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, IGImageImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<IGImage>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the i g images before and after the current i g image in the ordered set of i g images that the user has permission to view where groupId = &#63;.
	 *
	 * @param imageId the primary key of the current i g image
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next i g image
	 * @throws com.liferay.portlet.imagegallery.NoSuchImageException if a i g image with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public IGImage[] filterFindByGroupId_PrevAndNext(long imageId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchImageException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(imageId, groupId, orderByComparator);
		}

		IGImage igImage = findByPrimaryKey(imageId);

		Session session = null;

		try {
			session = openSession();

			IGImage[] array = new IGImageImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session, igImage,
					groupId, orderByComparator, true);

			array[1] = igImage;

			array[2] = filterGetByGroupId_PrevAndNext(session, igImage,
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

	protected IGImage filterGetByGroupId_PrevAndNext(Session session,
		IGImage igImage, long groupId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_IGIMAGE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_IGIMAGE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_IGIMAGE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(IGImageModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(IGImageModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				IGImage.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN,
				groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, IGImageImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, IGImageImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(igImage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<IGImage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the i g image where smallImageId = &#63; or throws a {@link com.liferay.portlet.imagegallery.NoSuchImageException} if it could not be found.
	 *
	 * @param smallImageId the small image ID
	 * @return the matching i g image
	 * @throws com.liferay.portlet.imagegallery.NoSuchImageException if a matching i g image could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public IGImage findBySmallImageId(long smallImageId)
		throws NoSuchImageException, SystemException {
		IGImage igImage = fetchBySmallImageId(smallImageId);

		if (igImage == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("smallImageId=");
			msg.append(smallImageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchImageException(msg.toString());
		}

		return igImage;
	}

	/**
	 * Returns the i g image where smallImageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param smallImageId the small image ID
	 * @return the matching i g image, or <code>null</code> if a matching i g image could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public IGImage fetchBySmallImageId(long smallImageId)
		throws SystemException {
		return fetchBySmallImageId(smallImageId, true);
	}

	/**
	 * Returns the i g image where smallImageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param smallImageId the small image ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching i g image, or <code>null</code> if a matching i g image could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public IGImage fetchBySmallImageId(long smallImageId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { smallImageId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_IGIMAGE_WHERE);

			query.append(_FINDER_COLUMN_SMALLIMAGEID_SMALLIMAGEID_2);

			query.append(IGImageModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(smallImageId);

				List<IGImage> list = q.list();

				result = list;

				IGImage igImage = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
						finderArgs, list);
				}
				else {
					igImage = list.get(0);

					cacheResult(igImage);

					if ((igImage.getSmallImageId() != smallImageId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
							finderArgs, igImage);
					}
				}

				return igImage;
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
				return (IGImage)result;
			}
		}
	}

	/**
	 * Returns the i g image where largeImageId = &#63; or throws a {@link com.liferay.portlet.imagegallery.NoSuchImageException} if it could not be found.
	 *
	 * @param largeImageId the large image ID
	 * @return the matching i g image
	 * @throws com.liferay.portlet.imagegallery.NoSuchImageException if a matching i g image could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public IGImage findByLargeImageId(long largeImageId)
		throws NoSuchImageException, SystemException {
		IGImage igImage = fetchByLargeImageId(largeImageId);

		if (igImage == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("largeImageId=");
			msg.append(largeImageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchImageException(msg.toString());
		}

		return igImage;
	}

	/**
	 * Returns the i g image where largeImageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param largeImageId the large image ID
	 * @return the matching i g image, or <code>null</code> if a matching i g image could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public IGImage fetchByLargeImageId(long largeImageId)
		throws SystemException {
		return fetchByLargeImageId(largeImageId, true);
	}

	/**
	 * Returns the i g image where largeImageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param largeImageId the large image ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching i g image, or <code>null</code> if a matching i g image could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public IGImage fetchByLargeImageId(long largeImageId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { largeImageId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_LARGEIMAGEID,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_IGIMAGE_WHERE);

			query.append(_FINDER_COLUMN_LARGEIMAGEID_LARGEIMAGEID_2);

			query.append(IGImageModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(largeImageId);

				List<IGImage> list = q.list();

				result = list;

				IGImage igImage = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_LARGEIMAGEID,
						finderArgs, list);
				}
				else {
					igImage = list.get(0);

					cacheResult(igImage);

					if ((igImage.getLargeImageId() != largeImageId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_LARGEIMAGEID,
							finderArgs, igImage);
					}
				}

				return igImage;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_LARGEIMAGEID,
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
				return (IGImage)result;
			}
		}
	}

	/**
	 * Returns the i g image where custom1ImageId = &#63; or throws a {@link com.liferay.portlet.imagegallery.NoSuchImageException} if it could not be found.
	 *
	 * @param custom1ImageId the custom1 image ID
	 * @return the matching i g image
	 * @throws com.liferay.portlet.imagegallery.NoSuchImageException if a matching i g image could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public IGImage findByCustom1ImageId(long custom1ImageId)
		throws NoSuchImageException, SystemException {
		IGImage igImage = fetchByCustom1ImageId(custom1ImageId);

		if (igImage == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("custom1ImageId=");
			msg.append(custom1ImageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchImageException(msg.toString());
		}

		return igImage;
	}

	/**
	 * Returns the i g image where custom1ImageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param custom1ImageId the custom1 image ID
	 * @return the matching i g image, or <code>null</code> if a matching i g image could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public IGImage fetchByCustom1ImageId(long custom1ImageId)
		throws SystemException {
		return fetchByCustom1ImageId(custom1ImageId, true);
	}

	/**
	 * Returns the i g image where custom1ImageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param custom1ImageId the custom1 image ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching i g image, or <code>null</code> if a matching i g image could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public IGImage fetchByCustom1ImageId(long custom1ImageId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { custom1ImageId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_CUSTOM1IMAGEID,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_IGIMAGE_WHERE);

			query.append(_FINDER_COLUMN_CUSTOM1IMAGEID_CUSTOM1IMAGEID_2);

			query.append(IGImageModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(custom1ImageId);

				List<IGImage> list = q.list();

				result = list;

				IGImage igImage = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CUSTOM1IMAGEID,
						finderArgs, list);
				}
				else {
					igImage = list.get(0);

					cacheResult(igImage);

					if ((igImage.getCustom1ImageId() != custom1ImageId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CUSTOM1IMAGEID,
							finderArgs, igImage);
					}
				}

				return igImage;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CUSTOM1IMAGEID,
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
				return (IGImage)result;
			}
		}
	}

	/**
	 * Returns the i g image where custom2ImageId = &#63; or throws a {@link com.liferay.portlet.imagegallery.NoSuchImageException} if it could not be found.
	 *
	 * @param custom2ImageId the custom2 image ID
	 * @return the matching i g image
	 * @throws com.liferay.portlet.imagegallery.NoSuchImageException if a matching i g image could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public IGImage findByCustom2ImageId(long custom2ImageId)
		throws NoSuchImageException, SystemException {
		IGImage igImage = fetchByCustom2ImageId(custom2ImageId);

		if (igImage == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("custom2ImageId=");
			msg.append(custom2ImageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchImageException(msg.toString());
		}

		return igImage;
	}

	/**
	 * Returns the i g image where custom2ImageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param custom2ImageId the custom2 image ID
	 * @return the matching i g image, or <code>null</code> if a matching i g image could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public IGImage fetchByCustom2ImageId(long custom2ImageId)
		throws SystemException {
		return fetchByCustom2ImageId(custom2ImageId, true);
	}

	/**
	 * Returns the i g image where custom2ImageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param custom2ImageId the custom2 image ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching i g image, or <code>null</code> if a matching i g image could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public IGImage fetchByCustom2ImageId(long custom2ImageId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { custom2ImageId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_CUSTOM2IMAGEID,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_IGIMAGE_WHERE);

			query.append(_FINDER_COLUMN_CUSTOM2IMAGEID_CUSTOM2IMAGEID_2);

			query.append(IGImageModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(custom2ImageId);

				List<IGImage> list = q.list();

				result = list;

				IGImage igImage = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CUSTOM2IMAGEID,
						finderArgs, list);
				}
				else {
					igImage = list.get(0);

					cacheResult(igImage);

					if ((igImage.getCustom2ImageId() != custom2ImageId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CUSTOM2IMAGEID,
							finderArgs, igImage);
					}
				}

				return igImage;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CUSTOM2IMAGEID,
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
				return (IGImage)result;
			}
		}
	}

	/**
	 * Returns all the i g images where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching i g images
	 * @throws SystemException if a system exception occurred
	 */
	public List<IGImage> findByG_U(long groupId, long userId)
		throws SystemException {
		return findByG_U(groupId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the i g images where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of i g images
	 * @param end the upper bound of the range of i g images (not inclusive)
	 * @return the range of matching i g images
	 * @throws SystemException if a system exception occurred
	 */
	public List<IGImage> findByG_U(long groupId, long userId, int start, int end)
		throws SystemException {
		return findByG_U(groupId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the i g images where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of i g images
	 * @param end the upper bound of the range of i g images (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching i g images
	 * @throws SystemException if a system exception occurred
	 */
	public List<IGImage> findByG_U(long groupId, long userId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, userId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<IGImage> list = (List<IGImage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_U,
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

			query.append(_SQL_SELECT_IGIMAGE_WHERE);

			query.append(_FINDER_COLUMN_G_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(IGImageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				list = (List<IGImage>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_U,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_U,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first i g image in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching i g image
	 * @throws com.liferay.portlet.imagegallery.NoSuchImageException if a matching i g image could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public IGImage findByG_U_First(long groupId, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchImageException, SystemException {
		List<IGImage> list = findByG_U(groupId, userId, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchImageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last i g image in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching i g image
	 * @throws com.liferay.portlet.imagegallery.NoSuchImageException if a matching i g image could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public IGImage findByG_U_Last(long groupId, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchImageException, SystemException {
		int count = countByG_U(groupId, userId);

		List<IGImage> list = findByG_U(groupId, userId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchImageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the i g images before and after the current i g image in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param imageId the primary key of the current i g image
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next i g image
	 * @throws com.liferay.portlet.imagegallery.NoSuchImageException if a i g image with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public IGImage[] findByG_U_PrevAndNext(long imageId, long groupId,
		long userId, OrderByComparator orderByComparator)
		throws NoSuchImageException, SystemException {
		IGImage igImage = findByPrimaryKey(imageId);

		Session session = null;

		try {
			session = openSession();

			IGImage[] array = new IGImageImpl[3];

			array[0] = getByG_U_PrevAndNext(session, igImage, groupId, userId,
					orderByComparator, true);

			array[1] = igImage;

			array[2] = getByG_U_PrevAndNext(session, igImage, groupId, userId,
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

	protected IGImage getByG_U_PrevAndNext(Session session, IGImage igImage,
		long groupId, long userId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_IGIMAGE_WHERE);

		query.append(_FINDER_COLUMN_G_U_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_USERID_2);

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
			query.append(IGImageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(igImage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<IGImage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the i g images that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching i g images that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<IGImage> filterFindByG_U(long groupId, long userId)
		throws SystemException {
		return filterFindByG_U(groupId, userId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the i g images that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of i g images
	 * @param end the upper bound of the range of i g images (not inclusive)
	 * @return the range of matching i g images that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<IGImage> filterFindByG_U(long groupId, long userId, int start,
		int end) throws SystemException {
		return filterFindByG_U(groupId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the i g images that the user has permissions to view where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of i g images
	 * @param end the upper bound of the range of i g images (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching i g images that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<IGImage> filterFindByG_U(long groupId, long userId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U(groupId, userId, start, end, orderByComparator);
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
			query.append(_FILTER_SQL_SELECT_IGIMAGE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_IGIMAGE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_USERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_IGIMAGE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(IGImageModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(IGImageModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				IGImage.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN,
				groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, IGImageImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, IGImageImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			return (List<IGImage>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the i g images before and after the current i g image in the ordered set of i g images that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * @param imageId the primary key of the current i g image
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next i g image
	 * @throws com.liferay.portlet.imagegallery.NoSuchImageException if a i g image with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public IGImage[] filterFindByG_U_PrevAndNext(long imageId, long groupId,
		long userId, OrderByComparator orderByComparator)
		throws NoSuchImageException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_PrevAndNext(imageId, groupId, userId,
				orderByComparator);
		}

		IGImage igImage = findByPrimaryKey(imageId);

		Session session = null;

		try {
			session = openSession();

			IGImage[] array = new IGImageImpl[3];

			array[0] = filterGetByG_U_PrevAndNext(session, igImage, groupId,
					userId, orderByComparator, true);

			array[1] = igImage;

			array[2] = filterGetByG_U_PrevAndNext(session, igImage, groupId,
					userId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected IGImage filterGetByG_U_PrevAndNext(Session session,
		IGImage igImage, long groupId, long userId,
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
			query.append(_FILTER_SQL_SELECT_IGIMAGE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_IGIMAGE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_USERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_IGIMAGE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(IGImageModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(IGImageModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				IGImage.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN,
				groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, IGImageImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, IGImageImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(igImage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<IGImage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the i g images where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @return the matching i g images
	 * @throws SystemException if a system exception occurred
	 */
	public List<IGImage> findByG_F(long groupId, long folderId)
		throws SystemException {
		return findByG_F(groupId, folderId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the i g images where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of i g images
	 * @param end the upper bound of the range of i g images (not inclusive)
	 * @return the range of matching i g images
	 * @throws SystemException if a system exception occurred
	 */
	public List<IGImage> findByG_F(long groupId, long folderId, int start,
		int end) throws SystemException {
		return findByG_F(groupId, folderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the i g images where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of i g images
	 * @param end the upper bound of the range of i g images (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching i g images
	 * @throws SystemException if a system exception occurred
	 */
	public List<IGImage> findByG_F(long groupId, long folderId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, folderId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<IGImage> list = (List<IGImage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_F,
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

			query.append(_SQL_SELECT_IGIMAGE_WHERE);

			query.append(_FINDER_COLUMN_G_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(IGImageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				list = (List<IGImage>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_F,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_F,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first i g image in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching i g image
	 * @throws com.liferay.portlet.imagegallery.NoSuchImageException if a matching i g image could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public IGImage findByG_F_First(long groupId, long folderId,
		OrderByComparator orderByComparator)
		throws NoSuchImageException, SystemException {
		List<IGImage> list = findByG_F(groupId, folderId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", folderId=");
			msg.append(folderId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchImageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last i g image in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching i g image
	 * @throws com.liferay.portlet.imagegallery.NoSuchImageException if a matching i g image could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public IGImage findByG_F_Last(long groupId, long folderId,
		OrderByComparator orderByComparator)
		throws NoSuchImageException, SystemException {
		int count = countByG_F(groupId, folderId);

		List<IGImage> list = findByG_F(groupId, folderId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", folderId=");
			msg.append(folderId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchImageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the i g images before and after the current i g image in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param imageId the primary key of the current i g image
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next i g image
	 * @throws com.liferay.portlet.imagegallery.NoSuchImageException if a i g image with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public IGImage[] findByG_F_PrevAndNext(long imageId, long groupId,
		long folderId, OrderByComparator orderByComparator)
		throws NoSuchImageException, SystemException {
		IGImage igImage = findByPrimaryKey(imageId);

		Session session = null;

		try {
			session = openSession();

			IGImage[] array = new IGImageImpl[3];

			array[0] = getByG_F_PrevAndNext(session, igImage, groupId,
					folderId, orderByComparator, true);

			array[1] = igImage;

			array[2] = getByG_F_PrevAndNext(session, igImage, groupId,
					folderId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected IGImage getByG_F_PrevAndNext(Session session, IGImage igImage,
		long groupId, long folderId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_IGIMAGE_WHERE);

		query.append(_FINDER_COLUMN_G_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

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
			query.append(IGImageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(folderId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(igImage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<IGImage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the i g images where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @return the matching i g images
	 * @throws SystemException if a system exception occurred
	 */
	public List<IGImage> findByG_F(long groupId, long[] folderIds)
		throws SystemException {
		return findByG_F(groupId, folderIds, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the i g images where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of i g images
	 * @param end the upper bound of the range of i g images (not inclusive)
	 * @return the range of matching i g images
	 * @throws SystemException if a system exception occurred
	 */
	public List<IGImage> findByG_F(long groupId, long[] folderIds, int start,
		int end) throws SystemException {
		return findByG_F(groupId, folderIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the i g images where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of i g images
	 * @param end the upper bound of the range of i g images (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching i g images
	 * @throws SystemException if a system exception occurred
	 */
	public List<IGImage> findByG_F(long groupId, long[] folderIds, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, StringUtil.merge(folderIds),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<IGImage> list = (List<IGImage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_F,
				finderArgs, this);

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_IGIMAGE_WHERE);

			boolean conjunctionable = false;

			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_F_GROUPID_5);

			conjunctionable = true;

			if ((folderIds == null) || (folderIds.length > 0)) {
				if (conjunctionable) {
					query.append(WHERE_AND);
				}

				query.append(StringPool.OPEN_PARENTHESIS);

				for (int i = 0; i < folderIds.length; i++) {
					query.append(_FINDER_COLUMN_G_F_FOLDERID_5);

					if ((i + 1) < folderIds.length) {
						query.append(WHERE_OR);
					}
				}

				query.append(StringPool.CLOSE_PARENTHESIS);

				conjunctionable = true;
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(IGImageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (folderIds != null) {
					qPos.add(folderIds);
				}

				list = (List<IGImage>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_F,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_F,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns all the i g images that the user has permission to view where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @return the matching i g images that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<IGImage> filterFindByG_F(long groupId, long folderId)
		throws SystemException {
		return filterFindByG_F(groupId, folderId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the i g images that the user has permission to view where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of i g images
	 * @param end the upper bound of the range of i g images (not inclusive)
	 * @return the range of matching i g images that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<IGImage> filterFindByG_F(long groupId, long folderId,
		int start, int end) throws SystemException {
		return filterFindByG_F(groupId, folderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the i g images that the user has permissions to view where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of i g images
	 * @param end the upper bound of the range of i g images (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching i g images that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<IGImage> filterFindByG_F(long groupId, long folderId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F(groupId, folderId, start, end, orderByComparator);
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
			query.append(_FILTER_SQL_SELECT_IGIMAGE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_IGIMAGE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_IGIMAGE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(IGImageModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(IGImageModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				IGImage.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN,
				groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, IGImageImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, IGImageImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(folderId);

			return (List<IGImage>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the i g images before and after the current i g image in the ordered set of i g images that the user has permission to view where groupId = &#63; and folderId = &#63;.
	 *
	 * @param imageId the primary key of the current i g image
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next i g image
	 * @throws com.liferay.portlet.imagegallery.NoSuchImageException if a i g image with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public IGImage[] filterFindByG_F_PrevAndNext(long imageId, long groupId,
		long folderId, OrderByComparator orderByComparator)
		throws NoSuchImageException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F_PrevAndNext(imageId, groupId, folderId,
				orderByComparator);
		}

		IGImage igImage = findByPrimaryKey(imageId);

		Session session = null;

		try {
			session = openSession();

			IGImage[] array = new IGImageImpl[3];

			array[0] = filterGetByG_F_PrevAndNext(session, igImage, groupId,
					folderId, orderByComparator, true);

			array[1] = igImage;

			array[2] = filterGetByG_F_PrevAndNext(session, igImage, groupId,
					folderId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected IGImage filterGetByG_F_PrevAndNext(Session session,
		IGImage igImage, long groupId, long folderId,
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
			query.append(_FILTER_SQL_SELECT_IGIMAGE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_IGIMAGE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_IGIMAGE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(IGImageModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(IGImageModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				IGImage.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN,
				groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, IGImageImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, IGImageImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(folderId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(igImage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<IGImage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the i g images that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @return the matching i g images that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<IGImage> filterFindByG_F(long groupId, long[] folderIds)
		throws SystemException {
		return filterFindByG_F(groupId, folderIds, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the i g images that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of i g images
	 * @param end the upper bound of the range of i g images (not inclusive)
	 * @return the range of matching i g images that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<IGImage> filterFindByG_F(long groupId, long[] folderIds,
		int start, int end) throws SystemException {
		return filterFindByG_F(groupId, folderIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the i g images that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of i g images
	 * @param end the upper bound of the range of i g images (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching i g images that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<IGImage> filterFindByG_F(long groupId, long[] folderIds,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F(groupId, folderIds, start, end, orderByComparator);
		}

		StringBundler query = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_IGIMAGE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_IGIMAGE_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean conjunctionable = false;

		if (conjunctionable) {
			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_F_GROUPID_5);

		conjunctionable = true;

		if ((folderIds == null) || (folderIds.length > 0)) {
			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(StringPool.OPEN_PARENTHESIS);

			for (int i = 0; i < folderIds.length; i++) {
				query.append(_FINDER_COLUMN_G_F_FOLDERID_5);

				if ((i + 1) < folderIds.length) {
					query.append(WHERE_OR);
				}
			}

			query.append(StringPool.CLOSE_PARENTHESIS);

			conjunctionable = true;
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_IGIMAGE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(IGImageModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(IGImageModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				IGImage.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN,
				groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, IGImageImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, IGImageImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (folderIds != null) {
				qPos.add(folderIds);
			}

			return (List<IGImage>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns all the i g images where groupId = &#63; and folderId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param name the name
	 * @return the matching i g images
	 * @throws SystemException if a system exception occurred
	 */
	public List<IGImage> findByG_F_N(long groupId, long folderId, String name)
		throws SystemException {
		return findByG_F_N(groupId, folderId, name, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the i g images where groupId = &#63; and folderId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param name the name
	 * @param start the lower bound of the range of i g images
	 * @param end the upper bound of the range of i g images (not inclusive)
	 * @return the range of matching i g images
	 * @throws SystemException if a system exception occurred
	 */
	public List<IGImage> findByG_F_N(long groupId, long folderId, String name,
		int start, int end) throws SystemException {
		return findByG_F_N(groupId, folderId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the i g images where groupId = &#63; and folderId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param name the name
	 * @param start the lower bound of the range of i g images
	 * @param end the upper bound of the range of i g images (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching i g images
	 * @throws SystemException if a system exception occurred
	 */
	public List<IGImage> findByG_F_N(long groupId, long folderId, String name,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, folderId, name,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<IGImage> list = (List<IGImage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_F_N,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_IGIMAGE_WHERE);

			query.append(_FINDER_COLUMN_G_F_N_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_N_FOLDERID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_G_F_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_F_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_F_N_NAME_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(IGImageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				if (name != null) {
					qPos.add(name);
				}

				list = (List<IGImage>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_F_N,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_F_N,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first i g image in the ordered set where groupId = &#63; and folderId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching i g image
	 * @throws com.liferay.portlet.imagegallery.NoSuchImageException if a matching i g image could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public IGImage findByG_F_N_First(long groupId, long folderId, String name,
		OrderByComparator orderByComparator)
		throws NoSuchImageException, SystemException {
		List<IGImage> list = findByG_F_N(groupId, folderId, name, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", folderId=");
			msg.append(folderId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchImageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last i g image in the ordered set where groupId = &#63; and folderId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching i g image
	 * @throws com.liferay.portlet.imagegallery.NoSuchImageException if a matching i g image could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public IGImage findByG_F_N_Last(long groupId, long folderId, String name,
		OrderByComparator orderByComparator)
		throws NoSuchImageException, SystemException {
		int count = countByG_F_N(groupId, folderId, name);

		List<IGImage> list = findByG_F_N(groupId, folderId, name, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", folderId=");
			msg.append(folderId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchImageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the i g images before and after the current i g image in the ordered set where groupId = &#63; and folderId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param imageId the primary key of the current i g image
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next i g image
	 * @throws com.liferay.portlet.imagegallery.NoSuchImageException if a i g image with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public IGImage[] findByG_F_N_PrevAndNext(long imageId, long groupId,
		long folderId, String name, OrderByComparator orderByComparator)
		throws NoSuchImageException, SystemException {
		IGImage igImage = findByPrimaryKey(imageId);

		Session session = null;

		try {
			session = openSession();

			IGImage[] array = new IGImageImpl[3];

			array[0] = getByG_F_N_PrevAndNext(session, igImage, groupId,
					folderId, name, orderByComparator, true);

			array[1] = igImage;

			array[2] = getByG_F_N_PrevAndNext(session, igImage, groupId,
					folderId, name, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected IGImage getByG_F_N_PrevAndNext(Session session, IGImage igImage,
		long groupId, long folderId, String name,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_IGIMAGE_WHERE);

		query.append(_FINDER_COLUMN_G_F_N_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_N_FOLDERID_2);

		if (name == null) {
			query.append(_FINDER_COLUMN_G_F_N_NAME_1);
		}
		else {
			if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_F_N_NAME_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_F_N_NAME_2);
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
			query.append(IGImageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(folderId);

		if (name != null) {
			qPos.add(name);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(igImage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<IGImage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the i g images that the user has permission to view where groupId = &#63; and folderId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param name the name
	 * @return the matching i g images that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<IGImage> filterFindByG_F_N(long groupId, long folderId,
		String name) throws SystemException {
		return filterFindByG_F_N(groupId, folderId, name, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the i g images that the user has permission to view where groupId = &#63; and folderId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param name the name
	 * @param start the lower bound of the range of i g images
	 * @param end the upper bound of the range of i g images (not inclusive)
	 * @return the range of matching i g images that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<IGImage> filterFindByG_F_N(long groupId, long folderId,
		String name, int start, int end) throws SystemException {
		return filterFindByG_F_N(groupId, folderId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the i g images that the user has permissions to view where groupId = &#63; and folderId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param name the name
	 * @param start the lower bound of the range of i g images
	 * @param end the upper bound of the range of i g images (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching i g images that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<IGImage> filterFindByG_F_N(long groupId, long folderId,
		String name, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F_N(groupId, folderId, name, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_IGIMAGE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_IGIMAGE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_F_N_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_N_FOLDERID_2);

		if (name == null) {
			query.append(_FINDER_COLUMN_G_F_N_NAME_1);
		}
		else {
			if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_F_N_NAME_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_F_N_NAME_2);
			}
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_IGIMAGE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(IGImageModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(IGImageModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				IGImage.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN,
				groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, IGImageImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, IGImageImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(folderId);

			if (name != null) {
				qPos.add(name);
			}

			return (List<IGImage>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the i g images before and after the current i g image in the ordered set of i g images that the user has permission to view where groupId = &#63; and folderId = &#63; and name = &#63;.
	 *
	 * @param imageId the primary key of the current i g image
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next i g image
	 * @throws com.liferay.portlet.imagegallery.NoSuchImageException if a i g image with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public IGImage[] filterFindByG_F_N_PrevAndNext(long imageId, long groupId,
		long folderId, String name, OrderByComparator orderByComparator)
		throws NoSuchImageException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F_N_PrevAndNext(imageId, groupId, folderId, name,
				orderByComparator);
		}

		IGImage igImage = findByPrimaryKey(imageId);

		Session session = null;

		try {
			session = openSession();

			IGImage[] array = new IGImageImpl[3];

			array[0] = filterGetByG_F_N_PrevAndNext(session, igImage, groupId,
					folderId, name, orderByComparator, true);

			array[1] = igImage;

			array[2] = filterGetByG_F_N_PrevAndNext(session, igImage, groupId,
					folderId, name, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected IGImage filterGetByG_F_N_PrevAndNext(Session session,
		IGImage igImage, long groupId, long folderId, String name,
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
			query.append(_FILTER_SQL_SELECT_IGIMAGE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_IGIMAGE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_F_N_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_N_FOLDERID_2);

		if (name == null) {
			query.append(_FINDER_COLUMN_G_F_N_NAME_1);
		}
		else {
			if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_F_N_NAME_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_F_N_NAME_2);
			}
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_IGIMAGE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(IGImageModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(IGImageModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				IGImage.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN,
				groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, IGImageImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, IGImageImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(folderId);

		if (name != null) {
			qPos.add(name);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(igImage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<IGImage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the i g images.
	 *
	 * @return the i g images
	 * @throws SystemException if a system exception occurred
	 */
	public List<IGImage> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the i g images.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of i g images
	 * @param end the upper bound of the range of i g images (not inclusive)
	 * @return the range of i g images
	 * @throws SystemException if a system exception occurred
	 */
	public List<IGImage> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the i g images.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of i g images
	 * @param end the upper bound of the range of i g images (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of i g images
	 * @throws SystemException if a system exception occurred
	 */
	public List<IGImage> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<IGImage> list = (List<IGImage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_IGIMAGE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_IGIMAGE.concat(IGImageModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<IGImage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<IGImage>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Removes all the i g images where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (IGImage igImage : findByUuid(uuid)) {
			igImagePersistence.remove(igImage);
		}
	}

	/**
	 * Removes the i g image where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchImageException, SystemException {
		IGImage igImage = findByUUID_G(uuid, groupId);

		igImagePersistence.remove(igImage);
	}

	/**
	 * Removes all the i g images where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (IGImage igImage : findByGroupId(groupId)) {
			igImagePersistence.remove(igImage);
		}
	}

	/**
	 * Removes the i g image where smallImageId = &#63; from the database.
	 *
	 * @param smallImageId the small image ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeBySmallImageId(long smallImageId)
		throws NoSuchImageException, SystemException {
		IGImage igImage = findBySmallImageId(smallImageId);

		igImagePersistence.remove(igImage);
	}

	/**
	 * Removes the i g image where largeImageId = &#63; from the database.
	 *
	 * @param largeImageId the large image ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByLargeImageId(long largeImageId)
		throws NoSuchImageException, SystemException {
		IGImage igImage = findByLargeImageId(largeImageId);

		igImagePersistence.remove(igImage);
	}

	/**
	 * Removes the i g image where custom1ImageId = &#63; from the database.
	 *
	 * @param custom1ImageId the custom1 image ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCustom1ImageId(long custom1ImageId)
		throws NoSuchImageException, SystemException {
		IGImage igImage = findByCustom1ImageId(custom1ImageId);

		igImagePersistence.remove(igImage);
	}

	/**
	 * Removes the i g image where custom2ImageId = &#63; from the database.
	 *
	 * @param custom2ImageId the custom2 image ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCustom2ImageId(long custom2ImageId)
		throws NoSuchImageException, SystemException {
		IGImage igImage = findByCustom2ImageId(custom2ImageId);

		igImagePersistence.remove(igImage);
	}

	/**
	 * Removes all the i g images where groupId = &#63; and userId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_U(long groupId, long userId)
		throws SystemException {
		for (IGImage igImage : findByG_U(groupId, userId)) {
			igImagePersistence.remove(igImage);
		}
	}

	/**
	 * Removes all the i g images where groupId = &#63; and folderId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_F(long groupId, long folderId)
		throws SystemException {
		for (IGImage igImage : findByG_F(groupId, folderId)) {
			igImagePersistence.remove(igImage);
		}
	}

	/**
	 * Removes all the i g images where groupId = &#63; and folderId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param name the name
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_F_N(long groupId, long folderId, String name)
		throws SystemException {
		for (IGImage igImage : findByG_F_N(groupId, folderId, name)) {
			igImagePersistence.remove(igImage);
		}
	}

	/**
	 * Removes all the i g images from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (IGImage igImage : findAll()) {
			igImagePersistence.remove(igImage);
		}
	}

	/**
	 * Returns the number of i g images where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching i g images
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_IGIMAGE_WHERE);

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
	 * Returns the number of i g images where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching i g images
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_IGIMAGE_WHERE);

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
	 * Returns the number of i g images where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching i g images
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_IGIMAGE_WHERE);

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
	 * Returns the number of i g images that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching i g images that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByGroupId(long groupId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_IGIMAGE_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				IGImage.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN,
				groupId);

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
	 * Returns the number of i g images where smallImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @return the number of matching i g images
	 * @throws SystemException if a system exception occurred
	 */
	public int countBySmallImageId(long smallImageId) throws SystemException {
		Object[] finderArgs = new Object[] { smallImageId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_SMALLIMAGEID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_IGIMAGE_WHERE);

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
	 * Returns the number of i g images where largeImageId = &#63;.
	 *
	 * @param largeImageId the large image ID
	 * @return the number of matching i g images
	 * @throws SystemException if a system exception occurred
	 */
	public int countByLargeImageId(long largeImageId) throws SystemException {
		Object[] finderArgs = new Object[] { largeImageId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_LARGEIMAGEID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_IGIMAGE_WHERE);

			query.append(_FINDER_COLUMN_LARGEIMAGEID_LARGEIMAGEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(largeImageId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_LARGEIMAGEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of i g images where custom1ImageId = &#63;.
	 *
	 * @param custom1ImageId the custom1 image ID
	 * @return the number of matching i g images
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCustom1ImageId(long custom1ImageId)
		throws SystemException {
		Object[] finderArgs = new Object[] { custom1ImageId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_CUSTOM1IMAGEID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_IGIMAGE_WHERE);

			query.append(_FINDER_COLUMN_CUSTOM1IMAGEID_CUSTOM1IMAGEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(custom1ImageId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CUSTOM1IMAGEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of i g images where custom2ImageId = &#63;.
	 *
	 * @param custom2ImageId the custom2 image ID
	 * @return the number of matching i g images
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCustom2ImageId(long custom2ImageId)
		throws SystemException {
		Object[] finderArgs = new Object[] { custom2ImageId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_CUSTOM2IMAGEID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_IGIMAGE_WHERE);

			query.append(_FINDER_COLUMN_CUSTOM2IMAGEID_CUSTOM2IMAGEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(custom2ImageId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CUSTOM2IMAGEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of i g images where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching i g images
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_U(long groupId, long userId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_U,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_IGIMAGE_WHERE);

			query.append(_FINDER_COLUMN_G_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_U, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of i g images that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching i g images that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_U(long groupId, long userId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_U(groupId, userId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_IGIMAGE_WHERE);

		query.append(_FINDER_COLUMN_G_U_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_USERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				IGImage.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN,
				groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

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
	 * Returns the number of i g images where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @return the number of matching i g images
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_F(long groupId, long folderId)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, folderId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_F,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_IGIMAGE_WHERE);

			query.append(_FINDER_COLUMN_G_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_F, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of i g images where groupId = &#63; and folderId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @return the number of matching i g images
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_F(long groupId, long[] folderIds)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, StringUtil.merge(folderIds) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_F,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_IGIMAGE_WHERE);

			boolean conjunctionable = false;

			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_F_GROUPID_5);

			conjunctionable = true;

			if ((folderIds == null) || (folderIds.length > 0)) {
				if (conjunctionable) {
					query.append(WHERE_AND);
				}

				query.append(StringPool.OPEN_PARENTHESIS);

				for (int i = 0; i < folderIds.length; i++) {
					query.append(_FINDER_COLUMN_G_F_FOLDERID_5);

					if ((i + 1) < folderIds.length) {
						query.append(WHERE_OR);
					}
				}

				query.append(StringPool.CLOSE_PARENTHESIS);

				conjunctionable = true;
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (folderIds != null) {
					qPos.add(folderIds);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_F, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of i g images that the user has permission to view where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @return the number of matching i g images that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_F(long groupId, long folderId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_F(groupId, folderId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_IGIMAGE_WHERE);

		query.append(_FINDER_COLUMN_G_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				IGImage.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN,
				groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(folderId);

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
	 * Returns the number of i g images that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @return the number of matching i g images that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_F(long groupId, long[] folderIds)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_F(groupId, folderIds);
		}

		StringBundler query = new StringBundler();

		query.append(_FILTER_SQL_COUNT_IGIMAGE_WHERE);

		boolean conjunctionable = false;

		if (conjunctionable) {
			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_F_GROUPID_5);

		conjunctionable = true;

		if ((folderIds == null) || (folderIds.length > 0)) {
			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(StringPool.OPEN_PARENTHESIS);

			for (int i = 0; i < folderIds.length; i++) {
				query.append(_FINDER_COLUMN_G_F_FOLDERID_5);

				if ((i + 1) < folderIds.length) {
					query.append(WHERE_OR);
				}
			}

			query.append(StringPool.CLOSE_PARENTHESIS);

			conjunctionable = true;
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				IGImage.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN,
				groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (folderIds != null) {
				qPos.add(folderIds);
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
	 * Returns the number of i g images where groupId = &#63; and folderId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param name the name
	 * @return the number of matching i g images
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_F_N(long groupId, long folderId, String name)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, folderId, name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_F_N,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_IGIMAGE_WHERE);

			query.append(_FINDER_COLUMN_G_F_N_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_N_FOLDERID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_G_F_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_F_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_F_N_NAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				if (name != null) {
					qPos.add(name);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_F_N,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of i g images that the user has permission to view where groupId = &#63; and folderId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param name the name
	 * @return the number of matching i g images that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_F_N(long groupId, long folderId, String name)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_F_N(groupId, folderId, name);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_IGIMAGE_WHERE);

		query.append(_FINDER_COLUMN_G_F_N_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_N_FOLDERID_2);

		if (name == null) {
			query.append(_FINDER_COLUMN_G_F_N_NAME_1);
		}
		else {
			if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_F_N_NAME_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_F_N_NAME_2);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				IGImage.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN,
				groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(folderId);

			if (name != null) {
				qPos.add(name);
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
	 * Returns the number of i g images.
	 *
	 * @return the number of i g images
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_IGIMAGE);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Initializes the i g image persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.imagegallery.model.IGImage")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<IGImage>> listenersList = new ArrayList<ModelListener<IGImage>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<IGImage>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(IGImageImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
	}

	@BeanReference(type = IGFolderPersistence.class)
	protected IGFolderPersistence igFolderPersistence;
	@BeanReference(type = IGImagePersistence.class)
	protected IGImagePersistence igImagePersistence;
	@BeanReference(type = ImagePersistence.class)
	protected ImagePersistence imagePersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = AssetCategoryPersistence.class)
	protected AssetCategoryPersistence assetCategoryPersistence;
	@BeanReference(type = AssetEntryPersistence.class)
	protected AssetEntryPersistence assetEntryPersistence;
	@BeanReference(type = AssetLinkPersistence.class)
	protected AssetLinkPersistence assetLinkPersistence;
	@BeanReference(type = AssetTagPersistence.class)
	protected AssetTagPersistence assetTagPersistence;
	@BeanReference(type = ExpandoValuePersistence.class)
	protected ExpandoValuePersistence expandoValuePersistence;
	@BeanReference(type = SocialActivityPersistence.class)
	protected SocialActivityPersistence socialActivityPersistence;
	private static final String _SQL_SELECT_IGIMAGE = "SELECT igImage FROM IGImage igImage";
	private static final String _SQL_SELECT_IGIMAGE_WHERE = "SELECT igImage FROM IGImage igImage WHERE ";
	private static final String _SQL_COUNT_IGIMAGE = "SELECT COUNT(igImage) FROM IGImage igImage";
	private static final String _SQL_COUNT_IGIMAGE_WHERE = "SELECT COUNT(igImage) FROM IGImage igImage WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "igImage.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "igImage.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(igImage.uuid IS NULL OR igImage.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "igImage.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "igImage.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(igImage.uuid IS NULL OR igImage.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "igImage.groupId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "igImage.groupId = ?";
	private static final String _FINDER_COLUMN_SMALLIMAGEID_SMALLIMAGEID_2 = "igImage.smallImageId = ?";
	private static final String _FINDER_COLUMN_LARGEIMAGEID_LARGEIMAGEID_2 = "igImage.largeImageId = ?";
	private static final String _FINDER_COLUMN_CUSTOM1IMAGEID_CUSTOM1IMAGEID_2 = "igImage.custom1ImageId = ?";
	private static final String _FINDER_COLUMN_CUSTOM2IMAGEID_CUSTOM2IMAGEID_2 = "igImage.custom2ImageId = ?";
	private static final String _FINDER_COLUMN_G_U_GROUPID_2 = "igImage.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_USERID_2 = "igImage.userId = ?";
	private static final String _FINDER_COLUMN_G_F_GROUPID_2 = "igImage.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_GROUPID_5 = "(" +
		_removeConjunction(_FINDER_COLUMN_G_F_GROUPID_2) + ")";
	private static final String _FINDER_COLUMN_G_F_FOLDERID_2 = "igImage.folderId = ?";
	private static final String _FINDER_COLUMN_G_F_FOLDERID_5 = "(" +
		_removeConjunction(_FINDER_COLUMN_G_F_FOLDERID_2) + ")";
	private static final String _FINDER_COLUMN_G_F_N_GROUPID_2 = "igImage.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_N_FOLDERID_2 = "igImage.folderId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_N_NAME_1 = "igImage.name IS NULL";
	private static final String _FINDER_COLUMN_G_F_N_NAME_2 = "igImage.name = ?";
	private static final String _FINDER_COLUMN_G_F_N_NAME_3 = "(igImage.name IS NULL OR igImage.name = ?)";

	private static String _removeConjunction(String sql) {
		int pos = sql.indexOf(" AND ");

		if (pos != -1) {
			sql = sql.substring(0, pos);
		}

		return sql;
	}

	private static final String _FILTER_SQL_SELECT_IGIMAGE_WHERE = "SELECT DISTINCT {igImage.*} FROM IGImage igImage WHERE ";
	private static final String _FILTER_SQL_SELECT_IGIMAGE_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {IGImage.*} FROM (SELECT DISTINCT igImage.imageId FROM IGImage igImage WHERE ";
	private static final String _FILTER_SQL_SELECT_IGIMAGE_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN IGImage ON TEMP_TABLE.imageId = IGImage.imageId";
	private static final String _FILTER_SQL_COUNT_IGIMAGE_WHERE = "SELECT COUNT(DISTINCT igImage.imageId) AS COUNT_VALUE FROM IGImage igImage WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "igImage";
	private static final String _FILTER_ENTITY_TABLE = "IGImage";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "igImage.imageId";
	private static final String _ORDER_BY_ENTITY_ALIAS = "igImage.";
	private static final String _ORDER_BY_ENTITY_TABLE = "IGImage.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No IGImage exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No IGImage exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(IGImagePersistenceImpl.class);
	private static IGImage _nullIGImage = new IGImageImpl() {
			public Object clone() {
				return this;
			}

			public CacheModel<IGImage> toCacheModel() {
				return _nullIGImageCacheModel;
			}
		};

	private static CacheModel<IGImage> _nullIGImageCacheModel = new CacheModel<IGImage>() {
			public IGImage toEntityModel() {
				return _nullIGImage;
			}
		};
}