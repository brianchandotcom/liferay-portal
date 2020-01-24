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

import ClayTabs from '@clayui/tabs';
import {PropTypes} from 'prop-types';
import React, {Component} from 'react';

import ThemeContext from '../ThemeContext.es';
import FormValueDebugger from '../utils/FormValueDebugger.es';
import {fetchDocuments, fetchResponse} from '../utils/api.es';
import {DEFAULT_DELTA} from '../utils/constants.es';
import {
	isNil,
	move,
	removeIdFromList,
	resultsDataToMap,
	updateDataMap
} from '../utils/util.es';
import PageToolbar from './PageToolbar.es';
import Alias from './alias/Alias.es';
import List from './list/List.es';
import ErrorBoundary from './shared/ErrorBoundary.es';
import HiddenInputs from './shared/HiddenInputs.es';

const DELTA = DEFAULT_DELTA.label;

class ResultRankingsForm extends Component {
	static contextType = ThemeContext;

	static propTypes = {
		cancelUrl: PropTypes.string.isRequired,
		fetchDocumentsHiddenUrl: PropTypes.string.isRequired,
		fetchDocumentsSearchUrl: PropTypes.string.isRequired,
		fetchDocumentsVisibleUrl: PropTypes.string.isRequired,
		formName: PropTypes.string.isRequired,
		initialAliases: PropTypes.arrayOf(String),
		initialInactive: PropTypes.bool,
		resultsRankingUid: PropTypes.string,
		searchQuery: PropTypes.string.isRequired,
		validateFormUrl: PropTypes.string.isRequired
	};

	static defaultProps = {
		initialAliases: []
	};

	state = {
		/**
		 * Number of the active tab.
		 * @type {number}
		 */
		activeTabKeyValue: 0,

		/**
		 * A list of strings of aliases.
		 * @type {Array}
		 */
		aliases: this.props.initialAliases,

		/**
		 * Stores the original start and end indexes for submitting to be able
		 * to update the stored pinned ids.
		 * @type {Object}
		 */
		dataLoadIndex: {
			pinned: {
				end: 0,
				start: 0
			}
		},

		/**
		 * Display a loading spinner under the hidden results tab while data
		 * is fetching.
		 * @type {boolean}
		 */
		dataLoadingHidden: false,

		/**
		 * Display a loading spinner under the visible results tab while data
		 * is fetching.
		 * @type {boolean}
		 */
		dataLoadingVisible: false,

		/**
		 * Map of all the data. Key is the ID and value is the data object.
		 * @type {Object}
		 */
		dataMap: {},

		/**
		 * Display an error message when a data fetch request fails.
		 * @type {boolean}
		 */
		displayError: false,

		/**
		 * Display an error message when a data fetch request fails for the
		 * hidden results tab.
		 * @type {boolean}
		 */
		displayErrorHidden: false,

		/**
		 * "Pagination" for items loaded in hidden label.
		 * @type {number}
		 */
		hiddenCur: 0,

		/**
		 * Indicates whether ranking is active or inactive.
		 * @type {boolean}
		 */
		inactive: this.props.initialInactive,

		/**
		 * A full list of IDs which include hidden and pinned items. This is
		 * equivalent to the IDs in dataMap, but in a sorted order.
		 * @type {Array}
		 */
		resultIds: [],

		/**
		 * The list of IDs that are currently hidden.
		 * @type {Array}
		 */
		resultIdsHidden: [],

		/**
		 * The list of IDs that are currently pinned. This is in sorted order.
		 * @type {Array}
		 */
		resultIdsPinned: [],

		/**
		 * Toggles on and off the debugger form.
		 * @type {boolean}
		 */
		showDebugger: process.env.NODE_ENV === 'development',

		/**
		 * Total number of hidden results returned from the fetch request.
		 * @type {number}
		 */
		totalResultsHiddenCount: 0,

		/**
		 * Total number of non-hidden results returned from the fetch request.
		 * @type {number}
		 */
		totalResultsVisibleCount: 0,

		/**
		 * "Pagination" for items loaded in visible label.
		 * @type {number}
		 */
		visibleCur: 0,

		/**
		 * Determines if the form submission is save as draft or publish.
		 * @type {string}
		 */
		workflowAction: ''
	};

	constructor(props) {
		super(props);

		this._initialResultIdsHidden = [];
		this._initialResultIdsPinned = [];
	}

	componentDidMount() {
		this._handleFetchResultsDataVisible();
		this._handleFetchResultsDataHidden();
	}

