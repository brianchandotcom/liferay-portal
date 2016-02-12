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
String redirect = ParamUtil.getString(request, "redirect");

AnnouncementsEntry entry = (AnnouncementsEntry)request.getAttribute(WebKeys.ANNOUNCEMENTS_ENTRY);

long entryId = BeanParamUtil.getLong(entry, request, "entryId");

String content = BeanParamUtil.getString(entry, request, "content");

boolean displayImmediately = ParamUtil.getBoolean(request, "displayImmediately");

if (entry == null) {
	displayImmediately = true;
}
%>

<aui:form method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveEntry();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="entryId" type="hidden" value="<%= entryId %>" />
	<aui:input name="alert" type="hidden" value="<%= portletName.equals(AnnouncementsPortletKeys.ALERTS) %>" />

	<liferay-ui:header
		backURL="<%= redirect %>"
		title="entry"
	/>

	<liferay-ui:error exception="<%= EntryContentException.class %>" message="please-enter-valid-content" />
	<liferay-ui:error exception="<%= EntryDisplayDateException.class %>" message="please-enter-a-valid-display-date" />
	<liferay-ui:error exception="<%= EntryExpirationDateException.class %>" message="please-enter-a-valid-expiration-date" />
	<liferay-ui:error exception="<%= EntryTitleException.class %>" message="please-enter-a-valid-title" />
	<liferay-ui:error exception="<%= EntryURLException.class %>" message="please-enter-a-valid-url" />

	<aui:model-context bean="<%= entry %>" model="<%= AnnouncementsEntry.class %>" />

	<aui:fieldset>
		<c:choose>
			<c:when test="<%= entry != null %>">

				<%@ include file="/entry_scope.jspf" %>

			</c:when>
			<c:otherwise>

				<%
				String distributionScope = ParamUtil.getString(request, "distributionScope");

				long classNameId = -1;
				long classPK = -1;

				String[] distributionScopeArray = StringUtil.split(distributionScope);

				if (distributionScopeArray.length == 2) {
					classNameId = GetterUtil.getLong(distributionScopeArray[0]);
					classPK = GetterUtil.getLong(distributionScopeArray[1]);
				}

				boolean submitOnChange = false;
				%>

				<%@ include file="/entry_select_scope.jspf" %>

			</c:otherwise>
		</c:choose>

		<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" name="title" />

		<aui:input name="url" />

		<aui:field-wrapper label="content">
			<liferay-ui:input-editor contents="<%= content %>" editorName='<%= PropsUtil.get("editor.wysiwyg.portal-web.docroot.html.portlet.announcements.edit_entry.jsp") %>' />

			<aui:input name="content" type="hidden" />
		</aui:field-wrapper>

		<aui:select name="type">

			<%
			for (String curType : AnnouncementsEntryConstants.TYPES) {
			%>

				<aui:option label="<%= curType %>" selected="<%= (entry != null) && curType.equals(entry.getType()) %>" />

			<%
			}
			%>

		</aui:select>

		<aui:select name="priority">
			<aui:option label="normal" selected="<%= (entry != null) && (entry.getPriority() == 0) %>" value="0" />
			<aui:option label="important" selected="<%= (entry != null) && (entry.getPriority() == 1) %>" value="1" />
		</aui:select>

		<aui:input dateTogglerCheckboxLabel="display-immediately" disabled="<%= displayImmediately %>" name="displayDate" />

		<aui:input name="expirationDate" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" onClick='<%= renderResponse.getNamespace() + "previewEntry();" %>' value="preview" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />getContent() {
		return window.<portlet:namespace />editor.getHTML();
	}

	function <portlet:namespace />previewEntry() {
		document.<portlet:namespace />fm.action = '<portlet:renderURL><portlet:param name="mvcRenderCommandName" value="/announcements/preview_entry" /></portlet:renderURL>';
		document.<portlet:namespace />fm.target = '_blank';
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= Constants.PREVIEW %>';
		document.<portlet:namespace />fm.<portlet:namespace />content.value = <portlet:namespace />getContent();
		document.<portlet:namespace />fm.submit();
	}

	function <portlet:namespace />saveEntry() {
		document.<portlet:namespace />fm.action = '<portlet:actionURL name="/announcements/edit_entry" />';
		document.<portlet:namespace />fm.target = '';
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= (entry == null) ? Constants.ADD : Constants.UPDATE %>';
		document.<portlet:namespace />fm.<portlet:namespace />content.value = <portlet:namespace />getContent();

		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>