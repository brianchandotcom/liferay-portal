<#if (maxWikiNodeCount > 0)>
	<#list 1..maxWikiNodeCount as wikiNodeCount>
		<#assign wikiNode = dataFactory.addWikiNode(groupId, sampleUserId, "Test Node " + wikiNodeCount, "This is a test node " + wikiNodeCount + ".")>

		insert into WikiNode values ('${portalUUIDUtil.generate()}', ${wikiNode.nodeId}, ${wikiNode.groupId}, ${companyId}, ${wikiNode.userId}, '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '${wikiNode.name}', '${wikiNode.description}', CURRENT_TIMESTAMP, ${wikiNode.status}, ${wikiNode.statusByUserId}, '${wikiNode.statusByUserName}', CURRENT_TIMESTAMP);

		<#if (maxWikiPageCount > 0)>
			<#list 1..maxWikiPageCount as wikiPageCount>
				<#assign wikiPage = dataFactory.addWikiPage(groupId, sampleUserId, wikiNode.nodeId, "Test Page " + wikiPageCount, 1.0, "This is a test page " + wikiPageCount + ".", true)>

				${sampleSQLBuilder.insertWikiPage(wikiNode, wikiPage)}

				<#assign mbRootMessageId = counter.get()>
				<#assign mbThreadId = counter.get()>

				${sampleSQLBuilder.insertMBDiscussion(groupId, sampleUserId, dataFactory.wikiPageClassNameId, wikiPage.resourcePrimKey, mbThreadId, mbRootMessageId, maxWikiPageCommentCount)}

				${writerWikiCSV.write(wikiNode.nodeId + "," + wikiNode.name + "," + wikiPage.resourcePrimKey + "," + wikiPage.title + "," + mbThreadId + "," + mbRootMessageId + "\n")}
			</#list>
		</#if>
	</#list>
</#if>