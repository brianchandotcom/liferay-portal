<#setting number_format = "0">

<#assign groupIds = dataFactory.addUserToGroupIds(group.groupId)>
<#assign roleIds = [dataFactory.administratorRole.roleId, dataFactory.powerUserRole.roleId, dataFactory.userRole.roleId]>

<#if (maxUserCount > 0)>
	<#list 1..maxUserCount as userCount>
		<#assign userName = dataFactory.nextUserName(userCount - 1)>

		<#assign contact = dataFactory.addContact(userName[0], userName[1])>
		<#assign user = dataFactory.addUser(false, "test" + userScreenNameCounter.get())>

		<#assign userGroup = dataFactory.addGroup(counter.get(), dataFactory.userClassNameId, user.userId, stringUtil.valueOf(user.userId), "/" + user.screenName, false)>

		${sampleSQLBuilder.insertGroup(userGroup, [dataFactory.addLayout(1, "Home", "/home", "", "33,")])}

		${sampleSQLBuilder.insertUser(contact, groupIds, roleIds, user)}

		<#assign blogsStatsUser = dataFactory.addBlogsStatsUser(groupId, user.userId)>

		insert into BlogsStatsUser (statsUserId, groupId, companyId, userId) values (${counter.get()}, ${blogsStatsUser.groupId}, ${companyId}, ${blogsStatsUser.userId});

		<#assign mbStatsUser = dataFactory.addMBStatsUser(groupId, user.userId)>

		insert into MBStatsUser (statsUserId, groupId, userId) values (${counter.get()}, ${mbStatsUser.groupId}, ${mbStatsUser.userId});
	</#list>
</#if>