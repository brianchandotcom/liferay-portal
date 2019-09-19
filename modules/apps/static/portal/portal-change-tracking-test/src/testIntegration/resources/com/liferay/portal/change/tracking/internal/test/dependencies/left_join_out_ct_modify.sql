SELECT
 MainTable.mainTableId
FROM
 MainTable
LEFT JOIN
 ReferenceTable
ON
 ReferenceTable.mainTableId = MainTable.mainTableId AND
 ((MainTable.mainTableId NOT IN ([$MAIN_TABLE_CT_ENTRY_MODEL_CLASS_PKS$]) AND MainTable.ctCollectionId = 0 OR MainTable.ctCollectionId = [$CT_COLLECTION_ID$]) OR MainTable.ctCollectionId IS NULL) AND
 ((ReferenceTable.referenceTableId NOT IN ([$REFERENCE_TABLE_CT_ENTRY_MODEL_CLASS_PKS$]) AND ReferenceTable.ctCollectionId = 0 OR ReferenceTable.ctCollectionId = [$CT_COLLECTION_ID$]) OR ReferenceTable.ctCollectionId IS NULL)
WHERE
 ReferenceTable.mainTableId IS NULL AND
 ((MainTable.mainTableId NOT IN ([$MAIN_TABLE_CT_ENTRY_MODEL_CLASS_PKS$]) AND MainTable.ctCollectionId = 0 OR MainTable.ctCollectionId = [$CT_COLLECTION_ID$]) OR MainTable.ctCollectionId IS NULL) AND
 ((ReferenceTable.referenceTableId NOT IN ([$REFERENCE_TABLE_CT_ENTRY_MODEL_CLASS_PKS$]) AND ReferenceTable.ctCollectionId = 0 OR ReferenceTable.ctCollectionId = [$CT_COLLECTION_ID$]) OR ReferenceTable.ctCollectionId IS NULL)
ORDER BY
 MainTable.mainTableId
ASC