create table CTScore (
						 mvccVersion LONG default 0 not null,
						 ctScoreId LONG not null primary key,
						 companyId LONG,
						 ctCollectionId LONG,
						 score INTEGER
);

create unique index IX_13F5EC85 on CTScore (ctCollectionId);

COMMIT_TRANSACTION;