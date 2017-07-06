<#-- Default user -->

<@insertUser _userModel=dataFactory.defaultUserModel />

<#-- Guest user -->

<#assign userModel = dataFactory.guestUserModel />

<@insertGroup
	_groupModel=dataFactory.newGroupModel(userModel)
	_publicPageCount=0
/>

<#assign
	groupIds = [dataFactory.guestGroupModel.groupId]
	roleIds = [dataFactory.administratorRoleModel.roleId]
/>

<@insertUser
	_groupIds=groupIds
	_roleIds=roleIds
	_userModel=userModel
/>

<#-- Sample user -->

<#assign
	userModel = dataFactory.sampleUserModel

	sampleUserId = userModel.userId

	userGroupModel = dataFactory.newGroupModel(userModel)

	layoutModel = dataFactory.newLayoutModel(userGroupModel.groupId, "home", "", "")
/>

<@insertLayout _layoutModel=layoutModel />

<@insertGroup
	_groupModel=userGroupModel
	_publicPageCount=1
/>

<#assign
	groupIds = dataFactory.getSequence(dataFactory.maxGroupCount)
	roleIds = [dataFactory.administratorRoleModel.roleId, dataFactory.powerUserRoleModel.roleId, dataFactory.userRoleModel.roleId]
/>

<@insertUser
	_groupIds=groupIds
	_roleIds=roleIds
	_userModel=userModel
/>

<#list groupIds as groupId>
	${dataFactory.toInsertSQL(dataFactory.newBlogsStatsUserModel(groupId))}

	${dataFactory.toInsertSQL(dataFactory.newMBStatsUserModel(groupId))}
</#list>