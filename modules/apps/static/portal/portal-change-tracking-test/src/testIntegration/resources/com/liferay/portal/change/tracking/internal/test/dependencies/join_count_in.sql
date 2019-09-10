SELECT
 COUNT(*)
FROM
 MainTable
INNER JOIN
 ReferenceTable
ON
 ReferenceTable.mainTableId = MainTable.mainTableId
WHERE
 ReferenceTable.name = ?