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

import classnames from 'classnames';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import {CP_INSTANCE_CHANGED} from '../../utilities/eventsDefinitions';
import QuantitySelector from '../quantity_selector/QuantitySelector';

function WrapperWithQuantity({AddToCartButton, ...props}) {
	const {cpInstance, settings} = props;

	const [currentQuantity, setCurrentQuantity] = useState();
	const [disabled, setDisabled] = useState(
		settings.disabled || !cpInstance.accountId
	);

	const onCPInstanceChange = ({cpInstance}) =>
		setDisabled(!cpInstance.stockQuantity > 0);

	useEffect(() => {
		if (settings.willUpdate) {
			Liferay.on(CP_INSTANCE_CHANGED, onCPInstanceChange);
		}

		return () => {
			if (settings.willUpdate) {
				Liferay.detach(CP_INSTANCE_CHANGED, onCPInstanceChange);
			}
		};
	}, [settings.willUpdate]);

	return (
		<div
			className={classnames({
				'add-to-cart-wrapper': true,
				'align-items-center': true,
				'd-flex': true,
				'flex-column': props.settings.block,
			})}
		>
			<QuantitySelector
				{...props.settings.withQuantity}
				disabled={disabled}
				large={!props.settings.block}
				onUpdate={setCurrentQuantity}
			/>

			<AddToCartButton {...props} quantity={currentQuantity} />
		</div>
	);
}

WrapperWithQuantity.propTypes = {
	AddToCartButton: PropTypes.func.isRequired,
	cpInstance: PropTypes.shape({
		accountId: PropTypes.number,
	}),
	quantity: PropTypes.number,
	settings: PropTypes.shape({
		block: PropTypes.bool,
		disabled: PropTypes.bool,
		willUpdate: PropTypes.bool,
		withQuantity: PropTypes.shape({
			allowedQuantities: PropTypes.arrayOf(PropTypes.number),
			disabled: PropTypes.bool,
			forceDropdown: PropTypes.bool,
			large: PropTypes.bool,
			maxQuantity: PropTypes.number,
			minQuantity: PropTypes.number,
			multipleQuantity: PropTypes.number,
			name: PropTypes.string,
			onUpdate: PropTypes.func,
			quantity: PropTypes.number,
		}),
	}),
};

export default WrapperWithQuantity;
