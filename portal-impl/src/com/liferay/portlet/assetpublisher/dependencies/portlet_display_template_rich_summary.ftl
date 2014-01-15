<#assign liferay_ui = taglibLiferayHash["/WEB-INF/tld/liferay-ui.tld"] />

<#list entries as entry>
	<#assign entry = entry />

	<#assign assetRenderer = entry.getAssetRenderer() />

	<#assign entryTitle = htmlUtil.escape(assetRenderer.getTitle(locale)) />

	<#assign viewURL = assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, entry) />

	<#if assetLinkBehavior != "showFullContent">
		<#assign viewURL = assetRenderer.getURLViewInContext(renderRequest, renderResponse, viewURL) />
	</#if>

	<div class="asset-abstract">
		<div class="lfr-meta-actions asset-actions">
			<@getPrintIcon />

			<@getFlagsIcon />

			<@getEditIcon />
		</div>

		<h3 class="asset-title">
			<a href="${viewURL}"><img alt="" src="${assetRenderer.getIconPath(renderRequest)}" />${entryTitle}</a>
		</h3>

		<@getMetadataField fieldName="tags" />

		<@getMetadataField fieldName="create-date" />

		<@getMetadataField fieldName="view-count" />

		<div class="asset-content">
			<@getSocialBookmarks />

			<div class="asset-summary">
				<@getMetadataField fieldName="author" />

				${htmlUtil.escape(assetRenderer.getSummary(locale))}

				<a href="${viewURL}"><@liferay.language key="read-more" /><span class="hide-accessible"><@liferay.language key="about" />${entryTitle}</span> &raquo;</a>
			</div>

			<@getRatings />

			<@getRelatedAssets />

			<@getDiscussion />
		</div>
	</div>

</#list>

<#macro getDiscussion>
	<#if validator.isNotNull(assetRenderer.getDiscussionPath()) && (enableComments == "true")>
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
			userId=assetRenderer.getUserId()
		/>
	</#if>
</#macro>

<#macro getEditIcon>
	<#if assetRenderer.hasEditPermission(themeDisplay.getPermissionChecker())>
		<#assign redirectURL = renderResponse.createRenderURL() />

		${redirectURL.setParameter("struts_action", "/asset_publisher/add_asset_redirect")}
		${redirectURL.setWindowState("pop_up")}

		<#assign editPortletURL = assetRenderer.getURLEdit(renderRequest, renderResponse, windowStateFactory.getWindowState("pop_up"), redirectURL)!"" />

		<#if validator.isNotNull(editPortletURL)>
			<#assign title = languageUtil.format(locale, "edit-x", entryTitle, false) />

			<@liferay_ui["icon"]
				image="edit"
				message=title
				url="javascript:Liferay.Util.openWindow({dialog: {width: 960}, id:'" + renderResponse.getNamespace() + "editAsset', title: '" + title + "', uri:'" + htmlUtil.escapeURL(editPortletURL.toString()) + "'});"
			/>
		</#if>
	</#if>
</#macro>

<#macro getFlagsIcon>
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

<#macro getMetadataField
	fieldName
>
	<#if stringUtil.split(metadataFields)?seq_contains(fieldName)>
		<span class="metadata-entry metadata-"${fieldName}">
			<#assign dateFormat = "dd MMM yyyy - HH:mm:ss" />

			<#if fieldName == "author">
				<@liferay.language key="by" /> ${portalUtil.getUserName(assetRenderer.getUserId(), assetRenderer.getUserName())}
			<#elseif fieldName == "categories">
				<@liferay_ui["asset-categories-summary"]
					className=entry.getClassName()
					classPK=entry.getClassPK()
					portletURL=renderResponse.createRenderURL()
				/>
			<#elseif fieldName == "create-date">
				${dateUtil.getDate(entry.getCreateDate(), dateFormat, locale)}
			<#elseif fieldName == "expiration-date">
				${dateUtil.getDate(entry.getExpirationDate(), dateFormat, locale)}
			<#elseif fieldName == "modified-date">
				${dateUtil.getDate(entry.getModifiedDate(), dateFormat, locale)}
			<#elseif fieldName == "priority">
				${entry.getPriority()}
			<#elseif fieldName == "publish-date">
				${dateUtil.getDate(entry.getPublishDate(), dateFormat, locale)}
			<#elseif fieldName == "tags">
				<@liferay_ui["asset-tags-summary"]
					className=entry.getClassName()
					classPK=entry.getClassPK()
					portletURL=renderResponse.createRenderURL()
				/>
			<#elseif fieldName == "view-count">
				<@liferay_ui["icon"]
					image="history"
				/>

				${entry.getViewCount()} <@liferay.language key="views" />
			</#if>
		</span>
	</#if>
</#macro>

<#macro getPrintIcon>
	<#if enablePrint == "true" >
		<#assign printURL = renderResponse.createRenderURL() />

		${printURL.setParameter("struts_action", "/asset_publisher/view_content")}
		${printURL.setParameter("assetEntryId", entry.getEntryId()?string)}
		${printURL.setParameter("viewMode", "print")}
		${printURL.setParameter("type", entry.getAssetRendererFactory().getType())}

		<#if (validator.isNotNull(assetRenderer.getUrlTitle()))>
			<#if (assetRenderer.getGroupId() != themeDisplay.getScopeGroupId())>
				${printURL.setParameter("groupId", assetRenderer.getGroupId()?string)}
			</#if>

			${printURL.setParameter("urlTitle", assetRenderer.getUrlTitle())}
		</#if>

		${printURL.setWindowState("pop_up")}

		<@liferay_ui["icon"]
			image="print"
			message="print"
			url="javascript:Liferay.Util.openWindow({id:'" + renderResponse.getNamespace() + "printAsset', title: '" + languageUtil.format(locale, "print-x-x", ["hide-accessible", entryTitle], false) + "', uri: '" + htmlUtil.escapeURL(printURL.toString()) + "'});"
		/>
	</#if>
</#macro>

<#macro getRatings>
	<#if (enableRatings == "true")>
		<div class="asset-ratings">
			<@liferay_ui["ratings"]
				className=entry.getClassName()
				classPK=entry.getClassPK()
			/>
		</div>
	</#if>
</#macro>

<#macro getRelatedAssets>
	<#if enableRelatedAssets == "true">
		<@liferay_ui["asset-links"]
			assetEntryId=entry.getEntryId()
		/>
	</#if>
</#macro>

<#macro getSocialBookmarks>
	<#if enableSocialBookmarks == "true">
		<@liferay_ui["social-bookmarks"]
			displayStyle="${socialBookmarksDisplayStyle}"
			target="_blank"
			title=entry.getTitle(locale)
			url=viewURL
		/>
	</#if>
</#macro>