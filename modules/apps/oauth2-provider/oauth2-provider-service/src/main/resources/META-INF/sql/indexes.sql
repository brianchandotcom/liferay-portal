create index IX_87DAF9C3 on OA2Auths_OA2ScopeGrants (companyId);
create index IX_2F541817 on OA2Auths_OA2ScopeGrants (oAuth2ScopeGrantId);

create index IX_523E5C67 on OAuth2Application (companyId, clientId[$COLUMN_LENGTH:75$]);
create unique index IX_67BC29B0 on OAuth2Application (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_4E7858F on OAuth2Application (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_282ECE83 on OAuth2ApplicationScopeAliases (companyId);
create index IX_2F9EBCBB on OAuth2ApplicationScopeAliases (oAuth2ApplicationId);

create index IX_77D3B9EA on OAuth2Authorization (accessTokenContentHash);
create index IX_FB9F7B8A on OAuth2Authorization (companyId, accessTokenContentHash);
create index IX_673EE35 on OAuth2Authorization (companyId, refreshTokenContentHash);
create index IX_70DD169C on OAuth2Authorization (oAuth2ApplicationId);
create index IX_10C77BD5 on OAuth2Authorization (refreshTokenContentHash);
create index IX_EFE93C04 on OAuth2Authorization (userId, oAuth2ApplicationId, rememberDeviceContent[$COLUMN_LENGTH:75$]);

create index IX_88938BF on OAuth2ScopeGrant (companyId, oA2AScopeAliasesId, applicationName[$COLUMN_LENGTH:255$], bundleSymbolicName[$COLUMN_LENGTH:255$], scope[$COLUMN_LENGTH:240$]);
create index IX_80FCAC23 on OAuth2ScopeGrant (oA2AScopeAliasesId);