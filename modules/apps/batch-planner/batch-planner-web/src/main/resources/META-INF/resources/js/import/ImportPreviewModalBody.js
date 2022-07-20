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
import ClayModal from '@clayui/modal';
import ClayTable from '@clayui/table';
import React from 'react';

const ImportPreviewModalBody = ({
	closeModal,
	fieldsSelections,
	fileContent,
	startImport,
}) => {
	const dbFieldsSelected = Object.keys(fieldsSelections).sort();

	return (
		<>
			<ClayModal.Header>
				{Liferay.Language.get('preview')}
			</ClayModal.Header>

			<ClayModal.Body className="p-0" scrollable>
				<ClayTable borderless hover={false}>
					<ClayTable.Head>
						<ClayTable.Row>
							{dbFieldsSelected.map((dbFieldName) => (
								<ClayTable.Cell headingCell key={dbFieldName}>
									{dbFieldName}
								</ClayTable.Cell>
							))}
						</ClayTable.Row>
					</ClayTable.Head>

					<ClayTable.Body>
						{fileContent.map((rowData, index) => (
							<ClayTable.Row
								key={`${JSON.stringify(rowData)}_${index}`}
							>
								{dbFieldsSelected.map((dbField) => {
									const fileField = fieldsSelections[dbField];

									return (
										<ClayTable.Cell
											key={dbField}
											truncate="true"
										>
											{JSON.stringify(rowData[fileField])}
										</ClayTable.Cell>
									);
								})}
							</ClayTable.Row>
						))}
					</ClayTable.Body>
				</ClayTable>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={closeModal}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							data-testid="start-import"
							displayType="primary"
							onClick={startImport}
							type="submit"
						>
							{Liferay.Language.get('start-import')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</>
	);
};

export default ImportPreviewModalBody;
