<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/osb_patcher/views/init.jsp" %>

<liferay-ui:header
	backURL='<%= (BrowserSnifferUtil.isChrome(request) && windowState.equals(LiferayWindowState.POP_UP)) ? request.getHeader(HttpHeaders.REFERER) : "javascript:history.go(-1);" %>'
	title="error"
/>

<%
String addPatcherFixWindow = null;
%>

<liferay-ui:error exception="<%= PatcherScanException.class %>">

	<%
	PatcherScanException patcherScanException = (PatcherScanException)errorException;

	Object[] arguments = patcherScanException.getArguments();
	%>

	<c:choose>
		<c:when test='<%= StringUtil.equalsIgnoreCase(patcherScanException.getMessage(), "failed-building-a-patch-for-fixes-x") %>'>
			<br /><br />

			<%
			String tickets = String.valueOf(arguments[2]);
			%>

			<liferay-ui:message arguments="<%= tickets %>" key="failed-building-a-patch-for-fixes-x" />

			<br /><br />

			<liferay-ui:message arguments="<%= tickets %>" key="there-was-no-match-found-in-our-fix-catalog-for-the-following-fixes-x" />

			<portlet:renderURL var="addPatcherFixURL">
				<portlet:param name="mvcRenderCommandName" value="/patcher/add_fixes" />
				<portlet:param name="patcherProductVersionId" value="<%= String.valueOf(arguments[0]) %>" />
				<portlet:param name="patcherProjectVersionId" value="<%= String.valueOf(arguments[1]) %>" />
				<portlet:param name="patcherFixName" value="<%= tickets %>" />
			</portlet:renderURL>

			<%
			addPatcherFixWindow = "window.open('" + HtmlUtil.escapeJS(addPatcherFixURL) + "');";
			%>

			<c:if test="<%= (arguments.length > 3) && (arguments[3] instanceof List) %>">
				<br /><br />

				<strong><liferay-ui:message key="process" />:</strong><br />

				<div class="bg-light border font-monospaced p-3">
					<c:forEach items="<%= (List<Map<String, Object>>)arguments[3] %>" var="step">
						FOUND:<br />
						ID: ${step.patcherFixId}<br />
						NAME: "${step.patcherFixName}"<br />
						REMOVING TICKETS FROM PHRASE..<br /><br />
						NEW PHRASE:<br />
						"${step.remainingTickets}"<br /><br />
					</c:forEach>

					NO MATCH FOUND
				</div>
			</c:if>
		</c:when>
		<c:when test='<%= StringUtil.equalsIgnoreCase(patcherScanException.getMessage(), "picked-up-fix-id-with-excluded-ancestors") %>'>
			<br /><br />

			<liferay-ui:message key="picked-up-fix-id-with-excluded-ancestors" />

			<br /><br />

			<liferay-ui:message arguments="<%= arguments[0] %>" key="fix-id-x" />

			<br /><br />

			<liferay-ui:message arguments="<%= arguments[1] %>" key="excluded-ancestors-x" />

			<c:if test="<%= (arguments.length > 2) && (arguments[2] instanceof List) %>">
				<br /><br />

				<div class="bg-light border font-monospaced p-3">
					<c:forEach items="<%= (List<Map<String, Object>>)arguments[2] %>" var="step">
						FOUND:<br />
						ID: ${step.patcherFixId}<br />
						NAME: "${step.patcherFixName}"<br />
						REMOVING TICKETS FROM PHRASE..<br /><br />
						NEW PHRASE:<br />
						"${step.remainingTickets}"<br /><br />
					</c:forEach>

					PICKED UP FIX ID WITH EXCLUDED ANCESTOR(S)
				</div>
			</c:if>
		</c:when>
		<c:otherwise>
			<br /><liferay-ui:message key="<%= patcherScanException.getMessage() %>" /><br /><br />
		</c:otherwise>
	</c:choose>
</liferay-ui:error>

<c:if test="<%= Validator.isNotNull(addPatcherFixWindow) %>">
	<aui:button-row>
		<clay:button
			displayType="primary"
			label='<%= LanguageUtil.get(request, "create-missing-fixes") %>'
			onClick="<%= addPatcherFixWindow %>"
		/>
	</aui:button-row>
</c:if>

<liferay-ui:error exception="<%= PortalException.class %>">

	<%
	PortalException portalException = (PortalException)errorException;
	%>

	<c:choose>
		<c:when test="<%= Validator.isNotNull(portalException.getMessage()) %>">
			<liferay-ui:message key="<%= portalException.getMessage() %>" />
		</c:when>
		<c:otherwise>
			<liferay-ui:message key="an-unexpected-error-occurred" />
		</c:otherwise>
	</c:choose>
</liferay-ui:error>