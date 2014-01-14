/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.util.ArrayUtil;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

/**
 * @author Bruno Farache
 * @author Brian Wing Shun Chan
 * @author Allen Chiang
 * @author Alex Wallace
 */
public class Field implements Serializable {

	public static final String ARTICLE_ID = "articleId";

	public static final String ASSET_CATEGORY_IDS = "assetCategoryIds";

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #ASSET_CATEGORY_TITLES}
	 */
	@Deprecated
	public static final String ASSET_CATEGORY_NAMES = "assetCategoryNames";

	public static final String ASSET_CATEGORY_TITLES = "assetCategoryTitles";

	public static final String ASSET_TAG_IDS = "assetTagIds";

	public static final String ASSET_TAG_NAMES = "assetTagNames";

	public static final String CATEGORY_ID = "categoryId";

	public static final String CLASS_NAME_ID = "classNameId";

	public static final String CLASS_PK = "classPK";

	public static final String CLASS_TYPE_ID = "classTypeId";

	public static final String COMMENTS = "comments";

	public static final String COMPANY_ID = "companyId";

	public static final String CONTENT = "content";

	public static final String CREATE_DATE = "createDate";

	public static final String DESCRIPTION = "description";

	public static final String ENTRY_CLASS_NAME = "entryClassName";

	public static final String ENTRY_CLASS_PK = "entryClassPK";

	public static final String EXPIRATION_DATE = "expirationDate";

	public static final String FOLDER_ID = "folderId";

	public static final String GROUP_ID = "groupId";

	public static final String GROUP_ROLE_ID = "groupRoleId";

	public static final String HIDDEN = "hidden";

	public static final String KEYWORD_SEARCH = "keywordSearch";

	public static final String[] KEYWORDS = {
		Field.ASSET_CATEGORY_TITLES, Field.ASSET_TAG_NAMES, Field.COMMENTS,
		Field.CONTENT, Field.DESCRIPTION, Field.PROPERTIES, Field.TITLE,
		Field.URL, Field.USER_NAME
	};

	public static final String LANGUAGE_ID = "languageId";

	public static final String LAYOUT_UUID = "layoutUuid";

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #MODIFIED_DATE}
	 */
	@Deprecated
	public static final String MODIFIED = "modified";

	public static final String MODIFIED_DATE = "modified";

	public static final String NAME = "name";

	public static final String NODE_ID = "nodeId";

	public static final String ORGANIZATION_ID = "organizationId";

	public static final String PORTLET_ID = "portletId";

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

	public static final String TITLE = "title";

	public static final String TREE_PATH = "treePath";

	public static final String TYPE = "type";

	public static final String UID = "uid";

	public static final String[] UNSCORED_FIELD_NAMES = {
		Field.ASSET_CATEGORY_IDS, Field.COMPANY_ID, Field.ENTRY_CLASS_NAME,
		Field.ENTRY_CLASS_PK, Field.FOLDER_ID, Field.GROUP_ID,
		Field.GROUP_ROLE_ID, Field.PORTLET_ID, Field.ROLE_ID,
		Field.SCOPE_GROUP_ID, Field.USER_ID
	};

	public static final String URL = "url";

	public static final String USER_GROUP_ID = "userGroupId";

	public static final String USER_ID = "userId";

	public static final String USER_NAME = "userName";

	public static final String VERSION = "version";

	public static final String VIEW_COUNT = "viewCount";

	public Field(String name, Map<Locale, String> localizedValues) {
		_name = name;
		_localizedValues = localizedValues;
	}

	public Field(String name, String value) {
		this(name, new String[] {value});
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	@Deprecated
	public Field(String name, String value, boolean tokenized) {
		this(name, value);

		setTokenized(tokenized);
	}

	public Field(String name, String[] values) {
		_name = name;
		_values = values;
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	@Deprecated
	public Field(String name, String[] values, boolean tokenized) {
		this(name, values);

		setTokenized(tokenized);
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	@Deprecated
	public Field(String name, String[] values, boolean tokenized, float boost) {
		this(name, values);

		setBoost(boost);
		setTokenized(tokenized);
	}

	public float getBoost() {
		return _boost;
	}

	public Map<Locale, String> getLocalizedValues() {
		return _localizedValues;
	}

	public String getName() {
		return _name;
	}

	public Class<? extends Number> getNumericClass() {
		return _numericClass;
	}

	public String getValue() {
		if (ArrayUtil.isNotEmpty(_values)) {
			return _values[0];
		}
		else {
			return null;
		}
	}

	public String[] getValues() {
		return _values;
	}

	public boolean isLocalized() {
		if (_localizedValues != null) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isNumeric() {
		return _numeric;
	}

	public boolean isTokenized() {
		return _tokenized;
	}

	public void setBoost(float boost) {
		_boost = boost;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setNumeric(boolean numeric) {
		_numeric = numeric;
	}

	public void setNumericClass(Class<? extends Number> numericClass) {
		_numericClass = numericClass;
	}

	public void setTokenized(boolean tokenized) {
		_tokenized = tokenized;
	}

	public void setValue(String value) {
		setValues(new String[] {value});
	}

	public void setValues(String[] values) {
		_values = values;
	}

	private float _boost = 1;
	private Map<Locale, String> _localizedValues;
	private String _name;
	private boolean _numeric;
	private Class<? extends Number> _numericClass;
	private boolean _tokenized;
	private String[] _values;

}