	/**
	 * Gets the added changes in hidden from the initial and current states.
	 */
	_getHiddenAdded = () =>
		this.state.resultIdsHidden.filter(
			item => !this._initialResultIdsHidden.includes(item)
		);

	/**
	 * Gets the removed changes in hidden from the initial and current states.
	 */
	_getHiddenRemoved = () =>
		this._initialResultIdsHidden.filter(
			item => !this.state.resultIdsHidden.includes(item)
		);

	/**
	 * Gets the visible data results to show in the visible tab. Organizes the
	 * pinned results on to top first and then the remaining unpinned and not
	 * hidden results.
	 */
	_getResultIdsVisible = () => {
		const {resultIds, resultIdsPinned} = this.state;

		const notPinnedOrHiddenIds = resultIds.filter(id =>
			this._isNotPinnedOrHidden(id)
		);

		return [...resultIdsPinned, ...notPinnedOrHiddenIds];
	};

	/**
	 * Handles what happens when the toggle switch is clicked. Changes the
	 * state of the ranking to inactive or active (boolean value).
	 */
	_handleActive = () => {
		this.setState(state => ({
			inactive: !state.inactive
		}));
	};

	/**
	 * Handles what happens when an item is pinned or unpinned. Updates the
	 * dataMap and adds the id to the resultsDataPinned list.
	 * @param {array} ids The list of ids to pin.
	 * @param {boolean} pin The new pin value to set. Defaults to true.
	 */
	_handleClickPin = (ids, pin = true) => {
		this.setState(state => ({
			dataMap: updateDataMap(state.dataMap, ids, {
				hidden: false,
				pinned: pin
			}),
			resultIdsHidden: removeIdFromList(state.resultIdsHidden, ids),
			resultIdsPinned: pin
				? [...state.resultIdsPinned, ...ids]
				: removeIdFromList(state.resultIdsPinned, ids)
		}));
	};

	/**
	 * Handles what happens when an item is hidden or shown. Updates the item
	 * on the dataMap and moves the id between the visible and hidden lists.
	 * Also unpins the item when hiding a pinned item.
	 * @param {array} ids The list of ids to hide or show.
	 * @param {boolean} hide The new hide value to set. Defaults to true.
	 */
	_handleClickHide = (ids, hide = true) => {
		this.setState(state => ({
			dataMap: updateDataMap(state.dataMap, ids, {
				hidden: hide,
				pinned: false
			}),
			resultIdsHidden: hide
				? [...ids, ...state.resultIdsHidden]
				: removeIdFromList(state.resultIdsHidden, ids),
			resultIdsPinned: hide
				? removeIdFromList(state.resultIdsPinned, ids)
				: state.resultIdsPinned
		}));
	};

	/**
	 * Retrieves visible results data which contains pinned results. This also
	 * handles loading more data into the results list.
	 */
	_handleFetchResultsDataVisible = () => {
		this.setState({
			dataLoadingVisible: true,
			displayError: false
		});

		const {companyId, namespace} = this.context;

		return fetchDocuments(this.props.fetchDocumentsVisibleUrl, {
			[`${namespace}companyId`]: companyId,
			[`${namespace}from`]: DELTA * this.state.visibleCur,
			[`${namespace}keywords`]: this.props.searchQuery,
			[`${namespace}size`]: DELTA
		})
			.then(({items, total}) => {
				const fetchedItems = items || {};

				// Get existing results to update its addedResult property.
				const existingFetchedIds = fetchedItems
					.filter(({id}) => this.state.resultIds.includes(id))
					.map(({id}) => id);

				// Remove duplicate results from the new list of results to
				// avoid duplicate key errors.
				const newItems = fetchedItems.filter(
					({id}) => !this.state.resultIds.includes(id)
				);

				const newIds = newItems.map(({id}) => id);
				const newPinnedIds = newItems
					.filter(({pinned}) => pinned)
					.map(({id}) => id);

				// Keep history of all initially pinned results to update the
				// end index of pinned results.
				this._initialResultIdsPinned = [
					...this._initialResultIdsPinned,
					...newPinnedIds
				];

				this.setState(
					state => ({
						dataLoadingVisible: false,
						dataMap: {
							// In the case when a previously added result is
							// actually one of the results that loads in, its
							// 'addedResult' property must be set to false.
							// This prevents confusion since unpinning or
							// un-hiding added results removes it from results
							// list entirely.
							...updateDataMap(
								state.dataMap,
								existingFetchedIds,
								{
									addedResult: false
								}
							),
							...resultsDataToMap(newItems)
						},
						resultIds: [...state.resultIds, ...newIds],
						resultIdsPinned: [
							...state.resultIdsPinned,
							...newPinnedIds
						],
						totalResultsVisibleCount: total,
						visibleCur: state.visibleCur + 1
					}),
					() => {
						this._updateDataLoadIndex(
							'pinned',
							this._initialResultIdsPinned
						);
					}
				);
			})
			.catch(error => {
				// Delay showing error message so the user has confirmation
				// when attempting to reload the content after an error.

				setTimeout(() => {
					if (process.env.NODE_ENV === 'development') {
						console.error('Unable to load content.', error);
					}

					this.setState({
						dataLoadingVisible: false,
						displayError: true
					});
				}, 1000);
			});
	};

