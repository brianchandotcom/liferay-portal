/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm, {ClayInput, ClaySelectWithOption} from '@clayui/form';
import ClayModal, {useModal} from '@clayui/modal';
import {RequiredMask} from 'frontend-js-components-web';
import React, {useState} from 'react';

import {
	ReportFeedbackPayload,
	ReportFeedbackReason,
	ReportFeedbackSurface,
	postAIIssueReport,
} from './api';

interface ReportFeedbackModalProps {
	agentId: string;
	agentVersion?: string | null;
	onClose: () => void;
	onSubmitted?: () => void;
	surface: ReportFeedbackSurface;
	traceId: string;
}

const REASON_OPTIONS: {label: string; value: ReportFeedbackReason | ''}[] = [
	{label: Liferay.Language.get('select-reason'), value: ''},
	{
		label: Liferay.Language.get('incorrect-or-inaccurate-response'),
		value: 'INCORRECT_OR_INACCURATE_RESPONSE',
	},
	{
		label: Liferay.Language.get('inappropriate-or-harmful-content'),
		value: 'INAPPROPRIATE_OR_HARMFUL_CONTENT',
	},
	{
		label: Liferay.Language.get('exposure-of-personal-sensitive-data-pii'),
		value: 'PII_EXPOSURE',
	},
	{
		label: Liferay.Language.get('agent-error-or-malfunction'),
		value: 'AGENT_ERROR_OR_MALFUNCTION',
	},
	{
		label: Liferay.Language.get('other'),
		value: 'OTHER',
	},
];

const ReportFeedbackModal: React.FC<ReportFeedbackModalProps> = ({
	agentId,
	agentVersion,
	onClose,
	onSubmitted,
	surface,
	traceId,
}) => {
	const [reason, setReason] = useState<ReportFeedbackReason | ''>('');
	const [comment, setComment] = useState<string>('');
	const [submitting, setSubmitting] = useState<boolean>(false);
	const [error, setError] = useState<string>('');

	const {observer, onClose: closeModal} = useModal({onClose});

	const canSubmit = Boolean(reason) && !submitting;

	async function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
		event.preventDefault();

		if (!reason) {
			return;
		}

		const payload: ReportFeedbackPayload = {
			agentId,
			agentVersion,
			reason,
			surface,
			traceId,
			...(comment.trim() ? {comment: comment.trim()} : {}),
		};

		setError('');
		setSubmitting(true);

		try {
			await postAIIssueReport(payload);

			Liferay.Util.openToast({
				message: Liferay.Language.get(
					'feedback-sent-thank-you-for-helping-us-improve'
				),
				type: 'success',
			});

			onSubmitted?.();
			closeModal();
		}
		catch (caught) {
			setError(
				(caught as Error).message ||
					Liferay.Language.get(
						'unable-to-send-feedback-please-try-again'
					)
			);
			setSubmitting(false);
		}
	}

	return (
		<ClayModal observer={observer}>
			<ClayForm onSubmit={handleSubmit}>
				<ClayModal.Header
					closeButtonAriaLabel={Liferay.Language.get('close')}
				>
					{Liferay.Language.get('send-feedback')}
				</ClayModal.Header>

				<ClayModal.Body>
					{error && (
						<ClayAlert displayType="danger">{error}</ClayAlert>
					)}

					<ClayForm.Group>
						<label htmlFor="reportFeedbackReason">
							{Liferay.Language.get('reason')}

							<RequiredMask />
						</label>

						<ClaySelectWithOption
							aria-required="true"
							disabled={submitting}
							id="reportFeedbackReason"
							onChange={(event) =>
								setReason(
									event.target.value as ReportFeedbackReason
								)
							}
							options={REASON_OPTIONS}
							required
							value={reason}
						/>
					</ClayForm.Group>

					<ClayForm.Group>
						<label htmlFor="reportFeedbackComment">
							{Liferay.Language.get('comment-optional')}
						</label>

						<ClayInput
							component="textarea"
							disabled={submitting}
							id="reportFeedbackComment"
							onChange={(event) => setComment(event.target.value)}
							style={{minHeight: '5rem'}}
							value={comment}
						/>
					</ClayForm.Group>
				</ClayModal.Body>

				<ClayModal.Footer
					last={
						<ClayButton.Group spaced>
							<ClayButton
								disabled={submitting}
								displayType="secondary"
								onClick={() => closeModal()}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton
								disabled={!canSubmit}
								displayType="primary"
								type="submit"
							>
								{Liferay.Language.get('send')}
							</ClayButton>
						</ClayButton.Group>
					}
				/>
			</ClayForm>
		</ClayModal>
	);
};

export default ReportFeedbackModal;
