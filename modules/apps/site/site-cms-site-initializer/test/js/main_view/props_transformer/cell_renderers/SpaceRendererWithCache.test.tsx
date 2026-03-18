/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render, screen, waitFor} from '@testing-library/react';
import React from 'react';

import ApiHelper from '../../../../../src/main/resources/META-INF/resources/js/common/services/ApiHelper';
import SpaceService from '../../../../../src/main/resources/META-INF/resources/js/common/services/SpaceService';
import {Space} from '../../../../../src/main/resources/META-INF/resources/js/common/types/Space';
import SpaceRendererWithCache from '../../../../../src/main/resources/META-INF/resources/js/main_view/props_transformer/cell_renderers/SpaceRendererWithCache';

describe('SpaceRendererWithCache', () => {
	const mockSpace = {
		externalReferenceCode: 'ERC',
		id: 123,
		name: 'Test Space',
		settings: {
			logoColor: 'outline-0',
		},
	} as Space;

	afterEach(() => {
		jest.restoreAllMocks();
	});

	it('fetches new space data when the scopeKey prop changes', async () => {
		const apiGetSpy = jest.spyOn(ApiHelper, 'get');

		apiGetSpy.mockResolvedValueOnce({
			data: {...mockSpace, name: 'Test Space'},
			error: null,
		});
		apiGetSpy.mockResolvedValueOnce({
			data: {...mockSpace, name: 'Test Space Updated'},
			error: null,
		});

		const {rerender} = render(
			<SpaceRendererWithCache
				scopeKey="Test Space"
				spaceExternalReferenceCode="ERC"
			/>
		);

		expect(await screen.findByText('Test Space')).toBeInTheDocument();

		expect(apiGetSpy).toHaveBeenCalledTimes(1);

		rerender(
			<SpaceRendererWithCache
				scopeKey="Test Space Updated"
				spaceExternalReferenceCode="ERC"
			/>
		);

		expect(
			await screen.findByText('Test Space Updated')
		).toBeInTheDocument();

		expect(apiGetSpy).toHaveBeenCalledTimes(2);
	});

	it('uses cached data on consecutive renders with the same scopeKey', async () => {
		const apiGetSpy = jest.spyOn(ApiHelper, 'get');

		apiGetSpy.mockResolvedValueOnce({
			data: {...mockSpace, name: 'Same space key'},
			error: null,
		});

		const {rerender} = render(
			<SpaceRendererWithCache
				scopeKey="Same space key"
				spaceExternalReferenceCode="ERC"
			/>
		);

		expect(await screen.findByText('Same space key')).toBeInTheDocument();

		expect(apiGetSpy).toHaveBeenCalledTimes(1);

		rerender(
			<SpaceRendererWithCache
				scopeKey="Same space key"
				spaceExternalReferenceCode="ERC"
			/>
		);

		expect(await screen.findByText('Same space key')).toBeInTheDocument();

		expect(apiGetSpy).toHaveBeenCalledTimes(1);
	});

	it('shows a loading indicator while fetching data', () => {
		jest.spyOn(SpaceService, 'getSpaceWithCache').mockReturnValue(
			new Promise(() => {})
		);

		render(
			<SpaceRendererWithCache
				scopeKey="Test Space"
				spaceExternalReferenceCode="ERC"
			/>
		);

		expect(
			screen.getByTestId('space-renderer-loading')
		).toBeInTheDocument();
	});

	it('renders the space name and logo color after data is fetched', async () => {
		jest.spyOn(SpaceService, 'getSpaceWithCache').mockResolvedValue({
			...mockSpace,
			name: 'Test Space',
		});

		const {container} = render(
			<SpaceRendererWithCache
				scopeKey="Test Space"
				spaceExternalReferenceCode="ERC"
			/>
		);

		expect(await screen.findByText('Test Space')).toBeInTheDocument();

		expect(SpaceService.getSpaceWithCache).toHaveBeenCalledWith(
			'ERC',
			'Test Space'
		);

		expect(container.querySelector('.sticker')).toHaveClass(
			'sticker-outline-0'
		);
	});

	it('renders nothing when no space data is returned', async () => {
		jest.spyOn(SpaceService, 'getSpaceWithCache').mockResolvedValue(
			null as any
		);

		const {container} = render(
			<SpaceRendererWithCache
				scopeKey="Test Space"
				spaceExternalReferenceCode="ERC"
			/>
		);

		await waitFor(() => {
			expect(container.firstChild).toBeNull();
		});
	});
});
