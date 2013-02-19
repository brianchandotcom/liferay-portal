<#if (maxWikiNodeCount > 0)>
	<#list 1..maxWikiNodeCount as wikiNodeCount>
		<#assign wikiNode = dataFactory.addWikiNode(groupId, firstUserId, "Test Node " + wikiNodeCount, "This is a test node " + wikiNodeCount + ".")>

		insert into WikiNode values ('${portalUUIDUtil.generate()}', ${wikiNode.nodeId}, ${wikiNode.groupId}, ${companyId}, ${wikiNode.userId}, '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '${wikiNode.name}', '${wikiNode.description}', CURRENT_TIMESTAMP, ${wikiNode.status}, ${wikiNode.statusByUserId}, '${wikiNode.statusByUserName}', CURRENT_TIMESTAMP);

		<#if (maxWikiPageCount > 0)>
			<#list 1..maxWikiPageCount as wikiPageCount>
				<#assign wikiPage = dataFactory.addWikiPage(groupId, firstUserId, wikiNode.nodeId, "Test Page " + wikiPageCount, 1.0, "This is a test page " + wikiPageCount + ".", true)>

				${sampleSQLBuilder.insertWikiPage(wikiNode, wikiPage)}

				<#assign mbCategoryId = -1>
				<#assign mbThreadId = counter.get()>
				<#assign mbRootMessageId = counter.get()>

				<#assign mbRootMessage = dataFactory.addMBMessage(mbRootMessageId, groupId, firstUserId, dataFactory.wikiPageClassNameId, wikiPage.resourcePrimKey, mbCategoryId, mbThreadId, mbRootMessageId, 0, stringUtil.valueOf(wikiPage.resourcePrimKey), stringUtil.valueOf(wikiPage.resourcePrimKey))>

				${sampleSQLBuilder.insertMBMessage(mbRootMessage)}

				<#assign mbThread = dataFactory.addMBThread(mbThreadId, groupId, companyId, mbCategoryId, mbRootMessage.messageId, maxWikiPageCommentCount, firstUserId)>

				insert into MBThread values (${mbThread.threadId}, ${mbThread.groupId}, ${mbThread.companyId}, ${mbThread.categoryId}, ${mbThread.rootMessageId}, ${mbThread.rootMessageUserId}, ${mbThread.messageCount}, 0, ${mbThread.lastPostByUserId}, CURRENT_TIMESTAMP, 0, FALSE, 0, ${mbThread.lastPostByUserId}, '', CURRENT_TIMESTAMP);

				<#if (maxWikiPageCommentCount > 0)>
					<#list 1..maxWikiPageCommentCount as wikiPageComment>
						<#assign mbMessage = dataFactory.addMBMessage(counter.get(), groupId, firstUserId, dataFactory.wikiPageClassNameId, wikiPage.resourcePrimKey, mbCategoryId, mbThreadId, mbRootMessage.messageId, mbRootMessage.messageId, "N/A", "This is a test comment " + wikiPageComment + ".")>

						${sampleSQLBuilder.insertMBMessage(mbMessage)}
					</#list>
				</#if>

				<#assign mbDiscussion = dataFactory.addMBDiscussion(dataFactory.wikiPageClassNameId, wikiPage.resourcePrimKey, mbThreadId)>

				insert into MBDiscussion values (${mbDiscussion.discussionId}, ${mbDiscussion.classNameId}, ${mbDiscussion.classPK}, ${mbDiscussion.threadId});

				${writerWikiCSV.write(wikiNode.nodeId + "," + wikiNode.name + "," + wikiPage.resourcePrimKey + "," + wikiPage.title + "," + mbThreadId + "," + mbRootMessageId + "\n")}
			</#list>
		</#if>
	</#list>
</#if>