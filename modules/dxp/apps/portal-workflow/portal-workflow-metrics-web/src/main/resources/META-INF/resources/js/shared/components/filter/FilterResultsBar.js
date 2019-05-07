import { Link, withRouter } from 'react-router-dom';
import {
	pushToHistory,
	removeFilters,
	removeItem
} from '../../../shared/components/filter/util/filterUtil';
import Icon from '../../../shared/components/Icon';
import React from 'react';
import { sub } from '../../../shared/util/lang';

class FilterResultsBar extends React.Component {
	onRemoveButtonClick(filter, itemToRemove) {
		const {
			location: { search }
		} = this.props;

		const filterQuery = removeItem(filter, itemToRemove, search);

		pushToHistory(filterQuery, this.props);
	}

	get removeFiltersQuery() {
		const {
			location: { search }
		} = this.props;

		return removeFilters(search);
	}

	get selectedFilters() {
		const { filters } = this.props;

		return filters.filter(filter => {
			filter.items = filter.items
				? filter.items.filter(item => item.active)
				: [];

			return !!filter.items.length;
		});
	}

	render() {
		const { selectedFilters } = this;

		if (!selectedFilters.length) {
			return null;
		}

		const {
			location: { pathname },
			totalCount
		} = this.props;

		let resultText = Liferay.Language.get('x-results-for-x');

		if (totalCount === 1) {
			resultText = Liferay.Language.get('x-result-for-x');
		}

		const onRemoveButtonClick = (filter, item) => () =>
			this.onRemoveButtonClick(filter, item);

		const itemRender = (filter, index, item) => (
			<li className="tbar-item" key={index}>
				<div className="tbar-section">
					<span className="component-label label label-dismissible tbar-label">
						<span className="label-item label-item-expand">
							<div className="label-section">
								<span className="font-weight-normal">{`${filter.name}: `}</span>

								<strong>{item.name}</strong>
							</div>
						</span>
						<span className="label-item label-item-after">
							<button
								aria-label="close"
								className="btn close"
								onClick={onRemoveButtonClick(filter, item)}
								type="button"
							>
								<Icon iconName="times" />
							</button>
						</span>
					</span>
				</div>
			</li>
		);

		return (
			<nav className="subnav-tbar subnav-tbar-primary tbar tbar-inline-xs-down">
				<div className="container-fluid container-fluid-max-xl">
					<ul className="tbar-nav tbar-nav-wrap">
						<li className="tbar-item">
							<div className="tbar-section">
								<span className="component-text text-truncate-inline">
									<span className="text-truncate">
										{sub(resultText, [totalCount, ''])}
									</span>
								</span>
							</div>
						</li>

						{selectedFilters.map(filter =>
							filter.items.map((item, index) => itemRender(filter, index, item))
						)}

						<li className="tbar-item tbar-item-expand">
							<div className="tbar-section text-right">
								<Link
									className="component-link tbar-link"
									to={{
										pathname,
										search: this.removeFiltersQuery
									}}
								>
									<span>{Liferay.Language.get('clear-all')}</span>
								</Link>
							</div>
						</li>
					</ul>
				</div>
			</nav>
		);
	}
}

export default withRouter(FilterResultsBar);
export { FilterResultsBar };