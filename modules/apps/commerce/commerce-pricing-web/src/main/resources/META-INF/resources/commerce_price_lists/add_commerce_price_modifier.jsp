<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommercePriceListDisplayContext commercePriceListDisplayContext = (CommercePriceListDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<portlet:actionURL name="/commerce_price_list/edit_commerce_price_modifier" var="editCommercePriceModifierActionURL" />

<commerce-ui:modal-content
	title='<%= LanguageUtil.get(request, "add-price-modifier") %>'
>
	<div class="col-12 lfr-form-content">
		<aui:form action="<%= editCommercePriceModifierActionURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="commercePriceListId" type="hidden" value="<%= commercePriceListDisplayContext.getCommercePriceListId() %>" />

			<aui:model-context model="<%= CommercePriceModifier.class %>" />

			<aui:input label="name" name="title" required="<%= true %>" />

			<aui:select name="target" required="<%= true %>" showEmptyOption="<%= true %>">

				<%
				for (String target : CommercePriceModifierConstants.TARGETS) {
				%>

					<aui:option label="<%= target %>" value="<%= target %>" />

				<%
				}
				%>

			</aui:select>

			<aui:select label="modifier" name="modifierType" required="<%= true %>" showEmptyOption="<%= true %>">

				<%
				for (CommercePriceModifierType commercePriceModifierType : commercePriceListDisplayContext.getCommercePriceModifierTypes()) {
				%>

					<aui:option label="<%= commercePriceModifierType.getLabel(locale) %>" value="<%= commercePriceModifierType.getKey() %>" />

				<%
				}
				%>

			</aui:select>
		</aui:form>
	</div>
</commerce-ui:modal-content>