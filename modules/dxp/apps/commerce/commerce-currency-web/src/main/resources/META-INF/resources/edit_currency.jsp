<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

ServletContext commerceAdminServletContext = (ServletContext)request.getAttribute(CommerceAdminWebKeys.COMMERCE_ADMIN_SERVLET_CONTEXT);

CommerceCurrenciesDisplayContext commerceCurrenciesDisplayContext = (CommerceCurrenciesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceCurrency commerceCurrency = commerceCurrenciesDisplayContext.getCommerceCurrency();
CommerceCurrency primaryCommerceCurrency = commerceCurrenciesDisplayContext.getPrimaryCommerceCurrency();
List<RoundingType> roundingTypes = commerceCurrenciesDisplayContext.getRoundingTypes();

String roundingTypeName = BeanParamUtil.getString(commerceCurrency, request, "roundingType");

boolean primary = BeanParamUtil.getBoolean(commerceCurrency, request, "primary");

String title = LanguageUtil.get(request, "add-currency");

if (commerceCurrency != null) {
	title = LanguageUtil.format(request, "edit-x", commerceCurrency.getName(locale), false);
}

Map<String, Object> data = new HashMap<>();

data.put("direction-right", StringPool.TRUE);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, commerceAdminModuleKey), redirect, data);
PortalUtil.addPortletBreadcrumbEntry(request, title, StringPool.BLANK, data);

renderResponse.setTitle(LanguageUtil.get(request, "settings"));
%>

<liferay-util:include page="/navbar.jsp" servletContext="<%= commerceAdminServletContext %>">
	<liferay-util:param name="commerceAdminModuleKey" value="<%= commerceAdminModuleKey %>" />
</liferay-util:include>

<%@ include file="/breadcrumb.jspf" %>

<portlet:actionURL name="editCommerceCurrency" var="editCommerceCurrencyActionURL" />

<aui:form action="<%= editCommerceCurrencyActionURL %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveCommerceCurrency();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceCurrency == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="commerceCurrencyId" type="hidden" value="<%= (commerceCurrency == null) ? 0 : commerceCurrency.getCommerceCurrencyId() %>" />

	<div class="lfr-form-content">
		<liferay-ui:error exception="<%= CommerceCurrencyCodeException.class %>" message="please-enter-a-valid-code" />
		<liferay-ui:error exception="<%= CommerceCurrencyNameException.class %>" message="please-enter-a-valid-name" />

		<aui:model-context bean="<%= commerceCurrency %>" model="<%= CommerceCurrency.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:input name="name" />

				<aui:input name="code" />

				<aui:select name="roundingType">

					<%
					for (RoundingType roundingType : roundingTypes) {
						String curRoundingTypeName = roundingType.getName();
					%>

						<aui:option label="<%= roundingType.getLabel(locale) %>" selected="<%= curRoundingTypeName.equals(roundingTypeName) %>" value="<%= curRoundingTypeName %>" />

					<%
					}
					%>

				</aui:select>

				<aui:input name="primary" value="<%= primary %>" />

				<%
				String taglibLabel = "exchange-rate";

				if (primaryCommerceCurrency != null) {
					taglibLabel = LanguageUtil.format(request, "exchange-rate-with-x", primaryCommerceCurrency.getName(locale), false);
				}
				%>

				<div class="<%= primary ? "hide" : StringPool.BLANK %>" id="<portlet:namespace />rateOptions">
					<aui:input label="<%= taglibLabel %>" name="rate" type="text" value="<%= (commerceCurrency == null) ? commerceCurrenciesDisplayContext.format(BigDecimal.ZERO) : commerceCurrenciesDisplayContext.format(commerceCurrency.getRate()) %>" />
				</div>

				<aui:input name="priority" />

				<aui:input name="active" />
			</aui:fieldset>
		</aui:fieldset-group>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveCommerceCurrency() {
		submitForm(document.<portlet:namespace />fm);
	}

	Liferay.Util.toggleBoxes('<portlet:namespace />primary', '<portlet:namespace />rateOptions', true);
</aui:script>