<#setting number_format = "0">

<#assign journalArticleResource = dataFactory.addJournalArticleResource(groupId)>

insert into JournalArticleResource values ('${journalArticleResource.uuid}', ${journalArticleResource.resourcePrimKey}, ${journalArticleResource.groupId}, '${journalArticleResource.articleId}');

<#assign journalArticle = dataFactory.addJournalArticle(journalArticleResource)>

insert into JournalArticle values ('${journalArticle.uuid}', ${journalArticle.id}, ${journalArticle.resourcePrimKey}, ${journalArticle.groupId}, ${journalArticle.companyId}, ${journalArticle.userId}, '${journalArticle.userName}', '${journalArticle.createDate?datetime}', '${journalArticle.modifiedDate?datetime}', ${journalArticle.folderId}, ${journalArticle.classNameId}, ${journalArticle.classPK}, '${journalArticle.articleId}', ${journalArticle.version}, '${journalArticle.title}', '${journalArticle.urlTitle}', '${journalArticle.description}', '${journalArticle.content}', '${journalArticle.type}', '${journalArticle.structureId}', '${journalArticle.templateId}', '${journalArticle.layoutUuid}', '${journalArticle.displayDate?datetime}', ${journalArticle.expirationDate!'null'}, ${journalArticle.reviewDate!'null'}, ${journalArticle.indexable?string}, ${journalArticle.smallImage?string}, ${journalArticle.smallImageId}, '${journalArticle.smallImageURL}', ${journalArticle.status}, ${journalArticle.statusByUserId}, '${journalArticle.statusByUserName}', ${journalArticle.statusDate!'null'});

${sampleSQLBuilder.insertResourcePermission("com.liferay.portlet.journal.model.JournalArticle", stringUtil.valueOf(journalArticleResource.resourcePrimKey))}

<#assign mbMessage = dataFactory.addMBMessage(journalArticle)>

${sampleSQLBuilder.insertMBMessage(mbMessage)}

<#assign assetEntry = dataFactory.addAssetEntry(journalArticle)>

insert into AssetEntry values (${assetEntry.entryId}, ${assetEntry.groupId}, ${assetEntry.companyId}, ${assetEntry.userId}, '${assetEntry.userName}', '${assetEntry.createDate?datetime}', '${assetEntry.modifiedDate?datetime}', ${assetEntry.classNameId}, ${assetEntry.classPK}, '${assetEntry.classUuid}', ${assetEntry.classTypeId}, ${assetEntry.visible?string}, ${assetEntry.startDate!'null'}, ${assetEntry.endDate!'null'}, ${assetEntry.publishDate!'null'}, ${assetEntry.expirationDate!'null'}, '${assetEntry.mimeType}', '${assetEntry.title}', '${assetEntry.description}', '${assetEntry.summary}', '${assetEntry.url}', '${assetEntry.layoutUuid}', ${assetEntry.height}, ${assetEntry.width}, ${assetEntry.priority}, ${assetEntry.viewCount});

<#assign mbThread = dataFactory.addMBThread(mbMessage, 1)>

insert into MBThread values (${mbThread.threadId}, ${mbThread.groupId}, ${mbThread.companyId}, ${mbThread.categoryId}, ${mbThread.rootMessageId}, ${mbThread.rootMessageUserId}, ${mbThread.messageCount}, ${mbThread.viewCount}, ${mbThread.lastPostByUserId}, '${mbThread.lastPostDate?datetime}', ${mbThread.priority}, ${mbThread.question?string}, ${mbThread.status}, ${mbThread.statusByUserId}, '${mbThread.statusByUserName}', ${mbThread.statusDate!'null'});

<#assign mbDiscussion = dataFactory.addMBDiscussion(journalArticle, mbThread.threadId)>

insert into MBDiscussion values (${mbDiscussion.discussionId}, ${mbDiscussion.classNameId}, ${mbDiscussion.classPK}, ${mbDiscussion.threadId});

<#list 1..maxJournalArticleCount as journalArticleCount>
	<#assign layout = dataFactory.addLayout(groupId, groupId + "_journal_article_" + journalArticleCount, "", "56,")>

	<#assign publicLayouts = publicLayouts + [layout]>

	${writerLayoutCSV.write(layout.friendlyURL + "\n")}

	<#assign portletPreferencesList = dataFactory.addPortletPreferences(journalArticle, layout.plid)>

	<#list portletPreferencesList as portletPreferences>
		insert into PortletPreferences values (${portletPreferences.portletPreferencesId}, ${portletPreferences.ownerId}, ${portletPreferences.ownerType}, ${portletPreferences.plid}, '${portletPreferences.portletId}', '${portletPreferences.preferences}');
	</#list>

	${sampleSQLBuilder.insertResourcePermission("86", layout.plid + "_LAYOUT_86")}

	<#assign journalContentSearch = dataFactory.addJournalContentSearch(journalArticle, layout.plid, "56")>

	insert into JournalContentSearch values (${journalContentSearch.contentSearchId}, ${journalContentSearch.groupId}, ${journalContentSearch.companyId}, ${journalContentSearch.privateLayout?string}, ${journalContentSearch.layoutId}, '${journalContentSearch.portletId}', '${journalContentSearch.articleId}');
</#list>