/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal from '@clayui/modal';
import {openToast} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useEffect, useId, useState} from 'react';

import {MemberInvite} from '../components/members';
import MemberService from '../services/MemberService';
import {AutocompleteItem, Member, MemberType} from '../types';

const showErrorMessage = (message: string) => {
	openToast({
		message,
		type: 'danger',
	});
};

const showSuccessMessage = (message: string) => {
	openToast({
		message,
		type: 'success',
	});
};

export default function DesignLibraryManageMembersModal({
	externalReferenceCode,
}: {
	externalReferenceCode: string;
}) {
	const [members, setMembers] = useState<Member[]>([]);
	const [loading, setLoading] = useState(true);
	const listLabelId = useId();

	useEffect(() => {
		const fetchMembers = async () => {
			try {
				const [{items: users}, {items: userGroups}] = await Promise.all(
					[
						MemberService.getUserMembers(externalReferenceCode),
						MemberService.getUserGroupMembers(
							externalReferenceCode
						),
					]
				);

				setMembers([
					...users.map((user): Member => ({...user, type: 'user'})),
					...userGroups.map(
						(userGroup): Member => ({
							...userGroup,
							type: 'userGroup',
						})
					),
				]);
			}
			catch (error: any) {
				showErrorMessage(
					error?.title ??
						error?.message ??
						Liferay.Language.get('unable-to-load-members')
				);
			}
			finally {
				setLoading(false);
			}
		};

		fetchMembers();
	}, [externalReferenceCode]);

	const invite = async (type: MemberType, item: AutocompleteItem) => {
		try {
			if (type === 'user') {
				await MemberService.addUser(
					externalReferenceCode,
					item.externalReferenceCode
				);
			}
			else {
				await MemberService.addUserGroup(
					externalReferenceCode,
					item.externalReferenceCode
				);
			}

			setMembers((currentMembers) => [
				...currentMembers,
				{
					externalReferenceCode: item.externalReferenceCode,
					id: item.id,
					image: item.image,
					name: item.name,
					numberOfUserAccounts: item.usersCount,
					roles: [],
					type,
				},
			]);

			showSuccessMessage(
				sub(
					Liferay.Language.get('x-was-successfully-added'),
					`<strong>${Liferay.Util.escapeHTML(item.name)}</strong>`
				)
			);
		}
		catch (error: any) {
			showErrorMessage(
				error?.title ??
					error?.message ??
					Liferay.Language.get('unable-to-add-member')
			);

			throw error;
		}
	};

	return (
		<>
			<ClayModal.Header
				closeButtonAriaLabel={Liferay.Language.get('close')}
			>
				{Liferay.Language.get('manage-members')}
			</ClayModal.Header>

			<ClayModal.Item>
				<MemberInvite invite={invite} members={members} />
			</ClayModal.Item>

			<ClayModal.Body>
				{loading ? (
					<ClayLoadingIndicator />
				) : (
					<>
						<h2
							className="c-mb-2 c-mt-n2 d-block text-4"
							id={listLabelId}
						>
							{Liferay.Language.get('who-has-access')}
						</h2>

						{members.length ? (
							<ul
								aria-labelledby={listLabelId}
								className="list-unstyled mb-0"
							>
								{members.map((member) => (
									<li
										className="c-py-2 text-3"
										key={`${member.type}-${member.externalReferenceCode}`}
									>
										{member.name}
									</li>
								))}
							</ul>
						) : (
							<div className="border-top c-ml-n4 c-mr-n4 c-p-4 c-pb-0 text-center">
								<p className="c-mb-1 c-mt-2 font-weight-semi-bold text-4">
									{Liferay.Language.get('no-members-yet')}
								</p>

								<p className="c-m-0 text-3 text-secondary">
									{Liferay.Language.get(
										'add-members-to-this-space'
									)}
								</p>
							</div>
						)}
					</>
				)}
			</ClayModal.Body>
		</>
	);
}
