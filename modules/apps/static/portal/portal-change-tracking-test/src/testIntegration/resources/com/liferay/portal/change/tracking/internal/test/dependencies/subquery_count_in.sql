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
   ReferenceTable.name = ?
 )