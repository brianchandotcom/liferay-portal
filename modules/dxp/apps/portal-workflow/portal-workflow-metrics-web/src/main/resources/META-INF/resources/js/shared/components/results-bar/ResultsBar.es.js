/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton from '@clayui/button';
import React, {useCallback} from 'react';

import {useFilter} from '../../hooks/useFilter.es';
import {useRouter} from '../../hooks/useRouter.es';
import {sub} from '../../util/lang.es';
import Icon from '../Icon.es';
import {
	removeFilters,
	removeItem,
	replaceHistory
} from '../filter/util/filterUtil.es';

const ResultsBar = ({children}) => {
	return (
		<nav className="mt-0 subnav-tbar subnav-tbar-primary tbar tbar-inline-xs-down">
			<div className="container-fluid container-fluid-max-xl">
				<ul className="tbar-nav tbar-nav-wrap">{children}</ul>
			</div>
		</nav>
	);
};

const Clear = ({filters = [], filterKeys = [], withoutRouteParams}) => {
	const {dispatch, filterState} = useFilter({withoutRouteParams});
	const routerProps = useRouter();

	const handleClearAll = useCallback(() => {
		filters.map(filter => {
			filter.items.map(item => {
				item.active = false;
			});
		});

		filterKeys.forEach(key => {
			filterState[key] = undefined;
		});

		dispatch(filterState);

		if (!withoutRouteParams) {
			const query = removeFilters(routerProps.location.search);

			replaceHistory(query, routerProps);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [filterState, routerProps, withoutRouteParams]);

	return (
		<li className="tbar-item tbar-item-expand">
			<div className="tbar-section text-right">
				<ClayButton
					className="component-link tbar-link"
					data-testid="clearAll"
					displayType="link"
					onClick={handleClearAll}
					small
				>
					{Liferay.Language.get('clear-all')}
				</ClayButton>
			</div>
		</li>
	);
};

const FilterItem = ({filter, item, withoutRouteParams}) => {
	const {dispatch, filterState} = useFilter({withoutRouteParams});
	const routerProps = useRouter();

	const removeFilter = useCallback(() => {
		item.active = false;

		filterState[filter.key] = filterState[filter.key]
			? filterState[filter.key].filter(({key}) => key !== item.key)
			: undefined;

		dispatch(filterState);

		if (!withoutRouteParams) {
			const query = removeItem(
				filter.key,
				item,
				routerProps.location.search
			);

			replaceHistory(query, routerProps);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [filterState, routerProps, withoutRouteParams]);

	return (
		<li className="tbar-item">
			<div className="tbar-section">
				<span className="component-label label label-dismissible tbar-label">
					<span className="label-item label-item-expand">
						<div className="label-section">
							<span className="font-weight-normal">{`${filter.name}: `}</span>

							<strong>
								{filter.items[0].key !== 'custom'
									? item.name
									: item.resultName}
							</strong>
						</div>
					</span>

					{!filter.pinned && (
						<span className="label-item label-item-after">
							<ClayButton
								className="text-dark"
								data-testid="removeFilter"
								displayType="unstyled"
								onClick={removeFilter}
							>
								<Icon iconName="times" />
							</ClayButton>
						</span>
					)}
				</span>
			</div>
		</li>
	);
};

const FilterItems = ({filters = [], ...props}) => {
	return filters.map(filter =>
		filter.items.map((item, index) => (
			<FilterItem filter={filter} item={item} key={index} {...props} />
		))
	);
};

const TotalCount = ({search, totalCount}) => {
	let resultText = Liferay.Language.get('x-results-for-x');

	if (totalCount === 1) {
		resultText = Liferay.Language.get('x-result-for-x');
	}

	return (
		<li className="tbar-item">
			<div className="tbar-section">
				<span className="component-text text-truncate-inline">
					<span className="text-truncate" data-testid="totalCount">
						{sub(resultText, [totalCount, search])}
					</span>
				</span>
			</div>
		</li>
	);
};

ResultsBar.Clear = Clear;
ResultsBar.FilterItems = FilterItems;
ResultsBar.TotalCount = TotalCount;

export default ResultsBar;
