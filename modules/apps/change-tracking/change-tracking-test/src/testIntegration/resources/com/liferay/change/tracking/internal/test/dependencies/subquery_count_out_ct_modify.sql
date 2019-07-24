SELECT
 COUNT(*)
FROM
 MainTable
WHERE
 MainTable.mainTableId IN (
  SELECT
   mainTableId
  FROM
   ReferenceTable
  WHERE
   ReferenceTable.name = ? AND
   (ReferenceTable.referenceTableId NOT IN ([$REFERENCE_TABLE_CT_ENTRY_MODEL_CLASS_PKS$]) AND ReferenceTable.ctCollectionId = 0 OR ReferenceTable.ctCollectionId = [$CT_COLLECTION_ID$])
 ) AND
 (MainTable.mainTableId NOT IN ([$MAIN_TABLE_CT_ENTRY_MODEL_CLASS_PKS$]) AND MainTable.ctCollectionId = 0 OR MainTable.ctCollectionId = [$CT_COLLECTION_ID$])