SELECT
 mainTable.mainTableId, mainTable.companyId, mainTable.groupId, mainTable.name, mainTable.ctCollectionId
FROM
 MainTable mainTable
INNER JOIN
 ReferenceTable referenceTable
ON
 referenceTable.mainTableId = mainTable.mainTableId
WHERE
 referenceTable.name = ? AND
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
 ) AND
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
ORDER BY
 mainTable.mainTableId ASC