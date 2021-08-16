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
import ClayButton from '@clayui/button';
import {Align, ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import ClayNavigationBar from '@clayui/navigation-bar';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

export default ({dataURL, getCache, languageId, spritemap, updateCache}) => {
	const CHANGE_TYPE_ADDED = 'added';
	const CHANGE_TYPE_DELETED = 'deleted';
	const CHANGE_TYPE_MODIFIED = 'modified';
	const CHANGE_TYPE_PRODUCTION = 'production';
	const CONTENT_TYPE_DATA = 'data';
	const CONTENT_TYPE_DISPLAY = 'display';
	const VIEW_LEFT = 'VIEW_LEFT';
	const VIEW_RIGHT = 'VIEW_RIGHT';
	const VIEW_SPLIT = 'VIEW_SPLIT';
	const VIEW_UNIFIED = 'VIEW_UNIFIED';

	const [loading, setLoading] = useState(false);
	const [state, setState] = useState({
		contentType: CONTENT_TYPE_DISPLAY,
		renderData: null,
		view: VIEW_UNIFIED,
	});

	useEffect(() => {
		let cachedData = null;

		if (getCache) {
			cachedData = getCache();
		}

		if (cachedData && cachedData.changeType) {
			if (cachedData.changeType === CHANGE_TYPE_PRODUCTION) {
				setState({
					contentType: CONTENT_TYPE_DATA,
					renderData: cachedData,
					view: VIEW_LEFT,
				});

				setLoading(false);

				return;
			}

			const newState = {
				contentType: CONTENT_TYPE_DISPLAY,
				renderData: cachedData,
				view: VIEW_UNIFIED,
			};

			if (
				!Object.prototype.hasOwnProperty.call(
					cachedData,
					'leftContent'
				) &&
				!Object.prototype.hasOwnProperty.call(
					cachedData,
					'leftLocalizedContent'
				) &&
				!Object.prototype.hasOwnProperty.call(
					cachedData,
					'rightContent'
				) &&
				!Object.prototype.hasOwnProperty.call(
					cachedData,
					'rightLocalizedContent'
				)
			) {
				newState.contentType = CONTENT_TYPE_DATA;
			}

			if (
				!Object.prototype.hasOwnProperty.call(cachedData, 'leftTitle')
			) {
				newState.view = VIEW_RIGHT;
			}
			else if (
				!Object.prototype.hasOwnProperty.call(cachedData, 'rightTitle')
			) {
				newState.view = VIEW_LEFT;
			}
			else if (
				cachedData.changeType === CHANGE_TYPE_MODIFIED &&
				!Object.prototype.hasOwnProperty.call(cachedData, 'leftRender')
			) {
				newState.view = VIEW_SPLIT;
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
					contentType: CONTENT_TYPE_DISPLAY,
					renderData: json,
					view: VIEW_UNIFIED,
				};

				if (
					!Object.prototype.hasOwnProperty.call(
						json,
						'leftContent'
					) &&
					!Object.prototype.hasOwnProperty.call(
						json,
						'leftLocalizedContent'
					) &&
					!Object.prototype.hasOwnProperty.call(
						json,
						'rightContent'
					) &&
					!Object.prototype.hasOwnProperty.call(
						json,
						'rightLocalizedContent'
					)
				) {
					newState.contentType = CONTENT_TYPE_DATA;
				}

				if (!Object.prototype.hasOwnProperty.call(json, 'leftTitle')) {
					newState.view = VIEW_RIGHT;
				}
				else if (
					!Object.prototype.hasOwnProperty.call(json, 'rightTitle')
				) {
					newState.view = VIEW_LEFT;
				}
				else if (
					json.changeType === CHANGE_TYPE_MODIFIED &&
					!Object.prototype.hasOwnProperty.call(json, 'leftRender')
				) {
					newState.view = VIEW_SPLIT;
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
	}, [dataURL, getCache, updateCache]);

	const setContentType = (contentType) => {
		setState({
			contentType,
			renderData: state.renderData,
			view: state.view,
		});
	};

	const getContentSelectTitle = (view) => {
		if (view === VIEW_LEFT) {
			return state.renderData.leftTitle;
		}
		else if (view === VIEW_RIGHT) {
			return state.renderData.rightTitle;
		}
		else if (view === VIEW_SPLIT) {
			return Liferay.Language.get('split-view');
		}

		return Liferay.Language.get('unified-view');
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
		else if (
			state.contentType === CONTENT_TYPE_DISPLAY &&
			Object.prototype.hasOwnProperty.call(
				state.renderData,
				'leftLocalizedContent'
			)
		) {
			if (state.renderData.leftLocalizedContent[languageId]) {
				return (
					<div
						dangerouslySetInnerHTML={{
							__html:
								state.renderData.leftLocalizedContent[
									languageId
								],
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
		else if (
			state.renderData.changeType === CHANGE_TYPE_MODIFIED &&
			!Object.prototype.hasOwnProperty.call(
				state.renderData,
				'leftRender'
			)
		) {
			return (
				<ClayAlert displayType="danger" spritemap={spritemap}>
					{Liferay.Language.get('this-item-is-missing-or-is-deleted')}
				</ClayAlert>
			);
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
		else if (
			state.contentType === CONTENT_TYPE_DISPLAY &&
			Object.prototype.hasOwnProperty.call(
				state.renderData,
				'rightLocalizedContent'
			)
		) {
			if (state.renderData.rightLocalizedContent[languageId]) {
				return (
					<div
						dangerouslySetInnerHTML={{
							__html:
								state.renderData.rightLocalizedContent[
									languageId
								],
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
		else if (
			state.contentType === CONTENT_TYPE_DISPLAY &&
			Object.prototype.hasOwnProperty.call(
				state.renderData,
				'unifiedLocalizedContent'
			)
		) {
			if (state.renderData.unifiedLocalizedContent[languageId]) {
				return (
					<div className="taglib-diff-html">
						<div
							dangerouslySetInnerHTML={{
								__html:
									state.renderData.unifiedLocalizedContent[
										languageId
									],
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
		if (state.view !== VIEW_UNIFIED) {
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

	const renderViewDropdown = () => {
		if (
			!Object.prototype.hasOwnProperty.call(
				state.renderData,
				'leftTitle'
			) ||
			!Object.prototype.hasOwnProperty.call(
				state.renderData,
				'rightTitle'
			)
		) {
			let title = null;

			if (state.view === VIEW_LEFT) {
				title = state.renderData.leftTitle;

				if (state.renderData.changeType === CHANGE_TYPE_DELETED) {
					title += ' (' + Liferay.Language.get('deleted') + ')';
				}
			}
			else if (state.view === VIEW_RIGHT) {
				title = state.renderData.rightTitle;

				if (state.renderData.changeType === CHANGE_TYPE_ADDED) {
					title += ' (' + Liferay.Language.get('new') + ')';
				}
			}

			return (
				<div>
					<span className="inline-item inline-item-before">
						<ClayIcon spritemap={spritemap} symbol="rectangle" />
					</span>

					{title}
				</div>
			);
		}

		const pushItem = (items, view) => {
			items.push({
				active: state.view === view,
				label: getContentSelectTitle(view),
				onClick: () => {
					setState({
						contentType: state.contentType,
						renderData: state.renderData,
						view,
					});
				},
				symbolLeft:
					view === VIEW_SPLIT ? 'rectangle-split' : 'rectangle',
			});
		};

		const items = [];

		if (
			state.renderData.changeType !== CHANGE_TYPE_MODIFIED ||
			Object.prototype.hasOwnProperty.call(state.renderData, 'leftRender')
		) {
			pushItem(items, VIEW_UNIFIED);

			items.push({
				type: 'divider',
			});
		}

		pushItem(items, VIEW_LEFT);
		pushItem(items, VIEW_RIGHT);

		items.push({
			type: 'divider',
		});

		pushItem(items, VIEW_SPLIT);

		return (
			<ClayDropDownWithItems
				alignmentPosition={Align.BottomCenter}
				items={items}
				spritemap={spritemap}
				trigger={
					<ClayButton borderless displayType="secondary">
						<span className="inline-item inline-item-before">
							<ClayIcon
								spritemap={spritemap}
								symbol={
									state.view === VIEW_SPLIT
										? 'rectangle-split'
										: 'rectangle'
								}
							/>
						</span>

						{getContentSelectTitle(state.view)}

						<span className="inline-item inline-item-after">
							<ClayIcon
								spritemap={spritemap}
								symbol="caret-bottom"
							/>
						</span>
					</ClayButton>
				}
			/>
		);
	};

	const renderDividers = () => {
		if (state.view === VIEW_SPLIT) {
			return (
				<tr className="publications-render-view-divider table-divider">
					<td
						className="publications-render-view-divider"
						colSpan={2}
					>
						{renderViewDropdown()}
					</td>
				</tr>
			);
		}

		return (
			<tr className="publications-render-view-divider table-divider">
				<td className="publications-render-view-divider">
					{renderViewDropdown()}
				</td>
			</tr>
		);
	};

	const renderToolbar = () => {
		if (state.renderData.changeType === CHANGE_TYPE_PRODUCTION) {
			return '';
		}

		let columns = 1;

		if (state.view === VIEW_SPLIT) {
			columns = 2;
		}

		return (
			<tr className={loading ? 'publications-loading' : ''}>
				<td
					className="publications-render-view-toolbar"
					colSpan={columns}
				>
					<div className="autofit-row">
						<div className="autofit-col">
							<ClayNavigationBar
								spritemap={spritemap}
								triggerLabel={Liferay.Language.get('display')}
							>
								<ClayNavigationBar.Item
									active={
										state.contentType ===
										CONTENT_TYPE_DISPLAY
									}
								>
									<ClayLink
										className={
											!Object.prototype.hasOwnProperty.call(
												state.renderData,
												'leftContent'
											) &&
											!Object.prototype.hasOwnProperty.call(
												state.renderData,
												'leftLocalizedContent'
											) &&
											!Object.prototype.hasOwnProperty.call(
												state.renderData,
												'rightContent'
											) &&
											!Object.prototype.hasOwnProperty.call(
												state.renderData,
												'rightLocalizedContent'
											)
												? 'nav-link btn-link disabled'
												: 'nav-link'
										}
										displayType="unstyled"
										onClick={() =>
											setContentType(CONTENT_TYPE_DISPLAY)
										}
										title={
											!Object.prototype.hasOwnProperty.call(
												state.renderData,
												'leftContent'
											) &&
											!Object.prototype.hasOwnProperty.call(
												state.renderData,
												'leftLocalizedContent'
											) &&
											!Object.prototype.hasOwnProperty.call(
												state.renderData,
												'rightContent'
											) &&
											!Object.prototype.hasOwnProperty.call(
												state.renderData,
												'rightLocalizedContent'
											)
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
										state.contentType === CONTENT_TYPE_DATA
									}
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

			<tr className={loading ? 'publications-loading' : ''}>
				{(state.view === VIEW_LEFT || state.view === VIEW_SPLIT) && (
					<td className="publications-render-view-content">
						{renderContentLeft()}
					</td>
				)}

				{(state.view === VIEW_RIGHT || state.view === VIEW_SPLIT) && (
					<td className="publications-render-view-content">
						{renderContentRight()}
					</td>
				)}

				{state.view === VIEW_UNIFIED && (
					<td className="publications-render-view-content">
						{renderContentUnified()}
					</td>
				)}
			</tr>
		</table>
	);
};
