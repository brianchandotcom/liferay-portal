<#if entries?has_content>
	<div class="grid-2-columns grid-container-fluid grid-display-1">
		<#list entries as curEntry>
			<#assign
				assetRenderer = curEntry.getAssetRenderer()
				viewURL = (!stringUtil.equals(assetLinkBehavior, "showFullContent"))?then(assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, curEntry, true), assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, curEntry))
			/>

			${(curEntry?index == 0)?then(request.setAttribute("aspectRatio", "aspect-ratio-9-to-16"), request.setAttribute("aspectRatio", "aspect-ratio-16-to-9"))}

			${request.setAttribute("viewURL", viewURL )}
			${request.setAttribute("author", portalUtil.getUserName(assetRenderer.getUserId(), assetRenderer.getUserName()) )}

			<@liferay_ui["asset-display"]
				assetEntry=curEntry
				template="Porygon_Entry"
			/>
		</#list>
	</div>

	<div class="clearfix"></div>

	${request.setAttribute("author", "" )}
	${request.setAttribute("viewURL", "" )}
</#if>