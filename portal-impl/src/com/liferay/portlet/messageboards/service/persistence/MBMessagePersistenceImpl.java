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

package com.liferay.portlet.messageboards.service.persistence;

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
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.util.ContentTypes;
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
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.CompanyPersistence;
import com.liferay.portal.service.persistence.GroupPersistence;
import com.liferay.portal.service.persistence.LockPersistence;
import com.liferay.portal.service.persistence.PortletPreferencesPersistence;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.SubscriptionPersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.asset.service.persistence.AssetEntryPersistence;
import com.liferay.portlet.asset.service.persistence.AssetLinkPersistence;
import com.liferay.portlet.asset.service.persistence.AssetTagPersistence;
import com.liferay.portlet.blogs.service.persistence.BlogsEntryPersistence;
import com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence;
import com.liferay.portlet.messageboards.NoSuchMessageException;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.impl.MBMessageImpl;
import com.liferay.portlet.messageboards.model.impl.MBMessageModelImpl;
import com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence;
import com.liferay.portlet.social.service.persistence.SocialActivityPersistence;
import com.liferay.portlet.social.service.persistence.SocialEquityLogPersistence;
import com.liferay.portlet.wiki.service.persistence.WikiPagePersistence;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the message-boards message service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBMessagePersistence
 * @see MBMessageUtil
 * @generated
 */
