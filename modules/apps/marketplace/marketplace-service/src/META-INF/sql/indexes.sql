create index IX_82491D39 on App (category);
create index IX_4CE01041 on App (companyId);
create index IX_58A276FF on App (remoteAppId);
create index IX_456C57BB on App (uuid_, companyId);

create index IX_30196E99 on Module (appId, bundleSymbolicName, bundleVersion);
create index IX_4620D990 on Module (appId, contextName);
create index IX_5C912705 on Module (bundleSymbolicName);
create index IX_E4753878 on Module (contextName);
create index IX_95910622 on Module (uuid_);