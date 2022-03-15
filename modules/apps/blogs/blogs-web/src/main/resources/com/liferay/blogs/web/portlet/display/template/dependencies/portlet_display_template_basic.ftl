<div class="widget-mode-simple">
	<#if entries?has_content>
		<#list entries as curBlogEntry>
			<div class="widget-mode-simple-entry">
				<div class="autofit-row widget-topbar">
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
							<a class="title-link" href="${viewEntryPortletURL.toString()}">${htmlUtil.escape(blogsEntryUtil.getDisplayTitle(resourceBundle, curBlogEntry))}</a>
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

				<div class="widget-content">
					<p class="widget-resume">${stringUtil.shorten(htmlUtil.stripHtml(curBlogEntry.getContent()), 400)}</p>

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
								displayStyle="inline"
								target="_blank"
								title=blogsEntryUtil.getDisplayTitle(resourceBundle, curBlogEntry)
								types=blogsPortletInstanceConfiguration.socialBookmarksTypes()
								url=portalUtil.getCanonicalURL(bookmarkURL.toString(), themeDisplay, themeDisplay.getLayout())
							/>
						</div>
					</div>
				</div>
			</div>
		</#list>
	</#if>
</div>