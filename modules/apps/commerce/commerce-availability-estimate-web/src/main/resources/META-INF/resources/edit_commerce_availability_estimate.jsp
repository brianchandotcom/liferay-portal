<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

CommerceAvailabilityEstimateDisplayContext commerceAvailabilityEstimateDisplayContext = (CommerceAvailabilityEstimateDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceAvailabilityEstimate commerceAvailabilityEstimate = commerceAvailabilityEstimateDisplayContext.getCommerceAvailabilityEstimate();

portletDisplay.setShowBackIcon(true);

if (Validator.isNull(redirect)) {
	portletDisplay.setURLBack(String.valueOf(renderResponse.createRenderURL()));
}
else {
	portletDisplay.setURLBack(redirect);
}
%>

<portlet:actionURL name="/commerce_availability_estimate/edit_commerce_availability_estimate" var="editCommerceAvailabilityEstimateActionURL" />

<aui:form action="<%= editCommerceAvailabilityEstimateActionURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceAvailabilityEstimate == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="commerceAvailabilityEstimateId" type="hidden" value="<%= (commerceAvailabilityEstimate == null) ? 0 : commerceAvailabilityEstimate.getCommerceAvailabilityEstimateId() %>" />

	<div class="lfr-form-content">
		<aui:model-context bean="<%= commerceAvailabilityEstimate %>" model="<%= CommerceAvailabilityEstimate.class %>" />

		<div class="sheet">
			<div class="panel-group panel-group-flush">
				<aui:fieldset>
					<aui:input name="title" />

					<aui:input name="priority" />
				</aui:fieldset>
			</div>
		</div>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>