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
String displayStyle = (String)request.getAttribute("liferay-social-bookmarks:bookmarks-settings:displayStyle");
String[] types = (String[])request.getAttribute("liferay-social-bookmarks:bookmarks-settings:types");

String[] displayStyles = {"inline", "menu"};

if (Validator.isNull(displayStyle)) {
	displayStyle = displayStyles[0];
}

// Left list

List leftList = new ArrayList();

for (int i = 0; i < types.length; i++) {
	SocialBookmark socialBookmark = SocialBookmarksRegistryUtil.getSocialBookmark(types[i]);

	if (socialBookmark != null) {
		leftList.add(new KeyValuePair(types[i], socialBookmark.getName(locale)));
	}
}

// Right list

List rightList = new ArrayList();

Set<String> typesSet = new HashSet<>(Arrays.asList(types));

for (String curType : SocialBookmarksRegistryUtil.getSocialBookmarksTypes()) {
	SocialBookmark socialBookmark = SocialBookmarksRegistryUtil.getSocialBookmark(curType);

	if (!typesSet.contains(curType)) {
		rightList.add(new KeyValuePair(curType, socialBookmark.getName(locale)));
	}
}

rightList = ListUtil.sort(rightList, new KeyValuePairComparator(false, true));
%>

<aui:input name="preferences--socialBookmarksTypes--" type="hidden" value="<%= String.join(StringPool.COMMA, types) %>" />

<liferay-ui:input-move-boxes
	leftBoxName="currentTypes"
	leftList="<%= leftList %>"
	leftReorder="<%= Boolean.TRUE.toString() %>"
	leftTitle="current"
	rightBoxName="availableTypes"
	rightList="<%= rightList %>"
	rightTitle="available"
/>

<h5>
	<liferay-ui:message key="display-style" />
</h5>

<div class="form-group" id="<portlet:namespace />typesOptions">

	<%
	for (String curDisplayStyle : displayStyles) {
	%>

		<aui:input checked="<%= displayStyle.equals(curDisplayStyle) %>" label="<%= curDisplayStyle %>" name="preferences--socialBookmarksDisplayStyle--" type="radio" value="<%= curDisplayStyle %>" />

	<%
	}
	%>

</div>

<aui:script sandbox="<%= true %>">
	var Util = Liferay.Util;

	var socialBookmarksTypes = AUI.$('#<portlet:namespace />socialBookmarksTypes');
	var currentTypes = AUI.$('#<portlet:namespace />currentTypes');

	Liferay.after(
		'inputmoveboxes:moveItem',
		function(event) {
			socialBookmarksTypes.val(Util.listSelect(currentTypes));
		}
	);

	Liferay.after(
		'inputmoveboxes:orderItem',
		function(event) {
			socialBookmarksTypes.val(Util.listSelect(currentTypes));
		}
	);
</aui:script>