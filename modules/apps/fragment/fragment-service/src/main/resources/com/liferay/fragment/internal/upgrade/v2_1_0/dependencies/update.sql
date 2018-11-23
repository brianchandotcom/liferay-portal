alter table FragmentEntry add type_ INTEGER;

COMMIT_TRANSACTION;

update FragmentEntry set type_ = 0;