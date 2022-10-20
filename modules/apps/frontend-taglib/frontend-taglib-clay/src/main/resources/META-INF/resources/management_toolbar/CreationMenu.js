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
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {sub, unescapeHTML} from 'frontend-js-web';
import React, {useContext, useRef, useState} from 'react';

import getDataAttributes from '../get_data_attributes';
import FeatureFlagContext from './FeatureFlagContext';
import LinkOrButton from './LinkOrButton';

import './CreationMenu.scss';

const Item = ({item, onClick}) => {
	return (
		<ClayDropDown.Item
			href={item.href}
			onClick={(event) => {
				onClick(event, {item});
			}}
			symbolLeft={item.icon}
			{...getDataAttributes(item.data)}
		>
			{unescapeHTML(item.label)}
		</ClayDropDown.Item>
	);
};

const ItemList = ({
	onItemClick,
	primaryItems,
	secondaryItems,
	visibleItemsCount,
}) => {
	let currentItemCount = 0;

	return (
		<ClayDropDown.ItemList
			className={classNames({
				'dropdown-menu-indicator-start': primaryItems.some(
					(item) => item.icon
				),
			})}
		>
			{primaryItems?.map((item, index) => {
				currentItemCount++;

				if (currentItemCount > visibleItemsCount) {
					return false;
				}

				return <Item item={item} key={index} />;
			})}

			{secondaryItems?.map((secondaryItemsGroup, index) => (
				<ClayDropDown.Group
					header={secondaryItemsGroup.label}
					key={index}
				>
					{secondaryItemsGroup.items.map((item, index) => {
						currentItemCount++;

						if (currentItemCount > visibleItemsCount) {
							return false;
						}

						return (
							<Item
								item={item}
								key={index}
								onClick={onItemClick}
							/>
						);
					})}

					{secondaryItemsGroup.separator && <ClayDropDown.Divider />}
				</ClayDropDown.Group>
			))}
		</ClayDropDown.ItemList>
	);
};

const CreationMenu = ({
	maxPrimaryItems,
	maxSecondaryItems,
	maxTotalItems = 15,
	onCreateButtonClick,
	onCreationMenuItemClick,
	onShowMoreButtonClick,
	primaryItems,
	secondaryItems,
	viewMoreURL,
}) => {
	const [active, setActive] = useState(false);
	const {showDesignImprovements} = useContext(FeatureFlagContext);

	const secondaryItemsCountRef = useRef(
		secondaryItems?.reduce((acc, cur) => {
			return acc + cur.items.length;
		}, 0) ?? 0
	);

	const totalItemsCountRef = useRef(
		primaryItems.length + secondaryItemsCountRef.current
	);

	const firstItemRef = useRef(
		primaryItems?.[0] ||
			secondaryItems?.[0].items?.[0] ||
			secondaryItems?.[0]
	);

	const getPlusIconLabel = () => {
		const item =
			totalItemsCountRef.current === 1 ? firstItemRef.current : null;

		return item?.label || Liferay.Language.get('new');
	};

	const getVisibleItemsCount = () => {
		const primaryItemsCount = primaryItems.length;
		const secondaryItemsCount = secondaryItemsCountRef.current;

		const defaultMaxPrimaryItems = maxPrimaryItems
			? primaryItemsCount > maxPrimaryItems
				? maxPrimaryItems
				: primaryItemsCount
			: primaryItemsCount > 8
			? 8
			: primaryItemsCount;

		const tempDefaultMaxSecondaryItems = maxSecondaryItems
			? secondaryItemsCount > maxSecondaryItems
				? maxSecondaryItems
				: secondaryItemsCount
			: secondaryItemsCount > 7
			? 7
			: secondaryItemsCount;

		const defaultMaxSecondaryItems =
			tempDefaultMaxSecondaryItems >
			maxTotalItems - defaultMaxPrimaryItems
				? maxTotalItems - defaultMaxPrimaryItems
				: tempDefaultMaxSecondaryItems;

		return secondaryItemsCount === 0
			? primaryItemsCount > maxTotalItems
				? maxTotalItems
				: primaryItemsCount
			: primaryItemsCount > defaultMaxPrimaryItems
			? secondaryItemsCount > defaultMaxSecondaryItems
				? defaultMaxPrimaryItems + defaultMaxSecondaryItems
				: defaultMaxPrimaryItems + secondaryItemsCount
			: secondaryItemsCount > defaultMaxSecondaryItems
			? primaryItemsCount + defaultMaxSecondaryItems
			: primaryItemsCount + secondaryItemsCount;
	};

	const [visibleItemsCount, setVisibleItemsCount] = useState(
		getVisibleItemsCount()
	);

	return (
		<>
			{totalItemsCountRef.current > 1 ? (
				<ClayDropDown
					active={active}
					className="creation-menu"
					onActiveChange={setActive}
					trigger={
						showDesignImprovements ? (
							<ClayButton
								aria-label={getPlusIconLabel()}
								className="nav-btn"
								title={getPlusIconLabel()}
							>
								<ClayIcon
									className="d-md-none dropdown-icon"
									symbol="plus"
								/>

								<span className="d-md-block d-none pl-3 pr-3">
									{getPlusIconLabel()}
								</span>
							</ClayButton>
						) : (
							<ClayButtonWithIcon
								aria-label={getPlusIconLabel()}
								className="nav-btn nav-btn-monospaced"
								symbol="plus"
								title={getPlusIconLabel()}
							/>
						)
					}
				>
					{visibleItemsCount < totalItemsCountRef.current ? (
						<>
							<div className="inline-scroller">
								<ItemList
									onItemClick={onCreationMenuItemClick}
									primaryItems={primaryItems}
									secondaryItems={secondaryItems}
									visibleItemsCount={visibleItemsCount}
								/>
							</div>

							<div className="dropdown-caption">
								{sub(
									Liferay.Language.get(
										'showing-x-of-x-elements'
									),
									visibleItemsCount,
									totalItemsCountRef.current
								)}
							</div>

							<div className="dropdown-section">
								<LinkOrButton
									button={{block: true}}
									displayType="secondary"
									href={viewMoreURL}
									onClick={() => {
										if (onShowMoreButtonClick) {
											onShowMoreButtonClick();

											return;
										}

										setVisibleItemsCount(
											totalItemsCountRef.current
										);
									}}
								>
									{Liferay.Language.get('more')}
								</LinkOrButton>
							</div>
						</>
					) : (
						<ItemList
							onItemClick={onCreationMenuItemClick}
							primaryItems={primaryItems}
							secondaryItems={secondaryItems}
							visibleItemsCount={totalItemsCountRef.current}
						/>
					)}
				</ClayDropDown>
			) : showDesignImprovements ? (
				<>
					<LinkOrButton
						aria-label={getPlusIconLabel()}
						button={true}
						className="nav-btn"
						displayType="primary"
						href={firstItemRef.current.href}
						onClick={(event) => {
							onCreateButtonClick(event, {
								item: firstItemRef.current,
							});
						}}
						symbol="plus"
						title={getPlusIconLabel()}
						wide
					>
						{Liferay.Language.get('new')}
					</LinkOrButton>
				</>
			) : (
				<LinkOrButton
					aria-label={getPlusIconLabel()}
					button={true}
					className="nav-btn nav-btn-monospaced"
					displayType="primary"
					href={firstItemRef.current.href}
					onClick={(event) => {
						onCreateButtonClick(event, {
							item: firstItemRef.current,
						});
					}}
					symbol="plus"
					title={getPlusIconLabel()}
				/>
			)}
		</>
	);
};

export default CreationMenu;
