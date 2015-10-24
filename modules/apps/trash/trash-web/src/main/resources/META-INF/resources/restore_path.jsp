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

if (Validator.isNull(redirect)) {
	PortletURL portletURL = renderResponse.createRenderURL();

	redirect = portletURL.toString();
}
%>

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
						type = ResourceActionsUtil.getModelResource(request, restoreClassName);
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

<portlet:resourceURL id="checkEntry" var="checkEntryURL" />

<portlet:renderURL var="duplicateEntryURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
	<portlet:param name="mvcPath" value="/restore_entry.jsp" />
	<portlet:param name="redirect" value="<%= redirect %>" />
</portlet:renderURL>

<aui:script use="liferay-restore-entry">
	new Liferay.RestoreEntry(
		{
			checkEntryURL: '<%= checkEntryURL.toString() %>',
			duplicateEntryURL: '<%= duplicateEntryURL.toString() %>',
			namespace: '<portlet:namespace />'
		}
	);
</aui:script>

<portlet:actionURL name="moveEntry" var="selectContainerURL" />

<aui:form action="<%= selectContainerURL.toString() %>" method="post" name="selectContainerForm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="className" type="hidden" value="" />
	<aui:input name="classPK" type="hidden" value="" />
	<aui:input name="containerModelId" type="hidden" value="" />
</aui:form>

<aui:script>
	function <portlet:namespace />restoreDialog(uri) {
		Liferay.Util.selectEntity(
			{
				dialog: {
					constrain: true,
					destroyOnHide: true,
					modal: true,
					width: 1024
				},
				eventName: '<portlet:namespace />selectContainer',
				id: '<portlet:namespace />selectContainer',
				title: '<liferay-ui:message key="warning" />',
				uri: uri
			},
			function(event) {
				var form = AUI.$(document.<portlet:namespace />selectContainerForm);

				form.fm('className').val(event.classname);
				form.fm('classPK').val(event.classpk);
				form.fm('containerModelId').val(event.containermodelid);
				form.fm('redirect').val(event.redirect);

				submitForm(form);
			}
		);
	}
</aui:script>