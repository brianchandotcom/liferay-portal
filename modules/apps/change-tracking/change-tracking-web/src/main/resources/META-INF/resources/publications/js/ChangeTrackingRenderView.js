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

const ChangeTrackingRenderView = ({dataURL, spritemap}) => {
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
	const [data, setData] = useState(null);
	const [loading, setLoading] = useState(false);
	const [viewType, setViewType] = useState(VIEW_TYPE_FULL);

	useEffect(() => {
		setLoading(true);

		fetch(dataURL)
			.then((response) => response.json())
			.then((json) => {
				let contentSelect = CONTENT_SELECT_UNIFIED;
				let contentType = CONTENT_TYPE_DISPLAY;
				let viewType = VIEW_TYPE_FULL;

				if (!json.content) {
					contentType = CONTENT_TYPE_DATA;
					viewType = VIEW_TYPE_FULL;
				}

				if (!json.leftTitle) {
					contentSelect = CONTENT_SELECT_RIGHT;
				}
				else if (!json.rightTitle) {
					contentSelect = CONTENT_SELECT_LEFT;
				}

				setContentSelect(contentSelect);
				setContentType(contentType);
				setData(json);
				setViewType(viewType);

				setLoading(false);
			})
			.catch(() => {
				setData({
					errorMessage: Liferay.Language.get(
						'an-unexpected-error-occurred'
					),
				});
			});
	}, [dataURL]);

	const validData = () => {
		if (data && data.changeType && !data.errorMessage) {
			return true;
		}

		return false;
	};

	const getContentSelectTitle = (contentSelect) => {
		if (contentSelect === CONTENT_SELECT_LEFT) {
			if (data.changeType === CHANGE_TYPE_ADDED && data.versioned) {
				return (
					data.leftTitle +
					' (' +
					Liferay.Language.get('previous') +
					')'
				);
			}

			return data.leftTitle;
		}
		else if (contentSelect === CONTENT_SELECT_RIGHT) {
			if (
				data.changeType === CHANGE_TYPE_ADDED &&
				data.versioned &&
				data.leftTitle
			) {
				return (
					data.rightTitle +
					' (' +
					Liferay.Language.get('current') +
					')'
				);
			}

			return data.rightTitle;
		}

		return Liferay.Language.get('unified');
	};

	const getContentSelectTooltip = () => {
		if (data.changeType === CHANGE_TYPE_DELETED) {
			return Liferay.Language.get(
				'item-does-not-have-another-version-to-compare-against'
			);
		}

		return Liferay.Language.get(
			'item-does-not-have-a-previous-version-to-compare-against'
		);
	};

	const getSplitViewTooltip = () => {
		if (validData() && (!data.leftTitle || !data.rightTitle)) {
			if (data.changeType === CHANGE_TYPE_DELETED) {
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

		if (!data.leftTitle || !data.rightTitle) {
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

		if (data.leftTitle && data.rightTitle) {
			pushItem(items, CONTENT_SELECT_UNIFIED);
		}

		if (data.leftTitle) {
			pushItem(items, CONTENT_SELECT_LEFT);
		}

		if (data.rightTitle) {
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
			Object.prototype.hasOwnProperty.call(data, 'leftRender')
		) {
			return <div dangerouslySetInnerHTML={{__html: data.leftRender}} />;
		}
		else if (
			contentType === CONTENT_TYPE_DISPLAY &&
			Object.prototype.hasOwnProperty.call(data, 'leftContent')
		) {
			if (data.leftContent) {
				return (
					<div dangerouslySetInnerHTML={{__html: data.leftContent}} />
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
			Object.prototype.hasOwnProperty.call(data, 'rightRender')
		) {
			return <div dangerouslySetInnerHTML={{__html: data.rightRender}} />;
		}
		else if (
			contentType === CONTENT_TYPE_DISPLAY &&
			Object.prototype.hasOwnProperty.call(data, 'rightContent')
		) {
			if (data.rightContent) {
				return (
					<div
						dangerouslySetInnerHTML={{__html: data.rightContent}}
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
			Object.prototype.hasOwnProperty.call(data, 'unifiedRender')
		) {
			return (
				<div className="taglib-diff-html">
					<div
						dangerouslySetInnerHTML={{__html: data.unifiedRender}}
					/>
				</div>
			);
		}
		else if (
			contentType === CONTENT_TYPE_DISPLAY &&
			Object.prototype.hasOwnProperty.call(data, 'unifiedContent')
		) {
			if (data.unifiedContent) {
				return (
					<div className="taglib-diff-html">
						<div
							dangerouslySetInnerHTML={{
								__html: data.unifiedContent,
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
		if (!data) {
			return (
				<tr>
					<td>
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
							{data.errorMessage}
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
						{data.leftTitle}
					</td>
					<td className="publications-render-view-divider">
						{data.rightTitle}
					</td>
				</tr>
			);
		}

		let title = null;

		if (contentSelect === CONTENT_SELECT_LEFT) {
			title = data.leftTitle;

			if (data.changeType === CHANGE_TYPE_DELETED) {
				title += ' (' + Liferay.Language.get('deleted') + ')';
			}
		}
		else if (contentSelect === CONTENT_SELECT_RIGHT) {
			title = data.rightTitle;

			if (data.changeType === CHANGE_TYPE_ADDED) {
				title += ' (' + Liferay.Language.get('new') + ')';
			}
		}
		else {
			title = data.leftTitle + ' | ' + data.rightTitle;
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
		else if (validData() && (!data.leftTitle || !data.rightTitle)) {
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
											validData() && !data.content
												? 'nav-link btn-link disabled'
												: 'nav-link'
										}
										displayType="unstyled"
										onClick={() =>
											setContentType(CONTENT_TYPE_DISPLAY)
										}
										title={
											validData() && !data.content
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
