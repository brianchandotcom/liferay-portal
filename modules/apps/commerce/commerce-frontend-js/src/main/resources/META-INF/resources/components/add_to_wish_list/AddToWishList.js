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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import LegacyWishListResource from './util/LegacyWishListResource';

function AddToWishList({
	iconOnly,
	isInWishlist,
	large,
	spritemap,
	...productInfo
}) {
	const [isAdded, setIsAdded] = useState(isInWishlist);

	const toggleInWishList = () =>
		LegacyWishListResource.toggleInWishList(productInfo).then(
			({success = false}) => {
				if (success) {
					setIsAdded(!isAdded);
				}
			}
		);

	/**
	 * The following to become a trigger
	 * for the ClayDropDown in 7.4 GA2
	 */
	return (
		<ClayButton
			className={`btn-outline-borderless btn-${large ? 'lg' : 'sm'}`}
			displayType={'secondary'}
			onClick={toggleInWishList}
		>
			{!iconOnly && (
				<span className={'text-truncate-inline'}>
					<span className={'font-weight-normal text-truncate'}>
						{Liferay.Language.get('add-to-list')}
					</span>
				</span>
			)}

			<span className={'wish-list-icon'}>
				<ClayIcon
					spritemap={spritemap}
					symbol={`heart${isAdded ? '-full' : ''}`}
				/>
			</span>
		</ClayButton>
	);
}

AddToWishList.defaultProps = {
	isInWishList: false,
	large: false,
};

AddToWishList.propTypes = {
	accountId: PropTypes.number,
	cpDefinitionId: PropTypes.number,
	isInWishList: PropTypes.bool,
	large: PropTypes.bool,
	skuId: PropTypes.number,
	spritemap: PropTypes.string,
};

export default AddToWishList;
