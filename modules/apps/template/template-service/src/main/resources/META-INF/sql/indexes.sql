create index IX_F001812 on TemplateEntry (ddmTemplateId, ctCollectionId);
create index IX_9126EB0A on TemplateEntry (groupId, ctCollectionId);
create index IX_240F43A4 on TemplateEntry (groupId, infoItemClassName[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_BB9EE09 on TemplateEntry (groupId, infoItemClassName[$COLUMN_LENGTH:75$], infoItemFormVariationKey[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_B1871E90 on TemplateEntry (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_F97F6494 on TemplateEntry (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_4DFEC592 on TemplateEntry (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);