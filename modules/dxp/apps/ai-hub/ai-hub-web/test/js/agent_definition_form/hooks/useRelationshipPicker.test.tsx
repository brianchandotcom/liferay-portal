/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, renderHook} from '@testing-library/react';

import {useRelationshipPicker} from '../../../../src/main/resources/META-INF/resources/js/agent_definition_form/hooks/useRelationshipPicker';

interface TestItem {
	externalReferenceCode: string;
	title?: string;
}

describe('useRelationshipPicker', () => {
	describe('initial state', () => {
		it('starts with empty selected, sourceList, and inputValue', () => {
			const {result} = renderHook(() =>
				useRelationshipPicker<TestItem>()
			);

			expect(result.current.selected).toEqual([]);
			expect(result.current.sourceList).toEqual([]);
			expect(result.current.inputValue).toBe('');
		});
	});

	describe('reset', () => {
		it('replaces selected with the passed-in initial list', () => {
			const {result} = renderHook(() =>
				useRelationshipPicker<TestItem>()
			);

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

		it('seeds the baseline so diff is empty immediately after reset', () => {
			const {result} = renderHook(() =>
				useRelationshipPicker<TestItem>()
			);

			act(() => {
				result.current.reset([
					{externalReferenceCode: 'A'},
					{externalReferenceCode: 'B'},
				]);
			});

			expect(result.current.diff()).toEqual({toAdd: [], toRemove: []});
		});
	});

	describe('diff', () => {
		it('returns selected as toAdd when there is no baseline', () => {
			const {result} = renderHook(() =>
				useRelationshipPicker<TestItem>()
			);

			act(() => {
				result.current.setSelected([
					{externalReferenceCode: 'A'},
					{externalReferenceCode: 'B'},
				]);
			});

			expect(result.current.diff()).toEqual({
				toAdd: [
					{externalReferenceCode: 'A'},
					{externalReferenceCode: 'B'},
				],
				toRemove: [],
			});
		});

		it('flags only the additions relative to the baseline', () => {
			const {result} = renderHook(() =>
				useRelationshipPicker<TestItem>()
			);

			act(() => {
				result.current.reset([{externalReferenceCode: 'A'}]);
			});

			act(() => {
				result.current.setSelected([
					{externalReferenceCode: 'A'},
					{externalReferenceCode: 'B'},
				]);
			});

			expect(result.current.diff()).toEqual({
				toAdd: [{externalReferenceCode: 'B'}],
				toRemove: [],
			});
		});

		it('flags only the removals relative to the baseline', () => {
			const {result} = renderHook(() =>
				useRelationshipPicker<TestItem>()
			);

			act(() => {
				result.current.reset([
					{externalReferenceCode: 'A'},
					{externalReferenceCode: 'B'},
				]);
			});

			act(() => {
				result.current.setSelected([{externalReferenceCode: 'A'}]);
			});

			expect(result.current.diff()).toEqual({
				toAdd: [],
				toRemove: [{externalReferenceCode: 'B'}],
			});
		});

		it('reports both additions and removals when selection diverges from baseline', () => {
			const {result} = renderHook(() =>
				useRelationshipPicker<TestItem>()
			);

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

			expect(result.current.diff()).toEqual({
				toAdd: [{externalReferenceCode: 'C'}],
				toRemove: [{externalReferenceCode: 'A'}],
			});
		});
	});

	describe('syncToInitial', () => {
		it('promotes the current selection to be the new baseline', () => {
			const {result} = renderHook(() =>
				useRelationshipPicker<TestItem>()
			);

			act(() => {
				result.current.reset([{externalReferenceCode: 'A'}]);
			});

			act(() => {
				result.current.setSelected([
					{externalReferenceCode: 'A'},
					{externalReferenceCode: 'B'},
				]);
			});

			act(() => {
				result.current.syncToInitial();
			});

			expect(result.current.diff()).toEqual({toAdd: [], toRemove: []});
		});
	});

	describe('setSourceList', () => {
		it('updates the source list without touching selected', () => {
			const {result} = renderHook(() =>
				useRelationshipPicker<TestItem>()
			);

			act(() => {
				result.current.setSelected([{externalReferenceCode: 'A'}]);
				result.current.setSourceList([
					{externalReferenceCode: 'A', title: 'Alpha'},
					{externalReferenceCode: 'B', title: 'Beta'},
				]);
			});

			expect(result.current.sourceList).toHaveLength(2);
			expect(result.current.selected).toEqual([
				{externalReferenceCode: 'A'},
			]);
		});
	});

	describe('setInputValue', () => {
		it('stores the typed value verbatim', () => {
			const {result} = renderHook(() =>
				useRelationshipPicker<TestItem>()
			);

			act(() => {
				result.current.setInputValue('alpha');
			});

			expect(result.current.inputValue).toBe('alpha');
		});
	});
});
