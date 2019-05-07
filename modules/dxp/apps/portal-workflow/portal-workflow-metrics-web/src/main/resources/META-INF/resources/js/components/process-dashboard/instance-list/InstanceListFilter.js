import {
	processStatusFilter,
	processStatusItems,
	processStepFilter,
	slaStatusFilter,
	slaStatusItems
} from './PageFilters';
import React, { Fragment } from 'react';
import { AppContext } from '../../AppContext';
import Filter from '../../../shared/components/filter/Filter';
import FilterResultsBar from '../../../shared/components/filter/FilterResultsBar';
import { getFiltersParam } from '../../../shared/components/filter/util/filterUtil';

class InstanceListFilter extends React.Component {
	constructor(props) {
		super(props);

		this.state = {
			processStepItems: []
		};
	}

	get filters() {
		const filters = [
			{
				...slaStatusFilter,
				items: [...slaStatusItems]
			},
			{
				...processStatusFilter,
				items: [...processStatusItems]
			},
			{
				...processStepFilter,
				items: [...this.state.processStepItems]
			}
		];
		const { query } = this.props;

		const filtersParam = getFiltersParam(query);

		filters.forEach(filter => {
			const filterValues = filtersParam[filter.key] || [];

			filter.items.forEach(item => {
				item.active = filterValues.includes(item.key);
			});
		});

		return filters;
	}

	componentWillMount() {
		return this.loadData();
	}

	loadData(props = this.props) {
		return this.requestData(props).then(({ items = [] }) => {
			this.setState({ processStepItems: items });
		});
	}

	requestData({ processId }) {
		const { client } = this.context;

		return client
			.get(`/processes/${processId}/tasks?fields=key,name&page=0&pageSize=0`)
			.then(({ data }) => {
				return data;
			});
	}

	render() {
		const { filters } = this;
		const { totalCount } = this.props;

		return (
			<Fragment>
				<nav className="management-bar management-bar-light navbar navbar-expand-md">
					<div className="container-fluid container-fluid-max-xl">
						<ul className="navbar-nav">
							<li className="nav-item">
								<strong className="ml-0 mr-0 navbar-text">
									{Liferay.Language.get('filter-by')}
								</strong>
							</li>

							{filters.map((filter, index) => (
								<Filter {...filter} filterKey={filter.key} key={index} />
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