<#assign wikiNodeModels = dataFactory.newWikiNodeModels(groupId)>

<#list wikiNodeModels as wikiNodeModel>
	insert into WikiNode values ('${wikiNodeModel.uuid}', ${wikiNodeModel.nodeId}, ${wikiNodeModel.groupId}, ${wikiNodeModel.userId}, '${wikiNodeModel.userName}', '${dataFactory.getDateString(wikiNodeModel.createDate)}', '${dataFactory.getDateString(wikiNodeModel.modifiedDate)}', '${wikiNodeModel.name}', '${wikiNodeModel.description}', '${dataFactory.getDateString(wikiNodeModel.lastPostDate)}', '${dataFactory.getDateString(wikiNodeModel.lastPublishDate)}', ${wikiNodeModel.status}, ${wikiNodeModel.statusByUserId}, '${wikiNodeModel.statusByUserName}', '${dataFactory.getDateString(wikiNodeModel.statusDate)}', ${wikiNodeModel.companyId});

	<@insertResourcePermissions
		_entry = wikiNodeModel
	/>

	<#assign wikiPageModels = dataFactory.newWikiPageModels(wikiNodeModel)>

	<#list wikiPageModels as wikiPageModel>
		insert into WikiPage values ('${wikiPageModel.uuid}', ${wikiPageModel.pageId}, ${wikiPageModel.resourcePrimKey}, ${wikiPageModel.groupId}, ${wikiPageModel.userId}, '${wikiPageModel.userName}', '${dataFactory.getDateString(wikiPageModel.createDate)}', '${dataFactory.getDateString(wikiPageModel.modifiedDate)}', ${wikiPageModel.nodeId}, '${wikiPageModel.title}', ${wikiPageModel.version}, ${wikiPageModel.minorEdit?string}, '${wikiPageModel.content}', '${wikiPageModel.summary}', '${wikiPageModel.format}', ${wikiPageModel.head?string}, '${wikiPageModel.parentTitle}', '${wikiPageModel.redirectTitle}', '${dataFactory.getDateString(wikiPageModel.lastPublishDate)}', ${wikiPageModel.status}, ${wikiPageModel.statusByUserId}, '${wikiPageModel.statusByUserName}', ${wikiPageModel.statusDate!'null'}, ${wikiPageModel.companyId});

		<@insertResourcePermissions
			_entry = wikiPageModel
		/>

		<@insertSubscription
			_entry = wikiPageModel
		/>

		<#assign wikiPageResourceModel = dataFactory.newWikiPageResourceModel(wikiPageModel)>

		insert into WikiPageResource values ('${wikiPageResourceModel.uuid}', ${wikiPageResourceModel.resourcePrimKey}, ${wikiPageResourceModel.groupId}, ${wikiPageResourceModel.nodeId}, '${wikiPageResourceModel.title}', ${wikiPageResourceModel.companyId});

		<@insertAssetEntry
			_entry = wikiPageModel
			_categoryAndTag = true
		/>

		<#assign mbRootMessageId = dataFactory.getCounterNext()>
		<#assign mbThreadId = dataFactory.getCounterNext()>

		<@insertMBDiscussion
			_classNameId = dataFactory.wikiPageClassNameId
			_classPK = wikiPageModel.resourcePrimKey
			_groupId = groupId
			_maxCommentCount = dataFactory.maxWikiPageCommentCount
			_mbRootMessageId = mbRootMessageId
			_mbThreadId = mbThreadId
		/>

		${wikiCSVWriter.write(wikiNodeModel.nodeId + "," + wikiNodeModel.name + "," + wikiPageModel.resourcePrimKey + "," + wikiPageModel.title + "," + mbThreadId + "," + mbRootMessageId + "\n")}
	</#list>
</#list>