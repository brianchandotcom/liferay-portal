create table RSVEntry (
	mvccVersion LONG default 0 not null,
	rsvEntryId LONG not null primary key,
	companyId LONG
);