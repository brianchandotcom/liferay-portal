create unique index IX_D569E8F2 on LayoutPageTemplateCollection (groupId, name[$COLUMN_LENGTH:75$]);

create index IX_957F6C5D on LayoutPageTemplateEntry (groupId, classNameId, classTypeId, defaultTemplate, status);
create index IX_E2488048 on LayoutPageTemplateEntry (groupId, classNameId, classTypeId, name[$COLUMN_LENGTH:75$], type_, status);
create index IX_227636E7 on LayoutPageTemplateEntry (groupId, classNameId, classTypeId, type_, status);
create index IX_1736F4A2 on LayoutPageTemplateEntry (groupId, classNameId, defaultTemplate);
create index IX_4C3A286A on LayoutPageTemplateEntry (groupId, layoutPageTemplateCollectionId, name[$COLUMN_LENGTH:75$], status);
create index IX_A4733F6B on LayoutPageTemplateEntry (groupId, layoutPageTemplateCollectionId, status);
create unique index IX_A075DAA4 on LayoutPageTemplateEntry (groupId, name[$COLUMN_LENGTH:75$]);
create index IX_1F1BEA76 on LayoutPageTemplateEntry (groupId, type_, status);