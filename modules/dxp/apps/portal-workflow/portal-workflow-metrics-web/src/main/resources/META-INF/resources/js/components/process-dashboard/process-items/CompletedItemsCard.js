import {
	getFiltersParam,
	verifySelectedItems
} from '../../../shared/components/filter/util/filterUtil';
import {AppContext} from '../../AppContext';
import {filterKeys} from '../instance-list/filterConstants';
import ProcessItemsCard from './ProcessItemsCard';
import React from 'react';
import timeRangeStore from '../store/timeRangeStore';

class CompletedItemsCard extends React.Component {
	constructor(props) {
		super(props);

		this.state = {
			loaded: false
		};
	}

	componentDidMount() {
		return timeRangeStore.fetchTimeRanges().then(() =>
			this.setState({
				loaded: true
			})
		);
	}

	get filter() {
		const {timeRanges} = timeRangeStore.getState();

		if (!timeRanges.length) {
			return null;
		}

		const filter = {
			defaultItem: timeRangeStore.defaultTimeRange,
			hideControl: true,
			items: [...timeRanges],
			key: filterKeys.completionPeriod,
			multiple: false,
			name: Liferay.Language.get('completion-period')
		};

		const {query} = this.props;

		const filtersParam = getFiltersParam(query);

		verifySelectedItems(filter, filtersParam);

		timeRangeStore.setState({
			timeRanges: filter.items
		});

		const {selectedTimeRange} = timeRangeStore;

		if (selectedTimeRange) {
			filter.name = selectedTimeRange.name;
		}

		return filter;
	}

	render() {
		const {processId} = this.props;
		const {selectedTimeRange} = timeRangeStore;

		return (
			<ProcessItemsCard
				completed
				description={Liferay.Language.get(
					'completed-items-description'
				)}
				filter={this.filter}
				processId={processId}
				timeRange={selectedTimeRange}
				title={Liferay.Language.get('completed-items')}
			/>
		);
	}
}

CompletedItemsCard.contextType = AppContext;
export default CompletedItemsCard;