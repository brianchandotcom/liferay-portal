/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {fireEvent, render, screen, waitFor} from '@testing-library/react';
import React from 'react';

import ReportFeedbackModal from '../../../src/main/resources/META-INF/resources/js/ReportFeedback/ReportFeedbackModal';
import {postAIIssueReport} from '../../../src/main/resources/META-INF/resources/js/ReportFeedback/api';

jest.mock(
	'../../../src/main/resources/META-INF/resources/js/ReportFeedback/api'
);

const mockPostAIIssueReport = postAIIssueReport as jest.MockedFunction<
	typeof postAIIssueReport
>;

const openToast = jest.fn();

function renderModal(
	props: Partial<{
		onClose: () => void;
		onSubmitted: () => void;
	}> = {}
) {
	return render(
		<ReportFeedbackModal
			agentId="agent-1"
			onClose={jest.fn()}
			surface="AI_ASSISTANT"
			traceId="trace-1"
			{...props}
		/>
	);
}

describe('ReportFeedbackModal', () => {
	beforeEach(() => {
		mockPostAIIssueReport.mockReset();
		openToast.mockReset();

		global.Liferay = {
			...global.Liferay,
			Util: {
				...global.Liferay?.Util,
				openToast,
			},
		};
	});

	it('renders the title and every reason option', async () => {
		renderModal();

		expect(await screen.findByText('send-feedback')).toBeInTheDocument();
		expect(
			screen.getByText('incorrect-or-inaccurate-response')
		).toBeInTheDocument();
		expect(
			screen.getByText('exposure-of-personal-sensitive-data-pii')
		).toBeInTheDocument();
		expect(
			screen.getByText('agent-error-or-malfunction')
		).toBeInTheDocument();
	});

	it('keeps Send disabled until a reason is chosen', async () => {
		renderModal();

		const send = await screen.findByRole('button', {name: 'send'});

		expect(send).toBeDisabled();

		fireEvent.change(screen.getByRole('combobox'), {
			target: {value: 'OTHER'},
		});

		expect(send).toBeEnabled();
	});

	it('submits the report, fires a success toast, and closes', async () => {
		mockPostAIIssueReport.mockResolvedValue({id: 'report-1'});

		const onClose = jest.fn();
		const onSubmitted = jest.fn();

		renderModal({onClose, onSubmitted});

		fireEvent.change(await screen.findByRole('combobox'), {
			target: {value: 'INCORRECT_OR_INACCURATE_RESPONSE'},
		});
		fireEvent.click(screen.getByRole('button', {name: 'send'}));

		await waitFor(() => expect(onSubmitted).toHaveBeenCalledTimes(1));

		expect(mockPostAIIssueReport).toHaveBeenCalledWith(
			expect.objectContaining({
				agentId: 'agent-1',
				reason: 'INCORRECT_OR_INACCURATE_RESPONSE',
				surface: 'AI_ASSISTANT',
				traceId: 'trace-1',
			})
		);
		expect(openToast).toHaveBeenCalledWith({
			message: 'thanks-for-your-feedback',
			type: 'success',
		});
	});

	it('shows an inline error and stays open on failure', async () => {
		mockPostAIIssueReport.mockRejectedValue(new Error('submit failed'));

		const onSubmitted = jest.fn();

		renderModal({onSubmitted});

		fireEvent.change(await screen.findByRole('combobox'), {
			target: {value: 'OTHER'},
		});
		fireEvent.click(screen.getByRole('button', {name: 'send'}));

		expect(await screen.findByText('submit failed')).toBeInTheDocument();
		expect(onSubmitted).not.toHaveBeenCalled();
		expect(openToast).not.toHaveBeenCalled();
	});
});
