create unique index IX_C3446693 on CTSContent (repositoryId, companyId, path_[$COLUMN_LENGTH:75$], storeType[$COLUMN_LENGTH:75$], version[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_D1F1C119 on CTSContent (repositoryId, companyId, storeType[$COLUMN_LENGTH:75$]);
create index IX_93E10A34 on CTSContent (repositoryId, path_[$COLUMN_LENGTH:75$]);