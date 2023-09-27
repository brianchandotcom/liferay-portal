<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		<div class="sheet">
			<div class="panel-group panel-group-flush">
				<aui:fieldset>
					<aui:input name="content" />

					<c:if test="<%= commerceOrderContentDisplayContext.hasModelPermission(commerceOrderNote.getCommerceOrderId(), CommerceOrderActionKeys.MANAGE_COMMERCE_ORDER_RESTRICTED_NOTES) %>">
						<aui:input helpMessage="restricted-help" label="private" name="restricted" />
					</c:if>
				</aui:fieldset>
			</div>
		</div>
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