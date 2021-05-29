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
CommerceOrderNote commerceOrderNote = commerceOrderContentDisplayContext.getCommerceOrderNote();
%>

<portlet:actionURL name="/commerce_open_order_content/edit_commerce_order_note" var="editCommerceOrderNoteActionURL">
	<portlet:param name="mvcRenderCommandName" value="/commerce_open_order_content/edit_commerce_order_note" />
</portlet:actionURL>

<div class="b2b-portlet-content-header">
	<div class="autofit-float autofit-row header-title-bar">
		<div class="autofit-col autofit-col-expand">
			<liferay-ui:header
				backURL="<%= redirect %>"
				title="edit-note"
			/>
		</div>
	</div>
</div>

<aui:form action="<%= editCommerceOrderNoteActionURL %>" cssClass="order-notes-form" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveCommerceOrderNote();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="commerceOrderNoteId" type="hidden" value="<%= String.valueOf(commerceOrderNote.getCommerceOrderNoteId()) %>" />

	<div class="lfr-form-content">
		<liferay-ui:error exception="<%= CommerceOrderNoteContentException.class %>" message="please-enter-valid-content" />

		<aui:model-context bean="<%= commerceOrderNote %>" model="<%= CommerceOrderNote.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:input autoFocus="<%= true %>" name="content" />

				<c:if test="<%= commerceOrderContentDisplayContext.hasModelPermission(commerceOrderNote.getCommerceOrderId(), CommerceOrderActionKeys.MANAGE_COMMERCE_ORDER_RESTRICTED_NOTES) %>">
					<aui:input helpMessage="restricted-help" label="private" name="restricted" />
				</c:if>
			</aui:fieldset>
		</aui:fieldset-group>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" primary="<%= true %>" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveCommerceOrderNote() {
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>