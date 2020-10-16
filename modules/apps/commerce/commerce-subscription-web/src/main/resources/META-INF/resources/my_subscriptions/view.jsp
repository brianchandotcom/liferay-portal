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
CommerceSubscriptionContentDisplayContext commerceSubscriptionContentDisplayContext = (CommerceSubscriptionContentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<c:choose>
	<c:when test="<%= !commerceSubscriptionContentDisplayContext.hasCommerceChannel() %>">
		<div class="alert alert-info mx-auto">
			<liferay-ui:message key="this-site-does-not-have-a-channel" />
		</div>
	</c:when>
	<c:otherwise>
		<div class="container-fluid container-fluid-max-xl" id="<portlet:namespace />subscriptionEntriesContainer">
			<div class="commerce-product-subscription-entries-container" id="<portlet:namespace />entriesContainer">
				<liferay-ui:search-container
					id="commerceSubscriptionEntries"
					iteratorURL="<%= commerceSubscriptionContentDisplayContext.getPortletURL() %>"
					searchContainer="<%= commerceSubscriptionContentDisplayContext.getSearchContainer() %>"
				>
					<liferay-ui:search-container-row
						className="com.liferay.commerce.model.CommerceSubscriptionEntry"
						keyProperty="commerceSubscriptionEntryId"
						modelVar="commerceSubscriptionEntry"
					>

						<%
						CommerceOrderItem commerceOrderItem = commerceSubscriptionEntry.fetchCommerceOrderItem();

						String thumbnailSrc = commerceSubscriptionContentDisplayContext.getCommerceSubscriptionEntryThumbnailSrc(commerceSubscriptionEntry);

						StringJoiner stringJoiner = new StringJoiner(StringPool.COMMA);

						for (KeyValuePair keyValuePair : commerceSubscriptionContentDisplayContext.getKeyValuePairs(commerceSubscriptionEntry)) {
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
							<a class="font-weight-bold" href="<%= HtmlUtil.escapeHREF(commerceSubscriptionContentDisplayContext.getCPDefinitionURL(commerceSubscriptionEntry, themeDisplay)) %>">
								<%= (commerceOrderItem == null) ? StringPool.BLANK : HtmlUtil.escape(commerceOrderItem.getName(languageId)) %>
							</a>

							<h6 class="text-default">
								<%= HtmlUtil.escape(stringJoiner.toString()) %>
							</h6>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-text
							name="status"
						>
							<h6 class="text-default">
								<%= HtmlUtil.escape(LanguageUtil.get(request, CommerceSubscriptionEntryConstants.getSubscriptionStatusLabel(commerceSubscriptionEntry.getSubscriptionStatus()))) %>
							</h6>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-date
							name="start-date"
							property="startDate"
						/>

						<liferay-ui:search-container-column-date
							name="next-iteration-date"
							property="nextIterationDate"
						/>

						<%
						CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();
						%>

						<c:if test="<%= commerceSubscriptionContentDisplayContext.isPaymentMethodActive(commerceOrder.getCommercePaymentMethodKey()) %>">
							<liferay-ui:search-container-column-text>

								<%
								DropdownItemList commerceSubscriptionEntryActionItemList = commerceSubscriptionContentDisplayContext.getCommerceSubscriptionEntryActionItemList(commerceSubscriptionEntry, renderRequest, renderResponse);
								%>

								<c:if test="<%= commerceSubscriptionEntryActionItemList != null %>">
									<c:choose>
										<c:when test="<%= commerceSubscriptionEntryActionItemList.isEmpty() %>">
										</c:when>
										<c:when test="<%= commerceSubscriptionEntryActionItemList.size() > 1 %>">
											<clay:dropdown-menu
												dropdownItems="<%= commerceSubscriptionEntryActionItemList %>"
											/>
										</c:when>
										<c:otherwise>

											<%
											DropdownItem dropdownItem = commerceSubscriptionEntryActionItemList.get(0);
											%>

											<clay:link
												displayType="secondary"
												href='<%= String.valueOf(dropdownItem.get("href")) %>'
												label='<%= String.valueOf(dropdownItem.get("label")) %>'
											/>
										</c:otherwise>
									</c:choose>
								</c:if>
							</liferay-ui:search-container-column-text>
						</c:if>
					</liferay-ui:search-container-row>

					<liferay-ui:search-iterator
						displayStyle="list"
						markupView="lexicon"
					/>
				</liferay-ui:search-container>
			</div>
		</div>
	</c:otherwise>
</c:choose>