<#assign
	groupIds = dataFactory.getNewUserGroupIds(groupModel.groupId, guestGroupModel)
	roleIds = [dataFactory.administratorRoleModel.roleId, dataFactory.powerUserRoleModel.roleId, dataFactory.userRoleModel.roleId]
/>

<#list dataFactory.newUserModels() as userModel>
	<#assign userGroupModel = dataFactory.newGroupModel(userModel) />

	${csvFileWriter.write("user", virtualHostModel.hostname + "," + userModel.screenName + "\n")}

	<@insertLayout _layoutModel=dataFactory.newLayoutModel(userGroupModel.groupId, "home", "", "") />

	<@insertGroup _groupModel=userGroupModel />

	<@insertUser
		_groupIds=groupIds
		_roleIds=roleIds
		_userModel=userModel
	/>
</#list>