<#assign rssPortletInstanceConfiguration = rssDisplayContext.getRSSPortletInstanceConfiguration() />

<style>
	.portlet-rss .feed-entry-content {
		margin-bottom: 20px;
		padding-left: 0;
	}
</style>

<#if entries?has_content>
	<#assign dateFormat = "dd MMM yyyy - HH:mm:ss" />

	<div class="container-fluid">
		<div class="row" id="<@portlet.namespace />feedsTab">
			<ul class="col-xs-4">
				<#list entries as curEntry>
					<li><a href="#tab-${curEntry_index}">${htmlUtil.escape(curEntry.getTitle())}</a></li>
				</#list>
			</ul>

			<div class="col-xs-8 tab-content">
				<#list entries as curEntry>
					<#assign rssFeedEntries = curEntry.getRSSFeedEntries(themeDisplay) />

					<#if rssFeedEntries??>
						<div class="tab-pane" id="tab-${curEntry_index}">
							<#list rssFeedEntries as rssFeedEntry>
								<#if (rssFeedEntry_index > entriesPerFeed?number)>
									<#break>
								</#if>

								<#assign syndEntry = rssFeedEntry.getSyndEntry() />

								<div class="feed-entry-content">
									<div class="feed-title">
										<@liferay_aui["a"] href="${htmlUtil.escapeJSLink(rssFeedEntry.getSyndEntryLink())}">${htmlUtil.escape(syndEntry.getTitle())}</@>
									</div>

									<#if rssPortletInstanceConfiguration.showFeedTitle() && syndEntry.getAuthor()??>
										<div class="feed-entry-author">
											${htmlUtil.escape(syndEntry.getAuthor())}
										</div>
									</#if>

									<#if syndEntry.getPublishedDate()??>
										<div class="feed-date">
											<@liferay_ui["icon"]
												icon="calendar"
												label=true
												markupView="lexicon"
												message="${dateUtil.getDate(syndEntry.getPublishedDate(), dateFormat, locale)}"
											/>
										</div>
									</#if>

									${rssFeedEntry.getSanitizedContent()}
								</div>
							</#list>
						</div>
					</#if>
				</#list>
			</div>
		</div>
	</div>

	<@liferay_aui["script"] use="aui-base,aui-tabview">
		new A.TabView(
			{
				srcNode: '#<@portlet.namespace />feedsTab',
				stacked: true,
				type: 'pills'
			}
		).render();
	</@>
</#if>