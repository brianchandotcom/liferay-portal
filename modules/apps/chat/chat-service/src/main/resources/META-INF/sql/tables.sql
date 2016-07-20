create table Chat_Entry (
	entryId LONG not null primary key,
	createDate LONG,
	fromUserId LONG,
	toUserId LONG,
	content VARCHAR(75) null,
	flag INTEGER
);

create table Chat_Status (
	statusId LONG not null primary key,
	userId LONG,
	modifiedDate LONG,
	online_ BOOLEAN,
	awake BOOLEAN,
	activePanelIds VARCHAR(75) null,
	message VARCHAR(75) null,
	playSound BOOLEAN
);