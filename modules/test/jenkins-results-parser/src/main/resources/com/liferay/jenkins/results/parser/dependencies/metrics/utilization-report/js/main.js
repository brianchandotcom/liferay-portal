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

				let percentage = parseFloat(cellElements[i].getAttribute('data-value')) * 100 / MAX_WEEKLY_SERVER_DURATION_MILLIS;

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

function createBarChartFromTable(chartTitle, elementID, metricName, tableElement) {
	headerElements = tableElement.querySelectorAll('thead tr th');

	let xLabels = [];

	headerElements.forEach(headerElement => {
		if (headerElement.classList.contains('col-1') || headerElement.classList.contains('col-2')) {
			return;
		}

		xLabels.push(headerElement.textContent);
	});

	let datasets = [];
	let rowElements = tableElement.querySelectorAll('tbody tr');

	rowElements.forEach(rowElement => {
		let cellElements = rowElement.querySelectorAll('td');

		if ((cellElements[0].textContent === 'All') || (cellElements[0].textContent === '[Total]')) {
			return;
		}

		if (cellElements[1].textContent !== metricName) {
			return;
		}

		let dataValues = [];

		cellElements.forEach(cellElement => {
			if (cellElement.classList.contains('col-1') || cellElement.classList.contains('col-2')) {
				return;
			}

			dataValues.push(cellElement.getAttribute('data-value'));
		});

		let color = getColor(datasets.length);

		let dataset = {
			backgroundColor: color,
			borderColor: color,
			data: dataValues,
			label: cellElements[0].textContent
		};

		datasets.push(dataset);
	});

	let barChart = new Chart(document.getElementById(elementID), {
		data: {
			datasets: datasets,
			labels: xLabels
		},
		options: {
			maintainAspectRatio: false,
			responsive: true,
			scales: {
				xAxes: [{
					stacked: true,
				}],
				yAxes: [{
					scaleLabel: {
						display: true,
						labelString: 'Percentage of Nodes Utilized'
					},
					stacked: true,
					ticks: {
						beginAtZero: true,
						callback: function(value) {
							return value + '%';
						},
						max: 100
					}
				}]
			},
			title: {
				display: true,
				fontSize: 14,
				text: chartTitle
			},
			tooltips: {
				callbacks: {
					label: function(tooltipItem, data) {
				        let label = data.datasets[tooltipItem.datasetIndex].label;
				        let percentage = data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index];
				        let totalPercentage = 0;

				        for (let i = 0; i < data.datasets.length; i++) {
				            totalPercentage += parseFloat(data.datasets[i].data[tooltipItem.index]);
				        }

				        if (tooltipItem.datasetIndex != 0) {
				            return label + ' : ' + percentage + '%';
				        }
				        else {
				            return [label + ' : ' + percentage + '%', "Total : " + totalPercentage.toFixed(2) + '%'];
				        }
					}
				},
				itemSort: function(a, b) {
					return b.datasetIndex - a.datasetIndex;
				},
				mode: 'index'
			}
		},
		type: 'bar'
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

if ((typeof tableData !== 'undefined') && tableData) {
	let tableElement = createTable(tableData, 'utilization-data-table');

	addUtilizationRows(tableElement);

	updateHeaderNames(tableElement);

	Sortable.init();

	window.onload = function () {
		triggerEvent(getElementByXpath('//th[contains(.,"Category")]'), 'click');

		createBarChartFromTable('Weekly Node Utilization by Job Category', 'utilization-canvas', 'Utilization Percentage', tableElement);
	}
}