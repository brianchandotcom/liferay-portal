<div class="display-card">
	<ul class="card-page">
		<#if entries?has_content>
			<#list entries as entry>
				<li class="card-page-item card-page-item-asset">
					<div class="card card-type-asset file-card">
						<div class="aspect-ratio card-item-first">
							<#if (entry.getThumbnailURLString()??) && validator.isNotNull(entry.getThumbnailURLString())>
								<img alt="${htmlUtil.escape(entry.getTitle())}" class="aspect-ratio-item aspect-ratio-item-center-middle aspect-ratio-item-vertical-fluid" src="${entry.getThumbnailURLString()}" />
							<#else>
								<div class="aspect-ratio-item aspect-ratio-item-center-middle aspect-ratio-item-vertical-fluid card-type-asset-icon">
									<@clay.icon symbol="${entry.getIconId()}" />
								</div>
							</#if>
						</div>

						<div class="card-body">
							<div class="card-row">
								<div class="autofit-col autofit-col-expand">
									<section class="autofit-section">
										<#if (entry.getCategoriesString()??) && validator.isNotNull(entry.getCategoriesString())>
											<span class="card-category text-truncate-inline">
												${entry.getCategoriesString()}
											</span>
										</#if>

										<h3 class="card-title" title="${htmlUtil.escape(entry.getTitle())}">
											<a href="${entry.getViewURL()}">
												${htmlUtil.escape(entry.getTitle())}
											</a>
										</h3>

										<#if (entry.getCreatorUserName()??) && validator.isNotNull(entry.getCreatorUserName())>
											<p class="card-subtitle">
												<span class="text-truncate-inline">
													<span class="text-truncate">
														${htmlUtil.escape(entry.getCreatorUserName())}
													</span>
												</span>
											</p>
										</#if>

										<#if (entry.getCreationDateString()??) && validator.isNotNull(entry.getCreationDateString())>
											<p class="card-subtitle">
												<span class="text-truncate-inline">
													<span class="text-truncate">
														${entry.getCreationDateString()}
													</span>
												</span>
											</p>
										</#if>

										<p class="card-subtitle">
											<span class="text-truncate-inline">
												<span class="text-truncate">
													${entry.getModelResource()}
												</span>
											</span>
										</p>

										<div class="card-description">
											${htmlUtil.escape(entry.getContent())}
										</div>
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