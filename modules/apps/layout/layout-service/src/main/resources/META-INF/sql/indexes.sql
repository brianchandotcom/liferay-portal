create unique index IX_FA38EE24 on LayoutClassedModelUsage (classNameId, classPK, containerKey[$COLUMN_LENGTH:200$], containerType, plid);
create index IX_B041F1F5 on LayoutClassedModelUsage (classNameId, classPK, type_);
create index IX_DF750659 on LayoutClassedModelUsage (containerKey[$COLUMN_LENGTH:200$], containerType, plid);
create index IX_19448DD6 on LayoutClassedModelUsage (plid);
create index IX_F2FE8FF on LayoutClassedModelUsage (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_694CA341 on LayoutClassedModelUsage (uuid_[$COLUMN_LENGTH:75$], groupId);