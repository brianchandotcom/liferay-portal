/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {ManagementToolbar} from 'frontend-js-components-web';
import React, {useContext, useMemo} from 'react';

import normalizeDropdownItems from '../normalize_dropdown_items';
import FeatureFlagContext from './FeatureFlagContext';
import LinkOrButton from './LinkOrButton';

function addAction(item, onActionButtonClick) {
	if (item.type === 'group') {
		return {
			...item,
			items: item.items?.map((child) =>
				addAction(child, onActionButtonClick)
			),
		};
	}

	const clone = {
		onClick: (event) => {
			onActionButtonClick(event, {item});
		},
		...item,
	};

	delete clone.quickAction;

	return clone;
}

const ActionControls = ({
	actionDropdownItems,
	disabled,
	onActionButtonClick,
}) => {
	const {showDesignImprovements} = useContext(FeatureFlagContext);

	const items = useMemo(
		() =>
			normalizeDropdownItems(
				actionDropdownItems?.map((item) =>
					addAction(item, onActionButtonClick)
				)
			) || [],
		[actionDropdownItems, onActionButtonClick]
	);

	return (
		<>
			{actionDropdownItems && (
				<>
					{actionDropdownItems
						.flatMap((item) =>
							item.type === 'group' ? item.items : [item]
						)
						.filter((item) => item.quickAction && item.icon)
						.map((item, index) => (
							<ManagementToolbar.Item
								className="d-md-flex d-none"
								key={index}
							>
								<LinkOrButton
									className={classNames(
										{'d-lg-none': showDesignImprovements},
										'nav-link nav-link-monospaced'
									)}
									disabled={disabled || item.disabled}
									displayType="unstyled"
									href={item.href}
									onClick={(event) => {
										onActionButtonClick(event, {
											item,
										});
									}}
									symbol={item.icon}
									title={item.label}
								/>

								{showDesignImprovements && (
									<LinkOrButton
										className="align-items-center d-lg-inline d-none mr-2 nav-link"
										disabled={disabled || item.disabled}
										displayType="unstyled"
										href={item.href}
										onClick={(event) => {
											onActionButtonClick(event, {
												item,
											});
										}}
										title={item.label}
									>
										<span className="inline-item inline-item-before">
											<ClayIcon symbol={item.icon} />
										</span>

										<span>{item.label}</span>
									</LinkOrButton>
								)}
							</ManagementToolbar.Item>
						))}

					<ManagementToolbar.Item>
						<ClayDropDownWithItems
							items={items}
							trigger={
								<ClayButtonWithIcon
									className="nav-link nav-link-monospaced"
									disabled={disabled}
									displayType="unstyled"
									symbol="ellipsis-v"
									title={Liferay.Language.get('actions')}
								/>
							}
						/>
					</ManagementToolbar.Item>
				</>
			)}
		</>
	);
};

export default ActionControls;
