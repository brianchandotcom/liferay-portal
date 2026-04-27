/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import {navigate} from 'frontend-js-web';
import React from 'react';

import {QuickActions} from '../../../../src/main/resources/META-INF/resources/js';
import {QuickActionAssetData} from '../../../../src/main/resources/META-INF/resources/js/main_view/home/QuickActions';
import createAssetAction from '../../../../src/main/resources/META-INF/resources/js/main_view/props_transformer/actions/createAssetAction';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/main_view/props_transformer/actions/createAssetAction',
	() => ({
		__esModule: true,
		default: jest.fn(),
	})
);

describe('QuickActions', () => {
	const basicWebContentAction: QuickActionAssetData = {
		action: 'createAsset',
		assetLibraries: [
			{
				externalReferenceCode: 'L_DEFAULT',
				groupId: 1,
				name: 'Default',
			},
		],
		icon: 'forms',
		redirect: '/cms/-/add_structured_content_item?objectDefinitionId=1',
		title: 'Basic Web Content',
	};

	const customStructureAction: QuickActionAssetData = {
		action: 'createAsset',
		assetLibraries: [
			{
				externalReferenceCode: 'L_DEFAULT',
				groupId: 1,
				name: 'Default',
			},
		],
		icon: 'forms',
		redirect: '/cms/-/add_structured_content_item?objectDefinitionId=42',
		title: 'My Custom Structure',
	};

	const vocabularyAction: QuickActionAssetData = {
		action: 'createVocabulary',
		icon: 'vocabulary',
		redirect: '/group/cms/categorization/new-vocabulary',
		title: 'Vocabulary',
	};

	beforeEach(() => {
		jest.clearAllMocks();
	});

	it('renders the quick-actions heading', () => {
		render(<QuickActions quickActions={[basicWebContentAction]} />);

		expect(
			screen.getByRole('heading', {name: 'quick-actions'})
		).toBeInTheDocument();
	});

	it('renders one button per quick action with its title', () => {
		render(
			<QuickActions
				quickActions={[
					basicWebContentAction,
					customStructureAction,
					vocabularyAction,
				]}
			/>
		);

		expect(
			screen.getByRole('button', {name: /Basic Web Content/})
		).toBeInTheDocument();
		expect(
			screen.getByRole('button', {name: /My Custom Structure/})
		).toBeInTheDocument();
		expect(
			screen.getByRole('button', {name: /Vocabulary/})
		).toBeInTheDocument();
	});

	it('navigates to the redirect URL when a createVocabulary action is clicked', async () => {
		render(<QuickActions quickActions={[vocabularyAction]} />);

		await userEvent.click(screen.getByRole('button', {name: /Vocabulary/}));

		expect(navigate).toHaveBeenCalledWith(vocabularyAction.redirect);
		expect(createAssetAction).not.toHaveBeenCalled();
	});

	it('invokes createAssetAction when a createAsset action is clicked', async () => {
		render(<QuickActions quickActions={[basicWebContentAction]} />);

		await userEvent.click(
			screen.getByRole('button', {name: /Basic Web Content/})
		);

		expect(createAssetAction).toHaveBeenCalledWith(basicWebContentAction);
		expect(navigate).not.toHaveBeenCalled();
	});
});
