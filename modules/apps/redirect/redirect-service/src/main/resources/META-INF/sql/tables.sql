create table RedirectEntry (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	redirectEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	destinationURL STRING null,
	expirationDate DATE null,
	lastOccurrenceDate DATE null,
	permanent_ BOOLEAN,
	sourceURL STRING null
);

create table RedirectNotFoundEntry (
	mvccVersion LONG default 0 not null,
	redirectNotFoundEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	hits LONG,
	ignored BOOLEAN,
	url STRING null
);