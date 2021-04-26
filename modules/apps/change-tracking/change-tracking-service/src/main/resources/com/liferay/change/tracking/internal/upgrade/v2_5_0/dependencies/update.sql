create table CTComment (
	mvccVersion LONG default 0 not null,
	ctCommentId LONG not null primary key,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	ctCollectionId LONG,
	ctEntryId LONG,
	value TEXT null
);

create index IX_FE644B52 on CTComment (ctCollectionId);
create index IX_C5E592B8 on CTComment (ctEntryId);

COMMIT_TRANSACTION;