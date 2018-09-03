<#if entries?has_content>
	<div class="blog-list grid-3-columns grid-container-fluid grid-gap-inside">
		<#list entries as curEntry>
			<#assign
				assetRenderer = curEntry.getAssetRenderer()
				assetObject = assetRenderer.getAssetObject()
				viewURL = (!stringUtil.equals(assetLinkBehavior, "showFullContent"))?then(assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, curEntry, true), assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, curEntry))
			/>

			${request.setAttribute("viewURL", viewURL )}
			${request.setAttribute("author", portalUtil.getUserName(assetRenderer.getUserId(), assetRenderer.getUserName()) )}

			<@liferay_journal["journal-article"]
				articleId=assetObject.getArticleId()
				ddmTemplateKey="PORYGON_ENTRY_16_9"
				groupId=assetObject.getGroupId()
			/>
		</#list>
	</div>

	${request.setAttribute("author", "" )}
	${request.setAttribute("viewURL", "" )}
</#if>