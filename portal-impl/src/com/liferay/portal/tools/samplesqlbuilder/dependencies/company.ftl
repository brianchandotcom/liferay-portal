<#setting number_format = "0">

insert into Company (companyId, accountId, webId, mx, active_) values (${companyId}, ${dataFactory.company.accountId}, 'liferay.com', 'liferay.com', TRUE);

${writerCompanyCSV.write(companyId + "\n")}

insert into Account_ (accountId, companyId, userId, userName, createDate, modifiedDate, parentAccountId, name, legalName, legalId, legalType, sicCode, tickerSymbol, industry, type_, size_) values (${dataFactory.company.accountId}, ${companyId}, ${defaultUserId}, '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0, 'Liferay', 'Liferay, Inc.', '', '', '', '', '', '', '');
insert into VirtualHost values (${counter.get()}, ${companyId}, 0, 'localhost');

<#assign contact = dataFactory.addContact("", "")>
<#assign user = dataFactory.addUser(true, "")>

${sampleSQLBuilder.insertUser(contact, null, null, null, user)}

<#assign contact = dataFactory.addContact("Test", "Test")>
<#assign user = dataFactory.addUser(false, "test")>

<#assign userGroup = dataFactory.addGroup(counter.get(), dataFactory.userClassNameId, user.userId, stringUtil.valueOf(user.userId), "/" + user.screenName, false)>

${sampleSQLBuilder.insertGroup(userGroup, [], [])}

<#assign groupIds = [dataFactory.guestGroup.groupId]>
<#assign organizationIds = []>
<#assign roleIds = [dataFactory.administratorRole.roleId]>

${sampleSQLBuilder.insertUser(contact, groupIds, organizationIds, roleIds, user)}

<#assign mbSystemCategory = dataFactory.addMBCategory(0, 0, 0, 0, "", "", 0, 0)>

${sampleSQLBuilder.insertMBCategory(mbSystemCategory)}