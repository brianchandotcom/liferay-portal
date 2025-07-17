/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {render, screen, within} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import {MembersListItem} from '../../../../src/main/resources/META-INF/resources/js/main_view/spaces/MemberListItem';

jest.mock('frontend-js-web', () => ({
	sub: (str: string, arg: string) => str.replace('x', arg),
}));

describe('MemberListItem', () => {
	const testUserAccount = {
		emailAddress: 'brian.smith@example.com',
		id: 'user',
		image: '/images/brian_smith.png',
		name: 'Brian Smith',
	};

	const testUserGroup = {
		id: 'group',
		name: 'Sample Group',
		numberOfUserAccounts: '5',
	};

	const props = {
		currentUserId: testUserAccount.id,
		emptyMessage: 'No users',
		onRemoveItem: jest.fn(),
	};

	it('renders default message when items is empty', () => {
		render(<MembersListItem {...props} itemType="user" items={[]} />);

		expect(screen.getByRole('listitem')).toHaveTextContent('No users');
	});

	it('renders correctly when items is user', () => {
		render(
			<MembersListItem
				{...props}
				itemType="user"
				items={[testUserAccount]}
			/>
		);

		const listItemElement = screen.getByRole('listitem');
		expect(listItemElement).toBeInTheDocument();
		expect(listItemElement).toHaveTextContent(
			`${testUserAccount.name}(you)`
		);

		expect(
			within(listItemElement).getByRole('button', {name: 'remove-user'})
		).toBeInTheDocument();

		const image = within(listItemElement).getByAltText(
			testUserAccount.name
		);
		expect(image).toHaveAttribute('src', testUserAccount.image);
	});

	it('renders a user with fallback image and without the (you) tag', () => {
		const anotherUser = {
			emailAddress: 'another.user@example.com',
			id: 'another-user-id',
			name: 'Another User',
		};

		render(
			<MembersListItem {...props} itemType="user" items={[anotherUser]} />
		);

		const listItemElement = screen.getByRole('listitem');
		expect(listItemElement).toHaveTextContent(anotherUser.name);
		expect(listItemElement).not.toHaveTextContent('(you)');

		const image = within(listItemElement).getByAltText(anotherUser.name);
		expect(image).toHaveAttribute('src', '/image/user_portrait');
	});

	it('renders the word owner and hides the remove button when the user is the owner', () => {
		render(
			<MembersListItem
				{...props}
				assetLibraryCreatorUserId={testUserAccount.id}
				itemType="user"
				items={[testUserAccount]}
			/>
		);

		expect(screen.getByRole('listitem')).toHaveTextContent(
			`${testUserAccount.name}(you)(owner)`
		);

		expect(
			screen.queryByRole('button', {name: /remove/i})
		).not.toBeInTheDocument();
	});

	it('renders correctly when items is group', () => {
		render(
			<MembersListItem
				{...props}
				itemType="group"
				items={[testUserGroup]}
			/>
		);

		const listItemElement = screen.getByRole('listitem');
		expect(listItemElement).toBeInTheDocument();
		expect(listItemElement).toHaveTextContent(testUserGroup.name);
		expect(listItemElement).toHaveTextContent(
			`(${testUserGroup.numberOfUserAccounts}-members)`
		);

		expect(
			within(listItemElement).getByRole('button', {name: 'remove-group'})
		).toBeInTheDocument();
	});

	it('renders correctly when items is group and there is no members', () => {
		const testUserGroupWithoutMembers = {
			id: 'group',
			name: 'Sample Group',
		};

		render(
			<MembersListItem
				{...props}
				itemType="group"
				items={[testUserGroupWithoutMembers]}
			/>
		);

		const listItemElement = screen.getByRole('listitem');
		expect(listItemElement).toBeInTheDocument();
		expect(listItemElement).toHaveTextContent(
			testUserGroupWithoutMembers.name
		);
		expect(listItemElement).toHaveTextContent('(0-members)');
	});

	it.each([
		['user', [testUserAccount]],
		['group', [testUserGroup]],
	])(
		'calls onRemoveItem when the remove %s button is clicked',
		async (itemType, items) => {
			const onRemoveItem = jest.fn();

			render(
				<MembersListItem
					{...props}
					itemType={itemType as 'user' | 'group'}
					items={items}
					onRemoveItem={onRemoveItem}
				/>
			);

			expect(onRemoveItem).not.toHaveBeenCalled();

			await userEvent.click(
				screen.getByRole('button', {name: `remove-${itemType}`})
			);

			expect(onRemoveItem).toHaveBeenCalledTimes(1);
			expect(onRemoveItem).toHaveBeenCalledWith(items[0]);
		}
	);
});
