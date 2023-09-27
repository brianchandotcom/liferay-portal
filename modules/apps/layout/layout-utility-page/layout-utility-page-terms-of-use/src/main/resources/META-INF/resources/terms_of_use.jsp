<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-layout:render-layout-utility-page-entry
	type="<%= LayoutUtilityPageEntryConstants.TYPE_TERMS_OF_USE %>"
>

	<%
	String referer = ParamUtil.getString(request, WebKeys.REFERER, PortalUtil.getCurrentURL(request));

	if (referer.equals(themeDisplay.getPathMain() + "/portal/update_terms_of_use")) {
		referer = themeDisplay.getPathMain() + "?doAsUserId=" + themeDisplay.getDoAsUserId();
	}

	TermsOfUseContentProvider termsOfUseContentProvider = (TermsOfUseContentProvider)request.getAttribute(TermsOfUseContentProvider.class.getName());
	%>

	<div class="mt-4 sheet sheet-lg">
		<div class="sheet-header">
			<div class="autofit-padded-no-gutters-x autofit-row">
				<div class="autofit-col autofit-col-expand">
					<h2 class="sheet-title">
						<liferay-ui:message key="terms-of-use" />
					</h2>
				</div>

				<div class="autofit-col">
					<div class="float-right">

						<%
						String updateLanguageFormAction = HttpComponentsUtil.addParameter(themeDisplay.getPathMain() + "/portal/update_language", "p_l_id", themeDisplay.getPlid());

						String updateLanguageRedirect = HttpComponentsUtil.addParameter(PortalUtil.getCurrentURL(request), "ticketKey", ParamUtil.getString(request, "ticketKey"));

						updateLanguageFormAction = HttpComponentsUtil.addParameter(updateLanguageFormAction, "redirect", updateLanguageRedirect);
						%>

						<liferay-ui:language
							formAction="<%= updateLanguageFormAction %>"
							languageId="<%= themeDisplay.getLanguageId() %>"
							languageIds="<%= LocaleUtil.toLanguageIds(LanguageUtil.getAvailableLocales(themeDisplay.getCompanyGroupId())) %>"
						/>
					</div>
				</div>
			</div>
		</div>

		<aui:form action='<%= themeDisplay.getPathMain() + "/portal/update_terms_of_use" %>' name="fm">
			<aui:input name="p_auth" type="hidden" value="<%= AuthTokenUtil.getToken(request) %>" />
			<aui:input name="doAsUserId" type="hidden" value="<%= themeDisplay.getDoAsUserId() %>" />
			<aui:input name="<%= WebKeys.REFERER %>" type="hidden" value="<%= referer %>" />

			<div class="sheet-text">
				<c:choose>
					<c:when test="<%= termsOfUseContentProvider != null %>">

						<%
						termsOfUseContentProvider.includeView(request, PipingServletResponseFactory.createPipingServletResponse(pageContext));
						%>

					</c:when>
					<c:otherwise>
						<p>
							<span>
								<liferay-ui:message key="placeholder-terms-of-use" />
							</span>
						</p>

						<p>
							<span>
								<liferay-ui:message key="you-must-configure-terms-of-use" />
							</span>
						</p>
					</c:otherwise>
				</c:choose>
			</div>

			<c:if test="<%= !user.isAgreedToTermsOfUse() %>">
				<div class="sheet-footer">
					<clay:button
						label="i-agree"
						type="submit"
					/>

					<clay:button
						displayType="secondary"
						label="i-disagree"
						propsTransformer="js/DisagreeButtonPropsTransformer"
						type="button"
					/>
				</div>
			</c:if>
		</aui:form>
	</div>
</liferay-layout:render-layout-utility-page-entry>