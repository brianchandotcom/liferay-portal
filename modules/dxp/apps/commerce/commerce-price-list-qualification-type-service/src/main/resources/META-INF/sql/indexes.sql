create index IX_297DE707 on CommercePriceListUserRel (CPLQualificationTypeRelId, classNameId, classPK);
create index IX_ED1D6D9C on CommercePriceListUserRel (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_7B1CAB1E on CommercePriceListUserRel (uuid_[$COLUMN_LENGTH:75$], groupId);