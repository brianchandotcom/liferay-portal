/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {transformAdditionalAPIURLParameters} from '../../src/main/resources/META-INF/resources/utils/transformAdditionalAPIURLParameters';
import {ILoadDataArgs} from '../../src/main/resources/META-INF/resources/utils/types';

const baseLoadDataArgs: ILoadDataArgs = {
	additionalAPIURLParameters: 'nestedFields=skus',
	apiURL: '/o/products',
	currentURL: '/sample-portlet-page-url',
	delta: 20,
	odataFiltersStrings: ['catalogId eq 32642'],
	page: 1,
	searchParam: 'test',
	sorts: [
		{
			active: true,
			direction: 'desc',
			key: 'modifiedDate',
		},
	],
};

describe('transformAdditionalAPIURLParameters', () => {
	it('is defined', () => {
		expect(transformAdditionalAPIURLParameters).toBeDefined();
	});

	it('returns the original additionalAPIURLParameters when no transformer is provided', () => {
		const result = transformAdditionalAPIURLParameters(baseLoadDataArgs);

		expect(result).toBe('nestedFields=skus');
	});

	it('returns undefined when no transformer is provided and additionalAPIURLParameters is undefined', () => {
		const args = {
			...baseLoadDataArgs,
			additionalAPIURLParameters: undefined,
		};

		const result = transformAdditionalAPIURLParameters(args);

		expect(result).toBeUndefined();
	});

	it('returns the value from the transformer when one is provided', () => {
		const transformer = (args: ILoadDataArgs) => {
			return `${args.additionalAPIURLParameters}&extraField=bar`;
		};

		const result = transformAdditionalAPIURLParameters(
			baseLoadDataArgs,
			transformer
		);

		expect(result).toBe('nestedFields=skus&extraField=bar');
	});

	it('passes all loadDataParams to the transformer', () => {
		const transformer = jest.fn(
			(args: ILoadDataArgs) => args.additionalAPIURLParameters
		);

		transformAdditionalAPIURLParameters(baseLoadDataArgs, transformer);

		expect(transformer).toHaveBeenCalledTimes(1);

		const receivedParams = transformer.mock.calls[0][0];

		expect(receivedParams.additionalAPIURLParameters).toBe(
			'nestedFields=skus'
		);
		expect(receivedParams.apiURL).toBe('/o/products');
		expect(receivedParams.currentURL).toBe('/sample-portlet-page-url');
		expect(receivedParams.delta).toBe(20);
		expect(receivedParams.odataFiltersStrings).toEqual([
			'catalogId eq 32642',
		]);
		expect(receivedParams.page).toBe(1);
		expect(receivedParams.searchParam).toBe('test');
		expect(receivedParams.sorts).toEqual([
			{
				active: true,
				direction: 'desc',
				key: 'modifiedDate',
			},
		]);
	});

	it('allows the transformer to conditionally modify additionalAPIURLParameters based on other params', () => {
		const transformer = (args: ILoadDataArgs) => {
			if (args.searchParam) {
				return `${args.additionalAPIURLParameters}&restrictFields=name`;
			}

			return args.additionalAPIURLParameters;
		};

		const resultWithSearch = transformAdditionalAPIURLParameters(
			baseLoadDataArgs,
			transformer
		);

		expect(resultWithSearch).toBe('nestedFields=skus&restrictFields=name');

		const resultWithoutSearch = transformAdditionalAPIURLParameters(
			{...baseLoadDataArgs, searchParam: undefined},
			transformer
		);

		expect(resultWithoutSearch).toBe('nestedFields=skus');
	});

	it('prevents the transformer from mutating the original params', () => {
		const transformer = (args: ILoadDataArgs) => {
			args.sorts!.push({
				active: true,
				direction: 'asc',
				key: 'name',
			});

			args.additionalAPIURLParameters = 'mutated=true';

			return 'nestedFields=skus&extra=value';
		};

		transformAdditionalAPIURLParameters(baseLoadDataArgs, transformer);

		expect(baseLoadDataArgs.additionalAPIURLParameters).toBe(
			'nestedFields=skus'
		);
		expect(baseLoadDataArgs.sorts).toEqual([
			{
				active: true,
				direction: 'desc',
				key: 'modifiedDate',
			},
		]);
	});

	it('allows the transformer to return undefined', () => {
		const transformer = () => undefined;

		const result = transformAdditionalAPIURLParameters(
			baseLoadDataArgs,
			transformer
		);

		expect(result).toBeUndefined();
	});
});
