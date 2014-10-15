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

package com.liferay.portal.util;

import com.liferay.portal.kernel.resiliency.spi.agent.annotation.Direction;
import com.liferay.portal.kernel.resiliency.spi.agent.annotation.Distributed;
import com.liferay.portal.kernel.resiliency.spi.agent.annotation.MatchType;

/**
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 */
public class WebKeys implements com.liferay.portal.kernel.util.WebKeys {

	public static final String ADDRESS = "ADDRESS";

	@Distributed
	public static final String ANNOUNCEMENTS_ENTRY = "LIFERAY_SHARED_ANNOUNCEMENTS_ENTRY";

	public static final String ASSET_CATEGORY = "ASSET_CATEGORY";

	public static final String ASSET_LAYOUT_TAG_NAMES = "ASSET_LAYOUT_TAG_NAMES";

	public static final String ASSET_PUBLISHER_ABSTRACT_LENGTH = "ASSET_PUBLISHER_ABSTRACT_LENGTH";

	public static final String ASSET_PUBLISHER_VIEW_URL = "ASSET_PUBLISHER_VIEW_URL";

	public static final String ASSET_TAG = "ASSET_TAG";

	public static final String ASSET_VOCABULARIES = "ASSET_VOCABULARIES";

	public static final String ASSET_VOCABULARY = "ASSET_VOCABULARY";

	public static final String AVAILABLE_LOCALES = "AVAILABLE_LOCALES";

	public static final String BASIC_AUTH_ENABLED = "BASIC_AUTH_ENABLED";

	public static final String BLOGS_CATEGORY = "BLOGS_CATEGORY";

	public static final String BLOGS_ENTRY = "BLOGS_ENTRY";

	public static final String CACHE_PORTLET_RESPONSES = "CACHE_PORTLET_RESPONSES";

	public static final String CALENDAR_EVENT = "CALENDAR_EVENT";

	public static final String CAPTCHA_COUNT = "CAPTCHA_COUNT";

	public static final String CAPTCHA_TEXT = "CAPTCHA_TEXT";

	public static final String CAS_FORCE_LOGOUT = "CAS_FORCE_LOGOUT";

	public static final String CAS_LOGIN = "CAS_LOGIN";

	public static final String CAS_NO_SUCH_USER_EXCEPTION = "CAS_NO_SUCH_USER_EXCEPTION";

	public static final String COLOR_SCHEME = "COLOR_SCHEME";

	public static final String COMIC_ENTRY = "COMIC_ENTRY";

	public static final String COMPANY = "COMPANY";

	public static final String COMPANY_ID = "COMPANY_ID";

	public static final String CONFIGURATION_ACTION_PATH = "CONFIGURATION_ACTION_PATH";

	public static final String CONTROL_PANEL_CATEGORIES_MAP = "CONTROL_PANEL_CATEGORIES_MAP";

	public static final String DEVICE = "DEVICE";

	public static final String DIFF_HTML_RESULTS = "DIFF_HTML_RESULTS";

	public static final String DIFF_RESULTS = "DIFF_RESULTS";

	public static final String DIFF_VERSION = "DIFF_VERSION";

	public static final String DOCUMENT_LIBRARY_FILE_ENTRIES = "DOCUMENT_LIBRARY_FILE_ENTRIES";

	public static final String DOCUMENT_LIBRARY_FILE_ENTRY = "DOCUMENT_LIBRARY_FILE_ENTRY";

	public static final String DOCUMENT_LIBRARY_FILE_ENTRY_TYPE = "DOCUMENT_LIBRARY_FILE_ENTRY_TYPE";

	public static final String DOCUMENT_LIBRARY_FILE_SHORTCUT = "DOCUMENT_LIBRARY_FILE_SHORTCUT";

	public static final String DOCUMENT_LIBRARY_FILE_SHORTCUTS = "DOCUMENT_LIBRARY_FILE_SHORTCUTS";

	public static final String DOCUMENT_LIBRARY_FILE_VERSION = "DOCUMENT_LIBRARY_FILE_VERSION";

	public static final String DOCUMENT_LIBRARY_FOLDER = "DOCUMENT_LIBRARY_FOLDER";

	public static final String DOCUMENT_LIBRARY_FOLDERS = "DOCUMENT_LIBRARY_FOLDERS";

