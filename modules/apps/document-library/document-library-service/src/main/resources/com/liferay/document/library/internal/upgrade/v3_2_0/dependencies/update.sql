create table DLStorageQuota(
	mvccVersion      LONG default 0 not null,
	dlStorageQuotaId LONG           not null primary key,
	companyId        LONG,
	storageSize      LONG
);

create unique index IX_1214035D on DLStorageQuota (companyId);

COMMIT_TRANSACTION;