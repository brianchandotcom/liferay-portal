create index IX_F925260 on CTAutoResolutionInfo (ctCollectionId, modelClassNameId, sourceModelClassPK);

create index IX_8D52E6F9 on CTCollection (companyId, status);
create index IX_CCDD86CD on CTCollection (schemaVersionId);

create unique index IX_295C418C on CTEntry (ctCollectionId, modelClassNameId, modelClassPK);

create index IX_9FB742FA on CTMessage (ctCollectionId);

create unique index IX_516E5375 on CTPreferences (companyId, userId);
create index IX_3FECC82B on CTPreferences (ctCollectionId);
create index IX_D9EA7A42 on CTPreferences (previousCtCollectionId);

create index IX_7523B0A4 on CTProcess (companyId);
create index IX_B4859762 on CTProcess (ctCollectionId);
create index IX_5F9B5D3E on CTProcess (userId);

create index IX_687AE35C on CTSchemaVersion (companyId);