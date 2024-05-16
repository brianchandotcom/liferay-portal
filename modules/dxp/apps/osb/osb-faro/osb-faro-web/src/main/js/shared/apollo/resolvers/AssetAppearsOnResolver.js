import {v4 as uuidv4} from 'uuid';

const getRndInteger = (min, max) =>
	Math.floor(Math.random() * (max - min + 1)) + min;

const metrics = {
	downloadsMetric: {
		__typename: 'AssetMetric',
		name: 'downloadsMetric'
	},
	previewsMetric: {
		__typename: 'AssetMetric',
		name: 'previewsMetric'
	},
	submissionsMetric: {
		__typename: 'AssetMetric',
		name: 'submissionsMetric'
	},
	viewsMetric: {
		__typename: 'AssetMetric',
		name: 'viewsMetric'
	}
};

function generateItems({selectedMetrics, size}) {
	const arr = new Array(size);

	for (let i = 0; i < arr.length; i++) {
		const assetTitle = uuidv4();
		const assetId = `http://liferay.com/web/test/abc/123/${assetTitle}`;

		arr[i] = {
			__typename: 'BlogMetric',
			assetId,
			assetTitle,
			selectedMetrics: selectedMetrics.map(selectedMetric => ({
				...metrics[selectedMetric],
				value: getRndInteger(0, 1000000)
			}))
		};
	}

	return arr;
}

export default (_, variables) => ({
	__typename: 'AssetPages',
	assetMetrics: generateItems(variables),
	total: 1000
});