	/**
	 * Retrieves only the hidden data. This is used for showing hidden results
	 * in the hidden tab.
	 */
	_handleFetchResultsDataHidden = () => {
		this.setState({
			dataLoadingHidden: true,
			displayError: false
		});

		const {companyId, namespace} = this.context;

		return fetchDocuments(this.props.fetchDocumentsHiddenUrl, {
			[`${namespace}companyId`]: companyId,
			[`${namespace}from`]: DELTA * this.state.hiddenCur,
			[`${namespace}keywords`]: this.props.searchQuery,
			[`${namespace}size`]: DELTA
		})
			.then(({items, total}) => {
				const fetchedItems = items || {};

				// Get already existing results in order to set addedResult
				// property to false in setState.
				const existingFetchedIds = fetchedItems
					.filter(({id}) => this.state.resultIds.includes(id))
					.map(({id}) => id);

				// Remove duplicate results from the new list of results to
				// avoid duplicate key errors.
				const newItems = fetchedItems.filter(
					({id}) => !this.state.resultIds.includes(id)
				);

				const newIds = newItems.map(({id}) => id);

				// Keep history of all initial results, to get the difference
				// for addedResults and for all added/removed hidden/pinned
				this._initialResultIdsHidden = [
					...this._initialResultIdsHidden,
					...newIds
				];

				this.setState(state => ({
					dataLoadingHidden: false,
					dataMap: {
						// In the case when a previously added result is
						// actually one of the results that loads in, its
						// 'addedResult' property must be set to false.
						// This prevents confusion since unpinning or
						// un-hiding added results removes it from results
						// list entirely.
						...updateDataMap(state.dataMap, existingFetchedIds, {
							addedResult: false
						}),
						...resultsDataToMap(newItems)
					},
					hiddenCur: state.hiddenCur + 1,
					resultIds: [...state.resultIds, ...newIds],
					resultIdsHidden: [...state.resultIdsHidden, ...newIds],
					totalResultsHiddenCount: total
				}));
			})
			.catch(error => {
				// Delay showing error message so the user has confirmation
				// when attempting to reload the content after an error.

				setTimeout(() => {
					if (process.env.NODE_ENV === 'development') {
						console.error('Unable to load content.', error);
					}

					this.setState({
						dataLoadingHidden: false,
						displayError: true
					});
				}, 1000);
			});
	};

	/**
	 * Handles reordering an item in a list. This will update the results list.
	 * @param {number} fromIndex The index of the item that is being moved.
	 * @param {number} toIndex The new index that the item will be moved to.
	 */
	_handleMove = (fromIndex, toIndex) => {
		if (!isNil(fromIndex) && !isNil(toIndex) && fromIndex !== toIndex) {
			this.setState(state => ({
				resultIdsPinned: move(state.resultIdsPinned, fromIndex, toIndex)
			}));
		}
	};

	/**
	 * Handles the publishing of the form. Sets the workflow action input and
	 * submits the form.
	 */
	_handlePublish = () => {
		const {indexName, namespace} = this.context;

		fetchResponse(this.props.validateFormUrl, {
			[`${namespace}aliases`]: this.state.aliases,
			[`${namespace}inactive`]: this.state.inactive,
			[`${namespace}index`]: indexName,
			[`${namespace}keywords`]: this.props.searchQuery,
			[`${namespace}resultsRankingUid`]: this.props.resultsRankingUid
		}).then(response => {
			if (response.errors.length) {
				response.errors.forEach(message => {
					Liferay.Util.openToast({
						message,
						title: Liferay.Language.get('error'),
						type: 'danger'
					});
				});
			}
			else {
				this.setState(
					{
						workflowAction: this.context.constants
							.WORKFLOW_ACTION_PUBLISH
					},
					() => {
						submitForm(document[this.props.formName]);
					}
				);
			}
		});
	};

