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

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayMultiSelect from '@clayui/multi-select';
import {getOpener} from 'frontend-js-web';
import React, {useState} from 'react';

function InviteUsersForm({
	accountEntryId,
	availableAccountRoles,
	portletNamespace,
	redirectURL,
}) {
	const [count, setCount] = useState(1);

	const closeModal = () => {
		const openerWindow = getOpener();

		openerWindow.Liferay.fire('closeModal');
	};

	const InviteUserFormGroup = ({index}) => {
		return (
			<ClayLayout.Sheet size="lg">
				<ClayForm.Group>
					<label
						htmlFor={`${portletNamespace}emailAddressesMultiSelect${index}`}
					>
						{Liferay.Language.get('emails')}

						<ClayIcon
							className="ml-1 reference-mark"
							symbol="asterisk"
						/>
					</label>

					<ClayInput.Group>
						<ClayInput.GroupItem>
							<ClayMultiSelect
								id={`${portletNamespace}emailAddressesMultiSelect${index}`}
							/>
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</ClayForm.Group>

				<ClayForm.Group>
					<label
						htmlFor={`${portletNamespace}accountRolesMultiSelect${index}`}
					>
						{Liferay.Language.get('roles')}
					</label>

					<ClayInput.Group>
						<ClayInput.GroupItem>
							<ClayMultiSelect
								id={`${portletNamespace}accountRolesMultiSelect${index}`}
								sourceItems={availableAccountRoles}
							/>

							<ClayForm.FeedbackGroup>
								<ClayForm.Text>
									{Liferay.Language.get(
										'roles-will-be-applied-to-all-users-above'
									)}
								</ClayForm.Text>
							</ClayForm.FeedbackGroup>
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</ClayForm.Group>
			</ClayLayout.Sheet>
		);
	};

	const submitForm = () => {
		const openerWindow = getOpener();

		openerWindow.Liferay.fire(`${portletNamespace}inviteUsers`, {
			accountEntryId,
			redirect: `${redirectURL}`,
		});
	};

	return (
		<ClayForm className="lfr-form-content">
			{[...Array(count)].map((_, index) => (
				<InviteUserFormGroup index={index} key={index} />
			))}

			<ClayLayout.SheetFooter>
				<ClayButton
					displayType="secondary"
					onClick={() => setCount(count + 1)}
				>
					<span className="inline-item inline-item-before">
						<ClayIcon symbol="plus" />
					</span>

					{Liferay.Language.get('add-entry')}
				</ClayButton>
			</ClayLayout.SheetFooter>

			<ClayLayout.SheetFooter className="dialog-footer">
				<ClayButton
					displayType="primary"
					onClick={submitForm}
					type="submit"
				>
					{Liferay.Language.get('invite')}
				</ClayButton>

				<ClayButton displayType="secondary" onClick={closeModal}>
					{Liferay.Language.get('cancel')}
				</ClayButton>
			</ClayLayout.SheetFooter>
		</ClayForm>
	);
}

export default InviteUsersForm;
