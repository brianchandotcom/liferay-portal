SELECT
 MainTable.mainTableId, MainTable.ctCollectionId
FROM
 MainTable
LEFT JOIN
 MainTable tempMainTable
ON
 MainTable.mainTableId < tempMainTable.mainTableId AND
 (MainTable.mainTableId NOT IN ([$MAIN_TABLE_CT_ENTRY_MODEL_CLASS_PKS$]) AND MainTable.ctCollectionId = 0 OR MainTable.ctCollectionId IS NULL) AND
 (tempMainTable.mainTableId NOT IN ([$MAIN_TABLE_CT_ENTRY_MODEL_CLASS_PKS$]) AND tempMainTable.ctCollectionId = 0 OR tempMainTable.ctCollectionId IS NULL)
WHERE
 tempMainTable.mainTableId IS NULL AND
 (MainTable.mainTableId NOT IN ([$MAIN_TABLE_CT_ENTRY_MODEL_CLASS_PKS$]) AND MainTable.ctCollectionId = 0 OR MainTable.ctCollectionId IS NULL) AND
 (tempMainTable.mainTableId NOT IN ([$MAIN_TABLE_CT_ENTRY_MODEL_CLASS_PKS$]) AND tempMainTable.ctCollectionId = 0 OR tempMainTable.ctCollectionId IS NULL)