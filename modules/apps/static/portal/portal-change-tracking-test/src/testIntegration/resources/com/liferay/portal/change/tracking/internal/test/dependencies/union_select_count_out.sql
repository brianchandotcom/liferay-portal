(
 SELECT
  COUNT(*)
 FROM
  MainTable
 WHERE
  MainTable.ctCollectionId = 0
)
UNION ALL
(
 SELECT
  COUNT(*)
 FROM
  ReferenceTable
 WHERE
  ReferenceTable.ctCollectionId = 0
)