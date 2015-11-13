alter table WikiPageResource add groupId LONG;

update WikiPageResource set groupId = (select max(groupId) from WikiPage where WikiPage.resourcePrimKey = WikiPageResource.resourcePrimKey);

COMMIT_TRANSACTION;