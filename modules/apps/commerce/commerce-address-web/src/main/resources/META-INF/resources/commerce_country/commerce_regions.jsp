<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceRegionsDisplayContext commerceRegionsDisplayContext = (CommerceRegionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<c:if test="<%= commerceRegionsDisplayContext.hasPermission(ActionKeys.MANAGE_COUNTRIES) %>">
	<clay:management-toolbar
		managementToolbarDisplayContext="<%= new CommerceRegionsManagementToolbarDisplayContext(commerceRegionsDisplayContext, request, liferayPortletRequest, liferayPortletResponse) %>"
		propsTransformer="js/CommerceRegionsManagementToolbarPropsTransformer"
	/>

	<div class="container-fluid container-fluid-max-xl">
		<portlet:actionURL name="/commerce_country/edit_commerce_region" var="editCommerceRegionActionURL" />

		<aui:form action="<%= editCommerceRegionActionURL %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="deleteRegionIds" type="hidden" />

			<liferay-ui:search-container
				id="commerceRegions"
				searchContainer="<%= commerceRegionsDisplayContext.getSearchContainer() %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.portal.kernel.model.Region"
					keyProperty="regionId"
					modelVar="region"
				>
					<liferay-ui:search-container-column-text
						cssClass="font-weight-bold important table-cell-expand"
						href='<%=
							PortletURLBuilder.createRenderURL(
								renderResponse
							).setMVCRenderCommandName(
								"/commerce_country/edit_commerce_region"
							).setRedirect(
								currentURL
							).setParameter(
								"countryId", region.getCountryId()
							).setParameter(
								"regionId", region.getRegionId()
							).buildPortletURL()
						%>'
						value="<%= HtmlUtil.escape(region.getName()) %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand"
						value="<%= HtmlUtil.escape(region.getRegionCode()) %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand"
						name="active"
					>
						<c:choose>
							<c:when test="<%= region.isActive() %>">
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
						name="priority"
						property="position"
					/>

					<liferay-ui:search-container-column-jsp
						cssClass="entry-action-column"
						path="/commerce_region_action.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					markupView="lexicon"
				/>
			</liferay-ui:search-container>
		</aui:form>
	</div>

	<aui:script>
		function <portlet:namespace />deleteCommerceRegions() {
			Liferay.Util.openConfirmModal({
				message:
					'<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-regions" />',
				onConfirm: (isConfirmed) => {
					if (isConfirmed) {
						var form = window.document['<portlet:namespace />fm'];

						form[
							'<portlet:namespace />deleteRegionIds'
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