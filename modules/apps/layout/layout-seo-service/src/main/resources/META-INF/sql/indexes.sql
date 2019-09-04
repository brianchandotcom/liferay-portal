create unique index IX_371B1831 on LayoutSEOEntry (groupId, privateLayout, layoutId);
create index IX_D9211E39 on LayoutSEOEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_429DDEFB on LayoutSEOEntry (uuid_[$COLUMN_LENGTH:75$], groupId);