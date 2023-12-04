SELECT DISTINCT
	AssetEntry.groupId,
	AssetEntry.companyId,
	AssetEntry.classPK,
	PortletPreferences.plid,
	PortletPreferences.portletId
FROM
	PortletPreferences
	INNER JOIN
		PortletPreferenceValue
	ON
		PortletPreferenceValue.name = 'assetEntryId'
		AND PortletPreferences.ownerId = [$PortletKeys.PREFS_OWNER_ID_DEFAULT$]
		AND PortletPreferences.ownerType = [$PortletKeys.PREFS_OWNER_TYPE_LAYOUT$]
		AND PortletPreferences.portletId LIKE '[$JournalContentPortletKeys.JOURNAL_CONTENT$]%'
		AND PortletPreferences.portletPreferencesId = PortletPreferenceValue.portletPreferencesId
	INNER JOIN
		AssetEntry
	ON
		AssetEntry.entryId = PortletPreferenceValue.smallValue
		AND AssetEntry.classNameId = [$journalArticleClassNameId$]
		AND AssetEntry.visible = [$TRUE$]
WHERE
	NOT EXISTS (
		SELECT
			1
		FROM
			LayoutClassedModelUsage
		WHERE
			LayoutClassedModelUsage.classPK = AssetEntry.classPK
			AND LayoutClassedModelUsage.classNameId = [$journalArticleClassNameId$]
			AND LayoutClassedModelUsage.containerKey = PortletPreferences.portletId
			AND LayoutClassedModelUsage.containerType = [$portletClassNameId$]
			AND LayoutClassedModelUsage.plid = PortletPreferences.plid
	)
	AND NOT EXISTS (
		SELECT
			1
		FROM
			LayoutClassedModelUsage
		WHERE
			LayoutClassedModelUsage.classPK = AssetEntry.classPK
			AND LayoutClassedModelUsage.classNameId = [$journalArticleClassNameId$]
			AND LayoutClassedModelUsage.containerKey IS NULL
			AND LayoutClassedModelUsage.containerType = 0
			AND LayoutClassedModelUsage.plid = 0
	)