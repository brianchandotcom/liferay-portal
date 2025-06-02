create unique index IX_EAC5DE50 on BatchEngineExportTask (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_DADA545C on BatchEngineExportTask (executeStatus[$COLUMN_LENGTH:75$]);
create index IX_8B990859 on BatchEngineExportTask (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_2BBBB941 on BatchEngineImportTask (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_ABC8050B on BatchEngineImportTask (executeStatus[$COLUMN_LENGTH:75$]);
create index IX_4FFDD808 on BatchEngineImportTask (uuid_[$COLUMN_LENGTH:75$]);

create index IX_863EDEA9 on BatchEngineImportTaskError (batchEngineImportTaskId);