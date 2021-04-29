create unique index IX_87C5B232 on DatasetViewActiveEntry (userId, datasetDisplayId[$COLUMN_LENGTH:75$], plid, portletId[$COLUMN_LENGTH:75$]);
create index IX_4732B469 on DatasetViewActiveEntry (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_8ED9F698 on DatasetViewStateEntry (uuid_[$COLUMN_LENGTH:75$], companyId);