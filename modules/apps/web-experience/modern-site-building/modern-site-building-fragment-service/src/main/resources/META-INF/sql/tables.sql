create table MSBFragmentCollection (
	msbFragmentCollectionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	description STRING null
);

create table MSBFragmentEntry (
	msbFragmentEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	msbFragmentCollectionId LONG,
	name VARCHAR(75) null,
	css STRING null,
	html STRING null,
	js STRING null
);