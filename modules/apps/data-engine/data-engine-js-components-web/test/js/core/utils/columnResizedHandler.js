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

import {handleResizeRight} from '../../../../src/main/resources/META-INF/resources/js/core/utils/columnResizedHandler';

const props = {};

describe('core/utils/columnResizedHandler', () => {
	describe('handleResizeRight(props, state, index, columnTarget)', () => {
		it('resizes first field to the left', () => {
			const indexes = [
				{
					columnIndex: 0,
					pageIndex: 0,
					rowIndex: 0,
				},
			];
			const state = {
				pages: [
					{
						rows: [
							{
								columns: [
									{
										fields: [{}],
										size: 4,
									},
									{
										fields: [{}],
										size: 8,
									},
								],
							},
						],
					},
				],
			};

			const result = handleResizeRight(props, state, indexes, 2);

			expect(result[0].rows[0].columns[0].size).toEqual(2);
			expect(result[0].rows[0].columns[1].size).toEqual(10);
		});

		it('resizes first field to the right', () => {
			const indexes = [
				{
					columnIndex: 0,
					pageIndex: 0,
					rowIndex: 0,
				},
			];
			const state = {
				pages: [
					{
						rows: [
							{
								columns: [
									{
										fields: [{}],
										size: 4,
									},
									{
										fields: [{}],
										size: 8,
									},
								],
							},
						],
					},
				],
			};

			const result = handleResizeRight(props, state, indexes, 6);

			expect(result[0].rows[0].columns[0].size).toEqual(6);
			expect(result[0].rows[0].columns[1].size).toEqual(6);
		});

		it('resizes second field to the left', () => {
			const indexes = [
				{
					columnIndex: 1,
					pageIndex: 0,
					rowIndex: 0,
				},
			];
			const state = {
				pages: [
					{
						rows: [
							{
								columns: [
									{
										fields: [{}],
										size: 4,
									},
									{
										fields: [{}],
										size: 8,
									},
								],
							},
						],
					},
				],
			};

			const result = handleResizeRight(props, state, indexes, 6);

			expect(result[0].rows[0].columns[0].size).toEqual(4);
			expect(result[0].rows[0].columns[1].size).toEqual(2);
			expect(result[0].rows[0].columns.length).toEqual(3);
		});

		it('resizes second field to the right', () => {
			const indexes = [
				{
					columnIndex: 1,
					pageIndex: 0,
					rowIndex: 0,
				},
			];
			const state = {
				pages: [
					{
						rows: [
							{
								columns: [
									{
										fields: [{}],
										size: 4,
									},
									{
										fields: [{}],
										size: 4,
									},
									{
										fields: [{}],
										size: 4,
									},
								],
							},
						],
					},
				],
			};

			const result = handleResizeRight(props, state, indexes, 11);

			expect(result[0].rows[0].columns[0].size).toEqual(4);
			expect(result[0].rows[0].columns[1].size).toEqual(7);
			expect(result[0].rows[0].columns[2].size).toEqual(1);
		});
	});
});