	/**
	 * Handles updating the alias list.
	 * @param {array} keywords The list of the new aliases (array of list-value
	 * objects).
	 */
	_handleUpdateAliases = keywords => {
		this.setState({aliases: keywords.map(({value}) => value)});
	};

	/**
	 * Handles updating the array of added result ids after 'Add a Result'
	 * submission.
	 * @param {array} addedResultsDataList The value of the added results
	 * (array of objects).
	 */
	_handleUpdateAddResultIds = addedResultsDataList => {
		const mappedData = resultsDataToMap(addedResultsDataList);

		// Make sure that all added results that are not part of the original
		// search results get marked as pinned and addedResult.

		const preMappedData = updateDataMap(
			mappedData,
			addedResultsDataList
				.filter(({id}) => !this.state.resultIds.includes(id))
				.map(({id}) => id),
			{
				addedResult: true,
				pinned: true
			}
		);

		// Make sure that all added results that are part of the original
		// search results get marked with as pinned (not addedResult).

		const newMappedData = updateDataMap(
			preMappedData,
			addedResultsDataList
				.filter(({id}) => this.state.resultIds.includes(id))
				.map(({id}) => id),
			{
				pinned: true
			}
		);

		const addedResultsIds = addedResultsDataList.map(({id}) => id);

		this.setState(state => ({
			dataMap: {
				...state.dataMap,
				...newMappedData
			},
			resultIds: [
				...state.resultIds,
				...addedResultsIds.filter(id => !state.resultIds.includes(id))
			],

			// Remove any results from hidden if they are getting pinned
			// and considered as an addedResult.

			resultIdsHidden: state.resultIdsHidden.filter(
				id => !addedResultsIds.includes(id)
			),
			resultIdsPinned: [
				// Place the addedResults at the top of the pinned list
				// while removing any that are already part of the
				// pinned list.

				...addedResultsDataList
					.filter(
						result => !state.resultIdsPinned.includes(result.id)
					)
					.map(({id}) => id),
				...state.resultIdsPinned
			]
		}));
	};

	/**
	 * Checks if there are any remaining results to be loaded by comparing the
	 * originally loaded with totalCount.
	 * @param {number} totalCount Total number of results to potentially load.
	 * @param {number} cur The 'page' at which loaded results are currently on.
	 * @param {number} delta Number of results per page.
	 */

	_hasMoreData = (totalCount, cur, delta = DELTA) => {
		return cur * delta < totalCount;
	};

	/**
	 * Checks if an item is neither pinned or hidden. Useful for displaying
	 * the remaining results in the visible tab.
	 * @param {number|string} id The id of the item to check.
	 */
	_isNotPinnedOrHidden = id => {
		const {resultIdsHidden, resultIdsPinned} = this.state;

		return !resultIdsPinned.includes(id) && !resultIdsHidden.includes(id);
	};

	/**
	 * Increments the `end` property of the dataLoadIndex state by the `DELTA`.
	 * @param {string} property The property to update.
	 * @param {Array} list The list to get the max length from.
	 * @param {number} increment The amount the new value should increase by.
	 */
	_updateDataLoadIndex = (property, list, increment = DELTA) => {
		this.setState(({dataLoadIndex}) => {
			const maxValue = list.length - 1;

			const newValue = dataLoadIndex[property].end + increment;

			return {
				dataLoadIndex: {
					...dataLoadIndex,
					[property]: {
						...dataLoadIndex[property],
						end: newValue > maxValue ? maxValue : newValue
					}
				}
			};
		});
	};

	/**
	 * Updates the active tab of the results list by changing activeTabKeyValue.
	 * @param {number} value The number of the tab.
	 */
	_setActiveTabKeyValue = value => {
		this.setState({
			activeTabKeyValue: value
		});
	};

