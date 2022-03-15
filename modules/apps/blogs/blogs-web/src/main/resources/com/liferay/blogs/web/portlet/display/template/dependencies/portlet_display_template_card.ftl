<div class="row widget-mode-card">
	<#if entries?has_content>
		<#list entries as curBlogEntry>
			<#if curBlogEntry.getCoverImageURL(themeDisplay)??>
				<#assign cardImage = true />
			<#else>
				<#assign cardImage = false />
			</#if>

			<div class="col-lg-4">
				<div class="card">
					<div class="card-header">
						<div class="aspect-ratio aspect-ratio-8-to-3">
							<img alt="thumbnail" class="aspect-ratio-item-center-middle aspect-ratio-item-fluid" src="${cardImage?then(curBlogEntry.getCoverImageURL(themeDisplay), portalUtil.getPathContext(renderRequest) + "/images/cover_image_placeholder.jpg")}">
						</div>
					</div>

					<div class="card-body widget-topbar">
						<div class="autofit-row card-title">
								<div class="autofit-col autofit-col-expand">
									<#assign viewEntryPortletURL = renderResponse.createRenderURL() />

									${viewEntryPortletURL.setParameter("mvcRenderCommandName", "/blogs/view_entry")}
									${viewEntryPortletURL.setParameter("redirect", currentURL)}

									<#if validator.isNotNull(curBlogEntry.getUrlTitle())>
										${viewEntryPortletURL.setParameter("urlTitle", curBlogEntry.getUrlTitle())}
									<#else>
										${viewEntryPortletURL.setParameter("entryId", curBlogEntry.getEntryId()?string)}
									</#if>

									<h3 class="title">
										<a class="title-link" href="${viewEntryPortletURL.toString()}">
										${htmlUtil.escape(blogsEntryUtil.getDisplayTitle(resourceBundle, curBlogEntry))}</a>
									</h3>
								</div>

								<div class="autofit-col">
									<@clay["dropdown-actions"]
										additionalProps=blogsEntryActionDropdownAdditionalProps
										dropdownItems=blogsEntryActionDropdownItemsProvider.getActionDropdownItems(curBlogEntry)
										propsTransformer="blogs_admin/js/ElementsPropsTransformer"
										propsTransformerServletContext=blogsEntryActionDropdownPropsTransformerServletContext
									/>
								</div>
						</div>

						<div class="autofit-row widget-metadata">
							<div class="autofit-col inline-item-before">
								<@liferay_ui["user-portrait"]
									size="lg"
									userId=curBlogEntry.userId
									userName=curBlogEntry.userName
								/>
							</div>

							<div class="autofit-col autofit-col-expand">
								<div class="autofit-row">
									<div class="autofit-col autofit-col-expand">
										<#if serviceLocator??>
											<#assign
												userLocalService = serviceLocator.findService("com.liferay.portal.kernel.service.UserLocalService")

												entryUser = userLocalService.fetchUser(curBlogEntry.getUserId())
											/>

											<#if entryUser?? && !entryUser.isDefaultUser()>
												<#assign entryUserURL = entryUser.getDisplayURL(themeDisplay) />
											</#if>
										</#if>

										<div class="text-truncate-inline">
											<a href="${(entryUserURL?? && validator.isNotNull(entryUserURL))?then(entryUserURL, "")}" class="text-truncate username">${curBlogEntry.getUserName()}</a>
										</div>

										<div>
											${dateUtil.getDate(curBlogEntry.getStatusDate(), "dd MMM", locale)}

											<#if blogsPortletInstanceConfiguration.enableReadingTime()>
												- <@liferay_reading_time["reading-time"] displayStyle="simple" model=curBlogEntry />
											</#if>

											<#assign assetEntry = blogsEntryAssetEntryUtil.getAssetEntry(request, curBlogEntry) />

											<#if blogsPortletInstanceConfiguration.enableViewCount()>
												- <@liferay_ui["message"] arguments=assetEntry.getViewCount() key=(assetEntry.getViewCount()==0)?then("x-view", "x-views") />
											</#if>
										</div>
									</div>
								</div>
							</div>
						</div>

						<#if validator.isNotNull(curBlogEntry.getDescription())>
							<#assign content = curBlogEntry.getDescription() />
						<#else>
							<#assign content = curBlogEntry.getContent() />
						</#if>

						<#if cardImage>
							<p class="widget-resume">${stringUtil.shorten(htmlUtil.stripHtml(content), 150)}</p>
						<#else>
							<p class="widget-resume">${stringUtil.shorten(htmlUtil.stripHtml(content), 400)}</p>
						</#if>
					</div>

					<div class="card-footer">
						<div class="autofit-float autofit-row autofit-row-center widget-toolbar">
							<#if blogsPortletInstanceConfiguration.enableComments()>
								<div class="autofit-col">
									<#assign viewCommentsPortletURL = renderResponse.createRenderURL() />

									${viewCommentsPortletURL.setParameter("mvcRenderCommandName", "/blogs/view_entry")}
									${viewCommentsPortletURL.setParameter("scroll", renderResponse.getNamespace() + "discussionContainer")}

									<#if validator.isNotNull(curBlogEntry.getUrlTitle())>
										${viewCommentsPortletURL.setParameter("urlTitle", curBlogEntry.getUrlTitle())}
									<#else>
										${viewCommentsPortletURL.setParameter("entryId", curBlogEntry.getEntryId()?string)}
									</#if>

									<a class="btn btn-outline-borderless btn-outline-secondary btn-sm" href="${viewCommentsPortletURL.toString()}" title="${language.get(locale, "comments")}">
										<span class="inline-item inline-item-before">
											<@clay["icon"] symbol="comments" />
										</span> ${commentManager.getCommentsCount("com.liferay.blogs.model.BlogsEntry", curBlogEntry.getEntryId())}
									</a>
								</div>
							</#if>

							<#if blogsPortletInstanceConfiguration.enableRatings()>
								<div class="autofit-col">
									<@liferay_ratings["ratings"]
										className="com.liferay.blogs.model.BlogsEntry"
										classPK=curBlogEntry.getEntryId()
									/>
								</div>
							</#if>

							<div class="autofit-col autofit-col-end">
								<#assign bookmarkURL = renderResponse.createRenderURL() />

								${bookmarkURL.setWindowState(windowStateFactory.getWindowState("NORMAL"))}
								${bookmarkURL.setParameter("mvcRenderCommandName", "/blogs/view_entry")}

								<#if validator.isNotNull(curBlogEntry.getUrlTitle())>
									${bookmarkURL.setParameter("urlTitle", curBlogEntry.getUrlTitle())}
								<#else>
									${bookmarkURL.setParameter("entryId", curBlogEntry.getEntryId()?string)}
								</#if>

								<@liferay_social_bookmarks["bookmarks"]
									className="com.liferay.blogs.model.BlogsEntry"
									classPK=curBlogEntry.getEntryId()
									maxInlineItems=0
									target="_blank"
									title=blogsEntryUtil.getDisplayTitle(resourceBundle, curBlogEntry)
									types=blogsPortletInstanceConfiguration.socialBookmarksTypes()
									url=portalUtil.getCanonicalURL(bookmarkURL.toString(), themeDisplay, themeDisplay.getLayout())
								/>
							</div>
						</div>
					</div>
				</div>
			</div>
		</#list>
	</#if>
</div>