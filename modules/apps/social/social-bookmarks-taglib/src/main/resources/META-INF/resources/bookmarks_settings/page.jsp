<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

List<KeyValuePair> leftList = new ArrayList<>();

Set<String> typesSet = new HashSet<>(Arrays.asList(types));

for (String socialBookmarkType : SocialBookmarksRegistryUtil.getSocialBookmarksTypes()) {
	SocialBookmark socialBookmark = SocialBookmarksRegistryUtil.getSocialBookmark(socialBookmarkType);

	if (!typesSet.contains(socialBookmarkType)) {
		leftList.add(new KeyValuePair(socialBookmarkType, socialBookmark.getName(locale)));
	}
}

leftList = ListUtil.sort(leftList, new KeyValuePairComparator(false, true));

List<KeyValuePair> rightList = new ArrayList<>();

for (String type : types) {
	SocialBookmark socialBookmark = SocialBookmarksRegistryUtil.getSocialBookmark(type);

	if (socialBookmark != null) {
		rightList.add(new KeyValuePair(type, socialBookmark.getName(locale)));
	}
}
%>

<aui:input name="preferences--socialBookmarksTypes--" type="hidden" value="<%= StringUtil.merge(types) %>" />

<liferay-ui:input-move-boxes
	leftBoxName="availableTypes"
	leftList="<%= leftList %>"
	leftTitle="available"
	rightBoxName="currentTypes"
	rightList="<%= rightList %>"
	rightReorder="<%= Boolean.TRUE.toString() %>"
	rightTitle="in-use"
/>

<label class="control-label" for="<portlet:namespace />typesOptions">
	<liferay-ui:message key="display-style" />
</label>

<div class="form-group" id="<portlet:namespace />typesOptions">

	<%
	for (String currentDisplayStyle : displayStyles) {
	%>

		<aui:input checked="<%= displayStyle.equals(currentDisplayStyle) %>" label="<%= currentDisplayStyle %>" name="preferences--socialBookmarksDisplayStyle--" type="radio" value="<%= currentDisplayStyle %>" />

	<%
	}
	%>

</div>

<aui:script>
	(function () {
		var Util = Liferay.Util;

		var socialBookmarksTypes = document.getElementById(
			'<portlet:namespace />socialBookmarksTypes'
		);
		var currentTypes = document.getElementById(
			'<portlet:namespace />currentTypes'
		);

		Liferay.after('inputmoveboxes:moveItem', () => {
			socialBookmarksTypes.value = Util.getSelectedOptionValues(currentTypes);
		});

		Liferay.after('inputmoveboxes:orderItem', () => {
			socialBookmarksTypes.value = Util.getSelectedOptionValues(currentTypes);
		});
	})();
</aui:script>