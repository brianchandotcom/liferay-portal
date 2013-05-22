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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.resiliency.spi.agent.annotation.Direction;
import com.liferay.portal.kernel.resiliency.spi.agent.annotation.Distributed;

/**
 * @author Brian Wing Shun Chan
 */
public interface WebKeys {

	public static final String ASSET_RENDERER = "ASSET_RENDERER";

	public static final String ASSET_RENDERER_FACTORY = "ASSET_RENDERER_FACTORY";

	public static final String ASSET_RENDERER_FACTORY_CLASS_TYPE_ID = "ASSET_RENDERER_FACTORY_CLASS_TYPE_ID";

	public static final String AUI_SCRIPT_DATA = "LIFERAY_SHARED_AUI_SCRIPT_DATA";

	public static final String AUTHENTICATION_TOKEN = "LIFERAY_SHARED_AUTHENTICATION_TOKEN";

	public static final String CLP_MESSAGE_LISTENERS = "CLP_MESSAGE_LISTENERS";

	public static final String CTX = "CTX";

	public static final String CTX_PATH = "CTX_PATH";

	public static final String CURRENT_COMPLETE_URL = "CURRENT_COMPLETE_URL";

	public static final String CURRENT_URL = "CURRENT_URL";

	public static final String EXTEND_SESSION = "EXTEND_SESSION";

	public static final String FILE_ITEM_THRESHOLD_SIZE_EXCEEDED = "FILE_ITEM_THRESHOLD_SIZE_EXCEEDED";

	public static final String FORGOT_PASSWORD_REMINDER_ATTEMPTS = "FORGOT_PASSWORD_REMINDER_ATTEMPTS";

	public static final String FORGOT_PASSWORD_REMINDER_USER = "FORGOT_PASSWORD_REMINDER_USER";

	public static final String FORGOT_PASSWORD_REMINDER_USER_EMAIL_ADDRESS = "FORGOT_PASSWORD_REMINDER_USER_EMAIL_ADDRESS";

	public static final String INVOKER_FILTER_URI = "INVOKER_FILTER_URI";

	public static final String JAVASCRIPT_CONTEXT = "JAVASCRIPT_CONTEXT";

	public static final String JOURNAL_ARTICLE = "JOURNAL_ARTICLE";

	public static final String JOURNAL_ARTICLE_DISPLAY = "JOURNAL_ARTICLE_DISPLAY";

	public static final String LAST_PATH = "LAST_PATH";

	public static final String LAYOUT = "LAYOUT";

	public static final String LAYOUTS = "LAYOUTS";

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #VISITED_GROUP_ID_PREVIOUS}
	 */
	public static final String LIFERAY_SHARED_VISITED_GROUP_ID_PREVIOUS = "LIFERAY_SHARED_VISITED_GROUP_ID_PREVIOUS";

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #VISITED_GROUP_ID_RECENT}
	 */
	public static final String LIFERAY_SHARED_VISITED_GROUP_ID_RECENT = "LIFERAY_SHARED_VISITED_GROUP_ID_RECENT";

	public static final String OSGI_BUNDLE = "OSGI_BUNDLE";

	public static final String OUTPUT_DATA = "LIFERAY_SHARED_OUTPUT_DATA";

	public static final String PAGE_BODY_BOTTOM = "PAGE_BODY_BOTTOM";

	public static final String PAGE_BODY_TOP = "PAGE_BODY_TOP";

	public static final String PAGE_BOTTOM = "PAGE_BOTTOM";

	public static final String PAGE_DESCRIPTION = "LIFERAY_SHARED_PAGE_DESCRIPTION";

	public static final String PAGE_KEYWORDS = "LIFERAY_SHARED_PAGE_KEYWORDS";

	public static final String PAGE_SUBTITLE = "LIFERAY_SHARED_PAGE_SUBTITLE";

	public static final String PAGE_TITLE = "LIFERAY_SHARED_PAGE_TITLE";

	public static final String PAGE_TOP = "PAGE_TOP";

	public static final String PARALLEL_RENDERING_MERGE_LOCK = "PARALLEL_RENDERING_MERGE_LOCK";

	public static final String PARALLEL_RENDERING_TIMEOUT_ERROR = "PARALLEL_RENDERING_TIMEOUT_ERROR";

	public static final String PORTLET_CONFIGURATOR_VISIBILITY = "PORTLET_CONFIGURATOR_VISIBILITY";

	public static final String PORTLET_DECORATE = "PORTLET_DECORATE";

	public static final String PORTLET_ID = "PORTLET_ID";

	public static final String PORTLET_PREFERENCES_MAP = "PORTLET_PREFERENCES_MAP";

	public static final String PORTLET_RESOURCE_STATIC_URLS = "PORTLET_RESOURCE_STATIC_URLS";

	public static final String REDIRECT = "REDIRECT";

	public static final String REFERER = "referer";

	public static final String RENDER_PATH = "RENDER_PATH";

	public static final String RENDER_PORTLET = "RENDER_PORTLET";

	public static final String RENDER_PORTLET_COLUMN_COUNT = "RENDER_PORTLET_COLUMN_COUNT";

	public static final String RENDER_PORTLET_COLUMN_ID = "RENDER_PORTLET_COLUMN_ID";

	public static final String RENDER_PORTLET_COLUMN_POS = "RENDER_PORTLET_COLUMN_POS";

	public static final String RENDER_PORTLET_RESOURCE = "RENDER_PORTLET_RESOURCE";

	public static final String SEARCH_CONTAINER = "SEARCH_CONTAINER";

	public static final String SEARCH_CONTAINER_RESULT_ROW = "SEARCH_CONTAINER_RESULT_ROW";

	public static final String SEARCH_CONTAINER_RESULT_ROW_ENTRY = "SEARCH_CONTAINER_RESULT_ROW_ENTRY";

	public static final String SEARCH_SEARCH_RESULTS = "SEARCH_SEARCH_RESULTS";

	public static final String SERVLET_CONTEXT_INCLUDE_FILTER_PATH = "SERVLET_CONTEXT_INCLUDE_FILTER_PATH";

	public static final String SERVLET_CONTEXT_INCLUDE_FILTER_STRICT = "SERVLET_CONTEXT_INCLUDE_FILTER_STRICT";

	public static final String SERVLET_CONTEXT_INCLUDE_FILTER_THEME = "SERVLET_CONTEXT_INCLUDE_FILTER_THEME";

	public static final String SERVLET_PATH = "SERVLET_PATH";

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

	public static final String THEME = "THEME";

	public static final String THEME_DISPLAY = "LIFERAY_SHARED_THEME_DISPLAY";

	public static final String UNIQUE_ELEMENT_IDS = "LIFERAY_SHARED_UNIQUE_ELEMENT_IDS";

	public static final String UPLOAD_EXCEPTION = "UPLOAD_EXCEPTION";

	public static final String USER = "USER";

	public static final String USER_ID = "USER_ID";

	public static final String USER_PASSWORD = "USER_PASSWORD";

	public static final String USER_UUID = "USER_UUID";

	public static final String VISITED_GROUP_ID_PREVIOUS = "LIFERAY_SHARED_VISITED_GROUP_ID_PREVIOUS";

	public static final String VISITED_GROUP_ID_RECENT = "LIFERAY_SHARED_VISITED_GROUP_ID_RECENT";

	public static final String VM_VARIABLES = "VM_VARIABLES";

	public static final String WINDOW_STATE = "WINDOW_STATE";

	public static final String XUGGLER_INSTALL_STATUS = "XUGGLER_INSTALL_STATUS";

}