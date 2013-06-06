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

package com.liferay.portal.util;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;

/**
 * @author Brian Wing Shun Chan
 */
public class PropsValues {

	public static String[] ADMIN_ANALYTICS_TYPES =  StringUtil.splitLines(PropsUtil.get(PropsKeys.ADMIN_ANALYTICS_TYPES));

	public static String[] ADMIN_DEFAULT_GROUP_NAMES = StringUtil.splitLines(PropsUtil.get(PropsKeys.ADMIN_DEFAULT_GROUP_NAMES));

	public static String[] ADMIN_DEFAULT_ORGANIZATION_GROUP_NAMES = StringUtil.splitLines(PropsUtil.get(PropsKeys.ADMIN_DEFAULT_ORGANIZATION_GROUP_NAMES));

	public static String[] ADMIN_DEFAULT_ROLE_NAMES = StringUtil.splitLines(PropsUtil.get(PropsKeys.ADMIN_DEFAULT_ROLE_NAMES));

	public static String[] ADMIN_DEFAULT_USER_GROUP_NAMES = StringUtil.splitLines(PropsUtil.get(PropsKeys.ADMIN_DEFAULT_USER_GROUP_NAMES));

	public static String ADMIN_EMAIL_FROM_ADDRESS = PropsUtil.get(PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);

	public static String ADMIN_EMAIL_FROM_NAME = PropsUtil.get(PropsKeys.ADMIN_EMAIL_FROM_NAME);

	public static final String[] ADMIN_MAIL_HOST_NAMES = StringUtil.splitLines(PropsUtil.get(PropsKeys.ADMIN_MAIL_HOST_NAMES));

	public static final String[] ADMIN_RESERVED_EMAIL_ADDRESSES = StringUtil.splitLines(PropsUtil.get(PropsKeys.ADMIN_RESERVED_EMAIL_ADDRESSES));

	public static final String[] ADMIN_RESERVED_SCREEN_NAMES = StringUtil.splitLines(PropsUtil.get(PropsKeys.ADMIN_RESERVED_SCREEN_NAMES));

