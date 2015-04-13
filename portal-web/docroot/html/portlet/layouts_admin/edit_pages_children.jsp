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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
String tabs4 = (String)request.getAttribute("edit_pages.jsp-tab4");

Group group = (Group)request.getAttribute("edit_pages.jsp-group");
Group liveGroup = (Group)request.getAttribute("edit_pages.jsp-liveGroup");
long groupId = ((Long)request.getAttribute("edit_pages.jsp-groupId")).longValue();
long selPlid = ((Long)request.getAttribute("edit_pages.jsp-selPlid")).longValue();
boolean privateLayout = ((Boolean)request.getAttribute("edit_pages.jsp-privateLayout")).booleanValue();
UnicodeProperties groupTypeSettings = (UnicodeProperties)request.getAttribute("edit_pages.jsp-groupTypeSettings");
Layout selLayout = (Layout)request.getAttribute("edit_pages.jsp-selLayout");

PortletURL redirectURL = (PortletURL)request.getAttribute("edit_pages.jsp-redirectURL");

List selLayoutChildren = null;

if (selLayout != null) {
	selLayoutChildren = selLayout.getChildren();
}
else {
	selLayoutChildren = LayoutLocalServiceUtil.getLayouts(groupId, privateLayout, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);
}

String tabs4Names = "new-page";

if ((selLayoutChildren != null) && !selLayoutChildren.isEmpty()) {
	tabs4Names += ",display-order";
}

if (!StringUtil.contains(tabs4Names, tabs4)) {
	tabs4 = "new-page";
}
%>

<liferay-ui:tabs
	names="<%= tabs4Names %>"
	param="tabs4"
	url='<%= redirectURL.toString() + "&" + renderResponse.getNamespace() + "selPlid=" + selPlid %>'
/>

<aui:input name="parentLayoutId" type="hidden" value="<%= (selLayout != null) ? selLayout.getLayoutId() : LayoutConstants.DEFAULT_PARENT_LAYOUT_ID %>" />
<aui:input name="layoutIds" type="hidden" />

