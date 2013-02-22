<#setting number_format = "0">

insert into Company (companyId, accountId, webId, mx, active_) values (${companyId}, ${dataFactory.company.accountId}, 'liferay.com', 'liferay.com', TRUE);

${writerCompanyCSV.write(companyId + "\n")}

insert into Account_ (accountId, companyId, userId, userName, createDate, modifiedDate, parentAccountId, name, legalName, legalId, legalType, sicCode, tickerSymbol, industry, type_, size_) values (${dataFactory.company.accountId}, ${companyId}, 0, '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0, 'Liferay', 'Liferay, Inc.', '', '', '', '', '', '', '');
insert into VirtualHost values (${counter.get()}, ${companyId}, 0, 'localhost');