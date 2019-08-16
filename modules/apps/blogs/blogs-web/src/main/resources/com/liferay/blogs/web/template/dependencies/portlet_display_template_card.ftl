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
					<#if cardImage>
						<div class="card-header">
							<div class="aspect-ratio aspect-ratio-8-to-3">
								<img alt="thumbnail" class="aspect-ratio-item-center-middle aspect-ratio-item-fluid" src="${curBlogEntry.getCoverImageURL(themeDisplay)}">
							</div>
						</div>
					</#if>

					<div class="card-body widget-topbar">
						<div class="autofit-row card-title">
							<div class="autofit-col autofit-col-expand">
								<#assign viewEntryPortletURL = renderResponse.createRenderURL() />

								${viewEntryPortletURL.setParameter("mvcRenderCommandName", "/blogs/view_entry")}

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

							<div class="autofit-col visible-interaction">
								<div class="dropdown dropdown-action">
									<@liferay_ui["icon-menu"]
										direction="left-side"
										icon=""
										markupView="lexicon"
										message="actions"
										showWhenSingleIcon=true
									>
										<#if blogsEntryPermission.contains(permissionChecker, curBlogEntry, "UPDATE")>
											<#assign editEntryPortletURL = renderResponse.createRenderURL() />

											${editEntryPortletURL.setWindowState(windowStateFactory.getWindowState("MAXIMIZED"))}
											${editEntryPortletURL.setParameter("mvcRenderCommandName", "/blogs/edit_entry")}
											${editEntryPortletURL.setParameter("redirect", currentURL)}
											${editEntryPortletURL.setParameter("entryId", curBlogEntry.getEntryId()?string)}

											<@liferay_ui["icon"]
												label=true
												message="edit"
												url=editEntryPortletURL.toString()
											/>
										</#if>
										<#if blogsEntryPermission.contains(permissionChecker, curBlogEntry, "PERMISSIONS")>
											<#assign permissionsEntryURL = permissionsURLTag.doTag(null, "com.liferay.blogs.model.BlogsEntry", blogsEntryUtil.getDisplayTitle(resourceBundle, curBlogEntry), curBlogEntry.getGroupId()?string, curBlogEntry.getEntryId()?string, windowStateFactory.getWindowState("POP_UP").toString(), null, request) />

											<@liferay_ui["icon"]
												label=true
												message="permissions"
												method="get"
												url=permissionsEntryURL
												useDialog=true
											/>
										</#if>
										<#if blogsEntryPermission.contains(permissionChecker, curBlogEntry, "DELETE")>
											<#assign deleteEntryPortletURL = renderResponse.createActionURL() />

											${deleteEntryPortletURL.setParameter("javax.portlet.action", "/blogs/edit_entry")}
											${deleteEntryPortletURL.setParameter("cmd", trashHelper.isTrashEnabled(themeDisplay.getScopeGroupId())?then("move_to_trash", "delete"))}
											${deleteEntryPortletURL.setParameter("redirect", currentURL)}
											${deleteEntryPortletURL.setParameter("entryId", curBlogEntry.getEntryId()?string)}

											<@liferay_ui["icon-delete"]
												label=true
												trash=trashHelper.isTrashEnabled(themeDisplay.getScopeGroupId())
												url=deleteEntryPortletURL.toString()
											/>
										</#if>
									</@>
								</div>
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

											<#if serviceLocator??>
												<#assign
													assetEntryLocalService = serviceLocator.findService("com.liferay.asset.kernel.service.AssetEntryLocalService")

													assetEntry = assetEntryLocalService.getEntry("com.liferay.blogs.model.BlogsEntry", curBlogEntry.getEntryId())
												/>

												<#if blogsPortletInstanceConfiguration.enableViewCount()>
													- <@liferay_ui["message"] arguments=assetEntry.getViewCount() key=(assetEntry.getViewCount()==0)?then("x-view", "x-views") />
												</#if>
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
						<div class="card-row">
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
										<@liferay_ui["ratings"]
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
			</div>
		</#list>
	</#if>
</div>