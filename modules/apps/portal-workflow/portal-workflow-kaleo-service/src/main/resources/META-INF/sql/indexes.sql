create index IX_19440F8A on KaleoAction (companyId, ctCollectionId);
create index IX_6B7ACF88 on KaleoAction (companyId, kaleoClassName[$COLUMN_LENGTH:200$], kaleoClassPK, ctCollectionId);
create index IX_8D4C696 on KaleoAction (companyId, kaleoClassName[$COLUMN_LENGTH:200$], kaleoClassPK, executionType[$COLUMN_LENGTH:20$], ctCollectionId);
create index IX_21D39FD8 on KaleoAction (kaleoClassName[$COLUMN_LENGTH:200$], kaleoClassPK, ctCollectionId);
create index IX_D9574C46 on KaleoAction (kaleoClassName[$COLUMN_LENGTH:200$], kaleoClassPK, executionType[$COLUMN_LENGTH:20$], ctCollectionId);
create index IX_A868C2AE on KaleoAction (kaleoDefinitionVersionId, ctCollectionId);

create index IX_73E788C5 on KaleoCondition (companyId, ctCollectionId);
create index IX_41E24C13 on KaleoCondition (kaleoDefinitionVersionId, ctCollectionId);
create index IX_A12B7BAA on KaleoCondition (kaleoNodeId, ctCollectionId);

create index IX_92976518 on KaleoDefinition (companyId, active_, ctCollectionId);
create index IX_5B11A98D on KaleoDefinition (companyId, ctCollectionId);
create index IX_A7773179 on KaleoDefinition (companyId, name[$COLUMN_LENGTH:200$], active_, ctCollectionId);
create index IX_A853C0C on KaleoDefinition (companyId, name[$COLUMN_LENGTH:200$], ctCollectionId);
create index IX_99D05A78 on KaleoDefinition (companyId, name[$COLUMN_LENGTH:200$], version, ctCollectionId);
create index IX_37426512 on KaleoDefinition (companyId, scope[$COLUMN_LENGTH:75$], active_, ctCollectionId);
create index IX_4999E853 on KaleoDefinition (companyId, scope[$COLUMN_LENGTH:75$], ctCollectionId);

create index IX_15AD2A1B on KaleoDefinitionVersion (companyId, ctCollectionId);
create index IX_9A359A1A on KaleoDefinitionVersion (companyId, name[$COLUMN_LENGTH:200$], ctCollectionId);
create unique index IX_3ADEC2A on KaleoDefinitionVersion (companyId, name[$COLUMN_LENGTH:200$], version[$COLUMN_LENGTH:75$], ctCollectionId);

create index IX_947A3F29 on KaleoInstance (className[$COLUMN_LENGTH:200$], classPK, ctCollectionId);
create index IX_EB3CA98B on KaleoInstance (companyId, ctCollectionId);
create index IX_9BC02056 on KaleoInstance (companyId, kaleoDefinitionName[$COLUMN_LENGTH:200$], kaleoDefinitionVersion, completionDate, ctCollectionId);
create index IX_BC4AD0C5 on KaleoInstance (companyId, userId, ctCollectionId);
create index IX_EC80C896 on KaleoInstance (kaleoDefinitionId, completed, ctCollectionId);
create index IX_1F43A40A on KaleoInstance (kaleoDefinitionVersionId, completed, ctCollectionId);
create index IX_487AC68D on KaleoInstance (kaleoDefinitionVersionId, ctCollectionId);
create index IX_1C73607B on KaleoInstance (kaleoInstanceId, companyId, userId, ctCollectionId);

create index IX_2052BC1C on KaleoInstanceToken (companyId, ctCollectionId);
create index IX_F28D3937 on KaleoInstanceToken (companyId, parentKaleoInstanceTokenId, completionDate, ctCollectionId);
create index IX_8BB39299 on KaleoInstanceToken (companyId, parentKaleoInstanceTokenId, ctCollectionId);
create index IX_89DD1FDC on KaleoInstanceToken (kaleoDefinitionVersionId, ctCollectionId);
create index IX_9792DA54 on KaleoInstanceToken (kaleoInstanceId, ctCollectionId);

