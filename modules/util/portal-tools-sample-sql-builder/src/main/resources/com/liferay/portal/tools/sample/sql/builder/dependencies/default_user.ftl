<#-- Default user -->

<@insertUser _userModel=dataFactory.newDefaultUserModel() />

<#-- Guest user -->

<#assign guestUserModel = dataFactory.newGuestUserModel() />

<@insertGroup _groupModel=dataFactory.newGroupModel(guestUserModel) />

<#assign
	groupIds = [guestGroupModel.groupId]
	roleIds = [dataFactory.administratorRoleModel.roleId]
/>

<@insertUser
	_groupIds=groupIds
	_roleIds=roleIds
	_userModel=guestUserModel
/>

<#-- Sample user -->

<#assign
	sampleUserModel = dataFactory.newSampleUserModel()

	userGroupModel = dataFactory.newGroupModel(sampleUserModel)

	layoutModel = dataFactory.newLayoutModel(userGroupModel.groupId, "home", "", "")
/>

<@insertLayout _layoutModel=layoutModel />

<@insertGroup _groupModel=userGroupModel />

<#assign
	groupIds = dataFactory.getSequence(dataFactory.maxGroupCount)
	roleIds = [dataFactory.administratorRoleModel.roleId, dataFactory.powerUserRoleModel.roleId, dataFactory.userRoleModel.roleId]
/>

<@insertUser
	_groupIds=groupIds
	_roleIds=roleIds
	_userModel=sampleUserModel
/>