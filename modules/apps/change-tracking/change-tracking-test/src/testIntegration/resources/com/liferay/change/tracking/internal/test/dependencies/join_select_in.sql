SELECT
 mainTable.mainTableId, mainTable.companyId, mainTable.groupId, mainTable.name, mainTable.ctCollectionId
FROM
 MainTable mainTable
INNER JOIN
 ReferenceTable referenceTable
ON
 referenceTable.mainTableId = mainTable.mainTableId
WHERE
 referenceTable.name = ?
ORDER BY
 mainTable.mainTableId ASC