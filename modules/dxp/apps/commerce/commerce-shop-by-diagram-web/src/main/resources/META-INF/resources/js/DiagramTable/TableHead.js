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

import {ClayCheckbox} from '@clayui/form';
import ClayTable from '@clayui/table';
import classNames from 'classnames';
import React from 'react';

export default function TableHead({
	isAdmin,
	selectableSkusId,
	selectedSkusId,
	setSelectedSkusId,
}) {
	return (
		<ClayTable.Head>
			<ClayTable.Row>
				{!isAdmin && (
					<ClayTable.Cell headingCell>
						<ClayCheckbox
							checked={
								selectedSkusId.length ===
								selectableSkusId.length
							}
							indeterminate={
								selectedSkusId.length > 0 &&
								selectedSkusId.length < selectableSkusId.length
							}
							onChange={() => {
								setSelectedSkusId(
									selectedSkusId.length !==
										selectableSkusId.length
										? selectableSkusId
										: []
								);
							}}
						/>
					</ClayTable.Cell>
				)}

				<ClayTable.Cell headingCell>#</ClayTable.Cell>

				<ClayTable.Cell className="table-cell-expand-small" headingCell>
					{Liferay.Language.get('sku-or-diagram')}
				</ClayTable.Cell>

				<ClayTable.Cell
					className={classNames(isAdmin && 'text-right')}
					headingCell
				>
					{Liferay.Language.get('quantity')}
				</ClayTable.Cell>

				{!isAdmin && (
					<ClayTable.Cell className="text-right" headingCell>
						{Liferay.Language.get('price')}
					</ClayTable.Cell>
				)}
			</ClayTable.Row>
		</ClayTable.Head>
	);
}
