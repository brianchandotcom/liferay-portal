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

JournalArticle article = journalContentDisplayContext.getArticle();

List<DDMTemplate> ddmTemplates = journalContentDisplayContext.getDDMTemplates();
%>

<liferay-ui:error exception="<%= NoSuchArticleException.class %>" message="the-web-content-could-not-be-found" />

<div class="article-preview row row-spacing">
	<div class="col-md-3 col-sm-6 col-xs-12">
		<p class="text-muted"><liferay-ui:message key="layout.types.article" /></p>

		<div class="hidden loading-animation"></div>

		<div class="alert alert-danger hidden">
			<liferay-ui:message key="an-unexpected-error-occurred" />
		</div>

		<div class="article-preview-content-container">
			<c:if test="<%= article != null %>">
				<liferay-util:include page="/journal_article_resources.jsp" servletContext="<%= application %>">
					<liferay-util:param name="articleId" value="<%= article.getArticleId() %>" />
				</liferay-util:include>
			</c:if>
		</div>
	</div>
</div>

<div class="row row-button row-spacing">
	<div class="col-md-12">
		<aui:button cssClass="web-content-selector" name="webContentSelector" value='<%= Validator.isNull(article) ? "select" : "change" %>' />
	</div>
</div>

<div class="row row-spacing template-preview <%= article == null ? "hidden" : "" %>">
	<div class="col-md-3 col-sm-6 col-xs-12">
		<p class="text-muted"><liferay-ui:message key="template" /></p>

		<div class="hidden loading-animation"></div>

		<div class="alert alert-danger hidden">
			<liferay-ui:message key="an-unexpected-error-occurred" />
		</div>

		<div class="template-preview-content-container">
			<c:if test="<%= article != null %>">
				<liferay-util:include page="/journal_template_resources.jsp" servletContext="<%= application %>">
					<liferay-util:param name="articleId" value="<%= article.getArticleId() %>" />
				</liferay-util:include>
			</c:if>
		</div>
	</div>
</div>

<div class="row row-button row-spacing template-preview-button <%= ddmTemplates.size() > 1 ? StringPool.BLANK : "hidden" %>">
	<div class="col-md-12">
		<aui:button name="templateSelector" value="change" />
	</div>
</div>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" varImpl="configurationRenderURL" />

