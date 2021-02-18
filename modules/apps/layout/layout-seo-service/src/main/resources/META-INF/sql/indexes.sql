create unique index IX_93D96C8F on LayoutSEOEntry (groupId, privateLayout, layoutId, ctCollectionId);
create index IX_EA626297 on LayoutSEOEntry (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_45265EED on LayoutSEOEntry (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_63195F59 on LayoutSEOEntry (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create unique index IX_E4DFAF28 on LayoutSEOSite (groupId, ctCollectionId);
create index IX_E8061C32 on LayoutSEOSite (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_9E369832 on LayoutSEOSite (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);