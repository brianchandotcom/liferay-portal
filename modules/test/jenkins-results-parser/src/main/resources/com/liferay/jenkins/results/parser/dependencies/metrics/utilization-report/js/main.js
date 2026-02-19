function addUtilizationRows(tableElement) {
	var rowElements = tableElement.querySelectorAll('tbody tr');

	let utilizationRowElements = [];

	rowElements.forEach(rowElement => {
		let cellElements = rowElement.querySelectorAll('td');

		if (cellElements[1].textContent !== 'Total Server Duration') {
			return;
		}

		let utilizationRowElement = document.createElement('tr');

		for (let i = 0; i < cellElements.length; i++) {
			let utilizationCellElement;

			if (i == 0) {
				utilizationCellElement = cellElements[i].cloneNode(true);
			}
			else if (i == 1) {
				utilizationCellElement = cellElements[i].cloneNode(true);

				utilizationCellElement.textContent = 'Utilization Percentage';
			}
			else {
				utilizationCellElement = document.createElement('td');

				let percentage = parseFloat(cellElements[i].getAttribute('data-value')) * 100 / (maxWeeklyServerDurationMillis || MAX_WEEKLY_SERVER_DURATION_MILLIS);

				percentage = percentage.toFixed(2);

				utilizationCellElement.setAttribute('data-value', percentage);

				utilizationCellElement.append(document.createTextNode(percentage.toString() + '%'));
			}

			utilizationRowElement.appendChild(utilizationCellElement);
		}

		utilizationRowElements.push(utilizationRowElement);
	});

	var tbodyElement = tableElement.querySelector('tbody');

	utilizationRowElements.forEach(rowElement => {
		tbodyElement.appendChild(rowElement);
	});
}

function updateHeaderNames(tableElement) {
	headerElements = tableElement.querySelectorAll('thead tr th');

	headerElements.forEach(headerElement => {
		if (headerElement.classList.contains('col-1') || headerElement.classList.contains('col-2')) {
			return;
		}

		let date = moment(headerElement.getAttribute('value'), 'YYYYMMDD');

		headerElement.textContent = 'Week of ' + date.format('MMM DD');
	});
}

addReportName();

if ((typeof categoryTableData !== 'undefined') && categoryTableData) {
	let categoryTableDataElement = createTable(categoryTableData, 'utilization-category-data-table');

	addUtilizationRows(categoryTableDataElement);

	updateHeaderNames(categoryTableDataElement);

	window.onload = function () {
		triggerEvent(getElementByXpath('//th[contains(.,"Category")]'), 'click');

		createBarChartFromTable('Weekly Node Utilization by Job Category', 'utilization-canvas', 'Utilization Percentage', categoryTableDataElement, '%', '100');
	}
}

if ((typeof testTypeTableData !== 'undefined') && testTypeTableData) {
	let testTypeTableDataElement = createTable(testTypeTableData, 'utilization-test-type-data-table');

	updateHeaderNames(testTypeTableDataElement);
}

Sortable.init();