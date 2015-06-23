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
JournalArticleDisplay articleDisplay = journalContentDisplayContext.getArticleDisplay();

%>

<liferay-ui:error exception="<%= NoSuchArticleException.class %>" message="the-web-content-could-not-be-found" />

<%
String webContentTitle = HtmlUtil.escapeAttribute(articleDisplay.getTitle());
String webContentSummary = HtmlUtil.escape(articleDisplay.getDescription());
webContentSummary = HtmlUtil.replaceNewLine(webContentSummary);
if (Validator.isNull(webContentSummary)) {
	webContentSummary = HtmlUtil.stripHtml(articleDisplay.getContent());
}
webContentSummary = StringUtil.shorten(webContentSummary, 70);
String webContentImgURL = HtmlUtil.escapeAttribute(articleDisplay.getArticleDisplayImageURL(themeDisplay));
long webContentUserId = articleDisplay.getUserId();
%>

<div class="article-preview row row-spacing">
	<div class="col-md-4 col-xs-12">
		<p class="text-muted"><liferay-ui:message key="selected-web-content" /></p>
		<c:if test="<%= article != null %>">
			<div class="article-preview-content">
				<div class="card-horizontal">
					<div class="card-row">
						<div class="card-col-5">
							<div class="square-thumbnail" style="background-image: url('<%= webContentImgURL %>');"></div>
						</div>
						<div class="card-col-7 card-col-gutters">
							<h2 class="lead"><%= webContentTitle %></h2>
							<p><%= webContentSummary %></p>
							<liferay-ui:user-display
								displayStyle="4"
								userId="<%= webContentUserId %>"
							/>
						</div>
					</div>
				</div>
			</div>
		</c:if>
	</div>
	<div class="col-md-12">
		<aui:button name="webContentSelector" value="change" />
	</div>
</div>	

<c:if test="<%= article != null %>">

	<%
	DDMTemplate ddmTemplate = journalContentDisplayContext.getDDMTemplate();
	String templateTitle = ddmTemplate.getName(locale);
	String templateDescription = ddmTemplate.getDescription();
	String templateImgUrl = ddmTemplate.getTemplateImageURL(themeDisplay);
	%>

	<div class="row row-spacing template-preview">
		<div class="col-md-12">
			<p class="text-muted"><liferay-ui:message key="template" /></p>
			<div class="media template-preview-content">
				<img alt="<%= templateTitle %>" class="media-object pull-left template-image" src="<%= templateImgUrl %>">
				<div class="media-body">
					<h2 class="heading4 template-title"><%= templateTitle %></h2>
					<p class="template-description"><%= templateDescription %></p>
				</div>
			</div>
		</div>
		<div class="col-md-12">
			<aui:button name="templateSelector" value="change" />
		</div>
	</div>
</c:if>

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
			selectWebContentURL.setWindowState(LiferayWindowState.POP_UP);
			%>

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
					uri: '<%= HttpUtil.addParameter(selectWebContentURL.toString(), "doAsGroupId", scopeGroupId) %>'
				},
				function(event) {
					form.fm('assetEntryId').val(event.assetentryid);
					form.fm('ddmTemplateKey').val('');

					$('.displaying-article-id-holder').removeClass('hide');
					$('.displaying-help-message-holder').addClass('hide');

					var displayArticleId = $('.displaying-article-id');

					displayArticleId.html(event.assettitle + ' (<liferay-ui:message key="modified" />)');

					displayArticleId.addClass('modified');
				}
			);
		}
	);
</aui:script>