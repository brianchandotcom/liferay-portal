<#assign aui = taglibLiferayHash["/WEB-INF/tld/aui.tld"] />
<#assign liferay_portlet = taglibLiferayHash["/WEB-INF/tld/liferay-portlet.tld"] />
<#assign liferay_ui = taglibLiferayHash["/WEB-INF/tld/liferay-ui.tld"] />

<#list entries as entry>

	<#-- Assign the loop variable to a plain variable to make it visible from macros -->

	<#assign entry = entry />

	<#assign asset_renderer = entry.getAssetRenderer() />

	<#assign view_url = assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, entry) />

	<#if assetLinkBehavior != "showFullContent">
		<#assign view_url = asset_renderer.getURLViewInContext(renderRequest, renderResponse, view_url) />
	</#if>

	<div class="asset-abstract">
		<div class="lfr-meta-actions asset-actions">
			<@print_icon />

			<@flags_icon />

			<@edit_icon />
		</div>

		<h3 class="asset-title">
			<a href="${view_url}"><img alt="" src="${asset_renderer.getIconPath(renderRequest)}"/>${entry.getTitle(locale)}</a>
		</h3>

		<@metadata_field field_name="tags" />

		<@metadata_field field_name="create-date" />

		<@metadata_field field_name="view-count" />

		<div class="asset-content">
			<@social_bookmarks />

			<div class="asset-summary">
				<@metadata_field field_name="author" />

				${asset_renderer.getSummary(locale)}

				<a href="${view_url}"><@liferay.language key="read-more" /><span class="aui-helper-hidden-accessible"><@liferay.language key="about"/>${entry.getTitle(locale)}</span> &raquo;</a>
			</div>

			<@ratings />

			<@related_assets />

			<@discussion />
		</div>
	</div>

</#list>

<#macro discussion>
	<#if validator.isNotNull(asset_renderer.getDiscussionPath()) && (enableComments == "true")>
		<br />

		<#assign discussion_url = renderResponse.createActionURL() />

		${discussion_url.setParameter("struts_action", "/asset_publisher/" + asset_renderer.getDiscussionPath())}

		<@liferay_ui["discussion"]
			className=entry.getClassName()
			classPK=entry.getClassPK()
			formAction=discussion_url?string
			formName="fm" + entry.getClassPK()
			ratingsEnabled=enableCommentRatings == "true"
			redirect=portalUtil.getCurrentURL(request)
			subject=asset_renderer.getTitle(locale)
			userId=asset_renderer.getUserId()
		/>
	</#if>
</#macro>

<#macro edit_icon>
	<#if asset_renderer.hasEditPermission(themeDisplay.getPermissionChecker())>
		<#assign redirect_url = renderResponse.createRenderURL() />

		${redirect_url.setParameter("struts_action", "/asset_publisher/add_asset_redirect")}
		${redirect_url.setWindowState("pop_up")}

		<#assign edit_portlet_url = asset_renderer.getURLEdit(renderRequest, renderResponse, windowStateFactory.getWindowState("pop_up"), redirect_url) />

		<#if validator.isNotNull(edit_portlet_url)>
			<#assign title = languageUtil.format(locale, "edit-x", htmlUtil.escape(asset_renderer.getTitle(locale))) />

			<#assign taglib_edit_url = "javascript:Liferay.Util.openWindow({dialog: {width: 960}, id:'" + renderResponse.getNamespace() + "editAsset', title: '" + title + "', uri:'" + htmlUtil.escapeURL(edit_portlet_url.toString()) + "'});" />

			<@liferay_ui["icon"]
				image="edit"
				message=title
				url=taglib_edit_url?string
			/>
		</#if>
	</#if>
</#macro>

<#macro flags_icon>
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

<#macro metadata_field field_name>
	<#assign date_format = "dd MMM yyyy - HH:mm:ss" />

	<#if stringUtil.split(metadataFields)?seq_contains(metadataFieldName)>
		<span class="metadata-entry metadata-"${metadataFieldName}">
			<#switch field_name>
				<#case "author">
					<@liferay.language key="by" /> ${portalUtil.getUserName(asset_renderer.getUserId(), asset_renderer.getUserName())}

					<#break>
				<#case "categories">
					<@liferay_ui["asset-categories-summary"]
						className=entry.getClassName()
						classPK=entry.getClassPK()
						portletURL=renderResponse.createRenderURL()
					/>

					<#break>
				<#case "create-date">
					${dateUtil.getDate(entry.getCreateDate(), date_format, locale)}

					<#break>
				<#case "expiration-date">
					${dateUtil.getDate(entry.getExpirationDate(), date_format, locale)}

					<#break>
				<#case "modified-date">
					${dateUtil.getDate(entry.getModifiedDate(), date_format, locale)}

					<#break>
				<#case "priority">
					${entry.getPriority()}

					<#break>
				<#case "publish-date">
					${ddateUtil.getDate(entry.getPublishDate(), date_format, locale)}

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
		<#assign print_portlet_url = renderResponse.createRenderURL() />

		${print_portlet_url.setWindowState("pop_up")}
		${print_portlet_url.setParameter("struts_action", "/asset_publisher/view_content")}
		${print_portlet_url.setParameter("assetEntryId", entry.getEntryId()?string)}
		${print_portlet_url.setParameter("viewMode", "print")}
		${print_portlet_url.setParameter("type", entry.getAssetRendererFactory().getType())}

		<#if (validator.isNotNull(asset_renderer.getUrlTitle()))>
			<#if (asset_renderer.getGroupId() != themeDisplay.getScopeGroupId())>
				${print_portlet_url.setParameter("groupId", asset_renderer.getGroupId()?string)}
			</#if>

			${print_portlet_url.setParameter("urlTitle", asset_renderer.getUrlTitle())}
		</#if>

		<#assign format_params = ["aui-helper-hidden-accessible", htmlUtil.escape(asset_renderer.getTitle(locale))] />

		<#assign title = languageUtil.format(locale, "print-x-x", format_params) />

		<#assign taglib_print_url = "javascript:Liferay.Util.openWindow({dialog: {width: 960}, id:'" + renderResponse.getNamespace() + "printAsset', title: '" + title + "', uri:'" + htmlUtil.escapeURL(print_portlet_url.toString()) + "'});" />

		<@liferay_ui["icon"]
			image="print"
			message="print"
			url=taglib_print_url?string
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
			url=view_url
		/>
	</#if>
</#macro>