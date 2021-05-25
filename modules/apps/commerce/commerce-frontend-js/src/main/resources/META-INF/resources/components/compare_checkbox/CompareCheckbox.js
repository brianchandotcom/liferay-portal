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

import {ClayCheckbox} from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useState} from 'react';

import {
	ITEM_REMOVED_FROM_COMPARE,
	PRODUCT_COMPARISON_TOGGLED,
	TOGGLE_ITEM_IN_PRODUCT_COMPARISON,
} from '../../utilities/eventsDefinitions';

function CompareCheckbox({
	disabled: isDisabled,
	inCompare: isInCompare,
	itemId,
	label,
	onUpdate,
	pictureUrl,
}) {
	const [inCompare, setInCompare] = useState(isInCompare);
	const [disabled, setDisabled] = useState(isDisabled);

	const toggleCompare = useCallback(
		({disabled = true}) => {
			setDisabled(disabled);

			onUpdate({disabled, inCompare});
		},
		[inCompare, onUpdate]
	);

	const removeFromCompare = useCallback(
		({id}) => {
			if (id === itemId) {
				setInCompare(() => {
					const inCompare = false;

					onUpdate({disabled, inCompare});

					return inCompare;
				});
			}
		},
		[disabled, itemId, onUpdate]
	);

	useEffect(() => {
		Liferay.on(ITEM_REMOVED_FROM_COMPARE, removeFromCompare);
		Liferay.on(PRODUCT_COMPARISON_TOGGLED, toggleCompare);

		return () => {
			Liferay.detach(ITEM_REMOVED_FROM_COMPARE, removeFromCompare);
			Liferay.detach(PRODUCT_COMPARISON_TOGGLED, toggleCompare);
		};
	}, [removeFromCompare, toggleCompare]);

	return (
		<ClayCheckbox
			aria-label={label}
			checked={inCompare}
			className="compare-checkbox-component"
			disabled={disabled && !inCompare}
			label={label}
			onChange={() =>
				setInCompare((currentlyInCompare) => {
					Liferay.fire(TOGGLE_ITEM_IN_PRODUCT_COMPARISON, {
						id: itemId,
						thumbnail: pictureUrl,
					});

					return !currentlyInCompare;
				})
			}
		/>
	);
}

CompareCheckbox.defaultProps = {
	disabled: false,
	inCompare: false,
	label: '',
	onUpdate: () => {},
	pictureUrl: '',
};

CompareCheckbox.propTypes = {
	disabled: PropTypes.bool,
	inCompare: PropTypes.bool,
	itemId: PropTypes.oneOfType([PropTypes.string, PropTypes.number])
		.isRequired,
	label: PropTypes.string,
	onUpdate: PropTypes.func,
	pictureUrl: PropTypes.string,
};

export default CompareCheckbox;
