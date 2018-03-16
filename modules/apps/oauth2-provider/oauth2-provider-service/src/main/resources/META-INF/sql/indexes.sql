create index IX_2F44D793 on OAuth2AccessToken (OAuth2AccessTokenContent[$COLUMN_LENGTH:75$]);
create index IX_993E2360 on OAuth2AccessToken (oAuth2ApplicationId);
create index IX_547A0688 on OAuth2AccessToken (oAuth2RefreshTokenId);

create index IX_523E5C67 on OAuth2Application (companyId, clientId[$COLUMN_LENGTH:75$]);

create index IX_5E2FB801 on OAuth2RefreshToken (oAuth2ApplicationId);
create index IX_1E522D31 on OAuth2RefreshToken (oAuth2RefreshTokenContent[$COLUMN_LENGTH:75$]);

create index IX_B10AE5BC on OAuth2ScopeGrant (OAuth2AccessTokenId);
create index IX_FD9E6392 on OAuth2ScopeGrant (applicationName[$COLUMN_LENGTH:75$], bundleSymbolicName[$COLUMN_LENGTH:75$], companyId, OAuth2AccessTokenId, scope[$COLUMN_LENGTH:75$]);