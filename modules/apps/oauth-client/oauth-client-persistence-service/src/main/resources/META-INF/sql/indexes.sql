create unique index IX_CE8F46C2 on OAuthClientAuthServer (companyId, issuer[$COLUMN_LENGTH:128$]);
create index IX_98AA4060 on OAuthClientAuthServer (companyId, type_[$COLUMN_LENGTH:75$]);
create index IX_A1023AD on OAuthClientAuthServer (userId);

create unique index IX_5116EA4 on OAuthClientEntry (companyId, authServerIssuer[$COLUMN_LENGTH:128$], clientId[$COLUMN_LENGTH:128$]);
create index IX_29A83E50 on OAuthClientEntry (userId);