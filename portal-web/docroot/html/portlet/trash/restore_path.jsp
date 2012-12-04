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

<%@ include file="/html/portlet/trash/init.jsp" %>

<c:if test="<%= SessionMessages.contains(renderRequest, portletDisplay.getId() + SessionMessages.KEY_SUFFIX_DELETE_SUCCESS_DATA) %>">
	<div class="portlet-msg-success">

		<%
		Map<String, List<String>> data = (HashMap<String, List<String>>)SessionMessages.get(renderRequest, portletDisplay.getId() + SessionMessages.KEY_SUFFIX_DELETE_SUCCESS_DATA);

		List<String> restoreLinks = data.get("restoreLinks");
		List<String> restoreMessages = data.get("restoreMessages");
		%>

		<c:choose>
			<c:when test="<%= (data != null) && (restoreLinks != null) && (restoreMessages != null) && (restoreLinks.size() > 0) && (restoreMessages.size() > 0) %>">

				<%
				StringBundler sb = new StringBundler(5 * restoreMessages.size());

				for (int i = 0; i < restoreLinks.size(); i++) {
					sb.append("<a href=\"");
					sb.append(restoreLinks.get(i));
					sb.append("\">");
					sb.append(restoreMessages.get(i));
					sb.append("</a> ");
				}
				%>

				<liferay-ui:message arguments="<%= sb.toString() %>" key="the-item-has-been-restored-to-x" />
			</c:when>
			<c:otherwise>
				<liferay-ui:message key="the-item-has-been-restored" />
			</c:otherwise>
		</c:choose>
	</div>
</c:if>

<liferay-ui:restore-entry />

<portlet:actionURL var="selectContainerURL">
	<portlet:param name="struts_action" value="/trash/edit_entry" />
</portlet:actionURL>

<aui:form action="<%= selectContainerURL.toString() %>" method="post" name="selectContainerForm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.MOVE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="className" type="hidden" value="" />
	<aui:input name="classPK" type="hidden" value="" />
	<aui:input name="containerModelId" type="hidden" value="" />
</aui:form>

<aui:script use="aui-dialog-iframe,liferay-util-window">
	A.getBody().delegate(
		'click',
		function(event) {
			var target = event.target;

			<portlet:namespace />restoreDialog(target.attr('data-uri'));
		},
		'.trash-restore-link'
	);

	Liferay.provide(
		window,
		'<portlet:namespace />restoreDialog',
		function(uri) {
			Liferay.Util.openWindow(
				{
					dialog: {
						align: Liferay.Util.Window.ALIGN_CENTER,
						cssClass: '',
						modal: true,
						width: 700
					},
					title: '<%= UnicodeLanguageUtil.get(pageContext, "warning") %>',
					uri: uri
				}
			);
		},
		['aui-base']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />submitForm',
		function(redirect, className, classPK, containerModelId) {
			document.<portlet:namespace />selectContainerForm.<portlet:namespace />redirect.value = redirect;
			document.<portlet:namespace />selectContainerForm.<portlet:namespace />className.value = className;
			document.<portlet:namespace />selectContainerForm.<portlet:namespace />classPK.value = classPK;
			document.<portlet:namespace />selectContainerForm.<portlet:namespace />containerModelId.value = containerModelId;

			submitForm(document.<portlet:namespace />selectContainerForm);
		},
		['aui-base']
	);
</aui:script>