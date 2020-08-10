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

import ClayForm, {ClayCheckbox, ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React, {useEffect, useRef, useState} from 'react';

import Button from '../../components/button/Button.es';
import Popover from '../../components/popover/Popover.es';

const CustomObjectPopover = ({
	alignElement,
	className,
	forwardRef,
	onCancel,
	onSubmit,
	visible,
}) => {
	const nameInputRef = useRef();
	const [isAddFormView, setAddFormView] = useState(true);
	const [hasError, setHasError] = useState(false);
	const [isLoading, setLoading] = useState(false);

	const handleSubmit = () => {
		const name = nameInputRef.current.value;

		if (validate(name)) {
			setLoading(true);
			onSubmit({isAddFormView, name}).catch(() => {
				setLoading(false);
			});
		}
		else {
			nameInputRef.current.focus();
		}
	};

	const validate = (value) => {
		const invalid = value.trim() === '';

		setHasError(invalid);

		return !invalid;
	};

	const resetForm = () => {
		nameInputRef.current.value = '';

		setAddFormView(true);
	};

	useEffect(() => {
		if (visible) {
			nameInputRef.current.focus();
		}
		else {
			resetForm();
		}
	}, [alignElement, nameInputRef, visible]);

	return (
		<Popover
			alignElement={alignElement}
			className={`${className} mw-100`}
			content={() => (
				<ClayForm
					onSubmit={(event) => {
						event.preventDefault();

						handleSubmit();
					}}
				>
					<div
						className={`form-group ${
							hasError ? 'has-error' : ''
						} mb-2`}
					>
						<label htmlFor="customObjectNameInput">
							{Liferay.Language.get('name')}

							<span className="reference-mark">
								<ClayIcon symbol="asterisk" />
							</span>
						</label>

						<ClayInput
							className="form-control"
							id="customObjectNameInput"
							onChange={({currentTarget}) =>
								validate(currentTarget.value)
							}
							ref={nameInputRef}
							type="text"
						/>

						{hasError && (
							<ClayForm.FeedbackGroup>
								<ClayForm.FeedbackItem>
									<strong>
										<ClayForm.FeedbackIndicator
											className="mr-1"
											symbol="exclamation-full"
										></ClayForm.FeedbackIndicator>

										{Liferay.Language.get(
											'this-field-is-required'
										)}
									</strong>
								</ClayForm.FeedbackItem>
							</ClayForm.FeedbackGroup>
						)}
					</div>

					<ClayCheckbox
						aria-label={Liferay.Language.get(
							'continue-and-create-form-view'
						)}
						checked={isAddFormView}
						label={Liferay.Language.get(
							'continue-and-create-form-view'
						)}
						onChange={() => setAddFormView(!isAddFormView)}
					/>
				</ClayForm>
			)}
			footer={() => (
				<div className="border-top p-3" style={{width: 450}}>
					<div className="d-flex justify-content-end">
						<Button
							className="mr-3"
							displayType="secondary"
							onClick={() => {
								resetForm();

								onCancel();
							}}
							small
						>
							{Liferay.Language.get('cancel')}
						</Button>

						<Button
							disabled={isLoading}
							onClick={() => handleSubmit()}
							small
						>
							{Liferay.Language.get('continue')}
						</Button>
					</div>
				</div>
			)}
			ref={forwardRef}
			showArrow={false}
			suggestedPosition="bottom"
			title={() => (
				<h4 className="m-0">
					{Liferay.Language.get('new-custom-object')}
				</h4>
			)}
			visible={visible}
		/>
	);
};

export default React.forwardRef((props, ref) => (
	<CustomObjectPopover {...props} forwardRef={ref} />
));
