create table ViewCountEntry (
	companyId LONG not null,
	classNameId LONG not null,
	classPK LONG not null,
	viewCount LONG,
	primary key (companyId, classNameId, classPK)
);