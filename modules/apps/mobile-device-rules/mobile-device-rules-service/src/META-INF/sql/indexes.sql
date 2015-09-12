create index IX_FD90786C on MDRAction (ruleGroupInstanceId);
create index IX_C58A516B on MDRAction (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_75BE36AD on MDRAction (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_4F4293F1 on MDRRule (ruleGroupId);
create index IX_7DEA8DF1 on MDRRule (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_F3EFDCB3 on MDRRule (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_5849891C on MDRRuleGroup (groupId);
create index IX_CC14DC2 on MDRRuleGroup (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_46665CC4 on MDRRuleGroup (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_808A0036 on MDRRuleGroupInstance (classNameId, classPK, ruleGroupId);
create index IX_22DAB85C on MDRRuleGroupInstance (groupId, classNameId, classPK);
create index IX_BF3E642B on MDRRuleGroupInstance (ruleGroupId);
create index IX_25C9D1F7 on MDRRuleGroupInstance (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_9CBC6A39 on MDRRuleGroupInstance (uuid_[$COLUMN_LENGTH:75$], groupId);