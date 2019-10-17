create unique index IX_A5521169 on InfoItemUsage (classNameId, classPK, containerKey[$COLUMN_LENGTH:75$], containerType, plid);
create unique index IX_4A70EE8D on InfoItemUsage (classNameId, classPK, containerType, containerKey[$COLUMN_LENGTH:75$], plid);
create index IX_550A3EBA on InfoItemUsage (classNameId, classPK, type_);
create index IX_DCFFEB5E on InfoItemUsage (containerKey[$COLUMN_LENGTH:75$], containerType, plid);
create index IX_821EC882 on InfoItemUsage (containerType, containerKey[$COLUMN_LENGTH:75$], plid);
create index IX_B7FAB1 on InfoItemUsage (plid);
create unique index IX_B80DFA5C on InfoItemUsage (uuid_[$COLUMN_LENGTH:75$], groupId);