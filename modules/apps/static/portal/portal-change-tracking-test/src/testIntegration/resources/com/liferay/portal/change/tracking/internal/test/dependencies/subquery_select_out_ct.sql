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
   (
    referenceTable.ctCollectionId = [$CT_COLLECTION_ID$] OR
    (
     referenceTable.ctCollectionId = 0 AND
     NOT EXISTS (
      SELECT
       1
      FROM
       CTEntry
      WHERE
       CTEntry.ctCollectionId = [$CT_COLLECTION_ID$] AND
       CTEntry.modelClassNameId = [$REFERENCE_TABLE_CLASS_NAME_ID$] AND
       CTEntry.modelClassPK = referenceTable.referenceTableId
     )
    )
   )
 ) AND
 (
  mainTable.ctCollectionId = [$CT_COLLECTION_ID$] OR
  (
   mainTable.ctCollectionId = 0 AND
   NOT EXISTS (
    SELECT
     1
    FROM
     CTEntry
    WHERE
     CTEntry.ctCollectionId = [$CT_COLLECTION_ID$] AND
     CTEntry.modelClassNameId = [$MAIN_TABLE_CLASS_NAME_ID$] AND
     CTEntry.modelClassPK = mainTable.mainTableId
    )
  )
 )
ORDER BY
 mainTable.mainTableId ASC