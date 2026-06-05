/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect, useState} from 'react';

import FeedbackActionsRow from '../../ReportFeedback/FeedbackActionsRow';
import ConfirmationBalloon from './ConfirmationBalloon';

export default function WritingAssistantConfirmationAction({
	containerRef,
	handleAccept,
	handleDiscard,
	hideBalloon,
	onReport,
	onThumbsUp,
}: {
	containerRef: HTMLElement;
	handleAccept: () => void;
	handleDiscard: () => void;
	hideBalloon: () => void;
	onReport?: () => void;
	onThumbsUp?: () => void;
}) {
	const [active, setActive] = useState(true);
	const [feedbackGiven, setFeedbackGiven] = useState(false);

	const actions = [
		{
			disabled: false,
			name: Liferay.Language.get('accept'),
			onClick: () => {
				handleAccept();
				setActive(false);
				hideBalloon();
			},
			symbolLeft: 'check',
		},
		{
			disabled: false,
			name: Liferay.Language.get('discard'),
			onClick: () => {
				handleDiscard();
				setActive(false);
				hideBalloon();
			},
			symbolLeft: 'times',
		},
	];

	useEffect(() => {
		function handleDocumentClick(event: MouseEvent) {
			if (
				active &&
				containerRef &&
				!containerRef.contains(event.target as Node)
			) {
				setActive(false);
				hideBalloon();
			}
		}
		document.addEventListener('mousedown', handleDocumentClick);

		return () => {
			document.removeEventListener('mousedown', handleDocumentClick);
		};
	}, [active, containerRef, hideBalloon]);

	if (!active) {
		return null;
	}

	return (
		<ConfirmationBalloon
			actions={actions}
			actionsRow={
				<FeedbackActionsRow
					feedbackGiven={feedbackGiven}
					onReport={() => {
						if (onReport) {
							setActive(false);
							hideBalloon();
							onReport();
						}
					}}
					onThumbsUp={
						onThumbsUp
							? () => {
									setFeedbackGiven(true);

									onThumbsUp();
								}
							: undefined
					}
				/>
			}
		/>
	);
}
