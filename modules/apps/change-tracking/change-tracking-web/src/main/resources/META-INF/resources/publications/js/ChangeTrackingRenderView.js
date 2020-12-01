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
import React, {useState} from 'react';

const ChangeTrackingRenderView = ({spritemap}) => {
	const CONTENT_SELECT_LEFT = 'CONTENT_SELECT_LEFT';
	const CONTENT_SELECT_RIGHT = 'CONTENT_SELECT_RIGHT';
	const CONTENT_SELECT_UNIFIED = 'CONTENT_SELECT_UNIFIED';
	const CONTENT_TYPE_DATA = 'data';
	const CONTENT_TYPE_DISPLAY = 'display';
	const VIEW_TYPE_FULL = 'VIEW_TYPE_FULL';
	const VIEW_TYPE_SPLIT = 'VIEW_TYPE_SPLIT';

	const [contentSelect, setContentSelect] = useState(CONTENT_SELECT_UNIFIED);
	const [viewType, setViewType] = useState(VIEW_TYPE_FULL);
	const [contentType, setContentType] = useState(CONTENT_TYPE_DISPLAY);

	const getContentSelectTitle = (contentSelect) => {
		if (contentSelect === CONTENT_SELECT_LEFT) {
			return Liferay.Language.get('production');
		}
		else if (contentSelect === CONTENT_SELECT_RIGHT) {
			return Liferay.Language.get('publication');
		}

		return Liferay.Language.get('unified');
	};

	const renderContentSelect = () => {
		if (viewType !== VIEW_TYPE_FULL) {
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
				<ClayDropDownWithItems
					items={[
						{
							active: contentSelect === CONTENT_SELECT_UNIFIED,
							label: getContentSelectTitle(
								CONTENT_SELECT_UNIFIED
							),
							onClick: () => {
								setContentSelect(CONTENT_SELECT_UNIFIED);
							},
						},
						{
							active: contentSelect === CONTENT_SELECT_LEFT,
							label: getContentSelectTitle(CONTENT_SELECT_LEFT),
							onClick: () => {
								setContentSelect(CONTENT_SELECT_LEFT);
							},
						},
						{
							active: contentSelect === CONTENT_SELECT_RIGHT,
							label: getContentSelectTitle(CONTENT_SELECT_RIGHT),
							onClick: () => {
								setContentSelect(CONTENT_SELECT_RIGHT);
							},
						},
					]}
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

	const renderDiffLegend = () => {
		if (
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

	const renderToolbar = () => {
		return (
			<td className="publications-render-table-toolbar">
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
									className="nav-link"
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
		<div>
			<table className="publications-render-table table">
				<tr>{renderToolbar()}</tr>
				<tr className="publications-render-table-divider table-divider">
					<td className="publications-render-table-td">DIVIDER</td>
				</tr>
				<tr>
					<td className="publications-render-table-td">CONTENT</td>
				</tr>
			</table>
		</div>
	);
};

export default ChangeTrackingRenderView;
export {ChangeTrackingRenderView};
