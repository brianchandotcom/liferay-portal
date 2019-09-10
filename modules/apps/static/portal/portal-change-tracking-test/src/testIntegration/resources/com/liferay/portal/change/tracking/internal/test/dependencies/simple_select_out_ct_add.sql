SELECT
 mainTable.mainTableId, mainTable.companyId, mainTable.groupId, mainTable.name, mainTable.ctCollectionId
FROM
 MainTable mainTable
WHERE
 mainTable.groupId = ? AND
 (mainTable.ctCollectionId = 0 OR mainTable.ctCollectionId = [$CT_COLLECTION_ID$])
ORDER BY
 mainTable.mainTableId ASC