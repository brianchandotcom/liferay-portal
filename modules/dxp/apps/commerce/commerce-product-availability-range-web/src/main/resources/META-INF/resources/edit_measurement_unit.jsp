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

CPAvailabilityRangeDisplayContext cpAvailabilityRangeDisplayContext = (CPAvailabilityRangeDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPAvailabilityRange cpAvailabilityRange = cpAvailabilityRangeDisplayContext.getCPAvailabilityRange();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((cpAvailabilityRange == null) ? LanguageUtil.get(request, "add-availability-range") : LanguageUtil.format(request, "edit-x", cpAvailabilityRange.getTitle(languageId), false));
%>

<portlet:actionURL name="editCPAvailabilityRange" var="editCPAvailabilityRangeActionURL" />

<aui:form action="<%= editCPAvailabilityRangeActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpAvailabilityRange == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="cpAvailabilityRangeId" type="hidden" value="<%= (cpAvailabilityRange == null) ? 0 : cpAvailabilityRange.getCPAvailabilityRangeId() %>" />

	<div class="lfr-form-content">
		<aui:model-context bean="<%= cpAvailabilityRange %>" model="<%= CPAvailabilityRange.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:input name="title" />
			</aui:fieldset>
		</aui:fieldset-group>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>