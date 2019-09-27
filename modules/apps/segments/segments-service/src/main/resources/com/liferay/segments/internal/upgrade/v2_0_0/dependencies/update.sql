alter table SegmentsExperience add segmentsExperienceKey VARCHAR(75) null;

create unique index IX_B8F358EB on SegmentsExperience (groupId, segmentsExperienceKey[$COLUMN_LENGTH:75$]);

update SegmentsExperience set segmentsExperienceKey = segmentsExperienceId;

create table SegmentsExperiment (
	uuid_ VARCHAR(75) null,
	segmentsExperimentId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	segmentsEntryId LONG,
	segmentsExperienceId LONG,
	segmentsExperimentKey VARCHAR(75) null,
	classNameId LONG,
	classPK LONG,
	name VARCHAR(75) null,
	description STRING null,
	typeSettings TEXT null,
	status INTEGER
);

create index IX_C7EDCF7 on SegmentsExperiment (classNameId, classPK, segmentsExperienceId, status);
create index IX_FE51B455 on SegmentsExperiment (groupId, classNameId, classPK, segmentsExperienceId);
create index IX_8D0D6955 on SegmentsExperiment (groupId, segmentsExperienceId, classNameId, classPK);
create unique index IX_72C2980B on SegmentsExperiment (groupId, segmentsExperimentKey[$COLUMN_LENGTH:75$]);
create index IX_12B591F7 on SegmentsExperiment (segmentsExperienceId, classNameId, classPK, status);
create index IX_2FF139A2 on SegmentsExperiment (segmentsExperienceId, status);
create index IX_127B4FCF on SegmentsExperiment (segmentsExperimentKey[$COLUMN_LENGTH:75$]);
create index IX_DECD8397 on SegmentsExperiment (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_284003D9 on SegmentsExperiment (uuid_[$COLUMN_LENGTH:75$], groupId);

create table SegmentsExperimentRel (
	segmentsExperimentRelId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	segmentsExperimentId LONG,
	segmentsExperienceId LONG,
	split DOUBLE
);

create unique index IX_52421287 on SegmentsExperimentRel (segmentsExperimentId, segmentsExperienceId);

COMMIT_TRANSACTION;