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

CPMeasurementUnitsDisplayContext cpMeasurementUnitsDisplayContext = (CPMeasurementUnitsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPMeasurementUnit cpMeasurementUnit = cpMeasurementUnitsDisplayContext.getCPMeasurementUnit();
CPMeasurementUnit primaryCPMeasurementUnit = cpMeasurementUnitsDisplayContext.getPrimaryCPMeasurementUnit();

int type = cpMeasurementUnitsDisplayContext.getType();

boolean defaultPrimary = false;

if (primaryCPMeasurementUnit == null) {
	defaultPrimary = true;
}

boolean primary = BeanParamUtil.getBoolean(cpMeasurementUnit, request, "primary", defaultPrimary);

portletDisplay.setShowBackIcon(true);

if (Validator.isNull(redirect)) {
	portletDisplay.setURLBack(String.valueOf(renderResponse.createRenderURL()));
}
else {
	portletDisplay.setURLBack(redirect);
}
%>

<portlet:actionURL name="/cp_measurement_unit/edit_cp_measurement_unit" var="editCPMeasurementUnitActionURL" />

<aui:form action="<%= editCPMeasurementUnitActionURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveCPMeasurementUnit();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpMeasurementUnit == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="cpMeasurementUnitId" type="hidden" value="<%= (cpMeasurementUnit == null) ? 0 : cpMeasurementUnit.getCPMeasurementUnitId() %>" />
	<aui:input name="type" type="hidden" value="<%= type %>" />

	<div class="lfr-form-content">
		<liferay-ui:error exception="<%= DuplicateCPMeasurementUnitKeyException.class %>" message="please-enter-a-valid-key" />

		<aui:model-context bean="<%= cpMeasurementUnit %>" model="<%= CPMeasurementUnit.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:input name="name" />

				<aui:input name="key" />

				<aui:input inlineLabel="right" labelCssClass="simple-toggle-switch" name="primary" type="toggle-switch" value="<%= primary %>" />

				<%
				String taglibLabel = "ratio-to-primary";

				if (primaryCPMeasurementUnit != null) {
					taglibLabel = LanguageUtil.format(request, "ratio-to-x", HtmlUtil.escape(primaryCPMeasurementUnit.getName(locale)), false);
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
	<aui:script require="frontend-js-web/index as frontendJsWeb, commerce-frontend-js/utilities/slugify as slugify">
		var {debounce} = frontendJsWeb;

		var form = document.getElementById('<portlet:namespace />fm');

		var keyInput = form.querySelector('#<portlet:namespace />key');
		var nameInput = form.querySelector('#<portlet:namespace />name');

		var handleOnNameInput = function () {
			keyInput.value = slugify.default(nameInput.value);
		};

		nameInput.addEventListener('input', debounce(handleOnNameInput, 200));
	</aui:script>
</c:if>

<aui:script>
	function <portlet:namespace />saveCPMeasurementUnit() {
		submitForm(document.<portlet:namespace />fm);
	}

	Liferay.Util.toggleBoxes(
		'<portlet:namespace />primary',
		'<portlet:namespace />rateOptions',
		true
	);
</aui:script>