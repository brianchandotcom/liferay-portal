<#assign wikiNodeModels = dataFactory.newWikiNodeModels(groupId) />

<#list wikiNodeModels as wikiNodeModel>
	${dataFactory.toInsertSQL(wikiNodeModel)}

	<#assign wikiPageModels = dataFactory.newWikiPageModels(wikiNodeModel) />

	<#list wikiPageModels as wikiPageModel>
		${dataFactory.toInsertSQL(wikiPageModel)}

		${dataFactory.toInsertSQL(dataFactory.newSubscriptionModel(wikiPageModel))}

		${dataFactory.toInsertSQL(dataFactory.newWikiPageResourceModel(wikiPageModel))}

		<@insertAssetEntry
			_categoryAndTag=true
			_entry=wikiPageModel
		/>

		<#assign mbRootMessageId = dataFactory.getCounterNext() />

		<@insertMBDiscussion
			_classNameId=dataFactory.wikiPageClassNameId
			_classPK=wikiPageModel.resourcePrimKey
			_groupId=groupId
			_maxCommentCount=dataFactory.maxWikiPageCommentCount
			_mbRootMessageId=mbRootMessageId
			_mbThreadId=dataFactory.getCounterNext()
		/>

		${csvFileWriter.write("wiki", wikiNodeModel.nodeId + "," + wikiNodeModel.name + "," + wikiPageModel.resourcePrimKey + "," + wikiPageModel.title + "," + mbRootMessageId + "\n")}
	</#list>
</#list>