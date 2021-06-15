create table JSONStoreEntry (
	mvccVersion LONG default 0 not null,
	ctCollectionId LONG default 0 not null,
	jsonStoreEntryId LONG not null,
	companyId LONG,
	classNameId LONG,
	classPK LONG,
	parentJSONStoreEntryId LONG,
	index_ INTEGER,
	key_ VARCHAR(255) null,
	type_ INTEGER,
	valueLong LONG,
	valueString TEXT null,
	primary key (jsonStoreEntryId, ctCollectionId)
);