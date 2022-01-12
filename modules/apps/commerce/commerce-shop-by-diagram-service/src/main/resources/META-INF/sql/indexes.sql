create index IX_C8B33DE2 on CSDiagramEntry (CPDefinitionId, ctCollectionId);
create unique index IX_4748C557 on CSDiagramEntry (CPDefinitionId, sequence[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_9D8C9924 on CSDiagramEntry (CPInstanceId, ctCollectionId);

create index IX_1BC6C985 on CSDiagramPin (CPDefinitionId, ctCollectionId);

create unique index IX_4F753100 on CSDiagramSetting (CPDefinitionId, ctCollectionId);
create index IX_CC4A82A5 on CSDiagramSetting (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_9B51BB9F on CSDiagramSetting (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);