	public static final boolean ADMIN_SYNC_DEFAULT_ASSOCIATIONS = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.ADMIN_SYNC_DEFAULT_ASSOCIATIONS));

	public static final String AMAZON_ACCESS_KEY_ID = PropsUtil.get(PropsKeys.AMAZON_ACCESS_KEY_ID);

	public static final String AMAZON_ASSOCIATE_TAG = PropsUtil.get(PropsKeys.AMAZON_ASSOCIATE_TAG);

	public static final String AMAZON_SECRET_ACCESS_KEY = PropsUtil.get(PropsKeys.AMAZON_SECRET_ACCESS_KEY);

	public static final String ANNOUNCEMENTS_EMAIL_BODY = PropsUtil.get(PropsKeys.ANNOUNCEMENTS_EMAIL_BODY);

	public static final String ANNOUNCEMENTS_EMAIL_FROM_ADDRESS = PropsUtil.get(PropsKeys.ANNOUNCEMENTS_EMAIL_FROM_ADDRESS);

	public static final String ANNOUNCEMENTS_EMAIL_FROM_NAME = PropsUtil.get(PropsKeys.ANNOUNCEMENTS_EMAIL_FROM_NAME);

	public static final String ANNOUNCEMENTS_EMAIL_SUBJECT = PropsUtil.get(PropsKeys.ANNOUNCEMENTS_EMAIL_SUBJECT);

	public static final String ANNOUNCEMENTS_EMAIL_TO_ADDRESS = PropsUtil.get(PropsKeys.ANNOUNCEMENTS_EMAIL_TO_ADDRESS);

	public static final String ANNOUNCEMENTS_EMAIL_TO_NAME = PropsUtil.get(PropsKeys.ANNOUNCEMENTS_EMAIL_TO_NAME);

	public static final int ANNOUNCEMENTS_ENTRY_CHECK_INTERVAL = GetterUtil.getInteger(PropsUtil.get(PropsKeys.ANNOUNCEMENTS_ENTRY_CHECK_INTERVAL));

	public static final int[] ANNOUNCEMENTS_ENTRY_PAGE_DELTA_VALUES = GetterUtil.getIntegerValues(PropsUtil.getArray(PropsKeys.ANNOUNCEMENTS_ENTRY_PAGE_DELTA_VALUES));

	public static final String[] APPLICATION_SHUTDOWN_EVENTS = PropsUtil.getArray(PropsKeys.APPLICATION_SHUTDOWN_EVENTS);

	public static final String[] APPLICATION_STARTUP_EVENTS = PropsUtil.getArray(PropsKeys.APPLICATION_STARTUP_EVENTS);

	public static final String ASSET_CATEGORIES_NAVIGATION_DISPLAY_TEMPLATES_CONFIG = PropsUtil.get(PropsKeys.ASSET_CATEGORIES_NAVIGATION_DISPLAY_TEMPLATES_CONFIG);

	public static final String[] ASSET_CATEGORIES_PROPERTIES_DEFAULT = PropsUtil.getArray(PropsKeys.ASSET_CATEGORIES_PROPERTIES_DEFAULT);

	public static final boolean ASSET_CATEGORIES_SEARCH_HIERARCHICAL = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.ASSET_CATEGORIES_SEARCH_HIERARCHICAL));

	public static final int ASSET_CATEGORIES_SELECTOR_MAX_ENTRIES = GetterUtil.getInteger(PropsUtil.get(PropsKeys.ASSET_CATEGORIES_SELECTOR_MAX_ENTRIES));

	public static final boolean ASSET_ENTRY_BUFFERED_INCREMENT_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.BUFFERED_INCREMENT_ENABLED, new Filter("AssetEntry")));

	public static final String ASSET_ENTRY_VALIDATOR = PropsUtil.get(PropsKeys.ASSET_ENTRY_VALIDATOR);

	public static final int ASSET_FILTER_SEARCH_LIMIT = GetterUtil.getInteger(PropsUtil.get(PropsKeys.ASSET_FILTER_SEARCH_LIMIT));

	public static final String ASSET_PUBLISHER_DISPLAY_STYLE_DEFAULT = PropsUtil.get(PropsKeys.ASSET_PUBLISHER_DISPLAY_STYLE_DEFAULT);

	public static final String[] ASSET_PUBLISHER_DISPLAY_STYLES = PropsUtil.getArray(PropsKeys.ASSET_PUBLISHER_DISPLAY_STYLES);

	public static final String ASSET_PUBLISHER_DISPLAY_TEMPLATES_CONFIG = PropsUtil.get(PropsKeys.ASSET_PUBLISHER_DISPLAY_TEMPLATES_CONFIG);

	public static final int ASSET_PUBLISHER_DYNAMIC_SUBSCRIPTION_LIMIT = GetterUtil.getInteger(PropsUtil.get(PropsKeys.ASSET_PUBLISHER_DYNAMIC_SUBSCRIPTION_LIMIT));

	public static final String ASSET_PUBLISHER_EMAIL_ASSET_ENTRY_ADDED_BODY = PropsUtil.get(PropsKeys.ASSET_PUBLISHER_EMAIL_ASSET_ENTRY_ADDED_BODY);

	public static final boolean ASSET_PUBLISHER_EMAIL_ASSET_ENTRY_ADDED_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.ASSET_PUBLISHER_EMAIL_ASSET_ENTRY_ADDED_ENABLED));

	public static final String ASSET_PUBLISHER_EMAIL_ASSET_ENTRY_ADDED_SUBJECT = PropsUtil.get(PropsKeys.ASSET_PUBLISHER_EMAIL_ASSET_ENTRY_ADDED_SUBJECT);

	public static final String ASSET_PUBLISHER_EMAIL_FROM_ADDRESS = PropsUtil.get(PropsKeys.ASSET_PUBLISHER_EMAIL_FROM_ADDRESS);

	public static final String ASSET_PUBLISHER_EMAIL_FROM_NAME = PropsUtil.get(PropsKeys.ASSET_PUBLISHER_EMAIL_FROM_NAME);

	public static final boolean ASSET_PUBLISHER_SEARCH_WITH_INDEX = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.ASSET_PUBLISHER_SEARCH_WITH_INDEX));

	public static final String[] ASSET_TAG_PROPERTIES_DEFAULT = PropsUtil.getArray(PropsKeys.ASSET_TAG_PROPERTIES_DEFAULT);

	public static final boolean ASSET_TAG_SUGGESTIONS_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.ASSET_TAG_SUGGESTIONS_ENABLED));

	public static final String ASSET_TAGS_NAVIGATION_DISPLAY_TEMPLATES_CONFIG = PropsUtil.get(PropsKeys.ASSET_TAGS_NAVIGATION_DISPLAY_TEMPLATES_CONFIG);

	public static final String ASSET_VOCABULARY_DEFAULT = PropsUtil.get(PropsKeys.ASSET_VOCABULARY_DEFAULT);

	public static final boolean AUDIT_MESSAGE_COM_LIFERAY_PORTAL_MODEL_LAYOUT_VIEW = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.AUDIT_MESSAGE_COM_LIFERAY_PORTAL_MODEL_LAYOUT_VIEW));

	public static final boolean AUDIT_MESSAGE_SCHEDULER_JOB = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.AUDIT_MESSAGE_SCHEDULER_JOB));

	public static final String[] AUTH_FAILURE = PropsUtil.getArray(PropsKeys.AUTH_FAILURE);

	public static boolean AUTH_FORWARD_BY_LAST_PATH = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.AUTH_FORWARD_BY_LAST_PATH));

	public static final boolean AUTH_FORWARD_BY_REDIRECT = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.AUTH_FORWARD_BY_REDIRECT));

	public static final boolean AUTH_LOGIN_DISABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.AUTH_LOGIN_DISABLED));

	public static final String AUTH_LOGIN_DISABLED_PATH = PropsUtil.get(PropsKeys.AUTH_LOGIN_DISABLED_PATH);

	public static final String AUTH_LOGIN_PORTLET_NAME = PropsUtil.get(PropsKeys.AUTH_LOGIN_PORTLET_NAME);

	public static final boolean AUTH_LOGIN_PROMPT_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.AUTH_LOGIN_PROMPT_ENABLED));

	public static final String AUTH_LOGIN_SITE_URL = PropsUtil.get(PropsKeys.AUTH_LOGIN_SITE_URL);

	public static final String AUTH_LOGIN_URL = PropsUtil.get(PropsKeys.AUTH_LOGIN_URL);

	public static final String[] AUTH_MAX_FAILURES = PropsUtil.getArray(PropsKeys.AUTH_MAX_FAILURES);

	public static final boolean AUTH_PIPELINE_ENABLE_LIFERAY_CHECK = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.AUTH_PIPELINE_ENABLE_LIFERAY_CHECK));

	public static final String[] AUTH_PIPELINE_POST = PropsUtil.getArray(PropsKeys.AUTH_PIPELINE_POST);

	public static final String[] AUTH_PIPELINE_PRE = PropsUtil.getArray(PropsKeys.AUTH_PIPELINE_PRE);

	public static final String[] AUTH_PUBLIC_PATHS = PropsUtil.getArray(PropsKeys.AUTH_PUBLIC_PATHS);

	public static final boolean AUTH_SIMULTANEOUS_LOGINS = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.AUTH_SIMULTANEOUS_LOGINS));

	public static final boolean AUTH_TOKEN_CHECK_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.AUTH_TOKEN_CHECK_ENABLED));

	public static final String[] AUTH_TOKEN_IGNORE_ACTIONS = PropsUtil.getArray(PropsKeys.AUTH_TOKEN_IGNORE_ACTIONS);

	public static final String[] AUTH_TOKEN_IGNORE_PORTLETS = PropsUtil.getArray(PropsKeys.AUTH_TOKEN_IGNORE_PORTLETS);

	public static final String AUTH_TOKEN_IMPL = PropsUtil.get(PropsKeys.AUTH_TOKEN_IMPL);

	public static final String AUTH_TOKEN_SHARED_SECRET = PropsUtil.get(PropsKeys.AUTH_TOKEN_SHARED_SECRET);

	public static final String[] AUTH_VERIFIER_PIPELINE = PropsUtil.getArray(PropsKeys.AUTH_VERIFIER_PIPELINE);

	public static final boolean AUTO_DEPLOY_COPY_COMMONS_LOGGING = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.AUTO_DEPLOY_COPY_COMMONS_LOGGING));

	public static final boolean AUTO_DEPLOY_COPY_LOG4J = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.AUTO_DEPLOY_COPY_LOG4J));

	public static final boolean AUTO_DEPLOY_CUSTOM_PORTLET_XML = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.AUTO_DEPLOY_CUSTOM_PORTLET_XML));

	public static final String AUTO_DEPLOY_DEFAULT_DEST_DIR = PropsUtil.get(PropsKeys.AUTO_DEPLOY_DEFAULT_DEST_DIR);

	public static final String AUTO_DEPLOY_DEPLOY_DIR = PropsUtil.get(PropsKeys.AUTO_DEPLOY_DEPLOY_DIR);

	public static final String AUTO_DEPLOY_DEST_DIR = PropsUtil.get(PropsKeys.AUTO_DEPLOY_DEST_DIR);

	public static final boolean AUTO_DEPLOY_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.AUTO_DEPLOY_ENABLED));

	public static final int AUTO_DEPLOY_INTERVAL = GetterUtil.getInteger(PropsUtil.get(PropsKeys.AUTO_DEPLOY_INTERVAL));

	public static final String AUTO_DEPLOY_JBOSS_PREFIX = PropsUtil.get(PropsKeys.AUTO_DEPLOY_JBOSS_PREFIX);

	public static final String AUTO_DEPLOY_TOMCAT_CONF_DIR = PropsUtil.get(PropsKeys.AUTO_DEPLOY_TOMCAT_CONF_DIR);

	public static final String AUTO_DEPLOY_TOMCAT_DEST_DIR = PropsUtil.get(PropsKeys.AUTO_DEPLOY_TOMCAT_DEST_DIR);

	public static final String AUTO_DEPLOY_TOMCAT_LIB_DIR = PropsUtil.get(PropsKeys.AUTO_DEPLOY_TOMCAT_LIB_DIR);

	public static final boolean AUTO_DEPLOY_UNPACK_WAR = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.AUTO_DEPLOY_UNPACK_WAR));

	public static final String AUTO_DEPLOY_WEBSPHERE_WSADMIN_APP_MANAGER_QUERY = PropsUtil.get(PropsKeys.AUTO_DEPLOY_WEBSPHERE_WSADMIN_APP_MANAGER_QUERY);

	public static final String[] AUTO_LOGIN_HOOKS = PropsUtil.getArray(PropsKeys.AUTO_LOGIN_HOOKS);

	public static final boolean BASIC_AUTH_PASSWORD_REQUIRED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.BASIC_AUTH_PASSWORD_REQUIRED));

	public static final String BLOGS_DISPLAY_TEMPLATES_CONFIG = PropsUtil.get(PropsKeys.BLOGS_DISPLAY_TEMPLATES_CONFIG);

	public static final String BLOGS_EMAIL_FROM_ADDRESS = PropsUtil.get(PropsKeys.BLOGS_EMAIL_FROM_ADDRESS);

	public static final String BLOGS_EMAIL_FROM_NAME = PropsUtil.get(PropsKeys.BLOGS_EMAIL_FROM_NAME);

	public static final boolean BLOGS_ENTRY_COMMENTS_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.BLOGS_ENTRY_COMMENTS_ENABLED));

	public static final int[] BLOGS_ENTRY_PAGE_DELTA_VALUES = GetterUtil.getIntegerValues(PropsUtil.getArray(PropsKeys.BLOGS_ENTRY_PAGE_DELTA_VALUES));

	public static final boolean BLOGS_ENTRY_PREVIOUS_AND_NEXT_NAVIGATION_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.BLOGS_ENTRY_PREVIOUS_AND_NEXT_NAVIGATION_ENABLED));

	public static final int BLOGS_LINKBACK_EXCERPT_LENGTH = GetterUtil.getInteger(PropsUtil.get(PropsKeys.BLOGS_LINKBACK_EXCERPT_LENGTH));

	public static final int BLOGS_PAGE_ABSTRACT_LENGTH = GetterUtil.getInteger(PropsUtil.get(PropsKeys.BLOGS_PAGE_ABSTRACT_LENGTH));

	public static final boolean BLOGS_PING_GOOGLE_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.BLOGS_PING_GOOGLE_ENABLED));

	public static final boolean BLOGS_PINGBACK_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.BLOGS_PINGBACK_ENABLED));

	public static final boolean BLOGS_PUBLISH_TO_LIVE_BY_DEFAULT = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.BLOGS_PUBLISH_TO_LIVE_BY_DEFAULT));

	public static final int BLOGS_RSS_ABSTRACT_LENGTH = GetterUtil.getInteger(PropsUtil.get(PropsKeys.BLOGS_RSS_ABSTRACT_LENGTH));

	public static final boolean BLOGS_TRACKBACK_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.BLOGS_TRACKBACK_ENABLED));

	public static final String BOOKMARKS_EMAIL_FROM_ADDRESS = PropsUtil.get(PropsKeys.BOOKMARKS_EMAIL_FROM_ADDRESS);

	public static final String BOOKMARKS_EMAIL_FROM_NAME = PropsUtil.get(PropsKeys.BOOKMARKS_EMAIL_FROM_NAME);

	public static final String BREADCRUMB_DISPLAY_STYLE_DEFAULT = PropsUtil.get(PropsKeys.BREADCRUMB_DISPLAY_STYLE_DEFAULT);

	public static final String[] BREADCRUMB_DISPLAY_STYLE_OPTIONS = PropsUtil.getArray(PropsKeys.BREADCRUMB_DISPLAY_STYLE_OPTIONS);

	public static final boolean BREADCRUMB_SHOW_GUEST_GROUP = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.BREADCRUMB_SHOW_GUEST_GROUP));

	public static final boolean BREADCRUMB_SHOW_PARENT_GROUPS = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.BREADCRUMB_SHOW_PARENT_GROUPS));

	public static final boolean BROWSER_CACHE_DISABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.BROWSER_CACHE_DISABLED));

	public static final boolean BROWSER_CACHE_SIGNED_IN_DISABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.BROWSER_CACHE_SIGNED_IN_DISABLED));

	public static final String BROWSER_COMPATIBILITY_IE_VERSIONS = PropsUtil.get(PropsKeys.BROWSER_COMPATIBILITY_IE_VERSIONS);

	public static final String BROWSER_LAUNCHER_URL = PropsUtil.get(PropsKeys.BROWSER_LAUNCHER_URL);

	public static final boolean BUFFERED_INCREMENT_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.BUFFERED_INCREMENT_ENABLED));

	public static final int BUFFERED_INCREMENT_STANDBY_QUEUE_THRESHOLD = GetterUtil.getInteger(PropsUtil.get(PropsKeys.BUFFERED_INCREMENT_STANDBY_QUEUE_THRESHOLD));

	public static final long BUFFERED_INCREMENT_STANDBY_TIME_UPPER_LIMIT = GetterUtil.getLong(PropsUtil.get(PropsKeys.BUFFERED_INCREMENT_STANDBY_TIME_UPPER_LIMIT));

	public static final int CACHE_CONTENT_THRESHOLD_SIZE = GetterUtil.getInteger(PropsUtil.get(PropsKeys.CACHE_CONTENT_THRESHOLD_SIZE));

	public static final String CALENDAR_EMAIL_FROM_ADDRESS = PropsUtil.get(PropsKeys.CALENDAR_EMAIL_FROM_ADDRESS);

	public static final String CALENDAR_EMAIL_FROM_NAME = PropsUtil.get(PropsKeys.CALENDAR_EMAIL_FROM_NAME);

	public static final int CALENDAR_EVENT_CHECK_INTERVAL = GetterUtil.getInteger(PropsUtil.get(PropsKeys.CALENDAR_EVENT_CHECK_INTERVAL));

	public static final boolean CALENDAR_EVENT_COMMENTS_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.CALENDAR_EVENT_COMMENTS_ENABLED));

	public static final boolean CALENDAR_EVENT_RATINGS_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.CALENDAR_EVENT_RATINGS_ENABLED));

	public static boolean CAPTCHA_CHECK_PORTAL_CREATE_ACCOUNT = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.CAPTCHA_CHECK_PORTAL_CREATE_ACCOUNT));

	public static final boolean CAPTCHA_CHECK_PORTAL_SEND_PASSWORD = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.CAPTCHA_CHECK_PORTAL_SEND_PASSWORD));

	public static final boolean CAPTCHA_CHECK_PORTLET_MESSAGE_BOARDS_EDIT_CATEGORY = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.CAPTCHA_CHECK_PORTLET_MESSAGE_BOARDS_EDIT_CATEGORY));

	public static final boolean CAPTCHA_CHECK_PORTLET_MESSAGE_BOARDS_EDIT_MESSAGE = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.CAPTCHA_CHECK_PORTLET_MESSAGE_BOARDS_EDIT_MESSAGE));

	public static final String CAPTCHA_ENGINE_IMPL = PropsUtil.get(PropsKeys.CAPTCHA_ENGINE_IMPL);

	public static final String CAPTCHA_ENGINE_RECAPTCHA_KEY_PRIVATE = PropsUtil.get(PropsKeys.CAPTCHA_ENGINE_RECAPTCHA_KEY_PRIVATE);

	public static final String CAPTCHA_ENGINE_RECAPTCHA_KEY_PUBLIC = PropsUtil.get(PropsKeys.CAPTCHA_ENGINE_RECAPTCHA_KEY_PUBLIC);

	public static final String CAPTCHA_ENGINE_RECAPTCHA_URL_NOSCRIPT = PropsUtil.get(PropsKeys.CAPTCHA_ENGINE_RECAPTCHA_URL_NOSCRIPT);

	public static final String CAPTCHA_ENGINE_RECAPTCHA_URL_SCRIPT = PropsUtil.get(PropsKeys.CAPTCHA_ENGINE_RECAPTCHA_URL_SCRIPT);

	public static final String CAPTCHA_ENGINE_RECAPTCHA_URL_VERIFY = PropsUtil.get(PropsKeys.CAPTCHA_ENGINE_RECAPTCHA_URL_VERIFY);

	public static final String[] CAPTCHA_ENGINE_SIMPLECAPTCHA_BACKGROUND_PRODUCERS = PropsUtil.getArray(PropsKeys.CAPTCHA_ENGINE_SIMPLECAPTCHA_BACKGROUND_PRODUCERS);

	public static final String[] CAPTCHA_ENGINE_SIMPLECAPTCHA_GIMPY_RENDERERS = PropsUtil.getArray(PropsKeys.CAPTCHA_ENGINE_SIMPLECAPTCHA_GIMPY_RENDERERS);

	public static final int CAPTCHA_ENGINE_SIMPLECAPTCHA_HEIGHT = GetterUtil.getInteger(PropsUtil.get(PropsKeys.CAPTCHA_ENGINE_SIMPLECAPTCHA_HEIGHT));

	public static final String[] CAPTCHA_ENGINE_SIMPLECAPTCHA_NOISE_PRODUCERS = PropsUtil.getArray(PropsKeys.CAPTCHA_ENGINE_SIMPLECAPTCHA_NOISE_PRODUCERS);

	public static final String[] CAPTCHA_ENGINE_SIMPLECAPTCHA_TEXT_PRODUCERS = PropsUtil.getArray(PropsKeys.CAPTCHA_ENGINE_SIMPLECAPTCHA_TEXT_PRODUCERS);

	public static final int CAPTCHA_ENGINE_SIMPLECAPTCHA_WIDTH = GetterUtil.getInteger(PropsUtil.get(PropsKeys.CAPTCHA_ENGINE_SIMPLECAPTCHA_WIDTH));

	public static final String[] CAPTCHA_ENGINE_SIMPLECAPTCHA_WORD_RENDERERS = PropsUtil.getArray(PropsKeys.CAPTCHA_ENGINE_SIMPLECAPTCHA_WORD_RENDERERS);

	public static final int CAPTCHA_MAX_CHALLENGES = GetterUtil.getInteger(PropsUtil.get(PropsKeys.CAPTCHA_MAX_CHALLENGES));

	public static final boolean CAS_AUTH_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.CAS_AUTH_ENABLED));

	public static final boolean CAS_IMPORT_FROM_LDAP = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.CAS_IMPORT_FROM_LDAP));

	public static final String CAS_LOGIN_URL = PropsUtil.get(PropsKeys.CAS_LOGIN_URL);

	public static final boolean CAS_LOGOUT_ON_SESSION_EXPIRATION = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.CAS_LOGOUT_ON_SESSION_EXPIRATION));

	public static final String CAS_LOGOUT_URL = PropsUtil.get(PropsKeys.CAS_LOGOUT_URL);

	public static final String CAS_NO_SUCH_USER_REDIRECT_URL = PropsUtil.get(PropsKeys.CAS_NO_SUCH_USER_REDIRECT_URL);

	public static final String CAS_SERVER_NAME = PropsUtil.get(PropsKeys.CAS_SERVER_NAME);

	public static final String CAS_SERVER_URL = PropsUtil.get(PropsKeys.CAS_SERVER_URL);

	public static final String CAS_SERVICE_URL = PropsUtil.get(PropsKeys.CAS_SERVICE_URL);

	public static final boolean CDN_DYNAMIC_RESOURCES_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.CDN_DYNAMIC_RESOURCES_ENABLED));

	public static final String CDN_HOST_HTTP = PropsUtil.get(PropsKeys.CDN_HOST_HTTP);

	public static final String CDN_HOST_HTTPS = PropsUtil.get(PropsKeys.CDN_HOST_HTTPS);

	public static final boolean CLUSTER_EXECUTOR_DEBUG_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.CLUSTER_EXECUTOR_DEBUG_ENABLED));

	public static final String CLUSTER_LINK_AUTODETECT_ADDRESS = PropsUtil.get(PropsKeys.CLUSTER_LINK_AUTODETECT_ADDRESS);

	public static final String[] CLUSTER_LINK_CHANNEL_SYSTEM_PROPERTIES = PropsUtil.getArray(PropsKeys.CLUSTER_LINK_CHANNEL_SYSTEM_PROPERTIES);

	public static final boolean CLUSTER_LINK_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.CLUSTER_LINK_ENABLED));

	public static final String[] COMBO_ALLOWED_FILE_EXTENSIONS = PropsUtil.getArray(PropsKeys.COMBO_ALLOWED_FILE_EXTENSIONS);

	public static final boolean COMBO_CHECK_TIMESTAMP = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMBO_CHECK_TIMESTAMP));

	public static final long COMBO_CHECK_TIMESTAMP_INTERVAL = GetterUtil.getLong(PropsUtil.get(PropsKeys.COMBO_CHECK_TIMESTAMP_INTERVAL));

	public static final String COMPANY_DEFAULT_HOME_URL = PropsUtil.get(PropsKeys.COMPANY_DEFAULT_HOME_URL);

	public static String COMPANY_DEFAULT_LOCALE = PropsUtil.get(PropsKeys.COMPANY_DEFAULT_LOCALE);

	public static String COMPANY_DEFAULT_NAME = PropsUtil.get(PropsKeys.COMPANY_DEFAULT_NAME);

	public static String COMPANY_DEFAULT_TIME_ZONE = PropsUtil.get(PropsKeys.COMPANY_DEFAULT_TIME_ZONE);

	public static String COMPANY_DEFAULT_WEB_ID = PropsUtil.get(PropsKeys.COMPANY_DEFAULT_WEB_ID);

	public static final boolean COMPANY_LOGIN_PREPOPULATE_DOMAIN = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_LOGIN_PREPOPULATE_DOMAIN));

	public static final boolean COMPANY_SECURITY_AUTH_REQUIRES_HTTPS = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS));

	public static final String COMPANY_SECURITY_AUTH_TYPE = PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_TYPE);

	public static final boolean COMPANY_SECURITY_AUTO_LOGIN = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTO_LOGIN));

	public static final int COMPANY_SECURITY_AUTO_LOGIN_MAX_AGE = GetterUtil.getInteger(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTO_LOGIN_MAX_AGE), CookieKeys.MAX_AGE);

	public static final boolean COMPANY_SECURITY_LOGIN_FORM_AUTOCOMPLETE = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_LOGIN_FORM_AUTOCOMPLETE));

	public static final boolean COMPANY_SECURITY_SEND_PASSWORD = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_SEND_PASSWORD));

	public static final boolean COMPANY_SECURITY_SEND_PASSWORD_RESET_LINK = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_SEND_PASSWORD_RESET_LINK));

	public static final boolean COMPANY_SECURITY_SITE_LOGO = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_SITE_LOGO));

	public static final boolean COMPANY_SECURITY_STRANGERS = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_STRANGERS));

	public static final String COMPANY_SECURITY_STRANGERS_URL = PropsUtil.get(PropsKeys.COMPANY_SECURITY_STRANGERS_URL);

	public static final boolean COMPANY_SECURITY_STRANGERS_VERIFY = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_STRANGERS_VERIFY));

	public static final boolean COMPANY_SECURITY_STRANGERS_WITH_MX = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_STRANGERS_WITH_MX));

	public static String[] COMPANY_SETTINGS_FORM_AUTHENTICATION = PropsUtil.getArray(PropsKeys.COMPANY_SETTINGS_FORM_AUTHENTICATION);

	public static String[] COMPANY_SETTINGS_FORM_CONFIGURATION = PropsUtil.getArray(PropsKeys.COMPANY_SETTINGS_FORM_CONFIGURATION);

	public static String[] COMPANY_SETTINGS_FORM_IDENTIFICATION = PropsUtil.getArray(PropsKeys.COMPANY_SETTINGS_FORM_IDENTIFICATION);

	public static String[] COMPANY_SETTINGS_FORM_MISCELLANEOUS = PropsUtil.getArray(PropsKeys.COMPANY_SETTINGS_FORM_MISCELLANEOUS);

	public static final String CONTROL_PANEL_DEFAULT_ENTRY_CLASS = PropsUtil.get(PropsKeys.CONTROL_PANEL_DEFAULT_ENTRY_CLASS);

	public static final String CONTROL_PANEL_HOME_PORTLET_ID = PropsUtil.get(PropsKeys.CONTROL_PANEL_HOME_PORTLET_ID);

	public static final String CONTROL_PANEL_LAYOUT_FRIENDLY_URL = PropsUtil.get(PropsKeys.CONTROL_PANEL_LAYOUT_FRIENDLY_URL);

	public static final String CONTROL_PANEL_LAYOUT_NAME = PropsUtil.get(PropsKeys.CONTROL_PANEL_LAYOUT_NAME);

	public static final String CONTROL_PANEL_LAYOUT_REGULAR_THEME_ID = PropsUtil.get(PropsKeys.CONTROL_PANEL_LAYOUT_REGULAR_THEME_ID);

	public static final int CONTROL_PANEL_NAVIGATION_MAX_SITES = GetterUtil.getInteger(PropsUtil.get(PropsKeys.CONTROL_PANEL_NAVIGATION_MAX_SITES));

	public static String[] CONVERT_PROCESSES = PropsUtil.getArray(PropsKeys.CONVERT_PROCESSES);

	public static final int COUNTER_INCREMENT = GetterUtil.getInteger(PropsUtil.get(PropsKeys.COUNTER_INCREMENT));

	public static final String CUSTOM_SQL_FUNCTION_ISNOTNULL = PropsUtil.get(PropsKeys.CUSTOM_SQL_FUNCTION_ISNOTNULL);

	public static final String CUSTOM_SQL_FUNCTION_ISNULL = PropsUtil.get(PropsKeys.CUSTOM_SQL_FUNCTION_ISNULL);

	public static final boolean DATABASE_INDEXES_UPDATE_ON_STARTUP = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.DATABASE_INDEXES_UPDATE_ON_STARTUP));

	public static final String DATABASE_MYSQL_ENGINE = PropsUtil.get(PropsKeys.DATABASE_MYSQL_ENGINE);

	public static final String DEFAULT_ADMIN_EMAIL_ADDRESS_PREFIX = PropsUtil.get(PropsKeys.DEFAULT_ADMIN_EMAIL_ADDRESS_PREFIX);

	public static String DEFAULT_ADMIN_FIRST_NAME = PropsUtil.get(PropsKeys.DEFAULT_ADMIN_FIRST_NAME);

	public static String DEFAULT_ADMIN_LAST_NAME = PropsUtil.get(PropsKeys.DEFAULT_ADMIN_LAST_NAME);

	public static final String DEFAULT_ADMIN_MIDDLE_NAME = PropsUtil.get(PropsKeys.DEFAULT_ADMIN_MIDDLE_NAME);

	public static final String DEFAULT_ADMIN_PASSWORD = PropsUtil.get(PropsKeys.DEFAULT_ADMIN_PASSWORD);

	public static String DEFAULT_ADMIN_SCREEN_NAME = PropsUtil.get(PropsKeys.DEFAULT_ADMIN_SCREEN_NAME);

	public static final String DEFAULT_GUEST_PUBLIC_LAYOUT_COLUMN = PropsUtil.get(PropsKeys.DEFAULT_GUEST_PUBLIC_LAYOUT_COLUMN);

	public static final String DEFAULT_GUEST_PUBLIC_LAYOUT_FRIENDLY_URL = PropsUtil.get(PropsKeys.DEFAULT_GUEST_PUBLIC_LAYOUT_FRIENDLY_URL);

	public static final String DEFAULT_GUEST_PUBLIC_LAYOUT_NAME = PropsUtil.get(PropsKeys.DEFAULT_GUEST_PUBLIC_LAYOUT_NAME);

	public static final String DEFAULT_GUEST_PUBLIC_LAYOUT_REGULAR_COLOR_SCHEME_ID = PropsUtil.get(PropsKeys.DEFAULT_GUEST_PUBLIC_LAYOUT_REGULAR_COLOR_SCHEME_ID);

	public static final String DEFAULT_GUEST_PUBLIC_LAYOUT_REGULAR_THEME_ID = PropsUtil.get(PropsKeys.DEFAULT_GUEST_PUBLIC_LAYOUT_REGULAR_THEME_ID);

	public static final String DEFAULT_GUEST_PUBLIC_LAYOUT_TEMPLATE_ID = PropsUtil.get(PropsKeys.DEFAULT_GUEST_PUBLIC_LAYOUT_TEMPLATE_ID);

	public static final String DEFAULT_GUEST_PUBLIC_LAYOUT_WAP_COLOR_SCHEME_ID = PropsUtil.get(PropsKeys.DEFAULT_GUEST_PUBLIC_LAYOUT_WAP_COLOR_SCHEME_ID);

	public static final String DEFAULT_GUEST_PUBLIC_LAYOUT_WAP_THEME_ID = PropsUtil.get(PropsKeys.DEFAULT_GUEST_PUBLIC_LAYOUT_WAP_THEME_ID);

	public static final String DEFAULT_GUEST_PUBLIC_LAYOUTS_LAR = PropsUtil.get(PropsKeys.DEFAULT_GUEST_PUBLIC_LAYOUTS_LAR);

	public static String DEFAULT_LANDING_PAGE_PATH = PropsUtil.get(PropsKeys.DEFAULT_LANDING_PAGE_PATH);

	public static final String DEFAULT_LAYOUT_TEMPLATE_ID = PropsUtil.get(PropsKeys.DEFAULT_LAYOUT_TEMPLATE_ID);

	public static final String DEFAULT_LOGOUT_PAGE_PATH = PropsUtil.get(PropsKeys.DEFAULT_LOGOUT_PAGE_PATH);

	public static final String DEFAULT_REGULAR_COLOR_SCHEME_ID = PropsUtil.get(PropsKeys.DEFAULT_REGULAR_COLOR_SCHEME_ID);

	public static final String DEFAULT_REGULAR_THEME_ID = PropsUtil.get(PropsKeys.DEFAULT_REGULAR_THEME_ID);

	public static final String DEFAULT_USER_PRIVATE_LAYOUT_COLUMN = PropsUtil.get(PropsKeys.DEFAULT_USER_PRIVATE_LAYOUT_COLUMN);

	public static final String DEFAULT_USER_PRIVATE_LAYOUT_FRIENDLY_URL = PropsUtil.get(PropsKeys.DEFAULT_USER_PRIVATE_LAYOUT_FRIENDLY_URL);

	public static final String DEFAULT_USER_PRIVATE_LAYOUT_NAME = PropsUtil.get(PropsKeys.DEFAULT_USER_PRIVATE_LAYOUT_NAME);

	public static final String DEFAULT_USER_PRIVATE_LAYOUT_REGULAR_COLOR_SCHEME_ID = PropsUtil.get(PropsKeys.DEFAULT_USER_PRIVATE_LAYOUT_REGULAR_COLOR_SCHEME_ID);

	public static final String DEFAULT_USER_PRIVATE_LAYOUT_REGULAR_THEME_ID = PropsUtil.get(PropsKeys.DEFAULT_USER_PRIVATE_LAYOUT_REGULAR_THEME_ID);

	public static final String DEFAULT_USER_PRIVATE_LAYOUT_TEMPLATE_ID = PropsUtil.get(PropsKeys.DEFAULT_USER_PRIVATE_LAYOUT_TEMPLATE_ID);

	public static final String DEFAULT_USER_PRIVATE_LAYOUT_WAP_COLOR_SCHEME_ID = PropsUtil.get(PropsKeys.DEFAULT_USER_PRIVATE_LAYOUT_WAP_COLOR_SCHEME_ID);

	public static final String DEFAULT_USER_PRIVATE_LAYOUT_WAP_THEME_ID = PropsUtil.get(PropsKeys.DEFAULT_USER_PRIVATE_LAYOUT_WAP_THEME_ID);

	public static final String DEFAULT_USER_PRIVATE_LAYOUTS_LAR = PropsUtil.get(PropsKeys.DEFAULT_USER_PRIVATE_LAYOUTS_LAR);

	public static final String DEFAULT_USER_PUBLIC_LAYOUT_COLUMN = PropsUtil.get(PropsKeys.DEFAULT_USER_PUBLIC_LAYOUT_COLUMN);

	public static final String DEFAULT_USER_PUBLIC_LAYOUT_FRIENDLY_URL = PropsUtil.get(PropsKeys.DEFAULT_USER_PUBLIC_LAYOUT_FRIENDLY_URL);

	public static final String DEFAULT_USER_PUBLIC_LAYOUT_NAME = PropsUtil.get(PropsKeys.DEFAULT_USER_PUBLIC_LAYOUT_NAME);

	public static final String DEFAULT_USER_PUBLIC_LAYOUT_REGULAR_COLOR_SCHEME_ID = PropsUtil.get(PropsKeys.DEFAULT_USER_PUBLIC_LAYOUT_REGULAR_COLOR_SCHEME_ID);

	public static final String DEFAULT_USER_PUBLIC_LAYOUT_REGULAR_THEME_ID = PropsUtil.get(PropsKeys.DEFAULT_USER_PUBLIC_LAYOUT_REGULAR_THEME_ID);

	public static final String DEFAULT_USER_PUBLIC_LAYOUT_TEMPLATE_ID = PropsUtil.get(PropsKeys.DEFAULT_USER_PUBLIC_LAYOUT_TEMPLATE_ID);

	public static final String DEFAULT_USER_PUBLIC_LAYOUT_WAP_COLOR_SCHEME_ID = PropsUtil.get(PropsKeys.DEFAULT_USER_PUBLIC_LAYOUT_WAP_COLOR_SCHEME_ID);

	public static final String DEFAULT_USER_PUBLIC_LAYOUT_WAP_THEME_ID = PropsUtil.get(PropsKeys.DEFAULT_USER_PUBLIC_LAYOUT_WAP_THEME_ID);

	public static final String DEFAULT_USER_PUBLIC_LAYOUTS_LAR = PropsUtil.get(PropsKeys.DEFAULT_USER_PUBLIC_LAYOUTS_LAR);

	public static String DEFAULT_WAP_COLOR_SCHEME_ID = PropsUtil.get(PropsKeys.DEFAULT_WAP_COLOR_SCHEME_ID);

	public static String DEFAULT_WAP_THEME_ID = PropsUtil.get(PropsKeys.DEFAULT_WAP_THEME_ID);

	public static final boolean DIRECT_SERVLET_CONTEXT_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.DIRECT_SERVLET_CONTEXT_ENABLED));

	public static final boolean DIRECT_SERVLET_CONTEXT_RELOAD = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.DIRECT_SERVLET_CONTEXT_RELOAD));

	public static final boolean DISCUSSION_COMMENTS_ALWAYS_EDITABLE_BY_OWNER = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.DISCUSSION_COMMENTS_ALWAYS_EDITABLE_BY_OWNER));

	public static final boolean DISCUSSION_SUBSCRIBE_BY_DEFAULT = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.DISCUSSION_SUBSCRIBE_BY_DEFAULT));

	public static final String DISCUSSION_THREAD_VIEW = PropsUtil.get(PropsKeys.DISCUSSION_THREAD_VIEW);

	public static final String[] DL_COMPARABLE_FILE_EXTENSIONS = PropsUtil.getArray(PropsKeys.DL_COMPARABLE_FILE_EXTENSIONS);

	public static final String DL_DEFAULT_DISPLAY_VIEW = PropsUtil.get(PropsKeys.DL_DEFAULT_DISPLAY_VIEW);

	public static final String DL_DISPLAY_TEMPLATES_CONFIG = PropsUtil.get(PropsKeys.DL_DISPLAY_TEMPLATES_CONFIG);

	public static final String[] DL_DISPLAY_VIEWS = PropsUtil.getArray(PropsKeys.DL_DISPLAY_VIEWS);

	public static final String DL_EMAIL_FILE_ENTRY_ADDED_BODY = PropsUtil.get(PropsKeys.DL_EMAIL_FILE_ENTRY_ADDED_BODY);

	public static final boolean DL_EMAIL_FILE_ENTRY_ADDED_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.DL_EMAIL_FILE_ENTRY_ADDED_ENABLED));

	public static final String DL_EMAIL_FILE_ENTRY_ADDED_SUBJECT = PropsUtil.get(PropsKeys.DL_EMAIL_FILE_ENTRY_ADDED_SUBJECT);

	public static final String DL_EMAIL_FILE_ENTRY_UPDATED_BODY = PropsUtil.get(PropsKeys.DL_EMAIL_FILE_ENTRY_UPDATED_BODY);

	public static final boolean DL_EMAIL_FILE_ENTRY_UPDATED_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.DL_EMAIL_FILE_ENTRY_UPDATED_ENABLED));

	public static final String DL_EMAIL_FILE_ENTRY_UPDATED_SUBJECT = PropsUtil.get(PropsKeys.DL_EMAIL_FILE_ENTRY_UPDATED_SUBJECT);

	public static final String DL_EMAIL_FROM_ADDRESS = PropsUtil.get(PropsKeys.DL_EMAIL_FROM_ADDRESS);

	public static final String DL_EMAIL_FROM_NAME = PropsUtil.get(PropsKeys.DL_EMAIL_FROM_NAME);

	public static final boolean DL_FILE_ENTRY_BUFFERED_INCREMENT_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.BUFFERED_INCREMENT_ENABLED, new Filter("DLFileEntry")));

	public static final boolean DL_FILE_ENTRY_COMMENTS_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.DL_FILE_ENTRY_COMMENTS_ENABLED));

	public static final boolean DL_FILE_ENTRY_CONVERSIONS_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.DL_FILE_ENTRY_CONVERSIONS_ENABLED));

	public static boolean DL_FILE_ENTRY_DRAFTS_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.DL_FILE_ENTRY_DRAFTS_ENABLED));

	public static final int DL_FILE_ENTRY_LOCK_POLICY = GetterUtil.getInteger(PropsUtil.get(PropsKeys.DL_FILE_ENTRY_LOCK_POLICY));

	public static boolean DL_FILE_ENTRY_OPEN_IN_MS_OFFICE_MANUAL_CHECK_IN_REQUIRED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.DL_FILE_ENTRY_OPEN_IN_MS_OFFICE_MANUAL_CHECK_IN_REQUIRED));

	public static final String[] DL_FILE_ENTRY_PREVIEW_AUDIO_CONTAINERS = PropsUtil.getArray(PropsKeys.DL_FILE_ENTRY_PREVIEW_AUDIO_CONTAINERS);

	public static final String[] DL_FILE_ENTRY_PREVIEW_AUDIO_MIME_TYPES = PropsUtil.getArray(PropsKeys.DL_FILE_ENTRY_PREVIEW_AUDIO_MIME_TYPES);

	public static final boolean DL_FILE_ENTRY_PREVIEW_AUTO_CREATE_ON_UPGRADE = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.DL_FILE_ENTRY_PREVIEW_AUTO_CREATE_ON_UPGRADE));

	public static final int DL_FILE_ENTRY_PREVIEW_DOCUMENT_DEPTH = GetterUtil.getInteger(PropsUtil.get(PropsKeys.DL_FILE_ENTRY_PREVIEW_DOCUMENT_DEPTH));

	public static final int DL_FILE_ENTRY_PREVIEW_DOCUMENT_DPI = GetterUtil.getInteger(PropsUtil.get(PropsKeys.DL_FILE_ENTRY_PREVIEW_DOCUMENT_DPI));

	public static final int DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_HEIGHT = GetterUtil.getInteger(PropsUtil.get(PropsKeys.DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_HEIGHT));

	public static final int DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_WIDTH = GetterUtil.getInteger(PropsUtil.get(PropsKeys.DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_WIDTH));

	public static final boolean DL_FILE_ENTRY_PREVIEW_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.DL_FILE_ENTRY_PREVIEW_ENABLED));

	public static final boolean DL_FILE_ENTRY_PREVIEW_FORK_PROCESS_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.DL_FILE_ENTRY_PREVIEW_FORK_PROCESS_ENABLED));

	public static final String[] DL_FILE_ENTRY_PREVIEW_IMAGE_MIME_TYPES = PropsUtil.getArray(PropsKeys.DL_FILE_ENTRY_PREVIEW_IMAGE_MIME_TYPES);

	public static final String[] DL_FILE_ENTRY_PREVIEW_VIDEO_CONTAINERS = PropsUtil.getArray(PropsKeys.DL_FILE_ENTRY_PREVIEW_VIDEO_CONTAINERS);

	public static final int DL_FILE_ENTRY_PREVIEW_VIDEO_HEIGHT = GetterUtil.getInteger(PropsUtil.get(PropsKeys.DL_FILE_ENTRY_PREVIEW_VIDEO_HEIGHT));

	public static final String[] DL_FILE_ENTRY_PREVIEW_VIDEO_MIME_TYPES = PropsUtil.getArray(PropsKeys.DL_FILE_ENTRY_PREVIEW_VIDEO_MIME_TYPES);

	public static final int DL_FILE_ENTRY_PREVIEW_VIDEO_WIDTH = GetterUtil.getInteger(PropsUtil.get(PropsKeys.DL_FILE_ENTRY_PREVIEW_VIDEO_WIDTH));

	public static final boolean DL_FILE_ENTRY_PROCESSORS_TRIGGER_SYNCHRONOUSLY = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.DL_FILE_ENTRY_PROCESSORS_TRIGGER_SYNCHRONOUSLY));

	public static final int DL_FILE_ENTRY_THUMBNAIL_CUSTOM_1_MAX_HEIGHT = GetterUtil.getInteger(PropsUtil.get(PropsKeys.DL_FILE_ENTRY_THUMBNAIL_CUSTOM_1_MAX_HEIGHT));

	public static final int DL_FILE_ENTRY_THUMBNAIL_CUSTOM_1_MAX_WIDTH = GetterUtil.getInteger(PropsUtil.get(PropsKeys.DL_FILE_ENTRY_THUMBNAIL_CUSTOM_1_MAX_WIDTH));

	public static final int DL_FILE_ENTRY_THUMBNAIL_CUSTOM_2_MAX_HEIGHT = GetterUtil.getInteger(PropsUtil.get(PropsKeys.DL_FILE_ENTRY_THUMBNAIL_CUSTOM_2_MAX_HEIGHT));

	public static final int DL_FILE_ENTRY_THUMBNAIL_CUSTOM_2_MAX_WIDTH = GetterUtil.getInteger(PropsUtil.get(PropsKeys.DL_FILE_ENTRY_THUMBNAIL_CUSTOM_2_MAX_WIDTH));

	public static final boolean DL_FILE_ENTRY_THUMBNAIL_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.DL_FILE_ENTRY_THUMBNAIL_ENABLED));

	public static final int DL_FILE_ENTRY_THUMBNAIL_MAX_HEIGHT = GetterUtil.getInteger(PropsUtil.get(PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_HEIGHT));

	public static final int DL_FILE_ENTRY_THUMBNAIL_MAX_WIDTH = GetterUtil.getInteger(PropsUtil.get(PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_WIDTH));

	public static final int DL_FILE_ENTRY_THUMBNAIL_VIDEO_FRAME_PERCENTAGE = GetterUtil.getInteger(PropsUtil.get(PropsKeys.DL_FILE_ENTRY_THUMBNAIL_VIDEO_FRAME_PERCENTAGE));

	public static boolean DL_FILE_ENTRY_TYPE_IG_IMAGE_AUTO_CREATE_ON_UPGRADE = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.DL_FILE_ENTRY_TYPE_IG_IMAGE_AUTO_CREATE_ON_UPGRADE));

	public static final int DL_FILE_ENTRY_VERSION_POLICY = GetterUtil.getInteger(PropsUtil.get(PropsKeys.DL_FILE_ENTRY_VERSION_POLICY));

	public static final String[] DL_FILE_EXTENSIONS = PropsUtil.getArray(PropsKeys.DL_FILE_EXTENSIONS);

	public static final boolean DL_FILE_EXTENSIONS_STRICT_CHECK = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.DL_FILE_EXTENSIONS_STRICT_CHECK));

	public static final long DL_FILE_INDEXING_MAX_SIZE = GetterUtil.getLong(PropsUtil.get(PropsKeys.DL_FILE_INDEXING_MAX_SIZE));

	public static final long DL_FILE_MAX_SIZE = GetterUtil.getLong(PropsUtil.get(PropsKeys.DL_FILE_MAX_SIZE));

	public static final boolean DL_FILE_RANK_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.DL_FILE_RANK_ENABLED));

	public static final int DL_FILE_RANK_MAX_SIZE = GetterUtil.getInteger(PropsUtil.get(PropsKeys.DL_FILE_RANK_MAX_SIZE));

	public static final boolean DL_PUBLISH_TO_LIVE_BY_DEFAULT = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.DL_PUBLISH_TO_LIVE_BY_DEFAULT));

	public static final int DL_REPOSITORY_CMIS_DELETE_DEPTH = GetterUtil.getInteger(PropsUtil.get(PropsKeys.DL_REPOSITORY_CMIS_DELETE_DEPTH));

	public static final String[] DL_REPOSITORY_IMPL = PropsUtil.getArray(PropsKeys.DL_REPOSITORY_IMPL);

	public static final boolean DL_SHOW_LIFERAY_SYNC_MESSAGE = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.DL_SHOW_LIFERAY_SYNC_MESSAGE));

	public static boolean DL_STORE_ANTIVIRUS_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.DL_STORE_ANTIVIRUS_ENABLED), false);

	public static final String DL_STORE_ANTIVIRUS_IMPL = PropsUtil.get(PropsKeys.DL_STORE_ANTIVIRUS_IMPL);

	public static final String DL_STORE_CMIS_CREDENTIALS_PASSWORD = PropsUtil.get(PropsKeys.DL_STORE_CMIS_CREDENTIALS_PASSWORD);

	public static final String DL_STORE_CMIS_CREDENTIALS_USERNAME = PropsUtil.get(PropsKeys.DL_STORE_CMIS_CREDENTIALS_USERNAME);

	public static final String DL_STORE_CMIS_REPOSITORY_URL = PropsUtil.get(PropsKeys.DL_STORE_CMIS_REPOSITORY_URL);

	public static final String DL_STORE_CMIS_SYSTEM_ROOT_DIR = PropsUtil.get(PropsKeys.DL_STORE_CMIS_SYSTEM_ROOT_DIR);

	public static final String DL_STORE_FILE_SYSTEM_ROOT_DIR = PropsUtil.get(PropsKeys.DL_STORE_FILE_SYSTEM_ROOT_DIR);

	public static String DL_STORE_IMPL = PropsUtil.get(PropsKeys.DL_STORE_IMPL);

	public static final int DL_STORE_JCR_FETCH_DELAY = GetterUtil.getInteger(PropsUtil.get(PropsKeys.DL_STORE_JCR_FETCH_DELAY));

	public static final int DL_STORE_JCR_FETCH_MAX_FAILURES = GetterUtil.getInteger(PropsUtil.get(PropsKeys.DL_STORE_JCR_FETCH_MAX_FAILURES));

	public static final boolean DL_STORE_JCR_MOVE_VERSION_LABELS = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.DL_STORE_JCR_MOVE_VERSION_LABELS));

	public static final int DL_STORE_S3_TEMP_DIR_CLEAN_UP_EXPUNGE = GetterUtil.getInteger(PropsUtil.get(PropsKeys.DL_STORE_S3_TEMP_DIR_CLEAN_UP_EXPUNGE));

	public static final int DL_STORE_S3_TEMP_DIR_CLEAN_UP_FREQUENCY = GetterUtil.getInteger(PropsUtil.get(PropsKeys.DL_STORE_S3_TEMP_DIR_CLEAN_UP_FREQUENCY));

	public static String[] DOCKBAR_ADD_PORTLETS = PropsUtil.getArray(PropsKeys.DOCKBAR_ADD_PORTLETS);

	public static final boolean DOCKBAR_ADMINISTRATIVE_LINKS_SHOW_IN_POP_UP = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.DOCKBAR_ADMINISTRATIVE_LINKS_SHOW_IN_POP_UP));

	public static final boolean DOCKBAR_SHOW_SITE_CONTENT_ICON = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.DOCKBAR_SHOW_SITE_CONTENT_ICON));

	public static final boolean DYNAMIC_DATA_LISTS_RECORD_SET_FORCE_AUTOGENERATE_KEY = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.DYNAMIC_DATA_LISTS_RECORD_SET_FORCE_AUTOGENERATE_KEY));

	public static final String DYNAMIC_DATA_LISTS_STORAGE_TYPE = PropsUtil.get(PropsKeys.DYNAMIC_DATA_LISTS_STORAGE_TYPE);

	public static final boolean DYNAMIC_DATA_MAPPING_STRUCTURE_FORCE_AUTOGENERATE_KEY = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.DYNAMIC_DATA_MAPPING_STRUCTURE_FORCE_AUTOGENERATE_KEY));

	public static final String[] DYNAMIC_DATA_MAPPING_STRUCTURE_PRIVATE_FIELD_NAMES = PropsUtil.getArray(PropsKeys.DYNAMIC_DATA_MAPPING_STRUCTURE_PRIVATE_FIELD_NAMES);

	public static final String DYNAMIC_DATA_MAPPING_TEMPLATE_LANGUAGE_DEFAULT = PropsUtil.get(PropsKeys.DYNAMIC_DATA_MAPPING_TEMPLATE_LANGUAGE_DEFAULT);

	public static final String[] DYNAMIC_RESOURCE_SERVLET_ALLOWED_PATHS = PropsUtil.getArray(PropsKeys.DYNAMIC_RESOURCE_SERVLET_ALLOWED_PATHS);

	public static final String EDITOR_WYSIWYG_DEFAULT = PropsUtil.get(PropsKeys.EDITOR_WYSIWYG_DEFAULT);

	public static final boolean EHCACHE_BLOCKING_CACHE_ALLOWED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.EHCACHE_BLOCKING_CACHE_ALLOWED));

	public static final String EHCACHE_BOOTSTRAP_CACHE_LOADER_FACTORY = PropsUtil.get(PropsKeys.EHCACHE_BOOTSTRAP_CACHE_LOADER_FACTORY);

	public static final String EHCACHE_CACHE_EVENT_LISTENER_FACTORY = PropsUtil.get(PropsKeys.EHCACHE_CACHE_EVENT_LISTENER_FACTORY);

	public static final String EHCACHE_CACHE_MANAGER_PEER_PROVIDER_FACTORY = PropsUtil.get(PropsKeys.EHCACHE_CACHE_MANAGER_PEER_PROVIDER_FACTORY);

	public static final boolean EHCACHE_CLUSTER_LINK_REPLICATION_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.EHCACHE_CLUSTER_LINK_REPLICATION_ENABLED));

	public static final boolean EHCACHE_PORTAL_CACHE_MANAGER_JMX_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.EHCACHE_PORTAL_CACHE_MANAGER_JMX_ENABLED));

	public static final boolean EHCACHE_STATISTICS_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.EHCACHE_STATISTICS_ENABLED));

	public static final String FACEBOOK_CONNECT_APP_ID = PropsUtil.get(PropsKeys.FACEBOOK_CONNECT_APP_ID);

	public static final String FACEBOOK_CONNECT_APP_SECRET = PropsUtil.get(PropsKeys.FACEBOOK_CONNECT_APP_SECRET);

	public static final boolean FACEBOOK_CONNECT_AUTH_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.FACEBOOK_CONNECT_AUTH_ENABLED));

	public static final String FACEBOOK_CONNECT_GRAPH_URL = PropsUtil.get(PropsKeys.FACEBOOK_CONNECT_GRAPH_URL);

	public static final String FACEBOOK_CONNECT_OAUTH_AUTH_URL = PropsUtil.get(PropsKeys.FACEBOOK_CONNECT_OAUTH_AUTH_URL);

	public static final String FACEBOOK_CONNECT_OAUTH_REDIRECT_URL = PropsUtil.get(PropsKeys.FACEBOOK_CONNECT_OAUTH_REDIRECT_URL);

	public static final String FACEBOOK_CONNECT_OAUTH_TOKEN_URL = PropsUtil.get(PropsKeys.FACEBOOK_CONNECT_OAUTH_TOKEN_URL);

	public static final boolean FACEBOOK_CONNECT_VERIFIED_ACCOUNT_REQUIRED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.FACEBOOK_CONNECT_VERIFIED_ACCOUNT_REQUIRED));

	public static final String[] FIELD_EDITABLE_COM_LIFERAY_PORTAL_MODEL_USER_EMAILADDRESS = PropsUtil.getArray(PropsKeys.FIELD_EDITABLE_COM_LIFERAY_PORTAL_MODEL_USER_EMAILADDRESS);

	public static final String[] FIELD_EDITABLE_COM_LIFERAY_PORTAL_MODEL_USER_SCREENNAME = PropsUtil.getArray(PropsKeys.FIELD_EDITABLE_COM_LIFERAY_PORTAL_MODEL_USER_SCREENNAME);

	public static boolean FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_CONTACT_BIRTHDAY = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_CONTACT_BIRTHDAY));

	public static boolean FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_CONTACT_MALE = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_CONTACT_MALE));

	public static boolean FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_ORGANIZATION_STATUS = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_ORGANIZATION_STATUS));

	public static final boolean FLAGS_GUEST_USERS_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.FLAGS_GUEST_USERS_ENABLED));

	public static final String[] FLAGS_REASONS = PropsUtil.getArray(PropsKeys.FLAGS_REASONS);

	public static final boolean FREEMARKER_ENGINE_LOCALIZED_LOOKUP = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.FREEMARKER_ENGINE_LOCALIZED_LOOKUP));

	public static final String FREEMARKER_ENGINE_MACRO_LIBRARY = PropsUtil.get(PropsKeys.FREEMARKER_ENGINE_MACRO_LIBRARY);

	public static final int FREEMARKER_ENGINE_RESOURCE_MODIFICATION_CHECK_INTERVAL = GetterUtil.getInteger(PropsUtil.get(PropsKeys.FREEMARKER_ENGINE_RESOURCE_MODIFICATION_CHECK_INTERVAL));

	public static final String[] FREEMARKER_ENGINE_RESTRICTED_CLASSES = PropsUtil.getArray(PropsKeys.FREEMARKER_ENGINE_RESTRICTED_CLASSES);

	public static final String[] FREEMARKER_ENGINE_RESTRICTED_PACKAGES = PropsUtil.getArray(PropsKeys.FREEMARKER_ENGINE_RESTRICTED_PACKAGES);

	public static final String FREEMARKER_ENGINE_TEMPLATE_EXCEPTION_HANDLER = PropsUtil.get(PropsKeys.FREEMARKER_ENGINE_TEMPLATE_EXCEPTION_HANDLER);

	public static final String[] FREEMARKER_ENGINE_TEMPLATE_PARSERS = PropsUtil.getArray(PropsKeys.FREEMARKER_ENGINE_TEMPLATE_PARSERS);

	public static final String[] GLOBAL_SHUTDOWN_EVENTS = PropsUtil.getArray(PropsKeys.GLOBAL_SHUTDOWN_EVENTS);

	public static final String[] GLOBAL_STARTUP_EVENTS = PropsUtil.getArray(PropsKeys.GLOBAL_STARTUP_EVENTS);

	public static final String GOOGLE_GADGET_SERVLET_MAPPING = PropsUtil.get(PropsKeys.GOOGLE_GADGET_SERVLET_MAPPING);

	public static final int GZIP_COMPRESSION_LEVEL = GetterUtil.getInteger(PropsUtil.get(PropsKeys.GZIP_COMPRESSION_LEVEL));

	public static final boolean HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));

	public static final String HIBERNATE_DIALECT = PropsUtil.get(PropsKeys.HIBERNATE_DIALECT);

	public static final boolean HIBERNATE_GENERATE_STATISTICS = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.HIBERNATE_GENERATE_STATISTICS));

	public static final int HIBERNATE_JDBC_BATCH_SIZE = GetterUtil.getInteger(PropsUtil.get(PropsKeys.HIBERNATE_JDBC_BATCH_SIZE));

	public static final boolean HOT_UNDEPLOY_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.HOT_UNDEPLOY_ENABLED));

	public static final int HOT_UNDEPLOY_INTERVAL = GetterUtil.getInteger(PropsUtil.get(PropsKeys.HOT_UNDEPLOY_INTERVAL));

	public static final boolean HOT_UNDEPLOY_ON_REDEPLOY = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.HOT_UNDEPLOY_ON_REDEPLOY));

	public static final String HTTP_HEADER_VERSION_VERBOSITY = PropsUtil.get(PropsKeys.HTTP_HEADER_VERSION_VERBOSITY);

	public static final String IFRAME_PASSWORD_PASSWORD_TOKEN_ROLE = PropsUtil.get(PropsKeys.IFRAME_PASSWORD_PASSWORD_TOKEN_ROLE);

	public static final boolean IMAGE_AUTO_SCALE = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.IMAGE_AUTO_SCALE));

	public static final String IMAGE_HOOK_FILE_SYSTEM_ROOT_DIR = PropsUtil.get(PropsKeys.IMAGE_HOOK_FILE_SYSTEM_ROOT_DIR);

	public static String IMAGE_HOOK_IMPL = GetterUtil.getString(PropsUtil.get(PropsKeys.IMAGE_HOOK_IMPL));

	public static final boolean IMAGEMAGICK_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.IMAGEMAGICK_ENABLED));

	public static final String INDEX_DATE_FORMAT_PATTERN = PropsUtil.get(PropsKeys.INDEX_DATE_FORMAT_PATTERN);

	public static final boolean INDEX_DUMP_COMPRESSION_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.INDEX_DUMP_COMPRESSION_ENABLED));

	public static boolean INDEX_ON_STARTUP = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.INDEX_ON_STARTUP));

	public static final int INDEX_ON_STARTUP_DELAY = GetterUtil.getInteger(PropsUtil.get(PropsKeys.INDEX_ON_STARTUP_DELAY));

	public static final boolean INDEX_ON_UPGRADE = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.INDEX_ON_UPGRADE));

	public static boolean INDEX_READ_ONLY = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.INDEX_READ_ONLY));

	public static final int INDEX_SEARCH_LIMIT = GetterUtil.getInteger(PropsUtil.get(PropsKeys.INDEX_SEARCH_LIMIT));

	public static final boolean INDEX_WITH_THREAD = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.INDEX_WITH_THREAD));

	public static final boolean JAVADOC_MANAGER_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.JAVADOC_MANAGER_ENABLED));

	public static final boolean JAVASCRIPT_BAREBONE_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.JAVASCRIPT_BAREBONE_ENABLED));

	public static final String[] JAVASCRIPT_BUNDLE_IDS = PropsUtil.getArray(PropsKeys.JAVASCRIPT_BUNDLE_IDS);

	public static boolean JAVASCRIPT_FAST_LOAD = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.JAVASCRIPT_FAST_LOAD));

	public static final boolean JAVASCRIPT_LOG_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.JAVASCRIPT_LOG_ENABLED));

	public static final boolean JCR_WRAP_SESSION = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.JCR_WRAP_SESSION));

	public static final String JDBC_DEFAULT_DRIVER_CLASS_NAME = PropsUtil.get(PropsKeys.JDBC_DEFAULT_DRIVER_CLASS_NAME);

	public static final String JDBC_DEFAULT_JNDI_NAME = PropsUtil.get(PropsKeys.JDBC_DEFAULT_JNDI_NAME);

	public static final String JDBC_DEFAULT_LIFERAY_POOL_PROVIDER = PropsUtil.get(PropsKeys.JDBC_DEFAULT_LIFERAY_POOL_PROVIDER);

	public static final String JDBC_DEFAULT_PASSWORD = PropsUtil.get(PropsKeys.JDBC_DEFAULT_PASSWORD);

	public static final String JDBC_DEFAULT_URL = PropsUtil.get(PropsKeys.JDBC_DEFAULT_URL);

	public static final String JDBC_DEFAULT_USERNAME = PropsUtil.get(PropsKeys.JDBC_DEFAULT_USERNAME);

	public static final int JOURNAL_ARTICLE_CHECK_INTERVAL = GetterUtil.getInteger(PropsUtil.get(PropsKeys.JOURNAL_ARTICLE_CHECK_INTERVAL));

	public static final boolean JOURNAL_ARTICLE_COMMENTS_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.JOURNAL_ARTICLE_COMMENTS_ENABLED));

	public static final String[] JOURNAL_ARTICLE_CUSTOM_TOKENS = PropsUtil.getArray(PropsKeys.JOURNAL_ARTICLE_CUSTOM_TOKENS);

	public static final boolean JOURNAL_ARTICLE_EXPIRE_ALL_VERSIONS = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.JOURNAL_ARTICLE_EXPIRE_ALL_VERSIONS));

	public static final boolean JOURNAL_ARTICLE_FORCE_AUTOGENERATE_ID = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.JOURNAL_ARTICLE_FORCE_AUTOGENERATE_ID));

	public static String[] JOURNAL_ARTICLE_FORM_ADD = PropsUtil.getArray(PropsKeys.JOURNAL_ARTICLE_FORM_ADD);

	public static String[] JOURNAL_ARTICLE_FORM_DEFAULT_VALUES = PropsUtil.getArray(PropsKeys.JOURNAL_ARTICLE_FORM_DEFAULT_VALUES);

	public static String[] JOURNAL_ARTICLE_FORM_TRANSLATE = PropsUtil.getArray(PropsKeys.JOURNAL_ARTICLE_FORM_TRANSLATE);

	public static String[] JOURNAL_ARTICLE_FORM_UPDATE = PropsUtil.getArray(PropsKeys.JOURNAL_ARTICLE_FORM_UPDATE);

	public static final String JOURNAL_ARTICLE_STORAGE_TYPE = PropsUtil.get(PropsKeys.JOURNAL_ARTICLE_STORAGE_TYPE);

	public static final String JOURNAL_ARTICLE_TOKEN_PAGE_BREAK = PropsUtil.get(PropsKeys.JOURNAL_ARTICLE_TOKEN_PAGE_BREAK);

	public static final boolean JOURNAL_ARTICLE_VIEW_PERMISSION_CHECK_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.JOURNAL_ARTICLE_VIEW_PERMISSION_CHECK_ENABLED));

	public static final int[] JOURNAL_ARTICLES_PAGE_DELTA_VALUES = GetterUtil.getIntegerValues(PropsUtil.getArray(PropsKeys.JOURNAL_ARTICLES_PAGE_DELTA_VALUES));

	public static final boolean JOURNAL_ARTICLES_SEARCH_WITH_INDEX = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.JOURNAL_ARTICLES_SEARCH_WITH_INDEX));

	public static final boolean JOURNAL_CONTENT_SEARCH_SHOW_LISTED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.JOURNAL_CONTENT_SEARCH_SHOW_LISTED));

	public static final String JOURNAL_DEFAULT_DISPLAY_VIEW = PropsUtil.get(PropsKeys.JOURNAL_DEFAULT_DISPLAY_VIEW);

	public static final String[] JOURNAL_DISPLAY_VIEWS = PropsUtil.getArray(PropsKeys.JOURNAL_DISPLAY_VIEWS);

	public static final String JOURNAL_EMAIL_FROM_ADDRESS = PropsUtil.get(PropsKeys.JOURNAL_EMAIL_FROM_ADDRESS);

	public static final String JOURNAL_EMAIL_FROM_NAME = PropsUtil.get(PropsKeys.JOURNAL_EMAIL_FROM_NAME);

	public static final boolean JOURNAL_FEED_FORCE_AUTOGENERATE_ID = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.JOURNAL_FEED_FORCE_AUTOGENERATE_ID));

	public static final String JOURNAL_LAR_CREATION_STRATEGY = PropsUtil.get(PropsKeys.JOURNAL_LAR_CREATION_STRATEGY);

	public static final boolean JOURNAL_PUBLISH_TO_LIVE_BY_DEFAULT = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.JOURNAL_PUBLISH_TO_LIVE_BY_DEFAULT));

	public static final boolean JOURNAL_PUBLISH_VERSION_HISTORY_BY_DEFAULT = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.JOURNAL_PUBLISH_VERSION_HISTORY_BY_DEFAULT));

	public static final String[] JOURNAL_TEMPLATE_FREEMARKER_RESTRICTED_VARIABLES = PropsUtil.getArray(PropsKeys.JOURNAL_TEMPLATE_FREEMARKER_RESTRICTED_VARIABLES);

	public static final String[] JOURNAL_TEMPLATE_VELOCITY_RESTRICTED_VARIABLES = PropsUtil.getArray(PropsKeys.JOURNAL_TEMPLATE_VELOCITY_RESTRICTED_VARIABLES);

	public static final String[] JPA_CONFIGS = PropsUtil.getArray(PropsKeys.JPA_CONFIGS);

	public static final String JPA_DATABASE_PLATFORM = PropsUtil.get(PropsKeys.JPA_DATABASE_PLATFORM);

	public static final String JPA_DATABASE_TYPE = PropsUtil.get(PropsKeys.JPA_DATABASE_TYPE);

	public static final String JPA_LOAD_TIME_WEAVER = PropsUtil.get(PropsKeys.JPA_LOAD_TIME_WEAVER);

	public static final String JPA_PROVIDER = PropsUtil.get(PropsKeys.JPA_PROVIDER);

	public static final boolean JSON_DESERIALIZER_STRICT_MODE = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.JSON_DESERIALIZER_STRICT_MODE));

	public static final boolean JSON_SERVICE_AUTH_TOKEN_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.JSON_SERVICE_AUTH_TOKEN_ENABLED));

	public static final String[] JSON_SERVICE_AUTH_TOKEN_HOSTS_ALLOWED = PropsUtil.getArray(PropsKeys.JSON_SERVICE_AUTH_TOKEN_HOSTS_ALLOWED);

	public static final String[] JSON_SERVICE_INVALID_CLASS_NAMES = PropsUtil.getArray(PropsKeys.JSON_SERVICE_INVALID_CLASS_NAMES);

	public static final boolean JSON_WEB_SERVICE_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.JSON_WEB_SERVICE_ENABLED));

	public static final String[] JSONWS_WEB_SERVICE_INVALID_HTTP_METHODS = PropsUtil.getArray(PropsKeys.JSONWS_WEB_SERVICE_INVALID_HTTP_METHODS);

	public static final boolean JSONWS_WEB_SERVICE_STRICT_HTTP_METHOD = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.JSONWS_WEB_SERVICE_STRICT_HTTP_METHOD));

	public static final boolean LAYOUT_AJAX_RENDER_ENABLE = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.LAYOUT_AJAX_RENDER_ENABLE)) && !GetterUtil.getBoolean(PropsUtil.get(PropsKeys.SESSION_DISABLED));

	public static final String LAYOUT_CLONE_IMPL = PropsUtil.get(PropsKeys.LAYOUT_CLONE_IMPL);

	public static final boolean LAYOUT_COMMENTS_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.LAYOUT_COMMENTS_ENABLED));

	public static final boolean LAYOUT_DEFAULT_P_L_RESET = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.LAYOUT_DEFAULT_P_L_RESET));

	public static final String LAYOUT_DEFAULT_TEMPLATE_ID = PropsUtil.get(PropsKeys.LAYOUT_DEFAULT_TEMPLATE_ID);

	public static String[] LAYOUT_FORM_ADD = PropsUtil.getArray(PropsKeys.LAYOUT_FORM_ADD);

	public static String[] LAYOUT_FORM_UPDATE = PropsUtil.getArray(PropsKeys.LAYOUT_FORM_UPDATE);

	public static final String[] LAYOUT_FRIENDLY_URL_KEYWORDS = PropsUtil.getArray(PropsKeys.LAYOUT_FRIENDLY_URL_KEYWORDS);

	public static final String LAYOUT_FRIENDLY_URL_PAGE_NOT_FOUND = PropsUtil.get(PropsKeys.LAYOUT_FRIENDLY_URL_PAGE_NOT_FOUND);

	public static final String LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING = PropsUtil.get(PropsKeys.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING);

	public static final String LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING = PropsUtil.get(PropsKeys.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING);

	public static final String LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING = PropsUtil.get(PropsKeys.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING);

	public static final boolean LAYOUT_GUEST_SHOW_MAX_ICON = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.LAYOUT_GUEST_SHOW_MAX_ICON));

	public static final boolean LAYOUT_GUEST_SHOW_MIN_ICON = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.LAYOUT_GUEST_SHOW_MIN_ICON));

	public static final int LAYOUT_MANAGE_PAGES_INITIAL_CHILDREN = GetterUtil.getInteger(PropsUtil.get(PropsKeys.LAYOUT_MANAGE_PAGES_INITIAL_CHILDREN));

	public static final boolean LAYOUT_PARALLEL_RENDER_ENABLE = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.LAYOUT_PARALLEL_RENDER_ENABLE));

	public static final boolean LAYOUT_PARALLEL_RENDER_THREAD_POOL_ALLOW_CORE_THREAD_TIMEOUT = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.LAYOUT_PARALLEL_RENDER_THREAD_POOL_ALLOW_CORE_THREAD_TIMEOUT));

	public static final int LAYOUT_PARALLEL_RENDER_THREAD_POOL_CORE_THREAD_COUNT = GetterUtil.getInteger(PropsUtil.get(PropsKeys.LAYOUT_PARALLEL_RENDER_THREAD_POOL_CORE_THREAD_COUNT));

	public static final long LAYOUT_PARALLEL_RENDER_THREAD_POOL_KEEP_ALIVE_TIME = GetterUtil.getLong(PropsUtil.get(PropsKeys.LAYOUT_PARALLEL_RENDER_THREAD_POOL_KEEP_ALIVE_TIME));

	public static final int LAYOUT_PARALLEL_RENDER_THREAD_POOL_MAX_QUEUE_SIZE = GetterUtil.getInteger(PropsUtil.get(PropsKeys.LAYOUT_PARALLEL_RENDER_THREAD_POOL_MAX_QUEUE_SIZE));

	public static final int LAYOUT_PARALLEL_RENDER_THREAD_POOL_MAX_THREAD_COUNT = GetterUtil.getInteger(PropsUtil.get(PropsKeys.LAYOUT_PARALLEL_RENDER_THREAD_POOL_MAX_THREAD_COUNT));

	public static final int LAYOUT_PARALLEL_RENDER_TIMEOUT = GetterUtil.getInteger(PropsUtil.get(PropsKeys.LAYOUT_PARALLEL_RENDER_TIMEOUT));

	public static boolean LAYOUT_PROTOTYPE_LINK_ENABLED_DEFAULT = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.LAYOUT_PROTOTYPE_LINK_ENABLED_DEFAULT));

	public static final int LAYOUT_PROTOTYPE_MERGE_FAIL_THRESHOLD = GetterUtil.getInteger(PropsUtil.get(PropsKeys.LAYOUT_PROTOTYPE_MERGE_FAIL_THRESHOLD));

	public static final long LAYOUT_PROTOTYPE_MERGE_LOCK_MAX_TIME = GetterUtil.getLong(PropsUtil.get(PropsKeys.LAYOUT_PROTOTYPE_MERGE_LOCK_MAX_TIME)) * Time.SECOND;

	public static final boolean LAYOUT_REMEMBER_MAXIMIZED_WINDOW_STATE = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.LAYOUT_REMEMBER_MAXIMIZED_WINDOW_STATE));

	public static String[] LAYOUT_SET_FORM_UPDATE = PropsUtil.getArray(PropsKeys.LAYOUT_SET_FORM_UPDATE);

	public static final int LAYOUT_SET_PROTOTYPE_MERGE_FAIL_THRESHOLD = GetterUtil.getInteger(PropsUtil.get(PropsKeys.LAYOUT_SET_PROTOTYPE_MERGE_FAIL_THRESHOLD));

	public static final long LAYOUT_SET_PROTOTYPE_MERGE_LOCK_MAX_TIME = GetterUtil.getLong(PropsUtil.get(PropsKeys.LAYOUT_SET_PROTOTYPE_MERGE_LOCK_MAX_TIME)) * Time.SECOND;

	public static final boolean LAYOUT_SHOW_HTTP_STATUS = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.LAYOUT_SHOW_HTTP_STATUS));

	public static final boolean LAYOUT_SHOW_PORTLET_ACCESS_DENIED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.LAYOUT_SHOW_PORTLET_ACCESS_DENIED));

	public static final boolean LAYOUT_SHOW_PORTLET_INACTIVE = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.LAYOUT_SHOW_PORTLET_INACTIVE));

	public static String[] LAYOUT_STATIC_PORTLETS_ALL = PropsUtil.getArray(PropsKeys.LAYOUT_STATIC_PORTLETS_ALL);

	public static boolean LAYOUT_TEMPLATE_CACHE_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.LAYOUT_TEMPLATE_CACHE_ENABLED));

	public static String[] LAYOUT_TYPES = PropsUtil.getArray(PropsKeys.LAYOUT_TYPES);

	public static boolean LAYOUT_USER_PRIVATE_LAYOUTS_AUTO_CREATE = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.LAYOUT_USER_PRIVATE_LAYOUTS_AUTO_CREATE));

	public static boolean LAYOUT_USER_PRIVATE_LAYOUTS_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.LAYOUT_USER_PRIVATE_LAYOUTS_ENABLED));

	public static boolean LAYOUT_USER_PRIVATE_LAYOUTS_POWER_USER_REQUIRED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.LAYOUT_USER_PRIVATE_LAYOUTS_POWER_USER_REQUIRED));

	public static boolean LAYOUT_USER_PUBLIC_LAYOUTS_AUTO_CREATE = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.LAYOUT_USER_PUBLIC_LAYOUTS_AUTO_CREATE));

	public static boolean LAYOUT_USER_PUBLIC_LAYOUTS_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.LAYOUT_USER_PUBLIC_LAYOUTS_ENABLED));

	public static boolean LAYOUT_USER_PUBLIC_LAYOUTS_POWER_USER_REQUIRED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.LAYOUT_USER_PUBLIC_LAYOUTS_POWER_USER_REQUIRED));

	public static final String LDAP_ATTRS_TRANSFORMER_IMPL = PropsUtil.get(PropsKeys.LDAP_ATTRS_TRANSFORMER_IMPL);

	public static final boolean LDAP_AUTH_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.LDAP_AUTH_ENABLED));

	public static final boolean LDAP_EXPORT_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.LDAP_EXPORT_ENABLED));

	public static final boolean LDAP_EXPORT_GROUP_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.LDAP_EXPORT_GROUP_ENABLED));

	public static final boolean LDAP_IMPORT_CREATE_ROLE_PER_GROUP = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.LDAP_IMPORT_CREATE_ROLE_PER_GROUP));

	public static final boolean LDAP_IMPORT_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.LDAP_IMPORT_ENABLED));

	public static final boolean LDAP_IMPORT_GROUP_CACHE_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.LDAP_IMPORT_GROUP_CACHE_ENABLED));

	public static final long LDAP_IMPORT_INTERVAL = GetterUtil.getLong(PropsUtil.get(PropsKeys.LDAP_IMPORT_INTERVAL));

	public static final long LDAP_IMPORT_LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(PropsKeys.LDAP_IMPORT_LOCK_EXPIRATION_TIME));

	public static final boolean LDAP_IMPORT_USER_PASSWORD_AUTOGENERATED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.LDAP_IMPORT_USER_PASSWORD_AUTOGENERATED));

	public static final String LDAP_IMPORT_USER_PASSWORD_DEFAULT = GetterUtil.getString(PropsUtil.get(PropsKeys.LDAP_IMPORT_USER_PASSWORD_DEFAULT));

	public static final boolean LDAP_IMPORT_USER_PASSWORD_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.LDAP_IMPORT_USER_PASSWORD_ENABLED));

	public static final int LDAP_PAGE_SIZE = GetterUtil.getInteger(PropsUtil.get(PropsKeys.LDAP_PAGE_SIZE));

	public static final boolean LDAP_PASSWORD_POLICY_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.LDAP_PASSWORD_POLICY_ENABLED));

	public static final int LDAP_RANGE_SIZE = GetterUtil.getInteger(PropsUtil.get(PropsKeys.LDAP_RANGE_SIZE));

	public static final String[] LDAP_USER_IGNORE_ATTRIBUTES = PropsUtil.getArray(PropsKeys.LDAP_USER_IGNORE_ATTRIBUTES);

	public static final String LDAP_USER_IMPL = PropsUtil.get(PropsKeys.LDAP_USER_IMPL);

	public static final String LIFERAY_HOME = PropsUtil.get(PropsKeys.LIFERAY_HOME);

	public static final String LIFERAY_LIB_GLOBAL_DIR = PropsUtil.get(PropsKeys.LIFERAY_LIB_GLOBAL_DIR);

	public static final String LIFERAY_LIB_GLOBAL_SHARED_DIR = PropsUtil.get(PropsKeys.LIFERAY_LIB_GLOBAL_SHARED_DIR);

	public static final String LIFERAY_LIB_PORTAL_DIR = PropsUtil.get(PropsKeys.LIFERAY_LIB_PORTAL_DIR);

	public static String LIFERAY_WEB_PORTAL_CONTEXT_TEMPDIR;

	public static final String LIFERAY_WEB_PORTAL_DIR = PropsUtil.get(PropsKeys.LIFERAY_WEB_PORTAL_DIR);

	public static final boolean LIVE_USERS_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.LIVE_USERS_ENABLED));

	public static final boolean LOCALE_DEFAULT_REQUEST = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.LOCALE_DEFAULT_REQUEST));

	public static final int LOCALE_PREPEND_FRIENDLY_URL_STYLE = GetterUtil.getInteger(PropsUtil.get(PropsKeys.LOCALE_PREPEND_FRIENDLY_URL_STYLE));

	public static String[] LOCALES = PropsUtil.getArray(PropsKeys.LOCALES);

	public static String[] LOCALES_BETA = PropsUtil.getArray(PropsKeys.LOCALES_BETA);

	public static String[] LOCALES_ENABLED = PropsUtil.getArray(PropsKeys.LOCALES_ENABLED);

	public static boolean LOGIN_CREATE_ACCOUNT_ALLOW_CUSTOM_PASSWORD = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.LOGIN_CREATE_ACCOUNT_ALLOW_CUSTOM_PASSWORD));

	public static final String LOGIN_EMAIL_FROM_ADDRESS = PropsUtil.get(PropsKeys.LOGIN_EMAIL_FROM_ADDRESS);

	public static final String LOGIN_EMAIL_FROM_NAME = PropsUtil.get(PropsKeys.LOGIN_EMAIL_FROM_NAME);

	public static final String[] LOGIN_EVENTS_POST = PropsUtil.getArray(PropsKeys.LOGIN_EVENTS_POST);

	public static final String[] LOGIN_EVENTS_PRE = PropsUtil.getArray(PropsKeys.LOGIN_EVENTS_PRE);

	public static String[] LOGIN_FORM_NAVIGATION_POST = PropsUtil.getArray(PropsKeys.LOGIN_FORM_NAVIGATION_POST);

	public static String[] LOGIN_FORM_NAVIGATION_PRE = PropsUtil.getArray(PropsKeys.LOGIN_FORM_NAVIGATION_PRE);

	public static final String[] LOGOUT_EVENTS_POST = PropsUtil.getArray(PropsKeys.LOGOUT_EVENTS_POST);

	public static final String[] LOGOUT_EVENTS_PRE = PropsUtil.getArray(PropsKeys.LOGOUT_EVENTS_PRE);

	public static final boolean LOOK_AND_FEEL_MODIFIABLE = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.LOOK_AND_FEEL_MODIFIABLE));

	public static final int LUCENE_ANALYZER_MAX_TOKENS = GetterUtil.getInteger(PropsUtil.get(PropsKeys.LUCENE_ANALYZER_MAX_TOKENS));

	public static final int LUCENE_BUFFER_SIZE = GetterUtil.getInteger(PropsUtil.get(PropsKeys.LUCENE_BUFFER_SIZE));

	public static final long LUCENE_CLUSTER_INDEX_LOADING_SYNC_TIMEOUT = GetterUtil.getLong(PropsUtil.get(PropsKeys.LUCENE_CLUSTER_INDEX_LOADING_SYNC_TIMEOUT));

	public static final int LUCENE_COMMIT_BATCH_SIZE = GetterUtil.getInteger(PropsUtil.get(PropsKeys.LUCENE_COMMIT_BATCH_SIZE));

	public static final int LUCENE_COMMIT_TIME_INTERVAL = GetterUtil.getInteger(PropsUtil.get(PropsKeys.LUCENE_COMMIT_TIME_INTERVAL));

	public static final String LUCENE_DIR = PropsUtil.get(PropsKeys.LUCENE_DIR);

	public static final String LUCENE_FILE_EXTRACTOR = PropsUtil.get(PropsKeys.LUCENE_FILE_EXTRACTOR);

	public static final String LUCENE_FILE_EXTRACTOR_REGEXP_STRIP = PropsUtil.get(PropsKeys.LUCENE_FILE_EXTRACTOR_REGEXP_STRIP);

	public static final int LUCENE_MERGE_FACTOR = GetterUtil.getInteger(PropsUtil.get(PropsKeys.LUCENE_MERGE_FACTOR));

	public static String LUCENE_MERGE_POLICY = PropsUtil.get(PropsKeys.LUCENE_MERGE_POLICY);

	public static final boolean LUCENE_REPLICATE_WRITE = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.LUCENE_REPLICATE_WRITE));

	public static final String[] LUCENE_SPELL_CHECKER_SUPPORTED_LOCALES = StringUtil.split(PropsUtil.get(PropsKeys.LUCENE_SPELL_CHECKER_SUPPORTED_LOCALES));

	public static final String LUCENE_STORE_TYPE = PropsUtil.get(PropsKeys.LUCENE_STORE_TYPE);

	public static final boolean LUCENE_STORE_TYPE_FILE_FORCE_MMAP = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.LUCENE_STORE_TYPE_FILE_FORCE_MMAP));

	public static final String MAIL_AUDIT_TRAIL = PropsUtil.get(PropsKeys.MAIL_AUDIT_TRAIL);

	public static final String MAIL_HOOK_IMPL = PropsUtil.get(PropsKeys.MAIL_HOOK_IMPL);

	public static final boolean MAIL_MX_UPDATE = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.MAIL_MX_UPDATE));

	public static final String MAIL_SESSION_MAIL_ADVANCED_PROPERTIES = PropsUtil.get(PropsKeys.MAIL_SESSION_MAIL_ADVANCED_PROPERTIES);

	public static final String MAIL_SESSION_MAIL_POP3_HOST = PropsUtil.get(PropsKeys.MAIL_SESSION_MAIL_POP3_HOST);

	public static final String MAIL_SESSION_MAIL_POP3_PASSWORD = PropsUtil.get(PropsKeys.MAIL_SESSION_MAIL_POP3_PASSWORD);

	public static final int MAIL_SESSION_MAIL_POP3_PORT = GetterUtil.getInteger(PropsUtil.get(PropsKeys.MAIL_SESSION_MAIL_POP3_PORT));

	public static final String MAIL_SESSION_MAIL_POP3_USER = PropsUtil.get(PropsKeys.MAIL_SESSION_MAIL_POP3_USER);

	public static final String MAIL_SESSION_MAIL_SMTP_HOST = PropsUtil.get(PropsKeys.MAIL_SESSION_MAIL_SMTP_HOST);

	public static final String MAIL_SESSION_MAIL_SMTP_PASSWORD = PropsUtil.get(PropsKeys.MAIL_SESSION_MAIL_SMTP_PASSWORD);

	public static final int MAIL_SESSION_MAIL_SMTP_PORT = GetterUtil.getInteger(PropsUtil.get(PropsKeys.MAIL_SESSION_MAIL_SMTP_PORT));

	public static final String MAIL_SESSION_MAIL_SMTP_USER = PropsUtil.get(PropsKeys.MAIL_SESSION_MAIL_SMTP_USER);

	public static final String MAIL_SESSION_MAIL_STORE_PROTOCOL = PropsUtil.get(PropsKeys.MAIL_SESSION_MAIL_STORE_PROTOCOL);

	public static final String MAIL_SESSION_MAIL_TRANSPORT_PROTOCOL = PropsUtil.get(PropsKeys.MAIL_SESSION_MAIL_TRANSPORT_PROTOCOL);

	public static final boolean MEMBERSHIP_POLICY_AUTO_VERIFY = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.MEMBERSHIP_POLICY_AUTO_VERIFY));

	public static final String MEMBERSHIP_POLICY_ORGANIZATIONS = PropsUtil.get(PropsKeys.MEMBERSHIP_POLICY_ORGANIZATIONS);

	public static final String MEMBERSHIP_POLICY_ROLES = PropsUtil.get(PropsKeys.MEMBERSHIP_POLICY_ROLES);

	public static final String MEMBERSHIP_POLICY_SITES = PropsUtil.get(PropsKeys.MEMBERSHIP_POLICY_SITES);

	public static final String MEMBERSHIP_POLICY_USER_GROUPS = PropsUtil.get(PropsKeys.MEMBERSHIP_POLICY_USER_GROUPS);

	public static final boolean MEMORY_CLUSTER_SCHEDULER_LOCK_CACHE_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.MEMORY_CLUSTER_SCHEDULER_LOCK_CACHE_ENABLED));

	public static final boolean MESSAGE_BOARDS_ANONYMOUS_POSTING_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.MESSAGE_BOARDS_ANONYMOUS_POSTING_ENABLED));

	public static final boolean MESSAGE_BOARDS_EMAIL_BULK = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.MESSAGE_BOARDS_EMAIL_BULK));

	public static final String MESSAGE_BOARDS_EMAIL_FROM_ADDRESS = PropsUtil.get(PropsKeys.MESSAGE_BOARDS_EMAIL_FROM_ADDRESS);

	public static final String MESSAGE_BOARDS_EMAIL_FROM_NAME = PropsUtil.get(PropsKeys.MESSAGE_BOARDS_EMAIL_FROM_NAME);

	public static final boolean MESSAGE_BOARDS_EMAIL_HTML_FORMAT = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.MESSAGE_BOARDS_EMAIL_HTML_FORMAT));

	public static final String MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_BODY = PropsUtil.get(PropsKeys.MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_BODY);

	public static final boolean MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_ENABLED));

	public static final String MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_SIGNATURE = PropsUtil.get(PropsKeys.MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_SIGNATURE);

	public static final String MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_SUBJECT_PREFIX = PropsUtil.get(PropsKeys.MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_SUBJECT_PREFIX);

	public static final String MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_BODY = PropsUtil.get(PropsKeys.MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_BODY);

	public static final boolean MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_ENABLED));

	public static final String MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_SIGNATURE = PropsUtil.get(PropsKeys.MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_SIGNATURE);

	public static final String MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_SUBJECT_PREFIX = PropsUtil.get(PropsKeys.MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_SUBJECT_PREFIX);

	public static final int MESSAGE_BOARDS_EXPIRE_BAN_INTERVAL = GetterUtil.getInteger(PropsUtil.get(PropsKeys.MESSAGE_BOARDS_EXPIRE_BAN_INTERVAL));

	public static final long MESSAGE_BOARDS_EXPIRE_BAN_JOB_INTERVAL = GetterUtil.getLong(PropsUtil.get(PropsKeys.MESSAGE_BOARDS_EXPIRE_BAN_JOB_INTERVAL));

	public static final boolean MESSAGE_BOARDS_PINGBACK_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.MESSAGE_BOARDS_PINGBACK_ENABLED));

	public static final boolean MESSAGE_BOARDS_PUBLISH_TO_LIVE_BY_DEFAULT = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.MESSAGE_BOARDS_PUBLISH_TO_LIVE_BY_DEFAULT));

	public static final int MESSAGE_BOARDS_RSS_ABSTRACT_LENGTH = GetterUtil.getInteger(PropsUtil.get(PropsKeys.MESSAGE_BOARDS_RSS_ABSTRACT_LENGTH));

	public static final boolean MESSAGE_BOARDS_SUBSCRIBE_BY_DEFAULT = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.MESSAGE_BOARDS_SUBSCRIBE_BY_DEFAULT));

	public static final boolean MESSAGE_BOARDS_THREAD_PREVIOUS_AND_NEXT_NAVIGATION_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.MESSAGE_BOARDS_THREAD_PREVIOUS_AND_NEXT_NAVIGATION_ENABLED));

	public static final String[] MESSAGE_BOARDS_THREAD_VIEWS = PropsUtil.getArray(PropsKeys.MESSAGE_BOARDS_THREAD_VIEWS);

	public static final String MESSAGE_BOARDS_THREAD_VIEWS_DEFAULT = PropsUtil.get(PropsKeys.MESSAGE_BOARDS_THREAD_VIEWS_DEFAULT);

	public static final String MICROSOFT_TRANSLATOR_CLIENT_ID = PropsUtil.get(PropsKeys.MICROSOFT_TRANSLATOR_CLIENT_ID);

	public static final String MICROSOFT_TRANSLATOR_CLIENT_SECRET = PropsUtil.get(PropsKeys.MICROSOFT_TRANSLATOR_CLIENT_SECRET);

	public static final String[] MIME_TYPES_CONTENT_DISPOSITION_INLINE = PropsUtil.getArray(PropsKeys.MIME_TYPES_CONTENT_DISPOSITION_INLINE);

	public static String[] MIME_TYPES_WEB_IMAGES = PropsUtil.getArray(PropsKeys.MIME_TYPES_WEB_IMAGES);

	public static final boolean MINIFIER_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.MINIFIER_ENABLED));

	public static final int MINIFIER_FILES_LIMIT = GetterUtil.getInteger(PropsUtil.get(PropsKeys.MINIFIER_FILES_LIMIT));

	public static final int MINIFIER_INLINE_CONTENT_CACHE_SIZE = GetterUtil.getInteger(PropsUtil.get(PropsKeys.MINIFIER_INLINE_CONTENT_CACHE_SIZE));

	public static final String[] MINIFIER_INLINE_CONTENT_CACHE_SKIP_CSS = PropsUtil.getArray(PropsKeys.MINIFIER_INLINE_CONTENT_CACHE_SKIP_CSS);

	public static final String[] MINIFIER_INLINE_CONTENT_CACHE_SKIP_JAVASCRIPT = PropsUtil.getArray(PropsKeys.MINIFIER_INLINE_CONTENT_CACHE_SKIP_JAVASCRIPT);

	public static final String MOBILE_DEVICE_RULES_RULE_GROUP_COPY_POSTFIX = PropsUtil.get(PropsKeys.MOBILE_DEVICE_RULES_RULE_GROUP_COPY_POSTFIX);

	public static final boolean MOBILE_DEVICE_SESSION_CACHE_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.MOBILE_DEVICE_SESSION_CACHE_ENABLED));

	public static final String[] MODULE_FRAMEWORK_AUTO_DEPLOY_DIRS = PropsUtil.getArray(PropsKeys.MODULE_FRAMEWORK_AUTO_DEPLOY_DIRS);

	public static final long MODULE_FRAMEWORK_AUTO_DEPLOY_INTERVAL = GetterUtil.getLong(PropsUtil.get(PropsKeys.MODULE_FRAMEWORK_AUTO_DEPLOY_INTERVAL));

	public static final String MODULE_FRAMEWORK_BASE_DIR = PropsUtil.get(PropsKeys.MODULE_FRAMEWORK_BASE_DIR);

	public static final int MODULE_FRAMEWORK_BEGINNING_START_LEVEL = GetterUtil.getInteger(PropsUtil.get(PropsKeys.MODULE_FRAMEWORK_BEGINNING_START_LEVEL));

	public static final String[] MODULE_FRAMEWORK_INITIAL_BUNDLES = PropsUtil.getArray(PropsKeys.MODULE_FRAMEWORK_INITIAL_BUNDLES);

	public static final String MODULE_FRAMEWORK_PORTAL_DIR = PropsUtil.get(PropsKeys.MODULE_FRAMEWORK_PORTAL_DIR);

	public static final boolean MODULE_FRAMEWORK_REGISTER_LIFERAY_SERVICES = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.MODULE_FRAMEWORK_REGISTER_LIFERAY_SERVICES));

	public static final int MODULE_FRAMEWORK_RUNTIME_START_LEVEL = GetterUtil.getInteger(PropsUtil.get(PropsKeys.MODULE_FRAMEWORK_RUNTIME_START_LEVEL));

	public static final String[] MODULE_FRAMEWORK_SERVICES_IGNORED_INTERFACES = PropsUtil.getArray(PropsKeys.MODULE_FRAMEWORK_SERVICES_IGNORED_INTERFACES);

	public static final String MODULE_FRAMEWORK_STATE_DIR = PropsUtil.get(PropsKeys.MODULE_FRAMEWORK_STATE_DIR);

	public static final String[] MODULE_FRAMEWORK_SYSTEM_BUNDLE_IGNORED_FRAGMENTS = PropsUtil.getArray(PropsKeys.MODULE_FRAMEWORK_SYSTEM_BUNDLE_IGNORED_FRAGMENTS);

	public static final String[] MODULE_FRAMEWORK_SYSTEM_PACKAGES_EXTRA = PropsUtil.getArray(PropsKeys.MODULE_FRAMEWORK_SYSTEM_PACKAGES_EXTRA);

	public static final boolean MONITORING_DATA_SAMPLE_THREAD_LOCAL = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.MONITORING_DATA_SAMPLE_THREAD_LOCAL));

	public static boolean MONITORING_PORTAL_REQUEST = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.MONITORING_PORTAL_REQUEST));

	public static boolean MONITORING_PORTLET_ACTION_REQUEST = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.MONITORING_PORTLET_ACTION_REQUEST));

	public static boolean MONITORING_PORTLET_EVENT_REQUEST = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.MONITORING_PORTLET_EVENT_REQUEST));

	public static boolean MONITORING_PORTLET_RENDER_REQUEST = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.MONITORING_PORTLET_RENDER_REQUEST));

	public static boolean MONITORING_PORTLET_RESOURCE_REQUEST = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.MONITORING_PORTLET_RESOURCE_REQUEST));

	public static final boolean MONITORING_SHOW_PER_REQUEST_DATA_SAMPLE = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.MONITORING_SHOW_PER_REQUEST_DATA_SAMPLE));

	public static final String[] MY_SITES_DIRECTORY_SITE_EXCLUDES = PropsUtil.getArray(PropsKeys.MY_SITES_DIRECTORY_SITE_EXCLUDES);

	public static final String MY_SITES_DISPLAY_STYLE = PropsUtil.get(PropsKeys.MY_SITES_DISPLAY_STYLE);

	public static final int MY_SITES_MAX_ELEMENTS = GetterUtil.getInteger(PropsUtil.get(PropsKeys.MY_SITES_MAX_ELEMENTS));

	public static boolean MY_SITES_SHOW_PRIVATE_SITES_WITH_NO_LAYOUTS = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.MY_SITES_SHOW_PRIVATE_SITES_WITH_NO_LAYOUTS));

	public static boolean MY_SITES_SHOW_PUBLIC_SITES_WITH_NO_LAYOUTS = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.MY_SITES_SHOW_PUBLIC_SITES_WITH_NO_LAYOUTS));

	public static boolean MY_SITES_SHOW_USER_PRIVATE_SITES_WITH_NO_LAYOUTS = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.MY_SITES_SHOW_USER_PRIVATE_SITES_WITH_NO_LAYOUTS));

	public static boolean MY_SITES_SHOW_USER_PUBLIC_SITES_WITH_NO_LAYOUTS = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.MY_SITES_SHOW_USER_PUBLIC_SITES_WITH_NO_LAYOUTS));

	public static final String NAVIGATION_DISPLAY_STYLE_DEFAULT = PropsUtil.get(PropsKeys.NAVIGATION_DISPLAY_STYLE_DEFAULT);

	public static final String[] NAVIGATION_DISPLAY_STYLE_OPTIONS = PropsUtil.getArray(PropsKeys.NAVIGATION_DISPLAY_STYLE_OPTIONS);

	public static final String NESTED_PORTLETS_LAYOUT_TEMPLATE_DEFAULT = PropsUtil.get(PropsKeys.NESTED_PORTLETS_LAYOUT_TEMPLATE_DEFAULT);

	public static final String NET_SF_EHCACHE_CONFIGURATION_RESOURCE_NAME = PropsUtil.get(PropsKeys.NET_SF_EHCACHE_CONFIGURATION_RESOURCE_NAME);

	public static final String NETVIBES_SERVLET_MAPPING = PropsUtil.get(PropsKeys.NETVIBES_SERVLET_MAPPING);

	public static final int NOTIFICATIONS_MAX_EVENTS = GetterUtil.getInteger(PropsUtil.get(PropsKeys.NOTIFICATIONS_MAX_EVENTS));

	public static final boolean NTLM_AUTH_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.NTLM_AUTH_ENABLED));

	public static final String NTLM_AUTH_NEGOTIATE_FLAGS = GetterUtil.getString(PropsUtil.get(PropsKeys.NTLM_AUTH_NEGOTIATE_FLAGS));

	public static final String NTLM_DOMAIN = PropsUtil.get(PropsKeys.NTLM_DOMAIN);

	public static final String NTLM_DOMAIN_CONTROLLER = PropsUtil.get(PropsKeys.NTLM_DOMAIN_CONTROLLER);

	public static final String NTLM_DOMAIN_CONTROLLER_NAME = PropsUtil.get(PropsKeys.NTLM_DOMAIN_CONTROLLER_NAME);

	public static final String NTLM_SERVICE_ACCOUNT = PropsUtil.get(PropsKeys.NTLM_SERVICE_ACCOUNT);

	public static final String NTLM_SERVICE_PASSWORD = PropsUtil.get(PropsKeys.NTLM_SERVICE_PASSWORD);

	public static final long[] OMNIADMIN_USERS = StringUtil.split(PropsUtil.get(PropsKeys.OMNIADMIN_USERS), 0L);

	public static final boolean OPEN_ID_AUTH_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.OPEN_ID_AUTH_ENABLED));

	public static final String[] OPEN_ID_PROVIDERS = PropsUtil.getArray(PropsKeys.OPEN_ID_PROVIDERS);

	public static final boolean OPEN_SSO_AUTH_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.OPEN_SSO_AUTH_ENABLED));

	public static final String OPEN_SSO_EMAIL_ADDRESS_ATTR = PropsUtil.get(PropsKeys.OPEN_SSO_EMAIL_ADDRESS_ATTR);

	public static final String OPEN_SSO_FIRST_NAME_ATTR = PropsUtil.get(PropsKeys.OPEN_SSO_FIRST_NAME_ATTR);

	public static final String OPEN_SSO_LAST_NAME_ATTR = PropsUtil.get(PropsKeys.OPEN_SSO_LAST_NAME_ATTR);

	public static final boolean OPEN_SSO_LDAP_IMPORT_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.OPEN_SSO_LDAP_IMPORT_ENABLED));

	public static final String OPEN_SSO_LOGIN_URL = PropsUtil.get(PropsKeys.OPEN_SSO_LOGIN_URL);

	public static final boolean OPEN_SSO_LOGOUT_ON_SESSION_EXPIRATION = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.OPEN_SSO_LOGOUT_ON_SESSION_EXPIRATION));

	public static final String OPEN_SSO_LOGOUT_URL = PropsUtil.get(PropsKeys.OPEN_SSO_LOGOUT_URL);

	public static final String OPEN_SSO_SCREEN_NAME_ATTR = PropsUtil.get(PropsKeys.OPEN_SSO_SCREEN_NAME_ATTR);

	public static final String OPEN_SSO_SERVICE_URL = PropsUtil.get(PropsKeys.OPEN_SSO_SERVICE_URL);

	public static final boolean OPENOFFICE_CACHE_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.OPENOFFICE_CACHE_ENABLED));

	public static final boolean OPENOFFICE_SERVER_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.OPENOFFICE_SERVER_ENABLED));

	public static final String OPENOFFICE_SERVER_HOST = PropsUtil.get(PropsKeys.OPENOFFICE_SERVER_HOST);

	public static final int OPENOFFICE_SERVER_PORT = GetterUtil.getInteger(PropsUtil.get(PropsKeys.OPENOFFICE_SERVER_PORT));

	public static final boolean ORGANIZATIONS_ASSIGNMENT_STRICT = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.ORGANIZATIONS_ASSIGNMENT_STRICT));

	public static String[] ORGANIZATIONS_FORM_ADD_IDENTIFICATION = PropsUtil.getArray(PropsKeys.ORGANIZATIONS_FORM_ADD_IDENTIFICATION);

	public static String[] ORGANIZATIONS_FORM_ADD_MAIN = PropsUtil.getArray(PropsKeys.ORGANIZATIONS_FORM_ADD_MAIN);

	public static String[] ORGANIZATIONS_FORM_ADD_MISCELLANEOUS = PropsUtil.getArray(PropsKeys.ORGANIZATIONS_FORM_ADD_MISCELLANEOUS);

	public static final boolean ORGANIZATIONS_INDEXER_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.ORGANIZATIONS_INDEXER_ENABLED));

	public static final boolean ORGANIZATIONS_MEMBERSHIP_STRICT = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.ORGANIZATIONS_MEMBERSHIP_STRICT));

	public static final boolean ORGANIZATIONS_SEARCH_WITH_INDEX = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.ORGANIZATIONS_SEARCH_WITH_INDEX));

	public static String[] ORGANIZATIONS_TYPES = PropsUtil.getArray(PropsKeys.ORGANIZATIONS_TYPES);

	public static final boolean PASSWORDS_DEFAULT_POLICY_ALLOW_DICTIONARY_WORDS = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.PASSWORDS_DEFAULT_POLICY_ALLOW_DICTIONARY_WORDS));

	public static final boolean PASSWORDS_DEFAULT_POLICY_CHANGE_REQUIRED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.PASSWORDS_DEFAULT_POLICY_CHANGE_REQUIRED));

	public static final boolean PASSWORDS_DEFAULT_POLICY_CHANGEABLE = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.PASSWORDS_DEFAULT_POLICY_CHANGEABLE));

	public static final boolean PASSWORDS_DEFAULT_POLICY_CHECK_SYNTAX = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.PASSWORDS_DEFAULT_POLICY_CHECK_SYNTAX));

	public static final boolean PASSWORDS_DEFAULT_POLICY_EXPIREABLE = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.PASSWORDS_DEFAULT_POLICY_EXPIREABLE));

	public static final int PASSWORDS_DEFAULT_POLICY_GRACE_LIMIT = GetterUtil.getInteger(PropsUtil.get(PropsKeys.PASSWORDS_DEFAULT_POLICY_GRACE_LIMIT));

	public static final boolean PASSWORDS_DEFAULT_POLICY_HISTORY = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.PASSWORDS_DEFAULT_POLICY_HISTORY));

	public static final int PASSWORDS_DEFAULT_POLICY_HISTORY_COUNT = GetterUtil.getInteger(PropsUtil.get(PropsKeys.PASSWORDS_DEFAULT_POLICY_HISTORY_COUNT));

	public static final boolean PASSWORDS_DEFAULT_POLICY_LOCKOUT = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.PASSWORDS_DEFAULT_POLICY_LOCKOUT));

	public static final long PASSWORDS_DEFAULT_POLICY_LOCKOUT_DURATION = GetterUtil.getLong(PropsUtil.get(PropsKeys.PASSWORDS_DEFAULT_POLICY_LOCKOUT_DURATION));

	public static final long PASSWORDS_DEFAULT_POLICY_MAX_AGE = GetterUtil.getLong(PropsUtil.get(PropsKeys.PASSWORDS_DEFAULT_POLICY_MAX_AGE));

	public static final int PASSWORDS_DEFAULT_POLICY_MAX_FAILURE = GetterUtil.getInteger(PropsUtil.get(PropsKeys.PASSWORDS_DEFAULT_POLICY_MAX_FAILURE));

	public static final long PASSWORDS_DEFAULT_POLICY_MIN_AGE = GetterUtil.getLong(PropsUtil.get(PropsKeys.PASSWORDS_DEFAULT_POLICY_MIN_AGE));

	public static final int PASSWORDS_DEFAULT_POLICY_MIN_ALPHANUMERIC = GetterUtil.getInteger(PropsUtil.get(PropsKeys.PASSWORDS_DEFAULT_POLICY_MIN_ALPHANUMERIC));

	public static final int PASSWORDS_DEFAULT_POLICY_MIN_LENGTH = GetterUtil.getInteger(PropsUtil.get(PropsKeys.PASSWORDS_DEFAULT_POLICY_MIN_LENGTH));

	public static final int PASSWORDS_DEFAULT_POLICY_MIN_LOWERCASE = GetterUtil.getInteger(PropsUtil.get(PropsKeys.PASSWORDS_DEFAULT_POLICY_MIN_LOWERCASE));

	public static final int PASSWORDS_DEFAULT_POLICY_MIN_NUMBERS = GetterUtil.getInteger(PropsUtil.get(PropsKeys.PASSWORDS_DEFAULT_POLICY_MIN_NUMBERS));

	public static final int PASSWORDS_DEFAULT_POLICY_MIN_SYMBOLS = GetterUtil.getInteger(PropsUtil.get(PropsKeys.PASSWORDS_DEFAULT_POLICY_MIN_SYMBOLS));

	public static final int PASSWORDS_DEFAULT_POLICY_MIN_UPPERCASE = GetterUtil.getInteger(PropsUtil.get(PropsKeys.PASSWORDS_DEFAULT_POLICY_MIN_UPPERCASE));

	public static final String PASSWORDS_DEFAULT_POLICY_NAME = PropsUtil.get(PropsKeys.PASSWORDS_DEFAULT_POLICY_NAME);

	public static final String PASSWORDS_DEFAULT_POLICY_REGEX = PropsUtil.get(PropsKeys.PASSWORDS_DEFAULT_POLICY_REGEX);

	public static final long PASSWORDS_DEFAULT_POLICY_RESET_FAILURE_COUNT = GetterUtil.getLong(PropsUtil.get(PropsKeys.PASSWORDS_DEFAULT_POLICY_RESET_FAILURE_COUNT));

	public static final long PASSWORDS_DEFAULT_POLICY_RESET_TICKET_MAX_AGE = GetterUtil.getLong(PropsUtil.get(PropsKeys.PASSWORDS_DEFAULT_POLICY_RESET_TICKET_MAX_AGE));

	public static final long PASSWORDS_DEFAULT_POLICY_WARNING_TIME = GetterUtil.getLong(PropsUtil.get(PropsKeys.PASSWORDS_DEFAULT_POLICY_WARNING_TIME));

	public static String PASSWORDS_ENCRYPTION_ALGORITHM_LEGACY = GetterUtil.getString(PropsUtil.get(PropsKeys.PASSWORDS_ENCRYPTION_ALGORITHM_LEGACY));

	public static final String PASSWORDS_DIGEST_ENCODING = PropsUtil.get(PropsKeys.PASSWORDS_DIGEST_ENCODING);

	public static final String PASSWORDS_PASSWORDPOLICYTOOLKIT_CHARSET_LOWERCASE = PropsUtil.get(PropsKeys.PASSWORDS_PASSWORDPOLICYTOOLKIT_CHARSET_LOWERCASE);

	public static final String PASSWORDS_PASSWORDPOLICYTOOLKIT_CHARSET_NUMBERS = PropsUtil.get(PropsKeys.PASSWORDS_PASSWORDPOLICYTOOLKIT_CHARSET_NUMBERS);

	public static final String PASSWORDS_PASSWORDPOLICYTOOLKIT_CHARSET_SYMBOLS = PropsUtil.get(PropsKeys.PASSWORDS_PASSWORDPOLICYTOOLKIT_CHARSET_SYMBOLS);

	public static final String PASSWORDS_PASSWORDPOLICYTOOLKIT_CHARSET_UPPERCASE = PropsUtil.get(PropsKeys.PASSWORDS_PASSWORDPOLICYTOOLKIT_CHARSET_UPPERCASE);

	public static String PASSWORDS_PASSWORDPOLICYTOOLKIT_GENERATOR = PropsUtil.get(PropsKeys.PASSWORDS_PASSWORDPOLICYTOOLKIT_GENERATOR);

	public static String PASSWORDS_PASSWORDPOLICYTOOLKIT_STATIC = PropsUtil.get(PropsKeys.PASSWORDS_PASSWORDPOLICYTOOLKIT_STATIC);

	public static final String PASSWORDS_TOOLKIT = PropsUtil.get(PropsKeys.PASSWORDS_TOOLKIT);

	public static final boolean PERMISSIONS_CHECK_GUEST_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.PERMISSIONS_CHECK_GUEST_ENABLED));

	public static final String PERMISSIONS_CHECKER = PropsUtil.get(PropsKeys.PERMISSIONS_CHECKER);

	public static boolean PERMISSIONS_CUSTOM_ATTRIBUTE_READ_CHECK_BY_DEFAULT = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.PERMISSIONS_CUSTOM_ATTRIBUTE_READ_CHECK_BY_DEFAULT));

	public static boolean PERMISSIONS_CUSTOM_ATTRIBUTE_WRITE_CHECK_BY_DEFAULT = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.PERMISSIONS_CUSTOM_ATTRIBUTE_WRITE_CHECK_BY_DEFAULT));

	public static final boolean PERMISSIONS_INLINE_SQL_CHECK_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.PERMISSIONS_INLINE_SQL_CHECK_ENABLED));

	public static final int PERMISSIONS_INLINE_SQL_RESOURCE_BLOCK_QUERY_THRESHHOLD = GetterUtil.getInteger(PropsUtil.get(PropsKeys.PERMISSIONS_INLINE_SQL_RESOURCE_BLOCK_QUERY_THRESHHOLD));

	public static final String PERMISSIONS_LIST_FILTER = PropsUtil.get(PropsKeys.PERMISSIONS_LIST_FILTER);

	public static final boolean PERMISSIONS_OBJECT_BLOCKING_CACHE = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.PERMISSIONS_OBJECT_BLOCKING_CACHE));

	public static final boolean PERMISSIONS_PROPAGATION_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.PERMISSIONS_PROPAGATION_ENABLED));

	public static final int PERMISSIONS_ROLE_RESOURCE_PERMISSION_QUERY_THRESHOLD = GetterUtil.getInteger(PropsUtil.get(PropsKeys.PERMISSIONS_ROLE_RESOURCE_PERMISSION_QUERY_THRESHOLD));

	public static final int PERMISSIONS_THREAD_LOCAL_CACHE_MAX_SIZE = GetterUtil.getInteger(PropsUtil.get(PropsKeys.PERMISSIONS_THREAD_LOCAL_CACHE_MAX_SIZE));

	public static int PERMISSIONS_USER_CHECK_ALGORITHM = GetterUtil.getInteger(PropsUtil.get(PropsKeys.PERMISSIONS_USER_CHECK_ALGORITHM));

	public static boolean PERMISSIONS_VIEW_DYNAMIC_INHERITANCE = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE));

	public static final String PERSISTENCE_PROVIDER = GetterUtil.getString(PropsUtil.get(PropsKeys.PERSISTENCE_PROVIDER));

	public static final String PHONE_NUMBER_FORMAT_IMPL = GetterUtil.getString(PropsUtil.get(PropsKeys.PHONE_NUMBER_FORMAT_IMPL));

	public static String PHONE_NUMBER_FORMAT_INTERNATIONAL_REGEXP = GetterUtil.getString(PropsUtil.get(PropsKeys.PHONE_NUMBER_FORMAT_INTERNATIONAL_REGEXP));

	public static String PHONE_NUMBER_FORMAT_USA_REGEXP = GetterUtil.getString(PropsUtil.get(PropsKeys.PHONE_NUMBER_FORMAT_USA_REGEXP));

	public static final boolean PLUGIN_NOTIFICATIONS_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.PLUGIN_NOTIFICATIONS_ENABLED));

	public static final String[] PLUGIN_NOTIFICATIONS_PACKAGES_IGNORED = StringUtil.splitLines(PropsUtil.get(PropsKeys.PLUGIN_NOTIFICATIONS_PACKAGES_IGNORED));

	public static final String[] PLUGIN_REPOSITORIES_TRUSTED = StringUtil.splitLines(PropsUtil.get(PropsKeys.PLUGIN_REPOSITORIES_TRUSTED));

	public static final String[] PLUGIN_REPOSITORIES_UNTRUSTED = StringUtil.splitLines(PropsUtil.get(PropsKeys.PLUGIN_REPOSITORIES_UNTRUSTED));

	public static final String[] PLUGIN_TYPES = PropsUtil.getArray(PropsKeys.PLUGIN_TYPES);

	public static final long POLLER_NOTIFICATIONS_TIMEOUT = GetterUtil.getInteger(PropsUtil.get(PropsKeys.POLLER_NOTIFICATIONS_TIMEOUT));

	public static final long POLLER_REQUEST_TIMEOUT = GetterUtil.getLong(PropsUtil.get(PropsKeys.POLLER_REQUEST_TIMEOUT));

	public static final boolean POP_SERVER_NOTIFICATIONS_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.POP_SERVER_NOTIFICATIONS_ENABLED));

	public static final int POP_SERVER_NOTIFICATIONS_INTERVAL = GetterUtil.getInteger(PropsUtil.get(PropsKeys.POP_SERVER_NOTIFICATIONS_INTERVAL));

	public static final String POP_SERVER_SUBDOMAIN = PropsUtil.get(PropsKeys.POP_SERVER_SUBDOMAIN);

	public static final boolean PORTAL_IMPERSONATION_ENABLE = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.PORTAL_IMPERSONATION_ENABLE));

	public static final int PORTAL_INSTANCE_HTTP_PORT = GetterUtil.getInteger(PropsUtil.get(PropsKeys.PORTAL_INSTANCE_HTTP_PORT));

	public static final String PORTAL_JAAS_AUTH_TYPE = GetterUtil.getString(PropsUtil.get(PropsKeys.PORTAL_JAAS_AUTH_TYPE));

	public static final boolean PORTAL_JAAS_ENABLE = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.PORTAL_JAAS_ENABLE));

	public static final String PORTAL_JAAS_IMPL = PropsUtil.get(PropsKeys.PORTAL_JAAS_IMPL);

	public static final boolean PORTAL_JAAS_STRICT_PASSWORD = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.PORTAL_JAAS_STRICT_PASSWORD));

	public static final String PORTAL_PROXY_PATH = PropsUtil.get(PropsKeys.PORTAL_PROXY_PATH);

	public static final boolean PORTAL_RESILIENCY_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.PORTAL_RESILIENCY_ENABLED));

	public static final boolean PORTAL_RESILIENCY_PORTLET_SHOW_FOOTER = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.PORTAL_RESILIENCY_PORTLET_SHOW_FOOTER));

	public static final int PORTAL_RESILIENCY_SPI_AGENT_CLIENT_POOL_MAX_SIZE = GetterUtil.getInteger(PropsUtil.get(PropsKeys.PORTAL_RESILIENCY_SPI_AGENT_CLIENT_POOL_MAX_SIZE));

	public static final String[] PORTAL_SECURITY_MANAGER_PRELOAD_CLASSLOADER_CLASSES = PropsUtil.getArray(PropsKeys.PORTAL_SECURITY_MANAGER_PRELOAD_CLASSLOADER_CLASSES);

	public static final String PORTAL_SECURITY_MANAGER_STRATEGY = PropsUtil.get(PropsKeys.PORTAL_SECURITY_MANAGER_STRATEGY);

	public static boolean PORTLET_ADD_DEFAULT_RESOURCE_CHECK_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.PORTLET_ADD_DEFAULT_RESOURCE_CHECK_ENABLED));

	public static String[] PORTLET_ADD_DEFAULT_RESOURCE_CHECK_WHITELIST = PropsUtil.getArray(PropsKeys.PORTLET_ADD_DEFAULT_RESOURCE_CHECK_WHITELIST);

	public static String[] PORTLET_ADD_DEFAULT_RESOURCE_CHECK_WHITELIST_ACTIONS = PropsUtil.getArray(PropsKeys.PORTLET_ADD_DEFAULT_RESOURCE_CHECK_WHITELIST_ACTIONS);

	public static final boolean PORTLET_CONTAINER_RESTRICT = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.PORTLET_CONTAINER_RESTRICT));

	public static final String PORTLET_CROSS_LAYOUT_INVOCATION_MODE = PropsUtil.get(PropsKeys.PORTLET_CROSS_LAYOUT_INVOCATION_MODE);

	public static final boolean PORTLET_CSS_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.PORTLET_CSS_ENABLED));

	public static final String PORTLET_EVENT_DISTRIBUTION = PropsUtil.get(PropsKeys.PORTLET_EVENT_DISTRIBUTION);

	public static final boolean PORTLET_EVENT_DISTRIBUTION_LAYOUT = PORTLET_EVENT_DISTRIBUTION.equalsIgnoreCase("layout");

	public static final boolean PORTLET_EVENT_DISTRIBUTION_LAYOUT_SET = !PORTLET_EVENT_DISTRIBUTION_LAYOUT;

	public static final String[] PORTLET_FILTERS_SYSTEM = PropsUtil.getArray(PropsKeys.PORTLET_FILTERS_SYSTEM);

	public static final String PORTLET_PUBLIC_RENDER_PARAMETER_DISTRIBUTION = PropsUtil.get(PropsKeys.PORTLET_PUBLIC_RENDER_PARAMETER_DISTRIBUTION);

	public static final boolean PORTLET_PUBLIC_RENDER_PARAMETER_DISTRIBUTION_LAYOUT = PORTLET_PUBLIC_RENDER_PARAMETER_DISTRIBUTION.equalsIgnoreCase("layout");

	public static final boolean PORTLET_PUBLIC_RENDER_PARAMETER_DISTRIBUTION_LAYOUT_SET = !PORTLET_PUBLIC_RENDER_PARAMETER_DISTRIBUTION_LAYOUT;

	public static final String PORTLET_RESOURCE_ID_BANNED_PATHS_REGEXP = PropsUtil.get(PropsKeys.PORTLET_RESOURCE_ID_BANNED_PATHS_REGEXP);

	public static final boolean PORTLET_URL_ANCHOR_ENABLE = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.PORTLET_URL_ANCHOR_ENABLE));

	public static final boolean PORTLET_URL_APPEND_PARAMETERS = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.PORTLET_URL_APPEND_PARAMETERS));

	public static final boolean PORTLET_URL_ESCAPE_XML = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.PORTLET_URL_ESCAPE_XML));

	public static final String PORTLET_VIRTUAL_PATH = PropsUtil.get(PropsKeys.PORTLET_VIRTUAL_PATH);

	public static final boolean PORTLET_XML_VALIDATE = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.PORTLET_XML_VALIDATE));

	public static final boolean PREFERENCE_VALIDATE_ON_STARTUP = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.PREFERENCE_VALIDATE_ON_STARTUP));

	public static final int RATINGS_DEFAULT_NUMBER_OF_STARS = GetterUtil.getInteger(PropsUtil.get(PropsKeys.RATINGS_DEFAULT_NUMBER_OF_STARS));

	public static final int RATINGS_MAX_SCORE = GetterUtil.getInteger(PropsUtil.get(PropsKeys.RATINGS_MAX_SCORE));

	public static final int RATINGS_MIN_SCORE = GetterUtil.getInteger(PropsUtil.get(PropsKeys.RATINGS_MIN_SCORE));

	public static final int RECENT_CONTENT_MAX_DISPLAY_ITEMS = GetterUtil.getInteger(PropsUtil.get(PropsKeys.RECENT_CONTENT_MAX_DISPLAY_ITEMS));

	public static final String[] REDIRECT_URL_DOMAINS_ALLOWED = PropsUtil.getArray(PropsKeys.REDIRECT_URL_DOMAINS_ALLOWED);

	public static final String[] REDIRECT_URL_IPS_ALLOWED = PropsUtil.getArray(PropsKeys.REDIRECT_URL_IPS_ALLOWED);

	public static final String REDIRECT_URL_SECURITY_MODE = PropsUtil.get(PropsKeys.REDIRECT_URL_SECURITY_MODE);

	public static final boolean REQUEST_HEADER_AUTH_IMPORT_FROM_LDAP = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.REQUEST_HEADER_AUTH_IMPORT_FROM_LDAP));

	public static final String[] REQUEST_HEADER_IGNORE_INIT_PARAMS = PropsUtil.getArray(PropsKeys.REQUEST_HEADER_IGNORE_INIT_PARAMS);

	public static final String[] REQUEST_SHARED_ATTRIBUTES = PropsUtil.getArray(PropsKeys.REQUEST_SHARED_ATTRIBUTES);

	public static String[] RESOURCE_ACTIONS_CONFIGS = PropsUtil.getArray(PropsKeys.RESOURCE_ACTIONS_CONFIGS);

	public static final boolean RESOURCE_ACTIONS_READ_PORTLET_RESOURCES = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.RESOURCE_ACTIONS_READ_PORTLET_RESOURCES));

	public static final String RESOURCE_REPOSITORIES_ROOT = PropsUtil.get(PropsKeys.RESOURCE_REPOSITORIES_ROOT);

	public static final String[] REST_PROXY_URL_PREFIXES_ALLOWED = PropsUtil.getArray(PropsKeys.REST_PROXY_URL_PREFIXES_ALLOWED);

	public static final String ROBOTS_TXT_WITH_SITEMAP = PropsUtil.get(PropsKeys.ROBOTS_TXT_WITH_SITEMAP);

	public static final String ROBOTS_TXT_WITHOUT_SITEMAP = PropsUtil.get(PropsKeys.ROBOTS_TXT_WITHOUT_SITEMAP);

	public static boolean ROLES_NAME_ALLOW_NUMERIC = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.ROLES_NAME_ALLOW_NUMERIC));

	public static final String[] ROLES_ORGANIZATION_SUBTYPES = PropsUtil.getArray(PropsKeys.ROLES_ORGANIZATION_SUBTYPES);

	public static final String[] ROLES_REGULAR_SUBTYPES = PropsUtil.getArray(PropsKeys.ROLES_REGULAR_SUBTYPES);

	public static final String[] ROLES_SITE_SUBTYPES = PropsUtil.getArray(PropsKeys.ROLES_SITE_SUBTYPES);

	public static final int RSS_CONNECTION_TIMEOUT = GetterUtil.getInteger(PropsUtil.get(PropsKeys.RSS_CONNECTION_TIMEOUT));

	public static String RSS_FEED_DISPLAY_STYLE_DEFAULT = PropsUtil.get(PropsKeys.RSS_FEED_DISPLAY_STYLE_DEFAULT);

	public static String RSS_FEED_TYPE_DEFAULT = PropsUtil.get(PropsKeys.RSS_FEED_TYPE_DEFAULT);

	public static String[] RSS_FEED_TYPES = PropsUtil.getArray(PropsKeys.RSS_FEED_TYPES);

	public static boolean RSS_FEEDS_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.RSS_FEEDS_ENABLED));

	public static final String SANDBOX_DEPLOY_DIR = PropsUtil.get(PropsKeys.SANDBOX_DEPLOY_DIR);

	public static final boolean SANDBOX_DEPLOY_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.SANDBOX_DEPLOY_ENABLED));

	public static final long SANDBOX_DEPLOY_INTERVAL = GetterUtil.getLong(PropsUtil.get(PropsKeys.SANDBOX_DEPLOY_INTERVAL));

	public static String[] SANITIZER_IMPL = PropsUtil.getArray(PropsKeys.SANITIZER_IMPL);

	public static final boolean SC_PRODUCT_COMMENTS_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.SC_PRODUCT_COMMENTS_ENABLED));

	public static final boolean SCHEDULER_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.SCHEDULER_ENABLED));

	public static final boolean SCHEMA_RUN_MINIMAL = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.SCHEMA_RUN_MINIMAL));

	public static final String[] SCRIPTING_FORBIDDEN_CLASSES = PropsUtil.getArray(PropsKeys.SCRIPTING_FORBIDDEN_CLASSES);

	public static final String SCRIPTING_JRUBY_COMPILE_MODE = PropsUtil.get(PropsKeys.SCRIPTING_JRUBY_COMPILE_MODE);

	public static final int SCRIPTING_JRUBY_COMPILE_THRESHOLD = GetterUtil.getInteger(PropsUtil.get(PropsKeys.SCRIPTING_JRUBY_COMPILE_THRESHOLD), 50);

	public static final String[] SCRIPTING_JRUBY_LOAD_PATHS = PropsUtil.getArray(PropsKeys.SCRIPTING_JRUBY_LOAD_PATHS);

	public static final int SEARCH_CONTAINER_PAGE_DEFAULT_DELTA = GetterUtil.getInteger(PropsUtil.get(PropsKeys.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA), 20);

	public static final int[] SEARCH_CONTAINER_PAGE_DELTA_VALUES = GetterUtil.getIntegerValues(PropsUtil.getArray(PropsKeys.SEARCH_CONTAINER_PAGE_DELTA_VALUES));

	public static final int SEARCH_CONTAINER_PAGE_ITERATOR_MAX_PAGES = GetterUtil.getInteger(PropsUtil.get(PropsKeys.SEARCH_CONTAINER_PAGE_ITERATOR_MAX_PAGES));

	public static final boolean SEARCH_CONTAINER_SHOW_PAGINATION_BOTTOM = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.SEARCH_CONTAINER_SHOW_PAGINATION_BOTTOM));

	public static final boolean SEARCH_CONTAINER_SHOW_PAGINATION_TOP = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.SEARCH_CONTAINER_SHOW_PAGINATION_TOP));

	public static final String SEARCH_FACET_CONFIGURATION = PropsUtil.get(PropsKeys.SEARCH_FACET_CONFIGURATION);

	public static final String[] SERVICE_BUILDER_SERVICE_READ_ONLY_PREFIXES = PropsUtil.getArray(PropsKeys.SERVICE_BUILDER_SERVICE_READ_ONLY_PREFIXES);

	public static final String[] SERVLET_SERVICE_EVENTS_POST = PropsUtil.getArray(PropsKeys.SERVLET_SERVICE_EVENTS_POST);

	public static final String[] SERVLET_SERVICE_EVENTS_PRE = PropsUtil.getArray(PropsKeys.SERVLET_SERVICE_EVENTS_PRE);

	public static final String SERVLET_SERVICE_EVENTS_PRE_ERROR_PAGE = PropsUtil.get(PropsKeys.SERVLET_SERVICE_EVENTS_PRE_ERROR_PAGE);

	public static final String[] SERVLET_SESSION_CREATE_EVENTS = PropsUtil.getArray(PropsKeys.SERVLET_SESSION_CREATE_EVENTS);

	public static final String[] SERVLET_SESSION_DESTROY_EVENTS = PropsUtil.getArray(PropsKeys.SERVLET_SESSION_DESTROY_EVENTS);

	public static final String SESSION_COOKIE_DOMAIN = PropsUtil.get(PropsKeys.SESSION_COOKIE_DOMAIN);

	public static final boolean SESSION_DISABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.SESSION_DISABLED));

	public static final boolean SESSION_ENABLE_PERSISTENT_COOKIES = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.SESSION_ENABLE_PERSISTENT_COOKIES));

	public static final boolean SESSION_ENABLE_PHISHING_PROTECTION = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.SESSION_ENABLE_PHISHING_PROTECTION));

	public static final boolean SESSION_ENABLE_URL_WITH_SESSION_ID = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.SESSION_ENABLE_URL_WITH_SESSION_ID));

	public static final String SESSION_ID_DELIMITER = PropsUtil.get(PropsKeys.SESSION_ID_DELIMITER);

	public static final int SESSION_MAX_ALLOWED = GetterUtil.getInteger(PropsUtil.get(PropsKeys.SESSION_MAX_ALLOWED));

	public static String[] SESSION_PHISHING_PROTECTED_ATTRIBUTES = PropsUtil.getArray(PropsKeys.SESSION_PHISHING_PROTECTED_ATTRIBUTES);

	public static final String[] SESSION_SHARED_ATTRIBUTES = PropsUtil.getArray(PropsKeys.SESSION_SHARED_ATTRIBUTES);

	public static final String[] SESSION_SHARED_ATTRIBUTES_EXCLUDES = PropsUtil.getArray(PropsKeys.SESSION_SHARED_ATTRIBUTES_EXCLUDES);

	public static boolean SESSION_STORE_PASSWORD = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.SESSION_STORE_PASSWORD));

	public static final boolean SESSION_TEST_COOKIE_SUPPORT = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.SESSION_TEST_COOKIE_SUPPORT));

	public static int SESSION_TIMEOUT = GetterUtil.getInteger(PropsUtil.get(PropsKeys.SESSION_TIMEOUT));

	public static final boolean SESSION_TIMEOUT_AUTO_EXTEND = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.SESSION_TIMEOUT_AUTO_EXTEND));

	public static final boolean SESSION_TIMEOUT_REDIRECT_ON_EXPIRE = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.SESSION_TIMEOUT_REDIRECT_ON_EXPIRE));

	public static final int SESSION_TIMEOUT_WARNING = GetterUtil.getInteger(PropsUtil.get(PropsKeys.SESSION_TIMEOUT_WARNING));

	public static final boolean SESSION_TRACKER_FRIENDLY_PATHS_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.SESSION_TRACKER_FRIENDLY_PATHS_ENABLED));

	public static final boolean SESSION_TRACKER_MEMORY_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.SESSION_TRACKER_MEMORY_ENABLED));

	public static final boolean SESSION_TRACKER_PERSISTENCE_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.SESSION_TRACKER_PERSISTENCE_ENABLED));

	public static final String[] SETUP_DATABASE_TYPES = PropsUtil.getArray(PropsKeys.SETUP_DATABASE_TYPES);

	public static final boolean SETUP_WIZARD_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.SETUP_WIZARD_ENABLED));

	public static final String SHARD_DEFAULT_NAME = PropsUtil.get(PropsKeys.SHARD_DEFAULT_NAME);

	public static final String SHARD_SELECTOR = PropsUtil.get(PropsKeys.SHARD_SELECTOR);

	public static final String SHOPPING_EMAIL_FROM_ADDRESS = PropsUtil.get(PropsKeys.SHOPPING_EMAIL_FROM_ADDRESS);

	public static final String SHOPPING_EMAIL_FROM_NAME = PropsUtil.get(PropsKeys.SHOPPING_EMAIL_FROM_NAME);

	public static final boolean SHOPPING_ORDER_COMMENTS_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.SHOPPING_ORDER_COMMENTS_ENABLED));

	public static final String SITEMAP_DISPLAY_TEMPLATES_CONFIG = PropsUtil.get(PropsKeys.SITEMAP_DISPLAY_TEMPLATES_CONFIG);

	public static final boolean SITEMINDER_AUTH_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.SITEMINDER_AUTH_ENABLED));

	public static final boolean SITEMINDER_IMPORT_FROM_LDAP = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.SITEMINDER_IMPORT_FROM_LDAP));

	public static final String SITEMINDER_USER_HEADER = PropsUtil.get(PropsKeys.SITEMINDER_USER_HEADER);

	public static final boolean SITES_CONTROL_PANEL_MEMBERS_VISIBLE = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.SITES_CONTROL_PANEL_MEMBERS_VISIBLE));

	public static String[] SITES_FORM_ADD_ADVANCED = PropsUtil.getArray(PropsKeys.SITES_FORM_ADD_ADVANCED);

	public static String[] SITES_FORM_ADD_MAIN = PropsUtil.getArray(PropsKeys.SITES_FORM_ADD_MAIN);

	public static String[] SITES_FORM_ADD_MISCELLANEOUS = PropsUtil.getArray(PropsKeys.SITES_FORM_ADD_MISCELLANEOUS);

	public static String[] SITES_FORM_ADD_SEO = PropsUtil.getArray(PropsKeys.SITES_FORM_ADD_SEO);

	public static String[] SITES_FORM_UPDATE_ADVANCED = PropsUtil.getArray(PropsKeys.SITES_FORM_UPDATE_ADVANCED);

	public static String[] SITES_FORM_UPDATE_MAIN = PropsUtil.getArray(PropsKeys.SITES_FORM_UPDATE_MAIN);

	public static String[] SITES_FORM_UPDATE_MISCELLANEOUS = PropsUtil.getArray(PropsKeys.SITES_FORM_UPDATE_MISCELLANEOUS);

	public static String[] SITES_FORM_UPDATE_SEO = PropsUtil.getArray(PropsKeys.SITES_FORM_UPDATE_SEO);

	public static String SITES_SITEMAP_DEFAULT_CHANGE_FREQUENCY = PropsUtil.get(PropsKeys.SITES_SITEMAP_DEFAULT_CHANGE_FREQUENCY);

	public static String SITES_SITEMAP_DEFAULT_PRIORITY = PropsUtil.get(PropsKeys.SITES_SITEMAP_DEFAULT_PRIORITY);

	public static final int[] SOCIAL_ACTIVITY_CONTRIBUTION_INCREMENTS = GetterUtil.getIntegerValues(PropsUtil.getArray(PropsKeys.SOCIAL_ACTIVITY_CONTRIBUTION_INCREMENTS));

	public static final int[] SOCIAL_ACTIVITY_CONTRIBUTION_LIMIT_VALUES = GetterUtil.getIntegerValues(PropsUtil.getArray(PropsKeys.SOCIAL_ACTIVITY_CONTRIBUTION_LIMIT_VALUES));

	public static final long SOCIAL_ACTIVITY_COUNTER_LOCK_RETRY_DELAY = GetterUtil.getLong(PropsUtil.get(PropsKeys.SOCIAL_ACTIVITY_COUNTER_LOCK_RETRY_DELAY));

	public static final long SOCIAL_ACTIVITY_COUNTER_LOCK_TIMEOUT = GetterUtil.getLong(PropsUtil.get(PropsKeys.SOCIAL_ACTIVITY_COUNTER_LOCK_TIMEOUT));

	public static final String SOCIAL_ACTIVITY_COUNTER_PERIOD_LENGTH = PropsUtil.get(PropsKeys.SOCIAL_ACTIVITY_COUNTER_PERIOD_LENGTH);

	public static final int[] SOCIAL_ACTIVITY_PARTICIPATION_INCREMENTS = GetterUtil.getIntegerValues(PropsUtil.getArray(PropsKeys.SOCIAL_ACTIVITY_PARTICIPATION_INCREMENTS));

	public static final int[] SOCIAL_ACTIVITY_PARTICIPATION_LIMIT_VALUES = GetterUtil.getIntegerValues(PropsUtil.getArray(PropsKeys.SOCIAL_ACTIVITY_PARTICIPATION_LIMIT_VALUES));

	public static final boolean SOCIAL_ACTIVITY_SETS_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.SOCIAL_ACTIVITY_SETS_ENABLED));

	public static final String SOCIAL_ACTIVITY_SETS_SELECTOR = GetterUtil.getString(PropsUtil.get(PropsKeys.SOCIAL_ACTIVITY_SETS_SELECTOR));

	public static final String[] SPRING_CONFIGS = PropsUtil.getArray(PropsKeys.SPRING_CONFIGS);

	public static final String[] SPRING_HIBERNATE_CONFIGURATION_PROXY_FACTORY_PRELOAD_CLASSLOADER_CLASSES = PropsUtil.getArray(PropsKeys.SPRING_HIBERNATE_CONFIGURATION_PROXY_FACTORY_PRELOAD_CLASSLOADER_CLASSES);

	public static boolean SPRING_HIBERNATE_SESSION_DELEGATED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.SPRING_HIBERNATE_SESSION_DELEGATED));

	public static final String[] SPRING_HIBERNATE_SESSION_FACTORY_PRELOAD_CLASSLOADER_CLASSES = PropsUtil.getArray(PropsKeys.SPRING_HIBERNATE_SESSION_FACTORY_PRELOAD_CLASSLOADER_CLASSES);

	public static final String SPRITE_FILE_NAME = PropsUtil.get(PropsKeys.SPRITE_FILE_NAME);

	public static final String SPRITE_PROPERTIES_FILE_NAME = PropsUtil.get(PropsKeys.SPRITE_PROPERTIES_FILE_NAME);

	public static final String SPRITE_ROOT_DIR = PropsUtil.get(PropsKeys.SPRITE_ROOT_DIR);

	public static final int SQL_DATA_MAX_PARAMETERS = GetterUtil.getInteger(PropsUtil.get(PropsKeys.SQL_DATA_MAX_PARAMETERS));

	public static boolean STAGING_LOCK_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.STAGING_LOCK_ENABLED));

	public static boolean STRIP_CSS_SASS_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.STRIP_CSS_SASS_ENABLED));

	public static final String[] STRIP_IGNORE_PATHS = PropsUtil.getArray(PropsKeys.STRIP_IGNORE_PATHS);

	public static boolean STRIP_JS_LANGUAGE_ATTRIBUTE_SUPPORT_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.STRIP_JS_LANGUAGE_ATTRIBUTE_SUPPORT_ENABLED));

	public static String[] STRIP_MIME_TYPES = PropsUtil.getArray(PropsKeys.STRIP_MIME_TYPES);

	public static final String STRUTS_PORTLET_REQUEST_PROCESSOR = PropsUtil.get(PropsKeys.STRUTS_PORTLET_REQUEST_PROCESSOR);

	public static final boolean TAGS_COMPILER_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.TAGS_COMPILER_ENABLED));

	public static final boolean TCK_URL = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.TCK_URL));

	public static final long TERMS_OF_USE_JOURNAL_ARTICLE_GROUP_ID = GetterUtil.getLong(PropsUtil.get(PropsKeys.TERMS_OF_USE_JOURNAL_ARTICLE_GROUP_ID));

	public static final String TERMS_OF_USE_JOURNAL_ARTICLE_ID = PropsUtil.get(PropsKeys.TERMS_OF_USE_JOURNAL_ARTICLE_ID);

	public static boolean TERMS_OF_USE_REQUIRED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.TERMS_OF_USE_REQUIRED));

	public static final boolean TEXT_EXTRACTION_FORK_PROCESS_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.TEXT_EXTRACTION_FORK_PROCESS_ENABLED));

	public static final String[] TEXT_EXTRACTION_FORK_PROCESS_MIME_TYPES = PropsUtil.getArray(PropsKeys.TEXT_EXTRACTION_FORK_PROCESS_MIME_TYPES);

	public static boolean THEME_CSS_FAST_LOAD = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.THEME_CSS_FAST_LOAD));

	public static boolean THEME_IMAGES_FAST_LOAD = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.THEME_IMAGES_FAST_LOAD));

	public static boolean THEME_JSP_OVERRIDE_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.THEME_JSP_OVERRIDE_ENABLED));

	public static boolean THEME_LOADER_NEW_THEME_ID_ON_IMPORT = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.THEME_LOADER_NEW_THEME_ID_ON_IMPORT));

	public static final String THEME_LOADER_STORAGE_PATH = PropsUtil.get(PropsKeys.THEME_LOADER_STORAGE_PATH);

	public static boolean THEME_PORTLET_DECORATE_DEFAULT = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.THEME_PORTLET_DECORATE_DEFAULT));

	public static boolean THEME_PORTLET_SHARING_DEFAULT = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.THEME_PORTLET_SHARING_DEFAULT));

	public static String THEME_SHORTCUT_ICON = PropsUtil.get(PropsKeys.THEME_SHORTCUT_ICON);

	public static final boolean THEME_SYNC_ON_GROUP = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.THEME_SYNC_ON_GROUP));

	public static final String THEME_VIRTUAL_PATH = PropsUtil.get(PropsKeys.THEME_VIRTUAL_PATH);

	public static int THREAD_DUMP_SPEED_THRESHOLD = GetterUtil.getInteger(PropsUtil.get(PropsKeys.THREAD_DUMP_SPEED_THRESHOLD));

	public static int TRANSACTION_ISOLATION_COUNTER = GetterUtil.getInteger(PropsUtil.get(PropsKeys.TRANSACTION_ISOLATION_COUNTER));

	public static int TRANSACTION_ISOLATION_PORTAL = GetterUtil.getInteger(PropsUtil.get(PropsKeys.TRANSACTION_ISOLATION_PORTAL));

	public static final String TRANSACTION_MANAGER_IMPL = PropsUtil.get(PropsKeys.TRANSACTION_MANAGER_IMPL);

	public static boolean TRANSACTIONAL_CACHE_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.TRANSACTIONAL_CACHE_ENABLED));

	public static final boolean TRANSLATIONS_DISABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.TRANSLATIONS_DISABLED));

	public static final boolean TRASH_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.TRASH_ENABLED));

	public static final int TRASH_ENTRIES_MAX_AGE = GetterUtil.getInteger(PropsUtil.get(PropsKeys.TRASH_ENTRIES_MAX_AGE));

	public static final int TRASH_ENTRY_CHECK_INTERVAL = GetterUtil.getInteger(PropsUtil.get(PropsKeys.TRASH_ENTRY_CHECK_INTERVAL));

	public static final int TRASH_SEARCH_LIMIT = GetterUtil.getInteger(PropsUtil.get(PropsKeys.TRASH_SEARCH_LIMIT));

	public static final boolean UPGRADE_DATABASE_TRANSACTIONS_DISABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.UPGRADE_DATABASE_TRANSACTIONS_DISABLED));

	public static boolean USER_GROUPS_COPY_LAYOUTS_TO_USER_PERSONAL_SITE = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.USER_GROUPS_COPY_LAYOUTS_TO_USER_PERSONAL_SITE));

	public static final boolean USER_GROUPS_INDEXER_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.USER_GROUPS_INDEXER_ENABLED));

	public static boolean USER_GROUPS_NAME_ALLOW_NUMERIC = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.USER_GROUPS_NAME_ALLOW_NUMERIC));

	public static final boolean USER_GROUPS_SEARCH_WITH_INDEX = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.USER_GROUPS_SEARCH_WITH_INDEX));

	public static boolean USER_NOTIFICATION_EVENT_CONFIRMATION_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.USER_NOTIFICATION_EVENT_CONFIRMATION_ENABLED));

	public static final boolean USERS_DELETE = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.USERS_DELETE));

	public static final String USERS_EMAIL_ADDRESS_AUTO_SUFFIX = PropsUtil.get(PropsKeys.USERS_EMAIL_ADDRESS_AUTO_SUFFIX);

	public static final String USERS_EMAIL_ADDRESS_GENERATOR = PropsUtil.get(PropsKeys.USERS_EMAIL_ADDRESS_GENERATOR);

	public static boolean USERS_EMAIL_ADDRESS_REQUIRED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.USERS_EMAIL_ADDRESS_REQUIRED));

	public static final String USERS_EMAIL_ADDRESS_VALIDATOR = PropsUtil.get(PropsKeys.USERS_EMAIL_ADDRESS_VALIDATOR);

	public static final String[] USERS_EXPORT_CSV_FIELDS = PropsUtil.getArray(PropsKeys.USERS_EXPORT_CSV_FIELDS);

	public static String[] USERS_FORM_ADD_IDENTIFICATION = PropsUtil.getArray(PropsKeys.USERS_FORM_ADD_IDENTIFICATION);

	public static String[] USERS_FORM_ADD_MAIN = PropsUtil.getArray(PropsKeys.USERS_FORM_ADD_MAIN);

	public static String[] USERS_FORM_ADD_MISCELLANEOUS = PropsUtil.getArray(PropsKeys.USERS_FORM_ADD_MISCELLANEOUS);

	public static String[] USERS_FORM_MY_ACCOUNT_IDENTIFICATION = PropsUtil.getArray(PropsKeys.USERS_FORM_MY_ACCOUNT_IDENTIFICATION);

	public static String[] USERS_FORM_MY_ACCOUNT_MAIN = PropsUtil.getArray(PropsKeys.USERS_FORM_MY_ACCOUNT_MAIN);

	public static String[] USERS_FORM_MY_ACCOUNT_MISCELLANEOUS = PropsUtil.getArray(PropsKeys.USERS_FORM_MY_ACCOUNT_MISCELLANEOUS);

	public static String[] USERS_FORM_UPDATE_IDENTIFICATION = PropsUtil.getArray(PropsKeys.USERS_FORM_UPDATE_IDENTIFICATION);

	public static String[] USERS_FORM_UPDATE_MAIN = PropsUtil.getArray(PropsKeys.USERS_FORM_UPDATE_MAIN);

	public static String[] USERS_FORM_UPDATE_MISCELLANEOUS = PropsUtil.getArray(PropsKeys.USERS_FORM_UPDATE_MISCELLANEOUS);

	public static final String USERS_FULL_NAME_GENERATOR = PropsUtil.get(PropsKeys.USERS_FULL_NAME_GENERATOR);

	public static final String USERS_FULL_NAME_VALIDATOR = PropsUtil.get(PropsKeys.USERS_FULL_NAME_VALIDATOR);

	public static int USERS_IMAGE_MAX_HEIGHT = GetterUtil.getInteger(PropsUtil.get(PropsKeys.USERS_IMAGE_MAX_HEIGHT));

	public static int USERS_IMAGE_MAX_WIDTH = GetterUtil.getInteger(PropsUtil.get(PropsKeys.USERS_IMAGE_MAX_WIDTH));

	public static final boolean USERS_INDEXER_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.USERS_INDEXER_ENABLED));

	public static boolean USERS_LAST_NAME_REQUIRED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.USERS_LAST_NAME_REQUIRED));

	public static final String[] USERS_LIST_VIEWS = PropsUtil.getArray(PropsKeys.USERS_LIST_VIEWS);

	public static final String USERS_PROFILE_FRIENDLY_URL = PropsUtil.get(PropsKeys.USERS_PROFILE_FRIENDLY_URL);

	public static final boolean USERS_REMINDER_QUERIES_CUSTOM_QUESTION_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.USERS_REMINDER_QUERIES_CUSTOM_QUESTION_ENABLED));

	public static final boolean USERS_REMINDER_QUERIES_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.USERS_REMINDER_QUERIES_ENABLED));

	public static final String[] USERS_REMINDER_QUERIES_QUESTIONS = PropsUtil.getArray(PropsKeys.USERS_REMINDER_QUERIES_QUESTIONS);

	public static final boolean USERS_REMINDER_QUERIES_REQUIRED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.USERS_REMINDER_QUERIES_REQUIRED));

	public static boolean USERS_SCREEN_NAME_ALLOW_NUMERIC = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.USERS_SCREEN_NAME_ALLOW_NUMERIC));

	public static boolean USERS_SCREEN_NAME_ALWAYS_AUTOGENERATE = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.USERS_SCREEN_NAME_ALWAYS_AUTOGENERATE));

	public static final String USERS_SCREEN_NAME_GENERATOR = PropsUtil.get(PropsKeys.USERS_SCREEN_NAME_GENERATOR);

	public static final String USERS_SCREEN_NAME_VALIDATOR = PropsUtil.get(PropsKeys.USERS_SCREEN_NAME_VALIDATOR);

	public static final boolean USERS_SEARCH_WITH_INDEX = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.USERS_SEARCH_WITH_INDEX));

	public static final boolean USERS_UPDATE_LAST_LOGIN = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.USERS_UPDATE_LAST_LOGIN));

	public static final boolean VALUE_OBJECT_ENTITY_BLOCKING_CACHE = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.VALUE_OBJECT_ENTITY_BLOCKING_CACHE));

	public static final boolean VALUE_OBJECT_ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.VALUE_OBJECT_ENTITY_CACHE_ENABLED));

	public static final int VALUE_OBJECT_ENTITY_THREAD_LOCAL_CACHE_MAX_SIZE = GetterUtil.getInteger(PropsUtil.get(PropsKeys.VALUE_OBJECT_ENTITY_THREAD_LOCAL_CACHE_MAX_SIZE));

	public static final boolean VALUE_OBJECT_FINDER_BLOCKING_CACHE = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_BLOCKING_CACHE));

	public static final boolean VALUE_OBJECT_FINDER_CACHE_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_ENABLED));

	public static final int VALUE_OBJECT_FINDER_THREAD_LOCAL_CACHE_MAX_SIZE = GetterUtil.getInteger(PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_THREAD_LOCAL_CACHE_MAX_SIZE));

	public static final int VELOCITY_ENGINE_RESOURCE_MODIFICATION_CHECK_INTERVAL = GetterUtil.getInteger(PropsUtil.get(PropsKeys.VELOCITY_ENGINE_RESOURCE_MODIFICATION_CHECK_INTERVAL));

	public static final String[] VELOCITY_ENGINE_RESOURCE_PARSERS = PropsUtil.getArray(PropsKeys.VELOCITY_ENGINE_RESOURCE_PARSERS);

	public static final String[] VELOCITY_ENGINE_RESTRICTED_CLASSES = PropsUtil.getArray(PropsKeys.VELOCITY_ENGINE_RESTRICTED_CLASSES);

	public static final String[] VELOCITY_ENGINE_RESTRICTED_PACKAGES = PropsUtil.getArray(PropsKeys.VELOCITY_ENGINE_RESTRICTED_PACKAGES);

	public static final boolean VERIFY_DATABASE_TRANSACTIONS_DISABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.VERIFY_DATABASE_TRANSACTIONS_DISABLED));

	public static final String VIRTUAL_HOSTS_DEFAULT_SITE_NAME = PropsUtil.get(PropsKeys.VIRTUAL_HOSTS_DEFAULT_SITE_NAME);

	public static final String[] VIRTUAL_HOSTS_IGNORE_EXTENSIONS = PropsUtil.getArray(PropsKeys.VIRTUAL_HOSTS_IGNORE_EXTENSIONS);

	public static final boolean WEB_SERVER_DISPLAY_NODE = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.WEB_SERVER_DISPLAY_NODE));

	public static final String WEB_SERVER_HOST = PropsUtil.get(PropsKeys.WEB_SERVER_HOST);

	public static final int WEB_SERVER_HTTP_PORT = GetterUtil.getInteger(PropsUtil.get(PropsKeys.WEB_SERVER_HTTP_PORT), -1);

	public static final int WEB_SERVER_HTTPS_PORT = GetterUtil.getInteger(PropsUtil.get(PropsKeys.WEB_SERVER_HTTPS_PORT), -1);

	public static final String WEB_SERVER_PROTOCOL = PropsUtil.get(PropsKeys.WEB_SERVER_PROTOCOL);

	public static final boolean WEB_SERVER_PROXY_LEGACY_MODE = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.WEB_SERVER_PROXY_LEGACY_MODE));

	public static final String[] WEB_SERVER_SERVLET_ACCEPT_RANGES_MIME_TYPES = PropsUtil.getArray(PropsKeys.WEB_SERVER_SERVLET_ACCEPT_RANGES_MIME_TYPES);

	public static final boolean WEB_SERVER_SERVLET_DIRECTORY_INDEXING_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.WEB_SERVER_SERVLET_DIRECTORY_INDEXING_ENABLED));

	public static final String WEB_SERVER_SERVLET_VERSION_VERBOSITY = GetterUtil.getString(PropsUtil.get(PropsKeys.WEB_SERVER_SERVLET_VERSION_VERBOSITY));

	public static final String[] WEBDAV_IGNORE = PropsUtil.getArray(PropsKeys.WEBDAV_IGNORE);

	public static final int WEBDAV_NONCE_EXPIRATION = GetterUtil.getInteger(PropsUtil.get(PropsKeys.WEBDAV_NONCE_EXPIRATION));

	public static final boolean WEBDAV_SERVLET_HTTPS_REQUIRED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.WEBDAV_SERVLET_HTTPS_REQUIRED));

	public static final String WIDGET_SERVLET_MAPPING = PropsUtil.get(PropsKeys.WIDGET_SERVLET_MAPPING);

	public static final String WIKI_DISPLAY_TEMPLATES_CONFIG = PropsUtil.get(PropsKeys.WIKI_DISPLAY_TEMPLATES_CONFIG);

	public static final String WIKI_EMAIL_FROM_ADDRESS = PropsUtil.get(PropsKeys.WIKI_EMAIL_FROM_ADDRESS);

	public static final String WIKI_EMAIL_FROM_NAME = PropsUtil.get(PropsKeys.WIKI_EMAIL_FROM_NAME);

	public static final String[] WIKI_IMPORTERS = PropsUtil.getArray(PropsKeys.WIKI_IMPORTERS);

	public static final String WIKI_INITIAL_NODE_NAME = PropsUtil.get(PropsKeys.WIKI_INITIAL_NODE_NAME);

	public static final boolean WIKI_PAGE_COMMENTS_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.WIKI_PAGE_COMMENTS_ENABLED));

	public static final boolean WIKI_PAGE_MINOR_EDIT_ADD_SOCIAL_ACTIVITY = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.WIKI_PAGE_MINOR_EDIT_ADD_SOCIAL_ACTIVITY));

	public static final boolean WIKI_PAGE_MINOR_EDIT_SEND_EMAIL = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.WIKI_PAGE_MINOR_EDIT_SEND_EMAIL));

	public static final boolean WIKI_PAGE_RATINGS_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.WIKI_PAGE_RATINGS_ENABLED));

	public static final String WIKI_PAGE_TITLES_REGEXP = PropsUtil.get(PropsKeys.WIKI_PAGE_TITLES_REGEXP);

	public static final String WIKI_PAGE_TITLES_REMOVE_REGEXP = PropsUtil.get(PropsKeys.WIKI_PAGE_TITLES_REMOVE_REGEXP);

	public static final int WIKI_RSS_ABSTRACT_LENGTH = GetterUtil.getInteger(PropsUtil.get(PropsKeys.WIKI_RSS_ABSTRACT_LENGTH));

	public static final boolean XML_VALIDATION_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.XML_VALIDATION_ENABLED));

	public static final boolean XUGGLER_ENABLED = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.XUGGLER_ENABLED));

	public static final String XUGGLER_JAR_URL = PropsUtil.get(PropsKeys.XUGGLER_JAR_URL);

	public static final int YUI_COMPRESSOR_CSS_LINE_BREAK = GetterUtil.getInteger(PropsUtil.get(PropsKeys.YUI_COMPRESSOR_CSS_LINE_BREAK));

	public static final boolean YUI_COMPRESSOR_JS_DISABLE_OPTIMIZATIONS = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.YUI_COMPRESSOR_JS_DISABLE_OPTIMIZATIONS));

	public static final int YUI_COMPRESSOR_JS_LINE_BREAK = GetterUtil.getInteger(PropsUtil.get(PropsKeys.YUI_COMPRESSOR_JS_LINE_BREAK));

	public static final boolean YUI_COMPRESSOR_JS_MUNGE = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.YUI_COMPRESSOR_JS_MUNGE));

	public static final boolean YUI_COMPRESSOR_JS_PRESERVE_ALL_SEMICOLONS = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.YUI_COMPRESSOR_JS_PRESERVE_ALL_SEMICOLONS));

	public static final boolean YUI_COMPRESSOR_JS_VERBOSE = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.YUI_COMPRESSOR_JS_VERBOSE));

	static {
		if (!LAYOUT_USER_PRIVATE_LAYOUTS_ENABLED) {
			LAYOUT_USER_PRIVATE_LAYOUTS_AUTO_CREATE = false;
		}

		if (!LAYOUT_USER_PUBLIC_LAYOUTS_ENABLED) {
			LAYOUT_USER_PUBLIC_LAYOUTS_AUTO_CREATE = false;
		}
	}

}