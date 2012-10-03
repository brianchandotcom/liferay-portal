<#setting number_format="computer">

<#assign aui = taglibLiferayHash["/WEB-INF/tld/aui.tld"] />
<#assign liferay_portlet = taglibLiferayHash["/WEB-INF/tld/liferay-portlet.tld"] />
<#assign liferay_ui = taglibLiferayHash["/WEB-INF/tld/liferay-ui.tld"] />

<#list entries as entry>
	<#assign entry = entry>
	<#assign assetRenderer = entry.getAssetRenderer() />
	<#assign viewURL = assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, entry) />

	<#if assetLinkBehavior != "showFullContent">
		<#assign viewURL = assetRenderer.getURLViewInContext(renderRequest, renderResponse, viewURL) />
	</#if>

	<div class="asset-abstract">
		<div class="lfr-meta-actions asset-actions">
			<@print_icon />
			<@flags />
			<@edit_icon />
		</div>

		<h3 class="asset-title">
			<a href="${viewURL}"><img alt="" src="${assetRenderer.getIconPath(renderRequest)}"/>${entry.getTitle(locale)}</a>
		</h3>

		<@metadata_field fieldName="tags" />
		<@metadata_field fieldName="create-date" />
		<@metadata_field fieldName="view-count" />

		<div class="asset-content">
			<@social_bookmarks />

			<div class="asset-summary">
				<@metadata_field fieldName="author" />
				${assetRenderer.getSummary(locale)}
				<a href="${viewURL}"><@liferay.language key="read-more" /><span class="aui-helper-hidden-accessible"><@liferay.language key="about"/>${entry.getTitle(locale)}</span>&raquo;</a>
			</div>

			<@ratings />
			<@related_assets />
			<@discussion />
		</div>
	</div>

</#list>

<#macro discussion>
	<#if validator.isNotNull(assetRenderer.getDiscussionPath()) && enableComments == "true">
		<br />

		<#assign discussionURL = renderResponse.createActionURL() />
		${discussionURL.setParameter("struts_action", "/asset_publisher/" + assetRenderer.getDiscussionPath())}

		<@liferay_ui["discussion"]
			className=entry.getClassName()
			classPK=entry.getClassPK()
			formAction=discussionURL?string
			formName="fm" + entry.getClassPK()
			ratingsEnabled=enableCommentRatings == "true"
			redirect=portalUtil.getCurrentURL(request)
			subject=assetRenderer.getTitle(locale)
			userId=assetRenderer.getUserId()
		/>
	</#if>
</#macro>

<#macro edit_icon>
	<#if assetRenderer.hasEditPermission(themeDisplay.getPermissionChecker())>
		<#assign redirectURL = renderResponse.createRenderURL() />
		${redirectURL.setWindowState("pop_up")}
		${redirectURL.setParameter("struts_action", "/asset_publisher/add_asset_redirect")}

		<#assign editPortletURL = assetRenderer.getURLEdit(renderRequest, renderResponse, "pop_up", redirectURL) />

		<#if validator.isNotNull(editPortletURL)>
			<#assign title = languageUtil.format(locale, "edit-x", htmlUtil.escape(assetRenderer.getTitle(locale))) />
			<#assign taglibEditURL = "javascript:Liferay.Util.openWindow({dialog: {width: 960}, id:'" + renderResponse.getNamespace() + "editAsset', title: '" + title + "', uri:'" + htmlUtil.escapeURL(editPortletURL.toString()) + "'});" />

			<@liferay_ui["icon"]
				image="edit"
				message=title
				url=taglibEditURL?string
			/>
		</#if>
	</#if>
</#macro>

<#macro flags>
	<#if enableFlags == "true">
		<@liferay_ui["flags"]
			className=entry.getClassName()
			classPK=entry.getClassPK()
			contentTitle=entry.getTitle(locale)
			label=false
			reportedUserId=entry.getUserId()
		/>
	</#if>
</#macro>

