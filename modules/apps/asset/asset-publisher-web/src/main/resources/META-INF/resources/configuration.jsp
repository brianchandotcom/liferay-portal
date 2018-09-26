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
String redirect = ParamUtil.getString(request, "redirect");

String portletResource = ParamUtil.getString(request, "portletResource");

List<AssetRendererFactory<?>> classTypesAssetRendererFactories = new ArrayList<>();
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" varImpl="configurationRenderURL" />

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
	onSubmit="event.preventDefault();"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL.toString() %>" />
	<aui:input name="groupId" type="hidden" />
	<aui:input name="typeSelection" type="hidden" />
	<aui:input name="assetEntryIds" type="hidden" />
	<aui:input name="assetEntryOrder" type="hidden" value="-1" />
	<aui:input name="assetEntryType" type="hidden" />

	<%
	request.setAttribute("configuration.jsp-classTypesAssetRendererFactories", classTypesAssetRendererFactories);
	request.setAttribute("configuration.jsp-configurationRenderURL", configurationRenderURL);
	request.setAttribute("configuration.jsp-redirect", redirect);
	%>

	<liferay-ui:success key='<%= portletResource + "requestProcessed" %>' message="the-asset-list-was-created-successfully" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:form-navigator
			id="<%= AssetPublisherConstants.FORM_NAVIGATOR_ID_CONFIGURATION %>"
			showButtons="<%= false %>"
		/>

		<c:if test="<%= !assetPublisherDisplayContext.isSelectionStyleAssetList() %>">
			<div class="mb-2">
				<aui:a cssClass="create-asset-list-link" href="javascript:;">
					<liferay-ui:message key="create-an-asset-list-from-this-configuration" />
				</aui:a>
			</div>
		</c:if>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button onClick='<%= renderResponse.getNamespace() + "saveSelectBoxes();" %>' type="submit" />

		<aui:button type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<aui:script>
	function <portlet:namespace />saveSelectBoxes() {
		var Util = Liferay.Util;

		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('classNameIds').val(Util.listSelect(form.fm('currentClassNameIds')));

		<%
		for (AssetRendererFactory<?> curRendererFactory : classTypesAssetRendererFactories) {
			String className = assetPublisherWebUtil.getClassName(curRendererFactory);
		%>

			form.fm('classTypeIds<%= className %>').val(Util.listSelect(form.fm('<%= className %>currentClassTypeIds')));

		<%
		}
		%>

		form.fm('metadataFields').val(Util.listSelect(form.fm('currentMetadataFields')));

		submitForm(form);
	}
</aui:script>

<aui:script require="metal-dom/src/all/dom as dom,frontend-js-web/liferay/modal/commands/OpenSimpleInputModal.es as modalCommands">
		function handleCreateAssetListLinkClick(event) {
			event.preventDefault();

			modalCommands.openSimpleInputModal(
				{
					dialogTitle: '<liferay-ui:message key="asset-list-title" />',
					formSubmitURL: '<liferay-portlet:actionURL name="/asset_publisher/add_asset_list" portletName="<%= portletResource %>"><portlet:param name="portletResource" value="<%= portletResource %>" /><portlet:param name="redirect" value="<%= currentURL %>" /></liferay-portlet:actionURL>',
					mainFieldLabel: '<liferay-ui:message key="title" />',
					mainFieldName: 'title',
					mainFieldPlaceholder: '<liferay-ui:message key="title" />',
					namespace: '<%= PortalUtil.getPortletNamespace(portletResource) %>',
					spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg'
				}
			);
		}

	var createAssetListLinkClickHandler = dom.delegate(
		document.body,
		'click',
		'a.create-asset-list-link',
		handleCreateAssetListLinkClick
	);

	function handleDestroyPortlet () {
		createAssetListLinkClickHandler.removeListener();

		Liferay.detach('destroyPortlet', handleDestroyPortlet);
	}

	Liferay.on('destroyPortlet', handleDestroyPortlet);
</aui:script>