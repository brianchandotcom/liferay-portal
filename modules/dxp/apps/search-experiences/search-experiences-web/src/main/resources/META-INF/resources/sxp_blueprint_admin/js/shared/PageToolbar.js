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
import ClayLayout from '@clayui/layout';
import ClayLink from '@clayui/link';
import ClayLocalizedInput from '@clayui/localized-input';
import ClayModal, {useModal} from '@clayui/modal';
import ClayNavigationBar from '@clayui/navigation-bar';
import ClayToolbar from '@clayui/toolbar';
import {ClayTooltipProvider} from '@clayui/tooltip';
import getCN from 'classnames';
import PropTypes from 'prop-types';
import React, {useContext, useRef, useState} from 'react';

import {
	formatLocaleWithDashes,
	formatLocaleWithUnderscores,
	sub,
} from '../utils/language';
import {removeDuplicates} from '../utils/utils';
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

/**
 * Determines which language to display the title and description, by
 * checking what's available in title. This prevents title and description
 * from being displayed in two different languages. Preference is given to
 * the locale language, then the defaultLanguage. If neither are available,
 * it chooses the first available language.
 * @param {Object} title Titles in all available locales
 * @param {string} locale
 * @param {string} defaultLocale
 * @param {Object} availableLanguages
 * @returns {string}
 */
const getDisplayLocale = (title, locale, defaultLocale, availableLanguages) => {
	if (title[formatLocaleWithDashes(locale)]) {
		return formatLocaleWithDashes(locale);
	}

	if (title[formatLocaleWithDashes(defaultLocale)]) {
		return formatLocaleWithDashes(defaultLocale);
	}

	if (
		Object.keys(title).length &&
		Object.keys(availableLanguages).includes(
			formatLocaleWithUnderscores(Object.keys(title)[0])
		)
	) {
		return Object.keys(title)[0];
	}

	return formatLocaleWithDashes(defaultLocale);
};

