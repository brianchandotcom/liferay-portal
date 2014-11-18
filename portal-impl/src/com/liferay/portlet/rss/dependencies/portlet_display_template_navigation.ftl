<#assign aui = taglibLiferayHash["/WEB-INF/tld/aui.tld"] />
<#assign liferay_portlet = taglibLiferayHash["/WEB-INF/tld/liferay-portlet.tld"] />
<#assign liferay_ui = taglibLiferayHash["/WEB-INF/tld/liferay-ui.tld"] />

<style>
	.portlet-rss .feed-entry-content {
		margin-bottom: 20px;
		padding-left: 0;
	}
</style>

<#if entries?has_content>
	<#assign dateFormat = "dd MMM yyyy - HH:mm:ss" />

	<div class="container-fluid">
		<div class="row" id="<@liferay_portlet.namespace />feedsTab">
			<ul class="col-xs-4">
				<#list entries as curEntry>
					<li><a href="#tab-${curEntry_index}">${htmlUtil.escape(curEntry.getTitle())}</a></li>
				</#list>
			</ul>

			<div class="col-xs-8 tab-content">
				<#list entries as curEntry>
					<#assign rssFeedEntryDisplayContexts = curEntry.getRSSFeedEntryDisplayContexts(themeDisplay) />

					<#if rssFeedEntryDisplayContexts??>
						<div id="tab-${curEntry_index}" class="tab-pane">
							<#list rssFeedEntryDisplayContexts as rssFeedEntryDisplayContext>
								<#if (rssFeedEntryDisplayContext_index > entriesPerFeed?number)>
									<#break>
								</#if>

								<#assign syndEntry = rssFeedEntryDisplayContext.getSyndEntry() />

								<div class="feed-entry-content">
									<div class="feed-title">
										<@aui["a"] href="${htmlUtil.escapeJSLink(rssFeedEntryDisplayContext.getSyndEntryLink())}">${htmlUtil.escape(syndEntry.getTitle())}</@>
									</div>

									<#if getterUtil.getBoolean(showFeedItemAuthor) && syndEntry.getAuthor()??>
										<div class="feed-entry-author">
											${htmlUtil.escape(syndEntry.getAuthor())}
										</div>
									</#if>

									<#if syndEntry.getPublishedDate()??>
										<div class="feed-date">
											<@liferay_ui["icon"]
												iconCssClass="icon-calendar"
												label=true
												message="${dateUtil.getDate(syndEntry.getPublishedDate(), dateFormat, locale)}"
											/>
										</div>
									</#if>

									${rssFeedEntryDisplayContext.getSanitizedContent()}
								</div>
							</#list>
						</div>
					</#if>
				</#list>
			</div>
		</div>
	</div>

	<@aui["script"] use="aui-base">
		A.use(
			'aui-tabview',
			function(Y) {
				new Y.TabView(
					{
						srcNode: '#<@liferay_portlet.namespace />feedsTab',
						stacked: true,
						type: 'pills'
					}
				).render();
			}
		);
	</@>
</#if>