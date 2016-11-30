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
%>

<liferay-ui:error exception="<%= NoSuchArticleException.class %>" message="the-web-content-could-not-be-found" />

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" varImpl="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />
	<aui:input name="preferences--assetEntryId--" type="hidden" value="<%= journalContentDisplayContext.getAssetEntryId() %>" />
	<aui:input name="preferences--ddmTemplateKey--" type="hidden" value="<%= journalContentDisplayContext.getDDMTemplateKey() %>" />

	<div class="portlet-configuration-body-content">
		<div class="container-fluid-1280">
			<aui:fieldset-group markupView="lexicon">
				<aui:fieldset>
					<div id="<portlet:namespace />articlePreview">
						<liferay-util:include page="/journal_resources.jsp" servletContext="<%= application %>">
							<liferay-util:param name="refererPortletName" value="<%= renderResponse.getNamespace() %>" />
						</liferay-util:include>
					</div>
				</aui:fieldset>
			</aui:fieldset-group>
		</div>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" name="saveButton" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script sandbox="<%= true %>" use="aui-io-request,aui-parse-content">
	var form = A.one('#<portlet:namespace />fm');

	var articlePreview = A.one('#<portlet:namespace />articlePreview');

	articlePreview.plug(A.Plugin.ParseContent);

	articlePreview.delegate(
		'click',
		function(event) {
			event.preventDefault();

			<%
			PortletURL selectWebContentURL = PortletProviderUtil.getPortletURL(request, JournalArticle.class.getName(), PortletProvider.Action.BROWSE);

			selectWebContentURL.setParameter("groupId", String.valueOf(scopeGroupId));
			selectWebContentURL.setParameter("selectedGroupIds", StringUtil.merge(journalContentDisplayContext.getSelectedGroupIds()));
			selectWebContentURL.setParameter("refererAssetEntryId", "[$ARTICLE_REFERER_ASSET_ENTRY_ID$]");
			selectWebContentURL.setParameter("typeSelection", JournalArticle.class.getName());
			selectWebContentURL.setParameter("showNonindexable", String.valueOf(Boolean.TRUE));
			selectWebContentURL.setParameter("showScheduled", String.valueOf(Boolean.TRUE));
			selectWebContentURL.setParameter("eventName", "selectContent");
			selectWebContentURL.setWindowState(LiferayWindowState.POP_UP);
			%>

			var baseSelectWebContentURI = '<%= selectWebContentURL.toString() %>';

			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						destroyOnHide: true,
						modal: true
					},
					eventName: 'selectContent',
					id: 'selectContent',
					title: '<liferay-ui:message key="select-web-content" />',
					uri: baseSelectWebContentURI.replace(encodeURIComponent('[$ARTICLE_REFERER_ASSET_ENTRY_ID$]'), form.attr('<portlet:namespace/>assetEntryId').val())
				},
				function(event) {
					form.attr('<portlet:namespace/>assetEntryId').val(event.entityid);

					articlePreview.html('<div class="loading-animation"></div>');

					var data = Liferay.Util.ns(
						'<%= PortalUtil.getPortletNamespace(JournalContentPortletKeys.JOURNAL_CONTENT) %>',
						{
							articleResourcePrimKey: event.assetclasspk
						}
					);

					A.io.request(
						'<liferay-portlet:resourceURL portletName="<%= JournalContentPortletKeys.JOURNAL_CONTENT %>" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="mvcPath" value="/journal_resources.jsp" /><portlet:param name="refererPortletName" value="<%= renderResponse.getNamespace() %>" /></liferay-portlet:resourceURL>',
						{
							data: data,
							on: {
								failure: function() {
									articlePreview.html('<div class="alert alert-danger hidden"><liferay-ui:message key="an-unexpected-error-occurred" /></div>');
								},
								success: function(event, id, obj) {
									var responseData = this.get('responseData');

									articlePreview.setContent(responseData);
								}
							}
						}
					);
				}
			);
		},
		'.web-content-selector'
	);
</aui:script>