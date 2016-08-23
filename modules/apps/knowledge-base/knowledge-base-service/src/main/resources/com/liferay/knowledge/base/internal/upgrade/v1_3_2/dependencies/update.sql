alter table KBArticle add kbFolderId LONG;

create table KBFolder (
	uuid_ VARCHAR(75) null,
	kbFolderId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	parentKBFolderId LONG,
	name VARCHAR(75) null,
	urlTitle VARCHAR(75) null,
	description STRING null
);