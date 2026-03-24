create unique index IX_A6BA8E81 on OAuthClientASLocalMetadata (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create unique index IX_328DCB29 on OAuthClientASLocalMetadata (companyId, issuer[$COLUMN_LENGTH:75$]);
create index IX_CEF2762B on OAuthClientASLocalMetadata (companyId, localWellKnownEnabled);
create unique index IX_B2201FE9 on OAuthClientASLocalMetadata (companyId, oAuthASLocalWellKnownURI[$COLUMN_LENGTH:256$]);
create unique index IX_AD59C966 on OAuthClientASLocalMetadata (localWellKnownURI[$COLUMN_LENGTH:256$]);
create index IX_D41859A6 on OAuthClientASLocalMetadata (userId);
create index IX_F1AD4AC8 on OAuthClientASLocalMetadata (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_FEC415C2 on OAuthClientEntry (companyId, authServerWellKnownURI[$COLUMN_LENGTH:256$], clientId[$COLUMN_LENGTH:256$]);
create unique index IX_5A4A92AB on OAuthClientEntry (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_29A83E50 on OAuthClientEntry (userId);
create index IX_999928DE on OAuthClientEntry (uuid_[$COLUMN_LENGTH:75$]);