<style>
	.partner-portal-announcements {
		display: -webkit-box;
		-webkit-box-orient: vertical;
		overflow: hidden;
	}

	.partner-portal-announcements img,
	.partner-portal-announcements picture {
		display: none;
	}

	.partner-portal-announcements-title:hover {
	  text-decoration: underline;
		cursor: pointer;
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
</style>

<div>
	<#if entries?has_content>
		<#list entries as curEntry>
			<#assign
				entryDate = dateUtil.getDate(curEntry.getDisplayDate(), "MMM dd, yyyy", locale)
				summary = curEntry.getContent()
				title = curEntry.getTitle()
			/>

			<div class="border border-neutral-3 d-flex justify-content-between mb-3 p-3 rounded">
				<div class="mr-4 pr-2 text-left text-wrap">
					<div class="font-weight-bold h6 mb-1 partner-portal-announcements-title text-neutral-10" onclick="handleClick(this)">
						${htmlUtil.escape(title)}
					</div>

					<div class="hide">
						${summary}
					</div>

					<div class="partner-portal-announcements text-neutral-8 text-paragraph-sm">
						${stringUtil.shorten(htmlUtil.stripHtml(summary), 200)}
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
							${entryDate}
						</div>
					</div>
				</div>
			</div>
		</#list>
	</#if>
</div>

<script>
	function handleClick(title){
		const text = title.nextElementSibling.innerHTML;

		Liferay.Util.openModal({
			  headerHTML: title.innerHTML,
				bodyHTML:
					text,
				size: 'lg',
			  center: true,
			  buttons: [
				{
					displayType: 'secondary',
					label: Liferay.Language.get('Cancel'),
					type: 'cancel',
				}
			  ]
			});
	}
</script>