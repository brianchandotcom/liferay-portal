UPDATE
 MainTable
SET
 ctCollectionId = ?
WHERE
 MainTable.mainTableId = ? AND
 MainTable.ctCollectionId = [$CT_COLLECTION_ID$]