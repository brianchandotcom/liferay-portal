create unique index IX_371B1831 on LayoutSEOEntry (groupId, privateLayout, layoutId);
create index IX_D9211E39 on LayoutSEOEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_429DDEFB on LayoutSEOEntry (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_EB206388 on SiteSEOEntry (groupId);
create index IX_E3FB6CD6 on SiteSEOEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_8A0530D8 on SiteSEOEntry (uuid_[$COLUMN_LENGTH:75$], groupId);