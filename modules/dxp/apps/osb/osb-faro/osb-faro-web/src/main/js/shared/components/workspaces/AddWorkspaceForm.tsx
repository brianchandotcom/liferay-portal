import ClayButton from '@clayui/button';
import Constants from 'shared/util/constants';
import Form, {
	validateMaxLength,
	validateMinLength,
	validatePattern,
	validateRequired
} from 'shared/components/form';
import getCN from 'classnames';
import Loading, {Align} from 'shared/components/Loading';
import NavigationWarning from 'shared/components/NavigationWarning';
import React, {useContext, useRef, useState} from 'react';
import Sheet from 'shared/components/Sheet';
import TimeZonePicker from '../form/TimeZonePicker';
import URLConstants from 'shared/util/url-constants';
import {BasePageContext} from './BasePage';
import {close, open} from 'shared/actions/modals';
import {connect} from 'react-redux';
import {Formik} from 'formik';
import {Modal} from 'shared/types';
import {Project, TimeZone} from 'shared/util/records';
import {sequence} from 'shared/util/promise';
import {sub} from 'shared/util/lang';
import {
	validateEmail,
	validateEmailArr,
	validateEmailDomain,
	validateEmailDomainArr
} from 'shared/util/email-validators';

const {
	faroURL,
	projectLocations: {AS1, DEV, EU2, EU3, SA, UAT, US}
} = Constants;

const DEFAULT_TIME_ZONE = 'UTC';

const getProjectLocations = (): {label: string; value: string}[] => {
	switch (FARO_ENV) {
		case 'dev':
			return [{label: Liferay.Language.get('location-dev'), value: DEV}];
		case 'uat':
			return [{label: Liferay.Language.get('location-uat'), value: UAT}];
		default:
			return [
				{label: Liferay.Language.get('location-as1'), value: AS1},
				{label: Liferay.Language.get('location-eu'), value: EU2},
				{label: Liferay.Language.get('location-eu2'), value: EU3},
				{label: Liferay.Language.get('location-sa'), value: SA},
				{label: Liferay.Language.get('location-us'), value: US}
			];
	}
};

const getDefaultServerLocation = (): string => {
	switch (FARO_ENV) {
		case 'dev':
			return DEV;
		case 'uat':
			return UAT;
		default:
			return US;
	}
};

interface IAddWorkspaceFormProps extends React.HTMLAttributes<HTMLElement> {
	close: Modal.close;
	disabled: boolean;
	editing: boolean;
	emailAddressDomains: string[];
	onSubmit: (values) => Promise<any>;
	open: Modal.open;
	project?: Project;
}

