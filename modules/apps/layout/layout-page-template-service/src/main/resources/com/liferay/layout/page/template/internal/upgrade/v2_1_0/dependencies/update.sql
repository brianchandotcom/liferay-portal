create table LayoutPageTemplateStructureRel (
	uuid_ VARCHAR(75) null,
	lPageTemplateStructureRelId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	layoutPageTemplateStructureId LONG,
	segmentsExperienceId LONG,
	data_ VARCHAR(75) null
);

create unique index IX_BB165B45 on LayoutPageTemplateStructureRel (layoutPageTemplateStructureId, segmentsExperienceId);
create index IX_6F8B3413 on LayoutPageTemplateStructureRel (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_2467A355 on LayoutPageTemplateStructureRel (uuid_[$COLUMN_LENGTH:75$], groupId);

COMMIT_TRANSACTION;