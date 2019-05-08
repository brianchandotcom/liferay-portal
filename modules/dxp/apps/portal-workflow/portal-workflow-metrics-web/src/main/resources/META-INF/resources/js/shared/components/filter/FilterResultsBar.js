import {
	pushToHistory,
	removeFilters
} from '../../../shared/components/filter/util/filterUtil';
import autobind from 'autobind-decorator';
import FilterResultsItem from './FilterResultsItem';
import React from 'react';
import { sub } from '../../../shared/util/lang';
import { withRouter } from 'react-router-dom';

class FilterResultsBar extends React.Component {
	@autobind
	onClearAllButtonClick() {
		const {
			location: { search }
		} = this.props;

		const query = removeFilters(search);

		pushToHistory(query, this.props);
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

		const { totalCount } = this.props;

		let resultText = Liferay.Language.get('x-results-for-x');

		if (totalCount === 1) {
			resultText = Liferay.Language.get('x-result-for-x');
		}

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
							filter.items.map((item, index) => (
								<FilterResultsItem filter={filter} item={item} key={index} />
							))
						)}

						<li className="tbar-item tbar-item-expand">
							<div className="tbar-section text-right">
								<button
									className="btn btn-unstyled component-link tbar-link"
									onClick={this.onClearAllButtonClick}
									type="button"
								>
									{Liferay.Language.get('clear-all')}
								</button>
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