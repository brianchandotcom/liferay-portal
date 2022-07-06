create index IX_684FC85D on ObjectAction (objectDefinitionId, active_, objectActionTriggerKey[$COLUMN_LENGTH:75$]);
create index IX_BA9B4A2F on ObjectAction (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_384C6F1F on ObjectDefinition (companyId, active_, status);
create index IX_5C293E0D on ObjectDefinition (companyId, active_, system_, status);
create index IX_2A008543 on ObjectDefinition (companyId, className[$COLUMN_LENGTH:75$]);
create index IX_3E56F38F on ObjectDefinition (companyId, name[$COLUMN_LENGTH:75$]);
create index IX_55C39BCE on ObjectDefinition (system_, status);
create index IX_B929D94C on ObjectDefinition (uuid_[$COLUMN_LENGTH:75$], companyId);

create unique index IX_FDFF4146 on ObjectEntry (groupId, companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_7403EBB8 on ObjectEntry (groupId, objectDefinitionId, status);
create index IX_A388E5A0 on ObjectEntry (objectDefinitionId, status);
create index IX_897D0EF4 on ObjectEntry (userId, objectDefinitionId);
create index IX_49B9450D on ObjectEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_5AF9AACF on ObjectEntry (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_8BEECA97 on ObjectField (companyId, externalReferenceCode[$COLUMN_LENGTH:75$], objectDefinitionId);
create index IX_6DCE835D on ObjectField (listTypeDefinitionId, state_);
create index IX_5DDCF209 on ObjectField (objectDefinitionId, dbTableName[$COLUMN_LENGTH:75$]);
create index IX_979A9013 on ObjectField (objectDefinitionId, dbType[$COLUMN_LENGTH:75$], indexed);
create index IX_A59C5981 on ObjectField (objectDefinitionId, name[$COLUMN_LENGTH:75$]);
create index IX_4A69C63E on ObjectField (objectDefinitionId, system_);
create index IX_594B4995 on ObjectField (uuid_[$COLUMN_LENGTH:75$], companyId);

create unique index IX_BB322D4A on ObjectFieldSetting (objectFieldId, name[$COLUMN_LENGTH:75$]);
create index IX_46FCF8AF on ObjectFieldSetting (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_FD0CCE8A on ObjectLayout (objectDefinitionId, defaultObjectLayout);
create index IX_E27AC523 on ObjectLayout (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_5F97F7CF on ObjectLayoutBox (objectLayoutTabId);
create index IX_CDCBE8DC on ObjectLayoutBox (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_E992BFE1 on ObjectLayoutColumn (objectFieldId);
create index IX_46CE5537 on ObjectLayoutColumn (objectLayoutRowId);
create index IX_53BB1AD9 on ObjectLayoutColumn (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_FA14DE56 on ObjectLayoutRow (objectLayoutBoxId);
create index IX_644C876B on ObjectLayoutRow (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_F01F1EEA on ObjectLayoutTab (objectLayoutId);
create index IX_4CC508B8 on ObjectLayoutTab (objectRelationshipId);
create index IX_94D361A6 on ObjectLayoutTab (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_A71785B6 on ObjectRelationship (objectDefinitionId1, name[$COLUMN_LENGTH:75$]);
create index IX_59059880 on ObjectRelationship (objectDefinitionId1, objectDefinitionId2, name[$COLUMN_LENGTH:75$], reverse, type_[$COLUMN_LENGTH:75$]);
create index IX_F6F370B8 on ObjectRelationship (objectDefinitionId1, objectDefinitionId2, name[$COLUMN_LENGTH:75$], type_[$COLUMN_LENGTH:75$]);
create index IX_2A3F6ED7 on ObjectRelationship (objectDefinitionId1, objectDefinitionId2, type_[$COLUMN_LENGTH:75$]);
create index IX_DE3EBEF8 on ObjectRelationship (objectDefinitionId2);
create index IX_F1DC092D on ObjectRelationship (objectFieldId2);
create index IX_11DAE1F1 on ObjectRelationship (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_C34F0F9E on ObjectState (listTypeEntryId, objectStateFlowId);
create index IX_F9D4BA53 on ObjectState (objectStateFlowId);
create index IX_FBD0BBAC on ObjectState (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_AE828160 on ObjectStateFlow (objectFieldId);
create index IX_C6174FA on ObjectStateFlow (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_DB56B27E on ObjectStateTransition (objectStateFlowId);
create index IX_9C3FAB55 on ObjectStateTransition (sourceObjectStateId);
create index IX_FB9AC71F on ObjectStateTransition (targetObjectStateId);
create index IX_4D699221 on ObjectStateTransition (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_C476B36E on ObjectValidationRule (objectDefinitionId, active_);
create index IX_40F1E68E on ObjectValidationRule (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_6AF6C9EA on ObjectView (objectDefinitionId, defaultObjectView);
create index IX_C771CDE on ObjectView (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_B7B14E3 on ObjectViewColumn (objectViewId, objectFieldName[$COLUMN_LENGTH:75$]);
create index IX_F3077A54 on ObjectViewColumn (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_B8CD6D4B on ObjectViewFilterColumn (objectViewId, objectFieldName[$COLUMN_LENGTH:75$]);
create index IX_3C598EC on ObjectViewFilterColumn (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_55C88365 on ObjectViewSortColumn (objectViewId, objectFieldName[$COLUMN_LENGTH:75$]);
create index IX_29BEA092 on ObjectViewSortColumn (uuid_[$COLUMN_LENGTH:75$], companyId);