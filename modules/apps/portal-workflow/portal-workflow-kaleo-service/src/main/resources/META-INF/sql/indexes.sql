create index IX_50E9112C on KaleoAction (companyId);
create index IX_4B2545E8 on KaleoAction (kaleoClassName[$COLUMN_LENGTH:200$], kaleoClassPK, executionType[$COLUMN_LENGTH:20$]);
create index IX_F8808C50 on KaleoAction (kaleoDefinitionVersionId);

create index IX_FEE46067 on KaleoCondition (companyId);
create index IX_353B7FB5 on KaleoCondition (kaleoDefinitionVersionId);
create index IX_86CBD4C on KaleoCondition (kaleoNodeId);

create index IX_408542BA on KaleoDefinition (companyId, active_);
create index IX_4C23F11B on KaleoDefinition (companyId, name[$COLUMN_LENGTH:200$], active_);
create index IX_EC14F81A on KaleoDefinition (companyId, name[$COLUMN_LENGTH:200$], version);

create unique index IX_AE02DCC on KaleoDefinitionVersion (companyId, name[$COLUMN_LENGTH:200$], version[$COLUMN_LENGTH:75$]);

create index IX_58D85ECB on KaleoInstance (className[$COLUMN_LENGTH:200$], classPK);
create index IX_BF5839F8 on KaleoInstance (companyId, kaleoDefinitionName[$COLUMN_LENGTH:200$], kaleoDefinitionVersion, completionDate);
create index IX_C6D7A867 on KaleoInstance (companyId, userId);
create index IX_3DA1A5AC on KaleoInstance (kaleoDefinitionVersionId, completed);

create index IX_360D34D9 on KaleoInstanceToken (companyId, parentKaleoInstanceTokenId, completionDate);
create index IX_1181057E on KaleoInstanceToken (kaleoDefinitionVersionId);
create index IX_F42AAFF6 on KaleoInstanceToken (kaleoInstanceId);

create index IX_73B5F4DE on KaleoLog (companyId);
create index IX_E66A153A on KaleoLog (kaleoClassName[$COLUMN_LENGTH:200$], kaleoClassPK, kaleoInstanceTokenId, type_[$COLUMN_LENGTH:50$]);
create index IX_935D8E5E on KaleoLog (kaleoDefinitionVersionId);
create index IX_5BC6AB16 on KaleoLog (kaleoInstanceId);
create index IX_470B9FF8 on KaleoLog (kaleoInstanceTokenId, type_[$COLUMN_LENGTH:50$]);
create index IX_B0CDCA38 on KaleoLog (kaleoTaskInstanceTokenId);

create index IX_4B1D16B4 on KaleoNode (companyId, kaleoDefinitionVersionId);
create index IX_F066921C on KaleoNode (kaleoDefinitionVersionId);

create index IX_38829497 on KaleoNotification (companyId);
create index IX_F3362E93 on KaleoNotification (kaleoClassName[$COLUMN_LENGTH:200$], kaleoClassPK, executionType[$COLUMN_LENGTH:20$]);
create index IX_B8486585 on KaleoNotification (kaleoDefinitionVersionId);

create index IX_2C8C4AF4 on KaleoNotificationRecipient (companyId);
create index IX_B6D98988 on KaleoNotificationRecipient (kaleoDefinitionVersionId);
create index IX_7F4FED02 on KaleoNotificationRecipient (kaleoNotificationId);

create index IX_E1F8B23D on KaleoTask (companyId);
create index IX_FECA871F on KaleoTask (kaleoDefinitionVersionId);
create index IX_77B3F1A2 on KaleoTask (kaleoNodeId);

create index IX_611732B0 on KaleoTaskAssignment (companyId);
create index IX_1087068E on KaleoTaskAssignment (kaleoClassName[$COLUMN_LENGTH:200$], kaleoClassPK, assigneeClassName[$COLUMN_LENGTH:200$]);
create index IX_E362B24C on KaleoTaskAssignment (kaleoDefinitionVersionId);

create index IX_3BD436FD on KaleoTaskAssignmentInstance (assigneeClassName[$COLUMN_LENGTH:200$], assigneeClassPK);
create index IX_6E3CDA1B on KaleoTaskAssignmentInstance (companyId);
create index IX_38A47B17 on KaleoTaskAssignmentInstance (groupId, assigneeClassPK);
create index IX_B751E781 on KaleoTaskAssignmentInstance (kaleoDefinitionVersionId);
create index IX_67A9EE93 on KaleoTaskAssignmentInstance (kaleoInstanceId);
create index IX_CF204C89 on KaleoTaskAssignmentInstance (kaleoTaskInstanceTokenId, assigneeClassName[$COLUMN_LENGTH:200$]);

create index IX_EFDA7E59 on KaleoTaskForm (companyId);
create index IX_3B8B7F83 on KaleoTaskForm (kaleoDefinitionVersionId);
create index IX_945326BE on KaleoTaskForm (kaleoNodeId);
create index IX_E38A5954 on KaleoTaskForm (kaleoTaskId, formUuid[$COLUMN_LENGTH:75$]);

create index IX_77B26CC4 on KaleoTaskFormInstance (companyId);
create index IX_F118DB8 on KaleoTaskFormInstance (kaleoDefinitionVersionId);
create index IX_FF271E7C on KaleoTaskFormInstance (kaleoInstanceId);
create index IX_E7F42BD0 on KaleoTaskFormInstance (kaleoTaskFormId);
create index IX_2A86346C on KaleoTaskFormInstance (kaleoTaskId);
create index IX_2C81C992 on KaleoTaskFormInstance (kaleoTaskInstanceTokenId);

create index IX_A3271995 on KaleoTaskInstanceToken (className[$COLUMN_LENGTH:200$], classPK);
create index IX_997FE723 on KaleoTaskInstanceToken (companyId);
create index IX_B2822979 on KaleoTaskInstanceToken (kaleoDefinitionVersionId);
create index IX_B857A115 on KaleoTaskInstanceToken (kaleoInstanceId, kaleoTaskId);

create index IX_1A479F32 on KaleoTimer (kaleoClassName[$COLUMN_LENGTH:200$], kaleoClassPK, blocking);

create index IX_DB96C55B on KaleoTimerInstanceToken (kaleoInstanceId);
create index IX_F904A89A on KaleoTimerInstanceToken (kaleoInstanceTokenId, blocking, completed);
create index IX_DB279423 on KaleoTimerInstanceToken (kaleoInstanceTokenId, completed);
create index IX_13A5BA2C on KaleoTimerInstanceToken (kaleoInstanceTokenId, kaleoTimerId);

create index IX_41D6C6D on KaleoTransition (companyId);
create index IX_16B426EF on KaleoTransition (kaleoDefinitionVersionId);
create index IX_A38E2194 on KaleoTransition (kaleoNodeId, defaultTransition);
create index IX_85268A11 on KaleoTransition (kaleoNodeId, name[$COLUMN_LENGTH:200$]);