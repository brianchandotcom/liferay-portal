create index IX_97417FEE on FriendlyURL (companyId, groupId, classNameId, classPK, main);
create index IX_DD283EDE on FriendlyURL (companyId, groupId, classNameId, classPK, urlTitle[$COLUMN_LENGTH:75$]);
create index IX_F32A413F on FriendlyURL (companyId, groupId, classNameId, urlTitle[$COLUMN_LENGTH:75$]);
create index IX_C9E74CCE on FriendlyURL (groupId, classNameId);
create index IX_BBBC6ADE on FriendlyURL (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_1C7E10E0 on FriendlyURL (uuid_[$COLUMN_LENGTH:75$], groupId);