alter table KaleoAction add scriptRequiredContexts STRING null;

alter table KaleoCondition add scriptRequiredContexts STRING null;

alter table KaleoNotificationRecipient add recipientScript TEXT null;
alter table KaleoNotificationRecipient add recipientScriptLanguage VARCHAR(75) null;
alter table KaleoNotificationRecipient add recipientScriptContexts TEXT null;
alter table KaleoNotificationRecipient add notificationReceptionType VARCHAR(3) null;

alter table KaleoTaskAssignment add assigneeScriptRequiredContexts STRING null;

COMMIT_TRANSACTION;