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

// Left list

List<KeyValuePair> leftList = new ArrayList<>();

Set<String> typesSet = new HashSet<>(Arrays.asList(types));

for (String curType : SocialBookmarksRegistryUtil.getSocialBookmarksTypes()) {
	SocialBookmark socialBookmark = SocialBookmarksRegistryUtil.getSocialBookmark(curType);

	if (!typesSet.contains(curType)) {
		leftList.add(new KeyValuePair(curType, socialBookmark.getName(locale)));
	}
}

leftList = ListUtil.sort(leftList, new KeyValuePairComparator(false, true));

// Right list

List<KeyValuePair> rightList = new ArrayList<>();

for (int i = 0; i < types.length; i++) {
	SocialBookmark socialBookmark = SocialBookmarksRegistryUtil.getSocialBookmark(types[i]);

	if (socialBookmark != null) {
		rightList.add(new KeyValuePair(types[i], socialBookmark.getName(locale)));
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
	for (String curDisplayStyle : displayStyles) {
	%>

		<aui:input checked="<%= displayStyle.equals(curDisplayStyle) %>" label="<%= curDisplayStyle %>" name="preferences--socialBookmarksDisplayStyle--" type="radio" value="<%= curDisplayStyle %>" />

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

		Liferay.after('inputmoveboxes:moveItem', () => {
			setTimeout(() => {
				var currentTypes = document.getElementById(
					'<portlet:namespace />currentTypes'
				);
				socialBookmarksTypes.value =
					Util.getSelectedOptionValues(currentTypes);
			});
		});

		Liferay.after('inputmoveboxes:orderItem', () => {
			setTimeout(() => {
				var currentTypes = document.getElementById(
					'<portlet:namespace />currentTypes'
				);
				socialBookmarksTypes.value =
					Util.getSelectedOptionValues(currentTypes);
			});
		});
	})();
</aui:script>