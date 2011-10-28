<#if (maxWikiNodeCount > 0)>
	<#list 1..maxWikiNodeCount as wikiNodeCount>
		<#assign wikiNode = dataFactory.addWikiNode(groupId, firstUserId, "Test Node " + wikiNodeCount, "This is a test node " + wikiNodeCount + ".")>

		insert into WikiNode values ('${portalUUIDUtil.generate()}', ${wikiNode.nodeId}, ${wikiNode.groupId}, ${companyId}, ${wikiNode.userId}, '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '${wikiNode.name}', '${wikiNode.description}', CURRENT_TIMESTAMP);

		${sampleSQLBuilder.insertSecurity("com.liferay.portlet.wiki.model.WikiNode", wikiNode.nodeId)}

		<#if (maxWikiPageCount > 0)>
			<#list 1..maxWikiPageCount as wikiPageCount>
				<#assign wikiPageCounterIncrement = wikiPageCounter.increment()>

				<#assign wikiPage = dataFactory.addWikiPage(groupId, firstUserId, wikiNode.nodeId, "Test Page " + wikiPageCount, 1.0, "This is a test page " + wikiPageCount + ".", true)>

				${sampleSQLBuilder.insertWikiPage(wikiNode, wikiPage)}

				<#assign mbCompanyId = 0>
				<#assign mbGroupId = 0>
				<#assign mbUserId = wikiPage.userId>
				<#assign mbCategoryId = 0>
				<#assign mbThreadId = counter.get()>

				<#assign mbRootMessage = dataFactory.addMBMessage(counter.get(), mbGroupId, mbUserId, dataFactory.wikiPageClassName.classNameId, wikiPage.resourcePrimKey, mbCategoryId, mbThreadId, 0, 0, stringUtil.valueOf(wikiPage.resourcePrimKey), stringUtil.valueOf(wikiPage.resourcePrimKey))>

				${sampleSQLBuilder.insertMBMessage(mbRootMessage)}

				<#assign mbThread = dataFactory.addMBThread(mbThreadId, mbGroupId, companyId, mbCategoryId, mbRootMessage.messageId, maxWikiPageCommentCount, mbUserId)>

				insert into MBThread values (${mbThread.threadId}, ${mbThread.groupId}, ${mbThread.companyId}, ${mbThread.categoryId}, ${mbThread.rootMessageId}, ${mbThread.rootMessageUserId}, ${mbThread.messageCount}, 0, ${mbThread.lastPostByUserId}, CURRENT_TIMESTAMP, 0, FALSE, 0, ${mbThread.lastPostByUserId}, '', CURRENT_TIMESTAMP);

				<#if (maxWikiPageCommentCount > 0)>
					<#list 1..maxWikiPageCommentCount as wikiPageComment>
						<#assign mbMessage = dataFactory.addMBMessage(counter.get(), mbGroupId, mbUserId, dataFactory.wikiPageClassName.classNameId, wikiPage.resourcePrimKey, mbCategoryId, mbThreadId, mbRootMessage.messageId, mbRootMessage.messageId, "N/A", "This is a test comment " + wikiPageComment + ".")>

						${sampleSQLBuilder.insertMBMessage(mbMessage)}
					</#list>
				</#if>

				<#assign mbDiscussion = dataFactory.addMBDiscussion(dataFactory.wikiPageClassName.classNameId, wikiPage.resourcePrimKey, mbThreadId)>

				insert into MBDiscussion values (${mbDiscussion.discussionId}, ${mbDiscussion.classNameId}, ${mbDiscussion.classPK}, ${mbDiscussion.threadId});

				${writerWikiCSV.write(wikiNode.nodeId + "," + wikiNode.name + "," + wikiPage.resourcePrimKey + "," + wikiPage.title + "," + mbMessage.threadId + "," + mbMessage.messageId + ",")}

				<#if (wikiPageCounter.value < (maxGroupCount * maxWikiNodeCount * maxWikiPageCount))>
					${writerWikiCSV.write("\n")}
				</#if>
			</#list>
		</#if>
	</#list>
</#if>