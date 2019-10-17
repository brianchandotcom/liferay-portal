create table InfoItemUsage (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	infoItemUsageId LONG not null primary key,
	groupId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	containerKey VARCHAR(75) null,
	containerType LONG,
	plid LONG,
	type_ INTEGER,
	lastPublishDate DATE null
);