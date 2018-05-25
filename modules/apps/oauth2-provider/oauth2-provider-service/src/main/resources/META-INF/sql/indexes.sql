create index IX_87DAF9C3 on OA2Auths_OA2ScopeGrants (companyId);
create index IX_F4C82F24 on OA2Auths_OA2ScopeGrants (oAuth2AuthorizationId);
create index IX_2F541817 on OA2Auths_OA2ScopeGrants (oAuth2ScopeGrantId);

create index IX_523E5C67 on OAuth2Application (companyId, clientId[$COLUMN_LENGTH:75$]);

create index IX_282ECE83 on OAuth2ApplicationScopeAliases (companyId);
create index IX_D8310AF9 on OAuth2ApplicationScopeAliases (oAuth2ApplicationId, scopeAliases[$COLUMN_LENGTH:4096$]);

create index IX_3B12D3C on OAuth2Authorization (accessTokenContent[$COLUMN_LENGTH:4096$]);
create index IX_70DD169C on OAuth2Authorization (oAuth2ApplicationId);
create index IX_C2109CA7 on OAuth2Authorization (refreshTokenContent[$COLUMN_LENGTH:4096$]);
create index IX_719D503E on OAuth2Authorization (userId);

create index IX_88938BF on OAuth2ScopeGrant (companyId, oA2AScopeAliasesId, applicationName[$COLUMN_LENGTH:255$], bundleSymbolicName[$COLUMN_LENGTH:255$], scope[$COLUMN_LENGTH:255$]);
create index IX_80FCAC23 on OAuth2ScopeGrant (oA2AScopeAliasesId);