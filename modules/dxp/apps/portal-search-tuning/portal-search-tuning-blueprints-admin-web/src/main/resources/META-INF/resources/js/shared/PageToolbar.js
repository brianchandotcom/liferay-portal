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
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayLink from '@clayui/link';
import ClayModal, {useModal} from '@clayui/modal';
import ClayNavigationBar from '@clayui/navigation-bar';
import ClayToolbar from '@clayui/toolbar';
import {ClayTooltipProvider} from '@clayui/tooltip';
import PropTypes from 'prop-types';
import React, {useContext, useRef, useState} from 'react';

import ThemeContext from './ThemeContext';

const DEFAULT_LOCALE = 'en-US';

function EditTitleModal({
	initialDescription,
	initialTitle,
	modalFieldFocus,
	observer,
	onClose,
	onSubmit,
}) {
	const [description, setDescription] = useState(initialDescription);
	const [hasError, setHasError] = useState(false);
	const [title, setTitle] = useState(initialTitle);

	const titleInput = useRef();

	const _handleSubmit = (event) => {
		event.preventDefault();

		if (!title[DEFAULT_LOCALE]) {
			setHasError(true);

			titleInput.current.focus();
		}
		else {
			onSubmit({description, title});

			onClose();
		}
	};

	return (
		<ClayModal
			className="blueprint-edit-title-modal"
			observer={observer}
			size="md"
		>
			<ClayForm onSubmit={_handleSubmit}>
				<ClayModal.Body>
					<ClayForm.Group className={hasError ? 'has-error' : ''}>
						<label htmlFor="name">
							{Liferay.Language.get('name')}

							<ClayIcon
								className="ml-1 reference-mark"
								focusable="false"
								role="presentation"
								symbol="asterisk"
							/>
						</label>

						<ClayInput
							autoFocus={modalFieldFocus === 'name'}
							id="name"
							onBlur={({currentTarget}) => {
								setHasError(!currentTarget.value);
							}}
							onChange={({target: {value}}) =>
								setTitle({
									[DEFAULT_LOCALE]: value,
								})
							}
							ref={titleInput}
							type="text"
							value={title[DEFAULT_LOCALE]}
						/>

						{hasError && (
							<ClayForm.FeedbackGroup>
								<ClayForm.FeedbackItem>
									<ClayForm.FeedbackIndicator symbol="exclamation-full" />
									{Liferay.Language.get(
										'this-field-is-required'
									)}
								</ClayForm.FeedbackItem>
							</ClayForm.FeedbackGroup>
						)}
					</ClayForm.Group>

					<ClayForm.Group>
						<label htmlFor="description">
							{Liferay.Language.get('description')}
						</label>

						<ClayInput
							autoFocus={modalFieldFocus === 'description'}
							component="textarea"
							id="description"
							onChange={({target: {value}}) =>
								setDescription({
									[DEFAULT_LOCALE]: value,
								})
							}
							type="text"
							value={description[DEFAULT_LOCALE]}
						/>
					</ClayForm.Group>
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
	initialDescription = {},
	initialTitle = {},
	isSubmitting,
	onCancel,
	onChangeTab,
	onSubmit,
	tab,
	tabs,
	children,
}) {
	const {namespace} = useContext(ThemeContext);

	const [description, setDescription] = useState(initialDescription);
	const [title, setTitle] = useState(initialTitle);

	const [modalFieldFocus, setModalFieldFocus] = useState('name');
	const [modalVisible, setModalVisible] = useState(false);

	const {observer, onClose} = useModal({
		onClose: () => setModalVisible(false),
	});

	const _renderLocalizedInputs = (inputId, translations) => {
		return Object.keys(translations).map((key) => (
			<input
				key={key}
				name={`${inputId}_${key.replace('-', '_')}`}
				type="hidden"
				value={translations[key]}
			/>
		));
	};

	const descriptionInputId = `${namespace}description`;
	const titleInputId = `${namespace}title`;

	const _handleClickEdit = (fieldFocus) => () => {
		setModalFieldFocus(fieldFocus);

		setModalVisible(true);
	};

	const _handleEditTitleSubmit = ({description, title}) => {
		setDescription(description);
		setTitle(title);
	};

	return (
		<div className="page-toolbar-root">
			<ClayToolbar light>
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
									onSubmit={_handleEditTitleSubmit}
								/>
							)}

							<div>
								<ClayButton
									aria-label={Liferay.Language.get(
										'edit-name'
									)}
									className="blueprint-heading-edit-button"
									displayType="unstyled"
									monospaced={false}
									onClick={_handleClickEdit('name')}
								>
									<div className="blueprint-title text-truncate">
										{title[DEFAULT_LOCALE]}

										<ClayIcon
											className="blueprint-heading-edit-icon"
											symbol="pencil"
										/>
									</div>
								</ClayButton>

								{_renderLocalizedInputs(titleInputId, title)}

								<ClayButton
									aria-label={Liferay.Language.get(
										'edit-description'
									)}
									className="blueprint-heading-edit-button"
									displayType="unstyled"
									monospaced={false}
									onClick={_handleClickEdit('description')}
								>
									<ClayTooltipProvider>
										<div
											className="blueprint-description text-truncate"
											data-tooltip-align="bottom"
											title={description[DEFAULT_LOCALE]}
										>
											{description[DEFAULT_LOCALE] ? (
												description[DEFAULT_LOCALE]
											) : (
												<span className="blueprint-description-blank">
													{Liferay.Language.get(
														'no-description'
													)}
												</span>
											)}

											<ClayIcon
												className="blueprint-heading-edit-icon"
												symbol="pencil"
											/>
										</div>
									</ClayTooltipProvider>
								</ClayButton>

								{_renderLocalizedInputs(
									descriptionInputId,
									description
								)}
							</div>
						</ClayToolbar.Item>

						{children}

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
				<ClayNavigationBar triggerLabel={tabs[tab]}>
					{Object.keys(tabs).map((tabKey) => (
						<ClayNavigationBar.Item
							active={tab === tabKey}
							key={tabKey}
						>
							<ClayButton
								block
								className="nav-link"
								displayType="unstyled"
								onClick={() => onChangeTab(tabKey)}
								small
							>
								<span className="navbar-text-truncate">
									{tabs[tabKey]}
								</span>
							</ClayButton>
						</ClayNavigationBar.Item>
					))}
				</ClayNavigationBar>
			)}
		</div>
	);
}

PageToolbar.propTypes = {
	initialDescription: PropTypes.object,
	initialTitle: PropTypes.object,
	isSubmitting: PropTypes.bool,
	onCancel: PropTypes.string.isRequired,
	onChangeTab: PropTypes.func,
	onSubmit: PropTypes.func.isRequired,
	tab: PropTypes.string,
	tabs: PropTypes.object,
	toolbarItem: PropTypes.element,
};
