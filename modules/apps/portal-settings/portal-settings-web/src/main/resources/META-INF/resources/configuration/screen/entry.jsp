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

PortletURL portletURL = renderResponse.createRenderURL();

if (Validator.isNull(redirect)) {
	redirect = portletURL.toString();
}

PortalSettingsConfigurationScreenContributor portalSettingsConfigurationScreenContributor = (PortalSettingsConfigurationScreenContributor)request.getAttribute(PortalSettingsWebKeys.PORTAL_SETTINGS_CONFIGURATION_SCREEN_CONTRIBUTOR);
%>

<portlet:actionURL var="editCompanyURL" />

<div class="sheet sheet-lg">
	<h2 class="autofit-row">
		<span class="autofit-col autofit-col-expand">
			<liferay-ui:message key="<%= portalSettingsConfigurationScreenContributor.getName(locale) %>" />
		</span>

		<c:if test="<%= Validator.isNotNull(portalSettingsConfigurationScreenContributor.getDeleteMVCActionCommandName()) || Validator.isNotNull(portalSettingsConfigurationScreenContributor.getTestButtonOnClick(renderRequest, renderResponse)) %>">
			<span class="autofit-col">
				<liferay-ui:icon-menu
					cssClass="float-right"
					direction="right"
					markupView="lexicon"
					showWhenSingleIcon="<%= true %>"
				>
					<c:if test="<%= Validator.isNotNull(portalSettingsConfigurationScreenContributor.getDeleteMVCActionCommandName()) %>">
						<portlet:actionURL name="<%= portalSettingsConfigurationScreenContributor.getDeleteMVCActionCommandName() %>" var="resetValuesURL">
							<portlet:param name="redirect" value="<%= currentURL %>" />
						</portlet:actionURL>

						<%
						String taglibOnClick = "if (confirm('" + request.getAttribute(PortalSettingsWebKeys.DELETE_CONFIRMATION_TEXT) + "')) {submitForm(document.hrefFm, '" + resetValuesURL.toString() + "');}";
						%>

						<liferay-ui:icon
							message="reset-values"
							method="post"
							onClick="<%= taglibOnClick %>"
							url="javascript:;"
						/>
					</c:if>

					<c:if test="<%= Validator.isNotNull(portalSettingsConfigurationScreenContributor.getTestButtonOnClick(renderRequest, renderResponse)) %>">
						<liferay-ui:icon
							message='<%= GetterUtil.getString(portalSettingsConfigurationScreenContributor.getTestButtonLabel(locale), LanguageUtil.get(request, "test")) %>'
							method="post"
							onClick="<%= portalSettingsConfigurationScreenContributor.getTestButtonOnClick(renderRequest, renderResponse) %>"
							url="javascript:;"
						/>
					</c:if>
				</liferay-ui:icon-menu>
			</span>
		</c:if>
	</h2>

	<aui:form action="<%= editCompanyURL %>" data-senna-off="true" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<c:if test="<%= Validator.isNotNull(portalSettingsConfigurationScreenContributor.getSaveMVCActionCommandName()) %>">
			<aui:input id="<%= PortalUtil.generateRandomKey(request, portalSettingsConfigurationScreenContributor.getKey()) %>" name="<%= ActionRequest.ACTION_NAME %>" type="hidden" value="<%= portalSettingsConfigurationScreenContributor.getSaveMVCActionCommandName() %>" />
		</c:if>

		<liferay-util:include page="<%= portalSettingsConfigurationScreenContributor.getJspPath() %>" servletContext="<%= portalSettingsConfigurationScreenContributor.getServletContext() %>" />

		<div class="sheet-footer">
			<div class="btn-group">
				<div class="btn-group-item">
					<aui:button type="submit" value="save" />
				</div>

				<div class="btn-group-item">
					<aui:button href="<%= redirect %>" name="cancel" type="cancel" />
				</div>
			</div>
		</div>
	</aui:form>
</div>