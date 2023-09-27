<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceRegionsDisplayContext commerceRegionsDisplayContext = (CommerceRegionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

long countryId = commerceRegionsDisplayContext.getCountryId();

Region region = commerceRegionsDisplayContext.getRegion();

long regionId = commerceRegionsDisplayContext.getRegionId();

portletDisplay.setShowBackIcon(true);

if (Validator.isNull(redirect)) {
	portletDisplay.setURLBack(String.valueOf(renderResponse.createRenderURL()));
}
else {
	portletDisplay.setURLBack(redirect);
}
%>

<portlet:actionURL name="/commerce_country/edit_commerce_region" var="editCommerceRegionActionURL" />

<aui:form action="<%= editCommerceRegionActionURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveCommerceRegion();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (region == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="countryId" type="hidden" value="<%= String.valueOf(countryId) %>" />
	<aui:input name="regionId" type="hidden" value="<%= String.valueOf(regionId) %>" />

	<div class="lfr-form-content">
		<liferay-ui:error exception="<%= RegionNameException.class %>" message="please-enter-a-valid-name" />

		<aui:model-context bean="<%= region %>" model="<%= Region.class %>" />

		<div class="sheet">
			<div class="panel-group panel-group-flush">
				<aui:fieldset>
					<aui:input name="name" />

					<aui:input label="code" name="regionCode" />

					<aui:input label="position" name="position" />

					<aui:input checked="<%= (region == null) ? false : region.isActive() %>" inlineLabel="right" labelCssClass="simple-toggle-switch" name="active" type="toggle-switch" />
				</aui:fieldset>
			</div>
		</div>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= backURL %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveCommerceRegion() {
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>