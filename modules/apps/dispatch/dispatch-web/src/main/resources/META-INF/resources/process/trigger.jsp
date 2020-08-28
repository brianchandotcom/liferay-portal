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
DispatchTrigger dispatchTrigger = (DispatchTrigger)request.getAttribute(DispatchWebKeys.DISPATCH_TRIGGER);

boolean neverEnd = ParamUtil.getBoolean(request, "neverEnd", true);

if ((dispatchTrigger != null) && (dispatchTrigger.getEndDate() != null)) {
	neverEnd = false;
}
%>

<portlet:actionURL name="editDispatchTrigger" var="editDispatchTriggerActionURL" />

<aui:form action="<%= editDispatchTriggerActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="schedule" />
	<aui:input name="dispatchTriggerId" type="hidden" value="<%= String.valueOf(dispatchTrigger.getDispatchTriggerId()) %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<aui:model-context bean="<%= dispatchTrigger %>" model="<%= DispatchTrigger.class %>" />

			<div class="lfr-form-content">
				<aui:fieldset>
					<aui:input name="active" />

					<aui:input name="cronExpression" />

					<aui:input formName="fm" name="startDate" />

					<aui:input dateTogglerCheckboxLabel="never-end" disabled="<%= neverEnd %>" formName="fm" name="endDate" />
				</aui:fieldset>

				<aui:button-row>
					<aui:button cssClass="btn-lg" type="submit" value="save" />

					<aui:button cssClass="btn-lg" href="<%= backURL %>" type="cancel" />
				</aui:button-row>
			</div>
		</aui:fieldset>
	</aui:fieldset-group>
</aui:form>