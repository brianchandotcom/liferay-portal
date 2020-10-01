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

import {act, cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import {PageHeader} from '../../../src/main/resources/META-INF/resources/js/components/PageRenderer/EditablePageHeader.es';
import {FormNoopProvider} from '../../../src/main/resources/META-INF/resources/js/hooks/useForm.es';
import {PageProvider} from '../../../src/main/resources/META-INF/resources/js/hooks/usePage.es';
import mockPages from '../__mock__/mockPages.es';

const PageHeaderWithProvider = ({
	description,
	onEvent,
	placeholder,
	title,
	value,
}) => (
	<FormNoopProvider onEvent={onEvent}>
		<PageProvider value={value}>
			<PageHeader
				description={description}
				placeholder={placeholder}
				title={title}
			/>
		</PageProvider>
	</FormNoopProvider>
);

const props = {
	description: 'Description',
	placeholder: 'Placeholder',
	title: 'Title',
	value: {
		editingLanguageId: 'en_US',
		pageIndex: 0,
		pages: mockPages,
	},
};

describe('Editable Page Header', () => {
	afterEach(cleanup);

	beforeEach(() => {
		jest.useFakeTimers();
	});

	it('renders', () => {
		const {container} = render(<PageHeaderWithProvider {...props} />);

		const pageHeaderDescription = container.querySelector(
			'.form-builder-page-header-description'
		);

		expect(pageHeaderDescription.value).toBe('Description');

		const pageHeaderTitle = container.querySelector(
			'.form-builder-page-header-title'
		);

		expect(pageHeaderTitle.placeholder).toBe('Placeholder');
		expect(pageHeaderTitle.value).toBe('Title');
	});

	describe('Page header events', () => {
		it('emits the pagesUpdated event when changing the page description', () => {
			const onEvent = jest.fn();

			const {container} = render(
				<PageHeaderWithProvider {...props} onEvent={onEvent} />
			);

			const input = container.querySelector(
				'.form-builder-page-header-description'
			);

			const newDescription = 'New Description';

			fireEvent.change(input, {
				target: {
					value: newDescription,
				},
			});

			act(() => {
				jest.runAllTimers();
			});

			const {value} = props;
			const {editingLanguageId, pageIndex, pages} = value;

			expect(input.value).toBe(newDescription);

			expect(onEvent).toHaveBeenCalledWith('pagesUpdated', [
				{
					...pages[pageIndex],
					description: newDescription,
					localizedDescription: {
						...pages[pageIndex].localizedDescription,
						[editingLanguageId]: newDescription,
					},
				},
			]);
		});

		it('emits the pagesUpdated event when changing the page description in pt_BR language', () => {
			const onEvent = jest.fn();

			const {container} = render(
				<PageHeaderWithProvider
					{...props}
					onEvent={onEvent}
					value={{
						...props.value,
						editingLanguageId: 'pt_BR',
					}}
				/>
			);

			const input = container.querySelector(
				'.form-builder-page-header-description'
			);

			const newDescription = 'Nova Descrição';

			fireEvent.change(input, {
				target: {
					value: newDescription,
				},
			});

			act(() => {
				jest.runAllTimers();
			});

			const {value} = props;
			const {pageIndex, pages} = value;

			expect(input.value).toBe(newDescription);

			expect(onEvent).toHaveBeenCalledWith('pagesUpdated', [
				{
					...pages[pageIndex],
					description: newDescription,
					localizedDescription: {
						...pages[pageIndex].localizedDescription,
						['pt_BR']: newDescription,
					},
				},
			]);
		});

		it('emits the pagesUpdated event when changing the page title', () => {
			const onEvent = jest.fn();

			const {container} = render(
				<PageHeaderWithProvider {...props} onEvent={onEvent} />
			);

			const input = container.querySelector(
				'.form-builder-page-header-title'
			);

			const newTitle = 'New Title';

			fireEvent.change(input, {
				target: {
					value: newTitle,
				},
			});

			act(() => {
				jest.runAllTimers();
			});

			const {value} = props;
			const {editingLanguageId, pageIndex, pages} = value;

			expect(input.value).toBe(newTitle);

			expect(onEvent).toHaveBeenCalledWith('pagesUpdated', [
				{
					...pages[pageIndex],
					localizedTitle: {
						...pages[pageIndex].localizedTitle,
						[editingLanguageId]: newTitle,
					},
					title: newTitle,
				},
			]);
		});

		it('emits the pagesUpdated event when changing the page title in pt_BR language', () => {
			const onEvent = jest.fn();

			const {container} = render(
				<PageHeaderWithProvider
					{...props}
					onEvent={onEvent}
					value={{
						...props.value,
						editingLanguageId: 'pt_BR',
					}}
				/>
			);

			const input = container.querySelector(
				'.form-builder-page-header-title'
			);

			const newTitle = 'Novo Título';

			fireEvent.change(input, {
				target: {
					value: newTitle,
				},
			});

			act(() => {
				jest.runAllTimers();
			});

			const {value} = props;
			const {pageIndex, pages} = value;

			expect(input.value).toBe(newTitle);

			expect(onEvent).toHaveBeenCalledWith('pagesUpdated', [
				{
					...pages[pageIndex],
					localizedTitle: {
						...pages[pageIndex].localizedTitle,
						['pt_BR']: newTitle,
					},
					title: newTitle,
				},
			]);
		});
	});
});
