create index IX_993E2360 on OAuth2AccessToken (oAuth2ApplicationId);
create index IX_547A0688 on OAuth2AccessToken (oAuth2RefreshTokenId);
create index IX_B42CDEF4 on OAuth2AccessToken (tokenContent[$COLUMN_LENGTH:75$]);

create index IX_523E5C67 on OAuth2Application (companyId, clientId[$COLUMN_LENGTH:75$]);

create index IX_5E2FB801 on OAuth2RefreshToken (oAuth2ApplicationId);
create index IX_AB48CD73 on OAuth2RefreshToken (tokenContent[$COLUMN_LENGTH:75$]);

create index IX_17BF4F72 on OAuth2ScopeGrant (applicationName[$COLUMN_LENGTH:75$], bundleSymbolicName[$COLUMN_LENGTH:75$], companyId, oAuth2AccessTokenId, scope[$COLUMN_LENGTH:75$]);
create index IX_442995DC on OAuth2ScopeGrant (oAuth2AccessTokenId);