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
long auditEventId = ParamUtil.getLong(request, "auditEventId");

AuditEvent auditEvent = null;

String eventTypeAction = StringPool.BLANK;

if (auditEventId > 0) {
	auditEvent = AuditEventManagerUtil.fetchAuditEvent(auditEventId);

	if (auditEvent != null) {
		auditEvent = auditEvent.toEscapedModel();

		eventTypeAction = (String)PortalClassInvoker.invoke(new MethodKey(ClassResolverUtil.resolve("com.liferay.portal.kernel.security.permission.ResourceActionsUtil", PortalClassLoaderUtil.getClassLoader()), "getAction", HttpServletRequest.class, String.class), request, auditEvent.getEventType());
	}
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(ParamUtil.getString(request, "redirect", String.valueOf(renderResponse.createRenderURL())));

renderResponse.setTitle((auditEvent == null) ? "audit-event" : auditEvent.getEventType() + " (" + eventTypeAction + ")");
%>

<clay:sheet>
	<c:choose>
		<c:when test="<%= auditEvent == null %>">
			<div class="portlet-msg-error">
				<liferay-ui:message key="the-event-could-not-be-found" />
			</div>
		</c:when>
		<c:otherwise>
			<aui:field-wrapper label="event-id">
				<%= auditEvent.getAuditEventId() %>
			</aui:field-wrapper>

			<aui:field-wrapper label="create-date">
				<%= dateFormatDateTime.format(auditEvent.getCreateDate()) %>
			</aui:field-wrapper>

			<aui:field-wrapper label="resource-id">
				<%= auditEvent.getClassPK() %>
			</aui:field-wrapper>

			<aui:field-wrapper label="resource-name">
				<%= auditEvent.getClassName() %>

				(<%= (String)PortalClassInvoker.invoke(new MethodKey(ClassResolverUtil.resolve("com.liferay.portal.kernel.security.permission.ResourceActionsUtil", PortalClassLoaderUtil.getClassLoader()), "getModelResource", HttpServletRequest.class, String.class), request, auditEvent.getClassName()) %>)
			</aui:field-wrapper>

			<aui:field-wrapper label="resource-action">
				<%= auditEvent.getEventType() %>

				(<%= eventTypeAction %>)
			</aui:field-wrapper>

			<aui:field-wrapper label="user-id">
				<%= auditEvent.getUserId() %>
			</aui:field-wrapper>

			<aui:field-wrapper label="user-name">
				<%= auditEvent.getUserName() %>
			</aui:field-wrapper>

			<aui:field-wrapper label="client-host">
				<%= Validator.isNotNull(auditEvent.getClientHost()) ? auditEvent.getClientHost() : LanguageUtil.get(request, "none") %>
			</aui:field-wrapper>

			<aui:field-wrapper label="client-ip">
				<%= Validator.isNotNull(auditEvent.getClientIP()) ? auditEvent.getClientIP() : LanguageUtil.get(request, "none") %>
			</aui:field-wrapper>

			<aui:field-wrapper label="server-name">
				<%= Validator.isNotNull(auditEvent.getServerName()) ? auditEvent.getServerName() : LanguageUtil.get(request, "none") %>
			</aui:field-wrapper>

			<aui:field-wrapper label="additional-information">
				<%= Validator.isNotNull(auditEvent.getAdditionalInfo()) ? auditEvent.getAdditionalInfo() : LanguageUtil.get(request, "none") %>
			</aui:field-wrapper>
		</c:otherwise>
	</c:choose>
</clay:sheet>