	public static final String DOCUMENT_LIBRARY_REPOSITORY = "DOCUMENT_LIBRARY_REPOSITORY";

	public static final String DYNAMIC_DATA_LISTS_RECORD = "DYNAMIC_DATA_LISTS_RECORD";

	public static final String DYNAMIC_DATA_LISTS_RECORD_SET = "DYNAMIC_DATA_LISTS_RECORD_SET";

	public static final String DYNAMIC_DATA_LISTS_RECORD_VERSION = "DYNAMIC_DATA_LISTS_RECORD_VERSION";

	public static final String DYNAMIC_DATA_MAPPING_STRUCTURE = "DYNAMIC_DATA_MAPPING_STRUCTURE";

	public static final String DYNAMIC_DATA_MAPPING_TEMPLATE = "DYNAMIC_DATA_MAPPING_TEMPLATE";

	public static final String EMAIL_ADDRESS = "EMAIL_ADDRESS";

	public static final String ENCRYPT = "shuo";

	public static final String EXPANDO_COLUMN = "EXPANDO_COLUMN";

	public static final String EXPORT_IMPORT_CONFIGURATION_ID = "EXPORT_IMPORT_CONFIGURATION_ID";

	public static final String FACEBOOK_ACCESS_TOKEN = "FACEBOOK_ACCESS_TOKEN";

	public static final String FACEBOOK_CANVAS_PAGE_URL = "FACEBOOK_CANVAS_PAGE_URL";

	public static final String FACEBOOK_INCOMPLETE_USER_ID = "FACEBOOK_INCOMPLETE_USER_ID";

	public static final String FACEBOOK_USER_EMAIL_ADDRESS = "FACEBOOK_USER_EMAIL_ADDRESS";

	public static final String FACEBOOK_USER_ID = "FACEBOOK_USER_ID";

	public static final String FORM_NAME = "FORM_NAME";

	public static final String FORM_NAVIGATOR_SECTION_SHOW = "FORM_NAVIGATOR_SECTION_SHOW_";

	public static final String FORWARD_URL = "FORWARD_URL";

	public static final String FRIENDLY_URL = "FRIENDLY_URL";

	public static final String FTL_VARIABLES = "FTL_VARIABLES";

	public static final String GOOGLE_GADGET = "GOOGLE_GADGET";

	public static final String HTTPS_INITIAL = "HTTPS_INITIAL";

	public static final String I18N_LANGUAGE_ID = "I18N_LANGUAGE_ID";

	public static final String I18N_PATH = "I18N_PATH";

	public static final String JOURNAL_ARTICLE_CONTENT = "JOURNAL_ARTICLE_CONTENT";

	public static final String JOURNAL_ARTICLE_CONTENT_EL = "JOURNAL_ARTICLE_CONTENT_EL";

	public static final String JOURNAL_ARTICLE_GROUP_ID = "JOURNAL_ARTICLE_GROUP_ID";

	public static final String JOURNAL_ARTICLES = "JOURNAL_ARTICLES";

	public static final String JOURNAL_FEED = "JOURNAL_FEED";

	public static final String JOURNAL_FOLDER = "JOURNAL_FOLDER";

	public static final String JOURNAL_FOLDERS = "JOURNAL_FOLDERS";

	public static final String JOURNAL_RECENT_ARTICLES = "JOURNAL_RECENT_ARTICLES";

	public static final String JOURNAL_RECENT_DYNAMIC_DATA_MAPPING_STRUCTURES = "JOURNAL_RECENT_DYNAMIC_DATA_MAPPING_STRUCTURES";

	public static final String JOURNAL_RECENT_DYNAMIC_DATA_MAPPING_TEMPLATES = "JOURNAL_RECENT_DYNAMIC_DATA_MAPPING_TEMPLATES";

	public static final String JOURNAL_STRUCTURE = "JOURNAL_STRUCTURE";

	public static final String JOURNAL_STRUCTURE_CLOSE_DROPPABLE_TAG = "JOURNAL_STRUCTURE_CLOSE_DROPPABLE_TAG";

	public static final String JOURNAL_STRUCTURE_EL = "JOURNAL_STRUCTURE_EL";

	public static final String JOURNAL_STRUCTURE_EL_CONTENT = "JOURNAL_STRUCTURE_EL_CONTENT";

