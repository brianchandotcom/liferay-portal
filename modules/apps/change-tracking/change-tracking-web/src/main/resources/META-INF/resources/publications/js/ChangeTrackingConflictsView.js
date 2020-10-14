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

import {ClayButtonWithIcon} from '@clayui/button';
import {Align, ClayDropDownWithItems} from '@clayui/drop-down';
import ClayList from '@clayui/list';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import React from 'react';

class ChangeTrackingConflictsView extends React.Component {
	constructor(props) {
		super(props);

		const {conflicts, spritemap} = props;

		this.conflicts = conflicts;
		this.spritemap = spritemap;

		this.state = {
			delta: 20,
			page: 1,
		};
	}

	_filterConflicts(conflicts) {
		if (conflicts.length > 5) {
			conflicts = conflicts.slice(
				this.state.delta * (this.state.page - 1),
				this.state.delta * this.state.page
			);
		}

		return conflicts;
	}

	_getDismissAction(conflict) {
		if (!conflict.dismissURL) {
			return '';
		}

		return (
			<ClayList.ItemField>
				<a
					className="btn btn-outline-secondary btn-sm"
					href={conflict.dismissURL}
				>
					{Liferay.Language.get('dismiss')}
				</a>
			</ClayList.ItemField>
		);
	}

	_getDropdownMenu(conflict) {
		if (!conflict.actions) {
			return '';
		}

		const items = [];

		for (let i = 0; i < conflict.actions.length; i++) {
			const action = conflict.actions[i];

			items.push({
				href: action.href,
				label: action.label,
				symbolLeft: action.symbol,
			});
		}

		return (
			<ClayList.ItemField>
				<ClayDropDownWithItems
					alignmentPosition={Align.BottomLeft}
					items={items}
					spritemap={this.spritemap}
					trigger={
						<ClayButtonWithIcon
							displayType="unstyled"
							small
							spritemap={this.spritemap}
							symbol="ellipsis-v"
						/>
					}
				/>
			</ClayList.ItemField>
		);
	}

	_getListItems() {
		const items = [];

		const conflicts = this._filterConflicts(this.conflicts);

		for (let i = 0; i < conflicts.length; i++) {
			const conflict = conflicts[i];

			items.push(
				<ClayList.Item flex>
					<ClayList.ItemField expand>
						<a onClick={() => this._openWindow(conflict)}>
							<ClayList.ItemText
								className="conflicts-description-text"
								subtext
							>
								{conflict.description}
							</ClayList.ItemText>
							<ClayList.ItemTitle>
								{conflict.title}
							</ClayList.ItemTitle>
							<ClayList.ItemText
								className={
									'conflicts-' + conflict.alertType + '-text'
								}
							>
								<strong>
									{conflict.conflictDescription + ': '}
								</strong>
								{conflict.conflictResolution}
							</ClayList.ItemText>
						</a>
					</ClayList.ItemField>

					{this._getDismissAction(conflict)}

					{this._getQuickActionMenu(conflict)}

					{this._getDropdownMenu(conflict)}
				</ClayList.Item>
			);
		}

		return items;
	}

	_getQuickActionMenu(conflict) {
		if (!conflict.actions) {
			return '';
		}

		const items = [];

		for (let i = 0; i < conflict.actions.length; i++) {
			const action = conflict.actions[i];

			items.push(
				<ClayList.QuickActionMenu.Item
					className="lfr-portal-tooltip"
					href={action.href}
					spritemap={this.spritemap}
					symbol={action.symbol}
					title={action.label}
				/>
			);
		}

		return (
			<ClayList.ItemField>
				<ClayList.QuickActionMenu>{items}</ClayList.QuickActionMenu>
			</ClayList.ItemField>
		);
	}

	_handleDeltaChange(delta) {
		this.setState({
			delta,
			page: 1,
		});
	}

	_handlePageChange(page) {
		this.setState({
			page,
		});
	}

	_openWindow(conflict) {
		AUI().use('liferay-portlet-url', 'liferay-util-window', () => {
			const portletURL = Liferay.PortletURL.createURL(conflict.viewURL);

			portletURL.setParameter(
				'alertDescription',
				conflict.conflictDescription
			);
			portletURL.setParameter(
				'alertResolution',
				conflict.conflictResolution
			);
			portletURL.setParameter('alertType', conflict.alertType);

			const header = [];

			if (conflict.actions) {
				for (let i = 0; i < conflict.actions.length; i++) {
					const action = conflict.actions[i];

					header.push({
						cssClass:
							'btn btn-outline-secondary btn-sm publications-view-header-button',
						discardDefaultButtonCssClasses: true,
						labelHTML:
							'<span class="inline-item inline-item-before">' +
							'<svg class="lexicon-icon" focusable="false">' +
							'<use href="' +
							this.spritemap +
							'#' +
							action.symbol +
							' " />' +
							'</svg></span>' +
							action.label,
						on: {
							click() {
								Liferay.Util.navigate(action.href);
							},
						},
					});
				}
			}

			if (conflict.dismissURL) {
				header.push({
					cssClass:
						'btn btn-outline-secondary btn-sm publications-view-header-button',
					discardDefaultButtonCssClasses: true,
					label: Liferay.Language.get('dismiss'),
					on: {
						click() {
							Liferay.Util.navigate(conflict.dismissURL);
						},
					},
				});
			}

			header.push({
				cssClass: 'close btn btn-unstyled',
				discardDefaultButtonCssClasses: true,
				labelHTML:
					'<svg class="lexicon-icon" focusable="false"><use href="' +
					this.spritemap +
					'#times" /></svg>',
				on: {
					click() {
						dialog.hide();
					},
				},
			});

			const dialog = Liferay.Util.Window.getWindow({
				dialog: {
					destroyOnHide: true,
					resizable: false,
					toolbars: {
						header,
					},
				},
				title: Liferay.Language.get('conflicting-change'),
				uri: portletURL.toString(),
			});
		});
	}

	_renderPagination() {
		if (this.conflicts.length <= 5) {
			return '';
		}

		return (
			<ClayPaginationBarWithBasicItems
				activeDelta={this.state.delta}
				activePage={this.state.page}
				deltas={[4, 8, 20, 40, 60].map((size) => ({
					label: size,
				}))}
				ellipsisBuffer={3}
				onDeltaChange={(delta) => this._handleDeltaChange(delta)}
				onPageChange={(page) => this._handlePageChange(page)}
				totalItems={this.conflicts.length}
			/>
		);
	}

	render() {
		return (
			<>
				<ClayList showQuickActionsOnHover>
					{this._getListItems()}
				</ClayList>

				{this._renderPagination()}
			</>
		);
	}
}

export default function (props) {
	return <ChangeTrackingConflictsView {...props} />;
}
