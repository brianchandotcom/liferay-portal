<#setting number_format = "0">

insert into User_ values ('${user.uuid}', ${user.userId}, ${user.companyId}, '${user.createDate?datetime}', '${user.modifiedDate?datetime}', ${user.defaultUser?string}, ${user.contactId}, '${user.password}', ${user.passwordEncrypted?string}, ${user.passwordReset?string}, ${user.passwordModifiedDate!'null'}, '${user.digest}', '${user.reminderQueryQuestion}', '${user.reminderQueryAnswer}', ${user.graceLoginCount}, '${user.screenName}', '${user.emailAddress}', ${user.facebookId}, ${user.ldapServerId}, '${user.openId}', ${user.portraitId}, '${user.languageId}', '${user.timeZoneId}', '${user.greeting}', '${user.comments}', '${user.firstName}', '${user.middleName}', '${user.lastName}', '${user.jobTitle}', '${user.loginDate?datetime}', '${user.loginIP}', '${user.lastLoginDate?datetime}', '${user.lastLoginIP}',  ${user.lastFailedLoginDate!'null'}, ${user.failedLoginAttempts}, ${user.lockout?string}, ${user.lockoutDate!'null'}, ${user.agreedToTermsOfUse?string}, ${user.emailAddressVerified?string}, '${user.status}');

<#assign contact = dataFactory.addContact(user)>

insert into Contact_ values (${contact.contactId}, ${contact.companyId}, ${contact.userId}, '${contact.userName}', '${contact.createDate?datetime}', '${contact.modifiedDate?datetime}', ${contact.classNameId}, ${contact.classPK}, ${contact.accountId}, ${contact.parentContactId}, '${contact.emailAddress}', '${contact.firstName}', '${contact.middleName}', '${contact.lastName}', ${contact.prefixId}, ${contact.suffixId}, ${contact.male?string}, ${contact.birthday!'null'}, '${contact.smsSn}', '${contact.aimSn}', '${contact.facebookSn}', '${contact.icqSn}', '${contact.jabberSn}', '${contact.msnSn}', '${contact.mySpaceSn}', '${contact.skypeSn}', '${contact.twitterSn}', '${contact.ymSn}', '${contact.employeeStatusId}', '${contact.employeeNumber}', '${contact.jobTitle}', '${contact.jobClass}', '${contact.hoursOfOperation}');

<#if !user.defaultUser>
	<#list roleIds as roleId>
		insert into Users_Roles values (${user.userId}, ${roleId});
	</#list>

	<#list groupIds as groupId>
		insert into Users_Groups values (${user.userId}, ${groupId});
	</#list>

	<#assign group = dataFactory.addGroup(user)>

	<#assign publicLayouts = []>

	<#if user.userId != dataFactory.guestUser.userId>
		<#assign publicLayouts = [dataFactory.addLayout(group.groupId, "home", "", "33,")]>
	</#if>

	${sampleSQLBuilder.insertGroup(group, publicLayouts)}
</#if>