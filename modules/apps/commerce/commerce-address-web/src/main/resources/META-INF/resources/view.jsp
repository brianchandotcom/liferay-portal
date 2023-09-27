<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceCountriesDisplayContext commerceCountriesDisplayContext = (CommerceCountriesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<c:if test="<%= commerceCountriesDisplayContext.hasPermission(ActionKeys.MANAGE_COUNTRIES) %>">
	<clay:management-toolbar
		managementToolbarDisplayContext="<%= new CommerceCountriesManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, commerceCountriesDisplayContext.getSearchContainer()) %>"
		propsTransformer="js/CommerceCountriesManagementToolbarPropsTransformer"
	/>

	<div class="container-fluid container-fluid-max-xl">
		<portlet:actionURL name="/commerce_country/edit_commerce_country" var="editCommerceCountryActionURL" />

		<aui:form action="<%= editCommerceCountryActionURL %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

			<liferay-ui:search-container
				id="commerceCountries"
				searchContainer="<%= commerceCountriesDisplayContext.getSearchContainer() %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.portal.kernel.model.Country"
					keyProperty="countryId"
					modelVar="country"
				>
					<liferay-ui:search-container-column-text
						cssClass="font-weight-bold important table-cell-expand"
						href='<%=
							PortletURLBuilder.createRenderURL(
								renderResponse
							).setMVCRenderCommandName(
								"/commerce_country/edit_commerce_country"
							).setRedirect(
								currentURL
							).setParameter(
								"countryId", country.getCountryId()
							).buildPortletURL()
						%>'
						name="name"
						value="<%= HtmlUtil.escape(country.getTitle(locale)) %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand"
						name="billing-allowed"
					>
						<c:choose>
							<c:when test="<%= country.isBillingAllowed() %>">
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
						cssClass="table-cell-expand"
						name="shipping-allowed"
					>
						<c:choose>
							<c:when test="<%= country.isShippingAllowed() %>">
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
						cssClass="table-cell-expand"
						name="two-letter-iso-code"
						property="a2"
					/>

					<liferay-ui:search-container-column-text
						name="active"
					>
						<c:choose>
							<c:when test="<%= country.isActive() %>">
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
						name="priority"
						property="position"
					/>

					<liferay-ui:search-container-column-jsp
						cssClass="entry-action-column"
						path="/commerce_country_action.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					markupView="lexicon"
				/>
			</liferay-ui:search-container>
		</aui:form>
	</div>

	<aui:script>
		function <portlet:namespace />deleteCommerceCountries() {
			Liferay.Util.openConfirmModal({
				message:
					'<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-countries" />',
				onConfirm: (isConfirmed) => {
					if (isConfirmed) {
						var form = window.document['<portlet:namespace />fm'];

						form[
							'<portlet:namespace />deleteCountryIds'
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