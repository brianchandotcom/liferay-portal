create table FragmentCollection (
	fragmentCollectionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	fragmentCollectionKey VARCHAR(75) null,
	name VARCHAR(75) null,
	description STRING null
);

create table FragmentEntry (
	fragmentEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	fragmentCollectionId LONG,
	fragmentEntryKey VARCHAR(75) null,
	name VARCHAR(75) null,
	css STRING null,
	html STRING null,
	js STRING null,
	htmlPreviewEntryId LONG,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

create table FragmentEntryLink (
	uuid_ VARCHAR(75) null,
	fragmentEntryLinkId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	originalFragmentEntryLinkId LONG,
	fragmentEntryId LONG,
	classNameId LONG,
	classPK LONG,
	css STRING null,
	html STRING null,
	js STRING null,
	editableValues STRING null,
	position INTEGER
);