<c:choose>
	<c:when test='<%= tabs4.equals("new-page") %>'>

		<%
		String name = ParamUtil.getString(request, "name");
		String type = ParamUtil.getString(request, "type");
		boolean hidden = ParamUtil.getBoolean(request, "hidden");

		String defaultLanguageId = LocaleUtil.toLanguageId(themeDisplay.getSiteDefaultLocale());
		%>

		<liferay-ui:message key="add-child-pages" />

		<br /><br />

		<table class="lfr-table">
		<tr>
			<td class="lfr-label">
				<liferay-ui:message key="name" />
			</td>
			<td>
				<input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" name="<portlet:namespace />name_<%= defaultLanguageId %>" size="30" type="text" value="<%= HtmlUtil.escape(name) %>" />
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<br />
			</td>
		</tr>

		<%
		List<LayoutPrototype> layoutPrototypes = LayoutPrototypeServiceUtil.search(company.getCompanyId(), Boolean.TRUE, null);
		%>

		<c:if test="<%= !layoutPrototypes.isEmpty() %>">
			<tr>
				<td>
					<liferay-ui:message key="template" />
				</td>
				<td colspan="2">
					<select id="<portlet:namespace />layoutPrototypeId" name="<portlet:namespace />layoutPrototypeId">
						<option selected value="">(<liferay-ui:message key="none" />)</option>

						<%
						for (LayoutPrototype layoutPrototype : layoutPrototypes) {
						%>

							<option value="<%= layoutPrototype.getLayoutPrototypeId() %>"><%= HtmlUtil.escape(layoutPrototype.getName(user.getLanguageId())) %></option>

						<%
						}
						%>

					</select>
				</td>
			</tr>
		</c:if>

		<tr class="hidden-field">
			<td class="lfr-label">
				<liferay-ui:message key="type" />
			</td>
			<td>
				<select name="<portlet:namespace />type">

					<%
					for (int i = 0; i < PropsValues.LAYOUT_TYPES.length; i++) {
						if (PropsValues.LAYOUT_TYPES[i].equals("article") && (group.isLayoutPrototype() || group.isLayoutSetPrototype())) {
							continue;
						}
					%>

						<option <%= type.equals(PropsValues.LAYOUT_TYPES[i]) ? "selected" : "" %> value="<%= PropsValues.LAYOUT_TYPES[i] %>"><%= LanguageUtil.get(pageContext, "layout.types." + PropsValues.LAYOUT_TYPES[i]) %></option>

					<%
					}
					%>

				</select>
			</td>
		</tr>
		<tr class="hidden-field">
			<td class="lfr-label">
				<liferay-ui:message key="hidden" />
			</td>
			<td>
				<liferay-ui:input-checkbox defaultValue="<%= hidden %>" param="hidden" />
			</td>
		</tr>

		<c:if test="<%= (selLayout != null) && selLayout.isTypePortlet() %>">
			<tr class="hidden-field">
				<td class="lfr-label">
					<liferay-ui:message key="copy-parent" />
				</td>
				<td>
					<liferay-ui:input-checkbox defaultValue="false" param="inheritFromParentLayoutId" />
				</td>
			</tr>
		</c:if>

		</table>

		<br />

		<input type="submit" value="<liferay-ui:message key="add-page" />" /><br />

		<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
			<c:if test="<%= !layoutPrototypes.isEmpty() %>">
				<aui:script use="aui-base">
					var layoutPrototypeIdSelect = A.one('#<portlet:namespace />layoutPrototypeId');

					function showHiddenFields() {
						var hiddenFields = A.all('.hidden-field');

						hiddenFields.hide();

						if (layoutPrototypeIdSelect && (layoutPrototypeIdSelect.val() == '')) {
							hiddenFields.show();
						}
						else {
							hiddenFields.hide();
						}
					}

					showHiddenFields();

					if (layoutPrototypeIdSelect) {
						layoutPrototypeIdSelect.on(
							'change',
							function(event) {
								showHiddenFields();
							}
						);
					}
				</aui:script>
			</c:if>
		</c:if>
	</c:when>
	<c:when test='<%= tabs4.equals("display-order") %>'>
		<liferay-ui:error exception="<%= RequiredLayoutException.class %>">

			<%
			RequiredLayoutException rle = (RequiredLayoutException)errorException;
			%>

			<c:if test="<%= rle.getType() == RequiredLayoutException.AT_LEAST_ONE %>">
				<liferay-ui:message key="you-must-have-at-least-one-page" />
			</c:if>

			<c:if test="<%= rle.getType() == RequiredLayoutException.FIRST_LAYOUT_TYPE %>">
				<liferay-ui:message key="your-first-page-must-have-one-of-the-following-types" />: <%= PortalUtil.getFirstPageLayoutTypes(pageContext) %>
			</c:if>
		</liferay-ui:error>

		<liferay-ui:message key="set-the-display-order-of-child-pages" />

		<br /><br />

		<table class="lfr-table">
		<tr>
			<td>
				<select name="<portlet:namespace />layoutIdsBox" size="7">

				<%
				for (int i = 0; i < selLayoutChildren.size(); i++) {
					Layout selLayoutChild = (Layout)selLayoutChildren.get(i);
				%>

					<option value="<%= selLayoutChild.getLayoutId() %>"><%= HtmlUtil.escape(selLayoutChild.getName(locale)) %></option>

				<%
				}
				%>

				</select>
			</td>
			<td class="lfr-top">
				<a href="javascript:Liferay.Util.reorder(document.<portlet:namespace />fm.<portlet:namespace />layoutIdsBox, 0);"><img alt="<liferay-ui:message key="move-up" />" border="0" height="16" hspace="0" src="<%= themeDisplay.getPathThemeImages() %>/arrows/02_up.png" vspace="2" width="16" /></a><br />

				<a href="javascript:Liferay.Util.reorder(document.<portlet:namespace />fm.<portlet:namespace />layoutIdsBox, 1);"><img alt="<liferay-ui:message key="move-down" />" border="0" height="16" hspace="0" src="<%= themeDisplay.getPathThemeImages() %>/arrows/02_down.png" vspace="2" width="16" /></a><br />

				<a href="javascript:<portlet:namespace />removePage(document.<portlet:namespace />fm.<portlet:namespace />layoutIdsBox);"><img alt="<liferay-ui:message key="remove" />" border="0" height="16" hspace="0" src="<%= themeDisplay.getPathThemeImages() %>/arrows/02_x.png" vspace="2" width="16" /></a><br />
			</td>
		</tr>
		</table>

		<br />

		<input onClick="<portlet:namespace />updateDisplayOrder();" type="button" value="<liferay-ui:message key="update-display-order" />" />
	</c:when>
	<c:when test='<%= tabs4.equals("merge-pages") %>'>

		<%
		boolean mergeGuestPublicPages = PropertiesParamUtil.getBoolean(groupTypeSettings, request, "mergeGuestPublicPages");
		%>

		<liferay-ui:message arguments="<%= HtmlUtil.escape(company.getGroup().getDescriptiveName(locale)) %>" key="you-can-configure-the-top-level-pages-of-this-public-site-to-merge-with-the-top-level-pages-of-the-public-x-site" />

		<br /><br />

		<table class="lfr-table">
		<tr>
			<td class="lfr-label">
				<liferay-ui:message arguments="<%= HtmlUtil.escape(company.getGroup().getDescriptiveName(locale)) %>" key="merge-x-public-pages" />
			</td>
			<td>
				<liferay-ui:input-checkbox defaultValue="<%= mergeGuestPublicPages %>" param="mergeGuestPublicPages" />
			</td>
		</tr>
		</table>

		<br />

		<input type="submit" value="<liferay-ui:message key="save" />" />
	</c:when>
</c:choose>