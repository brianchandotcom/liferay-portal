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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.resiliency.spi.agent.annotation.Direction;
import com.liferay.portal.kernel.resiliency.spi.agent.annotation.Distributed;
import com.liferay.portal.kernel.resiliency.spi.agent.annotation.MatchType;

/**
 * @author Brian Wing Shun Chan
 */
public interface WebKeys {

	public static final String ADDRESS = "ADDRESS";

	@Distributed
	public static final String ANNOUNCEMENTS_ENTRY = "LIFERAY_SHARED_ANNOUNCEMENTS_ENTRY";

	public static final String ASSET_ADDON_ENTRIES = "ASSET_ADDON_ENTRIES";

	public static final String ASSET_ADDON_ENTRY = "ASSET_ADDON_ENTRY";

	public static final String ASSET_CATEGORY = "ASSET_CATEGORY";

	public static final String ASSET_ENTRY_ABSTRACT_LENGTH = "ASSET_ENTRY_ABSTRACT_LENGTH";

	public static final String ASSET_ENTRY_VIEW_URL = "ASSET_ENTRY_VIEW_URL";

	public static final String ASSET_LAYOUT_TAG_NAMES = "ASSET_LAYOUT_TAG_NAMES";

	public static final String ASSET_RENDERER = "ASSET_RENDERER";

	public static final String ASSET_RENDERER_FACTORY = "ASSET_RENDERER_FACTORY";

	public static final String ASSET_RENDERER_FACTORY_CLASS_TYPE_ID = "ASSET_RENDERER_FACTORY_CLASS_TYPE_ID";

	public static final String ASSET_TAG = "ASSET_TAG";

	public static final String ASSET_VOCABULARIES = "ASSET_VOCABULARIES";

	public static final String ASSET_VOCABULARY = "ASSET_VOCABULARY";

	@Distributed(direction = Direction.RESPONSE)
	public static final String AUI_SCRIPT_DATA = "LIFERAY_SHARED_AUI_SCRIPT_DATA";

	public static final String AUTHENTICATION_TOKEN = "LIFERAY_SHARED_AUTHENTICATION_TOKEN";

	public static final String AVAILABLE_LOCALES = "AVAILABLE_LOCALES";

	public static final String BLOGS_CATEGORY = "BLOGS_CATEGORY";

	public static final String BLOGS_ENTRY = "BLOGS_ENTRY";

	public static final String BROWSER_SNIFFER_REVISION = "BROWSER_SNIFFER_REVISION";

	public static final String BROWSER_SNIFFER_VERSION = "BROWSER_SNIFFER_VERSION";

	public static final String CACHE_PORTLET_RESPONSES = "CACHE_PORTLET_RESPONSES";

	public static final String CALENDAR_EVENT = "CALENDAR_EVENT";

	public static final String CAPTCHA_COUNT = "CAPTCHA_COUNT";

	public static final String CAPTCHA_TEXT = "CAPTCHA_TEXT";

	public static final String CAS_FORCE_LOGOUT = "CAS_FORCE_LOGOUT";

	public static final String CAS_LOGIN = "CAS_LOGIN";

	public static final String CAS_NO_SUCH_USER_EXCEPTION = "CAS_NO_SUCH_USER_EXCEPTION";

	public static final String CLP_MESSAGE_LISTENERS = "CLP_MESSAGE_LISTENERS";

	public static final String COLOR_SCHEME = "COLOR_SCHEME";

	public static final String COMIC_ENTRY = "COMIC_ENTRY";

	public static final String COMMENT = "COMMENT";

	public static final String COMPANY = "COMPANY";

	public static final String COMPANY_ID = "COMPANY_ID";

	public static final String CONFIGURATION_ACTION = "CONFIGURATION_ACTION";

	public static final String CONFIGURATION_ACTION_PATH = "CONFIGURATION_ACTION_PATH";

	public static final String CONTROL_PANEL_CATEGORIES_MAP = "CONTROL_PANEL_CATEGORIES_MAP";

	public static final String CTX = "CTX";

	public static final String CTX_PATH = "CTX_PATH";

	public static final String CURRENT_COMPLETE_URL = "CURRENT_COMPLETE_URL";

