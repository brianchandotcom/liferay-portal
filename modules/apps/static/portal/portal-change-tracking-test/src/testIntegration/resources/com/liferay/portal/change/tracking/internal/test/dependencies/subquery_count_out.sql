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
   ReferenceTable.ctCollectionId = 0
 ) AND
 MainTable.ctCollectionId = 0