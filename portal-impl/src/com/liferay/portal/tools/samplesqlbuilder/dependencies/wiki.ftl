<#if (maxWikiNodeCount > 0)>
	<#list 1..maxWikiNodeCount as wikiNodeCount>
		<#assign wikiNode = dataFactory.addWikiNode(groupId, firstUserId, "Test Node " + wikiNodeCount, "This is a test node " + wikiNodeCount + ".")>

		insert into WikiNode values ('${portalUUIDUtil.generate()}', ${wikiNode.nodeId}, ${wikiNode.groupId}, ${companyId}, ${wikiNode.userId}, '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '${wikiNode.name}', '${wikiNode.description}', CURRENT_TIMESTAMP, ${wikiNode.status}, ${wikiNode.statusByUserId}, '${wikiNode.statusByUserName}', CURRENT_TIMESTAMP);

		<#if (maxWikiPageCount > 0)>
			<#list 1..maxWikiPageCount as wikiPageCount>
				<#assign wikiPage = dataFactory.addWikiPage(groupId, firstUserId, wikiNode.nodeId, "Test Page " + wikiPageCount, 1.0, "This is a test page " + wikiPageCount + ".", true)>

				${sampleSQLBuilder.insertWikiPage(wikiNode, wikiPage)}

				<#assign mbThreadId = counter.get()>
				<#assign mbRootMessageId = counter.get()>

				${sampleSQLBuilder.insertMBDiscussion(groupId, firstUserId, mbRootMessageId, mbThreadId, dataFactory.wikiPageClassNameId, wikiPage.resourcePrimKey, maxWikiPageCommentCount)}

				${writerWikiCSV.write(wikiNode.nodeId + "," + wikiNode.name + "," + wikiPage.resourcePrimKey + "," + wikiPage.title + "," + mbThreadId + "," + mbRootMessageId + "\n")}
			</#list>
		</#if>
	</#list>
</#if>