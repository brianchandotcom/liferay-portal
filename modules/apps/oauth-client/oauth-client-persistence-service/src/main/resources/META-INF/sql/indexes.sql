create index IX_51E47B3C on OAuthClientASLocalMetadata (companyId);
create unique index IX_AD59C966 on OAuthClientASLocalMetadata (localWellKnownURI[$COLUMN_LENGTH:256$]);
create index IX_D41859A6 on OAuthClientASLocalMetadata (userId);

create unique index IX_FEC415C2 on OAuthClientEntry (companyId, authServerWellKnownURI[$COLUMN_LENGTH:256$], clientId[$COLUMN_LENGTH:256$]);
create index IX_29A83E50 on OAuthClientEntry (userId);