create unique index IX_8323E5F7 on LayoutCanonicalURL (groupId, privateLayout, layoutId);
create index IX_3D1A37FF on LayoutCanonicalURL (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_5B7EB241 on LayoutCanonicalURL (uuid_[$COLUMN_LENGTH:75$], groupId);