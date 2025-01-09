create table CTScore (
	 mvccVersion LONG default 0 not null,
	 ctCollectionId LONG not null primary key,
	 companyId LONG,
	 score INTEGER
);

COMMIT_TRANSACTION;