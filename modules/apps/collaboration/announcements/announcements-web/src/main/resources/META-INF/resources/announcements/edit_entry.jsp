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

<%@ include file="/announcements/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

AnnouncementsEntry entry = (AnnouncementsEntry)request.getAttribute(AnnouncementsWebKeys.ANNOUNCEMENTS_ENTRY);

long entryId = BeanParamUtil.getLong(entry, request, "entryId");

String title = BeanParamUtil.getString(entry, request, "title");
String content = BeanParamUtil.getString(entry, request, "content");

boolean alert = BeanParamUtil.getBoolean(entry, request, "alert");

boolean displayImmediately = ParamUtil.getBoolean(request, "displayImmediately");

if (entry == null) {
	displayImmediately = true;
}

String headerTitle = null;

if (entry != null) {
	headerTitle = entry.getTitle();
}
else {
	if (alert) {
		headerTitle = LanguageUtil.get(resourceBundle, "new-alert");
	}
	else {
		headerTitle = LanguageUtil.get(resourceBundle, "new-announcement");
	}
}

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));

if (portletTitleBasedNavigation) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(redirect);

	renderResponse.setTitle(headerTitle);
}
%>

<div <%= portletTitleBasedNavigation ? "class=\"container-fluid-1280\"" : StringPool.BLANK %>>
	<aui:form method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveEntry();" %>'>
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="entryId" type="hidden" value="<%= entryId %>" />
		<aui:input name="alert" type="hidden" value="<%= alert %>" />

		<c:if test="<%= !portletTitleBasedNavigation %>">
			<liferay-ui:header
				backURL="<%= redirect %>"
				title="entry"
			/>
		</c:if>

		<liferay-ui:error exception="<%= EntryContentException.class %>" message="please-enter-valid-content" />
		<liferay-ui:error exception="<%= EntryDisplayDateException.class %>" message="please-enter-a-valid-display-date" />
		<liferay-ui:error exception="<%= EntryExpirationDateException.class %>" message="please-enter-a-valid-expiration-date" />
		<liferay-ui:error exception="<%= EntryTitleException.class %>" message="please-enter-a-valid-title" />
		<liferay-ui:error exception="<%= EntryURLException.class %>" message="please-enter-a-valid-url" />

		<aui:model-context bean="<%= entry %>" model="<%= AnnouncementsEntry.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<h1><liferay-ui:input-editor contents="<%= HtmlUtil.escape(title) %>" editorName="alloyeditor" name="titleEditor" placeholder="title" showSource="<%= false %>" /></h1>

				<aui:input name="title" type="hidden" />

				<liferay-ui:input-editor contents="<%= content %>" editorName='<%= PropsUtil.get("editor.wysiwyg.portal-web.docroot.html.portlet.announcements.edit_entry.jsp") %>' name="contentEditor" />

				<aui:input name="content" type="hidden" />
			</aui:fieldset>

			<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="configuration">
				<c:choose>
					<c:when test="<%= entry != null %>">
						<%@ include file="/announcements/entry_scope.jspf" %>
					</c:when>
					<c:otherwise>

						<%
						String distributionScope = ParamUtil.getString(request, "distributionScope");

						long classNameId = 0;
						long classPK = 0;

						String[] distributionScopeArray = StringUtil.split(distributionScope);

						if (distributionScopeArray.length == 2) {
							classNameId = GetterUtil.getLong(distributionScopeArray[0]);
							classPK = GetterUtil.getLong(distributionScopeArray[1]);
						}

						boolean submitOnChange = false;
						%>

						<%@ include file="/announcements/entry_select_scope.jspf" %>
					</c:otherwise>
				</c:choose>

				<aui:input name="url" />

				<aui:select name="type">

					<%
					for (String curType : AnnouncementsEntryConstants.TYPES) {
					%>

						<aui:option label="<%= curType %>" selected="<%= (entry != null) && curType.equals(entry.getType()) %>" />

					<%
					}
					%>

				</aui:select>

				<c:choose>
					<c:when test="<%= alert %>">
						<aui:select disabled="<%= true %>" name="priority">
							<aui:option label="important" selected="<%= true %>" value="1" />
						</aui:select>
					</c:when>
					<c:otherwise>
						<aui:select name="priority">
							<aui:option label="normal" selected="<%= (entry != null) && (entry.getPriority() == 0) %>" value="0" />
							<aui:option label="important" selected="<%= (entry != null) && (entry.getPriority() == 1) %>" value="1" />
						</aui:select>
					</c:otherwise>
				</c:choose>

				<aui:input dateTogglerCheckboxLabel="display-immediately" disabled="<%= displayImmediately %>" name="displayDate" />

				<aui:input name="expirationDate" />
			</aui:fieldset>
		</aui:fieldset-group>

		<aui:button-row>
			<aui:button cssClass="btn-lg" primary="<%= true %>" type="submit" />

			<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:form>
</div>

<aui:script>
	function <portlet:namespace />getContent() {
		return window.<portlet:namespace />contentEditor.getHTML();
	}

	function <portlet:namespace />getTitle() {
		return window.<portlet:namespace />titleEditor.getText();
	}

	function <portlet:namespace />saveEntry() {
		document.<portlet:namespace />fm.action = '<portlet:actionURL name="/announcements/edit_entry"><portlet:param name="mvcRenderCommandName" value="/announcements/edit_entry" /></portlet:actionURL>';
		document.<portlet:namespace />fm.target = '';
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= (entry == null) ? Constants.ADD : Constants.UPDATE %>';
		document.<portlet:namespace />fm.<portlet:namespace />content.value = <portlet:namespace />getContent();
		document.<portlet:namespace />fm.<portlet:namespace />title.value = <portlet:namespace />getTitle();

		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>