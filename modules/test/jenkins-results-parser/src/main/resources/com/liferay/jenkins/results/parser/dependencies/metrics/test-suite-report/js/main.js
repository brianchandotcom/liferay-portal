addReportName();

if ((typeof tableData !== 'undefined') && tableData) {
	let tableElement = createTable(tableData, 'test-suite-data-table');

	addTotalColumn(tableElement);

	window.onload = function () {
		triggerEvent(getElementByXpath('//th[contains(.,"Test Suite Name")]'), 'click');

		createBarChartFromTable('Daily Server Duration by Test Suite', 'server-duration-canvas', 'Total Server Duration', tableElement, 'hrs');
	}
}

Sortable.init();