	public static final String JOURNAL_STRUCTURE_EL_COUNT = "JOURNAL_STRUCTURE_EL_COUNT";

	public static final String JOURNAL_STRUCTURE_EL_DEPTH = "JOURNAL_STRUCTURE_EL_DEPTH";

	public static final String JOURNAL_STRUCTURE_EL_INDEX_TYPE = "JOURNAL_STRUCTURE_EL_INDEX_TYPE";

	public static final String JOURNAL_STRUCTURE_EL_INSTANCE_ID = "JOURNAL_STRUCTURE_EL_INSTANCE_ID";

	public static final String JOURNAL_STRUCTURE_EL_LANGUAGE_ID = "JOURNAL_STRUCTURE_EL_LANGUAGE_ID";

	public static final String JOURNAL_STRUCTURE_EL_META_DATA = "JOURNAL_STRUCTURE_EL_META_DATA";

	public static final String JOURNAL_STRUCTURE_EL_NAME = "JOURNAL_STRUCTURE_EL_NAME";

	public static final String JOURNAL_STRUCTURE_EL_PARENT_ID = "JOURNAL_STRUCTURE_EL_PARENT_ID";

	public static final String JOURNAL_STRUCTURE_EL_REPEAT_COUNT_MAP = "JOURNAL_STRUCTURE_EL_REPEAT_COUNT_MAP";

	public static final String JOURNAL_STRUCTURE_EL_REPEATABLE = "JOURNAL_STRUCTURE_EL_REPEATABLE";

	public static final String JOURNAL_STRUCTURE_EL_REPEATABLE_PROTOTYPE = "JOURNAL_STRUCTURE_EL_REPEATABLE_PROTOTYPE";

	public static final String JOURNAL_STRUCTURE_EL_SIBLINGS = "JOURNAL_STRUCTURE_EL_SIBLINGS";

	public static final String JOURNAL_STRUCTURE_EL_TYPE = "JOURNAL_STRUCTURE_EL_TYPE";

	public static final String JOURNAL_TEMPLATE = "JOURNAL_TEMPLATE";

	public static final String JOURNAL_TEMPLATE_ID = "JOURNAL_TEMPLATE_ID";

	public static final String LANGUAGE_ID = "LANGUAGE_ID";

	@Distributed
	public static final String LAYOUT_ASSET_ENTRY = "LIFERAY_SHARED_LAYOUT_ASSET_ENTRY";

	public static final String LAYOUT_CONTENT = "LAYOUT_CONTENT";

	public static final String LAYOUT_DEFAULT = "LAYOUT_DEFAULT";

	public static final String LAYOUT_DESCRIPTIONS = "LAYOUT_DESCRIPTIONS";

	public static final String LAYOUT_PORTLETS = "LAYOUT_PORTLETS";

	public static final String LAYOUT_PROTOTYPE = "LAYOUT_PROTOTYPE";

	public static final String LAYOUT_REVISION = "LAYOUT_REVISION";

	public static final String LOGOUT = "LOGOUT";

	public static final String MAIL_CURRENT_ACCOUNT = "MAIL_CURRENT_ACCOUNT";

	public static final String MAIL_FOLDER = "MAIL_FOLDER";

	public static final String MAIL_MESSAGE_ATTACHMENTS = "MAIL_MESSAGE_ATTACHMENTS";

	public static final String MAIL_MESSAGE_BODY = "MAIL_MESSAGE_BODY";

	public static final String MAIL_MESSAGE_ID = "MAIL_MESSAGE_ID";

	public static final String MAIL_MESSAGE_IN_REPLY_TO = "MAIL_MESSAGE_IN_REPLY_TO";

	public static final String MAIL_MESSAGE_ORIGINAL_ID = "MAIL_MESSAGE_ORIGINAL_ID";

	public static final String MAIL_MESSAGE_RECIPIENTS = "MAIL_MESSAGE_RECIPIENTS";

	public static final String MAIL_MESSAGE_REFERENCES = "MAIL_MESSAGE_REFERENCES";

	public static final String MAIL_MESSAGE_SUBJECT = "MAIL_MESSAGE_SUBJECT";

	public static final String MAIL_SEARCH_RESULTS = "MAIL_SEARCH_RESULTS";

