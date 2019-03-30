alter table FragmentEntry add type_ INTEGER;
alter table FragmentEntryLink add rendererType INTEGER;
alter table FragmentEntryLink add rendererKey VARCHAR(75);

COMMIT_TRANSACTION;

update FragmentEntry set type_ = 0;
update FragmentEntryLink set rendererType = 0;