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

/* eslint no-unused-vars: "warn" */

import PropTypes from 'prop-types';
import React, {useState} from 'react';

import ClayButton from '@clayui/button';
import Button from './Button.es';

const InlineConfirm = props => {
	const [performingAction, setPerformingAction] = useState(false);
	const _handleConfirmButtonClick = () => {
		setPerformingAction(true);
		props.onConfirmButtonClick().then(() => setPerformingAction(false));
	};
	return (
		<div
			className="inline-confirm"
			ref={node => node && node.querySelector('.yes').focus()}
			role="alertdialog"
		>
			<p className="text-center text-secondary">
				<strong>{props.message}</strong>
			</p>
			<ClayButton.Group spaced>
				<Button
					className="yes"
					displayType="primary"
					loading={performingAction}
					onClick={_handleConfirmButtonClick}
					small
				>
					{props.confirmButtonLabel}
				</Button>

				<Button
					disabled={performingAction}
					displayType="secondary"
					onClick={props.onCancelButtonClick}
					small
					type="button"
				>
					{props.cancelButtonLabel}
				</Button>
			</ClayButton.Group>
		</div>
	);
};

InlineConfirm.propTypes = {
	cancelButtonLabel: PropTypes.string,
	confirmButtonLabel: PropTypes.string,

	message: PropTypes.string,

	onCancelButtonClick: PropTypes.func,
	onConfirmButtonClick: PropTypes.func
};

export {InlineConfirm};
export default InlineConfirm;
