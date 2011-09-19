Highcharts.setOptions({
	chart: {
		borderWidth: 2,
		marginTop: 3,
		marginBottom: 3
	},
	title: {
		text: null
	},
	tooltip: {
		formatter: function() {
			return this.y;
		}
	},
	xAxis: {
		labels: {
			enabled: false
		},
		tickWidth: 0,
		gridLineWidth: 0,
		lineWidth: 0,
		startOnTick: true,
		endOnTick: true
	},
	yAxis: {
		gridLineWidth: .75,
		tickWidth: 0,
		lineWidth: 1,
		title: {
			text: null
		},
		endOnTick: false,
		tickPixelInterval: 20,
		labels: {
			style: {
				color: '#000000'
			}
		},
		showFirstLabel: false,
		gridLineColor: '#000000'
	},
	legend: {
		enabled: false
	},
	credits: {
		enabled: false
	},
	plotOptions: {
		area: {
			marker: {
				enabled: false
			},
			fillColor: {
				linearGradient: [0, 0, 0, 250],
				stops: [
					[0, '#FFB700'],
					[1, 'rgba(2,0,0,0)']
				]
			},
			lineColor: '#000000',
			lineWidth: 2
		},
		pie: {
			borderColor: '#000000',
			innerSize: '30%',
			size: '95%',
			dataLabels: {
				enabled: false,
				connectorWidth: 0
			}
		},
		column: {
			borderColor: '#000000',
			pointPadding: 0,
            groupPadding: 0.1,
            minPointLength: 3
		}
	}
});