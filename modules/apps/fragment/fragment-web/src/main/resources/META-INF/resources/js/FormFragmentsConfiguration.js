/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import ClayTable from '@clayui/table';
import {
	createPortletURL,
	getPortletId,
	openSelectionModal,
	sub,
} from 'frontend-js-web';
import React, {useState} from 'react';

export default function FormFragmentsConfiguration({
	formTypes,
	portletNamespace,
	selectFragmentEntryURL,
	updateInputFragmentEntriesURL,
}) {
	const [values, setValues] = useState({});

	const onClick = (name) => {
		openSelectionModal({
			onSelect: ({fragmententrykey, fragmententryname, groupkey}) => {
				setValues((previousValues) => ({
					...previousValues,
					[name]: {
						fragmentEntryName: fragmententryname,
						groupKey: groupkey,
						key: fragmententrykey,
					},
				}));
			},
			selectEventName: 'selectFragment',
			title: sub(
				Liferay.Language.get('select-x'),
				Liferay.Language.get('fragment')
			),
			url: createPortletURL(selectFragmentEntryURL, {
				inputType: name,
				p_p_id: getPortletId(portletNamespace),
			}),
		});
	};

	return (
		<ClayLayout.Container className="c-mt-3">
			<ClayForm action={updateInputFragmentEntriesURL} method="POST">
				<div className="sheet">
					<h2 className="sheet-title">
						{Liferay.Language.get('form-fragments')}
					</h2>

					<p className="c-mb-4 text-3 text-secondary">
						{Liferay.Language.get(
							'define-the-default-form-fragments-for-this-site'
						)}

						{Liferay.Language.get(
							'the-selected-fragments-are-displayed-in-the-forms-created-on-the-pages-by-default'
						)}
					</p>

					<ClayTable className="c-mb-4">
						<ClayTable.Head>
							<ClayTable.Row>
								<ClayTable.Cell headingCell>
									{Liferay.Language.get('field-type')}
								</ClayTable.Cell>

								<ClayTable.Cell headingCell>
									{Liferay.Language.get('form-fragment')}
								</ClayTable.Cell>
							</ClayTable.Row>
						</ClayTable.Head>

						<ClayTable.Body>
							{formTypes.map(({fragmentName, label, name}) => (
								<ClayTable.Row key={label}>
									<ClayTable.Cell>{label}</ClayTable.Cell>

									<ClayTable.Cell>
										<ClayForm.Group
											className="c-mb-0"
											small
										>
											<ClayInput.Group>
												<ClayInput.GroupItem>
													<ClayInput
														onClick={() =>
															onClick(name)
														}
														readOnly
														value={
															values[name]
																?.fragmentEntryName ||
															fragmentName
														}
													/>
												</ClayInput.GroupItem>

												<ClayInput.GroupItem shrink>
													<ClayButton
														displayType="secondary"
														onClick={() =>
															onClick(name)
														}
														size="sm"
													>
														{Liferay.Language.get(
															'select'
														)}
													</ClayButton>
												</ClayInput.GroupItem>
											</ClayInput.Group>
										</ClayForm.Group>
									</ClayTable.Cell>
								</ClayTable.Row>
							))}
						</ClayTable.Body>
					</ClayTable>

					<div className="sheet-footer">
						<ClayButton.Group spaced>
							<ClayButton type="submit">
								{Liferay.Language.get('save')}
							</ClayButton>

							<ClayButton displayType="secondary">
								{Liferay.Language.get('cancel')}
							</ClayButton>
						</ClayButton.Group>
					</div>
				</div>

				<ClayInput
					name={`${portletNamespace}values`}
					readOnly
					type="hidden"
					value={JSON.stringify(values)}
				/>
			</ClayForm>
		</ClayLayout.Container>
	);
}
