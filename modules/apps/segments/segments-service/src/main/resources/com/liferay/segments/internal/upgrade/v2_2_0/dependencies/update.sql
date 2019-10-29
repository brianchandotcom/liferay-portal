create table SegmentsEntryRole (
	mvccVersion LONG default 0 not null,
	segmentsEntryRoleId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	roleId LONG,
	segmentsEntryId LONG
);

create unique index IX_B2DD086A on SegmentsEntryRole (roleId, segmentsEntryId);
create index IX_1B24832F on SegmentsEntryRole (segmentsEntryId);

COMMIT_TRANSACTION;