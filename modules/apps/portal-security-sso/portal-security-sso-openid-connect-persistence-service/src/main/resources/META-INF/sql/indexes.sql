create index IX_C00E32C1 on OpenIdConnectSession (companyId, providerName[$COLUMN_LENGTH:75$]);
create unique index IX_A7FC5B3A on OpenIdConnectSession (configurationPid[$COLUMN_LENGTH:256$], userId);
create unique index IX_A202B8E1 on OpenIdConnectSession (userId, providerName[$COLUMN_LENGTH:75$]);