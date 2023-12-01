create index IX_F6C6095A on SXPBlueprint (companyId);
create index IX_B51FB76F on SXPBlueprint (externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_D45697E6 on SXPBlueprint (uuid_[$COLUMN_LENGTH:75$]);

create index IX_62CF31E7 on SXPElement (companyId, readOnly);
create index IX_2F49914A on SXPElement (companyId, type_, status);
create index IX_81B9D9E6 on SXPElement (externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_47DA885D on SXPElement (uuid_[$COLUMN_LENGTH:75$]);