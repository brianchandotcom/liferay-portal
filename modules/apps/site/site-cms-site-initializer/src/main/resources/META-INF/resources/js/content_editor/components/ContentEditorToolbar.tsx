/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '../../../css/content_editor/ContentEditorToolbar.scss';

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import {ClayInput} from '@clayui/form';
import ClayLink from '@clayui/link';
import {isCtrlOrMeta} from '@liferay/layout-js-components-web';
import {sessionStorage, sub} from 'frontend-js-web';
import React, {useCallback, useEffect, useId, useState} from 'react';

import Toolbar from '../../common/components/Toolbar';
import AIAssistantChat from './AIAssistantChat/AIAssistantChat';
import {toMomentDate} from './ScheduleField';
import SchedulePublicationModal from './SchedulePublicationModal';

export const EVENT_VALIDATE_FORM = 'contentEditor:validateForm';

const STATUS_DRAFT_CODE = 2;

export default function ContentEditorToolbar({
	backURL,
	displayDate: initialDisplayDate,
	hasWorkflow,
	headerTitle,
	type,
}: {
	backURL: string;
	displayDate: string;
	hasWorkflow: boolean;
	headerTitle: string;
	type: string;
}) {
	const [displayDate, setDisplayDate] = useState<string>('');
	const [formId, setFormId] = useState<string | undefined>();
	const [showModal, setShowModal] = useState<boolean>(false);

	const optionsTitle = hasWorkflow
		? Liferay.Language.get('submit-for-workflow-options')
		: Liferay.Language.get('publish-options');
	const submitLabelId = useId();
	const submitTitle = getSubmitTitle(
		hasWorkflow
			? sub(Liferay.Language.get('submit-x-for-workflow'), type)
			: sub(Liferay.Language.get('publish-x'), type)
	);

	const getForm = useCallback((): HTMLFormElement => {
		let form = document.querySelector('.lfr-main-form-container');

		if (!form) {
			form = document.querySelector('.lfr-layout-structure-item-form');
		}

		return form as HTMLFormElement;
	}, []);

	const setSuccessMessage = useCallback(
		(message: string) => {
			const form = getForm();

			if (form?.checkValidity?.()) {
				const titleInput = form.querySelector(
					'[name^="ObjectField_title"]'
				) as HTMLInputElement;

				const value = titleInput ? titleInput.value : headerTitle;

				sessionStorage.setItem(
					'com.liferay.site.cms.site.initializer.successMessage',
					sub(message, `<strong>${value}</strong>`),
					sessionStorage.TYPES.NECESSARY
				);
			}
		},
		[getForm, headerTitle]
	);

	const handleSaveSuccessMessage = useCallback(() => {
		setSuccessMessage(Liferay.Language.get('x-was-saved-successfully'));
	}, [setSuccessMessage]);

	const handlePublishSuccessMessage = useCallback(() => {
		setSuccessMessage(
			hasWorkflow
				? Liferay.Language.get('x-was-submitted-for-workflow')
				: Liferay.Language.get('x-was-published-successfully')
		);
	}, [hasWorkflow, setSuccessMessage]);

	useEffect(() => {
		const form = getForm();

		if (form) {
			setFormId(form.id);

			const handlePublishShortcut = (event: KeyboardEvent) => {
				if (
					event.altKey &&
					event.key === 'Enter' &&
					isCtrlOrMeta(event)
				) {
					handlePublishSuccessMessage();

					form.submit();
				}
			};

			window.addEventListener('keydown', handlePublishShortcut);

			return () =>
				window.removeEventListener('keydown', handlePublishShortcut);
		}
	}, [getForm, handlePublishSuccessMessage]);

	return (
		<Toolbar
			backURL={backURL}
			className="content-editor__toolbar position-fixed"
			title={headerTitle}
		>
			{Liferay.FeatureFlags['LPD-62272'] && (
				<>
					<Toolbar.Item>
						<AIAssistantChat />
					</Toolbar.Item>

					<div
						className="align-self-center"
						style={{
							borderColor: '#A7A9BC',
							borderStyle: 'solid',
							borderWidth: 1,
							height: 16,
						}}
					/>
				</>
			)}

			<Toolbar.Item>
				<ClayLink
					aria-label={Liferay.Language.get('cancel')}
					borderless
					button
					displayType="secondary"
					href={backURL}
					small
				>
					{Liferay.Language.get('cancel')}
				</ClayLink>
			</Toolbar.Item>

			<Toolbar.Item>
				<ClayButton
					displayType="secondary"
					form={formId}
					name="status"
					onClick={handleSaveSuccessMessage}
					size="sm"
					type="submit"
					value={STATUS_DRAFT_CODE}
				>
					{Liferay.Language.get('save-as-draft')}
				</ClayButton>
			</Toolbar.Item>

			<Toolbar.Item>
				<ClayButton.Group>
					<ClayButton
						aria-labelledby={submitLabelId}
						data-title={submitTitle}
						data-title-set-as-html
						form={formId}
						onClick={(event) => {
							handlePublishSuccessMessage();

							Liferay.fire(EVENT_VALIDATE_FORM, {event});
						}}
						size="sm"
						type="submit"
					>
						{hasWorkflow
							? Liferay.Language.get('submit-for-workflow')
							: Liferay.Language.get('publish')}
					</ClayButton>

					<span
						className="sr-only"
						dangerouslySetInnerHTML={{__html: submitTitle}}
						id={submitLabelId}
					/>

					<ClayDropDownWithItems
						className="btn-group"
						items={[
							{
								label: hasWorkflow
									? Liferay.Language.get(
											'schedule-publication-and-submit-for-workflow'
										)
									: Liferay.Language.get(
											'schedule-publication'
										),
								onClick: () => setShowModal(true),
								symbolLeft: 'date-time',
							},
						]}
						trigger={
							<ClayButtonWithIcon
								aria-label={optionsTitle}
								size="sm"
								symbol="caret-bottom"
								title={optionsTitle}
							/>
						}
					/>
				</ClayButton.Group>

				<ClayInput
					form={formId}
					name="redirect"
					type="hidden"
					value={backURL}
				/>

				<ClayInput
					form={formId}
					name="ObjectEntry_displayDate"
					type="hidden"
					value={displayDate}
				/>
			</Toolbar.Item>

			{showModal ? (
				<SchedulePublicationModal
					date={toMomentDate(displayDate || initialDisplayDate)}
					formId={formId!}
					hasWorkflow={hasWorkflow}
					onCloseModal={() => setShowModal(false)}
					onUpdateDate={setDisplayDate}
					type={type}
				/>
			) : null}
		</Toolbar>
	);
}

function getSubmitTitle(title: string) {
	const isMac = Liferay.Browser?.isMac();

	return `
		<span class="d-block">
			${title}
		</span>
		<kbd class="c-kbd c-kbd-dark mt-1">
			<kbd class="c-kbd">${isMac ? '⌘' : 'Ctrl'}</kbd>
			<span class="c-kbd-separator"> + </span>
			<kbd class="c-kbd">${isMac ? '⌥' : 'Alt'}</kbd>
			<span class="c-kbd-separator"> + </span>
			<kbd class="c-kbd">${Liferay.Language.get('enter')}</kbd>
		</kbd>`
		.replaceAll('\n', '')
		.replaceAll('\t', '');
}
