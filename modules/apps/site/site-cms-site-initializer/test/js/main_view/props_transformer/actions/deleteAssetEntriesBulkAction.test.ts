/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import SpaceService from '../../../../../src/main/resources/META-INF/resources/js/common/services/SpaceService';
import {openCMSModal} from '../../../../../src/main/resources/META-INF/resources/js/common/utils/openCMSModal';
import deleteAssetEntriesBulkAction, {
	executeBulkDeleteAction,
} from '../../../../../src/main/resources/META-INF/resources/js/main_view/props_transformer/actions/deleteAssetEntriesBulkAction';
import {triggerAssetBulkAction} from '../../../../../src/main/resources/META-INF/resources/js/main_view/props_transformer/actions/triggerAssetBulkAction';

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/js/common/services/SpaceService',
	() => ({
		__esModule: true,
		default: {getSpace: jest.fn()},
	})
);

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/js/common/utils/getScopeExternalReferenceCode',
	() => ({
		getScopeExternalReferenceCode: jest.fn(
			(item: any) => item?.spaceExternalReferenceCode
		),
	})
);

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/js/common/utils/openCMSModal',
	() => ({openCMSModal: jest.fn()})
);

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/js/main_view/props_transformer/actions/triggerAssetBulkAction',
	() => ({triggerAssetBulkAction: jest.fn()})
);

const buildItems = (count: number) =>
	Array.from({length: count}, (_, i) => ({
		embedded: {status: {label: 'approved'}},
		entryClassName: 'ClassName1',
		spaceExternalReferenceCode: `space-${i}`,
	}));

const space = (trashEnabled: boolean) => ({settings: {trashEnabled}});

beforeEach(() => {
	jest.clearAllMocks();
});

describe('executeBulkDeleteAction', () => {
	it('uses entryClassName from the first item when items are present', () => {
		executeBulkDeleteAction('api-url', 'data-set', {
			items: buildItems(1) as any,
			selectAll: false,
		});

		expect(triggerAssetBulkAction).toHaveBeenCalledWith(
			expect.objectContaining({
				apiURL: 'api-url',
				dataSetId: 'data-set',
				keyValues: {className: 'ClassName1'},
				type: 'DeleteObjectBulkSelectionAction',
			})
		);
	});

	it('passes an empty className when items is undefined (Select All flow)', () => {
		executeBulkDeleteAction('api-url', 'data-set', {
			selectAll: true,
		} as any);

		expect(triggerAssetBulkAction).toHaveBeenCalledWith(
			expect.objectContaining({
				keyValues: {className: ''},
			})
		);
	});

	it('invokes processClose through the triggerAssetBulkAction onCreateSuccess callback', () => {
		const processClose = jest.fn();

		executeBulkDeleteAction(
			'api-url',
			'data-set',
			{items: buildItems(1) as any, selectAll: false},
			processClose
		);

		const {onCreateSuccess} = (triggerAssetBulkAction as jest.Mock).mock
			.calls[0][0];

		onCreateSuccess();

		expect(processClose).toHaveBeenCalledTimes(1);
	});
});

