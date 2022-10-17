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

import {Button, useModal} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import ClayModal from '@clayui/modal';
import classNames from 'classnames';
import {useState} from 'react';
import i18n from '../../../../../../../../../common/I18n';
import {Skeleton} from '../../../../../../../../../common/components';
import InviteTeamMembersForm from '../../../../../../../../../common/containers/setup-forms/InviteTeamMembersForm';
import SearchBar from '../../../../../../../components/SearchBar';
import PopoverIconButton from '../PopoverIconButton/PopoverIconButton';

const TeamMembersTableHeader = ({
	articleAccountSupportURL,
	availableSupportSeatsCount,
	count,
	hasAdministratorRole,
	koroneikiAccount,
	loading,
	onSearch,
	searching,
	sessionId,
}) => {
	const [searchTerm, setSearchTerm] = useState('');
	const [clearSearchTerm, setClearSearchTerm] = useState(false);

	const {observer, onOpenChange, open} = useModal();

	return (
		<>
			{open && (
				<ClayModal center observer={observer}>
					<InviteTeamMembersForm
						availableAdministratorAssets={
							availableSupportSeatsCount
						}
						handlePage={() => onOpenChange(false)}
						leftButton={i18n.translate('cancel')}
						project={{
							...koroneikiAccount,
							id:
								koroneikiAccount.r_accountEntryToKoroneikiAccount_accountEntryId,
						}}
						sessionId={sessionId}
					/>
				</ClayModal>
			)}

			<div className="bg-neutral-1 d-flex flex-column px-3 py-3 rounded">
				<div className="d-flex">
					<div>
						<SearchBar
							clearSearchTerm={clearSearchTerm}
							onSearchSubmit={(term) => {
								setSearchTerm(term);
								onSearch(term);
								setClearSearchTerm(false);
							}}
						/>
					</div>

					<div className="align-items-center d-flex ml-auto">
						{koroneikiAccount?.maxRequestors > 0 && (
							<>
								<PopoverIconButton
									alignPosition="top"
									articleAccountSupportURL={
										articleAccountSupportURL
									}
								/>

								<p className="font-weight-bold m-0">
									{i18n.translate('support-seats-available')}:
									&nbsp;
								</p>

								{loading ? (
									<Skeleton height={24} width={42} />
								) : (
									<p
										className={classNames(
											'font-weight-semi-bold m-0 text-neutral-7',
											{
												'mr-4': !hasAdministratorRole,
											}
										)}
									>
										{i18n.sub('x-of-x-available', [
											availableSupportSeatsCount,
											koroneikiAccount.maxRequestors,
										])}
									</p>
								)}
							</>
						)}

						{hasAdministratorRole && (
							<Button
								className="bg-white ml-3 px-3 py-2"
								displayType="primary"
								onClick={() => {
									onOpenChange(true);
									setClearSearchTerm(true);
								}}
								outline
							>
								<span className="inline-item inline-item-before mr-2">
									<ClayIcon symbol="user-plus" />
								</span>

								{i18n.translate('invite')}
							</Button>
						)}
					</div>
				</div>

				<div className="d-flex">
					{Boolean(searchTerm) && !searching && (
						<p className="font-weight-semi-bold m-0 mt-3 text-paragraph-sm">
							{count > 1
								? i18n.sub('x-results-for-x', [
										count,
										searchTerm,
								  ])
								: i18n.sub('x-result-for-x', [
										count,
										searchTerm,
								  ])}
						</p>
					)}
				</div>
			</div>
		</>
	);
};

export default TeamMembersTableHeader;
