create index IX_11EC38E2 on SegmentsEntry (active_, type_[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_543DF7AE on SegmentsEntry (companyId, ctCollectionId);
create index IX_780F47D5 on SegmentsEntry (groupId, active_, ctCollectionId);
create index IX_5A459E2D on SegmentsEntry (groupId, active_, source[$COLUMN_LENGTH:75$], type_[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_575940DC on SegmentsEntry (groupId, active_, type_[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_A4354E30 on SegmentsEntry (groupId, ctCollectionId);
create unique index IX_DB53F1B1 on SegmentsEntry (groupId, segmentsEntryKey[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_B65DAD05 on SegmentsEntry (source[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_96AE375B on SegmentsEntry (type_[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_D7DEE62A on SegmentsEntry (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_86F8593A on SegmentsEntry (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_CEF7ABAC on SegmentsEntry (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_694665A0 on SegmentsEntryRel (classNameId, classPK, ctCollectionId);
create index IX_42A8B790 on SegmentsEntryRel (groupId, classNameId, classPK, ctCollectionId);
create unique index IX_AAD22503 on SegmentsEntryRel (segmentsEntryId, classNameId, classPK, ctCollectionId);
create index IX_2CA498AE on SegmentsEntryRel (segmentsEntryId, ctCollectionId);

create index IX_156C5BB1 on SegmentsEntryRole (roleId, ctCollectionId);
create index IX_A4591B8D on SegmentsEntryRole (segmentsEntryId, ctCollectionId);
create unique index IX_2876B1F2 on SegmentsEntryRole (segmentsEntryId, roleId, ctCollectionId);

create index IX_874CAE78 on SegmentsExperience (groupId, ctCollectionId);
create index IX_1166B022 on SegmentsExperience (groupId, plid, active_, ctCollectionId);
create index IX_3231EB43 on SegmentsExperience (groupId, plid, ctCollectionId);
create unique index IX_7F495C9B on SegmentsExperience (groupId, plid, priority, ctCollectionId);
create index IX_1BAAE099 on SegmentsExperience (groupId, segmentsEntryId, plid, active_, ctCollectionId);
create index IX_E73A08EC on SegmentsExperience (groupId, segmentsEntryId, plid, ctCollectionId);
create unique index IX_A4991554 on SegmentsExperience (groupId, segmentsExperienceKey[$COLUMN_LENGTH:75$], plid, ctCollectionId);
create index IX_BAA8E72B on SegmentsExperience (segmentsEntryId, ctCollectionId);
create index IX_BDBB56E2 on SegmentsExperience (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_7F7B2B82 on SegmentsExperience (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_3C28EA64 on SegmentsExperience (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_B2F2805 on SegmentsExperiment (groupId, ctCollectionId);
create index IX_B9644710 on SegmentsExperiment (groupId, plid, ctCollectionId);
create unique index IX_5CB505A9 on SegmentsExperiment (groupId, segmentsExperienceId, plid, ctCollectionId);
create unique index IX_9749F869 on SegmentsExperiment (groupId, segmentsExperimentKey[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_D6C79065 on SegmentsExperiment (segmentsExperienceId, plid, ctCollectionId);
create index IX_69EC4C4B on SegmentsExperiment (segmentsExperienceId, plid, status, ctCollectionId);
create index IX_2FF139A2 on SegmentsExperiment (segmentsExperienceId, status);
create index IX_F6C2A82D on SegmentsExperiment (segmentsExperimentKey[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_BB044BF5 on SegmentsExperiment (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_E4D8A44F on SegmentsExperiment (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_9B420837 on SegmentsExperiment (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_76A679B9 on SegmentsExperimentRel (segmentsExperienceId, ctCollectionId);
create index IX_3FA9F4CC on SegmentsExperimentRel (segmentsExperimentId, ctCollectionId);
create unique index IX_9EDCFAE5 on SegmentsExperimentRel (segmentsExperimentId, segmentsExperienceId, ctCollectionId);