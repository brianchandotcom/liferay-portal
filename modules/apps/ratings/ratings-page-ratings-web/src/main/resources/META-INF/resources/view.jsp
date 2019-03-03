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

<%@ include file="/init.jsp" %>

<%
String className = Layout.class.getName();
long classPK = layout.getPlid();

AssetEntry assetEntry = (AssetEntry)request.getAttribute(WebKeys.LAYOUT_ASSET_ENTRY);

if ((assetEntry != null) && Objects.equals(layout.getType(), LayoutConstants.TYPE_ASSET_DISPLAY)) {
	className = assetEntry.getClassName();
	classPK = assetEntry.getClassPK();
}
%>

<liferay-ui:ratings
	className="<%= className %>"
	classPK="<%= classPK %>"
	inTrash="<%= false %>"
/>