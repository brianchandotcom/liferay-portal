<#assign aui = taglibLiferayHash["/WEB-INF/tld/aui.tld"] />
<#assign liferay_ui = taglibLiferayHash["/WEB-INF/tld/liferay-ui.tld"] />

<#if entries?has_content>
	<#assign dateFormat = "dd MMM yyyy - HH:mm:ss" />

	<#list entries as entry>
		<#assign cssClass = "">

		<#if !entry_has_next>
			<#assign cssClass = "last">
		</#if>

		<div class="feed ${cssClass}">
			<#assign syndFeed = entry.getSyndFeed()>

			<#if syndFeed.getImage()?? && getterUtil.getBoolean(showFeedImage)>
			    <#assign syndImage = syndFeed.getImage()>

				<div class="feed-image">
				    <#assign alt = "">

				    <#if syndImage.getDescription()??>
				        <#assign alt = htmlUtil.escapeAttribute(syndImage.getDescription())>
				    </#if>

					<img alt="${alt}" src="${htmlUtil.escapeHREF(entry.getSyndFeedImageURL())}" />
				</div>
			</#if>

			<#if getterUtil.getBoolean(showFeedTitle)>
				<div class="feed-title">
					<@aui["a"] href="${htmlUtil.escapeJSLink(entry.getSyndFeedLink())}" target="_blank">${htmlUtil.escape(entry.getTitle())}</@>
				</div>
			</#if>

			<#if syndFeed.getPublishedDate()?? && getterUtil.getBoolean(showFeedDescription)>
				<div class="feed-date feed-published-date">
					<@liferay_ui["icon"]
						iconCssClass="icon-calendar"
						label=true
						message="${dateUtil.getDate(syndFeed.getPublishedDate(), dateFormat, locale)}"
					/>
				</div>
			</#if>

			<#if syndFeed.getDescription()?? && getterUtil.getBoolean(showFeedDescription)>
				<div class="feed-description">
					${htmlUtil.escape(syndFeed.getDescription())}
				</div>
			</#if>

			<#assign rssFeedEntryDisplayContexts = entry.getRSSFeedEntryDisplayContexts(themeDisplay)>

			<#if rssFeedEntryDisplayContexts??>
				<@liferay_ui["panel-container"] cssClass="feed-entries" extended=false id="rssFeedsPanelContainer" persistState=false>
					<#list rssFeedEntryDisplayContexts as rssFeedEntryDisplayContext>
						<#if (rssFeedEntryDisplayContext_index > entriesPerFeed?number)>
							<#break>
						</#if>

						<#assign defaultState = "close">

						<#if themeDisplay.isStateMaximized() || (rssFeedEntryDisplayContext_index <= expandedEntriesPerFeed?number)>
							<#assign defaultState = "open">
						</#if>

						<#assign syndEntry = rssFeedEntryDisplayContext.getSyndEntry()>

						<@liferay_ui["panel"] cssClass="feed-entry" defaultState="${defaultState}" extended=false title="${htmlUtil.escape(syndEntry.getTitle())}">
							<div class="feed-entry-content">
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

								<#if rssFeedEntryDisplayContext.getSyndEnclosureLink()??>
									<div class="feed-entry-enclosure">
										<@aui["a"] href="${htmlUtil.escapeJSLink(rssFeedEntryDisplayContext.getSyndEnclosureLink())}" target="_blank">${htmlUtil.escape(rssFeedEntryDisplayContext.getSyndEnclosureLinkTitle())}</@>
									</div>
								</#if>

								${rssFeedEntryDisplayContext.getSanitizedContent()}

								<br />

								<@aui["a"] href="${htmlUtil.escapeJSLink(rssFeedEntryDisplayContext.getSyndEntryLink())}"><@liferay_ui["message"] key="read-more" /></@>
							</div>
						</@>
					</#list>
				</@>
			</#if>
		</div>
	</#list>
</#if>