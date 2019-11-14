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

import pathToRegexp from 'path-to-regexp';
import React from 'react';
import {Link} from 'react-router-dom';

import {useRouter} from '../../hooks/useRouter.es';
import {sub} from '../../util/lang.es';
import Icon from '../Icon.es';
import {removeItem, removeFilters} from '../filter/util/filterUtil.es';

const ResultsBar = ({children}) => {
	return (
		<nav className="subnav-tbar subnav-tbar-primary tbar tbar-inline-xs-down">
			<div className="container-fluid container-fluid-max-xl">
				<ul className="tbar-nav tbar-nav-wrap">{children}</ul>
			</div>
		</nav>
	);
};

const Clear = props => {
	const {
		location: {search},
		match: {path}
	} = useRouter();

	const handleClearAll = () => {
		const {filters = []} = props;

		filters.map(filter => {
			filter.items.map(item => {
				item.active = false;
			});
		});
	};

	const pathname = pathToRegexp.compile(path)(props);

	const query = removeFilters(search);

	return (
		<li className="tbar-item tbar-item-expand">
			<div className="tbar-section text-right">
				<Link
					className="component-link tbar-link"
					data-testid="clearAll"
					onClick={handleClearAll}
					to={{
						pathname,
						search: query
					}}
				>
					<span>{Liferay.Language.get('clear-all')}</span>
				</Link>
			</div>
		</li>
	);
};

const FilterItem = props => {
	const {filter, item} = props;
	const {
		location: {search},
		match: {path}
	} = useRouter();

	const pathname = pathToRegexp.compile(path)(props);

	const query = removeItem(filter.key, item, search);

	const removeFilter = () => {
		item.active = false;
	};

	return (
		<li className="tbar-item">
			<div className="tbar-section">
				<span className="component-label label label-dismissible tbar-label">
					<span className="label-item label-item-expand">
						<div className="label-section">
							<span className="font-weight-normal">{`${filter.name}: `}</span>

							<strong>{item.name}</strong>
						</div>
					</span>

					{!filter.pinned && (
						<span className="label-item label-item-after">
							<Link
								aria-label="close"
								className="close"
								data-testid="removeFilter"
								onClick={removeFilter}
								to={{
									pathname,
									search: query
								}}
							>
								<Icon iconName="times" />
							</Link>
						</span>
					)}
				</span>
			</div>
		</li>
	);
};

const FilterItems = props => {
	const {filters = []} = props;

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
