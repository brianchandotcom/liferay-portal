create index IX_1ED300B1 on SharingEntry (classNameId, classPK);
create index IX_CC1C3576 on SharingEntry (classNameId, companyId);
create unique index IX_129770E8 on SharingEntry (classNameId, toUserId, classPK, toUserGroupId);
create index IX_8E0359AC on SharingEntry (classNameId, userId);
create index IX_1E35B88D on SharingEntry (expirationDate);
create unique index IX_FA5E24AF on SharingEntry (groupId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_C024CFB1 on SharingEntry (toUserId);
create index IX_EA2FF796 on SharingEntry (userId);
create unique index IX_5EDE78D2 on SharingEntry (uuid_[$COLUMN_LENGTH:75$], groupId);