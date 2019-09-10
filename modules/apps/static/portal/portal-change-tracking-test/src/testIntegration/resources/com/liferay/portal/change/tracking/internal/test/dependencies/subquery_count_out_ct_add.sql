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
   (ReferenceTable.ctCollectionId = 0 OR ReferenceTable.ctCollectionId = [$CT_COLLECTION_ID$])
 ) AND
 (MainTable.ctCollectionId = 0 OR MainTable.ctCollectionId = [$CT_COLLECTION_ID$])