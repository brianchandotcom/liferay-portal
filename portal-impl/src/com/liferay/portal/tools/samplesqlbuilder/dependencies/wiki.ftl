<#if (maxWikiNodeCount > 0)>
	<#list 1..maxWikiNodeCount as wikiNodeCount>
		<#assign wikiNodeModel = dataFactory.newWikiNodeModel(groupId, wikiNodeCount)>

		insert into WikiNode values ('${wikiNodeModel.uuid}', ${wikiNodeModel.nodeId}, ${wikiNodeModel.groupId}, ${wikiNodeModel.companyId}, ${wikiNodeModel.userId}, '${wikiNodeModel.userName}', '${dataFactory.getDateString(wikiNodeModel.createDate)}', '${dataFactory.getDateString(wikiNodeModel.modifiedDate)}', '${wikiNodeModel.name}', '${wikiNodeModel.description}', '${dataFactory.getDateString(wikiNodeModel.lastPostDate)}', ${wikiNodeModel.status}, ${wikiNodeModel.statusByUserId}, '${wikiNodeModel.statusByUserName}', '${dataFactory.getDateString(wikiNodeModel.statusDate)}');

		<@insertResourcePermissions
			_entry = wikiNodeModel
		/>

		<#if (maxWikiPageCount > 0)>
			<#list 1..maxWikiPageCount as wikiPageCount>
				<#assign wikiPageModel = dataFactory.newWikiPageModel(wikiNodeModel, wikiPageCount)>

				insert into WikiPage values ('${wikiPageModel.uuid}', ${wikiPageModel.pageId}, ${wikiPageModel.resourcePrimKey}, ${wikiPageModel.groupId}, ${wikiPageModel.companyId}, ${wikiPageModel.userId}, '${wikiPageModel.userName}', '${dataFactory.getDateString(wikiPageModel.createDate)}', '${dataFactory.getDateString(wikiPageModel.modifiedDate)}', ${wikiPageModel.nodeId}, '${wikiPageModel.title}', ${wikiPageModel.version}, ${wikiPageModel.minorEdit?string}, '${wikiPageModel.content}', '${wikiPageModel.summary}', '${wikiPageModel.format}', ${wikiPageModel.head?string}, '${wikiPageModel.parentTitle}', '${wikiPageModel.redirectTitle}', ${wikiPageModel.status}, ${wikiPageModel.statusByUserId}, '${wikiPageModel.statusByUserName}', ${wikiPageModel.statusDate!'null'});

				<@insertResourcePermissions
					_entry = wikiPageModel
				/>

				<@insertSubscription
					_entry = wikiPageModel
				/>

				<#assign wikiPageResourceModel = dataFactory.newWikiPageResourceModel(wikiPageModel)>

				insert into WikiPageResource values ('${wikiPageResourceModel.uuid}', ${wikiPageResourceModel.resourcePrimKey}, ${wikiPageResourceModel.nodeId}, '${wikiPageResourceModel.title}');

				<@insertAssetEntry
					_entry = wikiPageModel
					_categoryAndTag = true
				/>

				<#assign mbRootMessageId = counter.get()>
				<#assign mbThreadId = counter.get()>

				<@insertMBDiscussion
					_classNameId = dataFactory.wikiPageClassNameId
					_classPK = wikiPageModel.resourcePrimKey
					_groupId = groupId
					_maxCommentCount = maxWikiPageCommentCount
					_mbRootMessageId = mbRootMessageId
					_mbThreadId = mbThreadId
				/>

				${wikiCSVWriter.write(wikiNodeModel.nodeId + "," + wikiNodeModel.name + "," + wikiPageModel.resourcePrimKey + "," + wikiPageModel.title + "," + mbThreadId + "," + mbRootMessageId + "\n")}
			</#list>
		</#if>
	</#list>
</#if>