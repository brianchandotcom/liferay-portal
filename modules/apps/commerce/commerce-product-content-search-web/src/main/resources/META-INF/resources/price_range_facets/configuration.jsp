<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

CPPriceRangeFacetsDisplayContext cpPriceRangeFacetsDisplayContext = (CPPriceRangeFacetsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<%
	request.setAttribute("configuration.jsp-configurationRenderURL", configurationRenderURL);
	request.setAttribute("configuration.jsp-redirect", redirect);
	%>

	<liferay-frontend:edit-form-body>
		<aui:fieldset markupView="lexicon">
			<aui:input checked="<%= cpPriceRangeFacetsDisplayContext.showInputRange() %>" inlineLabel="right" label="show-input-range" labelCssClass="simple-toggle-switch" name="preferences--showInputRange--" type="toggle-switch" />

			<aui:input helpMessage="ranges-json-array-help" label="ranges-json-array" name="preferences--rangesJSONArrayString--" type="textarea" value="<%= cpPriceRangeFacetsDisplayContext.getRangesJSONArrayString() %>" />
		</aui:fieldset>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>