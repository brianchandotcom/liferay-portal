/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {fireEvent, render, screen, waitFor} from '@testing-library/react';
import {openToast} from 'frontend-js-components-web';
import React from 'react';

import AutoFixPanel from '../../../../js/components/insights_view/AutoFixPanel';
import {TITLE_AUTO_FIX_DEFINITION} from '../../../../js/components/insights_view/auto_fix_definitions/TitleAutoFixDefinition';
import {
	applyFix,
	generateCandidates,
	resolveInsight,
} from '../../../../js/components/insights_view/services/AutoFixService';
import {ScanInsightItem} from '../../../../js/components/insights_view/types/AutoFix';

jest.mock('frontend-js-components-web', () => ({
	openToast: jest.fn(),
}));

jest.mock('../../../../js/components/insights_view/services/AutoFixService', () => ({
	applyFix: jest.fn(),
	generateCandidates: jest.fn(),
	resolveInsight: jest.fn(),
}));

const item: ScanInsightItem = {
	id: 123,
	r_seoStudioPageToSEOStudioScanInsights_seoStudioPage: {
		pageURL: 'http://example.com/web/customer/products',
		title: 'Products',
		type: 'Web Content',
	},
	state: 1,
};

function renderPanel(
	props: Partial<React.ComponentProps<typeof AutoFixPanel>> = {}
) {
	return render(
		<AutoFixPanel
			insightTypeName={TITLE_AUTO_FIX_DEFINITION.insightTypeName}
			item={item}
			onClose={jest.fn()}
			onResolved={jest.fn()}
			{...props}
		/>
	);
}

describe('AutoFixPanel', () => {
	beforeEach(() => {
		jest.clearAllMocks();
	});

	it('shows the generated title options with an apply action each', async () => {
		(generateCandidates as jest.Mock).mockResolvedValue([
			{rationale: 'r1', value: 'Option A'},
			{rationale: 'r2', value: 'Option B'},
		]);

		renderPanel();

		expect(await screen.findByText('Option A')).toBeInTheDocument();
		expect(screen.getByText('Option B')).toBeInTheDocument();
		expect(
			screen.getAllByRole('button', {name: /apply-option-x/})
		).toHaveLength(2);
	});

	it('applies the chosen option then resolves the insight', async () => {
		(generateCandidates as jest.Mock).mockResolvedValue([
			{rationale: 'r1', value: 'Option A'},
		]);
		(applyFix as jest.Mock).mockResolvedValue(undefined);
		(resolveInsight as jest.Mock).mockResolvedValue(undefined);

		const onClose = jest.fn();
		const onResolved = jest.fn();

		renderPanel({onClose, onResolved});

		fireEvent.click(
			await screen.findByRole('button', {name: /apply-option-x/})
		);

		await waitFor(() => expect(resolveInsight).toHaveBeenCalledWith(123));

		expect(applyFix).toHaveBeenCalledWith({
			insightType: TITLE_AUTO_FIX_DEFINITION.insightTypeName,
			pageURL: 'http://example.com/web/customer/products',
			value: 'Option A',
		});
		expect(
			(applyFix as jest.Mock).mock.invocationCallOrder[0]
		).toBeLessThan(
			(resolveInsight as jest.Mock).mock.invocationCallOrder[0]
		);
		expect(onResolved).toHaveBeenCalled();
		expect(onClose).toHaveBeenCalled();
	});

	it('shows an empty message when no candidates are generated', async () => {
		(generateCandidates as jest.Mock).mockResolvedValue([]);

		renderPanel();

		expect(
			await screen.findByText('no-suggestions-were-generated')
		).toBeInTheDocument();
	});

	it('shows the loading state while candidates are generating', () => {
		(generateCandidates as jest.Mock).mockReturnValue(
			new Promise(() => {})
		);

		renderPanel();

		expect(screen.getByText('generating')).toBeInTheDocument();
	});

	it('cancels the in flight generation when the item changes', () => {
		let capturedSignal: AbortSignal | undefined;

		(generateCandidates as jest.Mock).mockImplementation(
			(
				_definition: unknown,
				_pageContent: string,
				signal: AbortSignal
			) => {
				capturedSignal = signal;

				return new Promise(() => {});
			}
		);

		const {rerender} = renderPanel();

		const firstSignal = capturedSignal;

		expect(firstSignal?.aborted).toBe(false);

		rerender(
			<AutoFixPanel
				insightTypeName={TITLE_AUTO_FIX_DEFINITION.insightTypeName}
				item={{...item, id: 456}}
				onClose={jest.fn()}
				onResolved={jest.fn()}
			/>
		);

		expect(firstSignal?.aborted).toBe(true);
	});

	it('shows a distinct message when the title applies but resolving fails', async () => {
		(generateCandidates as jest.Mock).mockResolvedValue([
			{rationale: 'r1', value: 'Option A'},
		]);
		(applyFix as jest.Mock).mockResolvedValue(undefined);
		(resolveInsight as jest.Mock).mockRejectedValue(new Error('nope'));

		const onClose = jest.fn();
		const onResolved = jest.fn();

		renderPanel({onClose, onResolved});

		fireEvent.click(
			await screen.findByRole('button', {name: /apply-option-x/})
		);

		await waitFor(() =>
			expect(openToast).toHaveBeenCalledWith(
				expect.objectContaining({
					message:
						'the-title-tag-was-applied-but-the-insight-could-not-be-marked-as-resolved',
					type: 'danger',
				})
			)
		);

		expect(onResolved).not.toHaveBeenCalled();
		expect(onClose).not.toHaveBeenCalled();
	});

	it('shows an error and keeps the insight open when applying fails', async () => {
		(generateCandidates as jest.Mock).mockResolvedValue([
			{rationale: 'r1', value: 'Option A'},
		]);
		(applyFix as jest.Mock).mockRejectedValue(new Error('nope'));

		const onClose = jest.fn();
		const onResolved = jest.fn();

		renderPanel({onClose, onResolved});

		fireEvent.click(
			await screen.findByRole('button', {name: /apply-option-x/})
		);

		await waitFor(() =>
			expect(openToast).toHaveBeenCalledWith(
				expect.objectContaining({type: 'danger'})
			)
		);

		expect(resolveInsight).not.toHaveBeenCalled();
		expect(onResolved).not.toHaveBeenCalled();
		expect(onClose).not.toHaveBeenCalled();
	});
});
