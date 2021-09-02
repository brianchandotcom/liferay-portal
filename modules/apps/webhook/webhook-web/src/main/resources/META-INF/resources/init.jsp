<%--
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
--%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.webhook.constants.WebhookConstants" %><%@
page import="com.liferay.webhook.exception.DuplicateWebhookEntryException" %><%@
page import="com.liferay.webhook.exception.WebhookEntryDestinationNameException" %><%@
page import="com.liferay.webhook.exception.WebhookEntryDestinationWebhookEventKeysException" %><%@
page import="com.liferay.webhook.exception.WebhookEntryNameException" %><%@
page import="com.liferay.webhook.exception.WebhookEntryURLException" %><%@
page import="com.liferay.webhook.model.WebhookEntry" %><%@
page import="com.liferay.webhook.web.internal.constants.WebhookClayDataSetDisplayNames" %><%@
page import="com.liferay.webhook.web.internal.constants.WebhookPortletKeys" %><%@
page import="com.liferay.webhook.web.internal.constants.WebhookWebKeys" %><%@
page import="com.liferay.webhook.web.internal.display.context.EditWebhookEntryDisplayContext" %><%@
page import="com.liferay.webhook.web.internal.display.context.SelectDestinationWebhookEventsDisplayContext" %><%@
page import="com.liferay.webhook.web.internal.display.context.WebhookDisplayContext" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />