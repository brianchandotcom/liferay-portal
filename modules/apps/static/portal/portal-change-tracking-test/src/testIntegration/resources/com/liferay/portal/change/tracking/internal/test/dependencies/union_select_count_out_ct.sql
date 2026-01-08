(
 SELECT
  COUNT(*)
 FROM
  MainTable
 WHERE
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
  )
)
UNION ALL
(
 SELECT
  COUNT(*)
 FROM
  ReferenceTable
 WHERE
  (
   ReferenceTable.ctCollectionId = [$CT_COLLECTION_ID$] OR
   (
    ReferenceTable.ctCollectionId = 0 AND
    NOT EXISTS (
     SELECT
      1
     FROM
      CTEntry
     WHERE
      CTEntry.ctCollectionId = [$CT_COLLECTION_ID$] AND
      CTEntry.modelClassNameId = [$REFERENCE_TABLE_CLASS_NAME_ID$] AND
      CTEntry.modelClassPK = ReferenceTable.referenceTableId
    )
   )
  )
)