SELECT
 mainTable.mainTableId, mainTable.companyId, mainTable.groupId, mainTable.name, mainTable.ctCollectionId
FROM
 MainTable mainTable
WHERE
 mainTable.groupId = ? AND
 (mainTable.mainTableId NOT IN ([$MAIN_TABLE_CT_ENTRY_MODEL_CLASS_PKS$]) AND mainTable.ctCollectionId = 0 OR mainTable.ctCollectionId = [$CT_COLLECTION_ID$])
ORDER BY
 mainTable.mainTableId ASC