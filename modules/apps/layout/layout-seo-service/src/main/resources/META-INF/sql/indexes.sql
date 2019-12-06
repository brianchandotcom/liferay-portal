create unique index IX_D8009357 on LayoutSEOEntry (groupId, privateLayout, layoutId, companyId);
create index IX_D9211E39 on LayoutSEOEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_5F528A4D on LayoutSEOEntry (uuid_[$COLUMN_LENGTH:75$], groupId, companyId);

create unique index IX_649F551E on LayoutSEOSite (groupId, companyId);
create index IX_24696DD4 on LayoutSEOSite (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_D76A9912 on LayoutSEOSite (uuid_[$COLUMN_LENGTH:75$], groupId, companyId);