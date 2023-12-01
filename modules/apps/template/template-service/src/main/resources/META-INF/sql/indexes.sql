create index IX_9B9729B4 on TemplateEntry (ddmTemplateId);
create index IX_D011CDAB on TemplateEntry (groupId, infoItemClassName[$COLUMN_LENGTH:75$], infoItemFormVariationKey[$COLUMN_LENGTH:75$]);
create index IX_3AF3BA36 on TemplateEntry (uuid_[$COLUMN_LENGTH:75$]);