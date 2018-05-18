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
CommerceShippingSettingsDisplayContext commerceShippingSettingsDisplayContext = (CommerceShippingSettingsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceShippingGroupServiceConfiguration commerceShippingGroupServiceConfiguration = commerceShippingSettingsDisplayContext.getCommerceShippingGroupServiceConfiguration();
Map<String, CommerceShippingOriginLocator> commerceShippingOriginLocators = commerceShippingSettingsDisplayContext.getCommerceShippingOriginLocators();
%>

<liferay-ui:error exception="<%= CommerceWarehouseActiveException.class %>" message="please-geolocate-warehouse-to-set-as-active" />
<liferay-ui:error exception="<%= CommerceWarehouseNameException.class %>" message="please-enter-a-valid-name" />

<aui:fieldset>

	<%
	for (Map.Entry<String, CommerceShippingOriginLocator> entry : commerceShippingOriginLocators.entrySet()) {
		String key = entry.getKey();
		CommerceShippingOriginLocator commerceShippingOriginLocator = entry.getValue();

		boolean checked = false;

		if (key.equals(commerceShippingGroupServiceConfiguration.commerceShippingOriginLocatorKey())) {
			checked = true;
		}
	%>

		<aui:input checked="<%= checked %>" helpMessage="<%= commerceShippingOriginLocator.getDescription(locale) %>" id='<%= key + "Origin" %>' label="<%= commerceShippingOriginLocator.getName(locale) %>" name="commerceShippingOriginLocatorKey" type="radio" value="<%= key %>" />

		<div class="<%= checked ? StringPool.BLANK : "hide" %>" id="<portlet:namespace /><%= key %>OriginOptions">

			<%
			commerceShippingOriginLocator.renderConfiguration(renderRequest, renderResponse);
			%>

		</div>

	<%
	}
	%>

</aui:fieldset>

<aui:script>

	<%
	for (String key : commerceShippingOriginLocators.keySet()) {
	%>

		Liferay.Util.toggleRadio('<portlet:namespace /><%= key %>Origin', '<portlet:namespace /><%= key %>OriginOptions', <%= commerceShippingSettingsDisplayContext.getCommerceShippingOriginLocatorHideBoxIds(key) %>);

	<%
	}
	%>

</aui:script>