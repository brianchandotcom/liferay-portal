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
	<clay:management-toolbar
		managementToolbarDisplayContext="<%= new CommerceCurrenciesManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, commerceCurrenciesDisplayContext.getSearchContainer()) %>"
		propsTransformer="js/CommerceCurrenciesManagementToolbarPropsTransformer"
	/>

	<portlet:actionURL name="/commerce_currency/edit_commerce_currency" var="editCommerceCurrencyActionURL" />

	<aui:form action="<%= editCommerceCurrencyActionURL %>" cssClass="container" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<liferay-ui:search-container
			id="commerceCurrencies"
			searchContainer="<%= commerceCurrenciesDisplayContext.getSearchContainer() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.commerce.currency.model.CommerceCurrency"
				keyProperty="commerceCurrencyId"
				modelVar="commerceCurrency"
			>
				<liferay-ui:search-container-column-text
					cssClass="font-weight-bold important table-cell-expand"
					href='<%=
						PortletURLBuilder.createRenderURL(
							renderResponse
						).setMVCRenderCommandName(
							"/commerce_currency/edit_commerce_currency"
						).setRedirect(
							currentURL
						).setParameter(
							"commerceCurrencyId", commerceCurrency.getCommerceCurrencyId()
						).buildPortletURL()
					%>'
					name="name"
					value="<%= HtmlUtil.escape(commerceCurrency.getName(locale)) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand"
					name="code"
					value="<%= HtmlUtil.escape(commerceCurrency.getCode()) %>"
				/>

				<liferay-ui:search-container-column-text
					name="exchange-rate"
					value="<%= commerceCurrenciesDisplayContext.format(commerceCurrency.getRate()) %>"
				/>

				<liferay-ui:search-container-column-text
					name="primary"
				>
					<c:if test="<%= commerceCurrency.isPrimary() %>">
						<liferay-ui:icon
							cssClass="commerce-admin-icon-check"
							icon="check"
							markupView="lexicon"
						/>
					</c:if>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					name="active"
				>
					<c:choose>
						<c:when test="<%= commerceCurrency.isActive() %>">
							<liferay-ui:icon
								cssClass="commerce-admin-icon-check"
								icon="check"
								markupView="lexicon"
							/>
						</c:when>
						<c:otherwise>
							<liferay-ui:icon
								cssClass="commerce-admin-icon-times"
								icon="times"
								markupView="lexicon"
							/>
						</c:otherwise>
					</c:choose>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					property="priority"
				/>

				<liferay-ui:search-container-column-jsp
					cssClass="entry-action-column"
					path="/currency_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>

	<aui:script>
		function <portlet:namespace />deleteCommerceCurrencies() {
			Liferay.Util.openConfirmModal({
				message:
					'<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-currencies" />',
				onConfirm: (isConfirmed) => {
					if (isConfirmed) {
						const form = window.document['<portlet:namespace />fm'];

						form['<portlet:namespace /><%= Constants.CMD %>'].value =
							'<%= Constants.DELETE %>';
						form[
							'<portlet:namespace />deleteCommerceCurrencyIds'
						].value = Liferay.Util.getCheckedCheckboxes(
							form,
							'<portlet:namespace />allRowIds'
						);

						submitForm(form);
					}
				},
			});
		}

		function <portlet:namespace />updateExchangeRates() {
			Liferay.Util.openConfirmModal({
				message:
					'<liferay-ui:message key="are-you-sure-you-want-to-update-the-exchange-rate-of-the-selected-currencies" />',
				onConfirm: (isConfirmed) => {
					if (isConfirmed) {
						const form = window.document['<portlet:namespace />fm'];

						form['<portlet:namespace /><%= Constants.CMD %>'].value =
							'updateExchangeRates';
						form[
							'<portlet:namespace />updateCommerceCurrencyExchangeRateIds'
						].value = Liferay.Util.getCheckedCheckboxes(
							form,
							'<portlet:namespace />allRowIds'
						);

						submitForm(form);
					}
				},
			});
		}
	</aui:script>
</c:if>