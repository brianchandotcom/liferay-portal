create table DLStorageQuota(
	mvccVersion      LONG default 0 not null,
	dlStorageQuotaId LONG           not null primary key,
	companyId        LONG,
	storageSize      LONG
);

create unique index IX_1214035D on DLStorageQuota (companyId);
create unique index IX_B6F21286 on DLFileEntryType (groupId, dataDefinitionId, ctCollectionId);

COMMIT_TRANSACTION;