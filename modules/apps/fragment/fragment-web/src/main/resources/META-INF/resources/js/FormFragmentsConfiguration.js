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
import ClayForm, {ClayInput} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import ClayTable from '@clayui/table';
import {
	createPortletURL,
	getPortletId,
	openSelectionModal,
} from 'frontend-js-web';
import React, {useState} from 'react';

export default function FormFragmentsConfiguration({
	formTypes,
	portletNamespace,
	selectFragmentURL,
	updateInputFragmentsURL,
}) {
	const [values, setValues] = useState({});

	const onClick = (name) => {
		openSelectionModal({
			onSelect: ({fragmententrykey, fragmententryname, groupkey}) => {
				setValues((previousValues) => ({
					...previousValues,
					[name]: {
						fragmentEntryKey: fragmententrykey,
						fragmentEntryName: fragmententryname,
						groupKey: groupkey,
					},
				}));
			},
			selectEventName: 'selectFragment',
			title: 'select',
			url: createPortletURL(selectFragmentURL, {
				inputType: name,
				p_p_id: getPortletId(portletNamespace),
			}),
		});
	};

	return (
		<ClayLayout.Container className="c-mt-3">
			<ClayForm action={updateInputFragmentsURL} method="POST">
				<div className="sheet">
					<h2 className="sheet-title">
						{Liferay.Language.get('form-fragments')}
					</h2>

					<p className="c-mb-4 text-3 text-secondary">
						{Liferay.Language.get(
							'define-the-default-form-fragments-for-this-site'
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
