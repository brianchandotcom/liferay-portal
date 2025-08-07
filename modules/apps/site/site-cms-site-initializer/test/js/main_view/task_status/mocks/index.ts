/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const MOCKED_TASK = {
	actions: {
		create: {
			href: 'http://localhost:8080/o/c/bulkactiontasks/',
			method: 'POST',
		},
		createBatch: {
			href: 'http://localhost:8080/o/c/bulkactiontasks/batch',
			method: 'POST',
		},
		deleteBatch: {
			href: 'http://localhost:8080/o/c/bulkactiontasks/batch',
			method: 'DELETE',
		},
		updateBatch: {
			href: 'http://localhost:8080/o/c/bulkactiontasks/batch',
			method: 'PUT',
		},
	},
	facets: [],
	items: [
		{
			actionName: 'Asset deleted',
			actionType: '',
			actions: {
				delete: {
					href: 'http://localhost:8080/o/c/bulkactiontasks/35456',
					method: 'DELETE',
				},
				expire: {
					href: 'http://localhost:8080/o/c/bulkactiontasks/35456/expire',
					method: 'POST',
				},
				get: {
					href: 'http://localhost:8080/o/c/bulkactiontasks/35456',
					method: 'GET',
				},
				permissions: {
					href: 'http://localhost:8080/o/c/bulkactiontasks/35456/permissions',
					method: 'GET',
				},
				replace: {
					href: 'http://localhost:8080/o/c/bulkactiontasks/35456',
					method: 'PUT',
				},
				update: {
					href: 'http://localhost:8080/o/c/bulkactiontasks/35456',
					method: 'PATCH',
				},
				versions: {
					href: 'http://localhost:8080/o/c/bulkactiontasks/35456/versions',
					method: 'GET',
				},
			},
			creator: {
				additionalName: '',
				contentType: 'UserAccount',
				externalReferenceCode: '6d2bae2c-8967-35f1-360e-7c0e06e45d2f',
				familyName: 'Test',
				givenName: 'Test',
				id: 20132,
				name: 'Test Test',
			},
			dateCreated: '2025-07-30T08:12:01Z',
			dateModified: '2025-07-30T08:25:39Z',
			defaultLanguageId: 'en_US',
			externalReferenceCode: 'c8dce340-3096-54bb-76d4-6df57592a081',
			friendlyUrlPath: 'c8dce340-3096-54bb-76d4-6df57592a081',
			friendlyUrlPath_i18n: {
				en_US: 'c8dce340-3096-54bb-76d4-6df57592a081',
			},
			id: 35456,
			keywords: [],
			objectEntryFolderExternalReferenceCode: '',
			objectEntryFolderId: 0,
			scopeId: 0,
			status: {
				code: 0,
				label: 'approved',
				label_i18n: 'Approved',
			},
			taskItems: 25,
			taskResult: 'completed',
			taxonomyCategoryBriefs: [],
		},
		{
			actionName: 'Assets movement',
			actionType: '',
			actions: {
				delete: {
					href: 'http://localhost:8080/o/c/bulkactiontasks/35507',
					method: 'DELETE',
				},
				expire: {
					href: 'http://localhost:8080/o/c/bulkactiontasks/35507/expire',
					method: 'POST',
				},
				get: {
					href: 'http://localhost:8080/o/c/bulkactiontasks/35507',
					method: 'GET',
				},
				permissions: {
					href: 'http://localhost:8080/o/c/bulkactiontasks/35507/permissions',
					method: 'GET',
				},
				replace: {
					href: 'http://localhost:8080/o/c/bulkactiontasks/35507',
					method: 'PUT',
				},
				update: {
					href: 'http://localhost:8080/o/c/bulkactiontasks/35507',
					method: 'PATCH',
				},
				versions: {
					href: 'http://localhost:8080/o/c/bulkactiontasks/35507/versions',
					method: 'GET',
				},
			},
			creator: {
				additionalName: '',
				contentType: 'UserAccount',
				externalReferenceCode: '6d2bae2c-8967-35f1-360e-7c0e06e45d2f',
				familyName: 'Test',
				givenName: 'Test',
				id: 20132,
				name: 'Test Test',
			},
			dateCreated: '2025-07-30T08:18:54Z',
			dateModified: '2025-07-30T08:25:39Z',
			defaultLanguageId: 'en_US',
			externalReferenceCode: '681ea53c-3b4e-7d87-54ce-0599ce91fa57',
			friendlyUrlPath: '681ea53c-3b4e-7d87-54ce-0599ce91fa57',
			friendlyUrlPath_i18n: {
				en_US: '681ea53c-3b4e-7d87-54ce-0599ce91fa57',
			},
			id: 35507,
			keywords: [],
			objectEntryFolderExternalReferenceCode: '',
			objectEntryFolderId: 0,
			scopeId: 0,
			status: {
				code: 0,
				label: 'approved',
				label_i18n: 'Approved',
			},
			taskItems: 15,
			taskResult: 'completed',
			taxonomyCategoryBriefs: [],
		},
		{
			actionName: 'Asset editing',
			actionType: 'type',
			actions: {
				delete: {
					href: 'http://localhost:8080/o/c/bulkactiontasks/35631',
					method: 'DELETE',
				},
				expire: {
					href: 'http://localhost:8080/o/c/bulkactiontasks/35631/expire',
					method: 'POST',
				},
				get: {
					href: 'http://localhost:8080/o/c/bulkactiontasks/35631',
					method: 'GET',
				},
				permissions: {
					href: 'http://localhost:8080/o/c/bulkactiontasks/35631/permissions',
					method: 'GET',
				},
				replace: {
					href: 'http://localhost:8080/o/c/bulkactiontasks/35631',
					method: 'PUT',
				},
				update: {
					href: 'http://localhost:8080/o/c/bulkactiontasks/35631',
					method: 'PATCH',
				},
				versions: {
					href: 'http://localhost:8080/o/c/bulkactiontasks/35631/versions',
					method: 'GET',
				},
			},
			completedDate: '2025-07-30T00:00:00.000Z',
			creator: {
				additionalName: '',
				contentType: 'UserAccount',
				externalReferenceCode: '6d2bae2c-8967-35f1-360e-7c0e06e45d2f',
				familyName: 'Test',
				givenName: 'Test',
				id: 20132,
				name: 'Test Test',
			},
			dateCreated: '2025-07-30T09:21:11Z',
			dateModified: '2025-07-31T11:11:55Z',
			defaultLanguageId: 'en_US',
			externalReferenceCode: '411c213c-3815-c163-1694-380f4ef83770',
			friendlyUrlPath: '411c213c-3815-c163-1694-380f4ef83770',
			friendlyUrlPath_i18n: {
				en_US: '411c213c-3815-c163-1694-380f4ef83770',
			},
			id: 35631,
			keywords: [],
			objectEntryFolderExternalReferenceCode: '',
			objectEntryFolderId: 0,
			scopeId: 0,
			status: {
				code: 0,
				label: 'approved',
				label_i18n: 'Approved',
			},
			taskItems: 1,
			taskResult: 'processing',
			taxonomyCategoryBriefs: [],
		},
		{
			actionName: 'Assets Delete',
			actionType: 'delete',
			actions: {
				delete: {
					href: 'http://localhost:8080/o/c/bulkactiontasks/35842',
					method: 'DELETE',
				},
				expire: {
					href: 'http://localhost:8080/o/c/bulkactiontasks/35842/expire',
					method: 'POST',
				},
				get: {
					href: 'http://localhost:8080/o/c/bulkactiontasks/35842',
					method: 'GET',
				},
				permissions: {
					href: 'http://localhost:8080/o/c/bulkactiontasks/35842/permissions',
					method: 'GET',
				},
				replace: {
					href: 'http://localhost:8080/o/c/bulkactiontasks/35842',
					method: 'PUT',
				},
				update: {
					href: 'http://localhost:8080/o/c/bulkactiontasks/35842',
					method: 'PATCH',
				},
				versions: {
					href: 'http://localhost:8080/o/c/bulkactiontasks/35842/versions',
					method: 'GET',
				},
			},
			creator: {
				additionalName: '',
				contentType: 'UserAccount',
				externalReferenceCode: '6d2bae2c-8967-35f1-360e-7c0e06e45d2f',
				familyName: 'Test',
				givenName: 'Test',
				id: 20132,
				name: 'Test Test',
			},
			dateCreated: '2025-07-31T11:12:20Z',
			dateModified: '2025-07-31T11:12:31Z',
			defaultLanguageId: 'en_US',
			externalReferenceCode: '702b1340-4b9d-d233-0ca2-e56a41fed1f5',
			friendlyUrlPath: '702b1340-4b9d-d233-0ca2-e56a41fed1f5',
			friendlyUrlPath_i18n: {
				en_US: '702b1340-4b9d-d233-0ca2-e56a41fed1f5',
			},
			id: 35842,
			keywords: [],
			objectEntryFolderExternalReferenceCode: '',
			objectEntryFolderId: 0,
			scopeId: 0,
			status: {
				code: 0,
				label: 'approved',
				label_i18n: 'Approved',
			},
			taskItems: 100,
			taskResult: 'processing',
			taxonomyCategoryBriefs: [],
		},
		{
			actionName: 'Asset delete',
			actionType: 'delete',
			actions: {
				delete: {
					href: 'http://localhost:8080/o/c/bulkactiontasks/35842',
					method: 'DELETE',
				},
				expire: {
					href: 'http://localhost:8080/o/c/bulkactiontasks/35842/expire',
					method: 'POST',
				},
				get: {
					href: 'http://localhost:8080/o/c/bulkactiontasks/35842',
					method: 'GET',
				},
				permissions: {
					href: 'http://localhost:8080/o/c/bulkactiontasks/35842/permissions',
					method: 'GET',
				},
				replace: {
					href: 'http://localhost:8080/o/c/bulkactiontasks/35842',
					method: 'PUT',
				},
				update: {
					href: 'http://localhost:8080/o/c/bulkactiontasks/35842',
					method: 'PATCH',
				},
				versions: {
					href: 'http://localhost:8080/o/c/bulkactiontasks/35842/versions',
					method: 'GET',
				},
			},
			creator: {
				additionalName: '',
				contentType: 'UserAccount',
				externalReferenceCode: '6d2bae2c-8967-35f1-360e-7c0e06e45d2f',
				familyName: 'Test',
				givenName: 'Test',
				id: 20132,
				name: 'Test Test',
			},
			dateCreated: '2025-07-31T11:12:20Z',
			dateModified: '2025-08-05 16:16:59Z',
			defaultLanguageId: 'en_US',
			externalReferenceCode: '702b1340-4b9d-d233-0ca2-e56a41fed1f5',
			friendlyUrlPath: '702b1340-4b9d-d233-0ca2-e56a41fed1f5',
			friendlyUrlPath_i18n: {
				en_US: '702b1340-4b9d-d233-0ca2-e56a41fed1f5',
			},
			id: 35842,
			keywords: [],
			objectEntryFolderExternalReferenceCode: '',
			objectEntryFolderId: 0,
			scopeId: 0,
			status: {
				code: 0,
				label: 'approved',
				label_i18n: 'Approved',
			},
			taskItems: 505,
			taskResult: 'failed',
			taxonomyCategoryBriefs: [],
		},
	],
	lastPage: 1,
	page: 1,
	pageSize: 5,
	totalCount: 5,
};

export default MOCKED_TASK;
