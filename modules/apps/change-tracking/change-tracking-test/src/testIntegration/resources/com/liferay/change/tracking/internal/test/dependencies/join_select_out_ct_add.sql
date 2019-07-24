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
 (mainTable.ctCollectionId = 0 OR mainTable.ctCollectionId = [$CT_COLLECTION_ID$]) AND
 (referenceTable.ctCollectionId = 0 OR referenceTable.ctCollectionId = [$CT_COLLECTION_ID$])
ORDER BY
 mainTable.mainTableId ASC