	public static final String CURRENT_PORTLET_URL = "CURRENT_PORTLET_URL";

	@Distributed(direction = Direction.REQUEST)
	public static final String CURRENT_URL = "CURRENT_URL";

	public static final String DEVICE = "DEVICE";

	public static final String DIFF_HTML_RESULTS = "DIFF_HTML_RESULTS";

	public static final String DIFF_RESULTS = "DIFF_RESULTS";

	public static final String DIFF_VERSION = "DIFF_VERSION";

	public static final String DOCUMENT_LIBRARY_DYNAMIC_DATA_MAPPING_STRUCTURE = "DOCUMENT_LIBRARY_DYNAMIC_DATA_MAPPING_STRUCTURE";

	public static final String DOCUMENT_LIBRARY_FILE_ENTRIES = "DOCUMENT_LIBRARY_FILE_ENTRIES";

	public static final String DOCUMENT_LIBRARY_FILE_ENTRY = "DOCUMENT_LIBRARY_FILE_ENTRY";

	public static final String DOCUMENT_LIBRARY_FILE_ENTRY_TYPE = "DOCUMENT_LIBRARY_FILE_ENTRY_TYPE";

	public static final String DOCUMENT_LIBRARY_FILE_SHORTCUT = "DOCUMENT_LIBRARY_FILE_SHORTCUT";

	public static final String DOCUMENT_LIBRARY_FILE_SHORTCUTS = "DOCUMENT_LIBRARY_FILE_SHORTCUTS";

	public static final String DOCUMENT_LIBRARY_FILE_VERSION = "DOCUMENT_LIBRARY_FILE_VERSION";

	public static final String DOCUMENT_LIBRARY_FOLDER = "DOCUMENT_LIBRARY_FOLDER";

	public static final String DOCUMENT_LIBRARY_FOLDERS = "DOCUMENT_LIBRARY_FOLDERS";

	public static final String DOCUMENT_LIBRARY_REPOSITORY = "DOCUMENT_LIBRARY_REPOSITORY";

	public static final String EMAIL_ADDRESS = "EMAIL_ADDRESS";

	public static final String ENCRYPT = "shuo";

	public static final String EXPANDO_COLUMN = "EXPANDO_COLUMN";

	public static final String EXTEND_SESSION = "EXTEND_SESSION";

	public static final String FACEBOOK_CANVAS_PAGE_URL = "FACEBOOK_CANVAS_PAGE_URL";

	public static final String FACEBOOK_INCOMPLETE_USER_ID = "FACEBOOK_INCOMPLETE_USER_ID";

	public static final String FACEBOOK_USER_EMAIL_ADDRESS = "FACEBOOK_USER_EMAIL_ADDRESS";

	public static final String FORGOT_PASSWORD_REMINDER_ATTEMPTS = "FORGOT_PASSWORD_REMINDER_ATTEMPTS";

	public static final String FORGOT_PASSWORD_REMINDER_USER = "FORGOT_PASSWORD_REMINDER_USER";

	public static final String FORGOT_PASSWORD_REMINDER_USER_EMAIL_ADDRESS = "FORGOT_PASSWORD_REMINDER_USER_EMAIL_ADDRESS";

	public static final String FORM_NAME = "FORM_NAME";

	public static final String FORM_NAVIGATOR_SECTION_SHOW = "FORM_NAVIGATOR_SECTION_SHOW_";

	public static final String FORWARD_URL = "FORWARD_URL";

	public static final String FRIENDLY_URL = "FRIENDLY_URL";

	public static final String FTL_VARIABLES = "FTL_VARIABLES";

	public static final String GOOGLE_GADGET = "GOOGLE_GADGET";

	public static final String GROUP = "GROUP";

	public static final String HTTPS_INITIAL = "HTTPS_INITIAL";

	public static final String I18N_LANGUAGE_CODE = "I18N_LANGUAGE_CODE";

	public static final String I18N_LANGUAGE_ID = "I18N_LANGUAGE_ID";

	public static final String I18N_PATH = "I18N_PATH";

	public static final String INVOKER_FILTER_URI = "INVOKER_FILTER_URI";

