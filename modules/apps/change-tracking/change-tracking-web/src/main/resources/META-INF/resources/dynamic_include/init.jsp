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

<%@ taglib uri="http://liferay.com/tld/react" prefix="react" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.change.tracking.web.internal.constants.CTWebKeys" %><%@
page import="com.liferay.change.tracking.web.internal.display.context.ChangeTrackingIndicatorDisplayContext" %>

<liferay-theme:defineObjects />

<%
ChangeTrackingIndicatorDisplayContext changeTrackingIndicatorDisplayContext = (ChangeTrackingIndicatorDisplayContext)request.getAttribute(CTWebKeys.CHANGE_TRACKING_INDICATOR_DISPLAY_CONTEXT);
%>