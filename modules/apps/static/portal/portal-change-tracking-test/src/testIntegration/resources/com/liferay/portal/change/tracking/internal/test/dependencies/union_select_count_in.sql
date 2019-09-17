(
 SELECT
  COUNT(*)
 FROM
  MainTable
)
UNION ALL
(
 SELECT
  COUNT(*)
 FROM
  ReferenceTable
)