	render() {
		const {namespace} = this.context;

		const {cancelUrl, fetchDocumentsSearchUrl, searchQuery} = this.props;

		const {
			activeTabKeyValue,
			aliases,
			dataLoadIndex,
			dataLoadingHidden,
			dataLoadingVisible,
			dataMap,
			displayError,
			displayErrorHidden,
			hiddenCur,
			inactive,
			resultIdsHidden,
			resultIdsPinned,
			showDebugger,
			totalResultsHiddenCount,
			totalResultsVisibleCount,
			visibleCur,
			workflowAction
		} = this.state;

		return (
			<div className="result-rankings-form-root">
				<HiddenInputs
					valueMap={{
						aliases,
						hiddenIdsAdded: this._getHiddenAdded(),
						hiddenIdsRemoved: this._getHiddenRemoved(),
						inactive,
						pinnedIds: resultIdsPinned,
						pinnedIdsEndIndex: dataLoadIndex.pinned.end,
						pinnedIdsStartIndex: dataLoadIndex.pinned.start,
						workflowAction
					}}
				/>

				<PageToolbar
					inactive={inactive}
					onCancel={cancelUrl}
					onChangeActive={this._handleActive}
					onPublish={this._handlePublish}
				/>

				<div className="container-fluid container-fluid-max-xl container-form-lg result-rankings-container">
					<div className="form-section-header sheet sheet-lg">
						<label>{Liferay.Language.get('query')}</label>

						<h2 className="sheet-title">{`${searchQuery}`}</h2>

						<ErrorBoundary
							component={Liferay.Language.get('aliases')}
							toast
						>
							<Alias
								keywords={aliases}
								onChange={this._handleUpdateAliases}
							/>
						</ErrorBoundary>
					</div>

					<div className="form-section-body sheet sheet-lg">
						<div className="results-title sheet-text">
							{Liferay.Language.get('results')}
						</div>

						<ErrorBoundary>
							<div className="form-section-results-list">
								<nav
									aria-label={Liferay.Language.get(
										'visible-hidden-results'
									)}
									className="navbar navbar-expand-md navbar-underline navigation-bar navigation-bar-light"
								>
									<ClayTabs className="navbar-nav">
										<ClayTabs.Item
											active={activeTabKeyValue === 0}
											aria-label={Liferay.Language.get(
												'visible-results'
											)}
											onClick={() =>
												this._setActiveTabKeyValue(0)
											}
											role="tab"
										>
											{Liferay.Language.get('visible')}
										</ClayTabs.Item>

										<ClayTabs.Item
											active={activeTabKeyValue === 1}
											aria-label={Liferay.Language.get(
												'hidden-results'
											)}
											onClick={() =>
												this._setActiveTabKeyValue(1)
											}
											role="tab"
										>
											{Liferay.Language.get('hidden')}
										</ClayTabs.Item>
									</ClayTabs>
								</nav>

								<ClayTabs.Content
									activeIndex={activeTabKeyValue}
								>
									<ClayTabs.TabPane>
										<List
											dataLoading={dataLoadingVisible}
											dataMap={dataMap}
											displayError={displayError}
											fetchDocumentsSearchUrl={
												fetchDocumentsSearchUrl
											}
											onAddResultSubmit={
												this._handleUpdateAddResultIds
											}
											onClickHide={this._handleClickHide}
											onClickPin={this._handleClickPin}
											onLoadResults={
												this
													._handleFetchResultsDataVisible
											}
											onMove={this._handleMove}
											resultIds={this._getResultIdsVisible()}
											resultIdsPinned={
												this.state.resultIdsPinned
											}
											showLoadMore={this._hasMoreData(
												totalResultsVisibleCount,
												visibleCur
											)}
										/>
									</ClayTabs.TabPane>

									<ClayTabs.TabPane>
										<List
											dataLoading={dataLoadingHidden}
											dataMap={dataMap}
											displayError={displayErrorHidden}
											onClickHide={this._handleClickHide}
											onClickPin={this._handleClickPin}
											onLoadResults={
												this
													._handleFetchResultsDataHidden
											}
											resultIds={resultIdsHidden}
											showLoadMore={this._hasMoreData(
												totalResultsHiddenCount,
												hiddenCur
											)}
										/>
									</ClayTabs.TabPane>
								</ClayTabs.Content>
							</div>
						</ErrorBoundary>
					</div>
				</div>

				{showDebugger && (
					<FormValueDebugger
						values={[
							{
								name: `${namespace}aliases`,
								value: aliases
							},
							{
								name: `${namespace}hiddenIdsAdded`,
								value: this._getHiddenAdded()
							},
							{
								name: `${namespace}hiddenIdsRemoved`,
								value: this._getHiddenRemoved()
							},
							{
								name: `${namespace}pinnedIds`,
								value: resultIdsPinned
							},
							{
								name: `${namespace}pinnedIdsEndIndex`,
								value: dataLoadIndex.pinned.end
							},
							{
								name: `${namespace}pinnedIdsStartIndex`,
								value: dataLoadIndex.pinned.start
							},
							{
								name: `${namespace}workflowAction`,
								value: workflowAction
							}
						]}
					/>
				)}
			</div>
		);
	}
}

export default ResultRankingsForm;
