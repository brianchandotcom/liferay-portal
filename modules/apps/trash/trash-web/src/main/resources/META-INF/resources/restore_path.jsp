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

<c:if test="<%= SessionMessages.contains(renderRequest, portletDisplay.getId() + SessionMessages.KEY_SUFFIX_DELETE_SUCCESS_DATA) %>">
	<liferay-util:buffer
		var="alertMessage"
	>

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

					<liferay-util:buffer
						var="entityLink"
					>
						<em class="restore-entry-title"><aui:a cssClass="alert-link" href="<%= restoreEntryLinks.get(i) %>" label="<%= HtmlUtil.escape(restoreEntryMessages.get(i)) %>" /></em>
					</liferay-util:buffer>

					<liferay-util:buffer
						var="link"
					>
						<em class="restore-entry-title"><aui:a cssClass="alert-link" href="<%= restoreLinks.get(i) %>" label="<%= HtmlUtil.escape(restoreMessages.get(i)) %>" /></em>
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
	</liferay-util:buffer>

	<liferay-ui:success key="<%= portletDisplay.getId() + SessionMessages.KEY_SUFFIX_DELETE_SUCCESS_DATA %>" message="<%= alertMessage %>" />
</c:if>

<portlet:actionURL name="moveEntry" var="selectContainerURL" />

<aui:form action="<%= selectContainerURL.toString() %>" method="post" name="selectContainerForm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="className" type="hidden" value="" />
	<aui:input name="classPK" type="hidden" value="" />
	<aui:input name="containerModelId" type="hidden" value="" />
</aui:form>

<aui:script>
	function <portlet:namespace />restoreDialog(uri) {
		Liferay.Util.openSelectionModal({
			onSelect: (event) => {
				const selectContainerForm = document.getElementById(
					'<portlet:namespace />selectContainerForm'
				);

				if (selectContainerForm) {
					const className = selectContainerForm.querySelector(
						'#<portlet:namespace />className'
					);

					if (className) {
						className.setAttribute('value', event.classname);
					}

					const classPK = selectContainerForm.querySelector(
						'#<portlet:namespace />classPK'
					);

					if (classPK) {
						classPK.setAttribute('value', event.classpk);
					}

					const containerModelId = selectContainerForm.querySelector(
						'#<portlet:namespace />containerModelId'
					);

					if (containerModelId) {
						containerModelId.setAttribute(
							'value',
							event.containermodelid
						);
					}

					const redirect = selectContainerForm.querySelector(
						'#<portlet:namespace />redirect'
					);

					if (redirect) {
						redirect.setAttribute('value', event.redirect);
					}

					submitForm(selectContainerForm);
				}
			},
			selectEventName: '<portlet:namespace />selectContainer',
			title: '<liferay-ui:message key="warning" />',
			url: uri,
		});
	}
</aui:script>