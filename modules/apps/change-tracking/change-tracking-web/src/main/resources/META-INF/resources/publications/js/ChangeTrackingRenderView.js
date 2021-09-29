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
import ClayDropDown, {Align, ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import ClayLink from '@clayui/link';
import ClayNavigationBar from '@clayui/navigation-bar';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

const LocalizationDropdown = ({
	currentLocale,
	defaultLocale,
	locales,
	setSelectedLocale,
	spritemap,
}) => {
	const [active, setActive] = useState(false);

	return (
		<div className="autofit-col publications-localization">
			<ClayDropDown
				active={active}
				onActiveChange={setActive}
				trigger={
					<ClayButton
						displayType="secondary"
						monospaced
						onClick={() => setActive(!active)}
					>
						<span className="inline-item">
							<ClayIcon
								spritemap={spritemap}
								symbol={currentLocale.symbol}
							/>
						</span>
						<span className="btn-section">
							{currentLocale.label}
						</span>
					</ClayButton>
				}
			>
				<ClayDropDown.ItemList>
					{locales
						.sort((a, b) => {
							if (a.label === defaultLocale.label) {
								return -1;
							}
							else if (b.label === defaultLocale.label) {
								return 1;
							}

							return 0;
						})
						.map((locale) => {
							return (
								<ClayDropDown.Item
									key={locale.label}
									onClick={() => {
										setActive(false);
										setSelectedLocale(locale);
									}}
								>
									<ClayLayout.ContentRow containerElement="span">
										<ClayLayout.ContentCol
											containerElement="span"
											expand
										>
											<ClayLayout.ContentSection>
												<ClayIcon
													className="inline-item inline-item-before"
													spritemap={spritemap}
													symbol={locale.symbol}
												/>

												{locale.label}
											</ClayLayout.ContentSection>
										</ClayLayout.ContentCol>
										<ClayLayout.ContentCol containerElement="span">
											<ClayLayout.ContentSection>
												<ClayLabel
													displayType={
														locale.label ===
														defaultLocale.label
															? 'info'
															: 'success'
													}
												>
													{locale.label ===
													defaultLocale.label
														? Liferay.Language.get(
																'default'
														  )
														: Liferay.Language.get(
																'translated'
														  )}
												</ClayLabel>
											</ClayLayout.ContentSection>
										</ClayLayout.ContentCol>
									</ClayLayout.ContentRow>
								</ClayDropDown.Item>
							);
						})}
				</ClayDropDown.ItemList>
			</ClayDropDown>
		</div>
	);
};

export default ({
	dataURL,
	defaultLocale,
	description,
	discardURL,
	getCache,
	showDropdown,
	showHeader = true,
	spritemap,
	title,
	updateCache,
}) => {
	const CHANGE_TYPE_ADDED = 'added';
	const CHANGE_TYPE_DELETED = 'deleted';
	const CHANGE_TYPE_MODIFIED = 'modified';
	const CHANGE_TYPE_PRODUCTION = 'production';
	const CONTENT_TYPE_RENDER = 'data';
	const CONTENT_TYPE_PREVIEW = 'display';
	const VIEW_LEFT = 'VIEW_LEFT';
	const VIEW_RIGHT = 'VIEW_RIGHT';
	const VIEW_SPLIT = 'VIEW_SPLIT';
	const VIEW_UNIFIED = 'VIEW_UNIFIED';

	const [loading, setLoading] = useState(false);
	const [selectedLocale, setSelectedLocale] = useState(defaultLocale);
	const [state, setState] = useState({
		contentType: CONTENT_TYPE_PREVIEW,
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
					contentType: CONTENT_TYPE_RENDER,
					renderData: cachedData,
					view: VIEW_LEFT,
				});

				setLoading(false);

				return;
			}

			const newState = {
				contentType: CONTENT_TYPE_PREVIEW,
				renderData: cachedData,
				view: VIEW_UNIFIED,
			};

			if (
				!Object.prototype.hasOwnProperty.call(
					cachedData,
					'leftPreview'
				) &&
				!Object.prototype.hasOwnProperty.call(
					cachedData,
					'leftLocalizedPreview'
				) &&
				!Object.prototype.hasOwnProperty.call(
					cachedData,
					'rightPreview'
				) &&
				!Object.prototype.hasOwnProperty.call(
					cachedData,
					'rightLocalizedPreview'
				)
			) {
				newState.contentType = CONTENT_TYPE_RENDER;
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

			if (
				newState.view === VIEW_UNIFIED &&
				((newState.contentType === CONTENT_TYPE_RENDER &&
					!Object.prototype.hasOwnProperty.call(
						cachedData,
						'unifiedRender'
					) &&
					!Object.prototype.hasOwnProperty.call(
						cachedData,
						'unifiedLocalizedRender'
					)) ||
					(newState.contentType === CONTENT_TYPE_PREVIEW &&
						!Object.prototype.hasOwnProperty.call(
							cachedData,
							'unifiedPreview'
						) &&
						!Object.prototype.hasOwnProperty.call(
							cachedData,
							'unifiedLocalizedPreview'
						)))
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
					contentType: CONTENT_TYPE_PREVIEW,
					renderData: json,
					view: VIEW_UNIFIED,
				};

				if (
					!Object.prototype.hasOwnProperty.call(
						json,
						'leftPreview'
					) &&
					!Object.prototype.hasOwnProperty.call(
						json,
						'leftLocalizedPreview'
					) &&
					!Object.prototype.hasOwnProperty.call(
						json,
						'rightPreview'
					) &&
					!Object.prototype.hasOwnProperty.call(
						json,
						'rightLocalizedPreview'
					)
				) {
					newState.contentType = CONTENT_TYPE_RENDER;
				}

				if (!Object.prototype.hasOwnProperty.call(json, 'leftTitle')) {
					newState.view = VIEW_RIGHT;
				}
				else if (
					!Object.prototype.hasOwnProperty.call(json, 'rightTitle')
				) {
					newState.view = VIEW_LEFT;
				}

				if (
					newState.view === VIEW_UNIFIED &&
					((newState.contentType === CONTENT_TYPE_RENDER &&
						!Object.prototype.hasOwnProperty.call(
							json,
							'unifiedRender'
						) &&
						!Object.prototype.hasOwnProperty.call(
							json,
							'unifiedLocalizedRender'
						)) ||
						(newState.contentType === CONTENT_TYPE_PREVIEW &&
							!Object.prototype.hasOwnProperty.call(
								json,
								'unifiedPreview'
							) &&
							!Object.prototype.hasOwnProperty.call(
								json,
								'unifiedLocalizedPreview'
							)))
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

	let currentLocale = selectedLocale;
	let currentTitle = title;

	if (showHeader && state.renderData) {
		if (
			!state.renderData.locales ||
			!state.renderData.locales.find(
				(item) => item.label === currentLocale.label
			)
		) {
			if (state.renderData.defaultLocale) {
				currentLocale = state.renderData.defaultLocale;
			}
			else {
				currentLocale = defaultLocale;
			}
		}

		if (
			state.renderData.localizedTitles &&
			state.renderData.localizedTitles[currentLocale.label]
		) {
			currentTitle =
				state.renderData.localizedTitles[currentLocale.label];
		}
	}

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

	const renderPreviewLeft = () => {
		if (
			state.contentType === CONTENT_TYPE_RENDER &&
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
			state.contentType === CONTENT_TYPE_RENDER &&
			Object.prototype.hasOwnProperty.call(
				state.renderData,
				'leftLocalizedRender'
			)
		) {
			if (state.renderData.leftLocalizedRender[currentLocale.label]) {
				return (
					<div
						dangerouslySetInnerHTML={{
							__html:
								state.renderData.leftLocalizedRender[
									currentLocale.label
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
		else if (
			state.contentType === CONTENT_TYPE_PREVIEW &&
			Object.prototype.hasOwnProperty.call(
				state.renderData,
				'leftPreview'
			)
		) {
			if (state.renderData.leftPreview) {
				return (
					<div
						dangerouslySetInnerHTML={{
							__html: state.renderData.leftPreview,
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
			state.contentType === CONTENT_TYPE_PREVIEW &&
			Object.prototype.hasOwnProperty.call(
				state.renderData,
				'leftLocalizedPreview'
			)
		) {
			if (state.renderData.leftLocalizedPreview[currentLocale.label]) {
				return (
					<div
						dangerouslySetInnerHTML={{
							__html:
								state.renderData.leftLocalizedPreview[
									currentLocale.label
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
			) &&
			!Object.prototype.hasOwnProperty.call(
				state.renderData,
				'leftLocalizedRender'
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

	const renderPreviewRight = () => {
		if (
			state.contentType === CONTENT_TYPE_RENDER &&
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
			state.contentType === CONTENT_TYPE_RENDER &&
			Object.prototype.hasOwnProperty.call(
				state.renderData,
				'rightLocalizedRender'
			)
		) {
			if (state.renderData.rightLocalizedRender[currentLocale.label]) {
				return (
					<div
						dangerouslySetInnerHTML={{
							__html:
								state.renderData.rightLocalizedRender[
									currentLocale.label
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
		else if (
			state.contentType === CONTENT_TYPE_PREVIEW &&
			Object.prototype.hasOwnProperty.call(
				state.renderData,
				'rightPreview'
			)
		) {
			if (state.renderData.rightPreview) {
				return (
					<div
						dangerouslySetInnerHTML={{
							__html: state.renderData.rightPreview,
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
			state.contentType === CONTENT_TYPE_PREVIEW &&
			Object.prototype.hasOwnProperty.call(
				state.renderData,
				'rightLocalizedPreview'
			)
		) {
			if (state.renderData.rightLocalizedPreview[currentLocale.label]) {
				return (
					<div
						dangerouslySetInnerHTML={{
							__html:
								state.renderData.rightLocalizedPreview[
									currentLocale.label
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

	const renderPreviewUnified = () => {
		if (
			state.contentType === CONTENT_TYPE_RENDER &&
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
			state.contentType === CONTENT_TYPE_RENDER &&
			Object.prototype.hasOwnProperty.call(
				state.renderData,
				'unifiedLocalizedRender'
			)
		) {
			if (state.renderData.unifiedLocalizedRender[currentLocale.label]) {
				return (
					<div className="taglib-diff-html">
						<div
							dangerouslySetInnerHTML={{
								__html:
									state.renderData.unifiedLocalizedRender[
										currentLocale.label
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
		else if (
			state.contentType === CONTENT_TYPE_PREVIEW &&
			Object.prototype.hasOwnProperty.call(
				state.renderData,
				'unifiedPreview'
			)
		) {
			if (state.renderData.unifiedPreview) {
				return (
					<div className="taglib-diff-html">
						<div
							dangerouslySetInnerHTML={{
								__html: state.renderData.unifiedPreview,
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
			state.contentType === CONTENT_TYPE_PREVIEW &&
			Object.prototype.hasOwnProperty.call(
				state.renderData,
				'unifiedLocalizedPreview'
			)
		) {
			if (state.renderData.unifiedLocalizedPreview[currentLocale.label]) {
				return (
					<div className="taglib-diff-html">
						<div
							dangerouslySetInnerHTML={{
								__html:
									state.renderData.unifiedLocalizedPreview[
										currentLocale.label
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

	const renderDropdownMenu = () => {
		if (!showDropdown || !state.renderData) {
			return null;
		}

		const dropdownItems = [];

		if (state.renderData.editURL) {
			dropdownItems.push({
				href: state.renderData.editURL,
				label: Liferay.Language.get('edit'),
				symbolLeft: 'pencil',
			});
		}

		dropdownItems.push({
			href: discardURL,
			label: Liferay.Language.get('discard'),
			symbolLeft: 'times-circle',
		});

		for (let i = 0; i < dropdownItems.length; i++) {
			const dropdownItem = dropdownItems[i];

			const href = dropdownItem.href;

			if (typeof href !== 'string') {
				continue;
			}

			const index = href.indexOf('?');

			if (index > 0) {
				let redirectKey = null;

				const params = new URLSearchParams(href.substring(index + 1));

				params.forEach((value, key) => {
					if (key.endsWith('_redirect')) {
						redirectKey = key;
					}
				});

				if (redirectKey) {
					params.set(
						redirectKey,
						window.location.pathname + window.location.search
					);

					dropdownItem.href =
						href.substring(0, index) + '?' + params.toString();
				}
			}
		}

		return (
			<div className="autofit-col">
				<ClayDropDownWithItems
					alignmentPosition={Align.BottomLeft}
					items={dropdownItems}
					spritemap={spritemap}
					trigger={
						<ClayButtonWithIcon
							displayType="unstyled"
							small
							spritemap={spritemap}
							symbol="ellipsis-v"
						/>
					}
				/>
			</div>
		);
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
			(state.contentType === CONTENT_TYPE_RENDER &&
				(Object.prototype.hasOwnProperty.call(
					state.renderData,
					'unifiedRender'
				) ||
					Object.prototype.hasOwnProperty.call(
						state.renderData,
						'unifiedLocalizedRender'
					))) ||
			(state.contentType === CONTENT_TYPE_PREVIEW &&
				(Object.prototype.hasOwnProperty.call(
					state.renderData,
					'unifiedPreview'
				) ||
					Object.prototype.hasOwnProperty.call(
						state.renderData,
						'unifiedLocalizedPreview'
					)))
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

	const renderEntry = () => {
		if (!state.renderData) {
			if (loading) {
				return (
					<div>
						<span
							aria-hidden="true"
							className="loading-animation"
						/>
					</div>
				);
			}

			return '';
		}
		else if (
			!state.renderData.changeType ||
			state.renderData.errorMessage
		) {
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

				<tr>
					{(state.view === VIEW_LEFT ||
						state.view === VIEW_SPLIT) && (
						<td className="publications-render-view-content">
							{renderPreviewLeft()}
						</td>
					)}

					{(state.view === VIEW_RIGHT ||
						state.view === VIEW_SPLIT) && (
						<td className="publications-render-view-content">
							{renderPreviewRight()}
						</td>
					)}

					{state.view === VIEW_UNIFIED && (
						<td className="publications-render-view-content">
							{renderPreviewUnified()}
						</td>
					)}
				</tr>
			</table>
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
			<tr>
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
										CONTENT_TYPE_PREVIEW
									}
								>
									<ClayLink
										className={
											!Object.prototype.hasOwnProperty.call(
												state.renderData,
												'leftPreview'
											) &&
											!Object.prototype.hasOwnProperty.call(
												state.renderData,
												'leftLocalizedPreview'
											) &&
											!Object.prototype.hasOwnProperty.call(
												state.renderData,
												'rightPreview'
											) &&
											!Object.prototype.hasOwnProperty.call(
												state.renderData,
												'rightLocalizedPreview'
											)
												? 'nav-link btn-link disabled'
												: 'nav-link'
										}
										displayType="unstyled"
										onClick={() => {
											if (
												state &&
												state.view === VIEW_UNIFIED &&
												state.renderData &&
												!Object.prototype.hasOwnProperty.call(
													state.renderData,
													'unifiedPreview'
												) &&
												!Object.prototype.hasOwnProperty.call(
													state.renderData,
													'unifiedLocalizedPreview'
												)
											) {
												setState({
													contentType: CONTENT_TYPE_PREVIEW,
													renderData:
														state.renderData,
													view: VIEW_SPLIT,
												});

												return;
											}

											setContentType(
												CONTENT_TYPE_PREVIEW
											);
										}}
										title={
											!Object.prototype.hasOwnProperty.call(
												state.renderData,
												'leftPreview'
											) &&
											!Object.prototype.hasOwnProperty.call(
												state.renderData,
												'leftLocalizedPreview'
											) &&
											!Object.prototype.hasOwnProperty.call(
												state.renderData,
												'rightPreview'
											) &&
											!Object.prototype.hasOwnProperty.call(
												state.renderData,
												'rightLocalizedPreview'
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
										state.contentType ===
										CONTENT_TYPE_RENDER
									}
								>
									<ClayLink
										className="nav-link"
										displayType="unstyled"
										onClick={() =>
											setContentType(CONTENT_TYPE_RENDER)
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

	if (!showHeader) {
		return renderEntry();
	}

	return (
		<div className={`sheet ${loading ? 'publications-loading' : ''}`}>
			{state.renderData && (
				<div className="autofit-row sheet-title">
					{state.renderData.locales &&
						state.renderData.locales.length > 0 && (
							<LocalizationDropdown
								currentLocale={currentLocale}
								defaultLocale={state.renderData.defaultLocale}
								locales={state.renderData.locales}
								setSelectedLocale={setSelectedLocale}
								spritemap={spritemap}
							/>
						)}
					<div className="autofit-col autofit-col-expand">
						<h2>{currentTitle}</h2>

						<div className="entry-description">{description}</div>
					</div>
					{renderDropdownMenu()}
				</div>
			)}
			<div className="sheet-section">{renderEntry()}</div>
		</div>
	);
};
