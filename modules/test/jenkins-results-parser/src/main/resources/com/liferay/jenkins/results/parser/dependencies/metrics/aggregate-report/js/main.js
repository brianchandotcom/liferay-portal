function createBuildCountLineChart(timelineData, elementID) {
	let datasets = getDatasets(timelineData, 'buildCounts');

	let lineChart = getLineChart('Build Count', datasets, elementID, 'Builds');

	lineChart.options.title = {
		display: false
	};

	lineChart.options.tooltips = {
		callbacks: {
		    label: function(tooltipItem, data) {
		        let label = data.datasets[tooltipItem.datasetIndex].label || '';

		        if (label) {
		            label += ': ';
		        }

		        label += tooltipItem.yLabel;

		        label += ' (';

		        label += Math.round(tooltipItem.yLabel * 10000 / MAX_WEEKLY_SERVER_DURATION_MILLIS) / 100;

		        label += '%)'

		        return label;
		    }
		},
		mode: 'index'
	};
}

function createDurationLineChart(chartTitle, datasets, elementID) {
	let lineChart = getLineChart(chartTitle, datasets, elementID, 'Duration (mins)');

	lineChart.options.scales.yAxes[0].ticks = {
		beginAtZero: true,
		callback: function(value) {
			return Math.round(value / 60000);
		}
	};

	lineChart.options.tooltips = {
		callbacks: {
			label: function(tooltipItem, data) {
			    let label = data.datasets[tooltipItem.datasetIndex].label || '';

			    if (label) {
			        label += ': ';
			    }

			    label += Math.round(tooltipItem.yLabel / 60000);

			    label += ' mins';

			    return label;
			}
		},
		mode: 'index'
	};
}

function createBuildDurationLineChart(timelineData, elementID) {
	let datasets = getDatasets(timelineData, 'averageBuildTime');

	createDurationLineChart('Average Build Duration', datasets, elementID);
}

function createQueueDurationLineChart(timelineData, elementID) {
	let datasets = getDatasets(timelineData, 'averageQueueTime');

	createDurationLineChart('Average Queue Duration', datasets, elementID);
}

function getDataPoints(xDataPoints, yDataPoints) {
	let dataPoints = [];

	for (let i = 0; i < xDataPoints.length; i++) {
		let dataPoint = {
			x: xDataPoints[i],
			y: yDataPoints[i]
		};

		dataPoints.push(dataPoint);
	}

	return dataPoints;
}

function getDatasets(timelineData, key) {
	let datasets = [];

	for (let i = 0; i < timelineData.jobTimelines.length; i++) {
		let color = getColor(datasets.length);

		let jobTimeline = timelineData.jobTimelines[i];

		let dataset = {
			backgroundColor: color,
			borderColor: color,
			data: getDataPoints(timelineData.time, jobTimeline[key]),
			label: timelineData.jobTimelines[i].name
		};

		datasets.push(dataset);
	}

	return datasets;
}

function getLineChart(chartTitle, datasets, elementID, yLabel) {
	return new Chart(document.getElementById(elementID), {
		data: {
			datasets: datasets
		},
		options: {
			elements: {
				point: {
					hitRadius: 10,
					hoverRadius: 4,
					radius: 0
				}
			},
			hover: {
			    animationDuration: 0
			},
			maintainAspectRatio: false,
			responsive: true,
			scales: {
				xAxes: [{
					autoSkipPadding: 50,
					type: 'time',
					ticks: {
						sampleSize: 100
					},
					time: {
					    displayFormats: {
					        hour: 'ddd MMM DD hA'
					    },
					    unit: 'hour'
					}
				}],
				yAxes: [{
					scaleLabel: {
						display: true,
						labelString: yLabel
					},
					stacked: true,
					ticks: {
						beginAtZero: true
					}
				}]
			},
			title: {
				display: true,
				fontSize: 14,
				text: chartTitle
			}
		},
		type: 'line'
	});
}

let buttonElements = document.getElementsByClassName('accordion');

for (let i = 0; i < buttonElements.length; i++) {
	buttonElements[i].addEventListener('click', function () {
		this.classList.toggle('active');

		let panelDivElement = this.nextElementSibling;

		if (panelDivElement.style.display === 'block') {
			panelDivElement.style.display = 'none';
		}
		else {
			panelDivElement.style.display = 'block';
		}
	});
}

window.onload = function () {
	for (let i = 0; i < buttonElements.length; i++) {
		buttonElements[i].classList.toggle('active');
		buttonElements[i].nextElementSibling.style.display = 'block';
	}
}

if ((typeof timelineData !== 'undefined') && timelineData) {
	createBuildCountLineChart(timelineData, 'build-history-canvas');

	createBuildDurationLineChart(timelineData, 'build-duration-canvas');

	createQueueDurationLineChart(timelineData, 'queue-duration-canvas');
}

if ((typeof tableData !== 'undefined') && tableData) {
	let tableElement = createTable(tableData, 'build-history-table');

	addTotalColumn(tableElement);

	Sortable.init();
}