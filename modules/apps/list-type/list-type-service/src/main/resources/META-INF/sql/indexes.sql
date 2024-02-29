create unique index IX_FF39F41C on ListTypeDefinition (externalReferenceCode[$COLUMN_LENGTH:75$], companyId);
create index IX_C3F53B03 on ListTypeDefinition (uuid_[$COLUMN_LENGTH:75$]);

create index IX_8FB531BD on ListTypeEntry (externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_C413932E on ListTypeEntry (listTypeDefinitionId, key_[$COLUMN_LENGTH:75$]);
create index IX_79966E34 on ListTypeEntry (uuid_[$COLUMN_LENGTH:75$]);