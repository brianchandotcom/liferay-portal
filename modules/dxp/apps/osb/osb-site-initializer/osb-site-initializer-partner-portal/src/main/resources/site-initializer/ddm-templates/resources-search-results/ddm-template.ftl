<div class="search-total-label">
	<#if searchContainer.getTotal() == 1>
		${languageUtil.format(locale, "x-result-for-x", [searchContainer.getTotal(), "<strong>" + htmlUtil.escape(searchResultsPortletDisplayContext.getKeywords()) + "</strong>"], false)}
	<#else>
		${languageUtil.format(locale, "x-results-for-x", [searchContainer.getTotal(), "<strong>" + htmlUtil.escape(searchResultsPortletDisplayContext.getKeywords()) + "</strong>"], false)}
	</#if>
</div>

<div class="display-card">
	<ul class="card-page">
		<#if entries?has_content>
			<#list entries as entry>
				<li class="card-page-item card-page-item-asset">
					<div class="bg-brand-primary-lighten-6 border border-brand-primary-lighten-5 card card-type-asset file-card mb-3 rounded">
						<div class="aspect-ratio card-item-first">
							<#if entry.isThumbnailVisible()>
								<img alt="${htmlUtil.escape(entry.getTitle())}" class="aspect-ratio-item aspect-ratio-item-center-middle aspect-ratio-item-vertical-fluid" src="${entry.getThumbnailURLString()}" />
							<#elseif entry.isUserPortraitVisible() && stringUtil.equals(entry.getClassName(), userClassName)>
								<div class="user-card">
									<div class="aspect-ratio-item aspect-ratio-item-center-middle aspect-ratio-item-vertical-fluid card-type-asset-icon">
											<span class="sticker sticker-secondary sticker-user-icon">
												<span class="sticker-overlay">
													<img alt="${htmlUtil.escape(entry.getTitle())}" class="img-fluid" src="${entry.getUserPortraitURLString()}" />
												</span>
											</span>
									</div>
								</div>
							<#else>
								<div class="aspect-ratio-item aspect-ratio-item-center-middle aspect-ratio-item-vertical-fluid card-type-asset-icon">
									<@clay.icon symbol="${(entry.isIconVisible())?then(entry.getIconId(),'web-content')}" />
								</div>
							</#if>
						</div>

						<div class="card-body">
							<div class="card-row">
								<div class="autofit-col autofit-col-expand">
									<section class="autofit-section">
										<#if entry.isModelResourceVisible()>
											<p class="card-subtitle">
												<span class="text-truncate-inline">
													<span class="text-truncate">
														${entry.getModelResource()}
													</span>
												</span>
											</p>
										</#if>

										<h3 class="card-title">
											${entry.getHighlightedTitle()}
										</h3>

										<a class="border-brand-primary-darken-1 btn btn-secondary btn-sm mt-2 text-brand-primary-darken-1" href="${entry.getViewURL()}">
											${languageUtil.get(request, "download")}
										</a>
									</section>
								</div>
							</div>
						</div>
					</div>
				</li>
			</#list>
		</#if>
	</ul>
</div>