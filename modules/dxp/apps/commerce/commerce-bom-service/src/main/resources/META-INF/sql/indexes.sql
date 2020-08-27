create index IX_5BB752BD on CBOMFolderApplicationRel (commerceApplicationModelId);
create index IX_E89B4704 on CBOMFolderApplicationRel (commerceBOMFolderId);

create index IX_585FB5E0 on CommerceBOMDefinition (commerceBOMFolderId);

create index IX_9E3B4DF0 on CommerceBOMEntry (commerceBOMDefinitionId);

create index IX_BD662485 on CommerceBOMFolder (companyId, parentCommerceBOMFolderId);