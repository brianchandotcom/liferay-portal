create index IX_263868C8 on DLContent (companyId, repositoryId, ctCollectionId);
create index IX_FD632DBE on DLContent (companyId, repositoryId, path_[$COLUMN_LENGTH:255$], ctCollectionId);
create unique index IX_8E223106 on DLContent (companyId, repositoryId, path_[$COLUMN_LENGTH:255$], version[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_20C476B5 on DLContent (ctCollectionId);