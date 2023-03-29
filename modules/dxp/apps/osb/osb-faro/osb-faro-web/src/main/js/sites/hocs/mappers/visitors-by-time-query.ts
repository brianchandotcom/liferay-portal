import {getSafeRangeSelectors} from 'shared/util/util';
import {safeResultToProps} from 'shared/util/mappers';
import {sum} from 'lodash';
import {WEEKDAYS} from 'shared/util/date';

const mapResultToProps: () => object = safeResultToProps(result => {
	const data = result.siteVisitorHeatMap;
	const sumTotal = sum(data.map(({value}) => value));

	return !sumTotal
		? {data, total: 0}
		: {
				data: data.map(item => ({
					...item,
					column: WEEKDAYS[item.column]
				}))
		  };
});

const mapPropsToOptions: object = ({
	rangeSelectors,
	router: {
		params: {channelId}
	}
}) => ({
	variables: {
		channelId,
		...getSafeRangeSelectors(rangeSelectors)
	}
});

export {mapPropsToOptions, mapResultToProps};
