/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {HttpResponse, http} from 'msw';
import {setupWorker} from 'msw/browser';

export function setupExportImportMocks() {
	const worker = setupWorker(
		http.get('/group/__mocks__/get-import-error-list', () => {
			return HttpResponse.json(
				{
					actions: {},
					facets: [],
					items: [
						{
							actions: {},
							creator: {},
							dateCreated: '2025-06-05T08:51:54Z',
							dateModified: '2025-06-05T08:51:54Z',
							entityType: 'Employees',
							errorId: 12345,
							errorMessage: 'This is an example error message 1.',
							errorType: 'An unexpected error occurred',
							externalReferenceCode: 'ERC-1',
							id: 51949,
							status: {
								code: 0,
								label: 'approved',
								label_i18n: 'Approved',
							},
						},
						{
							actions: {},
							creator: {},
							dateCreated: '2025-06-05T08:51:54Z',
							dateModified: '2025-06-05T08:51:54Z',
							entityType: 'Employees',
							errorId: 12346,
							errorMessage: 'This is an example error message 2.',
							errorType: 'Validation error',
							externalReferenceCode: 'ERC-2',
							id: 51950,
							status: {
								code: 0,
								label: 'approved',
								label_i18n: 'Approved',
							},
						},
						{
							actions: {},
							creator: {},
							dateCreated: '2025-06-05T08:51:54Z',
							dateModified: '2025-06-05T08:51:54Z',
							entityType: 'Employees',
							errorId: 12347,
							errorMessage: 'This is an example error message 3.',
							errorType: 'Database connection error',
							externalReferenceCode: 'ERC-3',
							id: 51951,
							status: {
								code: 0,
								label: 'approved',
								label_i18n: 'Approved',
							},
						},
						{
							actions: {},
							creator: {},
							dateCreated: '2025-06-05T08:51:54Z',
							dateModified: '2025-06-05T08:51:54Z',
							entityType: 'Employees',
							errorId: 12348,
							errorMessage: 'This is an example error message 4.',
							errorType: 'Missing required field',
							externalReferenceCode: 'ERC-4',
							id: 51952,
							status: {
								code: 0,
								label: 'approved',
								label_i18n: 'Approved',
							},
						},
						{
							actions: {},
							creator: {},
							dateCreated: '2025-06-05T08:51:54Z',
							dateModified: '2025-06-05T08:51:54Z',
							entityType: 'Employees',
							errorId: 12349,
							errorMessage: 'This is an example error message 5.',
							errorType: 'Invalid input format',
							externalReferenceCode: 'ERC-5',
							id: 51953,
							status: {
								code: 0,
								label: 'approved',
								label_i18n: 'Approved',
							},
						},
						{
							actions: {},
							creator: {},
							dateCreated: '2025-06-05T08:51:54Z',
							dateModified: '2025-06-05T08:51:54Z',
							entityType: 'Employees',
							errorId: 12350,
							errorMessage: 'This is an example error message 6.',
							errorType: 'Authentication failure',
							externalReferenceCode: 'ERC-6',
							id: 51954,
							status: {
								code: 0,
								label: 'approved',
								label_i18n: 'Approved',
							},
						},
						{
							actions: {},
							creator: {},
							dateCreated: '2025-06-05T08:51:54Z',
							dateModified: '2025-06-05T08:51:54Z',
							entityType: 'Employees',
							errorId: 12351,
							errorMessage: 'This is an example error message 7.',
							errorType: 'Authorization error',
							externalReferenceCode: 'ERC-7',
							id: 51955,
							status: {
								code: 0,
								label: 'approved',
								label_i18n: 'Approved',
							},
						},
						{
							actions: {},
							creator: {},
							dateCreated: '2025-06-05T08:51:54Z',
							dateModified: '2025-06-05T08:51:54Z',
							entityType: 'Employees',
							errorId: 12352,
							errorMessage: 'This is an example error message 8.',
							errorType: 'Resource not found',
							externalReferenceCode: 'ERC-8',
							id: 51956,
							status: {
								code: 0,
								label: 'approved',
								label_i18n: 'Approved',
							},
						},
						{
							actions: {},
							creator: {},
							dateCreated: '2025-06-05T08:51:54Z',
							dateModified: '2025-06-05T08:51:54Z',
							entityType: 'Employees',
							errorId: 12353,
							errorMessage: 'This is an example error message 9.',
							errorType: 'Timeout error',
							externalReferenceCode: 'ERC-9',
							id: 51957,
							status: {
								code: 0,
								label: 'approved',
								label_i18n: 'Approved',
							},
						},
						{
							actions: {},
							creator: {},
							dateCreated: '2025-06-05T08:51:54Z',
							dateModified: '2025-06-05T08:51:54Z',
							entityType: 'Employees',
							errorId: 12354,
							errorMessage:
								'This is an example error message 10.',
							errorType: 'Duplicate entry',
							externalReferenceCode: 'ERC-10',
							id: 51958,
							status: {
								code: 0,
								label: 'approved',
								label_i18n: 'Approved',
							},
						},
						{
							actions: {},
							creator: {},
							dateCreated: '2025-06-05T08:51:54Z',
							dateModified: '2025-06-05T08:51:54Z',
							entityType: 'Employees',
							errorId: 12355,
							errorMessage:
								'This is an example error message 11.',
							errorType: 'Data conflict',
							externalReferenceCode: 'ERC-11',
							id: 51959,
							status: {
								code: 0,
								label: 'approved',
								label_i18n: 'Approved',
							},
						},
						{
							actions: {},
							creator: {},
							dateCreated: '2025-06-05T08:51:54Z',
							dateModified: '2025-06-05T08:51:54Z',
							entityType: 'Employees',
							errorId: 12356,
							errorMessage:
								'This is an example error message 12.',
							errorType: 'File not found',
							externalReferenceCode: 'ERC-12',
							id: 51960,
							status: {
								code: 0,
								label: 'approved',
								label_i18n: 'Approved',
							},
						},
						{
							actions: {},
							creator: {},
							dateCreated: '2025-06-05T08:51:54Z',
							dateModified: '2025-06-05T08:51:54Z',
							entityType: 'Employees',
							errorId: 12357,
							errorMessage:
								'This is an example error message 13.',
							errorType: 'Server overload',
							externalReferenceCode: 'ERC-13',
							id: 51961,
							status: {
								code: 0,
								label: 'approved',
								label_i18n: 'Approved',
							},
						},
					],
					lastPage: 1,
					page: 1,
					pageSize: 20,
					totalCount: 20,
				},
				200,
				{
					'Content-Type': 'application/json',
				}
			);
		}),

		http.get('/group/__mocks__/get-import-error-detail', () => {
			return HttpResponse.json(
				{
					actions: {},
					creator: {
						name: 'John Doe',
					},
					dateCreated: '2025-06-05T08:51:54Z',
					dateModified: '2025-06-05T08:51:54Z',
					entityExternalReferenceCode: 'ERC-1001',
					entityId: 1001,
					entityScope: 'global',
					entitySite: 'default-site',
					entityType: 'Employees',
					errorId: 12345,
					errorMessage:
						'An essential process has failed to complete successfully. Please recheck your inputs and try again. Report this issue to support if it persists. Error ID: 987654321AB.',
					errorStackTrace:
						'java.lang.NullPointerException: Cannot invoke "java.lang.String.length()" because "myObject" is null\n\tat com.example.MyClass.myMethod(MyClass.java:42)\n\tat com.example.AnotherClass.process(AnotherClass.java:105)\n\tat com.example.Main.main(Main.java:15)\nCaused by: java.lang.IllegalArgumentException: Invalid input\n\tat com.example.Validator.validate(Validator.java:20)\n\t...java.lang.NullPointerException: Cannot invoke "java.lang.String.length()" because "myObject" is null\n\tat com.example.MyClass.myMethod(MyClass.java:42)\n\tat com.example.AnotherClass.process(AnotherClass.java:105)\n\tat com.example.Main.main(Main.java:15)\nCaused by: java.lang.IllegalArgumentException: Invalid input\n\tat com.example.Validator.validate(Validator.java:20)\n\t...java.lang.NullPointerException: Cannot invoke "java.lang.String.length()" because "myObject" is null\n\tat com.example.MyClass.myMethod(MyClass.java:42)\n\tat com.example.AnotherClass.process(AnotherClass.java:105)\n\tat com.example.Main.main(Main.java:15)\nCaused by: java.lang.IllegalArgumentException: Invalid input\n\tat com.example.Validator.validate(Validator.java:20)\n\t...java.lang.NullPointerException: Cannot invoke "java.lang.String.length()" because "myObject" is null\n\tat com.example.MyClass.myMethod(MyClass.java:42)\n\tat com.example.AnotherClass.process(AnotherClass.java:105)\n\tat com.example.Main.main(Main.java:15)\nCaused by: java.lang.IllegalArgumentException: Invalid input\n\tat com.example.Validator.validate(Validator.java:20)\n\t...java.lang.NullPointerException: Cannot invoke "java.lang.String.length()" because "myObject" is null\n\tat com.example.MyClass.myMethod(MyClass.java:42)\n\tat com.example.AnotherClass.process(AnotherClass.java:105)\n\tat com.example.Main.main(Main.java:15)\nCaused by: java.lang.IllegalArgumentException: Invalid input\n\tat com.example.Validator.validate(Validator.java:20)\n\t...java.lang.NullPointerException: Cannot invoke "java.lang.String.length()" because "myObject" is null\n\tat com.example.MyClass.myMethod(MyClass.java:42)\n\tat com.example.AnotherClass.process(AnotherClass.java:105)\n\tat com.example.Main.main(Main.java:15)\nCaused by: java.lang.IllegalArgumentException: Invalid input\n\tat com.example.Validator.validate(Validator.java:20)\n\t...java.lang.NullPointerException: Cannot invoke "java.lang.String.length()" because "myObject" is null\n\tat com.example.MyClass.myMethod(MyClass.java:42)\n\tat com.example.AnotherClass.process(AnotherClass.java:105)\n\tat com.example.Main.main(Main.java:15)\nCaused by: java.lang.IllegalArgumentException: Invalid input\n\tat com.example.Validator.validate(Validator.java:20)\n\t...java.lang.NullPointerException: Cannot invoke "java.lang.String.length()" because "myObject" is null\n\tat com.example.MyClass.myMethod(MyClass.java:42)\n\tat com.example.AnotherClass.process(AnotherClass.java:105)\n\tat com.example.Main.main(Main.java:15)\nCaused by: java.lang.IllegalArgumentException: Invalid input\n\tat com.example.Validator.validate(Validator.java:20)\n\t...java.lang.NullPointerException: Cannot invoke "java.lang.String.length()" because "myObject" is null\n\tat com.example.MyClass.myMethod(MyClass.java:42)\n\tat com.example.AnotherClass.process(AnotherClass.java:105)\n\tat com.example.Main.main(Main.java:15)\nCaused by: java.lang.IllegalArgumentException: Invalid input\n\tat com.example.Validator.validate(Validator.java:20)\n\t...java.lang.NullPointerException: Cannot invoke "java.lang.String.length()" because "myObject" is null\n\tat com.example.MyClass.myMethod(MyClass.java:42)\n\tat com.example.AnotherClass.process(AnotherClass.java:105)\n\tat com.example.Main.main(Main.java:15)\nCaused by: java.lang.IllegalArgumentException: Invalid input\n\tat com.example.Validator.validate(Validator.java:20)\n\t...java.lang.NullPointerException: Cannot invoke "java.lang.String.length()" because "myObject" is null\n\tat com.example.MyClass.myMethod(MyClass.java:42)\n\tat com.example.AnotherClass.process(AnotherClass.java:105)\n\tat com.example.Main.main(Main.java:15)\nCaused by: java.lang.IllegalArgumentException: Invalid input\n\tat com.example.Validator.validate(Validator.java:20)\n\t...java.lang.NullPointerException: Cannot invoke "java.lang.String.length()" because "myObject" is null\n\tat com.example.MyClass.myMethod(MyClass.java:42)\n\tat com.example.AnotherClass.process(AnotherClass.java:105)\n\tat com.example.Main.main(Main.java:15)\nCaused by: java.lang.IllegalArgumentException: Invalid input\n\tat com.example.Validator.validate(Validator.java:20)\n\t... 3 more',
					errorType: 'An unexpected error occurred',
					externalReferenceCode: 'ERC-1',
					id: 51949,
					status: {
						code: 0,
						label: 'approved',
						label_i18n: 'Approved',
					},
				},
				200,
				{
					'Content-Type': 'application/json',
				}
			);
		})
	);
	worker
		.start({
			onUnhandledRequest: 'bypass',
			serviceWorker: {
				options: {
					scope: '/',
				},
				url: '/o/exportimport-web/ExportImportMockServiceWorker.js',
			},
		})
		.then((registration) => {
			Liferay.on('destroyPortlet', async () => {
				await registration?.unregister?.();
			});
		})
		.catch((error) => {
			console.error('Error starting the service worker:', error);
		});
}
