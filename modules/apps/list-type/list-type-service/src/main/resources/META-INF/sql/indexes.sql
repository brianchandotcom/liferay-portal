create unique index IX_17295166 on ListTypeDefinition (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_67134731 on ListTypeDefinition (companyId, userId);
create index IX_C3F53B03 on ListTypeDefinition (uuid_[$COLUMN_LENGTH:75$]);

create index IX_749438E2 on ListTypeEntry (companyId, userId);
create index IX_8FB531BD on ListTypeEntry (externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_C413932E on ListTypeEntry (listTypeDefinitionId, key_[$COLUMN_LENGTH:75$]);
create index IX_79966E34 on ListTypeEntry (uuid_[$COLUMN_LENGTH:75$]);