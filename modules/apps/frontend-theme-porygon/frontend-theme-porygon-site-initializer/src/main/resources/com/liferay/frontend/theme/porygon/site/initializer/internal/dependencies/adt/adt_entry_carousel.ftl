<#if entries?has_content>
	<section class="blog-carousel main-carousel-wrapper" id="<@portlet.namespace />">
		<div class="carousel slide" data-ride="carousel" id="<@portlet.namespace />-main-carousel">
			<ol class="carousel-indicators hidden-sm hidden-xs">
				<#list entries as indiEntry>
					<li class="${(indiEntry?counter == 1)?then('active', '')}" data-slide-to="${(indiEntry?counter == 1)?then(0, (indiEntry?counter - 1))}" data-target="<@portlet.namespace />-main-carousel"></li>
				</#list>
			</ol>

			<div class="carousel-inner gallery-xxl" role="listbox">
				<#list entries as curEntry>
					<div class="${(curEntry?counter == 1)?then('active', '')} carousel-item">
						<#assign
							assetRenderer = curEntry.getAssetRenderer()
							assetObject = assetRenderer.getAssetObject()
							viewURL = (!stringUtil.equals(assetLinkBehavior, "showFullContent"))?then(assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, curEntry, true), assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, curEntry))
						/>

						${request.setAttribute("viewURL", viewURL )}
						${request.setAttribute("author", portalUtil.getUserName(assetRenderer.getUserId(), assetRenderer.getUserName()) )}

						<@liferay_journal["journal-article"]
							articleId=assetObject.getArticleId()
							ddmTemplateKey="PORYGON_CAROUSEL"
							groupId=assetObject.getGroupId()
						/>
					</div>
				</#list>
			</div>

			<a class="carousel-control-prev" data-slide="prev" href="#<@portlet.namespace />-main-carousel" role="button">
				<@liferay_ui["icon"]
					icon="angle-left"
					markupView="lexicon"
					message="Previous"
				/>
			</a>

			<a class="carousel-control-next" data-slide="next" href="#<@portlet.namespace />-main-carousel" role="button">
				<@liferay_ui["icon"]
					icon="angle-right"
					markupView="lexicon"
					message="Next"
				/>
			</a>
		</div>
	</section>
<#else>
	<#if !themeDisplay.isSignedIn()>
		${renderRequest.setAttribute("PORTLET_CONFIGURATOR_VISIBILITY", true)}
	</#if>

	<div class="alert alert-info">
		<@liferay_ui["message"] key="there-are-no-results" />
	</div>
</#if>

${request.setAttribute("author", "" )}
${request.setAttribute("viewURL", "" )}