create index IX_DA074F3C on KaleoLog (companyId, ctCollectionId);
create index IX_9D873798 on KaleoLog (kaleoClassName[$COLUMN_LENGTH:200$], kaleoClassPK, kaleoInstanceTokenId, type_[$COLUMN_LENGTH:50$], ctCollectionId);
create index IX_A64BE8BC on KaleoLog (kaleoDefinitionVersionId, ctCollectionId);
create index IX_B149574 on KaleoLog (kaleoInstanceId, ctCollectionId);
create index IX_BE78656 on KaleoLog (kaleoInstanceTokenId, type_[$COLUMN_LENGTH:50$], ctCollectionId);
create index IX_BA8D3096 on KaleoLog (kaleoTaskInstanceTokenId, ctCollectionId);

create index IX_1E1EC33E on KaleoNode (companyId, ctCollectionId);
create index IX_570C0512 on KaleoNode (companyId, kaleoDefinitionVersionId, ctCollectionId);
create index IX_AD01B07A on KaleoNode (kaleoDefinitionVersionId, ctCollectionId);

create index IX_52775CF5 on KaleoNotification (companyId, ctCollectionId);
create index IX_7BDFCC8D on KaleoNotification (kaleoClassName[$COLUMN_LENGTH:200$], kaleoClassPK, ctCollectionId);
create index IX_340F7EF1 on KaleoNotification (kaleoClassName[$COLUMN_LENGTH:200$], kaleoClassPK, executionType[$COLUMN_LENGTH:20$], ctCollectionId);
create index IX_AA8991E3 on KaleoNotification (kaleoDefinitionVersionId, ctCollectionId);

create index IX_260AB952 on KaleoNotificationRecipient (companyId, ctCollectionId);
create index IX_3C504FE6 on KaleoNotificationRecipient (kaleoDefinitionVersionId, ctCollectionId);
create index IX_7A947F60 on KaleoNotificationRecipient (kaleoNotificationId, ctCollectionId);

create index IX_DC396E9B on KaleoTask (companyId, ctCollectionId);
create index IX_18D93F7D on KaleoTask (kaleoDefinitionVersionId, ctCollectionId);
create index IX_3C9B4400 on KaleoTask (kaleoNodeId, ctCollectionId);

create index IX_8AA6A90E on KaleoTaskAssignment (companyId, ctCollectionId);
create index IX_715F00EC on KaleoTaskAssignment (kaleoClassName[$COLUMN_LENGTH:200$], kaleoClassPK, assigneeClassName[$COLUMN_LENGTH:200$], ctCollectionId);
create index IX_49C2EFD4 on KaleoTaskAssignment (kaleoClassName[$COLUMN_LENGTH:200$], kaleoClassPK, ctCollectionId);
create index IX_839770AA on KaleoTaskAssignment (kaleoDefinitionVersionId, ctCollectionId);

create index IX_35F3735B on KaleoTaskAssignmentInstance (assigneeClassName[$COLUMN_LENGTH:200$], assigneeClassPK, ctCollectionId);
create index IX_F6DBD715 on KaleoTaskAssignmentInstance (assigneeClassName[$COLUMN_LENGTH:200$], ctCollectionId);
create index IX_349E1A79 on KaleoTaskAssignmentInstance (companyId, ctCollectionId);
create index IX_E2FC4375 on KaleoTaskAssignmentInstance (groupId, assigneeClassPK, ctCollectionId);
create index IX_77A79BDF on KaleoTaskAssignmentInstance (kaleoDefinitionVersionId, ctCollectionId);
create index IX_C9033EF1 on KaleoTaskAssignmentInstance (kaleoInstanceId, ctCollectionId);
create index IX_BD5AF0E7 on KaleoTaskAssignmentInstance (kaleoTaskInstanceTokenId, assigneeClassName[$COLUMN_LENGTH:200$], ctCollectionId);
create index IX_8BE8E3B9 on KaleoTaskAssignmentInstance (kaleoTaskInstanceTokenId, ctCollectionId);

