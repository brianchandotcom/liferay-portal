<#setting number_format = "0">

<#list 1..maxJournalArticleCount as journalArticleCount>
	<#assign journalArticleResource = dataFactory.newJournalArticleResource(groupId)>

	insert into JournalArticleResource values ('${journalArticleResource.uuid}', ${journalArticleResource.resourcePrimKey}, ${journalArticleResource.groupId}, '${journalArticleResource.articleId}');

	<#list 1..maxJournalArticleVersionCount as versionCount>
		<#assign journalArticle = dataFactory.newJournalArticle(journalArticleResource, journalArticleCount, versionCount)>

		insert into JournalArticle values ('${journalArticle.uuid}', ${journalArticle.id}, ${journalArticle.resourcePrimKey}, ${journalArticle.groupId}, ${journalArticle.companyId}, ${journalArticle.userId}, '${journalArticle.userName}', '${dataFactory.getDateString(journalArticle.createDate)}', '${dataFactory.getDateString(journalArticle.modifiedDate)}', ${journalArticle.folderId}, ${journalArticle.classNameId}, ${journalArticle.classPK}, '${journalArticle.articleId}', ${journalArticle.version}, '${journalArticle.title}', '${journalArticle.urlTitle}', '${journalArticle.description}', '${journalArticle.content}', '${journalArticle.type}', '${journalArticle.structureId}', '${journalArticle.templateId}', '${journalArticle.layoutUuid}', '${dataFactory.getDateString(journalArticle.displayDate)}', '${dataFactory.getDateString(journalArticle.expirationDate)}', '${dataFactory.getDateString(journalArticle.reviewDate)}', ${journalArticle.indexable?string}, ${journalArticle.smallImage?string}, ${journalArticle.smallImageId}, '${journalArticle.smallImageURL}', ${journalArticle.status}, ${journalArticle.statusByUserId}, '${journalArticle.statusByUserName}', '${dataFactory.getDateString(journalArticle.statusDate)}');

		<#assign socialActivity = dataFactory.newSocialActivity(journalArticle)>

		insert into SocialActivity values (${socialActivity.activityId}, ${socialActivity.groupId}, ${socialActivity.companyId}, ${socialActivity.userId}, ${socialActivity.createDate}, ${socialActivity.activitySetId}, ${socialActivity.mirrorActivityId}, ${socialActivity.classNameId}, ${socialActivity.classPK}, ${socialActivity.type}, '${socialActivity.extraData}', ${socialActivity.receiverUserId});

		<#if (versionCount = maxJournalArticleVersionCount) >
			<#assign assetEntry = dataFactory.newAssetEntry(journalArticle)>

			insert into AssetEntry values (${assetEntry.entryId}, ${assetEntry.groupId}, ${assetEntry.companyId}, ${assetEntry.userId}, '${assetEntry.userName}', '${dataFactory.getDateString(assetEntry.createDate)}', '${dataFactory.getDateString(assetEntry.modifiedDate)}', ${assetEntry.classNameId}, ${assetEntry.classPK}, '${assetEntry.classUuid}', ${assetEntry.classTypeId}, ${assetEntry.visible?string}, '${dataFactory.getDateString(assetEntry.startDate)}', '${dataFactory.getDateString(assetEntry.endDate)}', '${dataFactory.getDateString(assetEntry.publishDate)}', '${dataFactory.getDateString(assetEntry.expirationDate)}', '${assetEntry.mimeType}', '${assetEntry.title}', '${assetEntry.description}', '${assetEntry.summary}', '${assetEntry.url}', '${assetEntry.layoutUuid}', ${assetEntry.height}, ${assetEntry.width}, ${assetEntry.priority}, ${assetEntry.viewCount});
		</#if>
	</#list>

	${sampleSQLBuilder.insertResourcePermission("com.liferay.portlet.journal.model.JournalArticle", stringUtil.valueOf(journalArticleResource.resourcePrimKey))}

	${sampleSQLBuilder.insertMBDiscussion(groupId, dataFactory.journalArticleClassNameId, journalArticleResource.resourcePrimKey, counter.get(), counter.get(), 0)}

	<#assign layout = dataFactory.newLayout(groupId, groupId + "_journal_article_" + journalArticleCount, "", "56,")>

	${writerLayoutCSV.write(layout.friendlyURL + "\n")}

	${sampleSQLBuilder.insertLayout(layout)}

	<#assign portletPreferencesList = dataFactory.newPortletPreferences(layout.plid, journalArticleResource)>

	<#list portletPreferencesList as portletPreferences>
		insert into PortletPreferences values (${portletPreferences.portletPreferencesId}, ${portletPreferences.ownerId}, ${portletPreferences.ownerType}, ${portletPreferences.plid}, '${portletPreferences.portletId}', '${portletPreferences.preferences}');

		<#assign primKey = dataFactory.getPortletPermissionPrimaryKey(layout.plid, portletPreferences.portletId)>

		${sampleSQLBuilder.insertResourcePermission(portletPreferences.portletId, primKey)}
	</#list>

	<#assign journalContentSearch = dataFactory.newJournalContentSearch(journalArticle, layout.plid)>

	insert into JournalContentSearch values (${journalContentSearch.contentSearchId}, ${journalContentSearch.groupId}, ${journalContentSearch.companyId}, ${journalContentSearch.privateLayout?string}, ${journalContentSearch.layoutId}, '${journalContentSearch.portletId}', '${journalContentSearch.articleId}');
</#list>