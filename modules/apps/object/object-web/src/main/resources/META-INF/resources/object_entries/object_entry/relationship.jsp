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

<%@ include file="/init.jsp" %>

<%
String backURL = ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL()));

ObjectEntryDisplayContext objectEntryDisplayContext = (ObjectEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

ObjectEntry objectEntry = objectEntryDisplayContext.getObjectEntry();
ObjectLayoutTab objectLayoutTab = objectEntryDisplayContext.getObjectLayoutTab();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);
%>

<clay:data-set-display
	contextParams='<%=
		HashMapBuilder.<String, String>put(
			"objectEntryId", String.valueOf(objectEntry.getObjectEntryId())
		).put(
			"objectRelationshipId", String.valueOf(objectLayoutTab.getObjectRelationshipId())
		).build()
	%>'
	dataProviderKey="<%= RelatedObjectEntryClayDataSetDisplayNames.RELATED_ITEMS %>"
	formId="fm"
	id="<%= RelatedObjectEntryClayDataSetDisplayNames.RELATED_ITEMS %>"
	itemsPerPage="<%= 20 %>"
	namespace="<%= liferayPortletResponse.getNamespace() %>"
	pageNumber="<%= 1 %>"
	portletURL="<%= liferayPortletResponse.createRenderURL() %>"
	style="fluid"
/>