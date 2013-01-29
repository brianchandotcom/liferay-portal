<#setting number_format = "0">

<#assign groupIds = dataFactory.addUserToGroupIds(groupId)>
<#assign roleIds = [dataFactory.administratorRole.roleId, dataFactory.powerUserRole.roleId, dataFactory.userRole.roleId]>

<#if (maxUserCount > 0)>
	<#list 1..maxUserCount as userCount>
		<#assign user = dataFactory.addUser(userCount)>

		${sampleSQLBuilder.insertUser(user, roleIds, groupIds)}
	</#list>
</#if>