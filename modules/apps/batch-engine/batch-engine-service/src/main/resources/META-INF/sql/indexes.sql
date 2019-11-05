create index IX_ABC8050B on BatchEngineImportTask (executeStatus[$COLUMN_LENGTH:75$]);
create index IX_BE725720 on BatchEngineImportTask (uuid_[$COLUMN_LENGTH:75$], companyId);