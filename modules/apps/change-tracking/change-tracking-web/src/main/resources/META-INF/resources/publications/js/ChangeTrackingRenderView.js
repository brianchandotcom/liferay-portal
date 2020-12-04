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
				if (!json.content) {
					setContentType(CONTENT_TYPE_DATA);
					setViewType(VIEW_TYPE_FULL);
				}

				if (!json.leftTitle) {
					setContentSelect(CONTENT_SELECT_RIGHT);
				}
				else if (!json.rightTitle) {
					setContentSelect(CONTENT_SELECT_LEFT);
				}

				setData(json);
				setLoading(false);
			});
	}, [dataURL]);

	const getColumns = () => {
		if (data && viewType === VIEW_TYPE_SPLIT) {
			return 2;
		}

		return 1;
	};

	const getContentSelectTitle = (contentSelect) => {
		if (contentSelect === CONTENT_SELECT_LEFT) {
			if (data && data.leftTitle) {
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

			return Liferay.Language.get('production');
		}
		else if (contentSelect === CONTENT_SELECT_RIGHT) {
			if (data && data.rightTitle) {
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

			return Liferay.Language.get('publication');
		}

		return Liferay.Language.get('unified');
	};

	const renderContentSelect = () => {
		if (!data || viewType !== VIEW_TYPE_FULL) {
			return '';
		}

		const items = [];

		if (data.leftTitle && data.rightTitle) {
			items.push({
				active: contentSelect === CONTENT_SELECT_UNIFIED,
				label: getContentSelectTitle(CONTENT_SELECT_UNIFIED),
				onClick: () => {
					setContentSelect(CONTENT_SELECT_UNIFIED);
				},
			});
		}

		if (data.leftTitle) {
			items.push({
				active: contentSelect === CONTENT_SELECT_LEFT,
				label: getContentSelectTitle(CONTENT_SELECT_LEFT),
				onClick: () => {
					setContentSelect(CONTENT_SELECT_LEFT);
				},
			});
		}

		if (data.rightTitle) {
			items.push({
				active: contentSelect === CONTENT_SELECT_RIGHT,
				label: getContentSelectTitle(CONTENT_SELECT_RIGHT),
				onClick: () => {
					setContentSelect(CONTENT_SELECT_RIGHT);
				},
			});
		}

		const elements = [];

		elements.push(
			<div className="autofit-col row-divider">
				<div />
			</div>
		);

		elements.push(
			<div className="autofit-col">
				<ClayDropDownWithItems
					items={items}
					spritemap={spritemap}
					trigger={
						<ClayButton
							borderless
							disabled={!data.leftTitle || !data.rightTitle}
							displayType="secondary"
						>
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
			return <div dangerouslySetInnerHTML={{__html: data.leftContent}} />;
		}

		return '';
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
			return (
				<div dangerouslySetInnerHTML={{__html: data.rightContent}} />
			);
		}

		return '';
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
			return (
				<div className="taglib-diff-html">
					<div
						dangerouslySetInnerHTML={{__html: data.unifiedContent}}
					/>
				</div>
			);
		}

		return '';
	};

	const renderContent = () => {
		if (!data) {
			return '';
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
			!data ||
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
		if (!data) {
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
						? className + 'publications-render-view-loading'
						: className
				}
			>
				<td>{title}</td>
			</tr>
		);
	};

	const renderToolbar = () => {
		return (
			<td
				className="publications-render-view-toolbar"
				colSpan={getColumns()}
			>
				{data && loading && (
					<div className="publications-render-view-loading-wrapper">
						<span
							aria-hidden="true"
							className="loading-animation"
						/>
					</div>
				)}

				<div className="autofit-row">
					<div className="autofit-col">
						<ClayNavigationBar
							spritemap={spritemap}
							triggerLabel={Liferay.Language.get('display')}
						>
							<ClayNavigationBar.Item
								active={contentType === CONTENT_TYPE_DISPLAY}
							>
								<ClayLink
									className={
										data && !data.content
											? 'nav-link btn-link disabled'
											: 'nav-link'
									}
									displayType="unstyled"
									onClick={() =>
										setContentType(CONTENT_TYPE_DISPLAY)
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
									className={
										viewType === VIEW_TYPE_SPLIT
											? 'active'
											: ''
									}
									data-tooltip-align="top"
									disabled={
										data &&
										(!data.leftTitle || !data.rightTitle)
									}
									displayType="secondary"
									onClick={() => setViewType(VIEW_TYPE_SPLIT)}
									spritemap={spritemap}
									symbol="product-menu-open"
									title={Liferay.Language.get('split-view')}
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

			{!data && (
				<tr>
					<td>
						<span
							aria-hidden="true"
							className="loading-animation"
						/>
					</td>
				</tr>
			)}

			{renderDividers()}

			{renderContent()}
		</table>
	);
};

export default ChangeTrackingRenderView;
export {ChangeTrackingRenderView};
