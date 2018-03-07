create unique index IX_D569E8F2 on LayoutPageTemplateCollection (groupId, name[$COLUMN_LENGTH:75$]);
create index IX_3E57F702 on LayoutPageTemplateCollection (groupId, type_);

create index IX_1736F4A2 on LayoutPageTemplateEntry (groupId, classNameId, defaultTemplate);
create index IX_30AFAD84 on LayoutPageTemplateEntry (groupId, layoutPageTemplateCollectionId, name[$COLUMN_LENGTH:75$]);
create unique index IX_A075DAA4 on LayoutPageTemplateEntry (groupId, name[$COLUMN_LENGTH:75$]);