/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal from '@clayui/modal';
import {openToast} from 'frontend-js-components-web';
import React, {useEffect, useId, useState} from 'react';

import {MemberInvite} from '../components/members';
import MemberService from '../services/MemberService';
import {Member} from '../types';

const showErrorMessage = (message: string) => {
	openToast({
		message,
		type: 'danger',
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
		let active = true;

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

				if (!active) {
					return;
				}

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
				if (!active) {
					return;
				}

				showErrorMessage(
					error?.title ??
						error?.message ??
						Liferay.Language.get('unable-to-load-members')
				);
			}
			finally {
				if (active) {
					setLoading(false);
				}
			}
		};

		fetchMembers();

		return () => {
			active = false;
		};
	}, [externalReferenceCode]);

	return (
		<>
			<ClayModal.Header
				closeButtonAriaLabel={Liferay.Language.get('close')}
			>
				{Liferay.Language.get('manage-members')}
			</ClayModal.Header>

			<ClayModal.Item>
				<MemberInvite
					externalReferenceCode={externalReferenceCode}
					members={members}
					onMemberAdded={(member) =>
						setMembers((currentMembers) => [
							...currentMembers,
							member,
						])
					}
				/>
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
