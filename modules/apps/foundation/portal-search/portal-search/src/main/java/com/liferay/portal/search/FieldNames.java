/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.search;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Michael C. Han
 */
@ProviderType
public class FieldNames {

	public static final String ANY = StringPool.STAR;

	public static final String ARTICLE_ID = "articleId";

	public static final String ASSET_CATEGORY_ID = "assetCategoryId";

	public static final String ASSET_CATEGORY_IDS = "assetCategoryIds";

	public static final String ASSET_CATEGORY_TITLE = "assetCategoryTitle";

	public static final String ASSET_CATEGORY_TITLES = "assetCategoryTitles";

	public static final String ASSET_PARENT_CATEGORY_ID = "parentCategoryId";

	public static final String ASSET_PARENT_CATEGORY_IDS = "parentCategoryIds";

	public static final String ASSET_TAG_IDS = "assetTagIds";

	public static final String ASSET_TAG_NAMES = "assetTagNames";

	public static final String ASSET_VOCABULARY_ID = "assetVocabularyId";

	public static final String ASSET_VOCABULARY_IDS = "assetVocabularyIds";

	public static final String CAPTION = "caption";

	public static final String CATEGORY_ID = "categoryId";

	public static final String CLASS_NAME_ID = "classNameId";

	public static final String CLASS_PK = "classPK";

	public static final String CLASS_TYPE_ID = "classTypeId";

	public static final String COMMENTS = "comments";

	public static final String COMPANY_ID = "companyId";

	public static final String CONTENT = "content";

	public static final String CREATE_DATE = "createDate";

	public static final String DEFAULT_LANGUAGE_ID = "defaultLanguageId";

	public static final String DESCRIPTION = "description";

	public static final String ENTRY_CLASS_NAME = "entryClassName";

	public static final String ENTRY_CLASS_PK = "entryClassPK";

	public static final String EXPIRATION_DATE = "expirationDate";

	public static final String FOLDER_ID = "folderId";

	public static final String GEO_LOCATION = "geoLocation";

	public static final String GROUP_ID = "groupId";

	public static final String GROUP_ROLE_ID = "groupRoleId";

	public static final String HIDDEN = "hidden";

	public static final String KEYWORD_SEARCH = "keywordSearch";

	public static final String[] KEYWORDS = {
		FieldNames.ASSET_CATEGORY_TITLES, FieldNames.ASSET_TAG_NAMES,
		FieldNames.COMMENTS, FieldNames.CONTENT, FieldNames.DESCRIPTION,
		FieldNames.PROPERTIES, FieldNames.TITLE, FieldNames.URL,
		FieldNames.USER_NAME
	};

	public static final String LANGUAGE_ID = "languageId";

	public static final String LAYOUT_UUID = "layoutUuid";

	public static final String MODIFIED_DATE = "modified";

	public static final String NAME = "name";

	public static final String NODE_ID = "nodeId";

	public static final String ORGANIZATION_ID = "organizationId";

	public static final String PRIORITY = "priority";

	public static final String PROPERTIES = "properties";

	public static final String PUBLISH_DATE = "publishDate";

	public static final String RATINGS = "ratings";

	public static final String RELATED_ENTRY = "relatedEntry";

	public static final String REMOVED_BY_USER_NAME = "removedByUserName";

	public static final String REMOVED_DATE = "removedDate";

	public static final String ROLE_ID = "roleId";

	public static final String ROOT_ENTRY_CLASS_NAME = "rootEntryClassName";

	public static final String ROOT_ENTRY_CLASS_PK = "rootEntryClassPK";

	public static final String SCOPE_GROUP_ID = "scopeGroupId";

	public static final String SNIPPET = "snippet";

	public static final String SPELL_CHECK_WORD = "spellCheckWord";

	public static final String STAGING_GROUP = "stagingGroup";

	public static final String STATUS = "status";

	public static final String SUBTITLE = "subtitle";

	public static final String TITLE = "title";

	public static final String TREE_PATH = "treePath";

	public static final String TYPE = "type";

	public static final String UID = "uid";

	public static final String URL = "url";

	public static final String USER_GROUP_ID = "userGroupId";

	public static final String USER_ID = "userId";

	public static final String USER_NAME = "userName";

	public static final String VERSION = "version";

	public static final String VIEW_ACTION_ID = "viewActionId";

	public static final String VIEW_COUNT = "viewCount";

	public static final String[] UNSCORED_FieldNames_NAMES = {
		FieldNames.ASSET_CATEGORY_IDS, FieldNames.COMPANY_ID,
		FieldNames.ENTRY_CLASS_NAME, FieldNames.ENTRY_CLASS_PK,
		FieldNames.FOLDER_ID, FieldNames.GROUP_ID, FieldNames.GROUP_ROLE_ID,
		FieldNames.ROLE_ID, FieldNames.SCOPE_GROUP_ID, FieldNames.USER_ID
	};

}