<#setting number_format = "0">

<#assign journalArticleResource = dataFactory.addJournalArticleResource(groupId)>

insert into JournalArticleResource values ('${portalUUIDUtil.generate()}', ${journalArticleResource.resourcePrimKey}, ${journalArticleResource.groupId}, '${journalArticleResource.articleId}');

<#assign journalArticle = dataFactory.addJournalArticle(groupId, companyId, journalArticleResource.resourcePrimKey, journalArticleResource.articleId)>

<#assign journalArticleTitle = '<?xml version="1.0" encoding="UTF-8"?><root available-locales="en_US" default-locale="en_US"><Title language-id="en_US">test journal article</Title></root>'>

<#assign journalArticleContent = '<?xml version="1.0"?><root available-locales="en_US" default-locale="en_US">	<static-content language-id="en_US">&lt;p&gt;' + journalArticle.content +'&lt;/p&gt;</static-content></root>'>

insert into JournalArticle values ('${portalUUIDUtil.generate()}', ${journalArticle.id}, ${journalArticle.resourcePrimKey}, ${journalArticle.groupId}, ${journalArticle.companyId}, ${defaultUserId}, '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0, 0, '${journalArticleResource.articleId}', 1, '${journalArticleTitle}', 'test journal article', '', '${journalArticleContent}', 'general', '', '', '', CURRENT_TIMESTAMP, null, null, 1, 0, 0, '', 0, 0, '', CURRENT_TIMESTAMP);

${sampleSQLBuilder.insertResourcePermission("com.liferay.portlet.journal.model.JournalArticle", stringUtil.valueOf(journalArticleResource.resourcePrimKey))}

<#assign assetEntry = dataFactory.addAssetEntry(groupId, defaultUserId, dataFactory.journalArticleClassName.classNameId, journalArticleResource.resourcePrimKey, true, "text/html", journalArticleTitle)>

insert into AssetEntry (entryId, groupId, companyId, userId, createDate, modifiedDate, classNameId, classPK, visible, mimeType, title) values (${counter.get()}, ${assetEntry.groupId}, ${companyId}, ${assetEntry.userId}, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ${assetEntry.classNameId}, ${assetEntry.classPK}, <#if assetEntry.visible>TRUE<#else>FALSE</#if>, '${assetEntry.mimeType}', '${assetEntry.title}');

<#assign messageId = counter.get()>

<#assign threadId = counter.get()>

<#assign mbMessage = dataFactory.addMBMessage(messageId, groupId, defaultUserId, dataFactory.journalArticleClassName.classNameId, journalArticleResource.resourcePrimKey, -1, threadId, messageId, 0, stringUtil.valueOf(journalArticleResource.resourcePrimKey), stringUtil.valueOf(journalArticleResource.resourcePrimKey))>

${sampleSQLBuilder.insertMBMessage(mbMessage)}

<#assign mbThread = dataFactory.addMBThread(threadId, groupId, companyId, -1, messageId, 1, defaultUserId)>

insert into MBThread values (${mbThread.threadId}, ${mbThread.groupId}, ${mbThread.companyId}, ${mbThread.categoryId}, ${mbThread.rootMessageId}, ${mbThread.rootMessageUserId}, ${mbThread.messageCount}, 0, ${mbThread.lastPostByUserId}, CURRENT_TIMESTAMP, 0, FALSE, 0, ${mbThread.lastPostByUserId}, '', CURRENT_TIMESTAMP);

<#assign mbDiscussion = dataFactory.addMBDiscussion(dataFactory.journalArticleClassName.classNameId, journalArticleResource.resourcePrimKey, counter.get())>

insert into MBDiscussion values (${mbDiscussion.discussionId}, ${mbDiscussion.classNameId}, ${mbDiscussion.classPK}, ${mbDiscussion.threadId});

<#list webContentLayouts as layout>
	<#assign portletPreferences = dataFactory.addPortletPreferences(layout.plid, defaultUserId, "86", "<portlet-preferences><preference><name>showAvailableLocales</name><value>false</value></preference><preference><name>enableViewCountIncrement</name><value>false</value></preference><preference><name>enableRatings</name><value>false</value></preference><preference><name>articleId</name><value>196</value></preference><preference><name>extensions</name><value>NULL_VALUE</value></preference><preference><name>enableRelatedAssets</name><value>true</value></preference><preference><name>templateId</name><value></value></preference><preference><name>enablePrint</name><value>false</value></preference><preference><name>enableCommentRatings</name><value>false</value></preference><preference><name>groupId</name><value>1</value></preference><preference><name>enableComments</name><value>false</value></preference></portlet-preferences>")>
	
	insert into PortletPreferences values (${portletPreferences.portletPreferencesId}, ${portletPreferences.ownerId}, ${portletPreferences.ownerType}, ${portletPreferences.plid}, '${portletPreferences.portletId}', '${portletPreferences.preferences}');
	
	<#assign portletPreferences = dataFactory.addPortletPreferences(layout.plid, defaultUserId, "56", "<portlet-preferences><preference><name>showAvailableLocales</name><value>false</value></preference><preference><name>enableViewCountIncrement</name><value>false</value></preference><preference><name>enableRatings</name><value>false</value></preference><preference><name>articleId</name><value>196</value></preference><preference><name>extensions</name><value>NULL_VALUE</value></preference><preference><name>enableRelatedAssets</name><value>true</value></preference><preference><name>templateId</name><value></value></preference><preference><name>enablePrint</name><value>false</value></preference><preference><name>enableCommentRatings</name><value>false</value></preference><preference><name>groupId</name><value>1</value></preference><preference><name>enableComments</name><value>false</value></preference></portlet-preferences>")>
	
	insert into PortletPreferences values (${portletPreferences.portletPreferencesId}, ${portletPreferences.ownerId}, ${portletPreferences.ownerType}, ${portletPreferences.plid}, '${portletPreferences.portletId}', '${portletPreferences.preferences}');
	
	<#assign primKey = layout.plid + "_LAYOUT_86" >
	
	${sampleSQLBuilder.insertResourcePermission("86", primKey)}
	
	insert into JournalContentSearch values (${counter.get()}, ${groupId}, ${companyId}, 0, ${layout.layoutId}, '56', '${journalArticle.articleId}');
</#list>