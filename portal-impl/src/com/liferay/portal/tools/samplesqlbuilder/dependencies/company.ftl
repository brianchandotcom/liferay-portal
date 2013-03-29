<#assign company = dataFactory.company>

insert into Company values (${company.companyId}, ${company.accountId}, '${company.webId}', '${company.key}', '${company.mx}', '${company.homeURL}', ${company.logoId}, ${company.system?string}, ${company.maxUsers}, ${company.active?string});

<#assign account = dataFactory.account>

insert into Account_ values (${account.accountId}, ${account.companyId}, ${account.userId}, '${account.userName}', '${dataFactory.getDateString(account.createDate)}', '${dataFactory.getDateString(account.modifiedDate)}', '${account.parentAccountId}', '${account.name}', '${account.legalName}', '${account.legalId}', '${account.legalType}', '${account.sicCode}', '${account.tickerSymbol}', '${account.industry}', '${account.type}', '${account.size}');

<#assign virtualHost = dataFactory.virtualHost>

insert into VirtualHost values (${virtualHost.virtualHostId}, ${virtualHost.companyId}, ${virtualHost.layoutSetId}, '${virtualHost.hostname}');

${writerCompanyCSV.write(company.companyId + "\n")}