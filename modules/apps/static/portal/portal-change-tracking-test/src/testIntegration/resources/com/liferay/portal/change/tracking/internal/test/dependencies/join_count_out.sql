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
 MainTable.ctCollectionId = 0 AND
 ReferenceTable.ctCollectionId = 0