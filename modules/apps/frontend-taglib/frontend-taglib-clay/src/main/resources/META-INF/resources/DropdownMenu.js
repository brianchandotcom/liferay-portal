/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import classNames from 'classnames';
import React, {useState} from 'react';

import normalizeDropdownItems from './normalize_dropdown_items';

export default function DropdownMenu({
	actionsDropdown = false,
	additionalProps: _additionalProps,
	alignmentByViewport,
	alignmentPosition,
	componentId: _componentId,
	cssClass,
	icon,
	items,
	label,
	locale: _locale,
	menuProps,
	portletId: _portletId,
	portletNamespace: _portletNamespace,
	searchable,
	swapIconSide,
	...otherProps
}) {
	const [filteredItems, setFilteredItems] = useState(items);
	const [searchValue, setSearchValue] = useState('');

	const searchableProps = searchable
		? {
				onSearchValueChange: (value) => {
					setFilteredItems(() =>
						value
							? items.filter(
									({label}) =>
										label
											.toLowerCase()
											.indexOf(value.toLowerCase()) !== -1
								)
							: items
					);

					setSearchValue(value);
				},
				searchValue,
				searchable,
			}
		: {};

	return (
		<>
			<ClayDropDownWithItems
				{...searchableProps}
				alignmentByViewport={alignmentByViewport}
				alignmentPosition={alignmentPosition}
				className={classNames({
					'dropdown-action': actionsDropdown,
				})}
				items={normalizeDropdownItems(filteredItems) || []}
				menuElementAttrs={menuProps}
				trigger={
					<ClayButton
						className={classNames(cssClass, {
							'component-action': actionsDropdown,
						})}
						{...otherProps}
					>
						{icon && !swapIconSide && (
							<span
								className={classNames('inline-item', {
									'inline-item-before': label,
								})}
							>
								<ClayIcon symbol={icon} />
							</span>
						)}

						{label}

						{icon && swapIconSide && (
							<span
								className={classNames('inline-item', {
									'inline-item-after': label,
								})}
							>
								<ClayIcon symbol={icon} />
							</span>
						)}
					</ClayButton>
				}
			/>

			<div className="quick-action-menu">
				{items.map(({data, href, icon, label, quickAction, ...rest}) =>
					data?.action && quickAction ? (
						<ClayButtonWithIcon
							className="component-action quick-action-item"
							displayType="unstyled"
							key={data.action}
							small={true}
							symbol={icon}
							title={label}
							{...rest}
						/>
					) : (
						href &&
						icon &&
						quickAction && (
							<ClayLink
								className="component-action quick-action-item"
								href={href}
								key={href}
								title={label}
								{...rest}
							>
								<ClayIcon symbol={icon} />
							</ClayLink>
						)
					)
				)}
			</div>
		</>
	);
}
