SELECT
 MainTable.mainTableId, MainTable.ctCollectionId
FROM
 MainTable
LEFT JOIN
 MainTable tempMainTable
ON
 MainTable.mainTableId < tempMainTable.mainTableId AND
 (
  (
   MainTable.ctCollectionId = [$CT_COLLECTION_ID$] OR
   (
    MainTable.ctCollectionId = 0 AND
    NOT EXISTS (
     SELECT
      1
     FROM
      CTEntry
     WHERE
      CTEntry.ctCollectionId = [$CT_COLLECTION_ID$] AND
      CTEntry.modelClassNameId = [$MAIN_TABLE_CLASS_NAME_ID$] AND
      CTEntry.modelClassPK = MainTable.mainTableId
    )
   )
  ) OR
  MainTable.ctCollectionId IS NULL
 ) AND
 (
  (
   tempMainTable.ctCollectionId = [$CT_COLLECTION_ID$] OR
   (
    tempMainTable.ctCollectionId = 0 AND
    NOT EXISTS (
     SELECT
      1
     FROM
      CTEntry
     WHERE
      CTEntry.ctCollectionId = [$CT_COLLECTION_ID$] AND
      CTEntry.modelClassNameId = [$MAIN_TABLE_CLASS_NAME_ID$] AND
      CTEntry.modelClassPK = tempMainTable.mainTableId
    )
   )
  ) OR
  tempMainTable.ctCollectionId IS NULL
 )
WHERE
 tempMainTable.mainTableId IS NULL AND
 (
  (
   MainTable.ctCollectionId = [$CT_COLLECTION_ID$] OR
   (
    MainTable.ctCollectionId = 0 AND
    NOT EXISTS (
     SELECT
      1
     FROM
      CTEntry
     WHERE
      CTEntry.ctCollectionId = [$CT_COLLECTION_ID$] AND
      CTEntry.modelClassNameId = [$MAIN_TABLE_CLASS_NAME_ID$] AND
      CTEntry.modelClassPK = MainTable.mainTableId
    )
   )
  ) OR
  MainTable.ctCollectionId IS NULL
 )