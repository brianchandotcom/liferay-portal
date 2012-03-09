<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/taglib/ui/toggle_area/init.jsp" %>

<div style="float: <%= align %>">
	<liferay-ui:toggle
		defaultShowContent="<%= defaultShowContent %>"
		hideImage="<%= hideImage %>"
		hideMessage="<%= hideMessage %>"
		id="<%= id %>"
		showImage="<%= showImage %>"
		showMessage="<%= showMessage %>"
		stateVar="<%= stateVar %>"
	/>
</div>

<%
String clickValue = SessionClicks.get(request, id, null);

if (clickValue == null) {
	if (defaultShowContent) {
		clickValue = "block";
	}
	else {
		clickValue = "none";
	}
}
else if (clickValue.equals(StringPool.BLANK)) {
	clickValue = "block";
}
%>

<div id="<%= id %>" style="display: <%= clickValue %>;">