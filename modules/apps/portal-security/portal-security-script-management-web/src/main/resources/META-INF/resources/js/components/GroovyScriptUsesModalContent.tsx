/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Body, Cell, Head, Row, Table, Text} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import ClayModal from '@clayui/modal';
import React from 'react';

import {GroovyScriptUseItem} from './ScriptManagementContainer';

import './GroovyScriptUsesModalContent.scss';

interface GroovyScriptUsesModalProps {
	groovyScriptUses: GroovyScriptUseItem[];
	handleOnClose: () => void;
}

const tableHeaderItems = [
	{
		id: 'scriptSource',
		name: Liferay.Language.get('script-source'),
	},
	{
		id: 'instanceWebId',
		name: Liferay.Language.get('instance-web-id'),
	},
];

export function GroovyScriptUsesModalContent({
	groovyScriptUses,
	handleOnClose,
}: GroovyScriptUsesModalProps) {
	return (
		<>
			<ClayModal.Header>
				{Liferay.Language.get('setting-cannot-be-deactivated')}
			</ClayModal.Header>

			<ClayModal.Body className="lfr__script-management-groovy-script-uses-modal-body">
				<div className="lfr__script-management-groovy-script-uses-modal-description">
					<Text color="secondary">
						{Liferay.Language.get(
							'resolve-all-active-scripting-uses-before-proceeding'
						)}
					</Text>

					<Text color="secondary">
						{Liferay.Language.get(
							'these-active-scripting-uses-must-be-resolved-click-to-open-a-new-tab'
						)}
					</Text>
				</div>

				<Table headingNoWrap noWrap striped={false}>
					<Head items={tableHeaderItems}>
						{(column) => (
							<Cell expanded key={column.id}>
								{column.name}
							</Cell>
						)}
					</Head>

					<Body defaultItems={groovyScriptUses}>
						{(row) => (
							<Row>
								<Cell expanded>
									<ClayLink
										displayType="tertiary"
										href={row.sourceURL}
										target="_blank"
										weight="semi-bold"
									>
										{row.sourceName}
									</ClayLink>
								</Cell>

								<Cell expanded>{row.companyWebId}</Cell>

								<Cell>
									<ClayLink
										displayType="secondary"
										href={row.sourceURL}
										role="button"
										target="_blank"
										weight="semi-bold"
									>
										<ClayIcon
											aria-label="shortcut"
											symbol="shortcut"
										/>
									</ClayLink>
								</Cell>
							</Row>
						)}
					</Body>
				</Table>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton
						displayType="warning"
						onClick={() => handleOnClose()}
					>
						{Liferay.Language.get('done')}
					</ClayButton>
				}
			/>
		</>
	);
}