const AddWorkspaceForm: React.FC<IAddWorkspaceFormProps> = ({
	className,
	close,
	disabled = false,
	editing = false,
	emailAddressDomains,
	onSubmit,
	open,
	project
}) => {
	const {currentUser} = useContext(BasePageContext);

	const formRef = useRef<Formik>();

	const [inputListValue, setInputListValue] = useState();
	const [
		emailAddressesInputValues,
		setEmailAddressesInputValues
	] = useState();

	const handleSubmit = (
		values,
		{resetForm, setFieldError, setSubmitting}
	) => {
		const {initialValues} = formRef.current;
		const {friendlyURL: initialFriendlyURL} = initialValues;

		const {friendlyURL: newFriendlyURL} = values;

		const submitFn = () =>
			onSubmit({
				...values,
				ownerEmailAddress:
					project?.ownerEmailAddress || currentUser.emailAddress
			})
				.then(() => {
					setSubmitting(false);

					if (
						editing &&
						project.getIn(['timeZone', 'timeZoneId']) !==
							values.timeZoneId
					) {
						analytics.track('Updated Timezone');
					}

					if (initialFriendlyURL === newFriendlyURL) {
						resetForm(values);
					}
				})
				.catch(({field, message}) => {
					setSubmitting(false);

					if (field) {
						setFieldError(field, message);
					}
				});

		if (newFriendlyURL !== initialFriendlyURL) {
			setSubmitting(false);

			open(Modal.modalTypes.CONFIRMATION_MODAL, {
				message: (
					<div>
						<p className='text-secondary'>
							{Liferay.Language.get(
								'you-can-only-set-your-friendly-workspace-url-once.-are-you-sure-you-would-like-to-save-it-as-the-following-url'
							)}
						</p>

						<p>
							<span className='text-secondary'>
								{`${faroURL}/workspace/`}
							</span>

							<b>{newFriendlyURL}</b>
						</p>
					</div>
				),
				modalVariant: 'modal-info',
				onClose: close,
				onSubmit: submitFn,
				submitMessage: editing
					? Liferay.Language.get('save')
					: Liferay.Language.get('create-workspace'),
				title: Liferay.Language.get('setting-friendly-workspace-url'),
				titleIcon: 'info-circle'
			});
		} else {
			submitFn();
		}
	};

	return (
		<div className={getCN('add-workspace-form-root', className)}>
			<Sheet>
				<Form
					enableReinitialize
					initialValues={{
						emailAddressDomains: emailAddressDomains || [],
						friendlyURL:
							project?.friendlyURL?.replace('/', '') || '',
						incidentReportEmailAddresses:
							project?.incidentReportEmailAddresses?.toArray() ||
							[],
						name: (project && project.name) || '',
						ownerEmailAddress:
							project?.ownerEmailAddress ||
							currentUser.emailAddress,
						serverLocation:
							project?.serverLocation ||
							getDefaultServerLocation(),
						timeZoneId:
							project?.getIn(['timeZone', 'timeZoneId']) ||
							DEFAULT_TIME_ZONE
					}}
					onSubmit={handleSubmit}
					ref={formRef}
				>
					{({
						dirty,
						handleSubmit,
						initialValues,
						isSubmitting,
						isValid,
						resetForm,
						setFieldTouched,
						setFieldValue
					}) => (
						<Form.Form onSubmit={handleSubmit}>
							<NavigationWarning when={!!project && dirty} />

							<Sheet.Header>
								<Sheet.Subtitle>
									{Liferay.Language.get('general')}
								</Sheet.Subtitle>

								<Sheet.Section className='input-name'>
									<Form.Input
										disabled={disabled}
										label={Liferay.Language.get(
											'workspace-name'
										)}
										name='name'
										required
										validate={sequence([
											validateRequired,
											validateMaxLength(255)
										])}
									/>
								</Sheet.Section>

								<Sheet.Section className='input-workspace-owner-email'>
									<Form.Input
										disabled
										label={Liferay.Language.get(
											'workspace-owner-email'
										)}
										name='ownerEmailAddress'
										required
									/>
								</Sheet.Section>

								<Sheet.Section className='input-server'>
									<Form.Select
										data-testid='server-location-input'
										disabled={
											disabled ||
											editing ||
											(project && project.serverLocation)
										}
										label={Liferay.Language.get(
											'data-center-location'
										)}
										name='serverLocation'
										required
										secondaryInfo={Liferay.Language.get(
											'select-a-server-to-store-your-data.-this-could-have-implications-to-your-organizations-policy-on-user-data-storage'
										)}
									>
										{getProjectLocations().map(
											({label, value}) => (
												<Form.Select.Item
													key={value}
													value={value}
												>
													{label}
												</Form.Select.Item>
											)
										)}
									</Form.Select>

									{/* <p class="extra-instruction text-secondary">
											{sub(
												Liferay.Language.get(
													'cant-find-the-right-server?-send-us-a-x'
												),
												[
													// TODO: This should in the future direct to a
													// suggestion form in the app
													<a href="#1" key="suggestion">
														{Liferay.Language.get(
															'suggestion-fragment'
														)}
													</a>
												],
												false
											)}
									</p> */}
								</Sheet.Section>

								<Sheet.Section>
									<Form.Label>
										{Liferay.Language.get('timezone')}
									</Form.Label>

									<p className='instructions'>
										{Liferay.Language.get(
											'select-a-timezone-that-will-be-used-for-all-data-reporting-in-your-workspace'
										)}
									</p>

									<TimeZonePicker
										fieldName='timeZoneId'
										initialTimeZone={
											project
												? new TimeZone(
														project.getIn([
															'timeZone'
														])
												  )
												: new TimeZone()
										}
										setFieldTouched={setFieldTouched}
										setFieldValue={setFieldValue}
									/>
								</Sheet.Section>

								<Sheet.Section>
									<Form.Input
										data-testid='friendly-url-input'
										disabled={
											disabled ||
											(project && project.friendlyURL)
										}
										label={Liferay.Language.get(
											'set-a-friendly-workspace-url'
										)}
										name='friendlyURL'
										secondaryInfo={
											<>
												{!editing &&
													Liferay.Language.get(
														'you-can-only-set-your-friendly-workspace-url-once'
													)}

												<span className='instructions'>
													{`${faroURL}/workspace`}
												</span>
											</>
										}
										text={{
											content: '/',
											position: 'prepend'
										}}
										validate={sequence([
											validateMinLength(2),
											validateMaxLength(255),
											validatePattern(
												/^(?=.*[a-z])[a-z0-9._-]+$/,
												sub(
													Liferay.Language.get(
														'workspace-url-must-only-contain-x-and-at-least-one-letter'
													),
													["a-z, 0-9, '.', '_', '-'"]
												) as string
											)
										])}
									/>
								</Sheet.Section>

								<Sheet.Section>
									<Form.InputList
										disabled={disabled}
										errorMessage={Liferay.Language.get(
											'please-enter-the-domain-in-this-format-domain-com'
										)}
										label={Liferay.Language.get(
											'allowed-email-domains'
										)}
										name='emailAddressDomains'
										onChangeInputList={setInputListValue}
										secondaryInfo={Liferay.Language.get(
											'anyone-with-an-email-address-at-these-domains-can-request-access-to-your-workspace'
										)}
										text={{
											content: '@',
											position: 'prepend'
										}}
										validate={items =>
											validateEmailDomainArr(
												items,
												inputListValue
											)
										}
										validationFn={validateEmailDomain}
									/>
								</Sheet.Section>

								<Sheet.Subtitle>
									{Liferay.Language.get('security')}
								</Sheet.Subtitle>

								<Sheet.Section>
									<Form.InputList
										errorMessage={Liferay.Language.get(
											'please-enter-the-email-in-this-format-sample-email-com'
										)}
										label={Liferay.Language.get(
											'add-incident-report-contacts'
										)}
										name='incidentReportEmailAddresses'
										onChangeInputList={
											setEmailAddressesInputValues
										}
										popover={{
											content: (
												<div className='add-workspace-popover-content'>
													{Liferay.Language.get(
														'this-person-will-be-contacted-in-the-event-of'
													)}

													<ul>
														<li>
															{Liferay.Language.get(
																'service-interruptions-fragment'
															)}
														</li>

														<li>
															{Liferay.Language.get(
																'security-incidents-fragment'
															)}
														</li>

														<li>
															{Liferay.Language.get(
																'other-urgent-service-updates-that-require-action-fragment'
															)}
														</li>
													</ul>
												</div>
											),
											title: Liferay.Language.get(
												'incident-report-contact'
											)
										}}
										required
										secondaryInfo={Liferay.Language.get(
											'who-should-we-contact-in-case-of-a-security-breach'
										)}
										validate={sequence([
											validateRequired,
											items =>
												validateEmailArr(
													items,
													emailAddressesInputValues
												)
										])}
										validationFn={validateEmail}
									/>
								</Sheet.Section>
							</Sheet.Header>

							<Sheet.Footer divider={false}>
								{!editing ? (
									<>
										<div className='terms'>
											<Form.Checkbox
												label={Liferay.Language.get(
													'i-agree'
												)}
												name='termsAcceptance'
												validate={validateRequired}
											/>

											<p>
												{sub(
													Liferay.Language.get(
														'by-selecting-i-agree-,-you-agree-to-our-x-including-our-x'
													),
													[
														<a
															href={
																URLConstants.TermsAndConditions
															}
															key="'terms-and-conditions'"
														>
															{Liferay.Language.get(
																'terms-and-conditions'
															)}
														</a>,
														<a
															href={
																URLConstants.PrivacyPolicy
															}
															key='privacy-policy'
														>
															{Liferay.Language.get(
																'privacy-policy'
															)}
														</a>
													],
													false
												)}
											</p>
										</div>

										<ClayButton
											block
											className='button-root'
											disabled={
												disabled ||
												isSubmitting ||
												!isValid
											}
											displayType='primary'
											type='submit'
										>
											{isSubmitting && (
												<Loading align={Align.Left} />
											)}

											{Liferay.Language.get(
												'finish-setup'
											)}
										</ClayButton>
									</>
								) : (
									<>
										<ClayButton
											className='button-root mr-3'
											disabled={
												disabled ||
												isSubmitting ||
												!isValid
											}
											displayType='primary'
											type='submit'
										>
											{isSubmitting && (
												<Loading align={Align.Left} />
											)}

											{Liferay.Language.get('save')}
										</ClayButton>

										<ClayButton
											className='button-root'
											disabled={disabled || !dirty}
											displayType='secondary'
											onClick={() =>
												resetForm(initialValues)
											}
										>
											{Liferay.Language.get('cancel')}
										</ClayButton>
									</>
								)}
							</Sheet.Footer>
						</Form.Form>
					)}
				</Form>
			</Sheet>
		</div>
	);
};

export default connect(null, {close, open})(AddWorkspaceForm);