	public static final String JAVASCRIPT_CONTEXT = "JAVASCRIPT_CONTEXT";

	public static final String JOURNAL_ARTICLE = "JOURNAL_ARTICLE";

	public static final String JOURNAL_ARTICLE_CONTENT = "JOURNAL_ARTICLE_CONTENT";

	public static final String JOURNAL_ARTICLE_CONTENT_EL = "JOURNAL_ARTICLE_CONTENT_EL";

	public static final String JOURNAL_ARTICLE_DISPLAY = "JOURNAL_ARTICLE_DISPLAY";

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

	public static final String LAST_PATH = "LAST_PATH";

	public static final String LAYOUT = "LAYOUT";

	@Distributed
	public static final String LAYOUT_ASSET_ENTRY = "LIFERAY_SHARED_LAYOUT_ASSET_ENTRY";

	public static final String LAYOUT_CONTENT = "LAYOUT_CONTENT";

	public static final String LAYOUT_DEFAULT = "LAYOUT_DEFAULT";

	public static final String LAYOUT_DESCRIPTIONS = "LAYOUT_DESCRIPTIONS";

	public static final String LAYOUT_PORTLETS = "LAYOUT_PORTLETS";

	public static final String LAYOUT_PROTOTYPE = "LAYOUT_PROTOTYPE";

	public static final String LAYOUT_REVISION = "LAYOUT_REVISION";

	public static final String LAYOUTS = "LAYOUTS";

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #VISITED_GROUP_ID_PREVIOUS}
	 */
	@Deprecated
	public static final String LIFERAY_SHARED_VISITED_GROUP_ID_PREVIOUS = "LIFERAY_SHARED_VISITED_GROUP_ID_PREVIOUS";

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #VISITED_GROUP_ID_RECENT}
	 */
	@Deprecated
	public static final String LIFERAY_SHARED_VISITED_GROUP_ID_RECENT = "LIFERAY_SHARED_VISITED_GROUP_ID_RECENT";

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

	public static final String NETVIBES = "NETVIBES";

	public static final String NOTES_LIST = "NOTES_LIST";

	public static final String OPEN_ID_LOGIN = "OPEN_ID_LOGIN";

	public static final String OPEN_ID_LOGIN_PENDING = "OPEN_ID_LOGIN_PENDING";

	public static final String ORG_LABOR = "ORG_LABOR";

	public static final String ORGANIZATION = "ORGANIZATION";

	public static final String OSGI_BUNDLE = "OSGI_BUNDLE";

	public static final String OUTER_PORTLET_ID = "OUTER_PORTLET_ID";

	@Distributed(direction = Direction.RESPONSE)
	public static final String OUTPUT_DATA = "LIFERAY_SHARED_OUTPUT_DATA";

	public static final String PAGE_BODY_BOTTOM = "PAGE_BODY_BOTTOM";

	public static final String PAGE_BODY_TOP = "PAGE_BODY_TOP";

	public static final String PAGE_BOTTOM = "PAGE_BOTTOM";

	@Distributed(direction = Direction.RESPONSE)
	public static final String PAGE_DESCRIPTION = "LIFERAY_SHARED_PAGE_DESCRIPTION";

	@Distributed(direction = Direction.RESPONSE)
	public static final String PAGE_KEYWORDS = "LIFERAY_SHARED_PAGE_KEYWORDS";

	@Distributed(direction = Direction.RESPONSE)
	public static final String PAGE_SUBTITLE = "LIFERAY_SHARED_PAGE_SUBTITLE";

	@Distributed(direction = Direction.RESPONSE)
	public static final String PAGE_TITLE = "LIFERAY_SHARED_PAGE_TITLE";

	public static final String PAGE_TOP = "PAGE_TOP";

	public static final String PARALLEL_RENDERING_MERGE_LOCK = "PARALLEL_RENDERING_MERGE_LOCK";

	public static final String PARALLEL_RENDERING_TIMEOUT_ERROR = "PARALLEL_RENDERING_TIMEOUT_ERROR";

	public static final String PASSWORD_POLICY = "PASSWORD_POLICY";

	public static final String PHONE = "PHONE";

	public static final String PLUGIN_LAYOUT_TEMPLATES = "PLUGIN_LAYOUT_TEMPLATES";