	public static final String MAIL_STORE = "MAIL_STORE";

	public static final String MEMBERSHIP_REQUEST = "MEMBERSHIP_REQUEST";

	public static final String MESSAGE_BOARDS_CATEGORY = "MESSAGE_BOARDS_CATEGORY";

	public static final String MESSAGE_BOARDS_MESSAGE = "MESSAGE_BOARDS_MESSAGE";

	public static final String MESSAGE_BOARDS_TREE_WALKER = "MESSAGE_BOARDS_TREE_WALKER";

	public static final String MESSAGE_BOARDS_TREE_WALKER_CATEGORY = "MESSAGE_BOARDS_TREE_WALKER_CATEGORY";

	public static final String MESSAGE_BOARDS_TREE_WALKER_CUR_MESSAGE = "MESSAGE_BOARDS_TREE_WALKER_CUR_MESSAGE";

	public static final String MESSAGE_BOARDS_TREE_WALKER_DEPTH = "MESSAGE_BOARDS_TREE_WALKER_DEPTH";

	public static final String MESSAGE_BOARDS_TREE_WALKER_LAST_NODE = "MESSAGE_BOARDS_TREE_WALKER_LAST_NODE";

	public static final String MESSAGE_BOARDS_TREE_WALKER_SEL_MESSAGE = "MESSAGE_BOARDS_TREE_WALKER_SEL_MESSAGE";

	public static final String MESSAGE_BOARDS_TREE_WALKER_THREAD = "MESSAGE_BOARDS_TREE_WALKER_THREAD";

	public static final String MESSAGE_BOARDS_TREE_WALKER_THREAD_FLAG = "MESSAGE_BOARDS_TREE_WALKER_THREAD_FLAG";

	public static final String MESSAGE_BOARDS_TREE_WALKER_VIEWABLE_THREAD = "MESSAGE_BOARDS_TREE_WALKER_VIEWABLE_THREAD";

	public static final String MOBILE_DEVICE_RULES_RULE = "MOBILE_DEVICE_RULES_RULE";

	public static final String MOBILE_DEVICE_RULES_RULE_EDITOR_JSP = "MOBILE_DEVICE_RULES_RULE_EDITOR_JSP";

	public static final String MOBILE_DEVICE_RULES_RULE_GROUP = "MOBILE_DEVICE_RULES_RULE_GROUP";

	public static final String MOBILE_DEVICE_RULES_RULE_GROUP_ACTION = "MOBILE_DEVICE_RULES_RULE_GROUP_ACTION";

	public static final String MOBILE_DEVICE_RULES_RULE_GROUP_ACTION_EDITOR_JSP = "MOBILE_DEVICE_RULES_RULE_GROUP_ACTION_EDITOR_JSP";

	public static final String MOBILE_DEVICE_RULES_RULE_GROUP_ACTION_TYPE = "MOBILE_DEVICE_RULES_RULE_GROUP_ACTION_TYPE";

	public static final String MOBILE_DEVICE_RULES_RULE_GROUP_INSTANCE = "MOBILE_DEVICE_RULES_RULE_GROUP_INSTANCE";

	public static final String MOBILE_DEVICE_RULES_RULE_INSTANCE = "MOBILE_DEVICE_RULES_RULE_INSTANCE";

	public static final String MOBILE_DEVICE_RULES_RULE_TYPE = "MOBILE_DEVICE_RULES_RULE_TYPE";

	public static final String NETVIBES = "NETVIBES";

	public static final String NOTES_LIST = "NOTES_LIST";

	public static final String NTLM_REMOTE_USER = "NTLM_REMOTE_USER";

	public static final String NTLM_USER_ACCOUNT = "NTLM_USER_ACCOUNT";

	public static final String OPEN_ID_DISCO = "openid-disco";

	public static final String OPEN_ID_LOGIN = "OPEN_ID_LOGIN";

	public static final String OPEN_ID_LOGIN_PENDING = "OPEN_ID_LOGIN_PENDING";

	public static final String OPEN_SSO_LOGIN = "OPEN_SSO_LOGIN";

	public static final String ORG_LABOR = "ORG_LABOR";

	public static final String ORGANIZATION = "ORGANIZATION";

	public static final String OUTER_PORTLET_ID = "OUTER_PORTLET_ID";

