SELECT
 COUNT(*)
FROM
 MainTable
WHERE
 (MainTable.ctCollectionId = 0 OR MainTable.ctCollectionId = [$CT_COLLECTION_ID$])