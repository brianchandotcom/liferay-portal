create index IX_9B9729B4 on TemplateEntry (ddmTemplateId);
create index IX_D011CDAB on TemplateEntry (groupId, infoItemClassName[$COLUMN_LENGTH:75$], infoItemFormVariationKey[$COLUMN_LENGTH:75$]);
create index IX_E46EC32 on TemplateEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_ECA0D734 on TemplateEntry (uuid_[$COLUMN_LENGTH:75$], groupId);