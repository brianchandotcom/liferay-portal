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

package com.liferay.journal.configuration;

import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Eduardo Garcia
 */
public class JournalServiceConfigurationValues {

	public static final String[] CHAR_BLACKLIST =
		JournalServiceConfigurationUtil.getArray(
			JournalServiceConfigurationKeys.CHAR_BLACKLIST);

	public static final String EMAIL_ARTICLE_ADDED_BODY =
		JournalServiceConfigurationUtil.get(
			JournalServiceConfigurationKeys.EMAIL_ARTICLE_ADDED_BODY);

	public static final boolean EMAIL_ARTICLE_ADDED_ENABLED =
		GetterUtil.getBoolean(
			JournalServiceConfigurationUtil.get(
				JournalServiceConfigurationKeys.EMAIL_ARTICLE_ADDED_ENABLED));

	public static final String EMAIL_ARTICLE_ADDED_SUBJECT =
		JournalServiceConfigurationUtil.get(
			JournalServiceConfigurationKeys.EMAIL_ARTICLE_ADDED_SUBJECT);

	public static final String EMAIL_ARTICLE_APPROVAL_DENIED_BODY =
		JournalServiceConfigurationUtil.get(
			JournalServiceConfigurationKeys.EMAIL_ARTICLE_APPROVAL_DENIED_BODY);

	public static final boolean EMAIL_ARTICLE_APPROVAL_DENIED_ENABLED =
		GetterUtil.getBoolean(
			JournalServiceConfigurationUtil.get(
				JournalServiceConfigurationKeys.
					EMAIL_ARTICLE_APPROVAL_DENIED_ENABLED));

	public static final String EMAIL_ARTICLE_APPROVAL_DENIED_SUBJECT =
		JournalServiceConfigurationUtil.get(
			JournalServiceConfigurationKeys.
				EMAIL_ARTICLE_APPROVAL_DENIED_SUBJECT);

	public static final String EMAIL_ARTICLE_APPROVAL_GRANTED_BODY =
		JournalServiceConfigurationUtil.get(
			JournalServiceConfigurationKeys.
				EMAIL_ARTICLE_APPROVAL_GRANTED_BODY);

	public static final boolean EMAIL_ARTICLE_APPROVAL_GRANTED_ENABLED =
		GetterUtil.getBoolean(
			JournalServiceConfigurationUtil.get(
				JournalServiceConfigurationKeys.
					EMAIL_ARTICLE_APPROVAL_GRANTED_ENABLED));

	public static final String EMAIL_ARTICLE_APPROVAL_GRANTED_SUBJECT =
		JournalServiceConfigurationUtil.get(
			JournalServiceConfigurationKeys.
				EMAIL_ARTICLE_APPROVAL_GRANTED_SUBJECT);

	public static final String EMAIL_ARTICLE_APPROVAL_REQUESTED_BODY =
		JournalServiceConfigurationUtil.get(
			JournalServiceConfigurationKeys.
				EMAIL_ARTICLE_APPROVAL_REQUESTED_BODY);

	public static final boolean EMAIL_ARTICLE_APPROVAL_REQUESTED_ENABLED =
		GetterUtil.getBoolean(
			JournalServiceConfigurationUtil.get(
				JournalServiceConfigurationKeys.
					EMAIL_ARTICLE_APPROVAL_REQUESTED_ENABLED));

	public static final String EMAIL_ARTICLE_APPROVAL_REQUESTED_SUBJECT =
		JournalServiceConfigurationUtil.get(
			JournalServiceConfigurationKeys.
				EMAIL_ARTICLE_APPROVAL_REQUESTED_SUBJECT);

	public static final String EMAIL_ARTICLE_MOVED_FROM_FOLDER_BODY =
		JournalServiceConfigurationUtil.get(
			JournalServiceConfigurationKeys.
				EMAIL_ARTICLE_MOVED_FROM_FOLDER_BODY);

	public static final boolean EMAIL_ARTICLE_MOVED_FROM_FOLDER_ENABLED =
		GetterUtil.getBoolean(
			JournalServiceConfigurationUtil.get(
				JournalServiceConfigurationKeys.
					EMAIL_ARTICLE_MOVED_FROM_FOLDER_ENABLED));

	public static final String EMAIL_ARTICLE_MOVED_FROM_FOLDER_SUBJECT =
		JournalServiceConfigurationUtil.get(
			JournalServiceConfigurationKeys.
				EMAIL_ARTICLE_MOVED_FROM_FOLDER_SUBJECT);

	public static final String EMAIL_ARTICLE_MOVED_TO_FOLDER_BODY =
		JournalServiceConfigurationUtil.get(
			JournalServiceConfigurationKeys.EMAIL_ARTICLE_MOVED_TO_FOLDER_BODY);

	public static final boolean EMAIL_ARTICLE_MOVED_TO_FOLDER_ENABLED =
		GetterUtil.getBoolean(
			JournalServiceConfigurationUtil.get(
				JournalServiceConfigurationKeys.
					EMAIL_ARTICLE_MOVED_TO_FOLDER_ENABLED));

	public static final String EMAIL_ARTICLE_MOVED_TO_FOLDER_SUBJECT =
		JournalServiceConfigurationUtil.get(
			JournalServiceConfigurationKeys.
				EMAIL_ARTICLE_MOVED_TO_FOLDER_SUBJECT);

	public static final String EMAIL_ARTICLE_REVIEW_BODY =
		JournalServiceConfigurationUtil.get(
			JournalServiceConfigurationKeys.EMAIL_ARTICLE_REVIEW_BODY);

