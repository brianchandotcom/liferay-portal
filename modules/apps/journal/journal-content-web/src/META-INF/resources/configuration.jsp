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
JournalArticle article = journalContentDisplayContext.getArticle();
%>

<liferay-ui:error exception="<%= NoSuchArticleException.class %>" message="the-web-content-could-not-be-found" />

<div class="article-preview row row-spacing">
	<div class="col-md-4 col-xs-12">
		<p class="text-muted"><liferay-ui:message key="selected-web-content" /></p>
		<div class="article-preview-content-container">
			<c:if test="<%= article != null %>">
				<liferay-util:include page="/journal_article_resources.jsp" servletContext="<%= application %>">
					<liferay-util:param name="articleId" value="<%= article.getArticleId() %>" />
				</liferay-util:include>
			</c:if>
		</div>
	</div>
	<div class="col-md-12">
		<aui:button name="webContentSelector" value="change" />
	</div>
</div>	

<div class="<%= article == null ? "hidden " : "" %>row row-spacing template-preview">
	<div class="col-md-4 col-xs-12">
		<p class="text-muted"><liferay-ui:message key="template" /></p>
		<div class="template-preview-content-container">
			<c:if test="<%= article != null %>">
				<liferay-util:include page="/journal_template_resources.jsp" servletContext="<%= application %>">
					<liferay-util:param name="articleId" value="<%= article.getArticleId() %>" />
				</liferay-util:include>
			</c:if>
		</div>
	</div>
	<div class="col-md-12">
		<aui:button name="templateSelector" value="change" />
	</div>
</div>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" varImpl="configurationRenderURL" />

