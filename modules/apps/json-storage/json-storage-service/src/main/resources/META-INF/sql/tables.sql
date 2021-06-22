create table JSONStorageEntry (
	mvccVersion LONG default 0 not null,
	ctCollectionId LONG default 0 not null,
	jsonStorageEntryId LONG not null,
	companyId LONG,
	classNameId LONG,
	classPK LONG,
	parentJSONStorageEntryId LONG,
	index_ INTEGER,
	key_ VARCHAR(75) null,
	type_ INTEGER,
	valueLong LONG,
	valueString VARCHAR(75) null,
	primary key (jsonStorageEntryId, ctCollectionId)
);