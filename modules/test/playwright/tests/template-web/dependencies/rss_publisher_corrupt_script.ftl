<#if entries?has_content>
	<#list rssFeedEntries as rssFeedEntry>
		${rssFeedEntry.getSanitizedContent()}