<#if (maxJournalArticleCount > 0)>
	<#assign journalArticleResource = dataFactory.addJournalArticleResource(groupId)>

	insert into JournalArticleResource values ('${journalArticleResource.uuid}', ${journalArticleResource.resourcePrimKey}, ${journalArticleResource.groupId}, '${journalArticleResource.articleId}');

	<#assign journalArticle = dataFactory.addJournalArticle(journalArticleResource)>

	insert into JournalArticle values ('${journalArticle.uuid}', ${journalArticle.id}, ${journalArticle.resourcePrimKey}, ${journalArticle.groupId}, ${journalArticle.companyId}, ${journalArticle.userId}, '${journalArticle.userName}', '${journalArticle.createDate?datetime}', '${journalArticle.modifiedDate?datetime}', ${journalArticle.folderId}, ${journalArticle.classNameId}, ${journalArticle.classPK}, '${journalArticle.articleId}', ${journalArticle.version}, '${journalArticle.title}', '${journalArticle.urlTitle}', '${journalArticle.description}', '${journalArticle.content}', '${journalArticle.type}', '${journalArticle.structureId}', '${journalArticle.templateId}', '${journalArticle.layoutUuid}', '${journalArticle.displayDate?datetime}', ${journalArticle.expirationDate!'null'}, ${journalArticle.reviewDate!'null'}, ${journalArticle.indexable?string}, ${journalArticle.smallImage?string}, ${journalArticle.smallImageId}, '${journalArticle.smallImageURL}', ${journalArticle.status}, ${journalArticle.statusByUserId}, '${journalArticle.statusByUserName}', ${journalArticle.statusDate!'null'});

	<@insertResourcePermission _resourceName = "com.liferay.portlet.journal.model.JournalArticle" _resourcePrimkey = journalArticleResource.resourcePrimKey/>

	<#assign mbRootMessage = dataFactory.addMBMessage(journalArticle)>

	<@insertMBDiscussion _entry = journalArticle _maxCommentCount = 1 _mbRootMessage = mbRootMessage/>

	<#list 1..maxJournalArticleCount as journalArticleCount>
		<#assign layout = dataFactory.addLayout(groupId, groupId + "_journal_article_" + journalArticleCount, "", "56,")>

		<#assign publicLayouts = publicLayouts + [layout]>

		<#assign portletPreferencesList = dataFactory.addPortletPreferences(journalArticle, layout.plid)>

		<#list portletPreferencesList as portletPreferences>
			insert into PortletPreferences values (${portletPreferences.portletPreferencesId}, ${portletPreferences.ownerId}, ${portletPreferences.ownerType}, ${portletPreferences.plid}, '${portletPreferences.portletId}', '${portletPreferences.preferences}');
		</#list>

		<@insertResourcePermission _resourceName = "86" _resourcePrimkey = layout.plid + "_LAYOUT_86"/>

		<#assign journalContentSearch = dataFactory.addJournalContentSearch(journalArticle, layout.plid, "56")>

		insert into JournalContentSearch values (${journalContentSearch.contentSearchId}, ${journalContentSearch.groupId}, ${journalContentSearch.companyId}, ${journalContentSearch.privateLayout?string}, ${journalContentSearch.layoutId}, '${journalContentSearch.portletId}', '${journalContentSearch.articleId}');

		${writerLayoutCSV.write(layout.friendlyURL + "\n")}
	</#list>
</#if>