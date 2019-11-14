create unique index IX_371B1831 on LayoutSEOEntry (groupId, privateLayout, layoutId);
create index IX_D9211E39 on LayoutSEOEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_429DDEFB on LayoutSEOEntry (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_6E43ACCA on LayoutSEOSite (groupId);
create index IX_24696DD4 on LayoutSEOSite (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_C75B5956 on LayoutSEOSite (uuid_[$COLUMN_LENGTH:75$], groupId);