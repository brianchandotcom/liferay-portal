<div class="widget-mode-simple">
	<div class="container">
		<div class="col-md-8 mx-auto">
			<#assign
				blogEntries = restClient.get("/headless-delivery/v1.0/sites/" + themeDisplay.getSiteGroupId() + "/blog-postings?filter=creatorId+ne+0&sort=dateCreated:desc").items
			/>

			<#if blogEntries?has_content>
				<#list blogEntries as curBlogEntry>
					<div class="widget-mode-simple-entry">
						<div class="autofit-padded-no-gutters-x autofit-row widget-topbar">
							<div class="autofit-col autofit-col-expand">
								<div class="autofit-row">
									<div class="autofit-col autofit-col-expand">
										<h3 class="title">
											<a
												class="title-link"
												href="${themeDisplay.getPathMain() + "/blogs/find_entry?p_l_id=" + themeDisplay.getPlid() + "&entryId=" + curBlogEntry.id}">${htmlUtil.escape(curBlogEntry.headline)}
											</a>
										</h3>
									</div>
								</div>
							</div>
						</div>
					</div>
				</#list>
			</#if>
		</div>
	</div>
</div>