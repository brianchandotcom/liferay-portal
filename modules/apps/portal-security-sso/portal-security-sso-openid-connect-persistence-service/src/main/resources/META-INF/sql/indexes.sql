create index IX_396C5BCB on OpenIdConnectSession (accessTokenExpirationDate);
create index IX_AE077141 on OpenIdConnectSession (authServerWellKnownURI[$COLUMN_LENGTH:256$], clientId[$COLUMN_LENGTH:256$], companyId);
create unique index IX_60980B41 on OpenIdConnectSession (userId, authServerWellKnownURI[$COLUMN_LENGTH:256$], clientId[$COLUMN_LENGTH:256$]);