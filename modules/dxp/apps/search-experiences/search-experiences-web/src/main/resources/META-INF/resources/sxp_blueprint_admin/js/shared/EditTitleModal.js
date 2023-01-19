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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLocalizedInput from '@clayui/localized-input';
import ClayModal from '@clayui/modal';
import getCN from 'classnames';
import React, {useContext, useRef, useState} from 'react';

import removeDuplicates from '../utils/functions/remove_duplicates';
import formatLocaleWithDashes from '../utils/language/format_locale_with_dashes';
import sub from '../utils/language/sub';
import ThemeContext from './ThemeContext';

/**
 * Turns a basic locale into an object with original locale (label) and icon
 * for flag (symbol), used for Clay's localized input.
 * @param {string} locale Language identifier
 * @returns {Object}
 */
const convertLocaleStringToObject = (locale) => ({
	label: formatLocaleWithDashes(locale),
	symbol: formatLocaleWithDashes(locale).toLocaleLowerCase(),
});

export default function EditTitleModal({
	disabled,
	displayLocale,
	fieldFocus,
	initialDescription,
	initialTitle,
	observer,
	onClose,
	onSubmit,
}) {
	const {availableLanguages, defaultLocale} = useContext(ThemeContext);

	// Converts the availableLanguages into the list expected for
	// Clay's localized input. Positions defaultLocale first in order
	// to view it as the 'default' option.

	const localesForSelector = removeDuplicates([
		defaultLocale,
		...Object.keys(availableLanguages),
	]).map(convertLocaleStringToObject);

	const [selectedLocale, setSelectedLocale] = useState(
		convertLocaleStringToObject(displayLocale)
	);

	const defaultLocaleBCP47 = formatLocaleWithDashes(defaultLocale);

	const [description, setDescription] = useState(initialDescription);
	const [hasError, setHasError] = useState(false);
	const [title, setTitle] = useState(initialTitle);

	const descriptionInputRef = useRef();
	const titleInputRef = useRef();

	const _handleBlur = (event) => {
		if (selectedLocale.label === defaultLocaleBCP47) {
			setHasError(!event.currentTarget.value);
		}
		else {
			setHasError(!title[defaultLocaleBCP47]);
		}
	};

	const _handleSelectedLocaleChange = (inputRef) => (value) => {
		setSelectedLocale(value);
		inputRef.current.focus();
	};

	const _handleSubmit = (event) => {
		event.preventDefault();

		if (!title[defaultLocaleBCP47]) {
			setHasError(true);

			titleInputRef.current.focus();
		}
		else {
			onSubmit({description, title});

			onClose();
		}
	};

	const _getTitleLabel = () => (
		<>
			{Liferay.Language.get('title')}

			{selectedLocale.label === defaultLocaleBCP47 && (
				<ClayIcon
					className="ml-1 reference-mark"
					focusable="false"
					role="presentation"
					symbol="asterisk"
				/>
			)}
		</>
	);

	return (
		<ClayModal
			className="sxp-edit-title-modal-root"
			observer={observer}
			size="md"
		>
			<ClayForm onSubmit={_handleSubmit}>
				<ClayModal.Body>
					{disabled && (
						<ClayAlert
							displayType="danger"
							title={Liferay.Language.get('error')}
						>
							{sub(Liferay.Language.get('x-is-invalid'), [
								Liferay.Language.get('element-source-json'),
							])}
						</ClayAlert>
					)}

					<div
						className={getCN('edit-title', {
							disabled,
							'has-error': hasError,
						})}
					>
						<ClayLocalizedInput
							autoFocus={fieldFocus === 'title'}
							disabled={disabled}
							id="title"
							label={_getTitleLabel()}
							locales={localesForSelector}
							onBlur={_handleBlur}
							onSelectedLocaleChange={_handleSelectedLocaleChange(
								titleInputRef
							)}
							onTranslationsChange={setTitle}
							placeholder=""
							ref={titleInputRef}
							selectedLocale={selectedLocale}
							translations={disabled ? {} : title}
						/>

						{hasError && (
							<ClayForm.FeedbackGroup>
								<ClayForm.FeedbackItem>
									<ClayForm.FeedbackIndicator symbol="exclamation-full" />

									{sub(
										Liferay.Language.get(
											'please-enter-a-valid-title-for-the-default-language-x'
										),
										[defaultLocaleBCP47]
									)}
								</ClayForm.FeedbackItem>
							</ClayForm.FeedbackGroup>
						)}
					</div>

					<div
						className={getCN('edit-description', {
							disabled,
						})}
					>
						<ClayLocalizedInput
							autoFocus={fieldFocus === 'description'}
							component="textarea"
							disabled={disabled}
							id="description"
							label={Liferay.Language.get('description')}
							locales={localesForSelector}
							onSelectedLocaleChange={_handleSelectedLocaleChange(
								descriptionInputRef
							)}
							onTranslationsChange={setDescription}
							placeholder=""
							ref={descriptionInputRef}
							selectedLocale={selectedLocale}
							translations={disabled ? {} : description}
						/>
					</div>
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
								disabled={disabled}
								displayType="primary"
								type="submit"
							>
								{Liferay.Language.get('done')}
							</ClayButton>
						</ClayButton.Group>
					}
				/>
			</ClayForm>
		</ClayModal>
	);
}
