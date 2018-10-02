<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/html/portal/init.jsp" %>

<%
Map<String, String> attributes = new HashMap<>();

attributes.put("content", (String)request.getAttribute("tilesContent"));
attributes.put("pop_up", (String)request.getAttribute("tilesPopUp"));
attributes.put("title", (String)request.getAttribute("tilesTitle"));

request.setAttribute(PortalTilesPlugin.DEFINITION, new Definition(StringPool.BLANK, attributes));
%>

<liferay-util:include page="/html/common/themes/portal.jsp" />