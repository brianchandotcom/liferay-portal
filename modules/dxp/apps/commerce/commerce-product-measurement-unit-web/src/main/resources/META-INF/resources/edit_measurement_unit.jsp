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

ServletContext commerceAdminServletContext = (ServletContext)request.getAttribute("commerceAdminServletContext");

CPMeasurementUnitsDisplayContext cpMeasurementUnitsDisplayContext = (CPMeasurementUnitsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPMeasurementUnit cpMeasurementUnit = cpMeasurementUnitsDisplayContext.getCPMeasurementUnit();
CPMeasurementUnit primaryCPMeasurementUnit = cpMeasurementUnitsDisplayContext.getPrimaryCPMeasurementUnit();

int type = cpMeasurementUnitsDisplayContext.getType();

boolean defaultPrimary = false;

if (primaryCPMeasurementUnit == null) {
	defaultPrimary = true;
}

boolean primary = BeanParamUtil.getBoolean(cpMeasurementUnit, request, "primary", defaultPrimary);

String title = LanguageUtil.get(request, "add-measurement-unit");

if (cpMeasurementUnit != null) {
	title = LanguageUtil.format(request, "edit-x", cpMeasurementUnit.getName(locale), false);
}

Map<String, Object> data = new HashMap<>();

data.put("direction-right", StringPool.TRUE);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, commerceAdminModuleKey), redirect, data);
PortalUtil.addPortletBreadcrumbEntry(request, title, StringPool.BLANK, data);

renderResponse.setTitle(LanguageUtil.get(request, "settings"));
%>

<liferay-util:include page="/navbar.jsp" servletContext="<%= commerceAdminServletContext %>">
	<liferay-util:param name="commerceAdminModuleKey" value="<%= commerceAdminModuleKey %>" />
</liferay-util:include>

<%@ include file="/breadcrumb.jspf" %>

<portlet:actionURL name="editCPMeasurementUnit" var="editCPMeasurementUnitActionURL" />

<aui:form action="<%= editCPMeasurementUnitActionURL %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveCPMeasurementUnit();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpMeasurementUnit == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="cpMeasurementUnitId" type="hidden" value="<%= (cpMeasurementUnit == null) ? 0 : cpMeasurementUnit.getCPMeasurementUnitId() %>" />
	<aui:input name="type" type="hidden" value="<%= type %>" />

	<div class="lfr-form-content">
		<liferay-ui:error exception="<%= CPMeasurementUnitKeyException.class %>" message="please-enter-a-valid-key" />

		<aui:model-context bean="<%= cpMeasurementUnit %>" model="<%= CPMeasurementUnit.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:input name="name" />

				<aui:input name="key" />

				<aui:input name="primary" value="<%= primary %>" />

				<%
				String taglibLabel = "ratio-to-primary";

				if (primaryCPMeasurementUnit != null) {
					taglibLabel = LanguageUtil.format(request, "ratio-to-x", primaryCPMeasurementUnit.getName(locale), false);
				}
				%>

				<div class="<%= primary ? "hide" : StringPool.BLANK %>" id="<portlet:namespace />rateOptions">
					<aui:input label="<%= taglibLabel %>" name="rate" />
				</div>

				<aui:input name="priority" />
			</aui:fieldset>
		</aui:fieldset-group>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<c:if test="<%= cpMeasurementUnit == null %>">
	<aui:script sandbox="<%= true %>">
		var form = $(document.<portlet:namespace />fm);

		var keyInput = form.fm('key');
		var nameInput = form.fm('name');

		var onNameInput = _.debounce(
			function(event) {
				keyInput.val(nameInput.val());
			},
			200
		);

		nameInput.on('input', onNameInput);
	</aui:script>
</c:if>

<aui:script>
	function <portlet:namespace />saveCPMeasurementUnit() {
		submitForm(document.<portlet:namespace />fm);
	}

	Liferay.Util.toggleBoxes('<portlet:namespace />primary', '<portlet:namespace />rateOptions', true);
</aui:script>