<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/returns/init.jsp" %>

<%
CommerceReturnItemCommentEditDisplayContext commerceReturnItemCommentEditDisplayContext = (CommerceReturnItemCommentEditDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

Comment comment = commerceReturnItemCommentEditDisplayContext.getComment();

renderResponse.setTitle(LanguageUtil.get(request, "edit-comment"));
%>

<liferay-frontend:side-panel-content
	title='<%= LanguageUtil.get(request, "edit-comment") %>'
/>

<portlet:actionURL name="/commerce_return_content/edit_commerce_return_item_comment" var="editCommerceReturnItemCommentActionURL">
	<portlet:param name="mvcRenderCommandName" value="/commerce_return_content/edit_commerce_return_item_comment" />
</portlet:actionURL>

<aui:form action="<%= editCommerceReturnItemCommentActionURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveComment();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (comment == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= portletDisplay.getURLBack() %>" />
	<aui:input name="commentId" type="hidden" value="<%= String.valueOf(comment.getCommentId()) %>" />

	<div>
		<liferay-ui:error exception="<%= DuplicateCommentException.class %>" message="please-enter-valid-content" />

		<aui:fieldset>
			<aui:input label="comment" name="body" type="textarea" value="<%= comment.getBody() %>" />
		</aui:fieldset>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" primary="<%= true %>" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= portletDisplay.getURLBack() %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveComment() {
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>