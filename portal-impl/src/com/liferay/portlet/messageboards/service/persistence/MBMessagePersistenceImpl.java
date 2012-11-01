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
import com.liferay.portal.kernel.util.ArrayUtil;
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
import com.liferay.portal.service.persistence.CompanyPersistence;
import com.liferay.portal.service.persistence.GroupPersistence;
import com.liferay.portal.service.persistence.LockPersistence;
import com.liferay.portal.service.persistence.PortletPreferencesPersistence;
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
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			MBMessageModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			MBMessageModelImpl.UUID_COLUMN_BITMASK |
			MBMessageModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			MBMessageModelImpl.UUID_COLUMN_BITMASK |
			MBMessageModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			MBMessageModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			MBMessageModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_THREADID = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByThreadId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_THREADID =
		new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByThreadId",
			new String[] { Long.class.getName() },
			MBMessageModelImpl.THREADID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_THREADID = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByThreadId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_THREADREPLIES =
		new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByThreadReplies",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_THREADREPLIES =
		new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByThreadReplies",
			new String[] { Long.class.getName() },
			MBMessageModelImpl.THREADID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_THREADREPLIES = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByThreadReplies",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID =
		new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] { Long.class.getName() },
			MBMessageModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_U",
			new String[] { Long.class.getName(), Long.class.getName() },
			MBMessageModelImpl.GROUPID_COLUMN_BITMASK |
			MBMessageModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			MBMessageModelImpl.GROUPID_COLUMN_BITMASK |
			MBMessageModelImpl.CATEGORYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_S",
			new String[] { Long.class.getName(), Integer.class.getName() },
			MBMessageModelImpl.GROUPID_COLUMN_BITMASK |
			MBMessageModelImpl.STATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_S",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_S",
			new String[] { Long.class.getName(), Integer.class.getName() },
			MBMessageModelImpl.COMPANYID_COLUMN_BITMASK |
			MBMessageModelImpl.STATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_S",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_U_C = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_C = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			MBMessageModelImpl.USERID_COLUMN_BITMASK |
			MBMessageModelImpl.CLASSNAMEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_C = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_U_C = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByU_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			MBMessageModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			MBMessageModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_T_P = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByT_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_P = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByT_P",
			new String[] { Long.class.getName(), Long.class.getName() },
			MBMessageModelImpl.THREADID_COLUMN_BITMASK |
			MBMessageModelImpl.PARENTMESSAGEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_T_P = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByT_P",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_T_A = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByT_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_A = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByT_A",
			new String[] { Long.class.getName(), Boolean.class.getName() },
			MBMessageModelImpl.THREADID_COLUMN_BITMASK |
			MBMessageModelImpl.ANSWER_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_T_A = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByT_A",
			new String[] { Long.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_T_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByT_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByT_S",
			new String[] { Long.class.getName(), Integer.class.getName() },
			MBMessageModelImpl.THREADID_COLUMN_BITMASK |
			MBMessageModelImpl.STATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_T_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByT_S",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_TR_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByTR_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TR_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByTR_S",
			new String[] { Long.class.getName(), Integer.class.getName() },
			MBMessageModelImpl.THREADID_COLUMN_BITMASK |
			MBMessageModelImpl.STATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_TR_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByTR_S",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_U_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_U_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			MBMessageModelImpl.GROUPID_COLUMN_BITMASK |
			MBMessageModelImpl.USERID_COLUMN_BITMASK |
			MBMessageModelImpl.STATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_T = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_T = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			MBMessageModelImpl.GROUPID_COLUMN_BITMASK |
			MBMessageModelImpl.CATEGORYID_COLUMN_BITMASK |
			MBMessageModelImpl.THREADID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_T = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			MBMessageModelImpl.GROUPID_COLUMN_BITMASK |
			MBMessageModelImpl.CATEGORYID_COLUMN_BITMASK |
			MBMessageModelImpl.STATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_U_C_C = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_C_C = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			MBMessageModelImpl.USERID_COLUMN_BITMASK |
			MBMessageModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			MBMessageModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_C_C = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_U_C_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_C_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			MBMessageModelImpl.USERID_COLUMN_BITMASK |
			MBMessageModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			MBMessageModelImpl.STATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_C_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_U_C_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByU_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			MBMessageModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			MBMessageModelImpl.CLASSPK_COLUMN_BITMASK |
			MBMessageModelImpl.STATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_T_A = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_T_A",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_T_A =
		new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_T_A",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			MBMessageModelImpl.GROUPID_COLUMN_BITMASK |
			MBMessageModelImpl.CATEGORYID_COLUMN_BITMASK |
			MBMessageModelImpl.THREADID_COLUMN_BITMASK |
			MBMessageModelImpl.ANSWER_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_T_A = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_T_A",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_T_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_T_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_T_S =
		new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_T_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			MBMessageModelImpl.GROUPID_COLUMN_BITMASK |
			MBMessageModelImpl.CATEGORYID_COLUMN_BITMASK |
			MBMessageModelImpl.THREADID_COLUMN_BITMASK |
			MBMessageModelImpl.STATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_T_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_T_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_U_C_C_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_C_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_C_C_S =
		new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_C_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			MBMessageModelImpl.USERID_COLUMN_BITMASK |
			MBMessageModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			MBMessageModelImpl.CLASSPK_COLUMN_BITMASK |
			MBMessageModelImpl.STATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_C_C_S = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_C_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, MBMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

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
						MBMessageImpl.class, mbMessage.getPrimaryKey()) == null) {
				cacheResult(mbMessage);
			}
			else {
				mbMessage.resetOriginalValues();
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
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
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

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(mbMessage);
	}

	@Override
	public void clearCache(List<MBMessage> mbMessages) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (MBMessage mbMessage : mbMessages) {
			EntityCacheUtil.removeResult(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
				MBMessageImpl.class, mbMessage.getPrimaryKey());

			clearUniqueFindersCache(mbMessage);
		}
	}

	protected void clearUniqueFindersCache(MBMessage mbMessage) {
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
	 * @param messageId the primary key of the message-boards message
	 * @return the message-boards message that was removed
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage remove(long messageId)
		throws NoSuchMessageException, SystemException {
		return remove(Long.valueOf(messageId));
	}

	/**
	 * Removes the message-boards message with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the message-boards message
	 * @return the message-boards message that was removed
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MBMessage remove(Serializable primaryKey)
		throws NoSuchMessageException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MBMessage mbMessage = (MBMessage)session.get(MBMessageImpl.class,
					primaryKey);

			if (mbMessage == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchMessageException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(mbMessage);
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

	@Override
	protected MBMessage removeImpl(MBMessage mbMessage)
		throws SystemException {
		mbMessage = toUnwrappedModel(mbMessage);

		Session session = null;

		try {
			session = openSession();

			if (mbMessage.isCachedModel()) {
				mbMessage = (MBMessage)session.get(MBMessageImpl.class,
						mbMessage.getPrimaryKeyObj());
			}

			if (mbMessage != null) {
				session.delete(mbMessage);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (mbMessage != null) {
			clearCache(mbMessage);
		}

		return mbMessage;
	}

	@Override
	public MBMessage updateImpl(
		com.liferay.portlet.messageboards.model.MBMessage mbMessage)
		throws SystemException {
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

			if (mbMessage.isNew()) {
				session.save(mbMessage);

				mbMessage.setNew(false);
			}
			else {
				session.merge(mbMessage);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !MBMessageModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((mbMessageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						mbMessageModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { mbMessageModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((mbMessageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						mbMessageModelImpl.getOriginalUuid(),
						Long.valueOf(mbMessageModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						mbMessageModelImpl.getUuid(),
						Long.valueOf(mbMessageModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((mbMessageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((mbMessageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((mbMessageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_THREADID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getOriginalThreadId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_THREADID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_THREADID,
					args);

				args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getThreadId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_THREADID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_THREADID,
					args);
			}

			if ((mbMessageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_THREADREPLIES.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getOriginalThreadId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_THREADREPLIES,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_THREADREPLIES,
					args);

				args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getThreadId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_THREADREPLIES,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_THREADREPLIES,
					args);
			}

			if ((mbMessageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getOriginalUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);

				args = new Object[] { Long.valueOf(mbMessageModelImpl.getUserId()) };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);
			}

			if ((mbMessageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getOriginalGroupId()),
						Long.valueOf(mbMessageModelImpl.getOriginalUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_U, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U,
					args);

				args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getGroupId()),
						Long.valueOf(mbMessageModelImpl.getUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_U, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U,
					args);
			}

			if ((mbMessageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getOriginalGroupId()),
						Long.valueOf(mbMessageModelImpl.getOriginalCategoryId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C,
					args);

				args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getGroupId()),
						Long.valueOf(mbMessageModelImpl.getCategoryId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C,
					args);
			}

			if ((mbMessageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getOriginalGroupId()),
						Integer.valueOf(mbMessageModelImpl.getOriginalStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S,
					args);

				args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getGroupId()),
						Integer.valueOf(mbMessageModelImpl.getStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S,
					args);
			}

			if ((mbMessageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getOriginalCompanyId()),
						Integer.valueOf(mbMessageModelImpl.getOriginalStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_S,
					args);

				args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getCompanyId()),
						Integer.valueOf(mbMessageModelImpl.getStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_S,
					args);
			}

			if ((mbMessageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getOriginalUserId()),
						Long.valueOf(mbMessageModelImpl.getOriginalClassNameId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_C,
					args);

				args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getUserId()),
						Long.valueOf(mbMessageModelImpl.getClassNameId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_C,
					args);
			}

			if ((mbMessageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getOriginalClassNameId()),
						Long.valueOf(mbMessageModelImpl.getOriginalClassPK())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);

				args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getClassNameId()),
						Long.valueOf(mbMessageModelImpl.getClassPK())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);
			}

			if ((mbMessageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getOriginalThreadId()),
						Long.valueOf(mbMessageModelImpl.getOriginalParentMessageId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_T_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_P,
					args);

				args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getThreadId()),
						Long.valueOf(mbMessageModelImpl.getParentMessageId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_T_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_P,
					args);
			}

			if ((mbMessageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_A.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getOriginalThreadId()),
						Boolean.valueOf(mbMessageModelImpl.getOriginalAnswer())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_T_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_A,
					args);

				args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getThreadId()),
						Boolean.valueOf(mbMessageModelImpl.getAnswer())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_T_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_A,
					args);
			}

			if ((mbMessageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getOriginalThreadId()),
						Integer.valueOf(mbMessageModelImpl.getOriginalStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_T_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_S,
					args);

				args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getThreadId()),
						Integer.valueOf(mbMessageModelImpl.getStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_T_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_S,
					args);
			}

			if ((mbMessageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TR_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getOriginalThreadId()),
						Integer.valueOf(mbMessageModelImpl.getOriginalStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_TR_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TR_S,
					args);

				args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getThreadId()),
						Integer.valueOf(mbMessageModelImpl.getStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_TR_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TR_S,
					args);
			}

			if ((mbMessageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getOriginalGroupId()),
						Long.valueOf(mbMessageModelImpl.getOriginalUserId()),
						Integer.valueOf(mbMessageModelImpl.getOriginalStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_U_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_S,
					args);

				args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getGroupId()),
						Long.valueOf(mbMessageModelImpl.getUserId()),
						Integer.valueOf(mbMessageModelImpl.getStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_U_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_S,
					args);
			}

			if ((mbMessageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getOriginalGroupId()),
						Long.valueOf(mbMessageModelImpl.getOriginalCategoryId()),
						Long.valueOf(mbMessageModelImpl.getOriginalThreadId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C_T, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_T,
					args);

				args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getGroupId()),
						Long.valueOf(mbMessageModelImpl.getCategoryId()),
						Long.valueOf(mbMessageModelImpl.getThreadId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C_T, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_T,
					args);
			}

			if ((mbMessageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getOriginalGroupId()),
						Long.valueOf(mbMessageModelImpl.getOriginalCategoryId()),
						Integer.valueOf(mbMessageModelImpl.getOriginalStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_S,
					args);

				args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getGroupId()),
						Long.valueOf(mbMessageModelImpl.getCategoryId()),
						Integer.valueOf(mbMessageModelImpl.getStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_S,
					args);
			}

			if ((mbMessageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getOriginalUserId()),
						Long.valueOf(mbMessageModelImpl.getOriginalClassNameId()),
						Long.valueOf(mbMessageModelImpl.getOriginalClassPK())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_C_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_C_C,
					args);

				args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getUserId()),
						Long.valueOf(mbMessageModelImpl.getClassNameId()),
						Long.valueOf(mbMessageModelImpl.getClassPK())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_C_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_C_C,
					args);
			}

			if ((mbMessageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_C_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getOriginalUserId()),
						Long.valueOf(mbMessageModelImpl.getOriginalClassNameId()),
						Integer.valueOf(mbMessageModelImpl.getOriginalStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_C_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_C_S,
					args);

				args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getUserId()),
						Long.valueOf(mbMessageModelImpl.getClassNameId()),
						Integer.valueOf(mbMessageModelImpl.getStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_C_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_C_S,
					args);
			}

			if ((mbMessageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getOriginalClassNameId()),
						Long.valueOf(mbMessageModelImpl.getOriginalClassPK()),
						Integer.valueOf(mbMessageModelImpl.getOriginalStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_S,
					args);

				args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getClassNameId()),
						Long.valueOf(mbMessageModelImpl.getClassPK()),
						Integer.valueOf(mbMessageModelImpl.getStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_S,
					args);
			}

			if ((mbMessageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_T_A.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getOriginalGroupId()),
						Long.valueOf(mbMessageModelImpl.getOriginalCategoryId()),
						Long.valueOf(mbMessageModelImpl.getOriginalThreadId()),
						Boolean.valueOf(mbMessageModelImpl.getOriginalAnswer())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C_T_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_T_A,
					args);

				args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getGroupId()),
						Long.valueOf(mbMessageModelImpl.getCategoryId()),
						Long.valueOf(mbMessageModelImpl.getThreadId()),
						Boolean.valueOf(mbMessageModelImpl.getAnswer())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C_T_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_T_A,
					args);
			}

			if ((mbMessageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_T_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getOriginalGroupId()),
						Long.valueOf(mbMessageModelImpl.getOriginalCategoryId()),
						Long.valueOf(mbMessageModelImpl.getOriginalThreadId()),
						Integer.valueOf(mbMessageModelImpl.getOriginalStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C_T_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_T_S,
					args);

				args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getGroupId()),
						Long.valueOf(mbMessageModelImpl.getCategoryId()),
						Long.valueOf(mbMessageModelImpl.getThreadId()),
						Integer.valueOf(mbMessageModelImpl.getStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C_T_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_T_S,
					args);
			}

			if ((mbMessageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_C_C_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getOriginalUserId()),
						Long.valueOf(mbMessageModelImpl.getOriginalClassNameId()),
						Long.valueOf(mbMessageModelImpl.getOriginalClassPK()),
						Integer.valueOf(mbMessageModelImpl.getOriginalStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_C_C_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_C_C_S,
					args);

				args = new Object[] {
						Long.valueOf(mbMessageModelImpl.getUserId()),
						Long.valueOf(mbMessageModelImpl.getClassNameId()),
						Long.valueOf(mbMessageModelImpl.getClassPK()),
						Integer.valueOf(mbMessageModelImpl.getStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_C_C_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_C_C_S,
					args);
			}
		}

		EntityCacheUtil.putResult(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageImpl.class, mbMessage.getPrimaryKey(), mbMessage);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					mbMessage.getUuid(), Long.valueOf(mbMessage.getGroupId())
				}, mbMessage);
		}
		else {
			if ((mbMessageModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						mbMessageModelImpl.getOriginalUuid(),
						Long.valueOf(mbMessageModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
					new Object[] {
						mbMessage.getUuid(),
						Long.valueOf(mbMessage.getGroupId())
					}, mbMessage);
			}
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
				MBMessageImpl.class, messageId);

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

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBMessage mbMessage : list) {
				if (!Validator.equals(uuid, mbMessage.getUuid())) {
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
	 * Returns the first message-boards message in the ordered set where uuid = &#63;.
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
		MBMessage mbMessage = fetchByUuid_First(uuid, orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMessageException(msg.toString());
	}

	/**
	 * Returns the first message-boards message in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<MBMessage> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message-boards message in the ordered set where uuid = &#63;.
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
		MBMessage mbMessage = fetchByUuid_Last(uuid, orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMessageException(msg.toString());
	}

	/**
	 * Returns the last message-boards message in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<MBMessage> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where uuid = &#63;.
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
			Object[] values = orderByComparator.getOrderByConditionValues(mbMessage);

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

		if (result instanceof MBMessage) {
			MBMessage mbMessage = (MBMessage)result;

			if (!Validator.equals(uuid, mbMessage.getUuid()) ||
					(groupId != mbMessage.getGroupId())) {
				result = null;
			}
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
	 * Returns all the message-boards messages where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByUuid_C(String uuid, long companyId)
		throws SystemException {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message-boards messages where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByUuid_C(String uuid, long companyId, int start,
		int end) throws SystemException {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message-boards messages where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByUuid_C(String uuid, long companyId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
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

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBMessage mbMessage : list) {
				if (!Validator.equals(uuid, mbMessage.getUuid()) ||
						(companyId != mbMessage.getCompanyId())) {
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

			query.append(_SQL_SELECT_MBMESSAGE_WHERE);

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

				qPos.add(companyId);

				list = (List<MBMessage>)QueryUtil.list(q, getDialect(), start,
						end);
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
	 * Returns the first message-boards message in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMessageException(msg.toString());
	}

	/**
	 * Returns the first message-boards message in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<MBMessage> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message-boards message in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMessageException(msg.toString());
	}

	/**
	 * Returns the last message-boards message in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid_C(uuid, companyId);

		List<MBMessage> list = findByUuid_C(uuid, companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage[] findByUuid_C_PrevAndNext(long messageId, String uuid,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		Session session = null;

		try {
			session = openSession();

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, mbMessage, uuid,
					companyId, orderByComparator, true);

			array[1] = mbMessage;

			array[2] = getByUuid_C_PrevAndNext(session, mbMessage, uuid,
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

	protected MBMessage getByUuid_C_PrevAndNext(Session session,
		MBMessage mbMessage, String uuid, long companyId,
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

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbMessage);

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

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBMessage mbMessage : list) {
				if ((groupId != mbMessage.getGroupId())) {
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
	 * Returns the first message-boards message in the ordered set where groupId = &#63;.
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
		MBMessage mbMessage = fetchByGroupId_First(groupId, orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMessageException(msg.toString());
	}

	/**
	 * Returns the first message-boards message in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByGroupId_First(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		List<MBMessage> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63;.
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
		MBMessage mbMessage = fetchByGroupId_Last(groupId, orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMessageException(msg.toString());
	}

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByGroupId_Last(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByGroupId(groupId);

		List<MBMessage> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where groupId = &#63;.
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
			query.append(MBMessageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbMessage);

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
			Object[] values = orderByComparator.getOrderByConditionValues(mbMessage);

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

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBMessage mbMessage : list) {
				if ((companyId != mbMessage.getCompanyId())) {
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
	 * Returns the first message-boards message in the ordered set where companyId = &#63;.
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
		MBMessage mbMessage = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMessageException(msg.toString());
	}

	/**
	 * Returns the first message-boards message in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByCompanyId_First(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<MBMessage> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message-boards message in the ordered set where companyId = &#63;.
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
		MBMessage mbMessage = fetchByCompanyId_Last(companyId, orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMessageException(msg.toString());
	}

	/**
	 * Returns the last message-boards message in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByCompanyId(companyId);

		List<MBMessage> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where companyId = &#63;.
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
			query.append(MBMessageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbMessage);

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
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_THREADID;
			finderArgs = new Object[] { threadId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_THREADID;
			finderArgs = new Object[] { threadId, start, end, orderByComparator };
		}

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBMessage mbMessage : list) {
				if ((threadId != mbMessage.getThreadId())) {
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
	 * Returns the first message-boards message in the ordered set where threadId = &#63;.
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
		MBMessage mbMessage = fetchByThreadId_First(threadId, orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("threadId=");
		msg.append(threadId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMessageException(msg.toString());
	}

	/**
	 * Returns the first message-boards message in the ordered set where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByThreadId_First(long threadId,
		OrderByComparator orderByComparator) throws SystemException {
		List<MBMessage> list = findByThreadId(threadId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message-boards message in the ordered set where threadId = &#63;.
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
		MBMessage mbMessage = fetchByThreadId_Last(threadId, orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("threadId=");
		msg.append(threadId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMessageException(msg.toString());
	}

	/**
	 * Returns the last message-boards message in the ordered set where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByThreadId_Last(long threadId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByThreadId(threadId);

		List<MBMessage> list = findByThreadId(threadId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where threadId = &#63;.
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
			query.append(MBMessageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(threadId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbMessage);

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
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_THREADREPLIES;
			finderArgs = new Object[] { threadId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_THREADREPLIES;
			finderArgs = new Object[] { threadId, start, end, orderByComparator };
		}

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBMessage mbMessage : list) {
				if ((threadId != mbMessage.getThreadId())) {
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
	 * Returns the first message-boards message in the ordered set where threadId = &#63;.
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
		MBMessage mbMessage = fetchByThreadReplies_First(threadId,
				orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("threadId=");
		msg.append(threadId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMessageException(msg.toString());
	}

	/**
	 * Returns the first message-boards message in the ordered set where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByThreadReplies_First(long threadId,
		OrderByComparator orderByComparator) throws SystemException {
		List<MBMessage> list = findByThreadReplies(threadId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message-boards message in the ordered set where threadId = &#63;.
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
		MBMessage mbMessage = fetchByThreadReplies_Last(threadId,
				orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("threadId=");
		msg.append(threadId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMessageException(msg.toString());
	}

	/**
	 * Returns the last message-boards message in the ordered set where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByThreadReplies_Last(long threadId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByThreadReplies(threadId);

		List<MBMessage> list = findByThreadReplies(threadId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where threadId = &#63;.
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
			query.append(MBMessageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(threadId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbMessage);

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

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBMessage mbMessage : list) {
				if ((userId != mbMessage.getUserId())) {
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
	 * Returns the first message-boards message in the ordered set where userId = &#63;.
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
		MBMessage mbMessage = fetchByUserId_First(userId, orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMessageException(msg.toString());
	}

	/**
	 * Returns the first message-boards message in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByUserId_First(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		List<MBMessage> list = findByUserId(userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message-boards message in the ordered set where userId = &#63;.
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
		MBMessage mbMessage = fetchByUserId_Last(userId, orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMessageException(msg.toString());
	}

	/**
	 * Returns the last message-boards message in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByUserId_Last(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUserId(userId);

		List<MBMessage> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where userId = &#63;.
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
			query.append(MBMessageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbMessage);

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
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U;
			finderArgs = new Object[] { groupId, userId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U;
			finderArgs = new Object[] {
					groupId, userId,
					
					start, end, orderByComparator
				};
		}

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBMessage mbMessage : list) {
				if ((groupId != mbMessage.getGroupId()) ||
						(userId != mbMessage.getUserId())) {
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
	 * Returns the first message-boards message in the ordered set where groupId = &#63; and userId = &#63;.
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
		MBMessage mbMessage = fetchByG_U_First(groupId, userId,
				orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMessageException(msg.toString());
	}

	/**
	 * Returns the first message-boards message in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByG_U_First(long groupId, long userId,
		OrderByComparator orderByComparator) throws SystemException {
		List<MBMessage> list = findByG_U(groupId, userId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63; and userId = &#63;.
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
		MBMessage mbMessage = fetchByG_U_Last(groupId, userId, orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMessageException(msg.toString());
	}

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByG_U_Last(long groupId, long userId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByG_U(groupId, userId);

		List<MBMessage> list = findByG_U(groupId, userId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where groupId = &#63; and userId = &#63;.
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
			Object[] values = orderByComparator.getOrderByConditionValues(mbMessage);

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
			Object[] values = orderByComparator.getOrderByConditionValues(mbMessage);

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
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C;
			finderArgs = new Object[] { groupId, categoryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C;
			finderArgs = new Object[] {
					groupId, categoryId,
					
					start, end, orderByComparator
				};
		}

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBMessage mbMessage : list) {
				if ((groupId != mbMessage.getGroupId()) ||
						(categoryId != mbMessage.getCategoryId())) {
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
	 * Returns the first message-boards message in the ordered set where groupId = &#63; and categoryId = &#63;.
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
		MBMessage mbMessage = fetchByG_C_First(groupId, categoryId,
				orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", categoryId=");
		msg.append(categoryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMessageException(msg.toString());
	}

	/**
	 * Returns the first message-boards message in the ordered set where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByG_C_First(long groupId, long categoryId,
		OrderByComparator orderByComparator) throws SystemException {
		List<MBMessage> list = findByG_C(groupId, categoryId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63; and categoryId = &#63;.
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
		MBMessage mbMessage = fetchByG_C_Last(groupId, categoryId,
				orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", categoryId=");
		msg.append(categoryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMessageException(msg.toString());
	}

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByG_C_Last(long groupId, long categoryId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByG_C(groupId, categoryId);

		List<MBMessage> list = findByG_C(groupId, categoryId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where groupId = &#63; and categoryId = &#63;.
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
			Object[] values = orderByComparator.getOrderByConditionValues(mbMessage);

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
			Object[] values = orderByComparator.getOrderByConditionValues(mbMessage);

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
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S;
			finderArgs = new Object[] { groupId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_S;
			finderArgs = new Object[] {
					groupId, status,
					
					start, end, orderByComparator
				};
		}

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBMessage mbMessage : list) {
				if ((groupId != mbMessage.getGroupId()) ||
						(status != mbMessage.getStatus())) {
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
	 * Returns the first message-boards message in the ordered set where groupId = &#63; and status = &#63;.
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
		MBMessage mbMessage = fetchByG_S_First(groupId, status,
				orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMessageException(msg.toString());
	}

	/**
	 * Returns the first message-boards message in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByG_S_First(long groupId, int status,
		OrderByComparator orderByComparator) throws SystemException {
		List<MBMessage> list = findByG_S(groupId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63; and status = &#63;.
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
		MBMessage mbMessage = fetchByG_S_Last(groupId, status, orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMessageException(msg.toString());
	}

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByG_S_Last(long groupId, int status,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByG_S(groupId, status);

		List<MBMessage> list = findByG_S(groupId, status, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where groupId = &#63; and status = &#63;.
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
			Object[] values = orderByComparator.getOrderByConditionValues(mbMessage);

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
			Object[] values = orderByComparator.getOrderByConditionValues(mbMessage);

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
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_S;
			finderArgs = new Object[] { companyId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_S;
			finderArgs = new Object[] {
					companyId, status,
					
					start, end, orderByComparator
				};
		}

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBMessage mbMessage : list) {
				if ((companyId != mbMessage.getCompanyId()) ||
						(status != mbMessage.getStatus())) {
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
	 * Returns the first message-boards message in the ordered set where companyId = &#63; and status = &#63;.
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
		MBMessage mbMessage = fetchByC_S_First(companyId, status,
				orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMessageException(msg.toString());
	}

	/**
	 * Returns the first message-boards message in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByC_S_First(long companyId, int status,
		OrderByComparator orderByComparator) throws SystemException {
		List<MBMessage> list = findByC_S(companyId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message-boards message in the ordered set where companyId = &#63; and status = &#63;.
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
		MBMessage mbMessage = fetchByC_S_Last(companyId, status,
				orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMessageException(msg.toString());
	}

	/**
	 * Returns the last message-boards message in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByC_S_Last(long companyId, int status,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByC_S(companyId, status);

		List<MBMessage> list = findByC_S(companyId, status, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where companyId = &#63; and status = &#63;.
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
			Object[] values = orderByComparator.getOrderByConditionValues(mbMessage);

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
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_C;
			finderArgs = new Object[] { userId, classNameId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_U_C;
			finderArgs = new Object[] {
					userId, classNameId,
					
					start, end, orderByComparator
				};
		}

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBMessage mbMessage : list) {
				if ((userId != mbMessage.getUserId()) ||
						(classNameId != mbMessage.getClassNameId())) {
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
	 * Returns the first message-boards message in the ordered set where userId = &#63; and classNameId = &#63;.
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
		MBMessage mbMessage = fetchByU_C_First(userId, classNameId,
				orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMessageException(msg.toString());
	}

	/**
	 * Returns the first message-boards message in the ordered set where userId = &#63; and classNameId = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByU_C_First(long userId, long classNameId,
		OrderByComparator orderByComparator) throws SystemException {
		List<MBMessage> list = findByU_C(userId, classNameId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message-boards message in the ordered set where userId = &#63; and classNameId = &#63;.
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
		MBMessage mbMessage = fetchByU_C_Last(userId, classNameId,
				orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMessageException(msg.toString());
	}

	/**
	 * Returns the last message-boards message in the ordered set where userId = &#63; and classNameId = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByU_C_Last(long userId, long classNameId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByU_C(userId, classNameId);

		List<MBMessage> list = findByU_C(userId, classNameId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where userId = &#63; and classNameId = &#63;.
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
			Object[] values = orderByComparator.getOrderByConditionValues(mbMessage);

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
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_U_C;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderArgs = new Object[] { userId, StringUtil.merge(classNameIds) };
		}
		else {
			finderArgs = new Object[] {
					userId, StringUtil.merge(classNameIds),
					
					start, end, orderByComparator
				};
		}

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBMessage mbMessage : list) {
				if ((userId != mbMessage.getUserId()) ||
						!ArrayUtil.contains(classNameIds,
							mbMessage.getClassNameId())) {
					list = null;

					break;
				}
			}
		}

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
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C;
			finderArgs = new Object[] { classNameId, classPK };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C;
			finderArgs = new Object[] {
					classNameId, classPK,
					
					start, end, orderByComparator
				};
		}

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBMessage mbMessage : list) {
				if ((classNameId != mbMessage.getClassNameId()) ||
						(classPK != mbMessage.getClassPK())) {
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
	 * Returns the first message-boards message in the ordered set where classNameId = &#63; and classPK = &#63;.
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
		MBMessage mbMessage = fetchByC_C_First(classNameId, classPK,
				orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMessageException(msg.toString());
	}

	/**
	 * Returns the first message-boards message in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByC_C_First(long classNameId, long classPK,
		OrderByComparator orderByComparator) throws SystemException {
		List<MBMessage> list = findByC_C(classNameId, classPK, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message-boards message in the ordered set where classNameId = &#63; and classPK = &#63;.
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
		MBMessage mbMessage = fetchByC_C_Last(classNameId, classPK,
				orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMessageException(msg.toString());
	}

	/**
	 * Returns the last message-boards message in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByC_C_Last(long classNameId, long classPK,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByC_C(classNameId, classPK);

		List<MBMessage> list = findByC_C(classNameId, classPK, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where classNameId = &#63; and classPK = &#63;.
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
			Object[] values = orderByComparator.getOrderByConditionValues(mbMessage);

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
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_P;
			finderArgs = new Object[] { threadId, parentMessageId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_T_P;
			finderArgs = new Object[] {
					threadId, parentMessageId,
					
					start, end, orderByComparator
				};
		}

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBMessage mbMessage : list) {
				if ((threadId != mbMessage.getThreadId()) ||
						(parentMessageId != mbMessage.getParentMessageId())) {
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
	 * Returns the first message-boards message in the ordered set where threadId = &#63; and parentMessageId = &#63;.
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
		MBMessage mbMessage = fetchByT_P_First(threadId, parentMessageId,
				orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("threadId=");
		msg.append(threadId);

		msg.append(", parentMessageId=");
		msg.append(parentMessageId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMessageException(msg.toString());
	}

	/**
	 * Returns the first message-boards message in the ordered set where threadId = &#63; and parentMessageId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param parentMessageId the parent message ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByT_P_First(long threadId, long parentMessageId,
		OrderByComparator orderByComparator) throws SystemException {
		List<MBMessage> list = findByT_P(threadId, parentMessageId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message-boards message in the ordered set where threadId = &#63; and parentMessageId = &#63;.
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
		MBMessage mbMessage = fetchByT_P_Last(threadId, parentMessageId,
				orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("threadId=");
		msg.append(threadId);

		msg.append(", parentMessageId=");
		msg.append(parentMessageId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMessageException(msg.toString());
	}

	/**
	 * Returns the last message-boards message in the ordered set where threadId = &#63; and parentMessageId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param parentMessageId the parent message ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByT_P_Last(long threadId, long parentMessageId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByT_P(threadId, parentMessageId);

		List<MBMessage> list = findByT_P(threadId, parentMessageId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where threadId = &#63; and parentMessageId = &#63;.
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
			Object[] values = orderByComparator.getOrderByConditionValues(mbMessage);

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
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_A;
			finderArgs = new Object[] { threadId, answer };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_T_A;
			finderArgs = new Object[] {
					threadId, answer,
					
					start, end, orderByComparator
				};
		}

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBMessage mbMessage : list) {
				if ((threadId != mbMessage.getThreadId()) ||
						(answer != mbMessage.getAnswer())) {
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
	 * Returns the first message-boards message in the ordered set where threadId = &#63; and answer = &#63;.
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
		MBMessage mbMessage = fetchByT_A_First(threadId, answer,
				orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("threadId=");
		msg.append(threadId);

		msg.append(", answer=");
		msg.append(answer);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMessageException(msg.toString());
	}

	/**
	 * Returns the first message-boards message in the ordered set where threadId = &#63; and answer = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByT_A_First(long threadId, boolean answer,
		OrderByComparator orderByComparator) throws SystemException {
		List<MBMessage> list = findByT_A(threadId, answer, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message-boards message in the ordered set where threadId = &#63; and answer = &#63;.
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
		MBMessage mbMessage = fetchByT_A_Last(threadId, answer,
				orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("threadId=");
		msg.append(threadId);

		msg.append(", answer=");
		msg.append(answer);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMessageException(msg.toString());
	}

	/**
	 * Returns the last message-boards message in the ordered set where threadId = &#63; and answer = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByT_A_Last(long threadId, boolean answer,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByT_A(threadId, answer);

		List<MBMessage> list = findByT_A(threadId, answer, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where threadId = &#63; and answer = &#63;.
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
			Object[] values = orderByComparator.getOrderByConditionValues(mbMessage);

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
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_S;
			finderArgs = new Object[] { threadId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_T_S;
			finderArgs = new Object[] {
					threadId, status,
					
					start, end, orderByComparator
				};
		}

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBMessage mbMessage : list) {
				if ((threadId != mbMessage.getThreadId()) ||
						(status != mbMessage.getStatus())) {
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
	 * Returns the first message-boards message in the ordered set where threadId = &#63; and status = &#63;.
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
		MBMessage mbMessage = fetchByT_S_First(threadId, status,
				orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("threadId=");
		msg.append(threadId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMessageException(msg.toString());
	}

	/**
	 * Returns the first message-boards message in the ordered set where threadId = &#63; and status = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByT_S_First(long threadId, int status,
		OrderByComparator orderByComparator) throws SystemException {
		List<MBMessage> list = findByT_S(threadId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message-boards message in the ordered set where threadId = &#63; and status = &#63;.
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
		MBMessage mbMessage = fetchByT_S_Last(threadId, status,
				orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("threadId=");
		msg.append(threadId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMessageException(msg.toString());
	}

	/**
	 * Returns the last message-boards message in the ordered set where threadId = &#63; and status = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByT_S_Last(long threadId, int status,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByT_S(threadId, status);

		List<MBMessage> list = findByT_S(threadId, status, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where threadId = &#63; and status = &#63;.
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
			Object[] values = orderByComparator.getOrderByConditionValues(mbMessage);

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
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TR_S;
			finderArgs = new Object[] { threadId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_TR_S;
			finderArgs = new Object[] {
					threadId, status,
					
					start, end, orderByComparator
				};
		}

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBMessage mbMessage : list) {
				if ((threadId != mbMessage.getThreadId()) ||
						(status != mbMessage.getStatus())) {
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
	 * Returns the first message-boards message in the ordered set where threadId = &#63; and status = &#63;.
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
		MBMessage mbMessage = fetchByTR_S_First(threadId, status,
				orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("threadId=");
		msg.append(threadId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMessageException(msg.toString());
	}

	/**
	 * Returns the first message-boards message in the ordered set where threadId = &#63; and status = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByTR_S_First(long threadId, int status,
		OrderByComparator orderByComparator) throws SystemException {
		List<MBMessage> list = findByTR_S(threadId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message-boards message in the ordered set where threadId = &#63; and status = &#63;.
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
		MBMessage mbMessage = fetchByTR_S_Last(threadId, status,
				orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("threadId=");
		msg.append(threadId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMessageException(msg.toString());
	}

	/**
	 * Returns the last message-boards message in the ordered set where threadId = &#63; and status = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByTR_S_Last(long threadId, int status,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByTR_S(threadId, status);

		List<MBMessage> list = findByTR_S(threadId, status, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where threadId = &#63; and status = &#63;.
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
			Object[] values = orderByComparator.getOrderByConditionValues(mbMessage);

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
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_S;
			finderArgs = new Object[] { groupId, userId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_S;
			finderArgs = new Object[] {
					groupId, userId, status,
					
					start, end, orderByComparator
				};
		}

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBMessage mbMessage : list) {
				if ((groupId != mbMessage.getGroupId()) ||
						(userId != mbMessage.getUserId()) ||
						(status != mbMessage.getStatus())) {
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
	 * Returns the first message-boards message in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
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
		MBMessage mbMessage = fetchByG_U_S_First(groupId, userId, status,
				orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

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

	/**
	 * Returns the first message-boards message in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByG_U_S_First(long groupId, long userId, int status,
		OrderByComparator orderByComparator) throws SystemException {
		List<MBMessage> list = findByG_U_S(groupId, userId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
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
		MBMessage mbMessage = fetchByG_U_S_Last(groupId, userId, status,
				orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

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

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByG_U_S_Last(long groupId, long userId, int status,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByG_U_S(groupId, userId, status);

		List<MBMessage> list = findByG_U_S(groupId, userId, status, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
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
			Object[] values = orderByComparator.getOrderByConditionValues(mbMessage);

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
			Object[] values = orderByComparator.getOrderByConditionValues(mbMessage);

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
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_T;
			finderArgs = new Object[] { groupId, categoryId, threadId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_T;
			finderArgs = new Object[] {
					groupId, categoryId, threadId,
					
					start, end, orderByComparator
				};
		}

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBMessage mbMessage : list) {
				if ((groupId != mbMessage.getGroupId()) ||
						(categoryId != mbMessage.getCategoryId()) ||
						(threadId != mbMessage.getThreadId())) {
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
	 * Returns the first message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
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
		MBMessage mbMessage = fetchByG_C_T_First(groupId, categoryId, threadId,
				orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

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

	/**
	 * Returns the first message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByG_C_T_First(long groupId, long categoryId,
		long threadId, OrderByComparator orderByComparator)
		throws SystemException {
		List<MBMessage> list = findByG_C_T(groupId, categoryId, threadId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
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
		MBMessage mbMessage = fetchByG_C_T_Last(groupId, categoryId, threadId,
				orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

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

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByG_C_T_Last(long groupId, long categoryId,
		long threadId, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByG_C_T(groupId, categoryId, threadId);

		List<MBMessage> list = findByG_C_T(groupId, categoryId, threadId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
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
			Object[] values = orderByComparator.getOrderByConditionValues(mbMessage);

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
			Object[] values = orderByComparator.getOrderByConditionValues(mbMessage);

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
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_S;
			finderArgs = new Object[] { groupId, categoryId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_S;
			finderArgs = new Object[] {
					groupId, categoryId, status,
					
					start, end, orderByComparator
				};
		}

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBMessage mbMessage : list) {
				if ((groupId != mbMessage.getGroupId()) ||
						(categoryId != mbMessage.getCategoryId()) ||
						(status != mbMessage.getStatus())) {
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
	 * Returns the first message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and status = &#63;.
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
		MBMessage mbMessage = fetchByG_C_S_First(groupId, categoryId, status,
				orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

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

	/**
	 * Returns the first message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByG_C_S_First(long groupId, long categoryId,
		int status, OrderByComparator orderByComparator)
		throws SystemException {
		List<MBMessage> list = findByG_C_S(groupId, categoryId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and status = &#63;.
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
		MBMessage mbMessage = fetchByG_C_S_Last(groupId, categoryId, status,
				orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

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

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByG_C_S_Last(long groupId, long categoryId,
		int status, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByG_C_S(groupId, categoryId, status);

		List<MBMessage> list = findByG_C_S(groupId, categoryId, status,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and status = &#63;.
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
			Object[] values = orderByComparator.getOrderByConditionValues(mbMessage);

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
			Object[] values = orderByComparator.getOrderByConditionValues(mbMessage);

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
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_C_C;
			finderArgs = new Object[] { userId, classNameId, classPK };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_U_C_C;
			finderArgs = new Object[] {
					userId, classNameId, classPK,
					
					start, end, orderByComparator
				};
		}

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBMessage mbMessage : list) {
				if ((userId != mbMessage.getUserId()) ||
						(classNameId != mbMessage.getClassNameId()) ||
						(classPK != mbMessage.getClassPK())) {
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
	 * Returns the first message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63;.
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
		MBMessage mbMessage = fetchByU_C_C_First(userId, classNameId, classPK,
				orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

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

	/**
	 * Returns the first message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByU_C_C_First(long userId, long classNameId,
		long classPK, OrderByComparator orderByComparator)
		throws SystemException {
		List<MBMessage> list = findByU_C_C(userId, classNameId, classPK, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63;.
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
		MBMessage mbMessage = fetchByU_C_C_Last(userId, classNameId, classPK,
				orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

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

	/**
	 * Returns the last message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByU_C_C_Last(long userId, long classNameId,
		long classPK, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByU_C_C(userId, classNameId, classPK);

		List<MBMessage> list = findByU_C_C(userId, classNameId, classPK,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63;.
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
			Object[] values = orderByComparator.getOrderByConditionValues(mbMessage);

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
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_C_S;
			finderArgs = new Object[] { userId, classNameId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_U_C_S;
			finderArgs = new Object[] {
					userId, classNameId, status,
					
					start, end, orderByComparator
				};
		}

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBMessage mbMessage : list) {
				if ((userId != mbMessage.getUserId()) ||
						(classNameId != mbMessage.getClassNameId()) ||
						(status != mbMessage.getStatus())) {
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
	 * Returns the first message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and status = &#63;.
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
		MBMessage mbMessage = fetchByU_C_S_First(userId, classNameId, status,
				orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

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

	/**
	 * Returns the first message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByU_C_S_First(long userId, long classNameId,
		int status, OrderByComparator orderByComparator)
		throws SystemException {
		List<MBMessage> list = findByU_C_S(userId, classNameId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and status = &#63;.
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
		MBMessage mbMessage = fetchByU_C_S_Last(userId, classNameId, status,
				orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

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

	/**
	 * Returns the last message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByU_C_S_Last(long userId, long classNameId,
		int status, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByU_C_S(userId, classNameId, status);

		List<MBMessage> list = findByU_C_S(userId, classNameId, status,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and status = &#63;.
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
			Object[] values = orderByComparator.getOrderByConditionValues(mbMessage);

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
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_U_C_S;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderArgs = new Object[] {
					userId, StringUtil.merge(classNameIds), status
				};
		}
		else {
			finderArgs = new Object[] {
					userId, StringUtil.merge(classNameIds), status,
					
					start, end, orderByComparator
				};
		}

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBMessage mbMessage : list) {
				if ((userId != mbMessage.getUserId()) ||
						!ArrayUtil.contains(classNameIds,
							mbMessage.getClassNameId()) ||
						(status != mbMessage.getStatus())) {
					list = null;

					break;
				}
			}
		}

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
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_S;
			finderArgs = new Object[] { classNameId, classPK, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C_S;
			finderArgs = new Object[] {
					classNameId, classPK, status,
					
					start, end, orderByComparator
				};
		}

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBMessage mbMessage : list) {
				if ((classNameId != mbMessage.getClassNameId()) ||
						(classPK != mbMessage.getClassPK()) ||
						(status != mbMessage.getStatus())) {
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
	 * Returns the first message-boards message in the ordered set where classNameId = &#63; and classPK = &#63; and status = &#63;.
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
		MBMessage mbMessage = fetchByC_C_S_First(classNameId, classPK, status,
				orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

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

	/**
	 * Returns the first message-boards message in the ordered set where classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByC_C_S_First(long classNameId, long classPK,
		int status, OrderByComparator orderByComparator)
		throws SystemException {
		List<MBMessage> list = findByC_C_S(classNameId, classPK, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message-boards message in the ordered set where classNameId = &#63; and classPK = &#63; and status = &#63;.
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
		MBMessage mbMessage = fetchByC_C_S_Last(classNameId, classPK, status,
				orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

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

	/**
	 * Returns the last message-boards message in the ordered set where classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByC_C_S_Last(long classNameId, long classPK,
		int status, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByC_C_S(classNameId, classPK, status);

		List<MBMessage> list = findByC_C_S(classNameId, classPK, status,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where classNameId = &#63; and classPK = &#63; and status = &#63;.
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
			Object[] values = orderByComparator.getOrderByConditionValues(mbMessage);

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
	 * Returns all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @return the matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByG_C_T_A(long groupId, long categoryId,
		long threadId, boolean answer) throws SystemException {
		return findByG_C_T_A(groupId, categoryId, threadId, answer,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByG_C_T_A(long groupId, long categoryId,
		long threadId, boolean answer, int start, int end)
		throws SystemException {
		return findByG_C_T_A(groupId, categoryId, threadId, answer, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> findByG_C_T_A(long groupId, long categoryId,
		long threadId, boolean answer, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_T_A;
			finderArgs = new Object[] { groupId, categoryId, threadId, answer };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_T_A;
			finderArgs = new Object[] {
					groupId, categoryId, threadId, answer,
					
					start, end, orderByComparator
				};
		}

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBMessage mbMessage : list) {
				if ((groupId != mbMessage.getGroupId()) ||
						(categoryId != mbMessage.getCategoryId()) ||
						(threadId != mbMessage.getThreadId()) ||
						(answer != mbMessage.getAnswer())) {
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
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_G_C_T_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_T_A_CATEGORYID_2);

			query.append(_FINDER_COLUMN_G_C_T_A_THREADID_2);

			query.append(_FINDER_COLUMN_G_C_T_A_ANSWER_2);

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

				qPos.add(answer);

				list = (List<MBMessage>)QueryUtil.list(q, getDialect(), start,
						end);
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
	 * Returns the first message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByG_C_T_A_First(long groupId, long categoryId,
		long threadId, boolean answer, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = fetchByG_C_T_A_First(groupId, categoryId,
				threadId, answer, orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", categoryId=");
		msg.append(categoryId);

		msg.append(", threadId=");
		msg.append(threadId);

		msg.append(", answer=");
		msg.append(answer);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMessageException(msg.toString());
	}

	/**
	 * Returns the first message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByG_C_T_A_First(long groupId, long categoryId,
		long threadId, boolean answer, OrderByComparator orderByComparator)
		throws SystemException {
		List<MBMessage> list = findByG_C_T_A(groupId, categoryId, threadId,
				answer, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage findByG_C_T_A_Last(long groupId, long categoryId,
		long threadId, boolean answer, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = fetchByG_C_T_A_Last(groupId, categoryId,
				threadId, answer, orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", categoryId=");
		msg.append(categoryId);

		msg.append(", threadId=");
		msg.append(threadId);

		msg.append(", answer=");
		msg.append(answer);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMessageException(msg.toString());
	}

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByG_C_T_A_Last(long groupId, long categoryId,
		long threadId, boolean answer, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByG_C_T_A(groupId, categoryId, threadId, answer);

		List<MBMessage> list = findByG_C_T_A(groupId, categoryId, threadId,
				answer, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage[] findByG_C_T_A_PrevAndNext(long messageId, long groupId,
		long categoryId, long threadId, boolean answer,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		Session session = null;

		try {
			session = openSession();

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = getByG_C_T_A_PrevAndNext(session, mbMessage, groupId,
					categoryId, threadId, answer, orderByComparator, true);

			array[1] = mbMessage;

			array[2] = getByG_C_T_A_PrevAndNext(session, mbMessage, groupId,
					categoryId, threadId, answer, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBMessage getByG_C_T_A_PrevAndNext(Session session,
		MBMessage mbMessage, long groupId, long categoryId, long threadId,
		boolean answer, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBMESSAGE_WHERE);

		query.append(_FINDER_COLUMN_G_C_T_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_T_A_CATEGORYID_2);

		query.append(_FINDER_COLUMN_G_C_T_A_THREADID_2);

		query.append(_FINDER_COLUMN_G_C_T_A_ANSWER_2);

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

		qPos.add(answer);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbMessage);

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
	 * Returns all the message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @return the matching message-boards messages that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> filterFindByG_C_T_A(long groupId, long categoryId,
		long threadId, boolean answer) throws SystemException {
		return filterFindByG_C_T_A(groupId, categoryId, threadId, answer,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> filterFindByG_C_T_A(long groupId, long categoryId,
		long threadId, boolean answer, int start, int end)
		throws SystemException {
		return filterFindByG_C_T_A(groupId, categoryId, threadId, answer,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the message-boards messages that the user has permissions to view where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessage> filterFindByG_C_T_A(long groupId, long categoryId,
		long threadId, boolean answer, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_T_A(groupId, categoryId, threadId, answer, start,
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

		query.append(_FINDER_COLUMN_G_C_T_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_T_A_CATEGORYID_2);

		query.append(_FINDER_COLUMN_G_C_T_A_THREADID_2);

		query.append(_FINDER_COLUMN_G_C_T_A_ANSWER_2);

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

			qPos.add(answer);

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
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set of message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageException if a message-boards message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage[] filterFindByG_C_T_A_PrevAndNext(long messageId,
		long groupId, long categoryId, long threadId, boolean answer,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_T_A_PrevAndNext(messageId, groupId, categoryId,
				threadId, answer, orderByComparator);
		}

		MBMessage mbMessage = findByPrimaryKey(messageId);

		Session session = null;

		try {
			session = openSession();

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = filterGetByG_C_T_A_PrevAndNext(session, mbMessage,
					groupId, categoryId, threadId, answer, orderByComparator,
					true);

			array[1] = mbMessage;

			array[2] = filterGetByG_C_T_A_PrevAndNext(session, mbMessage,
					groupId, categoryId, threadId, answer, orderByComparator,
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

	protected MBMessage filterGetByG_C_T_A_PrevAndNext(Session session,
		MBMessage mbMessage, long groupId, long categoryId, long threadId,
		boolean answer, OrderByComparator orderByComparator, boolean previous) {
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

		query.append(_FINDER_COLUMN_G_C_T_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_T_A_CATEGORYID_2);

		query.append(_FINDER_COLUMN_G_C_T_A_THREADID_2);

		query.append(_FINDER_COLUMN_G_C_T_A_ANSWER_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBMESSAGE_NO_INLINE_DISTINCT_WHERE_2);
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

		qPos.add(answer);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbMessage);

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
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_T_S;
			finderArgs = new Object[] { groupId, categoryId, threadId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_T_S;
			finderArgs = new Object[] {
					groupId, categoryId, threadId, status,
					
					start, end, orderByComparator
				};
		}

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBMessage mbMessage : list) {
				if ((groupId != mbMessage.getGroupId()) ||
						(categoryId != mbMessage.getCategoryId()) ||
						(threadId != mbMessage.getThreadId()) ||
						(status != mbMessage.getStatus())) {
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
	 * Returns the first message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
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
		MBMessage mbMessage = fetchByG_C_T_S_First(groupId, categoryId,
				threadId, status, orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

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

	/**
	 * Returns the first message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByG_C_T_S_First(long groupId, long categoryId,
		long threadId, int status, OrderByComparator orderByComparator)
		throws SystemException {
		List<MBMessage> list = findByG_C_T_S(groupId, categoryId, threadId,
				status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
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
		MBMessage mbMessage = fetchByG_C_T_S_Last(groupId, categoryId,
				threadId, status, orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

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

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByG_C_T_S_Last(long groupId, long categoryId,
		long threadId, int status, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByG_C_T_S(groupId, categoryId, threadId, status);

		List<MBMessage> list = findByG_C_T_S(groupId, categoryId, threadId,
				status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
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
			Object[] values = orderByComparator.getOrderByConditionValues(mbMessage);

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
			Object[] values = orderByComparator.getOrderByConditionValues(mbMessage);

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
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_C_C_S;
			finderArgs = new Object[] { userId, classNameId, classPK, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_U_C_C_S;
			finderArgs = new Object[] {
					userId, classNameId, classPK, status,
					
					start, end, orderByComparator
				};
		}

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBMessage mbMessage : list) {
				if ((userId != mbMessage.getUserId()) ||
						(classNameId != mbMessage.getClassNameId()) ||
						(classPK != mbMessage.getClassPK()) ||
						(status != mbMessage.getStatus())) {
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
	 * Returns the first message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
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
		MBMessage mbMessage = fetchByU_C_C_S_First(userId, classNameId,
				classPK, status, orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

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

	/**
	 * Returns the first message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByU_C_C_S_First(long userId, long classNameId,
		long classPK, int status, OrderByComparator orderByComparator)
		throws SystemException {
		List<MBMessage> list = findByU_C_C_S(userId, classNameId, classPK,
				status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
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
		MBMessage mbMessage = fetchByU_C_C_S_Last(userId, classNameId, classPK,
				status, orderByComparator);

		if (mbMessage != null) {
			return mbMessage;
		}

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

	/**
	 * Returns the last message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage fetchByU_C_C_S_Last(long userId, long classNameId,
		long classPK, int status, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByU_C_C_S(userId, classNameId, classPK, status);

		List<MBMessage> list = findByU_C_C_S(userId, classNameId, classPK,
				status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
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
			Object[] values = orderByComparator.getOrderByConditionValues(mbMessage);

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

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(finderPath,
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
	 * Removes all the message-boards messages where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (MBMessage mbMessage : findByUuid(uuid)) {
			remove(mbMessage);
		}
	}

	/**
	 * Removes the message-boards message where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the message-boards message that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessage removeByUUID_G(String uuid, long groupId)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByUUID_G(uuid, groupId);

		return remove(mbMessage);
	}

	/**
	 * Removes all the message-boards messages where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid_C(String uuid, long companyId)
		throws SystemException {
		for (MBMessage mbMessage : findByUuid_C(uuid, companyId)) {
			remove(mbMessage);
		}
	}

	/**
	 * Removes all the message-boards messages where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (MBMessage mbMessage : findByGroupId(groupId)) {
			remove(mbMessage);
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
			remove(mbMessage);
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
			remove(mbMessage);
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
			remove(mbMessage);
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
			remove(mbMessage);
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
			remove(mbMessage);
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
			remove(mbMessage);
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
			remove(mbMessage);
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
			remove(mbMessage);
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
			remove(mbMessage);
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
			remove(mbMessage);
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
			remove(mbMessage);
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
			remove(mbMessage);
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
			remove(mbMessage);
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
			remove(mbMessage);
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
			remove(mbMessage);
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
			remove(mbMessage);
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
			remove(mbMessage);
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
			remove(mbMessage);
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
			remove(mbMessage);
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
			remove(mbMessage);
		}
	}

	/**
	 * Removes all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_C_T_A(long groupId, long categoryId, long threadId,
		boolean answer) throws SystemException {
		for (MBMessage mbMessage : findByG_C_T_A(groupId, categoryId, threadId,
				answer)) {
			remove(mbMessage);
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
			remove(mbMessage);
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
			remove(mbMessage);
		}
	}

	/**
	 * Removes all the message-boards messages from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (MBMessage mbMessage : findAll()) {
			remove(mbMessage);
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
	 * Returns the number of message-boards messages where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid_C(String uuid, long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MBMESSAGE_WHERE);

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

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_U_C,
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

				FinderCacheUtil.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_U_C,
					finderArgs, count);

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

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_U_C_S,
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

				FinderCacheUtil.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_U_C_S,
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
	 * Returns the number of message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @return the number of matching message-boards messages
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_C_T_A(long groupId, long categoryId, long threadId,
		boolean answer) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, categoryId, threadId, answer };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_C_T_A,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_MBMESSAGE_WHERE);

			query.append(_FINDER_COLUMN_G_C_T_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_T_A_CATEGORYID_2);

			query.append(_FINDER_COLUMN_G_C_T_A_THREADID_2);

			query.append(_FINDER_COLUMN_G_C_T_A_ANSWER_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_C_T_A,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @return the number of matching message-boards messages that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_C_T_A(long groupId, long categoryId,
		long threadId, boolean answer) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_T_A(groupId, categoryId, threadId, answer);
		}

		StringBundler query = new StringBundler(5);

		query.append(_FILTER_SQL_COUNT_MBMESSAGE_WHERE);

		query.append(_FINDER_COLUMN_G_C_T_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_T_A_CATEGORYID_2);

		query.append(_FINDER_COLUMN_G_C_T_A_THREADID_2);

		query.append(_FINDER_COLUMN_G_C_T_A_ANSWER_2);

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

			qPos.add(answer);

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
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);

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
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
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
	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "mbMessage.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "mbMessage.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(mbMessage.uuid IS NULL OR mbMessage.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "mbMessage.companyId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "mbMessage.groupId = ? AND mbMessage.categoryId != -1";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "mbMessage.companyId = ? AND mbMessage.categoryId != -1";
	private static final String _FINDER_COLUMN_THREADID_THREADID_2 = "mbMessage.threadId = ?";
	private static final String _FINDER_COLUMN_THREADREPLIES_THREADID_2 = "mbMessage.threadId = ? AND mbMessage.parentMessageId != 0";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "mbMessage.userId = ? AND mbMessage.categoryId != -1";
	private static final String _FINDER_COLUMN_G_U_GROUPID_2 = "mbMessage.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_USERID_2 = "mbMessage.userId = ? AND (mbMessage.categoryId != -1) AND (mbMessage.anonymous = [$FALSE$])";
	private static final String _FINDER_COLUMN_G_C_GROUPID_2 = "mbMessage.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_CATEGORYID_2 = "mbMessage.categoryId = ?";
	private static final String _FINDER_COLUMN_G_S_GROUPID_2 = "mbMessage.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_S_STATUS_2 = "mbMessage.status = ? AND mbMessage.categoryId != -1";
	private static final String _FINDER_COLUMN_C_S_COMPANYID_2 = "mbMessage.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_S_STATUS_2 = "mbMessage.status = ? AND mbMessage.categoryId != -1";
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
	private static final String _FINDER_COLUMN_G_U_S_STATUS_2 = "mbMessage.status = ? AND mbMessage.categoryId != -1";
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
	private static final String _FINDER_COLUMN_G_C_T_A_GROUPID_2 = "mbMessage.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_T_A_CATEGORYID_2 = "mbMessage.categoryId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_T_A_THREADID_2 = "mbMessage.threadId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_T_A_ANSWER_2 = "mbMessage.answer = ?";
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