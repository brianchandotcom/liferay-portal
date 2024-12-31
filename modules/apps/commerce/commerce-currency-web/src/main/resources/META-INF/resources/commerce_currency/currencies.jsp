<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceCurrenciesDisplayContext commerceCurrenciesDisplayContext = (CommerceCurrenciesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<c:if test="<%= commerceCurrenciesDisplayContext.hasManageCommerceCurrencyPermission() %>">
	<div class="container-fluid container-xl mt-4">
		<commerce-ui:panel
			bodyClasses="flex-fill"
		>
			<aui:form method="post" name="fm">
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

				<frontend-data-set:headless-display
					apiURL="<%= commerceCurrenciesDisplayContext.getCurrencyApiURL() %>"
					bulkActionDropdownItems="<%= commerceCurrenciesDisplayContext.getBulkActionDropdownItems() %>"
					creationMenu="<%= commerceCurrenciesDisplayContext.getFDSCreationMenu() %>"
					fdsActionDropdownItems="<%= commerceCurrenciesDisplayContext.getFDSActionDropdownItems() %>"
					formName="fm"
					id="<%= CommerceCurrencyFDSNames.COMMERCE_CURRENCIES %>"
					selectedItemsKey="id"
					selectionType="multiple"
					style="fluid"
				/>
			</aui:form>
		</commerce-ui:panel>
	</div>
</c:if>