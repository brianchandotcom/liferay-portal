create index IX_396C5BCB on OpenIdConnectSession (accessTokenExpirationDate);
create index IX_AE077141 on OpenIdConnectSession (authServerWellKnownURI[$COLUMN_LENGTH:256$], clientId[$COLUMN_LENGTH:256$], companyId);
create unique index IX_5EFFA9C1 on OpenIdConnectSession (authServerWellKnownURI[$COLUMN_LENGTH:256$], clientId[$COLUMN_LENGTH:256$], userId);