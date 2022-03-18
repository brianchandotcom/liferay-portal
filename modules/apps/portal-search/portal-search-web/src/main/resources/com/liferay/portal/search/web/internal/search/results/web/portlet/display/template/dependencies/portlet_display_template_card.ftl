<div class="search-total-label">
	${languageUtil.format(locale, "x-results-for-x", [searchContainer.getTotal(), "<strong>" + htmlUtil.escape(searchResultsPortletDisplayContext.getKeywords()) + "</strong>"], false)}
</div>

<div class="display-card">
	<ul class="card-page">
		<#if entries?has_content>
			<#list entries as entry>
				<li class="card-page-item card-page-item-asset">
					<div class="card card-type-asset file-card">
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
										<h3 class="card-title">
											<a href="${entry.getViewURL()}">
												${entry.getHighlightedTitle()}
											</a>
										</h3>

										<#if entry.isCreatorVisible()>
											<p class="card-subtitle">
												<span class="text-truncate-inline">
													<span class="text-truncate">
														${htmlUtil.escape(entry.getCreatorUserName())}
													</span>
												</span>
											</p>
										</#if>

										<#if entry.isCreationDateVisible()>
											<p class="card-subtitle">
												<span class="text-truncate-inline">
													<span class="text-truncate">
														${entry.getCreationDateString()}
													</span>
												</span>
											</p>
										</#if>

										<#if entry.isModelResourceVisible()>
											<p class="card-subtitle">
												<span class="text-truncate-inline">
													<span class="text-truncate">
														${entry.getModelResource()}
													</span>
												</span>
											</p>
										</#if>

										<#if entry.isContentVisible()>
											<p class="card-description">
												${entry.getContent()}
											</p>
										</#if>
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