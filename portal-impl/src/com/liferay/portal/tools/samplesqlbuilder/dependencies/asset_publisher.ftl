<#list 1..maxAssetPublisherPageCount as pageCount>
	<#assign portletId = "101_INSTANCE_TEST_" + pageCount>

	<#assign layout = dataFactory.newLayout(groupId, groupId + "_asset_publisher_" + pageCount, "", portletId)>

	${writerAssetPublisherCSV.write(layout.friendlyURL + "\n")}

	<@insertLayout
		_layout = layout
	/>

	<#assign portletPreferencesList = dataFactory.newPortletPreferencesForAssetPublisher(layout.plid)>

	<#list portletPreferencesList as portletPreferences>
		<@insertPortletPreferences
			_portletPreferences = portletPreferences
		/>
	</#list>

	<#assign portletPreferences = dataFactory.newPortletPreferences(layout.plid, groupId, portletId, pageCount)>

	<@insertPortletPreferences
		_portletPreferences = portletPreferences
	/>
</#list>