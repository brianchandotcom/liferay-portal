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

	<#assign preferences = "<portlet-preferences><preference><name>articleId</name><value>" + journalArticleResource.articleId + "</value></preference><preference><name>enableCommentRatings</name><value>false</value></preference><preference><name>enableComments</name><value>false</value></preference><preference><name>enablePrint</name><value>false</value></preference><preference><name>enableRatings</name><value>false</value></preference><preference><name>enableRelatedAssets</name><value>true</value></preference><preference><name>enableViewCountIncrement</name><value>false</value></preference><preference><name>extensions</name><value>NULL_VALUE</value></preference><preference><name>groupId</name><value>" + groupId + "</value></preference><preference><name>showAvailableLocales</name><value>false</value></preference><preference><name>templateId</name><value></value></preference></portlet-preferences>">

	<#assign portletPreferences = dataFactory.newPortletPreferences(0, layout.plid, "86", preferences)>

	insert into PortletPreferences values (${portletPreferences.portletPreferencesId}, ${portletPreferences.ownerId}, ${portletPreferences.ownerType}, ${portletPreferences.plid}, '${portletPreferences.portletId}', '${portletPreferences.preferences}');

	<#assign portletPreferences = dataFactory.newPortletPreferences(0, layout.plid, "56", preferences)>

	insert into PortletPreferences values (${portletPreferences.portletPreferencesId}, ${portletPreferences.ownerId}, ${portletPreferences.ownerType}, ${portletPreferences.plid}, '${portletPreferences.portletId}', '${portletPreferences.preferences}');

	${sampleSQLBuilder.insertResourcePermission("86", layout.plid + "_LAYOUT_86")}

	insert into JournalContentSearch values (${counter.get()}, ${groupId}, ${companyId}, 0, ${layout.layoutId}, '56', '${journalArticleResource.articleId}');
</#list>