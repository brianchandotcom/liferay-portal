<#include "macro.ftl">

insert into User_ values ('${user.uuid}', ${user.userId}, ${user.companyId}, '${dataFactory.getDateString(user.createDate)}', '${dataFactory.getDateString(user.modifiedDate)}', ${user.defaultUser?string}, ${user.contactId}, '${user.password}', ${user.passwordEncrypted?string}, ${user.passwordReset?string}, '${dataFactory.getDateString(user.passwordModifiedDate)}', '${user.digest}', '${user.reminderQueryQuestion}', '${user.reminderQueryAnswer}', ${user.graceLoginCount}, '${user.screenName}', '${user.emailAddress}', ${user.facebookId}, ${user.ldapServerId}, '${user.openId}', ${user.portraitId}, '${user.languageId}', '${user.timeZoneId}', '${user.greeting}', '${user.comments}', '${user.firstName}', '${user.middleName}', '${user.lastName}', '${user.jobTitle}', '${dataFactory.getDateString(user.loginDate)}', '${user.loginIP}', '${dataFactory.getDateString(user.lastLoginDate)}', '${user.lastLoginIP}', '${dataFactory.getDateString(user.lastFailedLoginDate)}', ${user.failedLoginAttempts}, ${user.lockout?string}, '${dataFactory.getDateString(user.lockoutDate)}', ${user.agreedToTermsOfUse?string}, ${user.emailAddressVerified?string}, '${user.status}');

<#assign contact = dataFactory.newContact(user)>

insert into Contact_ values (${contact.contactId}, ${contact.companyId}, ${contact.userId}, '${contact.userName}', '${dataFactory.getDateString(contact.createDate)}', '${dataFactory.getDateString(contact.modifiedDate)}', ${contact.classNameId}, ${contact.classPK}, ${contact.accountId}, ${contact.parentContactId}, '${contact.emailAddress}', '${contact.firstName}', '${contact.middleName}', '${contact.lastName}', ${contact.prefixId}, ${contact.suffixId}, ${contact.male?string}, '${dataFactory.getDateString(contact.birthday)}', '${contact.smsSn}', '${contact.aimSn}', '${contact.facebookSn}', '${contact.icqSn}', '${contact.jabberSn}', '${contact.msnSn}', '${contact.mySpaceSn}', '${contact.skypeSn}', '${contact.twitterSn}', '${contact.ymSn}', '${contact.employeeStatusId}', '${contact.employeeNumber}', '${contact.jobTitle}', '${contact.jobClass}', '${contact.hoursOfOperation}');

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