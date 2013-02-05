<#if (maxWikiNodeCount > 0)>
	<#list 1..maxWikiNodeCount as wikiNodeCount>
		<#assign wikiNode = dataFactory.addWikiNode(groupId, wikiNodeCount)>

		insert into WikiNode values ('${wikiNode.uuid}', ${wikiNode.nodeId}, ${wikiNode.groupId}, ${wikiNode.companyId}, ${wikiNode.userId}, '${wikiNode.userName}', '${wikiNode.createDate?datetime}', '${wikiNode.modifiedDate?datetime}', '${wikiNode.name}', '${wikiNode.description}', '${wikiNode.lastPostDate?datetime}', ${wikiNode.status}, ${wikiNode.statusByUserId}, '${wikiNode.statusByUserName}', ${wikiNode.statusDate!'null'});

		<#if (maxWikiPageCount > 0)>
			<#list 1..maxWikiPageCount as wikiPageCount>
				<#assign wikiPage = dataFactory.addWikiPage(wikiNode, wikiPageCount)>

				<@insertWikiPage _wikiPage = wikiPage/>

				<#assign mbRootMessage = dataFactory.addMBMessage(wikiPage)>

				<@insertMBDiscussion _entry = wikiPage _maxCommentCount = maxWikiPageCommentCount _mbRootMessage = mbRootMessage/>

				${writerWikiCSV.write(wikiNode.nodeId + "," + wikiNode.name + "," + wikiPage.resourcePrimKey + "," + wikiPage.title + "," + mbRootMessage.threadId + "," + mbRootMessage.messageId + "\n")}
			</#list>
		</#if>
	</#list>
</#if>