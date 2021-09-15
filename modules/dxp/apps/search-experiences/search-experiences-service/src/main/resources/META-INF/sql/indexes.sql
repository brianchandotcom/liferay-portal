create index IX_F6C6095A on SXPBlueprint (companyId);
create index IX_11ED4142 on SXPBlueprint (groupId, status);
create index IX_C989A082 on SXPBlueprint (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_1AA45F84 on SXPBlueprint (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_6EA5F664 on SXPElement (companyId, type_);
create index IX_A9C3C6BC on SXPElement (groupId, status, type_);
create index IX_F2BB5162 on SXPElement (groupId, type_);
create index IX_34C38FAB on SXPElement (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_52B204ED on SXPElement (uuid_[$COLUMN_LENGTH:75$], groupId);