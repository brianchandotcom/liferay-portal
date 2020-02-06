create unique index IX_FBD6BAB8 on LayoutPageTemplateCollection (groupId, lptCollectionKey[$COLUMN_LENGTH:75$]);
create unique index IX_D569E8F2 on LayoutPageTemplateCollection (groupId, name[$COLUMN_LENGTH:75$]);
create index IX_BCD4D4B on LayoutPageTemplateCollection (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_9E4EAA8D on LayoutPageTemplateCollection (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_957F6C5D on LayoutPageTemplateEntry (groupId, classNameId, classTypeId, defaultTemplate, status);
create index IX_E2488048 on LayoutPageTemplateEntry (groupId, classNameId, classTypeId, name[$COLUMN_LENGTH:75$], type_, status);
create index IX_227636E7 on LayoutPageTemplateEntry (groupId, classNameId, classTypeId, type_, status);
create index IX_CD171EDF on LayoutPageTemplateEntry (groupId, classNameId, type_, defaultTemplate);
create index IX_4C3A286A on LayoutPageTemplateEntry (groupId, layoutPageTemplateCollectionId, name[$COLUMN_LENGTH:75$], status);
create index IX_A4733F6B on LayoutPageTemplateEntry (groupId, layoutPageTemplateCollectionId, status);
create index IX_4BCAC4B0 on LayoutPageTemplateEntry (groupId, layoutPageTemplateCollectionId, type_);
create unique index IX_DB7DADB9 on LayoutPageTemplateEntry (groupId, layoutPageTemplateEntryKey[$COLUMN_LENGTH:75$]);
create unique index IX_C3960EB1 on LayoutPageTemplateEntry (groupId, name[$COLUMN_LENGTH:75$], type_);
create index IX_85E526A1 on LayoutPageTemplateEntry (groupId, type_, defaultTemplate, status);
create index IX_1F1BEA76 on LayoutPageTemplateEntry (groupId, type_, status);
create index IX_A185457E on LayoutPageTemplateEntry (layoutPrototypeId);
create unique index IX_84D30230 on LayoutPageTemplateEntry (plid);
create index IX_CEC0A659 on LayoutPageTemplateEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_34C0EF1B on LayoutPageTemplateEntry (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_87B60D9 on LayoutPageTemplateStructure (groupId, classNameId, classPK);
create index IX_6DB0225A on LayoutPageTemplateStructure (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_4DB1775C on LayoutPageTemplateStructure (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_BB165B45 on LayoutPageTemplateStructureRel (layoutPageTemplateStructureId, segmentsExperienceId);
create index IX_12808938 on LayoutPageTemplateStructureRel (segmentsExperienceId);
create index IX_6F8B3413 on LayoutPageTemplateStructureRel (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_2467A355 on LayoutPageTemplateStructureRel (uuid_[$COLUMN_LENGTH:75$], groupId);