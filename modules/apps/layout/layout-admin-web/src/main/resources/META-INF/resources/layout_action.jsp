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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Layout curLayout = (Layout)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= layoutsAdminDisplayContext.isShowViewLayoutAction(curLayout) %>">
		<liferay-ui:icon
			message="view"
			url="<%= layoutsAdminDisplayContext.getViewLayoutURL(curLayout) %>"
		/>
	</c:if>

	<%
	String editLayoutURL = layoutsAdminDisplayContext.getEditLayoutURL(curLayout);
	%>

	<c:if test="<%= Validator.isNotNull(editLayoutURL) %>">
		<liferay-ui:icon
			message='<%= layoutsAdminDisplayContext.isConversionDraft(layout) ? "edit-conversion-draft" : "edit" %>'
			url="<%= editLayoutURL %>"
		/>
	</c:if>

	<c:if test="<%= layoutsAdminDisplayContext.isShowConfigureAction(curLayout) %>">
		<liferay-ui:icon
			message="configure"
			url="<%= layoutsAdminDisplayContext.getConfigureLayoutURL(curLayout) %>"
		/>
	</c:if>

	<c:if test="<%= layoutsAdminDisplayContext.isShowAddChildPageAction(curLayout) %>">
		<liferay-ui:icon
			message="add-child-page"
			url="<%= layoutsAdminDisplayContext.getSelectLayoutPageTemplateEntryURL(layoutsAdminDisplayContext.getFirstLayoutPageTemplateCollectionId(), curLayout.getPlid(), curLayout.isPrivateLayout()) %>"
		/>
	</c:if>

	<c:if test="<%= layoutsAdminDisplayContext.isShowCopyLayoutAction(curLayout) %>">
		<liferay-ui:icon
			cssClass='<%= liferayPortletResponse.getNamespace() + "copy-layout-action-option" %>'
			message="copy-page"
			url="javascript:;"
		/>
	</c:if>

	<c:if test="<%= layoutsAdminDisplayContext.isShowPermissionsAction(curLayout) %>">
		<liferay-ui:icon
			message="permissions"
			method="get"
			url="<%= layoutsAdminDisplayContext.getPermissionsURL(curLayout) %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= layoutsAdminDisplayContext.isShowOrphanPortletsAction(curLayout) %>">
		<liferay-ui:icon
			message="orphan-widgets"
			url="<%= layoutsAdminDisplayContext.getOrphanPortletsURL(curLayout) %>"
		/>
	</c:if>

	<c:if test="<%= layoutsAdminDisplayContext.isShowConvertLayoutAction(curLayout) %>">
		<liferay-ui:icon
			message="convert-to-content-page..."
			url="<%= layoutsAdminDisplayContext.getConvertLayoutURL(curLayout) %>"
		/>
	</c:if>

	<c:if test="<%= layoutsAdminDisplayContext.isShowDeleteAction(curLayout) %>">
		<liferay-ui:icon-delete
			confirmation='<%= curLayout.hasChildren() ? "this-page-has-child-pages-that-will-also-be-removed-are-you-sure-you-want-to-delete-this-page" : "are-you-sure-you-want-to-delete-this-page" %>'
			url="<%= layoutsAdminDisplayContext.getDeleteLayoutURL(curLayout) %>"
		/>
	</c:if>

	<c:if test="<%= layoutsAdminDisplayContext.isShowDiscardDraftAction(curLayout) %>">
		<liferay-ui:icon
			message="discard-draft"
			url="<%= layoutsAdminDisplayContext.getDiscardDraftURL(curLayout) %>"
		/>
	</c:if>

	<c:if test="<%= layoutsAdminDisplayContext.isShowViewCollectionItemsAction(curLayout) %>">
		<liferay-ui:icon
			cssClass='<%= liferayPortletResponse.getNamespace() + "view-collection-items-action-option" %>'
			message="view-collection-items"
			url="javascript:;"
		/>
	</c:if>
</liferay-ui:icon-menu>

<aui:script require="frontend-js-web/liferay/delegate/delegate.es as delegateModule">
	var delegate = delegateModule.default;

	var copyLayoutActionOptionQueryClickHandler = delegate(
		document.body,
		'click',
		'.<portlet:namespace />copy-layout-action-option',
		function (event) {
			Liferay.Util.openModal({
				id: '<portlet:namespace />addLayoutDialog',
				title: '<liferay-ui:message key="copy-page" />',
				url:
					'<%= layoutsAdminDisplayContext.getCopyLayoutRenderURL(curLayout) %>',
			});
		}
	);

	var viewCollectionItemsActionOptionQueryClickHandler = delegate(
		document.body,
		'click',
		'.<portlet:namespace />view-collection-items-action-option',
		function (event) {
			Liferay.Util.openModal({
				id: '<portlet:namespace />viewCollectionItemsDialog',
				title: '<liferay-ui:message key="collection-items" />',
				url:
					'<%= layoutsAdminDisplayContext.getViewCollectionItemsURL(curLayout) %>',
			});
		}
	);

	function handleDestroyPortlet() {
		copyLayoutActionOptionQueryClickHandler.dispose();
		viewCollectionItemsActionOptionQueryClickHandler.dispose();

		Liferay.detach('destroyPortlet', handleDestroyPortlet);
	}

	Liferay.on('destroyPortlet', handleDestroyPortlet);
</aui:script>