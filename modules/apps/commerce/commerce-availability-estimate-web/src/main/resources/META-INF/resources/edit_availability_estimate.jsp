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

<portlet:actionURL name="editCommerceAvailabilityEstimate" var="editCommerceAvailabilityEstimateActionURL" />

<aui:form action="<%= editCommerceAvailabilityEstimateActionURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceAvailabilityEstimate == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="commerceAvailabilityEstimateId" type="hidden" value="<%= (commerceAvailabilityEstimate == null) ? 0 : commerceAvailabilityEstimate.getCommerceAvailabilityEstimateId() %>" />

	<div class="lfr-form-content">
		<aui:model-context bean="<%= commerceAvailabilityEstimate %>" model="<%= CommerceAvailabilityEstimate.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:input name="title" />

				<aui:input name="priority" />
			</aui:fieldset>
		</aui:fieldset-group>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>