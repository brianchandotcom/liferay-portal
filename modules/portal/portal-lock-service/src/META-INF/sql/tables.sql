create table Lock_ (
	mvccVersion LONG default 0,
	uuid_ VARCHAR(75) null,
	lockId LONG not null primary key,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	className VARCHAR(75) null,
	key_ VARCHAR(200) null,
	owner VARCHAR(1024) null,
	inheritable BOOLEAN,
	expirationDate DATE null,
	companyId LONG
);