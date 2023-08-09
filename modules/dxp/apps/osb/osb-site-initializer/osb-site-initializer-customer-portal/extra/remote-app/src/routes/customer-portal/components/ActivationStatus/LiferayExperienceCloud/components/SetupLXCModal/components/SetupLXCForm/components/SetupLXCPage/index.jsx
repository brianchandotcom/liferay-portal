/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import {FieldArray} from 'formik';
import {useEffect, useState} from 'react';
import i18n from '../../../../../../../../../../../common/I18n';
import {
	Button,
	Input,
	Select,
} from '../../../../../../../../../../../common/components';
import SetupHighPriorityContactForm, {
	HIGH_PRIORITY_CONTACT_CATEGORIES,
} from '../../../../../../../../../../../common/components/HighPriorityContacts/SetupHighPriorityContact';
import Layout from '../../../../../../../../../../../common/containers/setup-forms/Layout';
import getInitialLxcAdmins from '../../utils/getInitialLxcAdmins';
import AdminInputs from './components/AdminsInput';
import useGetPrimaryRegionList from './hooks/useGetPrimaryRegionList';
import useSubmitLXCEnvironment from './hooks/useSubmitLXCEnvironment';
const INITIAL_SETUP_ADMIN_COUNT = 1;

const SetupLiferayExperienceCloudPage = ({
	errors,
	handleChangeForm,
	handleOnLeftButtonClick,
	leftButton,
	project,
	setFormAlreadySubmitted,
	subscriptionGroupLxcId,
	touched,
	values,
}) => {
	const [baseButtonDisabled, setBaseButtonDisabled] = useState(true);
	const [
		addHighPriorityContactList,
		setAddHighPriorityContactList,
	] = useState([]);
	const [
		removeHighPriorityContactList,
		setRemoveHighPriorityContactList,
	] = useState([]);
	const [step, setStep] = useState(1);

	const handlePreviousStep = () => {
		setStep(step - 1);
	};

	const handleNextStep = () => {
		setStep(step + 1);
	};

	const handleButtonClick = () => {
		// eslint-disable-next-line no-unused-expressions
		step === 1 ? handleOnLeftButtonClick() : handlePreviousStep();
	};

	const addHighPriorityContacts = (contactList) => {
		setAddHighPriorityContactList((oldList) => {
			const uniqueContacts = [
				...oldList,
				...contactList.filter(
					(contact) =>
						!oldList.some(
							(oldContact) => oldContact.id === contact.id
						)
				),
			];

			return uniqueContacts;
		});
	};

	const removeHighPriorityContacts = (contactList) => {
		setRemoveHighPriorityContactList((oldList) => {
			const uniqueContacts = [
				...oldList,
				...contactList.filter(
					(contact) =>
						!oldList.some(
							(oldContact) => oldContact.id === contact.id
						)
				),
			];

			return uniqueContacts;
		});
	};

	const primaryRegionList = useGetPrimaryRegionList();

	useEffect(() => {
		const hasTouched = !Object.keys(touched).length;
		const hasError = Object.keys(errors).length;

		setBaseButtonDisabled(hasTouched || hasError);
	}, [touched, errors]);

	const handleSubmitLxcEnvironment = useSubmitLXCEnvironment(
		handleChangeForm,
		project,
		setFormAlreadySubmitted,
		addHighPriorityContactList,
		removeHighPriorityContactList,
		subscriptionGroupLxcId,
		values
	);

	return (
		<Layout
			className="pt-1 px-3"
			footerProps={{
				leftButton: (
					<Button
						borderless
						className="text-neutral-10"
						onClick={() => {
							handleButtonClick();
						}}
					>
						{step === 1 ? leftButton : i18n.translate('previous')}
					</Button>
				),
				middleButton: (
					<Button
						disabled={baseButtonDisabled}
						displayType="primary"
						onClick={
							step === 1
								? handleNextStep
								: handleSubmitLxcEnvironment
						}
					>
						{step === 1
							? i18n.translate('next')
							: i18n.translate('submit')}
					</Button>
				),
			}}
			headerProps={{
				helper: i18n.translate(
					'we-ll-need-a-few-details-to-finish-creating-your-liferay-experience-cloud-workspace'
				),
				title: i18n.translate('set-up-liferay-experience-cloud'),
			}}
		>
			{step === 1 && (
				<FieldArray
					name="lxc.admins"
					render={({pop, push}) => (
						<>
							<div className="d-flex justify-content-between mb-2 pb-1 pl-3">
								<div className="mr-4 pr-2">
									<label>
										{i18n.translate('organization-name')}
									</label>

									<p className="dxp-cloud-project-name text-neutral-6 text-paragraph-lg">
										<strong>{project.name}</strong>
									</p>
								</div>
							</div>
							<ClayForm.Group className="mb-0">
								<ClayForm.Group className="mb-0 pb-1">
									<Input
										groupStyle="pb-1"
										helper={i18n.translate(
											'lowercase-letters-and-numbers-only-project-ids-cannot-be-changed'
										)}
										label={i18n.translate('project-id')}
										name="lxc.projectId"
										required
										type="text"
									/>

									<Select
										groupStyle="mb-0"
										key={primaryRegionList}
										label={i18n.translate('primary-region')}
										name="lxc.primaryRegion"
										options={primaryRegionList}
										required
									/>
								</ClayForm.Group>

								<ClayForm.Group className="mb-0">
									{values.lxc.admins.map((admin, index) => (
										<AdminInputs
											admin={admin}
											id={index}
											key={index}
										/>
									))}
								</ClayForm.Group>
							</ClayForm.Group>

							{values?.lxc?.admins?.length >
								INITIAL_SETUP_ADMIN_COUNT && (
								<Button
									className="ml-3 my-2 text-brandy-secondary"
									displayType="secondary"
									onClick={() => {
										pop();
										setBaseButtonDisabled(false);
									}}
									prependIcon="hr"
									small
								>
									{i18n.translate('remove-project-admin')}
								</Button>
							)}

							<Button
								className="cp-btn-add-dxp-cloud ml-3 my-2 rounded-xs"
								onClick={() => {
									push(
										getInitialLxcAdmins(values?.lxc?.admins)
									);
									setBaseButtonDisabled(true);
								}}
								prependIcon="plus"
								small
							>
								{i18n.translate('add-another-admin')}
							</Button>

							<hr />
						</>
					)}
				/>
			)}

			{step === 2 && (
				<div>
					<SetupHighPriorityContactForm
						addContactList={addHighPriorityContacts}
						filter={
							HIGH_PRIORITY_CONTACT_CATEGORIES.criticalIncidentContact
						}
						removedContactList={removeHighPriorityContacts}
					/>

					<SetupHighPriorityContactForm
						addContactList={addHighPriorityContacts}
						filter={
							HIGH_PRIORITY_CONTACT_CATEGORIES.privacyBreachContact
						}
						removedContactList={removeHighPriorityContacts}
					/>

					<SetupHighPriorityContactForm
						addContactList={addHighPriorityContacts}
						filter={
							HIGH_PRIORITY_CONTACT_CATEGORIES.securityBreachContact
						}
						removedContactList={removeHighPriorityContacts}
					/>
				</div>
			)}
		</Layout>
	);
};

export default SetupLiferayExperienceCloudPage;
