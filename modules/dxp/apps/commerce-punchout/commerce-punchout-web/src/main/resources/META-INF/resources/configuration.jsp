<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommercePunchOutDisplayContext commercePunchOutDisplayContext = (CommercePunchOutDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<portlet:actionURL name="/commerce_channels/edit_commerce_punch_out_configuration" var="editCommercePunchOutConfigurationActionURL" />

<aui:form action="<%= editCommercePunchOutConfigurationActionURL %>" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="commerceChannelId" type="hidden" value="<%= commercePunchOutDisplayContext.getCommerceChannelId() %>" />

	<div class="row">
		<div class="col-12">
			<commerce-ui:panel
				bodyClasses="flex-fill"
				title='<%= LanguageUtil.get(request, "details") %>'
			>
				<aui:input checked="<%= commercePunchOutDisplayContext.enabled() %>" label="enabled" labelOff="disabled" labelOn="enabled" name="settings--enabled--" type="toggle-switch" />

				<aui:input label="punch-out-start-url" name="settings--punchOutStartURL--" value="<%= commercePunchOutDisplayContext.getPunchOutStartURL() %>" />
			</commerce-ui:panel>
		</div>
	</div>
</aui:form>