/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {showErrorNotification} from '../../utilities/notifications';
import LegacyWishListResource from './util/LegacyWishListResource';

function AddToWishList({iconOnly, isInWishList, large, ...productInfo}) {
	const [isAdded, setIsAdded] = useState(isInWishList);

	const toggleInWishList = () =>
		LegacyWishListResource.toggleInWishList(productInfo)
			.then(({success}) => setIsAdded(success))
			.catch((error) => {
				showErrorNotification(error);
			});

	/**
	 * The following to become a trigger
	 * for the ClayDropDown in 7.4 GA2
	 */
	return (
		<ClayButton
			className={`btn-outline-borderless btn-${large ? 'lg' : 'sm'}`}
			displayType="secondary"
			onClick={toggleInWishList}
		>
			{!iconOnly && (
				<span className="text-truncate-inline">
					<span className="font-weight-normal text-truncate">
						{Liferay.Language.get('add-to-list')}
					</span>
				</span>
			)}

			<span className="wish-list-icon">
				<ClayIcon symbol={`heart${isAdded ? '-full' : ''}`} />
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
};

export default AddToWishList;
