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
String redirect = msbFragmentDisplayContext.getEditMSBFragmentCollectionRedirect();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(msbFragmentDisplayContext.getMSBFragmentCollectionTitle());
%>

<portlet:actionURL name="editMSBFragmentCollection" var="editMSBFragmentCollectionURL">
	<portlet:param name="mvcPath" value="/edit_msb_fragment_collection.jsp" />
</portlet:actionURL>

<aui:form action="<%= editMSBFragmentCollectionURL %>" cssClass="container-fluid-1280" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="msbFragmentCollectionId" type="hidden" value="<%= msbFragmentDisplayContext.getMSBFragmentCollectionId() %>" />

	<liferay-ui:error exception="<%= DuplicateMSBFragmentCollectionException.class %>" message="please-enter-a-unique-name" />
	<liferay-ui:error exception="<%= MSBFragmentCollectionNameException.class %>" message="please-enter-a-valid-name" />

	<aui:model-context bean="<%= msbFragmentDisplayContext.getMSBFragmentCollection() %>" model="<%= MSBFragmentCollection.class %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<aui:input autoFocus="<%= true %>" label="name" name="name" placeholder="name" />

			<aui:input name="description" placeholder="description" />
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>