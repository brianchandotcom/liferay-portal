const COLORS = [
	'#59adf6',
	'#42d6a4',
	'#ff6961',
	'#ffb480',
	'#f8f38d',
	'#08cad1',
	'#9d94ff',
	'#c780e8'
];

const MAX_WEEKLY_SERVER_DURATION_MILLIS = 2370 * 7 * 24 * 60 * 60 * 1000;

function addReportName() {
	var headerElement = document.getElementById('report-name');

	headerElement.textContent = reportName;

	var titleElement = document.getElementById('title');

	titleElement.textContent = reportName;
}

function addTotalColumn(tableElement) {
	theadElement = tableElement.querySelector('thead tr');

	let totalHeaderElement = document.createElement('th');

	totalHeaderElement.textContent = 'Total';

	theadElement.appendChild(totalHeaderElement);

	var rowElements = tableElement.querySelectorAll('tbody tr');

	rowElements.forEach(rowElement => {
		let totalValue = 0;

		let cellElements = rowElement.querySelectorAll('td');

		for (let i = 2; i < cellElements.length; i++) {
			let value = parseFloat(cellElements[i].getAttribute('data-value'));

			if (!isNaN(value)) {
				totalValue += value;
			}
		}

		let totalCellElement = document.createElement('td');

		totalCellElement.setAttribute('data-value', totalValue);

		if (cellElements[1].textContent.includes('Duration')) {
			totalValue = getReadableDuration(totalValue);
		}

		totalCellElement.textContent = totalValue;

		rowElement.appendChild(totalCellElement);
	});
}

function createTable(table, tableElementID) {
	let tableElement = document.getElementById(tableElementID);

	let tbodyElement = tableElement.createTBody();

	table.forEach((cellValues, index) => {
		if (index == 0) {
			let theadElement = tableElement.createTHead();

			theadElement.classList.add('thead-light');

			let rowElement = theadElement.insertRow();

			cellValues.forEach((cellValue, columnIndex) => {
				let thElement = document.createElement('th');

				if (columnIndex == 0) {
					thElement.classList.add('col-1');
				}
				else if (columnIndex == 1) {
					thElement.classList.add('col-2');
				}
				else {
					thElement.setAttribute('value', cellValue);

					let date = moment(cellValue, 'YYYYMMDD');

					cellValue = date.format('ddd MMM DD');
				}

				thElement.appendChild(document.createTextNode(cellValue));

				rowElement.appendChild(thElement);
			});

			return;
		}

		let rowElement = tbodyElement.insertRow();

		cellValues.forEach((cellValue, columnIndex) => {
			let cellElement = rowElement.insertCell();

			if (columnIndex == 0) {
				cellElement.classList.add('col-1');
				cellElement.classList.add('truncate');
			}

			if (columnIndex == 1) {
				cellElement.classList.add('col-2');
			}

			let node = null;

			if ((typeof cellValue === 'string') || (cellValue instanceof String)) {
				let divElement = document.createElement('div');
				let spanElement = document.createElement('span');

				spanElement.appendChild(document.createTextNode(cellValue));

				divElement.appendChild(spanElement);

				divElement.setAttribute('data-value', cellValue);

				node = divElement;
			}
			else {
				cellElement.setAttribute('data-value', cellValue);

				if (cellValues[1].includes('Duration')) {
					cellValue = getReadableDuration(cellValue);
				}

				node = document.createTextNode(cellValue);
			}

			cellElement.appendChild(node);

		});

	});

	return tableElement;
}

function getColor(index) {
	return COLORS[index % COLORS.length];
}

function getElementByXpath(path) {
	return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
}

function getReadableDuration(milliseconds) {
	if (milliseconds == 0) {
		return milliseconds;
	}

	let duration = moment.duration(milliseconds);

	if (duration.asHours() >= 10) {
		return Math.round(duration.asHours()) + ' hours';
	}

	if (duration.asHours() >= 1) {
		return Math.round(duration.asHours() * 10) / 10 + ' hours';
	}

	if (Math.round(duration.asMinutes()) >= 1) {
		return Math.round(duration.asMinutes()) + ' minutes';
	}

	return '< 1 minute';
}

function searchTable(inputID, tableID) {
	let inputElement = document.getElementById(inputID);
	let tableElement = document.getElementById(tableID);

	let inputValue = inputElement.value.toUpperCase();

	let rowElements = tableElement.getElementsByTagName('tr');

	for (let i = 0; i < rowElements.length; i++) {
		let cellElements = rowElements[i].getElementsByTagName('td');

		for (let j = 0; j < cellElements.length; j++) {
			if (j < 2) {
				let cellValue = cellElements[j].textContent || cellElements[j].innerText;

				cellValue = cellValue.toUpperCase();

				if (cellValue.indexOf(inputValue) > -1) {
					rowElements[i].style.display = '';

					break;
				}
				else {
					rowElements[i].style.display = 'none';
				}
			}
		}
	}
}

function triggerEvent(element, eventName) {
	element.dispatchEvent(new Event(eventName));
}