<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceOrganizationOrderDisplayContext commerceOrganizationOrderDisplayContext = (CommerceOrganizationOrderDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceOrderNote commerceOrderNote = commerceOrganizationOrderDisplayContext.getCommerceOrderNote();
%>

<portlet:actionURL name="editCommerceOrderNote" var="editCommerceOrderNoteActionURL">
	<portlet:param name="mvcRenderCommandName" value="editCommerceOrderNote" />
</portlet:actionURL>

<div class="b2b-portlet-content-header">
	<c:if test="<%= Validator.isNotNull(redirect) %>">
		<liferay-ui:icon
			cssClass="header-back-to"
			icon="order-arrow-down"
			id="TabsBack"
			label="<%= false %>"
			markupView="lexicon"
			message='<%= LanguageUtil.get(resourceBundle, "back") %>'
			method="get"
			url="<%= redirect %>"
		/>
	</c:if>

	<div class="autofit-float autofit-row header-title-bar">
		<div class="autofit-col autofit-col-expand">
			<liferay-ui:header
				backURL="<%= redirect %>"
				showBackURL="<%= false %>"
				title="edit-note"
			/>
		</div>
	</div>
</div>

<aui:form action="<%= editCommerceOrderNoteActionURL %>" cssClass="order-notes-form" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveCommerceOrderNote();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="commerceOrderNoteId" type="hidden" value="<%= String.valueOf(commerceOrderNote.getCommerceOrderNoteId()) %>" />

	<div class="lfr-form-content">
		<liferay-ui:error exception="<%= CommerceOrderNoteContentException.class %>" message="please-enter-a-valid-content" />

		<aui:model-context bean="<%= commerceOrderNote %>" model="<%= CommerceOrderNote.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:input autoFocus="<%= true %>" name="content" />

				<c:if test="<%= commerceOrganizationOrderDisplayContext.hasPermission(commerceOrderNote.getCommerceOrderId(), CommerceOrderActionKeys.MANAGE_COMMERCE_ORDER_RESTRICTED_NOTES) %>">
					<aui:input helpMessage="restricted-help" label="private" name="restricted" />
				</c:if>
			</aui:fieldset>
		</aui:fieldset-group>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg btn-primary" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveCommerceOrderNote() {
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>