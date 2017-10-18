create unique index IX_D569E8F2 on LayoutPageTemplateCollection (groupId, name[$COLUMN_LENGTH:75$]);

create index IX_30AFAD84 on LayoutPageTemplateEntry (groupId, layoutPageTemplateCollectionId, name[$COLUMN_LENGTH:75$]);
create unique index IX_A075DAA4 on LayoutPageTemplateEntry (groupId, name[$COLUMN_LENGTH:75$]);

create index IX_F1F89D0C on LayoutPageTemplateFragment (groupId, fragmentEntryId);
create unique index IX_3D504FC4 on LayoutPageTemplateFragment (groupId, layoutPageTemplateEntryId, fragmentEntryId);