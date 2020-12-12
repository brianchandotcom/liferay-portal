/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayAlert from '@clayui/alert';
import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import ClayNavigationBar from '@clayui/navigation-bar';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

const ChangeTrackingRenderView = ({
	dataURL,
	getCache,
	spritemap,
	updateCache,
}) => {
	const CHANGE_TYPE_ADDED = 'added';
	const CHANGE_TYPE_DELETED = 'deleted';
	const CHANGE_TYPE_PRODUCTION = 'production';
	const CONTENT_SELECT_LEFT = 'CONTENT_SELECT_LEFT';
	const CONTENT_SELECT_RIGHT = 'CONTENT_SELECT_RIGHT';
	const CONTENT_SELECT_UNIFIED = 'CONTENT_SELECT_UNIFIED';
	const CONTENT_TYPE_DATA = 'data';
	const CONTENT_TYPE_DISPLAY = 'display';
	const VIEW_TYPE_FULL = 'VIEW_TYPE_FULL';
	const VIEW_TYPE_SPLIT = 'VIEW_TYPE_SPLIT';

	const [loading, setLoading] = useState(false);
	const [state, setState] = useState({
		contentSelect: CONTENT_SELECT_UNIFIED,
		contentType: CONTENT_TYPE_DISPLAY,
		renderData: null,
		viewType: VIEW_TYPE_FULL,
	});

	useEffect(() => {
		let cachedData = null;

		if (getCache) {
			cachedData = getCache();
		}

		if (cachedData && cachedData.changeType) {
			if (cachedData.changeType === CHANGE_TYPE_PRODUCTION) {
				setState({
					contentSelect: CONTENT_SELECT_LEFT,
					contentType: CONTENT_TYPE_DATA,
					renderData: cachedData,
					viewType: VIEW_TYPE_FULL,
				});

				setLoading(false);

				return;
			}

			const newState = {
				contentSelect: CONTENT_SELECT_UNIFIED,
				contentType: CONTENT_TYPE_DISPLAY,
				renderData: cachedData,
				viewType: VIEW_TYPE_FULL,
			};

			if (!cachedData.content) {
				newState.contentType = CONTENT_TYPE_DATA;
				newState.viewType = VIEW_TYPE_FULL;
			}

			if (!cachedData.leftTitle) {
				newState.contentSelect = CONTENT_SELECT_RIGHT;
			}
			else if (!cachedData.rightTitle) {
				newState.contentSelect = CONTENT_SELECT_LEFT;
			}

			setState(newState);

			setLoading(false);

			return;
		}

		setLoading(true);

		fetch(dataURL)
			.then((response) => response.json())
			.then((json) => {
				if (!json.changeType) {
					setLoading(false);
					setState({
						renderData: {
							errorMessage: Liferay.Language.get(
								'an-unexpected-error-occurred'
							),
						},
					});

					return;
				}

				if (updateCache) {
					updateCache(json);
				}

				const newState = {
					contentSelect: CONTENT_SELECT_UNIFIED,
					contentType: CONTENT_TYPE_DISPLAY,
					renderData: json,
					viewType: VIEW_TYPE_FULL,
				};

				if (!json.content) {
					newState.contentType = CONTENT_TYPE_DATA;
					newState.viewType = VIEW_TYPE_FULL;
				}

				if (!json.leftTitle) {
					newState.contentSelect = CONTENT_SELECT_RIGHT;
				}
				else if (!json.rightTitle) {
					newState.contentSelect = CONTENT_SELECT_LEFT;
				}

				setState(newState);

				setLoading(false);
			})
			.catch(() => {
				setLoading(false);
				setState({
					renderData: {
						errorMessage: Liferay.Language.get(
							'an-unexpected-error-occurred'
						),
					},
				});
			});
	}, [getCache, dataURL, updateCache]);

	const setContentSelect = (contentSelect) => {
		setState({
			contentSelect,
			contentType: state.contentType,
			renderData: state.renderData,
			viewType: state.viewType,
		});
	};

	const setContentType = (contentType) => {
		setState({
			contentSelect: state.contentSelect,
			contentType,
			renderData: state.renderData,
			viewType: state.viewType,
		});
	};

	const setViewType = (viewType) => {
		setState({
			contentSelect: state.contentSelect,
			contentType: state.contentType,
			renderData: state.renderData,
			viewType,
		});
	};

	const getContentSelectTitle = (value) => {
		if (value === CONTENT_SELECT_LEFT) {
			if (
				state.renderData.changeType === CHANGE_TYPE_ADDED &&
				state.renderData.versioned
			) {
				return (
					state.renderData.leftTitle +
					' (' +
					Liferay.Language.get('previous') +
					')'
				);
			}

			return state.renderData.leftTitle;
		}
		else if (value === CONTENT_SELECT_RIGHT) {
			if (
				state.renderData.changeType === CHANGE_TYPE_ADDED &&
				state.renderData.versioned &&
				state.renderData.leftTitle
			) {
				return (
					state.renderData.rightTitle +
					' (' +
					Liferay.Language.get('current') +
					')'
				);
			}

			return state.renderData.rightTitle;
		}

		return Liferay.Language.get('unified');
	};

	const getContentSelectTooltip = () => {
		if (state.renderData.changeType === CHANGE_TYPE_DELETED) {
			return Liferay.Language.get(
				'item-does-not-have-another-version-to-compare-against'
			);
		}

		return Liferay.Language.get(
			'item-does-not-have-a-previous-version-to-compare-against'
		);
	};

	const getSplitViewTooltip = () => {
		if (!state.renderData.leftTitle || !state.renderData.rightTitle) {
			if (state.renderData.changeType === CHANGE_TYPE_DELETED) {
				return Liferay.Language.get(
					'item-does-not-have-another-version-to-compare-against'
				);
			}

			return Liferay.Language.get(
				'item-does-not-have-a-previous-version-to-compare-against'
			);
		}

		return Liferay.Language.get('split-view');
	};

	const renderContentLeft = () => {
		if (
			state.contentType === CONTENT_TYPE_DATA &&
			Object.prototype.hasOwnProperty.call(state.renderData, 'leftRender')
		) {
			return (
				<div
					dangerouslySetInnerHTML={{
						__html: state.renderData.leftRender,
					}}
				/>
			);
		}
		else if (
			state.contentType === CONTENT_TYPE_DISPLAY &&
			Object.prototype.hasOwnProperty.call(
				state.renderData,
				'leftContent'
			)
		) {
			if (state.renderData.leftContent) {
				return (
					<div
						dangerouslySetInnerHTML={{
							__html: state.renderData.leftContent,
						}}
					/>
				);
			}

			return (
				<ClayAlert displayType="info" spritemap={spritemap}>
					{Liferay.Language.get('content-is-empty')}
				</ClayAlert>
			);
		}
		else if (loading) {
			return '';
		}

		return (
			<ClayAlert displayType="danger" spritemap={spritemap}>
				{Liferay.Language.get(
					'unable-to-display-content-due-to-an-unexpected-error'
				)}
			</ClayAlert>
		);
	};

	const renderContentRight = () => {
		if (
			state.contentType === CONTENT_TYPE_DATA &&
			Object.prototype.hasOwnProperty.call(
				state.renderData,
				'rightRender'
			)
		) {
			return (
				<div
					dangerouslySetInnerHTML={{
						__html: state.renderData.rightRender,
					}}
				/>
			);
		}
		else if (
			state.contentType === CONTENT_TYPE_DISPLAY &&
			Object.prototype.hasOwnProperty.call(
				state.renderData,
				'rightContent'
			)
		) {
			if (state.renderData.rightContent) {
				return (
					<div
						dangerouslySetInnerHTML={{
							__html: state.renderData.rightContent,
						}}
					/>
				);
			}

			return (
				<ClayAlert displayType="info" spritemap={spritemap}>
					{Liferay.Language.get('content-is-empty')}
				</ClayAlert>
			);
		}
		else if (loading) {
			return '';
		}

		return (
			<ClayAlert displayType="danger" spritemap={spritemap}>
				{Liferay.Language.get(
					'unable-to-display-content-due-to-an-unexpected-error'
				)}
			</ClayAlert>
		);
	};

	const renderContentSelect = () => {
		if (state.viewType !== VIEW_TYPE_FULL) {
			return '';
		}

		const pushItem = (items, key) => {
			items.push({
				active: state.contentSelect === key,
				label: getContentSelectTitle(key),
				onClick: () => {
					setContentSelect(key);
				},
			});
		};

		const elements = [];

		elements.push(
			<div className="autofit-col row-divider">
				<div />
			</div>
		);

		if (!state.renderData.leftTitle || !state.renderData.rightTitle) {
			elements.push(
				<div className="autofit-col">
					<div className="dropdown">
						<ClayTooltipProvider>
							<ClayButton
								borderless
								className="disabled"
								data-tooltip-align="top"
								displayType="secondary"
								title={getContentSelectTooltip()}
							>
								{getContentSelectTitle(state.contentSelect)}

								<span className="inline-item inline-item-after">
									<ClayIcon
										spritemap={spritemap}
										symbol="caret-bottom"
									/>
								</span>
							</ClayButton>
						</ClayTooltipProvider>
					</div>
				</div>
			);

			return elements;
		}

		const items = [];

		if (state.renderData.leftTitle && state.renderData.rightTitle) {
			pushItem(items, CONTENT_SELECT_UNIFIED);
		}

		if (state.renderData.leftTitle) {
			pushItem(items, CONTENT_SELECT_LEFT);
		}

		if (state.renderData.rightTitle) {
			pushItem(items, CONTENT_SELECT_RIGHT);
		}

		elements.push(
			<div className="autofit-col">
				<ClayDropDownWithItems
					items={items}
					spritemap={spritemap}
					trigger={
						<ClayButton borderless displayType="secondary">
							{getContentSelectTitle(state.contentSelect)}

							<span className="inline-item inline-item-after">
								<ClayIcon
									spritemap={spritemap}
									symbol="caret-bottom"
								/>
							</span>
						</ClayButton>
					}
				/>
			</div>
		);

		return elements;
	};

	const renderContentUnified = () => {
		if (
			state.contentType === CONTENT_TYPE_DATA &&
			Object.prototype.hasOwnProperty.call(
				state.renderData,
				'unifiedRender'
			)
		) {
			return (
				<div className="taglib-diff-html">
					<div
						dangerouslySetInnerHTML={{
							__html: state.renderData.unifiedRender,
						}}
					/>
				</div>
			);
		}
		else if (
			state.contentType === CONTENT_TYPE_DISPLAY &&
			Object.prototype.hasOwnProperty.call(
				state.renderData,
				'unifiedContent'
			)
		) {
			if (state.renderData.unifiedContent) {
				return (
					<div className="taglib-diff-html">
						<div
							dangerouslySetInnerHTML={{
								__html: state.renderData.unifiedContent,
							}}
						/>
					</div>
				);
			}

			return (
				<ClayAlert displayType="info" spritemap={spritemap}>
					{Liferay.Language.get('content-is-empty')}
				</ClayAlert>
			);
		}
		else if (loading) {
			return '';
		}

		return (
			<ClayAlert displayType="danger" spritemap={spritemap}>
				{Liferay.Language.get(
					'unable-to-display-content-due-to-an-unexpected-error'
				)}
			</ClayAlert>
		);
	};

	const renderDiffLegend = () => {
		if (
			state.contentSelect !== CONTENT_SELECT_UNIFIED ||
			state.viewType !== VIEW_TYPE_FULL
		) {
			return '';
		}

		const elements = [];

		elements.push(
			<div className="autofit-col row-divider">
				<div />
			</div>
		);

		elements.push(
			<div className="autofit-col">
				<div className="taglib-diff-html">
					<span className="diff-html-added legend-item">
						{Liferay.Language.get('added')}
					</span>
					<span className="diff-html-removed legend-item">
						{Liferay.Language.get('deleted')}
					</span>
					<span className="diff-html-changed">
						{Liferay.Language.get('format-changes')}
					</span>
				</div>
			</div>
		);

		return elements;
	};

	const renderDividers = () => {
		if (state.viewType === VIEW_TYPE_SPLIT) {
			return (
				<tr className="publications-render-view-divider table-divider">
					<td className="publications-render-view-divider">
						{state.renderData.leftTitle}
					</td>
					<td className="publications-render-view-divider">
						{state.renderData.rightTitle}
					</td>
				</tr>
			);
		}

		let title = null;

		if (state.contentSelect === CONTENT_SELECT_LEFT) {
			title = state.renderData.leftTitle;

			if (state.renderData.changeType === CHANGE_TYPE_DELETED) {
				title += ' (' + Liferay.Language.get('deleted') + ')';
			}
		}
		else if (state.contentSelect === CONTENT_SELECT_RIGHT) {
			title = state.renderData.rightTitle;

			if (state.renderData.changeType === CHANGE_TYPE_ADDED) {
				title += ' (' + Liferay.Language.get('new') + ')';
			}
		}
		else {
			title =
				state.renderData.leftTitle +
				' | ' +
				state.renderData.rightTitle;
		}

		const className = 'publications-render-view-divider table-divider';

		return (
			<tr
				className={
					loading
						? className + ' publications-render-view-loading'
						: className
				}
			>
				<td>{title}</td>
			</tr>
		);
	};

	const renderToolbar = () => {
		if (state.renderData.changeType === CHANGE_TYPE_PRODUCTION) {
			return '';
		}

		let columns = 1;

		if (state.viewType === VIEW_TYPE_SPLIT) {
			columns = 2;
		}

		let splitViewClassName = '';

		if (state.viewType === VIEW_TYPE_SPLIT) {
			splitViewClassName = 'active';
		}
		else if (
			!state.renderData.leftTitle ||
			!state.renderData.rightTitle
		) {
			splitViewClassName = 'disabled';
		}

		return (
			<tr className={loading ? 'publications-render-view-loading' : ''}>
				<td
					className="publications-render-view-toolbar"
					colSpan={columns}
				>
					<div className="autofit-row">
						<div className="autofit-col">
							<ClayTooltipProvider>
								<ClayNavigationBar
									spritemap={spritemap}
									triggerLabel={Liferay.Language.get(
										'display'
									)}
								>
									<ClayNavigationBar.Item
										active={
											state.contentType ===
											CONTENT_TYPE_DISPLAY
										}
									>
										<ClayLink
											className={
												!state.renderData.content
													? 'nav-link btn-link disabled'
													: 'nav-link'
											}
											displayType="unstyled"
											onClick={() =>
												setContentType(
													CONTENT_TYPE_DISPLAY
												)
											}
											title={
												!state.renderData.content
													? Liferay.Language.get(
															'item-does-not-have-a-content-display'
													  )
													: ''
											}
										>
											{Liferay.Language.get('display')}
										</ClayLink>
									</ClayNavigationBar.Item>
									<ClayNavigationBar.Item
										active={
											state.contentType ===
											CONTENT_TYPE_DATA
										}
									>
										<ClayLink
											className="nav-link"
											displayType="unstyled"
											onClick={() =>
												setContentType(
													CONTENT_TYPE_DATA
												)
											}
										>
											{Liferay.Language.get('data')}
										</ClayLink>
									</ClayNavigationBar.Item>
								</ClayNavigationBar>
							</ClayTooltipProvider>
						</div>
						<div className="autofit-col row-divider">
							<div />
						</div>
						<div className="autofit-col">
							<ClayTooltipProvider>
								<ClayButton.Group>
									<ClayButtonWithIcon
										borderless
										className={
											state.viewType === VIEW_TYPE_FULL
												? 'active'
												: ''
										}
										data-tooltip-align="top"
										displayType="secondary"
										onClick={() =>
											setViewType(VIEW_TYPE_FULL)
										}
										spritemap={spritemap}
										symbol="web-content"
										title={Liferay.Language.get(
											'full-view'
										)}
									/>
									<ClayButtonWithIcon
										borderless
										className={splitViewClassName}
										data-tooltip-align="top"
										displayType="secondary"
										onClick={() =>
											setViewType(VIEW_TYPE_SPLIT)
										}
										spritemap={spritemap}
										symbol="product-menu-open"
										title={getSplitViewTooltip()}
									/>
								</ClayButton.Group>
							</ClayTooltipProvider>
						</div>

						{renderContentSelect()}
						{renderDiffLegend()}
					</div>
				</td>
			</tr>
		);
	};

	if (!state.renderData) {
		if (loading) {
			return (
				<div>
					<span aria-hidden="true" className="loading-animation" />
				</div>
			);
		}

		return '';
	}
	else if (!state.renderData.changeType || state.renderData.errorMessage) {
		return (
			<ClayAlert
				displayType="danger"
				spritemap={spritemap}
				title={Liferay.Language.get('error')}
			>
				{state.renderData.errorMessage
					? state.renderData.errorMessage
					: Liferay.Language.get('an-unexpected-error-occurred')}
			</ClayAlert>
		);
	}

	return (
		<table className="publications-render-view table">
			{renderToolbar()}

			{renderDividers()}

			<tr className={loading ? 'publications-render-view-loading' : ''}>
				{(state.contentSelect === CONTENT_SELECT_LEFT ||
					state.viewType === VIEW_TYPE_SPLIT) && (
					<td className="publications-render-view-content">
						{renderContentLeft()}
					</td>
				)}

				{(state.contentSelect === CONTENT_SELECT_RIGHT ||
					state.viewType === VIEW_TYPE_SPLIT) && (
					<td className="publications-render-view-content">
						{renderContentRight()}
					</td>
				)}

				{state.contentSelect === CONTENT_SELECT_UNIFIED &&
					state.viewType === VIEW_TYPE_FULL && (
						<td className="publications-render-view-content">
							{renderContentUnified()}
						</td>
					)}
			</tr>
		</table>
	);
};

export default ChangeTrackingRenderView;
export {ChangeTrackingRenderView};
