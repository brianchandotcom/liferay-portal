create unique index IX_93D96C8F on LayoutSEOEntry (groupId, privateLayout, layoutId, ctCollectionId);
create index IX_7EA4868F on LayoutSEOEntry (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_E4DFAF28 on LayoutSEOSite (groupId, ctCollectionId);
create index IX_E311E9D4 on LayoutSEOSite (uuid_[$COLUMN_LENGTH:75$]);