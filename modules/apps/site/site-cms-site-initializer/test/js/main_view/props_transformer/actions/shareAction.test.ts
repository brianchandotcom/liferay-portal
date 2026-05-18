/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CollaboratorService} from 'frontend-js-components-web';

import {openCMSModal} from '../../../../../src/main/resources/META-INF/resources/js/common/utils/openCMSModal';
import shareAction from '../../../../../src/main/resources/META-INF/resources/js/main_view/props_transformer/actions/shareAction';

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/js/common/utils/openCMSModal',
	() => ({
		openCMSModal: jest.fn(),
	})
);

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/js/main_view/modal/share_modal_content/CMSShareModalContent',
	() => jest.fn()
);

jest.mock('frontend-js-components-web', () => ({
	CollaboratorService: {
		getCollaborators: jest.fn(),
	},
	openToast: jest.fn(),
}));

const DEFAULT_ARGS = {
	autocompleteURL: '/search',
	collaboratorURL: '/o/cms/basic-documents/{objectEntryId}/collaborators',
	creator: {
		contentType: 'UserAccount',
		id: 1,
		name: 'Owner User',
	},
	entryClassName: '11111-className',
	itemId: 20,
	title: 'Test Document',
};

const baseItem = {
	dateExpired: undefined,
	id: 2,
	name: 'Test2 Test2',
	portrait: undefined,
	share: false,
	type: 'User',
};

const getInitialCollaborators = () => {
	const openCMSModalArgs = (openCMSModal as jest.Mock).mock.calls[0][0];

	const element = openCMSModalArgs.contentComponent({closeModal: jest.fn()});

	return element.props.initialCollaborators;
};

describe('shareAction', () => {
	beforeEach(() => {
		jest.clearAllMocks();
	});

	it.each([
		[['VIEW'], 'VIEW'],
		[['DOWNLOAD', 'VIEW'], 'VIEW'],
		[['ADD_DISCUSSION', 'DOWNLOAD', 'VIEW'], 'ADD_DISCUSSION,VIEW'],
		[
			['ADD_DISCUSSION', 'DOWNLOAD', 'UPDATE', 'VIEW'],
			'ADD_DISCUSSION,UPDATE,VIEW',
		],
		[['UPDATE', 'VIEW'], 'UPDATE,VIEW'],
	])(
		'maps API actionIds %j to initialCollaborator actionIds %j',
		async (apiActionIds, expectedActionIds) => {
			(
				CollaboratorService.getCollaborators as jest.Mock
			).mockResolvedValue([{...baseItem, actionIds: apiActionIds}]);

			await shareAction(DEFAULT_ARGS);

			const [{actionIds}] = getInitialCollaborators();

			expect(actionIds).toBe(expectedActionIds);
		}
	);

	it('strips DOWNLOAD so the picker matches a permission option', async () => {
		(CollaboratorService.getCollaborators as jest.Mock).mockResolvedValue([
			{...baseItem, actionIds: ['DOWNLOAD', 'VIEW']},
		]);

		await shareAction(DEFAULT_ARGS);

		const [collaborator] = getInitialCollaborators();

		expect(collaborator.actionIds).not.toContain('DOWNLOAD');
		expect(collaborator.actionIds).toBe('VIEW');
	});
});
