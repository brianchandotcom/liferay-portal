public class TempClass {
	public void method() {
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		SearchContainer<AccountEntryDisplay> accountEntryDisplaySearchContainer = AccountEntryDisplaySearchContainerFactory.create(liferayPortletRequest, liferayPortletResponse);

		ViewAccountEntriesManagementToolbarDisplayContext viewAccountEntriesManagementToolbarDisplayContext = new ViewAccountEntriesManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, accountEntryDisplaySearchContainer);
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		row.setData(
				HashMapBuilder.<String, Object>put(
						"actions", StringUtil.merge(viewAccountEntriesManagementToolbarDisplayContext.getAvailableActions(accountEntryDisplay))
				).build());
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		if (!AccountEntryPermission.contains(permissionChecker, accountEntryDisplay.getAccountEntryId(), ActionKeys.UPDATE)) {
			rowURL = null;
		}
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		if (portletName.equals(AccountPortletKeys.ACCOUNT_ENTRIES_MANAGEMENT)) {
		}
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		if (accountEntryDisplay.isSelectedAccountEntry(themeDisplay.getScopeGroupId(), user.getUserId())) {
		}
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
		// PLACEHOLDER
	}
}