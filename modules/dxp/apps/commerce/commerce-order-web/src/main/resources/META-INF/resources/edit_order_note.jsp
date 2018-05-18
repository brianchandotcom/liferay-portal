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
CommerceOrderNoteEditDisplayContext commerceOrderNoteEditDisplayContext = (CommerceOrderNoteEditDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceOrderNote commerceOrderNote = commerceOrderNoteEditDisplayContext.getCommerceOrderNote();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(LanguageUtil.get(request, "edit-note"));
%>

<portlet:actionURL name="editCommerceOrderNote" var="editCommerceOrderNoteActionURL">
	<portlet:param name="mvcRenderCommandName" value="editCommerceOrderNote" />
</portlet:actionURL>

<aui:form action="<%= editCommerceOrderNoteActionURL %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveCommerceOrderNote();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceOrderNote == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="commerceOrderNoteId" type="hidden" value="<%= String.valueOf(commerceOrderNote.getCommerceOrderNoteId()) %>" />

	<div class="lfr-form-content">
		<liferay-ui:error exception="<%= CommerceOrderNoteContentException.class %>" message="please-enter-a-valid-content" />

		<aui:model-context bean="<%= commerceOrderNote %>" model="<%= CommerceOrderNote.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:input autoFocus="<%= true %>" name="content" />

				<aui:input helpMessage="restricted-help" label="private" name="restricted" />
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