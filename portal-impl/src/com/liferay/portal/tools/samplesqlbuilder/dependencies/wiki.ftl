<#if (maxWikiNodeCount > 0)>
	<#list 1..maxWikiNodeCount as wikiNodeCount>
		<#assign wikiNode = dataFactory.newWikiNode(groupId, wikiNodeCount)>

		insert into WikiNode values ('${wikiNode.uuid}', ${wikiNode.nodeId}, ${wikiNode.groupId}, ${wikiNode.companyId}, ${wikiNode.userId}, '${wikiNode.userName}', '${dataFactory.getDateString(wikiNode.createDate)}', '${dataFactory.getDateString(wikiNode.modifiedDate)}', '${wikiNode.name}', '${wikiNode.description}', '${dataFactory.getDateString(wikiNode.lastPostDate)}', ${wikiNode.status}, ${wikiNode.statusByUserId}, '${wikiNode.statusByUserName}', '${dataFactory.getDateString(wikiNode.statusDate)}');

		<#if (maxWikiPageCount > 0)>
			<#list 1..maxWikiPageCount as wikiPageCount>
				<#assign wikiPage = dataFactory.newWikiPage(wikiNode, wikiPageCount)>

				${sampleSQLBuilder.insertWikiPage(wikiPage)}

				<#assign mbRootMessageId = counter.get()>
				<#assign mbThreadId = counter.get()>

				${sampleSQLBuilder.insertMBDiscussion(groupId, dataFactory.wikiPageClassNameId, wikiPage.resourcePrimKey, mbThreadId, mbRootMessageId, maxWikiPageCommentCount)}

				${writerWikiCSV.write(wikiNode.nodeId + "," + wikiNode.name + "," + wikiPage.resourcePrimKey + "," + wikiPage.title + "," + mbThreadId + "," + mbRootMessageId + "\n")}
			</#list>
		</#if>
	</#list>
</#if>