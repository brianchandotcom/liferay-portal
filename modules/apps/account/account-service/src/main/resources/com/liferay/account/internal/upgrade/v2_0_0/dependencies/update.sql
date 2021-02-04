create table AccountGroupRel (
	mvccVersion LONG default 0 not null,
	AccountGroupRelId LONG not null primary key,
	companyId LONG,
	accountGroupId LONG,
	classNameId LONG,
	classPK LONG
);

create index IX_448835E3 on AccountGroupRel (accountGroupId, classNameId, classPK);
create index IX_E31F0762 on AccountGroupRel (classNameId, classPK);