<#if entries?has_content>
	<div class="blog-list grid-container-fluid">
			<#list entries as curEntry>
				<#assign
					assetRenderer = curEntry.getAssetRenderer()
					assetObject = assetRenderer.getAssetObject()
				/>

				${request.setAttribute("author", portalUtil.getUserName(assetRenderer.getUserId(), assetRenderer.getUserName()) )}

				<@liferay_journal["journal-article"]
					articleId=assetObject.getArticleId()
					ddmTemplateKey="PORYGON_ENTRY_16_9"
					groupId=assetObject.getGroupId()
				/>
			</#list>
		</div>
	</div>

	${request.setAttribute("author", "" )}
</#if>