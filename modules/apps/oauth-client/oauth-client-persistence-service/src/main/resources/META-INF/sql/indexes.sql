create unique index IX_328DCB29 on OAuthClientASLocalMetadata (companyId, issuer[$COLUMN_LENGTH:75$]);
create index IX_CEF2762B on OAuthClientASLocalMetadata (companyId, localWellKnownEnabled);
create unique index IX_B2201FE9 on OAuthClientASLocalMetadata (companyId, oAuthASLocalWellKnownURI[$COLUMN_LENGTH:256$]);
create unique index IX_AD59C966 on OAuthClientASLocalMetadata (localWellKnownURI[$COLUMN_LENGTH:256$]);
create index IX_D41859A6 on OAuthClientASLocalMetadata (userId);

create unique index IX_FEC415C2 on OAuthClientEntry (companyId, authServerWellKnownURI[$COLUMN_LENGTH:256$], clientId[$COLUMN_LENGTH:256$]);
create index IX_29A83E50 on OAuthClientEntry (userId);