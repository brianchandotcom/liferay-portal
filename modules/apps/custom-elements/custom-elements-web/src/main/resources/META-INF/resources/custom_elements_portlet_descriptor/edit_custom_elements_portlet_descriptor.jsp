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

<%@ include file="/custom_elements_portlet_descriptor/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

CustomElementsPortletDescriptor customElementsPortletDescriptor = (CustomElementsPortletDescriptor)request.getAttribute(CustomElementsWebKeys.CUSTOM_ELEMENTS_PORTLET_DESCRIPTOR);

long customElementsPortletDescriptorId = BeanParamUtil.getLong(customElementsPortletDescriptor, request, "customElementsPortletDescriptorId");
String htmlElementName = BeanParamUtil.getString(customElementsPortletDescriptor, request, "htmlElementName");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((customElementsPortletDescriptor == null) ? LanguageUtil.get(request, "add-custom-elements-portlet-descriptor") : customElementsPortletDescriptor.getName());
%>

<portlet:actionURL name="/custom_elements_portlet_descriptor/edit_custom_elements_portlet_descriptor" var="editCustomElementsPortletDescriptorURL" />

<clay:container-fluid>
	<aui:form action="<%= editCustomElementsPortletDescriptorURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveCustomElementsPortletDescriptor();" %>'>
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="customElementsPortletDescriptorId" type="hidden" value="<%= customElementsPortletDescriptorId %>" />

		<aui:model-context bean="<%= customElementsPortletDescriptor %>" model="<%= CustomElementsPortletDescriptor.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:input name="name" />

				<aui:select name="HTMLElementName" showEmptyOption="<%= false %>">

					<%
					for (CustomElementsSource customElementsSource : customElementsPortletDescriptorDisplayContext.getCustomElementSources()) {
					%>

						<aui:option label="<%= HtmlUtil.escape(customElementsSource.getName()) %>" selected="<%= htmlElementName.equals(customElementsSource.getHTMLElementName()) %>" value="<%= customElementsSource.getHTMLElementName() %>" />

					<%
					}
					%>

				</aui:select>

				<aui:input name="instanceable" />

				<aui:input helpMessage="properties-help" name="properties" type="textarea" />

				<aui:input helpMessage="css-urls-help" label="css-urls" name="CSSURLs" type="textarea" />
			</aui:fieldset>
		</aui:fieldset-group>

		<aui:button-row>
			<aui:button type="submit" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:form>
</clay:container-fluid>

<aui:script>
	function <portlet:namespace />saveCustomElementsPortletDescriptor() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value =
			'<%= (customElementsPortletDescriptor == null) ? Constants.ADD : Constants.UPDATE %>';

		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>