public class MBMessagePersistenceImpl extends BasePersistenceImpl<MBMessage>
	implements MBMessagePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link MBMessageUtil} to access the message-boards message persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = MBMessageImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_THREADID = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST, "findByThreadId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_THREADID = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByThreadId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_THREADREPLIES = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST, "findByThreadReplies",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_THREADREPLIES = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByThreadReplies",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_USERID = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_U = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST, "findByG_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_C = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST, "findByG_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByG_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST, "findByG_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByG_S",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_C_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST, "findByC_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByC_S",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_U_C = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST, "findByU_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_U_C = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByU_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_C_C = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_T_P = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST, "findByT_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_T_P = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByT_P",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_T_A = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST, "findByT_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_T_A = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByT_A",
			new String[] { Long.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_T_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST, "findByT_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_T_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByT_S",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_TR_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST, "findByTR_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_TR_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByTR_S",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_U_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST, "findByG_U_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByG_U_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_G_C_T = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST, "findByG_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_T = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByG_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_G_C_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST, "findByG_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByG_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_U_C_C = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST, "findByU_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_U_C_C = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByU_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_U_C_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST, "findByU_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_U_C_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByU_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_C_C_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST, "findByC_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByC_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_G_C_T_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST, "findByG_C_T_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_T_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByG_C_T_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_U_C_C_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST, "findByU_C_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_U_C_C_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByU_C_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the message-boards message in the entity cache if it is enabled.
	 *
	 * @param mbMessage the message-boards message
	 */
	public void cacheResult(MBMessage mbMessage) {
		EntityCacheUtil.putResult(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageImpl.class, mbMessage.getPrimaryKey(), mbMessage);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				mbMessage.getUuid(), Long.valueOf(mbMessage.getGroupId())
			}, mbMessage);

		mbMessage.resetOriginalValues();
	}

	/**
	 * Caches the message-boards messages in the entity cache if it is enabled.
	 *
	 * @param mbMessages the message-boards messages
	 */
	public void cacheResult(List<MBMessage> mbMessages) {
		for (MBMessage mbMessage : mbMessages) {
			if (EntityCacheUtil.getResult(
						MBMessageModelImpl.ENTITY_CACHE_ENABLED,
						MBMessageImpl.class, mbMessage.getPrimaryKey(), this) == null) {
				cacheResult(mbMessage);
			}
		}
	}

	/**
	 * Clears the cache for all message-boards messages.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(MBMessageImpl.class.getName());
		}

		EntityCacheUtil.clearCache(MBMessageImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the message-boards message.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(MBMessage mbMessage) {
		EntityCacheUtil.removeResult(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageImpl.class, mbMessage.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				mbMessage.getUuid(), Long.valueOf(mbMessage.getGroupId())
			});
	}

	/**
	 * Creates a new message-boards message with the primary key. Does not add the message-boards message to the database.
	 *
	 * @param messageId the primary key for the new message-boards message
	 * @return the new message-boards message
	 */
	public MBMessage create(long messageId) {
		MBMessage mbMessage = new MBMessageImpl();

		mbMessage.setNew(true);
		mbMessage.setPrimaryKey(messageId);

		String uuid = PortalUUIDUtil.generate();

		mbMessage.setUuid(uuid);

		return mbMessage;
	}

	/**
	 * Removes the message-boards message with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the message-boards message
	 * @return the message-boards message that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MBMessage remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the message-boards message with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param messageId the primary key of the message-boards message
	 * @return the message-boards message that was removed
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage remove(long messageId)
		throws NoSuchMessageException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MBMessage mbMessage = (MBMessage)session.get(MBMessageImpl.class,
					Long.valueOf(messageId));

			if (mbMessage == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + messageId);
				}

				throw new NoSuchMessageException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					messageId);
			}

			return mbMessagePersistence.remove(mbMessage);
		}
		catch (NoSuchMessageException nsee) {
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
	 * Removes the message-boards message from the database. Also notifies the appropriate model listeners.
	 *
	 * @param mbMessage the message-boards message
	 * @return the message-boards message that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MBMessage remove(MBMessage mbMessage) throws SystemException {
		return super.remove(mbMessage);
	}

	@Override
	protected MBMessage removeImpl(MBMessage mbMessage)
		throws SystemException {
		mbMessage = toUnwrappedModel(mbMessage);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, mbMessage);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		MBMessageModelImpl mbMessageModelImpl = (MBMessageModelImpl)mbMessage;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				mbMessageModelImpl.getUuid(),
				Long.valueOf(mbMessageModelImpl.getGroupId())
			});

		EntityCacheUtil.removeResult(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageImpl.class, mbMessage.getPrimaryKey());

		return mbMessage;
	}

	@Override
	public MBMessage updateImpl(
		com.liferay.portlet.messageboards.model.MBMessage mbMessage,
		boolean merge) throws SystemException {
		mbMessage = toUnwrappedModel(mbMessage);

		boolean isNew = mbMessage.isNew();

		MBMessageModelImpl mbMessageModelImpl = (MBMessageModelImpl)mbMessage;

		if (Validator.isNull(mbMessage.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			mbMessage.setUuid(uuid);
		}

		long userId = GetterUtil.getLong(PrincipalThreadLocal.getName());

		if (userId > 0) {
			long companyId = mbMessage.getCompanyId();

			long groupId = mbMessage.getGroupId();

			long messageId = 0;

			if (!isNew) {
				messageId = mbMessage.getPrimaryKey();
			}

			try {
				mbMessage.setSubject(SanitizerUtil.sanitize(companyId, groupId,
						userId,
						com.liferay.portlet.messageboards.model.MBMessage.class.getName(),
						messageId, ContentTypes.TEXT_PLAIN, Sanitizer.MODE_ALL,
						mbMessage.getSubject(), null));
			}
			catch (SanitizerException se) {
				throw new SystemException(se);
			}
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, mbMessage, merge);

			mbMessage.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageImpl.class, mbMessage.getPrimaryKey(), mbMessage);

		if (!isNew &&
				(!Validator.equals(mbMessage.getUuid(),
					mbMessageModelImpl.getOriginalUuid()) ||
				(mbMessage.getGroupId() != mbMessageModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					mbMessageModelImpl.getOriginalUuid(),
					Long.valueOf(mbMessageModelImpl.getOriginalGroupId())
				});
		}

		if (isNew ||
				(!Validator.equals(mbMessage.getUuid(),
					mbMessageModelImpl.getOriginalUuid()) ||
				(mbMessage.getGroupId() != mbMessageModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					mbMessage.getUuid(), Long.valueOf(mbMessage.getGroupId())
				}, mbMessage);
		}

		return mbMessage;
	}

	protected MBMessage toUnwrappedModel(MBMessage mbMessage) {
		if (mbMessage instanceof MBMessageImpl) {
			return mbMessage;
		}

		MBMessageImpl mbMessageImpl = new MBMessageImpl();

		mbMessageImpl.setNew(mbMessage.isNew());
		mbMessageImpl.setPrimaryKey(mbMessage.getPrimaryKey());

		mbMessageImpl.setUuid(mbMessage.getUuid());
		mbMessageImpl.setMessageId(mbMessage.getMessageId());
		mbMessageImpl.setGroupId(mbMessage.getGroupId());
		mbMessageImpl.setCompanyId(mbMessage.getCompanyId());
		mbMessageImpl.setUserId(mbMessage.getUserId());
		mbMessageImpl.setUserName(mbMessage.getUserName());
		mbMessageImpl.setCreateDate(mbMessage.getCreateDate());
		mbMessageImpl.setModifiedDate(mbMessage.getModifiedDate());
		mbMessageImpl.setClassNameId(mbMessage.getClassNameId());
		mbMessageImpl.setClassPK(mbMessage.getClassPK());
		mbMessageImpl.setCategoryId(mbMessage.getCategoryId());
		mbMessageImpl.setThreadId(mbMessage.getThreadId());
		mbMessageImpl.setRootMessageId(mbMessage.getRootMessageId());
		mbMessageImpl.setParentMessageId(mbMessage.getParentMessageId());
		mbMessageImpl.setSubject(mbMessage.getSubject());
		mbMessageImpl.setBody(mbMessage.getBody());
		mbMessageImpl.setFormat(mbMessage.getFormat());
		mbMessageImpl.setAttachments(mbMessage.isAttachments());
		mbMessageImpl.setAnonymous(mbMessage.isAnonymous());
		mbMessageImpl.setPriority(mbMessage.getPriority());
		mbMessageImpl.setAllowPingbacks(mbMessage.isAllowPingbacks());
		mbMessageImpl.setAnswer(mbMessage.isAnswer());
		mbMessageImpl.setStatus(mbMessage.getStatus());
		mbMessageImpl.setStatusByUserId(mbMessage.getStatusByUserId());
		mbMessageImpl.setStatusByUserName(mbMessage.getStatusByUserName());
		mbMessageImpl.setStatusDate(mbMessage.getStatusDate());

		return mbMessageImpl;
	}

	/**
	 * Returns the message-boards message with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the message-boards message
	 * @return the message-boards message
	 * @throws com.liferay.portal.NoSuchModelException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MBMessage findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the message-boards message with the primary key or throws a {@link com.liferay.portlet.messageboards.NoSuchMessageException} if it could not be found.
	 *
	 * @param messageId the primary key of the message-boards message
	 * @return the message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByPrimaryKey(long messageId)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = fetchByPrimaryKey(messageId);

		if (mbMessage == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + messageId);
			}

			throw new NoSuchMessageException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				messageId);
		}

		return mbMessage;
	}

	/**
	 * Returns the message-boards message with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the message-boards message
	 * @return the message-boards message, or <code>null</code> if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MBMessage fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the message-boards message with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param messageId the primary key of the message-boards message
	 * @return the message-boards message, or <code>null</code> if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByPrimaryKey(long messageId)
		throws SystemException {
		MBMessage mbMessage = (MBMessage)EntityCacheUtil.getResult(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
				MBMessageImpl.class, messageId, this);

		if (mbMessage == _nullMBMessage) {
			return null;
		}

		if (mbMessage == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				mbMessage = (MBMessage)session.get(MBMessageImpl.class,
						Long.valueOf(messageId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (mbMessage != null) {
					cacheResult(mbMessage);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
						MBMessageImpl.class, messageId, _nullMBMessage);
				}

				closeSession(session);
			}
		}

		return mbMessage;
	}

	/**
	 * Returns all the message-boards messages where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByUuid(String uuid) throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message-boards messages where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message-boards messages where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
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

			query.append(_SQL_SELECT_MBMESSAGE_WHERE);

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
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
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

				list = (List<MBMessage>)QueryUtil.list(q, getDialect(), start,
						end);
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
	 * Returns the first message-boards message in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		List<MBMessage> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last message-boards message in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		int count = countByUuid(uuid);

		List<MBMessage> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage[] findByUuid_PrevAndNext(long messageId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		Session session = null;

		try {
			session = openSession();

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = getByUuid_PrevAndNext(session, mbMessage, uuid,
					orderByComparator, true);

			array[1] = mbMessage;

			array[2] = getByUuid_PrevAndNext(session, mbMessage, uuid,
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

	protected MBMessage getByUuid_PrevAndNext(Session session,
		MBMessage mbMessage, String uuid, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBMESSAGE_WHERE);

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
			query.append(MBMessageModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByValues(mbMessage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMessage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the message-boards message where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.messageboards.NoSuchMessageException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByUUID_G(String uuid, long groupId)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = fetchByUUID_G(uuid, groupId);

		if (mbMessage == null) {
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

			throw new NoSuchMessageException(msg.toString());
		}

		return mbMessage;
	}

	/**
	 * Returns the message-boards message where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the message-boards message where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_MBMESSAGE_WHERE);

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

			query.append(MBMessageModelImpl.ORDER_BY_JPQL);

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

				List<MBMessage> list = q.list();

				result = list;

				MBMessage mbMessage = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					mbMessage = list.get(0);

					cacheResult(mbMessage);

					if ((mbMessage.getUuid() == null) ||
							!mbMessage.getUuid().equals(uuid) ||
							(mbMessage.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, mbMessage);
					}
				}

				return mbMessage;
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
				return (MBMessage)result;
			}
		}
	}

	/**
	 * Returns all the message-boards messages where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message-boards messages where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message-boards messages where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByGroupId(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
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

			query.append(_SQL_SELECT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<MBMessage>)QueryUtil.list(q, getDialect(), start,
						end);
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
	 * Returns the first message-boards message in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		List<MBMessage> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		int count = countByGroupId(groupId);

		List<MBMessage> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage[] findByGroupId_PrevAndNext(long messageId, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		Session session = null;

		try {
			session = openSession();

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, mbMessage, groupId,
					orderByComparator, true);

			array[1] = mbMessage;

			array[2] = getByGroupId_PrevAndNext(session, mbMessage, groupId,
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

	protected MBMessage getByGroupId_PrevAndNext(Session session,
		MBMessage mbMessage, long groupId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBMESSAGE_WHERE);

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
			query.append(MBMessageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(mbMessage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMessage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message-boards messages that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching message-boards messages that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> filterFindByGroupId(long groupId)
		throws SystemException {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message-boards messages that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> filterFindByGroupId(long groupId, int start, int end)
		throws SystemException {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message-boards messages that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> filterFindByGroupId(long groupId, int start,
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
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBMessageModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBMessage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, MBMessageImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, MBMessageImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<MBMessage>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set of message-boards messages that the user has permission to view where groupId = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage[] filterFindByGroupId_PrevAndNext(long messageId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(messageId, groupId,
				orderByComparator);
		}

		MBMessage mbMessage = findByPrimaryKey(messageId);

		Session session = null;

		try {
			session = openSession();

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session, mbMessage,
					groupId, orderByComparator, true);

			array[1] = mbMessage;

			array[2] = filterGetByGroupId_PrevAndNext(session, mbMessage,
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

	protected MBMessage filterGetByGroupId_PrevAndNext(Session session,
		MBMessage mbMessage, long groupId, OrderByComparator orderByComparator,
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
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBMessageModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBMessage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, MBMessageImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, MBMessageImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(mbMessage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMessage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message-boards messages where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByCompanyId(long companyId)
		throws SystemException {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the message-boards messages where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message-boards messages where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByCompanyId(long companyId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				companyId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
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

			query.append(_SQL_SELECT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<MBMessage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_COMPANYID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first message-boards message in the ordered set where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		List<MBMessage> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last message-boards message in the ordered set where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		int count = countByCompanyId(companyId);

		List<MBMessage> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage[] findByCompanyId_PrevAndNext(long messageId,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		Session session = null;

		try {
			session = openSession();

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, mbMessage,
					companyId, orderByComparator, true);

			array[1] = mbMessage;

			array[2] = getByCompanyId_PrevAndNext(session, mbMessage,
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

	protected MBMessage getByCompanyId_PrevAndNext(Session session,
		MBMessage mbMessage, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBMESSAGE_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
			query.append(MBMessageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(mbMessage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMessage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message-boards messages where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @return the matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByThreadId(long threadId)
		throws SystemException {
		return findByThreadId(threadId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the message-boards messages where threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByThreadId(long threadId, int start, int end)
		throws SystemException {
		return findByThreadId(threadId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message-boards messages where threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByThreadId(long threadId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				threadId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_THREADID,
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

			query.append(_SQL_SELECT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_THREADID_THREADID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(threadId);

				list = (List<MBMessage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_THREADID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_THREADID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first message-boards message in the ordered set where threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByThreadId_First(long threadId,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		List<MBMessage> list = findByThreadId(threadId, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("threadId=");
			msg.append(threadId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last message-boards message in the ordered set where threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByThreadId_Last(long threadId,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		int count = countByThreadId(threadId);

		List<MBMessage> list = findByThreadId(threadId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("threadId=");
			msg.append(threadId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage[] findByThreadId_PrevAndNext(long messageId,
		long threadId, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		Session session = null;

		try {
			session = openSession();

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = getByThreadId_PrevAndNext(session, mbMessage, threadId,
					orderByComparator, true);

			array[1] = mbMessage;

			array[2] = getByThreadId_PrevAndNext(session, mbMessage, threadId,
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

	protected MBMessage getByThreadId_PrevAndNext(Session session,
		MBMessage mbMessage, long threadId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBMESSAGE_WHERE);

		query.append(_FINDER_COLUMN_THREADID_THREADID_2);

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
			query.append(MBMessageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(threadId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(mbMessage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMessage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message-boards messages where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @return the matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByThreadReplies(long threadId)
		throws SystemException {
		return findByThreadReplies(threadId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message-boards messages where threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByThreadReplies(long threadId, int start, int end)
		throws SystemException {
		return findByThreadReplies(threadId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message-boards messages where threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByThreadReplies(long threadId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				threadId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_THREADREPLIES,
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

			query.append(_SQL_SELECT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_THREADREPLIES_THREADID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(threadId);

				list = (List<MBMessage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_THREADREPLIES,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_THREADREPLIES,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first message-boards message in the ordered set where threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByThreadReplies_First(long threadId,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		List<MBMessage> list = findByThreadReplies(threadId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("threadId=");
			msg.append(threadId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last message-boards message in the ordered set where threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByThreadReplies_Last(long threadId,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		int count = countByThreadReplies(threadId);

		List<MBMessage> list = findByThreadReplies(threadId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("threadId=");
			msg.append(threadId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage[] findByThreadReplies_PrevAndNext(long messageId,
		long threadId, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		Session session = null;

		try {
			session = openSession();

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = getByThreadReplies_PrevAndNext(session, mbMessage,
					threadId, orderByComparator, true);

			array[1] = mbMessage;

			array[2] = getByThreadReplies_PrevAndNext(session, mbMessage,
					threadId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBMessage getByThreadReplies_PrevAndNext(Session session,
		MBMessage mbMessage, long threadId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBMESSAGE_WHERE);

		query.append(_FINDER_COLUMN_THREADREPLIES_THREADID_2);

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
			query.append(MBMessageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(threadId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(mbMessage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMessage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message-boards messages where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByUserId(long userId) throws SystemException {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message-boards messages where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message-boards messages where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByUserId(long userId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				userId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_USERID,
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

			query.append(_SQL_SELECT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = (List<MBMessage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_USERID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_USERID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first message-boards message in the ordered set where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByUserId_First(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		List<MBMessage> list = findByUserId(userId, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last message-boards message in the ordered set where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByUserId_Last(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		int count = countByUserId(userId);

		List<MBMessage> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage[] findByUserId_PrevAndNext(long messageId, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		Session session = null;

		try {
			session = openSession();

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = getByUserId_PrevAndNext(session, mbMessage, userId,
					orderByComparator, true);

			array[1] = mbMessage;

			array[2] = getByUserId_PrevAndNext(session, mbMessage, userId,
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

	protected MBMessage getByUserId_PrevAndNext(Session session,
		MBMessage mbMessage, long userId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBMESSAGE_WHERE);

		query.append(_FINDER_COLUMN_USERID_USERID_2);

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
			query.append(MBMessageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(mbMessage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMessage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message-boards messages where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByG_U(long groupId, long userId)
		throws SystemException {
		return findByG_U(groupId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the message-boards messages where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByG_U(long groupId, long userId, int start,
		int end) throws SystemException {
		return findByG_U(groupId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message-boards messages where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByG_U(long groupId, long userId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, userId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_U,
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

			query.append(_SQL_SELECT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_G_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				list = (List<MBMessage>)QueryUtil.list(q, getDialect(), start,
						end);
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
	 * Returns the first message-boards message in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByG_U_First(long groupId, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		List<MBMessage> list = findByG_U(groupId, userId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByG_U_Last(long groupId, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		int count = countByG_U(groupId, userId);

		List<MBMessage> list = findByG_U(groupId, userId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage[] findByG_U_PrevAndNext(long messageId, long groupId,
		long userId, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		Session session = null;

		try {
			session = openSession();

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = getByG_U_PrevAndNext(session, mbMessage, groupId,
					userId, orderByComparator, true);

			array[1] = mbMessage;

			array[2] = getByG_U_PrevAndNext(session, mbMessage, groupId,
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

	protected MBMessage getByG_U_PrevAndNext(Session session,
		MBMessage mbMessage, long groupId, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBMESSAGE_WHERE);

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
			query.append(MBMessageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(mbMessage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMessage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message-boards messages that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching message-boards messages that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> filterFindByG_U(long groupId, long userId)
		throws SystemException {
		return filterFindByG_U(groupId, userId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message-boards messages that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> filterFindByG_U(long groupId, long userId,
		int start, int end) throws SystemException {
		return filterFindByG_U(groupId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message-boards messages that the user has permissions to view where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> filterFindByG_U(long groupId, long userId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
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
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_USERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBMessageModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBMessage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, MBMessageImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, MBMessageImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			return (List<MBMessage>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set of message-boards messages that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage[] filterFindByG_U_PrevAndNext(long messageId,
		long groupId, long userId, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_PrevAndNext(messageId, groupId, userId,
				orderByComparator);
		}

		MBMessage mbMessage = findByPrimaryKey(messageId);

		Session session = null;

		try {
			session = openSession();

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = filterGetByG_U_PrevAndNext(session, mbMessage, groupId,
					userId, orderByComparator, true);

			array[1] = mbMessage;

			array[2] = filterGetByG_U_PrevAndNext(session, mbMessage, groupId,
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

	protected MBMessage filterGetByG_U_PrevAndNext(Session session,
		MBMessage mbMessage, long groupId, long userId,
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
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_USERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBMessageModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBMessage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, MBMessageImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, MBMessageImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(mbMessage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMessage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message-boards messages where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @return the matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByG_C(long groupId, long categoryId)
		throws SystemException {
		return findByG_C(groupId, categoryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message-boards messages where groupId = &#63; and categoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByG_C(long groupId, long categoryId, int start,
		int end) throws SystemException {
		return findByG_C(groupId, categoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message-boards messages where groupId = &#63; and categoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByG_C(long groupId, long categoryId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, categoryId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_C,
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

			query.append(_SQL_SELECT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_G_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_CATEGORYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

				list = (List<MBMessage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_C,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_C,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first message-boards message in the ordered set where groupId = &#63; and categoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByG_C_First(long groupId, long categoryId,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		List<MBMessage> list = findByG_C(groupId, categoryId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", categoryId=");
			msg.append(categoryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63; and categoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByG_C_Last(long groupId, long categoryId,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		int count = countByG_C(groupId, categoryId);

		List<MBMessage> list = findByG_C(groupId, categoryId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", categoryId=");
			msg.append(categoryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where groupId = &#63; and categoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage[] findByG_C_PrevAndNext(long messageId, long groupId,
		long categoryId, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		Session session = null;

		try {
			session = openSession();

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = getByG_C_PrevAndNext(session, mbMessage, groupId,
					categoryId, orderByComparator, true);

			array[1] = mbMessage;

			array[2] = getByG_C_PrevAndNext(session, mbMessage, groupId,
					categoryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBMessage getByG_C_PrevAndNext(Session session,
		MBMessage mbMessage, long groupId, long categoryId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBMESSAGE_WHERE);

		query.append(_FINDER_COLUMN_G_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_CATEGORYID_2);

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
			query.append(MBMessageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(categoryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(mbMessage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMessage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @return the matching message-boards messages that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> filterFindByG_C(long groupId, long categoryId)
		throws SystemException {
		return filterFindByG_C(groupId, categoryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> filterFindByG_C(long groupId, long categoryId,
		int start, int end) throws SystemException {
		return filterFindByG_C(groupId, categoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message-boards messages that the user has permissions to view where groupId = &#63; and categoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> filterFindByG_C(long groupId, long categoryId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C(groupId, categoryId, start, end, orderByComparator);
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
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_CATEGORYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBMessageModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBMessage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, MBMessageImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, MBMessageImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(categoryId);

			return (List<MBMessage>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set of message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage[] filterFindByG_C_PrevAndNext(long messageId,
		long groupId, long categoryId, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_PrevAndNext(messageId, groupId, categoryId,
				orderByComparator);
		}

		MBMessage mbMessage = findByPrimaryKey(messageId);

		Session session = null;

		try {
			session = openSession();

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = filterGetByG_C_PrevAndNext(session, mbMessage, groupId,
					categoryId, orderByComparator, true);

			array[1] = mbMessage;

			array[2] = filterGetByG_C_PrevAndNext(session, mbMessage, groupId,
					categoryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBMessage filterGetByG_C_PrevAndNext(Session session,
		MBMessage mbMessage, long groupId, long categoryId,
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
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_CATEGORYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBMessageModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBMessage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, MBMessageImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, MBMessageImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(categoryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(mbMessage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMessage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message-boards messages where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByG_S(long groupId, int status)
		throws SystemException {
		return findByG_S(groupId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the message-boards messages where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByG_S(long groupId, int status, int start,
		int end) throws SystemException {
		return findByG_S(groupId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message-boards messages where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByG_S(long groupId, int status, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, status,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_S,
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

			query.append(_SQL_SELECT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_G_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(status);

				list = (List<MBMessage>)QueryUtil.list(q, getDialect(), start,
						end);
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
	 * Returns the first message-boards message in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByG_S_First(long groupId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		List<MBMessage> list = findByG_S(groupId, status, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByG_S_Last(long groupId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		int count = countByG_S(groupId, status);

		List<MBMessage> list = findByG_S(groupId, status, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage[] findByG_S_PrevAndNext(long messageId, long groupId,
		int status, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		Session session = null;

		try {
			session = openSession();

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = getByG_S_PrevAndNext(session, mbMessage, groupId,
					status, orderByComparator, true);

			array[1] = mbMessage;

			array[2] = getByG_S_PrevAndNext(session, mbMessage, groupId,
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

	protected MBMessage getByG_S_PrevAndNext(Session session,
		MBMessage mbMessage, long groupId, int status,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBMESSAGE_WHERE);

		query.append(_FINDER_COLUMN_G_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_S_STATUS_2);

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
			query.append(MBMessageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(mbMessage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMessage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message-boards messages that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching message-boards messages that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> filterFindByG_S(long groupId, int status)
		throws SystemException {
		return filterFindByG_S(groupId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message-boards messages that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> filterFindByG_S(long groupId, int status, int start,
		int end) throws SystemException {
		return filterFindByG_S(groupId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message-boards messages that the user has permissions to view where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> filterFindByG_S(long groupId, int status, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_S(groupId, status, start, end, orderByComparator);
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
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBMessageModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBMessage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, MBMessageImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, MBMessageImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(status);

			return (List<MBMessage>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set of message-boards messages that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage[] filterFindByG_S_PrevAndNext(long messageId,
		long groupId, int status, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_S_PrevAndNext(messageId, groupId, status,
				orderByComparator);
		}

		MBMessage mbMessage = findByPrimaryKey(messageId);

		Session session = null;

		try {
			session = openSession();

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = filterGetByG_S_PrevAndNext(session, mbMessage, groupId,
					status, orderByComparator, true);

			array[1] = mbMessage;

			array[2] = filterGetByG_S_PrevAndNext(session, mbMessage, groupId,
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

	protected MBMessage filterGetByG_S_PrevAndNext(Session session,
		MBMessage mbMessage, long groupId, int status,
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
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBMessageModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBMessage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, MBMessageImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, MBMessageImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(mbMessage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMessage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message-boards messages where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByC_S(long companyId, int status)
		throws SystemException {
		return findByC_S(companyId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message-boards messages where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByC_S(long companyId, int status, int start,
		int end) throws SystemException {
		return findByC_S(companyId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message-boards messages where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByC_S(long companyId, int status, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				companyId, status,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_S,
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

			query.append(_SQL_SELECT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(status);

				list = (List<MBMessage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_C_S,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_S,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first message-boards message in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByC_S_First(long companyId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		List<MBMessage> list = findByC_S(companyId, status, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last message-boards message in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByC_S_Last(long companyId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		int count = countByC_S(companyId, status);

		List<MBMessage> list = findByC_S(companyId, status, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage[] findByC_S_PrevAndNext(long messageId, long companyId,
		int status, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		Session session = null;

		try {
			session = openSession();

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = getByC_S_PrevAndNext(session, mbMessage, companyId,
					status, orderByComparator, true);

			array[1] = mbMessage;

			array[2] = getByC_S_PrevAndNext(session, mbMessage, companyId,
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

	protected MBMessage getByC_S_PrevAndNext(Session session,
		MBMessage mbMessage, long companyId, int status,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBMESSAGE_WHERE);

		query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_S_STATUS_2);

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
			query.append(MBMessageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(mbMessage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMessage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message-boards messages where userId = &#63; and classNameId = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @return the matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByU_C(long userId, long classNameId)
		throws SystemException {
		return findByU_C(userId, classNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message-boards messages where userId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByU_C(long userId, long classNameId, int start,
		int end) throws SystemException {
		return findByU_C(userId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message-boards messages where userId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByU_C(long userId, long classNameId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				userId, classNameId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_U_C,
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

			query.append(_SQL_SELECT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_U_C_USERID_2);

			query.append(_FINDER_COLUMN_U_C_CLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(classNameId);

				list = (List<MBMessage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_U_C,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_U_C,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first message-boards message in the ordered set where userId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByU_C_First(long userId, long classNameId,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		List<MBMessage> list = findByU_C(userId, classNameId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last message-boards message in the ordered set where userId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByU_C_Last(long userId, long classNameId,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		int count = countByU_C(userId, classNameId);

		List<MBMessage> list = findByU_C(userId, classNameId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where userId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage[] findByU_C_PrevAndNext(long messageId, long userId,
		long classNameId, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		Session session = null;

		try {
			session = openSession();

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = getByU_C_PrevAndNext(session, mbMessage, userId,
					classNameId, orderByComparator, true);

			array[1] = mbMessage;

			array[2] = getByU_C_PrevAndNext(session, mbMessage, userId,
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

	protected MBMessage getByU_C_PrevAndNext(Session session,
		MBMessage mbMessage, long userId, long classNameId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBMESSAGE_WHERE);

		query.append(_FINDER_COLUMN_U_C_USERID_2);

		query.append(_FINDER_COLUMN_U_C_CLASSNAMEID_2);

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
			query.append(MBMessageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		qPos.add(classNameId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(mbMessage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMessage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message-boards messages where userId = &#63; and classNameId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameIds the class name IDs
	 * @return the matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByU_C(long userId, long[] classNameIds)
		throws SystemException {
		return findByU_C(userId, classNameIds, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message-boards messages where userId = &#63; and classNameId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameIds the class name IDs
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByU_C(long userId, long[] classNameIds,
		int start, int end) throws SystemException {
		return findByU_C(userId, classNameIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message-boards messages where userId = &#63; and classNameId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameIds the class name IDs
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByU_C(long userId, long[] classNameIds,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				userId, StringUtil.merge(classNameIds),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_U_C,
				finderArgs, this);

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_MBMESSAGE_WHERE);

			boolean conjunctionable = false;

			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_U_C_USERID_5);

			conjunctionable = true;

			if ((classNameIds == null) || (classNameIds.length > 0)) {
				if (conjunctionable) {
					query.append(WHERE_AND);
				}

				query.append(StringPool.OPEN_PARENTHESIS);

				for (int i = 0; i < classNameIds.length; i++) {
					query.append(_FINDER_COLUMN_U_C_CLASSNAMEID_5);

					if ((i + 1) < classNameIds.length) {
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
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (classNameIds != null) {
					qPos.add(classNameIds);
				}

				list = (List<MBMessage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_U_C,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_U_C,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns all the message-boards messages where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByC_C(long classNameId, long classPK)
		throws SystemException {
		return findByC_C(classNameId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message-boards messages where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByC_C(long classNameId, long classPK, int start,
		int end) throws SystemException {
		return findByC_C(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message-boards messages where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByC_C(long classNameId, long classPK, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				classNameId, classPK,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_C,
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

			query.append(_SQL_SELECT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				list = (List<MBMessage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_C_C,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_C,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first message-boards message in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByC_C_First(long classNameId, long classPK,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		List<MBMessage> list = findByC_C(classNameId, classPK, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last message-boards message in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByC_C_Last(long classNameId, long classPK,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		int count = countByC_C(classNameId, classPK);

		List<MBMessage> list = findByC_C(classNameId, classPK, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage[] findByC_C_PrevAndNext(long messageId, long classNameId,
		long classPK, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		Session session = null;

		try {
			session = openSession();

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = getByC_C_PrevAndNext(session, mbMessage, classNameId,
					classPK, orderByComparator, true);

			array[1] = mbMessage;

			array[2] = getByC_C_PrevAndNext(session, mbMessage, classNameId,
					classPK, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBMessage getByC_C_PrevAndNext(Session session,
		MBMessage mbMessage, long classNameId, long classPK,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBMESSAGE_WHERE);

		query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

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
			query.append(MBMessageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(mbMessage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMessage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message-boards messages where threadId = &#63; and parentMessageId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param parentMessageId the parent message ID
	 * @return the matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByT_P(long threadId, long parentMessageId)
		throws SystemException {
		return findByT_P(threadId, parentMessageId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message-boards messages where threadId = &#63; and parentMessageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param parentMessageId the parent message ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByT_P(long threadId, long parentMessageId,
		int start, int end) throws SystemException {
		return findByT_P(threadId, parentMessageId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message-boards messages where threadId = &#63; and parentMessageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param parentMessageId the parent message ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByT_P(long threadId, long parentMessageId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				threadId, parentMessageId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_T_P,
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

			query.append(_SQL_SELECT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_T_P_THREADID_2);

			query.append(_FINDER_COLUMN_T_P_PARENTMESSAGEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(threadId);

				qPos.add(parentMessageId);

				list = (List<MBMessage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_T_P,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_T_P,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first message-boards message in the ordered set where threadId = &#63; and parentMessageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param parentMessageId the parent message ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByT_P_First(long threadId, long parentMessageId,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		List<MBMessage> list = findByT_P(threadId, parentMessageId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("threadId=");
			msg.append(threadId);

			msg.append(", parentMessageId=");
			msg.append(parentMessageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last message-boards message in the ordered set where threadId = &#63; and parentMessageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param parentMessageId the parent message ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByT_P_Last(long threadId, long parentMessageId,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		int count = countByT_P(threadId, parentMessageId);

		List<MBMessage> list = findByT_P(threadId, parentMessageId, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("threadId=");
			msg.append(threadId);

			msg.append(", parentMessageId=");
			msg.append(parentMessageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where threadId = &#63; and parentMessageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param threadId the thread ID
	 * @param parentMessageId the parent message ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage[] findByT_P_PrevAndNext(long messageId, long threadId,
		long parentMessageId, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		Session session = null;

		try {
			session = openSession();

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = getByT_P_PrevAndNext(session, mbMessage, threadId,
					parentMessageId, orderByComparator, true);

			array[1] = mbMessage;

			array[2] = getByT_P_PrevAndNext(session, mbMessage, threadId,
					parentMessageId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBMessage getByT_P_PrevAndNext(Session session,
		MBMessage mbMessage, long threadId, long parentMessageId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBMESSAGE_WHERE);

		query.append(_FINDER_COLUMN_T_P_THREADID_2);

		query.append(_FINDER_COLUMN_T_P_PARENTMESSAGEID_2);

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
			query.append(MBMessageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(threadId);

		qPos.add(parentMessageId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(mbMessage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMessage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message-boards messages where threadId = &#63; and answer = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @return the matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByT_A(long threadId, boolean answer)
		throws SystemException {
		return findByT_A(threadId, answer, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message-boards messages where threadId = &#63; and answer = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByT_A(long threadId, boolean answer, int start,
		int end) throws SystemException {
		return findByT_A(threadId, answer, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message-boards messages where threadId = &#63; and answer = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByT_A(long threadId, boolean answer, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				threadId, answer,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_T_A,
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

			query.append(_SQL_SELECT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_T_A_THREADID_2);

			query.append(_FINDER_COLUMN_T_A_ANSWER_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(threadId);

				qPos.add(answer);

				list = (List<MBMessage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_T_A,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_T_A,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first message-boards message in the ordered set where threadId = &#63; and answer = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByT_A_First(long threadId, boolean answer,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		List<MBMessage> list = findByT_A(threadId, answer, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("threadId=");
			msg.append(threadId);

			msg.append(", answer=");
			msg.append(answer);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last message-boards message in the ordered set where threadId = &#63; and answer = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByT_A_Last(long threadId, boolean answer,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		int count = countByT_A(threadId, answer);

		List<MBMessage> list = findByT_A(threadId, answer, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("threadId=");
			msg.append(threadId);

			msg.append(", answer=");
			msg.append(answer);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where threadId = &#63; and answer = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage[] findByT_A_PrevAndNext(long messageId, long threadId,
		boolean answer, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		Session session = null;

		try {
			session = openSession();

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = getByT_A_PrevAndNext(session, mbMessage, threadId,
					answer, orderByComparator, true);

			array[1] = mbMessage;

			array[2] = getByT_A_PrevAndNext(session, mbMessage, threadId,
					answer, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBMessage getByT_A_PrevAndNext(Session session,
		MBMessage mbMessage, long threadId, boolean answer,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBMESSAGE_WHERE);

		query.append(_FINDER_COLUMN_T_A_THREADID_2);

		query.append(_FINDER_COLUMN_T_A_ANSWER_2);

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
			query.append(MBMessageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(threadId);

		qPos.add(answer);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(mbMessage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMessage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message-boards messages where threadId = &#63; and status = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @return the matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByT_S(long threadId, int status)
		throws SystemException {
		return findByT_S(threadId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message-boards messages where threadId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByT_S(long threadId, int status, int start,
		int end) throws SystemException {
		return findByT_S(threadId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message-boards messages where threadId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByT_S(long threadId, int status, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				threadId, status,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_T_S,
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

			query.append(_SQL_SELECT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_T_S_THREADID_2);

			query.append(_FINDER_COLUMN_T_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(threadId);

				qPos.add(status);

				list = (List<MBMessage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_T_S,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_T_S,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first message-boards message in the ordered set where threadId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByT_S_First(long threadId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		List<MBMessage> list = findByT_S(threadId, status, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("threadId=");
			msg.append(threadId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last message-boards message in the ordered set where threadId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByT_S_Last(long threadId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		int count = countByT_S(threadId, status);

		List<MBMessage> list = findByT_S(threadId, status, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("threadId=");
			msg.append(threadId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where threadId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param threadId the thread ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage[] findByT_S_PrevAndNext(long messageId, long threadId,
		int status, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		Session session = null;

		try {
			session = openSession();

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = getByT_S_PrevAndNext(session, mbMessage, threadId,
					status, orderByComparator, true);

			array[1] = mbMessage;

			array[2] = getByT_S_PrevAndNext(session, mbMessage, threadId,
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

	protected MBMessage getByT_S_PrevAndNext(Session session,
		MBMessage mbMessage, long threadId, int status,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBMESSAGE_WHERE);

		query.append(_FINDER_COLUMN_T_S_THREADID_2);

		query.append(_FINDER_COLUMN_T_S_STATUS_2);

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
			query.append(MBMessageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(threadId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(mbMessage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMessage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message-boards messages where threadId = &#63; and status = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @return the matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByTR_S(long threadId, int status)
		throws SystemException {
		return findByTR_S(threadId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message-boards messages where threadId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByTR_S(long threadId, int status, int start,
		int end) throws SystemException {
		return findByTR_S(threadId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message-boards messages where threadId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByTR_S(long threadId, int status, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				threadId, status,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_TR_S,
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

			query.append(_SQL_SELECT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_TR_S_THREADID_2);

			query.append(_FINDER_COLUMN_TR_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(threadId);

				qPos.add(status);

				list = (List<MBMessage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_TR_S,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_TR_S,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first message-boards message in the ordered set where threadId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByTR_S_First(long threadId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		List<MBMessage> list = findByTR_S(threadId, status, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("threadId=");
			msg.append(threadId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last message-boards message in the ordered set where threadId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByTR_S_Last(long threadId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		int count = countByTR_S(threadId, status);

		List<MBMessage> list = findByTR_S(threadId, status, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("threadId=");
			msg.append(threadId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where threadId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param threadId the thread ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage[] findByTR_S_PrevAndNext(long messageId, long threadId,
		int status, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		Session session = null;

		try {
			session = openSession();

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = getByTR_S_PrevAndNext(session, mbMessage, threadId,
					status, orderByComparator, true);

			array[1] = mbMessage;

			array[2] = getByTR_S_PrevAndNext(session, mbMessage, threadId,
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

	protected MBMessage getByTR_S_PrevAndNext(Session session,
		MBMessage mbMessage, long threadId, int status,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBMESSAGE_WHERE);

		query.append(_FINDER_COLUMN_TR_S_THREADID_2);

		query.append(_FINDER_COLUMN_TR_S_STATUS_2);

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
			query.append(MBMessageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(threadId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(mbMessage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMessage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message-boards messages where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @return the matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByG_U_S(long groupId, long userId, int status)
		throws SystemException {
		return findByG_U_S(groupId, userId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message-boards messages where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByG_U_S(long groupId, long userId, int status,
		int start, int end) throws SystemException {
		return findByG_U_S(groupId, userId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message-boards messages where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByG_U_S(long groupId, long userId, int status,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, userId, status,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_U_S,
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

			query.append(_SQL_SELECT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_G_U_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_S_USERID_2);

			query.append(_FINDER_COLUMN_G_U_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(status);

				list = (List<MBMessage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_U_S,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_U_S,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first message-boards message in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByG_U_S_First(long groupId, long userId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		List<MBMessage> list = findByG_U_S(groupId, userId, status, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByG_U_S_Last(long groupId, long userId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		int count = countByG_U_S(groupId, userId, status);

		List<MBMessage> list = findByG_U_S(groupId, userId, status, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage[] findByG_U_S_PrevAndNext(long messageId, long groupId,
		long userId, int status, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		Session session = null;

		try {
			session = openSession();

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = getByG_U_S_PrevAndNext(session, mbMessage, groupId,
					userId, status, orderByComparator, true);

			array[1] = mbMessage;

			array[2] = getByG_U_S_PrevAndNext(session, mbMessage, groupId,
					userId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBMessage getByG_U_S_PrevAndNext(Session session,
		MBMessage mbMessage, long groupId, long userId, int status,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBMESSAGE_WHERE);

		query.append(_FINDER_COLUMN_G_U_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_S_USERID_2);

		query.append(_FINDER_COLUMN_G_U_S_STATUS_2);

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
			query.append(MBMessageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(mbMessage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMessage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message-boards messages that the user has permission to view where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @return the matching message-boards messages that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> filterFindByG_U_S(long groupId, long userId,
		int status) throws SystemException {
		return filterFindByG_U_S(groupId, userId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message-boards messages that the user has permission to view where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> filterFindByG_U_S(long groupId, long userId,
		int status, int start, int end) throws SystemException {
		return filterFindByG_U_S(groupId, userId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message-boards messages that the user has permissions to view where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> filterFindByG_U_S(long groupId, long userId,
		int status, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_S(groupId, userId, status, start, end,
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
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_S_USERID_2);

		query.append(_FINDER_COLUMN_G_U_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBMessageModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBMessage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, MBMessageImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, MBMessageImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			qPos.add(status);

			return (List<MBMessage>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set of message-boards messages that the user has permission to view where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage[] filterFindByG_U_S_PrevAndNext(long messageId,
		long groupId, long userId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_S_PrevAndNext(messageId, groupId, userId, status,
				orderByComparator);
		}

		MBMessage mbMessage = findByPrimaryKey(messageId);

		Session session = null;

		try {
			session = openSession();

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = filterGetByG_U_S_PrevAndNext(session, mbMessage,
					groupId, userId, status, orderByComparator, true);

			array[1] = mbMessage;

			array[2] = filterGetByG_U_S_PrevAndNext(session, mbMessage,
					groupId, userId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBMessage filterGetByG_U_S_PrevAndNext(Session session,
		MBMessage mbMessage, long groupId, long userId, int status,
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
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_S_USERID_2);

		query.append(_FINDER_COLUMN_G_U_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBMessageModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBMessage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, MBMessageImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, MBMessageImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(mbMessage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMessage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @return the matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByG_C_T(long groupId, long categoryId,
		long threadId) throws SystemException {
		return findByG_C_T(groupId, categoryId, threadId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByG_C_T(long groupId, long categoryId,
		long threadId, int start, int end) throws SystemException {
		return findByG_C_T(groupId, categoryId, threadId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByG_C_T(long groupId, long categoryId,
		long threadId, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, categoryId, threadId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_C_T,
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

			query.append(_SQL_SELECT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_G_C_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_T_CATEGORYID_2);

			query.append(_FINDER_COLUMN_G_C_T_THREADID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

				qPos.add(threadId);

				list = (List<MBMessage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_C_T,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_C_T,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByG_C_T_First(long groupId, long categoryId,
		long threadId, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		List<MBMessage> list = findByG_C_T(groupId, categoryId, threadId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", categoryId=");
			msg.append(categoryId);

			msg.append(", threadId=");
			msg.append(threadId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByG_C_T_Last(long groupId, long categoryId,
		long threadId, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		int count = countByG_C_T(groupId, categoryId, threadId);

		List<MBMessage> list = findByG_C_T(groupId, categoryId, threadId,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", categoryId=");
			msg.append(categoryId);

			msg.append(", threadId=");
			msg.append(threadId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage[] findByG_C_T_PrevAndNext(long messageId, long groupId,
		long categoryId, long threadId, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		Session session = null;

		try {
			session = openSession();

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = getByG_C_T_PrevAndNext(session, mbMessage, groupId,
					categoryId, threadId, orderByComparator, true);

			array[1] = mbMessage;

			array[2] = getByG_C_T_PrevAndNext(session, mbMessage, groupId,
					categoryId, threadId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBMessage getByG_C_T_PrevAndNext(Session session,
		MBMessage mbMessage, long groupId, long categoryId, long threadId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBMESSAGE_WHERE);

		query.append(_FINDER_COLUMN_G_C_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_T_CATEGORYID_2);

		query.append(_FINDER_COLUMN_G_C_T_THREADID_2);

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
			query.append(MBMessageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(categoryId);

		qPos.add(threadId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(mbMessage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMessage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @return the matching message-boards messages that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> filterFindByG_C_T(long groupId, long categoryId,
		long threadId) throws SystemException {
		return filterFindByG_C_T(groupId, categoryId, threadId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> filterFindByG_C_T(long groupId, long categoryId,
		long threadId, int start, int end) throws SystemException {
		return filterFindByG_C_T(groupId, categoryId, threadId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message-boards messages that the user has permissions to view where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> filterFindByG_C_T(long groupId, long categoryId,
		long threadId, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_T(groupId, categoryId, threadId, start, end,
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
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_T_CATEGORYID_2);

		query.append(_FINDER_COLUMN_G_C_T_THREADID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBMessageModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBMessage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, MBMessageImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, MBMessageImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(categoryId);

			qPos.add(threadId);

			return (List<MBMessage>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set of message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage[] filterFindByG_C_T_PrevAndNext(long messageId,
		long groupId, long categoryId, long threadId,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_T_PrevAndNext(messageId, groupId, categoryId,
				threadId, orderByComparator);
		}

		MBMessage mbMessage = findByPrimaryKey(messageId);

		Session session = null;

		try {
			session = openSession();

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = filterGetByG_C_T_PrevAndNext(session, mbMessage,
					groupId, categoryId, threadId, orderByComparator, true);

			array[1] = mbMessage;

			array[2] = filterGetByG_C_T_PrevAndNext(session, mbMessage,
					groupId, categoryId, threadId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBMessage filterGetByG_C_T_PrevAndNext(Session session,
		MBMessage mbMessage, long groupId, long categoryId, long threadId,
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
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_T_CATEGORYID_2);

		query.append(_FINDER_COLUMN_G_C_T_THREADID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBMessageModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBMessage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, MBMessageImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, MBMessageImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(categoryId);

		qPos.add(threadId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(mbMessage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMessage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message-boards messages where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @return the matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByG_C_S(long groupId, long categoryId, int status)
		throws SystemException {
		return findByG_C_S(groupId, categoryId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message-boards messages where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByG_C_S(long groupId, long categoryId,
		int status, int start, int end) throws SystemException {
		return findByG_C_S(groupId, categoryId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message-boards messages where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByG_C_S(long groupId, long categoryId,
		int status, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, categoryId, status,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_C_S,
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

			query.append(_SQL_SELECT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_G_C_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_S_CATEGORYID_2);

			query.append(_FINDER_COLUMN_G_C_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

				qPos.add(status);

				list = (List<MBMessage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_C_S,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_C_S,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByG_C_S_First(long groupId, long categoryId,
		int status, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		List<MBMessage> list = findByG_C_S(groupId, categoryId, status, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", categoryId=");
			msg.append(categoryId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByG_C_S_Last(long groupId, long categoryId,
		int status, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		int count = countByG_C_S(groupId, categoryId, status);

		List<MBMessage> list = findByG_C_S(groupId, categoryId, status,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", categoryId=");
			msg.append(categoryId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage[] findByG_C_S_PrevAndNext(long messageId, long groupId,
		long categoryId, int status, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		Session session = null;

		try {
			session = openSession();

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = getByG_C_S_PrevAndNext(session, mbMessage, groupId,
					categoryId, status, orderByComparator, true);

			array[1] = mbMessage;

			array[2] = getByG_C_S_PrevAndNext(session, mbMessage, groupId,
					categoryId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBMessage getByG_C_S_PrevAndNext(Session session,
		MBMessage mbMessage, long groupId, long categoryId, int status,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBMESSAGE_WHERE);

		query.append(_FINDER_COLUMN_G_C_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_S_CATEGORYID_2);

		query.append(_FINDER_COLUMN_G_C_S_STATUS_2);

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
			query.append(MBMessageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(categoryId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(mbMessage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMessage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @return the matching message-boards messages that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> filterFindByG_C_S(long groupId, long categoryId,
		int status) throws SystemException {
		return filterFindByG_C_S(groupId, categoryId, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> filterFindByG_C_S(long groupId, long categoryId,
		int status, int start, int end) throws SystemException {
		return filterFindByG_C_S(groupId, categoryId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message-boards messages that the user has permissions to view where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> filterFindByG_C_S(long groupId, long categoryId,
		int status, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_S(groupId, categoryId, status, start, end,
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
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_S_CATEGORYID_2);

		query.append(_FINDER_COLUMN_G_C_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBMessageModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBMessage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, MBMessageImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, MBMessageImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(categoryId);

			qPos.add(status);

			return (List<MBMessage>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set of message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage[] filterFindByG_C_S_PrevAndNext(long messageId,
		long groupId, long categoryId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_S_PrevAndNext(messageId, groupId, categoryId,
				status, orderByComparator);
		}

		MBMessage mbMessage = findByPrimaryKey(messageId);

		Session session = null;

		try {
			session = openSession();

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = filterGetByG_C_S_PrevAndNext(session, mbMessage,
					groupId, categoryId, status, orderByComparator, true);

			array[1] = mbMessage;

			array[2] = filterGetByG_C_S_PrevAndNext(session, mbMessage,
					groupId, categoryId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBMessage filterGetByG_C_S_PrevAndNext(Session session,
		MBMessage mbMessage, long groupId, long categoryId, int status,
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
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_S_CATEGORYID_2);

		query.append(_FINDER_COLUMN_G_C_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBMessageModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBMessage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, MBMessageImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, MBMessageImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(categoryId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(mbMessage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMessage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message-boards messages where userId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByU_C_C(long userId, long classNameId,
		long classPK) throws SystemException {
		return findByU_C_C(userId, classNameId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message-boards messages where userId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByU_C_C(long userId, long classNameId,
		long classPK, int start, int end) throws SystemException {
		return findByU_C_C(userId, classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message-boards messages where userId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByU_C_C(long userId, long classNameId,
		long classPK, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				userId, classNameId, classPK,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_U_C_C,
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

			query.append(_SQL_SELECT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_U_C_C_USERID_2);

			query.append(_FINDER_COLUMN_U_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_U_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
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

				list = (List<MBMessage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_U_C_C,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_U_C_C,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByU_C_C_First(long userId, long classNameId,
		long classPK, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		List<MBMessage> list = findByU_C_C(userId, classNameId, classPK, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByU_C_C_Last(long userId, long classNameId,
		long classPK, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		int count = countByU_C_C(userId, classNameId, classPK);

		List<MBMessage> list = findByU_C_C(userId, classNameId, classPK,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage[] findByU_C_C_PrevAndNext(long messageId, long userId,
		long classNameId, long classPK, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		Session session = null;

		try {
			session = openSession();

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = getByU_C_C_PrevAndNext(session, mbMessage, userId,
					classNameId, classPK, orderByComparator, true);

			array[1] = mbMessage;

			array[2] = getByU_C_C_PrevAndNext(session, mbMessage, userId,
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

	protected MBMessage getByU_C_C_PrevAndNext(Session session,
		MBMessage mbMessage, long userId, long classNameId, long classPK,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBMESSAGE_WHERE);

		query.append(_FINDER_COLUMN_U_C_C_USERID_2);

		query.append(_FINDER_COLUMN_U_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_U_C_C_CLASSPK_2);

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
			query.append(MBMessageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(mbMessage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMessage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message-boards messages where userId = &#63; and classNameId = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param status the status
	 * @return the matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByU_C_S(long userId, long classNameId, int status)
		throws SystemException {
		return findByU_C_S(userId, classNameId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message-boards messages where userId = &#63; and classNameId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByU_C_S(long userId, long classNameId,
		int status, int start, int end) throws SystemException {
		return findByU_C_S(userId, classNameId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message-boards messages where userId = &#63; and classNameId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByU_C_S(long userId, long classNameId,
		int status, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				userId, classNameId, status,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_U_C_S,
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

			query.append(_SQL_SELECT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_U_C_S_USERID_2);

			query.append(_FINDER_COLUMN_U_C_S_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_U_C_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(classNameId);

				qPos.add(status);

				list = (List<MBMessage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_U_C_S,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_U_C_S,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByU_C_S_First(long userId, long classNameId,
		int status, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		List<MBMessage> list = findByU_C_S(userId, classNameId, status, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByU_C_S_Last(long userId, long classNameId,
		int status, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		int count = countByU_C_S(userId, classNameId, status);

		List<MBMessage> list = findByU_C_S(userId, classNameId, status,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage[] findByU_C_S_PrevAndNext(long messageId, long userId,
		long classNameId, int status, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		Session session = null;

		try {
			session = openSession();

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = getByU_C_S_PrevAndNext(session, mbMessage, userId,
					classNameId, status, orderByComparator, true);

			array[1] = mbMessage;

			array[2] = getByU_C_S_PrevAndNext(session, mbMessage, userId,
					classNameId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBMessage getByU_C_S_PrevAndNext(Session session,
		MBMessage mbMessage, long userId, long classNameId, int status,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBMESSAGE_WHERE);

		query.append(_FINDER_COLUMN_U_C_S_USERID_2);

		query.append(_FINDER_COLUMN_U_C_S_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_U_C_S_STATUS_2);

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
			query.append(MBMessageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		qPos.add(classNameId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(mbMessage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMessage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message-boards messages where userId = &#63; and classNameId = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameIds the class name IDs
	 * @param status the status
	 * @return the matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByU_C_S(long userId, long[] classNameIds,
		int status) throws SystemException {
		return findByU_C_S(userId, classNameIds, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message-boards messages where userId = &#63; and classNameId = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameIds the class name IDs
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByU_C_S(long userId, long[] classNameIds,
		int status, int start, int end) throws SystemException {
		return findByU_C_S(userId, classNameIds, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message-boards messages where userId = &#63; and classNameId = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameIds the class name IDs
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByU_C_S(long userId, long[] classNameIds,
		int status, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				userId, StringUtil.merge(classNameIds), status,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_U_C_S,
				finderArgs, this);

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_MBMESSAGE_WHERE);

			boolean conjunctionable = false;

			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_U_C_S_USERID_5);

			conjunctionable = true;

			if ((classNameIds == null) || (classNameIds.length > 0)) {
				if (conjunctionable) {
					query.append(WHERE_AND);
				}

				query.append(StringPool.OPEN_PARENTHESIS);

				for (int i = 0; i < classNameIds.length; i++) {
					query.append(_FINDER_COLUMN_U_C_S_CLASSNAMEID_5);

					if ((i + 1) < classNameIds.length) {
						query.append(WHERE_OR);
					}
				}

				query.append(StringPool.CLOSE_PARENTHESIS);

				conjunctionable = true;
			}

			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_U_C_S_STATUS_5);

			conjunctionable = true;

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (classNameIds != null) {
					qPos.add(classNameIds);
				}

				qPos.add(status);

				list = (List<MBMessage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_U_C_S,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_U_C_S,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns all the message-boards messages where classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param status the status
	 * @return the matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByC_C_S(long classNameId, long classPK,
		int status) throws SystemException {
		return findByC_C_S(classNameId, classPK, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message-boards messages where classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByC_C_S(long classNameId, long classPK,
		int status, int start, int end) throws SystemException {
		return findByC_C_S(classNameId, classPK, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message-boards messages where classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByC_C_S(long classNameId, long classPK,
		int status, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				classNameId, classPK, status,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_C_S,
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

			query.append(_SQL_SELECT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_C_C_S_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_S_CLASSPK_2);

			query.append(_FINDER_COLUMN_C_C_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(status);

				list = (List<MBMessage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_C_C_S,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_C_S,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first message-boards message in the ordered set where classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByC_C_S_First(long classNameId, long classPK,
		int status, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		List<MBMessage> list = findByC_C_S(classNameId, classPK, status, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last message-boards message in the ordered set where classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByC_C_S_Last(long classNameId, long classPK,
		int status, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		int count = countByC_C_S(classNameId, classPK, status);

		List<MBMessage> list = findByC_C_S(classNameId, classPK, status,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage[] findByC_C_S_PrevAndNext(long messageId,
		long classNameId, long classPK, int status,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		Session session = null;

		try {
			session = openSession();

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = getByC_C_S_PrevAndNext(session, mbMessage, classNameId,
					classPK, status, orderByComparator, true);

			array[1] = mbMessage;

			array[2] = getByC_C_S_PrevAndNext(session, mbMessage, classNameId,
					classPK, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBMessage getByC_C_S_PrevAndNext(Session session,
		MBMessage mbMessage, long classNameId, long classPK, int status,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBMESSAGE_WHERE);

		query.append(_FINDER_COLUMN_C_C_S_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_S_CLASSPK_2);

		query.append(_FINDER_COLUMN_C_C_S_STATUS_2);

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
			query.append(MBMessageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		qPos.add(classPK);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(mbMessage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMessage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param status the status
	 * @return the matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByG_C_T_S(long groupId, long categoryId,
		long threadId, int status) throws SystemException {
		return findByG_C_T_S(groupId, categoryId, threadId, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByG_C_T_S(long groupId, long categoryId,
		long threadId, int status, int start, int end)
		throws SystemException {
		return findByG_C_T_S(groupId, categoryId, threadId, status, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByG_C_T_S(long groupId, long categoryId,
		long threadId, int status, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, categoryId, threadId, status,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_C_T_S,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(6 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_G_C_T_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_T_S_CATEGORYID_2);

			query.append(_FINDER_COLUMN_G_C_T_S_THREADID_2);

			query.append(_FINDER_COLUMN_G_C_T_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

				qPos.add(threadId);

				qPos.add(status);

				list = (List<MBMessage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_C_T_S,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_C_T_S,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByG_C_T_S_First(long groupId, long categoryId,
		long threadId, int status, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		List<MBMessage> list = findByG_C_T_S(groupId, categoryId, threadId,
				status, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", categoryId=");
			msg.append(categoryId);

			msg.append(", threadId=");
			msg.append(threadId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByG_C_T_S_Last(long groupId, long categoryId,
		long threadId, int status, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		int count = countByG_C_T_S(groupId, categoryId, threadId, status);

		List<MBMessage> list = findByG_C_T_S(groupId, categoryId, threadId,
				status, count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", categoryId=");
			msg.append(categoryId);

			msg.append(", threadId=");
			msg.append(threadId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage[] findByG_C_T_S_PrevAndNext(long messageId, long groupId,
		long categoryId, long threadId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		Session session = null;

		try {
			session = openSession();

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = getByG_C_T_S_PrevAndNext(session, mbMessage, groupId,
					categoryId, threadId, status, orderByComparator, true);

			array[1] = mbMessage;

			array[2] = getByG_C_T_S_PrevAndNext(session, mbMessage, groupId,
					categoryId, threadId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBMessage getByG_C_T_S_PrevAndNext(Session session,
		MBMessage mbMessage, long groupId, long categoryId, long threadId,
		int status, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBMESSAGE_WHERE);

		query.append(_FINDER_COLUMN_G_C_T_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_T_S_CATEGORYID_2);

		query.append(_FINDER_COLUMN_G_C_T_S_THREADID_2);

		query.append(_FINDER_COLUMN_G_C_T_S_STATUS_2);

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
			query.append(MBMessageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(categoryId);

		qPos.add(threadId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(mbMessage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMessage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param status the status
	 * @return the matching message-boards messages that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> filterFindByG_C_T_S(long groupId, long categoryId,
		long threadId, int status) throws SystemException {
		return filterFindByG_C_T_S(groupId, categoryId, threadId, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> filterFindByG_C_T_S(long groupId, long categoryId,
		long threadId, int status, int start, int end)
		throws SystemException {
		return filterFindByG_C_T_S(groupId, categoryId, threadId, status,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the message-boards messages that the user has permissions to view where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> filterFindByG_C_T_S(long groupId, long categoryId,
		long threadId, int status, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_T_S(groupId, categoryId, threadId, status, start,
				end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_T_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_T_S_CATEGORYID_2);

		query.append(_FINDER_COLUMN_G_C_T_S_THREADID_2);

		query.append(_FINDER_COLUMN_G_C_T_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBMessageModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBMessage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, MBMessageImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, MBMessageImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(categoryId);

			qPos.add(threadId);

			qPos.add(status);

			return (List<MBMessage>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set of message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage[] filterFindByG_C_T_S_PrevAndNext(long messageId,
		long groupId, long categoryId, long threadId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_T_S_PrevAndNext(messageId, groupId, categoryId,
				threadId, status, orderByComparator);
		}

		MBMessage mbMessage = findByPrimaryKey(messageId);

		Session session = null;

		try {
			session = openSession();

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = filterGetByG_C_T_S_PrevAndNext(session, mbMessage,
					groupId, categoryId, threadId, status, orderByComparator,
					true);

			array[1] = mbMessage;

			array[2] = filterGetByG_C_T_S_PrevAndNext(session, mbMessage,
					groupId, categoryId, threadId, status, orderByComparator,
					false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBMessage filterGetByG_C_T_S_PrevAndNext(Session session,
		MBMessage mbMessage, long groupId, long categoryId, long threadId,
		int status, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_T_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_T_S_CATEGORYID_2);

		query.append(_FINDER_COLUMN_G_C_T_S_THREADID_2);

		query.append(_FINDER_COLUMN_G_C_T_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBMessageModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBMessage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, MBMessageImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, MBMessageImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(categoryId);

		qPos.add(threadId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(mbMessage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMessage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message-boards messages where userId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param status the status
	 * @return the matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByU_C_C_S(long userId, long classNameId,
		long classPK, int status) throws SystemException {
		return findByU_C_C_S(userId, classNameId, classPK, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message-boards messages where userId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByU_C_C_S(long userId, long classNameId,
		long classPK, int status, int start, int end) throws SystemException {
		return findByU_C_C_S(userId, classNameId, classPK, status, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the message-boards messages where userId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByU_C_C_S(long userId, long classNameId,
		long classPK, int status, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				userId, classNameId, classPK, status,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_U_C_C_S,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(6 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_U_C_C_S_USERID_2);

			query.append(_FINDER_COLUMN_U_C_C_S_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_U_C_C_S_CLASSPK_2);

			query.append(_FINDER_COLUMN_U_C_C_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(MBMessageModelImpl.ORDER_BY_JPQL);
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

				qPos.add(status);

				list = (List<MBMessage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_U_C_C_S,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_U_C_C_S,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByU_C_C_S_First(long userId, long classNameId,
		long classPK, int status, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		List<MBMessage> list = findByU_C_C_S(userId, classNameId, classPK,
				status, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByU_C_C_S_Last(long userId, long classNameId,
		long classPK, int status, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		int count = countByU_C_C_S(userId, classNameId, classPK, status);

		List<MBMessage> list = findByU_C_C_S(userId, classNameId, classPK,
				status, count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage[] findByU_C_C_S_PrevAndNext(long messageId, long userId,
		long classNameId, long classPK, int status,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		Session session = null;

		try {
			session = openSession();

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = getByU_C_C_S_PrevAndNext(session, mbMessage, userId,
					classNameId, classPK, status, orderByComparator, true);

			array[1] = mbMessage;

			array[2] = getByU_C_C_S_PrevAndNext(session, mbMessage, userId,
					classNameId, classPK, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBMessage getByU_C_C_S_PrevAndNext(Session session,
		MBMessage mbMessage, long userId, long classNameId, long classPK,
		int status, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBMESSAGE_WHERE);

		query.append(_FINDER_COLUMN_U_C_C_S_USERID_2);

		query.append(_FINDER_COLUMN_U_C_C_S_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_U_C_C_S_CLASSPK_2);

		query.append(_FINDER_COLUMN_U_C_C_S_STATUS_2);

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
			query.append(MBMessageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		qPos.add(classNameId);

		qPos.add(classPK);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(mbMessage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMessage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message-boards messages.
	 *
	 * @return the message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message-boards messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the message-boards messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_MBMESSAGE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_MBMESSAGE.concat(MBMessageModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<MBMessage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<MBMessage>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the message-boards messages where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (MBMessage mbMessage : findByUuid(uuid)) {
			mbMessagePersistence.remove(mbMessage);
		}
	}

	/**
	 * Removes the message-boards message where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByUUID_G(uuid, groupId);

		mbMessagePersistence.remove(mbMessage);
	}

	/**
	 * Removes all the message-boards messages where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (MBMessage mbMessage : findByGroupId(groupId)) {
			mbMessagePersistence.remove(mbMessage);
		}
	}

	/**
	 * Removes all the message-boards messages where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCompanyId(long companyId) throws SystemException {
		for (MBMessage mbMessage : findByCompanyId(companyId)) {
			mbMessagePersistence.remove(mbMessage);
		}
	}

	/**
	 * Removes all the message-boards messages where threadId = &#63; from the database.
	 *
	 * @param threadId the thread ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByThreadId(long threadId) throws SystemException {
		for (MBMessage mbMessage : findByThreadId(threadId)) {
			mbMessagePersistence.remove(mbMessage);
		}
	}

	/**
	 * Removes all the message-boards messages where threadId = &#63; from the database.
	 *
	 * @param threadId the thread ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByThreadReplies(long threadId) throws SystemException {
		for (MBMessage mbMessage : findByThreadReplies(threadId)) {
			mbMessagePersistence.remove(mbMessage);
		}
	}

	/**
	 * Removes all the message-boards messages where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUserId(long userId) throws SystemException {
		for (MBMessage mbMessage : findByUserId(userId)) {
			mbMessagePersistence.remove(mbMessage);
		}
	}

	/**
	 * Removes all the message-boards messages where groupId = &#63; and userId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_U(long groupId, long userId)
		throws SystemException {
		for (MBMessage mbMessage : findByG_U(groupId, userId)) {
			mbMessagePersistence.remove(mbMessage);
		}
	}

	/**
	 * Removes all the message-boards messages where groupId = &#63; and categoryId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_C(long groupId, long categoryId)
		throws SystemException {
		for (MBMessage mbMessage : findByG_C(groupId, categoryId)) {
			mbMessagePersistence.remove(mbMessage);
		}
	}

	/**
	 * Removes all the message-boards messages where groupId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_S(long groupId, int status) throws SystemException {
		for (MBMessage mbMessage : findByG_S(groupId, status)) {
			mbMessagePersistence.remove(mbMessage);
		}
	}

	/**
	 * Removes all the message-boards messages where companyId = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_S(long companyId, int status)
		throws SystemException {
		for (MBMessage mbMessage : findByC_S(companyId, status)) {
			mbMessagePersistence.remove(mbMessage);
		}
	}

	/**
	 * Removes all the message-boards messages where userId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByU_C(long userId, long classNameId)
		throws SystemException {
		for (MBMessage mbMessage : findByU_C(userId, classNameId)) {
			mbMessagePersistence.remove(mbMessage);
		}
	}

	/**
	 * Removes all the message-boards messages where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_C(long classNameId, long classPK)
		throws SystemException {
		for (MBMessage mbMessage : findByC_C(classNameId, classPK)) {
			mbMessagePersistence.remove(mbMessage);
		}
	}

	/**
	 * Removes all the message-boards messages where threadId = &#63; and parentMessageId = &#63; from the database.
	 *
	 * @param threadId the thread ID
	 * @param parentMessageId the parent message ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByT_P(long threadId, long parentMessageId)
		throws SystemException {
		for (MBMessage mbMessage : findByT_P(threadId, parentMessageId)) {
			mbMessagePersistence.remove(mbMessage);
		}
	}

	/**
	 * Removes all the message-boards messages where threadId = &#63; and answer = &#63; from the database.
	 *
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByT_A(long threadId, boolean answer)
		throws SystemException {
		for (MBMessage mbMessage : findByT_A(threadId, answer)) {
			mbMessagePersistence.remove(mbMessage);
		}
	}

	/**
	 * Removes all the message-boards messages where threadId = &#63; and status = &#63; from the database.
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByT_S(long threadId, int status)
		throws SystemException {
		for (MBMessage mbMessage : findByT_S(threadId, status)) {
			mbMessagePersistence.remove(mbMessage);
		}
	}

	/**
	 * Removes all the message-boards messages where threadId = &#63; and status = &#63; from the database.
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByTR_S(long threadId, int status)
		throws SystemException {
		for (MBMessage mbMessage : findByTR_S(threadId, status)) {
			mbMessagePersistence.remove(mbMessage);
		}
	}

	/**
	 * Removes all the message-boards messages where groupId = &#63; and userId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_U_S(long groupId, long userId, int status)
		throws SystemException {
		for (MBMessage mbMessage : findByG_U_S(groupId, userId, status)) {
			mbMessagePersistence.remove(mbMessage);
		}
	}

	/**
	 * Removes all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_C_T(long groupId, long categoryId, long threadId)
		throws SystemException {
		for (MBMessage mbMessage : findByG_C_T(groupId, categoryId, threadId)) {
			mbMessagePersistence.remove(mbMessage);
		}
	}

	/**
	 * Removes all the message-boards messages where groupId = &#63; and categoryId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_C_S(long groupId, long categoryId, int status)
		throws SystemException {
		for (MBMessage mbMessage : findByG_C_S(groupId, categoryId, status)) {
			mbMessagePersistence.remove(mbMessage);
		}
	}

	/**
	 * Removes all the message-boards messages where userId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByU_C_C(long userId, long classNameId, long classPK)
		throws SystemException {
		for (MBMessage mbMessage : findByU_C_C(userId, classNameId, classPK)) {
			mbMessagePersistence.remove(mbMessage);
		}
	}

	/**
	 * Removes all the message-boards messages where userId = &#63; and classNameId = &#63; and status = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param status the status
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByU_C_S(long userId, long classNameId, int status)
		throws SystemException {
		for (MBMessage mbMessage : findByU_C_S(userId, classNameId, status)) {
			mbMessagePersistence.remove(mbMessage);
		}
	}

	/**
	 * Removes all the message-boards messages where classNameId = &#63; and classPK = &#63; and status = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param status the status
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_C_S(long classNameId, long classPK, int status)
		throws SystemException {
		for (MBMessage mbMessage : findByC_C_S(classNameId, classPK, status)) {
			mbMessagePersistence.remove(mbMessage);
		}
	}

	/**
	 * Removes all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param status the status
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_C_T_S(long groupId, long categoryId, long threadId,
		int status) throws SystemException {
		for (MBMessage mbMessage : findByG_C_T_S(groupId, categoryId, threadId,
				status)) {
			mbMessagePersistence.remove(mbMessage);
		}
	}

	/**
	 * Removes all the message-boards messages where userId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param status the status
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByU_C_C_S(long userId, long classNameId, long classPK,
		int status) throws SystemException {
		for (MBMessage mbMessage : findByU_C_C_S(userId, classNameId, classPK,
				status)) {
			mbMessagePersistence.remove(mbMessage);
		}
	}

	/**
	 * Removes all the message-boards messages from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (MBMessage mbMessage : findAll()) {
			mbMessagePersistence.remove(mbMessage);
		}
	}

	/**
	 * Returns the number of message-boards messages where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MBMESSAGE_WHERE);

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
	 * Returns the number of message-boards messages where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MBMESSAGE_WHERE);

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
	 * Returns the number of message-boards messages where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MBMESSAGE_WHERE);

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
	 * Returns the number of message-boards messages that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching message-boards messages that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByGroupId(long groupId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_MBMESSAGE_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBMessage.class.getName(),
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
	 * Returns the number of message-boards messages where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MBMESSAGE_WHERE);

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
	 * Returns the number of message-boards messages where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @return the number of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public int countByThreadId(long threadId) throws SystemException {
		Object[] finderArgs = new Object[] { threadId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_THREADID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_THREADID_THREADID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(threadId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_THREADID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of message-boards messages where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @return the number of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public int countByThreadReplies(long threadId) throws SystemException {
		Object[] finderArgs = new Object[] { threadId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_THREADREPLIES,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_THREADREPLIES_THREADID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(threadId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_THREADREPLIES,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of message-boards messages where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MBMESSAGE_WHERE);

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
	 * Returns the number of message-boards messages where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_U(long groupId, long userId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_U,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MBMESSAGE_WHERE);

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
	 * Returns the number of message-boards messages that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching message-boards messages that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_U(long groupId, long userId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_U(groupId, userId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_MBMESSAGE_WHERE);

		query.append(_FINDER_COLUMN_G_U_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_USERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBMessage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

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
	 * Returns the number of message-boards messages where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @return the number of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_C(long groupId, long categoryId)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, categoryId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_G_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_CATEGORYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

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
	 * Returns the number of message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @return the number of matching message-boards messages that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_C(long groupId, long categoryId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C(groupId, categoryId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_MBMESSAGE_WHERE);

		query.append(_FINDER_COLUMN_G_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_CATEGORYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBMessage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(categoryId);

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
	 * Returns the number of message-boards messages where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_S(long groupId, int status) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, status };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_G_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_S, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of message-boards messages that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching message-boards messages that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_S(long groupId, int status)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_S(groupId, status);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_MBMESSAGE_WHERE);

		query.append(_FINDER_COLUMN_G_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBMessage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(status);

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
	 * Returns the number of message-boards messages where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_S(long companyId, int status) throws SystemException {
		Object[] finderArgs = new Object[] { companyId, status };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_S, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of message-boards messages where userId = &#63; and classNameId = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @return the number of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public int countByU_C(long userId, long classNameId)
		throws SystemException {
		Object[] finderArgs = new Object[] { userId, classNameId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_U_C_USERID_2);

			query.append(_FINDER_COLUMN_U_C_CLASSNAMEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_C, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of message-boards messages where userId = &#63; and classNameId = any &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameIds the class name IDs
	 * @return the number of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public int countByU_C(long userId, long[] classNameIds)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				userId, StringUtil.merge(classNameIds)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_MBMESSAGE_WHERE);

			boolean conjunctionable = false;

			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_U_C_USERID_5);

			conjunctionable = true;

			if ((classNameIds == null) || (classNameIds.length > 0)) {
				if (conjunctionable) {
					query.append(WHERE_AND);
				}

				query.append(StringPool.OPEN_PARENTHESIS);

				for (int i = 0; i < classNameIds.length; i++) {
					query.append(_FINDER_COLUMN_U_C_CLASSNAMEID_5);

					if ((i + 1) < classNameIds.length) {
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

				qPos.add(userId);

				if (classNameIds != null) {
					qPos.add(classNameIds);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_C, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of message-boards messages where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the number of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_C(long classNameId, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { classNameId, classPK };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of message-boards messages where threadId = &#63; and parentMessageId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param parentMessageId the parent message ID
	 * @return the number of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public int countByT_P(long threadId, long parentMessageId)
		throws SystemException {
		Object[] finderArgs = new Object[] { threadId, parentMessageId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_T_P,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_T_P_THREADID_2);

			query.append(_FINDER_COLUMN_T_P_PARENTMESSAGEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(threadId);

				qPos.add(parentMessageId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_T_P, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of message-boards messages where threadId = &#63; and answer = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @return the number of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public int countByT_A(long threadId, boolean answer)
		throws SystemException {
		Object[] finderArgs = new Object[] { threadId, answer };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_T_A,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_T_A_THREADID_2);

			query.append(_FINDER_COLUMN_T_A_ANSWER_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(threadId);

				qPos.add(answer);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_T_A, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of message-boards messages where threadId = &#63; and status = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @return the number of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public int countByT_S(long threadId, int status) throws SystemException {
		Object[] finderArgs = new Object[] { threadId, status };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_T_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_T_S_THREADID_2);

			query.append(_FINDER_COLUMN_T_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(threadId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_T_S, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of message-boards messages where threadId = &#63; and status = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @return the number of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public int countByTR_S(long threadId, int status) throws SystemException {
		Object[] finderArgs = new Object[] { threadId, status };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_TR_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_TR_S_THREADID_2);

			query.append(_FINDER_COLUMN_TR_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(threadId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_TR_S,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of message-boards messages where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @return the number of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_U_S(long groupId, long userId, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, userId, status };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_U_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_G_U_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_S_USERID_2);

			query.append(_FINDER_COLUMN_G_U_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_U_S,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of message-boards messages that the user has permission to view where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @return the number of matching message-boards messages that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_U_S(long groupId, long userId, int status)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_U_S(groupId, userId, status);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_MBMESSAGE_WHERE);

		query.append(_FINDER_COLUMN_G_U_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_S_USERID_2);

		query.append(_FINDER_COLUMN_G_U_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBMessage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			qPos.add(status);

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
	 * Returns the number of message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @return the number of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_C_T(long groupId, long categoryId, long threadId)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, categoryId, threadId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_C_T,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_G_C_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_T_CATEGORYID_2);

			query.append(_FINDER_COLUMN_G_C_T_THREADID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

				qPos.add(threadId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_C_T,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @return the number of matching message-boards messages that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_C_T(long groupId, long categoryId, long threadId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_T(groupId, categoryId, threadId);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_MBMESSAGE_WHERE);

		query.append(_FINDER_COLUMN_G_C_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_T_CATEGORYID_2);

		query.append(_FINDER_COLUMN_G_C_T_THREADID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBMessage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(categoryId);

			qPos.add(threadId);

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
	 * Returns the number of message-boards messages where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @return the number of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_C_S(long groupId, long categoryId, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, categoryId, status };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_C_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_G_C_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_S_CATEGORYID_2);

			query.append(_FINDER_COLUMN_G_C_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_C_S,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @return the number of matching message-boards messages that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_C_S(long groupId, long categoryId, int status)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_S(groupId, categoryId, status);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_MBMESSAGE_WHERE);

		query.append(_FINDER_COLUMN_G_C_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_S_CATEGORYID_2);

		query.append(_FINDER_COLUMN_G_C_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBMessage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(categoryId);

			qPos.add(status);

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
	 * Returns the number of message-boards messages where userId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the number of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public int countByU_C_C(long userId, long classNameId, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { userId, classNameId, classPK };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_C_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_U_C_C_USERID_2);

			query.append(_FINDER_COLUMN_U_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_U_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_C_C,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of message-boards messages where userId = &#63; and classNameId = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param status the status
	 * @return the number of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public int countByU_C_S(long userId, long classNameId, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] { userId, classNameId, status };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_C_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_U_C_S_USERID_2);

			query.append(_FINDER_COLUMN_U_C_S_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_U_C_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(classNameId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_C_S,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of message-boards messages where userId = &#63; and classNameId = any &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameIds the class name IDs
	 * @param status the status
	 * @return the number of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public int countByU_C_S(long userId, long[] classNameIds, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				userId, StringUtil.merge(classNameIds), status
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_C_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_MBMESSAGE_WHERE);

			boolean conjunctionable = false;

			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_U_C_S_USERID_5);

			conjunctionable = true;

			if ((classNameIds == null) || (classNameIds.length > 0)) {
				if (conjunctionable) {
					query.append(WHERE_AND);
				}

				query.append(StringPool.OPEN_PARENTHESIS);

				for (int i = 0; i < classNameIds.length; i++) {
					query.append(_FINDER_COLUMN_U_C_S_CLASSNAMEID_5);

					if ((i + 1) < classNameIds.length) {
						query.append(WHERE_OR);
					}
				}

				query.append(StringPool.CLOSE_PARENTHESIS);

				conjunctionable = true;
			}

			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_U_C_S_STATUS_5);

			conjunctionable = true;

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (classNameIds != null) {
					qPos.add(classNameIds);
				}

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_C_S,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of message-boards messages where classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param status the status
	 * @return the number of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_C_S(long classNameId, long classPK, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] { classNameId, classPK, status };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_C_C_S_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_S_CLASSPK_2);

			query.append(_FINDER_COLUMN_C_C_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C_S,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param status the status
	 * @return the number of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_C_T_S(long groupId, long categoryId, long threadId,
		int status) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, categoryId, threadId, status };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_C_T_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_G_C_T_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_T_S_CATEGORYID_2);

			query.append(_FINDER_COLUMN_G_C_T_S_THREADID_2);

			query.append(_FINDER_COLUMN_G_C_T_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

				qPos.add(threadId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_C_T_S,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param status the status
	 * @return the number of matching message-boards messages that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_C_T_S(long groupId, long categoryId,
		long threadId, int status) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_T_S(groupId, categoryId, threadId, status);
		}

		StringBundler query = new StringBundler(5);

		query.append(_FILTER_SQL_COUNT_MBMESSAGE_WHERE);

		query.append(_FINDER_COLUMN_G_C_T_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_T_S_CATEGORYID_2);

		query.append(_FINDER_COLUMN_G_C_T_S_THREADID_2);

		query.append(_FINDER_COLUMN_G_C_T_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBMessage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(categoryId);

			qPos.add(threadId);

			qPos.add(status);

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
	 * Returns the number of message-boards messages where userId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param status the status
	 * @return the number of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public int countByU_C_C_S(long userId, long classNameId, long classPK,
		int status) throws SystemException {
		Object[] finderArgs = new Object[] { userId, classNameId, classPK, status };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_C_C_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_U_C_C_S_USERID_2);

			query.append(_FINDER_COLUMN_U_C_C_S_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_U_C_C_S_CLASSPK_2);

			query.append(_FINDER_COLUMN_U_C_C_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(classNameId);

				qPos.add(classPK);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_C_C_S,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of message-boards messages.
	 *
	 * @return the number of message-boards messages
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

				Query q = session.createQuery(_SQL_COUNT_MBMESSAGE);

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
	 * Initializes the message-boards message persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.messageboards.model.MBMessage")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<MBMessage>> listenersList = new ArrayList<ModelListener<MBMessage>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<MBMessage>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(MBMessageImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
	}

	@BeanReference(type = MBBanPersistence.class)
	protected MBBanPersistence mbBanPersistence;
	@BeanReference(type = MBCategoryPersistence.class)
	protected MBCategoryPersistence mbCategoryPersistence;
	@BeanReference(type = MBDiscussionPersistence.class)
	protected MBDiscussionPersistence mbDiscussionPersistence;
	@BeanReference(type = MBMailingListPersistence.class)
	protected MBMailingListPersistence mbMailingListPersistence;
	@BeanReference(type = MBMessagePersistence.class)
	protected MBMessagePersistence mbMessagePersistence;
	@BeanReference(type = MBStatsUserPersistence.class)
	protected MBStatsUserPersistence mbStatsUserPersistence;
	@BeanReference(type = MBThreadPersistence.class)
	protected MBThreadPersistence mbThreadPersistence;
	@BeanReference(type = MBThreadFlagPersistence.class)
	protected MBThreadFlagPersistence mbThreadFlagPersistence;
	@BeanReference(type = CompanyPersistence.class)
	protected CompanyPersistence companyPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = LockPersistence.class)
	protected LockPersistence lockPersistence;
	@BeanReference(type = PortletPreferencesPersistence.class)
	protected PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = SubscriptionPersistence.class)
	protected SubscriptionPersistence subscriptionPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = WorkflowInstanceLinkPersistence.class)
	protected WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	@BeanReference(type = AssetEntryPersistence.class)
	protected AssetEntryPersistence assetEntryPersistence;
	@BeanReference(type = AssetLinkPersistence.class)
	protected AssetLinkPersistence assetLinkPersistence;
	@BeanReference(type = AssetTagPersistence.class)
	protected AssetTagPersistence assetTagPersistence;
	@BeanReference(type = BlogsEntryPersistence.class)
	protected BlogsEntryPersistence blogsEntryPersistence;
	@BeanReference(type = ExpandoValuePersistence.class)
	protected ExpandoValuePersistence expandoValuePersistence;
	@BeanReference(type = RatingsStatsPersistence.class)
	protected RatingsStatsPersistence ratingsStatsPersistence;
	@BeanReference(type = SocialActivityPersistence.class)
	protected SocialActivityPersistence socialActivityPersistence;
	@BeanReference(type = SocialEquityLogPersistence.class)
	protected SocialEquityLogPersistence socialEquityLogPersistence;
	@BeanReference(type = WikiPagePersistence.class)
	protected WikiPagePersistence wikiPagePersistence;
	private static final String _SQL_SELECT_MBMESSAGE = "SELECT mbMessage FROM MBMessage mbMessage";
	private static final String _SQL_SELECT_MBMESSAGE_WHERE = "SELECT mbMessage FROM MBMessage mbMessage WHERE ";
	private static final String _SQL_COUNT_MBMESSAGE = "SELECT COUNT(mbMessage) FROM MBMessage mbMessage";
	private static final String _SQL_COUNT_MBMESSAGE_WHERE = "SELECT COUNT(mbMessage) FROM MBMessage mbMessage WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "mbMessage.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "mbMessage.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(mbMessage.uuid IS NULL OR mbMessage.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "mbMessage.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "mbMessage.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(mbMessage.uuid IS NULL OR mbMessage.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "mbMessage.groupId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "mbMessage.groupId = ?";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "mbMessage.companyId = ?";
	private static final String _FINDER_COLUMN_THREADID_THREADID_2 = "mbMessage.threadId = ?";
	private static final String _FINDER_COLUMN_THREADREPLIES_THREADID_2 = "mbMessage.threadId = ? AND mbMessage.parentMessageId != 0";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "mbMessage.userId = ?";
	private static final String _FINDER_COLUMN_G_U_GROUPID_2 = "mbMessage.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_USERID_2 = "mbMessage.userId = ? AND mbMessage.categoryId != -1";
	private static final String _FINDER_COLUMN_G_C_GROUPID_2 = "mbMessage.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_CATEGORYID_2 = "mbMessage.categoryId = ?";
	private static final String _FINDER_COLUMN_G_S_GROUPID_2 = "mbMessage.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_S_STATUS_2 = "mbMessage.status = ? AND mbMessage.categoryId != -1";
	private static final String _FINDER_COLUMN_C_S_COMPANYID_2 = "mbMessage.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_S_STATUS_2 = "mbMessage.status = ?";
	private static final String _FINDER_COLUMN_U_C_USERID_2 = "mbMessage.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_C_USERID_5 = "(" +
		_removeConjunction(_FINDER_COLUMN_U_C_USERID_2) + ")";
	private static final String _FINDER_COLUMN_U_C_CLASSNAMEID_2 = "mbMessage.classNameId = ?";
	private static final String _FINDER_COLUMN_U_C_CLASSNAMEID_5 = "(" +
		_removeConjunction(_FINDER_COLUMN_U_C_CLASSNAMEID_2) + ")";
	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 = "mbMessage.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 = "mbMessage.classPK = ?";
	private static final String _FINDER_COLUMN_T_P_THREADID_2 = "mbMessage.threadId = ? AND ";
	private static final String _FINDER_COLUMN_T_P_PARENTMESSAGEID_2 = "mbMessage.parentMessageId = ?";
	private static final String _FINDER_COLUMN_T_A_THREADID_2 = "mbMessage.threadId = ? AND ";
	private static final String _FINDER_COLUMN_T_A_ANSWER_2 = "mbMessage.answer = ?";
	private static final String _FINDER_COLUMN_T_S_THREADID_2 = "mbMessage.threadId = ? AND ";
	private static final String _FINDER_COLUMN_T_S_STATUS_2 = "mbMessage.status = ?";
	private static final String _FINDER_COLUMN_TR_S_THREADID_2 = "mbMessage.threadId = ? AND ";
	private static final String _FINDER_COLUMN_TR_S_STATUS_2 = "mbMessage.status = ? AND mbMessage.parentMessageId != 0";
	private static final String _FINDER_COLUMN_G_U_S_GROUPID_2 = "mbMessage.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_S_USERID_2 = "mbMessage.userId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_S_STATUS_2 = "mbMessage.status = ?";
	private static final String _FINDER_COLUMN_G_C_T_GROUPID_2 = "mbMessage.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_T_CATEGORYID_2 = "mbMessage.categoryId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_T_THREADID_2 = "mbMessage.threadId = ?";
	private static final String _FINDER_COLUMN_G_C_S_GROUPID_2 = "mbMessage.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_S_CATEGORYID_2 = "mbMessage.categoryId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_S_STATUS_2 = "mbMessage.status = ?";
	private static final String _FINDER_COLUMN_U_C_C_USERID_2 = "mbMessage.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_C_C_CLASSNAMEID_2 = "mbMessage.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_U_C_C_CLASSPK_2 = "mbMessage.classPK = ?";
	private static final String _FINDER_COLUMN_U_C_S_USERID_2 = "mbMessage.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_C_S_USERID_5 = "(" +
		_removeConjunction(_FINDER_COLUMN_U_C_S_USERID_2) + ")";
	private static final String _FINDER_COLUMN_U_C_S_CLASSNAMEID_2 = "mbMessage.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_U_C_S_CLASSNAMEID_5 = "(" +
		_removeConjunction(_FINDER_COLUMN_U_C_S_CLASSNAMEID_2) + ")";
	private static final String _FINDER_COLUMN_U_C_S_STATUS_2 = "mbMessage.status = ?";
	private static final String _FINDER_COLUMN_U_C_S_STATUS_5 = "(" +
		_removeConjunction(_FINDER_COLUMN_U_C_S_STATUS_2) + ")";
	private static final String _FINDER_COLUMN_C_C_S_CLASSNAMEID_2 = "mbMessage.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_S_CLASSPK_2 = "mbMessage.classPK = ? AND ";
	private static final String _FINDER_COLUMN_C_C_S_STATUS_2 = "mbMessage.status = ?";
	private static final String _FINDER_COLUMN_G_C_T_S_GROUPID_2 = "mbMessage.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_T_S_CATEGORYID_2 = "mbMessage.categoryId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_T_S_THREADID_2 = "mbMessage.threadId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_T_S_STATUS_2 = "mbMessage.status = ?";
	private static final String _FINDER_COLUMN_U_C_C_S_USERID_2 = "mbMessage.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_C_C_S_CLASSNAMEID_2 = "mbMessage.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_U_C_C_S_CLASSPK_2 = "mbMessage.classPK = ? AND ";
	private static final String _FINDER_COLUMN_U_C_C_S_STATUS_2 = "mbMessage.status = ?";

	private static String _removeConjunction(String sql) {
		int pos = sql.indexOf(" AND ");

		if (pos != -1) {
			sql = sql.substring(0, pos);
		}

		return sql;
	}

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "mbMessage.rootMessageId";
	private static final String _FILTER_SQL_SELECT_MBMESSAGE_WHERE = "SELECT DISTINCT {mbMessage.*} FROM MBMessage mbMessage WHERE ";
	private static final String _FILTER_SQL_SELECT_MBMESSAGE_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {MBMessage.*} FROM (SELECT DISTINCT mbMessage.messageId FROM MBMessage mbMessage WHERE ";
	private static final String _FILTER_SQL_SELECT_MBMESSAGE_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN MBMessage ON TEMP_TABLE.messageId = MBMessage.messageId";
	private static final String _FILTER_SQL_COUNT_MBMESSAGE_WHERE = "SELECT COUNT(DISTINCT mbMessage.messageId) AS COUNT_VALUE FROM MBMessage mbMessage WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "mbMessage";
	private static final String _FILTER_ENTITY_TABLE = "MBMessage";
	private static final String _ORDER_BY_ENTITY_ALIAS = "mbMessage.";
	private static final String _ORDER_BY_ENTITY_TABLE = "MBMessage.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No MBMessage exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No MBMessage exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(MBMessagePersistenceImpl.class);
	private static MBMessage _nullMBMessage = new MBMessageImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<MBMessage> toCacheModel() {
				return _nullMBMessageCacheModel;
			}
		};

	private static CacheModel<MBMessage> _nullMBMessageCacheModel = new CacheModel<MBMessage>() {
			public MBMessage toEntityModel() {
				return _nullMBMessage;
			}
		};
}