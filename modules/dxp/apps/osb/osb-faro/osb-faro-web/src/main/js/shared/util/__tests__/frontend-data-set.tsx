import {columns, useSnapshots} from '../frontend-data-set';
import {render, renderHook, screen, waitFor} from '@testing-library/react';
import {Routes} from '../router';

jest.unmock('react-dom');

describe('columns.nameAndLinkRenderer', () => {
	it('should generate an href that includes the channelId path segment', () => {
		render(
			columns.nameAndLinkRenderer({
				channelId: '123',
				groupId: '23',
				itemData: {id: 'abc'},
				route: Routes.CONTACTS_ACCOUNT,
				value: 'Acme Corp'
			})
		);

		expect(screen.getByRole('link')).toHaveAttribute(
			'href',
			'/workspace/23/123/contacts/accounts/abc'
		);
	});

	it('should use value as the link text when provided', () => {
		render(
			columns.nameAndLinkRenderer({
				channelId: '123',
				groupId: '23',
				itemData: {id: 'abc'},
				route: Routes.CONTACTS_ACCOUNT,
				value: 'Acme Corp'
			})
		);

		expect(screen.getByRole('link')).toHaveTextContent('Acme Corp');
	});

	it('should fall back to itemData.id as the link text when value is empty', () => {
		render(
			columns.nameAndLinkRenderer({
				channelId: '123',
				groupId: '23',
				itemData: {id: 'abc'},
				route: Routes.CONTACTS_ACCOUNT,
				value: ''
			})
		);

		expect(screen.getByRole('link')).toHaveTextContent('abc');
	});
});

describe('useSnapshots', () => {
	const mockFetch = (items: unknown[]) => {
		const fetch = jest.fn(() =>
			Promise.resolve({json: () => Promise.resolve({items})})
		);

		Liferay.Util = {fetch} as unknown as typeof Liferay.Util;

		return fetch;
	};

	beforeEach(() => {
		Liferay.FeatureFlags['LPD-34594'] = true;
		Liferay.FeatureFlags['LPS-164563'] = true;
	});

	afterEach(() => {
		delete Liferay.FeatureFlags['LPD-34594'];
		delete Liferay.FeatureFlags['LPS-164563'];
	});

	it('should return null while the snapshots are still loading', () => {
		mockFetch([]);

		const {result} = renderHook(() =>
			useSnapshots('accounts-list-dataset')
		);

		expect(result.current).toBeNull();
	});

	it('should wrap saved views in a single headerless group', async () => {
		mockFetch([
			{
				externalReferenceCode: 'erc-1',
				label: 'My View',
				viewConfig: '{"filters":[]}'
			}
		]);

		const {result} = renderHook(() =>
			useSnapshots('accounts-list-dataset')
		);

		await waitFor(() =>
			expect(result.current).toEqual([
				{
					headerVisible: false,
					items: [
						{
							configuration: '{"filters":[]}',
							erc: 'erc-1',
							label: 'My View'
						}
					]
				}
			])
		);
	});

	it('should return an empty array when there are no saved views', async () => {
		mockFetch([]);

		const {result} = renderHook(() =>
			useSnapshots('accounts-list-dataset')
		);

		await waitFor(() => expect(result.current).toEqual([]));
	});

	it('should not fetch when the feature flags are disabled', () => {
		Liferay.FeatureFlags['LPD-34594'] = false;

		const fetch = mockFetch([]);

		const {result} = renderHook(() =>
			useSnapshots('accounts-list-dataset')
		);

		expect(fetch).not.toHaveBeenCalled();
		expect(result.current).toEqual([]);
	});
});