describe('deleteAssetEntriesBulkAction', () => {
	it('skips the confirmation modal when trashEnabled is true and selection is not from the recycle bin', async () => {
		await deleteAssetEntriesBulkAction({
			apiURL: 'api-url',
			dataSetId: 'data-set',
			selectedData: {items: buildItems(1) as any, selectAll: false},
			trashEnabled: true,
		});

		expect(openCMSModal).not.toHaveBeenCalled();
		expect(triggerAssetBulkAction).toHaveBeenCalledTimes(1);
	});

	it('skips the confirmation modal when every selected item belongs to a trash-enabled space and selection is not from the recycle bin', async () => {
		(SpaceService.getSpace as jest.Mock)
			.mockResolvedValueOnce(space(true))
			.mockResolvedValueOnce(space(true));

		await deleteAssetEntriesBulkAction({
			apiURL: 'api-url',
			dataSetId: 'data-set',
			selectedData: {items: buildItems(2) as any, selectAll: false},
		});

		expect(openCMSModal).not.toHaveBeenCalled();
		expect(triggerAssetBulkAction).toHaveBeenCalledTimes(1);
	});

	it('shows the confirmation modal when trashEnabled is false', async () => {
		await deleteAssetEntriesBulkAction({
			apiURL: 'api-url',
			dataSetId: 'data-set',
			selectedData: {items: buildItems(1) as any, selectAll: false},
			trashEnabled: false,
		});

		expect(openCMSModal).toHaveBeenCalledTimes(1);
		expect(triggerAssetBulkAction).not.toHaveBeenCalled();
	});

	it('shows the confirmation modal when the selection is from the recycle bin', async () => {
		await deleteAssetEntriesBulkAction({
			apiURL: 'api-url',
			dataSetId:
				'com.liferay.site.cms.site.initializer-recycleBinSection',
			selectedData: {items: buildItems(1) as any, selectAll: false},
			trashEnabled: true,
		});

		expect(openCMSModal).toHaveBeenCalledTimes(1);
	});

	it('shows the confirmation modal when showConfirmationModal is true', async () => {
		await deleteAssetEntriesBulkAction({
			apiURL: 'api-url',
			dataSetId: 'data-set',
			selectedData: {items: buildItems(1) as any, selectAll: false},
			showConfirmationModal: true,
			trashEnabled: true,
		});

		expect(openCMSModal).toHaveBeenCalledTimes(1);
	});

	it('uses the delete-all-items title and confirmation when selectAll is true', async () => {
		await deleteAssetEntriesBulkAction({
			apiURL: 'api-url',
			dataSetId: 'data-set',
			selectedData: {items: [], selectAll: true},
			trashEnabled: false,
		});

		const [modalArgs] = (openCMSModal as jest.Mock).mock.calls[0];

		expect(modalArgs.bodyHTML).toContain('delete-all-items-confirmation');
		expect(modalArgs.title).toBe('delete-all-items');
	});

	it('uses the delete-all-items title and confirmation when selectAll is true and the selection is from the recycle bin, no matter trashEnabled', async () => {
		await deleteAssetEntriesBulkAction({
			apiURL: 'api-url',
			dataSetId:
				'com.liferay.site.cms.site.initializer-recycleBinSection',
			selectedData: {items: [], selectAll: true},
			trashEnabled: true,
		});

		const [modalArgs] = (openCMSModal as jest.Mock).mock.calls[0];

		expect(modalArgs.bodyHTML).toContain('delete-all-items-confirmation');
		expect(modalArgs.title).toBe('delete-all-items');
	});

	it('uses the singular delete-item title and confirmation when a single item is selected', async () => {
		await deleteAssetEntriesBulkAction({
			apiURL: 'api-url',
			dataSetId: 'data-set',
			selectedData: {items: buildItems(1) as any, selectAll: false},
			trashEnabled: false,
		});

		const [modalArgs] = (openCMSModal as jest.Mock).mock.calls[0];

		expect(modalArgs.bodyHTML).toContain('delete-item-confirmation');
		expect(modalArgs.title).toBe('delete-item');
	});

	it('uses the singular delete-item title and confirmation when a single item is selected and the selection is from the recycle bin, no matter trashEnabled', async () => {
		await deleteAssetEntriesBulkAction({
			apiURL: 'api-url',
			dataSetId:
				'com.liferay.site.cms.site.initializer-recycleBinSection',
			selectedData: {items: buildItems(1) as any, selectAll: false},
			trashEnabled: true,
		});

		const [modalArgs] = (openCMSModal as jest.Mock).mock.calls[0];

		expect(modalArgs.bodyHTML).toContain('delete-item-confirmation');
		expect(modalArgs.title).toBe('delete-item');
	});

	it('uses the delete-x-items title and bulk confirmation when more than one item is selected', async () => {
		await deleteAssetEntriesBulkAction({
			apiURL: 'api-url',
			dataSetId: 'data-set',
			selectedData: {items: buildItems(3) as any, selectAll: false},
			trashEnabled: false,
		});

		const [modalArgs] = (openCMSModal as jest.Mock).mock.calls[0];

		expect(modalArgs.bodyHTML).toContain('delete-x-items-confirmation');
		expect(modalArgs.title).toBe('delete-x-items');
	});

	it('uses the delete-x-items title and bulk confirmation when more than one item is selected and the selection is from the recycle bin, no matter trashEnabled', async () => {
		await deleteAssetEntriesBulkAction({
			apiURL: 'api-url',
			dataSetId:
				'com.liferay.site.cms.site.initializer-recycleBinSection',
			selectedData: {items: buildItems(3) as any, selectAll: false},
			trashEnabled: true,
		});

		const [modalArgs] = (openCMSModal as jest.Mock).mock.calls[0];

		expect(modalArgs.bodyHTML).toContain('delete-x-items-confirmation');
		expect(modalArgs.title).toBe('delete-x-items');
	});

	it('shows the multiple-spaces warning when some (but not all) selected items belong to trash-enabled spaces', async () => {
		(SpaceService.getSpace as jest.Mock)
			.mockResolvedValueOnce(space(true))
			.mockResolvedValueOnce(space(false));

		await deleteAssetEntriesBulkAction({
			apiURL: 'api-url',
			dataSetId: 'data-set',
			selectedData: {items: buildItems(2) as any, selectAll: false},
		});

		const [modalArgs] = (openCMSModal as jest.Mock).mock.calls[0];

		expect(modalArgs.bodyHTML).toContain(
			'you-are-about-to-delete-the-selected-items-from-multiple-spaces'
		);
		expect(modalArgs.bodyHTML).toContain(
			'bulk-delete-from-multiple-spaces-warning'
		);
		expect(modalArgs.title).toBe('delete-x-items');
	});

	it('shows the multiple-spaces warning with the delete-all-items title when selectAll is true and trashEnabled is not provided', async () => {
		await deleteAssetEntriesBulkAction({
			apiURL: 'api-url',
			dataSetId: 'data-set',
			selectedData: {items: [], selectAll: true},
		});

		const [modalArgs] = (openCMSModal as jest.Mock).mock.calls[0];

		expect(modalArgs.bodyHTML).toContain(
			'you-are-about-to-delete-the-selected-items-from-multiple-spaces'
		);
		expect(modalArgs.bodyHTML).toContain(
			'bulk-delete-from-multiple-spaces-warning'
		);
		expect(modalArgs.title).toBe('delete-all-items');
	});
});
