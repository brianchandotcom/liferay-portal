import * as API from 'shared/api';
import ClayButton from '@clayui/button';
import FileDropTarget from 'shared/components/FileDropTarget';
import Form from 'shared/components/form';
import getCN from 'classnames';
import Input from 'shared/components/Input';
import Loading, {Align} from 'shared/components/Loading';
import Modal from 'shared/components/modal';
import React, {useRef, useState} from 'react';
import SearchInputList from 'shared/components/SearchInputList';
import {Formik, FormikValues} from 'formik';
import {paginationDefaults} from 'shared/util/pagination';
import {sub} from 'shared/util/lang';

const SAMPLE_CSV = 'user@example.com\nuser1@example.com\nuser2@example.com';

const {page} = paginationDefaults;

const AUTOCOMPLETE_DELTA = 5;

enum SubjectIdType {
	ByEmail = 'email',
	ByFile = 'file'
}

const getCheckboxLabel = (title: string, subtitle: string): React.ReactNode => (
	<span className='checkbox-label'>
		{title}

		<span className='text-secondary'>{` - ${subtitle}`}</span>
	</span>
);

interface INewRequestModalProps {
	groupId: string;
	onClose: () => void;
	onSubmit: (value: {
		emailAddresses?: string[];
		fileName?: string;
		types: string[];
	}) => void;
}

