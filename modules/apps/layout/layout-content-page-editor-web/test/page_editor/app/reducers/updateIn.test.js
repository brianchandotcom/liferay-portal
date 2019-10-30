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

import updateIn from '../../../../src/main/resources/META-INF/resources/page_editor/app/reducers/updateIn';

describe('updateIn', () => {
	let data;

	beforeEach(() => {
		data = {
			arr: [
				'a',
				'b',
				{
					c: 'd'
				}
			],

			obj: {}
		};
	});

	it('calls the given updater inside the given keyPath', () => {
		const updater = jest.fn();

		updateIn(data, ['arr', 2, 'c'], updater);

		expect(updater).toHaveBeenCalledWith('d');
	});

	it('sets defaultValue if path does not exist', () => {
		const updater = jest.fn();

		updateIn(data, ['obj', 'nope'], updater, {fun: true});

		expect(updater).toHaveBeenCalledWith({fun: true});
	});

	it("creates objects in path if they don't exist", () => {
		const nextData = updateIn(
			data,
			['obj', 'newProp', 'another'],
			() => 'new-val'
		);

		expect(nextData.obj).toEqual({
			newProp: {
				another: 'new-val'
			}
		});
	});

	it('changes exising properties', () => {
		const nextData = updateIn(data, ['arr'], () => []);

		expect(nextData).toEqual({arr: [], obj: {}});
	});
});
