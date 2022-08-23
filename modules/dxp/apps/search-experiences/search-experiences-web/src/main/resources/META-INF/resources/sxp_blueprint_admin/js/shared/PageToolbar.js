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

import {formatLocaleWithDashes, sub} from '../utils/language';
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
 * @returns {string}
 */
const getDisplayLocale = (title, locale, defaultLocale) => {
	if (title[formatLocaleWithDashes(locale)]) {
		return formatLocaleWithDashes(locale);
	}
	if (title[formatLocaleWithDashes(defaultLocale)]) {
		return formatLocaleWithDashes(defaultLocale);
	}
	if (Object.keys(title).length) {
		return Object.keys(title)[0];
	}

	return formatLocaleWithDashes(defaultLocale);
};

function EditTitleModal({
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
			getDisplayLocale(initialTitle, locale, defaultLocale)
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

	const _handleChangeLocale = (inputRef) => (value) => {
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
			className="sxp-blueprint-edit-title-modal"
			observer={observer}
			size="md"
		>
			<ClayForm onSubmit={_handleSubmit}>
				<ClayModal.Body>
					<div
						className={getCN('edit-title', {'has-error': hasError})}
					>
						<ClayLocalizedInput
							autoFocus={modalFieldFocus === 'title'}
							id="title"
							label={_getTitleLabel()}
							locales={localesForSelector}
							onBlur={_handleBlur}
							onSelectedLocaleChange={_handleChangeLocale(
								titleInputRef
							)}
							onTranslationsChange={setTitle}
							placeholder=""
							ref={titleInputRef}
							selectedLocale={selectedLocale}
							translations={title}
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

					<div className="edit-description">
						<ClayLocalizedInput
							autoFocus={modalFieldFocus === 'description'}
							component="textarea"
							id="description"
							label={Liferay.Language.get('description')}
							locales={localesForSelector}
							onSelectedLocaleChange={_handleChangeLocale(
								descriptionInputRef
							)}
							onTranslationsChange={setDescription}
							placeholder=""
							ref={descriptionInputRef}
							selectedLocale={selectedLocale}
							translations={description}
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

							<ClayButton displayType="primary" type="submit">
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
	isSubmitting,
	onCancel,
	onChangeTab,
	onChangeTitleAndDescription,
	onSubmit,
	tab,
	tabs,
	title,
}) {
	const {defaultLocale, locale} = useContext(ThemeContext);

	const [modalFieldFocus, setModalFieldFocus] = useState('title');
	const [modalVisible, setModalVisible] = useState(false);

	const {observer, onClose} = useModal({
		onClose: () => setModalVisible(false),
	});

	const displayLocale = getDisplayLocale(title, locale, defaultLocale);

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
									initialDescription={description}
									initialTitle={title}
									modalFieldFocus={modalFieldFocus}
									observer={observer}
									onClose={onClose}
									onSubmit={onChangeTitleAndDescription}
								/>
							)}

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
										{title[displayLocale]}

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
									onClick={_handleClickEdit('description')}
								>
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

											<ClayIcon
												className="entry-heading-edit-icon"
												symbol="pencil"
											/>
										</div>
									</ClayTooltipProvider>
								</ClayButton>
							</div>
						</ClayToolbar.Item>

						{children}

						{!!children && (
							<ClayToolbar.Item>
								<div className="tbar-divider" />
							</ClayToolbar.Item>
						)}

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
	isSubmitting: PropTypes.bool,
	onCancel: PropTypes.string.isRequired,
	onChangeTab: PropTypes.func,
	onChangeTitleAndDescription: PropTypes.func,
	onSubmit: PropTypes.func.isRequired,
	tab: PropTypes.string,
	tabs: PropTypes.object,
	title: PropTypes.object,
};
