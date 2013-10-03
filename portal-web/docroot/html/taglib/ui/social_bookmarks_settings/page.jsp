<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/taglib/init.jsp" %>

<%
String displayPosition = (String)request.getAttribute("liferay-ui:social-bookmarks-settings:displayPosition");
String displayStyle = (String)request.getAttribute("liferay-ui:social-bookmarks-settings:displayStyle");
boolean enabled = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:social-bookmarks-settings:enabled"));
String types = (String)request.getAttribute("liferay-ui:social-bookmarks-settings:types");

String[] displayStyles = PropsUtil.getArray(PropsKeys.SOCIAL_BOOKMARK_DISPLAY_STYLES);

if (Validator.isNull(displayStyle)) {
	displayStyle = displayStyles[0];
}
%>

<aui:fieldset>
	<aui:input name="preferences--enableSocialBookmarks--" type="checkbox" value="<%= enabled %>" />

	<div class="social-boomarks-options" id="<portlet:namespace />socialBookmarksOptions">
		<aui:select label="display-style" name="preferences--socialBookmarksDisplayStyle--">

			<%
			for (String curDisplayStyle : PropsUtil.getArray(PropsKeys.SOCIAL_BOOKMARK_DISPLAY_STYLES)) {
			%>

			<aui:option label="<%= curDisplayStyle %>" selected="<%= displayStyle.equals(curDisplayStyle) %>" />

			<%
			}
			%>

		</aui:select>

		<aui:select label="display-position" name="preferences--socialBookmarksDisplayPosition--">
			<aui:option label="top" selected='<%= displayPosition.equals("top") %>' />
			<aui:option label="bottom" selected='<%= displayPosition.equals("bottom") %>' />
		</aui:select>

		<c:if test="<%= Validator.isNotNull(types) %>">
			<aui:field-wrapper label="social-bookmarks">

				<%
				String[] socialBookmarksTypesArray = StringUtil.split(types);

				for (String type : PropsUtil.getArray(PropsKeys.SOCIAL_BOOKMARK_TYPES)) {
				%>

					<aui:field-wrapper inlineLabel="right" label="<%= type %>" name='<%= "socialBookmarksTypes" + type %>'>
						<input <%= ArrayUtil.contains(socialBookmarksTypesArray, type) ? "checked": "" %> id="<portlet:namespace />socialBookmarksTypes<%= type %>" name="<portlet:namespace />preferences--socialBookmarksTypes--" type="checkbox" value="<%= type %>" />
					</aui:field-wrapper>

				<%
				}
				%>

			</aui:field-wrapper>
		</c:if>
	</div>
</aui:fieldset>

<aui:script use="aui-base">
	Liferay.Util.toggleBoxes('<portlet:namespace />enableSocialBookmarksCheckbox','<portlet:namespace />socialBookmarksOptions');
</aui:script>