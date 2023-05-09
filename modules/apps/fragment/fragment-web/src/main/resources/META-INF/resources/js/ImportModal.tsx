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
	portletNamespace: string;
}

function ImportModal({portletNamespace}: Props) {
	const [visible, setVisible] = useState(true);
	const [error, setError] = useState('');
	const [isValidForm, setIsValidForm] = useState(false);
	const [overwrite, setOverwrite] = useState(true);

	const {observer, onClose} = useModal({
		onClose: () => setVisible(false),
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
		visible && (
			<ClayModal observer={observer}>
				<ClayModal.Header>
					{Liferay.Language.get('import')}
				</ClayModal.Header>

				<form
					action="http://localhost:8080/group/guest/~/control_panel/manage?p_p_id=com_liferay_fragment_web_portlet_FragmentPortlet&p_p_lifecycle=1&p_p_state=pop_up&p_p_mode=view&_com_liferay_fragment_web_portlet_FragmentPortlet_javax.portlet.action=%2Ffragment%2Fimport&_com_liferay_fragment_web_portlet_FragmentPortlet_redirect=http%3A%2F%2Flocalhost%3A8080%2Fgroup%2Fguest%2F%7E%2Fcontrol_panel%2Fmanage%3Fp_p_id%3Dcom_liferay_fragment_web_portlet_FragmentPortlet%26p_p_lifecycle%3D0%26p_p_state%3Dpop_up%26p_p_mode%3Dview%26_com_liferay_fragment_web_portlet_FragmentPortlet_mvcRenderCommandName%3D%252Ffragment%252Fview_import%26_com_liferay_fragment_web_portlet_FragmentPortlet_bodyCssClass%3Dcadmin%2Bdialog-iframe-popup%26p_p_auth%3Du4H9TGmZ&_com_liferay_fragment_web_portlet_FragmentPortlet_portletResource=com_liferay_fragment_web_portlet_FragmentPortlet&_com_liferay_fragment_web_portlet_FragmentPortlet_fragmentCollectionId=0&p_auth=dQD6UEa6&p_p_auth=u4H9TGmZ"
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
		)
	);
}

export default ImportModal;
