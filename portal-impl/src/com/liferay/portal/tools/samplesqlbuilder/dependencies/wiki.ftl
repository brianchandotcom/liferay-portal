<#if (maxWikiNodeCount > 0)>
	<#list 1..maxWikiNodeCount as wikiNodeCount>
		<#assign wikiNode = dataFactory.addWikiNode(groupId, wikiNodeCount)>

		insert into WikiNode values ('${wikiNode.uuid}', ${wikiNode.nodeId}, ${wikiNode.groupId}, ${wikiNode.companyId}, ${wikiNode.userId}, '${wikiNode.userName}', '${wikiNode.createDate?datetime}', '${wikiNode.modifiedDate?datetime}', '${wikiNode.name}', '${wikiNode.description}', '${wikiNode.lastPostDate?datetime}', ${wikiNode.status}, ${wikiNode.statusByUserId}, '${wikiNode.statusByUserName}', ${wikiNode.statusDate!'null'});

		<#if (maxWikiPageCount > 0)>
			<#list 1..maxWikiPageCount as wikiPageCount>
				<#assign wikiPage = dataFactory.addWikiPage(wikiNode, wikiPageCount)>

				${sampleSQLBuilder.insertWikiPage(wikiPage)}

				<#assign mbRootMessage = dataFactory.addMBMessage(wikiPage)>

				${sampleSQLBuilder.insertMBMessage(mbRootMessage)}

				<#assign mbThread = dataFactory.addMBThread(mbRootMessage, maxWikiPageCount)>

				insert into MBThread values (${mbThread.threadId}, ${mbThread.groupId}, ${mbThread.companyId}, ${mbThread.categoryId}, ${mbThread.rootMessageId}, ${mbThread.rootMessageUserId}, ${mbThread.messageCount}, ${mbThread.viewCount}, ${mbThread.lastPostByUserId}, '${mbThread.lastPostDate?datetime}', ${mbThread.priority}, ${mbThread.question?string}, ${mbThread.status}, ${mbThread.statusByUserId}, '${mbThread.statusByUserName}', ${mbThread.statusDate!'null'});

				<#if (maxWikiPageCommentCount > 0)>
					<#list 1..maxWikiPageCommentCount as wikiPageComment>
						<#assign mbMessage = dataFactory.addMBMessage(wikiPage, mbRootMessage, wikiPageComment)>

						${sampleSQLBuilder.insertMBMessage(mbMessage)}
					</#list>
				</#if>

				<#assign mbDiscussion = dataFactory.addMBDiscussion(wikiPage, mbThread.threadId)>

				insert into MBDiscussion values (${mbDiscussion.discussionId}, ${mbDiscussion.classNameId}, ${mbDiscussion.classPK}, ${mbDiscussion.threadId});

				${writerWikiCSV.write(wikiNode.nodeId + "," + wikiNode.name + "," + wikiPage.resourcePrimKey + "," + wikiPage.title + "," + mbMessage.threadId + "," + mbMessage.messageId + "\n")}
			</#list>
		</#if>
	</#list>
</#if>