	public static final String PASSWORD_POLICY = "PASSWORD_POLICY";

	public static final String PHONE = "PHONE";

	public static final String PORTAL_PREFERENCES = "PORTAL_PREFERENCES";

	public static final String PORTAL_REQUEST_DATA_SAMPLE = "PORTAL_REQUEST_DATA_SAMPLE";

	public static final String PORTAL_RESILIENCY_ACTION = "PORTAL_RESILIENCY_ACTION";

	public static final String PORTLET_AJAX_RENDER = "PORTLET_AJAX_RENDER";

	public static final String PORTLET_CATEGORY = "PORTLET_CATEGORY";

	public static final String PORTLET_CATEGORY_INDEX = "PORTLET_CATEGORY_INDEX";

	public static final String PORTLET_CATEGORY_PATH = "PORTLET_CATEGORY_PATH";

	public static final String PORTLET_CONTENT = "PORTLET_CONTENT";

	public static final String PORTLET_PARALLEL_RENDER = "PORTLET_PARALLEL_RENDER";

	public static final String PORTLET_RENDER_PARAMETERS = "PORTLET_RENDER_PARAMETERS_";

	public static final String PORTLET_STRUTS_ACTION = "PORTLET_STRUTS_ACTION";

	public static final String PORTLET_STRUTS_ATTRIBUTES = "PORTLET_STRUTS_ATTRIBUTES";

	public static final String PORTLET_STRUTS_EXCEPTION = "PORTLET_STRUTS_EXCEPTION";

	public static final String PORTLET_STRUTS_EXECUTE = "PORTLET_STRUTS_EXECUTE";

	@Distributed(direction = Direction.DUPLEX, matchType = MatchType.POSTFIX)
	public static final String PORTLET_STRUTS_FORWARD = "PORTLET_STRUTS_FORWARD";

	public static final String PORTLET_STRUTS_PROCESSOR = "PORTLET_STRUTS_PROCESSOR";

	public static final String PREVIOUS_LAYOUT = "PREVIOUS_LAYOUT";

	public static final String PUBLIC_RENDER_PARAMETER_CONFIGURATIONS = "PUBLIC_RENDER_PARAMETER_CONFIGURATIONS";

	public static final String PUBLIC_RENDER_PARAMETERS = "PUBLIC_RENDER_PARAMETERS";

	public static final String PUBLIC_RENDER_PARAMETERS_POOL = "PUBLIC_RENDER_PARAMETERS_POOL_";

	public static final String REDIRECT_TO_DEFAULT_LAYOUT = "REDIRECT_TO_DEFAULT_LAYOUT";

	public static final String REQUESTED_LAYOUT = "REQUESTED_LAYOUT";

	public static final String ROLE = "ROLE";

	public static final String SEL_COMPANY = "SEL_COMPANY";

	public static final String SEL_LAYOUT = "SEL_LAYOUT";

	public static final String SESSION_LISTENER = "SESSION_LISTENER";

	public static final String SESSION_MAX_ALLOWED = "SESSION_MAX_ALLOWED";

	public static final String SETUP_WIZARD_DEFAULT_LOCALE = "SETUP_WIZARD_DEFAULT_LOCALE";

	public static final String SETUP_WIZARD_PASSWORD_UPDATED = "SETUP_WIZARD_PASSWORD_UPDATED";

	public static final String SETUP_WIZARD_PROPERTIES = "SETUP_WIZARD_PROPERTIES";

	public static final String SETUP_WIZARD_PROPERTIES_FILE_CREATED = "SETUP_WIZARD_PROPERTIES_FILE_CREATED";

	public static final String SHOPPING_CATEGORY = "SHOPPING_CATEGORY";

	public static final String SHOPPING_COUPON = "SHOPPING_COUPON";

	public static final String SHOPPING_ITEM = "SHOPPING_ITEM";

	public static final String SHOPPING_ORDER = "SHOPPING_ORDER";

	public static final String SITE_ADMINISTRATION_CATEGORIES_MAP = "SITES_ADMINISTRATION_CATEGORIES_MAP";

	public static final String SOCIAL_ACTIVITY_SETTINGS_MAP = "SOCIAL_ACTIVITY_SETTINGS_MAP";

	public static final String SOCIAL_REQUESTS = "SOCIAL_REQUESTS";

