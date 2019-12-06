create unique index IX_3D6FF6 on LayoutPageTemplateCollection (groupId, name[$COLUMN_LENGTH:75$], companyId);
create index IX_BCD4D4B on LayoutPageTemplateCollection (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_17152F7B on LayoutPageTemplateCollection (uuid_[$COLUMN_LENGTH:75$], groupId, companyId);

create index IX_957F6C5D on LayoutPageTemplateEntry (groupId, classNameId, classTypeId, defaultTemplate, status);
create index IX_E2488048 on LayoutPageTemplateEntry (groupId, classNameId, classTypeId, name[$COLUMN_LENGTH:75$], type_, status);
create index IX_227636E7 on LayoutPageTemplateEntry (groupId, classNameId, classTypeId, type_, status);
create index IX_CD171EDF on LayoutPageTemplateEntry (groupId, classNameId, type_, defaultTemplate);
create index IX_4C3A286A on LayoutPageTemplateEntry (groupId, layoutPageTemplateCollectionId, name[$COLUMN_LENGTH:75$], status);
create index IX_A4733F6B on LayoutPageTemplateEntry (groupId, layoutPageTemplateCollectionId, status);
create index IX_4BCAC4B0 on LayoutPageTemplateEntry (groupId, layoutPageTemplateCollectionId, type_);
create unique index IX_29028CD7 on LayoutPageTemplateEntry (groupId, name[$COLUMN_LENGTH:75$], type_, companyId);
create index IX_97370C97 on LayoutPageTemplateEntry (groupId, name[$COLUMN_LENGTH:75$], type_, status);
create index IX_1F1BEA76 on LayoutPageTemplateEntry (groupId, type_, status);
create index IX_A185457E on LayoutPageTemplateEntry (layoutPrototypeId);
create unique index IX_FB60C3F8 on LayoutPageTemplateEntry (plid, companyId);
create index IX_CEC0A659 on LayoutPageTemplateEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_D9DA262D on LayoutPageTemplateEntry (uuid_[$COLUMN_LENGTH:75$], groupId, companyId);

create unique index IX_6DB7D1AF on LayoutPageTemplateStructure (groupId, classNameId, classPK, companyId);
create index IX_6DB0225A on LayoutPageTemplateStructure (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_1EDF9B4C on LayoutPageTemplateStructure (uuid_[$COLUMN_LENGTH:75$], groupId, companyId);

create unique index IX_B14E5BC3 on LayoutPageTemplateStructureRel (layoutPageTemplateStructureId, segmentsExperienceId, companyId);
create index IX_12808938 on LayoutPageTemplateStructureRel (segmentsExperienceId);
create index IX_6F8B3413 on LayoutPageTemplateStructureRel (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_6CFA69B3 on LayoutPageTemplateStructureRel (uuid_[$COLUMN_LENGTH:75$], groupId, companyId);