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
import ClayLink from '@clayui/link';
import React, {useRef, useState} from 'react';

const CreationMenu = ({
	maxPrimaryItems,
	maxSecondaryItems,
	maxTotalItems = 15,
	onShowMoreButtonClick,
	primaryItems,
	secondaryItems,
	viewMoreURL,
}) => {
	const [active, setActive] = useState(false);

	const secondaryItemsCountRef = useRef(
		secondaryItems?.reduce((acc, cur) => {
			return acc + cur.items.length;
		}, 0) ?? 0
	);

	const totalItemsCountRef = useRef(
		primaryItems.length + secondaryItemsCountRef.current
	);

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
			{primaryItems.length > 1 || secondaryItems ? (
				<ClayDropDown
					active={active}
					onActiveChange={setActive}
					trigger={
						<ClayButtonWithIcon
							className="nav-btn nav-btn-monospaced"
							symbol="plus"
						/>
					}
				>
					{visibleItemsCount < totalItemsCountRef.current ? (
						<>
							<div className="inline-scroller">
								<ItemList
									primaryItems={primaryItems}
									secondaryItems={secondaryItems}
									visibleItemsCount={visibleItemsCount}
								/>
							</div>

							<div className="dropdown-caption">
								{Liferay.Util.sub(
									Liferay.Language.get(
										'showing-x-of-x-elements'
									),
									visibleItemsCount,
									totalItemsCountRef.current
								)}
							</div>

							<div className="dropdown-section">
								{viewMoreURL ? (
									<ClayLink
										button={{block: true}}
										displayType="secondary"
										href={viewMoreURL}
									>
										{Liferay.Language.get('more')}
									</ClayLink>
								) : (
									<ClayButton
										block={true}
										displayType="secondary"
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
									</ClayButton>
								)}
							</div>
						</>
					) : (
						<ItemList
							primaryItems={primaryItems}
							secondaryItems={secondaryItems}
							visibleItemsCount={totalItemsCountRef.current}
						/>
					)}
				</ClayDropDown>
			) : (
				<ClayLink
					button={true}
					className="nav-btn nav-btn-monospaced"
					displayType="primary"
					href={primaryItems[0].href}
				>
					<ClayIcon symbol="plus" />
				</ClayLink>
			)}
		</>
	);
};

const ItemList = ({primaryItems, secondaryItems, visibleItemsCount}) => {
	let currentItemCount = 0;

	return (
		<ClayDropDown.ItemList>
			{primaryItems?.map((item, index) => {
				currentItemCount++;

				if (currentItemCount > visibleItemsCount) {
					return false;
				}

				return (
					<ClayDropDown.Item href={item.href} key={index}>
						{item.label}
					</ClayDropDown.Item>
				);
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
							<ClayDropDown.Item href={item.href} key={index}>
								{item.label}
							</ClayDropDown.Item>
						);
					})}

					{secondaryItemsGroup.separator && (
						<ClayDropDown.Item className="dropdown-divider" />
					)}
				</ClayDropDown.Group>
			))}
		</ClayDropDown.ItemList>
	);
};

export default CreationMenu;
