create table SequenceEntry (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	sequenceEntryId LONG not null primary key,
	companyId LONG
);