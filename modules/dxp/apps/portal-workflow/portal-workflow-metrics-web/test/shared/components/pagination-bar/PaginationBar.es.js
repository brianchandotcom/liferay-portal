/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import React, {cloneElement, useState} from 'react';

import PaginationBar from '../../../../src/main/resources/META-INF/resources/js/shared/components/pagination-bar/PaginationBar.es';
import {MockRouter} from '../../../mock/MockRouter.es';

import '@testing-library/jest-dom/extend-expect';

const ContainerProps = ({children, initialPage = 1, initialPageSize = 20}) => {
	const [page, setPage] = useState(initialPage);
	const [pageSize, setPageSize] = useState(initialPageSize);
	const stateProps = {page, pageSize, setPage, setPageSize};

	return (
		<MockRouter withoutRouterProps>
			{cloneElement(children, stateProps)}
		</MockRouter>
	);
};

describe('The PaginationBar component should', () => {
	afterEach(cleanup);

	test('Render with initial params and change pageSize and page using state', () => {
		const {baseElement} = render(
			<PaginationBar routing={false} totalCount={20} />,
			{wrapper: ContainerProps}
		);

		const pageSizeBtn = baseElement.querySelector('button.dropdown-toggle');

		expect(pageSizeBtn).toHaveTextContent('x-entries');

		fireEvent.click(pageSizeBtn);

		const pageSizeOptions = baseElement.querySelectorAll('.dropdown-item');

		expect(pageSizeOptions.length).toBe(6);

		let pageLinks = baseElement.querySelectorAll('button.page-link');

		expect(pageLinks.length).toBe(3);

		expect(pageLinks[0]).toHaveAttribute('disabled');
		expect(pageLinks[1]).not.toHaveAttribute('disabled');
		expect(pageLinks[1]).toHaveTextContent('1');
		expect(pageLinks[2]).toHaveAttribute('disabled');

		fireEvent.click(pageSizeOptions[0]);

		pageLinks = baseElement.querySelectorAll('button.page-link');
		let pageItems = baseElement.querySelectorAll('.page-item');

		expect(pageLinks.length).toBe(6);

		expect(pageLinks[1]).toHaveTextContent('1');
		expect(pageLinks[2]).toHaveTextContent('2');
		expect(pageLinks[3]).toHaveTextContent('3');

		expect(pageItems[1].className.includes('active')).toBe(true);
		expect(pageItems[2].className.includes('active')).toBe(false);
		expect(pageItems[3].className.includes('active')).toBe(false);

		fireEvent.click(pageLinks[3]);

		pageItems = baseElement.querySelectorAll('.page-item');

		expect(pageItems[1].className.includes('active')).toBe(false);
		expect(pageItems[2].className.includes('active')).toBe(false);
		expect(pageItems[3].className.includes('active')).toBe(true);
	});

	test('Render with initial params and change pageSize and page using route params', () => {
		const {baseElement} = render(<PaginationBar totalCount={50} />, {
			wrapper: MockRouter
		});

		const pageSizeBtn = baseElement.querySelector('button.dropdown-toggle');

		expect(pageSizeBtn).toHaveTextContent('x-entries');

		fireEvent.click(pageSizeBtn);

		const pageSizeOptions = baseElement.querySelectorAll('.dropdown-item');

		expect(pageSizeOptions.length).toBe(6);

		let pageLinks = baseElement.querySelectorAll('button.page-link');
		let pageItems = baseElement.querySelectorAll('.page-item');

		expect(pageLinks.length).toBe(5);

		expect(pageLinks[0]).toHaveAttribute('disabled');
		expect(pageLinks[1]).toHaveTextContent('1');
		expect(pageLinks[2]).toHaveTextContent('2');
		expect(pageLinks[3]).toHaveTextContent('3');

		expect(pageItems[1].className.includes('active')).toBe(true);

		expect(pageItems[1].className.includes('active')).toBe(true);
		expect(pageItems[2].className.includes('active')).toBe(false);
		expect(pageItems[3].className.includes('active')).toBe(false);

		fireEvent.click(pageLinks[3]);

		pageItems = baseElement.querySelectorAll('.page-item');

		expect(pageItems[1].className.includes('active')).toBe(false);
		expect(pageItems[2].className.includes('active')).toBe(false);
		expect(pageItems[3].className.includes('active')).toBe(true);

		fireEvent.click(pageSizeOptions[4]);

		pageLinks = baseElement.querySelectorAll('button.page-link');
		pageItems = baseElement.querySelectorAll('.page-item');

		expect(pageLinks.length).toBe(3);

		expect(pageItems[1].className.includes('active')).toBe(true);
		expect(pageLinks[1]).toHaveTextContent('1');
		expect(pageLinks[2]).toHaveAttribute('disabled');
	});

	test('Render with insufficient total count to pagination', () => {
		const {container} = render(<PaginationBar totalCount={4} />, {
			wrapper: MockRouter
		});

		expect(container.innerHTML).toEqual('');
	});
});
