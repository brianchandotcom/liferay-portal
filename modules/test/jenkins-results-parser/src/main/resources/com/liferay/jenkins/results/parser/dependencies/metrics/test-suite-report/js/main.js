window.onload = function () {
	var statusChangesRowHeader = getElementByXpath('//th[contains(.,"Test Suite")]');

	triggerEvent(statusChangesRowHeader, 'click');
}

addReportName();

if ((typeof tableData !== 'undefined') && tableData) {
	let tableElement = createTable(tableData, 'test-suite-data-table');

	addTotalColumn(tableElement);

	Sortable.init();
}