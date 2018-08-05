<#if entries?has_content>
	<div class="grid-3-columns grid-container-fluid">
		<#list entries as curEntry>
			<#assign
				assetRenderer = curEntry.getAssetRenderer()
				viewURL = (!stringUtil.equals(assetLinkBehavior, "showFullContent"))?then(assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, curEntry, true), assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, curEntry))
			/>

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