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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button/lib/Button';
import {Context as ClayModalContext} from '@clayui/modal';
import ClayPanel from '@clayui/panel';
import React, {useContext} from 'react';

import AppContext from '../../../AppContext.es';
import {getItem} from '../../../utils/client.es';

const getName = ({name = {}}) => {
	const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

	return name[defaultLanguageId] || Liferay.Language.get('untitled');
};

export default () => {
	const [{dataDefinition, dataLayout}] = useContext(AppContext);
	const [{onClose}, dispatchModal] = useContext(ClayModalContext);

	return ({fieldSet, modal, onPropagate}) => {
		return getItem(
			`/o/data-engine/v2.0/data-definitions/${fieldSet.id}/data-definition-field-links`
		).then(({items}) => {
			const findDataDefinition = () => {
				const dataDefinitionField = dataDefinition.dataDefinitionFields.find(
					({customProperties: {ddmStructureId}}) =>
						ddmStructureId == fieldSet.id
				);

				if (dataDefinitionField) {
					const data = {
						dataDefinition,
						dataLayouts: [dataLayout],
						dataListViews: [],
					};

					items = [data];

					return data;
				}

				return {};
			};
			const {dataLayouts = [], dataListViews = []} =
				items[0] || findDataDefinition();

			const isFieldSetUsed =
				!!dataLayouts.length || !!dataListViews.length;

			if (!items.length || !isFieldSetUsed) {
				return onPropagate(fieldSet, true);
			}

			const Items = ({name}) => {
				return items.map((item, index) => {
					if (!item[name].length) {
						return null;
					}

					return (
						<div className="mb-4" key={index}>
							<label>{getName(item.dataDefinition)}</label>

							<ol>
								{item[name].map((content, index) => (
									<li key={index}>{getName(content)}</li>
								))}
							</ol>
						</div>
					);
				});
			};

			return new Promise((resolve) => {
				const {
					actionMessage,
					fieldSetMessage,
					headerMessage,
					warningMessage,
					...otherModalProps
				} = modal;

				const payload = {
					payload: {
						body: (
							<>
								{warningMessage && (
									<ClayAlert displayType="warning">
										<strong>
											{Liferay.Language.get('warning')}:
										</strong>

										{warningMessage}
									</ClayAlert>
								)}

								{fieldSetMessage && (
									<p className="fieldset-message">
										{fieldSetMessage}
									</p>
								)}

								{dataLayouts.length > 0 && (
									<ClayPanel
										className="remove-object-field-panel"
										displayTitle={Liferay.Language.get(
											'form-views'
										)}
										displayType="secondary"
									>
										<ClayPanel.Body>
											<Items name="dataLayouts" />
										</ClayPanel.Body>
									</ClayPanel>
								)}

								{dataListViews.length > 0 && (
									<ClayPanel
										className="remove-object-field-panel"
										displayTitle={Liferay.Language.get(
											'table-views'
										)}
										displayType="secondary"
									>
										<ClayPanel.Body>
											<Items name="dataListViews" />
										</ClayPanel.Body>
									</ClayPanel>
								)}
							</>
						),
						footer: [
							<></>,
							<></>,
							<ClayButton.Group key={0} spaced>
								<ClayButton
									displayType="secondary"
									key={1}
									onClick={onClose}
								>
									{Liferay.Language.get('cancel')}
								</ClayButton>
								<ClayButton
									key={2}
									onClick={() => {
										onPropagate(fieldSet);

										onClose();
									}}
								>
									{actionMessage}
								</ClayButton>
							</ClayButton.Group>,
						],
						header: headerMessage,
						size: 'md',
						...otherModalProps,
					},
					type: 1,
				};
				resolve(dispatchModal(payload));
			});
		});
	};
};
