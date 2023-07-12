create index IX_F925260 on CTAutoResolutionInfo (ctCollectionId, modelClassNameId, sourceModelClassPK);

create index IX_8D52E6F9 on CTCollection (companyId, status);
create unique index IX_19065F80 on CTCollection (externalReferenceCode[$COLUMN_LENGTH:75$], companyId);
create index IX_CCDD86CD on CTCollection (schemaVersionId);
create index IX_A5CE8CA9 on CTCollection (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_489283B9 on CTCollectionTemplate (companyId);

create index IX_FE644B52 on CTComment (ctCollectionId);
create index IX_C5E592B8 on CTComment (ctEntryId);

create unique index IX_295C418C on CTEntry (ctCollectionId, modelClassNameId, modelClassPK);

create index IX_9FB742FA on CTMessage (ctCollectionId);

create unique index IX_516E5375 on CTPreferences (companyId, userId);
create index IX_3FECC82B on CTPreferences (ctCollectionId);
create index IX_D9EA7A42 on CTPreferences (previousCtCollectionId);

create index IX_7523B0A4 on CTProcess (companyId);
create index IX_46BA2033 on CTProcess (ctCollectionId, type_);
create index IX_5F9B5D3E on CTProcess (userId);

create index IX_9B9391EB on CTRemote (companyId);

create index IX_687AE35C on CTSchemaVersion (companyId);