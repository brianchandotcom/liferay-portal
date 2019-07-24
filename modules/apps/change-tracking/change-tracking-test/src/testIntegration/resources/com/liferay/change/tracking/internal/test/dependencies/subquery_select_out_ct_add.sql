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
   (referenceTable.ctCollectionId = 0 OR referenceTable.ctCollectionId = [$CT_COLLECTION_ID$])
 ) AND
 (mainTable.ctCollectionId = 0 OR mainTable.ctCollectionId = [$CT_COLLECTION_ID$])
ORDER BY
 mainTable.mainTableId ASC