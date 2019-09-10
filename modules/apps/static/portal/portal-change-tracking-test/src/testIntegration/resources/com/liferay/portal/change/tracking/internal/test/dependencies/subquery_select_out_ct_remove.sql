SELECT
 mainTable.mainTableId, mainTable.companyId, mainTable.groupId, mainTable.name, mainTable.ctCollectionId
FROM
 MainTable mainTable
WHERE
 mainTable.mainTableId IN (
  SELECT
   referenceTable.mainTableId
  FROM
   ReferenceTable referenceTable
  WHERE
   referenceTable.name = ? AND
   referenceTable.referenceTableId NOT IN ([$REFERENCE_TABLE_CT_ENTRY_MODEL_CLASS_PKS$]) AND referenceTable.ctCollectionId = 0
 ) AND
 mainTable.mainTableId NOT IN ([$MAIN_TABLE_CT_ENTRY_MODEL_CLASS_PKS$]) AND mainTable.ctCollectionId = 0
ORDER BY
 mainTable.mainTableId ASC