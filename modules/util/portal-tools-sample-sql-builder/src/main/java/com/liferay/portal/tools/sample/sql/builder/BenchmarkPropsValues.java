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

package com.liferay.portal.tools.sample.sql.builder;

import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Lily Chi
 */
public class BenchmarkPropsValues {

	public static final DBType DB_TYPE = DBType.valueOf(
		StringUtil.toUpperCase(
			BenchmarkPropsUtil.get(BenchmarkPropsKeys.DB_TYPE)));

	public static final int MAX_ASSET_CATEGORY_COUNT = GetterUtil.getInteger(
		BenchmarkPropsUtil.get(BenchmarkPropsKeys.MAX_ASSET_CATEGORY_COUNT));

	public static final int MAX_ASSET_ENTRY_TO_ASSET_CATEGORY_COUNT =
		GetterUtil.getInteger(
			BenchmarkPropsUtil.get(
				BenchmarkPropsKeys.MAX_ASSET_ENTRY_TO_ASSET_CATEGORY_COUNT));

	public static final int MAX_ASSET_ENTRY_TO_ASSET_TAG_COUNT =
		GetterUtil.getInteger(
			BenchmarkPropsUtil.get(
				BenchmarkPropsKeys.MAX_ASSET_ENTRY_TO_ASSET_TAG_COUNT));

	public static final int MAX_ASSET_TAG_COUNT = GetterUtil.getInteger(
		BenchmarkPropsUtil.get(BenchmarkPropsKeys.MAX_ASSET_TAG_COUNT));

	public static final int MAX_ASSET_VUCABULARY_COUNT = GetterUtil.getInteger(
		BenchmarkPropsUtil.get(BenchmarkPropsKeys.MAX_ASSET_VUCABULARY_COUNT));

	public static final int MAX_ASSETPUBLISHER_PAGE_COUNT =
		GetterUtil.getInteger(
			BenchmarkPropsUtil.get(
				BenchmarkPropsKeys.MAX_ASSETPUBLISHER_PAGE_COUNT));

	public static final int MAX_BLOGS_ENTRY_COMMENT_COUNT =
		GetterUtil.getInteger(
			BenchmarkPropsUtil.get(
				BenchmarkPropsKeys.MAX_BLOGS_ENTRY_COMMENT_COUNT));

	public static final int MAX_BLOGS_ENTRY_COUNT = GetterUtil.getInteger(
		BenchmarkPropsUtil.get(BenchmarkPropsKeys.MAX_BLOGS_ENTRY_COUNT));

	public static final int MAX_COMMERCE_PRODUCT_COUNT = GetterUtil.getInteger(
		BenchmarkPropsUtil.get(BenchmarkPropsKeys.MAX_COMMERCE_PRODUCT_COUNT));

	public static final int MAX_COMMERCE_PRODUCT_DEFINITION_COUNT =
		GetterUtil.getInteger(
			BenchmarkPropsUtil.get(
				BenchmarkPropsKeys.MAX_COMMERCE_PRODUCT_DEFINITION_COUNT));

	public static final int MAX_COMMERCE_PRODUCT_INSTANCE_COUNT =
		GetterUtil.getInteger(
			BenchmarkPropsUtil.get(
				BenchmarkPropsKeys.MAX_COMMERCE_PRODUCT_INSTANCE_COUNT));

	public static final int MAX_CONTENT_LAYOUT_COUNT = GetterUtil.getInteger(
		BenchmarkPropsUtil.get(BenchmarkPropsKeys.MAX_CONTENT_LAYOUT_COUNT));

	public static final int MAX_DDL_CUSTOM_FIELD_COUNT = GetterUtil.getInteger(
		BenchmarkPropsUtil.get(BenchmarkPropsKeys.MAX_DDL_CUSTOM_FIELD_COUNT));

	public static final int MAX_DDL_RECORD_COUNT = GetterUtil.getInteger(
		BenchmarkPropsUtil.get(BenchmarkPropsKeys.MAX_DDL_RECORD_COUNT));

	public static final int MAX_DDL_RECORD_SET_COUNT = GetterUtil.getInteger(
		BenchmarkPropsUtil.get(BenchmarkPropsKeys.MAX_DDL_RECORD_SET_COUNT));

	public static final int MAX_DL_FILE_ENTRY_COUNT = GetterUtil.getInteger(
		BenchmarkPropsUtil.get(BenchmarkPropsKeys.MAX_DL_FILE_ENTRY_COUNT));

