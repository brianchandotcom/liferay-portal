import {
	completionPeriodFilter,
	processStatusFilter,
	processStatusItems,
	processStepFilter,
	slaStatusFilter,
	slaStatusItems
} from './PageFilters';
import {
	getFiltersParam,
	isSelected,
	verifySelectedItems
} from '../../../shared/components/filter/util/filterUtil';
import React, {Fragment} from 'react';
import {AppContext} from '../../AppContext';
import Filter from '../../../shared/components/filter/Filter';
import FilterResultsBar from '../../../shared/components/filter/FilterResultsBar';
import {processStatusKeys} from './filterConstants';
import processTaskStore from '../store/processTaskStore';
import timeRangeStore from '../store/timeRangeStore';

class InstanceListFilter extends React.Component {
	constructor(props) {
		super(props);

		this.state = {
			loaded: false
		};
	}

	componentWillMount() {
		return this.loadData();
	}

	get filters() {
		const filters = [
			{
				...slaStatusFilter,
				items: [...slaStatusItems],
				multiple: true
			},
			{
				...processStatusFilter,
				items: [...processStatusItems],
				multiple: true
			}
		];

		const {query} = this.props;

		if (this.showCompletedFilter(query)) {
			filters.push({
				...completionPeriodFilter,
				items: [...timeRangeStore.getState().timeRanges],
				multiple: false
			});
		}

		filters.push({
			...processStepFilter,
			items: [...processTaskStore.getState().processTasks],
			multiple: true
		});

		const filtersParam = getFiltersParam(query);

		filters.forEach(filter => {
			verifySelectedItems(filter, filtersParam);
		});

		return filters;
	}

	loadData(props = this.props) {
		const promises = [
			processTaskStore.fetchProcessTasks(props.processId),
			timeRangeStore.fetchTimeRanges()
		];

		return Promise.all(promises).then(() =>
			this.setState({
				loaded: true
			})
		);
	}

	showCompletedFilter(query) {
		return isSelected(
			processStatusFilter.key,
			processStatusKeys.completed,
			query
		);
	}

	render() {
		const {filters} = this;
		const {totalCount} = this.props;

		return (
			<Fragment>
				<nav className='management-bar management-bar-light navbar navbar-expand-md'>
					<div className='container-fluid container-fluid-max-xl'>
						<ul className='navbar-nav'>
							<li className='nav-item'>
								<strong className='ml-0 mr-0 navbar-text'>
									{Liferay.Language.get('filter-by')}
								</strong>
							</li>

							{filters.map(filter => (
								<Filter
									{...filter}
									filterKey={filter.key}
									key={filter.key}
								/>
							))}
						</ul>
					</div>
				</nav>

				<FilterResultsBar filters={filters} totalCount={totalCount} />
			</Fragment>
		);
	}
}

InstanceListFilter.contextType = AppContext;
export default InstanceListFilter;