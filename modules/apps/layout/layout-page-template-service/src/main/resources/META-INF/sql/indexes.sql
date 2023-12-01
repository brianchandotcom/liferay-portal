create index IX_5A1F4BFC on LayoutPageTemplateCollection (groupId, parentLPTCollectionId);
create unique index IX_F074765 on LayoutPageTemplateCollection (groupId, type_, ctCollectionId, lptCollectionKey[$COLUMN_LENGTH:75$]);
create unique index IX_E6EE511F on LayoutPageTemplateCollection (groupId, type_, ctCollectionId, name[$COLUMN_LENGTH:75$]);
create index IX_A17F0EBD on LayoutPageTemplateCollection (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_E4BCB00E on LayoutPageTemplateEntry (ctCollectionId, plid);
create index IX_A6459477 on LayoutPageTemplateEntry (groupId, classNameId, classTypeId, defaultTemplate);
create index IX_F2B223DD on LayoutPageTemplateEntry (groupId, classNameId, status, classTypeId, defaultTemplate);
create unique index IX_246C8117 on LayoutPageTemplateEntry (groupId, ctCollectionId, layoutPageTemplateEntryKey[$COLUMN_LENGTH:75$]);
create index IX_E7CC5585 on LayoutPageTemplateEntry (groupId, layoutPageTemplateCollectionId);
create index IX_FFE79984 on LayoutPageTemplateEntry (groupId, name[$COLUMN_LENGTH:75$], layoutPageTemplateCollectionId);
create index IX_DB1B076B on LayoutPageTemplateEntry (groupId, status, layoutPageTemplateCollectionId);
create index IX_7686C6EA on LayoutPageTemplateEntry (groupId, status, name[$COLUMN_LENGTH:75$], layoutPageTemplateCollectionId);
create index IX_F406284D on LayoutPageTemplateEntry (groupId, type_, classNameId, defaultTemplate);
create index IX_459D50A0 on LayoutPageTemplateEntry (groupId, type_, classNameId, name[$COLUMN_LENGTH:75$], classTypeId);
create index IX_F94E49FA on LayoutPageTemplateEntry (groupId, type_, classNameId, status, name[$COLUMN_LENGTH:75$], classTypeId);
create index IX_CD9D4A70 on LayoutPageTemplateEntry (groupId, type_, layoutPageTemplateCollectionId);
create unique index IX_BC5F7AD on LayoutPageTemplateEntry (groupId, type_, name[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_9C2D0C95 on LayoutPageTemplateEntry (groupId, type_, status, defaultTemplate);
create index IX_A185457E on LayoutPageTemplateEntry (layoutPrototypeId);
create index IX_2D68D26F on LayoutPageTemplateEntry (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_D822FD2D on LayoutPageTemplateStructure (groupId, plid, ctCollectionId);
create index IX_542ECD0E on LayoutPageTemplateStructure (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_AFE18D91 on LayoutPageTemplateStructureRel (segmentsExperienceId, layoutPageTemplateStructureId, ctCollectionId);
create index IX_E86D94F5 on LayoutPageTemplateStructureRel (uuid_[$COLUMN_LENGTH:75$]);