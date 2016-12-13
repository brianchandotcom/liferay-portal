create table Release_ (
	mvccVersion LONG default 0 not null,
	releaseId LONG not null primary key,
	createDate DATE null,
	modifiedDate DATE null,
	servletContextName VARCHAR(75) null,
	schemaVersion VARCHAR(75) null,
	buildNumber INTEGER,
	buildDate DATE null,
	verified BOOLEAN,
	state_ INTEGER,
	testString VARCHAR(1024) null
);

create table ServiceComponent (
	mvccVersion LONG default 0 not null,
	serviceComponentId LONG not null primary key,
	buildNamespace VARCHAR(75) null,
	buildNumber LONG,
	buildDate LONG,
	data_ TEXT null
);

create unique index IX_8BD6BCA7 on Release_ (servletContextName);

create unique index IX_4F0315B8 on ServiceComponent (buildNamespace, buildNumber);