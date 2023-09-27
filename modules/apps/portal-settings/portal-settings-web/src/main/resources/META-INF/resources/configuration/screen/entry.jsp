<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

<clay:sheet>
	<clay:content-row
		containerElement="h2"
	>
		<clay:content-col
			containerElement="span"
			expand="<%= true %>"
		>
			<liferay-ui:message key="<%= portalSettingsConfigurationScreenContributor.getName(locale) %>" />
		</clay:content-col>

		<c:if test="<%= Validator.isNotNull(portalSettingsConfigurationScreenContributor.getDeleteMVCActionCommandName()) || Validator.isNotNull(portalSettingsConfigurationScreenContributor.getTestButtonOnClick(renderRequest, renderResponse)) %>">
			<clay:content-col
				containerElement="span"
			>
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

						<aui:script>
							function <portlet:namespace />handleResetValues(event) {
								event.preventDefault();

								Liferay.Util.openConfirmModal({
									message:
										'<%= request.getAttribute(PortalSettingsWebKeys.DELETE_CONFIRMATION_TEXT) %>',
									onConfirm: (isConfirmed) => {
										if (isConfirmed) {
											submitForm(document.hrefFm, '<%= resetValuesURL.toString() %>');
										}
									},
								});
							}
						</aui:script>

						<liferay-ui:icon
							message="reset-values"
							method="post"
							onClick='<%= liferayPortletResponse.getNamespace() + "handleResetValues(event)" %>'
							url="javascript:void(0);"
						/>
					</c:if>

					<c:if test="<%= Validator.isNotNull(portalSettingsConfigurationScreenContributor.getTestButtonOnClick(renderRequest, renderResponse)) %>">
						<liferay-ui:icon
							message='<%= GetterUtil.getString(portalSettingsConfigurationScreenContributor.getTestButtonLabel(locale), LanguageUtil.get(request, "test")) %>'
							method="post"
							onClick="<%= portalSettingsConfigurationScreenContributor.getTestButtonOnClick(renderRequest, renderResponse) %>"
							url="javascript:void(0);"
						/>
					</c:if>
				</liferay-ui:icon-menu>
			</clay:content-col>
		</c:if>
	</clay:content-row>

	<aui:form action="<%= editCompanyURL %>" data-senna-off="true" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<c:if test="<%= Validator.isNotNull(portalSettingsConfigurationScreenContributor.getSaveMVCActionCommandName()) %>">
			<aui:input id="<%= PortalUtil.generateRandomKey(request, portalSettingsConfigurationScreenContributor.getKey()) %>" name="<%= ActionRequest.ACTION_NAME %>" type="hidden" value="<%= portalSettingsConfigurationScreenContributor.getSaveMVCActionCommandName() %>" />
		</c:if>

		<liferay-util:include page="<%= portalSettingsConfigurationScreenContributor.getJspPath() %>" servletContext="<%= portalSettingsConfigurationScreenContributor.getServletContext() %>" />

		<clay:sheet-footer>
			<div class="btn-group">
				<div class="btn-group-item">
					<aui:button type="submit" value="save" />
				</div>

				<div class="btn-group-item">
					<aui:button href="<%= redirect %>" name="cancel" type="cancel" />
				</div>
			</div>
		</clay:sheet-footer>
	</aui:form>
</clay:sheet>