function EditTitleModal({
	disabled,
	initialDescription,
	initialTitle,
	modalFieldFocus,
	observer,
	onClose,
	onSubmit,
}) {
	const {availableLanguages, defaultLocale, locale} = useContext(
		ThemeContext
	);

	// Converts the availableLanguages into the list expected for
	// Clay's localized input. Positions defaultLocale first in order
	// to view it as the 'default' option.

	const localesForSelector = removeDuplicates([
		defaultLocale,
		...Object.keys(availableLanguages),
	]).map(convertLocaleStringToObject);

	const [selectedLocale, setSelectedLocale] = useState(
		convertLocaleStringToObject(
			getDisplayLocale(
				initialTitle,
				locale,
				defaultLocale,
				availableLanguages
			)
		)
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
			className="sxp-edit-title-modal"
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
							autoFocus={modalFieldFocus === 'title'}
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
							autoFocus={modalFieldFocus === 'description'}
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

export default function PageToolbar({
	children,
	description,
	disableTitleAndDescriptionModal = false,
	isSubmitting,
	onCancel,
	onChangeTab,
	onSubmit,
	onTitleAndDescriptionChange,
	readOnly = false,
	tab,
	tabs,
	title,
}) {
	const {availableLanguages, defaultLocale, locale} = useContext(
		ThemeContext
	);

	const [modalFieldFocus, setModalFieldFocus] = useState('title');
	const [modalVisible, setModalVisible] = useState(false);

	const {observer, onClose} = useModal({
		onClose: () => setModalVisible(false),
	});

	const displayLocale = getDisplayLocale(
		title,
		locale,
		defaultLocale,
		availableLanguages
	);

	const _handleClickEdit = (fieldFocus) => () => {
		setModalFieldFocus(fieldFocus);

		setModalVisible(true);
	};

	return (
		<div className="page-toolbar-root">
			<ClayToolbar
				aria-label={Liferay.Language.get('page-toolbar')}
				light
			>
				<ClayLayout.ContainerFluid>
					<ClayToolbar.Nav>
						<ClayToolbar.Item className="text-left" expand>
							{modalVisible && (
								<EditTitleModal
									disabled={disableTitleAndDescriptionModal}
									initialDescription={description}
									initialTitle={title}
									modalFieldFocus={modalFieldFocus}
									observer={observer}
									onClose={onClose}
									onSubmit={onTitleAndDescriptionChange}
								/>
							)}

							{readOnly ? (
								<div>
									<div className="entry-title text-truncate">
										{title[displayLocale] || (
											<span className="entry-title-blank">
												{Liferay.Language.get(
													'untitled'
												)}
											</span>
										)}
									</div>

									<ClayTooltipProvider>
										<div
											className="entry-description text-truncate"
											data-tooltip-align="bottom"
											title={description[displayLocale]}
										>
											{description[displayLocale] || (
												<span className="entry-description-blank">
													{Liferay.Language.get(
														'no-description'
													)}
												</span>
											)}
										</div>
									</ClayTooltipProvider>
								</div>
							) : (
								<div>
									<ClayButton
										aria-label={Liferay.Language.get(
											'edit-title'
										)}
										className="entry-heading-edit-button"
										displayType="unstyled"
										monospaced={false}
										onClick={_handleClickEdit('title')}
									>
										<div className="entry-title text-truncate">
											{title[displayLocale] || (
												<span className="entry-title-blank">
													{Liferay.Language.get(
														'untitled'
													)}
												</span>
											)}

											<ClayIcon
												className="entry-heading-edit-icon"
												symbol="pencil"
											/>
										</div>
									</ClayButton>

									<ClayButton
										aria-label={Liferay.Language.get(
											'edit-description'
										)}
										className="entry-heading-edit-button"
										displayType="unstyled"
										monospaced={false}
										onClick={_handleClickEdit(
											'description'
										)}
									>
										<ClayTooltipProvider>
											<div
												className="entry-description text-truncate"
												data-tooltip-align="bottom"
												title={
													description[displayLocale]
												}
											>
												{description[displayLocale] || (
													<span className="entry-description-blank">
														{Liferay.Language.get(
															'no-description'
														)}
													</span>
												)}

												<ClayIcon
													className="entry-heading-edit-icon"
													symbol="pencil"
												/>
											</div>
										</ClayTooltipProvider>
									</ClayButton>
								</div>
							)}
						</ClayToolbar.Item>

						{children}

						{!!children && (
							<ClayToolbar.Item>
								<div className="tbar-divider" />
							</ClayToolbar.Item>
						)}

						{readOnly ? (
							<ClayToolbar.Item>
								<ClayLink
									displayType="secondary"
									href={onCancel}
									outline="secondary"
								>
									{Liferay.Language.get('close')}
								</ClayLink>
							</ClayToolbar.Item>
						) : (
							<>
								<ClayToolbar.Item>
									<ClayLink
										displayType="secondary"
										href={onCancel}
										outline="secondary"
									>
										{Liferay.Language.get('cancel')}
									</ClayLink>
								</ClayToolbar.Item>

								<ClayToolbar.Item>
									<ClayButton
										disabled={isSubmitting}
										onClick={onSubmit}
										small
										type="submit"
									>
										{Liferay.Language.get('save')}
									</ClayButton>
								</ClayToolbar.Item>
							</>
						)}
					</ClayToolbar.Nav>
				</ClayLayout.ContainerFluid>
			</ClayToolbar>

			{onChangeTab && (
				<ClayNavigationBar
					aria-label={Liferay.Language.get('navigation')}
					triggerLabel={tabs[tab]}
				>
					{Object.keys(tabs).map((tabKey) => (
						<ClayNavigationBar.Item
							active={tab === tabKey}
							key={tabKey}
						>
							<ClayButton onClick={() => onChangeTab(tabKey)}>
								{tabs[tabKey]}
							</ClayButton>
						</ClayNavigationBar.Item>
					))}
				</ClayNavigationBar>
			)}
		</div>
	);
}

PageToolbar.propTypes = {
	description: PropTypes.object,
	disableTitleAndDescriptionModal: PropTypes.bool,
	isSubmitting: PropTypes.bool,
	onCancel: PropTypes.string.isRequired,
	onChangeTab: PropTypes.func,
	onSubmit: PropTypes.func.isRequired,
	onTitleAndDescriptionChange: PropTypes.func,
	readOnly: PropTypes.bool,
	tab: PropTypes.string,
	tabs: PropTypes.object,
	title: PropTypes.object,
};
