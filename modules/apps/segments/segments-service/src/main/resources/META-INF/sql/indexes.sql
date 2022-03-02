create index IX_11EC38E2 on SegmentsEntry (active_, type_[$COLUMN_LENGTH:75$], ctCollectionId);
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

create index IX_99B31F58 on SegmentsExperience (groupId, classNameId, classPK, active_, ctCollectionId);
create index IX_B42F674D on SegmentsExperience (groupId, classNameId, classPK, ctCollectionId);
create unique index IX_8AD425 on SegmentsExperience (groupId, classNameId, classPK, priority, ctCollectionId);
create index IX_874CAE78 on SegmentsExperience (groupId, ctCollectionId);
create index IX_F48D81CF on SegmentsExperience (groupId, segmentsEntryId, classNameId, classPK, active_, ctCollectionId);
create index IX_D78112F6 on SegmentsExperience (groupId, segmentsEntryId, classNameId, classPK, ctCollectionId);
create unique index IX_B24A4F5E on SegmentsExperience (groupId, segmentsExperienceKey[$COLUMN_LENGTH:75$], classNameId, classPK, ctCollectionId);
create index IX_BAA8E72B on SegmentsExperience (segmentsEntryId, ctCollectionId);
create index IX_BDBB56E2 on SegmentsExperience (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_7F7B2B82 on SegmentsExperience (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_3C28EA64 on SegmentsExperience (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_C7EDCF7 on SegmentsExperiment (classNameId, classPK, segmentsExperienceId, status);
create index IX_9152891A on SegmentsExperiment (groupId, classNameId, classPK, ctCollectionId);
create index IX_FE51B455 on SegmentsExperiment (groupId, classNameId, classPK, segmentsExperienceId);
create index IX_B2F2805 on SegmentsExperiment (groupId, ctCollectionId);
create index IX_8D0D6955 on SegmentsExperiment (groupId, segmentsExperienceId, classNameId, classPK);
create unique index IX_9749F869 on SegmentsExperiment (groupId, segmentsExperimentKey[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_C16A886F on SegmentsExperiment (segmentsExperienceId, classNameId, classPK, ctCollectionId);
create index IX_A8C39A55 on SegmentsExperiment (segmentsExperienceId, classNameId, classPK, status, ctCollectionId);
create index IX_2FF139A2 on SegmentsExperiment (segmentsExperienceId, status);
create index IX_F6C2A82D on SegmentsExperiment (segmentsExperimentKey[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_BB044BF5 on SegmentsExperiment (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_E4D8A44F on SegmentsExperiment (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_9B420837 on SegmentsExperiment (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_3FA9F4CC on SegmentsExperimentRel (segmentsExperimentId, ctCollectionId);
create unique index IX_9EDCFAE5 on SegmentsExperimentRel (segmentsExperimentId, segmentsExperienceId, ctCollectionId);