SELECT
 mainTable.mainTableId, mainTable.companyId, mainTable.groupId, mainTable.name, mainTable.ctCollectionId
FROM
 MainTable mainTable
INNER JOIN
 ReferenceTable referenceTable
ON
 referenceTable.mainTableId = mainTable.mainTableId
WHERE
 referenceTable.name = ? AND
 mainTable.mainTableId NOT IN ([$MAIN_TABLE_CT_ENTRY_MODEL_CLASS_PKS$]) AND mainTable.ctCollectionId = 0 AND
 referenceTable.referenceTableId NOT IN ([$REFERENCE_TABLE_CT_ENTRY_MODEL_CLASS_PKS$]) AND referenceTable.ctCollectionId = 0
ORDER BY
 mainTable.mainTableId ASC