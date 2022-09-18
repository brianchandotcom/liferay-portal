<style>
	.partner-portal-announcements {
		display: -webkit-box;
		-webkit-box-orient: vertical;
		overflow: hidden;
	}

	@media (min-width: 320px){
		.partner-portal-announcements {
			-webkit-line-clamp: 6;
			max-height: 150px;
			max-width: 120px;
		}
	}

	@media (min-width: 768px){
		.partner-portal-announcements {
			-webkit-line-clamp: 2;
			max-height: 40px;
			max-width: inherit;
		}
	}

	.partner-portal-announcements p {
		display: contents;
	}
</style>

<div>
	<#if entries?has_content>
		<#list entries as curEntry>
			<div class="border border-neutral-3 d-flex justify-content-between mb-3 p-3 rounded">
				<div class="mr-4 pr-2 text-left text-wrap">
					<h6 class="font-weight-bold mb-1 text-neutral-10">
						${htmlUtil.escape(curEntry.getTitle(locale))}
					</h6>

					<div class="partner-portal-announcements text-neutral-8 text-paragraph-sm">
						<@liferay_asset["asset-display"] assetEntry=curEntry />
					</div>
				</div>

				<div class="col-auto d-flex mx-n2">
					<@liferay_ui["user-portrait"]
						size="sm"
						userId=curEntry.getUserId()
						userName=curEntry.getUserName()
					/>

					<div class="flex-wrap ml-2 mt-n1">
						<div class="font-weight-semi-bold text-neutral-10 text-paragraph-xs">
							${htmlUtil.escape(curEntry.getUserName())}
						</div>

						<div class="text-neutral-8 text-paragraph-xxs">
							${dateUtil.getDate(curEntry.getPublishDate(), "MMM dd, yyyy", locale)}
						</div>
					</div>
				</div>
			</div>
		</#list>
	</#if>
</div>