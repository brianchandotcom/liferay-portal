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
%>

<portlet:actionURL name="updateArchivedSetup" var="updateArchivedSetupURL">
	<portlet:param name="mvcPath" value="/edit_configuration_templates.jsp" />
	<portlet:param name="portletConfiguration" value="<%= Boolean.TRUE.toString() %>" />
</portlet:actionURL>

<div class="portlet-configuration-add-template">
	<aui:form action="<%= updateArchivedSetupURL %>" cssClass="form" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="portletResource" type="hidden" value="<%= portletResource %>" />

		<div class="portlet-configuration-body-content">
			<div class="container-fluid-1280">
				<liferay-ui:error exception="<%= PortletItemNameException.class %>" message="please-enter-a-valid-setup-name" />

				<aui:fieldset-group markupView="lexicon">
					<aui:fieldset>

						<%
						String name = StringPool.BLANK;

						boolean useCustomTitle = GetterUtil.getBoolean(portletPreferences.getValue("portletSetupUseCustomTitle", null));

						if (useCustomTitle) {
							name = PortletConfigurationUtil.getPortletTitle(portletPreferences, LocaleUtil.toLanguageId(themeDisplay.getSiteDefaultLocale()));
						}
						%>

						<aui:input name="name" placeholder="name" required="<%= true %>" type="text" value="<%= name %>">
							<aui:validator name="maxLength">75</aui:validator>
						</aui:input>
					</aui:fieldset>
				</aui:fieldset-group>
			</div>
		</div>

		<aui:button-row>
			<aui:button cssClass="btn-lg" type="submit" />

			<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:form>
</div>