<%
String ddmTemplateKey = journalContentDisplayContext.getDDMTemplateKey();
String redirect = ParamUtil.getString(request, "redirect");
%>

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />
	<aui:input name="preferences--assetEntryId--" type="hidden" value="<%= journalContentDisplayContext.getAssetEntryId() %>" />
	<aui:input name="preferences--ddmTemplateKey--" type="hidden" value="<%= ddmTemplateKey %>" />

	<aui:fieldset>
		<aui:field-wrapper label="user-tools">
			<liferay-ui:asset-addon-entry-selector
				assetAddonEntries="<%= (List<AssetAddonEntry>)(List<?>)journalContentDisplayContext.getEnabledUserToolAssetAddonEntries() %>"
				hiddenInput="preferences--userToolAssetAddonEntryKeys--"
				id="userToolsAssetAddonEntriesSelector"
				selectedAssetAddonEntries="<%= (List<AssetAddonEntry>)(List<?>)journalContentDisplayContext.getSelectedUserToolAssetAddonEntries() %>"
				title="select-user-tools"
			/>
		</aui:field-wrapper>

		<aui:field-wrapper label="content-metadata">
			<liferay-ui:asset-addon-entry-selector
				assetAddonEntries="<%= (List<AssetAddonEntry>)(List<?>)journalContentDisplayContext.getEnabledContentMetadataAssetAddonEntries() %>"
				hiddenInput="preferences--contentMetadataAssetAddonEntryKeys--"
				id="contentMetadataAssetAddonEntriesSelector"
				selectedAssetAddonEntries="<%= (List<AssetAddonEntry>)(List<?>)journalContentDisplayContext.getSelectedContentMetadataAssetAddonEntries() %>"
				title="select-content-metadata"
			/>
		</aui:field-wrapper>

		<aui:input name="preferences--enableViewCountIncrement--" type="checkbox" value="<%= journalContentDisplayContext.isEnableViewCountIncrement() %>" />
	</aui:fieldset>

	<aui:button-row cssClass="dialog-footer">
		<aui:button name="saveButton" type="submit" value="done" />
		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script sandbox="<%= true %>">
	var form = AUI.$(document.<portlet:namespace />fm);

	$('#<portlet:namespace />webContentSelector').on(
		'click',
		function(event) {
			event.preventDefault();

			<%
			PortletURL selectWebContentURL = PortletProviderUtil.getPortletURL(request, JournalArticle.class.getName(), PortletProvider.Action.BROWSE);
			selectWebContentURL.setParameter("groupId", String.valueOf(scopeGroupId));
			selectWebContentURL.setParameter("selectedGroupIds", StringUtil.merge(PortalUtil.getSharedContentSiteGroupIds(company.getCompanyId(), scopeGroupId, user.getUserId())));
			selectWebContentURL.setParameter("typeSelection", JournalArticle.class.getName());
			selectWebContentURL.setParameter("eventName", "selectContent");
			selectWebContentURL.setParameter("refererAssetEntryId", "[$ARTICLE_REFERER_ASSET_ENTRY_ID$]");
			selectWebContentURL.setWindowState(LiferayWindowState.POP_UP);
			String selectWebContentURI = HttpUtil.addParameter(selectWebContentURL.toString(), "doAsGroupId", scopeGroupId);
			%>

			var baseSelectWebContentURI = '<%= selectWebContentURI %>';

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
					uri: baseSelectWebContentURI.replace(escape('[$ARTICLE_REFERER_ASSET_ENTRY_ID$]'), form.fm('assetEntryId').val())
				},
				function(event) {
					<liferay-portlet:resourceURL portletName="<%= JournalContentPortletKeys.JOURNAL_CONTENT %>" var="journalArticleResource">
						<portlet:param name="mvcPath" value="/journal_resources.jsp" />
						<portlet:param name="articleResourcePrimKey" value="[$ARTICLE_RESOURCE_PRIMKEY$]" />
					</liferay-portlet:resourceURL>

					var baseJournalArticleResourceUrl = '<%= journalArticleResource.toString() %>';
					
					$.ajax(
						baseJournalArticleResourceUrl.replace(escape('[$ARTICLE_RESOURCE_PRIMKEY$]'), event.assetclasspk),
						{
							success: function(responseData) {
								$('.article-preview .article-preview-content-container').html($('.article-preview-content', $(responseData)));
								$('.template-preview .template-preview-content-container').html($('.template-preview-content', $(responseData)));
								$('.template-preview').removeClass('hidden');
								form.fm('assetEntryId').val(event.assetentryid);
								form.fm('ddmTemplateKey').val($('.template-preview .template-preview-content').attr('data-template-key'));
							}
						}
					);
				}
			);
		}
	);

	$('#<portlet:namespace />templateSelector').on(
		'click',
		function(event) {
			event.preventDefault();

			<%
			DDMStructure ddmStructure = journalContentDisplayContext.getDDMStructure();
			%>

			Liferay.Util.openDDMPortlet(
				{
					basePortletURL: '<%= PortletURLFactoryUtil.create(request, PortletKeys.DYNAMIC_DATA_MAPPING, PortalUtil.getControlPanelPlid(company.getCompanyId()), PortletRequest.RENDER_PHASE) %>',
					classNameId: '<%= PortalUtil.getClassNameId(DDMStructure.class) %>',
					classPK: $('.template-preview-content').attr('data-structure-key'),
					dialog: {
						destroyOnHide: true
					},
					eventName: 'selectTemplate',
					groupId: $('.template-preview-content').attr('data-group-id'),
					mvcPath: '/select_template.jsp',
					refererPortletName: '<%= JournalPortletKeys.JOURNAL %>',
					resourceClassNameId: $('.template-preview-content').attr('data-structure-id'),
					showAncestorScopes: true,
					templateId: $('.template-preview-content').attr('data-template-id'),
					title: '<liferay-ui:message key="templates" />'
				},
				function(event) {
					$('.template-preview-content').attr('data-template-id', event.ddmtemplateid);
					$('.template-preview-content .template-title').html(event.name);
					$('.template-preview-content .template-description').html(event.description);

					var templateImage = $('.template-preview-content .template-image');

					templateImage.attr('src', event.imageurl);
					templateImage.attr('alt', event.name);

					form.fm('ddmTemplateKey').val(event.ddmtemplatekey);
				}
			);
		}
	);
</aui:script>