const NewRequestModal: React.FC<INewRequestModalProps> = ({
	groupId,
	onClose,
	onSubmit
}) => {
	const [emails, setEmails] = useState([]);
	const [fileName, setFileName] = useState(null);

	const _formRef = useRef<Formik>();

	const fetchIndividuals = (inputValue: string): Promise<any> =>
		API.individuals
			.search({
				delta: AUTOCOMPLETE_DELTA,
				filter: inputValue
					? `contains(demographics/email/value, '${inputValue}')`
					: '',
				groupId,
				page
			})
			.then(({items}) => items.map(({properties}) => properties.email));

	const handleAccessClick = event => {
		const {checked} = event.target;

		if (_formRef.current) {
			const {setFieldValue} = _formRef.current;

			setFieldValue('accessRequest', checked);
		}
	};

	const handleDeleteClick = event => {
		const {checked} = event.target;

		if (_formRef.current) {
			const {setFieldValue} = _formRef.current;

			setFieldValue('deleteRequest', checked);

			setFieldValue('suppressRequest', checked);
		}
	};

	const handleFileChange = file => {
		if (file) {
			const {completed, response, status} = file;

			const fileUploaded = completed && status !== 500;

			setFileName(fileUploaded ? response : null);
		} else {
			setFileName(null);
		}
	};

	const handleSubmit = ({
		accessRequest,
		deleteRequest,
		subjectIdType,
		suppressRequest
	}) => {
		let types = [];

		if (accessRequest) {
			types = [...types, 'ACCESS'];
		}

		if (suppressRequest) {
			types = [...types, 'SUPPRESS'];
		}

		if (deleteRequest) {
			types = [...types, 'DELETE'];
		}

		if (subjectIdType === SubjectIdType.ByEmail) {
			onSubmit({
				emailAddresses: emails,
				types
			});
		} else {
			onSubmit({
				fileName,
				types
			});
		}
	};

	const handleSuppressClick = event => {
		const {checked} = event.target;

		if (_formRef.current) {
			const {
				setFieldValue,
				state: {
					values: {deleteRequest}
				}
			} = _formRef.current;

			if (!deleteRequest) {
				setFieldValue('suppressRequest', checked);
			}
		}
	};

	const isValid = ({
		accessRequest,
		deleteRequest,
		subjectIdType,
		suppressRequest
	}: FormikValues): boolean => {
		const requests = accessRequest || deleteRequest || suppressRequest;

		const byEmail =
			subjectIdType === SubjectIdType.ByEmail && emails.length;
		const byFile = subjectIdType === SubjectIdType.ByFile && fileName;

		return requests && (byEmail || byFile);
	};

	return (
		<Modal className='new-request-modal-root'>
			<Modal.Header
				onClose={onClose}
				title={Liferay.Language.get('new-request')}
			/>

			<Form
				initialValues={{
					accessRequest: false,
					deleteRequest: false,
					subjectIdType: SubjectIdType.ByEmail,
					suppressRequest: false
				}}
				onSubmit={handleSubmit}
				ref={_formRef}
			>
				{({handleSubmit, isSubmitting, values}) => (
					<Form.Form onSubmit={handleSubmit}>
						<Modal.Body>
							<p className='text-secondary'>
								{Liferay.Language.get(
									'new-requests-will-be-added-to-the-queue-and-you-will-be-notified-once-the-job-has-completed-running'
								)}
							</p>

							<Form.Group>
								<h4>{Liferay.Language.get('job-type')}</h4>

								<Form.GroupItem>
									<Form.Checkbox
										autoFocus
										label={getCheckboxLabel(
											Liferay.Language.get('access'),
											Liferay.Language.get(
												'creates-downloadable-file-of-all-the-data-collected-related-to-a-user'
											)
										)}
										name='accessRequest'
										onChange={handleAccessClick}
										value='accessRequest'
									/>
								</Form.GroupItem>

								<Form.GroupItem>
									<Form.Checkbox
										label={getCheckboxLabel(
											Liferay.Language.get('delete'),
											Liferay.Language.get(
												'delete-users-pii-and-add-them-to-the-suppression-list'
											)
										)}
										name='deleteRequest'
										onChange={handleDeleteClick}
										value='deleteRequest'
									/>
								</Form.GroupItem>

								<Form.GroupItem>
									<Form.Checkbox
										disabled={values.deleteRequest}
										label={getCheckboxLabel(
											Liferay.Language.get('suppress'),
											Liferay.Language.get(
												'suppress-identity-resolution-of-users-based-on-their-email'
											)
										)}
										name='suppressRequest'
										onChange={handleSuppressClick}
										value='suppressRequest'
									/>
								</Form.GroupItem>
							</Form.Group>

							<Form.Group>
								<h4>
									{Liferay.Language.get('data-subject-id')}
								</h4>

								<Form.GroupItem>
									<Form.RadioGroup name='subjectIdType'>
										<Form.RadioGroup.Option
											key={SubjectIdType.ByEmail}
											label={Liferay.Language.get(
												'find-by-email'
											)}
											value={SubjectIdType.ByEmail}
										/>

										<Form.RadioGroup.Subsection>
											<Input.Group>
												<SearchInputList
													clearOnAdd
													containerClass='new-request-modal-container'
													dataSourceFn={
														fetchIndividuals
													}
													disabled={
														values.subjectIdType ===
														SubjectIdType.ByFile
													}
													items={emails}
													onItemsChange={setEmails}
													placeholder={Liferay.Language.get(
														'example-email'
													)}
												/>
											</Input.Group>
										</Form.RadioGroup.Subsection>

										<Form.RadioGroup.Option
											key={SubjectIdType.ByFile}
											label={Liferay.Language.get(
												'upload-file'
											)}
											value={SubjectIdType.ByFile}
										/>

										<Form.RadioGroup.Subsection
											className={getCN({
												hide:
													values.subjectIdType ===
													SubjectIdType.ByEmail
											})}
										>
											<div>
												<FileDropTarget
													fileTypes={['.csv']}
													onChange={handleFileChange}
													uploadURL={`/o/proxy/download/data-control-tasks?projectGroupId=${groupId}`}
												/>

												<div className='example-file-text text-secondary'>
													{sub(
														Liferay.Language.get(
															'please-upload-files-in-csv-format.-a-sample-file-can-be-found-x'
														),
														[
															<a
																download='example_user_request.csv'
																href={`data:text/octet-stream;charset=utf-8,${SAMPLE_CSV}`}
																key='EXAMPLE_FILE'
															>
																{Liferay.Language.get(
																	'here-fragment'
																)}
															</a>
														],
														false
													)}
												</div>
											</div>
										</Form.RadioGroup.Subsection>
									</Form.RadioGroup>
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
								disabled={!isValid(values)}
								displayType='primary'
								type='submit'
							>
								{isSubmitting && <Loading align={Align.Left} />}

								{Liferay.Language.get('save')}
							</ClayButton>
						</Modal.Footer>
					</Form.Form>
				)}
			</Form>
		</Modal>
	);
};

export default NewRequestModal;
