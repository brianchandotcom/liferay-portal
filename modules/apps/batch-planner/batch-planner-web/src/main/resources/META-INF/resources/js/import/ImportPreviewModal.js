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

import CellPreview from './CellPreview';

const ImportPreviewModal = ({
	closeModal,
	fieldsSelections,
	fileContentPreview,
	setStartImport,
}) => {
	return (
		<>
			<ClayModal.Header>
				{Liferay.Language.get('preview')}
			</ClayModal.Header>

			<ClayModal.Body className="p-2">
				<ClayTable borderless hover={false}>
					<ClayTable.Head>
						<ClayTable.Row>
							{Object.keys(fieldsSelections).map(
								(element, index) => {
									return (
										<ClayTable.Cell
											headingCell
											key={`preview_table_header-${index}`}
										>
											{element}
										</ClayTable.Cell>
									);
								}
							)}
						</ClayTable.Row>
					</ClayTable.Head>

					<ClayTable.Body className="inline-scroller w-100">
						{fileContentPreview.map((row, index) => {
							return (
								<ClayTable.Row
									key={`preview_table_row-${index}`}
								>
									{Object.values(row).map(
										(cell, cellIndex) => {
											return (
												<CellPreview
													cell={cell}
													cellIndex={cellIndex}
													fileRows={
														fileContentPreview
													}
													key={cellIndex}
													rowIndex={index}
												/>
											);
										}
									)}
								</ClayTable.Row>
							);
						})}
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
							disabled={
								fieldsSelections?.length === 0 &&
								fileContentPreview?.length === 0
							}
							displayType="primary"
							onClick={() => setStartImport(true)}
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

export default ImportPreviewModal;
