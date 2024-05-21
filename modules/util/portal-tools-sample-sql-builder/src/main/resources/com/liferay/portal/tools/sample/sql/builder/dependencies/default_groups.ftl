<#assign
	globalGroupModel = dataFactory.newGlobalGroupModel()
	guestGroupModel = dataFactory.newGuestGroupModel()
/>

<#include "guest_user.ftl">

<@insertGroup _groupModel = globalGroupModel />

<@insertGroup _groupModel = guestGroupModel />

<@insertGroup _groupModel = dataFactory.newUserPersonalSiteGroupModel() />