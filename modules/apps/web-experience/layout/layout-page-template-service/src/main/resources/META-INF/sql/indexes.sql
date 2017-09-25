create index IX_81C01BE0 on LayoutPageTemplate (groupId, layoutPageTemplateFolderId, name[$COLUMN_LENGTH:75$]);
create unique index IX_9A7FC30 on LayoutPageTemplate (groupId, name[$COLUMN_LENGTH:75$]);
create index IX_BA45D349 on LayoutPageTemplate (layoutPageTemplateFolderId);

create unique index IX_952693A2 on LayoutPageTemplateFolder (groupId, name[$COLUMN_LENGTH:75$]);