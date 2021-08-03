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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider" %><%@
page import="com.liferay.object.model.ObjectEntry" %><%@
page import="com.liferay.object.web.internal.constants.ObjectWebKeys" %><%@
page import="com.liferay.portal.kernel.util.Validator" %>

<%@ page import="java.io.Serializable" %>

<%@ page import="java.util.Map" %>

<liferay-theme:defineObjects />

<%
AssetDisplayPageFriendlyURLProvider assetDisplayPageFriendlyURLProvider = (AssetDisplayPageFriendlyURLProvider)request.getAttribute(AssetDisplayPageFriendlyURLProvider.class.getName());
%>