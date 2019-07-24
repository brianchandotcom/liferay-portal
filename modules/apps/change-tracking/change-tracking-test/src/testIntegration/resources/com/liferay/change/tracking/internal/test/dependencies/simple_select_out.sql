SELECT
 mainTable.mainTableId, mainTable.companyId, mainTable.groupId, mainTable.name, mainTable.ctCollectionId
FROM
 MainTable mainTable
WHERE
 mainTable.groupId = ? AND
 mainTable.ctCollectionId = 0
ORDER BY
 mainTable.mainTableId ASC