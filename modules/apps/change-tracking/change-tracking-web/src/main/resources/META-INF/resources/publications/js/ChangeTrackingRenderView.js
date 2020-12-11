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
	const CONTENT_SELECT_LEFT = 'CONTENT_SELECT_LEFT';
	const CONTENT_SELECT_RIGHT = 'CONTENT_SELECT_RIGHT';
	const CONTENT_SELECT_UNIFIED = 'CONTENT_SELECT_UNIFIED';
	const CONTENT_TYPE_DATA = 'data';
	const CONTENT_TYPE_DISPLAY = 'display';
	const VIEW_TYPE_FULL = 'VIEW_TYPE_FULL';
	const VIEW_TYPE_SPLIT = 'VIEW_TYPE_SPLIT';

	const [contentSelect, setContentSelect] = useState(CONTENT_SELECT_UNIFIED);
	const [contentType, setContentType] = useState(CONTENT_TYPE_DISPLAY);
	const [loading, setLoading] = useState(false);
	const [renderData, setRenderData] = useState(null);
	const [viewType, setViewType] = useState(VIEW_TYPE_FULL);

	useEffect(() => {
		const cache = getCache();

		if (cache && cache.changeType) {
			let newContentSelect = CONTENT_SELECT_UNIFIED;
			let newContentType = CONTENT_TYPE_DISPLAY;
			let newViewType = VIEW_TYPE_FULL;

			if (!cache.content) {
				newContentType = CONTENT_TYPE_DATA;
				newViewType = VIEW_TYPE_FULL;
			}

			if (!cache.leftTitle) {
				newContentSelect = CONTENT_SELECT_RIGHT;
			}
			else if (!cache.rightTitle) {
				newContentSelect = CONTENT_SELECT_LEFT;
			}

			setContentSelect(newContentSelect);
			setContentType(newContentType);
			setRenderData(cache);
			setViewType(newViewType);

			setLoading(false);
		}
		else {
			setLoading(true);

			const currentDataURL = dataURL;

			fetch(dataURL)
				.then((response) => response.json())
				.then((json) => {
					if (dataURL === currentDataURL) {
						if (!json.changeType) {
							setLoading(false);
							setRenderData({
								errorMessage: Liferay.Language.get(
									'an-unexpected-error-occurred'
								),
							});

							return;
						}

						let newContentSelect = CONTENT_SELECT_UNIFIED;
						let newContentType = CONTENT_TYPE_DISPLAY;
						let newViewType = VIEW_TYPE_FULL;

						if (!json.content) {
							newContentType = CONTENT_TYPE_DATA;
							newViewType = VIEW_TYPE_FULL;
						}

						if (!json.leftTitle) {
							newContentSelect = CONTENT_SELECT_RIGHT;
						}
						else if (!json.rightTitle) {
							newContentSelect = CONTENT_SELECT_LEFT;
						}

						setContentSelect(newContentSelect);
						setContentType(newContentType);
						setRenderData(json);
						setViewType(newViewType);

						setLoading(false);

						updateCache(json);
					}
				})
				.catch(() => {
					setLoading(false);
					setRenderData({
						errorMessage: Liferay.Language.get(
							'an-unexpected-error-occurred'
						),
					});
				});
		}
	}, [getCache, dataURL, updateCache]);

	const validData = () => {
		if (renderData && renderData.changeType && !renderData.errorMessage) {
			return true;
		}

		return false;
	};

	const getContentSelectTitle = (value) => {
		if (value === CONTENT_SELECT_LEFT) {
			if (
				renderData.changeType === CHANGE_TYPE_ADDED &&
				renderData.versioned
			) {
				return (
					renderData.leftTitle +
					' (' +
					Liferay.Language.get('previous') +
					')'
				);
			}

			return renderData.leftTitle;
		}
		else if (value === CONTENT_SELECT_RIGHT) {
			if (
				renderData.changeType === CHANGE_TYPE_ADDED &&
				renderData.versioned &&
				renderData.leftTitle
			) {
				return (
					renderData.rightTitle +
					' (' +
					Liferay.Language.get('current') +
					')'
				);
			}

			return renderData.rightTitle;
		}

		return Liferay.Language.get('unified');
	};

	const getContentSelectTooltip = () => {
		if (renderData.changeType === CHANGE_TYPE_DELETED) {
			return Liferay.Language.get(
				'item-does-not-have-another-version-to-compare-against'
			);
		}

		return Liferay.Language.get(
			'item-does-not-have-a-previous-version-to-compare-against'
		);
	};

	const getSplitViewTooltip = () => {
		if (validData() && (!renderData.leftTitle || !renderData.rightTitle)) {
			if (renderData.changeType === CHANGE_TYPE_DELETED) {
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

	const renderContentSelect = () => {
		if (!validData() || viewType !== VIEW_TYPE_FULL) {
			return '';
		}

		const pushItem = (items, key) => {
			items.push({
				active: contentSelect === key,
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

		if (!renderData.leftTitle || !renderData.rightTitle) {
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
								{getContentSelectTitle(contentSelect)}

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

		if (renderData.leftTitle && renderData.rightTitle) {
			pushItem(items, CONTENT_SELECT_UNIFIED);
		}

		if (renderData.leftTitle) {
			pushItem(items, CONTENT_SELECT_LEFT);
		}

		if (renderData.rightTitle) {
			pushItem(items, CONTENT_SELECT_RIGHT);
		}

		elements.push(
			<div className="autofit-col">
				<ClayDropDownWithItems
					items={items}
					spritemap={spritemap}
					trigger={
						<ClayButton borderless displayType="secondary">
							{getContentSelectTitle(contentSelect)}

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

	const renderContentLeft = () => {
		if (
			contentType === CONTENT_TYPE_DATA &&
			Object.prototype.hasOwnProperty.call(renderData, 'leftRender')
		) {
			return (
				<div
					dangerouslySetInnerHTML={{__html: renderData.leftRender}}
				/>
			);
		}
		else if (
			contentType === CONTENT_TYPE_DISPLAY &&
			Object.prototype.hasOwnProperty.call(renderData, 'leftContent')
		) {
			if (renderData.leftContent) {
				return (
					<div
						dangerouslySetInnerHTML={{
							__html: renderData.leftContent,
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
			contentType === CONTENT_TYPE_DATA &&
			Object.prototype.hasOwnProperty.call(renderData, 'rightRender')
		) {
			return (
				<div
					dangerouslySetInnerHTML={{__html: renderData.rightRender}}
				/>
			);
		}
		else if (
			contentType === CONTENT_TYPE_DISPLAY &&
			Object.prototype.hasOwnProperty.call(renderData, 'rightContent')
		) {
			if (renderData.rightContent) {
				return (
					<div
						dangerouslySetInnerHTML={{
							__html: renderData.rightContent,
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

	const renderContentUnified = () => {
		if (
			contentType === CONTENT_TYPE_DATA &&
			Object.prototype.hasOwnProperty.call(renderData, 'unifiedRender')
		) {
			return (
				<div className="taglib-diff-html">
					<div
						dangerouslySetInnerHTML={{
							__html: renderData.unifiedRender,
						}}
					/>
				</div>
			);
		}
		else if (
			contentType === CONTENT_TYPE_DISPLAY &&
			Object.prototype.hasOwnProperty.call(renderData, 'unifiedContent')
		) {
			if (renderData.unifiedContent) {
				return (
					<div className="taglib-diff-html">
						<div
							dangerouslySetInnerHTML={{
								__html: renderData.unifiedContent,
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

	const renderContent = () => {
		if (!renderData) {
			return (
				<tr>
					<td className="publications-render-view-content">
						<span
							aria-hidden="true"
							className="loading-animation"
						/>
					</td>
				</tr>
			);
		}
		else if (!validData()) {
			return (
				<tr>
					<td>
						<ClayAlert
							displayType="danger"
							spritemap={spritemap}
							title={Liferay.Language.get('error')}
						>
							{renderData.errorMessage}
						</ClayAlert>
					</td>
				</tr>
			);
		}

		return (
			<tr className={loading ? 'publications-render-view-loading' : ''}>
				{(contentSelect === CONTENT_SELECT_LEFT ||
					viewType === VIEW_TYPE_SPLIT) && (
					<td className="publications-render-view-content">
						{renderContentLeft()}
					</td>
				)}

				{(contentSelect === CONTENT_SELECT_RIGHT ||
					viewType === VIEW_TYPE_SPLIT) && (
					<td className="publications-render-view-content">
						{renderContentRight()}
					</td>
				)}

				{contentSelect === CONTENT_SELECT_UNIFIED &&
					viewType === VIEW_TYPE_FULL && (
						<td className="publications-render-view-content">
							{renderContentUnified()}
						</td>
					)}
			</tr>
		);
	};

	const renderDiffLegend = () => {
		if (
			!validData() ||
			contentSelect !== CONTENT_SELECT_UNIFIED ||
			viewType !== VIEW_TYPE_FULL
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
		if (!validData()) {
			return '';
		}
		else if (viewType === VIEW_TYPE_SPLIT) {
			return (
				<tr className="publications-render-view-divider table-divider">
					<td className="publications-render-view-divider">
						{renderData.leftTitle}
					</td>
					<td className="publications-render-view-divider">
						{renderData.rightTitle}
					</td>
				</tr>
			);
		}

		let title = null;

		if (contentSelect === CONTENT_SELECT_LEFT) {
			title = renderData.leftTitle;

			if (renderData.changeType === CHANGE_TYPE_DELETED) {
				title += ' (' + Liferay.Language.get('deleted') + ')';
			}
		}
		else if (contentSelect === CONTENT_SELECT_RIGHT) {
			title = renderData.rightTitle;

			if (renderData.changeType === CHANGE_TYPE_ADDED) {
				title += ' (' + Liferay.Language.get('new') + ')';
			}
		}
		else {
			title = renderData.leftTitle + ' | ' + renderData.rightTitle;
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
		let className = '';

		if (viewType === VIEW_TYPE_SPLIT) {
			className = 'active';
		}
		else if (
			validData() &&
			(!renderData.leftTitle || !renderData.rightTitle)
		) {
			className = 'disabled';
		}

		let columns = 1;

		if (validData() && viewType === VIEW_TYPE_SPLIT) {
			columns = 2;
		}

		return (
			<td className="publications-render-view-toolbar" colSpan={columns}>
				{validData() && loading && (
					<div className="publications-render-view-loading-wrapper">
						<span
							aria-hidden="true"
							className="loading-animation"
						/>
					</div>
				)}

				<div className="autofit-row">
					<div className="autofit-col">
						<ClayTooltipProvider>
							<ClayNavigationBar
								spritemap={spritemap}
								triggerLabel={Liferay.Language.get('display')}
							>
								<ClayNavigationBar.Item
									active={
										contentType === CONTENT_TYPE_DISPLAY
									}
								>
									<ClayLink
										className={
											validData() && !renderData.content
												? 'nav-link btn-link disabled'
												: 'nav-link'
										}
										displayType="unstyled"
										onClick={() =>
											setContentType(CONTENT_TYPE_DISPLAY)
										}
										title={
											validData() && !renderData.content
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
									active={contentType === CONTENT_TYPE_DATA}
								>
									<ClayLink
										className="nav-link"
										displayType="unstyled"
										onClick={() =>
											setContentType(CONTENT_TYPE_DATA)
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
										viewType === VIEW_TYPE_FULL
											? 'active'
											: ''
									}
									data-tooltip-align="top"
									displayType="secondary"
									onClick={() => setViewType(VIEW_TYPE_FULL)}
									spritemap={spritemap}
									symbol="web-content"
									title={Liferay.Language.get('full-view')}
								/>
								<ClayButtonWithIcon
									borderless
									className={className}
									data-tooltip-align="top"
									displayType="secondary"
									onClick={() => setViewType(VIEW_TYPE_SPLIT)}
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
		);
	};

	return (
		<table className="publications-render-view table">
			<tr>{renderToolbar()}</tr>
			{renderDividers()}
			{renderContent()}
		</table>
	);
};

export default ChangeTrackingRenderView;
export {ChangeTrackingRenderView};
