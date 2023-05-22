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
import ClayForm, {ClayCheckbox, ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import classNames from 'classnames';
import React, {useState} from 'react';

interface Props {
	disposeModal: () => void;
	importURL: string;
	portletNamespace: string;
}

function ImportModal({disposeModal, importURL, portletNamespace}: Props) {
	const [error, setError] = useState('');
	const [isValidForm, setIsValidForm] = useState(false);
	const [overwrite, setOverwrite] = useState(true);

	const {observer, onClose} = useModal({
		onClose: disposeModal,
	});

	const validateFile = (event: React.ChangeEvent<HTMLInputElement>) => {
		if (!event.target.files || event.target.files?.length === 0) {
			setIsValidForm(false);

			return;
		}

		const fileName: string = event.target.files[0]?.name || '';

		const fileExtension = fileName
			.substring(fileName.lastIndexOf('.') + 1)
			.toLowerCase();

		if (fileExtension === 'zip') {
			setError('');
			setIsValidForm(true);
		}
		else {
			setError(Liferay.Language.get('only-zip-files-are-allowed'));
			setIsValidForm(false);
		}
	};

	return (
		<ClayModal observer={observer}>
			<ClayModal.Header>
				{Liferay.Language.get('import')}
			</ClayModal.Header>

			<form
				action={importURL}
				data-fm-namespace={portletNamespace}
				encType="multipart/form-data"
				id={`${portletNamespace}fm`}
				method="post"
				name={`${portletNamespace}fm`}
			>
				<ClayModal.Body>
					<p>
						{Liferay.Language.get(
							'select-a-zip-file-containing-one-or-multiple-entries'
						)}
					</p>

					<ClayForm.Group
						className={classNames({'has-error': error})}
					>
						<label htmlFor={`${portletNamespace}file`}>
							{Liferay.Language.get('file')}

							<ClayIcon
								className="reference-mark"
								symbol="asterisk"
							/>
						</label>

						<ClayInput
							data-testid={`${portletNamespace}file`}
							id={`${portletNamespace}file`}
							name={`${portletNamespace}file`}
							onChange={validateFile}
							required
							type="file"
						/>

						{error && (
							<ClayForm.FeedbackGroup>
								<ClayForm.FeedbackItem>
									<ClayForm.FeedbackIndicator symbol="exclamation-full" />

									{error}
								</ClayForm.FeedbackItem>
							</ClayForm.FeedbackGroup>
						)}
					</ClayForm.Group>

					<ClayCheckbox
						checked={overwrite}
						data-testid={`${portletNamespace}overwrite`}
						id={`${portletNamespace}overwrite`}
						label={Liferay.Language.get(
							'overwrite-existing-entries'
						)}
						name={`${portletNamespace}overwrite`}
						onChange={() => setOverwrite((val) => !val)}
					/>
				</ClayModal.Body>

				<ClayModal.Footer
					last={
						<ClayButton.Group spaced>
							<ClayButton
								displayType="secondary"
								onClick={onClose}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton
								disabled={!isValidForm}
								displayType="primary"
								type="submit"
							>
								{Liferay.Language.get('import')}
							</ClayButton>
						</ClayButton.Group>
					}
				/>
			</form>
		</ClayModal>
	);
}

export default ImportModal;
