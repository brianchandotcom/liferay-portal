/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, renderHook, waitFor} from '@testing-library/react';

import {useRelationshipPicker} from '../../../../src/main/resources/META-INF/resources/js/agent_definition_form/hooks/useRelationshipPicker';

interface TestItem {
	externalReferenceCode: string;
	title?: string;
}

function setup({items = [] as TestItem[]} = {}) {
	const deleteRelationship = jest.fn().mockResolvedValue(undefined);
	const fetchSourceList = jest.fn().mockResolvedValue({items});
	const putRelationship = jest.fn().mockResolvedValue(undefined);

	const renderResult = renderHook(() =>
		useRelationshipPicker<TestItem>({
			deleteRelationship,
			fetchSourceList,
			putRelationship,
		})
	);

	return {
		deleteRelationship,
		fetchSourceList,
		putRelationship,
		...renderResult,
	};
}

describe('useRelationshipPicker', () => {
	describe('initial state', () => {
		it('loads the source list from the fetcher on mount', async () => {
			const {fetchSourceList, result} = setup({
				items: [
					{externalReferenceCode: 'A', title: 'Alpha'},
					{externalReferenceCode: 'B', title: 'Beta'},
				],
			});

			await waitFor(() =>
				expect(result.current.sourceList).toHaveLength(2)
			);

			expect(fetchSourceList).toHaveBeenCalledTimes(1);
		});

		it('starts with empty selected and inputValue', () => {
			const {result} = setup();

			expect(result.current.selected).toEqual([]);
			expect(result.current.inputValue).toBe('');
		});
	});

	describe('reset', () => {
		it('replaces selected with the passed-in initial list', () => {
			const {result} = setup();

			act(() => {
				result.current.reset([
					{externalReferenceCode: 'A'},
					{externalReferenceCode: 'B'},
				]);
			});

			expect(result.current.selected).toEqual([
				{externalReferenceCode: 'A'},
				{externalReferenceCode: 'B'},
			]);
		});
	});

	describe('sync', () => {
		it('deletes only the removals relative to the baseline', async () => {
			const {deleteRelationship, putRelationship, result} = setup();

			act(() => {
				result.current.reset([
					{externalReferenceCode: 'A'},
					{externalReferenceCode: 'B'},
				]);
			});

			act(() => {
				result.current.setSelected([{externalReferenceCode: 'A'}]);
			});

			await act(async () => {
				await result.current.sync('AGENT');
			});

			expect(deleteRelationship).toHaveBeenCalledTimes(1);
			expect(deleteRelationship).toHaveBeenCalledWith('AGENT', 'B');
			expect(putRelationship).not.toHaveBeenCalled();
		});

		it('issues both additions and removals when selection diverges', async () => {
			const {deleteRelationship, putRelationship, result} = setup();

			act(() => {
				result.current.reset([
					{externalReferenceCode: 'A'},
					{externalReferenceCode: 'B'},
				]);
			});

			act(() => {
				result.current.setSelected([
					{externalReferenceCode: 'B'},
					{externalReferenceCode: 'C'},
				]);
			});

			await act(async () => {
				await result.current.sync('AGENT');
			});

			expect(putRelationship).toHaveBeenCalledWith('AGENT', 'C');
			expect(deleteRelationship).toHaveBeenCalledWith('AGENT', 'A');
		});

		it('issues no requests when the selection matches the baseline', async () => {
			const {deleteRelationship, putRelationship, result} = setup();

			act(() => {
				result.current.reset([{externalReferenceCode: 'A'}]);
			});

			await act(async () => {
				await result.current.sync('AGENT');
			});

			expect(putRelationship).not.toHaveBeenCalled();
			expect(deleteRelationship).not.toHaveBeenCalled();
		});

		it('promotes the selection to the baseline so a second sync is a no-op', async () => {
			const {putRelationship, result} = setup();

			act(() => {
				result.current.reset([{externalReferenceCode: 'A'}]);
			});

			act(() => {
				result.current.setSelected([
					{externalReferenceCode: 'A'},
					{externalReferenceCode: 'B'},
				]);
			});

			await act(async () => {
				await result.current.sync('AGENT');
			});

			await act(async () => {
				await result.current.sync('AGENT');
			});

			expect(putRelationship).toHaveBeenCalledTimes(1);
		});

		it('puts only the additions relative to the baseline', async () => {
			const {deleteRelationship, putRelationship, result} = setup();

			act(() => {
				result.current.reset([{externalReferenceCode: 'A'}]);
			});

			act(() => {
				result.current.setSelected([
					{externalReferenceCode: 'A'},
					{externalReferenceCode: 'B'},
				]);
			});

			await act(async () => {
				await result.current.sync('AGENT');
			});

			expect(putRelationship).toHaveBeenCalledTimes(1);
			expect(putRelationship).toHaveBeenCalledWith('AGENT', 'B');
			expect(deleteRelationship).not.toHaveBeenCalled();
		});
	});

	describe('setInputValue', () => {
		it('stores the typed value verbatim', () => {
			const {result} = setup();

			act(() => {
				result.current.setInputValue('alpha');
			});

			expect(result.current.inputValue).toBe('alpha');
		});
	});
});
