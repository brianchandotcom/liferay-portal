create table Audit_AuditEvent (
	auditEventId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(200) null,
	createDate DATE null,
	eventType VARCHAR(75) null,
	className VARCHAR(200) null,
	classPK VARCHAR(75) null,
	message STRING null,
	clientHost VARCHAR(255) null,
	clientIP VARCHAR(255) null,
	serverName VARCHAR(255) null,
	serverPort INTEGER,
	sessionID VARCHAR(255) null,
	additionalInfo TEXT null
);