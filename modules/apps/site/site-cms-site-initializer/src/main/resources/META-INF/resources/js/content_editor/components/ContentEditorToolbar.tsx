/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '../../../css/content_editor/ContentEditorToolbar.scss';

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import {ClayInput} from '@clayui/form';
import ClayLink from '@clayui/link';
import React, {useEffect, useState} from 'react';

import Toolbar from '../../common/components/Toolbar';
import {toMomentDate} from './ScheduleField';
import SchedulePublicationModal from './SchedulePublicationModal';

export const EVENT_VALIDATE_FORM = 'contentEditor:validateForm';

const STATUS_DRAFT_CODE = 2;

export default function ContentEditorToolbar({
	backURL,
	displayDate,
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
	const [formId, setFormId] = useState<string | undefined>();
	const [showModal, setShowModal] = useState<boolean>(false);

	useEffect(() => {
		let form = document.querySelector('.lfr-main-form-container');

		if (!form) {
			form = document.querySelector('.lfr-layout-structure-item-form');
		}

		if (form) {
			setFormId(form.id);
		}
	}, []);

	const SubmitButton = ({label}: {label: string}) => (
		<ClayButton
			form={formId}
			onClick={(event) => {
				Liferay.fire(EVENT_VALIDATE_FORM, {event});
			}}
			size="sm"
			type="submit"
		>
			{label}
		</ClayButton>
	);

	return (
		<Toolbar
			backURL={backURL}
			className="content-editor__toolbar position-fixed"
			title={headerTitle}
		>
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
					size="sm"
					type="submit"
					value={STATUS_DRAFT_CODE}
				>
					{Liferay.Language.get('save-as-draft')}
				</ClayButton>
			</Toolbar.Item>

			<Toolbar.Item>
				{hasWorkflow ? (
					<SubmitButton
						label={Liferay.Language.get('submit-for-workflow')}
					/>
				) : (
					<ClayButton.Group>
						<SubmitButton label={Liferay.Language.get('publish')} />

						<ClayDropDownWithItems
							className="btn-group"
							items={[
								{
									label: Liferay.Language.get(
										'schedule-publication'
									),
									onClick: () => setShowModal(true),
									symbolLeft: 'date-time',
								},
							]}
							trigger={
								<ClayButtonWithIcon
									aria-label={Liferay.Language.get(
										'publish-options'
									)}
									size="sm"
									symbol="caret-bottom"
									title={Liferay.Language.get(
										'publish-options'
									)}
								/>
							}
						/>
					</ClayButton.Group>
				)}

				<ClayInput
					form={formId}
					name="redirect"
					type="hidden"
					value={backURL}
				/>
			</Toolbar.Item>

			{showModal ? (
				<SchedulePublicationModal
					date={toMomentDate(displayDate)}
					onCloseModal={() => setShowModal(false)}
					type={type}
				/>
			) : null}
		</Toolbar>
	);
}
