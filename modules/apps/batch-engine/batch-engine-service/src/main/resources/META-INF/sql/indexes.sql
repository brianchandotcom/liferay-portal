create index IX_6DA5084D on BatchEngineExportTask (companyId);
create index IX_DADA545C on BatchEngineExportTask (executeStatus[$COLUMN_LENGTH:75$]);
create index IX_35D0E1E2 on BatchEngineExportTask (externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_8B990859 on BatchEngineExportTask (uuid_[$COLUMN_LENGTH:75$]);

create index IX_CEAC687C on BatchEngineImportTask (companyId);
create index IX_ABC8050B on BatchEngineImportTask (executeStatus[$COLUMN_LENGTH:75$]);
create index IX_30D67391 on BatchEngineImportTask (externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_4FFDD808 on BatchEngineImportTask (uuid_[$COLUMN_LENGTH:75$]);

create index IX_863EDEA9 on BatchEngineImportTaskError (batchEngineImportTaskId);