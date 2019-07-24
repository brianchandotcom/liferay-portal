SELECT
 COUNT(*)
FROM
 MainTable
INNER JOIN
 ReferenceTable
ON
 ReferenceTable.mainTableId = MainTable.mainTableId
WHERE
 ReferenceTable.name = ? AND
 (MainTable.ctCollectionId = 0 OR MainTable.ctCollectionId = [$CT_COLLECTION_ID$]) AND
 (ReferenceTable.ctCollectionId = 0 OR ReferenceTable.ctCollectionId = [$CT_COLLECTION_ID$])