	public static final int MAX_DL_FILE_ENTRY_SIZE = GetterUtil.getInteger(
		BenchmarkPropsUtil.get(BenchmarkPropsKeys.MAX_DL_FILE_ENTRY_SIZE));

	public static final int MAX_DL_FOLDER_COUNT = GetterUtil.getInteger(
		BenchmarkPropsUtil.get(BenchmarkPropsKeys.MAX_DL_FOLDER_COUNT));

	public static final int MAX_DL_FOLDER_DEPTH = GetterUtil.getInteger(
		BenchmarkPropsUtil.get(BenchmarkPropsKeys.MAX_DL_FOLDER_DEPTH));

	public static final int MAX_GROUP_COUNT = GetterUtil.getInteger(
		BenchmarkPropsUtil.get(BenchmarkPropsKeys.MAX_GROUP_COUNT));

	public static final int MAX_JOURNAL_ARTICLE_COUNT = GetterUtil.getInteger(
		BenchmarkPropsUtil.get(BenchmarkPropsKeys.MAX_JOURNAL_ARTICLE_COUNT));

	public static final int MAX_JOURNAL_ARTICLE_PAGE_COUNT =
		GetterUtil.getInteger(
			BenchmarkPropsUtil.get(
				BenchmarkPropsKeys.MAX_JOURNAL_ARTICLE_PAGE_COUNT));

	public static final int MAX_JOURNAL_ARTICLE_SIZE = GetterUtil.getInteger(
		BenchmarkPropsUtil.get(BenchmarkPropsKeys.MAX_JOURNAL_ARTICLE_SIZE));

	public static final int MAX_JOURNAL_ARTICLE_VERSION_COUNT =
		GetterUtil.getInteger(
			BenchmarkPropsUtil.get(
				BenchmarkPropsKeys.MAX_JOURNAL_ARTICLE_VERSION_COUNT));

	public static final int MAX_MB_CATEGORY_COUNT = GetterUtil.getInteger(
		BenchmarkPropsUtil.get(BenchmarkPropsKeys.MAX_MB_CATEGORY_COUNT));

	public static final int MAX_MB_MESSAGE_COUNT = GetterUtil.getInteger(
		BenchmarkPropsUtil.get(BenchmarkPropsKeys.MAX_MB_MESSAGE_COUNT));

	public static final int MAX_MB_THREAD_COUNT = GetterUtil.getInteger(
		BenchmarkPropsUtil.get(BenchmarkPropsKeys.MAX_MB_THREAD_COUNT));

	public static final int MAX_USER_COUNT = GetterUtil.getInteger(
		BenchmarkPropsUtil.get(BenchmarkPropsKeys.MAX_USER_COUNT));

	public static final int MAX_USER_TO_GROUP_COUNT = GetterUtil.getInteger(
		BenchmarkPropsUtil.get(BenchmarkPropsKeys.MAX_USER_TO_GROUP_COUNT));

	public static final int MAX_WIKI_NODE_COUNT = GetterUtil.getInteger(
		BenchmarkPropsUtil.get(BenchmarkPropsKeys.MAX_WIKI_NODE_COUNT));

	public static final int MAX_WIKI_PAGE_COMMENT_COUNT = GetterUtil.getInteger(
		BenchmarkPropsUtil.get(BenchmarkPropsKeys.MAX_WIKI_PAGE_COMMENT_COUNT));

	public static final int MAX_WIKI_PAGE_COUNT = GetterUtil.getInteger(
		BenchmarkPropsUtil.get(BenchmarkPropsKeys.MAX_WIKI_PAGE_COUNT));

	public static final int OPTIMIZE_BUFFER_SIZE = GetterUtil.getInteger(
		BenchmarkPropsUtil.get(BenchmarkPropsKeys.OPTIMIZE_BUFFER_SIZE));

	public static final String[] OUTPUT_CSV_FILE_NAMES = StringUtil.split(
		BenchmarkPropsUtil.get(BenchmarkPropsKeys.OUTPUT_CSV_FILE_NAMES));

	public static final String OUTPUT_DIR = BenchmarkPropsUtil.get(
		BenchmarkPropsKeys.OUTPUT_DIR);

	public static final boolean OUTPUT_MERGE = GetterUtil.getBoolean(
		BenchmarkPropsUtil.get(BenchmarkPropsKeys.OUTPUT_MERGE));

	public static final String SCRIPT = BenchmarkPropsUtil.get(
		BenchmarkPropsKeys.SCRIPT);

	public static final String VIRTUAL_HOST_NAME = BenchmarkPropsUtil.get(
		BenchmarkPropsKeys.VIRTUAL_HOST_NAME);

}