	public static final boolean EMAIL_ARTICLE_REVIEW_ENABLED =
		GetterUtil.getBoolean(
			JournalServiceConfigurationUtil.get(
				JournalServiceConfigurationKeys.EMAIL_ARTICLE_REVIEW_ENABLED));

	public static final String EMAIL_ARTICLE_REVIEW_SUBJECT =
		JournalServiceConfigurationUtil.get(
			JournalServiceConfigurationKeys.EMAIL_ARTICLE_REVIEW_SUBJECT);

	public static final String EMAIL_ARTICLE_UPDATED_BODY =
		JournalServiceConfigurationUtil.get(
			JournalServiceConfigurationKeys.EMAIL_ARTICLE_UPDATED_BODY);

	public static final boolean EMAIL_ARTICLE_UPDATED_ENABLED =
		GetterUtil.getBoolean(
			JournalServiceConfigurationUtil.get(
				JournalServiceConfigurationKeys.EMAIL_ARTICLE_UPDATED_ENABLED));

	public static final String EMAIL_ARTICLE_UPDATED_SUBJECT =
		JournalServiceConfigurationUtil.get(
			JournalServiceConfigurationKeys.EMAIL_ARTICLE_UPDATED_SUBJECT);

	public static final String EMAIL_FROM_ADDRESS =
		JournalServiceConfigurationUtil.get(
			JournalServiceConfigurationKeys.EMAIL_FROM_ADDRESS);

	public static final String EMAIL_FROM_NAME =
		JournalServiceConfigurationUtil.get(
			JournalServiceConfigurationKeys.EMAIL_FROM_NAME);

	public static final int JOURNAL_ARTICLE_CHECK_INTERVAL =
		GetterUtil.getInteger(
			JournalServiceConfigurationUtil.get(
				JournalServiceConfigurationKeys.
					JOURNAL_ARTICLE_CHECK_INTERVAL));

	public static final boolean JOURNAL_ARTICLE_COMMENTS_ENABLED =
		GetterUtil.getBoolean(
			JournalServiceConfigurationUtil.get(
				JournalServiceConfigurationKeys.
					JOURNAL_ARTICLE_COMMENTS_ENABLED));

	public static final String[] JOURNAL_ARTICLE_CUSTOM_TOKENS =
		JournalServiceConfigurationUtil.getArray(
			JournalServiceConfigurationKeys.JOURNAL_ARTICLE_CUSTOM_TOKENS);

	public static boolean JOURNAL_ARTICLE_DATABASE_KEYWORD_SEARCH_CONTENT =
		GetterUtil.getBoolean(
			JournalServiceConfigurationUtil.get(
				JournalServiceConfigurationKeys.
					JOURNAL_ARTICLE_DATABASE_KEYWORD_SEARCH_CONTENT));

	public static final boolean JOURNAL_ARTICLE_EXPIRE_ALL_VERSIONS =
		GetterUtil.getBoolean(
			JournalServiceConfigurationUtil.get(
				JournalServiceConfigurationKeys.
					JOURNAL_ARTICLE_EXPIRE_ALL_VERSIONS));

	public static boolean JOURNAL_ARTICLE_INDEX_ALL_VERSIONS =
		GetterUtil.getBoolean(
			JournalServiceConfigurationUtil.get(
				JournalServiceConfigurationKeys.
					JOURNAL_ARTICLE_INDEX_ALL_VERSIONS));

	public static final String JOURNAL_ARTICLE_STORAGE_TYPE =
		JournalServiceConfigurationUtil.get(
			JournalServiceConfigurationKeys.JOURNAL_ARTICLE_STORAGE_TYPE);

	public static final String JOURNAL_ARTICLE_TOKEN_PAGE_BREAK =
		JournalServiceConfigurationUtil.get(
			JournalServiceConfigurationKeys.JOURNAL_ARTICLE_TOKEN_PAGE_BREAK);

	public static final boolean JOURNAL_ARTICLE_VIEW_PERMISSION_CHECK_ENABLED =
		GetterUtil.getBoolean(
			JournalServiceConfigurationUtil.get(
				JournalServiceConfigurationKeys.
					JOURNAL_ARTICLE_VIEW_PERMISSION_CHECK_ENABLED));

	public static final boolean JOURNAL_FOLDER_ICON_CHECK_COUNT =
		GetterUtil.getBoolean(
			JournalServiceConfigurationUtil.get(
				JournalServiceConfigurationKeys.
					JOURNAL_FOLDER_ICON_CHECK_COUNT));

	public static final String LAR_CREATION_STRATEGY =
		JournalServiceConfigurationUtil.get(
			JournalServiceConfigurationKeys.LAR_CREATION_STRATEGY);

	public static final boolean SYNC_CONTENT_SEARCH_ON_STARTUP =
		GetterUtil.getBoolean(
			JournalServiceConfigurationKeys.SYNC_CONTENT_SEARCH_ON_STARTUP);

	public static final long TERMS_OF_USE_JOURNAL_ARTICLE_GROUP_ID =
		GetterUtil.getLong(
			JournalServiceConfigurationUtil.get(
				JournalServiceConfigurationKeys.
					TERMS_OF_USE_JOURNAL_ARTICLE_GROUP_ID));

	public static final String TERMS_OF_USE_JOURNAL_ARTICLE_ID =
		JournalServiceConfigurationUtil.get(
			JournalServiceConfigurationKeys.TERMS_OF_USE_JOURNAL_ARTICLE_ID);

}