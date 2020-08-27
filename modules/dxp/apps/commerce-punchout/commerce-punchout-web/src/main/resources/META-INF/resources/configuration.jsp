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
CommercePunchOutDisplayContext commercePunchOutDisplayContext = (CommercePunchOutDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

long commerceChannelId = commercePunchOutDisplayContext.getCommerceChannelId();
%>

<portlet:actionURL name="editCommercePunchOutConfiguration" var="editCommercePunchOutConfigurationActionURL" />

<aui:form action="<%= editCommercePunchOutConfigurationActionURL %>" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="commerceChannelId" type="hidden" value="<%= commerceChannelId %>" />

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