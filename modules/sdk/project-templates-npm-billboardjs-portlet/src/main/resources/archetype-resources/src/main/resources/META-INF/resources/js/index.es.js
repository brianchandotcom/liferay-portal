import { bb, d3 } from 'billboard.js/dist/billboard';

export default function(portletNamespace) {
	const columnsData = [
		['data1', 300, 350, 300, 100, 200, 100],
		['data2', 130, 100, 140, 200, 150, 50],
	];

	const barChart = bb.generate({
		data: {
			columns: columnsData,
			type: 'bar',
		},
		bar: {
			width: {
				ratio: 0.5,
			},
		},
		bindto: `#${portletNamespace}`,
	});
}