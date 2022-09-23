/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import Nav from '@clayui/nav';
import {memo, useState} from 'react';
import Skeleton from '../Skeleton';

const NavSegment = ({
	disabled,
	items,
	loading,
	maxItemsLoading = 3,
	onSelect,
	selectedIndex,
}) => {
	const [currentIndex, setCurrentIndex] = useState(selectedIndex || 0);

	const handleOnClick = (index) => {
		if (index !== currentIndex) {
			setCurrentIndex(index);

			if (selectedIndex !== undefined) {
				onSelect(index);

				return;
			}

			onSelect(items[index]);
		}
	};

	const getNavItemsSkeleton = () =>
		[...new Array(maxItemsLoading)].map((_, index) => (
			<Nav.Item key={index}>
				<Nav.Link>
					<Skeleton height={20} width={100} />
				</Nav.Link>
			</Nav.Item>
		));

	const getNavItems = () =>
		items?.map((item, index) => (
			<Nav.Item
				key={`${item.key}-${index}`}
				onClick={() => handleOnClick(index)}
			>
				<Nav.Link
					active={index === currentIndex}
					className="cp-nav-link text-neutral-10"
					disabled={disabled}
				>
					{item.label}
				</Nav.Link>
			</Nav.Item>
		));

	return (
		<Nav className="nav-segment">
			{loading ? getNavItemsSkeleton() : getNavItems()}
		</Nav>
	);
};

export default memo(NavSegment);
