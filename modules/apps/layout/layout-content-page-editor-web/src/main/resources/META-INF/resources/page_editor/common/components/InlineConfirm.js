/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {useEffect, useRef, useState} from 'react';

import Button from './Button';

export default function InlineConfirm({
	cancelButtonLabel,
	confirmButtonLabel,
	message,
	onCancelButtonClick,
	onConfirmButtonClick,
}) {
	const [performingAction, setPerformingAction] = useState(false);
	const wrapperRef = useRef(null);
	const isMounted = useIsMounted();

	const _handleConfirmButtonClick = () => {
		if (wrapperRef.current) {
			wrapperRef.current.focus();
		}

		setPerformingAction(true);

		onConfirmButtonClick().then(() => {
			if (isMounted()) {
				setPerformingAction(false);
			}
		});
	};

	useEffect(() => {
		if (wrapperRef.current) {
			wrapperRef.current.focus();
		}
	}, []);

	useEffect(() => {
		if (wrapperRef.current) {
			const confirmButton = wrapperRef.current.querySelector(
				'page-editor__inline-confirm-button'
			);

			if (confirmButton) {
				confirmButton.focus();
			}
		}

		const _handleDocumentFocusOut = () => {
			requestAnimationFrame(() => {
				if (wrapperRef.current && !performingAction) {
					if (
						!wrapperRef.current.contains(document.activeElement) &&
						wrapperRef.current !== document.activeElement
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
			className="page-editor__inline-confirm"
			onKeyDown={(event) =>
				event.key === 'Escape' && onCancelButtonClick()
			}
			ref={wrapperRef}
			role="alertdialog"
			tabIndex="-1"
		>
			<p className="text-center text-secondary">
				<strong>{message}</strong>
			</p>

			<ClayButton.Group spaced>
				<Button
					className="page-editor__inline-confirm-button"
					disabled={performingAction}
					displayType="primary"
					loading={performingAction}
					onClick={_handleConfirmButtonClick}
					size="sm"
				>
					{confirmButtonLabel}
				</Button>

				<Button
					disabled={performingAction}
					displayType="secondary"
					onClick={onCancelButtonClick}
					size="sm"
					type="button"
				>
					{cancelButtonLabel}
				</Button>
			</ClayButton.Group>
		</div>
	);
}

InlineConfirm.propTypes = {
	cancelButtonLabel: PropTypes.string,
	confirmButtonLabel: PropTypes.string,
	message: PropTypes.string,
	onCancelButtonClick: PropTypes.func,
	onConfirmButtonClick: PropTypes.func,
};
