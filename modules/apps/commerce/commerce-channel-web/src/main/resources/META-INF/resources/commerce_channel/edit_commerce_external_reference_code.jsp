<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceChannelDisplayContext commerceChannelDisplayContext = (CommerceChannelDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<portlet:actionURL name="/commerce_channels/edit_commerce_channel_external_reference_code" var="editCommerceChannelExternalReferenceCodeURL" />

<commerce-ui:modal-content>
	<aui:form action="<%= editCommerceChannelExternalReferenceCodeURL %>" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<aui:model-context bean="<%= commerceChannelDisplayContext.getCommerceChannel() %>" model="<%= CommerceChannel.class %>" />

		<aui:input name="commerceChannelId" type="hidden" />

		<aui:input name="externalReferenceCode" type="text" wrapperCssClass="form-group-item" />
	</aui:form>
</commerce-ui:modal-content>