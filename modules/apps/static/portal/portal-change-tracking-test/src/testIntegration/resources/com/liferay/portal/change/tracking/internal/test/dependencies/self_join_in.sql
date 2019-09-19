SELECT
 MainTable.mainTableId, MainTable.ctCollectionId
FROM
 MainTable
LEFT JOIN
 MainTable tempMainTable
ON
 MainTable.mainTableId < tempMainTable.mainTableId
WHERE
 tempMainTable.mainTableId IS NULL