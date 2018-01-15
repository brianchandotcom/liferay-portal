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
String redirect = fragmentDisplayContext.getEditFragmentCollectionRedirect();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(fragmentDisplayContext.getFragmentCollectionTitle());
%>

<portlet:actionURL name="/fragment/edit_fragment_collection" var="editFragmentCollectionURL">
	<portlet:param name="mvcRenderCommandName" value="/fragment/edit_fragment_collection" />
</portlet:actionURL>

<aui:form action="<%= editFragmentCollectionURL %>" cssClass="container-fluid-1280" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="fragmentCollectionId" type="hidden" value="<%= fragmentDisplayContext.getFragmentCollectionId() %>" />

	<liferay-ui:error exception="<%= DuplicateFragmentCollectionException.class %>" message="please-enter-a-unique-name" />
	<liferay-ui:error exception="<%= FragmentCollectionNameException.class %>" message="please-enter-a-valid-name" />

	<aui:model-context bean="<%= fragmentDisplayContext.getFragmentCollection() %>" model="<%= FragmentCollection.class %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<aui:input autoFocus="<%= true %>" label="name" name="name" placeholder="name" />

			<aui:input name="description" placeholder="description" />
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>