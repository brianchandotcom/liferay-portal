window.onload = function () {
	var statusChangesRowHeader = getElementByXpath("//th[contains(.,'Test Suite')]");

	triggerEvent(statusChangesRowHeader, 'click');
}

var headerElement = document.getElementById('report-name');

headerElement.textContent = reportName;

var titleElement = document.getElementById('title');

titleElement.textContent = reportName;

if ((typeof tableData !== 'undefined') && tableData) {
	createTable(tableData, "test-suite-data-table");

	Sortable.init();
}