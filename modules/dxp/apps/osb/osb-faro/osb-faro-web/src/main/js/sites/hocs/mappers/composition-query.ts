import {CompositionTypes} from 'shared/util/constants';
import {getSafeRangeSelectors} from 'shared/util/util';
import {safeResultToProps} from 'shared/util/mappers';

const getMapResultToProps = (compositionBagName: CompositionTypes) =>
	safeResultToProps(
		({
			[compositionBagName]: {compositions, maxCount, total, totalCount}
		}: {
			[key: string]: {
				compositions: Array<any>;
				maxCount: number;
				total: number;
				totalCount: number;
			};
		}) => ({
			empty: !total,
			items: compositions,
			maxCount,
			total,
			totalCount
		})
	);

const mapPropsToOptions: object = ({
	channelId,
	delta,
	id,
	page,
	rangeSelectors
}) => ({
	variables: {
		channelId,
		id,
		size: delta,
		start: (page - 1) * delta,
		...getSafeRangeSelectors(rangeSelectors)
	}
});

const mapCardPropsToOptions: object = ({
	activeTabId,
	channelId,
	rangeSelectors
}) => ({
	variables: {
		activeTabId,
		channelId,
		size: 5,
		start: 0,
		...getSafeRangeSelectors(rangeSelectors)
	}
});

export {getMapResultToProps, mapCardPropsToOptions, mapPropsToOptions};
