create table DLSyncEvent (
	syncEventId LONG not null primary key,
	companyId LONG,
	modifiedTime LONG,
	event VARCHAR(75) null,
	type_ VARCHAR(75) null,
	typePK LONG
);