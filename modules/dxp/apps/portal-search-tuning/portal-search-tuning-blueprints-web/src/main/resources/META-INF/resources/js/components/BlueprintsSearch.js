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
import {useResource} from '@clayui/data-provider';
import ClayEmptyState from '@clayui/empty-state';
import ClayLayout from '@clayui/layout';
import ClayLink from '@clayui/link';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {PropTypes} from 'prop-types';
import React, {useContext, useEffect, useState} from 'react';

import ThemeContext from '../ThemeContext';
import {sub} from '../utils/language';
import {
	buildUrl,
	formatFacets,
	formatSortBy,
	formatTimeRange,
	validDateRange,
} from '../utils/util';
import Facet from './Facet';
import Results from './Results';
import SearchBar from './SearchBar';
import SortSelect from './SortSelect';

const LOCATOR = {
	label: 'text',
	value: 'value',
};

export default function BlueprintsSearch({fetchResultsURL, suggestionsURL}) {
	const [activePage, setActivePage] = useState(1);
	const [query, setQuery] = useState('');
	const [selectedFacets, setSelectedFacets] = useState({});
	const [state, setState] = useState(() => ({
		error: false,
		loading: false,
	}));
	const [timeRange, setTimeRange] = useState({});
	const [sortBy, setSortBy] = useState({});

	const {namespace} = useContext(ThemeContext);

	const {refetch, resource} = useResource({
		link: buildUrl(fetchResultsURL, {
			[`${namespace}q`]: query,
			[`${namespace}page`]: activePage,
			...formatFacets(selectedFacets, namespace, LOCATOR.value),
			...formatTimeRange(timeRange, namespace),
			...formatSortBy(sortBy, namespace),
		}),
		onNetworkStatusChange: (status) =>
			setState({
				error: status === 5,
				loading: status < 4,
			}),
	});

	useEffect(() => {
		if (query) {
			if (timeRange.time === 'custom-range') {
				if (validDateRange(timeRange)) {
					refetch();
				}
			}
			else {
				refetch();
			}
		}
	}, [query, activePage, selectedFacets, timeRange, sortBy]); // eslint-disable-line

	const _hasResults = () =>
		!state.error && !!(resource && resource.hits && resource.hits.length);

	function _renderEmptyState() {
		let emptyState = (
			<ClayEmptyState imgSrc="/o/admin-theme/images/states/empty_state.gif" />
		);

		if (state.loading) {
			emptyState = <ClayLoadingIndicator />;
		}
		if (state.error) {
			emptyState = (
				<ClayEmptyState
					description={Liferay.Language.get(
						'an-unexpected-error-occurred'
					)}
					imgSrc="/o/admin-theme/images/states/empty_state.gif"
					title={Liferay.Language.get('unable-to-load-content')}
				/>
			);
		}

		return <>{emptyState}</>;
	}

	function _renderMetaData() {
		return (
			<div className="meta-data">
				{sub(
					Liferay.Language.get(
						'page-x-x-of-x-total-hits-search-took-x-seconds'
					),
					[
						resource.pagination.activePage,
						resource.pagination.totalPages,
						resource.meta.totalHits,
						resource.meta.executionTime,
					],
					false
				)}
			</div>
		);
	}

	function _renderShowInsteadOf() {
		return (
			<div className="misspellings">
				<div className="showing-results-for">
					{Liferay.Language.get('showing-results-for')}
					<span className="keyword">{resource.meta.keywords}</span>
				</div>
				<div className="instead-of">
					{Liferay.Language.get('search-instead-for')}
					<ClayLink className="link" href="#">
						{resource.meta.showingInsteadOf}
					</ClayLink>
				</div>
			</div>
		);
	}

	function _renderDidYouMeanSuggestions() {
		return (
			<div className="did-you-mean justify-content-center row">
				<h4>{Liferay.Language.get('did-you-mean-suggestions')}</h4>
				<ul>
					{resource.didYouMean.map((keyword, index) => (
						<li key={index}>{keyword}</li>
					))}
				</ul>
			</div>
		);
	}

	function _handleChangeSelectedFacets(param, facets) {
		setSelectedFacets((selectedFacets) => ({
			...selectedFacets,
			[`${param}`]: facets,
		}));

		setActivePage(1);
	}

	return (
		<>
			<div className="searchbar-wrapper">
				<SearchBar
					handleSubmit={(val) => {
						if (val !== query) {
							setQuery(val);
							setActivePage(1);
							setSelectedFacets({});
						}
						else {
							if (timeRange.time === 'custom-range') {
								if (validDateRange(timeRange)) {
									refetch();
								}
							}
							else {
								refetch();
							}
						}
					}}
					suggestionsURL={suggestionsURL}
				/>

				{query && (
					<ClayButton
						className="clear"
						displayType="unstyled"
						onClick={() => {
							setActivePage(1);
							setSelectedFacets({});
							setTimeRange({});
							setSortBy({});
						}}
						small
					>
						{Liferay.Language.get('clear')}
					</ClayButton>
				)}
			</div>

			{query && (
				<div className="search-results">
					{_hasResults() ? (
						<>
							{resource.meta.showingInsteadOf &&
								_renderShowInsteadOf()}

							{resource.facets && (
								<Facet
									facets={resource.facets}
									onChange={_handleChangeSelectedFacets}
									selectedFacets={selectedFacets}
								/>
							)}

							{resource.pagination.activePage &&
								resource.pagination.totalPages &&
								resource.meta.totalHits &&
								resource.meta.executionTime &&
								_renderMetaData()}

							<ClayLayout.Row justify="end">
								<SortSelect
									onChange={(val) => {
										setActivePage(1);
										setSortBy(val);
									}}
									value={sortBy}
								/>
							</ClayLayout.Row>

							<Results
								activePage={activePage}
								hits={resource.hits}
								onPageChange={(page) => {
									setActivePage(page);
								}}
								query={query}
								totalHits={resource.meta.totalHits}
								totalPages={resource.pagination.totalPages}
							/>
						</>
					) : (
						_renderEmptyState()
					)}

					{resource &&
						resource.didYouMean &&
						_renderDidYouMeanSuggestions()}
				</div>
			)}
		</>
	);
}

BlueprintsSearch.propTypes = {
	fetchResultsURL: PropTypes.string,
	suggestionsURL: PropTypes.string,
};