	public static final String PLUGIN_PORTLETS = "PLUGIN_PORTLETS";

	public static final String PLUGIN_REPOSITORY_REPORT = "PLUGIN_REPOSITORY_REPORT";

	public static final String PLUGIN_THEMES = "PLUGIN_THEMES";

	public static final String PORTAL_MESSAGES = "LIFERAY_SHARED_PORTAL_MESSAGES";

	public static final String PORTAL_PREFERENCES = "PORTAL_PREFERENCES";

	public static final String PORTAL_RESILIENCY_ACTION = "PORTAL_RESILIENCY_ACTION";

	public static final String PORTLET_AJAX_RENDER = "PORTLET_AJAX_RENDER";

	public static final String PORTLET_BREADCRUMBS = "LIFERAY_SHARED_PORTLET_BREADCRUMBS";

	public static final String PORTLET_CATEGORY = "PORTLET_CATEGORY";

	public static final String PORTLET_CATEGORY_INDEX = "PORTLET_CATEGORY_INDEX";

	public static final String PORTLET_CATEGORY_PATH = "PORTLET_CATEGORY_PATH";

	public static final String PORTLET_CONFIGURATOR_VISIBILITY = "PORTLET_CONFIGURATOR_VISIBILITY";

	public static final String PORTLET_CONTENT = "PORTLET_CONTENT";

	public static final String PORTLET_CONTENT_JSP = "PORTLET_CONTENT_JSP";

	public static final String PORTLET_DECORATE = "PORTLET_DECORATE";

	public static final String PORTLET_DISPLAY_CONTEXT = "PORTLET_DISPLAY_CONTEXT";

	public static final String PORTLET_DISPLAY_TEMPLATE = "PORTLET_DISPLAY_TEMPLATE";

	public static final String PORTLET_ID = "PORTLET_ID";

	public static final String PORTLET_PARALLEL_RENDER = "PORTLET_PARALLEL_RENDER";

	public static final String PORTLET_PREFERENCES_MAP = "PORTLET_PREFERENCES_MAP";

	public static final String PORTLET_QUICK_ACCESS_ENTRIES = "LIFERAY_SHARED_PORTLET_QUICK_ACCESS_ENTRIES";

	public static final String PORTLET_RENDER_PARAMETERS = "PORTLET_RENDER_PARAMETERS_";

	public static final String PORTLET_RESOURCE_STATIC_URLS = "PORTLET_RESOURCE_STATIC_URLS";

	public static final String PORTLET_SESSION = "PORTLET_SESSION";

	public static final String PORTLET_SESSION_ATTRIBUTES = "PORTLET_SESSION_ATTRIBUTES_";

	public static final String PORTLET_STRUTS_ACTION = "PORTLET_STRUTS_ACTION";

	public static final String PORTLET_STRUTS_ATTRIBUTES = "PORTLET_STRUTS_ATTRIBUTES";

	public static final String PORTLET_STRUTS_EXCEPTION = "PORTLET_STRUTS_EXCEPTION";

	public static final String PORTLET_STRUTS_EXECUTE = "PORTLET_STRUTS_EXECUTE";

	@Distributed(direction = Direction.DUPLEX, matchType = MatchType.POSTFIX)
	public static final String PORTLET_STRUTS_FORWARD = "PORTLET_STRUTS_FORWARD";

	public static final String PORTLET_STRUTS_PROCESSOR = "PORTLET_STRUTS_PROCESSOR";

	public static final String PREVIOUS_LAYOUT_PLID = "PREVIOUS_LAYOUT_PLID";

	public static final String PRIVATE_LAYOUT = "PRIVATE_LAYOUT";

	public static final String PUBLIC_RENDER_PARAMETER_CONFIGURATIONS = "PUBLIC_RENDER_PARAMETER_CONFIGURATIONS";

	public static final String PUBLIC_RENDER_PARAMETERS = "PUBLIC_RENDER_PARAMETERS";

	public static final String PUBLIC_RENDER_PARAMETERS_POOL = "PUBLIC_RENDER_PARAMETERS_POOL_";

	public static final String REDIRECT = "REDIRECT";

