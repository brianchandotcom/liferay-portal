<#setting number_format = "0">

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, reminderQueryQuestion, reminderQueryAnswer, screenName, emailAddress, greeting, firstName, lastName, loginDate, lastLoginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('${portalUUIDUtil.generate()}', ${user.userId}, ${companyId}, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, <#if user.defaultUser>TRUE<#else>FALSE</#if>, ${contact.contactId}, 'test', FALSE, FALSE, 'What is your screen name?', '${user.screenName}', '${user.screenName}', '${user.emailAddress}', 'Welcome ${contact.fullName}!', '${contact.firstName}', '${contact.lastName}', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0, TRUE, '${user.status}');
insert into Contact_ values (${contact.contactId}, ${companyId}, ${user.userId}, '${contact.fullName}', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ${dataFactory.userClassNameId}, ${user.userId}, ${contact.accountId}, 0, '${user.emailAddress}', '${contact.firstName}', '', '${contact.lastName}', 0, 0, TRUE, '01/01/1970', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');

<#if roleIds??>
	<#list roleIds as roleId>
		insert into Users_Roles values (${user.userId}, ${roleId});
	</#list>
</#if>

<#if groupIds??>
	<#list groupIds as groupId>
		insert into Users_Groups values (${user.userId}, ${groupId});
	</#list>
</#if>