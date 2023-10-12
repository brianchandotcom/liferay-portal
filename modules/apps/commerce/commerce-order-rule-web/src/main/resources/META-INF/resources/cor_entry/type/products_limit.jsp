<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
COREntryDisplayContext corEntryDisplayContext = (COREntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<div class="row">
	<div class="col">
		<commerce-ui:panel
			bodyClasses="flex-fill"
			title='<%= LanguageUtil.get(request, "configuration") %>'
		>
			<aui:input label="quantity" name='<%= "type--settings--" + COREntryConstants.TYPE_PRODUCTS_LIMIT_FIELD_PRODUCT_QUANTITY + "--" %>' type="text" value="<%= corEntryDisplayContext.getQuantity() %>">
				<aui:validator name="number" />
			</aui:input>

			<aui:input id='<%= "type--settings--" + COREntryConstants.TYPE_PRODUCTS_LIMIT_FIELD_PRODUCT_IDS + "--" %>' name='<%= "type--settings--" + COREntryConstants.TYPE_PRODUCTS_LIMIT_FIELD_PRODUCT_IDS + "--" %>' type="hidden" value="<%= corEntryDisplayContext.getCProductIds() %>" />

			<div class="row">
				<div class="col-12 pt-4">
					<div id="item-finder-root"></div>

					<liferay-frontend:component
						context='<%=
							HashMapBuilder.<String, Object>put(
								"dataSetId", COREntryFDSNames.CONFIGURABLE_PRODUCTS
							).put(
								"rootPortletId", portletDisplay.getRootPortletId()
							).put(
								"typeSettingsInputId", "type--settings--" + COREntryConstants.TYPE_PRODUCTS_LIMIT_FIELD_PRODUCT_IDS + "--"
							).put(
								"workflowAction", WorkflowConstants.ACTION_PUBLISH
							).build()
						%>'
						module="js/products_limit"
					/>
				</div>

				<div class="col-12">
					<commerce-ui:panel
						bodyClasses="p-0"
						title='<%= LanguageUtil.get(request, "products") %>'
					>
						<frontend-data-set:classic-display
							contextParams='<%=
								HashMapBuilder.<String, String>put(
									"corEntryId", String.valueOf(corEntryDisplayContext.getCOREntryId())
								).build()
							%>'
							dataProviderKey="<%= COREntryFDSNames.CONFIGURABLE_PRODUCTS %>"
							formName="fm"
							id="<%= COREntryFDSNames.CONFIGURABLE_PRODUCTS %>"
							itemsPerPage="<%= 10 %>"
							showSearch="<%= false %>"
						/>
					</commerce-ui:panel>
				</div>
			</div>
		</commerce-ui:panel>
	</div>
</div>