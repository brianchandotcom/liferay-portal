create table LayoutClassedModelUsage (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	layoutClassedModelUsageId LONG not null primary key,
	groupId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	containerKey VARCHAR(200) null,
	containerType LONG,
	plid LONG,
	type_ INTEGER,
	lastPublishDate DATE null
);

create unique index IX_FA38EE24 on LayoutClassedModelUsage (classNameId, classPK, containerKey[$COLUMN_LENGTH:200$], containerType, plid);
create index IX_B041F1F5 on LayoutClassedModelUsage (classNameId, classPK, type_);
create index IX_DF750659 on LayoutClassedModelUsage (containerKey[$COLUMN_LENGTH:200$], containerType, plid);
create index IX_19448DD6 on LayoutClassedModelUsage (plid);
create unique index IX_694CA341 on LayoutClassedModelUsage (uuid_[$COLUMN_LENGTH:75$], groupId);

COMMIT_TRANSACTION;