SELECT
 mainTable.mainTableId, mainTable.companyId, mainTable.groupId, mainTable.name, mainTable.ctCollectionId
FROM
 MainTable mainTable
INNER JOIN
 ReferenceTable referenceTable
ON
 referenceTable.mainTableId = mainTable.mainTableId
WHERE
 referenceTable.name = ? AND
 mainTable.ctCollectionId = 0 AND
 referenceTable.ctCollectionId = 0
ORDER BY
 mainTable.mainTableId ASC