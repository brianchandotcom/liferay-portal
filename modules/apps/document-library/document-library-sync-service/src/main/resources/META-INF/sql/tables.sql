create table DLSyncEvent (
	syncEventId LONG not null,
	companyId LONG not null,
	modifiedTime LONG,
	event VARCHAR(75) null,
	type_ VARCHAR(75) null,
	typePK LONG,
	primary key (syncEventId, companyId)
);