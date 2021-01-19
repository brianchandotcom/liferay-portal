<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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

		SearchContainer<CommerceVirtualOrderItem> commerceVirtualOrderContentDisplayContextSearchContainer = commerceVirtualOrderItemContentDisplayContext.getSearchContainer();
		%>

		<liferay-ddm:template-renderer
			className="<%= CommerceVirtualOrderItemContentPortlet.class.getName() %>"
			contextObjects="<%= contextObjects %>"
			displayStyle="<%= commerceVirtualOrderItemContentDisplayContext.getDisplayStyle() %>"
			displayStyleGroupId="<%= commerceVirtualOrderItemContentDisplayContext.getDisplayStyleGroupId() %>"
			entries="<%= commerceVirtualOrderContentDisplayContextSearchContainer.getResults() %>"
		>
			<div class="container-fluid container-fluid-max-xl" id="<portlet:namespace />virtualOrderItemsContainer">
				<div class="commerce-virtual-order-items-container" id="<portlet:namespace />entriesContainer">
					<liferay-ui:search-container
						id="commerceVirtualOrderItems"
						iteratorURL="<%= currentURLObj %>"
						searchContainer="<%= commerceVirtualOrderContentDisplayContextSearchContainer %>"
					>
						<liferay-ui:search-container-row
							className="com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem"
							keyProperty="commerceVirtualOrderItemId"
							modelVar="commerceVirtualOrderItem"
						>

							<%
							CommerceOrderItem commerceOrderItem = commerceVirtualOrderItem.getCommerceOrderItem();

							String thumbnailSrc = commerceVirtualOrderItemContentDisplayContext.getCommerceOrderItemThumbnailSrc(commerceOrderItem);

							List<KeyValuePair> keyValuePairs = commerceVirtualOrderItemContentDisplayContext.getKeyValuePairs(commerceOrderItem.getCPDefinitionId(), commerceOrderItem.getJson(), locale);

							StringJoiner stringJoiner = new StringJoiner(StringPool.COMMA);

							for (KeyValuePair keyValuePair : keyValuePairs) {
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
								cssClass="entry-action-column important"
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

								<c:if test="<%= CommerceVirtualOrderItemPermission.contains(permissionChecker, commerceVirtualOrderItem, CommerceVirtualOrderActionKeys.DOWNLOAD_COMMERCE_VIRTUAL_ORDER_ITEM) %>">
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
					function (dialogId) {
						var dialog = Liferay.Util.Window.getById(dialogId);

						dialog.destroy();
					},
					['liferay-util-window']
				);

				Liferay.provide(
					window,
					'<portlet:namespace />downloadCommerceVirtualOrderItem',
					function (dialogId, commerceVirtualOrderItemId) {
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