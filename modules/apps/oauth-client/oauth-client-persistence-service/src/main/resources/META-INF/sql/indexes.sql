create unique index IX_CE8F46C2 on OAuthClientAuthServer (companyId, issuer[$COLUMN_LENGTH:128$]);
create index IX_98AA4060 on OAuthClientAuthServer (companyId, type_[$COLUMN_LENGTH:75$]);
create index IX_9502530F on OAuthClientAuthServer (companyId, userId);

create unique index IX_5116EA4 on OAuthClientEntry (companyId, authServerIssuer[$COLUMN_LENGTH:128$], clientId[$COLUMN_LENGTH:128$]);
create index IX_3752448C on OAuthClientEntry (companyId, userId);