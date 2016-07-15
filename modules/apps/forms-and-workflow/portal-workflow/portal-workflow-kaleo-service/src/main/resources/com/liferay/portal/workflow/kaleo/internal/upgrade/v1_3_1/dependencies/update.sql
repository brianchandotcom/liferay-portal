alter table KaleoNotification add kaleoClassName VARCHAR(200) null;
alter table KaleoNotification add kaleoClassPK LONG;

COMMIT_TRANSACTION;