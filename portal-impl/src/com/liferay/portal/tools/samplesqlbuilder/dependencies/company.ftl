<#setting number_format = "0">

insert into Company (companyId, accountId, webId, mx, active_) values (${companyId}, ${dataFactory.company.accountId}, 'liferay.com', 'liferay.com', TRUE);
insert into Account_ (accountId, companyId, userId, userName, createDate, modifiedDate, parentAccountId, name, legalName, legalId, legalType, sicCode, tickerSymbol, industry, type_, size_) values (${dataFactory.company.accountId}, ${companyId}, ${defaultUserId}, '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0, 'Liferay', 'Liferay, Inc.', '', '', '', '', '', '', '');

${writerCompanyCSV.write(companyId + "\n")}

<#assign contact = dataFactory.addContact("", "")>
<#assign user = dataFactory.addUser(true, "")>

${sampleSQLBuilder.insertUser(contact, null, null, null, null, null, null, user)}

<#assign contact = dataFactory.addContact("Test", "Test")>
<#assign user = dataFactory.addUser(false, "test")>

<#assign userGroup = dataFactory.addGroup(counter.get(), dataFactory.userClassName.classNameId, user.userId, stringUtil.valueOf(user.userId), "/" + user.screenName, false)>

<#assign groupIds = [dataFactory.guestGroup.groupId]>
<#assign organizationIds = []>
<#assign privateLayouts = []>
<#assign publicLayouts = []>
<#assign roleIds = [dataFactory.administratorRole.roleId]>

${sampleSQLBuilder.insertUser(contact, userGroup, groupIds, organizationIds, privateLayouts, publicLayouts, roleIds, user)}

<#assign mbSystemCategory = dataFactory.addMBCategory(0, 0, 0, 0, "", "", 0, 0)>

${sampleSQLBuilder.insertMBCategory(mbSystemCategory)}