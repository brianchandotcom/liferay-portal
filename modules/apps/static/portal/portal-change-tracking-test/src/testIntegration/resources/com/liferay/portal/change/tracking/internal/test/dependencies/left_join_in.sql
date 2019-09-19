SELECT
 MainTable.mainTableId
FROM
 MainTable
LEFT JOIN
 ReferenceTable
ON
 ReferenceTable.mainTableId = MainTable.mainTableId
WHERE
 ReferenceTable.mainTableId IS NULL
ORDER BY
 MainTable.mainTableId
ASC