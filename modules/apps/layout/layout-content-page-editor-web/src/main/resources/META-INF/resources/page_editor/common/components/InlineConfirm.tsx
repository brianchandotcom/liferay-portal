/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import React, {useEffect, useRef, useState} from 'react';

import Button from './Button';

type Props = {
	cancelButtonLabel: string;
	confirmButtonLabel: string;
	message: string;
	onCancelButtonClick: () => void;
	onConfirmButtonClick: () => Promise<void>;
};

export default function InlineConfirm({
	cancelButtonLabel,
	confirmButtonLabel,
	message,
	onCancelButtonClick,
	onConfirmButtonClick,
}: Props) {
	const [performingAction, setPerformingAction] = useState(false);
	const wrapperRef = useRef<HTMLDivElement | null>(null);
	const isMounted = useIsMounted();

	const handleConfirmButtonClick = () => {
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

			if (confirmButton instanceof HTMLElement) {
				confirmButton.focus();
			}
		}

		const handleDocumentFocusOut = () => {
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

		document.addEventListener('focusout', handleDocumentFocusOut, true);

		return () =>
			window.removeEventListener(
				'focusout',
				handleDocumentFocusOut,
				true
			);
	}, [performingAction, onCancelButtonClick]);

	return (
		<div
			className="page-editor__inline-confirm"
			onKeyDown={(event: React.KeyboardEvent<HTMLDivElement>) =>
				event.key === 'Escape' && onCancelButtonClick()
			}
			ref={wrapperRef}
			role="alertdialog"
			tabIndex={-1}
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
					onClick={handleConfirmButtonClick}
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