create index IX_379782B7 on KaleoTaskForm (companyId, ctCollectionId);
create index IX_E704EFE1 on KaleoTaskForm (kaleoDefinitionVersionId, ctCollectionId);
create index IX_38C4C11C on KaleoTaskForm (kaleoNodeId, ctCollectionId);
create index IX_3F83691F on KaleoTaskForm (kaleoTaskId, ctCollectionId);
create index IX_AAE007B2 on KaleoTaskForm (kaleoTaskId, formUuid[$COLUMN_LENGTH:75$], ctCollectionId);

create index IX_D4D33B22 on KaleoTaskFormInstance (companyId, ctCollectionId);
create index IX_EBD9F416 on KaleoTaskFormInstance (kaleoDefinitionVersionId, ctCollectionId);
create index IX_60DD7CDA on KaleoTaskFormInstance (kaleoInstanceId, ctCollectionId);
create index IX_27AD622E on KaleoTaskFormInstance (kaleoTaskFormId, ctCollectionId);
create index IX_7CB2B2CA on KaleoTaskFormInstance (kaleoTaskId, ctCollectionId);
create index IX_1B3BF0 on KaleoTaskFormInstance (kaleoTaskInstanceTokenId, ctCollectionId);

create index IX_941E25F3 on KaleoTaskInstanceToken (className[$COLUMN_LENGTH:200$], classPK, ctCollectionId);
create index IX_9F761781 on KaleoTaskInstanceToken (companyId, ctCollectionId);
create index IX_CDB6F91C on KaleoTaskInstanceToken (companyId, userId, completed, ctCollectionId);
create index IX_C489E8BB on KaleoTaskInstanceToken (companyId, userId, ctCollectionId);
create index IX_58C4EDD7 on KaleoTaskInstanceToken (kaleoDefinitionVersionId, ctCollectionId);
create index IX_A65B55F9 on KaleoTaskInstanceToken (kaleoInstanceId, ctCollectionId);
create index IX_324FAD73 on KaleoTaskInstanceToken (kaleoInstanceId, kaleoTaskId, ctCollectionId);

create index IX_83C1D190 on KaleoTimer (kaleoClassName[$COLUMN_LENGTH:200$], kaleoClassPK, blocking, ctCollectionId);
create index IX_FBE94CE7 on KaleoTimer (kaleoClassName[$COLUMN_LENGTH:200$], kaleoClassPK, ctCollectionId);

create index IX_E93985B9 on KaleoTimerInstanceToken (kaleoInstanceId, ctCollectionId);
create index IX_64F0AF8 on KaleoTimerInstanceToken (kaleoInstanceTokenId, blocking, completed, ctCollectionId);
create index IX_7E23C481 on KaleoTimerInstanceToken (kaleoInstanceTokenId, completed, ctCollectionId);
create index IX_78EB88A on KaleoTimerInstanceToken (kaleoInstanceTokenId, kaleoTimerId, ctCollectionId);

create index IX_C183C8CB on KaleoTransition (companyId, ctCollectionId);
create index IX_49A93F4D on KaleoTransition (kaleoDefinitionVersionId, ctCollectionId);
create index IX_F8B7D230 on KaleoTransition (kaleoNodeId, ctCollectionId);
create index IX_E4CB4FF2 on KaleoTransition (kaleoNodeId, defaultTransition, ctCollectionId);
create index IX_FB851E6F on KaleoTransition (kaleoNodeId, name[$COLUMN_LENGTH:200$], ctCollectionId);