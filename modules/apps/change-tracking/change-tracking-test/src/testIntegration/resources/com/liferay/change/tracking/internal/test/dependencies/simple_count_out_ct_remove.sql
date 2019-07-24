SELECT
 COUNT(*)
FROM
 MainTable
WHERE
 MainTable.mainTableId NOT IN ([$MAIN_TABLE_CT_ENTRY_MODEL_CLASS_PKS$]) AND MainTable.ctCollectionId = 0