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

long wsrpConsumerId = ParamUtil.getLong(request, "wsrpConsumerId");

WSRPConsumer wsrpConsumer = WSRPConsumerLocalServiceUtil.getWSRPConsumer(wsrpConsumerId);

UnicodeProperties registrationProperties = wsrpConsumer.getRegistrationProperties();

WSRPConsumerManager wsrpConsumerManager = WSRPConsumerManagerFactoryUtil.getWSRPConsumerManager(wsrpConsumer);

ServiceDescription serviceDescription = wsrpConsumerManager.getServiceDescription();

boolean supportsInbandRegistration = false;

if (serviceDescription.isRequiresRegistration() && (wsrpConsumerManager.getRegistrationService() != null)) {
	supportsInbandRegistration = true;
}

PropertyDescription[] propertyDescriptions = new PropertyDescription[0];

if (supportsInbandRegistration) {
	propertyDescriptions = wsrpConsumerManager.getPropertyDescriptions();
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(((wsrpConsumer == null) ? LanguageUtil.get(request, "new-consumer-registration") : wsrpConsumer.getName()));
%>

<portlet:actionURL name="updateWSRPConsumerRegistration" var="updateWSRPConsumerRegistrationURL" />

<aui:form action="<%= updateWSRPConsumerRegistrationURL %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveConsumerRegistration();" %>'>
	<aui:input name="mvcPath" type="hidden" value="/admin/edit_consumer_registration.jsp" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="wsrpConsumerId" type="hidden" value="<%= wsrpConsumerId %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<aui:field-wrapper label="name">
				<%= wsrpConsumer.getName() %>
			</aui:field-wrapper>

			<aui:field-wrapper label="url">
				<aui:a href="<%= wsrpConsumer.getUrl() %>" target="_blank"><%= wsrpConsumer.getUrl() %></aui:a>
			</aui:field-wrapper>

			<aui:select label="registration-type" name="inbandRegistration">
				<c:if test="<%= supportsInbandRegistration %>">
					<aui:option label="inband" value="true" />
				</c:if>

				<aui:option label="outband" value="false" />
			</aui:select>

			<div <%= supportsInbandRegistration ? "class=\"hide\"" : "" %> id="<portlet:namespace />registrationHandleSettings">
				<aui:input name="registrationHandle" />
			</div>

			<div <%= !supportsInbandRegistration ? "class=\"hide\"" : "" %> id="<portlet:namespace />registrationPropertiesSettings">
				<aui:field-wrapper label="registration-properties">

					<%
					SearchContainer searchContainer = new SearchContainer();

					List<String> headerNames = new ArrayList<String>();

					headerNames.add("name");
					headerNames.add("value");
					headerNames.add("description");

					searchContainer.setHeaderNames(headerNames);
					searchContainer.setEmptyResultsMessage("there-are-no-registration-properties");

					List resultRows = searchContainer.getResultRows();

					for (int i = 0; i < propertyDescriptions.length; i++) {
						PropertyDescription propertyDescription = propertyDescriptions[i];

						String fullyQualifiedName = propertyDescription.getName().toString();

						String name = propertyDescription.getName().getLocalPart();

						String description = null;

						LocalizedString localizedString = propertyDescription.getDescription();

						if (localizedString == null) {
							description = StringPool.BLANK;
						}
						else {
							description = localizedString.getValue();
						}

						localizedString = propertyDescription.getHint();

						description += localizedString.getValue();

						ResultRow row = new ResultRow(name, name, i);

						// Name

						row.addText(name);

						// Value

						StringBuilder sb = new StringBuilder();

						sb.append("<input name=\"");
						sb.append(renderResponse.getNamespace());
						sb.append("registrationPropertyName");
						sb.append(i);
						sb.append("\" type=\"hidden\" value=\"");
						sb.append(fullyQualifiedName);
						sb.append("\" />");

						String registrationPropertyValue = GetterUtil.getString(registrationProperties.get(fullyQualifiedName));

						sb.append("<input name=\"");
						sb.append(renderResponse.getNamespace());
						sb.append("registrationPropertyValue");
						sb.append(i);
						sb.append("\" type=\"text\" value=\"");
						sb.append(registrationPropertyValue);
						sb.append("\" />");

						row.addText(sb.toString());

						// Description

						row.addText(description);

						// Add result row

						resultRows.add(row);
					}
					%>

					<liferay-ui:search-iterator
						paginate="<%= false %>"
						searchContainer="<%= searchContainer %>"
					/>
				</aui:field-wrapper>
			</div>
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveConsumerRegistration() {
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>

<aui:script use="aui-base">
	A.one('#<portlet:namespace />inbandRegistration').on(
		'change',
		function(event) {
			A.one('#<portlet:namespace />registrationHandleSettings').toggle();
			A.one('#<portlet:namespace />registrationPropertiesSettings').toggle();
		}
	);
</aui:script>

<%
if (wsrpConsumer != null) {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "edit"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "add-consumer-registration"), currentURL);
}
%>