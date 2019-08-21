create unique index IX_A0CFE092 on CTCollection (companyId, name[$COLUMN_LENGTH:75$]);
create index IX_8D52E6F9 on CTCollection (companyId, status);

create unique index IX_295C418C on CTEntry (ctCollectionId, modelClassNameId, modelClassPK);
create index IX_CE8C7174 on CTEntry (ctCollectionId, modelClassNameId, status);
create index IX_BA657F81 on CTEntry (ctCollectionId, modelResourcePrimKey, status);
create index IX_20AB084B on CTEntry (ctCollectionId, status);

create unique index IX_516E5375 on CTPreferences (companyId, userId);
create index IX_3FECC82B on CTPreferences (ctCollectionId);

create index IX_7523B0A4 on CTProcess (companyId);
create index IX_B4859762 on CTProcess (ctCollectionId);
create index IX_5F9B5D3E on CTProcess (userId);