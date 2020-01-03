create table MicroblogsEntry (
	microblogsEntryId LONG not null,
	companyId LONG not null,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	creatorClassNameId LONG,
	creatorClassPK LONG,
	content STRING null,
	type_ INTEGER,
	parentMicroblogsEntryId LONG,
	socialRelationType INTEGER,
	primary key (microblogsEntryId, companyId)
);