create index IX_13688454 on LayoutPageTemplateEntry (groupId, layoutPageTemplateFolderId, name[$COLUMN_LENGTH:75$]);
create unique index IX_A075DAA4 on LayoutPageTemplateEntry (groupId, name[$COLUMN_LENGTH:75$]);

create unique index IX_952693A2 on LayoutPageTemplateFolder (groupId, name[$COLUMN_LENGTH:75$]);

create index IX_F1F89D0C on LayoutPageTemplateFragment (groupId, fragmentEntryId);
create index IX_33E03209 on LayoutPageTemplateFragment (groupId, layoutPageTemplateEntryId);