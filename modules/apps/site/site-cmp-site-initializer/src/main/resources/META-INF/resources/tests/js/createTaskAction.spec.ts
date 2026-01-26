/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-components-web';
import {navigate} from 'frontend-js-web';

import SelectProjectModalContent from '../../js/components/modal/SelectProjectModalContent';
import createTaskAction, {
	Data,
} from '../../js/components/props_transformer/actions/createTaskAction';

jest.mock('@clayui/data-provider', () => {
	return {
		__esModule: true,
		...((jest.requireActual('@clayui/data-provider') ?? {}) as any),
		useResource: jest.fn(),
	};
});

jest.mock('frontend-js-web', () => ({
	navigate: jest.fn(),
}));

jest.mock('frontend-js-components-web', () => ({
	openModal: jest.fn(),
}));

jest.mock('../../js/components/modal/SelectProjectModalContent', () =>
	jest.fn()
);

describe('createTaskAction', () => {
	afterEach(() => {
		jest.clearAllMocks();
	});

	describe('redirect path', () => {
		it('navigates to redirect URL', () => {
			const data: Data = {
				projectObjectDefinitionId: 'project-id',
				redirect: 'http://localhost/redirect-url',
			};

			createTaskAction(data);

			expect(navigate).toHaveBeenCalledWith('/redirect-url');
			expect(openModal).not.toHaveBeenCalled();
		});
	});

	describe('modal path', () => {
		it('opens modal when addProjectURL and addTaskURL are provided', () => {
			const mockSelectProjectModalContent =
				SelectProjectModalContent as jest.Mock;

			const data: Data = {
				addProjectURL: '/add-project',
				addTaskURL: '/add-task',
				projectObjectDefinitionId: 'project-id',
			};

			createTaskAction(data);

			expect(openModal).toHaveBeenCalledTimes(1);
			expect(navigate).not.toHaveBeenCalled();

			const openModalConfig = (openModal as jest.Mock).mock.calls[0][0];
			const mockCloseModal = jest.fn();

			openModalConfig.contentComponent({closeModal: mockCloseModal});

			expect(mockSelectProjectModalContent).toHaveBeenCalledWith({
				addProjectURL: '/add-project',
				addTaskURL: '/add-task',
				closeModal: mockCloseModal,
				projectObjectDefinitionId: 'project-id',
			});
		});
	});

	describe('error handling when redirect is missing and', () => {
		it.each([
			['addProjectURL is missing', {addTaskURL: '/add-task'}],
			['addTaskURL is missing', {addProjectURL: '/add-project'}],
			['both URLs are missing', {}],
		])('throws error when %s', (_, urls) => {
			const data: Data = {
				...urls,
				projectObjectDefinitionId: 'project-id',
			};

			expect(() => createTaskAction(data)).toThrow();
			expect(navigate).not.toHaveBeenCalled();
			expect(openModal).not.toHaveBeenCalled();
		});
	});
});
