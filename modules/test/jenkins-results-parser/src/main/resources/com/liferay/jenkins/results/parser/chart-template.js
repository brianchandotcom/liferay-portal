
var ctx = document.getElementById("timeline");
var timeline = new Chart(ctx, {
	data: {
		labels: 'xData',
		datasets: [{
			backgroundColor: 'rgba(255, 99, 132, 0.3)',
			borderWidth: 0,
			data: 'y1Data',
			label: 'Jenkins Slaves in Use'
		}, {
            backgroundColor: 'rgba(54, 162, 235, 1)',
            borderWidth: 0,
            data: 'y2Data',
            label: 'Axis Invocations'
        }]
	},
	options: {
	    elements: {
            point: {
                hitRadius: 10,
                hoverRadius: 4,
                radius: 0
            }
        },
		maintainAspectRatio: false,
		responsive: true,
		scales: {
			xAxes: [{
			    scaleLabel: {
			        display: true, labelString: 'Elapsed Time (hh:mm:ss)'
			    },
				ticks: {
					autoSkipPadding: 50,
					callback: function(value) {
					    var time = new Date(value);

                        var hours = time.getUTCHours().toString();
                        var minutes = time.getUTCMinutes().toString();
                        var seconds = time.getUTCSeconds().toString();

                        if (hours.length == 1) {
                             hours = '0' + hours;
                        }
                        if (minutes.length == 1) {
                             minutes = '0' + minutes;
                        }
                        if (seconds.length == 1) {
                             seconds = '0' + seconds;
                        }

						return hours + ':' + minutes + ':' + seconds;
					}
				}
			}],
			yAxes: [{
				ticks: {
					beginAtZero: true
				}
			}]
		}
	},
	type: 'line'
});
