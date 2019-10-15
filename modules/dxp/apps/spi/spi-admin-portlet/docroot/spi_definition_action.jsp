<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

SPIDefinition spiDefinition = null;

if (row != null) {
	spiDefinition = (SPIDefinition)row.getObject();
}
else {
	long spiDefinitionId = ParamUtil.getLong(request, "spiDefinitionId");

	spiDefinition = SPIDefinitionServiceUtil.getSPIDefinition(spiDefinitionId);
}
%>

<liferay-ui:icon-menu
	icon="<%= StringPool.BLANK %>"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= SPIDefinitionPermissionUtil.contains(permissionChecker, spiDefinition, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcPath" value="/edit_spi_definition.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="backURL" value="<%= currentURL %>" />
			<portlet:param name="spiDefinitionId" value="<%= String.valueOf(spiDefinition.getSpiDefinitionId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			iconCssClass="icon-edit"
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= SPIDefinitionPermissionUtil.contains(permissionChecker, spiDefinition, ActionKeys.MANAGE) %>">
		<c:choose>
			<c:when test="<%= spiDefinition.getStatus() == SPIAdminConstants.STATUS_STARTED %>">
				<portlet:actionURL name="restartSPI" var="restartURL">
					<portlet:param name="mvcPath" value="/view.jsp" />
					<portlet:param name="spiDefinitionId" value="<%= String.valueOf(spiDefinition.getSpiDefinitionId()) %>" />
				</portlet:actionURL>

				<liferay-ui:icon
					iconCssClass="icon-repeat"
					message="restart"
					url="<%= restartURL %>"
				/>

				<portlet:actionURL name="stopSPI" var="stopURL">
					<portlet:param name="mvcPath" value="/view.jsp" />
					<portlet:param name="spiDefinitionId" value="<%= String.valueOf(spiDefinition.getSpiDefinitionId()) %>" />
				</portlet:actionURL>

				<liferay-ui:icon
					iconCssClass="icon-stop"
					message="stop"
					url="<%= stopURL %>"
				/>
			</c:when>
			<c:when test="<%= spiDefinition.getStatus() == SPIAdminConstants.STATUS_STOPPED %>">
				<portlet:actionURL name="startSPI" var="startURL">
					<portlet:param name="mvcPath" value="/view.jsp" />
					<portlet:param name="spiDefinitionId" value="<%= String.valueOf(spiDefinition.getSpiDefinitionId()) %>" />
				</portlet:actionURL>

				<liferay-ui:icon
					iconCssClass="icon-play"
					message="start"
					url="<%= startURL %>"
				/>
			</c:when>
		</c:choose>
	</c:if>

	<c:if test="<%= SPIDefinitionPermissionUtil.contains(permissionChecker, spiDefinition, ActionKeys.DELETE) && !spiDefinition.isAlive() %>">
		<c:choose>
			<c:when test="<%= spiDefinition.getStatus() == SPIAdminConstants.STATUS_STOPPED %>">
				<portlet:actionURL name="deleteSPIDefinition" var="deleteURL">
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="spiDefinitionId" value="<%= String.valueOf(spiDefinition.getSpiDefinitionId()) %>" />
				</portlet:actionURL>

				<liferay-ui:icon-delete
					url="<%= deleteURL %>"
				/>
			</c:when>
			<c:otherwise>

				<%
				String taglibURL = "javascript:alert('" + UnicodeLanguageUtil.get(request, "you-cannot-delete-a-running-spi.-please-stop-the-spi-before-deleting-it") + "');";
				%>

				<liferay-ui:icon
					cssClass="item-remove"
					iconCssClass="icon-remove"
					url="<%= taglibURL %>"
				/>
			</c:otherwise>
		</c:choose>
	</c:if>
</liferay-ui:icon-menu>