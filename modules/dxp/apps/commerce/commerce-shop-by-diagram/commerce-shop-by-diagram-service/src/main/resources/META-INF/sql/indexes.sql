create index IX_FBD9E8B4 on CPDefinitionDiagramEntry (CPDefinitionId);
create unique index IX_AB7E7C61 on CPDefinitionDiagramEntry (number_);

create index IX_CD3A657 on CPDefinitionDiagramPin (CPDefinitionId);

create unique index IX_13D84BD2 on CPDefinitionDiagramSetting (CPDefinitionId);
create index IX_78A0FB77 on CPDefinitionDiagramSetting (uuid_[$COLUMN_LENGTH:75$], companyId);