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

		<#assign
			mbRootMessageId = dataFactory.getCounterNext()
			mbThreadId = dataFactory.getCounterNext()
		/>

		<@insertMBDiscussion
			_classNameId=dataFactory.wikiPageClassNameId
			_classPK=wikiPageModel.resourcePrimKey
			_groupId=groupId
			_maxCommentCount=dataFactory.maxWikiPageCommentCount
			_mbRootMessageId=mbRootMessageId
			_mbThreadId=mbThreadId
		/>

		${dataFactory.getCSVWriter("wiki").write(wikiNodeModel.nodeId + "," + wikiNodeModel.name + "," + wikiPageModel.resourcePrimKey + "," + wikiPageModel.title + "," + mbThreadId + "," + mbRootMessageId + "\n")}
	</#list>
</#list>