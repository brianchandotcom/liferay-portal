create table JSONStoreEntry (
	mvccVersion LONG default 0 not null,
	ctCollectionId LONG default 0 not null,
	jsonStoreEntryId LONG not null,
	companyId LONG,
	classNameId LONG,
	classPK LONG,
	parentJSONStoreEntryId LONG,
	index_ INTEGER,
	key_ VARCHAR(75) null,
	type_ INTEGER,
	valueLong LONG,
	valueString VARCHAR(75) null,
	primary key (jsonStoreEntryId, ctCollectionId)
);