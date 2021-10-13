<#assign blogsEntryModels = dataFactory.newBlogsEntryModels(groupId) />

<#list blogsEntryModels as blogsEntryModel>
	${dataFactory.toInsertSQL(blogsEntryModel)}

	<#assign friendlyURLEntryModel = dataFactory.newFriendlyURLEntryModel(blogsEntryModel.groupId, dataFactory.blogsEntryClassNameId, blogsEntryModel.entryId) />

	${dataFactory.toInsertSQL(friendlyURLEntryModel)}

	${dataFactory.toInsertSQL(dataFactory.newFriendlyURLEntryLocalizationModel(friendlyURLEntryModel, blogsEntryModel.urlTitle))}

	${dataFactory.toInsertSQL(dataFactory.newFriendlyURLEntryMapping(friendlyURLEntryModel))}

	<@insertAssetEntry
		_categoryAndTag=true
		_entry=blogsEntryModel
	/>

	<#assign mbRootMessageId = dataFactory.getCounterNext() />

	<@insertMBDiscussion
		_classNameId=dataFactory.blogsEntryClassNameId
		_classPK=blogsEntryModel.entryId
		_groupId=groupId
		_maxCommentCount=dataFactory.maxBlogsEntryCommentCount
		_mbRootMessageId=mbRootMessageId
		_mbThreadId=dataFactory.getCounterNext()
	/>

	${dataFactory.toInsertSQL(dataFactory.newSubscriptionModel(blogsEntryModel))}

	${dataFactory.toInsertSQL(dataFactory.newSocialActivityModel(blogsEntryModel))}

	${csvFileWriter.write("blog", blogsEntryModel.entryId + "," + blogsEntryModel.urlTitle + "," + mbRootMessageId + "\n")}
</#list>