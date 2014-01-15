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

<%@ include file="/html/portlet/trash/init.jsp" %>

<c:if test="<%= SessionMessages.contains(renderRequest, portletDisplay.getId() + SessionMessages.KEY_SUFFIX_DELETE_SUCCESS_DATA) %>">
	<div class="alert alert-success">

		<%
		Map<String, List<String>> data = (HashMap<String, List<String>>)SessionMessages.get(renderRequest, portletDisplay.getId() + SessionMessages.KEY_SUFFIX_DELETE_SUCCESS_DATA);

		List<String> restoreClassNames = data.get("restoreClassNames");
		List<String> restoreEntryMessages = data.get("restoreEntryMessages");
		List<String> restoreEntryLinks = data.get("restoreEntryLinks");
		List<String> restoreLinks = data.get("restoreLinks");
		List<String> restoreMessages = data.get("restoreMessages");
		%>

		<c:choose>
			<c:when test="<%= (data != null) && (restoreLinks != null) && (restoreMessages != null) && (restoreLinks.size() > 0) && (restoreMessages.size() > 0) %>">

				<%
				for (int i = 0; i < restoreLinks.size(); i++) {
					String type = "selected-item";

					String restoreClassName = restoreClassNames.get(i);

					if (Validator.isNotNull(restoreClassName)) {
						type = ResourceActionsUtil.getModelResource(pageContext, restoreClassName);
					}
				%>

					<liferay-util:buffer var="entityLink">
						<em class="restore-entry-title"><aui:a href="<%= restoreEntryLinks.get(i) %>" label="<%= HtmlUtil.escape(restoreEntryMessages.get(i)) %>" /></em>
					</liferay-util:buffer>

					<liferay-util:buffer var="link">
						<em class="restore-entry-title"><aui:a href="<%= restoreLinks.get(i) %>" label="<%= HtmlUtil.escape(restoreMessages.get(i)) %>" /></em>
					</liferay-util:buffer>

					<liferay-ui:message arguments="<%= new Object[] {type, entityLink.trim(), link.trim()} %>" key="the-x-x-was-restored-to-x" translateArguments="<%= false %>" />

				<%
				}
				%>

			</c:when>
			<c:otherwise>
				<liferay-ui:message key="the-item-was-restored" />
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

<aui:script use="aui-dialog-iframe-deprecated,liferay-util-window">
	A.getBody().delegate(
		'click',
		function(event) {
			var link = event.currentTarget.one('a');

			<portlet:namespace />restoreDialog(link.attr('data-uri'));
		},
		'.trash-restore-link'
	);

	Liferay.provide(
		window,
		'<portlet:namespace />restoreDialog',
		function(uri) {
			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						destroyOnHide: true,
						modal: true,
						width: 1024
					},
					eventName: '<portlet:namespace />selectFolder',
					id: '<portlet:namespace />selectFolder',
					title: '<liferay-ui:message key="warning" />',
					uri: uri
				},
				function(event) {
					document.<portlet:namespace />selectContainerForm.<portlet:namespace />className.value = event.classname;
					document.<portlet:namespace />selectContainerForm.<portlet:namespace />classPK.value = event.classpk;
					document.<portlet:namespace />selectContainerForm.<portlet:namespace />containerModelId.value = event.containermodelid;

					submitForm(document.<portlet:namespace />selectContainerForm);
				}
			);
		},
		['aui-base']
	);
</aui:script>