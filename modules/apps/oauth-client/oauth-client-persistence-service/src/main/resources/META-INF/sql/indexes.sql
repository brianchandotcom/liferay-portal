create unique index IX_E5878996 on OAuthClientASLocalMetadata (companyId, localWellKnownURI[$COLUMN_LENGTH:256$]);
create index IX_D41859A6 on OAuthClientASLocalMetadata (userId);

create unique index IX_FEC415C2 on OAuthClientEntry (companyId, authServerWellKnownURI[$COLUMN_LENGTH:256$], clientId[$COLUMN_LENGTH:128$]);
create index IX_29A83E50 on OAuthClientEntry (userId);