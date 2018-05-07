create unique index IX_D569E8F2 on LayoutPageTemplateCollection (groupId, name[$COLUMN_LENGTH:75$]);

create index IX_A6459477 on LayoutPageTemplateEntry (groupId, classNameId, classTypeId, defaultTemplate);
create index IX_614AC362 on LayoutPageTemplateEntry (groupId, classNameId, classTypeId, name[$COLUMN_LENGTH:75$], type_);
create index IX_B28C8901 on LayoutPageTemplateEntry (groupId, classNameId, classTypeId, type_);
create index IX_1736F4A2 on LayoutPageTemplateEntry (groupId, classNameId, defaultTemplate);
create index IX_30AFAD84 on LayoutPageTemplateEntry (groupId, layoutPageTemplateCollectionId, name[$COLUMN_LENGTH:75$]);
create unique index IX_A075DAA4 on LayoutPageTemplateEntry (groupId, name[$COLUMN_LENGTH:75$]);
create index IX_D4CA3B90 on LayoutPageTemplateEntry (groupId, type_);