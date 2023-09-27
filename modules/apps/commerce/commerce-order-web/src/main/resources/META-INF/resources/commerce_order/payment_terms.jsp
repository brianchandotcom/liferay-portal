<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceOrderEditDisplayContext commerceOrderEditDisplayContext = (CommerceOrderEditDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceOrder commerceOrder = commerceOrderEditDisplayContext.getCommerceOrder();

List<CommerceTermEntry> paymentCommerceTermEntries = commerceOrderEditDisplayContext.getPaymentTermsEntries();

long paymentCommerceTermEntryId = commerceOrder.getPaymentCommerceTermEntryId();
%>

<portlet:actionURL name="/commerce_order/edit_commerce_order" var="editCommerceOrderPaymentTermsActionURL" />

<commerce-ui:modal-content
	title='<%= (paymentCommerceTermEntryId == 0) ? LanguageUtil.get(request, "payment-terms") : LanguageUtil.get(request, "edit-payment-terms") %>'
>
	<c:choose>
		<c:when test="<%= paymentCommerceTermEntries.isEmpty() %>">
			<clay:row>
				<clay:col
					size="12"
				>
					<clay:alert
						message="there-are-no-available-payment-terms"
					/>
				</clay:col>
			</clay:row>

			<aui:script use="aui-base">
				var continueButton = A.one('#<portlet:namespace />continue');

				if (continueButton) {
					Liferay.Util.toggleDisabled(continueButton, true);
				}
			</aui:script>
		</c:when>
		<c:otherwise>
			<liferay-ui:error key="paymentTermsInvalid" message="please-select-payment-terms" />

			<%
			Map<Long, String> terms = new HashMap<Long, String>();
			%>

			<aui:form action="<%= editCommerceOrderPaymentTermsActionURL %>" method="post" name="fm">
				<aui:input name="<%= Constants.CMD %>" type="hidden" value="updatePaymentTerms" />
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
				<aui:input name="commerceOrderId" type="hidden" value="<%= commerceOrder.getCommerceOrderId() %>" />

				<aui:select label='<%= LanguageUtil.get(request, "title") %>' name="commercePaymentTermId" showEmptyOption="<%= true %>">

					<%
					for (CommerceTermEntry commerceTermEntry : paymentCommerceTermEntries) {
					%>

						<aui:option label="<%= commerceTermEntry.getLabel(LanguageUtil.getLanguageId(locale)) %>" selected="<%= paymentCommerceTermEntryId == commerceTermEntry.getCommerceTermEntryId() %>" value="<%= commerceTermEntry.getCommerceTermEntryId() %>" />

					<%
						terms.put(commerceTermEntry.getCommerceTermEntryId(), commerceTermEntry.getDescription(LanguageUtil.getLanguageId(locale)));
					}
					%>

				</aui:select>
			</aui:form>

			<label class="control-label <%= (paymentCommerceTermEntryId == 0) ? " d-none" : "" %>" id="description-label"><liferay-ui:message key="description" /></label>

			<div id="description-container">
				<%= commerceOrder.getPaymentCommerceTermEntryDescription() %>
			</div>

			<liferay-frontend:component
				context='<%=
					HashMapBuilder.<String, Object>put(
						"selectId", liferayPortletResponse.getNamespace() + "commercePaymentTermId"
					).put(
						"terms", terms
					).build()
				%>'
				module="js/termsDescriptionHandler"
			/>
		</c:otherwise>
	</c:choose>
</commerce-ui:modal-content>