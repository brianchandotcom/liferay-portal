<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceVirtualOrderItemContentDisplayContext commerceVirtualOrderItemContentDisplayContext = (CommerceVirtualOrderItemContentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<c:choose>
	<c:when test="<%= !commerceVirtualOrderItemContentDisplayContext.hasCommerceChannel() %>">
		<div class="alert alert-info mx-auto">
			<liferay-ui:message key="this-site-does-not-have-a-channel" />
		</div>
	</c:when>
	<c:otherwise>

		<%
		Map<String, Object> contextObjects = HashMapBuilder.<String, Object>put(
			"commerceVirtualOrderItemContentDisplayContext", commerceVirtualOrderItemContentDisplayContext
		).build();

		SearchContainer<CommerceVirtualOrderItem> commerceVirtualOrderItemContentDisplayContextSearchContainer = commerceVirtualOrderItemContentDisplayContext.getSearchContainer();
		%>

		<liferay-ddm:template-renderer
			className="<%= CommerceVirtualOrderItemContentPortlet.class.getName() %>"
			contextObjects="<%= contextObjects %>"
			displayStyle="<%= commerceVirtualOrderItemContentDisplayContext.getDisplayStyle() %>"
			displayStyleGroupId="<%= commerceVirtualOrderItemContentDisplayContext.getDisplayStyleGroupId() %>"
			entries="<%= commerceVirtualOrderItemContentDisplayContextSearchContainer.getResults() %>"
		>
			<div class="container-fluid container-fluid-max-xl" id="<portlet:namespace />virtualOrderItemsContainer">
				<div class="commerce-virtual-order-items-container" id="<portlet:namespace />entriesContainer">
					<liferay-ui:search-container
						id="commerceVirtualOrderItems"
						iteratorURL="<%= currentURLObj %>"
						searchContainer="<%= commerceVirtualOrderItemContentDisplayContextSearchContainer %>"
					>
						<liferay-ui:search-container-row
							className="com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem"
							keyProperty="commerceVirtualOrderItemId"
							modelVar="commerceVirtualOrderItem"
						>

							<%
							CommerceOrderItem commerceOrderItem = commerceVirtualOrderItem.getCommerceOrderItem();

							String thumbnailSrc = commerceVirtualOrderItemContentDisplayContext.getCommerceOrderItemThumbnailSrc(commerceOrderItem);

							StringJoiner stringJoiner = new StringJoiner(StringPool.COMMA);

							for (KeyValuePair keyValuePair : commerceVirtualOrderItemContentDisplayContext.getKeyValuePairs(commerceOrderItem.getCPDefinitionId(), commerceOrderItem.getJson(), locale)) {
								stringJoiner.add(keyValuePair.getValue());
							}
							%>

							<liferay-ui:search-container-column-image
								name="product"
								src="<%= thumbnailSrc %>"
							/>

							<liferay-ui:search-container-column-text
								name="description"
							>
								<%= HtmlUtil.escape(commerceOrderItem.getName(languageId)) %>

								<h6 class="text-default">
									<%= HtmlUtil.escape(stringJoiner.toString()) %>
								</h6>
							</liferay-ui:search-container-column-text>

							<liferay-ui:search-container-column-text
								cssClass="entry-action-column font-weight-bold important"
							>

								<%
								String downloadURL = commerceVirtualOrderItemContentDisplayContext.getDownloadURL(commerceVirtualOrderItem);

								CPDefinitionVirtualSetting cpDefinitionVirtualSetting = commerceVirtualOrderItemContentDisplayContext.getCPDefinitionVirtualSetting(commerceOrderItem);

								Map<String, Object> data = new HashMap<>();
								boolean useDialog = false;

								if ((cpDefinitionVirtualSetting != null) && cpDefinitionVirtualSetting.isTermsOfUseRequired()) {
									data.put("destroyOnHide", true);
									data.put("id", HtmlUtil.escape(portletDisplay.getNamespace()) + "viewTermsOfUseDialog");
									data.put("title", HtmlUtil.escape(LanguageUtil.get(request, "terms-of-use")));

									useDialog = true;
								}
								%>

								<c:if test="<%= commerceVirtualOrderItemContentDisplayContext.hasPermission(permissionChecker, commerceVirtualOrderItem, CommerceVirtualOrderActionKeys.DOWNLOAD_COMMERCE_VIRTUAL_ORDER_ITEM) && (cpDefinitionVirtualSetting != null) && ((cpDefinitionVirtualSetting.getFileEntryId() > 0) || Validator.isNotNull(cpDefinitionVirtualSetting.getUrl())) %>">
									<aui:form action="<%= String.valueOf(commerceVirtualOrderItemContentDisplayContext.getDownloadResourceURL(commerceVirtualOrderItem.getCommerceVirtualOrderItemId())) %>" method="post" name='<%= commerceVirtualOrderItem.getCommerceVirtualOrderItemId() + "Fm" %>' />

									<liferay-ui:icon
										data="<%= data %>"
										label="<%= true %>"
										message="download"
										url="<%= downloadURL %>"
										useDialog="<%= useDialog %>"
									/>
								</c:if>
							</liferay-ui:search-container-column-text>
						</liferay-ui:search-container-row>

						<liferay-ui:search-iterator
							displayStyle="list"
							markupView="lexicon"
						/>
					</liferay-ui:search-container>
				</div>
			</div>

			<aui:script>
				Liferay.provide(
					window,
					'<portlet:namespace />closePopup',
					(dialogId) => {
						var dialog = Liferay.Util.Window.getById(dialogId);

						dialog.destroy();
					},
					['liferay-util-window']
				);

				Liferay.provide(
					window,
					'<portlet:namespace />downloadCommerceVirtualOrderItem',
					(dialogId, commerceVirtualOrderItemId) => {
						<portlet:namespace />closePopup(dialogId);

						var formName =
							'#<portlet:namespace />' + commerceVirtualOrderItemId + 'Fm';

						var form = window.document.querySelector(formName);

						submitForm(form);
					},
					['aui-base']
				);
			</aui:script>
		</liferay-ddm:template-renderer>
	</c:otherwise>
</c:choose>