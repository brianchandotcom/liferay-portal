create index IX_3E56F38F on ObjectDefinition (companyId, name[$COLUMN_LENGTH:75$]);
create index IX_55C39BCE on ObjectDefinition (system_, status);
create index IX_B929D94C on ObjectDefinition (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_FDFF4146 on ObjectEntry (groupId, companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_2F209ED2 on ObjectEntry (groupId, objectDefinitionId);
create index IX_139200BA on ObjectEntry (objectDefinitionId);
create index IX_49B9450D on ObjectEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_5AF9AACF on ObjectEntry (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_5DDCF209 on ObjectField (objectDefinitionId, dbTableName[$COLUMN_LENGTH:75$]);
create index IX_A59C5981 on ObjectField (objectDefinitionId, name[$COLUMN_LENGTH:75$]);
create index IX_594B4995 on ObjectField (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_81F3D350 on ObjectLayout (objectDefinitionId);
create index IX_E27AC523 on ObjectLayout (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_5F97F7CF on ObjectLayoutBox (objectLayoutTabId);
create index IX_CDCBE8DC on ObjectLayoutBox (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_46CE5537 on ObjectLayoutColumn (objectLayoutRowId);
create index IX_53BB1AD9 on ObjectLayoutColumn (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_FA14DE56 on ObjectLayoutRow (objectLayoutBoxId);
create index IX_644C876B on ObjectLayoutRow (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_F01F1EEA on ObjectLayoutTab (objectLayoutId);
create index IX_94D361A6 on ObjectLayoutTab (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_72BFC24A on ObjectRelationship (companyId, name[$COLUMN_LENGTH:75$]);
create index IX_A71785B6 on ObjectRelationship (objectDefinitionId1, name[$COLUMN_LENGTH:75$]);
create index IX_F6F370B8 on ObjectRelationship (objectDefinitionId1, objectDefinitionId2, name[$COLUMN_LENGTH:75$], type_[$COLUMN_LENGTH:75$]);
create index IX_2A3F6ED7 on ObjectRelationship (objectDefinitionId1, objectDefinitionId2, type_[$COLUMN_LENGTH:75$]);
create index IX_DE3EBEF8 on ObjectRelationship (objectDefinitionId2);
create index IX_F1DC092D on ObjectRelationship (objectFieldId2);
create index IX_11DAE1F1 on ObjectRelationship (uuid_[$COLUMN_LENGTH:75$], companyId);