	public static final String REDIRECT_TO_DEFAULT_LAYOUT = "REDIRECT_TO_DEFAULT_LAYOUT";

	public static final String REFERER = "referer";

	public static final String RENDER_PATH = "RENDER_PATH";

	public static final String RENDER_PORTLET = "RENDER_PORTLET";

	public static final String RENDER_PORTLET_BOUNDARY = "RENDER_PORTLET_BOUNDARY";

	public static final String RENDER_PORTLET_COLUMN_COUNT = "RENDER_PORTLET_COLUMN_COUNT";

	public static final String RENDER_PORTLET_COLUMN_ID = "RENDER_PORTLET_COLUMN_ID";

	public static final String RENDER_PORTLET_COLUMN_POS = "RENDER_PORTLET_COLUMN_POS";

	public static final String RENDER_PORTLET_PREFERENCES = "RENDER_PORTLET_PREFERENCES";

	public static final String RENDER_PORTLET_RESOURCE = "RENDER_PORTLET_RESOURCE";

	public static final String REQUESTED_LAYOUT = "REQUESTED_LAYOUT";

	public static final String ROLE = "ROLE";

	public static final String SEARCH_CONTAINER = "SEARCH_CONTAINER";

	public static final String SEARCH_CONTAINER_REFERENCE = "LIFERAY_SHARED_SEARCH_CONTAINER_REFERENCE";

	public static final String SEARCH_CONTAINER_RESULT_ROW = "SEARCH_CONTAINER_RESULT_ROW";

	public static final String SEARCH_CONTAINER_RESULT_ROW_CHECKER = "SEARCH_CONTAINER_RESULT_ROW_CHECKER";

	public static final String SEARCH_CONTAINER_RESULT_ROW_ENTRY = "SEARCH_CONTAINER_RESULT_ROW_ENTRY";

	public static final String SEARCH_ENTRY_HREF = "SEARCH_ENTRY_HREF";

	public static final String SEARCH_SEARCH_RESULTS = "SEARCH_SEARCH_RESULTS";

	public static final String SEL_COMPANY = "SEL_COMPANY";

	public static final String SEL_LAYOUT = "SEL_LAYOUT";

	public static final String SERVLET_CONTEXT_INCLUDE_FILTER_PATH = "SERVLET_CONTEXT_INCLUDE_FILTER_PATH";

	public static final String SERVLET_CONTEXT_INCLUDE_FILTER_STRICT = "SERVLET_CONTEXT_INCLUDE_FILTER_STRICT";

	public static final String SERVLET_CONTEXT_INCLUDE_FILTER_THEME = "SERVLET_CONTEXT_INCLUDE_FILTER_THEME";

	public static final String SERVLET_PATH = "SERVLET_PATH";

	public static final String SESSION_LISTENER = "SESSION_LISTENER";

	public static final String SESSION_MAX_ALLOWED = "SESSION_MAX_ALLOWED";

	public static final String SETTINGS_SCOPE = "SETTINGS_SCOPE";

	public static final String SETUP_WIZARD_DEFAULT_LOCALE = "SETUP_WIZARD_DEFAULT_LOCALE";

	public static final String SETUP_WIZARD_PASSWORD_UPDATED = "SETUP_WIZARD_PASSWORD_UPDATED";

	public static final String SETUP_WIZARD_PROPERTIES = "SETUP_WIZARD_PROPERTIES";

	public static final String SETUP_WIZARD_PROPERTIES_FILE_CREATED = "SETUP_WIZARD_PROPERTIES_FILE_CREATED";

	public static final String SHOPPING_CATEGORY = "SHOPPING_CATEGORY";

	public static final String SHOPPING_COUPON = "SHOPPING_COUPON";

	public static final String SHOPPING_ITEM = "SHOPPING_ITEM";

	public static final String SHOPPING_ORDER = "SHOPPING_ORDER";

	public static final String SINGLE_PAGE_APPLICATION_CLEAR_CACHE = "LIFERAY_SHARED_SINGLE_PAGE_APPLICATION_CLEAR_CACHE";

	public static final String SINGLE_PAGE_APPLICATION_LAST_PORTLET_ID = "SINGLE_PAGE_APPLICATION_LAST_PORTLET_ID";

