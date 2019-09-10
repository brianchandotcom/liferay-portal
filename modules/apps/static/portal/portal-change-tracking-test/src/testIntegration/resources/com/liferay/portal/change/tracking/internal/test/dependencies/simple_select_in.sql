SELECT
 mainTable.mainTableId, mainTable.companyId, mainTable.groupId, mainTable.name, mainTable.ctCollectionId
FROM
 MainTable mainTable
WHERE
 mainTable.groupId = ?
ORDER BY
 mainTable.mainTableId ASC