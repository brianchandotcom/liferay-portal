<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceReturnEditDisplayContext commerceReturnEditDisplayContext = (CommerceReturnEditDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceOrderItem commerceOrderItem = commerceReturnEditDisplayContext.getCommerceReturnItemCommerceOrderItem();
CommerceReturn commerceReturn = commerceReturnEditDisplayContext.getCommerceReturn();
CommerceReturnItem commerceReturnItem = commerceReturnEditDisplayContext.getCommerceReturnItem();
%>

<aui:form name="commerceReturnItemsFm" onSubmit="event.preventDefault();">
	<aui:input name="commerceReturnItemId" type="hidden" value="<%= commerceReturnEditDisplayContext.getCommerceReturnItemId() %>" />

	<commerce-ui:panel
		title='<%= LanguageUtil.get(request, "details") %>'
	>
		<div class="row vertically-divided">
			<div class="col-xl-4">
				<commerce-ui:info-box
					elementClasses="py-3"
					title='<%= LanguageUtil.get(request, "unit-of-measure") %>'
				>
					<p class="mb-0" data-qa-id="commerceReturnUnitOfMeasure"><%= commerceOrderItem.getUnitOfMeasureKey() %></p>
				</commerce-ui:info-box>
			</div>

			<div class="col-xl-4">
				<commerce-ui:info-box
					elementClasses="py-3"
					title='<%= LanguageUtil.get(request, "requested-quantity") %>'
				>
					<p class="mb-0" data-qa-id="commerceReturnRequestedQuantity"><%= commerceReturnItem.getQuantity() %></p>
				</commerce-ui:info-box>
			</div>

			<div class="col-xl-4">
				<commerce-ui:info-box
					elementClasses="py-3"
					title='<%= LanguageUtil.get(request, "return-reason") %>'
				>
					<p class="mb-0" data-qa-id="commerceReturnReturnReason"><%= commerceReturnItem.getReturnReason() %></p>
				</commerce-ui:info-box>
			</div>
		</div>
	</commerce-ui:panel>

	<commerce-ui:panel
		title='<%= LanguageUtil.get(request, "workflow-actions") %>'
	>
		<div class="sheet-section">
			<aui:field-wrapper cssClass="sheet-subtitle" helpMessage="authorization-step-help" label="authorization-step" />

			<div class="row">
				<div class="col">
					<aui:input name="authorized" readonly="<%= ArrayUtil.contains(CommerceReturnConstants.RETURN_STATUSES_LATEST, commerceReturn.getReturnStatus()) || StringUtil.equals(CommerceReturnConstants.RETURN_ITEM_STATUS_PROCESSED, commerceReturnItem.getReturnItemStatus()) %>" required="<%= true %>" type="text" value="<%= commerceReturnItem.getAuthorized() %>" />
					<aui:input disabled="<%= ArrayUtil.contains(CommerceReturnConstants.RETURN_STATUSES_LATEST, commerceReturn.getReturnStatus()) || StringUtil.equals(CommerceReturnConstants.RETURN_ITEM_STATUS_PROCESSED, commerceReturnItem.getReturnItemStatus()) %>" inlineLabel="right" label="authorize-return-without-returning-products" name="authorizeReturnWithoutReturningProducts" type="checkbox" value="<%= commerceReturnItem.isAuthorizeReturnWithoutReturningProducts() %>" />
				</div>
			</div>

			<aui:field-wrapper cssClass="sheet-subtitle" helpMessage="item-acceptance-step-help" label="item-acceptance-step" />

			<div class="row">
				<div class="col">
					<aui:input name="received" readonly="<%= ArrayUtil.contains(CommerceReturnConstants.RETURN_STATUSES_LATEST, commerceReturn.getReturnStatus()) || StringUtil.equals(CommerceReturnConstants.RETURN_ITEM_STATUS_PROCESSED, commerceReturnItem.getReturnItemStatus()) %>" required="<%= true %>" type="text" value="<%= commerceReturnItem.getReceived() %>" />
				</div>
			</div>

			<aui:field-wrapper cssClass="sheet-subtitle" helpMessage="resolution-method-step-help" label="resolution-method-step" />

			<div class="row">
				<div class="col">
					<aui:field-wrapper label='<%= LanguageUtil.get(resourceBundle, "resolution-method") %>' name="resolutionMethodFieldWrapper">
						<div id="autocomplete-root"></div>
					</aui:field-wrapper>
				</div>
			</div>
		</div>
	</commerce-ui:panel>

	<commerce-ui:panel
		title='<%= LanguageUtil.get(request, "comments") %>'
	>
		<div id="<portlet:namespace />commerceReturnItemComments">
			<liferay-comment:discussion
				className="<%= ObjectEntry.class.getName() %>"
				classPK="<%= commerceReturnItem.getId() %>"
				formName="commerceReturnItemsFm"
				hideControls="<%= true %>"
				ratingsEnabled="<%= false %>"
				redirect="<%= currentURL %>"
				userId="<%= themeDisplay.getUserId() %>"
			/>
		</div>
	</commerce-ui:panel>

	<aui:button-row>
		<aui:button cssClass="btn-lg" disabled="<%= ArrayUtil.contains(CommerceReturnConstants.RETURN_STATUSES_LATEST, commerceReturn.getReturnStatus()) || StringUtil.equals(CommerceReturnConstants.RETURN_ITEM_STATUS_PROCESSED, commerceReturnItem.getReturnItemStatus()) %>" type="submit" value="save" />

		<aui:button cssClass="btn-lg" type="cancel" />
	</aui:button-row>
</aui:form>