alter table FragmentEntry add type_ INTEGER;

COMMIT_TRANSACTION;

update FragmentEntry set type_ = 0;

alter table FragmentEntryLink add rendererKey VARCHAR(75);