create index IX_FD90786C on MDRAction (ruleGroupInstanceId);
create index IX_C58A516B on MDRAction (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_B0EACF5B on MDRAction (uuid_[$COLUMN_LENGTH:75$], groupId, companyId);

create index IX_4F4293F1 on MDRRule (ruleGroupId);
create index IX_7DEA8DF1 on MDRRule (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_797F4995 on MDRRule (uuid_[$COLUMN_LENGTH:75$], groupId, companyId);

create index IX_5849891C on MDRRuleGroup (groupId);
create index IX_CC14DC2 on MDRRuleGroup (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_76C4C4E4 on MDRRuleGroup (uuid_[$COLUMN_LENGTH:75$], groupId, companyId);

create unique index IX_2228E632 on MDRRuleGroupInstance (classNameId, classPK, ruleGroupId, companyId);
create index IX_22DAB85C on MDRRuleGroupInstance (groupId, classNameId, classPK);
create index IX_BF3E642B on MDRRuleGroupInstance (ruleGroupId);
create index IX_25C9D1F7 on MDRRuleGroupInstance (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_78452C4F on MDRRuleGroupInstance (uuid_[$COLUMN_LENGTH:75$], groupId, companyId);