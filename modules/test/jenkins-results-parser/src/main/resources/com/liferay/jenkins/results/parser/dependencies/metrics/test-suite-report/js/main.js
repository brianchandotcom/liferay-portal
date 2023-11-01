window.onload = function () {
	var statusChangesRowHeader = getElementByXpath("//th[contains(.,'Test Suite')]");

	triggerEvent(statusChangesRowHeader, 'click');
}

if ((typeof tableData !== 'undefined') && tableData) {
	createTable(tableData, "test-suite-data-table");

	Sortable.init();
}