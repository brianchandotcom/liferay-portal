create index IX_94A7EF25 on Marketplace_App (category[$COLUMN_LENGTH:75$]);
create index IX_865B7BD5 on Marketplace_App (companyId);
create index IX_20F14D93 on Marketplace_App (remoteAppId);
create index IX_A7807DA7 on Marketplace_App (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_5848F52D on Marketplace_Module (appId, bundleSymbolicName[$COLUMN_LENGTH:500$], bundleVersion[$COLUMN_LENGTH:75$]);
create index IX_C6938724 on Marketplace_Module (appId, contextName[$COLUMN_LENGTH:75$]);
create index IX_DD03D499 on Marketplace_Module (bundleSymbolicName[$COLUMN_LENGTH:500$]);
create index IX_F2F1E964 on Marketplace_Module (contextName[$COLUMN_LENGTH:75$]);
create index IX_896A375A on Marketplace_Module (uuid_[$COLUMN_LENGTH:75$], companyId);