	public static final String SITE_ADMINISTRATION_CATEGORIES_MAP = "SITES_ADMINISTRATION_CATEGORIES_MAP";

	public static final String SOURCE_NAME = "SOURCE_NAME";

	public static final String SOURCE_VERSION = "SOURCE_VERSION";

	@Distributed(direction = Direction.RESPONSE)
	public static final String SPI_AGENT_ACTION_RESULT = "SPI_AGENT_ACTION_RESULT";

	@Distributed(direction = Direction.REQUEST)
	public static final String SPI_AGENT_EVENT = "SPI_AGENT_EVENT";

	@Distributed(direction = Direction.RESPONSE)
	public static final String SPI_AGENT_EVENT_RESULT = "SPI_AGENT_EVENT_RESULT";

	@Distributed(direction = Direction.REQUEST)
	public static final String SPI_AGENT_LAYOUT = "SPI_AGENT_LAYOUT";

	@Distributed(direction = Direction.RESPONSE)
	public static final String SPI_AGENT_LAYOUT_TYPE_SETTINGS = "SPI_AGENT_LAYOUT_TYPE_SETTINGS";

	@Distributed(direction = Direction.REQUEST)
	public static final String SPI_AGENT_LIFECYCLE = "SPI_AGENT_LIFECYCLE";

	public static final String SPI_AGENT_ORIGINAL_RESPONSE = "SPI_AGENT_ORIGINAL_RESPONSE";

	@Distributed(direction = Direction.REQUEST)
	public static final String SPI_AGENT_PORTLET = "SPI_AGENT_PORTLET";

	public static final String SPI_AGENT_REQUEST = "SPI_AGENT_REQUEST";

	public static final String SPI_AGENT_RESPONSE = "SPI_AGENT_RESPONSE";

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

	public static final String THEME = "THEME";

	public static final String THEME_DISPLAY = "LIFERAY_SHARED_THEME_DISPLAY";

	public static final String TICKET = "TICKET";

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

	public static final String UNIQUE_ELEMENT_IDS = "LIFERAY_SHARED_UNIQUE_ELEMENT_IDS";

	public static final String UPLOAD_EXCEPTION = "UPLOAD_EXCEPTION";

	@Distributed(direction = Direction.REQUEST)
	public static final String USER = "USER";

	public static final String USER_GROUP = "USER_GROUP";

	@Distributed(direction = Direction.REQUEST)
	public static final String USER_ID = "USER_ID";

	public static final String USER_PASSWORD = "USER_PASSWORD";

	public static final String USER_UUID = "USER_UUID";

	public static final String USERS_NOTIFIED = "USERS_NOTIFIED";

	public static final String VIRTUAL_HOST_LAYOUT_SET = "VIRTUAL_HOST_LAYOUT_SET";

	public static final String VISITED_GROUP_ID_PREVIOUS = "LIFERAY_SHARED_VISITED_GROUP_ID_PREVIOUS";

	public static final String VISITED_GROUP_ID_RECENT = "LIFERAY_SHARED_VISITED_GROUP_ID_RECENT";

	public static final String VM_VARIABLES = "VM_VARIABLES";

	public static final String WEBSITE = "WEBSITE";

	public static final String WIDGET = "WIDGET";

	public static final String WINDOW_STATE = "WINDOW_STATE";

	public static final String WORDS_LIST = "WORDS_LIST";

	public static final String WORKFLOW_ASSET_PREVIEW = "WORKFLOW_ASSET_PREVIEW";

	public static final String WORKFLOW_DEFINITION = "WORKFLOW_DEFINITION";

	public static final String WORKFLOW_DEFINITIONS = "WORKFLOW_DEFINITIONS";

	public static final String WORKFLOW_INSTANCE = "WORKFLOW_INSTANCE";

	public static final String WORKFLOW_TASK = "WORKFLOW_TASK";

	public static final String WSRP_NEW_SESSION = "WSRP_NEW_SESSION";

	public static final String WSRP_PRODUCER = "WSRP_PRODUCER";

	public static final String XUGGLER_INSTALL_STATUS = "XUGGLER_INSTALL_STATUS";

}