	public static final String SOFTWARE_CATALOG_FRAMEWORK_VERSION = "SOFTWARE_CATALOG_FRAMEWORK_VERSION";

	public static final String SOFTWARE_CATALOG_LICENSE = "SOFTWARE_CATALOG_LICENSE";

	public static final String SOFTWARE_CATALOG_PRODUCT_ENTRY = "SOFTWARE_CATALOG_PRODUCT_ENTRY";

	public static final String SOFTWARE_CATALOG_PRODUCT_VERSION = "SOFTWARE_CATALOG_PRODUCT_VERSION";

	public static final String SOURCE_NAME = "SOURCE_NAME";

	public static final String SOURCE_VERSION = "SOURCE_VERSION";

	public static final String STALE_SESSION = "STALE_SESSION";

	public static final String STARTUP_FINISHED = "STARTUP_FINISHED";

	public static final String STRUTS_BRIDGES_ATTRIBUTES = "STRUTS_BRIDGES_ATTRIBUTES";

	public static final String SUBJECT = "SUBJECT";

	public static final String TAB_INDEX = "TAB_INDEX";

	public static final String TAGS_LAYOUT_ENTRIES = "TAGS_LAYOUT_ENTRIES";

	public static final String TARGET_NAME = "TARGET_NAME";

	public static final String TARGET_VERSION = "TARGET_VERSION";

	public static final String TASK_LIST = "TASK_LIST";

	public static final String TEAM = "TEAM";

	public static final String TITLE = "TITLE";

	public static final String TRANSLATOR_TRANSLATION = "TRANSLATOR_TRANSLATION";

	public static final String TRASH_CONTAINER_MODEL = "TRASH_CONTAINER_MODEL";

	public static final String TRASH_ENTRY = "TRASH_ENTRY";

	public static final String TRASH_RENDERER = "TRASH_RENDERER";

	public static final String TREE_GROUP_ID = "TREE_GROUP_ID";

	public static final String TREE_NODE_ID = "TREE_NODE_ID";

	public static final String TREE_OPEN_NODES = "TREE_OPEN_NODES";

	public static final String TREE_PARENT_LAYOUT_ID = "TREE_PARENT_LAYOUT_ID";

	public static final String TREE_PORTLET_URL = "TREE_PORTLET_URL";

	public static final String TREE_PRIVATE_LAYOUT = "TREE_PRIVATE_LAYOUT";

	public static final String TREE_RENDER_CHILDREN_ONLY = "TREE_RENDER_CHILDREN_ONLY";

	public static final String TREE_SELECTABLE_TREE = "TREE_SELECTABLE_TREE";

	public static final String TREE_SELECTED_NODES = "TREE_SELECTED_NODES";

	public static final String USER_GROUP = "USER_GROUP";

	public static final String USERS_NOTIFIED = "USERS_NOTIFIED";

	public static final String VIRTUAL_HOST_LAYOUT_SET = "VIRTUAL_HOST_LAYOUT_SET";

	public static final String WEBSITE = "WEBSITE";

	public static final String WIDGET = "WIDGET";

	public static final String WIKI_NODE = "WIKI_NODE";

	public static final String WIKI_NODE_ID = "WIKI_NODE_ID";

	public static final String WIKI_PAGE = "WIKI_PAGE";

	public static final String WIKI_TREE_WALKER_DEPTH = "WIKI_TREE_WALKER_DEPTH";

	public static final String WIKI_TREE_WALKER_PAGE = "WIKI_TREE_WALKER_PAGE";

	public static final String WIKI_TREE_WALKER_PARENT = "WIKI_TREE_WALKER_PARENT";

	public static final String WORDS_LIST = "WORDS_LIST";

	public static final String WORKFLOW_ASSET_PREVIEW = "WORKFLOW_ASSET_PREVIEW";

	public static final String WORKFLOW_DEFINITION = "WORKFLOW_DEFINITION";

	public static final String WORKFLOW_INSTANCE = "WORKFLOW_INSTANCE";

	public static final String WORKFLOW_TASK = "WORKFLOW_TASK";

	public static final String WSRP_NEW_SESSION = "WSRP_NEW_SESSION";

	public static final String WSRP_PRODUCER = "WSRP_PRODUCER";

}