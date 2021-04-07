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
import {ConfigProvider} from 'data-engine-js-components-web/js/core/hooks/useConfig.es';
import {
	FormProvider,
	useFormState,
} from 'data-engine-js-components-web/js/core/hooks/useForm.es';
import {PageProvider} from 'data-engine-js-components-web/js/core/hooks/usePage.es';
import {EVENT_TYPES} from 'data-engine-js-components-web/js/custom/form/eventTypes.es';
import {pageReducer} from 'data-engine-js-components-web/js/custom/form/reducers/index.es';
import {PageHeader} from 'data-engine-js-components-web/js/custom/form/renderer/MultiPagesVariant.es';
import React from 'react';

import mockPages from '../__mock__/mockPages.es';

const WithProvider = ({children, config, onAction, page, state}) => (
	<ConfigProvider value={config}>
		<FormProvider
			onAction={onAction}
			reducers={[pageReducer]}
			value={state}
		>
			<PageProvider value={page}>{children}</PageProvider>
		</FormProvider>
	</ConfigProvider>
);

const PageHeaderWithProviderConsumer = () => {
	const {pages} = useFormState();

	return (
		<PageHeader
			localizedDescription={pages[0].localizedDescription}
			localizedTitle={pages[0].localizedTitle}
		/>
	);
};

const defaultProps = {
	config: {
		defaultLanguageId: 'en_US',
	},
	page: {
		pageIndex: 0,
	},
	state: {
		editingLanguageId: 'en_US',
		pages: mockPages,
	},
};

describe('MultiPagesVariant.PageHeader', () => {
	afterEach(cleanup);

	beforeEach(() => {
		jest.useFakeTimers();
	});

	it('renders', () => {
		const {localizedDescription, localizedTitle} = mockPages[
			defaultProps.page.pageIndex
		];

		const {container} = render(
			<WithProvider {...defaultProps}>
				<PageHeaderWithProviderConsumer />
			</WithProvider>
		);

		const pageHeaderDescription = container.querySelector(
			'.form-builder-page-header-description'
		);

		expect(pageHeaderDescription.value).toBe(
			localizedDescription[defaultProps.state.editingLanguageId]
		);

		const pageHeaderTitle = container.querySelector(
			'.form-builder-page-header-title'
		);

		expect(pageHeaderTitle.placeholder).toBe('page-title');
		expect(pageHeaderTitle.value).toBe(
			localizedTitle[defaultProps.state.editingLanguageId]
		);
	});

	describe('Actions', () => {
		it('dispatch the DESCRIPTION_CHANGE action when changing the page description', () => {
			const onAction = jest.fn();

			const {container} = render(
				<WithProvider {...defaultProps} onAction={onAction}>
					<PageHeaderWithProviderConsumer />
				</WithProvider>
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

			const {pageIndex} = defaultProps.page;

			expect(input.value).toBe(newDescription);

			expect(onAction).toHaveBeenCalledWith({
				payload: {pageIndex, value: newDescription},
				type: EVENT_TYPES.PAGE.DESCRIPTION_CHANGE,
			});
		});

		it('dispatch the DESCRIPTION_CHANGE action when changing the page description in pt_BR language', () => {
			const onAction = jest.fn();

			const {container} = render(
				<WithProvider
					{...defaultProps}
					onAction={onAction}
					state={{...defaultProps.state, editingLanguageId: 'pt_BR'}}
				>
					<PageHeaderWithProviderConsumer />
				</WithProvider>
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

			const {pageIndex} = defaultProps.page;

			expect(input.value).toBe(newDescription);

			expect(onAction).toHaveBeenCalledWith({
				payload: {pageIndex, value: newDescription},
				type: EVENT_TYPES.PAGE.DESCRIPTION_CHANGE,
			});
		});

		it('dispatch the TITLE_CHANGE action when changing the page title', () => {
			const onAction = jest.fn();

			const {container} = render(
				<WithProvider {...defaultProps} onAction={onAction}>
					<PageHeaderWithProviderConsumer />
				</WithProvider>
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

			const {pageIndex} = defaultProps.page;

			expect(input.value).toBe(newTitle);

			expect(onAction).toHaveBeenCalledWith({
				payload: {pageIndex, value: newTitle},
				type: EVENT_TYPES.PAGE.TITLE_CHANGE,
			});
		});

		it('dispatch the TITLE_CHANGE action when changing the page title in pt_BR language', () => {
			const onAction = jest.fn();

			const {container} = render(
				<WithProvider
					{...defaultProps}
					onAction={onAction}
					state={{...defaultProps.state, editingLanguageId: 'pt_BR'}}
				>
					<PageHeaderWithProviderConsumer />
				</WithProvider>
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

			const {pageIndex} = defaultProps.page;

			expect(input.value).toBe(newTitle);

			expect(onAction).toHaveBeenCalledWith({
				payload: {pageIndex, value: newTitle},
				type: EVENT_TYPES.PAGE.TITLE_CHANGE,
			});
		});
	});
});
