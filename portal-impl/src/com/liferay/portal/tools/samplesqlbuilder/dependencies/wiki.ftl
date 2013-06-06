<#if (maxWikiNodeCount > 0)>
	<#list 1..maxWikiNodeCount as wikiNodeCount>
		<#assign wikiNode = dataFactory.newWikiNode(groupId, wikiNodeCount)>

		insert into WikiNode values ('${wikiNode.uuid}', ${wikiNode.nodeId}, ${wikiNode.groupId}, ${wikiNode.companyId}, ${wikiNode.userId}, '${wikiNode.userName}', '${dataFactory.getDateString(wikiNode.createDate)}', '${dataFactory.getDateString(wikiNode.modifiedDate)}', '${wikiNode.name}', '${wikiNode.description}', '${dataFactory.getDateString(wikiNode.lastPostDate)}', ${wikiNode.status}, ${wikiNode.statusByUserId}, '${wikiNode.statusByUserName}', '${dataFactory.getDateString(wikiNode.statusDate)}');

		<@insertResourcePermissions
			_entry = wikiNode
		/>

		<#if (maxWikiPageCount > 0)>
			<#list 1..maxWikiPageCount as wikiPageCount>
				<#assign wikiPage = dataFactory.newWikiPage(wikiNode, wikiPageCount)>

				insert into WikiPage values ('${wikiPage.uuid}', ${wikiPage.pageId}, ${wikiPage.resourcePrimKey}, ${wikiPage.groupId}, ${wikiPage.companyId}, ${wikiPage.userId}, '${wikiPage.userName}', '${dataFactory.getDateString(wikiPage.createDate)}', '${dataFactory.getDateString(wikiPage.modifiedDate)}', ${wikiPage.nodeId}, '${wikiPage.title}', ${wikiPage.version}, ${wikiPage.minorEdit?string}, '${wikiPage.content}', '${wikiPage.summary}', '${wikiPage.format}', ${wikiPage.head?string}, '${wikiPage.parentTitle}', '${wikiPage.redirectTitle}', ${wikiPage.status}, ${wikiPage.statusByUserId}, '${wikiPage.statusByUserName}', ${wikiPage.statusDate!'null'});

				<@insertResourcePermissions
					_entry = wikiPage
				/>

				<@insertSubscription
					_entry = wikiPage
				/>

				<#assign wikiPageResource = dataFactory.newWikiPageResource(wikiPage)>

				insert into WikiPageResource values ('${wikiPageResource.uuid}', ${wikiPageResource.resourcePrimKey}, ${wikiPageResource.nodeId}, '${wikiPageResource.title}');

				<@insertAssetEntry
					_entry = wikiPage
					_categoryAndTag = true
				/>

				<#assign mbRootMessageId = counter.get()>
				<#assign mbThreadId = counter.get()>

				<@insertMBDiscussion
					_classNameId = dataFactory.wikiPageClassNameId
					_classPK = wikiPage.resourcePrimKey
					_groupId = groupId
					_maxCommentCount = maxWikiPageCommentCount
					_mbRootMessageId = mbRootMessageId
					_mbThreadId = mbThreadId
				/>

				${writerWikiCSV.write(wikiNode.nodeId + "," + wikiNode.name + "," + wikiPage.resourcePrimKey + "," + wikiPage.title + "," + mbThreadId + "," + mbRootMessageId + "\n")}
			</#list>
		</#if>
	</#list>
</#if>