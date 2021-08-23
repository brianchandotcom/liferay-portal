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

<%@ include file="/custom_elements_source/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

CustomElementsSource customElementsSource = (CustomElementsSource)request.getAttribute(CustomElementsWebKeys.CUSTOM_ELEMENTS_SOURCE);

long customElementsSourceId = BeanParamUtil.getLong(customElementsSource, request, "customElementsSourceId");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((customElementsSource == null) ? LanguageUtil.get(request, "add-custom-elements-source") : customElementsSource.getName());
%>

<portlet:actionURL name="/custom_elements_source/edit_custom_elements_source" var="editCustomElementsSourceURL" />

<clay:container-fluid>
	<aui:form action="<%= editCustomElementsSourceURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveCustomElementsSource();" %>'>
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="customElementsSourceId" type="hidden" value="<%= customElementsSourceId %>" />

		<aui:model-context bean="<%= customElementsSource %>" model="<%= CustomElementsSource.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:input name="name" />

				<aui:input fieldParam="htmlElementName" name="HTMLElementName" />

				<aui:input fieldParam="urls" helpMessage="urls-help" label="urls" name="URLs" type="textarea" />
			</aui:fieldset>
		</aui:fieldset-group>

		<aui:button-row>
			<aui:button type="submit" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:form>
</clay:container-fluid>

<aui:script>
	function <portlet:namespace />saveCustomElementsSource() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value =
			'<%= (customElementsSource == null) ? Constants.ADD : Constants.UPDATE %>';

		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>