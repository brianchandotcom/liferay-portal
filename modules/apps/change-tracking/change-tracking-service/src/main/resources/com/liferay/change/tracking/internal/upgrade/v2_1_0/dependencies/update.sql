create table CTAutoResolutionInfo (
	mvccVersion LONG default 0 not null,
	ctAutoResolutionInfoId LONG not null primary key,
	companyId LONG,
	createDate DATE null,
	ctCollectionId LONG,
	modelClassNameId LONG,
	sourceModelClassPK LONG,
	targetModelClassPK LONG,
	conflictIdentifier VARCHAR(500) null
);

create index IX_FC5D3CFE on CTAutoResolutionInfo (ctCollectionId);

COMMIT_TRANSACTION;