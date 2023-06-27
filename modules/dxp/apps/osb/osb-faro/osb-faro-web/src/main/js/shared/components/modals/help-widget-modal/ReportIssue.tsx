import * as API from 'shared/api';
import ClayButton from '@clayui/button';
import Form, {
	validateMaxLength,
	validateRequired
} from 'shared/components/form';
import Loading, {Align} from 'shared/components/Loading';
import Modal from 'shared/components/modal';
import React from 'react';
import {addAlert} from 'shared/actions/alerts';
import {Alert} from 'shared/types';
import {connect, ConnectedProps} from 'react-redux';
import {IHelpWidgetScreenProps} from './types';
import {sequence} from 'shared/util/promise';

const connector = connect(null, {addAlert});

type PropsFromRedux = ConnectedProps<typeof connector>;

const ReportIssue: React.FC<
	IHelpWidgetScreenProps & PropsFromRedux & {addAlert: Alert.AddAlert}
> = ({addAlert, groupId, onClose, onNext}) => {
	const onSubmit = ({description, issueTitle}, {setSubmitting}) => {
		API.issue
			.create({
				currentUrl: window.location.href,
				description,
				groupId,
				title: issueTitle
			})
			.then(() => {
				setSubmitting(false);

				analytics.track('Submitted Free Tier Ticket', {
					currentUrl: window.location.href
				});

				onNext();
			})
			.catch(() => {
				addAlert({
					alertType: Alert.Types.Error,
					message: Liferay.Language.get(
						'there-was-an-error-processing-your-request.-please-try-again'
					)
				});

				setSubmitting(false);
			});
	};

	return (
		<>
			<Modal.Header
				onClose={onClose}
				title={Liferay.Language.get('report-an-issue')}
			/>

			<Form
				initialValues={{description: '', issueTitle: ''}}
				onSubmit={onSubmit}
			>
				{({handleSubmit, isSubmitting, isValid}) => (
					<Form.Form onSubmit={handleSubmit}>
						<Modal.Body>
							<Form.Group>
								<Form.GroupItem className='mb-4'>
									<Form.Input
										label={Liferay.Language.get(
											'issue-title'
										)}
										name='issueTitle'
										required
										validate={sequence([
											validateRequired,
											validateMaxLength(150)
										])}
									/>
								</Form.GroupItem>

								<Form.GroupItem>
									<Form.Input
										label={Liferay.Language.get(
											'description'
										)}
										name='description'
										required
										type='textarea'
										validate={validateRequired}
									/>
								</Form.GroupItem>

								<Form.GroupItem className='text-secondary'>
									{Liferay.Language.get(
										'please-include-as-many-details-as-possible'
									)}
								</Form.GroupItem>
							</Form.Group>
						</Modal.Body>

						<Modal.Footer>
							<ClayButton
								className='button-root'
								displayType='secondary'
								onClick={onClose}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton
								className='button-root'
								disabled={!isValid}
								displayType='primary'
								type='submit'
							>
								{isSubmitting && <Loading align={Align.Left} />}

								{Liferay.Language.get('submit')}
							</ClayButton>
						</Modal.Footer>
					</Form.Form>
				)}
			</Form>
		</>
	);
};

export default connector(ReportIssue);
