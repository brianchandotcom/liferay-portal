<#list 1..maxJournalArticlePageCount as journalArticlePageCount>
	<#assign portletIdPrefix = "56_INSTANCE_TEST_" + journalArticlePageCount + "_">

	<#assign layout = dataFactory.newLayout(groupId, groupId + "_journal_article_" + journalArticlePageCount, "", dataFactory.getJournalArticleLayoutColumn(portletIdPrefix))>

	${writerLayoutCSV.write(layout.friendlyURL + "\n")}

	<@insertLayout
		_layout = layout
	/>

	<#assign portletPreferencesList = dataFactory.newPortletPreferences(layout.plid)>

	<#list portletPreferencesList as portletPreferences>
		<@insertPortletPreferences
			_portletPreferences = portletPreferences
		/>
	</#list>

	<#list 1..maxJournalArticleCount as journalArticleCount>
		<#assign journalArticleResource = dataFactory.newJournalArticleResource(groupId)>

		insert into JournalArticleResource values ('${journalArticleResource.uuid}', ${journalArticleResource.resourcePrimKey}, ${journalArticleResource.groupId}, '${journalArticleResource.articleId}');

		<#list 1..maxJournalArticleVersionCount as versionCount>
			<#assign journalArticle = dataFactory.newJournalArticle(journalArticleResource, journalArticleCount, versionCount)>

			insert into JournalArticle values ('${journalArticle.uuid}', ${journalArticle.id}, ${journalArticle.resourcePrimKey}, ${journalArticle.groupId}, ${journalArticle.companyId}, ${journalArticle.userId}, '${journalArticle.userName}', '${dataFactory.getDateString(journalArticle.createDate)}', '${dataFactory.getDateString(journalArticle.modifiedDate)}', ${journalArticle.folderId}, ${journalArticle.classNameId}, ${journalArticle.classPK}, '${journalArticle.articleId}', ${journalArticle.version}, '${journalArticle.title}', '${journalArticle.urlTitle}', '${journalArticle.description}', '${journalArticle.content}', '${journalArticle.type}', '${journalArticle.structureId}', '${journalArticle.templateId}', '${journalArticle.layoutUuid}', '${dataFactory.getDateString(journalArticle.displayDate)}', '${dataFactory.getDateString(journalArticle.expirationDate)}', '${dataFactory.getDateString(journalArticle.reviewDate)}', ${journalArticle.indexable?string}, ${journalArticle.smallImage?string}, ${journalArticle.smallImageId}, '${journalArticle.smallImageURL}', ${journalArticle.status}, ${journalArticle.statusByUserId}, '${journalArticle.statusByUserName}', '${dataFactory.getDateString(journalArticle.statusDate)}');

			<@insertSocialActivity
				_entry = journalArticle
			/>

			<#if (versionCount = maxJournalArticleVersionCount) >
				<@insertAssetEntry
					_entry = journalArticle
					_categoryAndTag = true
				/>
			</#if>
		</#list>

		<@insertResourcePermissions
			_entry = journalArticleResource
		/>

		<@insertMBDiscussion
			_classNameId = dataFactory.journalArticleClassNameId
			_classPK = journalArticleResource.resourcePrimKey
			_groupId = groupId
			_maxCommentCount = 0
			_mbRootMessageId = counter.get()
			_mbThreadId = counter.get()
		/>

		<#assign portletPreferences = dataFactory.newPortletPreferences(layout.plid, portletIdPrefix + journalArticleCount, journalArticleResource)>

		<@insertPortletPreferences
			_portletPreferences = portletPreferences
		/>

		<#assign journalContentSearch = dataFactory.newJournalContentSearch(journalArticle, layout.plid)>

		insert into JournalContentSearch values (${journalContentSearch.contentSearchId}, ${journalContentSearch.groupId}, ${journalContentSearch.companyId}, ${journalContentSearch.privateLayout?string}, ${journalContentSearch.layoutId}, '${journalContentSearch.portletId}', '${journalContentSearch.articleId}');
	</#list>
</#list>