<#macro metadata_field fieldName>
	<#assign dateFormat = "dd MMM yyyy - HH:mm:ss" />
	<#assign metadataFieldArray = stringUtil.split(metadataFields) />
	<#assign portletURL = renderResponse.createRenderURL() />

	<#if (metadataFieldArray?seq_contains(metadataFieldName))>
		<span class="metadata-entry metadata-"${metadataFieldName}">
			<#switch fieldName>
			    <#case "author">
					<@liferay.language key="by" /> ${portalUtil.getUserName(assetRenderer.getUserId(), assetRenderer.getUserName())}
					<#break>
				<#case "categories">
					<@liferay_ui["asset-categories-summary"]
						className=entry.getClassName()
						classPK=entry.getClassPK()
						portletURL=renderResponse.createRenderURL()
					/>
					<#break>
				<#case "create-date">
					${dateUtil.getDate(entry.getCreateDate(), dateFormat, locale)}
					<#break>
				<#case "expiration-date">
					${dateUtil.getDate(entry.getExpirationDate(), dateFormat, locale)}
					<#break>
				<#case "modified-date">
					${dateUtil.getDate(entry.getModifiedDate(), dateFormat, locale)}
					<#break>
				<#case "priority">
					${entry.getPriority()}
					<#break>
				<#case "publish-date">
					${ddateUtil.getDate(entry.getPublishDate(), dateFormat, locale)}
					<#break>
				<#case "tags">
					<@liferay_ui["asset-tags-summary"]
						className=entry.getClassName()
						classPK=entry.getClassPK()
						portletURL=renderResponse.createRenderURL()
					/>
					<#break>
				<#case "view-count">
					<@liferay_ui["icon"]
						image="history"
					/>
					${entry.getViewCount()} <@liferay.language key="views" />
					<#break>
			</#switch>
		</span>
	</#if>
</#macro>

<#macro print_icon>
	<#if enablePrint == "true" >
		<#assign printPortletURL = renderResponse.createRenderURL() />
		${printPortletURL.setWindowState("pop_up")}
		${printPortletURL.setParameter("struts_action", "/asset_publisher/view_content")}
		${printPortletURL.setParameter("assetEntryId", entry.getEntryId()?string)}
		${printPortletURL.setParameter("viewMode", "print")}
		${printPortletURL.setParameter("type", entry.getAssetRendererFactory().getType())}

		<#if (validator.isNotNull(assetRenderer.getUrlTitle()))>
			<#if (assetRenderer.getGroupId() != themeDisplay.getScopeGroupId())>
				${printPortletURL.setParameter("groupId", assetRenderer.getGroupId()?string)}
			</#if>
			${printPortletURL.setParameter("urlTitle", assetRenderer.getUrlTitle())}
		</#if>

		<#assign formatParams = ["aui-helper-hidden-accessible", htmlUtil.escape(assetRenderer.getTitle(locale))] />
		<#assign title = languageUtil.format(locale, "print-x-x", formatParams) />
		<#assign taglibPrintURL = "javascript:Liferay.Util.openWindow({dialog: {width: 960}, id:'" + renderResponse.getNamespace() + "printAsset', title: '" + title + "', uri:'" + htmlUtil.escapeURL(printPortletURL.toString()) + "'});" />
		<@liferay_ui["icon"]
			image="print"
			message="print"
			url=taglibPrintURL?string
		/>
	</#if>
</#macro>

<#macro ratings>
	<#if (enableRatings == "true")>
		<div class="asset-ratings">
			<@liferay_ui["ratings"]
				className=entry.getClassName()
				classPK=entry.getClassPK()
			/>
		</div>
	</#if>
</#macro>

<#macro related_assets>
	<#if enableRelatedAssets == "true">
		<@liferay_ui["asset-links"]
			assetEntryId=entry.getEntryId()
		/>
	</#if>
</#macro>

<#macro social_bookmarks>
	<#if enableSocialBookmarks == "true">
		<@liferay_ui["social-bookmarks"]
			displayStyle="${socialBookmarksDisplayStyle}"
			target="_blank"
			title=entry.getTitle(locale)
			url=viewURL
		/>
	</#if>
</#macro>