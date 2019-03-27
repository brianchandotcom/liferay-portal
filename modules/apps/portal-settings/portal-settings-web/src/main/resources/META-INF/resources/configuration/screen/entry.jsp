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

String deleteConfirmationText = (String)request.getAttribute("deleteConfirmationText");
PortalSettingsConfigurationScreenContributor portalSettingsConfigurationScreenContributor = (PortalSettingsConfigurationScreenContributor)request.getAttribute("portalSettingsConfigurationScreenContributor");

String deleteMVCActionCommandName = portalSettingsConfigurationScreenContributor.getDeleteMVCActionCommandName();
String testButtonOnClick = portalSettingsConfigurationScreenContributor.getTestButtonOnClick(renderRequest, renderResponse);
String saveMVCActionCommandName = portalSettingsConfigurationScreenContributor.getSaveMVCActionCommandName();
%>

<portlet:actionURL var="editCompanyURL" />

<div class="sheet sheet-lg">
	<h2 class="autofit-row">
		<span class="autofit-col autofit-col-expand">
			<liferay-ui:message key="<%= portalSettingsConfigurationScreenContributor.getName(locale) %>" />
		</span>

		<c:if test="<%= Validator.isNotNull(deleteMVCActionCommandName) || Validator.isNotNull(testButtonOnClick) %>">
			<span class="autofit-col">
				<liferay-ui:icon-menu
					cssClass="float-right"
					direction="right"
					markupView="lexicon"
					showWhenSingleIcon="<%= true %>"
				>
					<c:if test="<%= Validator.isNotNull(deleteMVCActionCommandName) %>">
						<portlet:actionURL name="<%= deleteMVCActionCommandName %>" var="resetValuesURL">
							<portlet:param name="redirect" value="<%= currentURL %>" />
						</portlet:actionURL>

						<%
						String taglibOnClick = "if (confirm('" + deleteConfirmationText + "')) {submitForm(document.hrefFm, '" + resetValuesURL.toString() + "');}";
						%>

						<liferay-ui:icon
							message="reset-values"
							method="post"
							onClick="<%= taglibOnClick %>"
							url="javascript:;"
						/>
					</c:if>

					<c:if test="<%= Validator.isNotNull(testButtonOnClick) %>">
						<liferay-ui:icon
							message='<%= GetterUtil.getString(portalSettingsConfigurationScreenContributor.getTestButtonLabel(locale), LanguageUtil.get(request, "test")) %>'
							method="post"
							onClick="<%= testButtonOnClick %>"
							url="javascript:;"
						/>
					</c:if>
				</liferay-ui:icon-menu>
			</span>
		</c:if>
	</h2>

	<aui:form action="<%= editCompanyURL %>" data-senna-off="true" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<c:if test="<%= Validator.isNotNull(saveMVCActionCommandName) %>">
			<aui:input id="<%= PortalUtil.generateRandomKey(request, portalSettingsConfigurationScreenContributor.getKey()) %>" name="<%= ActionRequest.ACTION_NAME %>" type="hidden" value="<%= saveMVCActionCommandName %>" />
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