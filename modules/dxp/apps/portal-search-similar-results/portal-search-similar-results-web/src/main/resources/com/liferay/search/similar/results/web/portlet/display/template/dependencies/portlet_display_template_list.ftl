<div class="display-list">
	<ul class="list-group">
		<#if entries?has_content>
			<#list entries as entry>
				<li class="list-group-item list-group-item-flex">
					<div class="autofit-col">
						<div class="sticker sticker-secondary">
							<@clay.icon symbol="${entry.getIconId()}" />
						</div>
					</div>

					<div class="autofit-col autofit-col-expand">
						<section class="autofit-section">
							<div class="list-group-title">
								<a href="${entry.getViewURL()}" title="${htmlUtil.escape(entry.getTitle())}">
									${htmlUtil.escape(entry.getTitle())}
								</a>
							</div>

							<div class="similar-results-metadata">
								<p class="list-group-subtext">
									<#if (entry.getCreatorUserName()??) && validator.isNotNull(entry.getCreatorUserName())>
										<span class="subtext-item">
											${htmlUtil.escape(entry.getCreatorUserName())}
										</span>
									</#if>

									<#if (entry.getCreationDateString()??) && validator.isNotNull(entry.getCreationDateString())>
										<span class="subtext-item">
											${entry.getCreationDateString()}
										</span>
									</#if>
								</p>

								<p class="list-group-subtext">
									${entry.getModelResource()}
								</p>
							</div>

							<p class="list-group-text similar-results-description">
								${htmlUtil.escape(entry.getContent())}
							</p>
						</section>
					</div>
				</li>
			</#list>
		</#if>
	</ul>
</div>