<#setting number_format = "0">

<#assign groupIds = dataFactory.addUserToGroupIds(group.groupId)>
<#assign roleIds = [dataFactory.administratorRole.roleId, dataFactory.powerUserRole.roleId, dataFactory.userRole.roleId]>

<#if (maxUserCount > 0)>
	<#list 1..maxUserCount as userCount>
		<#assign user = dataFactory.addUser(userCount)>

		<#assign userGroup = dataFactory.addGroup(counter.get(), dataFactory.userClassNameId, user.userId, stringUtil.valueOf(user.userId), "/" + user.screenName, false)>

		${sampleSQLBuilder.insertGroup(userGroup, [dataFactory.addLayout(1, "Home", "/home", "", "33,")])}

		${sampleSQLBuilder.insertUser(groupIds, roleIds, user)}
	</#list>
</#if>