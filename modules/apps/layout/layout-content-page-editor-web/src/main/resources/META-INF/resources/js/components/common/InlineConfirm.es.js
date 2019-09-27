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
import {useIsMounted} from 'frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {useState, useRef, useEffect} from 'react';

import Button from './Button.es';

const CONFIRM_BUTTON_CLASS = 'fragments-editor__inline-confirm-button';

const InlineConfirm = props => {
	const {onCancelButtonClick, onConfirmButtonClick} = props;
	const [performingAction, setPerformingAction] = useState(false);
	const wrapper = useRef(null);
	const isMounted = useIsMounted();

	const _handleConfirmButtonClick = () => {
		if (wrapper.current) {
			wrapper.current.focus();
		}

		setPerformingAction(true);

		onConfirmButtonClick().then(() => {
			if (isMounted()) {
				setPerformingAction(false);
			}
		});
	};

	useEffect(() => {
		if (wrapper.current) {
			const confirmButton = wrapper.current.querySelector(
				`.${CONFIRM_BUTTON_CLASS}`
			);

			if (confirmButton) {
				confirmButton.focus();
			}
		}

		const _handleDocumentFocusOut = () => {
			requestAnimationFrame(() => {
				if (wrapper.current && !performingAction) {
					if (
						!wrapper.current.contains(document.activeElement) &&
						wrapper.current !== document.activeElement
					) {
						onCancelButtonClick();
					}
				}
			});
		};

		document.addEventListener('focusout', _handleDocumentFocusOut, true);

		return () =>
			window.removeEventListener(
				'focusout',
				_handleDocumentFocusOut,
				true
			);
	}, [performingAction, onCancelButtonClick]);

	return (
		<div
			className="fragments-editor__inline-confirm"
			onKeyDown={e => e.key === 'Escape' && onCancelButtonClick()}
			ref={wrapper}
			role="alertdialog"
			tabIndex="-1"
		>
			<p className="text-center text-secondary">
				<strong>{props.message}</strong>
			</p>

			<ClayButton.Group spaced>
				<Button
					className={CONFIRM_BUTTON_CLASS}
					disabled={performingAction}
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
					onClick={onCancelButtonClick}
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
