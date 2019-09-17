(
 SELECT
  COUNT(*)
 FROM
  MainTable
 WHERE
  (MainTable.ctCollectionId = 0 OR MainTable.ctCollectionId = [$CT_COLLECTION_ID$])
)
UNION ALL
(
 SELECT
  COUNT(*)
 FROM
  ReferenceTable
 WHERE
  (ReferenceTable.ctCollectionId = 0 OR ReferenceTable.ctCollectionId = [$CT_COLLECTION_ID$])
)