<%
String ddmTemplateKey = journalContentDisplayContext.getDDMTemplateKey();
%>

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<div class="<%= article == null ? "hidden" : "" %> configuration-options-container">
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
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" name="saveButton" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script sandbox="<%= true %>">
	var STR_HIDDEN = 'hidden';

	var form = AUI.$(document.<portlet:namespace />fm);

	var articlePreviewNode = $('.article-preview');
	var templatePreviewButtonNode = $('.template-preview-button');
	var templatePreviewNode = $('.template-preview');

	var showLoading = function(element) {
		element.find('.loading-animation').removeClass(STR_HIDDEN);
	};

	var hideLoading = function(element) {
		element.find('.loading-animation').addClass(STR_HIDDEN);
	};

	var showError = function(element) {
		element.find('.alert').removeClass(STR_HIDDEN);
	};

	var hideError = function(element) {
		element.find('.alert').addClass(STR_HIDDEN);
	};

	var showArticleError = function() {
		showError(articlePreviewNode);
	};

	var showTemplateError = function() {
		templatePreviewNode.removeClass(STR_HIDDEN);

		showError(templatePreviewNode);
	};

	$('#<portlet:namespace />webContentSelector').on(
		'click',
		function(event) {
			event.preventDefault();

			<%
			PortletURL selectWebContentURL = PortletProviderUtil.getPortletURL(request, JournalArticle.class.getName(), PortletProvider.Action.BROWSE);

			selectWebContentURL.setParameter("groupId", String.valueOf(scopeGroupId));
			selectWebContentURL.setParameter("selectedGroupIds", StringUtil.merge(PortalUtil.getSharedContentSiteGroupIds(company.getCompanyId(), scopeGroupId, user.getUserId())));
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
					uri: baseSelectWebContentURI.replace(encodeURIComponent('[$ARTICLE_REFERER_ASSET_ENTRY_ID$]'), form.fm('assetEntryId').val())
				},
				function(event) {
					<liferay-portlet:resourceURL portletName="<%= JournalContentPortletKeys.JOURNAL_CONTENT %>" var="journalArticleResource">
						<portlet:param name="mvcPath" value="/journal_resources.jsp" />
						<portlet:param name="articleResourcePrimKey" value="[$ARTICLE_RESOURCE_PRIMKEY$]" />
					</liferay-portlet:resourceURL>

					var baseJournalArticleResourceUrl = '<%= journalArticleResource.toString() %>';

					form.fm('assetEntryId').val(event.assetentryid);
					form.fm('ddmTemplateKey').val('');

					hideError(articlePreviewNode);
					showLoading(articlePreviewNode);

					templatePreviewButtonNode.addClass(STR_HIDDEN);
					templatePreviewNode.addClass(STR_HIDDEN);

					articlePreviewNode.find('.article-preview-content-container').html('');
					templatePreviewNode.find('.template-preview-content-container').html('');

					articlePreviewNode.find('.web-content-selector').html('<liferay-ui:message key="change" />');

					$.ajax(
						baseJournalArticleResourceUrl.replace(encodeURIComponent('[$ARTICLE_RESOURCE_PRIMKEY$]'), event.assetclasspk),
						{
							error: function() {
								hideLoading(articlePreviewNode);
								showArticleError();
							},
							success: function(responseData) {
								var responseDataNode = $(responseData);

								var articlePreviewContent = responseDataNode.find('.article-preview-content');
								var templatePreviewContent = responseDataNode.find('.template-preview-content');

								hideLoading(articlePreviewNode);

								articlePreviewNode.find('.article-preview-content-container').html(articlePreviewContent);
								templatePreviewNode.find('.template-preview-content-container').html(templatePreviewContent);

								if (articlePreviewContent.length > 0) {
									$('.configuration-options-container').removeClass(STR_HIDDEN);

									if (templatePreviewContent.length > 0) {
										var templatePreviewContentNode = templatePreviewNode.find('.template-preview-content');

										templatePreviewButtonNode.toggleClass(STR_HIDDEN, templatePreviewContentNode.attr('data-change-enabled') === 'false');
										templatePreviewNode.removeClass(STR_HIDDEN);

										form.fm('ddmTemplateKey').val(templatePreviewContentNode.attr('data-template-key'));
									}
									else {
										showTemplateError();
									}
								}
								else {
									showArticleError();
								}
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

			var templatePreviewContent = templatePreviewNode.find('.template-preview-content');

			Liferay.Util.openDDMPortlet(
				{
					basePortletURL: '<%= PortalUtil.getControlPanelPortletURL(request, PortletProviderUtil.getPortletId(DDMStructure.class.getName(), PortletProvider.Action.VIEW), PortletRequest.RENDER_PHASE) %>',
					classNameId: '<%= PortalUtil.getClassNameId(DDMStructure.class) %>',
					classPK: templatePreviewContent.attr('data-structure-key'),
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
					templatePreviewContent.attr('data-template-id', event.ddmtemplateid);
					templatePreviewContent.attr('data-template-key', event.ddmtemplatekey);

					templatePreviewContent.find('.template-title').html(event.name);
					templatePreviewContent.find('.template-description').html(event.description);

					var templateImage = templatePreviewContent.find('.template-image');

					if (event.imageurl !== 'null') {
						templateImage.attr('alt', event.name);
						templateImage.attr('src', event.imageurl);
						templateImage.removeClass('hidden');
					}
					else {
						templateImage.attr('alt', '');
						templateImage.attr('src', '');
						templateImage.addClass('hidden');
					}

					form.fm('ddmTemplateKey').val(event.ddmtemplatekey);
				}
			);
		}
	);
</aui:script>