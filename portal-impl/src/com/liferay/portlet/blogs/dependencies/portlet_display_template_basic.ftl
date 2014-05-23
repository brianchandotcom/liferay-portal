<#assign liferay_ui = taglibLiferayHash["/WEB-INF/tld/liferay-ui.tld"] />

<#list entries as entry>
	<div class="entry">
		<#assign viewURL = renderResponse.createRenderURL() />

		${viewURL.setParameter("struts_action", "/blogs/view_entry")}
		${viewURL.setParameter("redirect", currentURL)}
		${viewURL.setParameter("urlTitle", entry.getUrlTitle())}

		<div class="entry-content">
			<div class="entry-title">
				<h2><a href="${viewURL}">${htmlUtil.escape(entry.getTitle())}</a></h2>
			</div>
		</div>

		<div class="entry-body">
			<div class="icon-user entry-author">
				<@liferay.language key="written-by" /> ${htmlUtil.escape(portalUtil.getUserName(entry.getUserId(), entry.getUserName()))}
			</div>

			<#assign summary = entry.getDescription() />

			<#if (validator.isNull(summary))>
				<#assign summary = entry.getContent() />
			</#if>

			${stringUtil.shorten(htmlUtil.stripHtml(summary), 100)}

			<a href="${viewURL}"><@liferay.language key="read-more" /> <span class="hide-accessible"><@liferay.language key="about"/>${htmlUtil.escape(entry.getTitle())}</span> &raquo;</a>
		</div>

		<div class="entry-footer">
			<span class="icon-date entry-date">
				${dateUtil.getDate(entry.getCreateDate(), "dd MMM yyyy - HH:mm:ss", locale)}
			</span>

			<#assign blogsEntryClassName = "com.liferay.portlet.blogs.model.BlogsEntry" />

			<#if getterUtil.getBoolean(enableFlags)>
				<@liferay_ui["flags"]
					className=blogsEntryClassName
					classPK=entry.getEntryId()
					contentTitle=entry.getTitle()
					reportedUserId=entry.getUserId()
				/>
			</#if>

			<span class="entry-categories">
				<@liferay_ui["asset-categories-summary"]
					className=blogsEntryClassName
					classPK=entry.getEntryId()
					portletURL=renderResponse.createRenderURL()
				/>
			</span>

			<span class="entry-tags">
				<@liferay_ui["asset-tags-summary"]
					className=blogsEntryClassName
					classPK=entry.getEntryId()
					portletURL=renderResponse.createRenderURL()
				/>
			</span>
		</div>
	</div>

	<div class="separator"><!-- --></div>
</#list>